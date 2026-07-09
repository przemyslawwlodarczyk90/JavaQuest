package com.example.javaquest._13_libraries.Lesson13_OkHttpAsyncAndInterceptors;

public class _Exercises_Lesson13_OkHttpAsyncAndInterceptors {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FirstAsyncCall {
        /*
         * 🧪 Zadanie 1:
         * Uruchom lokalny com.sun.net.httpserver.HttpServer na porcie 0 z
         * kontekstem "/dane" zwracającym tekst "async-ok". Wykonaj GET
         * przez client.newCall(request).enqueue(Callback) i użyj
         * CountDownLatch(1), aby main() poczekał na wynik przed
         * zakończeniem. Wypisz odebrane ciało w onResponse.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OnFailureForConnectionRefused {
        /*
         * 🧪 Zadanie 2:
         * Zbuduj Request wskazujący na "http://localhost:1/brak" (port bez
         * nasłuchującego serwera) i wykonaj enqueue(). W onFailure wypisz
         * nazwę klasy złapanego wyjątku. Użyj CountDownLatch, żeby main()
         * poczekał na wynik (z rozsądnym timeoutem, np. connectTimeout(2,
         * TimeUnit.SECONDS) na kliencie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OnResponseCalledEvenFor404 {
        /*
         * 🧪 Zadanie 3:
         * Kontekst "/brak-zasobu" niech zwraca kod 404. Wykonaj żądanie
         * asynchronicznie i zademonstruj (przez CountDownLatch), że
         * onResponse JEST wywoływane (a NIE onFailure) - wypisz
         * response.code() wewnątrz onResponse.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SimpleLoggingInterceptor {
        /*
         * 🧪 Zadanie 4:
         * Napisz prosty Interceptor wypisujący przez System.out metodę i
         * URL każdego żądania PRZED chain.proceed(request) oraz kod
         * odpowiedzi PO. Zarejestruj go przez
         * OkHttpClient.Builder().addInterceptor(...) i wykonaj nim jedno
         * żądanie GET do dowolnego kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AuthHeaderInterceptor {
        /*
         * 🧪 Zadanie 5:
         * Napisz Interceptor dodający nagłówek
         * "Authorization: Bearer test-token" do KAŻDEGO żądania (przez
         * chain.request().newBuilder().header(...).build()). Kontekst
         * serwera "/sprawdz" niech odczyta ten nagłówek i odeśle go jako
         * ciało odpowiedzi. Zweryfikuj, że nagłówek dotarł, mimo że NIE
         * ustawiałeś go ręcznie przy budowie Request.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MeasureResponseTimeInInterceptor {
        /*
         * 🧪 Zadanie 6:
         * W interceptorze zmierz czas między chain.request() a otrzymaniem
         * Response z chain.proceed(request) (System.nanoTime()). Wypisz
         * czas w milisekundach. Kontekst serwera niech celowo śpi 100ms
         * przed odpowiedzią, żeby zmierzony czas był zauważalny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TwoInterceptorsOrder {
        /*
         * 🧪 Zadanie 7:
         * Zarejestruj DWA interceptory (przez dwa wywołania addInterceptor)
         * - każdy niech wypisuje swój numer ("Interceptor 1 - start" /
         * "Interceptor 2 - start" itd. przed i po chain.proceed). Wykonaj
         * jedno żądanie i zaobserwuj w konsoli KOLEJNOŚĆ wywołań
         * (interceptory tworzą łańcuch - pierwszy dodany opakowuje drugi).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleAsyncCallsWithSharedLatch {
        /*
         * 🧪 Zadanie 8:
         * Wykonaj TRZY niezależne żądania asynchroniczne (enqueue) do
         * trzech różnych kontekstów lokalnego serwera, używając JEDNEGO
         * CountDownLatch(3) (każdy callback wywołuje countDown()). Poczekaj
         * na wszystkie trzy (await()) i wypisz zebrane wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CallbackStoresResultInAtomicReference {
        /*
         * 🧪 Zadanie 9:
         * Wykonaj żądanie asynchroniczne, zapisując wynik (ciało odpowiedzi)
         * w AtomicReference<String> wewnątrz onResponse. Po await() na
         * CountDownLatch wypisz zawartość AtomicReference w main().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_InterceptorAddsCustomHeaderToAllRequests {
        /*
         * 🧪 Zadanie 10:
         * Zarejestruj interceptor dodający nagłówek "X-Klient: javaQuest-demo"
         * do KAŻDEGO żądania. Wykonaj DWA różne żądania (do dwóch różnych
         * kontekstów) tym samym klientem i zweryfikuj (przez echo po
         * stronie serwera), że nagłówek pojawił się w OBU przypadkach.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AsyncVsSyncTimingComparison {
        /*
         * 🧪 Zadanie 11:
         * Kontekst "/wolne" niech śpi 200ms. Wykonaj DWA żądania do niego -
         * jedno przez execute() (synchronicznie, mierząc czas blokady
         * main()), drugie przez enqueue() (asynchronicznie, z wypisaniem
         * komunikatu "main() kontynuuje" ZARAZ po enqueue(), przed
         * otrzymaniem wyniku). Porównaj w komentarzu różnicę w zachowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_InterceptorShortCircuitsOnCondition {
        /*
         * 🧪 Zadanie 12:
         * Napisz interceptor, który SPRAWDZA URL żądania (chain.request().url())
         * - jeśli ścieżka zawiera "/zablokowane", NIE wywołuje
         * chain.proceed(), tylko od razu buduje i zwraca własny obiekt
         * Response z kodem 403 (przez new Response.Builder()...). Wykonaj
         * żądanie do "/zablokowane" i zweryfikuj kod 403 BEZ faktycznego
         * wysłania żądania do serwera (licznik żądań po stronie serwera = 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RetryInterceptorOnServerError {
        /*
         * 🧪 Zadanie 13:
         * Kontekst serwera niech zwraca 500 dla pierwszych 2 wywołań, a 200
         * dla trzeciego. Napisz interceptor, który W PĘTLI (maks. 5 prób)
         * wywołuje chain.proceed(request), a jeśli kod odpowiedzi to 500,
         * zamyka odpowiedź i próbuje ponownie. Zweryfikuj, że finalna
         * odpowiedź ma kod 200.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_InterceptorLogsRequestCount {
        /*
         * 🧪 Zadanie 14:
         * Napisz interceptor trzymający AtomicInteger jako licznik
         * przechwyconych żądań (inkrementowany przy każdym wywołaniu
         * intercept()). Wykonaj 5 różnych żądań (mix execute/enqueue) tym
         * samym klientem i wypisz finalną wartość licznika (powinna wynosić 5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AsyncDownloadTwoUrlsInParallel {
        /*
         * 🧪 Zadanie 15:
         * Zarejestruj konteksty "/a" i "/b" zwracające różne teksty.
         * Uruchom OBA żądania asynchronicznie (enqueue) RÓWNOCZEŚNIE (bez
         * czekania na pierwsze przed wysłaniem drugiego), zbierz oba wyniki
         * do Map<String,String> przy pomocy CountDownLatch(2) i wypisz
         * posortowaną mapę po zakończeniu obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_InterceptorModifiesResponseHeader {
        /*
         * 🧪 Zadanie 16:
         * Napisz interceptor, który PO otrzymaniu odpowiedzi z
         * chain.proceed() buduje NOWY obiekt Response (przez
         * response.newBuilder().header("X-Przetworzone", "tak").build())
         * dodający własny nagłówek do odpowiedzi. Wykonaj żądanie i wypisz
         * response.header("X-Przetworzone") po stronie klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CancelAsyncCallBeforeCompletion {
        /*
         * 🧪 Zadanie 17:
         * Kontekst "/wolne" niech śpi 2 sekundy. Wykonaj żądanie
         * asynchroniczne (enqueue), a NATYCHMIAST po jego wysłaniu wywołaj
         * call.cancel() na zwróconym obiekcie Call. Zweryfikuj w onFailure,
         * że otrzymano wyjątek związany z anulowaniem (np. sprawdź
         * call.isCanceled()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ChainOfThreeInterceptorsWithSharedState {
        /*
         * 🧪 Zadanie 18:
         * Zarejestruj TRZY interceptory: pierwszy dodaje nagłówek
         * "X-Krok: 1", drugi loguje URL, trzeci mierzy czas. Wykonaj jedno
         * żądanie i zademonstruj (kontekst serwera odsyła otrzymane
         * nagłówki jako ciało), że nagłówek dodany w pierwszym interceptorze
         * dotarł do serwera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AsyncCallWithTimeoutHandling {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj klienta z .callTimeout(300, TimeUnit.MILLISECONDS).
         * Kontekst "/bardzo-wolne" niech śpi 1 sekundę. Wykonaj żądanie
         * asynchroniczne i w onFailure zweryfikuj, że otrzymano wyjątek
         * związany z przekroczeniem czasu (np. InterruptedIOException).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NetworkInterceptorVsApplicationInterceptor {
        /*
         * 🧪 Zadanie 20:
         * Zarejestruj JEDEN interceptor przez addInterceptor() i JEDEN
         * przez addNetworkInterceptor() - każdy niech wypisuje inny
         * komunikat. Wykonaj jedno żądanie i w komentarzu opisz
         * zaobserwowaną kolejność/kontekst wywołań obu interceptorów.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AsyncFanOutToFiveEndpoints {
        /*
         * 🧪 Zadanie 21:
         * Zarejestruj 5 kontekstów zwracających różne stałe wartości
         * liczbowe jako tekst. Wykonaj 5 żądań asynchronicznych
         * RÓWNOCZEŚNIE, zsumuj odebrane wartości (parsowane z ciała
         * odpowiedzi) w AtomicInteger, korzystając z CountDownLatch(5).
         * Wypisz sumę końcową po await().
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_InterceptorBasedRateLimiter {
        /*
         * 🧪 Zadanie 22:
         * Napisz interceptor działający jako prosty "rate limiter" -
         * jeśli licznik żądań (AtomicInteger) przekroczy 3 w danym
         * uruchomieniu, interceptor zwraca własny Response z kodem 429
         * BEZ wywołania chain.proceed(). Wykonaj 5 żądań pod rząd i
         * zweryfikuj, że pierwsze 3 mają kod 200, a kolejne 2 - kod 429.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CircuitBreakerStyleInterceptor {
        /*
         * 🧪 Zadanie 23:
         * Kontekst serwera niech zawsze zwraca 500. Napisz interceptor
         * zliczający KOLEJNE błędy (500) - po 3 błędach z rzędu, kolejne
         * wywołania interceptora NATYCHMIAST zwracają Response 503 z
         * ciałem "circuit open" BEZ wysyłania żądania do serwera (jak
         * uproszczony "circuit breaker"). Zademonstruj to zachowanie na
         * 5 kolejnych wywołaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AsyncPipelineWithDependentCalls {
        /*
         * 🧪 Zadanie 24:
         * Kontekst "/id" zwraca identyfikator tekstowy (np. "42"). Kontekst
         * "/szczegoly/{id}" (zbuduj URL dynamicznie) zwraca dane powiązane
         * z danym id. Wykonaj PIERWSZE żądanie asynchronicznie, a w jego
         * onResponse - na podstawie otrzymanego id - wykonaj DRUGIE żądanie
         * asynchroniczne (zagnieżdżone enqueue). Użyj CountDownLatch(1)
         * zwalnianego dopiero po zakończeniu DRUGIEGO żądania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_InterceptorAddsCorrelationId {
        /*
         * 🧪 Zadanie 25:
         * Napisz interceptor generujący unikalne "correlation ID"
         * (np. UUID.randomUUID()) dla KAŻDEGO żądania, dodawany jako
         * nagłówek "X-Correlation-Id", oraz logujący go razem z metodą i
         * URL-em. Kontekst serwera niech odeśle otrzymany nagłówek z
         * powrotem jako ciało odpowiedzi. Wykonaj 3 żądania i zweryfikuj,
         * że każde ma INNY correlation ID.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TimeoutRaceBetweenTwoAsyncCalls {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj konteksty "/szybkie" (natychmiastowa odpowiedź) i
         * "/wolne2" (śpi 500ms). Wykonaj OBA żądania asynchronicznie i
         * zaimplementuj logikę "kto pierwszy, ten wygrywa" - zapisz w
         * AtomicReference<String> NAZWĘ endpointu, którego callback
         * wykonał się jako pierwszy (użyj CountDownLatch(1) zwalnianego
         * TYLKO przez pierwszy zakończony callback).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_InterceptorChainChangesRequestMethod {
        /*
         * 🧪 Zadanie 27:
         * Kontekst serwera niech obsługuje zarówno GET jak i POST na tej
         * samej ścieżce, zwracając informację o metodzie w ciele
         * odpowiedzi. Napisz interceptor, który przechwytuje żądania GET
         * do konkretnej ścieżki i PRZEBUDOWUJE je na POST (z pustym body)
         * przed chain.proceed(). Zweryfikuj po stronie klienta, że serwer
         * faktycznie zobaczył POST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullAsyncBatchProcessorWithErrorCollection {
        /*
         * 🧪 Zadanie 28:
         * Zarejestruj 4 konteksty, z których 1 zawsze zwraca błąd (500) a
         * pozostałe 3 sukces. Wykonaj wszystkie 4 żądania asynchronicznie,
         * zbieraj OSOBNO listę sukcesów i listę błędów (dwie
         * thread-safe kolekcje, np. z java.util.concurrent), używając
         * CountDownLatch(4). Po zakończeniu wypisz podsumowanie: liczba
         * sukcesów i liczba błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_InterceptorBasedRequestResponseAudit {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj interceptor prowadzący pełny "dziennik audytowy" (lista
         * rekordów AuditEntry(String method, String url, int statusCode,
         * long durationMs) w thread-safe liście) dla KAŻDEGO żądania
         * wykonanego danym klientem. Wykonaj 5 różnych żądań (mix
         * execute/enqueue) i na koniec wypisz PEŁNY dziennik audytowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullResilientClientWithAuthLoggingAndRetry {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj JEDEN OkHttpClient łączący WSZYSTKIE mechanizmy z tej
         * lekcji: interceptor autoryzacji (dodaje token), interceptor
         * logujący (metoda/URL/czas/kod), interceptor z retry przy
         * błędach 500 (maks. 3 próby). Wykonaj nim serię 3 różnych żądań
         * asynchronicznych (enqueue + CountDownLatch) do różnych
         * kontekstów lokalnego serwera (w tym jeden zwracający błędy dla
         * pierwszych prób) i wypisz pełne podsumowanie wyników na końcu.
         */
        public static void main(String[] args) { }
    }
}
