package com.example.javaquest._19_security_basics.Lesson10_Csrf;

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

public class _Lesson10_Csrf {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 10: CSRF (CROSS-SITE REQUEST FORGERY) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: PRZEGLADARKA WYSYLA CIASTKA AUTOMATYCZNIE
         * ============================================================
         * Z Lesson04 wiemy, ze przegladarka AUTOMATYCZNIE dolacza
         * ciastka (w tym ciastko sesyjne) do KAZDEGO zadania do danego
         * hosta - NIEZALEZNIE od tego, ktora strona to zadanie
         * zainicjowala! Jesli jestes zalogowany na `bank.pl` (masz jego
         * ciastko sesyjne), a nastepnie odwiedzisz `zlosliwa-strona.pl`,
         * ktora ma ukryty formularz auto-wysylajacy POST do
         * `bank.pl/transfer` - Twoja przegladarka WYSLE to ciastko
         * sesyjne RAZEM z tym zadaniem. Bank (bez dodatkowej ochrony) NIE
         * ODROZNI tego od PRAWDZIWEGO zadania zainicjowanego przez Ciebie
         * swiadomie. To WLASNIE jest CSRF.
         */
        System.out.println("CSRF = zlosliwa strona 'podszywa sie' pod Twoje zadanie, wykorzystujac Twoje AUTOMATYCZNIE wysylane ciastko sesyjne.");

        ServerState state = new ServerState();
        HttpServer server = startServer(state);
        int port = server.getAddress().getPort();
        System.out.println("\nLokalny 'bank.pl' (serwer) wystartowal na porcie " + port);

        try {
            demonstrateLegitimateTransfer(port);
            demonstrateCsrfAttackWithoutProtection(port);
            demonstrateCsrfTokenBlocksAttack(port);
            demonstrateSameSiteCookieAsDefense();
        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 🔹 OBRONA #1: CSRF TOKEN (SYNCHRONIZER TOKEN PATTERN)
         * ============================================================
         * Serwer generuje LOSOWY token przy logowaniu, powiazany z
         * sesja, i WYMAGA go w kazdym zadaniu MODYFIKUJACYM stan (POST/
         * PUT/DELETE) - jako naglowek LUB ukryte pole formularza (NIE
         * jako ciastko - to bylby ten sam problem od nowa!). Zlosliwa
         * strona NIE ZNA tego tokenu (nie moze go odczytac z ciastka
         * ofiary - Same-Origin Policy z Lesson09 to blokuje), wiec jej
         * "podrobione" zadanie NIE PRZEJDZIE weryfikacji.
         *
         * ============================================================
         * 🔹 OBRONA #2: ATRYBUT SAMESITE NA CIASTKU
         * ============================================================
         * - `SameSite=Strict` - ciastko WYSYLANE TYLKO gdy zadanie
         *   pochodzi z TEJ SAMEJ strony (nawet klikniecie linku z innej
         *   strony NIE wysle ciastka).
         * - `SameSite=Lax` (DOMYSLNE we wspolczesnych przegladarkach) -
         *   ciastko wysylane przy nawigacji GET (klikniecie linku), ale
         *   NIE przy automatycznych POST z formularzy na innych stronach.
         * - `SameSite=None` - stare zachowanie (zawsze wysylane), WYMAGA
         *   `Secure` (tylko HTTPS, patrz Lesson08).
         * WAZNE: `SameSite=Lax` (domyslne) to SOLIDNA ochrona, ale
         * CSRF token WCIAZ jest zalecany jako DODATKOWA warstwa (obrona
         * w glab, "defense in depth") - nie wszystkie przegladarki/
         * scenariusze honoruja SameSite identycznie.
         */
        System.out.println("\nObrona: CSRF token (osobny od ciastka, nieznany zlosliwej stronie) + atrybut SameSite na ciastku.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - CSRF wykorzystuje AUTOMATYCZNE wysylanie ciastek przez
         *   przegladarke - zlosliwa strona "podszywa sie" pod uzytkownika.
         * - Ofiara MUSI byc zalogowana (miec aktywna sesje) w atakowanym
         *   serwisie - CSRF atakuje SESJE, nie haslo.
         * - Obrona #1: CSRF token - losowy, powiazany z sesja, WYMAGANY
         *   w kazdym zadaniu modyfikujacym stan, nieznany atakujacemu.
         * - Obrona #2: `SameSite=Lax/Strict` na ciastku sesyjnym -
         *   ogranicza automatyczne wysylanie ciastek cross-site.
         * - CSRF dotyczy GLOWNIE uwierzytelniania OPARTEGO NA CIASTKACH -
         *   API uzywajace `Authorization: Bearer <JWT>` (Lesson05, token
         *   NIE wysylany automatycznie jak ciastko) sa z NATURY mniej
         *   podatne na klasyczny CSRF.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static class ServerState {
        final Map<String, String> sessions = new ConcurrentHashMap<>(); // sessionId -> username
        final Map<String, String> csrfTokens = new ConcurrentHashMap<>(); // sessionId -> csrfToken
        final Map<String, Double> balances = new ConcurrentHashMap<>(Map.of("jan.kowalski", 1000.0));
        boolean requireCsrfToken = false;
    }

    private static HttpServer startServer(ServerState state) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);

        server.createContext("/login", exchange -> {
            String sessionId = UUID.randomUUID().toString();
            String csrfToken = UUID.randomUUID().toString();
            state.sessions.put(sessionId, "jan.kowalski");
            state.csrfTokens.put(sessionId, csrfToken);
            exchange.getResponseHeaders().add("Set-Cookie", "SESSIONID=" + sessionId + "; Path=/; SameSite=Lax");
            byte[] response = ("Zalogowano. csrf_token=" + csrfToken).getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });

        server.createContext("/transfer", exchange -> {
            String sessionId = extractCookie(exchange, "SESSIONID");
            String username = sessionId == null ? null : state.sessions.get(sessionId);

            if (username == null) {
                sendText(exchange, 401, "error=brak_sesji");
                return;
            }
            if (state.requireCsrfToken) {
                String providedToken = exchange.getRequestHeaders().getFirst("X-CSRF-Token");
                String expectedToken = state.csrfTokens.get(sessionId);
                if (providedToken == null || !providedToken.equals(expectedToken)) {
                    sendText(exchange, 403, "error=brak_lub_zly_csrf_token");
                    return;
                }
            }
            state.balances.merge(username, -100.0, Double::sum);
            sendText(exchange, 200, "Przelano 100 PLN z konta " + username + ". Nowe saldo: " + state.balances.get(username));
        });

        server.setExecutor(null);
        server.start();
        return server;
    }

