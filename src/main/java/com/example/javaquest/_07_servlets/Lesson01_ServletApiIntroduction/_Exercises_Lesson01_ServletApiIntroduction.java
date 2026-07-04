package com.example.javaquest._07_servlets.Lesson01_ServletApiIntroduction;

public class _Exercises_Lesson01_ServletApiIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FirstHelloServlet {
        /*
         * 🧪 Zadanie 1:
         * Uruchom tymczasowy, osadzony Tomcat (setBaseDir na katalog tymczasowy,
         * getConnector().setPort(0)). Zarejestruj serwlet dziedziczący po HttpServlet,
         * nadpisujący tylko doGet(), zwracający w odpowiedzi tekst "Witaj z serwletu!"
         * pod ścieżką "/powitanie". Wykonaj GET na tę ścieżkę, wypisz status i ciało
         * odpowiedzi, na końcu zatrzymaj serwer (stop()+destroy() w finally).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_InitLogging {
        /*
         * 🧪 Zadanie 2:
         * Utwórz serwlet, który nadpisuje init(ServletConfig) i wypisuje na konsolę
         * "INIT: serwlet gotowy". Zamapuj go pod "/start", uruchom Tomcat na porcie 0,
         * wykonaj jedno żądanie GET na "/start" i sprawdź (przez kolejność wypisów
         * w konsoli), że komunikat z init() pojawia się PRZED odpowiedzią z doGet().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_DestroyLogging {
        /*
         * 🧪 Zadanie 3:
         * Utwórz serwlet nadpisujący destroy(), wypisujący "DESTROY: serwlet zamkniety".
         * Zamapuj go pod "/koniec", uruchom Tomcat, wykonaj GET na "/koniec", a następnie
         * w bloku finally wywołaj tomcat.stop() i tomcat.destroy() – sprawdź, że komunikat
         * z destroy() pojawia się w konsoli dopiero PO odebraniu odpowiedzi HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DefaultStatus200 {
        /*
         * 🧪 Zadanie 4:
         * Zamapuj serwlet pod "/domyslny", którego doGet() jedynie pisze treść
         * odpowiedzi (resp.getWriter().write("OK")) bez wywoływania setStatus().
         * Wykonaj GET na "/domyslny" i wypisz response.statusCode() – zweryfikuj,
         * że domyślny status to 200 mimo braku jawnego ustawienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExactBodyCheck {
        /*
         * 🧪 Zadanie 5:
         * Zamapuj serwlet pod "/status" zwracający dokładnie ciąg "OK" (bez znaku
         * nowej linii). Wykonaj GET, pobierz response.body() i wypisz na konsolę
         * wynik porównania "OK".equals(response.body()) (true/false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SameInstanceCounter {
        /*
         * 🧪 Zadanie 6:
         * Utwórz serwlet z polem int licznik = 0 (niebezpieczne wątkowo, ale
         * wystarczające do demonstracji), zwiększanym o 1 w każdym doGet() i
         * zwracanym jako treść odpowiedzi. Zamapuj pod "/licznik", wykonaj 3 kolejne
         * żądania GET w pętli i wypisz kolejno zwrócone wartości (powinny być 1, 2, 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_UnmappedPath404 {
        /*
         * 🧪 Zadanie 7:
         * Uruchom Tomcat z jednym serwletem zamapowanym pod "/istnieje". Wykonaj GET
         * na ŚCIEŻKĘ "/brak", której nie zamapowano do żadnego serwletu. Wypisz
         * statusCode() odpowiedzi – zweryfikuj, że kontener zwraca 404.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TwoServletsTwoPaths {
        /*
         * 🧪 Zadanie 8:
         * Zarejestruj dwa RÓŻNE serwlety w jednym kontekście: pierwszy pod "/pierwszy"
         * zwracający "Serwlet A", drugi pod "/drugi" zwracający "Serwlet B". Wykonaj
         * GET na obie ścieżki i wypisz oba ciała odpowiedzi, potwierdzając, że każda
         * ścieżka trafia do innego serwletu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PostWithoutDoPostGives405 {
        /*
         * 🧪 Zadanie 9:
         * Zamapuj pod "/tylko-get" serwlet nadpisujący WYŁĄCZNIE doGet() (bez doPost()).
         * Wykonaj żądanie POST (z dowolnym, np. pustym ciałem) na "/tylko-get" i wypisz
         * statusCode() – zweryfikuj, że domyślna implementacja HttpServlet zwraca 405
         * (Method Not Allowed) dla nienadpisanej metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ContentTypeCheck {
        /*
         * 🧪 Zadanie 10:
         * Zamapuj serwlet pod "/typ", który w doGet() wywołuje
         * resp.setContentType("text/plain") przed zapisaniem treści "dane tekstowe".
         * Wykonaj GET i wypisz nagłówek Content-Type z odpowiedzi
         * (response.headers().firstValue("Content-Type")), sprawdzając, że zawiera "text/plain".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InitStateUsedInDoGet {
        /*
         * 🧪 Zadanie 11:
         * Utwórz serwlet z polem String powitanie, ustawianym w nadpisanym
         * init(ServletConfig) na wartość "Witaj, Kursancie!" (super.init(config) też wywołaj).
         * W doGet() zwróć wartość tego pola jako treść odpowiedzi. Zamapuj pod "/hello2",
         * wykonaj GET i wypisz otrzymane ciało – potwierdź zgodność z wartością z init().
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LifecycleOrderList {
        /*
         * 🧪 Zadanie 12:
         * Utwórz serwlet ze statyczną listą List<String> kolejnosc (np. CopyOnWriteArrayList),
         * dopisującą "init", "doGet" i "destroy" w odpowiednich metodach cyklu życia.
         * Zamapuj pod "/cykl", wykonaj jedno żądanie GET, zatrzymaj Tomcat (stop+destroy)
         * i na końcu wypisz całą listę kolejnosc – zweryfikuj, że brzmi dokładnie
         * ["init", "doGet", "destroy"].
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SameInstanceAcrossRequests {
        /*
         * 🧪 Zadanie 13:
         * Utwórz serwlet, którego doGet() zwraca jako tekst wynik System.identityHashCode(this).
         * Zamapuj pod "/tozsamosc", wykonaj 3 kolejne żądania GET, zbierz 3 zwrócone
         * wartości hash i wypisz, czy wszystkie trzy są sobie równe – potwierdzając,
         * że kontener użył JEDNEJ instancji serwletu dla wszystkich żądań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TwoClassesDifferentPaths {
        /*
         * 🧪 Zadanie 14:
         * Utwórz dwie osobne klasy serwletów: AServlet (zwraca "Odpowiedz A") i
         * BServlet (zwraca "Odpowiedz B"). Zamapuj AServlet pod "/a", BServlet pod "/b"
         * w tym samym kontekście Tomcata. Wykonaj GET na obie ścieżki i wypisz oba
         * ciała odpowiedzi, potwierdzając poprawne rozróżnienie klas przez kontener.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FiveSequentialRequestsCounter {
        /*
         * 🧪 Zadanie 15:
         * Utwórz serwlet z polem java.util.concurrent.atomic.AtomicInteger licznik,
         * inkrementowanym w każdym doGet() i zwracanym jako treść odpowiedzi. Zamapuj
         * pod "/atomowy", wykonaj 5 kolejnych żądań GET w pętli for, zbierz 5 zwróconych
         * wartości do listy i wypisz ją na końcu – powinna wynosić [1, 2, 3, 4, 5].
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ServletNameFromConfig {
        /*
         * 🧪 Zadanie 16:
         * Utwórz serwlet, którego doGet() zwraca jako treść odpowiedzi wynik
         * getServletConfig().getServletName(). Zarejestruj go metodą Tomcat.addServlet
         * pod nazwą "nazwanyServlet" i zamapuj pod "/nazwa". Wykonaj GET i wypisz
         * zwrócone ciało – zweryfikuj, że pasuje do nazwy podanej przy rejestracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SameInstanceTwoMappings {
        /*
         * 🧪 Zadanie 17:
         * Zarejestruj JEDNĄ instancję serwletu (z licznikiem inkrementowanym w doGet())
         * pod DWIEMA różnymi ścieżkami: "/x" i "/y" (addServletMappingDecoded wywołane
         * dwukrotnie dla tej samej nazwy serwletu). Wykonaj po jednym GET na "/x" i "/y",
         * wypisz zwracane wartości licznika – zweryfikuj, że druga wartość jest o 1
         * większa od pierwszej (ta sama instancja obsługuje obie ścieżki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_StatusAndContentTypeTogether {
        /*
         * 🧪 Zadanie 18:
         * Zamapuj serwlet pod "/kombo", którego doGet() jawnie ustawia
         * resp.setStatus(HttpServletResponse.SC_OK), resp.setContentType("text/plain")
         * oraz treść "wynik kombinowany". Wykonaj GET i wypisz zarówno statusCode(),
         * jak i nagłówek Content-Type odpowiedzi – zweryfikuj oba naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GetVsPostSameUrl {
        /*
         * 🧪 Zadanie 19:
         * Zamapuj pod "/mieszany" serwlet nadpisujący WYŁĄCZNIE doGet() (zwraca "GET OK").
         * W jednym uruchomieniu main() wykonaj kolejno: żądanie GET na "/mieszany"
         * (oczekiwany status 200, ciało "GET OK") oraz żądanie POST na "/mieszany"
         * (oczekiwany status 405, bo doPost() nie jest nadpisane). Wypisz oba statusy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FourPathsInLoop {
        /*
         * 🧪 Zadanie 20:
         * Zarejestruj 3 serwlety pod ścieżkami "/p1", "/p2", "/p3", z których każdy
         * zwraca inny, stały tekst (np. "Strona 1", "Strona 2", "Strona 3"). Zbuduj
         * tablicę String[] {"/p1","/p2","/p3"}, w pętli for wykonaj GET na każdą
         * ścieżkę i wypisz parę (ścieżka, ciało odpowiedzi) dla każdej iteracji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FourServletsCrossCheck {
        /*
         * 🧪 Zadanie 21:
         * Zarejestruj 4 serwlety pod ścieżkami "/a", "/b", "/c", "/d", z których KAŻDY
         * zwraca w ciele odpowiedzi swoją WŁASNĄ nazwę ścieżki (np. serwlet pod "/c"
         * zwraca tekst "c"). Wykonaj GET na wszystkie 4 ścieżki i sprawdź (println
         * porównania), że każda odpowiedź zawiera dokładnie oczekiwaną literę,
         * a nie odpowiedź innego serwletu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_StartupToFirstResponseTiming {
        /*
         * 🧪 Zadanie 22:
         * Zmierz czas (System.nanoTime()) tuż przed tomcat.start() oraz tuż po
         * otrzymaniu pierwszej odpowiedzi z zamapowanego pod "/pomiar" serwletu.
         * Wypisz różnicę w milisekundach jako orientacyjny czas startu osadzonego
         * Tomcata i wykonania pierwszego żądania (nie jest to ścisły test, tylko demonstracja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SingleInstanceCreatedOnce {
        /*
         * 🧪 Zadanie 23:
         * Utwórz serwlet ze statycznym polem AtomicInteger utworzoneInstancje,
         * inkrementowanym w konstruktorze klasy serwletu. Zamapuj JEDNĄ instancję
         * (utworzoną raz przez "new") pod "/instancje", wykonaj 5 żądań GET w pętli,
         * a na końcu wypisz wartość utworzoneInstancje – powinna wynosić dokładnie 1
         * mimo 5 obsłużonych żądań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SelfCheckHarness {
        /*
         * 🧪 Zadanie 24:
         * Zamapuj serwlet pod "/smoke", zwracający status 200, nagłówek
         * "X-Lekcja: 1" oraz ciało zawierające podciąg "serwlet dziala". Po wykonaniu
         * GET zaimplementuj mini "harness" – 3 niezależne sprawdzenia (status==200,
         * nagłówek obecny, ciało zawiera podciąg) i wypisz dla każdego "PASS" albo "FAIL",
         * a na końcu jedno podsumowujące zdanie ile testów przeszło.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ThreadNameAcrossRequests {
        /*
         * 🧪 Zadanie 25:
         * Zamapuj serwlet pod "/watek", którego doGet() zwraca jako treść
         * Thread.currentThread().getName(). Wykonaj 3 kolejne żądania GET, zbierz
         * 3 zwrócone nazwy wątków do Set<String> i wypisz jego rozmiar – skomentuj
         * (println), czy pula wątków użyła tego samego, czy różnych wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_UnhandledExceptionGives500 {
        /*
         * 🧪 Zadanie 26:
         * Zamapuj pod "/blad" serwlet, którego doGet() rzuca
         * new RuntimeException("Symulowany blad"). Wykonaj GET na "/blad" i wypisz
         * statusCode() odpowiedzi – zweryfikuj, że nieobsłużony wyjątek w serwlecie
         * skutkuje domyślną odpowiedzią z kodem 500 (Internal Server Error).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_InitResourceCleanupPattern {
        /*
         * 🧪 Zadanie 27:
         * Utwórz serwlet z polem List<String> zasob, tworzonym (new ArrayList<>())
         * i wypełnianym jednym elementem "dane-startowe" w init(). W doGet() dopisz
         * do listy element "dane-z-zadania" i zwróć jej rozmiar jako tekst. W destroy()
         * wyczyść listę (zasob.clear()) i wypisz jej rozmiar (powinien wynosić 0) –
         * potwierdzając poprawne zwolnienie zasobu przy zamykaniu serwletu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_IdempotentGetDemo {
        /*
         * 🧪 Zadanie 28:
         * Zamapuj pod "/idempotentny" serwlet, którego doGet() TYLKO odczytuje dane
         * (nie modyfikuje żadnego stanu) i zawsze zwraca ten sam tekst "dane niezmienne".
         * Wykonaj to samo żądanie GET dwukrotnie pod rząd i porównaj (println equals)
         * oba statusy i oba ciała odpowiedzi – powinny być identyczne za każdym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_VisitCounterFiveCalls {
        /*
         * 🧪 Zadanie 29:
         * Utwórz serwlet "licznik odwiedzin" pod ścieżką "/odwiedziny", zwracający
         * przy każdym doGet() kolejną rosnącą wartość (int pole zwiększane o 1,
         * zwracane jako tekst). Wykonaj 5 żądań GET w pętli, zbierz 5 zwróconych
         * wartości do listy i wypisz ją na końcu – oczekiwany wynik to [1, 2, 3, 4, 5].
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullLifecycleSummary {
        /*
         * 🧪 Zadanie 30:
         * Utwórz serwlet, którego init() zapisuje czas startu (System.currentTimeMillis())
         * do pola, doGet() inkrementuje pole AtomicInteger obsluzoneZadania i zwraca
         * jego aktualną wartość jako tekst, a destroy() wypisuje na konsolę podsumowanie:
         * "Obsluzono X zadan, czas dzialania serwletu: Y ms" (Y liczone względem czasu
         * z init()). Zamapuj pod "/podsumowanie", wykonaj 4 żądania GET, zatrzymaj
         * Tomcat i sprawdź, że podsumowanie w destroy() zgadza się z liczbą 4.
         */
        public static void main(String[] args) { }
    }
}
