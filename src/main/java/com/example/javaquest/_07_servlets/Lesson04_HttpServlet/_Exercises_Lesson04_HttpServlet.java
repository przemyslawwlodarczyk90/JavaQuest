package com.example.javaquest._07_servlets.Lesson04_HttpServlet;

public class _Exercises_Lesson04_HttpServlet {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_OverrideDoGetOnly {
        /*
         * 🧪 Zadanie 1:
         * Utwórz serwlet dziedziczący po HttpServlet, nadpisujący TYLKO doGet(),
         * zwracający tekst "Odpowiedz GET". Zamapuj pod "/tylko-get" w osadzonym
         * Tomcacie (port 0), wykonaj żądanie GET i wypisz status oraz ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_OverrideDoPostOnly {
        /*
         * 🧪 Zadanie 2:
         * Utwórz serwlet nadpisujący TYLKO doPost(), zwracający tekst "Odpowiedz POST".
         * Zamapuj pod "/tylko-post", wykonaj żądanie POST z pustym ciałem
         * (HttpRequest.BodyPublishers.noBody()) i wypisz status oraz ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BothMethodsOneServlet {
        /*
         * 🧪 Zadanie 3:
         * Utwórz JEDEN serwlet nadpisujący zarówno doGet() (zwraca "GET: odczyt"), jak
         * i doPost() (zwraca "POST: zapis"). Zamapuj pod "/oba", wykonaj kolejno GET i
         * POST na tę samą ścieżkę i wypisz oba ciała odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_Post405WhenNotOverridden {
        /*
         * 🧪 Zadanie 4:
         * Zamapuj pod "/brak-post" serwlet nadpisujący wyłącznie doGet(). Wykonaj
         * żądanie POST na tę ścieżkę i wypisz statusCode() – zweryfikuj wartość 405
         * (Method Not Allowed) zwracaną przez domyślną implementację HttpServlet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_EchoPostBody {
        /*
         * 🧪 Zadanie 5:
         * Utwórz serwlet, którego doPost() odczytuje ciało żądania przez
         * req.getInputStream().readAllBytes() (zdekodowane jako UTF-8) i zwraca je
         * poprzedzone tekstem "Otrzymano: ". Zamapuj pod "/echo-post", wyślij POST z
         * ciałem "test123" i wypisz otrzymaną odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SameUrlDifferentBehaviorPerMethod {
        /*
         * 🧪 Zadanie 6:
         * Zamapuj JEDEN serwlet pod "/zasob", którego doGet() zwraca "Odczyt zasobu", a
         * doPost() zwraca "Zapis zasobu". Wykonaj oba żądania na "/zasob" i wypisz oba
         * ciała, potwierdzając, że ten sam adres URL zachowuje się inaczej zależnie od
         * metody HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ContentTypePerMethod {
        /*
         * 🧪 Zadanie 7:
         * Utwórz serwlet, którego doGet() ustawia resp.setContentType("text/plain"), a
         * doPost() ustawia resp.setContentType("application/json") (nawet jeśli treść
         * nie jest prawdziwym JSON-em). Zamapuj pod "/typ-metody", wykonaj oba żądania
         * i wypisz nagłówek Content-Type dla każdej odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleServletsMixedMethods {
        /*
         * 🧪 Zadanie 8:
         * Zarejestruj dwa serwlety: pierwszy pod "/czytelny" (tylko doGet, zwraca
         * "Tylko odczyt"), drugi pod "/zapisywalny" (tylko doPost, zwraca "Tylko
         * zapis"). Wykonaj GET na pierwszy i POST na drugi, wypisując oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PostWithStringBody {
        /*
         * 🧪 Zadanie 9:
         * Utwórz serwlet, którego doPost() odczytuje ciało jako String (UTF-8) i
         * zwraca jego długość w znakach jako tekst odpowiedzi (np. "Dlugosc: 11").
         * Zamapuj pod "/dlugosc", wyślij POST z ciałem "Hello World" (11 znaków) i
         * wypisz otrzymaną odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GetIgnoresBody {
        /*
         * 🧪 Zadanie 10:
         * Zamapuj pod "/ignoruj-cialo" serwlet, którego doGet() zwraca stały tekst
         * "GET nie uzywa ciala zadania" niezależnie od czegokolwiek. Wykonaj GET (bez
         * ciała, standardowo) i wypisz odpowiedź, komentując (println), że GET z
         * założenia nie przenosi ciała żądania w typowym użyciu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeServletsThreeBehaviors {
        /*
         * 🧪 Zadanie 11:
         * Zarejestruj 3 serwlety w jednym kontekście: pod "/tylko-get" (tylko doGet),
         * "/tylko-post" (tylko doPost), "/oba-typy" (doGet i doPost). Wykonaj: GET na
         * pierwszy, POST na drugi, GET i POST na trzeci (razem 4 żądania) i wypisz
         * wszystkie statusy oraz ciała odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MethodNotAllowedForBothDirections {
        /*
         * 🧪 Zadanie 12:
         * Zamapuj pod "/asymetryczny" serwlet nadpisujący TYLKO doGet(). Wykonaj GET
         * (oczekiwany status 200) oraz POST (oczekiwany status 405) na tę samą
         * ścieżkę w jednym uruchomieniu i wypisz oba statusy, potwierdzając brak
         * obsługi POST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_EchoAndTransformPost {
        /*
         * 🧪 Zadanie 13:
         * Utwórz serwlet, którego doPost() odczytuje ciało jako tekst i zwraca je
         * zamienione na WIELKIE LITERY (String.toUpperCase()). Zamapuj pod
         * "/wielkie-litery", wyślij POST z ciałem "male litery" i wypisz odpowiedź
         * (powinna brzmieć "MALE LITERY").
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CounterSeparateForGetAndPost {
        /*
         * 🧪 Zadanie 14:
         * Utwórz serwlet z dwoma osobnymi polami AtomicInteger: licznikGet i
         * licznikPost, inkrementowanymi odpowiednio w doGet() i doPost(), oba zwracane
         * jako treść odpowiedzi ("GET#n" / "POST#n"). Zamapuj pod "/liczniki-metod",
         * wykonaj 2 GET i 3 POST na przemian i wypisz wszystkie 5 odpowiedzi w
         * kolejności wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_JsonLikeBodyEcho {
        /*
         * 🧪 Zadanie 15:
         * Utwórz serwlet, którego doPost() odczytuje ciało JSON-podobne (np.
         * "{\"klucz\":\"wartosc\"}") jako zwykły tekst (bez parsowania) i odsyła je z
         * powrotem z nagłówkiem Content-Type "application/json". Zamapuj pod
         * "/json-echo", wyślij ten ciąg i wypisz zwrócone ciało oraz nagłówek Content-Type.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultiplePostsAccumulateInMemory {
        /*
         * 🧪 Zadanie 16:
         * Utwórz serwlet z polem List<String> wpisy (np. CopyOnWriteArrayList),
         * którego doPost() dopisuje odczytane ciało żądania do listy i zwraca
         * aktualny rozmiar listy jako tekst. Zamapuj pod "/dodaj-wpis", wykonaj 3
         * kolejne żądania POST z różnymi ciałami ("a", "b", "c") i wypisz 3 zwrócone
         * rozmiary (powinny być 1, 2, 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_GetReturnsCurrentStateAfterPosts {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz serwlet z Zadania 16 o obsługę doGet(), zwracającą wszystkie
         * dotychczas zebrane wpisy połączone przecinkiem. Wykonaj 2 żądania POST
         * ("x", "y"), a następnie jedno żądanie GET na tę samą ścieżkę i wypisz jego
         * odpowiedź – powinna zawierać zarówno "x", jak i "y".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DifferentContentTypesPerPath {
        /*
         * 🧪 Zadanie 18:
         * Zarejestruj 2 serwlety: pod "/tekst" (doGet zwraca zwykły tekst z
         * Content-Type "text/plain"), pod "/html" (doGet zwraca "<b>Pogrubione</b>" z
         * Content-Type "text/html"). Wykonaj GET na obie ścieżki i wypisz Content-Type
         * oraz ciało obu odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PostThenGetConsistencyCheck {
        /*
         * 🧪 Zadanie 19:
         * Utwórz serwlet z jednym polem String ostatniaWiadomosc, ustawianym w
         * doPost() na treść ciała żądania, i odczytywanym (zwracanym) w doGet().
         * Zamapuj pod "/ostatnia", wyślij POST z ciałem "Zapisane dane", następnie
         * wykonaj GET i wypisz jego odpowiedź – powinna brzmieć "Zapisane dane".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FourRequestsMixedSummary {
        /*
         * 🧪 Zadanie 20:
         * Zarejestruj 2 serwlety obsługujące oba doGet i doPost pod "/mix1" i "/mix2".
         * Wykonaj po jednym GET i jednym POST na każdą ścieżkę (łącznie 4 żądania),
         * zbierz wszystkie statusy do listy i wypisz podsumowanie: liczba żądań z
         * statusem 200 oraz pełną listę wykonanych (metoda, ścieżka, status).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SimpleInMemoryCrudLikeEndpoint {
        /*
         * 🧪 Zadanie 21:
         * Utwórz serwlet symulujący prosty "magazyn" tekstu: doPost() dopisuje ciało
         * żądania do wewnętrznej listy (jako nowy "wpis") i zwraca jego numer
         * porządkowy, doGet() zwraca liczbę wszystkich zapisanych dotąd wpisów.
         * Zamapuj pod "/magazyn", wykonaj 3 żądania POST, potem 1 GET, i wypisz
         * wszystkie 4 odpowiedzi – GET powinien zwrócić "3".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ContentNegotiationBySimulatedHeader {
        /*
         * 🧪 Zadanie 22:
         * Utwórz serwlet, którego doGet() sprawdza req.getHeader("Accept") - jeśli
         * zawiera "json", ustawia Content-Type "application/json" i zwraca
         * "{\"format\":\"json\"}", w przeciwnym razie zwraca zwykły tekst
         * "format=text". Zamapuj pod "/negocjacja", wyślij dwa żądania GET (jedno z
         * nagłówkiem Accept="application/json", drugie bez tego nagłówka) i wypisz
         * obie odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MethodNotAllowedFallbackForAllVerbs {
        /*
         * 🧪 Zadanie 23:
         * Zamapuj pod "/tylko-put-fikcyjnie" serwlet nienadpisujący ŻADNEJ z metod
         * doXxx(). Wykonaj kolejno żądania GET i POST na tę ścieżkę i wypisz oba
         * statusy – zweryfikuj, że OBA zwracają 405, bo domyślna implementacja
         * HttpServlet nie obsługuje żadnej z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SizeLimitedPostValidation {
        /*
         * 🧪 Zadanie 24:
         * Utwórz serwlet, którego doPost() sprawdza długość ciała żądania: jeśli
         * przekracza 20 znaków, wywołuje resp.sendError(413, "Cialo zbyt dlugie"),
         * w przeciwnym razie zwraca "OK, dlugosc: N". Zamapuj pod "/limit", wyślij
         * jedno żądanie z krótkim ciałem ("krotkie") i jedno z długim (ponad 20
         * znaków) i wypisz oba statusy oraz ciała odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_StateMachineAcrossMethods {
        /*
         * 🧪 Zadanie 25:
         * Utwórz serwlet symulujący prosty automat stanów tekstowych: pole String
         * stan = "NOWY". doPost() zmienia stan na wartość ciała żądania i zwraca nowy
         * stan, doGet() tylko odczytuje bieżący stan. Zamapuj pod "/automat", wykonaj
         * kolejno: GET (oczekiwane "NOWY"), POST z ciałem "AKTYWNY", GET ponownie
         * (oczekiwane "AKTYWNY") – wypisz wszystkie 3 odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ParallelSemanticsCheckSequential {
        /*
         * 🧪 Zadanie 26:
         * Utwórz serwlet z AtomicInteger globalnyLicznik, inkrementowanym zarówno w
         * doGet(), jak i w doPost() (współdzielony między obiema metodami), zwracanym
         * jako treść odpowiedzi. Zamapuj pod "/wspolny-licznik", wykonaj na przemian:
         * GET, POST, GET, POST, GET (5 żądań) i wypisz zwrócone wartości – powinny
         * rosnąć 1,2,3,4,5 niezależnie od metody HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ErrorHandlingInsideDoPost {
        /*
         * 🧪 Zadanie 27:
         * Utwórz serwlet, którego doPost() próbuje sparsować ciało żądania jako
         * Integer.parseInt(cialo) - jeśli się to nie uda (NumberFormatException),
         * łapie wyjątek i woła resp.sendError(400, "Oczekiwano liczby"), w
         * przeciwnym razie zwraca podwojoną wartość jako tekst. Zamapuj pod
         * "/podwoj", wyślij POST z ciałem "21" (oczekiwane "42") i POST z ciałem
         * "abc" (oczekiwany status 400) – wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CombinedGetPostReportServlet {
        /*
         * 🧪 Zadanie 28:
         * Utwórz serwlet raportujący: doPost() dopisuje przesłaną liczbę (parsowaną z
         * ciała) do wewnętrznej List<Integer>, doGet() zwraca sumę i średnią
         * wszystkich dotąd przesłanych liczb jako tekst (np. "suma=60, srednia=20.0").
         * Zamapuj pod "/raport-liczb", wyślij POST z "10", "20", "30", a potem GET, i
         * wypisz odpowiedź GET.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiServletPipelineGetThenPost {
        /*
         * 🧪 Zadanie 29:
         * Zarejestruj dwa serwlety: pod "/krok1" (doGet zwraca token tekstowy, np.
         * "TOKEN-ABC"), pod "/krok2" (doPost odczytuje ciało żądania i sprawdza, czy
         * równa się "TOKEN-ABC" - jeśli tak, zwraca "Autoryzowano", jeśli nie,
         * status 403). W main() wykonaj GET na "/krok1", pobierz token z odpowiedzi, a
         * następnie POST na "/krok2" z tym tokenem jako ciałem - wypisz finalny
         * status i ciało.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMixedMethodCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj serwlet "notatnik": doPost() dodaje przesłaną notatkę (ciało
         * żądania) do wewnętrznej listy z numerem porządkowym, doGet() zwraca
         * wszystkie dotychczasowe notatki w formacie "1: tresc1\n2: tresc2\n...".
         * Zamapuj pod "/notatnik", wykonaj 3 żądania POST z różnymi notatkami,
         * następnie 1 żądanie GET, i wypisz finalną treść notatnika, licząc też ile
         * łącznie żądań (GET+POST) obsłużył serwlet za pomocą osobnego licznika.
         */
        public static void main(String[] args) { }
    }
}
