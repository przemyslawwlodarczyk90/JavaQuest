package com.example.javaquest._18_rest_api.Lesson09_PathVariablesAndQueryParams;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson09_PathVariablesAndQueryParams {

    record Article(int id, String title, String category, boolean published) {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 9: PATH VARIABLES I QUERY PARAMS ===");

        /*
         * ============================================================
         * 📦 PODSTAWOWA ROZNICA: "KTORY ZASOB" vs "JAK GO PRZEFILTROWAC"
         * ============================================================
         * - PATH VARIABLE (segment sciezki, np. /articles/{id}) - identyfikuje
         *   KONKRETNY, POJEDYNCZY zasob - zawsze WYMAGANY, jest CZESCIA
         *   tozsamosci zasobu (Lesson03).
         * - QUERY PARAM (np. /articles?category=tech) - modyfikuje KSZTALT
         *   requestu do KOLEKCJI (filtrowanie/sortowanie/stronicowanie,
         *   Lesson10) - zazwyczaj OPCJONALNY, z sensowna wartoscia domyslna.
         *
         * Regula: jesli usuniecie parametru zmienia, JAKI zasob
         * identyfikujemy - to path variable. Jesli tylko zmienia, KTORE
         * elementy kolekcji dostajemy - to query param.
         */
        System.out.println("Path variable = KTORY zasob (wymagany, tozsamosc). Query param = JAK przefiltrowac (opcjonalny).");

        List<Article> articles = new ArrayList<>(List.of(
                new Article(1, "Wprowadzenie do REST", "tech", true),
                new Article(2, "Przepis na pierogi", "kulinaria", true),
                new Article(3, "Draft o Javie 25", "tech", false)
        ));

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/articles", exchange -> handleArticles(exchange, articles));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstratePathVariable(client, port);
            demonstrateMultiplePathVariables();
            demonstrateSingleQueryParam(client, port);
            demonstrateMultipleQueryParams(client, port);
            demonstrateQueryParamDefaults(client, port);
            demonstrateUrlEncodingInQueryParam(client, port);
            demonstrateMultiValueQueryParam();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Path variable identyfikuje KONKRETNY zasob (/articles/{id}) -
         *   zawsze wymagany, brak = 404.
         * - Query param filtruje/modyfikuje request do KOLEKCJI - zwykle
         *   opcjonalny z domyslna wartoscia, brak = "wez wszystko".
         * - Wiele path variables mozliwe w zagniezdzonych zasobach
         *   (/users/{userId}/orders/{orderId}, Lesson03).
         * - Wartosci query params MUSZA byc URL-encoded, jesli zawieraja
         *   znaki specjalne (spacje, &, =, znaki narodowe).
         * - Wielowartosciowe query params: powtorzony klucz
         *   (?tag=java&tag=spring) LUB lista rozdzielona przecinkami
         *   (?tags=java,spring) - obie konwencje istnieja w praktyce,
         *   WYBIERZ 1 i badz konsekwentny w calym API.
         * - Kolejna lekcja (Lesson10): konkretne wzorce query params dla
         *   stronicowania, sortowania i filtrowania.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void handleArticles(com.sun.net.httpserver.HttpExchange exchange, List<Article> articles) throws java.io.IOException {
        String path = exchange.getRequestURI().getPath();
        Map<String, List<String>> query = parseQueryParams(exchange.getRequestURI().getRawQuery());

        if (path.equals("/articles")) {
            List<Article> result = new ArrayList<>(articles);

            if (query.containsKey("category")) {
                String category = query.get("category").get(0);
                result.removeIf(a -> !a.category().equals(category));
            }
            boolean includeDrafts = query.containsKey("includeDrafts") && query.get("includeDrafts").get(0).equals("true");
            if (!includeDrafts) {
                result.removeIf(a -> !a.published());
            }

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < result.size(); i++) {
                Article a = result.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id\":").append(a.id()).append(",\"title\":\"").append(a.title()).append("\"}");
            }
            json.append("]");
            respond(exchange, 200, json.toString());
        } else {
            int id = Integer.parseInt(path.substring("/articles/".length()));
            Article found = articles.stream().filter(a -> a.id() == id).findFirst().orElse(null);
            if (found == null) {
                respond(exchange, 404, "{\"error\":\"nie znaleziono\"}");
            } else {
                respond(exchange, 200, "{\"id\":" + found.id() + ",\"title\":\"" + found.title() + "\"}");
            }
        }
    }

    /**
     * Reczny parser query stringa - dekoduje wartosci URL-encoded i
     * zbiera WIELE wystapien tego samego klucza do listy (obsluga
     * "?tag=java&tag=spring").
     */
    private static Map<String, List<String>> parseQueryParams(String rawQuery) {
        Map<String, List<String>> result = new LinkedHashMap<>();
        if (rawQuery == null || rawQuery.isBlank()) {
            return result;
        }
        for (String pair : rawQuery.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "";
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        return result;
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, int status, String json) throws java.io.IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void demonstratePathVariable(HttpClient client, int port) throws Exception {
        System.out.println("\n=== PATH VARIABLE: KTORY KONKRETNY ZASOB ===");
        var response = get(client, port, "/articles/2");
        System.out.println("GET /articles/2 -> " + response.statusCode() + " " + response.body() + "  (id=2 to CZESC tozsamosci zasobu)");
    }

    private static void demonstrateMultiplePathVariables() {
        System.out.println("\n=== WIELE PATH VARIABLES W ZAGNIEZDZONYM ZASOBIE ===");
        System.out.println("GET /articles/2/comments/7  <- 2 path variables: articleId=2, commentId=7 (por. Lesson03).");
    }

    private static void demonstrateSingleQueryParam(HttpClient client, int port) throws Exception {
        System.out.println("\n=== 1 QUERY PARAM: FILTROWANIE KOLEKCJI ===");
        var response = get(client, port, "/articles?category=tech");
        System.out.println("GET /articles?category=tech -> " + response.body());
    }

    private static void demonstrateMultipleQueryParams(HttpClient client, int port) throws Exception {
        System.out.println("\n=== WIELE QUERY PARAMS NARAZ ===");
        var response = get(client, port, "/articles?category=tech&includeDrafts=true");
        System.out.println("GET /articles?category=tech&includeDrafts=true -> " + response.body()
                + "  (2 niezalezne filtry zastosowane RAZEM)");
    }

    private static void demonstrateQueryParamDefaults(HttpClient client, int port) throws Exception {
        System.out.println("\n=== BRAK QUERY PARAM = WARTOSC DOMYSLNA ===");
        var withoutParams = get(client, port, "/articles");
        System.out.println("GET /articles (brak filtrow) -> " + withoutParams.body()
                + "  (domyslnie: tylko opublikowane, wszystkie kategorie)");
    }

    private static void demonstrateUrlEncodingInQueryParam(HttpClient client, int port) throws Exception {
        System.out.println("\n=== URL ENCODING WARTOSCI QUERY PARAM ===");
        String rawValue = "programowanie & sieci";
        String encoded = URLEncoder.encode(rawValue, StandardCharsets.UTF_8);
        System.out.println("Wartosc oryginalna: \"" + rawValue + "\"");
        System.out.println("Po URL-encode:      \"" + encoded + "\"  (spacja->+, & jest zarezerwowany jako separator par)");

        var response = get(client, port, "/articles?category=" + encoded);
        System.out.println("GET /articles?category=" + encoded + " -> " + response.statusCode()
                + " (serwer poprawnie zdekodowal wartosc PRZED porownaniem)");
    }

    private static void demonstrateMultiValueQueryParam() {
        System.out.println("\n=== WIELOWARTOSCIOWE QUERY PARAMS - 2 KONWENCJE ===");
        System.out.println("Konwencja A (powtorzony klucz):     ?tag=java&tag=spring&tag=rest");
        System.out.println("Konwencja B (lista rozdzielona przecinkami): ?tags=java,spring,rest");
        System.out.println("-> obie sa poprawne i szeroko stosowane - wybierz 1 i trzymaj sie jej w CALYM API.");
    }

    private static HttpResponse<String> get(HttpClient client, int port, String pathAndQuery) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + pathAndQuery))
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
