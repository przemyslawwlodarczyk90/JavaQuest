package com.example.javaquest._22_spring_web.Lesson02_RequestMappingGetPostPutDelete;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class _Lesson02_RequestMappingGetPostPutDelete {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: @RequestMapping I SKROTY METOD HTTP ===");

        /*
         * ============================================================
         * 📦 STARY STYL vs NOWY STYL (Z _20_spring_core/Lesson02)
         * ============================================================
         * `@RequestMapping(method = RequestMethod.GET)` to STARY,
         * ROZWLEKLY zapis - dziala WCIAZ (nie jest deprecated), ale od
         * Spring 4.3 (2016, TA SAMA wersja co niejawna constructor
         * injection - `_20_spring_core/Lesson10`) istnieja SKROTY:
         * `@GetMapping`/`@PostMapping`/`@PutMapping`/`@DeleteMapping`/
         * `@PatchMapping` - KROTSZE, CZYTELNIEJSZE, DZISIAJ idiomatyczny
         * standard. Zobaczysz OBA style obok siebie, DZIALAJACE
         * IDENTYCZNIE.
         */
        System.out.println("@RequestMapping(method=...) = STARY, rozwlekly zapis. @GetMapping/@PostMapping/... (od Spring 4.3) = KROTSZY, DZISIEJSZY standard.");

        demonstrateOldStyleVsNewStyleSideBySide();
        demonstrateAllHttpMethodShortcuts();
        demonstrateClassLevelRequestMappingAsCommonPrefix();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@RequestMapping(method = RequestMethod.X)` i `@XMapping`
         *   (skrot) sa FUNKCJONALNIE IDENTYCZNE - skrot to TYLKO
         *   wygodniejszy zapis (meta-adnotacja NAD `@RequestMapping`).
         * - `@GetMapping`/`@PostMapping`/`@PutMapping`/`@DeleteMapping`/
         *   `@PatchMapping` - PO JEDNYM na KAZDA glowna metode HTTP
         *   (`_18_rest_api/Lesson04`).
         * - `@RequestMapping("/prefix")` NA KLASIE - WCIAZ potrzebne,
         *   nawet przy uzywaniu skrotow - definiuje WSPOLNY prefiks
         *   sciezki dla WSZYSTKICH metod.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    @RestController
    static class MixedStyleController {
        @RequestMapping(value = "/old-style", method = RequestMethod.GET)
        String oldStyle() {
            return "Odpowiedz ze STAREGO stylu: @RequestMapping(method = RequestMethod.GET)";
        }

        @GetMapping("/new-style")
        String newStyle() {
            return "Odpowiedz z NOWEGO stylu: @GetMapping";
        }
    }

    @SpringBootApplication
    static class MixedStyleApp {
    }

    private static void demonstrateOldStyleVsNewStyleSideBySide() throws Exception {
        System.out.println("\n=== STARY STYL I NOWY STYL - DZIALAJA IDENTYCZNIE ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(MixedStyleApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /old-style -> " + httpGet(port, "/old-style").body());
            System.out.println("GET /new-style -> " + httpGet(port, "/new-style").body());
            System.out.println("-> OBIE metody dzialaja - '@GetMapping' to PO PROSTU '@RequestMapping(method=GET)' pod inna, krotsza nazwa.");
        } finally {
            context.close();
        }
    }

    record Order(String id, String status) {
    }

    @RestController
    static class OrdersController {
        @GetMapping("/orders/{id}")
        Order getOrder(@org.springframework.web.bind.annotation.PathVariable String id) {
            return new Order(id, "ZLOZONE");
        }

        @PostMapping("/orders")
        Order createOrder() {
            return new Order("NOWE-1", "UTWORZONE");
        }

        @PutMapping("/orders/{id}")
        Order replaceOrder(@org.springframework.web.bind.annotation.PathVariable String id) {
            return new Order(id, "ZASTAPIONE");
        }

        @DeleteMapping("/orders/{id}")
        void deleteOrder(@org.springframework.web.bind.annotation.PathVariable String id) {
            System.out.println("    [OrdersController] usunieto zamowienie: " + id);
        }

        @PatchMapping("/orders/{id}")
        Order patchOrder(@org.springframework.web.bind.annotation.PathVariable String id) {
            return new Order(id, "CZESCIOWO ZAKTUALIZOWANE");
        }
    }

    @SpringBootApplication
    static class OrdersApp {
    }

    private static void demonstrateAllHttpMethodShortcuts() throws Exception {
        System.out.println("\n=== WSZYSTKIE SKROTY METOD HTTP NA ZYWO ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(OrdersApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();
            String base = "http://localhost:" + port;

            System.out.println("GET /orders/1 -> " + client.send(HttpRequest.newBuilder(URI.create(base + "/orders/1")).GET().build(), HttpResponse.BodyHandlers.ofString()).body());
            System.out.println("POST /orders -> " + client.send(HttpRequest.newBuilder(URI.create(base + "/orders")).POST(HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.ofString()).body());
            System.out.println("PUT /orders/1 -> " + client.send(HttpRequest.newBuilder(URI.create(base + "/orders/1")).PUT(HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.ofString()).body());
            System.out.println("PATCH /orders/1 -> " + client.send(HttpRequest.newBuilder(URI.create(base + "/orders/1")).method("PATCH", HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.ofString()).body());
            HttpResponse<String> deleteResponse = client.send(HttpRequest.newBuilder(URI.create(base + "/orders/1")).DELETE().build(), HttpResponse.BodyHandlers.ofString());
            System.out.println("DELETE /orders/1 -> status: " + deleteResponse.statusCode());
        } finally {
            context.close();
        }
    }

    @RestController
    @RequestMapping("/api/v1")
    static class PrefixedController {
        @GetMapping("/status")
        String status() {
            return "OK";
        }

        @GetMapping("/version")
        String version() {
            return "1.0.0";
        }
    }

    @SpringBootApplication
    static class PrefixedApp {
    }

    private static void demonstrateClassLevelRequestMappingAsCommonPrefix() throws Exception {
        System.out.println("\n=== @RequestMapping NA KLASIE: WSPOLNY PREFIKS DLA WSZYSTKICH METOD ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(PrefixedApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /api/v1/status -> " + httpGet(port, "/api/v1/status").body());
            System.out.println("GET /api/v1/version -> " + httpGet(port, "/api/v1/version").body());
            System.out.println("-> ZADNA metoda NIE powtarza '/api/v1' - prefiks jest zdefiniowany RAZ, na poziomie KLASY.");
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
