package com.example.javaquest._22_spring_web.Lesson09_GlobalExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class _Lesson09_GlobalExceptionHandler {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 9: Globalna obsluga wyjatkow (@RestControllerAdvice) ===");

        /*
         * ============================================================
         * 📦 SCENTRALIZOWANA OBSLUGA BLEDOW - POGLEBIENIE _17_architecture/Lesson16
         * ============================================================
         * `_17_architecture/Lesson16_ErrorHandlingArchitecture` uczyl
         * KONCEPCJI (scentralizowana obsluga bledow NA GRANICY aplikacji).
         * Tu pokazujemy KONKRETNY mechanizm Springa: `@ExceptionHandler`
         * NA POJEDYNCZYM kontrolerze (lokalny) vs `@RestControllerAdvice`
         * (GLOBALNY - jedna klasa lapie wyjatki ZE WSZYSTKICH
         * kontrolerow w aplikacji).
         *
         * Bez tego mechanizmu KAZDY kontroler musialby POWTARZAC ten
         * sam kod `try/catch` - `@RestControllerAdvice` centralizuje
         * logike w JEDNYM miejscu, dajac SPOJNY ksztalt bledu W CALYM
         * API (patrz Lesson08 - `MethodArgumentNotValidException` i
         * `ConstraintViolationException` wymagaja OSOBNEJ obslugi).
         */
        System.out.println("@RestControllerAdvice = JEDNA klasa przechwytujaca wyjatki ZE WSZYSTKICH kontrolerow w aplikacji.");

        demonstrateLocalExceptionHandlerOnSingleController();
        demonstrateGlobalControllerAdviceCatchesMultipleExceptionTypes();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@ExceptionHandler` NA kontrolerze = obsluguje wyjatki TYLKO
         *   Z TEGO JEDNEGO kontrolera.
         * - `@RestControllerAdvice` (= `@ControllerAdvice` +
         *   `@ResponseBody` NA CALEJ klasie) = GLOBALNY handler,
         *   DOMYSLNIE dla WSZYSTKICH kontrolerow w aplikacji.
         * - JEDNA klasa `@RestControllerAdvice` MOZE miec WIELE metod
         *   `@ExceptionHandler` - Spring wybiera NAJBARDZIEJ
         *   SZCZEGOLOWO dopasowany typ wyjatku.
         * - Nastepna lekcja (`Lesson10_ErrorResponseDto`) pokaze, jak
         *   ZAPROJEKTOWAC SPOJNY ksztalt DTO bledu (powiazanie z RFC
         *   7807 z `_18_rest_api/Lesson12`).
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    static class OrderNotFoundException extends RuntimeException {
        OrderNotFoundException(String orderId) {
            super("Zamowienie o ID '" + orderId + "' nie istnieje");
        }
    }

    @RestController
    static class LocalHandlerController {
        @GetMapping("/orders/{id}")
        String getOrder(@org.springframework.web.bind.annotation.PathVariable String id) {
            if (!id.equals("O1")) {
                throw new OrderNotFoundException(id);
            }
            return "Zamowienie: " + id;
        }

        @ExceptionHandler(OrderNotFoundException.class)
        ResponseEntity<String> handleNotFound(OrderNotFoundException ex) {
            // LOKALNY handler - dziala TYLKO dla wyjatkow rzuconych W TYM kontrolerze.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @SpringBootApplication
    static class LocalHandlerApp {
    }

    private static void demonstrateLocalExceptionHandlerOnSingleController() throws Exception {
        System.out.println("\n=== @ExceptionHandler LOKALNY (TYLKO na 1 kontrolerze) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(LocalHandlerApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /orders/O1 -> status: " + httpGet(port, "/orders/O1").statusCode());

            HttpResponse<String> missing = httpGet(port, "/orders/O999");
            System.out.println("GET /orders/O999 -> status: " + missing.statusCode() + " (oczekiwane: 404), body: " + missing.body());
        } finally {
            context.close();
        }
    }

    static class PaymentDeclinedException extends RuntimeException {
        PaymentDeclinedException(String reason) {
            super(reason);
        }
    }

    record CreatePaymentRequest(@NotBlank String cardNumber) {
    }

    @RestController
    static class PaymentController {
        @PostMapping("/payments")
        String createPayment(@Valid @RequestBody CreatePaymentRequest request) {
            return "Platnosc zaakceptowana dla karty: " + request.cardNumber();
        }

        @GetMapping("/payments/simulate-decline")
        void simulateDecline(@RequestParam String reason) {
            throw new PaymentDeclinedException(reason);
        }
    }

    @SpringBootApplication
    static class GlobalAdviceApp {
    }

    @RestControllerAdvice
    static class GlobalExceptionHandler {

        @ExceptionHandler(PaymentDeclinedException.class)
        @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
        Map<String, String> handlePaymentDeclined(PaymentDeclinedException ex) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "PAYMENT_DECLINED");
            body.put("reason", ex.getMessage());
            return body;
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
            // Obsluguje bledy @Valid na @RequestBody (patrz Lesson08) - GLOBALNIE, dla WSZYSTKICH kontrolerow.
            Map<String, String> body = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                    body.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(body);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
            // Obsluguje bledy @Validated na @RequestParam/@PathVariable (patrz Lesson08 - PULAPKA bez tego handlera = 500).
            return ResponseEntity.badRequest().body("Nieprawidlowe parametry: " + ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        ResponseEntity<String> handleGeneric(Exception ex) {
            // "Siatka bezpieczenstwa" - lapie WSZYSTKO, co NIE zostalo obsluzone POWYZEJ (bardziej szczegolowo).
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nieoczekiwany blad serwera");
        }
    }

    private static void demonstrateGlobalControllerAdviceCatchesMultipleExceptionTypes() throws Exception {
        System.out.println("\n=== @RestControllerAdvice GLOBALNY (WIELE typow wyjatkow, JEDNA klasa) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(GlobalAdviceApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> declined = httpGet(port, "/payments/simulate-decline?reason=Brak+srodkow");
            System.out.println("GET /payments/simulate-decline -> status: " + declined.statusCode() + " (oczekiwane: 402), body: " + declined.body());

            HttpResponse<String> invalidPayment = httpPost(port, "/payments", "{\"cardNumber\":\"\"}");
            System.out.println("POST /payments (puste 'cardNumber') -> status: " + invalidPayment.statusCode() + " (oczekiwane: 400), body: " + invalidPayment.body());
            System.out.println("-> WSZYSTKIE 3 rodzaje bledow (404-podobny/400-walidacja ciala/400-walidacja parametru) obsluzone przez JEDNA klase @RestControllerAdvice.");
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> httpPost(int port, String path, String jsonBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
