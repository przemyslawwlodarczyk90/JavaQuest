package com.example.javaquest._06_networking.Lesson09_URLConnection;

public class _Exercises_Lesson09_URLConnection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_LocalServerPingPong {
        /*
         * 🧪 Zadanie 1:
         * Uruchom lokalny com.sun.net.httpserver.HttpServer na porcie 0
         * (system sam wybierze port) z kontekstem "/ping" zwracającym tekst
         * "pong". Połącz się przez URLConnection (url.openConnection()),
         * odczytaj ciało odpowiedzi przez getInputStream() i wypisz je.
         * Na końcu koniecznie zatrzymaj serwer (server.stop(0)).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadSingleHeader {
        /*
         * 🧪 Zadanie 2:
         * W lokalnym serwerze (kontekst "/info") ustaw nagłówek odpowiedzi
         * "Content-Type" na "text/plain; charset=UTF-8". Po stronie klienta
         * odczytaj tę wartość metodą connection.getHeaderField("Content-Type")
         * i wypisz ją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PrintAllHeaders {
        /*
         * 🧪 Zadanie 3:
         * Dla dowolnego kontekstu lokalnego serwera odczytaj
         * connection.getHeaderFields() i wypisz WSZYSTKIE nagłówki
         * (nazwa -> lista wartości), w tym specjalny wpis dla klucza null
         * (linia statusu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ContentTypeAndLengthShortcuts {
        /*
         * 🧪 Zadanie 4:
         * Skorzystaj z metod-skrótów getContentType() i getContentLength()
         * na obiekcie URLConnection dla lokalnego kontekstu zwracającego
         * tekst o znanej długości. Wypisz obie wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CountLinesFromBody {
        /*
         * 🧪 Zadanie 5:
         * Lokalny kontekst niech zwraca 3-liniowy tekst (rozdzielony "\n").
         * Po stronie klienta odczytaj ciało przez BufferedReader owinięty
         * na InputStreamReader z getInputStream() i policz, ile linii
         * odczytano metodą readLine() w pętli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CheckReturnedConnectionType {
        /*
         * 🧪 Zadanie 6:
         * Otwórz połączenie do lokalnego serwera http przez openConnection()
         * i wypisz connection.getClass().getName() – zaobserwuj, że mimo
         * deklarowanego typu URLConnection, realny obiekt to podtyp
         * HttpURLConnection.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ConnectionUrlGetter {
        /*
         * 🧪 Zadanie 7:
         * Otwórz URLConnection do lokalnego serwera i wypisz
         * connection.getURL() – porównaj (equals) z oryginalnym obiektem
         * URL, z którego zostało utworzone połączenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReadAllBytesLength {
        /*
         * 🧪 Zadanie 8:
         * Odczytaj ciało odpowiedzi lokalnego serwera jako tablicę bajtów
         * metodą getInputStream().readAllBytes() i wypisz jej długość
         * w bajtach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CustomRequestPropertyEcho {
        /*
         * 🧪 Zadanie 9:
         * Ustaw na kliencie własny nagłówek żądania przez
         * connection.setRequestProperty("X-Kurs", "JavaQuest"). Lokalny
         * kontekst serwera niech odczyta ten nagłówek (exchange.getRequestHeaders())
         * i odeśle jego wartość jako ciało odpowiedzi. Wypisz odebrane ciało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LazyConnectDemo {
        /*
         * 🧪 Zadanie 10:
         * Wywołaj url.openConnection() i wypisz komunikat "Połączenie
         * utworzone (jeszcze nie nawiązane)". Dopiero PO wywołaniu
         * getInputStream() wypisz drugi komunikat "Dane odebrane" –
         * zademonstruj w praktyce, że samo openConnection() nie łączy się
         * jeszcze z serwerem (leniwe połączenie).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_VerifyJsonContentType {
        /*
         * 🧪 Zadanie 11:
         * Lokalny kontekst zwraca nagłówek Content-Type ustawiony na
         * "application/json; charset=UTF-8". Po stronie klienta sprawdź
         * (startsWith) czy getContentType() zaczyna się od "application/json"
         * i wypisz wynik sprawdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TwoContextsCompareHeaders {
        /*
         * 🧪 Zadanie 12:
         * Zarejestruj na jednym serwerze dwa konteksty "/a" i "/b" zwracające
         * różne nagłówki niestandardowe (np. X-Endpoint: A / X-Endpoint: B).
         * Połącz się z obydwoma po kolei i wypisz wartość X-Endpoint dla
         * każdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CounterHeaderAcrossCalls {
        /*
         * 🧪 Zadanie 13:
         * Kontekst serwera niech trzyma licznik (np. AtomicInteger) i przy
         * każdym żądaniu zwiększa go, dodając wartość jako nagłówek
         * "X-Licznik". Wywołaj żądanie dwukrotnie (dwa OSOBNE URLConnection)
         * i wypisz odczytaną wartość licznika za każdym razem (powinna rosnąć).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TimeoutsConfiguration {
        /*
         * 🧪 Zadanie 14:
         * Ustaw na połączeniu setConnectTimeout(2000) i setReadTimeout(2000)
         * przed wywołaniem getInputStream(). Wypisz oba ustawienia
         * (getConnectTimeout()/getReadTimeout()) oraz potwierdź, że dla
         * szybkiego lokalnego serwera odpowiedź i tak przychodzi natychmiast.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SmallReadTimeoutTriggersException {
        /*
         * 🧪 Zadanie 15:
         * Kontekst serwera niech celowo śpi 300ms (Thread.sleep) przed
         * odesłaniem odpowiedzi. Ustaw na kliencie setReadTimeout(50) (bardzo
         * mały) i złap SocketTimeoutException przy próbie odczytu, wypisując
         * komunikat o przekroczeniu czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ManualKeyValueBodyParsing {
        /*
         * 🧪 Zadanie 16:
         * Kontekst serwera niech zwraca ciało w formacie
         * "imie=Jan\nmiasto=Krakow\nwiek=30". Po stronie klienta odczytaj
         * całe ciało, a następnie ręcznie (bez zewnętrznych bibliotek)
         * rozbij je linia po linii na Map<String,String> i wypisz mapę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ContentLengthVsActualBytes {
        /*
         * 🧪 Zadanie 17:
         * Odczytaj connection.getContentLengthLong() PRZED odczytem ciała,
         * a następnie policz faktyczną liczbę odczytanych bajtów przez
         * readAllBytes().length. Porównaj obie wartości i wypisz, czy się zgadzają.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TwoContextsDifferentContentTypes {
        /*
         * 🧪 Zadanie 18:
         * Zarejestruj konteksty "/tekst" (Content-Type: text/plain) oraz
         * "/dane" (Content-Type: application/json). Klient łączy się z obydwoma
         * i na podstawie odczytanego Content-Type decyduje (if/else), czy
         * wypisać ciało jako "zwykły tekst" czy "dane JSON" (bez realnego
         * parsowania JSON – wystarczy odpowiedni komunikat).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CustomRequestHeaderRoundTrip {
        /*
         * 🧪 Zadanie 19:
         * Klient wysyła nagłówek "X-Klient-Id" z wartością np. "abc-123".
         * Kontekst serwera odczytuje ten nagłówek (exchange.getRequestHeaders())
         * i dołącza jego wartość do treści odpowiedzi. Klient odczytuje ciało
         * i sprawdza (equals), czy zawiera dokładnie wysłaną wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReusableFetchBodyHelper {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę pomocniczą fetchBody(String urlString) zwracającą
         * String z pełnym ciałem odpowiedzi (otwiera URLConnection, czyta
         * przez BufferedReader). Przetestuj ją dla 3 różnych kontekstów
         * zarejestrowanych na tym samym lokalnym serwerze.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RetryUntilSuccessViaStatusLine {
        /*
         * 🧪 Zadanie 21:
         * Kontekst serwera niech zwraca kod 500 dla pierwszych 2 wywołań,
         * a 200 dla trzeciego (licznik w handlerze). Ponieważ URLConnection
         * nie ma getResponseCode(), odczytaj status przez
         * connection.getHeaderField(0) (zwraca linię statusu, np.
         * "HTTP/1.1 500 Internal Server Error") i sparsuj z niej kod liczbowy.
         * Ponów żądanie (nowe URLConnection za każdym razem) do skutku,
         * maks. 5 prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimpleClientSideCache {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj prostą "pamięć podręczną" po stronie klienta: Map<String,String>
         * przechowującą ciało odpowiedzi dla danego URL-a. Za pierwszym razem
         * pobierz dane przez URLConnection i zapisz w mapie. Przy drugim
         * "żądaniu" dla tego samego URL-a sprawdź najpierw mapę i NIE łącz
         * się ponownie z serwerem, jeśli wpis już istnieje. Zweryfikuj to
         * licznikiem żądań po stronie serwera (powinien wynosić 1, nie 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConcurrentFetchFromThreeContexts {
        /*
         * 🧪 Zadanie 23:
         * Zarejestruj 3 konteksty ("/a","/b","/c") zwracające różne stałe
         * teksty. Użyj ExecutorService, aby pobrać wszystkie trzy RÓWNOLEGLE
         * przez URLConnection, zbierz wyniki do Map<String,String> (ścieżka
         * -> ciało) i wypisz posortowaną mapę. Pamiętaj o zamknięciu executor'a.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ContentNegotiationByAcceptHeader {
        /*
         * 🧪 Zadanie 24:
         * Kontekst serwera sprawdza nagłówek żądania "Accept": jeśli zawiera
         * "application/json", zwraca '{"ok":true}', w przeciwnym razie zwraca
         * zwykły tekst "ok". Klient wysyła dwa żądania z różnymi wartościami
         * Accept (ustawianymi przez setRequestProperty) i wypisuje otrzymane
         * ciała dla obu przypadków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_UnknownLengthChunkedRead {
        /*
         * 🧪 Zadanie 25:
         * Kontekst serwera niech wywoła sendResponseHeaders(200, 0) i zapisuje
         * odpowiedź w kilku porcjach (kilka wywołań write w pętli, tak by
         * długość nie była z góry znana klientowi). Po stronie klienta
         * odczytaj całość przez getInputStream().readAllBytes() i wypisz
         * łączną liczbę odebranych bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HeaderInspectorUtility {
        /*
         * 🧪 Zadanie 26:
         * Napisz metodę inspectHeaders(String urlString, String... headerNames)
         * zwracającą Map<String,String> z wartościami TYLKO wskazanych
         * nagłówków (np. "Content-Type", "Content-Length", "X-Custom").
         * Przetestuj ją dla dwóch różnych kontekstów lokalnego serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConnectThenSetPropertyThrows {
        /*
         * 🧪 Zadanie 27:
         * Otwórz URLConnection, wywołaj jawnie connection.connect(), a
         * następnie spróbuj wywołać setRequestProperty(...) PO połączeniu.
         * Złap wynikający z tego IllegalStateException i wypisz jego komunikat
         * – zademonstruj, że konfiguracja połączenia musi nastąpić PRZED
         * faktycznym połączeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_EachCallIsFreshConnection {
        /*
         * 🧪 Zadanie 28:
         * Kontekst serwera niech zwraca za każdym razem inną losową liczbę
         * (np. Random w handlerze). Wywołaj żądanie 5 razy (5 NOWYCH
         * obiektów URLConnection z tego samego URL-a) i wypisz zebrane
         * wartości – zaobserwuj, że są różne, bo każde połączenie jest
         * osobnym żądaniem do serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FlakyEndpointWithRetryAfter {
        /*
         * 🧪 Zadanie 29:
         * Kontekst serwera niech zwraca kod 503 z nagłówkiem "Retry-After: 1"
         * dla pierwszych 2 wywołań, a 200 dla trzeciego. Klient parsuje
         * status ze statusu linii (getHeaderField(0), jak w Zadaniu 21),
         * odczytuje Retry-After, czeka krótką chwilę (np. Thread.sleep(50) –
         * NIE realną liczbę sekund z nagłówka, żeby test był szybki) i ponawia
         * żądanie, aż uzyska sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_TinyHttpClientWrapper {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj rekord Response(int statusCode, Map<String,List<String>> headers,
         * String body) oraz klasę pomocniczą z metodą get(String urlString)
         * zwracającą Response (status parsowany z getHeaderField(0), jak
         * w poprzednich zadaniach). Przetestuj klasę na co najmniej 2 różnych
         * kontekstach lokalnego serwera, wypisując pełne obiekty Response.
         */
        public static void main(String[] args) { }
    }
}
