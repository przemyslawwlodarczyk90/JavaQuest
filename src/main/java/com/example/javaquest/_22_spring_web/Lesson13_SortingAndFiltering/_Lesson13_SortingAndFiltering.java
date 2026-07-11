package com.example.javaquest._22_spring_web.Lesson13_SortingAndFiltering;

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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class _Lesson13_SortingAndFiltering {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: Sortowanie i filtrowanie ===");

        /*
         * ============================================================
         * 📦 SORTOWANIE I FILTROWANIE PRZEZ Spring MVC - POGLEBIENIE _18_rest_api/Lesson10
         * ============================================================
         * Tak jak stronicowanie (Lesson12), sortowanie/filtrowanie NA
         * tym etapie kursu piszemy RECZNIE - `_23_spring_data_jpa`
         * pokaze pozniej `Sort`/`Specification` ze Spring Data, KTORE
         * automatyzuja to NA POZIOMIE zapytania SQL (`ORDER BY`/
         * `WHERE`). Tu uczymy sie MECHANIZMU: `@RequestParam sort=pole,kierunek`
         * mapowany NA `Comparator<T>`, a WIELE opcjonalnych
         * `@RequestParam` LACZONYCH W `Predicate<T>`.
         */
        System.out.println("Sortowanie/filtrowanie reczne (Comparator/Predicate) = fundament do zrozumienia Sort/Specification ze Spring Data.");

        demonstrateSortingByQueryParam();
        demonstrateFilteringByMultipleOptionalParams();
        demonstrateCombinedFilterAndSort();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@RequestParam sort=pole,kierunek` (np. `name,desc`) ->
         *   ROZPARSOWANY na `Comparator<T>` (`Comparator.comparing(...)`,
         *   ewentualnie `.reversed()`).
         * - WIELE opcjonalnych `@RequestParam` (np. `minPrice`,
         *   `category`) -> LACZONE W JEDEN `Predicate<T>` (przez
         *   `Predicate.and(...)`) - TYLKO OBECNE filtry SA STOSOWANE.
         * - Kolejnosc operacji MA znaczenie: FILTRUJ najpierw (mniejszy
         *   zbior), POTEM sortuj (mniej elementow do posortowania) -
         *   dokladnie tak, jak w Lesson12 (stronicuj NA KONCU, PO
         *   filtrowaniu/sortowaniu).
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    record Product(String id, String name, String category, double price) {
    }

    static List<Product> buildCatalog() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("P1", "Klawiatura", "Elektronika", 249.99));
        products.add(new Product("P2", "Mysz", "Elektronika", 89.50));
        products.add(new Product("P3", "Biurko", "Meble", 599.00));
        products.add(new Product("P4", "Krzeslo", "Meble", 349.00));
        products.add(new Product("P5", "Monitor", "Elektronika", 899.99));
        return products;
    }

    static Comparator<Product> parseSortParam(String sort) {
        // Format: "pole,kierunek" np. "price,desc" lub "name,asc".
        String[] parts = sort.split(",");
        String field = parts[0];
        boolean descending = parts.length > 1 && parts[1].equalsIgnoreCase("desc");

        Comparator<Product> comparator = switch (field) {
            case "price" -> Comparator.comparingDouble(Product::price);
            case "name" -> Comparator.comparing(Product::name);
            case "category" -> Comparator.comparing(Product::category);
            default -> Comparator.comparing(Product::id);
        };
        return descending ? comparator.reversed() : comparator;
    }

    @RestController
    static class SortingController {
        private final List<Product> catalog = buildCatalog();

        @GetMapping("/products/sorted")
        List<Product> listSorted(@RequestParam(defaultValue = "name,asc") String sort) {
            List<Product> copy = new ArrayList<>(catalog);
            copy.sort(parseSortParam(sort));
            return copy;
        }
    }

    @SpringBootApplication
    static class SortingApp {
    }

    private static void demonstrateSortingByQueryParam() throws Exception {
        System.out.println("\n=== SORTOWANIE PRZEZ ?sort=pole,kierunek ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SortingApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /products/sorted?sort=price,asc -> " + httpGet(port, "/products/sorted?sort=price,asc").body());
            System.out.println("GET /products/sorted?sort=price,desc -> " + httpGet(port, "/products/sorted?sort=price,desc").body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class FilteringController {
        private final List<Product> catalog = buildCatalog();

        @GetMapping("/products/filtered")
        List<Product> listFiltered(@RequestParam Optional<String> category, @RequestParam Optional<Double> minPrice) {
            return catalog.stream()
                    .filter(p -> category.map(c -> c.equalsIgnoreCase(p.category())).orElse(true))
                    .filter(p -> minPrice.map(min -> p.price() >= min).orElse(true))
                    .toList();
        }
    }

    @SpringBootApplication
    static class FilteringApp {
    }

    private static void demonstrateFilteringByMultipleOptionalParams() throws Exception {
        System.out.println("\n=== FILTROWANIE PRZEZ WIELE OPCJONALNYCH @RequestParam ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(FilteringApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /products/filtered (BEZ filtrow) -> " + httpGet(port, "/products/filtered").body());
            System.out.println("GET /products/filtered?category=Meble -> " + httpGet(port, "/products/filtered?category=Meble").body());
            System.out.println("GET /products/filtered?category=Elektronika&minPrice=200 -> " + httpGet(port, "/products/filtered?category=Elektronika&minPrice=200").body());
        } finally {
            context.close();
        }
    }

    @RestController
    static class CombinedController {
        private final List<Product> catalog = buildCatalog();

        @GetMapping("/products/search")
        List<Product> search(@RequestParam Optional<String> category,
                             @RequestParam(defaultValue = "price,asc") String sort) {
            List<Product> filtered = catalog.stream()
                    .filter(p -> category.map(c -> c.equalsIgnoreCase(p.category())).orElse(true))
                    .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
            filtered.sort(parseSortParam(sort));
            return filtered;
        }
    }

    @SpringBootApplication
    static class CombinedApp {
    }

    private static void demonstrateCombinedFilterAndSort() throws Exception {
        System.out.println("\n=== FILTROWANIE + SORTOWANIE RAZEM (najpierw filtruj, potem sortuj) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(CombinedApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            System.out.println("GET /products/search?category=Elektronika&sort=price,desc -> " + httpGet(port, "/products/search?category=Elektronika&sort=price,desc").body());
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
