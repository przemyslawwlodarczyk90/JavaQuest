package com.example.javaquest._22_spring_web.Lesson08_ValidationWithValid;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class _Lesson08_ValidationWithValid {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 8: Walidacja z @Valid ===");

        /*
         * ============================================================
         * 📦 WALIDACJA NA GRANICY API - POGLEBIENIE _19_security_basics/Lesson17
         * ============================================================
         * `_19_security_basics/Lesson17_InputValidation` uczyl Bean
         * Validation (`@NotBlank`/`@Size`/`@Email` itd.) NA POZIOMIE
         * OGOLNYM. Tu pokazujemy DOKLADNY mechanizm integracji Z
         * Spring MVC: `@Valid` NA parametrze `@RequestBody` mowi
         * Springowi "PO deserializacji JSON, ZWALIDUJ obiekt WEDLUG jego
         * adnotacji - jesli COKOLWIEK nie przejdzie, rzuc wyjatek PRZED
         * wejsciem do kodu metody".
         *
         * Ten wyjatek to `MethodArgumentNotValidException` - Spring Boot
         * MA DLA NIEGO domyslny handler zwracajacy 400 Bad Request Z
         * lista bledow (KTORE pole, JAKI problem) - BEZ pisania
         * WLASNEGO kodu obslugi (Lesson09 pokaze, jak ten format
         * DOSTOSOWAC).
         */
        System.out.println("@Valid na @RequestBody = automatyczna walidacja PO deserializacji, PRZED wejsciem do kodu metody.");

        demonstrateValidAndInvalidRequestsAgainstSameEndpoint();
        demonstrateValidatedOnRequestParamGivesUnhandled500ByDefault();
        demonstrateValidatedOnRequestParamFixedWithExceptionHandler();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Valid` NA `@RequestBody` = wlacza Bean Validation DLA
         *   CALEGO obiektu (rekurencyjnie, jesli pola tez maja `@Valid`).
         * - Niepowodzenie walidacji = `MethodArgumentNotValidException`
         *   -> DOMYSLNIE 400 Bad Request Z lista bledow pol.
         * - `@Validated` NA KLASIE kontrolera + adnotacje (`@Min` itd.)
         *   BEZPOSREDNIO na `@RequestParam`/`@PathVariable` - walidacja
         *   POJEDYNCZYCH parametrow (NIE calego obiektu DTO). ALE UWAGA:
         *   niepowodzenie TEJ walidacji rzuca `ConstraintViolationException`
         *   - Spring Boot NIE MA dla niego domyslnego handlera (w
         *   odroznieniu od `MethodArgumentNotValidException`) - BEZ
         *   WLASNEGO `@ExceptionHandler` skonczy sie to 500 Internal
         *   Server Error, NIE 400! To NAJCZESTSZA pulapka przy
         *   walidacji parametrow (w odroznieniu od walidacji ciala
         *   zadania).
         * - Nastepna lekcja (`Lesson09_GlobalExceptionHandler`) pokaze,
         *   jak PRZECHWYCIC i SFORMATOWAC te bledy WEDLUG WLASNEGO
         *   ksztaltu odpowiedzi (np. RFC 7807 z `_18_rest_api/Lesson12`).
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    record RegisterUserRequest(
            @NotBlank(message = "Nazwa uzytkownika nie moze byc pusta") @Size(min = 3, max = 20, message = "Nazwa uzytkownika musi miec 3-20 znakow") String username,
            @NotBlank(message = "Email nie moze byc pusty") @Email(message = "Nieprawidlowy format email") String email,
            @NotBlank(message = "Haslo nie moze byc puste") @Size(min = 8, message = "Haslo musi miec co najmniej 8 znakow") String password
    ) {
    }

    @RestController
    static class RegistrationController {
        @PostMapping("/register")
        String register(@Valid @RequestBody RegisterUserRequest request) {
            return "Zarejestrowano uzytkownika: " + request.username();
        }
    }

    @SpringBootApplication
    static class RegistrationApp {
    }

    private static void demonstrateValidAndInvalidRequestsAgainstSameEndpoint() throws Exception {
        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RegistrationApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            System.out.println("\n=== PRAWIDLOWE DANE - WALIDACJA PRZECHODZI ===");
            String validJson = "{\"username\":\"jkowalski\",\"email\":\"jan@example.com\",\"password\":\"BezpieczneHaslo123\"}";
            HttpResponse<String> validResponse = httpPost(port, "/register", validJson);
            System.out.println("POST /register (prawidlowe dane) -> status: " + validResponse.statusCode() + ", body: " + validResponse.body());

            System.out.println("\n=== NIEPRAWIDLOWE DANE - DOMYSLNY 400 Z LISTA BLEDOW POL ===");
            // 'username' za krotki, 'email' bez '@', 'password' za krotkie - TRZY naruszenia NARAZ.
            String invalidJson = "{\"username\":\"ab\",\"email\":\"nieprawidlowy-email\",\"password\":\"krotkie\"}";
            HttpResponse<String> invalidResponse = httpPost(port, "/register", invalidJson);
            System.out.println("POST /register (3 naruszenia walidacji) -> status: " + invalidResponse.statusCode() + " (oczekiwane: 400)");
            System.out.println("body: " + invalidResponse.body());
        } finally {
            context.close();
        }
    }

    @Validated
    @RestController
    static class UnhandledParamValidationController {
        @GetMapping("/products/search")
        String search(@RequestParam @Size(min = 2, message = "Zapytanie musi miec co najmniej 2 znaki") String query,
                      @RequestParam(defaultValue = "10") @Min(value = 1, message = "Rozmiar strony musi byc >= 1") int pageSize) {
            return "Szukam '" + query + "', rozmiar strony: " + pageSize;
        }
    }

    @SpringBootApplication
    static class UnhandledParamValidationApp {
    }

    private static void demonstrateValidatedOnRequestParamGivesUnhandled500ByDefault() throws Exception {
        System.out.println("\n=== PULAPKA: @Validated NA @RequestParam BEZ WLASNEGO HANDLERA -> 500, NIE 400! ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(UnhandledParamValidationApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> okResponse = httpGet(port, "/products/search?query=laptop&pageSize=5");
            System.out.println("GET /products/search?query=laptop&pageSize=5 -> status: " + okResponse.statusCode() + ", body: " + okResponse.body());

            HttpResponse<String> badResponse = httpGet(port, "/products/search?query=x&pageSize=5");
            System.out.println("GET /products/search?query=x (1 znak, za krotkie) -> status: " + badResponse.statusCode() + " (RZECZYWISTOSC: 500 - ConstraintViolationException NIE MA domyslnego handlera w Spring MVC!)");
        } finally {
            context.close();
        }
    }

    @Validated
    @RestController
    static class HandledParamValidationController {
        @GetMapping("/products/search-handled")
        String search(@RequestParam @Size(min = 2, message = "Zapytanie musi miec co najmniej 2 znaki") String query,
                      @RequestParam(defaultValue = "10") @Min(value = 1, message = "Rozmiar strony musi byc >= 1") int pageSize) {
            return "Szukam '" + query + "', rozmiar strony: " + pageSize;
        }

        @ExceptionHandler(ConstraintViolationException.class)
        ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
            // WLASNY handler - dopiero TERAZ ConstraintViolationException daje 400, NIE 500.
            String message = ex.getConstraintViolations().stream()
                    .map(v -> v.getMessage())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("Nieprawidlowe parametry zadania");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @SpringBootApplication
    static class HandledParamValidationApp {
    }

    private static void demonstrateValidatedOnRequestParamFixedWithExceptionHandler() throws Exception {
        System.out.println("\n=== NAPRAWA: WLASNY @ExceptionHandler(ConstraintViolationException.class) -> 400 ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(HandledParamValidationApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> badResponse = httpGet(port, "/products/search-handled?query=x&pageSize=5");
            System.out.println("GET /products/search-handled?query=x (1 znak, za krotkie) -> status: " + badResponse.statusCode() + " (oczekiwane: 400, dzieki WLASNEMU @ExceptionHandler), body: " + badResponse.body());
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpPost(int port, String path, String jsonBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
