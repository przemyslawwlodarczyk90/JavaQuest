package com.example.javaquest._19_security_basics.Lesson21_OwaspTop10OverviewAndCapstone;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

public class _Lesson21_OwaspTop10OverviewAndCapstone {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 21: PRZEGLAD OWASP TOP 10 I PROJEKT KONCOWY ===");

        printOwaspTop10Mapping();
        runCapstoneDemo();

        System.out.println("\n=== KONIEC LEKCJI 21, KONIEC ROZDZIALU _19_security_basics ===");
    }

    private static void printOwaspTop10Mapping() {
        /*
         * ============================================================
         * 📦 OWASP TOP 10 (2021) - MAPA NA LEKCJE TEGO ROZDZIALU
         * ============================================================
         * OWASP Top 10 to lista NAJPOWAZNIEJSZYCH, najczesciej
         * spotykanych kategorii podatnosci w aplikacjach webowych,
         * aktualizowana co kilka lat przez fundacje OWASP na podstawie
         * REALNYCH danych z tysiecy aplikacji. To NIE jest 10 konkretnych
         * bledow - to 10 KATEGORII, kazda obejmujaca wiele konkretnych
         * technik ataku (dokladnie to, co pokazaly poprzednie 20 lekcji).
         */
        System.out.println("\n=== OWASP TOP 10 (2021) - MAPA NA LEKCJE TEGO ROZDZIALU ===");

        Map<String, String> owaspMapping = new LinkedHashMap<>();
        owaspMapping.put("A01 Broken Access Control", "Lesson01 (AuthN vs AuthZ), Lesson07 (RBAC/ABAC)");
        owaspMapping.put("A02 Cryptographic Failures", "Lesson02 (hashowanie hasel), Lesson03 (BCrypt), Lesson08 (HTTPS/TLS)");
        owaspMapping.put("A03 Injection", "Lesson13 (SQL injection), Lesson15 (XXE)");
        owaspMapping.put("A04 Insecure Design", "Lesson17 (walidacja jako ARCHITEKTURA, nie tylko technika) + `_17_architecture`");
        owaspMapping.put("A05 Security Misconfiguration", "Lesson09 (CORS), Lesson12 (naglowki bezpieczenstwa)");
        owaspMapping.put("A06 Vulnerable and Outdated Components", "Lesson20 (bezpieczenstwo zaleznosci i lancucha dostaw)");
        owaspMapping.put("A07 Identification and Authentication Failures", "Lesson01, Lesson04 (sesje/ciasteczka), Lesson05 (JWT), Lesson06 (OAuth2/OIDC)");
        owaspMapping.put("A08 Software and Data Integrity Failures", "Lesson14 (niebezpieczna deserializacja), Lesson20 (integralnosc lancucha dostaw)");
        owaspMapping.put("A09 Security Logging and Monitoring Failures", "Lesson19 (bezpieczne logowanie i audyt)");
        owaspMapping.put("A10 Server-Side Request Forgery (SSRF)", "BRAK dedykowanej lekcji w tym rozdziale (patrz nizej)");

        for (Map.Entry<String, String> entry : owaspMapping.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        /*
         * ============================================================
         * 🔹 UCZCIWA LUKA: A10 SSRF NIE MA WLASNEJ LEKCJI
         * ============================================================
         * SSRF (Server-Side Request Forgery) - atakujacy nakłania SERWER,
         * zeby ten wykonal zadanie HTTP do miejsca, ktorego atakujacy NIE
         * MA prawa dotkniec bezposrednio (np. wewnetrzne API w sieci
         * firmowej, metadata endpoint chmury typu
         * `http://169.254.169.254/`, ktory potrafi zwrocic dane
         * uwierzytelniajace instancji). Klasyczny przyklad: formularz
         * "Pobierz obrazek z URL" ktory serwer pobiera SAM, bez
         * ograniczenia, DOKAD wolno mu sie polaczyc - atakujacy podaje
         * URL wewnetrznego zasobu zamiast obrazka.
         * Obrona (koncepcyjnie, bez pelnej lekcji): allowlist dozwolonych
         * hostow/protokolow (patrz Allowlist z `Lesson17_InputValidation`),
         * blokada zadan do adresow prywatnych/loopback, osobna siec dla
         * ruchu wychodzacego z serwera bez dostepu do zasobow
         * wewnetrznych. Ta luka jest ZAMIERZONA i JAWNIE przyznana -
         * rozdzial celowo NIE probuje "na sile" dociagnac tematu, ktorego
         * nie da sie porzadnie zmiescic bez oddzielnej lekcji.
         */
        System.out.println("\nA10 SSRF - swiadomie BEZ dedykowanej lekcji w tym rozdziale (koncepcja: serwer 'oszukany', by odpytal zasob, do ktorego atakujacy nie ma bezposredniego dostepu).");
    }

    // ======================== CAPSTONE: ZABEZPIECZONY MINI-ENDPOINT ========================

    private record AppUser(String username, String bcryptHash, String role) {
    }

    private record AuditEvent(String actor, String action, String outcome, Instant timestamp, String hash) {
    }

    private static final List<AuditEvent> AUDIT_CHAIN = new ArrayList<>();

    private static synchronized void audit(String actor, String action, String outcome) {
        String previousHash = AUDIT_CHAIN.isEmpty() ? "GENESIS" : AUDIT_CHAIN.get(AUDIT_CHAIN.size() - 1).hash();
        Instant timestamp = Instant.now();
        String payload = previousHash + "|" + actor + "|" + action + "|" + outcome + "|" + timestamp;
        String hash = sha256Hex(payload);
        AUDIT_CHAIN.add(new AuditEvent(actor, action, outcome, timestamp, hash));
    }

    private static boolean verifyAuditChain() {
        String expectedPreviousHash = "GENESIS";
        for (AuditEvent event : AUDIT_CHAIN) {
            String payload = expectedPreviousHash + "|" + event.actor() + "|" + event.action() + "|" + event.outcome() + "|" + event.timestamp();
            if (!sha256Hex(payload).equals(event.hash())) {
                return false;
            }
            expectedPreviousHash = event.hash();
        }
        return true;
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void addSecurityHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("X-Content-Type-Options", "nosniff");
        exchange.getResponseHeaders().add("X-Frame-Options", "DENY");
        exchange.getResponseHeaders().add("Content-Security-Policy", "default-src 'none'");
    }

    private static void sendResponse(HttpExchange exchange, int status, String body) throws IOException {
        addSecurityHeaders(exchange);
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (var os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static Map<String, String> parseFormBody(HttpExchange exchange) throws IOException {
        try (InputStream in = exchange.getRequestBody()) {
            String raw = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> result = new LinkedHashMap<>();
            for (String pair : raw.split("&")) {
                if (pair.isBlank()) {
                    continue;
                }
                String[] kv = pair.split("=", 2);
                result.put(kv[0], kv.length > 1 ? kv[1] : "");
            }
            return result;
        }
    }

    private static void handleLogin(HttpExchange exchange, Map<String, AppUser> users, SecretKey jwtKey) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        Map<String, String> form = parseFormBody(exchange);
        String username = form.getOrDefault("username", "");
        String password = form.getOrDefault("password", "");

        // Warstwa 1: walidacja wejscia (Lesson17) - PRZED jakakolwiek logika autentykacji.
        if (username.isBlank() || password.isBlank()) {
            audit(username.isBlank() ? "(pusty)" : username, "LOGIN", "VALIDATION_FAILED");
            sendResponse(exchange, 400, "Bad Request: username/password nie moga byc puste");
            return;
        }

        AppUser user = users.get(username);
        // Warstwa 2: uwierzytelnianie przez BCrypt (Lesson02/03) - NIGDY nie porownuj hasel wprost.
        if (user == null || !BCrypt.checkpw(password, user.bcryptHash())) {
            audit(username, "LOGIN", "AUTH_FAILURE");
            sendResponse(exchange, 401, "Unauthorized: nieprawidlowe dane logowania");
            return;
        }

        // Warstwa 3: wystawienie JWT (Lesson05) - krotki czas zycia, rola jako claim.
        String token = Jwts.builder()
                .subject(user.username())
                .claim("role", user.role())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(jwtKey)
                .compact();

        audit(username, "LOGIN", "AUTH_SUCCESS");
        sendResponse(exchange, 200, "token=" + token);
    }

    private static void handleAdminReport(HttpExchange exchange, SecretKey jwtKey, Map<String, Set<String>> rolePermissions) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            audit("(nieznany)", "GET /admin/report", "ACCESS_DENIED_NO_TOKEN");
            sendResponse(exchange, 401, "Unauthorized: brak tokenu");
            return;
        }

        String token = authHeader.substring("Bearer ".length());
        String username;
        String role;
        try {
            var claims = Jwts.parser().verifyWith(jwtKey).build().parseSignedClaims(token).getPayload();
            username = claims.getSubject();
            role = claims.get("role", String.class);
        } catch (JwtException e) {
            // Warstwa 4: odrzucenie niepoprawnego/zmodyfikowanego/wygaslego tokenu (Lesson05).
            audit("(niewazny token)", "GET /admin/report", "ACCESS_DENIED_INVALID_TOKEN");
            sendResponse(exchange, 401, "Unauthorized: niepoprawny token (" + e.getClass().getSimpleName() + ")");
            return;
        }

        // Warstwa 5: autoryzacja RBAC (Lesson07) - uwierzytelniony NIE oznacza uprawniony.
        Set<String> permissions = rolePermissions.getOrDefault(role, Set.of());
        if (!permissions.contains("report:read")) {
            audit(username, "GET /admin/report", "PERMISSION_DENIED");
            sendResponse(exchange, 403, "Forbidden: rola '" + role + "' nie ma uprawnienia 'report:read'");
            return;
        }

        audit(username, "GET /admin/report", "GRANTED");
        sendResponse(exchange, 200, "Raport: 42 zamowienia, 1337 uzytkownikow (dostep udzielony dla roli '" + role + "')");
    }

    private static void runCapstoneDemo() throws Exception {
        System.out.println("\n=== PROJEKT KONCOWY: ZABEZPIECZONY MINI-ENDPOINT LACZACY CALY ROZDZIAL ===");
        System.out.println("Warstwy: walidacja wejscia -> BCrypt -> JWT -> RBAC -> naglowki bezpieczenstwa -> audyt z lancuchem skrotow.");

        Map<String, AppUser> users = Map.of(
                "ala", new AppUser("ala", BCrypt.hashpw("sekret123", BCrypt.gensalt(4)), "admin"),
                "jan", new AppUser("jan", BCrypt.hashpw("haslo456", BCrypt.gensalt(4)), "user")
        );
        // Cost factor=4 UZYWANY TYLKO dla szybkosci tego demo (patrz Lesson03) - w produkcji uzyj 10-12+.
        Map<String, Set<String>> rolePermissions = Map.of(
                "admin", Set.of("report:read"),
                "user", Set.of()
        );
        SecretKey jwtKey = Jwts.SIG.HS256.key().build();

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/login", exchange -> handleLogin(exchange, users, jwtKey));
        server.createContext("/admin/report", exchange -> handleAdminReport(exchange, jwtKey, rolePermissions));
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            System.out.println("\n--- Scenariusz 1: udane logowanie jako admin ---");
            String adminToken = login(client, port, "ala", "sekret123");

            System.out.println("\n--- Scenariusz 2: nieudane logowanie (zle haslo) ---");
            login(client, port, "ala", "zle-haslo");

            System.out.println("\n--- Scenariusz 3: pusty username (walidacja wejscia) ---");
            login(client, port, "", "cokolwiek");

            System.out.println("\n--- Scenariusz 4: admin odczytuje raport (200 - AuthN+AuthZ OK) ---");
            callReport(client, port, adminToken);

            System.out.println("\n--- Scenariusz 5: zwykly uzytkownik probuje odczytac raport (403 - uwierzytelniony, ale NIE uprawniony) ---");
            String userToken = login(client, port, "jan", "haslo456");
            callReport(client, port, userToken);

            System.out.println("\n--- Scenariusz 6: brak tokenu w ogole (401) ---");
            callReport(client, port, null);

            System.out.println("\n--- Scenariusz 7: zmodyfikowany (nieprawidlowy) token (401) ---");
            callReport(client, port, adminToken + "zmanipulowany");
        } finally {
            server.stop(0);
        }

        System.out.println("\n=== INTEGRALNOSC DZIENNIKA AUDYTU PO CALYM DEMO ===");
        System.out.println("Liczba zarejestrowanych zdarzen audytu: " + AUDIT_CHAIN.size());
        System.out.println("Weryfikacja lancucha skrotow: " + verifyAuditChain() + " (oczekiwane: true - zaden wpis nie zostal naruszony)");
    }

    private static String login(HttpClient client, int port, String username, String password) throws Exception {
        String body = "username=" + username + "&password=" + password;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/login"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST /login (" + username + ") -> status " + response.statusCode() + ", body: " + response.body());
        if (response.statusCode() == 200) {
            return response.body().substring("token=".length());
        }
        return null;
    }

    private static void callReport(HttpClient client, int port, String token) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/admin/report"))
                .GET();
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /admin/report -> status " + response.statusCode() + ", body: " + response.body());
    }
}
