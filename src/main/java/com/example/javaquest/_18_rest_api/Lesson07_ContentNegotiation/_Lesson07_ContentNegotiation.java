package com.example.javaquest._18_rest_api.Lesson07_ContentNegotiation;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

public class _Lesson07_ContentNegotiation {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: NEGOCJACJA FORMATU (CONTENT NEGOTIATION) ===");

        /*
         * ============================================================
         * 📦 CZYM JEST CONTENT NEGOTIATION?
         * ============================================================
         * To mechanizm, w ktorym KLIENT i SERWER "uzgadniaja" najlepszy
         * wspolny format reprezentacji zasobu - zamiast serwera na sztywno
         * narzucajacego 1 format. Klient MOWI serwerowi, jakie formaty
         * rozumie (naglowek Accept, Lesson01/06), a serwer WYBIERA
         * najlepszy z tych, ktore SAM potrafi wygenerowac.
         *
         * To WLASNIE dlatego URI NIE powinno zawierac rozszerzenia pliku
         * (Lesson03: /users/42, NIE /users/42.json) - format wybiera
         * NAGLOWEK, nie sciezka.
         */
        System.out.println("Content Negotiation = klient i serwer UZGADNIAJA format przez naglowek Accept, NIE przez URI.");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.createContext("/report", _Lesson07_ContentNegotiation::handleReport);
        server.start();
        int port = server.getAddress().getPort();
        HttpClient client = HttpClient.newHttpClient();

