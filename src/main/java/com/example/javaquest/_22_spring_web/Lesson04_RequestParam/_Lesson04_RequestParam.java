package com.example.javaquest._22_spring_web.Lesson04_RequestParam;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

public class _Lesson04_RequestParam {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 4: @RequestParam ===");

        /*
         * ============================================================
         * 📦 PARAMETRY ZAPYTANIA (?klucz=wartosc) - POGLEBIENIE _18_rest_api/Lesson09
         * ============================================================
         * `@RequestParam` odczytuje wartosci z CZESCI ZAPYTANIA URL
         * (`?klucz=wartosc&inny=cos`) - w odroznieniu od `@PathVariable`
         * (Lesson03), ktory odczytuje z SEGMENTOW SCIEZKI. Typowe
         * uzycie: filtrowanie/sortowanie/stronicowanie (`_18_rest_api/
         * Lesson10`) - opcjonalne "dopiski" do zadania, NIE identyfikacja
         * zasobu.
         */
        System.out.println("@RequestParam = wartosci z '?klucz=wartosc' (zapytanie URL) - typowe dla filtrow/sortowania/stronicowania.");

        demonstrateRequiredParamFailsWithoutIt();
        demonstrateOptionalParamWithDefaultValue();
        demonstrateOptionalParamAsJavaOptional();
        demonstrateListOfValuesFromRepeatedParam();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@RequestParam` (bez atrybutow) - WYMAGANY - BRAK w zadaniu
         *   daje 400 Bad Request.
         * - `@RequestParam(defaultValue = "...")` - OPCJONALNY z
         *   WARTOSCIA DOMYSLNA, gdy brak.
         * - `@RequestParam(required = false)` LUB typ `Optional<T>` -
         *   OPCJONALNY BEZ wartosci domyslnej - `null`/`Optional.empty()`.
         * - `List<T>` jako typ parametru - zbiera WIELOKROTNE wystapienia
         *   TEGO SAMEGO klucza (`?tag=a&tag=b&tag=c`).
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    @RestController
    static class RequiredParamController {
        @GetMapping("/search")
        String search(@RequestParam String query) {
            return "Wyniki wyszukiwania dla: " + query;
        }
    }

    @SpringBootApplication
    static class RequiredParamApp {
    }

    private static void demonstrateRequiredParamFailsWithoutIt() throws Exception {
        System.out.println("\n=== WYMAGANY @RequestParam - BRAK DAJE 400 ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RequiredParamApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /search?query=laptop -> " + httpGet(port, "/search?query=laptop").body());

            HttpResponse<String> missingResponse = httpGet(port, "/search");
            System.out.println("GET /search (BEZ 'query') -> status: " + missingResponse.statusCode() + " (oczekiwane: 400)");
        } finally {
            context.close();
        }
    }

    @RestController
    static class DefaultValueController {
        @GetMapping("/products")
        String listProducts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
            return "Strona " + page + ", rozmiar " + size;
        }
    }

    @SpringBootApplication
    static class DefaultValueApp {
    }

    private static void demonstrateOptionalParamWithDefaultValue() throws Exception {
        System.out.println("\n=== OPCJONALNY @RequestParam Z WARTOSCIA DOMYSLNA ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DefaultValueApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /products (BEZ parametrow) -> " + httpGet(port, "/products").body());
            System.out.println("GET /products?page=3&size=25 -> " + httpGet(port, "/products?page=3&size=25").body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class OptionalController {
        @GetMapping("/notify")
        String notify(@RequestParam java.util.Optional<String> channel) {
            return channel.map(c -> "Powiadomienie WYSLANE przez: " + c).orElse("Powiadomienie NIE wyslane - brak wybranego kanalu");
        }
    }

    @SpringBootApplication
    static class OptionalApp {
    }

    private static void demonstrateOptionalParamAsJavaOptional() throws Exception {
        System.out.println("\n=== OPCJONALNY @RequestParam JAKO java.util.Optional<T> ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(OptionalApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /notify (BEZ 'channel') -> " + httpGet(port, "/notify").body());
            System.out.println("GET /notify?channel=email -> " + httpGet(port, "/notify?channel=email").body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class ListParamController {
        @GetMapping("/filter")
        String filterByTags(@RequestParam List<String> tag) {
            return "Filtrowanie po tagach: " + tag;
        }
    }

    @SpringBootApplication
    static class ListParamApp {
    }

    private static void demonstrateListOfValuesFromRepeatedParam() throws Exception {
        System.out.println("\n=== List<T> JAKO TYP @RequestParam - WIELOKROTNE WYSTAPIENIA TEGO SAMEGO KLUCZA ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ListParamApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /filter?tag=java&tag=spring&tag=boot -> " + httpGet(port, "/filter?tag=java&tag=spring&tag=boot").body());
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
