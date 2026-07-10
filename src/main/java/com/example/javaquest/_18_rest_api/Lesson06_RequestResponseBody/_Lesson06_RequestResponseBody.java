package com.example.javaquest._18_rest_api.Lesson06_RequestResponseBody;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson06_RequestResponseBody {

    record Product(int id, String name, double price) {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 6: CIALO REQUESTU I ODPOWIEDZI ===");

        /*
         * ============================================================
         * 📦 CIALO = REPREZENTACJA STANU ZASOBU
         * ============================================================
         * Cialo (body) to WLASCIWA tresc requestu/odpowiedzi - dane, a
         * nie metadane (te sa w naglowkach, Lesson01). W REST body niesie
         * REPREZENTACJE zasobu (Lesson02: "uniform interface" - manipulacja
         * przez reprezentacje) - najczesciej dzis w formacie JSON.
         */

        /*
         * ============================================================
         * 🔹 FORMATY CIALA - KROTKI PRZEGLAD
         * ============================================================
         * - application/json      - DOMINUJACY format w nowoczesnych API -
         *   lekki, czytelny, natywnie mapuje sie na obiekty JS/Java.
         * - application/xml       - starszy, bardziej "gadatliwy" format -
         *   wciaz spotykany w starszych/enterprise'owych systemach
         *   (`_06_networking/Lesson13_XmlParsing`).
         * - application/x-www-form-urlencoded - dane z formularzy HTML
         *   (klucz=wartosc&klucz2=wartosc2) - `_07_servlets/Lesson09`.
         * - multipart/form-data   - upload plikow + pola formularza naraz -
         *   `_07_servlets/Lesson18_FileUpload`.
         * Ten rozdzial (i wiekszosc nowoczesnych REST API) skupia sie na
         * JSON jako domyslnym formacie.
         */
        System.out.println("Format ciala = application/json w wiekszosci nowoczesnych REST API (Lesson01: Content-Type mowi jaki to format).");

        Map<Integer, Product> products = new LinkedHashMap<>();
        products.put(1, new Product(1, "Klawiatura", 199.99));
        products.put(2, new Product(2, "Monitor", 899.00));

        Gson gson = new Gson();
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/products", exchange -> handleProducts(exchange, products, gson));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateRequestBodyForCreate(client, gson, port);
            demonstrateResponseBodyShapes(client, gson, port);
            demonstrateEmptyBodyScenarios(client, port);
            demonstrateBareArrayVsEnvelope();
            demonstrateCharsetInContentType(client, port);
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Cialo = reprezentacja zasobu, format okreslony przez naglowek
         *   Content-Type (JSON dominuje wspolczesnie).
         * - GET/DELETE/HEAD zwykle BEZ ciala requestu; 204/HEAD/niektore
         *   3xx zawsze BEZ ciala odpowiedzi.
         * - Odpowiedz moze byc "goa" tablica/obiektem LUB "kopertowana"
         *   ({"data": ..., "meta": ...}) - obie sa poprawne, wybierz JEDNA
         *   konwencje i trzymaj sie jej w CALYM API.
         * - Content-Type: application/json; charset=utf-8 - jawnie deklaruje
         *   zarowno format, jak i kodowanie znakow.
         * - Kolejna lekcja (Lesson07): jak KLIENT negocjuje format ciala
         *   przez naglowek Accept (Content Negotiation).
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void handleProducts(com.sun.net.httpserver.HttpExchange exchange, Map<Integer, Product> products, Gson gson) throws java.io.IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if (method.equals("GET") && path.equals("/products")) {
            respondJson(exchange, 200, gson.toJson(products.values()));
        } else if (method.equals("POST") && path.equals("/products")) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<?, ?> parsed = gson.fromJson(body, Map.class);
            int newId = products.keySet().stream().mapToInt(i -> i).max().orElse(0) + 1;
            Product created = new Product(newId, (String) parsed.get("name"), (Double) parsed.get("price"));
            products.put(newId, created);
            respondJson(exchange, 201, gson.toJson(created));
        } else if (method.equals("DELETE")) {
            int id = Integer.parseInt(path.substring("/products/".length()));
            products.remove(id);
            exchange.sendResponseHeaders(204, -1); // brak ciala - nic do przekazania
            exchange.close();
        } else {
            exchange.sendResponseHeaders(404, -1);
            exchange.close();
        }
    }

    private static void respondJson(com.sun.net.httpserver.HttpExchange exchange, int status, String json) throws java.io.IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void demonstrateRequestBodyForCreate(HttpClient client, Gson gson, int port) throws Exception {
        System.out.println("\n=== CIALO REQUESTU: TWORZENIE NOWEGO ZASOBU (POST) ===");

        String requestBody = gson.toJson(Map.of("name", "Mysz bezprzewodowa", "price", 89.99));
        System.out.println("Wysylane cialo (application/json): " + requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/products"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Odebrane cialo odpowiedzi: " + response.statusCode() + " " + response.body());
    }

    private static void demonstrateResponseBodyShapes(HttpClient client, Gson gson, int port) throws Exception {
        System.out.println("\n=== CIALO ODPOWIEDZI: KOLEKCJA vs POJEDYNCZY OBIEKT ===");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/products"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<?> parsedAsList = gson.fromJson(response.body(), List.class);
        System.out.println("GET /products zwraca TABLICE (kolekcje): " + response.body());
        System.out.println("-> sparsowane jako List, dlugosc=" + parsedAsList.size());
    }

    private static void demonstrateEmptyBodyScenarios(HttpClient client, int port) throws Exception {
        System.out.println("\n=== KIEDY CIALO POWINNO BYC PUSTE ===");

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/products/2"))
                .DELETE().build();
        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("DELETE /products/2 -> status=" + deleteResponse.statusCode()
                + ", dlugosc ciala=" + deleteResponse.body().length() + " (204 = ZAWSZE puste cialo)");

        System.out.println("Inne przypadki bez ciala: GET/DELETE requesty (zazwyczaj), kazda odpowiedz HEAD, wiekszosc 3xx (dane w naglowku Location zamiast w ciele).");
    }

    private static void demonstrateBareArrayVsEnvelope() {
        System.out.println("\n=== 'GOLA' TABLICA vs 'KOPERTA' (envelope) - 2 ROWNOPRAWNE STYLE ===");

        String bareArray = "[{\"id\":1,\"name\":\"Klawiatura\"},{\"id\":2,\"name\":\"Monitor\"}]";
        String enveloped = """
                {
                  "data": [
                    {"id":1,"name":"Klawiatura"},
                    {"id":2,"name":"Monitor"}
                  ],
                  "meta": {"total": 2, "page": 1}
                }""";

        System.out.println("Styl 'gola tablica' (prostszy, ale trudniej dodac metadane pozniej):");
        System.out.println("  " + bareArray);
        System.out.println("Styl 'koperta' (elastyczniejszy - miejsce na meta/paginacje, Lesson09/10):");
        System.out.println(enveloped.indent(2));
        System.out.println("-> WYBIERZ 1 styl i trzymaj sie go w CALYM API - niespojnosc miedzy endpointami jest gorsza niz wybor 'gorszego' stylu.");
    }

    private static void demonstrateCharsetInContentType(HttpClient client, int port) throws Exception {
        System.out.println("\n=== Content-Type MUSI DEKLAROWAC KODOWANIE ZNAKOW ===");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/products"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Content-Type odpowiedzi: " + response.headers().firstValue("content-type").orElse("(brak)"));
        System.out.println("-> 'charset=utf-8' gwarantuje, ze polskie znaki (np. w nazwach produktow) beda odczytane poprawnie po obu stronach.");
    }
}
