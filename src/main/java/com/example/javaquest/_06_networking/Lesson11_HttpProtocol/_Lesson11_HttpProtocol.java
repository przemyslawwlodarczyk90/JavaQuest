package com.example.javaquest._06_networking.Lesson11_HttpProtocol;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class _Lesson11_HttpProtocol {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CZYM JEST HTTP?
         * ============================================================
         * HTTP (HyperText Transfer Protocol) to protokół warstwy
         * aplikacji, na którym opiera się cały "widzialny" internet
         * (strony WWW, REST API, itd.). Działa na zasadzie
         * ŻĄDANIE -> ODPOWIEDŹ (request -> response):
         *
         *   KLIENT (np. przeglądarka, HttpClient) --żądanie-->  SERWER
         *   KLIENT                                <--odpowiedź-- SERWER
         *
         * Pod spodem HTTP korzysta z TCP (patrz Lesson03_Socket) –
         * to zwykły tekst (w HTTP/1.1) wysyłany przez gniazdo TCP,
         * tylko o ściśle ustalonym formacie.
         *
         * 🔹 HTTP JEST BEZSTANOWY (stateless)!
         * Każde żądanie jest NIEZALEŻNE od poprzednich – serwer sam
         * z siebie "nie pamięta" wcześniejszych żądań tego samego
         * klienta. Do tego, jak mimo to budować logowanie/koszyk
         * zakupowy itp., wrócimy niżej (Cookies i Sesje).
         */

        /*
         * ============================================================
         * 🔹 STRUKTURA ŻĄDANIA (REQUEST)
         * ============================================================
         * Surowe żądanie HTTP/1.1 wygląda mniej więcej tak (tekst!):
         *
         *   GET /users/42 HTTP/1.1          <- LINIA ŻĄDANIA
         *   Host: example.com               <- NAGŁÓWKI (headers)
         *   User-Agent: Mozilla/5.0
         *   Accept: application/json
         *                                   <- pusta linia = koniec nagłówków
         *   (opcjonalne ciało żądania)      <- BODY (np. przy POST)
         *
         * LINIA ŻĄDANIA = METODA + ŚCIEŻKA + WERSJA PROTOKOŁU:
         *   GET        - metoda (co chcemy zrobić)
         *   /users/42  - ścieżka do zasobu na serwerze
         *   HTTP/1.1   - wersja protokołu
         */

        String rawRequestExample = """
                GET /users/42 HTTP/1.1
                Host: example.com
                User-Agent: JavaQuest-Client/1.0
                Accept: application/json
                """;

        System.out.println("=== PRZYKŁADOWE SUROWE ŻĄDANIE HTTP ===");
        System.out.println(rawRequestExample);

        /*
         * ============================================================
         * 🔹 STRUKTURA ODPOWIEDZI (RESPONSE)
         * ============================================================
         *   HTTP/1.1 200 OK                 <- LINIA STATUSU
         *   Content-Type: application/json  <- NAGŁÓWKI
         *   Content-Length: 27
         *                                   <- pusta linia = koniec nagłówków
         *   {"id":42,"name":"Anna"}         <- BODY (ciało odpowiedzi)
         *
         * LINIA STATUSU = WERSJA + KOD STATUSU + FRAZA:
         *   HTTP/1.1  - wersja protokołu
         *   200       - kod statusu (liczbowy)
         *   OK        - fraza opisowa (czysto informacyjna dla człowieka)
         */

        String rawResponseExample = """
                HTTP/1.1 200 OK
                Content-Type: application/json
                Content-Length: 27

                {"id":42,"name":"Anna"}
                """;

        System.out.println("=== PRZYKŁADOWA SUROWA ODPOWIEDŹ HTTP ===");
        System.out.println(rawResponseExample);

        /*
         * ============================================================
         * 🔍 NAJWAŻNIEJSZE METODY HTTP
         * ============================================================
         * GET     - pobranie zasobu, BEZ efektów ubocznych (idempotentna,
         *           bezpieczna), zwykle bez ciała żądania
         * POST    - utworzenie nowego zasobu / wykonanie akcji,
         *           NIE jest idempotentna (2x POST = 2 nowe zasoby)
         * PUT     - podmiana CAŁEGO zasobu pod danym adresem,
         *           IDEMPOTENTNA (2x ten sam PUT = ten sam efekt co 1x)
         * PATCH   - CZĘŚCIOWA aktualizacja zasobu (tylko wskazane pola)
         * DELETE  - usunięcie zasobu, idempotentna
         * HEAD    - jak GET, ale serwer zwraca TYLKO nagłówki, bez ciała
         *           (przydatne np. do sprawdzenia rozmiaru pliku bez
         *           pobierania go)
         * OPTIONS - pytanie "jakie metody/opcje są dostępne dla tego
         *           zasobu?" (używane też przy CORS "preflight")
         *
         * IDEMPOTENTNA = wielokrotne powtórzenie tego samego żądania
         * daje TAKI SAM efekt końcowy, jak wykonanie go raz.
         */

        System.out.println("=== METODY HTTP ===");
        System.out.println("GET     - pobierz zasób (bez efektów ubocznych)");
        System.out.println("POST    - utwórz nowy zasób / wykonaj akcję");
        System.out.println("PUT     - podmień cały zasób (idempotentna)");
        System.out.println("PATCH   - częściowa aktualizacja zasobu");
        System.out.println("DELETE  - usuń zasób (idempotentna)");
        System.out.println("HEAD    - jak GET, ale tylko nagłówki, bez ciała");
        System.out.println("OPTIONS - jakie metody są dostępne dla zasobu");

        /*
         * ============================================================
         * 🔹 KODY STATUSU – RODZINY 1xx-5xx
         * ============================================================
         * Pierwsza cyfra kodu statusu określa JEGO KATEGORIĘ:
         *
         * 1xx - INFORMACYJNE   - żądanie odebrane, przetwarzanie trwa
         *                        (rzadko widoczne "z zewnątrz")
         * 2xx - SUKCES          - żądanie zostało poprawnie obsłużone
         *   200 OK               - standardowy sukces
         *   201 Created           - utworzono nowy zasób (typowo po POST)
         * 3xx - PRZEKIEROWANIE - trzeba wykonać dodatkowy krok
         *   301 Moved Permanently - zasób TRWALE przeniesiony pod inny adres
         *   302 Found             - zasób TYMCZASOWO pod innym adresem
         * 4xx - BŁĄD KLIENTA   - to KLIENT zrobił coś źle
         *   400 Bad Request       - źle sformułowane żądanie (np. zły JSON)
         *   401 Unauthorized      - brak / błędne uwierzytelnienie
         *   403 Forbidden         - uwierzytelniono, ale BRAK UPRAWNIEŃ
         *   404 Not Found         - zasób nie istnieje pod tym adresem
         * 5xx - BŁĄD SERWERA   - to SERWER zawiódł (mimo poprawnego żądania)
         *   500 Internal Server Error - ogólny, nieoczekiwany błąd serwera
         *
         * 🔎 401 vs 403 - częsty punkt pomyłek:
         * 401 = "nie wiem kim jesteś" (brak/zła autentykacja)
         * 403 = "wiem kim jesteś, ale nie masz do tego dostępu"
         */

        System.out.println("\n=== RODZINY KODÓW STATUSU ===");
        System.out.println("1xx - informacyjne     (np. 100 Continue)");
        System.out.println("2xx - sukces            200 OK, 201 Created");
        System.out.println("3xx - przekierowanie    301 Moved Permanently, 302 Found");
        System.out.println("4xx - błąd klienta      400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found");
        System.out.println("5xx - błąd serwera      500 Internal Server Error");

        /*
         * ============================================================
         * 🔹 NAJWAŻNIEJSZE NAGŁÓWKI (HEADERS)
         * ============================================================
         * Nagłówki to pary klucz: wartość, niosące METADANE o żądaniu
         * lub odpowiedzi (nie samą "treść" zasobu):
         *
         * Content-Type   - jaki format ma ciało (np. application/json,
         *                  text/html, text/plain; charset=UTF-8)
         * Content-Length - długość ciała w bajtach
         * Host           - do jakiego hosta kierowane jest żądanie
         *                  (WYMAGANY w HTTP/1.1 - jeden serwer może
         *                  obsługiwać wiele domen na tym samym porcie)
         * User-Agent     - identyfikacja klienta (przeglądarka, biblioteka...)
         * Authorization  - dane uwierzytelniające (np. "Bearer <token>")
         * Set-Cookie     - serwer PROSI klienta o zapamiętanie ciasteczka
         *                  (nagłówek ODPOWIEDZI)
         * Cookie         - klient ODSYŁA zapamiętane ciasteczka serwerowi
         *                  (nagłówek ŻĄDANIA)
         */

        System.out.println("\n=== NAJWAŻNIEJSZE NAGŁÓWKI ===");
        System.out.println("Content-Type    - format ciała (np. application/json)");
        System.out.println("Content-Length  - długość ciała w bajtach");
        System.out.println("Host            - docelowy host (wymagany w HTTP/1.1)");
        System.out.println("User-Agent      - identyfikacja klienta");
        System.out.println("Authorization   - dane uwierzytelniające");
        System.out.println("Set-Cookie      - serwer prosi o zapamiętanie ciasteczka (w odpowiedzi)");
        System.out.println("Cookie          - klient odsyła ciasteczko (w żądaniu)");

        /*
         * ============================================================
         * 📋 COOKIES I SESJE – JAK "OSZUKAĆ" BEZSTANOWOŚĆ HTTP
         * ============================================================
         * Skoro HTTP nie pamięta niczego między żądaniami, jak działa
         * np. logowanie na stronie? Odpowiedź: COOKIES (ciasteczka).
         *
         * 1) Klient loguje się -> serwer odsyła odpowiedź z nagłówkiem
         *    Set-Cookie: SESSIONID=abc123
         * 2) Przeglądarka ZAPAMIĘTUJE tę wartość.
         * 3) Przy KAŻDYM kolejnym żądaniu do tego serwera przeglądarka
         *    automatycznie dołącza nagłówek: Cookie: SESSIONID=abc123
         * 4) Serwer po stronie backendu trzyma mapę
         *    SESSIONID -> dane sesji (np. kim jest zalogowany użytkownik)
         *    - to jest SESJA (session). Samo cookie to tylko "klucz",
         *    prawdziwe dane siedzą po stronie serwera (lub w tokenie,
         *    przy podejściach bezstanowych typu JWT).
         *
         * Dzięki temu POJEDYNCZE, z natury bezstanowe żądania HTTP
         * SPRAWIAJĄ WRAŻENIE ciągłej, "stanowej" rozmowy z serwerem.
         * Ten temat wrócimy szerzej rozwiniemy przy okazji rozdziału
         * o Servletach (obsługa HttpSession po stronie serwera Java).
         */

        System.out.println("\n=== COOKIES I SESJE (skrót) ===");
        System.out.println("1. Logowanie -> serwer: Set-Cookie: SESSIONID=abc123");
        System.out.println("2. Przeglądarka zapamiętuje ciasteczko");
        System.out.println("3. Kolejne żądania -> klient: Cookie: SESSIONID=abc123");
        System.out.println("4. Serwer kojarzy SESSIONID z danymi sesji (kto jest zalogowany)");

        /*
         * ============================================================
         * 🔍 PRAWDZIWE NAGŁÓWKI "NA ŻYWO" - LOKALNY SERWER HTTP
         * ============================================================
         * Żeby zobaczyć, że powyższa teoria to nie abstrakcja, tylko
         * realny tekst lecący przez sieć, uruchomimy lokalny serwer
         * (com.sun.net.httpserver.HttpServer, patrz Lesson09) i wypiszemy
         * DOKŁADNIE te nagłówki, które faktycznie dotarły do serwera,
         * gdy wyślemy żądanie klientem HttpClient (patrz Lesson10).
         */

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/echo", exchange -> {
            System.out.println("\n=== NAGŁÓWKI ODEBRANE PRZEZ SERWER ===");
            System.out.println("Metoda: " + exchange.getRequestMethod());
            System.out.println("Ścieżka: " + exchange.getRequestURI());
            exchange.getRequestHeaders().forEach((name, values) ->
                    System.out.println(name + ": " + values));

            byte[] body = "OK".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(200, body.length);
            try (var out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
        int port = server.getAddress().getPort();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/echo"))
                    .header("User-Agent", "JavaQuest-Client/1.0")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n=== ODPOWIEDŹ WIDZIANA PRZEZ KLIENTA ===");
            System.out.println("Kod statusu: " + response.statusCode());
            System.out.println("Ciało odpowiedzi: " + response.body());
        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - HTTP działa na zasadzie żądanie -> odpowiedź, jest BEZSTANOWY
         * - Żądanie: LINIA ŻĄDANIA (metoda + ścieżka + wersja) + nagłówki
         *   + opcjonalne ciało
         * - Odpowiedź: LINIA STATUSU (wersja + kod + fraza) + nagłówki
         *   + opcjonalne ciało
         * - Metody: GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS
         * - Kody statusu: 1xx info, 2xx sukces, 3xx przekierowanie,
         *   4xx błąd klienta, 5xx błąd serwera
         * - Ważne nagłówki: Content-Type, Content-Length, Host,
         *   User-Agent, Authorization, Set-Cookie/Cookie
         * - Cookies + sesje po stronie serwera "dokładają" stan do
         *   z natury bezstanowego protokołu HTTP (rozwinięcie: Servlety)
         */
    }
}
