package com.example.javaquest.platform.auth;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import com.example.javaquest.platform.user.ConfirmationToken;
import com.example.javaquest.platform.user.ConfirmationTokenRepository;
import com.example.javaquest.platform.user.PasswordResetToken;
import com.example.javaquest.platform.user.PasswordResetTokenRepository;
import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private static final long CONFIRMATION_TOKEN_HOURS = 24;
    // Krotszy niz token aktywacyjny - reset hasla jest bardziej wrazliwy (kto przechwyci ten link,
    // przejmuje konto), a uzytkownik klika go od razu z maila, nie za 20 godzin.
    private static final long PASSWORD_RESET_TOKEN_HOURS = 1;

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;
    private final String baseUrl;
    private final boolean confirmationRequired;

    AuthService(UserRepository userRepository, ConfirmationTokenRepository tokenRepository,
                PasswordResetTokenRepository passwordResetTokenRepository,
                PasswordEncoder passwordEncoder, JwtService jwtService, MailService mailService,
                @Value("${app.base-url}") String baseUrl,
                @Value("${app.mail.confirmation-required:true}") boolean confirmationRequired) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.confirmationRequired = confirmationRequired;
    }

    /**
     * Zaklada konto i wysyla mail powitalny z linkiem potwierdzajacym. Jesli konto z tym e-mailem
     * istnieje, ale nigdy nie zostalo potwierdzone (np. mail zaginal), rejestracja NADPISUJE je nowymi
     * danymi i wysyla nowy link. Blad wysylki maila cofa cala transakcje - uzytkownik moze po prostu
     * sprobowac ponownie, bez "wiszacego" konta, ktorego nie da sie aktywowac.
     *
     * <p>{@code app.mail.confirmation-required=false} (patrz konstruktor) omija CALY powyzszy
     * mechanizm - konto jest od razu aktywne, bez tokenu i bez wysylki maila. Dodane po tym, jak
     * lokalny SMTP (Gmail) zaczal odrzucac uwierzytelnianie (haslo aplikacji odrzucone przez Google,
     * NIEZALEZNIE od poprawnosci wartosci w javaquest-platform-secrets.properties) i blokowalo
     * rejestracje w 100% (transakcja zawsze wycofywana) - przelacznik NIE jest wlaczony domyslnie
     * (patrz default "true" powyzej) - odblokowuje lokalny dev bez dotykania Gmaila, ale NIE zastepuje
     * naprawy prawdziwego maila przed pokazaniem platformy komukolwiek poza deweloperem.
     *
     * @return true jesli konto zostalo aktywowane od razu (przelacznik wylaczony, jak wyzej)
     */
    @Transactional
    boolean register(String firstName, String lastName, String rawEmail, String rawPassword) {
        String email = normalize(rawEmail);
        String passwordHash = passwordEncoder.encode(rawPassword);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = userRepository.save(new User(firstName.trim(), lastName.trim(), email, passwordHash));
        } else if (user.isEnabled()) {
            throw AuthException.emailAlreadyInUse();
        } else {
            user.setFirstName(firstName.trim());
            user.setLastName(lastName.trim());
            user.setPassword(passwordHash);
            tokenRepository.deleteByUser(user);
        }

        if (!confirmationRequired) {
            log.warn("app.mail.confirmation-required=false - konto {} aktywowane BEZ potwierdzenia mailem "
                    + "(tylko dev, patrz javadoc register()).", user.getEmail());
            user.setEnabled(true);
            return true;
        }

        String token = UUID.randomUUID().toString();
        tokenRepository.save(new ConfirmationToken(token, LocalDateTime.now().plusHours(CONFIRMATION_TOKEN_HOURS), user));
        mailService.sendWelcomeEmail(user.getEmail(), user.getFirstName(), baseUrl + "/potwierdz?token=" + token);
        return false;
    }

    @Transactional
    void confirm(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(AuthException::tokenNotFound);
        if (confirmationToken.isExpired()) {
            throw AuthException.tokenExpired();
        }
        confirmationToken.getUser().setEnabled(true);
        tokenRepository.delete(confirmationToken);
    }

    /**
     * Jesli konto z tym e-mailem istnieje i jest aktywne, wysyla mail z linkiem do resetu hasla
     * (usuwajac najpierw ewentualny stary token - jedna prosba "wygrywa"). Milczy (bez wyjatku), gdy
     * konto nie istnieje albo nie jest jeszcze aktywowane - {@link AuthController} zwraca ZAWSZE ten
     * sam, ogolny komunikat niezaleznie od wyniku, zeby nie zdradzac, ktore adresy sa zarejestrowane
     * (ten sam powod co jeden komunikat dla zlego e-maila/hasla w {@link #login}).
     */
    @Transactional
    void forgotPassword(String rawEmail) {
        userRepository.findByEmail(normalize(rawEmail))
                .filter(User::isEnabled)
                .ifPresent(user -> {
                    passwordResetTokenRepository.deleteByUser(user);
                    String token = UUID.randomUUID().toString();
                    passwordResetTokenRepository.save(new PasswordResetToken(
                            token, LocalDateTime.now().plusHours(PASSWORD_RESET_TOKEN_HOURS), user));
                    mailService.sendPasswordResetEmail(user.getEmail(), user.getFirstName(),
                            baseUrl + "/reset-hasla?token=" + token);
                });
    }

    /**
     * Ustawia nowe haslo i zuzywa token (jednorazowy). NIE uniewaznia istniejacych tokenow JWT
     * uzytkownika - sa bezstanowe (podpisane, bez rejestru po stronie serwera), wiec juz wydane
     * tokeny dzialaja do swojego wygasniecia (patrz auth.jwt.expiration-days) NAWET po resecie hasla.
     * Do naprawienia tylko przez dodanie wersji/rewokacji tokenow (np. pole "tokenVersion" na User,
     * sprawdzane w JwtAuthFilter) - poza zakresem tej funkcji.
     */
    @Transactional
    void resetPassword(String token, String newRawPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(AuthException::passwordResetTokenNotFound);
        if (resetToken.isExpired()) {
            throw AuthException.passwordResetTokenExpired();
        }
        resetToken.getUser().setPassword(passwordEncoder.encode(newRawPassword));
        passwordResetTokenRepository.delete(resetToken);
    }

    /** Ten sam komunikat dla zlego e-maila i zlego hasla - nie zdradza, ktore konta istnieja. */
    @Transactional(readOnly = true)
    AuthController.AuthResponse login(String rawEmail, String rawPassword) {
        User user = userRepository.findByEmail(normalize(rawEmail))
                .filter(candidate -> passwordEncoder.matches(rawPassword, candidate.getPassword()))
                .orElseThrow(AuthException::invalidCredentials);
        if (!user.isEnabled()) {
            throw AuthException.accountNotActivated();
        }
        return new AuthController.AuthResponse(jwtService.createToken(user.getEmail()), AuthController.UserSummary.of(user));
    }

    @Transactional(readOnly = true)
    AuthController.UserSummary currentUser(String email) {
        return userRepository.findByEmail(email)
                .map(AuthController.UserSummary::of)
                .orElseThrow(AuthException::invalidCredentials);
    }

    private static String normalize(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
