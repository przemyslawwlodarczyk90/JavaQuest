package com.example.javaquest._07_servlets.Lesson17_ForwardAndRedirect;

public class _Exercises_Lesson17_ForwardAndRedirect {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicForwardTwoServlets {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj serwlet źródłowy "/source", który przez
         * request.getRequestDispatcher("/target").forward(req, resp) przekazuje
         * sterowanie do serwletu "/target". Wyślij jedno zadanie GET /source i
         * zweryfikuj, że treść odpowiedzi pochodzi z serwletu docelowego, w jednym
         * cyklu HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BasicRedirect {
        /*
         * 🧪 Zadanie 2:
         * Serwlet "/source" wywołuje response.sendRedirect("/target"). Użyj domyślnego
         * HttpClient (bez followRedirects) i wyślij GET /source - zweryfikuj status 302
         * oraz wartość nagłówka Location równą "/target".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FollowRedirectClient {
        /*
         * 🧪 Zadanie 3:
         * Ten sam serwlet "/source" z Zadania 2 (redirect na "/target"), ale tym razem
         * użyj HttpClient zbudowanego z
         * .followRedirects(HttpClient.Redirect.NORMAL). Wyślij GET /source i zweryfikuj
         * finalny status 200, treść z serwletu docelowego oraz response.uri() wskazujące
         * "/target".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ForwardAttributePassthrough {
        /*
         * 🧪 Zadanie 4:
         * Serwlet "/source" ustawia request.setAttribute("wiadomosc", "test-4") PRZED
         * forward() do "/target". Serwlet "/target" odczytuje ten atrybut i zwraca go w
         * odpowiedzi. Zweryfikuj, że wartość "test-4" dotarła do serwletu docelowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RedirectAttributeLost {
        /*
         * 🧪 Zadanie 5:
         * Serwlet "/source" ustawia request.setAttribute("wiadomosc", "test-5") PRZED
         * sendRedirect("/target"). Serwlet "/target" próbuje odczytać ten sam atrybut z
         * SWOJEGO (nowego) obiektu request. Użyj klienta z followRedirects(NORMAL) i
         * zweryfikuj, że atrybut jest null (nowy request przy drugim zadaniu HTTP).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ForwardDoesNotChangeStatusCode {
        /*
         * 🧪 Zadanie 6:
         * Serwlet docelowy ustawia resp.setStatus(201) przed napisaniem treści. Serwlet
         * źródłowy robi forward() do niego bez modyfikacji statusu. Zweryfikuj, że klient
         * finalnie widzi status 201 (forward przenosi status ustawiony w serwlecie
         * docelowym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ForwardVsRedirectResponseBodyComparison {
        /*
         * 🧪 Zadanie 7:
         * W jednym uruchomieniu wykonaj OBA scenariusze: forward (Zadanie 1) i redirect z
         * followRedirects (Zadanie 3). Wypisz obie treści odpowiedzi jedna pod drugą z
         * czytelną etykietą, porównując rezultat mimo różnych mechanizmów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RedirectToConcretePath {
        /*
         * 🧪 Zadanie 8:
         * Serwlet "/submit" (symulujący obsłużenie formularza) wywołuje
         * sendRedirect("/thanks") po zalogowaniu komunikatu w konsoli. Wyślij GET /submit
         * bez podążania za przekierowaniem i zweryfikuj dokładną wartość nagłówka
         * Location.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ForwardChainThroughTwoServlets {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj ServletA, który forward()'uje do ServletB, a ServletB z kolei
         * forward()'uje do ServletC. Wyślij jedno zadanie GET do ServletA i zweryfikuj,
         * że finalna treść odpowiedzi pochodzi z ServletC, w ramach JEDNEGO zadania HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MultipleRedirectsChain {
        /*
         * 🧪 Zadanie 10:
         * ServletA przekierowuje na ServletB, a ServletB przekierowuje na ServletC. Użyj
         * klienta z followRedirects(NORMAL), wyślij GET do ServletA i zweryfikuj, że
         * klient podążył za OBOMA przekierowaniami, kończąc na treści z ServletC z
         * response.uri() wskazującym na jego ścieżkę.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PostRedirectGetPattern {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj wzorzec Post/Redirect/Get: POST "/submit-form" loguje "formularz
         * przetworzony" i wywołuje sendRedirect("/thank-you"); GET "/thank-you" zwraca
         * treść potwierdzenia. Wyślij POST z followRedirects(NORMAL) i zweryfikuj finalny
         * status oraz treść potwierdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ForwardWithQueryParamsPreserved {
        /*
         * 🧪 Zadanie 12:
         * Wyślij GET "/source?name=Jan". Serwlet "/source" wywołuje forward() do
         * "/target" BEZ modyfikacji parametrów. Serwlet "/target" odczytuje
         * req.getParameter("name") i zwraca go - zweryfikuj, że parametr zapytania
         * przetrwał forward (to wciąż ten sam obiekt request).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RedirectWithQueryParamManuallyAppended {
        /*
         * 🧪 Zadanie 13:
         * Serwlet "/source" wywołuje sendRedirect("/target?id=42") - ręcznie doklejając
         * parametr do URL-a przekierowania (bo atrybuty by przepadły). Serwlet "/target"
         * odczytuje parametr "id" z URL-a. Użyj klienta z followRedirects(NORMAL) i
         * zweryfikuj odczytaną wartość "42".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ForwardWithFilterLoggingOnce {
        /*
         * 🧪 Zadanie 14:
         * Zarejestruj filtr (z Lekcji 14) na wzorzec "/*" logujący "PRZED"/"PO" z licznikiem
         * AtomicInteger. Serwlet "/source" wywołuje forward() do "/target". Wyślij jedno
         * zadanie GET /source i zweryfikuj, że filtr zalogował się DOKŁADNIE RAZ (forward
         * po stronie serwera nie generuje nowego zadania HTTP dla domyślnego typu
         * dyspozycji REQUEST).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RedirectTriggersFilterTwice {
        /*
         * 🧪 Zadanie 15:
         * Ten sam filtr liczący co w Zadaniu 14, ale tym razem "/source" wywołuje
         * sendRedirect("/target"). Użyj klienta z followRedirects(NORMAL), wyślij GET
         * /source i zweryfikuj, że filtr zalogował się DWA RAZY (dwa osobne zadania
         * HTTP: do "/source" i do "/target").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ForwardPreservesRequestMethod {
        /*
         * 🧪 Zadanie 16:
         * Wyślij zadanie POST do "/source", który wywołuje forward() do "/target".
         * Serwlet "/target" implementuje TYLKO doPost (rzuca błąd/405 w doGet). Zweryfikuj,
         * że forward poprawnie zachował metodę POST i doPost serwletu docelowego
         * faktycznie się wykonał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RedirectFollowedRequestBecomesGet {
        /*
         * 🧪 Zadanie 17:
         * Wyślij POST do serwletu "/source", który natychmiast wywołuje
         * sendRedirect("/target"). Serwlet "/target" implementuje TYLKO doGet. Użyj
         * klienta z followRedirects(NORMAL) i zweryfikuj, że podążenie za
         * przekierowaniem 302 skutkuje wywołaniem doGet na "/target" (a nie doPost).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ForwardSessionVsRequestAttributeScope {
        /*
         * 🧪 Zadanie 18:
         * Serwlet "/source" ustawia ZARÓWNO atrybut sesji, jak i atrybut request przed
         * forward() do "/target" (który odczytuje oba). Następnie wyślij OSOBNE, nowe
         * zadanie GET do trzeciego serwletu "/other" (ten sam klient/cookie), który
         * odczytuje TYLKO atrybut sesji (wciąż obecny) i próbuje odczytać atrybut request
         * (nieobecny) - zweryfikuj oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConditionalForwardOrRedirectByParam {
        /*
         * 🧪 Zadanie 19:
         * Jeden serwlet "/route" sprawdza parametr zapytania "mode": dla "forward" robi
         * RequestDispatcher forward() do "/target", dla "redirect" robi
         * sendRedirect("/target"). Wyślij dwa zadania (mode=forward i mode=redirect,
         * klient z followRedirects) i porównaj otrzymane statusy/response.uri().
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ForwardToErrorServletOnValidationFailure {
        /*
         * 🧪 Zadanie 20:
         * Serwlet "/source" waliduje obecność wymaganego parametru "id". Gdy go brak,
         * wywołuje forward() do dedykowanego serwletu błędu, który pisze komunikat i
         * ustawia status 400; gdy jest obecny, forward()'uje do normalnego serwletu
         * docelowego. Wyślij dwa osobne zadania (z parametrem i bez) i zweryfikuj obie
         * ścieżki.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullPipelineFilterPlusForwardOrRedirect {
        /*
         * 🧪 Zadanie 21:
         * Zarejestruj filtr mierzący czas (Lekcja 14) na "/*". Serwlet "/go" wybiera
         * forward lub redirect na podstawie nagłówka żądania "X-Mode". Wyślij dwa
         * zadania (jedno z każdym trybem) i zweryfikuj, że log czasu filtra poprawnie
         * opakowuje CAŁĄ obsługę w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ListenerLoggingCombinedWithForwardRedirectDemo {
        /*
         * 🧪 Zadanie 22:
         * Dodaj ServletContextListener (Lekcja 15) logujący start/stop aplikacji.
         * Przeprowadź w tym samym cyklu życia Tomcata zarówno scenariusz forward, jak i
         * scenariusz redirect (z followRedirects). Na końcu wypisz połączony raport:
         * logi listenera + logi obu przepływów w kolejności wystąpienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_IllegalDoubleDispatchAfterCommit {
        /*
         * 🧪 Zadanie 23:
         * Napisz serwlet, który próbuje wywołać sendRedirect(...) PO tym, jak
         * odpowiedź została już częściowo zapisana/zatwierdzona (np. po
         * resp.getWriter().flush()). Przechwyć i zaloguj IllegalStateException, jeśli
         * zostanie rzucony, oraz porównaj z poprawną wersją, w której redirect następuje
         * PRZED jakimkolwiek zapisem do odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AuthFilterProtectingForwardTarget {
        /*
         * 🧪 Zadanie 24:
         * Filtr autoryzacji (wzorzec z Lekcji 14) blokuje zadania bez nagłówka
         * "X-Api-Key" statusem 401 PRZED dotarciem do serwletu "/source" (który normalnie
         * forward()'uje do "/target"). Wyślij zadanie bez nagłówka (oczekuj 401, forward
         * nigdy się nie wykonuje) i z poprawnym nagłówkiem (oczekuj forwardu do końca).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RedirectLoopDetectionSafely {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj dwa serwlety przekierowujące WZAJEMNIE do siebie (A->B->A->...). Napisz
         * WŁASNĄ, ograniczoną pętlę klienta (HttpClient z Redirect.NEVER), ręcznie
         * podążającą za nagłówkiem Location, z limitem MAKSYMALNIE 3 skoków - po
         * przekroczeniu limitu przerwij pętlę i wypisz "wykryto możliwą pętlę
         * przekierowań" zamiast zawiesić program.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ForwardWithRequestWrapperInjectingHeader {
        /*
         * 🧪 Zadanie 26:
         * Napisz HttpServletRequestWrapper nadpisujący getHeader(String), zwracający
         * podmienioną wartość dla konkretnego nagłówka (np. "X-User-Role" zawsze
         * "ADMIN"). Serwlet "/source" tworzy taki wrapper i forward()'uje go (zamiast
         * oryginalnego request) do "/target", który odczytuje ten nagłówek i zwraca go w
         * odpowiedzi - zweryfikuj podmienioną wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ComparePerformanceForwardVsRedirect {
        /*
         * 🧪 Zadanie 27:
         * Zmierz System.nanoTime() wokół pełnego wywołania client.send(...) dla
         * scenariusza forward (jedno zadanie) oraz dla scenariusza redirect z
         * followRedirects (dwa zadania). Wypisz oba zmierzone czasy w milisekundach,
         * ilustrując różnicę kosztu "1 zadanie vs 2 zadania".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SessionBasedPostRedirectGetWithFlashMessage {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj wzorzec "flash message": serwlet "/submit" zapisuje
         * jednorazowy komunikat w SESJI przed sendRedirect("/result"). Serwlet
         * "/result" odczytuje ten atrybut z sesji I NATYCHMIAST go usuwa
         * (session.removeAttribute). Wyślij zadanie do "/submit" (z followRedirects),
         * a potem TRZECIE, osobne zadanie GET do "/result" tym samym klientem -
         * zweryfikuj, że komunikat pojawił się tylko RAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ForwardVsRedirectDecisionServiceLayer {
        /*
         * 🧪 Zadanie 29:
         * Wydziel logikę z Zadania 19 do osobnej metody pomocniczej
         * shouldForward(HttpServletRequest), używanej przez serwlet-kontroler - sam
         * serwlet ma być deklaratywny (tylko wywołuje metodę i wykonuje odpowiedni
         * dispatch). Przetestuj kilka różnych wejść (parametry/nagłówki) sterujących
         * decyzją i zweryfikuj poprawny wybór mechanizmu dla każdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneMiniControllerWithFullPipeline {
        /*
         * 🧪 Zadanie 30:
         * Kapstone: połącz ServletContextListener logujący cykl życia aplikacji, filtr
         * logujący/mierzący czas dla wszystkich zadań, serwlet logowania, który przy
         * sukcesie forward()'uje do dashboardu (z przekazaniem atrybutu), a przy
         * niepowodzeniu przekierowuje na stronę błędu logowania z komunikatem flash w
         * sesji, oraz chroniony endpoint "/dashboard". Przeprowadź scenariusze udanego i
         * nieudanego logowania i wypisz końcowy raport statusów, adresów URI i
         * zmierzonych czasów dla całego przebiegu.
         */
        public static void main(String[] args) { }
    }
}
