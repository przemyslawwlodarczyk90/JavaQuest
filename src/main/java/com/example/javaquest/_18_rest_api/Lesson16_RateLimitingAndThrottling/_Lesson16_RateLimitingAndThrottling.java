package com.example.javaquest._18_rest_api.Lesson16_RateLimitingAndThrottling;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class _Lesson16_RateLimitingAndThrottling {

    /**
     * Prosty "token bucket" (wiadro tokenow) per klient - kazdy klient
     * ma limit N tokenow, kazdy request zuzywa 1, tokeny odnawiaja sie
     * z czasem az do maksimum. To 1 z kilku popularnych algorytmow
     * rate limitingu (obok fixed window / sliding window).
     */
    static class TokenBucket {
        private final int capacity;
        private final double refillPerMillis;
        private double tokens;
        private long lastRefillTime;

        TokenBucket(int capacity, double tokensPerSecond) {
            this.capacity = capacity;
            this.refillPerMillis = tokensPerSecond / 1000.0;
            this.tokens = capacity;
            this.lastRefillTime = System.currentTimeMillis();
        }

        synchronized boolean tryConsume() {
            refill();
            if (tokens >= 1) {
                tokens -= 1;
                return true;
            }
            return false;
        }

        synchronized int remaining() {
            refill();
            return (int) tokens;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long elapsed = now - lastRefillTime;
            tokens = Math.min(capacity, tokens + elapsed * refillPerMillis);
            lastRefillTime = now;
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: RATE LIMITING I THROTTLING ===");

        /*
         * ============================================================
         * 📦 PO CO OGRANICZAC LICZBE REQUESTOW?
         * ============================================================
         * - OCHRONA ZASOBOW serwera - bez limitu 1 klient (celowo lub
         *   przez blad w kodzie) moze zalac serwer requestami, degradujac
         *   dzialanie dla WSZYSTKICH innych klientow.
         * - SPRAWIEDLIWY dostep - kazdy klient dostaje UCZCIWA czesc
         *   pojemnosci serwera.
         * - OCHRONA PRZED NADUZYCIAMI - np. proby zgadywania hasel
         *   (bruteforce - `_19_security_basics`), scraping.
         */
        System.out.println("Rate limiting = ochrona serwera + sprawiedliwy dostep + obrona przed naduzyciami.");

        Map<String, TokenBucket> bucketsPerClient = new ConcurrentHashMap<>();

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/api/data", exchange -> handleRequest(exchange, bucketsPerClient));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateRateLimitHeaders(client, port);
            demonstrateExceedingLimit(client, port);
            demonstrateTokenRefillOverTime(client, port);
            demonstratePerClientIndependence(client, port);
            demonstrateAlgorithmOverview();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - 429 Too Many Requests - poprawny kod statusu, gdy limit zostal
         *   przekroczony (Lesson05: kategoria 4xx - to KLIENT przekroczyl
         *   swoj limit).
         * - Naglowki informacyjne (branzowa konwencja, nie oficjalny RFC):
         *   X-RateLimit-Limit (calkowity limit), X-RateLimit-Remaining
         *   (ile zostalo), X-RateLimit-Reset (kiedy limit sie odnowi).
         * - "Retry-After" (oficjalny naglowek HTTP) mowi klientowi, ILE
         *   SEKUND poczekac przed ponowna proba.
         * - 3 popularne algorytmy: fixed window (prosty, ale "wybuchy"
         *   na granicy okna), sliding window (dokladniejszy), token
         *   bucket (pozwala na krotkotrwale "wybuchy" ruchu do limitu
         *   pojemnosci wiadra, potem stala szybkosc odnawiania).
         * - Limit ZAWSZE liczony PER klient (po API key/IP/user ID) -
         *   NIGDY globalnie dla wszystkich naraz.
         * - Kolejna lekcja (Lesson17): Postman - narzedzie do RECZNEGO
         *   testowania API zbudowanego w tym rozdziale.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    private static void handleRequest(com.sun.net.httpserver.HttpExchange exchange, Map<String, TokenBucket> buckets) throws java.io.IOException {
        String clientId = exchange.getRequestHeaders().getFirst("X-Client-Id");
        if (clientId == null) {
            clientId = "anonymous";
        }

        TokenBucket bucket = buckets.computeIfAbsent(clientId, id -> new TokenBucket(5, 2.0)); // 5 tokenow, odnawia 2/sekunde

        exchange.getResponseHeaders().add("X-RateLimit-Limit", "5");
        exchange.getResponseHeaders().add("X-RateLimit-Remaining", String.valueOf(bucket.remaining()));

        if (!bucket.tryConsume()) {
            exchange.getResponseHeaders().add("Retry-After", "1");
            byte[] body = "{\"error\":\"Zbyt wiele requestow, sprobuj ponownie za chwile.\"}".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(429, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
            return;
        }

        byte[] body = "{\"data\":\"wynik operacji\"}".getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, body.length);
        exchange.getResponseBody().write(body);
        exchange.close();
    }

    private static void demonstrateRateLimitHeaders(HttpClient client, int port) throws Exception {
        System.out.println("\n=== NAGLOWKI INFORMUJACE O LIMICIE ===");
        var response = get(client, port, "client-A");
        System.out.println("GET /api/data -> " + response.statusCode()
                + ", X-RateLimit-Limit=" + response.headers().firstValue("x-ratelimit-limit").orElse("?")
                + ", X-RateLimit-Remaining=" + response.headers().firstValue("x-ratelimit-remaining").orElse("?"));
    }

    private static void demonstrateExceedingLimit(HttpClient client, int port) throws Exception {
        System.out.println("\n=== PRZEKROCZENIE LIMITU -> 429 TOO MANY REQUESTS ===");
        String clientId = "client-B";
        for (int i = 1; i <= 6; i++) {
            var response = get(client, port, clientId);
            System.out.println("Request " + i + " -> status=" + response.statusCode()
                    + ", remaining=" + response.headers().firstValue("x-ratelimit-remaining").orElse("?")
                    + (response.statusCode() == 429 ? ", Retry-After=" + response.headers().firstValue("retry-after").orElse("?") : ""));
        }
    }

    private static void demonstrateTokenRefillOverTime(HttpClient client, int port) throws Exception {
        System.out.println("\n=== TOKENY ODNAWIAJA SIE Z CZASEM ===");
        String clientId = "client-C";
        for (int i = 1; i <= 5; i++) {
            get(client, port, clientId); // zuzyj wszystkie 5 tokenow
        }
        var exhausted = get(client, port, clientId);
        System.out.println("Po zuzyciu wszystkich tokenow -> status=" + exhausted.statusCode() + " (429, brak tokenow)");

        Thread.sleep(1100); // poczekaj ~1.1s, przy 2 tokenach/s powinno odnowic sie ~2 tokeny
        var afterWait = get(client, port, clientId);
        System.out.println("Po ~1.1s oczekiwania -> status=" + afterWait.statusCode() + " (200, tokeny sie odnowily)");
    }

    private static void demonstratePerClientIndependence(HttpClient client, int port) throws Exception {
        System.out.println("\n=== LIMIT JEST NIEZALEZNY PER KLIENT ===");
        String exhaustedClient = "client-E"; // SWIEZY klient - wyczerpujemy go TERAZ, deterministycznie
        String freshClient = "client-D"; // inny, nietkniety klient, wlasny pelny bucket

        for (int i = 0; i < 5; i++) {
            get(client, port, exhaustedClient); // zuzyj wszystkie 5 tokenow client-E TUZ PRZED sprawdzeniem
        }
        var exhaustedResponse = get(client, port, exhaustedClient);
        var freshResponse = get(client, port, freshClient);

        System.out.println("client-E (dopiero co wyczerpany limit) -> status=" + exhaustedResponse.statusCode());
        System.out.println("client-D (nowy, wlasny limit)          -> status=" + freshResponse.statusCode()
                + " (limit client-E NIE WPLYWA na client-D)");
    }

    private static void demonstrateAlgorithmOverview() {
        System.out.println("\n=== 3 POPULARNE ALGORYTMY RATE LIMITINGU ===");
        System.out.println("Fixed Window  - licz requesty w stalych oknach czasowych (np. 'ta minuta') - prosty, ale");
        System.out.println("                mozliwy 'wybuch' 2x limitu na granicy 2 okien (koniec 1 + poczatek 2).");
        System.out.println("Sliding Window- liczy requesty w RUCHOMYM oknie (np. 'ostatnie 60 sekund OD TERAZ') -");
        System.out.println("                dokladniejszy, ale kosztowniejszy obliczeniowo.");
        System.out.println("Token Bucket  - (uzyty w tej lekcji) pozwala na KROTKOTRWALY 'wybuch' ruchu do pojemnosci");
        System.out.println("                wiadra, potem ograniczona stala szybkoscia odnawiania - elastyczny kompromis.");
    }

    private static HttpResponse<String> get(HttpClient client, int port, String clientId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/data"))
                .header("X-Client-Id", clientId)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
