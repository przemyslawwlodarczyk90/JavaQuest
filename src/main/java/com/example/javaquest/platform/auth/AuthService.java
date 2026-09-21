package com.example.javaquest.platform.auth;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import com.example.javaquest.platform.user.ConfirmationToken;
import com.example.javaquest.platform.user.ConfirmationTokenRepository;
import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class AuthService {

    private static final long CONFIRMATION_TOKEN_HOURS = 24;

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;
    private final String baseUrl;

    AuthService(UserRepository userRepository, ConfirmationTokenRepository tokenRepository,
                PasswordEncoder passwordEncoder, JwtService jwtService, MailService mailService,
                @Value("${app.base-url}") String baseUrl) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    /**
     * Zaklada konto (nieaktywne) i wysyla mail powitalny z linkiem. Jesli konto z tym e-mailem istnieje,
     * ale nigdy nie zostalo potwierdzone (np. mail zaginal), rejestracja NADPISUJE je nowymi danymi i
     * wysyla nowy link. Blad wysylki maila cofa cala transakcje - uzytkownik moze po prostu sprobowac
     * ponownie, bez "wiszacego" konta, ktorego nie da sie aktywowac.
     */
    @Transactional
    void register(String firstName, String lastName, String rawEmail, String rawPassword) {
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

        String token = UUID.randomUUID().toString();
        tokenRepository.save(new ConfirmationToken(token, LocalDateTime.now().plusHours(CONFIRMATION_TOKEN_HOURS), user));
        mailService.sendWelcomeEmail(user.getEmail(), user.getFirstName(), baseUrl + "/potwierdz?token=" + token);
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
