package com.example.javaquest._22_spring_web.Lesson03_PathVariable;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.UUID;

public class _Lesson03_PathVariable {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 3: @PathVariable ===");

        /*
         * ============================================================
         * 📦 ZMIENNE W SCIEZCE URL - POGLEBIENIE _18_rest_api/Lesson09
         * ============================================================
         * `_18_rest_api/Lesson09` uczyl KONCEPCJI zmiennych w sciezce
         * (`/orders/{id}`) na poziomie protokolu. `@PathVariable`
         * to MECHANIZM Springa, ktory AUTOMATYCZNIE wyciaga te wartosci
         * i KONWERTUJE je na WLASCIWY typ (String -> int, UUID, itd.) -
         * BEZ recznego parsowania sciezki (jak musialbys zrobic w
         * `_07_servlets` czy `_18_rest_api` z surowym `HttpServer`).
         */
        System.out.println("@PathVariable = automatyczne WYCIAGNIECIE i KONWERSJA zmiennej z URL - bez recznego parsowania sciezki.");

        demonstrateBasicPathVariable();
        demonstrateMultiplePathVariablesAndTypeConversion();
        demonstrateOptionalPathVariableAndTypeMismatch();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@PathVariable` wyciaga wartosc Z SEGMENTU sciezki (`{nazwa}`
         *   w `@GetMapping`) i wstrzykuje jako PARAMETR metody.
         * - Automatyczna KONWERSJA typu (String -> int/long/UUID/enum) -
         *   Spring PROBUJE skonwertowac, NIEUDANA konwersja = 400
         *   Bad Request.
         * - Nazwa parametru w Javie MUSI odpowiadac nazwie w `{...}`
         *   (chyba ze podasz JAWNIE `@PathVariable("inna-nazwa")`).
         * - Nastepna lekcja (`Lesson04_RequestParam`) pokaze DRUGI
         *   sposob przekazywania danych - parametry ZAPYTANIA
         *   (`?klucz=wartosc`), NIE segmenty sciezki.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    @RestController
    static class BasicController {
        @GetMapping("/products/{id}")
        String getProduct(@PathVariable String id) {
            return "Produkt o ID: " + id;
        }
    }

    @SpringBootApplication
    static class BasicApp {
    }

    private static void demonstrateBasicPathVariable() throws Exception {
        System.out.println("\n=== PODSTAWOWE @PathVariable ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(BasicApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /products/SKU-42 -> " + httpGet(port, "/products/SKU-42").body());
        } finally {
            context.close();
        }
    }

    record Order(String categoryId, int orderId, String status) {
    }

    @RestController
    static class MultiVariableController {
        @GetMapping("/categories/{categoryId}/orders/{orderId}")
        Order getOrder(@PathVariable String categoryId, @PathVariable int orderId) {
            // 'orderId' zostal AUTOMATYCZNIE przekonwertowany z String (segment URL) na int.
            return new Order(categoryId, orderId, "ZLOZONE");
        }
    }

    @SpringBootApplication
    static class MultiVariableApp {
    }

    private static void demonstrateMultiplePathVariablesAndTypeConversion() throws Exception {
        System.out.println("\n=== WIELE @PathVariable NARAZ + AUTOMATYCZNA KONWERSJA TYPU (String -> int) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(MultiVariableApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /categories/elektronika/orders/42 -> " + httpGet(port, "/categories/elektronika/orders/42").body());
            System.out.println("-> '42' (tekst z URL) zostalo skonwertowane na 'int orderId' AUTOMATYCZNIE - ZERO recznego Integer.parseInt().");

            HttpResponse<String> badResponse = httpGet(port, "/categories/elektronika/orders/NIE-LICZBA");
            System.out.println("GET /categories/elektronika/orders/NIE-LICZBA -> status: " + badResponse.statusCode() + " (oczekiwane: 400, konwersja 'NIE-LICZBA' -> int ZAWIODLA)");
        } finally {
            context.close();
        }
    }

    @RestController
    static class UuidController {
        @GetMapping("/sessions/{sessionId}")
        String getSession(@PathVariable UUID sessionId) {
            // Spring wie, jak skonwertowac String -> UUID (wbudowany konwerter).
            return "Sesja: " + sessionId;
        }
    }

    @SpringBootApplication
    static class UuidApp {
    }

    private static void demonstrateOptionalPathVariableAndTypeMismatch() throws Exception {
        System.out.println("\n=== KONWERSJA NA ZLOZONY TYP (UUID) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(UuidApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            UUID sampleId = UUID.randomUUID();
            System.out.println("GET /sessions/" + sampleId + " -> " + httpGet(port, "/sessions/" + sampleId).body());
            System.out.println("-> Spring skonwertowal String Z URL na PELNOPRAWNY obiekt java.util.UUID - BEZ recznego 'UUID.fromString(...)'.");
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