    private static void demonstrateLegitimateTransfer(int port) throws Exception {
        System.out.println("\n=== KROK 1: UZYTKOWNIK LOGUJE SIE I WYKONUJE PRAWDZIWY PRZELEW ===");

        HttpClient client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
        String baseUrl = "http://localhost:" + port;

        client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/login")).GET().build(), HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> transferResponse = client.send(
                HttpRequest.newBuilder(URI.create(baseUrl + "/transfer")).POST(HttpRequest.BodyPublishers.noBody()).build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("Swiadomy przelew uzytkownika -> status " + transferResponse.statusCode() + ", " + transferResponse.body());
    }

    private static void demonstrateCsrfAttackWithoutProtection(int port) throws Exception {
        System.out.println("\n=== KROK 2: ATAK CSRF - SERWER BEZ OCHRONY (requireCsrfToken=false) ===");

        HttpClient victimBrowser = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
        String baseUrl = "http://localhost:" + port;

        // Ofiara loguje sie normalnie w SWOJEJ przegladarce
        victimBrowser.send(HttpRequest.newBuilder(URI.create(baseUrl + "/login")).GET().build(), HttpResponse.BodyHandlers.ofString());
        System.out.println("Ofiara jest zalogowana (ma ciastko sesyjne w swojej przegladarce).");

        // Ofiara odwiedza zlosliwa-strona.pl, ktora ma UKRYTY formularz auto-wysylajacy POST do bank.pl/transfer.
        // Przegladarka ofiary AUTOMATYCZNIE dolacza ciastko sesyjne bank.pl - TA SAMA instancja HttpClient
        // symuluje TA SAMA przegladarke, ktora ma zapisane ciastko niezaleznie od tego, KTO zainicjowal zadanie.
        System.out.println("Ofiara (nieswiadomie) odwiedza zlosliwa-strona.pl z ukrytym formularzem POST do /transfer...");
        HttpResponse<String> forgedRequest = victimBrowser.send(
                HttpRequest.newBuilder(URI.create(baseUrl + "/transfer"))
                        .header("Origin", "https://zlosliwa-strona.pl") // przegladarka i tak wysle ciastko bank.pl!
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("Podrobiony przelew z zlosliwej strony -> status " + forgedRequest.statusCode() + ", " + forgedRequest.body());
        System.out.println("-> ATAK SIE POWIODL - serwer NIE ODROZNIL prawdziwego zadania od podrobionego (brak ochrony CSRF)!");
    }

    private static void demonstrateCsrfTokenBlocksAttack(int port) throws Exception {
        System.out.println("\n=== KROK 3: TA SAMA SYTUACJA, ALE Z WYMAGANYM CSRF TOKENEM ===");

        // W tym demo WLACZAMY wymog CSRF tokenu bezposrednio przez nowa instancje serwera z innym stanem.
        ServerState protectedState = new ServerState();
        protectedState.requireCsrfToken = true;
        HttpServer protectedServer = startServer(protectedState);
        int protectedPort = protectedServer.getAddress().getPort();

        try {
            HttpClient victimBrowser = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
            String baseUrl = "http://localhost:" + protectedPort;

            HttpResponse<String> loginResponse = victimBrowser.send(
                    HttpRequest.newBuilder(URI.create(baseUrl + "/login")).GET().build(), HttpResponse.BodyHandlers.ofString());
            String realCsrfToken = loginResponse.body().split("csrf_token=")[1];
            System.out.println("Ofiara zalogowana, prawdziwy CSRF token znany TYLKO stronie bank.pl: " + realCsrfToken);

            System.out.println("Zlosliwa strona probuje POWTORZYC atak z Kroku 2 (BEZ znajomosci CSRF tokenu)...");
            HttpResponse<String> forgedRequest = victimBrowser.send(
                    HttpRequest.newBuilder(URI.create(baseUrl + "/transfer"))
                            .header("Origin", "https://zlosliwa-strona.pl")
                            .POST(HttpRequest.BodyPublishers.noBody())
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("Podrobiony przelew (bez CSRF tokenu) -> status " + forgedRequest.statusCode() + ", " + forgedRequest.body());
            System.out.println("-> ATAK ZABLOKOWANY - zlosliwa strona nie mogla podac wymaganego naglowka X-CSRF-Token.");

            HttpResponse<String> legitRequest = victimBrowser.send(
                    HttpRequest.newBuilder(URI.create(baseUrl + "/transfer"))
                            .header("X-CSRF-Token", realCsrfToken)
                            .POST(HttpRequest.BodyPublishers.noBody())
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("Prawdziwy przelew (Z poprawnym CSRF tokenem) -> status " + legitRequest.statusCode() + ", " + legitRequest.body());
        } finally {
            protectedServer.stop(0);
        }
    }

    private static void demonstrateSameSiteCookieAsDefense() {
        System.out.println("\n=== OBRONA #2: ATRYBUT SAMESITE (KONCEPCYJNIE) ===");
        System.out.println("Ciastko wydane z 'Set-Cookie: SESSIONID=...; SameSite=Lax' NIE zostanie wyslane");
        System.out.println("przez przegladarke przy automatycznym POST z formularza na INNEJ stronie (zlosliwa-strona.pl),");
        System.out.println("nawet gdyby serwer NIE mial CSRF tokenu - to WAZNA, DODATKOWA warstwa ochrony.");
        System.out.println("UWAGA: `HttpClient` uzyty w tym demo NIE egzekwuje SameSite (to zachowanie przegladarek) -");
        System.out.println("ten fragment jest wiec celowo opisowy, nie zademonstrowany kodem (jak preflight w Lesson09).");
    }

    private static void sendText(HttpExchange exchange, int status, String body) throws java.io.IOException {
        byte[] bytes = body.getBytes();
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
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
}
