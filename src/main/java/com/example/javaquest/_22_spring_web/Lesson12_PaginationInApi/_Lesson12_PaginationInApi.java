package com.example.javaquest._22_spring_web.Lesson12_PaginationInApi;

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
import java.util.List;
import java.util.Properties;

public class _Lesson12_PaginationInApi {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: Stronicowanie w API ===");

        /*
         * ============================================================
         * 📦 STRONICOWANIE PRZEZ Spring MVC - POGLEBIENIE _18_rest_api/Lesson10
         * ============================================================
         * `_18_rest_api/Lesson10_PaginationSortingFiltering` uczyl
         * KONCEPCJI stronicowania NA surowym `HttpServer`. Tu pokazujemy
         * ten sam mechanizm PRZEZ Spring MVC: `@RequestParam` dla
         * `page`/`size` + WLASNE DTO `PageResponse<T>` opisujace
         * METADANE stronicowania (numer strony, rozmiar, calkowita
         * liczba elementow/stron).
         *
         * UWAGA: `_23_spring_data_jpa` (kolejny rozdzial) pokaze
         * WBUDOWANY mechanizm Spring Data (`Pageable`/`Page<T>`),
         * KTORY automatyzuje DOKLADNIE to, co tu piszemy RECZNIE -
         * przydatne, zeby ZROZUMIEC co Spring Data ROBI "pod maska",
         * ZANIM zaczniesz polegac na jego automatyzacji.
         */
        System.out.println("Reczne stronicowanie (page/size + PageResponse DTO) = fundament do zrozumienia Pageable/Page<T> ze Spring Data (_23_spring_data_jpa).");

        demonstrateManualPaginationWithMetadata();
        demonstratePaginationEdgeCases();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@RequestParam(defaultValue = "0") int page` +
         *   `@RequestParam(defaultValue = "10") int size` - standardowy
         *   wzorzec wejscia stronicowania.
         * - `PageResponse<T>` (WLASNE DTO) = `content` (elementy tej
         *   strony) + `page`/`size`/`totalElements`/`totalPages` -
         *   klient WIE, GDZIE jest I ILE jest stron.
         * - Przypadki brzegowe (strona POZA zakresem, `size` <= 0)
         *   MUSZA byc OBSLUZONE JAWNIE - inaczej `IndexOutOfBoundsException`
         *   lub PUSTA/BLEDNA odpowiedz.
         * - Kolejny rozdzial (`_23_spring_data_jpa`) pokaze, jak
         *   `Pageable`/`Page<T>` ROBIA to SAMO automatycznie NA
         *   POZIOMIE zapytania DO bazy (LIMIT/OFFSET) - TU uczymy sie
         *   MECHANIZMU RECZNIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    record Product(String id, String name) {
    }

    record PageResponse<T>(List<T> content, int page, int size, long totalElements, int totalPages) {
        static <T> PageResponse<T> of(List<T> allItems, int page, int size) {
            int totalElements = allItems.size();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            int fromIndex = Math.min(page * size, totalElements);
            int toIndex = Math.min(fromIndex + size, totalElements);
            List<T> content = allItems.subList(fromIndex, toIndex);

            return new PageResponse<>(content, page, size, totalElements, totalPages);
        }
    }

    static List<Product> buildCatalog() {
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            products.add(new Product("P" + i, "Produkt " + i));
        }
        return products;
    }

    @RestController
    static class PaginationController {
        private final List<Product> catalog = buildCatalog();

        @GetMapping("/products")
        PageResponse<Product> listProducts(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
            if (size <= 0) {
                size = 10;
            }
            if (page < 0) {
                page = 0;
            }
            return PageResponse.of(catalog, page, size);
        }
    }

    @SpringBootApplication
    static class PaginationApp {
    }

    private static void demonstrateManualPaginationWithMetadata() throws Exception {
        System.out.println("\n=== RECZNE STRONICOWANIE - 25 PRODUKTOW, page/size PRZEZ @RequestParam ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(PaginationApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            System.out.println("GET /products?page=0&size=10 -> " + httpGet(port, "/products?page=0&size=10").body());
            System.out.println("GET /products?page=1&size=10 -> " + httpGet(port, "/products?page=1&size=10").body());
            System.out.println("GET /products?page=2&size=10 -> " + httpGet(port, "/products?page=2&size=10").body());
            System.out.println("-> Strona 2 (ostatnia) ma TYLKO 5 elementow (25 razem, 10 na strone) - 'content' MA MNIEJ elementow niz 'size'.");
        } finally {
            context.close();
        }
    }

    @RestController
    static class EdgeCaseController {
        private final List<Product> catalog = buildCatalog();

        @GetMapping("/catalog")
        PageResponse<Product> listCatalog(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
            if (size <= 0) {
                size = 10;
            }
            if (page < 0) {
                page = 0;
            }
            return PageResponse.of(catalog, page, size);
        }
    }

    @SpringBootApplication
    static class EdgeCaseApp {
    }

    private static void demonstratePaginationEdgeCases() throws Exception {
        System.out.println("\n=== PRZYPADKI BRZEGOWE - strona POZA zakresem, size <= 0 ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(EdgeCaseApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            System.out.println("GET /catalog?page=999&size=10 (strona POZA zakresem) -> " + httpGet(port, "/catalog?page=999&size=10").body());
            System.out.println("-> 'content' jest PUSTA lista, NIE wyjatek - dzieki 'Math.min(...)' PRZY liczeniu indeksow.");

            System.out.println("GET /catalog?page=0&size=-5 (nieprawidlowy 'size') -> " + httpGet(port, "/catalog?page=0&size=-5").body());
            System.out.println("-> Nieprawidlowy 'size' zostal ZASTAPIONY WARTOSCIA DOMYSLNA (10), NIE spowodowal bledu.");
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
