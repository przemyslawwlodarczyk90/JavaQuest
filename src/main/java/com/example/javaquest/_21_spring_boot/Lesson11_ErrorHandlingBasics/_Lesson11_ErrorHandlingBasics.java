package com.example.javaquest._21_spring_boot.Lesson11_ErrorHandlingBasics;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class _Lesson11_ErrorHandlingBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: PODSTAWY OBSLUGI BLEDOW W SPRING BOOT ===");

        /*
         * ============================================================
         * 📦 DOMYSLNA STRONA/JSON BLEDU - /error, BasicErrorController
         * ============================================================
         * `_18_rest_api` uczyl PROJEKTOWANIA odpowiedzi bledu (RFC 7807).
         * Zanim NAPISZESZ wlasna obsluge (`@ControllerAdvice`,
         * `_22_spring_web/Lesson09`), Spring Boot JUZ MA DOMYSLNY
         * mechanizm: KAZDY nieobsluzony wyjatek w kontrolerze trafia do
         * `BasicErrorController` (auto-konfiguracja, jak w Lesson04),
         * ktory zwraca SPOJNY JSON (`timestamp`, `status`, `error`,
         * `path`) - BEZ jakiegokolwiek kodu z Twojej strony. Dzisiaj
         * zobaczysz TEN mechanizm NA ZYWO, przez PRAWDZIWY, wbudowany
         * serwer.
         */
        System.out.println("BasicErrorController (auto-konfiguracja) przechwytuje KAZDY nieobsluzony wyjatek i zwraca SPOJNY JSON - BEZ zadnego Twojego kodu.");

        demonstrateDefaultErrorJsonForUnhandledException();
        demonstrateResponseStatusExceptionControlsStatusCode();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `BasicErrorController` (auto-konfigurowany, `/error`) daje
         *   DOMYSLNA, SPOJNA odpowiedz JSON dla KAZDEGO nieobsluzonego
         *   wyjatku - PUNKT WYJSCIA, nie koniec drogi.
         * - `ResponseStatusException` pozwala KONTROLOWAC status HTTP
         *   BEZ pisania wlasnej klasy wyjatku.
         * - Pelna, WLASNA obsluga bledow (`@ControllerAdvice`+
         *   `@ExceptionHandler`, format RFC 7807 z `_18_rest_api/Lesson12`)
         *   to temat `_22_spring_web/Lesson09` - TAM przejmiesz PELNA
         *   kontrole nad KSZTALTEM odpowiedzi bledu.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    @RestController
    static class FailingController {
        @GetMapping("/boom")
        String boom() {
            throw new RuntimeException("Cos poszlo nie tak - NIEOBSLUZONY wyjatek");
        }

        @GetMapping("/missing/{id}")
        String missing(@PathVariable String id) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono zasobu o id: " + id);
        }
    }

    // BEZ jawnego @Bean - @RestController jest SAM w sobie @Component, wiec domyslny
    // @ComponentScan wewnatrz @SpringBootApplication ZNAJDZIE go automatycznie. Dodanie TEZ
    // jawnego @Bean dalo DWIE rejestracje TEGO SAMEGO kontrolera i blad "Ambiguous mapping" -
    // ta sama pulapka co @ComponentScan w _20_spring_core, tylko wywolana przez @SpringBootApplication.
    @SpringBootApplication
    static class ErrorHandlingApp {
    }

    private static void demonstrateDefaultErrorJsonForUnhandledException() throws Exception {
        System.out.println("\n=== NIEOBSLUZONY WYJATEK -> DOMYSLNY JSON BLEDU (BEZ ZADNEGO KODU OBSLUGI) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ErrorHandlingApp.class)
                .properties(props)
                .run();
        try {
            int port = getLocalServerPort(context);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/boom")).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("GET /boom -> status: " + response.statusCode());
            System.out.println("Cialo odpowiedzi (JSON WYGENEROWANY przez Boota AUTOMATYCZNIE): " + response.body());
            System.out.println("-> zauwaz pola 'timestamp'/'status'/'error'/'path' - TY nie napisales ANI JEDNEJ linii kodu obslugi tego bledu.");
        } finally {
            context.close();
        }
    }

    private static void demonstrateResponseStatusExceptionControlsStatusCode() throws Exception {
        System.out.println("\n=== ResponseStatusException: KONTROLA STATUSU BEZ WLASNEJ KLASY WYJATKU ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ErrorHandlingApp.class)
                .properties(props)
                .run();
        try {
            int port = getLocalServerPort(context);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/missing/42")).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("GET /missing/42 -> status: " + response.statusCode() + " (oczekiwane: 404, sterowane przez ResponseStatusException)");
            System.out.println("Cialo odpowiedzi: " + response.body());
            System.out.println("-> `ResponseStatusException(HttpStatus.NOT_FOUND, \"...\")` rzucony wprost w metodzie kontrolera - Boot PRZETLUMACZYL to na 404 automatycznie.");
        } finally {
            context.close();
        }
    }

    private static int getLocalServerPort(ConfigurableApplicationContext context) {
        // Boot ustawia te wlasciwosc automatycznie po wystartowaniu wbudowanego serwera na
        // losowym porcie (server.port=0) - wygodniejsze niz reczne rzutowanie na WebServerApplicationContext.
        return context.getEnvironment().getProperty("local.server.port", Integer.class);
    }
}
