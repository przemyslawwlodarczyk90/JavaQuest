package com.example.javaquest.platform.auth;

import com.example.javaquest.platform.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * API logowania: rejestracja (mail powitalny), potwierdzenie konta, logowanie (JWT) i "kim jestem".
 * Publiczne sa tylko trzy pierwsze - patrz {@link SecurityConfig}.
 */
@RestController
@RequestMapping("/api/auth")
class AuthController {

    record RegisterRequest(
            @NotBlank(message = "Podaj imię.") @Size(max = 100, message = "Imię jest za długie.") String firstName,
            @NotBlank(message = "Podaj nazwisko.") @Size(max = 100, message = "Nazwisko jest za długie.") String lastName,
            @NotBlank(message = "Podaj adres e-mail.") @Email(message = "Nieprawidłowy adres e-mail.")
            @Size(max = 254, message = "Adres e-mail jest za długi.") String email,
            // 72 = limit BCrypt (dluzsze hasla byly by po cichu przycinane)
            @NotBlank(message = "Podaj hasło.")
            @Size(min = 8, max = 72, message = "Hasło musi mieć od 8 do 72 znaków.") String password) {
    }

    record LoginRequest(
            @NotBlank(message = "Podaj adres e-mail.") @Email(message = "Nieprawidłowy adres e-mail.") String email,
            @NotBlank(message = "Podaj hasło.") String password) {
    }

    record UserSummary(Long id, String firstName, String lastName, String email) {

        static UserSummary of(User user) {
            return new UserSummary(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        }
    }

    record AuthResponse(String token, UserSummary user) {
    }

    record MessageResponse(String message) {
    }

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    MessageResponse register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request.firstName(), request.lastName(), request.email(), request.password());
        return new MessageResponse("Konto utworzone. Wysłaliśmy mail z linkiem aktywacyjnym.");
    }

    @GetMapping("/confirm")
    MessageResponse confirm(@RequestParam String token) {
        authService.confirm(token);
        return new MessageResponse("Konto zostało aktywowane. Możesz się zalogować.");
    }

    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.email(), request.password());
    }

    @GetMapping("/me")
    UserSummary me(Authentication authentication) {
        return authService.currentUser(authentication.getName());
    }
}
