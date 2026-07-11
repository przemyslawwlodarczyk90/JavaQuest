package com.example.javaquest._22_spring_web.Lesson10_ErrorResponseDto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

public class _Lesson10_ErrorResponseDto {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: DTO odpowiedzi bledu ===");

        /*
         * ============================================================
         * 📦 SPOJNY KSZTALT BLEDU - POGLEBIENIE _18_rest_api/Lesson12 (RFC 7807)
         * ============================================================
         * `_18_rest_api/Lesson12_ErrorResponseDesign` uczyl KONCEPCJI
         * RFC 7807 "Problem Details for HTTP APIs" na surowym
         * `HttpServer`. Tu pokazujemy DWA podejscia W Spring MVC:
         *
         * 1) WLASNE DTO bledu (rekord) - PELNA kontrola nad ksztaltem,
         *    ale TRZEBA go SAMEMU utrzymywac i wypelniac w KAZDYM
         *    handlerze.
         * 2) `ProblemDetail` (WBUDOWANA klasa Springa Framework 6+,
         *    od Spring Boot 3.0) - GOTOWA implementacja RFC 7807, Z
         *    metodami `forStatus(...)`/`forStatusAndDetail(...)` i
         *    mozliwoscia DODANIA wlasnych pol przez `setProperty(...)`.
         */
        System.out.println("RFC 7807 w Springu 6+: klasa ProblemDetail = gotowa implementacja, NIE trzeba pisac WLASNEGO DTO od zera.");

        demonstrateCustomErrorDto();
        demonstrateBuiltInProblemDetail();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - WLASNE DTO bledu = PELNA kontrola, ale WIECEJ kodu do
         *   utrzymania (i RYZYKO niespojnosci miedzy handlerami).
         * - `ProblemDetail` (Spring 6+) = WBUDOWANA, zgodna Z RFC 7807
         *   (pola `type`/`title`/`status`/`detail`/`instance`) +
         *   `setProperty("klucz", wartosc)` dla WLASNYCH rozszerzen.
         * - Domyslny `Content-Type` dla `ProblemDetail` to
         *   `application/problem+json` (NIE zwykle `application/json`) -
         *   zgodnie Z SPECYFIKACJA RFC 7807.
         * - W NOWYCH projektach (Boot 3+) preferuj `ProblemDetail` NAD
         *   WLASNYM DTO - mniej kodu, mniejsze ryzyko bledow, ZGODNOSC
         *   ZE standardem "z pudelka".
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    record ErrorResponseDto(Instant timestamp, int status, String error, String message, String path) {
        static ErrorResponseDto of(HttpStatus status, String message, String path) {
            return new ErrorResponseDto(Instant.now(), status.value(), status.getReasonPhrase(), message, path);
        }
    }

    static class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @RestController
    static class CustomDtoController {
        @GetMapping("/items/{id}")
        String getItem(@PathVariable String id) {
            if (!id.equals("I1")) {
                throw new ResourceNotFoundException("Element o ID '" + id + "' nie zostal znaleziony");
            }
            return "Element: " + id;
        }
    }

    @RestControllerAdvice
    static class CustomDtoAdvice {
        @ExceptionHandler(ResourceNotFoundException.class)
        ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex) {
            ErrorResponseDto body = ErrorResponseDto.of(HttpStatus.NOT_FOUND, ex.getMessage(), "/items/{id}");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }

    @SpringBootApplication
    static class CustomDtoApp {
    }

    private static void demonstrateCustomErrorDto() throws Exception {
        System.out.println("\n=== PODEJSCIE 1: WLASNY REKORD ErrorResponseDto ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CustomDtoApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGet(port, "/items/I999");
            System.out.println("GET /items/I999 -> status: " + response.statusCode() + ", Content-Type: " + response.headers().firstValue("Content-Type").orElse("BRAK"));
            System.out.println("body: " + response.body());
        } finally {
            context.close();
        }
    }

    static class OutOfStockException extends RuntimeException {
        final List<String> unavailableItems;

        OutOfStockException(List<String> unavailableItems) {
            super("Niektore pozycje sa niedostepne");
            this.unavailableItems = unavailableItems;
        }
    }

    @RestController
    static class ProblemDetailController {
        @GetMapping("/checkout")
        void checkout() {
            throw new OutOfStockException(List.of("SKU-1", "SKU-7"));
        }
    }

    @RestControllerAdvice
    static class ProblemDetailAdvice {
        @ExceptionHandler(OutOfStockException.class)
        ProblemDetail handleOutOfStock(OutOfStockException ex) {
            ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
            problem.setTitle("Brak dostepnosci produktow");
            problem.setProperty("unavailableItems", ex.unavailableItems);
            problem.setProperty("timestamp", Instant.now().toString());
            return problem;
        }
    }

    @SpringBootApplication
    static class ProblemDetailApp {
    }

    private static void demonstrateBuiltInProblemDetail() throws Exception {
        System.out.println("\n=== PODEJSCIE 2: WBUDOWANA KLASA ProblemDetail (Spring 6+, RFC 7807 'z pudelka') ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ProblemDetailApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGet(port, "/checkout");
            System.out.println("GET /checkout -> status: " + response.statusCode() + " (oczekiwane: 409), Content-Type: " + response.headers().firstValue("Content-Type").orElse("BRAK") + " (oczekiwane: application/problem+json)");
            System.out.println("body: " + response.body());
            System.out.println("-> Pola 'type'/'title'/'status'/'detail'/'instance' SA STANDARDOWE (RFC 7807) - 'unavailableItems'/'timestamp' to NASZE rozszerzenia przez setProperty(...).");
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
