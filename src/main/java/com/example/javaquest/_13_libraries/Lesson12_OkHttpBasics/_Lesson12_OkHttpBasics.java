package com.example.javaquest._13_libraries.Lesson12_OkHttpBasics;

import com.sun.net.httpserver.HttpServer;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class _Lesson12_OkHttpBasics {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 CZYM JEST OkHttp?
         * ============================================================
         * OkHttp to popularna, open-source'owa biblioteka HTTP dla Javy
         * i Androida stworzona przez firmę Square. Jest fundamentem, na
         * którym zbudowane jest wiele innych bibliotek - np. Retrofit
         * (typowany klient REST) korzysta z OkHttp "pod spodem" do
         * faktycznego wykonywania żądań sieciowych.
         *
         * Java od wersji 11 ma WBUDOWANY klient HTTP: java.net.http.HttpClient
         * (poznany szczegółowo w _06_networking, lekcje 8-10). Powstaje
         * więc naturalne pytanie: po co sięgać po zewnętrzną bibliotekę,
         * skoro JDK ma już własny klient?
         *
         * Powody, dla których OkHttp bywa wybierany w praktyce mimo
         * wbudowanego java.net.http:
         *   - connection pooling "out of the box" - reużycie połączeń
         *     TCP między żądaniami bez dodatkowej konfiguracji
         *   - transparentna obsługa GZIP (kompresja odpowiedzi) -
         *     automatyczna, bez ręcznego ustawiania nagłówków
         *   - bardzo wygodne API do przechwytywania żądań/odpowiedzi
         *     przez INTERCEPTORY (Lesson13) - logowanie, autoryzacja,
         *     retry, cache - wszystko w jednym, spójnym mechanizmie
         *   - szeroka adopcja w świecie Androida (przez wiele lat OkHttp
         *     było DOMYŚLNYM wyborem dla komunikacji sieciowej na Androidzie,
         *     zanim java.net.http stał się tam dostępny) oraz jako
         *     fundament Retrofit
         *   - dojrzałe, stabilne API, które nie zmieniało się drastycznie
         *     od lat - duża baza istniejącego kodu i dokumentacji
         *
         * W tej lekcji akcent kładziemy na to, co jest UNIKALNE dla
         * OkHttp - nie powtarzamy szczegółowo tematów już pokrytych
         * przez java.net.http w _06_networking.
         */

        /*
         * ============================================================
         * 🔹 LOKALNY SERWER TESTOWY
         * ============================================================
         * Tak jak w _06_networking, żeby demo nie zależało od internetu,
         * uruchamiamy własny, malutki serwer HTTP wbudowany w JDK
         * (com.sun.net.httpserver.HttpServer) na porcie 0 (system sam
         * wybiera wolny port).
         */

        System.out.println("=== Uruchamianie lokalnego serwera HTTP ===");
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        server.createContext("/hello", exchange -> {
            String responseBody = "Witaj z lokalnego serwera HTTP (OkHttp demo)!";
            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
            exchange.getResponseHeaders().add("X-Lekcja", "12-OkHttpBasics");
            exchange.sendResponseHeaders(200, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.createContext("/json", exchange -> {
            String json = "{\"kurs\":\"javaQuest\",\"rozdzial\":13,\"lekcja\":12}";
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.createContext("/echo-header", exchange -> {
            String custom = exchange.getRequestHeaders().getFirst("X-Custom");
            String responseBody = "Otrzymano naglowek X-Custom = " + custom;
            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.createContext("/not-found", exchange -> {
            byte[] bytes = "Brak zasobu".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(404, bytes.length);
            try (var out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("Serwer wystartowal na porcie: " + port);
        String baseUrl = "http://localhost:" + port;

        try {
            /*
             * ============================================================
             * 🔹 TWORZENIE OkHttpClient - RAZ, REUŻYWALNY
             * ============================================================
             * OkHttpClient to GŁÓWNY obiekt biblioteki - odpowiada za
             * pulę połączeń (connection pool), cache, konfigurację
             * timeoutów i interceptory. Jest ciężki w utworzeniu i
             * CAŁKOWICIE thread-safe.
             *
             * KLUCZOWA ZASADA: twórz JEDNĄ instancję OkHttpClient i
             * REUŻYWAJ jej dla WSZYSTKICH żądań w aplikacji (np. jako
             * pole statyczne/singleton), zamiast tworzyć nową instancję
             * przy każdym żądaniu.
             *
             * Dlaczego to takie ważne? Każda nowa instancja OkHttpClient
             * ma WŁASNĄ, osobną pulę połączeń (connection pool). Tworzenie
             * nowego klienta per request oznacza:
             *   - marnowanie zasobów - każdy request nawiązuje NOWE
             *     połączenie TCP (i handshake TLS dla https) od zera,
             *     zamiast reużyć już otwarte, "ciepłe" połączenie
             *   - brak korzyści z connection poolingu - główna zaleta
             *     OkHttp wymieniona wyżej przestaje działać
             *   - dodatkowe zużycie wątków/pamięci - OkHttpClient
             *     wewnętrznie zarządza pulą wątków (Dispatcher)
             *
             * Dokumentacja OkHttp wprost zaleca: "Create a single
             * OkHttpClient instance and reuse it for all of your HTTP calls
             * throughout your application."
             */

            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            System.out.println("\n=== OkHttpClient utworzony (jeden, reuzywalny) ===");

            /*
             * ============================================================
             * 🔹 BUDOWANIE ŻĄDANIA - Request.Builder
             * ============================================================
             * Request to niemutowalny (immutable) obiekt opisujący
             * żądanie HTTP: URL, metodę, nagłówki, ewentualne body.
             * Budujemy go przez wzorzec Builder - Request.Builder().
             *
             * Domyślna metoda to GET, jeśli nie wywołamy np. .post(body).
             */

            Request request = new Request.Builder()
                    .url(baseUrl + "/hello")
                    .build();

            System.out.println("\n=== Zbudowano Request ===");
            System.out.println("Metoda: " + request.method());
            System.out.println("URL:    " + request.url());

            /*
             * ============================================================
             * 🔍 WYKONANIE SYNCHRONICZNE - execute() w try-with-resources
             * ============================================================
             * client.newCall(request) tworzy obiekt Call - reprezentację
             * "gotowego do wykonania" żądania (Call można wykonać TYLKO
             * RAZ - ponowne wywołanie execute()/enqueue() na tym samym
             * Call rzuci IllegalStateException).
             *
             * execute() wykonuje żądanie SYNCHRONICZNIE - blokuje bieżący
             * wątek do momentu otrzymania odpowiedzi (albo zgłoszenia
             * IOException). Odpowiednik execute() z java.net.http to
             * HttpClient.send(...).
             *
             * Response IMPLEMENTUJE Closeable - reprezentuje otwarte
             * połączenie z niezamkniętym jeszcze strumieniem ciała
             * odpowiedzi. Dlatego ZAWSZE używamy try-with-resources -
             * inaczej połączenie (i zasoby leżące pod spodem) mogą
             * zostać niedomknięte, co uniemożliwia OkHttp reużycie
             * połączenia w puli.
             */

            System.out.println("\n=== Wykonanie synchroniczne (execute) ===");
            try (Response response = client.newCall(request).execute()) {

                /*
                 * ============================================================
                 * 🔹 SPRAWDZANIE STATUSU ODPOWIEDZI
                 * ============================================================
                 * response.isSuccessful() - wygodny skrót zwracający true
                 * dla kodów 200-299 (bez ręcznego porównywania zakresu).
                 * response.code() - surowy kod numeryczny (np. 200, 404).
                 * response.message() - fraza statusu (np. "OK").
                 */

                System.out.println("Kod odpowiedzi:       " + response.code());
                System.out.println("Komunikat statusu:    " + response.message());
                System.out.println("Czy sukces (2xx)?:    " + response.isSuccessful());

                /*
                 * ============================================================
                 * 🔹 ODCZYT CIAŁA ODPOWIEDZI - response.body().string()
                 * ============================================================
                 * response.body() zwraca obiekt ResponseBody - strumień
                 * powiązany z połączeniem sieciowym. Metoda string()
                 * odczytuje CAŁE ciało do pamięci jako String - wygodne
                 * dla małych odpowiedzi, ale NIEODPOWIEDNIE dla dużych
                 * plików (patrz Lesson14 - odczyt strumieniowy przez
                 * byteStream()).
                 *
                 * WAŻNE: string() (i ogólnie ResponseBody) można odczytać
                 * TYLKO RAZ - to jednorazowy strumień, nie da się go
                 * "przewinąć" i odczytać ponownie.
                 */

                String body = response.body().string();
                System.out.println("Cialo odpowiedzi:     " + body);
            }

            /*
             * ============================================================
             * 🔹 NAGŁÓWKI ŻĄDANIA I ODPOWIEDZI
             * ============================================================
             * .header(name, value) na Request.Builder USTAWIA nagłówek
             * (nadpisuje istniejący o tej samej nazwie). .addHeader(...)
             * DODAJE kolejną wartość, nie usuwając poprzednich (przydatne
             * dla nagłówków wielokrotnych, np. kilka "Accept").
             *
             * Po stronie odpowiedzi Headers to niemutowalna, wielomapowa
             * struktura - response.header(name) zwraca pierwszą wartość,
             * response.headers() zwraca wszystkie.
             */

            Request requestWithHeader = new Request.Builder()
                    .url(baseUrl + "/echo-header")
                    .header("X-Custom", "wartosc-z-javaquest")
                    .build();

            System.out.println("\n=== Request z wlasnym naglowkiem ===");
            try (Response response = client.newCall(requestWithHeader).execute()) {
                System.out.println("Odpowiedz serwera: " + response.body().string());
            }

            Request requestForHeaders = new Request.Builder()
                    .url(baseUrl + "/hello")
                    .build();

            System.out.println("\n=== Odczyt naglowkow odpowiedzi ===");
            try (Response response = client.newCall(requestForHeaders).execute()) {
                Headers headers = response.headers();
                System.out.println("Wszystkie naglowki:");
                for (String name : headers.names()) {
                    System.out.println("  " + name + " = " + headers.get(name));
                }
                System.out.println("Pojedynczy naglowek (X-Lekcja): " + response.header("X-Lekcja"));
            }

            /*
             * ============================================================
             * 🔍 ŻĄDANIE DO NIEISTNIEJĄCEGO ZASOBU (404)
             * ============================================================
             * isSuccessful() zwraca false dla kodów spoza 2xx - OkHttp
             * NIE rzuca wyjątku dla kodów błędu HTTP (404, 500 itd.) -
             * to wciąż POPRAWNA odpowiedź HTTP, tylko z kodem błędu.
             * IOException jest rzucany tylko przy PROBLEMACH SIECIOWYCH
             * (brak połączenia, timeout, zerwane połączenie).
             */

            Request notFoundRequest = new Request.Builder()
                    .url(baseUrl + "/not-found")
                    .build();

            System.out.println("\n=== Zadanie do nieistniejacego zasobu ===");
            try (Response response = client.newCall(notFoundRequest).execute()) {
                System.out.println("Kod: " + response.code() + ", isSuccessful: " + response.isSuccessful());
            }

            /*
             * ============================================================
             * 🔍 PORÓWNANIE: OkHttp vs java.net.http.HttpClient
             * ============================================================
             * Ten sam scenariusz (GET + odczyt body) w obu API:
             *
             * OkHttp:
             *   OkHttpClient client = new OkHttpClient();
             *   Request request = new Request.Builder().url(url).build();
             *   try (Response response = client.newCall(request).execute()) {
             *       String body = response.body().string();
             *   }
             *
             * java.net.http.HttpClient (JDK, patrz _06_networking):
             *   HttpClient client = HttpClient.newHttpClient();
             *   HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
             *   HttpResponse<String> response =
             *       client.send(request, HttpResponse.BodyHandlers.ofString());
             *   String body = response.body();
             *
             * Oba API są podobnie ergonomiczne dla PROSTEGO GET-a. Różnice
             * ujawniają się przy bardziej zaawansowanych scenariuszach:
             * interceptory (Lesson13), automatyczny GZIP, wygodniejsze
             * multipart (Lesson14) - to obszary, w których OkHttp
             * historycznie oferował więcej "gotowca".
             */

            System.out.println("\n=== Porownanie OkHttp vs java.net.http - patrz komentarz w kodzie ===");

        } finally {
            server.stop(0);
            System.out.println("\nSerwer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - OkHttp to popularny klient HTTP od Square, fundament m.in.
         *   Retrofit - wybierany w praktyce za connection pooling,
         *   transparentny GZIP, wygodne interceptory i dojrzałość
         *   ekosystemu (zwłaszcza Android)
         * - OkHttpClient twórz RAZ i REUŻYWAJ (thread-safe, własna pula
         *   połączeń) - NIGDY nowej instancji per request
         * - Request.Builder().url(...).build() - budowa żądania
         * - client.newCall(request).execute() - wykonanie SYNCHRONICZNE,
         *   Call można wykonać tylko RAZ
         * - Response implementuje Closeable - ZAWSZE try-with-resources
         * - response.body().string() - odczyt całego ciała (JEDNORAZOWY)
         * - response.isSuccessful() - true dla 2xx, response.code() -
         *   surowy kod, kody błędu (404/500) NIE rzucają wyjątku
         * - .header(name, value) na żądaniu, response.header(name) /
         *   response.headers() po stronie odpowiedzi
         * - Async wywołania i interceptory - Lesson13
         * - Upload/download i timeouty - Lesson14
         */

        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }
}
