package com.example.javaquest._13_libraries.Lesson14_OkHttpStreamingAndTesting;

import com.sun.net.httpserver.HttpServer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;
import okio.BufferedSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class _Lesson14_OkHttpStreamingAndTesting {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 TRZECIA (I OSTATNIA) LEKCJA O OkHttp
         * ============================================================
         * Lesson12 pokazał podstawy (OkHttpClient, Request, execute(),
         * odczyt całego ciała przez response.body().string()). Lesson13
         * dodał wywołania asynchroniczne (enqueue) i interceptory. Ta
         * lekcja kończy temat trzema praktycznymi zagadnieniami, które
         * pojawiają się w REALNYCH aplikacjach:
         *
         *   1. Odczyt strumieniowy dużych odpowiedzi (bez ładowania
         *      całości do pamięci - problem z response.body().string()
         *      dla dużych plików).
         *   2. Upload plików przez multipart/form-data (MultipartBody).
         *   3. Własne timeouty (connect/read/write) i testowanie klientów
         *      OkHttp BEZ prawdziwej sieci przy pomocy MockWebServer.
         *
         * Nie powtarzamy podstaw z Lesson12/13 - zakładamy, że budowa
         * Request, wykonanie execute() i try-with-resources na Response
         * są już znane.
         */

        System.out.println("=== Uruchamianie lokalnego serwera HTTP (produkcyjna czesc lekcji) ===");
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        // Duzy "plik" generowany raz, w pamieci - symuluje pobieranie
        // sporej odpowiedzi (np. raportu, obrazka, archiwum).
        byte[] largePayload = new byte[1_500_000]; // ok. 1.5 MB
        for (int i = 0; i < largePayload.length; i++) {
            largePayload[i] = (byte) ('A' + (i % 26));
        }

        server.createContext("/plik", exchange -> {
            exchange.getResponseHeaders().add("Content-Type", "application/octet-stream");
            exchange.sendResponseHeaders(200, largePayload.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(largePayload);
            }
        });

        // Kontekst wysylajacy odpowiedz PORCJAMI, z opoznieniem miedzy
        // fragmentami - do demonstracji readTimeout(). Content-length 0
        // wymusza w com.sun.net.httpserver.HttpServer chunked transfer -
        // kazdy write() to osobny fragment wyslany od razu.
        server.createContext("/wolny-strumien", exchange -> {
            exchange.sendResponseHeaders(200, 0);
            try (OutputStream out = exchange.getResponseBody()) {
                for (int i = 1; i <= 4; i++) {
                    out.write(("fragment-" + i + "\n").getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    Thread.sleep(400); // dluzsze niz nasz krotki readTimeout ponizej
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Kontekst odbierajacy upload multipart/form-data. Bez pelnego
        // parsera multipart (to wymagalby dodatkowej zaleznosci) -
        // odczytujemy surowe bajty i sprawdzamy, ze naglowek Content-Type
        // oraz oczekiwane fragmenty tresci faktycznie dotarly.
        server.createContext("/upload", exchange -> {
            byte[] bodyBytes;
            try (InputStream in = exchange.getRequestBody()) {
                bodyBytes = in.readAllBytes();
            }
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            String bodyAsText = new String(bodyBytes, StandardCharsets.ISO_8859_1);
            boolean isMultipart = contentType != null && contentType.startsWith("multipart/form-data");
            boolean containsFileName = bodyAsText.contains("raport.txt");
            boolean containsDescription = bodyAsText.contains("Plik testowy z lekcji 14");

            String responseBody = "Content-Type: " + contentType
                    + "\nRozmiar body: " + bodyBytes.length + " bajtow"
                    + "\nCzy multipart/form-data: " + isMultipart
                    + "\nZnaleziono nazwe pliku 'raport.txt': " + containsFileName
                    + "\nZnaleziono pole 'opis': " + containsDescription;

            byte[] respBytes = responseBody.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, respBytes.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(respBytes);
            }
        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        int port = server.getAddress().getPort();
        System.out.println("Serwer wystartowal na porcie: " + port);
        String baseUrl = "http://localhost:" + port;

        try {
            OkHttpClient client = new OkHttpClient.Builder().build();

            /*
             * ============================================================
             * 🔹 PROBLEM: response.body().string() DLA DUŻYCH ODPOWIEDZI
             * ============================================================
             * W Lesson12 czytaliśmy całe ciało odpowiedzi jednym
             * wywołaniem: response.body().string(). Dla małych odpowiedzi
             * (kilobajty JSON-a) to wygodne i bezpieczne. Dla DUŻYCH
             * odpowiedzi (megabajty/gigabajty - pliki, archiwa, eksporty
             * danych) jest to PROBLEMATYCZNE:
             *   - cała odpowiedź trafia do JEDNEGO obiektu String/byte[]
             *     w pamięci RAM - dla dużych plików grozi to
             *     OutOfMemoryError
             *   - nie da się zacząć przetwarzać danych, zanim CAŁOŚĆ nie
             *     zostanie pobrana
             *
             * Rozwiązanie: odczyt STRUMIENIOWY - pobieramy dane w małych,
             * stałej wielkości porcjach (np. po 8 KB) i od razu
             * zapisujemy/przetwarzamy każdą porcję, zamiast czekać na
             * całość.
             */

            System.out.println("\n=== Strumieniowe pobieranie duzej odpowiedzi (byteStream) ===");

            Request downloadRequest = new Request.Builder()
                    .url(baseUrl + "/plik")
                    .build();

            Path targetFile = Files.createTempFile("javaquest-lekcja14-", ".bin");

            try (Response response = client.newCall(downloadRequest).execute()) {
                ResponseBody responseBody = response.body();
                System.out.println("Deklarowany rozmiar (Content-Length): "
                        + responseBody.contentLength() + " bajtow");

                long totalRead = 0;
                byte[] buffer = new byte[8192];

                /*
                 * responseBody.byteStream() zwraca zwykly java.io.InputStream
                 * POWIAZANY z otwartym polaczeniem siecowym - kazde read()
                 * pobiera KOLEJNA porcje danych z sieci, a nie caly plik
                 * naraz. Zapisujemy kazda porcje bezposrednio do pliku na
                 * dysku - w pamieci RAM w danym momencie trzymamy tylko
                 * pojedynczy bufor 8 KB, NIE caly plik.
                 */
                try (InputStream input = responseBody.byteStream();
                     OutputStream output = Files.newOutputStream(targetFile)) {
                    int read;
                    while ((read = input.read(buffer)) != -1) {
                        output.write(buffer, 0, read);
                        totalRead += read;
                    }
                }

                System.out.println("Pobrano strumieniowo (porcjami po max 8192 B): "
                        + totalRead + " bajtow");
            }

            System.out.println("Rozmiar pliku zapisanego na dysku: "
                    + Files.size(targetFile) + " bajtow");
            Files.deleteIfExists(targetFile);

            /*
             * ============================================================
             * 🔍 NIŻSZY POZIOM: BufferedSource (Okio)
             * ============================================================
             * ResponseBody.byteStream() to wygodna "nakładka" na
             * ResponseBody.source(), która zwraca okio.BufferedSource -
             * natywny, niższopoziomowy typ biblioteki Okio, na której
             * OkHttp jest zbudowany (Okio to osobna biblioteka Square,
             * dostarczana transytywnie razem z OkHttp - to samo repo
             * pom.xml, bez dodatkowej deklaracji zależności).
             *
             * BufferedSource.read(Buffer sink, long byteCount) czyta
             * maksymalnie byteCount bajtów do przekazanego okio.Buffer i
             * zwraca liczbę faktycznie odczytanych bajtów (albo -1 na
             * końcu strumienia) - koncepcyjnie to samo co InputStream.read,
             * ale w API natywnym dla OkHttp/Okio. W praktyce byteStream()
             * (zwykły InputStream) wystarcza w większości aplikacji -
             * BufferedSource przydaje się przy integracji z kodem, który
             * już operuje na typach Okio.
             */

            Request downloadRequest2 = new Request.Builder()
                    .url(baseUrl + "/plik")
                    .build();

            System.out.println("\n=== To samo strumieniowanie, ale przez okio.BufferedSource ===");
            try (Response response = client.newCall(downloadRequest2).execute();
                 BufferedSource source = response.body().source()) {
                Buffer chunk = new Buffer();
                long total = 0;
                long read;
                while ((read = source.read(chunk, 8192)) != -1) {
                    total += read;
                    chunk.clear(); // w tym demo nas interesuje tylko liczenie porcji
                }
                System.out.println("Odczytano przez BufferedSource: " + total + " bajtow");
            }

            /*
             * ============================================================
             * 🔹 UPLOAD PLIKÓW - MultipartBody
             * ============================================================
             * multipart/form-data to standardowy format ciała żądania HTTP
             * do przesyłania FORMULARZY zawierających pliki (razem ze
             * zwykłymi polami tekstowymi) - ten sam format, którego używa
             * przeglądarka przy <form enctype="multipart/form-data">.
             *
             * OkHttp oferuje do tego dedykowany builder - MultipartBody.Builder:
             *   - .setType(MultipartBody.FORM) - standardowy typ formularza
             *   - .addFormDataPart(name, value) - zwykłe pole tekstowe
             *   - .addFormDataPart(name, filename, requestBody) - część
             *     reprezentująca PLIK - filename to nazwa pliku widoczna
             *     dla serwera, requestBody to jego zawartość + typ MIME
             *
             * MultipartBody SAM w sobie implementuje RequestBody, więc
             * przekazujemy go bezpośrednio do .post(multipartBody).
             */

            System.out.println("\n=== Upload pliku przez multipart/form-data ===");

            byte[] fileContent = ("To jest zawartosc przykladowego pliku raportu.\n"
                    + "Wygenerowana w Lesson14_OkHttpStreamingAndTesting.\n")
                    .getBytes(StandardCharsets.UTF_8);

            RequestBody fileBody = RequestBody.create(fileContent, MediaType.parse("text/plain"));

            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("opis", "Plik testowy z lekcji 14")
                    .addFormDataPart("plik", "raport.txt", fileBody)
                    .build();

            Request uploadRequest = new Request.Builder()
                    .url(baseUrl + "/upload")
                    .post(multipartBody)
                    .build();

            try (Response response = client.newCall(uploadRequest).execute()) {
                System.out.println("Odpowiedz serwera:");
                System.out.println(response.body().string());
            }

            /*
             * ============================================================
             * 🔹 WŁASNE TIMEOUTY - connectTimeout / readTimeout / writeTimeout
             * ============================================================
             * OkHttpClient.Builder pozwala skonfigurować TRZY niezależne
             * timeouty (domyślnie po 10 sekund każdy):
             *   - connectTimeout - czas na nawiązanie połączenia TCP
             *   - readTimeout - MAKSYMALNY czas ciszy między kolejnymi
             *     pakietami danych ODCZYTYWANYMI z odpowiedzi (nie czas
             *     całego pobierania! - jeśli dane płyną, choćby wolno,
             *     ale bez przerw dłuższych niż readTimeout, request trwa
             *     dalej)
             *   - writeTimeout - analogicznie, ale dla WYSYŁANIA danych
             *     (np. dużego ciała żądania POST)
             *
             * Poniżej: kontekst "/wolny-strumien" wysyła 4 fragmenty w
             * odstępach 400 ms. Klient z readTimeout ustawionym na 150 ms
             * napotka przerwę dłuższą niż limit i rzuci wyjątek.
             */

            OkHttpClient impatientClient = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(150, TimeUnit.MILLISECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    .build();

            Request slowRequest = new Request.Builder()
                    .url(baseUrl + "/wolny-strumien")
                    .build();

            System.out.println("\n=== Klient z krotkim readTimeout (150 ms) - oczekiwany blad ===");
            try (Response response = impatientClient.newCall(slowRequest).execute()) {
                // Samo execute() moze sie udac (naglowki przychodza szybko) -
                // blad zwykle pojawia sie dopiero przy CZYTANIU ciala.
                String body = response.body().string();
                System.out.println("Niespodziewany sukces, cialo: " + body);
            } catch (IOException e) {
                System.out.println("Zlapano wyjatek (spodziewany timeout): "
                        + e.getClass().getSimpleName() + " - " + e.getMessage());
            }

            OkHttpClient patientClient = new OkHttpClient.Builder()
                    .readTimeout(3, TimeUnit.SECONDS)
                    .build();

            Request slowRequestAgain = new Request.Builder()
                    .url(baseUrl + "/wolny-strumien")
                    .build();

            System.out.println("\n=== Ten sam endpoint, klient z wygodnym readTimeout (3 s) - sukces ===");
            try (Response response = patientClient.newCall(slowRequestAgain).execute()) {
                System.out.println("Cialo odebrane w calosci:\n" + response.body().string());
            }

        } finally {
            server.stop(0);
            System.out.println("\nLokalny serwer produkcyjny zatrzymany.");
        }

        /*
         * ============================================================
         * 📦 TESTOWANIE KLIENTÓW OkHttp - MockWebServer
         * ============================================================
         * Dotychczas w lekcjach 12-14 nasz "serwer testowy" to
         * com.sun.net.httpserver.HttpServer, dla którego SAMI piszemy
         * handlery (logikę odpowiedzi). To dobre do demonstrowania
         * OkHttp, ale w REALNYM projekcie testy jednostkowe klienta HTTP
         * (np. "czy nasz kod poprawnie parsuje odpowiedź API" albo "czy
         * wysyłamy poprawne nagłówki") nie powinny zależeć od prawdziwego
         * serwera ani nawet od ręcznie pisanych handlerów.
         *
         * okhttp3.mockwebserver.MockWebServer (osobny artefakt Square,
         * ale ten sam "rodzinny" projekt co OkHttp) to LEKKI, PRAWDZIWY
         * serwer HTTP działający lokalnie, którego zachowaniem sterujemy
         * deklaratywnie: "kiedy przyjdzie kolejne żądanie, odpowiedz TYM":
         *
         *   MockWebServer server = new MockWebServer();
         *   server.start();
         *   server.enqueue(new MockResponse().setResponseCode(200).setBody("..."));
         *   // ... wykonaj żądanie normalnym OkHttpClient na server.url(...) ...
         *   RecordedRequest recorded = server.takeRequest();
         *   // ... sprawdź recorded.getMethod(), recorded.getPath(), recorded.getBody() ...
         *
         * Kluczowa różnica względem ręcznego HttpServer: MockWebServer
         * NIE tylko odpowiada na żądania - NAGRYWA też każde otrzymane
         * żądanie, dzięki czemu można zweryfikować, CO DOKŁADNIE nasz
         * klient wysłał (metodę, ścieżkę, nagłówki, ciało) - to jest
         * dokładnie to, co potrzebne w testach jednostkowych.
         */

        MockWebServer mockServer = new MockWebServer();

        try {
            mockServer.start();
            System.out.println("\n=== MockWebServer uruchomiony na porcie: "
                    + mockServer.getPort() + " ===");

            OkHttpClient testClient = new OkHttpClient.Builder().build();

            /*
             * ============================================================
             * 🔹 enqueue() - "ZAPLANUJ" ODPOWIEDŹ NA KOLEJNE ŻĄDANIE
             * ============================================================
             * Każde wywołanie enqueue(MockResponse) dodaje odpowiedź do
             * KOLEJKI FIFO - pierwsze wysłane do serwera żądanie dostanie
             * PIERWSZĄ zaplanowaną odpowiedź, drugie żądanie - drugą itd.
             * Jeśli żądań przyjdzie więcej niż zaplanowanych odpowiedzi,
             * MockWebServer domyślnie zwraca błąd dla nadmiarowych żądań.
             */

            mockServer.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json")
                    .setBody("{\"status\":\"ok\",\"lekcja\":14}"));

            mockServer.enqueue(new MockResponse()
                    .setResponseCode(500)
                    .setBody("blad wewnetrzny serwera (zaplanowany celowo)"));

            System.out.println("\n=== Pierwsze zadanie do MockWebServer ===");
            Request firstRequest = new Request.Builder()
                    .url(mockServer.url("/api/dane"))
                    .build();

            try (Response response = testClient.newCall(firstRequest).execute()) {
                System.out.println("Kod: " + response.code());
                System.out.println("Cialo: " + response.body().string());
            }

            System.out.println("\n=== Drugie zadanie - odbiera KOLEJNA zaplanowana odpowiedz (FIFO) ===");
            Request secondRequest = new Request.Builder()
                    .url(mockServer.url("/api/inny-zasob"))
                    .build();

            try (Response response = testClient.newCall(secondRequest).execute()) {
                System.out.println("Kod: " + response.code() + " (zaplanowany blad 500)");
            }

            /*
             * ============================================================
             * 🔍 takeRequest() - WERYFIKACJA TEGO, CO KLIENT WYSŁAŁ
             * ============================================================
             * server.takeRequest() zwraca RecordedRequest opisujący
             * KOLEJNE (w kolejności otrzymania) żądanie, jakie faktycznie
             * dotarło do MockWebServer - to fundament testowania klienta:
             * zamiast zgadywać, CO nasz kod wysłał, możemy to sprawdzić
             * wprost - metodę, ścieżkę, nagłówki, ciało żądania.
             *
             * Wariant z timeoutem - takeRequest(timeout, TimeUnit) - czeka
             * maksymalnie zadany czas na nadejście żądania (przydatne przy
             * testowaniu kodu asynchronicznego z Lesson13) i zwraca null,
             * jeśli żądanie nie nadeszło w tym czasie.
             */

            RecordedRequest firstRecorded = mockServer.takeRequest(2, TimeUnit.SECONDS);
            System.out.println("\n=== Zweryfikowane PIERWSZE zadanie (RecordedRequest) ===");
            System.out.println("Metoda:  " + firstRecorded.getMethod());
            System.out.println("Sciezka: " + firstRecorded.getPath());

            boolean pathCorrect = "/api/dane".equals(firstRecorded.getPath());
            boolean methodCorrect = "GET".equals(firstRecorded.getMethod());
            System.out.println("Sciezka poprawna? " + pathCorrect + ", metoda poprawna? " + methodCorrect);

            /*
             * ============================================================
             * 🔹 WERYFIKACJA WYSŁANEGO CIAŁA I NAGŁÓWKÓW (POST)
             * ============================================================
             * RecordedRequest.getBody() zwraca okio.Buffer z surowym
             * ciałem żądania - readUtf8() odczytuje je jako String.
             * getHeader(name) pozwala sprawdzić dowolny nagłówek, jaki
             * nasz klient faktycznie wysłał (np. dodany przez interceptor
             * z Lesson13).
             */

            mockServer.enqueue(new MockResponse().setResponseCode(201).setBody("utworzono"));

            RequestBody jsonPayload = RequestBody.create(
                    "{\"imie\":\"Anna\"}", MediaType.parse("application/json"));

            Request postRequest = new Request.Builder()
                    .url(mockServer.url("/api/osoby"))
                    .header("X-Test-Header", "wartosc-testowa")
                    .post(jsonPayload)
                    .build();

            System.out.println("\n=== Zadanie POST do MockWebServer ===");
            try (Response response = testClient.newCall(postRequest).execute()) {
                System.out.println("Kod odpowiedzi: " + response.code());
            }

            RecordedRequest postRecorded = mockServer.takeRequest(2, TimeUnit.SECONDS);
            System.out.println("Wyslana metoda:      " + postRecorded.getMethod());
            System.out.println("Wyslane cialo:        " + postRecorded.getBody().readUtf8());
            System.out.println("Wyslany naglowek:     X-Test-Header = "
                    + postRecorded.getHeader("X-Test-Header"));

            /*
             * ============================================================
             * 🔍 MockWebServer vs RĘCZNY com.sun.net.httpserver.HttpServer
             * ============================================================
             * Oba są prawdziwymi, lokalnymi serwerami HTTP (żaden nie
             * wymaga internetu) - różnica to PRZEZNACZENIE:
             *
             *   - com.sun.net.httpserver.HttpServer (Lesson12-14, "/plik",
             *     "/upload", "/wolny-strumien") - piszemy WŁASNĄ logikę
             *     odpowiedzi (handler), dobre do DEMONSTROWANIA jak
             *     zachowuje się prawdziwy serwer (np. wolno wysyłający
             *     dane fragmentami)
             *   - MockWebServer - NIE piszemy logiki serwera - tylko
             *     DEKLARUJEMY kolejne odpowiedzi (enqueue) i WERYFIKUJEMY
             *     otrzymane żądania (takeRequest) - to narzędzie zbudowane
             *     WPROST pod pisanie TESTÓW klientów HTTP, typowo używane
             *     z JUnit w realnych projektach (tutaj wywołane wprost z
             *     main(), zgodnie z konwencją całego kursu)
             *
             * W realnym projekcie: testy jednostkowe klienta OkHttp (np.
             * własnej klasy ApiClient) piszemy właśnie na MockWebServer -
             * szybkie, deterministyczne, bez zależności od prawdziwej
             * sieci ani od dostępności zewnętrznego API.
             */

            System.out.println("\n=== Roznica MockWebServer vs recznie pisany HttpServer - patrz komentarz w kodzie ===");

        } finally {
            mockServer.shutdown();
            System.out.println("\nMockWebServer zatrzymany.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - response.body().string() ładuje CAŁE ciało do pamięci - dla
         *   dużych odpowiedzi używaj odczytu STRUMIENIOWEGO:
         *   responseBody.byteStream() (zwykły InputStream, czytany
         *   porcjami np. po 8 KB) albo responseBody.source()
         *   (okio.BufferedSource, niższy poziom API OkHttp/Okio)
         * - MultipartBody.Builder().setType(MultipartBody.FORM)
         *   .addFormDataPart(name, value) / .addFormDataPart(name,
         *   filename, requestBody) - budowa uploadu plików
         *   multipart/form-data, .post(multipartBody) do wysłania
         * - OkHttpClient.Builder() ma TRZY niezależne timeouty:
         *   connectTimeout / readTimeout / writeTimeout (domyślnie 10 s
         *   każdy) - readTimeout to maksymalna PRZERWA między porcjami
         *   danych, nie czas całego pobierania
         * - MockWebServer - prawdziwy, lekki, lokalny serwer HTTP do
         *   TESTOWANIA klientów OkHttp bez prawdziwej sieci:
         *   server.enqueue(new MockResponse()...) planuje kolejne
         *   odpowiedzi (kolejka FIFO), server.url(path) daje URL do
         *   żądania, server.takeRequest() zwraca RecordedRequest -
         *   pozwala zweryfikować metodę/ścieżkę/nagłówki/ciało żądania,
         *   jakie FAKTYCZNIE wysłał nasz klient
         * - To zamyka trylogię OkHttp: Lesson12 (podstawy), Lesson13
         *   (async + interceptory), Lesson14 (streaming, upload, timeouty,
         *   testowanie)
         */

        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }
}
