package com.example.javaquest.platform.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Obsluguje bledy TYLKO kontrolera logowania (assignableTypes) - nie dotyka reszty API platformy. */
@RestControllerAdvice(assignableTypes = AuthController.class)
class AuthExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthExceptionHandler.class);

    record ErrorResponse(String message, Map<String, String> fieldErrors) {
    }

    @ExceptionHandler(AuthException.class)
    ResponseEntity<ErrorResponse> handleAuth(AuthException ex) {
        if (ex.getCause() != null) {
            log.error("{} (przyczyna: {})", ex.getMessage(), ex.getCause().toString());
        }
        return ResponseEntity.status(ex.status()).body(new ErrorResponse(ex.getMessage(), Map.of()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(new ErrorResponse("Popraw zaznaczone pola.", fieldErrors));
    }
}
