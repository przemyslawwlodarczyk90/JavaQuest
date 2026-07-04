package com.example.javaquest._07_servlets.Lesson07_GetAndPost;

public class _Exercises_Lesson07_GetAndPost {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SameServletBothMethods {
        /*
         * 🧪 Zadanie 1:
         * Utwórz serwlet z doGet() zwracającym "Odczyt przez GET" i doPost()
         * zwracającym "Zapis przez POST". Zamapuj pod "/dane", wykonaj kolejno GET i
         * POST na tę samą ścieżkę i wypisz oba ciała odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetDataInQueryString {
        /*
         * 🧪 Zadanie 2:
         * Utwórz serwlet, którego doGet() odczytuje parametr "fraza" i zwraca
         * "Szukam: " + fraza. Zamapuj pod "/wyszukaj", wykonaj GET na
         * "/wyszukaj?fraza=serwlety" i wypisz PEŁNY adres URL żądania oraz ciało
         * odpowiedzi - zwróć uwagę, że dane są widoczne w samym URL-u.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PostDataInBodyNotUrl {
        /*
         * 🧪 Zadanie 3:
         * Utwórz serwlet, którego doPost() odczytuje ciało żądania jako tekst i
         * zwraca "Otrzymano w ciele: " + tresc. Zamapuj pod "/wyszukaj-post"
         * (BEZ żadnych parametrów w URL-u), wyślij POST z ciałem "serwlety" i wypisz
         * zarówno użyty URL (bez danych), jak i ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetIdempotentRepeatedCalls {
        /*
         * 🧪 Zadanie 4:
         * Utwórz serwlet pod "/tylko-odczyt" (doGet), który TYLKO zwraca stały tekst
         * "Dane niezmienne" bez modyfikowania żadnego stanu. Wykonaj to samo żądanie
         * GET 3 razy pod rząd i porównaj wszystkie 3 odpowiedzi (println equals) -
         * powinny być identyczne, demonstrując idempotentność GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_PostNotIdempotentSideEffect {
        /*
         * 🧪 Zadanie 5:
         * Utwórz serwlet pod "/zamow" (doPost), którego każde wywołanie zwiększa
         * pole AtomicInteger liczbaZamowien o 1 i zwraca "Utworzono zamowienie nr " +
         * wartosc. Wyślij TO SAMO żądanie POST 3 razy pod rząd i wypisz wszystkie 3
         * odpowiedzi - zwróć uwagę, że mimo identycznego żądania każde wywołanie ma
         * INNY efekt (numer rośnie), w przeciwieństwie do GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_QueryStringLengthObservation {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj URL GET z długim query stringiem (np. parametr "opis" z tekstem
         * powtórzonym 50 razy) i wykonaj żądanie GET na serwlet zwracający po prostu
         * "OK". Wypisz długość użytego URL-a (w znakach) jako obserwację, że dane w
         * GET są ograniczone praktycznymi limitami długości adresu URL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PostContentTypeFormUrlEncoded {
        /*
         * 🧪 Zadanie 7:
         * Utwórz serwlet, którego doPost() odczytuje nagłówek Content-Type żądania i
         * zwraca go jako część odpowiedzi. Zamapuj pod "/typ-formularza", wyślij POST
         * z nagłówkiem "Content-Type: application/x-www-form-urlencoded" i ciałem
         * "a=1&b=2", i wypisz odpowiedź potwierdzającą odczytany typ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BothMethodsSameEndpointDifferentSource {
        /*
         * 🧪 Zadanie 8:
         * Rozszerz jeden serwlet pod "/parametr-uniwersalny" tak, by doGet() i
         * doPost() OBA wołały tę samą prywatną metodę odczytującą parametr "klucz"
         * przez req.getParameter("klucz") (dla GET z query stringu, dla POST z
         * formularza x-www-form-urlencoded). Wyślij GET z "?klucz=abc" oraz POST z
         * ciałem "klucz=abc" (Content-Type odpowiedni), wypisz oba wyniki - powinny
         * być identyczne mimo różnych metod HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_GetCacheableHeaderDemo {
        /*
         * 🧪 Zadanie 9:
         * Utwórz serwlet pod "/cache-demo" (doGet), ustawiający nagłówek
         * "Cache-Control: max-age=60" jako demonstrację, że odpowiedzi GET mogą być
         * cache'owane. Wykonaj GET i wypisz wartość nagłówka Cache-Control z
         * odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PostNoCacheHeaderDemo {
        /*
         * 🧪 Zadanie 10:
         * Utwórz serwlet pod "/brak-cache" (doPost), ustawiający nagłówek
         * "Cache-Control: no-store" jako demonstrację typowego podejścia dla
         * żądań modyfikujących dane. Wykonaj POST i wypisz wartość nagłówka
         * Cache-Control z odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SameParameterNameBothMethods {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj serwlet SearchServlet (jak w teorii) pod "/szukaj-rozszerzony",
         * obsługujący parametry "fraza" i "strona" zarówno dla GET (query string),
         * jak i POST (formularz). Wykonaj oba żądania z tymi samymi wartościami
         * ("fraza=servlety", "strona=2") i wypisz oba wyniki, potwierdzając spójny
         * dostęp przez getParameter() niezależnie od metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GetWithSensitiveDataVisibleWarning {
        /*
         * 🧪 Zadanie 12:
         * Utwórz serwlet pod "/logowanie-get" (doGet), odczytujący parametry
         * "login" i "haslo" z query stringu i zwracający "Zalogowano: " + login.
         * Wykonaj GET na "/logowanie-get?login=user&haslo=sekret123" i wypisz PEŁNY
         * URL żądania - zwróć uwagę (komentarz + println), że hasło jest widoczne w
         * URL-u, co ilustruje, dlaczego dane wrażliwe NIE powinny iść przez GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PostWithSensitiveDataInBody {
        /*
         * 🧪 Zadanie 13:
         * Utwórz analogiczny serwlet pod "/logowanie-post" (doPost), odczytujący
         * "login" i "haslo" z ciała formularza (Content-Type
         * application/x-www-form-urlencoded). Wykonaj POST z ciałem
         * "login=user&haslo=sekret123" i wypisz użyty URL (bez danych) oraz ciało
         * odpowiedzi - porównaj (println) z Zadaniem 12, gdzie hasło było w URL-u.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RepeatedGetSameResultCount {
        /*
         * 🧪 Zadanie 14:
         * Utwórz serwlet pod "/odczyt-licznika" (doGet), który TYLKO odczytuje
         * (nie zmienia) wartość statycznego, wcześniej ustawionego pola int stan = 42
         * i ją zwraca. Wykonaj 5 identycznych żądań GET pod rząd, zbierz wszystkie 5
         * odpowiedzi do listy i wypisz, czy wszystkie są sobie równe (idempotentność).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RepeatedPostDifferentResultCount {
        /*
         * 🧪 Zadanie 15:
         * Utwórz serwlet pod "/inkrementuj" (doPost) zwiększający pole int stan o 1
         * przy każdym wywołaniu i zwracający nową wartość. Wykonaj 5 identycznych
         * żądań POST pod rząd, zbierz 5 odpowiedzi do listy i wypisz je - powinny
         * być różne (1,2,3,4,5), w przeciwieństwie do Zadania 14.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UrlLengthLimitSimulation {
        /*
         * 🧪 Zadanie 16:
         * Zbuduj metodę generującą query string o zadanej długości (np. parametr
         * "dane" wypełniony powtórzonym znakiem 'x'). Wykonaj GET na serwlet
         * zwracający "OK" dla dwóch długości: 100 znaków i 5000 znaków. Wypisz oba
         * statusy odpowiedzi - skomentuj (println), że mimo iż HTTP nie ma sztywnego
         * limitu, praktyczne ograniczenia klienta/serwera mogą się różnie zachować
         * dla bardzo długich URL-i.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LargeBodyViaPostNoUrlImpact {
        /*
         * 🧪 Zadanie 17:
         * Utwórz serwlet pod "/duze-dane-post" (doPost), zwracający długość
         * odebranego ciała jako tekst. Wyślij POST z ciałem o długości 10000 znaków
         * (np. powtórzony znak) i wypisz zwróconą długość - potwierdź, że URL
         * pozostaje krótki ("/duze-dane-post") niezależnie od rozmiaru ciała, w
         * przeciwieństwie do GET z Zadania 16.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MethodChoiceBasedOnSemantics {
        /*
         * 🧪 Zadanie 18:
         * Napisz metodę String wybierzMetode(boolean modyfikujeDane) zwracającą "GET"
         * dla false i "POST" dla true (na podstawie semantyki z tej lekcji).
         * Przetestuj ją dla 4 przykładowych operacji ("pobierz liste produktow" ->
         * false, "zloz zamowienie" -> true, "usun konto" -> true, "wyswietl profil"
         * -> false) i wypisz rekomendowaną metodę dla każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HeadersRevealNothingAboutSecurity {
        /*
         * 🧪 Zadanie 19:
         * Utwórz serwlet pod "/nie-szyfrowane" (doPost), zwracający ciało żądania z
         * powrotem (echo). Wyślij POST z ciałem "dane-w-body" i wypisz zwrócone
         * ciało - dodaj komentarz (println) przypominający, że sam fakt "danych w
         * ciele zamiast URL-a" NIE oznacza szyfrowania - to wyłącznie kwestia
         * miejsca przesyłu, nie bezpieczeństwa transportu (za to odpowiada HTTPS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_SameEndpointStatisticsBothMethods {
        /*
         * 🧪 Zadanie 20:
         * Utwórz serwlet pod "/statystyki" z dwoma osobnymi licznikami
         * AtomicInteger: liczbaGet i liczbaPost, inkrementowanymi w odpowiadających
         * metodach, każda zwracająca swoją wartość. Wykonaj 3 żądania GET i 2
         * żądania POST na tę ścieżkę, a na końcu wypisz oba liczniki (powinno być
         * GET=3, POST=2).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullSearchFormGetAndPostVariants {
        /*
         * 🧪 Zadanie 21:
         * Połącz wiedzę z Lesson04 (doGet/doPost) i Lesson05 (getParameter) z tej
         * lekcji: zbuduj serwlet pod "/formularz-wyszukiwania" obsługujący GET
         * (parametry z query stringu) i POST (parametry z formularza), zwracający w
         * OBU przypadkach identyczny format odpowiedzi "Wyniki dla: <fraza>, strona
         * <strona>". Wykonaj oba żądania z tymi samymi danymi i porównaj (println
         * equals) ich ciała odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GetVsPostWithResponseStatusCombo {
        /*
         * 🧪 Zadanie 22:
         * Łącząc z Lesson06 (HttpServletResponse): zbuduj serwlet pod "/zasob-rest",
         * którego doGet() zwraca status 200 z danymi zasobu, a doPost() zwraca
         * status 201 (SC_CREATED) po "utworzeniu" nowego zasobu (przyjmij ciało jako
         * nazwę zasobu). Wykonaj oba żądania i wypisz oba statusy oraz ciała,
         * ilustrując różną semantykę statusów dla GET (odczyt) i POST (tworzenie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_IdempotencyStressTestGet {
        /*
         * 🧪 Zadanie 23:
         * Utwórz serwlet pod "/stabilny" (doGet), zwracający hash SHA-like (np.
         * String.valueOf(Objects.hash(...)) stałych danych, bez żadnej mutacji
         * stanu. Wykonaj 10 żądań GET w pętli, zbierz wszystkie odpowiedzi do
         * Set<String> i wypisz jego rozmiar - powinien wynosić dokładnie 1
         * (wszystkie odpowiedzi identyczne), potwierdzając pełną idempotentność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_NonIdempotencyStressTestPost {
        /*
         * 🧪 Zadanie 24:
         * Utwórz serwlet pod "/niestabilny" (doPost), którego każde wywołanie dodaje
         * losową liczbę (Random, ustalony seed) do wewnętrznej listy i zwraca całą
         * listę jako tekst. Wykonaj 5 identycznych żądań POST w pętli, zbierz 5
         * odpowiedzi do Set<String> i wypisz jego rozmiar - powinien wynosić 5
         * (wszystkie różne), demonstrując brak idempotentności POST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_HybridEndpointWithHeadersAndRedirect {
        /*
         * 🧪 Zadanie 25:
         * Łącząc z Lesson06: zbuduj serwlet "koszyk" pod "/koszyk", gdzie doGet()
         * zwraca zawartość koszyka (stan wewnętrznej listy) jako tekst, a doPost()
         * dodaje przesłany produkt (ciało żądania) do listy i wywołuje
         * resp.sendRedirect("/koszyk") (wzorzec Post-Redirect-Get). Wykonaj POST z
         * produktem "Ksiazka", a następnie (podążając za przekierowaniem klientem
         * NORMAL) sprawdź, że finalna odpowiedź GET zawiera "Ksiazka".
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LoggingVisibilityComparisonSimulated {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj listę "symulowanych logów serwera" (List<String>) ręcznie dopisywaną
         * w serwletach: GET pod "/z-logami-get" dopisuje do listy pełny URL żądania
         * (req.getRequestURI() + "?" + req.getQueryString()), POST pod
         * "/z-logami-post" dopisuje tylko req.getRequestURI() (bez ciała). Wykonaj
         * oba żądania z "wrażliwymi" danymi i wypisz zawartość listy logów - zwróć
         * uwagę, że dane GET trafiły do "logu", a dane POST nie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiStepWorkflowGetThenPostThenGet {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj 2-krokowy workflow: serwlet pod "/produkt-info" (doGet) zwraca
         * informacje o produkcie na podstawie parametru "id" z query stringu;
         * serwlet pod "/zamow-produkt" (doPost) przyjmuje w ciele identyfikator
         * produktu i "składa zamówienie" (dopisuje do wewnętrznej listy), zwracając
         * status 201. Wykonaj: GET po informacje o produkcie id=5, następnie POST
         * zamawiający ten produkt, następnie ponowny GET na "/zamow-produkt" (jeśli
         * obsługiwany, zwracający liczbę zamówień) - wypisz wszystkie kroki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ContentLengthComparisonGetVsPost {
        /*
         * 🧪 Zadanie 28:
         * Wykonaj GET z parametrami w query stringu (np. "?a=1&b=2&c=3") na serwlet
         * zwracający "OK" oraz POST z identycznymi danymi jako ciało formularza
         * ("a=1&b=2&c=3") na inny serwlet też zwracający "OK". Porównaj długość
         * użytego URL-a w obu przypadkach (URI.toString().length()) oraz obecność
         * nagłówka Content-Length w żądaniu POST - wypisz obie obserwacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ErrorHandlingDifferencesPerMethod {
        /*
         * 🧪 Zadanie 29:
         * Łącząc z Lesson06: zbuduj serwlet pod "/walidacja-metody", którego doGet()
         * przy braku wymaganego parametru "q" zwraca sendError(400, "Brak frazy
         * wyszukiwania"), a doPost() przy pustym ciele zwraca sendError(400, "Puste
         * dane formularza"). Przetestuj oba przypadki błędne (GET bez parametru,
         * POST z pustym ciałem) i wypisz oba statusy oraz komunikaty błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullCapstoneRestLikeResource {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny mini-endpoint "/notatka" łączący wszystkie lekcje 4-7:
         * doGet() zwraca aktualną treść notatki (parametr "id" z query stringu
         * wybiera, którą) ze statusem 200 lub 404 jeśli id nieznane; doPost() tworzy
         * nową notatkę z ciała żądania, zwraca status 201 z nagłówkiem
         * "X-Notatka-Id" wskazującym nowo nadane id. Przetestuj: POST tworzący
         * notatkę "Pierwsza notatka", odczytanie jej przez GET z otrzymanym id
         * (status 200, poprawna treść), oraz GET z nieistniejącym id (status 404).
         * Wypisz pełny przebieg wszystkich 3 kroków.
         */
        public static void main(String[] args) { }
    }
}
