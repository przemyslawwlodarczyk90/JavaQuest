package com.example.javaquest._22_spring_web.Lesson06_ResponseEntity;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson06_ResponseEntity {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 6: ResponseEntity ===");

        /*
         * ============================================================
         * 📦 PELNA KONTROLA NAD ODPOWIEDZIA HTTP
         * ============================================================
         * Gdy metoda `@RestController` zwraca ZWYKLY obiekt (jak w
         * Lesson01-05), Spring Boot AUTOMATYCZNIE ustawia status 200 OK
         * (chyba ze rzucony zostanie wyjatek). To wystarcza dla
         * WIEKSZOSCI przypadkow GET, ale NIE dla wszystkich - POST
         * tworzacy zasob POWINIEN zwrocic 201 Created (+ naglowek
         * `Location`), DELETE moze zwrocic 204 No Content, a wyszukiwanie
         * NIEISTNIEJACEGO zasobu - 404 Not Found.
         *
         * `ResponseEntity<T>` to "koperta" wokol ciala odpowiedzi,
         * dajaca PELNA kontrole nad: kodem statusu, naglowkami I cialem
         * NARAZ - w JEDNYM zwracanym obiekcie.
         */
        System.out.println("ResponseEntity<T> = pelna kontrola: status HTTP + naglowki + cialo odpowiedzi, WSZYSTKO w jednym obiekcie.");

        demonstratePlainObjectAlwaysReturns200();
        demonstrateResponseEntityWithCustomStatus();
        demonstrateCreatedWithLocationHeader();
        demonstrateNotFoundWhenResourceMissing();
        demonstrateResponseStatusAnnotationAlternative();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zwykly obiekt zwracany z `@RestController` = ZAWSZE 200 OK
         *   (przy sukcesie) - proste, ale MALO elastyczne.
         * - `ResponseEntity.ok(body)` / `.status(...)` / `.notFound()`
         *   / `.noContent()` - buduje odpowiedz Z DOWOLNYM statusem.
         * - `ResponseEntity.created(uri).body(...)` - idiomatyczny wzorzec
         *   201 Created + naglowek `Location` wskazujacy NOWO utworzony
         *   zasob.
         * - `@ResponseStatus` na METODZIE - PROSTSZA alternatywa, gdy
         *   status jest STALY (nie zalezy od logiki w metodzie) -
         *   `ResponseEntity` wygrywa, gdy status ZALEZY OD WYNIKU
         *   (np. znaleziono/nie znaleziono).
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    record Product(String id, String name) {
    }

    @RestController
    static class PlainObjectController {
        @GetMapping("/products/{id}")
        Product getProduct(@PathVariable String id) {
            // Zawsze 200 OK, NAWET gdyby 'id' NIE istnialo (tu upraszczamy - brak sprawdzenia).
            return new Product(id, "Przykladowy produkt");
        }
    }

    @SpringBootApplication
    static class PlainObjectApp {
    }

    private static void demonstratePlainObjectAlwaysReturns200() throws Exception {
        System.out.println("\n=== ZWYKLY OBIEKT ZWROCONY Z KONTROLERA = ZAWSZE 200 OK ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(PlainObjectApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGet(port, "/products/XYZ");
            System.out.println("GET /products/XYZ -> status: " + response.statusCode() + ", body: " + response.body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class CustomStatusController {
        @GetMapping("/health")
        ResponseEntity<Map<String, String>> health() {
            Map<String, String> body = new HashMap<>();
            body.put("status", "UP");
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Health-Check", "javaquest")
                    .body(body);
        }
    }

    @SpringBootApplication
    static class CustomStatusApp {
    }

    private static void demonstrateResponseEntityWithCustomStatus() throws Exception {
        System.out.println("\n=== ResponseEntity Z WLASNYM STATUSEM I NAGLOWKIEM ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CustomStatusApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpResponse<String> response = httpGet(port, "/health");
            System.out.println("GET /health -> status: " + response.statusCode() + ", X-Health-Check: " + response.headers().firstValue("X-Health-Check").orElse("BRAK") + ", body: " + response.body());
        } finally {
            context.close();
        }
    }

    static final AtomicInteger NEXT_ID = new AtomicInteger(1);

    record CreateOrderRequest(String item) {
    }

    @RestController
    static class CreatedController {
        @PostMapping("/orders")
        ResponseEntity<Map<String, Object>> createOrder(@RequestBody CreateOrderRequest request) {
            int newId = NEXT_ID.getAndIncrement();
            Map<String, Object> body = new HashMap<>();
            body.put("id", newId);
            body.put("item", request.item());

            URI location = URI.create("/orders/" + newId);
            return ResponseEntity.created(location).body(body);
        }
    }

    @SpringBootApplication
    static class CreatedApp {
    }

    private static void demonstrateCreatedWithLocationHeader() throws Exception {
        System.out.println("\n=== ResponseEntity.created(uri) -> 201 Created + naglowek 'Location' ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CreatedApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/orders"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"item\":\"Monitor\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("POST /orders -> status: " + response.statusCode() + " (oczekiwane: 201), Location: " + response.headers().firstValue("Location").orElse("BRAK") + ", body: " + response.body());
        } finally {
            context.close();
        }
    }

    static final Map<String, Product> WAREHOUSE = Map.of("P1", new Product("P1", "Laptop"));

    @RestController
    static class NotFoundController {
        @GetMapping("/warehouse/{id}")
        ResponseEntity<Product> findProduct(@PathVariable String id) {
            Product product = WAREHOUSE.get(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product);
        }
    }

    @SpringBootApplication
    static class NotFoundApp {
    }

    private static void demonstrateNotFoundWhenResourceMissing() throws Exception {
        System.out.println("\n=== ResponseEntity.notFound().build() -> 404 GDY ZASOB NIE ISTNIEJE ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(NotFoundApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /warehouse/P1 -> status: " + httpGet(port, "/warehouse/P1").statusCode() + " (oczekiwane: 200)");
            System.out.println("GET /warehouse/NIEISTNIEJE -> status: " + httpGet(port, "/warehouse/NIEISTNIEJE").statusCode() + " (oczekiwane: 404)");
        } finally {
            context.close();
        }
    }

    @RestController
    static class ResponseStatusController {
        @DeleteMapping("/sessions/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        void deleteSession(@PathVariable String id) {
            // Metoda 'void' + '@ResponseStatus' = ZAWSZE ten sam, STALY status (tu: 204).
            // Nie da sie tu zwrocic RÓZNEGO statusu w zaleznosci od logiki - do tego sluzy ResponseEntity.
        }
    }

    @SpringBootApplication
    static class ResponseStatusApp {
    }

    private static void demonstrateResponseStatusAnnotationAlternative() throws Exception {
        System.out.println("\n=== ALTERNATYWA: @ResponseStatus NA METODZIE (STALY status, prostszy zapis) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ResponseStatusApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/sessions/S1"))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("DELETE /sessions/S1 -> status: " + response.statusCode() + " (oczekiwane: 204, ustawione przez @ResponseStatus)");
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
