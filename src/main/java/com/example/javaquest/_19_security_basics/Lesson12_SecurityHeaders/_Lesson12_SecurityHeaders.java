package com.example.javaquest._19_security_basics.Lesson12_SecurityHeaders;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _Lesson12_SecurityHeaders {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: NAGLOWKI BEZPIECZENSTWA (SECURITY HEADERS) ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE Z LEKCJI 8, 10, 11
         * ============================================================
         * Poprzednie lekcje pokazaly KONKRETNE ataki (HTTPS/TLS, CSRF,
         * XSS) i konkretne obrony PO STRONIE LOGIKI aplikacji. Ta lekcja
         * pokazuje DODATKOWA, PROSTA warstwe obrony - naglowki HTTP,
         * ktore serwer dodaje do KAZDEJ odpowiedzi, instruujac
         * PRZEGLADARKE, jak ma sie zachowac. To "obrona w glab" (defense
         * in depth) - dziala NIEZALEZNIE od reszty aplikacji, jednym
         * dopiskiem konfiguracyjnym.
         */
        System.out.println("Naglowki bezpieczenstwa = instrukcje dla przegladarki, dodatkowa warstwa obrony 'za darmo' (bez zmian w logice).");

        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        boolean[] headersEnabled = {false};
        server.createContext("/page", exchange -> handleRequest(exchange, headersEnabled[0]));
        server.setExecutor(null);
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("\nLokalny serwer wystartowal na porcie " + port);

        try {
            demonstrateWithoutSecurityHeaders(port, headersEnabled);
            demonstrateWithSecurityHeaders(port, headersEnabled);
            explainEachHeader();
        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Naglowki bezpieczenstwa to DODATKOWA, TANIA warstwa obrony -
         *   NIE zastepuja escapowania (Lesson11), CSRF tokenow
         *   (Lesson10), czy HTTPS (Lesson08), ale je UZUPELNIAJA.
         * - `Content-Security-Policy` - ogranicza SKAD moga pochodzic
         *   skrypty/style/obrazy - redukuje skutki XSS nawet gdy
         *   escapowanie zawiedzie.
         * - `X-Frame-Options`/`frame-ancestors` (CSP) - chroni przed
         *   "clickjackingiem" (osadzeniem Twojej strony w niewidocznej
         *   ramce `<iframe>` na zlosliwej stronie).
         * - `Strict-Transport-Security` (HSTS) - wymusza HTTPS nawet
         *   jesli uzytkownik wpisze `http://` recznie.
         * - `X-Content-Type-Options: nosniff` - blokuje "zgadywanie"
         *   typu tresci przez przegladarke (MIME sniffing), ktore moglo
         *   by np. wykonac plik `.txt` jako skrypt.
         * - Te naglowki NALEZY dodawac do WSZYSTKICH odpowiedzi, najlepiej
         *   w JEDNYM, centralnym miejscu (filtr/middleware), nie
         *   rozproszone po kazdym handlerze z osobna.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static void handleRequest(HttpExchange exchange, boolean securityHeadersEnabled) throws java.io.IOException {
        if (securityHeadersEnabled) {
            exchange.getResponseHeaders().add("Content-Security-Policy", "default-src 'self'; script-src 'self'");
            exchange.getResponseHeaders().add("X-Frame-Options", "DENY");
            exchange.getResponseHeaders().add("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
            exchange.getResponseHeaders().add("X-Content-Type-Options", "nosniff");
            exchange.getResponseHeaders().add("Referrer-Policy", "no-referrer");
        }
        byte[] response = "<html><body>Strona testowa</body></html>".getBytes();
        exchange.getResponseHeaders().add("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    private static void demonstrateWithoutSecurityHeaders(int port, boolean[] headersEnabled) throws Exception {
        System.out.println("\n=== ODPOWIEDZ BEZ NAGLOWKOW BEZPIECZENSTWA ===");

        headersEnabled[0] = false;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/page")).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Content-Security-Policy: " + response.headers().firstValue("Content-Security-Policy").orElse("(BRAK)"));
        System.out.println("X-Frame-Options: " + response.headers().firstValue("X-Frame-Options").orElse("(BRAK)"));
        System.out.println("Strict-Transport-Security: " + response.headers().firstValue("Strict-Transport-Security").orElse("(BRAK)"));
        System.out.println("-> BRAK jakiejkolwiek dodatkowej ochrony - strona podatna np. na osadzenie w iframe przez zlosliwa strone.");
    }

    private static void demonstrateWithSecurityHeaders(int port, boolean[] headersEnabled) throws Exception {
        System.out.println("\n=== ODPOWIEDZ Z NAGLOWKAMI BEZPIECZENSTWA ===");

        headersEnabled[0] = true;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/page")).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Content-Security-Policy: " + response.headers().firstValue("Content-Security-Policy").orElse("(BRAK)"));
        System.out.println("X-Frame-Options: " + response.headers().firstValue("X-Frame-Options").orElse("(BRAK)"));
        System.out.println("Strict-Transport-Security: " + response.headers().firstValue("Strict-Transport-Security").orElse("(BRAK)"));
        System.out.println("X-Content-Type-Options: " + response.headers().firstValue("X-Content-Type-Options").orElse("(BRAK)"));
        System.out.println("Referrer-Policy: " + response.headers().firstValue("Referrer-Policy").orElse("(BRAK)"));
        System.out.println("-> ta sama strona, ale przegladarka ma teraz JASNE instrukcje ograniczajace ryzyko kilku klas atakow naraz.");
    }

    private static void explainEachHeader() {
        System.out.println("\n=== ZNACZENIE KAZDEGO NAGLOWKA ===");

        System.out.println("Content-Security-Policy (CSP):");
        System.out.println("  'default-src self; script-src self' -> przegladarka WYKONA TYLKO skrypty z TEGO SAMEGO originu -");
        System.out.println("  nawet gdyby atakujacy przemycil <script src=\"https://zla-domena.pl/x.js\">, przegladarka go ZABLOKUJE.");

        System.out.println("\nX-Frame-Options: DENY:");
        System.out.println("  zabrania osadzania strony w <iframe> NA JAKIEJKOLWIEK stronie (w tym wlasnej) -");
        System.out.println("  chroni przed 'clickjackingiem' (niewidoczna ramka nad przyciskiem, ktora klika ofiara).");

        System.out.println("\nStrict-Transport-Security (HSTS):");
        System.out.println("  'max-age=31536000' -> przez rok przegladarka BEDZIE WYMUSZAC HTTPS dla tej domeny,");
        System.out.println("  nawet jesli uzytkownik wpisze recznie 'http://' - chroni przed 'SSL stripping' (Lesson08).");

        System.out.println("\nX-Content-Type-Options: nosniff:");
        System.out.println("  zabrania przegladarce 'zgadywania' typu pliku innego niz zadeklarowany w Content-Type -");
        System.out.println("  bez tego, plik '.txt' z kodem HTML/JS mogl by zostac wykonany jako strona/skrypt.");

        System.out.println("\nReferrer-Policy: no-referrer:");
        System.out.println("  ogranicza, jakie informacje o poprzedniej stronie (URL) przegladarka wysyla dalej -");
        System.out.println("  chroni przed wyciekiem wrazliwych danych (np. tokenow w URL) do zewnetrznych serwisow.");
    }
}
