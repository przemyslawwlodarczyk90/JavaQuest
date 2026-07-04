package com.example.javaquest._07_servlets.Lesson14_Filters;

public class _Exercises_Lesson14_Filters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicLoggingFilter {
        /*
         * 🧪 Zadanie 1:
         * Uruchom osadzony Tomcat (port 0) z jednym serwletem pod "/ping" oraz jednym
         * filtrem zarejestrowanym na wzorzec "/*" (FilterDef+FilterMap), który loguje
         * "PRZED" przed chain.doFilter(...) i "PO" po nim. Wyślij jedno zadanie GET /ping,
         * wypisz status i treść odpowiedzi, zatrzymaj Tomcat (stop()+destroy()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_HeaderAddingFilter {
        /*
         * 🧪 Zadanie 2:
         * Napisz filtr, który PRZED wywołaniem chain.doFilter(...) ustawia na
         * HttpServletResponse nagłówek "X-Powered-By: JavaQuest". Zarejestruj go dla
         * serwletu pod "/hello", wyślij GET, odczytaj nagłówek z odpowiedzi klienta
         * (response.headers().firstValue("X-Powered-By")) i wypisz jego wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RequestAttributeFilter {
        /*
         * 🧪 Zadanie 3:
         * Filtr generuje losowy identyfikator (np. UUID.randomUUID().toString()) i ustawia
         * go jako atrybut request.setAttribute("requestId", ...) przed chain.doFilter(...).
         * Serwlet pod "/echo" odczytuje ten atrybut i zwraca go w treści odpowiedzi.
         * Wyślij zadanie i wypisz otrzymany identyfikator.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_URLPatternMapping {
        /*
         * 🧪 Zadanie 4:
         * Zarejestruj filtr logujący tylko dla wzorca "/secure/*". Utwórz dwa serwlety:
         * jeden pod "/secure/data", drugi pod "/public/data". Wyślij GET do obu, po czym
         * wypisz, dla którego z nich w konsoli pojawiły się logi filtra (filtr NIE powinien
         * zadziałać dla "/public/data").
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_MultipleServletsSharedFilter {
        /*
         * 🧪 Zadanie 5:
         * Zarejestruj jeden filtr na wzorzec "/*" oraz dwa serwlety: "/a" i "/b".
         * Wyślij GET do obu adresów i potwierdź (logi filtra + odpowiedzi), że ten sam
         * filtr obsłużył oba zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FilterCountingRequests {
        /*
         * 🧪 Zadanie 6:
         * Filtr trzyma pole AtomicInteger inkrementowane w każdym wywołaniu doFilter().
         * Zarejestruj go dla serwletu "/hit", wyślij GET do niego 3 razy pod rząd i po
         * każdym zadaniu wypisz aktualną wartość licznika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FilterSettingCharacterEncoding {
        /*
         * 🧪 Zadanie 7:
         * Filtr wywołuje request.setCharacterEncoding("UTF-8") przed chain.doFilter(...).
         * Serwlet pod "/greet" odczytuje parametr zapytania "imie" (np. "Kraków") i odsyła
         * go w treści odpowiedzi. Wyślij GET /greet?imie=Kraków i sprawdź, czy polskie
         * znaki wróciły poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FilterTimingWrapper {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj filtr mierzący czas (jak TimingFilter z teorii), ale owiń nim serwlet,
         * który celowo wykonuje Thread.sleep(200) przed odpowiedzią (symulacja pracy).
         * Wypisz zmierzony czas obsługi zadania (powinien wynosić co najmniej ~200 ms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_InitParamFilter {
        /*
         * 🧪 Zadanie 9:
         * Ustaw na FilterDef parametr inicjalizacyjny filterDef.addInitParameter("greeting",
         * "Czesc"). W metodzie init(FilterConfig) filtra odczytaj ten parametr
         * (config.getInitParameter("greeting")) i wypisz go w konsoli przy starcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_FilterDestroyLifecycle {
        /*
         * 🧪 Zadanie 10:
         * Napisz filtr logujący komunikaty w init() i destroy(). Uruchom Tomcat, wyślij
         * jedno zadanie do prostego serwletu, a następnie jawnie wywołaj tomcat.stop() i
         * tomcat.destroy(). Wypisz w konsoli pełną kolejność zdarzeń: init -> doFilter ->
         * destroy.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoFiltersOrderDemo {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj DWA filtry (FilterA i FilterB), oba na wzorzec "/*", każdy logujący
         * "PRZED"/"PO" z własną nazwą. Wyślij jedno zadanie do serwletu i wypisz pełną,
         * zaobserwowaną kolejność logów, potwierdzającą zagnieżdżenie łańcucha
         * (A-przed -> B-przed -> serwlet -> B-po -> A-po).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FilterShortCircuitAuth {
        /*
         * 🧪 Zadanie 12:
         * Napisz filtr sprawdzający nagłówek "X-Api-Key". Jeśli brakuje go lub ma złą
         * wartość, filtr USTAWIA status 401 i pisze treść odpowiedzi BEZ wywołania
         * chain.doFilter(...). Wyślij dwa zadania do chronionego serwletu: bez nagłówka
         * (oczekuj 401) i z poprawnym nagłówkiem (oczekuj 200 od serwletu docelowego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FiltersOnDifferentPatterns {
        /*
         * 🧪 Zadanie 13:
         * Zarejestruj dwa filtry z osobnymi parametrami init (np. nazwa modułu) na dwóch
         * różnych wzorcach: "/api/*" i "/admin/*", oraz po jednym serwlecie pod każdym z
         * tych prefiksów. Wyślij zadania do obu i potwierdź, że każdy filtr uruchamia się
         * tylko dla swojego wzorca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FilterValidatesRequiredParam {
        /*
         * 🧪 Zadanie 14:
         * Filtr sprawdza obecność parametru zapytania "userId". Gdy go brak, ustawia
         * status 400 i kończy bez wywołania chain.doFilter(...); gdy jest obecny,
         * przepuszcza dalej. Wyślij dwa zadania: bez parametru i z parametrem "userId=7",
         * porównaj statusy odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FilterMappedByServletName {
        /*
         * 🧪 Zadanie 15:
         * Zarejestruj dwa serwlety: "servletA" pod "/a" i "servletB" pod "/b". Zamapuj
         * filtr NIE przez addURLPattern, lecz przez filterMap.addServletName("servletA") –
         * czyli po nazwie serwletu. Wyślij zadania do obu adresów i potwierdź, że filtr
         * odpalił się tylko dla "/a".
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CountingFilterAcrossEndpoints {
        /*
         * 🧪 Zadanie 16:
         * Jeden filtr na wzorzec "/*" z polem AtomicInteger (wspólny licznik). Zarejestruj
         * dwa serwlety "/one" i "/two". Wyślij GET /one raz i GET /two dwa razy, po czym
         * wypisz końcową wartość licznika (powinna wynosić 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FilterAddsMultipleHeadersFromInitParams {
        /*
         * 🧪 Zadanie 17:
         * Ustaw na FilterDef dwa init-parametry: "appName"="JavaQuest" i "appVersion"="1.0".
         * Filtr w init() zapamiętuje je w polach instancji, a w doFilter() ustawia oba jako
         * nagłówki odpowiedzi (X-App-Name, X-App-Version) przed chain.doFilter(...).
         * Wyślij zadanie i wypisz odczytane nagłówki z odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConditionalFilterByHttpMethod {
        /*
         * 🧪 Zadanie 18:
         * Filtr sprawdza httpRequest.getMethod() i loguje różny komunikat dla GET i dla
         * POST. Zarejestruj serwlet obsługujący oba doGet i doPost pod "/mixed". Wyślij
         * jedno zadanie GET i jedno POST, porównaj logi filtra dla obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TwoFiltersInitDestroyOrder {
        /*
         * 🧪 Zadanie 19:
         * Zarejestruj dwa filtry (oba logujące init()/destroy()) w określonej kolejności
         * rejestracji. Uruchom i zatrzymaj Tomcat, wypisz zaobserwowaną kolejność wywołań
         * init() przy starcie oraz destroy() przy zatrzymaniu obu filtrów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FilterWithTryFinallyCleanup {
        /*
         * 🧪 Zadanie 20:
         * Serwlet pod "/boom" celowo rzuca RuntimeException. Filtr opakowuje wywołanie
         * chain.doFilter(...) w blok try/finally, tak by komunikat "PO" zawsze się
         * wypisał, nawet gdy serwlet rzuci wyjątek. Wyślij zadanie, obsłuż odpowiedź
         * błędu po stronie klienta i potwierdź, że log "PO" faktycznie się pojawił.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AuthFilterWithSessionCheck {
        /*
         * 🧪 Zadanie 21:
         * Serwlet "/login" ustawia atrybut sesji "loggedIn"=true (request.getSession(true)).
         * Filtr chroniący "/profile" sprawdza obecność tego atrybutu w sesji – brak sesji
         * lub atrybutu skutkuje statusem 401 bez wywołania chain.doFilter(...). Użyj
         * HttpClient z CookieHandler, by sesja (cookie JSESSIONID) przetrwała między
         * zadaniami: najpierw GET /login, potem GET /profile (oczekuj 200).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiFilterPipelineWithMetrics {
        /*
         * 🧪 Zadanie 22:
         * Zarejestruj 3 filtry na "/*": logujący, mierzący czas (dodaje nagłówek
         * X-Response-Time) i generujący identyfikator zadania (dodaje nagłówek
         * X-Request-Id). Wyślij jedno zadanie do serwletu i wypisz wartości obu
         * nagłówków z odpowiedzi, potwierdzając, że wszystkie trzy filtry zadziałały.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FilterExcludingStaticLikePaths {
        /*
         * 🧪 Zadanie 23:
         * Filtr na "/*" wykonuje swoją logikę (logowanie) TYLKO, gdy
         * httpRequest.getRequestURI() NIE kończy się na ".png". Zarejestruj dwa
         * serwlety: "/resource/data.txt" i "/resource/image.png". Wyślij zadania do obu i
         * potwierdź, że logi filtra pojawiły się tylko dla pierwszego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RateLimitingFilterSimplified {
        /*
         * 🧪 Zadanie 24:
         * Filtr trzyma AtomicInteger jako prosty licznik zadań w ramach tego uruchomienia.
         * Po przekroczeniu 3 zadań zwraca status 429 zamiast wywołać chain.doFilter(...).
         * Wyślij 4 kolejne zadania GET do tego samego serwletu i wypisz status każdej
         * odpowiedzi (oczekuj 200,200,200,429).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FilterChainChangingRequestWrapper {
        /*
         * 🧪 Zadanie 25:
         * Napisz HttpServletRequestWrapper nadpisujący getParameter(String), który zwraca
         * wartość domyślną "anonim", gdy parametr "user" nie został przesłany. Filtr
         * tworzy taki wrapper i przekazuje go do chain.doFilter(wrapper, response) zamiast
         * oryginalnego żądania. Serwlet odczytuje parametr "user" - wyślij zadanie BEZ
         * tego parametru i potwierdź, że serwlet zobaczył wartość "anonim".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FilterChainOrderControlledByRegistration {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj 3 filtry (C, A, B - celowo w takiej kolejności) na "/*" wobec
         * jednego serwletu, każdy logujący swoją literę przy wejściu i wyjściu. Wyślij
         * jedno zadanie i wypisz zaobserwowaną kolejność, potwierdzając, że łańcuch
         * filtrów wykonuje się w KOLEJNOŚCI REJESTRACJI (C, A, B), a nie alfabetycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CombinedTimingAndAuthFilters {
        /*
         * 🧪 Zadanie 27:
         * Połącz filtr mierzący czas (zewnętrzny w łańcuchu) z filtrem autoryzacji
         * (wewnętrzny, blokujący brak nagłówka X-Api-Key) wobec jednego chronionego
         * serwletu. Wyślij zadanie autoryzowane i nieautoryzowane, potwierdź, że filtr
         * czasu loguje zmierzony czas w OBU przypadkach, niezależnie od wyniku autoryzacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ResponseWrapperCapturingOutput {
        /*
         * 🧪 Zadanie 28:
         * Napisz HttpServletResponseWrapper z własnym CharArrayWriter jako podmienionym
         * PrintWriter (getWriter() zwraca writer nad tym buforem). Filtr używa tego
         * wrappera zamiast oryginalnej odpowiedzi w chain.doFilter(wrapper), a PO
         * zakończeniu łańcucha odczytuje zebraną treść z bufora, zamienia ją na wielkie
         * litery i dopiero wtedy zapisuje do PRAWDZIWEJ odpowiedzi. Zweryfikuj, że klient
         * otrzymał tekst w wielkich literach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ErrorHandlingFilterConvertsExceptionToText {
        /*
         * 🧪 Zadanie 29:
         * Filtr opakowuje chain.doFilter(...) w try/catch – złapany wyjątek zamienia na
         * czytelną, ustrukturyzowaną odpowiedź tekstową (status 500, treść zawierająca
         * nazwę klasy wyjątku i jego komunikat) zamiast domyślnej strony błędu kontenera.
         * Serwlet pod "/fail" rzuca wyjątek z niestandardowym komunikatem - zweryfikuj
         * treść finalnej odpowiedzi klienta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullPipelineAuthRateLimitWrapperTiming {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-kapstone: zarejestruj razem filtr autoryzacji (nagłówek X-Api-Key),
         * filtr rate-limitingu (limit 2 zadania), filtr opakowujący odpowiedź (jak w
         * Zadaniu 28) oraz filtr mierzący czas - wszystkie wokół jednego chronionego
         * serwletu. Wyślij serię zadań: nieautoryzowane, autoryzowane (x2, powinny przejść)
         * i trzecie autoryzowane (zablokowane rate-limitem). Wypisz pełny raport statusów
         * i nagłówków dla każdego zadania.
         */
        public static void main(String[] args) { }
    }
}
