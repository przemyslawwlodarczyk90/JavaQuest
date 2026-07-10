package com.example.javaquest._18_rest_api.Lesson01_HttpDeepDive;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class _Lesson01_HttpDeepDive {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 1: HTTP OD PODSZEWKI ===");

        /*
         * ============================================================
         * 📦 PO CO ZACZYNAMY NOWY ROZDZIAL OD HTTP, SKORO BYL JUZ
         * `_06_networking` I `_07_servlets`?
         * ============================================================
         * - `_06_networking` uczylo HTTP z perspektywy NISKOPOZIOMOWEGO
         *   API klienta (Socket, URLConnection, HttpURLConnection) -
         *   "jak POLACZYC SIE z serwerem".
         * - `_07_servlets` uczylo HTTP z perspektywy KONTENERA serwletow
         *   (HttpServletRequest/Response) - "jak Java Web ODBIERA request".
         * - Ten rozdzial (`_18_rest_api`) uczy HTTP z perspektywy
         *   PROJEKTOWANIA API - "jak SWIADOMIE zaprojektowac kontrakt
         *   miedzy klientem a serwerem". Zeby to zrobic dobrze, trzeba
         *   NAJPIERW rozumiec sam protokol GLEBIEJ, niz wymagalo tego
         *   samo "wyslij i odbierz" - stad ta lekcja.
         */
        System.out.println("HTTP juz widziales - ale tu poznajemy go od strony PROJEKTOWANIA kontraktu API, nie samego polaczenia.");

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.setExecutor(Executors.newCachedThreadPool());
        int port = startEchoServer(server);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        try {
            demonstrateRequestAnatomy(client, port);
            demonstrateResponseAnatomy(client, port);
            demonstrateStatelessness(client, port);
            demonstrateHeaders(client, port);
            demonstrateContentLengthVsChunked(client, port);
            demonstrateProtocolVersionNegotiation(client, port);
            demonstrateUriAnatomy();
        } finally {
            server.stop(0);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HTTP request = linia startowa (metoda + sciezka + wersja) +
         *   naglowki + opcjonalne cialo. Response = linia statusu +
         *   naglowki + opcjonalne cialo - symetryczna struktura.
         * - HTTP jest BEZSTANOWY (stateless) - kazdy request jest
         *   niezalezny, serwer domyslnie NIC nie pamieta miedzy nimi
         *   (stan trzeba dobudowac recznie - cookies/sesje/tokeny,
         *   `_07_servlets/Lesson10-11`, `_19_security_basics/Lesson04-05`).
         * - Content-Length vs Transfer-Encoding: chunked - dwa sposoby,
         *   zeby odbiorca wiedzial, GDZIE konczy sie cialo.
         * - HttpClient domyslnie PROBUJE HTTP/2, ale spada do HTTP/1.1,
         *   jesli serwer go nie wspiera - negocjacja wersji protokolu.
         * - URI ma scisla anatomie: schemat, host, port, sciezka, query,
         *   fragment - fundament pod Lesson03 (zasoby/endpointy) i
         *   Lesson09 (path variables/query params).
         * - Kolejna lekcja (Lesson02): jak REST wykorzystuje TE WLASNIE
         *   cechy HTTP (metody, statusy, bezstanowosc) jako swoj styl
         *   architektoniczny.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    /**
     * Prosty serwer "echo" - odsyla informacje o tym, co faktycznie
     * odebral (metoda, sciezka, naglowki, dlugosc ciala) - pozwala nam
     * NAPRAWDE zobaczyc, co ladzie po drucie, zamiast o tym tylko czytac.
     */
    private static int startEchoServer(HttpServer server) {
        server.createContext("/echo", exchange -> {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            StringBuilder info = new StringBuilder();
            info.append("METHOD=").append(exchange.getRequestMethod()).append('\n');
            info.append("PATH=").append(exchange.getRequestURI().getPath()).append('\n');
            info.append("QUERY=").append(exchange.getRequestURI().getQuery()).append('\n');
            exchange.getRequestHeaders().forEach((name, values) ->
                    info.append("HEADER ").append(name).append('=').append(values).append('\n'));
            info.append("BODY_LENGTH=").append(requestBody.length()).append('\n');

            byte[] responseBytes = info.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
            exchange.getResponseHeaders().add("X-Demo-Header", "javaquest-lesson01");
            exchange.sendResponseHeaders(200, responseBytes.length);
            exchange.getResponseBody().write(responseBytes);
            exchange.close();
        });

        server.createContext("/chunked", exchange -> {
            // sendResponseHeaders(200, 0) = "nie znam z gory dlugosci ciala"
            // -> HttpServer sam przelacza sie na Transfer-Encoding: chunked
            exchange.sendResponseHeaders(200, 0);
            byte[] part1 = "Pierwsza czesc odpowiedzi... ".getBytes(StandardCharsets.UTF_8);
            byte[] part2 = "druga czesc, wyslana OSOBNO.".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseBody().write(part1);
            exchange.getResponseBody().flush();
            exchange.getResponseBody().write(part2);
            exchange.close();
        });

        server.start();
        return server.getAddress().getPort();
    }

    private static void demonstrateRequestAnatomy(HttpClient client, int port) throws Exception {
        System.out.println("\n=== ANATOMIA REQUESTU: LINIA STARTOWA + NAGLOWKI + CIALO ===");

        /*
         * ============================================================
         * 🔹 3 CZESCI KAZDEGO REQUESTU HTTP
         * ============================================================
         * 1. LINIA STARTOWA (request line): METODA SCIEZKA WERSJA
         *    np. "POST /echo?source=lesson01 HTTP/1.1"
         * 2. NAGLOWKI (headers): pary klucz-wartosc z metadanymi
         *    (Content-Type, Content-Length, Host, User-Agent, ...)
         * 3. CIALO (body): opcjonalne, oddzielone od naglowkow PUSTA
         *    linia - dane wlasciwe (np. JSON) - GET typowo go NIE ma,
         *    POST/PUT/PATCH typowo TAK.
         */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/echo?source=lesson01"))
                .header("X-Custom-Header", "wartosc-testowa")
                .POST(HttpRequest.BodyPublishers.ofString("{\"kurs\":\"javaQuest\"}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Serwer WIDZIAL nasz request tak:");
        System.out.println(response.body().indent(2));
    }

    private static void demonstrateResponseAnatomy(HttpClient client, int port) throws Exception {
        System.out.println("=== ANATOMIA ODPOWIEDZI: LINIA STATUSU + NAGLOWKI + CIALO ===");

        /*
         * ============================================================
         * 🔍 ODPOWIEDZ MA DOKLADNIE TA SAMA STRUKTURE, TYLKO ZAMIAST
         * "linii startowej" ma "linie statusu": WERSJA KOD_STATUSU FRAZA
         * np. "HTTP/1.1 200 OK" - kody statusu poznamy dokladnie w
         * Lesson05, na razie tylko widzimy, GDZIE zyja w odpowiedzi.
         */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/echo"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("statusCode() = " + response.statusCode());
        System.out.println("version()    = " + response.version());
        System.out.println("naglowki odpowiedzi:");
        response.headers().map().forEach((name, values) -> System.out.println("  " + name + " = " + values));
    }

    private static void demonstrateStatelessness(HttpClient client, int port) throws Exception {
        System.out.println("\n=== HTTP JEST BEZSTANOWY (STATELESS) ===");

        /*
         * ============================================================
         * 📦 CO TO ZNACZY "BEZSTANOWY"?
         * ============================================================
         * Kazdy request HTTP jest przetwarzany NIEZALEZNIE od
         * poprzednich - serwer (sam protokol) NIE PAMIETA "kim jest ten
         * klient" ani "co robil chwile temu", chyba ze APLIKACJA sama
         * dobuduje mechanizm pamieci (cookie z ID sesji, token w
         * naglowku Authorization - `_07_servlets/Lesson10-11`,
         * `_19_security_basics/Lesson04-05`).
         * To WLASNIE bezstanowosc jest jednym z fundamentow REST
         * (Lesson02) - kazdy request musi NIESC ze soba WSZYSTKO, co
         * potrzebne do jego obslugi.
         */
        HttpRequest first = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/echo"))
                .header("X-Request-Number", "1")
                .GET().build();
        HttpRequest second = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/echo"))
                .GET().build(); // BRAK X-Request-Number - nastepny request "nie wie" o poprzednim

        client.send(first, HttpResponse.BodyHandlers.discarding());
        HttpResponse<String> secondResponse = client.send(second, HttpResponse.BodyHandlers.ofString());

        boolean sawFirstHeaderAgain = secondResponse.body().contains("X-Request-Number");
        System.out.println("Czy drugi request 'pamieta' naglowek z pierwszego? " + sawFirstHeaderAgain
                + " (musi byc false - to WLASNIE bezstanowosc)");
    }

    private static void demonstrateHeaders(HttpClient client, int port) throws Exception {
        System.out.println("\n=== NAGLOWKI: METADANE, NIE DANE ===");

        /*
         * ============================================================
         * 🔹 NAJWAZNIEJSZE NAGLOWKI REQUESTU (do zapamietania)
         * ============================================================
         * - Host          - do KTOREGO serwera (wymagany od HTTP/1.1)
         * - User-Agent    - "kto pyta" (przegladarka/biblioteka klienta)
         * - Accept        - jakie formaty odpowiedzi klient rozumie
         *                   (dokladnie w Lesson07 - Content Negotiation)
         * - Content-Type  - jaki format ma CIALO requestu (np. application/json)
         * - Content-Length- ile bajtow ma cialo
         * - Authorization - dane uwierzytelniajace (`_19_security_basics`)
         *
         * 🔍 NAJWAZNIEJSZE NAGLOWKI ODPOWIEDZI
         * - Content-Type, Content-Length - jak wyzej, ale dla odpowiedzi
         * - Date          - kiedy odpowiedz zostala wygenerowana
         * - Server        - informacja o oprogramowaniu serwera
         * - Set-Cookie    - polecenie dla klienta, zeby zapamietal cookie
         */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/echo"))
                .header("Accept", "application/json")
                .header("Authorization", "Bearer demo-token-123")
                .GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Serwer odebral m.in.:");
        response.body().lines()
                .filter(line -> line.startsWith("HEADER accept") || line.startsWith("HEADER authorization")
                        || line.startsWith("HEADER host"))
                .forEach(line -> System.out.println("  " + line));
    }

    private static void demonstrateContentLengthVsChunked(HttpClient client, int port) throws Exception {
        System.out.println("\n=== JAK ODBIORCA WIE, GDZIE KONCZY SIE CIALO? ===");

        /*
         * ============================================================
         * 📦 DWA SPOSOBY WYZNACZANIA KONCA CIALA W HTTP/1.1
         * ============================================================
         * 1. Content-Length: N - "cialo ma DOKLADNIE N bajtow" - wymaga
         *    znajomosci calkowitego rozmiaru Z GORY (dobre dla malych,
         *    statycznych odpowiedzi).
         * 2. Transfer-Encoding: chunked - cialo wysylane w KAWALKACH o
         *    nieznanym z gory calkowitym rozmiarze, zakonczone kawalkiem
         *    zerowej dlugosci - dobre np. dla strumieniowania danych
         *    generowanych "w locie".
         * Ponizej: /echo uzywa Content-Length (znamy dlugosc od razu),
         * /chunked uzywa Transfer-Encoding: chunked (piszemy w 2 ratach).
         */
        HttpRequest fixedLength = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/echo"))
                .GET().build();
        HttpResponse<String> fixedResponse = client.send(fixedLength, HttpResponse.BodyHandlers.ofString());
        System.out.println("/echo    -> " + fixedResponse.headers().firstValue("content-length")
                .map(v -> "Content-Length: " + v).orElse("(brak Content-Length)"));

        HttpRequest chunked = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/chunked"))
                .GET().build();
        HttpResponse<String> chunkedResponse = client.send(chunked, HttpResponse.BodyHandlers.ofString());
        System.out.println("/chunked -> " + chunkedResponse.headers().firstValue("transfer-encoding")
                .map(v -> "Transfer-Encoding: " + v).orElse("(brak Transfer-Encoding)"));
        System.out.println("   cialo mimo to dotarlo w calosci: \"" + chunkedResponse.body() + "\"");
    }

    private static void demonstrateProtocolVersionNegotiation(HttpClient client, int port) throws Exception {
        System.out.println("\n=== WERSJE HTTP: 1.1 vs 2 vs 3 (KONCEPCYJNIE) ===");

        /*
         * ============================================================
         * 🔍 HTTP/1.1 (1997) - tekstowy, 1 request na polaczenie na raz
         *    (bez pipeliningu w praktyce), ale z Connection: keep-alive
         *    mozna PONOWNIE UZYC tego samego polaczenia TCP dla kolejnych
         *    requestow (unikamy kosztu nowego handshake'u za kazdym razem).
         * 🔍 HTTP/2 (2015) - BINARNY, MULTIPLEKSOWANY (wiele requestow
         *    naraz na 1 polaczeniu, bez blokowania sie nawzajem - "head-of
         *    -line blocking" na poziomie HTTP rozwiazany), kompresja
         *    naglowkow (HPACK) - mniej danych na drucie.
         * 🔍 HTTP/3 (2022) - jak HTTP/2, ale na QUIC/UDP zamiast TCP -
         *    rozwiazuje head-of-line blocking na poziomie TRANSPORTU
         *    (1 zgubiony pakiet nie blokuje WSZYSTKICH strumieni, jak
         *    moze sie zdarzyc w TCP).
         *
         * `java.net.http.HttpClient` domyslnie PROBUJE HTTP/2, ale jesli
         * serwer go nie oferuje (nasz HttpServer mowi TYLKO HTTP/1.1),
         * client automatycznie SPADA (fallback) do HTTP/1.1 - ponizej
         * widzimy to NAPRAWDE, nie tylko czytamy o tym.
         */
        System.out.println("client.version() zadeklarowana preferencja = " + client.version());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/echo"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.version() faktycznie wynegocjowana = " + response.version()
                + " (nasz HttpServer wspiera tylko HTTP/1.1, wiec client spadl do niej)");
    }

    private static void demonstrateUriAnatomy() {
        System.out.println("\n=== ANATOMIA URI: FUNDAMENT POD ZASOBY I ENDPOINTY ===");

        /*
         * ============================================================
         * 📌 CZESCI SKLADOWE URI (fundament pod Lesson03/Lesson09)
         * ============================================================
         *   https://api.example.com:8443/users/42?active=true#profile
         *   \___/   \_____________/\___/\________/\_________/\______/
         *  schemat       host      port  sciezka     query    fragment
         */
        URI uri = URI.create("https://api.example.com:8443/users/42?active=true#profile");
        System.out.println("scheme()   = " + uri.getScheme());
        System.out.println("host()     = " + uri.getHost());
        System.out.println("port()     = " + uri.getPort());
        System.out.println("path()     = " + uri.getPath());
        System.out.println("query()    = " + uri.getQuery());
        System.out.println("fragment() = " + uri.getFragment());
        System.out.println("-> fragment NIGDY nie jest wysylany do serwera (obslugiwany TYLKO po stronie klienta).");

        List<String> pathSegments = List.of(uri.getPath().split("/"));
        Map<String, String> exampleQueryParsed = Map.of("active", "true");
        System.out.println("segmenty sciezki: " + pathSegments + ", przykladowy sparsowany query: " + exampleQueryParsed);
    }
}