        try {
            demonstrateSingleAcceptValue(client, port);
            demonstrateQualityValues(client, port);
            demonstrateWildcardAccept(client, port);
            demonstrateNotAcceptable(client, port);
            demonstrateVersionedMediaType();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Content Negotiation = klient deklaruje preferencje (Accept),
         *   serwer WYBIERA najlepsza wspolna opcje (NIE narzuca formatu
         *   przez rozszerzenie w URI).
         * - Naglowek Accept moze zawierac WIELE wartosci z wagami "q"
         *   (0.0-1.0) - wyzsza waga = wyzszy priorytet.
         * - "*\/*" oznacza "akceptuje KAZDY format" (backslash tu tylko po to,
         *   zeby nie zamknac tego komentarza blokowego znakiem '*' + '/').
         * - Brak wspolnego formatu -> 406 Not Acceptable.
         * - Odpowiedz ZAWSZE musi zawierac Content-Type mowiacy, JAKI
         *   format faktycznie zostal wybrany ("Vary: Accept" informuje
         *   posrednie cache, ze odpowiedz zalezy od tego naglowka).
         * - Wersjonowanie da sie tez wyrazic przez media type (np.
         *   "application/vnd.myapi.v2+json") - alternatywa dla
         *   wersjonowania w URI z Lesson14.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void handleReport(com.sun.net.httpserver.HttpExchange exchange) throws java.io.IOException {
        String acceptHeader = exchange.getRequestHeaders().getFirst("Accept");
        String chosen = negotiate(acceptHeader);

        exchange.getResponseHeaders().add("Vary", "Accept"); // informuje cache: odpowiedz ZALEZY od Accept

        if (chosen == null) {
            exchange.sendResponseHeaders(406, -1);
            exchange.close();
            return;
        }

        String body = switch (chosen) {
            case "application/json" -> "{\"raport\":\"sprzedaz\",\"suma\":1234.5}";
            case "text/csv" -> "raport,suma\nsprzedaz,1234.5";
            case "text/plain" -> "Raport: sprzedaz, suma: 1234.5";
            default -> "";
        };

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", chosen + "; charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    /**
     * Uproszczona negocjacja: serwer wspiera dokladnie 3 formaty. Parsuje
     * naglowek Accept (rozdzielony przecinkami, opcjonalne ";q=..."),
     * sortuje wedlug wagi malejaco i zwraca PIERWSZY wspierany format.
     */
    private static String negotiate(String acceptHeader) {
        List<String> supported = List.of("application/json", "text/csv", "text/plain");
        if (acceptHeader == null || acceptHeader.isBlank()) {
            return supported.get(0); // brak Accept -> serwer wybiera domyslny
        }

        record AcceptEntry(String mediaType, double quality) {
        }

        List<AcceptEntry> entries = Arrays.stream(acceptHeader.split(","))
                .map(String::trim)
                .map(part -> {
                    String[] pieces = part.split(";");
                    String mediaType = pieces[0].trim();
                    double quality = 1.0;
                    for (int i = 1; i < pieces.length; i++) {
                        String p = pieces[i].trim();
                        if (p.startsWith("q=")) {
                            quality = Double.parseDouble(p.substring(2));
                        }
                    }
                    return new AcceptEntry(mediaType, quality);
                })
                .sorted(Comparator.comparingDouble(AcceptEntry::quality).reversed())
                .toList();

        for (AcceptEntry entry : entries) {
            if (entry.mediaType().equals("*/*")) {
                return supported.get(0);
            }
            if (supported.contains(entry.mediaType())) {
                return entry.mediaType();
            }
        }
        return null; // zaden wspolny format
    }

    private static void demonstrateSingleAcceptValue(HttpClient client, int port) throws Exception {
        System.out.println("\n=== KLIENT DEKLARUJE 1 PREFEROWANY FORMAT ===");
        var response = send(client, port, "text/csv");
        System.out.println("Accept: text/csv -> Content-Type=" + response.headers().firstValue("content-type").orElse("?")
                + ", cialo=\"" + response.body() + "\"");
    }

    private static void demonstrateQualityValues(HttpClient client, int port) throws Exception {
        System.out.println("\n=== WARTOSCI JAKOSCI 'q' - PRIORYTET WSROD WIELU FORMATOW ===");
        /*
         * "Accept: text/plain;q=0.3, application/json;q=0.9, text/csv;q=0.5"
         * -> klient NAJBARDZIEJ preferuje JSON (0.9), potem CSV (0.5),
         * na koncu zwykly tekst (0.3) - serwer wybiera NAJWYZSZY wspolny.
         */
        var response = send(client, port, "text/plain;q=0.3, application/json;q=0.9, text/csv;q=0.5");
        System.out.println("Accept z wagami -> wybrano: " + response.headers().firstValue("content-type").orElse("?")
                + "  (JSON mial najwyzsza wage q=0.9)");
    }

    private static void demonstrateWildcardAccept(HttpClient client, int port) throws Exception {
        System.out.println("\n=== '*/*' - KLIENT AKCEPTUJE DOWOLNY FORMAT ===");
        var response = send(client, port, "*/*");
        System.out.println("Accept: */* -> serwer wybral SWOJ domyslny format: "
                + response.headers().firstValue("content-type").orElse("?"));
    }

    private static void demonstrateNotAcceptable(HttpClient client, int port) throws Exception {
        System.out.println("\n=== BRAK WSPOLNEGO FORMATU -> 406 NOT ACCEPTABLE ===");
        var response = send(client, port, "application/xml");
        System.out.println("Accept: application/xml (serwer NIE wspiera XML) -> status=" + response.statusCode()
                + " (406 Not Acceptable)");
    }

    private static void demonstrateVersionedMediaType() {
        System.out.println("\n=== ALTERNATYWA DLA WERSJONOWANIA W URI: 'VENDOR' MEDIA TYPE ===");
        System.out.println("Accept: application/vnd.myapi.v2+json  <- wersja API zakodowana W TYPIE MEDIA, nie w sciezce URI.");
        System.out.println("-> pelne porownanie strategii wersjonowania (URI vs naglowek vs media type) w Lesson14.");
    }

    private static HttpResponse<String> send(HttpClient client, int port, String acceptHeader) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/report"))
                .header("Accept", acceptHeader)
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
