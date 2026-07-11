package com.example.javaquest._22_spring_web.Lesson05_RequestBody;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

public class _Lesson05_RequestBody {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 5: @RequestBody ===");

        /*
         * ============================================================
         * 📦 CIALO ZADANIA (REQUEST BODY) - JSON -> OBIEKT JAVA
         * ============================================================
         * `@RequestBody` mowi Springowi: "wez CALE cialo zadania HTTP i
         * ZDESERIALIZUJ je do tego obiektu". W odroznieniu od
         * `@RequestParam`/`@PathVariable` (male, pojedyncze wartosci z
         * URL), `@RequestBody` obsluguje ZLOZONE, ZAGNIEZDZONE struktury
         * danych - typowo JSON wysylany przy POST/PUT/PATCH.
         *
         * Mechanizm "pod maska": `HttpMessageConverter` (konkretnie
         * `MappingJackson2HttpMessageConverter`, auto-konfigurowany przez
         * Spring Boot dzieki obecnosci Jacksona na classpath - patrz
         * `_21_spring_boot/Lesson04_AutoConfiguration`) czyta naglowek
         * `Content-Type: application/json` i UZYWA Jacksona do
         * zdeserializowania body do WSKAZANEGO typu.
         */
        System.out.println("@RequestBody = cale cialo zadania (JSON) zdeserializowane do obiektu Javy przez Jacksona (HttpMessageConverter).");

        demonstrateBasicRequestBody();
        demonstrateNestedObjectRequestBody();
        demonstrateListRequestBody();
        demonstrateMalformedJsonGivesBadRequest();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@RequestBody` = deserializacja CALEGO ciala zadania (JSON)
         *   do obiektu (klasa/rekord).
         * - Dziala dla ZAGNIEZDZONYCH obiektow i list - Jackson
         *   REKURENCYJNIE mapuje strukture.
         * - Zle sformatowany JSON = 400 Bad Request (wyjatek
         *   `HttpMessageNotReadableException`), ZANIM logika kontrolera
         *   w ogole sie wykona.
         * - Nastepna lekcja (`Lesson06_ResponseEntity`) pokaze, jak
         *   PRECYZYJNIE kontrolowac kod statusu i naglowki ODPOWIEDZI.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    record CreateProductRequest(String name, double price) {
    }

    @RestController
    static class BasicController {
        @PostMapping("/products")
        String createProduct(@RequestBody CreateProductRequest request) {
            return "Utworzono produkt: " + request.name() + " za " + request.price() + " zl";
        }
    }

    @SpringBootApplication
    static class BasicApp {
    }

    private static void demonstrateBasicRequestBody() throws Exception {
        System.out.println("\n=== PODSTAWOWE @RequestBody (JSON -> rekord Javy) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(BasicApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String json = "{\"name\":\"Klawiatura\",\"price\":249.99}";
            System.out.println("POST /products " + json + " -> " + httpPost(port, "/products", json).body());
        } finally {
            context.close();
        }
    }

    record Address(String street, String city) {
    }

    record CreateCustomerRequest(String fullName, Address address) {
    }

    @RestController
    static class NestedController {
        @PostMapping("/customers")
        String createCustomer(@RequestBody CreateCustomerRequest request) {
            return "Klient: " + request.fullName() + ", miasto: " + request.address().city();
        }
    }

    @SpringBootApplication
    static class NestedApp {
    }

    private static void demonstrateNestedObjectRequestBody() throws Exception {
        System.out.println("\n=== ZAGNIEZDZONY OBIEKT W @RequestBody (Jackson mapuje REKURENCYJNIE) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NestedApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String json = "{\"fullName\":\"Jan Kowalski\",\"address\":{\"street\":\"Dluga 5\",\"city\":\"Krakow\"}}";
            System.out.println("POST /customers " + json + " -> " + httpPost(port, "/customers", json).body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class ListBodyController {
        @PostMapping("/orders/items")
        String addItems(@RequestBody List<String> itemNames) {
            return "Dodano " + itemNames.size() + " pozycji: " + itemNames;
        }
    }

    @SpringBootApplication
    static class ListBodyApp {
    }

    private static void demonstrateListRequestBody() throws Exception {
        System.out.println("\n=== TABLICA JSON JAKO List<T> W @RequestBody ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ListBodyApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String json = "[\"Mysz\",\"Podkladka\",\"Kabel USB\"]";
            System.out.println("POST /orders/items " + json + " -> " + httpPost(port, "/orders/items", json).body());
        } finally {
            context.close();
        }
    }

    @SpringBootApplication
    static class MalformedApp {
    }

    private static void demonstrateMalformedJsonGivesBadRequest() throws Exception {
        System.out.println("\n=== ZLE SFORMATOWANY JSON -> 400 Bad Request (PRZED logika kontrolera) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(MalformedApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String brokenJson = "{\"name\": \"Klawiatura\", \"price\": }";
            HttpResponse<String> response = httpPost(port, "/products", brokenJson);
            System.out.println("POST /products " + brokenJson + " -> status: " + response.statusCode() + " (oczekiwane: 400, HttpMessageNotReadableException)");
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
}
