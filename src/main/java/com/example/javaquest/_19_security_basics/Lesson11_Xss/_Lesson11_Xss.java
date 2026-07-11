package com.example.javaquest._19_security_basics.Lesson11_Xss;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class _Lesson11_Xss {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: XSS (CROSS-SITE SCRIPTING) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: NIEZAUFANY INPUT WYSWIETLONY JAKO HTML
         * ============================================================
         * XSS wystepuje, gdy aplikacja wyswietla dane pochodzace OD
         * UZYTKOWNIKA (parametr URL, tresc komentarza) bezposrednio w
         * HTML strony BEZ odpowiedniego "escapowania". Jesli uzytkownik
         * (atakujacy) wpisze zamiast zwyklego tekstu znacznik
         * `<script>...</script>`, a strona wstawi to DOSLOWNIE do HTML,
         * przegladarka OFIARY (kogos innego, kto zobaczy te strone/
         * komentarz) WYKONA ten skrypt - w KONTEKSCIE zaufanej strony
         * (z dostepem do JEJ ciastek, JEJ sesji - patrz Lesson04!).
         */
        System.out.println("XSS = niezaufany input uzytkownika wykonany jako kod JS w przegladarce INNEGO uzytkownika.");

        List<String> storedComments = new ArrayList<>();

        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        boolean[] escapingEnabled = {false}; // false = PODATNY serwer, true = BEZPIECZNY serwer
        server.createContext("/search", exchange -> handleSearch(exchange, escapingEnabled[0]));
        server.createContext("/comments", exchange -> handleComments(exchange, storedComments, escapingEnabled[0]));
        server.setExecutor(null);
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("\nLokalny serwer wystartowal na porcie " + port);

        try {
            demonstrateReflectedXssVulnerable(port, escapingEnabled);
            demonstrateReflectedXssFixed(port, escapingEnabled);
            demonstrateStoredXss(port, storedComments, escapingEnabled);
        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 🔹 3 GLOWNE RODZAJE XSS
         * ============================================================
         * - REFLECTED (odbity) - input z zadania (np. parametr
         *   `?q=...` w URL) jest NATYCHMIAST odbity w odpowiedzi HTML -
         *   atakujacy musi NAKLONIC ofiare do kliknieca SPECJALNIE
         *   spreparowanego linku.
         * - STORED (przechowywany) - zlosliwy input jest ZAPISANY (np.
         *   jako komentarz w bazie danych) i wyswietlany KAZDEMU, kto
         *   pozniej odwiedzi strone - GROZNIEJSZY, bo nie wymaga
         *   specjalnego linku, atakuje WSZYSTKICH odwiedzajacych.
         * - DOM-BASED - podatnosc ZYJE CALKOWICIE w JavaScript
         *   przegladarki (np. `element.innerHTML = location.hash`) -
         *   serwer moze nawet NIE WIDZIEC tego niebezpiecznego inputu.
         *
         * ============================================================
         * 🔹 OBRONA: ESCAPOWANIE (KODOWANIE KONTEKSTOWE)
         * ============================================================
         * PODSTAWOWA obrona to escapowanie znakow specjalnych HTML PRZED
         * wstawieniem inputu do strony: `<` -> `&lt;`, `>` -> `&gt;`,
         * `&` -> `&amp;`, `"` -> `&quot;`, `'` -> `&#x27;`. Po
         * escapowaniu, `<script>` staje sie TEKSTEM `&lt;script&gt;` -
         * przegladarka wyswietli go JAKO TEKST, a NIE wykona jako kod.
         * WAZNE: kontekst ma znaczenie - escapowanie dla HTML rozni sie
         * od escapowania dla atrybutu HTML, JavaScript, czy URL -
         * "kontekstowe kodowanie wyjscia" (context-aware output
         * encoding) to formalna nazwa tej zasady.
         */
        System.out.println("\nObrona: escapowanie znakow specjalnych HTML PRZED wstawieniem inputu do strony - kontekstowe kodowanie wyjscia.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - XSS = niezaufany input wykonany jako JS w przegladarce
         *   INNEGO uzytkownika.
         * - Reflected (odbity, wymaga specjalnego linku), Stored
         *   (przechowywany, atakuje wszystkich), DOM-based (cala
         *   podatnosc w JS klienta).
         * - Obrona: ESCAPOWANIE wyjscia (kontekstowe kodowanie) - NIGDY
         *   nie ufaj/nie wstawiaj surowego inputu do HTML.
         * - Nowoczesne frameworki frontendowe (React/Vue/Angular)
         *   escapuja DOMYSLNIE - podatnosc pojawia sie GLOWNIE gdy
         *   ktos jawnie to OMIJA (np. `dangerouslySetInnerHTML` w
         *   React).
         * - Dodatkowa warstwa ochrony: `Content-Security-Policy` (CSP) -
         *   naglowek ograniczajacy, SKAD moga pochodzic wykonywane
         *   skrypty - pogłębione w NASTEPNEJ lekcji (Lesson12:
         *   SecurityHeaders).
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void handleSearch(HttpExchange exchange, boolean escapingEnabled) throws java.io.IOException {
        String query = exchange.getRequestURI().getQuery();
        String searchTerm = "";
        if (query != null && query.startsWith("q=")) {
            searchTerm = java.net.URLDecoder.decode(query.substring(2), StandardCharsets.UTF_8);
        }

        String displayedTerm = escapingEnabled ? htmlEscape(searchTerm) : searchTerm;
        String html = "<html><body><p>Wyniki wyszukiwania dla: " + displayedTerm + "</p></body></html>";

        byte[] response = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    private static void handleComments(HttpExchange exchange, List<String> storedComments, boolean escapingEnabled) throws java.io.IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String comment = body.startsWith("comment=") ? java.net.URLDecoder.decode(body.substring(8), StandardCharsets.UTF_8) : "";
            storedComments.add(comment);
            exchange.sendResponseHeaders(201, -1);
            exchange.close();
            return;
        }

        StringBuilder html = new StringBuilder("<html><body><h3>Komentarze:</h3><ul>");
        for (String comment : storedComments) {
            String displayed = escapingEnabled ? htmlEscape(comment) : comment;
            html.append("<li>").append(displayed).append("</li>");
        }
        html.append("</ul></body></html>");

        byte[] response = html.toString().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    private static String htmlEscape(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    private static void demonstrateReflectedXssVulnerable(int port, boolean[] escapingEnabled) throws Exception {
        System.out.println("\n=== REFLECTED XSS - SERWER PODATNY (BEZ ESCAPOWANIA) ===");

        escapingEnabled[0] = false;
        String maliciousInput = "<script>alert('Wykradziono ciastko!')</script>";
        String encodedInput = URLEncoder.encode(maliciousInput, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/search?q=" + encodedInput)).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("Zapytanie: ?q=" + maliciousInput);
        System.out.println("Odpowiedz HTML (surowa): " + response.body());
        System.out.println("-> znacznik <script> trafil do HTML NIEZMIENIONY - przegladarka ofiary GO WYKONA!");
    }

    private static void demonstrateReflectedXssFixed(int port, boolean[] escapingEnabled) throws Exception {
        System.out.println("\n=== REFLECTED XSS - SERWER BEZPIECZNY (Z ESCAPOWANIEM) ===");

        escapingEnabled[0] = true;
        String maliciousInput = "<script>alert('Wykradziono ciastko!')</script>";
        String encodedInput = URLEncoder.encode(maliciousInput, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/search?q=" + encodedInput)).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        System.out.println("Zapytanie: ?q=" + maliciousInput);
        System.out.println("Odpowiedz HTML (zescapowana): " + response.body());
        System.out.println("-> znacznik <script> zostal zamieniony na TEKST &lt;script&gt; - przegladarka wyswietli GO JAKO TEKST, nie wykona.");
    }

    private static void demonstrateStoredXss(int port, List<String> storedComments, boolean[] escapingEnabled) throws Exception {
        System.out.println("\n=== STORED XSS - ZLOSLIWY KOMENTARZ ZAPISANY DLA WSZYSTKICH ODWIEDZAJACYCH ===");

        HttpClient client = HttpClient.newHttpClient();
        String baseUrl = "http://localhost:" + port;

        escapingEnabled[0] = false; // podatny serwer
        String maliciousComment = "Swietny artykul! <img src=x onerror=\"alert('Zaatakowany')\">";
        client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/comments"))
                        .POST(HttpRequest.BodyPublishers.ofString("comment=" + URLEncoder.encode(maliciousComment, StandardCharsets.UTF_8)))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .build(), HttpResponse.BodyHandlers.ofString());
        System.out.println("Atakujacy dodal komentarz: " + maliciousComment);

        HttpResponse<String> vulnerableView = client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/comments")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("KAZDY odwiedzajacy strone widzi (surowy HTML): " + vulnerableView.body());
        System.out.println("-> `onerror` na zepsutym obrazku WYKONA SIE u KAZDEGO, kto odwiedzi te strone - nie tylko u 1 ofiary!");

        escapingEnabled[0] = true; // ta sama lista komentarzy, ale serwer teraz escapuje przy WYSWIETLANIU
        HttpResponse<String> fixedView = client.send(HttpRequest.newBuilder(URI.create(baseUrl + "/comments")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("Po wlaczeniu escapowania PRZY WYSWIETLANIU (te same dane w 'bazie'!): " + fixedView.body());
        System.out.println("-> escapowanie MUSI nastapic PRZY WYSWIETLANIU, nie tylko przy zapisie - dane w 'bazie' pozostaja niezmienione.");
    }
}
