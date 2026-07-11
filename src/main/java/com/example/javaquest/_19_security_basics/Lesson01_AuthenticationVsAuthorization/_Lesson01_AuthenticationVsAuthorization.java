package com.example.javaquest._19_security_basics.Lesson01_AuthenticationVsAuthorization;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class _Lesson01_AuthenticationVsAuthorization {

    record User(String username, String password, String role) {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 1: UWIERZYTELNIANIE vs AUTORYZACJA ===");

        /*
         * ============================================================
         * 📦 PO CO NOWY, OSOBNY ROZDZIAL O BEZPIECZENSTWIE?
         * ============================================================
         * Rozdzial `_18_rest_api` nauczyl PROJEKTOWANIA API - zasoby,
         * metody, statusy, bledy. Ten rozdzial (`_19_security_basics`)
         * jest jego NATURALNYM uzupelnieniem - kazde realne API musi
         * odpowiedziec na 2 fundamentalne pytania: "KIM jest ten, kto
         * pyta?" (uwierzytelnianie) i "CO WOLNO mu zrobic?" (autoryzacja).
         * WSZYSTKIE kolejne lekcje tego rozdzialu (sesje, JWT, OAuth2,
         * RBAC, CORS, CSRF...) sa POGLEBIENIEM tych 2 pojec.
         */

        /*
         * ============================================================
         * 🔹 UWIERZYTELNIANIE (AUTHENTICATION, "AuthN") = KIM JESTES?
         * ============================================================
         * Proces WERYFIKACJI TOZSAMOSCI - sprawdzenie, czy ktos jest
         * FAKTYCZNIE tym, za kogo sie podaje (login+haslo, token,
         * certyfikat, biometria). Wynik: "TAK, wiem kim jestes (userId=42)"
         * albo "NIE, nie wiem kim jestes / podane dane sa bledne".
         *
         * 🔍 AUTORYZACJA (AUTHORIZATION, "AuthZ") = CO WOLNO CI ZROBIC?
         * ============================================================
         * Proces sprawdzania UPRAWNIEN - ZAKLADAJAC, ze tozsamosc jest
         * juz znana (uwierzytelnianie sie powiodlo), czy TA KONKRETNA
         * osoba ma prawo wykonac TA KONKRETNA akcje (np. "czy user=42
         * moze usunac CUDZE zamowienie?").
         *
         * KLUCZOWA ZALEZNOSC: autoryzacja ZAWSZE wymaga wczesniejszego
         * uwierzytelnienia - nie da sie sprawdzic UPRAWNIEN kogos, KOGO
         * TOZSAMOSCI nie znasz.
         */
        System.out.println("Uwierzytelnianie (AuthN) = KIM JESTES? Autoryzacja (AuthZ) = CO WOLNO CI ZROBIC?");
        System.out.println("Autoryzacja ZAWSZE zaklada wczesniejsze uwierzytelnienie - nie da sie sprawdzic uprawnien 'nikogo'.");

        Map<String, User> users = Map.of(
                "ala", new User("ala", "sekret123", "admin"),
                "jan", new User("jan", "haslo456", "user")
        );
        Map<String, String> activeSessions = new ConcurrentHashMap<>(); // token -> username

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/login", exchange -> handleLogin(exchange, users, activeSessions));
        server.createContext("/admin/dashboard", exchange -> handleAdminDashboard(exchange, users, activeSessions));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateFailedAuthentication(client, port);
            String adminToken = demonstrateSuccessfulAuthentication(client, port, "ala", "sekret123");
            String userToken = demonstrateSuccessfulAuthentication(client, port, "jan", "haslo456");
            demonstrateAuthorizedAccess(client, port, adminToken);
            demonstrateAuthenticatedButNotAuthorized(client, port, userToken);
            demonstrateMissingCredentialsAtAll(client, port);
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - AuthN ("kim jestes?") - weryfikacja tozsamosci - BLAD daje
         *   401 Unauthorized (majacy nazwe historyczna, myląca - w praktyce
         *   oznacza "nieuwierzytelniony", nie "nieautoryzowany"!).
         * - AuthZ ("co wolno ci?") - sprawdzenie uprawnien PO udanym
         *   AuthN - BLAD daje 403 Forbidden.
         * - 401 = "NIE WIEM kim jestes" (brak/zle dane logowania/token).
         * - 403 = "WIEM kim jestes, ale NIE MASZ wystarczajacych
         *   uprawnien" (znana tozsamosc, za malo uprawnien).
         * - Kolejne lekcje POGLEBIAJA kazdy z tych 2 filarow: Lesson02-03
         *   (JAK bezpiecznie przechowywac haslo do AuthN), Lesson04-06
         *   (JAK reprezentowac 'zalogowanie' - sesje/JWT/OAuth2),
         *   Lesson07 (JAK modelowac AuthZ przez role/uprawnienia - RBAC).
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void handleLogin(com.sun.net.httpserver.HttpExchange exchange, Map<String, User> users, Map<String, String> sessions) throws java.io.IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String[] parts = body.split("&"); // uproszczony format "username=ala&password=sekret123"
        String username = parts[0].substring("username=".length());
        String password = parts[1].substring("password=".length());

        User user = users.get(username);
        if (user == null || !user.password().equals(password)) {
            // AuthN ZAWIODLA - serwer NIE WIE, kim jest ten klient
            respond(exchange, 401, "{\"error\":\"Nieprawidlowa nazwa uzytkownika lub haslo\"}");
            return;
        }

        String token = UUID.randomUUID().toString();
        sessions.put(token, username);
        respond(exchange, 200, "{\"token\":\"" + token + "\"}");
    }

    private static void handleAdminDashboard(com.sun.net.httpserver.HttpExchange exchange, Map<String, User> users, Map<String, String> sessions) throws java.io.IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        String token = (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring("Bearer ".length()) : null;

        // KROK 1: AuthN - CZY W OGOLE WIEMY, KIM JEST TEN KLIENT?
        String username = (token != null) ? sessions.get(token) : null;
        if (username == null) {
            respond(exchange, 401, "{\"error\":\"Brak lub nieprawidlowy token uwierzytelniajacy\"}");
            return;
        }

        // KROK 2: AuthZ - SKORO WIEMY KIM JEST, CZY MA UPRAWNIENIA DO TEJ AKCJI?
        User user = users.get(username);
        if (!"admin".equals(user.role())) {
            respond(exchange, 403, "{\"error\":\"Wymagana rola 'admin' - Twoja rola: " + user.role() + "\"}");
            return;
        }

        respond(exchange, 200, "{\"dashboard\":\"tajne dane administracyjne\"}");
    }

    private static void respond(com.sun.net.httpserver.HttpExchange exchange, int status, String json) throws java.io.IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static void demonstrateFailedAuthentication(HttpClient client, int port) throws Exception {
        System.out.println("\n=== NIEUDANE UWIERZYTELNIANIE: ZLE DANE LOGOWANIA ===");
        var response = login(client, port, "ala", "zle-haslo");
        System.out.println("POST /login (zle haslo) -> " + response.statusCode() + " " + response.body() + "  (401 - serwer NIE WIE, kim jestes)");
    }

    private static String demonstrateSuccessfulAuthentication(HttpClient client, int port, String username, String password) throws Exception {
        System.out.println("\n=== UDANE UWIERZYTELNIANIE: " + username + " ===");
        var response = login(client, port, username, password);
        System.out.println("POST /login (" + username + ") -> " + response.statusCode() + " " + response.body());
        return response.body().replaceAll(".*\"token\":\"([^\"]+)\".*", "$1");
    }

    private static void demonstrateAuthorizedAccess(HttpClient client, int port, String adminToken) throws Exception {
        System.out.println("\n=== UWIERZYTELNIONY I AUTORYZOWANY (admin) ===");
        var response = getDashboard(client, port, adminToken);
        System.out.println("GET /admin/dashboard (token admina) -> " + response.statusCode() + " " + response.body());
    }

    private static void demonstrateAuthenticatedButNotAuthorized(HttpClient client, int port, String userToken) throws Exception {
        System.out.println("\n=== UWIERZYTELNIONY, ALE NIE AUTORYZOWANY (zwykly user) ===");
        var response = getDashboard(client, port, userToken);
        System.out.println("GET /admin/dashboard (token zwyklego usera) -> " + response.statusCode() + " " + response.body()
                + "  (403 - serwer WIE kim jestes, ale to za malo uprawnien)");
    }

    private static void demonstrateMissingCredentialsAtAll(HttpClient client, int port) throws Exception {
        System.out.println("\n=== BRAK JAKICHKOLWIEK DANYCH UWIERZYTELNIAJACYCH ===");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/admin/dashboard"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /admin/dashboard (bez naglowka Authorization) -> " + response.statusCode() + " " + response.body()
                + "  (401 - rowniez 'nie wiem kim jestes', ale z INNEGO powodu niz zle haslo)");
    }

    private static HttpResponse<String> login(HttpClient client, int port, String username, String password) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/login"))
                .POST(HttpRequest.BodyPublishers.ofString("username=" + username + "&password=" + password))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> getDashboard(HttpClient client, int port, String token) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/admin/dashboard"))
                .header("Authorization", "Bearer " + token)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
