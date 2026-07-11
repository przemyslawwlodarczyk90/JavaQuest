package com.example.javaquest._19_security_basics.Lesson06_OAuth2AndOpenIdConnectIntro;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class _Lesson06_OAuth2AndOpenIdConnectIntro {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 6: WPROWADZENIE DO OAUTH2 I OPENID CONNECT ===");

        /*
         * ============================================================
         * 📦 PROBLEM: DELEGOWANIE DOSTEPU BEZ DZIELENIA SIE HASLEM
         * ============================================================
         * Wyobrazmy sobie aplikacje "FotoDrukarnia", ktora chce wydrukowac
         * Twoje zdjecia z serwisu "ChmuraZdjec". FotoDrukarnia NIE
         * powinna nigdy poznac Twojego hasla do ChmuraZdjec - powinna
         * dostac OGRANICZONY dostep (np. tylko "odczyt zdjec"), ktory
         * MOZESZ w kazdej chwili cofnac. Dokladnie to rozwiazuje OAuth2
         * (RFC 6749) - protokol DELEGOWANEJ AUTORYZACJI.
         */
        System.out.println("OAuth2 = protokol DELEGOWANEJ autoryzacji - aplikacja dostaje OGRANICZONY dostep bez poznania hasla uzytkownika.");

        // 2 role: Authorization Server (wydaje tokeny) i Resource Server (chroni dane)
        AuthorizationServerState authState = new AuthorizationServerState();
        HttpServer authServer = startAuthorizationServer(authState);
        HttpServer resourceServer = startResourceServer(authState);

        int authPort = authServer.getAddress().getPort();
        int resourcePort = resourceServer.getAddress().getPort();
        System.out.println("Authorization Server (wydaje kody/tokeny) na porcie " + authPort);
        System.out.println("Resource Server (chroni dane uzytkownika) na porcie " + resourcePort);

        try {
            demonstrateAuthorizationCodeFlow(authPort, resourcePort);
            demonstrateInvalidTokenRejected(resourcePort);
        } finally {
            authServer.stop(0);
            resourceServer.stop(0);
            System.out.println("\nOba serwery zatrzymane.");
        }

        /*
         * ============================================================
         * 🔹 4 ROLE W OAUTH2
         * ============================================================
         * - RESOURCE OWNER    - Ty (uzytkownik), wlasciciel danych.
         * - CLIENT             - aplikacja chcaca dostepu (FotoDrukarnia).
         * - AUTHORIZATION SERVER - wydaje tokeny PO zgodzie uzytkownika
         *   (np. serwer logowania ChmuraZdjec).
         * - RESOURCE SERVER    - API przechowujace dane, WERYFIKUJE token
         *   przed zwroceniem danych (np. API zdjec ChmuraZdjec).
         *
         * ============================================================
         * 🔹 AUTHORIZATION CODE FLOW (najczestszy, najbezpieczniejszy)
         * ============================================================
         * 1. Client przekierowuje uzytkownika do Authorization Server
         *    (/authorize) z client_id, redirect_uri, response_type=code.
         * 2. Uzytkownik loguje sie i WYRAZA ZGODE (consent) na Authorization
         *    Server (NIE u Clienta!).
         * 3. Authorization Server przekierowuje z powrotem do Clienta z
         *    KROTKOTRWALYM authorization code.
         * 4. Client WYMIENIA code (+ client_secret, POZA przegladarka,
         *    "server-to-server") na access_token na /token.
         * 5. Client uzywa access_token do wywolan Resource Server.
         * KLUCZOWE: haslo uzytkownika NIGDY nie trafia do Clienta -
         *   podaje je TYLKO na stronie Authorization Servera.
         *
         * ============================================================
         * 🔹 OPENID CONNECT (OIDC) = OAUTH2 + TOZSAMOSC
         * ============================================================
         * OAuth2 mowi TYLKO o AUTORYZACJI (dostep do zasobow) - NIE
         * definiuje formalnie "kim jest zalogowany uzytkownik". OIDC to
         * WARSTWA na OAuth2, ktora dodaje ID TOKEN - token w formacie JWT
         * (patrz Lesson05!) zawierajacy tozsamosc uzytkownika (np. sub,
         * email, name). Skrot: OAuth2 = "co MOZESZ zrobic", OIDC = "KIM
         * jestes". Wiele popularnych "Zaloguj sie przez Google/GitHub"
         * to WLASNIE OIDC zbudowany na OAuth2.
         */
        System.out.println("\nOAuth2 = autoryzacja ('co mozesz zrobic'). OpenID Connect = OAuth2 + tozsamosc (ID token jako JWT, 'kim jestes').");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - OAuth2 pozwala DELEGOWAC ograniczony dostep BEZ dzielenia
         *   sie haslem.
         * - 4 role: Resource Owner, Client, Authorization Server,
         *   Resource Server.
         * - Authorization Code Flow: authorize -> zgoda -> code ->
         *   wymiana na token (server-to-server) -> uzycie tokenu.
         * - Authorization code jest KROTKOTRWALY i JEDNORAZOWY - ta
         *   lekcja demonstruje odrzucenie proby JEGO POWTORNEGO uzycia.
         * - OIDC dodaje ID token (JWT) - formalna warstwe TOZSAMOSCI nad
         *   OAuth2.
         * - W praktyce produkcyjnej NIE implementuje sie tego recznie -
         *   uzywa sie sprawdzonych bibliotek/dostawcow (Keycloak, Auth0,
         *   Okta, Spring Security OAuth2) - ta lekcja pokazuje "pod
         *   maska", zeby zrozumiec co te biblioteki robia za nas.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    /** Stan trzymany "po stronie" Authorization Servera - kody, tokeny, zarejestrowani klienci. */
    private static class AuthorizationServerState {
        final Map<String, String> registeredClients = Map.of("fotodrukarnia-client", "tajny-sekret-klienta");
        final Map<String, String> authorizationCodes = new ConcurrentHashMap<>(); // code -> username
        final Map<String, String> accessTokens = new ConcurrentHashMap<>(); // token -> username
    }

    private static HttpServer startAuthorizationServer(AuthorizationServerState state) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);

        server.createContext("/authorize", exchange -> {
            // W REALNYM systemie tutaj bylby formularz logowania + ekran zgody.
            // Tu ZAKLADAMY, ze uzytkownik "jan.kowalski" wlasnie sie zalogowal i wyrazil zgode.
            String code = UUID.randomUUID().toString();
            state.authorizationCodes.put(code, "jan.kowalski");
            String responseBody = "authorization_code=" + code;
            sendText(exchange, 200, responseBody);
        });

        server.createContext("/token", exchange -> {
            Map<String, String> params = parseFormBody(new String(exchange.getRequestBody().readAllBytes()));
            String clientId = params.get("client_id");
            String clientSecret = params.get("client_secret");
            String code = params.get("code");

            if (!state.registeredClients.containsKey(clientId) || !state.registeredClients.get(clientId).equals(clientSecret)) {
                sendText(exchange, 401, "error=invalid_client");
                return;
            }
            String username = state.authorizationCodes.remove(code); // JEDNORAZOWY - usuwany od razu po uzyciu
            if (username == null) {
                sendText(exchange, 400, "error=invalid_grant (kod nieznany lub juz uzyty)");
                return;
            }
            String accessToken = UUID.randomUUID().toString();
            state.accessTokens.put(accessToken, username);
            sendText(exchange, 200, "access_token=" + accessToken + "&token_type=Bearer");
        });

        server.setExecutor(null);
        server.start();
        return server;
    }

    private static HttpServer startResourceServer(AuthorizationServerState state) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);

        server.createContext("/api/photos", exchange -> {
            String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
            String token = (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
            String username = token == null ? null : state.accessTokens.get(token);

            if (username == null) {
                sendText(exchange, 401, "error=invalid_token");
            } else {
                sendText(exchange, 200, "Zdjecia uzytkownika " + username + ": [wakacje.jpg, urodziny.jpg]");
            }
        });

        server.setExecutor(null);
        server.start();
        return server;
    }

    private static void demonstrateAuthorizationCodeFlow(int authPort, int resourcePort) throws Exception {
        System.out.println("\n=== PELNY AUTHORIZATION CODE FLOW ===");

        HttpClient client = HttpClient.newHttpClient();
        String authBase = "http://localhost:" + authPort;
        String resourceBase = "http://localhost:" + resourcePort;

        System.out.println("Krok 1-2: uzytkownik przekierowany na /authorize, loguje sie i wyraza zgode (symulowane).");
        HttpResponse<String> authorizeResponse = client.send(
                HttpRequest.newBuilder(URI.create(authBase + "/authorize")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        String code = authorizeResponse.body().split("=")[1];
        System.out.println("Krok 3: Authorization Server wydal authorization code: " + code);

        System.out.println("Krok 4: Client wymienia code na access_token (server-to-server, z client_secret).");
        String tokenRequestBody = "client_id=fotodrukarnia-client&client_secret=tajny-sekret-klienta&code=" + code;
        HttpResponse<String> tokenResponse = client.send(
                HttpRequest.newBuilder(URI.create(authBase + "/token"))
                        .POST(HttpRequest.BodyPublishers.ofString(tokenRequestBody))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .build(),
                HttpResponse.BodyHandlers.ofString());
        String accessToken = tokenResponse.body().split("&")[0].split("=")[1];
        System.out.println("Otrzymany access_token: " + accessToken);

        System.out.println("Krok 5: Client uzywa access_token do wywolania Resource Servera.");
        HttpResponse<String> photosResponse = client.send(
                HttpRequest.newBuilder(URI.create(resourceBase + "/api/photos"))
                        .header("Authorization", "Bearer " + accessToken)
                        .GET().build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /api/photos -> status " + photosResponse.statusCode() + ", body: " + photosResponse.body());

        System.out.println("\nProba PONOWNEGO uzycia TEGO SAMEGO authorization code (kod jest jednorazowy):");
        HttpResponse<String> reuseResponse = client.send(
                HttpRequest.newBuilder(URI.create(authBase + "/token"))
                        .POST(HttpRequest.BodyPublishers.ofString(tokenRequestBody))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("POST /token (ponownie) -> status " + reuseResponse.statusCode() + ", body: " + reuseResponse.body());
    }

    private static void demonstrateInvalidTokenRejected(int resourcePort) throws Exception {
        System.out.println("\n=== ZLY/NIEISTNIEJACY TOKEN JEST ODRZUCANY PRZEZ RESOURCE SERVER ===");

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + resourcePort + "/api/photos"))
                        .header("Authorization", "Bearer zmyslony-losowy-token")
                        .GET().build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /api/photos z fikcyjnym tokenem -> status " + response.statusCode() + ", body: " + response.body());
    }

    private static void sendText(HttpExchange exchange, int status, String body) throws java.io.IOException {
        byte[] bytes = body.getBytes();
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
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
}
