package com.example.javaquest._13_libraries.Lesson14_OkHttpStreamingAndTesting;

public class _Exercises_Lesson14_OkHttpStreamingAndTesting {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StreamingDownloadBasic {
        /*
         * 🧪 Zadanie 1:
         * Uruchom lokalny com.sun.net.httpserver.HttpServer na porcie 0 z
         * kontekstem "/dane" zwracającym wygenerowaną tablicę 500 000
         * bajtów (np. wypełnioną powtarzającym się wzorcem znaków A-Z).
         * Pobierz ją przez OkHttp, ale odczytaj ciało odpowiedzi PORCJAMI
         * po 4096 bajtów (responseBody.byteStream().read(buffer)) w pętli,
         * zliczając łączną liczbę odebranych bajtów - NIE używaj
         * response.body().string(). Wypisz sumę bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StreamDownloadToTempFile {
        /*
         * 🧪 Zadanie 2:
         * Kontekst "/plik" niech zwraca 300 000 bajtów. Pobierz odpowiedź
         * strumieniowo i zapisz ją bezpośrednio do pliku tymczasowego
         * utworzonego przez Files.createTempFile(...), kopiując dane
         * porcjami (bufor 8192 bajtów) zamiast trzymać całość w pamięci.
         * Po zapisaniu porównaj Files.size(plik) z oczekiwanym rozmiarem
         * (300 000) i wypisz wynik porównania. Usuń plik na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareContentLengthWithActualBytes {
        /*
         * 🧪 Zadanie 3:
         * Kontekst "/raport" niech zwraca dowolną tablicę bajtów o znanym
         * rozmiarze. Przed odczytem ciała odczytaj deklarowany rozmiar
         * przez responseBody.contentLength(), następnie odczytaj ciało
         * strumieniowo (byteStream()) licząc faktyczną liczbę bajtów.
         * Wypisz obie wartości i sprawdź (boolean), czy są sobie równe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SimpleMultipartTextField {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj MultipartBody z JEDNYM polem tekstowym przez
         * .addFormDataPart("autor", "Jan Kowalski") (bez żadnego pliku).
         * Wyślij POST na lokalny kontekst "/formularz", który sprawdza
         * (exchange.getRequestHeaders().getFirst("Content-Type")), czy
         * nagłówek zaczyna się od "multipart/form-data" i odsyła wynik
         * tego sprawdzenia jako ciało odpowiedzi. Wypisz odebraną odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MultipartFileUpload {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj RequestBody z tablicy bajtów reprezentującej zawartość
         * pliku tekstowego "notatka.txt" (MediaType "text/plain") i dodaj
         * ją do MultipartBody przez
         * .addFormDataPart("plik", "notatka.txt", requestBody). Wyślij POST
         * na kontekst "/upload", który odczytuje CAŁE surowe body
         * (readAllBytes) i odsyła jego długość w bajtach jako odpowiedź.
         * Wypisz odebraną długość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipartTextFieldAndFileTogether {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj JEDEN MultipartBody zawierający zarówno pole tekstowe
         * "tytul" o wartości "Raport miesieczny", jak i część plikową
         * "plik" o nazwie "raport.csv". Kontekst serwera niech zamieni
         * surowe body na String (ISO-8859-1) i sprawdzi, czy zawiera
         * podciąg "raport.csv" ORAZ "Raport miesieczny" - odeśle wynik
         * obu sprawdzeń. Wypisz odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ConnectTimeoutOnRefusedPort {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj OkHttpClient z .connectTimeout(1, TimeUnit.SECONDS) i
         * wykonaj żądanie GET na "http://localhost:1/brak" (port bez
         * nasłuchującego serwera). Złap IOException w try-catch i wypisz
         * nazwę klasy wyjątku oraz jego komunikat - main() musi zakończyć
         * się poprawnie mimo błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReadTimeoutOnSlowChunkedResponse {
        /*
         * 🧪 Zadanie 8:
         * Kontekst "/wolno" niech wysyła odpowiedź w 3 fragmentach (przez
         * sendResponseHeaders(200, 0) - chunked) z odstępem 500 ms między
         * fragmentami (Thread.sleep). Zbuduj klienta z
         * .readTimeout(200, TimeUnit.MILLISECONDS) i wykonaj żądanie -
         * złap wyjątek podczas odczytu response.body().string() i wypisz
         * jego nazwę klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FirstMockWebServerResponse {
        /*
         * 🧪 Zadanie 9:
         * Utwórz i uruchom MockWebServer. Zaplanuj jedną odpowiedź przez
         * server.enqueue(new MockResponse().setResponseCode(200)
         * .setBody("mock-ok")). Wykonaj zwykłym OkHttpClient żądanie GET
         * na server.url("/test") i wypisz otrzymane ciało odpowiedzi.
         * Zatrzymaj serwer przez server.shutdown() w bloku finally.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TakeRequestBasics {
        /*
         * 🧪 Zadanie 10:
         * Uruchom MockWebServer, zaplanuj jedną odpowiedź 200 i wykonaj
         * żądanie GET na server.url("/uzytkownicy/42"). Po żądaniu wywołaj
         * server.takeRequest() i wypisz recorded.getMethod() oraz
         * recorded.getPath() - zweryfikuj (boolean), że ścieżka to
         * dokładnie "/uzytkownicy/42".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConfigurableChunkSizeCounter {
        /*
         * 🧪 Zadanie 11:
         * Kontekst "/dane2" niech zwraca 200 000 bajtów. Napisz metodę
         * countChunks(OkHttpClient client, String url, int bufferSize)
         * zwracającą liczbę WYWOŁAŃ read() potrzebnych do odczytania
         * całego ciała (nie liczbę bajtów!). Wywołaj ją dwukrotnie - raz
         * z buforem 1024, raz z 8192 - i wypisz oba wyniki, pokazując że
         * mniejszy bufor oznacza więcej odczytów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipartTwoFileParts {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj MultipartBody z DWIEMA częściami plikowymi -
         * "plikA" -> "dane1.txt" i "plikB" -> "dane2.txt" (różna
         * zawartość każdej). Kontekst serwera niech sprawdzi obecność OBU
         * nazw plików w surowym body i odeśle wynik obu sprawdzeń osobno.
         * Wypisz odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WriteTimeoutOnSlowServerRead {
        /*
         * 🧪 Zadanie 13:
         * Kontekst "/wolny-odbior" niech czyta ciało żądania bajt po bajcie
         * z Thread.sleep(50) między odczytami (symulacja wolnego odbioru).
         * Zbuduj MultipartBody z plikiem o rozmiarze ok. 2000 bajtów oraz
         * klienta z .writeTimeout(300, TimeUnit.MILLISECONDS). Wykonaj
         * upload, złap ewentualny wyjątek i opisz w komentarzu zaobserwowany
         * (empirycznie sprawdzony) rezultat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MockWebServerFifoOrder {
        /*
         * 🧪 Zadanie 14:
         * Zaplanuj w MockWebServer TRZY odpowiedzi z kodami kolejno 200,
         * 404, 500 (trzy osobne enqueue). Wykonaj TRZY kolejne żądania GET
         * (do dowolnych ścieżek) i zweryfikuj, że kody odpowiedzi
         * przychodzą w DOKŁADNIE tej kolejności, w jakiej zostały
         * zaplanowane (FIFO). Wypisz tabelę wynik-oczekiwany/otrzymany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RecordedRequestHeaderVerification {
        /*
         * 🧪 Zadanie 15:
         * Zaplanuj jedną odpowiedź 200 w MockWebServer. Zbuduj Request z
         * nagłówkiem .header("X-Klient", "javaQuest-test") i wykonaj go.
         * Odczytaj server.takeRequest() i sprawdź (boolean) czy
         * recorded.getHeader("X-Klient") równa się dokładnie
         * "javaQuest-test". Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RecordedRequestBodyVerification {
        /*
         * 🧪 Zadanie 16:
         * Zaplanuj odpowiedź 201 w MockWebServer. Wyślij POST z ciałem
         * JSON {"produkt":"laptop","cena":4500} (RequestBody.create z
         * MediaType "application/json"). Odczytaj recorded.getBody()
         * .readUtf8() i porównaj (equals) z oryginalnie wysłanym tekstem
         * JSON-a. Wypisz wynik porównania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MockResponseBodyDelay {
        /*
         * 🧪 Zadanie 17:
         * Zaplanuj w MockWebServer odpowiedź z .setBodyDelay(400,
         * TimeUnit.MILLISECONDS). Zmierz (System.nanoTime()) czas
         * wykonania żądania GET wobec tej odpowiedzi i wypisz czas w
         * milisekundach - powinien wynosić co najmniej ok. 400 ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_StreamDownloadFromMockWebServer {
        /*
         * 🧪 Zadanie 18:
         * Zaplanuj w MockWebServer odpowiedź, której ciało to wygenerowany
         * String o długości dokładnie 100 000 znaków (np. powtarzający się
         * wzorzec). Pobierz ją strumieniowo (byteStream(), bufor 4096) do
         * pliku tymczasowego i zweryfikuj, że Files.size(plik) odpowiada
         * liczbie bajtów oryginalnego Stringa (UTF-8). Usuń plik na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConnectVsReadTimeoutDistinction {
        /*
         * 🧪 Zadanie 19:
         * Wykonaj DWA osobne eksperymenty: (a) żądanie do portu bez
         * nasłuchującego serwera z krótkim connectTimeout - złap wyjątek i
         * zmierz czas do jego wyrzucenia; (b) żądanie do lokalnego
         * kontekstu "/pozniej", który odpowiada po Thread.sleep(600) (ale
         * NAWIĄZANIE połączenia jest natychmiastowe), z krótkim
         * readTimeout - złap wyjątek i zmierz czas. Wypisz oba czasy i
         * skomentuj różnicę w miejscu, gdzie każdy timeout "uderza".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReusableDownloadToFileHelper {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę pomocniczą
         * long downloadToFile(OkHttpClient client, String url, Path target)
         * zwracającą liczbę pobranych bajtów, odczytując ciało odpowiedzi
         * strumieniowo (bufor 8192) i zapisując je do pliku target.
         * Przetestuj ją na DWÓCH różnych kontekstach lokalnego serwera o
         * różnych rozmiarach odpowiedzi i wypisz wyniki obu wywołań.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ProgressTrackingStreamingDownload {
        /*
         * 🧪 Zadanie 21:
         * Kontekst "/duzy-plik" niech zwraca 1 000 000 bajtów. Pobierz go
         * strumieniowo, a w trakcie odczytu wypisuj postęp (procent
         * pobranych bajtów względem responseBody.contentLength()) za
         * każdym razem, gdy łączna liczba pobranych bajtów przekroczy
         * kolejną wielokrotność 250 000 (czyli 4 komunikaty postępu: ~25%,
         * ~50%, ~75%, ~100%).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LargeStreamedMultipartUpload {
        /*
         * 🧪 Zadanie 22:
         * Wygeneruj tablicę bajtów o rozmiarze 3 000 000 (3 MB) jako
         * zawartość pliku i wyślij ją przez MultipartBody jako część
         * plikową do kontekstu "/duzy-upload". Po stronie serwera odczytaj
         * ciało żądania PORCJAMI (własny bufor, NIE readAllBytes) zliczając
         * łączną liczbę odebranych bajtów i odeślij ją jako odpowiedź.
         * Wypisz odebraną wartość i porównaj z oczekiwanym rozmiarem
         * (uwzględniając narzut nagłówków multipart - sprawdź, że wynik
         * jest WIĘKSZY lub równy 3 000 000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MockWebServerTestOfMiniApiClient {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj mini klasę StatusApiClient z metodą
         * String fetchStatus(OkHttpClient client, String baseUrl) wysyłającą
         * GET na baseUrl + "/status" i zwracającą ciało odpowiedzi. Za
         * pomocą MockWebServer zaplanuj odpowiedź '{"status":"UP"}' i
         * przetestuj fetchStatus WYŁĄCZNIE przeciwko MockWebServer (bez
         * żadnego prawdziwego serwera) - wypisz zwróconą wartość i
         * porównaj ją (equals) z oczekiwanym tekstem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulateNetworkDisconnectWithMockWebServer {
        /*
         * 🧪 Zadanie 24:
         * Zaplanuj w MockWebServer odpowiedź przez
         * new MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
         * (import okhttp3.mockwebserver.SocketPolicy) - symuluje to zerwane
         * połączenie zaraz po nawiązaniu. Wykonaj żądanie, złap
         * IOException i wypisz jego nazwę klasy - zademonstruj, że
         * MockWebServer pozwala testować obsługę błędów sieciowych bez
         * realnej niestabilnej sieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RetryLoopUntilFullDownload {
        /*
         * 🧪 Zadanie 25:
         * Kontekst "/niestabilny" niech dla pierwszych 2 wywołań zwraca
         * kod 503 (bez ciała), a dla trzeciego - poprawne 400 000 bajtów.
         * Napisz pętlę wykonującą NOWY Call za każdym razem (maks. 5 prób),
         * która przy kodzie innym niż 200 czeka chwilę (np. 100 ms) i
         * próbuje ponownie, a przy 200 pobiera ciało strumieniowo i kończy
         * pętlę. Wypisz numer udanej próby i liczbę pobranych bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MockWebServerRequestOrderWithAsyncCalls {
        /*
         * 🧪 Zadanie 26:
         * Zaplanuj w MockWebServer TRZY odpowiedzi 200 z różnymi ciałami.
         * Wykonaj TRZY żądania asynchronicznie (enqueue z Lesson13,
         * CountDownLatch(3)) na trzy różne ścieżki (server.url("/a"),
         * "/b", "/c")). Po zakończeniu wszystkich odczytaj TRZY razy
         * server.takeRequest() i wypisz ścieżki w kolejności, w jakiej
         * serwer je ODEBRAŁ (może różnić się od kolejności wysłania, bo
         * żądania są asynchroniczne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultipartMixedContentTypesInspection {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj MultipartBody z częścią plikową o MediaType "text/plain"
         * ORAZ drugą częścią plikową o MediaType "application/json"
         * (różne RequestBody.create(..., MediaType.parse(...))). Kontekst
         * serwera niech odczyta surowe body jako String i sprawdzi
         * obecność obu nagłówków "Content-Type: text/plain" oraz
         * "Content-Type: application/json" WEWNĄTRZ treści multipart
         * (każda część multipart ma własny nagłówek Content-Type w swojej
         * sekcji). Wypisz wynik obu sprawdzeń.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullFileUploadClientWrapper {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj klasę FileUploadClient z metodą
         * int upload(OkHttpClient client, String url, Path file, String opis)
         * zwracającą kod odpowiedzi HTTP - metoda ma odczytać zawartość
         * pliku (Files.readAllBytes), zbudować MultipartBody z polem
         * "opis" i częścią plikową o nazwie równej file.getFileName(), i
         * wykonać POST. Utwórz realny plik tymczasowy (Files.write) z
         * przykładową zawartością i przetestuj klasę wobec lokalnego
         * kontekstu "/upload-test", który odsyła liczbę odebranych bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ThreeScenarioMockWebServerTestSuite {
        /*
         * 🧪 Zadanie 29:
         * Korzystając z JEDNEGO MockWebServer, przeprowadź TRZY kolejne
         * scenariusze testowe dla tego samego "klienta" (np. metody
         * fetchBody(client, url)): (1) enqueue 200 z poprawnym ciałem -
         * oczekiwany sukces, (2) enqueue 404 - oczekiwany kod błędu, (3)
         * enqueue odpowiedź z .setBodyDelay dłuższym niż readTimeout
         * klienta - oczekiwany wyjątek. Dla każdego scenariusza wypisz
         * "PASS" lub "FAIL" w zależności od tego, czy zaobserwowane
         * zachowanie zgadza się z oczekiwanym, oraz podsumowanie na końcu
         * (np. "3/3 scenariusze zgodne z oczekiwaniami").
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneProductionAndMockRun {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj JEDEN OkHttpClient z jawnie skonfigurowanymi
         * connectTimeout/readTimeout/writeTimeout. Część "produkcyjna":
         * uruchom lokalny com.sun.net.httpserver.HttpServer i wykonaj nim
         * (a) strumieniowe pobranie pliku ok. 500 000 bajtów oraz (b)
         * upload multipart pliku tekstowego - wypisz podsumowanie obu
         * operacji (rozmiary, kody odpowiedzi). Część "testowa": uruchom
         * MockWebServer, zaplanuj analogiczne odpowiedzi (dla pobrania i
         * uploadu) i powtórz TĘ SAMĄ logikę klienta wobec MockWebServer,
         * weryfikując przez takeRequest(), że wysłane żądania mają
         * poprawny kształt (metoda, ścieżka, obecność nagłówka
         * Content-Type multipart). Na końcu wypisz zbiorcze podsumowanie
         * porównujące obie części ("produkcyjna: OK" / "testowa: OK").
         */
        public static void main(String[] args) { }
    }
}
