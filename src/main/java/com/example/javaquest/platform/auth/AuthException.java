package com.example.javaquest.platform.auth;

import org.springframework.http.HttpStatus;

/**
 * Jeden wyjatek dla wszystkich biznesowych bledow logowania/rejestracji - niesie status HTTP i
 * komunikat dla uzytkownika (pokazywany 1:1 we frontendzie), a {@link AuthExceptionHandler}
 * zamienia go na odpowiedz {@code {"message": "..."}}.
 */
class AuthException extends RuntimeException {

    private final HttpStatus status;

    private AuthException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    HttpStatus status() {
        return status;
    }

    static AuthException emailAlreadyInUse() {
        return new AuthException(HttpStatus.CONFLICT, "Konto z tym adresem e-mail już istnieje.", null);
    }

    static AuthException invalidCredentials() {
        return new AuthException(HttpStatus.UNAUTHORIZED, "Nieprawidłowy e-mail lub hasło.", null);
    }

    static AuthException accountNotActivated() {
        return new AuthException(HttpStatus.FORBIDDEN,
                "Konto nie zostało jeszcze aktywowane - kliknij link z maila powitalnego.", null);
    }

    static AuthException tokenNotFound() {
        return new AuthException(HttpStatus.NOT_FOUND, "Link potwierdzający jest nieprawidłowy lub został już użyty.",
                null);
    }

    static AuthException tokenExpired() {
        return new AuthException(HttpStatus.BAD_REQUEST,
                "Link potwierdzający wygasł - zarejestruj się ponownie, a wyślemy nowy.", null);
    }

    static AuthException mailDeliveryFailed(Throwable cause) {
        return new AuthException(HttpStatus.SERVICE_UNAVAILABLE,
                "Nie udało się wysłać maila z potwierdzeniem. Spróbuj ponownie za chwilę.", cause);
    }
}
