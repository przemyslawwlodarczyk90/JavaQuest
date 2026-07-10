package com.example.javaquest._18_rest_api.Lesson11_HttpCachingAndConditionalRequests;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson11_HttpCachingAndConditionalRequests {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: CACHE-CONTROL, ETAG I ZAPYTANIA WARUNKOWE ===");

        /*
         * ============================================================
         * 📦 PO CO CACHE'OWAC ODPOWIEDZI HTTP?
         * ============================================================
         * Lesson02 wprowadzila "cacheable" jako 1 z 6 ograniczen REST.
         * Ta lekcja pokazuje MECHANIKE tego ograniczenia na poziomie
         * PROTOKOLU HTTP - jak SERWER mowi klientowi/posrednim proxy,
         * PRZEZ JAK DLUGO odpowiedz jest aktualna, i jak KLIENT moze
         * zapytac "czy Twoja wersja zasobu jest wciaz aktualna?" bez
         * pobierania calego ciala ponownie.
         */

        Map<Integer, String> articles = new HashMap<>();
        articles.put(1, "{\"id\":1,\"title\":\"Wersja poczatkowa\"}");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/articles", exchange -> handleArticle(exchange, articles));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateCacheControlDirectives();
            demonstrateEtagAndConditionalGet(client, port);
            demonstrateEtagChangesAfterUpdate(client, port, articles);
            demonstrateIfMatchOptimisticConcurrency(client, port, articles);
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Cache-Control: max-age=N (cache'uj przez N sekund), no-cache
         *   (zawsze zweryfikuj u serwera przed uzyciem), no-store (NIGDY
         *   nie cache'uj - np. dane wrazliwe), private (tylko przegladarka,
         *   nie proxy) vs public.
         * - ETag = "odcisk palca" zawartosci zasobu (np. hash) - zmienia
         *   sie TYLKO gdy zasob faktycznie sie zmienil.
         * - GET z "If-None-Match: <etag>" -> 304 Not Modified (BEZ ciala!),
         *   jesli ETag wciaz aktualny - oszczedza transfer danych.
         * - PUT/PATCH/DELETE z "If-Match: <etag>" -> 412 Precondition
         *   Failed, jesli ktos INNY zmodyfikowal zasob miedzyczasie -
         *   to jest OPTYMISTYCZNA KONTROLA WSPOLBIEZNOSCI (por.
         *   `_12_hibernate/Lesson25_OptimisticLocking`) na poziomie HTTP.
         * - Alternatywa dla ETag: Last-Modified/If-Modified-Since (czas
         *   zamiast hasha) - mniej precyzyjne (rozdzielczosc sekundowa),
         *   ale tansze do wygenerowania.
         * - Kolejna lekcja (Lesson12): projektowanie odpowiedzi bledow -
         *   ETag/cache dotyczy TYLKO odpowiedzi sukcesu.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void handleArticle(com.sun.net.httpserver.HttpExchange exchange, Map<Integer, String> articles) throws java.io.IOException {
        String method = exchange.getRequestMethod();
        int id = 1; // dla uproszczenia demo operujemy na 1 stalym zasobie

        if (method.equals("GET")) {
            String currentBody = articles.get(id);
            String currentEtag = computeEtag(currentBody);

            String ifNoneMatch = exchange.getRequestHeaders().getFirst("If-None-Match");
            if (currentEtag.equals(ifNoneMatch)) {
                exchange.getResponseHeaders().add("ETag", currentEtag);
                exchange.sendResponseHeaders(304, -1); // Not Modified - BEZ ciala
                exchange.close();
                return;
            }

            byte[] bytes = currentBody.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("ETag", currentEtag);
            exchange.getResponseHeaders().add("Cache-Control", "max-age=60");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();

        } else if (method.equals("PUT")) {
            String currentBody = articles.get(id);
            String currentEtag = computeEtag(currentBody);

            String ifMatch = exchange.getRequestHeaders().getFirst("If-Match");
            if (ifMatch != null && !ifMatch.equals(currentEtag)) {
                exchange.sendResponseHeaders(412, -1); // Precondition Failed - ktos inny juz zmienil zasob
                exchange.close();
                return;
            }

            String newBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            articles.put(id, newBody);
            String newEtag = computeEtag(newBody);

            byte[] bytes = newBody.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("ETag", newEtag);
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        }
    }

    private static String computeEtag(String content) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 powinien byc zawsze dostepny w JDK", e);
        }
        byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder("\"");
        for (int i = 0; i < 8; i++) { // skrocony hash - wystarczajaco unikalny na potrzeby demo
            hex.append(String.format("%02x", hash[i]));
        }
        return hex.append("\"").toString();
    }

    private static void demonstrateCacheControlDirectives() {
        System.out.println("\n=== Cache-Control: NAJWAZNIEJSZE DYREKTYWY ===");
        System.out.println("max-age=3600   - cache'uj przez 3600 sekund BEZ pytania serwera ponownie");
        System.out.println("no-cache       - MOZESZ cache'owac, ale ZAWSZE zweryfikuj u serwera przed uzyciem (ETag!)");
        System.out.println("no-store       - NIGDY nie zapisuj w cache (np. dane bankowe, `_19_security_basics`)");
        System.out.println("private        - tylko przegladarka klienta, NIE posrednie proxy/CDN");
        System.out.println("public         - MOZE byc cache'owane rowniez przez posrednikow (proxy/CDN)");
    }

    private static void demonstrateEtagAndConditionalGet(HttpClient client, int port) throws Exception {
        System.out.println("\n=== ETAG I ZAPYTANIE WARUNKOWE (If-None-Match) ===");

        HttpRequest first = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/articles")).GET().build();
        HttpResponse<String> firstResponse = client.send(first, HttpResponse.BodyHandlers.ofString());
        String etag = firstResponse.headers().firstValue("etag").orElseThrow();
        System.out.println("1. GET -> status=" + firstResponse.statusCode() + ", ETag=" + etag + ", cialo=" + firstResponse.body());

        HttpRequest second = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/articles"))
                .header("If-None-Match", etag)
                .GET().build();
        HttpResponse<String> secondResponse = client.send(second, HttpResponse.BodyHandlers.ofString());
        System.out.println("2. GET z If-None-Match=" + etag + " -> status=" + secondResponse.statusCode()
                + ", dlugosc ciala=" + secondResponse.body().length() + " (304 = zasob NIE zmienil sie, cialo NIE przeslane ponownie)");
    }

    private static void demonstrateEtagChangesAfterUpdate(HttpClient client, int port, Map<Integer, String> articles) throws Exception {
        System.out.println("\n=== ETAG ZMIENIA SIE PO MODYFIKACJI ZASOBU ===");

        HttpRequest before = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/articles")).GET().build();
        String etagBefore = client.send(before, HttpResponse.BodyHandlers.ofString()).headers().firstValue("etag").orElseThrow();

        HttpRequest put = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/articles"))
                .PUT(HttpRequest.BodyPublishers.ofString("{\"id\":1,\"title\":\"Wersja zaktualizowana\"}"))
                .build();
        client.send(put, HttpResponse.BodyHandlers.ofString());

        HttpRequest after = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/articles")).GET().build();
        String etagAfter = client.send(after, HttpResponse.BodyHandlers.ofString()).headers().firstValue("etag").orElseThrow();

        System.out.println("ETag PRZED aktualizacja: " + etagBefore);
        System.out.println("ETag PO aktualizacji:    " + etagAfter + "  (rozny = tresc faktycznie sie zmienila)");
    }

    private static void demonstrateIfMatchOptimisticConcurrency(HttpClient client, int port, Map<Integer, String> articles) throws Exception {
        System.out.println("\n=== If-Match: OPTYMISTYCZNA KONTROLA WSPOLBIEZNOSCI ===");

        HttpRequest getCurrent = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/articles")).GET().build();
        String staleEtag = client.send(getCurrent, HttpResponse.BodyHandlers.ofString()).headers().firstValue("etag").orElseThrow();

        // symulacja: KTOS INNY modyfikuje zasob miedzyczasie (bez naszej wiedzy)
        articles.put(1, "{\"id\":1,\"title\":\"Zmienione przez innego uzytkownika\"}");

        HttpRequest putWithStaleEtag = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/articles"))
                .header("If-Match", staleEtag) // NASZ (juz NIEAKTUALNY) ETag
                .PUT(HttpRequest.BodyPublishers.ofString("{\"id\":1,\"title\":\"Moja wersja\"}"))
                .build();
        HttpResponse<String> response = client.send(putWithStaleEtag, HttpResponse.BodyHandlers.ofString());

        System.out.println("PUT z NIEAKTUALNYM If-Match -> status=" + response.statusCode()
                + " (412 Precondition Failed - ktos INNY juz zmienil zasob, NASZ zapis zostal ODRZUCONY zamiast nadpisac cudza zmiane)");
    }
}
