package com.example.javaquest._19_security_basics.Lesson09_Cors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

public class _Lesson09_Cors {

    private static final Set<String> ALLOWED_ORIGINS = Set.of("https://sklep.przyklad.pl", "http://localhost:3000");

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 9: CORS (CROSS-ORIGIN RESOURCE SHARING) ===");

        /*
         * ============================================================
         * 📦 SAME-ORIGIN POLICY - DOMYSLNE OGRANICZENIE PRZEGLADAREK
         * ============================================================
         * Przegladarki maja WBUDOWANA zasade "Same-Origin Policy" -
         * kod JavaScript wczytany z `https://zla-strona.pl` domyslnie NIE
         * MOZE odczytac odpowiedzi z zadania `fetch()` do
         * `https://api.twoj-bank.pl` (INNE originy: rozny protokol/host/
         * port = INNY origin). To WAZNA ochrona - bez niej ZLOSLIWA
         * strona mogla by (z Twojej przegladarki, z Twoimi ciastkami
         * sesyjnymi z Lesson04!) wysylac zadania do dowolnego API w
         * Twoim imieniu i ODCZYTYWAC odpowiedzi.
         * WAZNE zastrzezenie tej lekcji: CORS jest mechanizmem
         * EGZEKWOWANYM PRZEZ PRZEGLADARKE - `HttpClient` (uzywany w tym
         * demo) NIE jest przegladarka i NIE blokuje NICZEGO sam z siebie.
         * Ta lekcja pokazuje, JAKIE NAGLOWKI serwer POWINIEN wyslac, zeby
         * przegladarka (klient KONCOWY) podjela poprawna decyzje.
         */
        System.out.println("Same-Origin Policy = domyslna ochrona przegladarki. CORS = naglowki, ktorymi serwer 'otwiera' wyjatki od niej.");
        System.out.println("UWAGA: HttpClient w tym demo NIE jest przegladarka - egzekwowanie CORS dzieje sie w PRZEGLADARCE, nie tutaj.");

        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/api/products", _Lesson09_Cors::handleProductsRequest);
        server.setExecutor(null);
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("\nLokalny serwer API wystartowal na porcie " + port);

