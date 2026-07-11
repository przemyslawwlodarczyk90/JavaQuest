package com.example.javaquest._19_security_basics.Lesson04_SessionsAndCookies;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class _Lesson04_SessionsAndCookies {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 4: SESJE I CIASTECZKA (COOKIES) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: HTTP JEST BEZSTANOWY (STATELESS)
         * ============================================================
         * Kazde zadanie HTTP jest NIEZALEZNE od poprzedniego - serwer
         * "nie pamieta", ze przed chwila ten sam klient sie zalogowal.
         * `_18_rest_api` uczylo o REST (ktory CELOWO jest bezstanowy),
         * ale klasyczne aplikacje webowe CZESTO potrzebuja "pamietac"
         * zalogowanego uzytkownika miedzy zadaniami - do tego sluza
         * SESJE i CIASTECZKA.
         */
        System.out.println("HTTP jest bezstanowy - kazde zadanie niezalezne. Sesje 'symuluja' pamiec miedzy zadaniami.");

        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        Map<String, String> sessions = new ConcurrentHashMap<>(); // sessionId -> username
        Map<String, String> users = Map.of("admin", "tajne123", "jan", "haslo456");

        server.createContext("/login", exchange -> handleLogin(exchange, sessions, users));
        server.createContext("/profile", exchange -> handleProfile(exchange, sessions));
        server.createContext("/logout", exchange -> handleLogout(exchange, sessions));
        server.setExecutor(null);
        server.start();

        int port = server.getAddress().getPort();
        System.out.println("Lokalny serwer wystartowal na porcie " + port);

        try {
            demonstrateCookieBasedSession(port);
            demonstrateSessionIsolationPerClient(port);
            demonstrateLogoutInvalidatesSession(port);
        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 🔹 CIASTECZKO (COOKIE) vs SESJA (SESSION)
         * ============================================================
         * - CIASTECZKO - maly kawalek danych, ktory PRZEGLADARKA
         *   przechowuje i ODSYLA automatycznie w kazdym zadaniu do TEGO
         *   SAMEGO serwera (nagłówek `Cookie: nazwa=wartosc`).
         * - SESJA - stan PO STRONIE SERWERA (zwykle w pamieci/Redis/
         *   bazie), powiazany z ciastekiem zawierajacym TYLKO identyfikator
         *   sesji (np. `JSESSIONID=abc123`) - SAME dane sesji (kim jest
         *   uzytkownik, co ma w koszyku) NIGDY nie trafiaja do przegladarki.
         * Ten wzorzec (cookie = TYLKO identyfikator, dane po stronie
         * serwera) jest bezpieczniejszy niz trzymanie danych w samym
         * ciastku - `_07_servlets/Lesson11_HttpSession` uczyl mechaniki
         * `HttpSession` w Servlet API; ta lekcja pokazuje TO SAMO
         * zbudowane RECZNIE, zeby zrozumiec co dzieje sie "pod spodem".
         */
        System.out.println("\nCookie = tylko identyfikator sesji wysylany do przegladarki. Dane sesji zyja PO STRONIE SERWERA.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HTTP jest bezstanowy - sesje "dodaja" pamiec miedzy zadaniami.
         * - Cookie z Set-Cookie przy logowaniu, klient odsyla je
         *   automatycznie w kazdym kolejnym zadaniu.
         * - Identyfikator sesji powinien byc LOSOWY i NIEPRZEWIDYWALNY
         *   (UUID/SecureRandom) - inaczej atakujacy moze zgadnac cudza
         *   sesje (session fixation/prediction).
         * - Wylogowanie = USUNIECIE sesji po stronie serwera (samo
         *   skasowanie ciastka po stronie klienta NIE WYSTARCZY, jesli
         *   atakujacy juz zna identyfikator).
         * - WAZNE atrybuty ciastek sesyjnych (pogłębione w Lesson12
         *   SecurityHeaders): `HttpOnly` (blokuje odczyt przez
         *   JavaScript - ochrona przed XSS), `Secure` (tylko HTTPS),
         *   `SameSite` (ochrona przed CSRF, patrz Lesson10).
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static void handleLogin(HttpExchange exchange, Map<String, String> sessions, Map<String, String> users) throws java.io.IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }
        String body = new String(exchange.getRequestBody().readAllBytes());
        Map<String, String> params = parseFormBody(body);
        String username = params.get("username");
        String password = params.get("password");

        if (username != null && password != null && password.equals(users.get(username))) {
            String sessionId = UUID.randomUUID().toString();
            sessions.put(sessionId, username);
            exchange.getResponseHeaders().add("Set-Cookie", "SESSIONID=" + sessionId + "; Path=/; HttpOnly");
            byte[] responseBytes = ("Zalogowano jako " + username).getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            exchange.getResponseBody().write(responseBytes);
        } else {
            exchange.sendResponseHeaders(401, -1);
        }
        exchange.close();
    }

    private static void handleProfile(HttpExchange exchange, Map<String, String> sessions) throws java.io.IOException {
        String sessionId = extractCookie(exchange, "SESSIONID");
        String username = sessionId == null ? null : sessions.get(sessionId);

        if (username == null) {
            exchange.sendResponseHeaders(401, -1);
        } else {
            byte[] responseBytes = ("Profil uzytkownika: " + username).getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            exchange.getResponseBody().write(responseBytes);
        }
        exchange.close();
    }

    private static void handleLogout(HttpExchange exchange, Map<String, String> sessions) throws java.io.IOException {
        String sessionId = extractCookie(exchange, "SESSIONID");
        if (sessionId != null) {
            sessions.remove(sessionId);
        }
        exchange.getResponseHeaders().add("Set-Cookie", "SESSIONID=; Path=/; Max-Age=0");
        exchange.sendResponseHeaders(200, -1);
        exchange.close();
    }

    private static String extractCookie(HttpExchange exchange, String cookieName) {
        String cookieHeader = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookieHeader == null) {
            return null;
        }
        for (String part : cookieHeader.split(";")) {
            String[] kv = part.trim().split("=", 2);
            if (kv.length == 2 && kv[0].equals(cookieName)) {
                return kv[1];
            }
        }
        return null;
    }

    private static Map<String, String> parseFormBody(String body) {
        Map<String, String> result = new java.util.HashMap<>();
        for (String pair : body.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                result.put(kv[0], kv[1]);
            }
        }
        return result;
    }

    private static void demonstrateCookieBasedSession(int port) throws Exception {
        System.out.println("\n=== LOGOWANIE, OTRZYMANIE CIASTKA, DOSTEP DO /profile ===");

        HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
        String baseUrl = "http://localhost:" + port;

        HttpRequest loginRequest = HttpRequest.newBuilder(URI.create(baseUrl + "/login"))
                .POST(HttpRequest.BodyPublishers.ofString("username=admin&password=tajne123"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST /login -> status " + loginResponse.statusCode() + ", Set-Cookie: "
                + loginResponse.headers().firstValue("Set-Cookie").orElse("(brak)"));

        HttpRequest profileRequest = HttpRequest.newBuilder(URI.create(baseUrl + "/profile")).GET().build();
        HttpResponse<String> profileResponse = client.send(profileRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /profile (z ciastkiem od CookieManager) -> status " + profileResponse.statusCode()
                + ", body: " + profileResponse.body());
    }

    private static void demonstrateSessionIsolationPerClient(int port) throws Exception {
        System.out.println("\n=== 2 NIEZALEZNI KLIENCI - KAZDY MA WLASNA SESJE ===");

        String baseUrl = "http://localhost:" + port;

        HttpClient clientWithoutCookie = HttpClient.newHttpClient(); // BEZ CookieManager - nie zapamieta ciastka
        HttpRequest profileRequest = HttpRequest.newBuilder(URI.create(baseUrl + "/profile")).GET().build();
        HttpResponse<String> response = clientWithoutCookie.send(profileRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /profile BEZ zadnego ciastka -> status " + response.statusCode() + " (brak sesji = brak dostepu).");
    }

    private static void demonstrateLogoutInvalidatesSession(int port) throws Exception {
        System.out.println("\n=== WYLOGOWANIE UNIEWAZNIA SESJE PO STRONIE SERWERA ===");

        HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
        String baseUrl = "http://localhost:" + port;

        client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/login"))
                .POST(HttpRequest.BodyPublishers.ofString("username=jan&password=haslo456"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build(), HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> beforeLogout = client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/profile")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /profile PRZED wylogowaniem -> status " + beforeLogout.statusCode() + ", body: " + beforeLogout.body());

        client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/logout")).POST(HttpRequest.BodyPublishers.noBody()).build(),
                HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> afterLogout = client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/profile")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /profile PO wylogowaniu (to samo, wygaszone ciastko) -> status " + afterLogout.statusCode()
                + " (sesja usunieta po stronie serwera, nie tylko cookie wygaszone u klienta).");
    }
}
