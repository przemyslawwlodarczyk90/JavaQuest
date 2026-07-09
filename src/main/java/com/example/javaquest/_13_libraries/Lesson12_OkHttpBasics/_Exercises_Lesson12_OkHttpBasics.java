package com.example.javaquest._13_libraries.Lesson12_OkHttpBasics;

public class _Exercises_Lesson12_OkHttpBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FirstOkHttpClientAndRequest {
        /*
         * 🧪 Zadanie 1:
         * Uruchom lokalny com.sun.net.httpserver.HttpServer na porcie 0 z
         * kontekstem "/ping" zwracającym tekst "pong". Utwórz JEDEN
         * OkHttpClient, zbuduj Request przez Request.Builder().url(...).build()
         * i wykonaj go przez execute() w try-with-resources. Wypisz
         * response.body().string(). Zatrzymaj serwer na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CheckStatusCodeAndSuccess {
        /*
         * 🧪 Zadanie 2:
         * Lokalny kontekst "/status" niech zwraca kod 200. Po stronie
         * klienta wypisz response.code(), response.message() oraz
         * response.isSuccessful().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReadJsonBody {
        /*
         * 🧪 Zadanie 3:
         * Kontekst "/json" niech zwraca ręcznie zbudowany String JSON
         * {"status":"ok","kod":1}. Wykonaj GET przez OkHttp i wypisz
         * odczytane ciało jako String (bez parsowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CustomRequestHeader {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj Request z nagłówkiem .header("X-Kurs", "javaQuest").
         * Kontekst serwera niech odczyta ten nagłówek
         * (exchange.getRequestHeaders()) i odeśle jego wartość jako ciało
         * odpowiedzi. Wypisz odebrane ciało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReadResponseHeader {
        /*
         * 🧪 Zadanie 5:
         * Kontekst serwera niech ustawia nagłówek odpowiedzi "X-Wersja"
         * na "1.0". Po stronie klienta odczytaj go metodą
         * response.header("X-Wersja") i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PrintAllResponseHeaders {
        /*
         * 🧪 Zadanie 6:
         * Dla dowolnego kontekstu lokalnego serwera odczytaj
         * response.headers() i wypisz WSZYSTKIE nazwy oraz wartości
         * nagłówków w pętli po headers.names().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NotFoundResponseNoException {
        /*
         * 🧪 Zadanie 7:
         * Kontekst "/brak" niech zwraca kod 404. Wykonaj do niego żądanie
         * i zademonstruj, że execute() NIE rzuca wyjątku dla kodu błędu -
         * wypisz response.code() i response.isSuccessful() (powinno być false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReusableClientForTwoRequests {
        /*
         * 🧪 Zadanie 8:
         * Utwórz JEDEN OkHttpClient i wykonaj nim DWA różne żądania (do
         * dwóch różnych kontekstów lokalnego serwera) - zademonstruj, że
         * ta sama instancja klienta obsługuje wiele wywołań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CallCanBeExecutedOnlyOnce {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj Request i utwórz Call przez client.newCall(request).
         * Wywołaj execute() dwukrotnie na TYM SAMYM obiekcie Call - złap
         * IllegalStateException przy drugim wywołaniu i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ContentTypeHeaderCheck {
        /*
         * 🧪 Zadanie 10:
         * Kontekst "/dane" niech zwraca Content-Type "application/json;
         * charset=UTF-8". Po stronie klienta sprawdź (startsWith), czy
         * response.header("Content-Type") zaczyna się od "application/json"
         * i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoContextsCompareBodies {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj konteksty "/a" i "/b" zwracające różne stałe teksty.
         * Wykonaj GET do obu (tym samym reużywalnym OkHttpClient) i wypisz
         * oba ciała, porównując je (equals) - powinny być różne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CounterHeaderAcrossCalls {
        /*
         * 🧪 Zadanie 12:
         * Kontekst serwera trzyma licznik (AtomicInteger) i przy każdym
         * żądaniu zwiększa go, dodając wartość jako nagłówek "X-Licznik".
         * Wywołaj żądanie trzykrotnie (trzy OSOBNE obiekty Call z tego
         * samego Request) i wypisz odczytaną wartość licznika za każdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddHeaderVsHeaderOverwrite {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj Request wywołując .header("X-Test", "pierwsza") a potem
         * .header("X-Test", "druga") na tym samym Builderze - sprawdź (przez
         * request.header("X-Test")), że druga wartość NADPISAŁA pierwszą.
         * Następnie zbuduj drugi Request z .addHeader() dwukrotnie i wypisz
         * request.headers("X-Test") - obie wartości powinny być obecne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ManualJsonFieldExtraction {
        /*
         * 🧪 Zadanie 14:
         * Kontekst "/osoba" niech zwraca ręczny JSON
         * {"imie":"Anna","wiek":28}. Po stronie klienta odczytaj ciało jako
         * String i (bez zewnętrznej biblioteki JSON) wyciągnij wartość pola
         * "imie" prostym parsowaniem tekstu (np. indexOf/substring). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RequestUrlBuilderWithQueryParams {
        /*
         * 🧪 Zadanie 15:
         * Zarejestruj kontekst "/search" odczytujący parametry zapytania z
         * exchange.getRequestURI().getQuery() i odsyłający je jako ciało
         * odpowiedzi. Zbuduj URL z parametrami przez
         * okhttp3.HttpUrl.Builder (np. .addQueryParameter("q", "java"))
         * użyty w Request.Builder().url(HttpUrl). Wypisz odebrane ciało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleClientsDifferentPools {
        /*
         * 🧪 Zadanie 16:
         * Utwórz DWIE osobne instancje OkHttpClient i wykonaj tym samym
         * Request-em (do tego samego kontekstu) po jednym żądaniu każdą z
         * nich. Wypisz komentarz wyjaśniający, że każda instancja ma WŁASNĄ
         * pulę połączeń (dlatego w praktyce powinna istnieć TYLKO jedna
         * instancja klienta w aplikacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ResponseBodyCanBeReadOnlyOnce {
        /*
         * 🧪 Zadanie 17:
         * Wykonaj żądanie i wywołaj response.body().string() dwukrotnie na
         * TYM SAMYM obiekcie Response - zaobserwuj i wypisz, co się dzieje
         * przy drugim wywołaniu (pusty String lub wyjątek - sprawdź
         * empirycznie i opisz wynik w komentarzu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareStatusLineAcrossEndpoints {
        /*
         * 🧪 Zadanie 18:
         * Zarejestruj 3 konteksty zwracające kolejno kody 200, 201 i 404.
         * Wykonaj żądania do wszystkich trzech i wypisz tabelę (ścieżka,
         * kod, isSuccessful) w sformatowanym printf.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HeadersBuilderStandalone {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj samodzielny obiekt Headers przez
         * new Headers.Builder().add("X-A","1").add("X-B","2").build() i
         * użyj go w Request.Builder().headers(headers). Wypisz
         * request.headers() po zbudowaniu żądania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ReusableGetBodyHelperMethod {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę pomocniczą fetchBody(OkHttpClient client, String url)
         * zwracającą String z ciałem odpowiedzi (execute() +
         * response.body().string() w try-with-resources). Przetestuj ją dla
         * co najmniej 3 różnych kontekstów lokalnego serwera.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ManualRetryOnServerError {
        /*
         * 🧪 Zadanie 21:
         * Kontekst serwera niech zwraca kod 500 dla pierwszych 2 wywołań, a
         * 200 dla trzeciego (licznik w handlerze). Napisz pętlę wykonującą
         * NOWY Call (client.newCall(request) za każdym razem - Call jest
         * jednorazowy) aż do uzyskania kodu 200, maksymalnie 5 prób. Wypisz
         * numer próby, przy której się udało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimpleClientSideCacheByUrl {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj prostą pamięć podręczną po stronie klienta: Map<String,String>
         * przechowującą ciało odpowiedzi dla danego URL-a. Za pierwszym
         * razem pobierz dane przez OkHttp i zapisz w mapie. Przy drugim
         * "żądaniu" dla tego samego URL-a NIE wykonuj nowego Call, tylko
         * odczytaj z mapy. Zweryfikuj to licznikiem żądań po stronie
         * serwera (powinien wynosić 1, nie 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ConcurrentClientsShareSamePoolStats {
        /*
         * 🧪 Zadanie 23:
         * Używając JEDNEGO reużywalnego OkHttpClient, wykonaj sekwencyjnie
         * 10 żądań GET do tego samego kontekstu lokalnego serwera. Po
         * zakończeniu odczytaj client.connectionPool().connectionCount() i
         * wypisz - zaobserwuj, że liczba połączeń w puli jest MAŁA (dzięki
         * reużyciu), mimo 10 wykonanych żądań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ContentNegotiationByAcceptHeader {
        /*
         * 🧪 Zadanie 24:
         * Kontekst serwera sprawdza nagłówek żądania "Accept": jeśli zawiera
         * "application/json", zwraca '{"ok":true}', w przeciwnym razie
         * zwykły tekst "ok". Wyślij dwa żądania z różnymi wartościami
         * Accept (przez .header("Accept", ...)) i wypisz otrzymane ciała
         * dla obu przypadków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TinyResponseWrapperRecord {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj rekord SimpleResponse(int statusCode, String body)
         * oraz metodę get(OkHttpClient client, String url) zwracającą
         * SimpleResponse. Przetestuj metodę na co najmniej 2 różnych
         * kontekstach lokalnego serwera, wypisując pełne obiekty SimpleResponse.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_QueryParamsRoundTripWithHttpUrl {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj kontekst "/produkty" odczytujący parametry "kategoria"
         * i "limit" z query stringa i odsyłający je jako sformatowany tekst.
         * Zbuduj URL przez HttpUrl.Builder z dwoma parametrami, wykonaj
         * żądanie i wypisz odebrane ciało - zweryfikuj, że oba parametry
         * dotarły poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_HandleConnectionRefusedGracefully {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj Request wskazujący na port, na którym NIC nie nasłuchuje
         * (np. losowy wysoki port bez uruchomionego serwera). Wykonaj
         * execute() w try-catch, złap IOException (np. ConnectException) i
         * wypisz przyjazny komunikat błędu - main() musi zakończyć się
         * poprawnie mimo błędu połączenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareTwoEndpointsPerformance {
        /*
         * 🧪 Zadanie 28:
         * Zarejestruj kontekst "/szybki" (odpowiada natychmiast) i
         * "/wolny" (Thread.sleep(200) przed odpowiedzią). Zmierz czas
         * (System.nanoTime()) wykonania żądania do obu i wypisz porównanie
         * czasów w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildRequestFromExistingRequestNewBuilder {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj bazowy Request. Utwórz z niego DRUGI Request przez
         * request.newBuilder().header("X-Dodatkowy", "tak").build() -
         * zademonstruj, że oryginalny obiekt Request pozostaje NIEZMIENIONY
         * (immutability), a nowy zawiera dodatkowy nagłówek. Wypisz oba
         * zestawy nagłówków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMiniHttpClientWrapper {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj klasę SimpleHttpClient opakowującą JEDEN, reużywalny
         * OkHttpClient z metodami get(String url), getWithHeader(String url,
         * String name, String value) oraz getStatus(String url). Przetestuj
         * wszystkie trzy metody na co najmniej 3 kontekstach lokalnego
         * serwera, wypisując czytelne podsumowanie wyników.
         */
        public static void main(String[] args) { }
    }
}