        try {
            demonstrateAllowedOriginGetsCorsHeaders(port);
            demonstrateDisallowedOriginGetsNoCorsHeaders(port);
            demonstratePreflightRequest(port);
            demonstratePreflightRejectedForDisallowedMethod(port);
        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 🔹 PROSTE ZADANIA (SIMPLE REQUESTS) vs PREFLIGHT
         * ============================================================
         * - "Proste" zadanie (GET/POST/HEAD, standardowe naglowki, typ
         *   `application/x-www-form-urlencoded`/`text/plain`) -
         *   przegladarka wysyla je OD RAZU, ale BLOKUJE odczyt
         *   odpowiedzi w JS, jesli brak `Access-Control-Allow-Origin`
         *   dopasowanego do originu strony.
         * - "Zlozone" zadanie (np. `Content-Type: application/json`,
         *   metody PUT/DELETE, wlasne naglowki jak `Authorization`) -
         *   przegladarka NAJPIERW wysyla PREFLIGHT - zadanie OPTIONS
         *   pytajace "czy mozesz to zaakceptowac?" - serwer MUSI
         *   odpowiedziec poprawnymi naglowkami `Access-Control-Allow-*`,
         *   ZANIM przegladarka wyśle WLASCIWE zadanie.
         *
         * ============================================================
         * 🔹 KLUCZOWE NAGLOWKI CORS
         * ============================================================
         * - `Access-Control-Allow-Origin` - KTORY origin moze odczytac
         *   odpowiedz (konkretny origin LUB "*" dla "kazdy", ale NIGDY
         *   "*" razem z ciastkami/danymi uwierzytelniajacymi!).
         * - `Access-Control-Allow-Methods` - dozwolone metody HTTP.
         * - `Access-Control-Allow-Headers` - dozwolone wlasne naglowki
         *   (np. "Authorization").
         * - `Access-Control-Allow-Credentials: true` - pozwala wysylac
         *   ciastka/dane logowania w zadaniach cross-origin (WYMAGA
         *   konkretnego originu, NIE moze byc polaczone z "*").
         */
        System.out.println("\nPreflight (OPTIONS) sprawdza z GORY, czy zlozone zadanie bedzie zaakceptowane - dopiero potem idzie WLASCIWE zadanie.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Same-Origin Policy chroni przez PRZEGLADARKE - inne originy
         *   nie moga domyslnie czytac odpowiedzi z Twojego API.
         * - CORS to naglowki, ktorymi SERWER jawnie "otwiera" wybrane
         *   originy - BIALA LISTA, nie ich wylaczanie w ogole.
         * - Zadania "zlozone" wymagaja PREFLIGHT (OPTIONS) - serwer musi
         *   na niego poprawnie odpowiedziec.
         * - `Access-Control-Allow-Origin: *` NIGDY razem z
         *   `Access-Control-Allow-Credentials: true` - to bylaby luka
         *   (kazda strona moglaby odczytac dane uwierzytelnione).
         * - CORS NIE jest mechanizmem bezpieczenstwa PO STRONIE SERWERA -
         *   to ochrona PRZEGLADARKI dla uzytkownika. Serwer NADAL musi
         *   sam autoryzowac kazde zadanie (Lesson01/Lesson07) - CORS
         *   tylko decyduje, czy JS INNEJ strony smie ODCZYTAC odpowiedz.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void handleProductsRequest(HttpExchange exchange) throws java.io.IOException {
        String origin = exchange.getRequestHeaders().getFirst("Origin");
        String method = exchange.getRequestMethod();

        if ("OPTIONS".equals(method)) {
            handlePreflight(exchange, origin);
            return;
        }

        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", origin);
        }
        byte[] response = "[\"Laptop\", \"Mysz\", \"Klawiatura\"]".getBytes();
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    private static void handlePreflight(HttpExchange exchange, String origin) throws java.io.IOException {
        String requestedMethod = exchange.getRequestHeaders().getFirst("Access-Control-Request-Method");
        boolean originAllowed = origin != null && ALLOWED_ORIGINS.contains(origin);
        boolean methodAllowed = "GET".equals(requestedMethod) || "POST".equals(requestedMethod);

        if (originAllowed && methodAllowed) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", origin);
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.sendResponseHeaders(204, -1);
        } else {
            exchange.sendResponseHeaders(403, -1);
        }
        exchange.close();
    }

    private static void demonstrateAllowedOriginGetsCorsHeaders(int port) throws Exception {
        System.out.println("\n=== ZADANIE Z DOZWOLONEGO ORIGINU DOSTAJE NAGLOWEK CORS ===");

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/products"))
                        .header("Origin", "https://sklep.przyklad.pl")
                        .GET().build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("GET z Origin=https://sklep.przyklad.pl -> status " + response.statusCode());
        System.out.println("Access-Control-Allow-Origin: " + response.headers().firstValue("Access-Control-Allow-Origin").orElse("(BRAK)"));
        System.out.println("-> przegladarka z tym originem MOZE odczytac odpowiedz JavaScriptem.");
    }

    private static void demonstrateDisallowedOriginGetsNoCorsHeaders(int port) throws Exception {
        System.out.println("\n=== ZADANIE Z NIEDOZWOLONEGO ORIGINU NIE DOSTAJE NAGLOWKA CORS ===");

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/products"))
                        .header("Origin", "https://zlosliwa-strona.pl")
                        .GET().build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("GET z Origin=https://zlosliwa-strona.pl -> status " + response.statusCode()
                + " (dane SA zwrocone przez serwer! HttpClient je widzi)");
        System.out.println("Access-Control-Allow-Origin: " + response.headers().firstValue("Access-Control-Allow-Origin").orElse("(BRAK)"));
        System.out.println("-> W PRAWDZIWEJ przegladarce: JS NIE MOZE odczytac tej odpowiedzi bez pasujacego naglowka,");
        System.out.println("   mimo ze zadanie fizycznie doszlo do serwera i serwer odpowiedzial (to NIE jest blokada sieciowa!).");
    }

    private static void demonstratePreflightRequest(int port) throws Exception {
        System.out.println("\n=== PREFLIGHT (OPTIONS) DLA ZLOZONEGO ZADANIA ===");

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/products"))
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "POST")
                        .method("OPTIONS", HttpRequest.BodyPublishers.noBody())
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("OPTIONS (preflight) z Origin=http://localhost:3000, metoda POST -> status " + response.statusCode());
        System.out.println("Access-Control-Allow-Methods: " + response.headers().firstValue("Access-Control-Allow-Methods").orElse("(BRAK)"));
        System.out.println("-> dopiero PO takiej odpowiedzi przegladarka wyslalaby WLASCIWE zadanie POST.");
    }

    private static void demonstratePreflightRejectedForDisallowedMethod(int port) throws Exception {
        System.out.println("\n=== PREFLIGHT ODRZUCONY DLA NIEDOZWOLONEJ METODY ===");

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/products"))
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "DELETE")
                        .method("OPTIONS", HttpRequest.BodyPublishers.noBody())
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("OPTIONS (preflight) z metoda DELETE (niedozwolona) -> status " + response.statusCode()
                + " -> przegladarka NIGDY nie wyslalaby zadania DELETE.");
    }
}
