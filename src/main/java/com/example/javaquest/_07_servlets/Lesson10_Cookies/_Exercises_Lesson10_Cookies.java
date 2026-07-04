package com.example.javaquest._07_servlets.Lesson10_Cookies;

public class _Exercises_Lesson10_Cookies {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SetSingleCookie {
        /*
         * 🧪 Zadanie 1:
         * Uruchom embedded Tomcat (port 0) z serwletem mapowanym na "/jezyk". Serwlet ma
         * ustawiać cookie "jezyk"="pl" (new Cookie("jezyk","pl"), setMaxAge(60), setPath("/")).
         * Użyj HttpClient.newBuilder().cookieHandler(new java.net.CookieManager()).build().
         * Wyślij jedno żądanie GET i wypisz nagłówek odpowiedzi "Set-Cookie".
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadCookieOnSecondRequest {
        /*
         * 🧪 Zadanie 2:
         * Używając TEGO SAMEGO klienta z CookieManager co w Zadaniu 1, wykonaj DWA kolejne
         * żądania GET na "/jezyk". Serwlet przy drugim żądaniu ma odczytać cookie "jezyk"
         * (req.getCookies()) i odesłać "Odczytano jezyk: pl". Wypisz body obu odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_NullCookiesFirstRequest {
        /*
         * 🧪 Zadanie 3:
         * Wyślij PIERWSZE żądanie do serwletu (świeży klient, bez wcześniej ustawionych
         * cookies). Serwlet ma sprawdzić req.getCookies() == null i odpowiedzieć
         * "Brak cookies w zadaniu" – zademonstruj, że getCookies() może zwrócić null i
         * zawsze trzeba to sprawdzić przed iteracją.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SetTwoCookiesAtOnce {
        /*
         * 🧪 Zadanie 4:
         * Serwlet ma ustawiać w JEDNEJ odpowiedzi DWA cookies: "motyw"="ciemny" oraz
         * "rozmiarCzcionki"="16" (dwa osobne wywołania resp.addCookie). Wyślij żądanie 1
         * (ustawienie), potem żądanie 2 tym samym klientem z CookieManager i odczytaj
         * OBIE wartości w serwlecie, wypisując je w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CookiePathRoot {
        /*
         * 🧪 Zadanie 5:
         * Ustaw cookie z setPath("/") i zweryfikuj, że jest ono odsyłane przy kolejnym
         * żądaniu na TĘ SAMĄ ścieżkę serwletu ("/jezyk"). Wypisz w serwlecie odczytaną
         * wartość cookie przy drugim żądaniu, potwierdzając poprawne działanie ścieżki "/".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SimpleAlphanumericValue {
        /*
         * 🧪 Zadanie 6:
         * Ustaw cookie "token"="abc123" (same znaki alfanumeryczne, bez potrzeby kodowania).
         * Wyślij dwa żądania tym samym klientem i zweryfikuj w serwlecie, że wartość
         * odczytana przy drugim żądaniu jest DOKŁADNIE równa "abc123".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TwoIndependentClientsIsolation {
        /*
         * 🧪 Zadanie 7:
         * Utwórz DWA oddzielne obiekty HttpClient, każdy z WŁASNYM CookieManager. Wyślij
         * po jednym żądaniu z każdego z nich do tego samego serwletu ustawiającego cookie.
         * Zweryfikuj, że drugi klient NIE widzi cookie ustawionego dla pierwszego (każdy
         * dostaje odpowiedź "Brak cookies w zadaniu" przy swoim pierwszym żądaniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WithVsWithoutCookieManager {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj serwlet zliczający odwiedziny w cookie "licznik" (wartość jako String liczby,
         * inkrementowana w serwlecie). Wykonaj 3 żądania klientem Z CookieManagerem (licznik
         * powinien rosnąć 1,2,3) oraz osobno 3 żądania NOWYM klientem BEZ CookieManagera za
         * każdym razem (licznik powinien zawsze wynosić 1). Porównaj oba zestawy wyników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CookieDeletionViaMaxAgeZero {
        /*
         * 🧪 Zadanie 9:
         * Ustaw cookie "sesyjne"="tak" (żądanie 1). W żądaniu 2 (ten sam klient,
         * ?usun=true) serwlet ma odesłać NOWE cookie o tej samej nazwie z setMaxAge(0)
         * i tym samym setPath("/") – to standardowy sposób "usuwania" cookie. Wypisz
         * odpowiedzi obu żądań i nagłówek Set-Cookie z drugiego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ManualCookieHeaderMultipleValues {
        /*
         * 🧪 Zadanie 10:
         * Bez użycia CookieManager, zbuduj RĘCZNIE żądanie z nagłówkiem
         * .header("Cookie", "a=1; b=2; c=3"). W serwlecie zaiteruj po req.getCookies()
         * (tablica 3 elementów) i wypisz każdą parę nazwa=wartość w osobnej linii.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LanguagePreferenceThreeRequestFlow {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj serwlet "/lang": żądanie 1 (bez cookie) ustawia domyślnie "lang"="pl".
         * Żądanie 2 z parametrem "?zmien=en" ma zaktualizować cookie "lang" na "en".
         * Żądanie 3 (bez parametru) ma tylko odczytać i wypisać aktualną wartość cookie
         * (oczekiwane "en"). Wykonaj wszystkie 3 żądania jednym klientem z CookieManager.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VisitCounterViaCookie {
        /*
         * 🧪 Zadanie 12:
         * Serwlet trzyma licznik odwiedzin WYŁĄCZNIE w cookie "wizyty" (parsowany z
         * Integer.parseInt, inkrementowany, zapisywany z powrotem jako String w nowym cookie).
         * Wykonaj 3 kolejne żądania tym samym klientem i zweryfikuj wartości 1, 2, 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RememberMeFlag {
        /*
         * 🧪 Zadanie 13:
         * Serwlet sprawdza cookie "zapamietaj". Jeśli brak – ustawia je i odpowiada
         * "Zaloguj sie po raz pierwszy". Jeśli obecne – odpowiada "Witaj ponownie!".
         * Wykonaj 2 kolejne żądania tym samym klientem i wypisz obie odpowiedzi,
         * potwierdzając zmianę zachowania między pierwszym a drugim żądaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultiplePreferencesTogether {
        /*
         * 🧪 Zadanie 14:
         * Serwlet ustawia RAZEM 3 cookies preferencji: "motyw", "jezyk", "czcionka" w żądaniu 1.
         * W żądaniu 2 (ten sam klient) odczytuje wszystkie trzy naraz i zwraca jedno
         * podsumowanie tekstowe łączące wszystkie 3 wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TwoClientsIndependentCounters {
        /*
         * 🧪 Zadanie 15:
         * Utwórz DWA klienty, każdy z własnym CookieManager. Wykonaj po 2 żądania z każdego
         * do serwletu-licznika z Zadania 12. Zweryfikuj, że oba liczniki niezależnie
         * osiągają wartość 2 (a nie np. 1 i 4) – dowód pełnej izolacji stanu między klientami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UrlEncodedCookieValue {
        /*
         * 🧪 Zadanie 16:
         * Ustaw cookie z wartością wymagającą kodowania, np. zawierającą spację –
         * zakoduj ją ręcznie (java.net.URLEncoder.encode("Jan Kowalski", "UTF-8")) przed
         * utworzeniem new Cookie("uzytkownik", zakodowanaWartosc). Odczytaj cookie w
         * kolejnym żądaniu i zdekoduj (URLDecoder.decode) z powrotem do oryginalnej postaci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PureCookieSessionCounterContrast {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj licznik odwiedzin WYŁĄCZNIE cookie'ami (jak w Zadaniu 12), a w
         * komentarzu/println zaznacz wprost, że cała wartość licznika FIZYCZNIE podróżuje
         * tam i z powrotem w nagłówkach Cookie/Set-Cookie przy KAŻDYM żądaniu (w
         * przeciwieństwie do HttpSession z Lesson11, gdzie tylko identyfikator podróżuje,
         * a dane siedzą po stronie serwera).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_LogoutDeletesCookie {
        /*
         * 🧪 Zadanie 18:
         * Serwlet "/konto": żądanie bez parametru zwraca stan zalogowania na podstawie
         * cookie "zalogowany". Parametr "?wyloguj=true" ma usunąć cookie (setMaxAge(0)).
         * Wykonaj sekwencję: żądanie ustawiające cookie, żądanie z wyloguj=true, żądanie
         * sprawdzające ponownie – zweryfikuj, że po wylogowaniu cookie już nie istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FormSubmissionSetsCookie {
        /*
         * 🧪 Zadanie 19:
         * Połącz z Lesson09: wyślij POST z ciałem formularza "jezykPreferowany=en"
         * (Content-Type x-www-form-urlencoded) do serwletu, który na podstawie
         * req.getParameter("jezykPreferowany") ustawia odpowiednie cookie "lang".
         * Następnie wykonaj osobne żądanie GET tym samym klientem i zweryfikuj, że
         * serwlet odczytuje cookie "lang"="en" ustawione przez wcześniejszy formularz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CookieSharedAcrossTwoServlets {
        /*
         * 🧪 Zadanie 20:
         * Zarejestruj DWA serwlety w tym samym kontekście: "/ustaw-motyw" (ustawia cookie
         * "motyw") i "/pokaz-motyw" (tylko odczytuje cookie "motyw", inna klasa serwletu).
         * Wykonaj żądanie do "/ustaw-motyw", a potem do "/pokaz-motyw" tym samym klientem –
         * zweryfikuj, że drugi serwlet widzi cookie ustawione przez pierwszy (bo path="/").
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ShoppingCartAccumulatedInCookie {
        /*
         * 🧪 Zadanie 21:
         * Serwlet "/koszyk?dodaj=X" ma odczytać aktualną wartość cookie "koszyk"
         * (lista id produktów oddzielonych przecinkiem), dopisać nowe id X i zapisać
         * z powrotem jako zaktualizowane cookie. Wykonaj 3 kolejne żądania z ?dodaj=1,
         * ?dodaj=2, ?dodaj=3, a na końcu żądanie bez parametru odczytujące finalną
         * zawartość koszyka – oczekiwane "1,2,3".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MalformedCookieDefensiveParsing {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj RĘCZNIE żądanie z nagłówkiem .header("Cookie", "licznik=NIE_LICZBA")
         * (pomijając CookieManager, aby sfałszować wartość) do serwletu-licznika z
         * Zadania 12, który normalnie parsuje wartość jako int. Zweryfikuj, że serwlet
         * NIE rzuca nieobsłużonego wyjątku (try/catch NumberFormatException), tylko
         * traktuje błędną wartość jako 0/reset i kontynuuje działanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MultiFlagFeatureProfile {
        /*
         * 🧪 Zadanie 23:
         * Ustaw 3 cookies reprezentujące flagi funkcji: "flagaA"="on", "flagaB"="off",
         * "flagaC"="on". W kolejnym żądaniu serwlet ma odczytać wszystkie trzy i zbudować
         * tekstowy "profil użytkownika" łączący ich stany (np. "Profil: A=wlaczone,
         * B=wylaczone, C=wlaczone"). Wypisz wynikowy profil.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FullLoginLogoutLoginCycle {
        /*
         * 🧪 Zadanie 24:
         * Wykonaj sekwencję 4 żądań na serwlecie logowania opartym o cookie "sesjaLogin":
         * (1) logowanie -> ustawia cookie z wartością "user-1", (2) wylogowanie ->
         * maxAge(0), (3) ponowne logowanie -> NOWA wartość "user-2", (4) sprawdzenie ->
         * potwierdzenie że wartość to "user-2", a nie "user-1". Wypisz i porównaj
         * wszystkie wartości cookie na każdym kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CookieVsSessionConceptualMeasurement {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj licznik odwiedzin cookie'ami (jak w Zadaniu 12) i zmierz
         * (np. response.headers().firstValue("set-cookie").get().length()) rozmiar
         * nagłówka Set-Cookie rosnący wraz z długością wartości licznika. Wypisz w
         * komentarzu/println wniosek: dane w cookie fizycznie zwiększają rozmiar KAŻDEGO
         * żądania/odpowiedzi, co jest kluczową różnicą względem HttpSession (Lesson11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LastVisitedPagesHistory {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj wspólny serwlet obsługujący 4 różne ścieżki ("/strona-a", "/strona-b",
         * "/strona-c", "/historia" – 4 osobne mapowania Tomcat.addServlet/addServletMappingDecoded
         * tej samej instancji lub klasy). Każde wywołanie "/strona-X" ma dopisywać X do cookie
         * "historia" (max. 3 ostatnie, oddzielone przecinkiem). "/historia" tylko odczytuje
         * i wypisuje bieżącą zawartość. Wykonaj sekwencję odwiedzin i sprawdź wynik końcowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TenCookiesRoundTrip {
        /*
         * 🧪 Zadanie 27:
         * Serwlet ustawia w JEDNEJ odpowiedzi 10 różnych cookies (np. "pref0".."pref9",
         * każde z inną wartością). W kolejnym żądaniu tym samym klientem odczytaj
         * WSZYSTKIE 10 z req.getCookies() i zweryfikuj (licząc dopasowania po nazwie),
         * że każde z nich zostało poprawnie odesłane i odczytane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_InterleavedClientsNoContamination {
        /*
         * 🧪 Zadanie 28:
         * Utwórz 2 klienty (każdy z własnym CookieManager) do serwletu-licznika z
         * Zadania 12. Wykonaj żądania w PRZEPLOCIE: klient1, klient2, klient1, klient2
         * (łącznie po 2 żądania na klienta). Zweryfikuj, że mimo przeplotu każdy klient
         * kończy z własnym licznikiem = 2, bez wzajemnego wpływu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FormPlusCookieMultiStepPreferences {
        /*
         * 🧪 Zadanie 29:
         * Połącz Lesson09 i Lesson10: wyślij POST na "/preferencje" z ciałem formularza
         * "motyw=ciemny&jezyk=pl&powiadomienia=on" (Content-Type x-www-form-urlencoded),
         * serwlet ustawia po jednym cookie na każde pole. Następnie wyślij osobne żądanie
         * GET na "/podsumowanie-preferencji" (inny serwlet lub ścieżka), które odczytuje
         * WSZYSTKIE cookies i renderuje pełne podsumowanie tekstowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_SixStepAssertionReport {
        /*
         * 🧪 Zadanie 30:
         * Wykonaj sekwencję 6 żądań na serwletach z tej lekcji: (1) ustaw cookie, (2) odczytaj
         * je, (3) zaktualizuj wartość, (4) usuń (maxAge=0), (5) ustaw ponownie z nową wartością,
         * (6) odczytaj i porównaj z wartością z kroku 5. Dla każdego kroku zapisz do listy
         * parę (opis kroku, oczekiwany wynik, faktyczny wynik) i wypisz na końcu tabelę
         * podsumowującą pass/fail dla wszystkich 6 kroków.
         */
        public static void main(String[] args) { }
    }
}
