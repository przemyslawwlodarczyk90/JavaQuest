package com.example.javaquest._07_servlets.Lesson05_HttpServletRequest;

public class _Exercises_Lesson05_HttpServletRequest {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SingleQueryParameter {
        /*
         * 🧪 Zadanie 1:
         * Utwórz serwlet, którego doGet() odczytuje parametr "imie" przez
         * req.getParameter("imie") i zwraca "Czesc, " + imie. Zamapuj pod "/przywitaj",
         * wykonaj GET na "/przywitaj?imie=Kasia" i wypisz otrzymane ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MissingParameterIsNull {
        /*
         * 🧪 Zadanie 2:
         * Zamapuj pod "/opcjonalny" serwlet, którego doGet() odczytuje parametr
         * "kolor" i zwraca "kolor=" + kolor. Wykonaj GET na "/opcjonalny" BEZ żadnych
         * parametrów w URL-u i wypisz odpowiedź - zweryfikuj, że req.getParameter
         * zwrócił null (odpowiedź powinna zawierać tekst "kolor=null").
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SingleHeaderRead {
        /*
         * 🧪 Zadanie 3:
         * Utwórz serwlet, którego doGet() odczytuje nagłówek "X-Test" przez
         * req.getHeader("X-Test") i zwraca jego wartość jako ciało odpowiedzi.
         * Zamapuj pod "/naglowek", wykonaj GET z nagłówkiem "X-Test: wartosc123" i
         * wypisz odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_HeaderCaseInsensitive {
        /*
         * 🧪 Zadanie 4:
         * Utwórz serwlet, którego doGet() odczytuje ten sam nagłówek DWOMA sposobami:
         * req.getHeader("X-Info") oraz req.getHeader("x-info") (małymi literami), i
         * zwraca oba wyniki. Zamapuj pod "/case", wykonaj GET z nagłówkiem
         * "X-Info: dane" i wypisz odpowiedź - potwierdź, że oba odczyty dają tę samą wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GetReaderBodyAsText {
        /*
         * 🧪 Zadanie 5:
         * Utwórz serwlet, którego doPost() czyta ciało żądania przez
         * req.getReader() (BufferedReader) linia po linii i zwraca liczbę
         * przeczytanych linii. Zamapuj pod "/linie", wyślij POST z ciałem
         * "linia1\nlinia2\nlinia3" i wypisz odpowiedź (oczekiwana liczba: 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ParameterMapSingleEntry {
        /*
         * 🧪 Zadanie 6:
         * Utwórz serwlet, którego doGet() iteruje po req.getParameterMap() i zwraca
         * wszystkie klucze i wartości (join przecinkiem). Zamapuj pod "/mapa-param",
         * wykonaj GET na "/mapa-param?jeden=1&dwa=2" i wypisz otrzymaną odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RepeatedParameterAllValues {
        /*
         * 🧪 Zadanie 7:
         * Utwórz serwlet, którego doGet() odczytuje WSZYSTKIE wartości parametru
         * "kolor" przez req.getParameterValues("kolor") (tablica String[]) i zwraca
         * je połączone przecinkiem. Zamapuj pod "/wiele-kolorow", wykonaj GET na
         * "/wiele-kolorow?kolor=czerwony&kolor=niebieski" i wypisz odpowiedź (oczekiwane
         * "czerwony,niebieski").
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_HeaderNamesEnumeration {
        /*
         * 🧪 Zadanie 8:
         * Utwórz serwlet, którego doGet() konwertuje req.getHeaderNames() przez
         * Collections.list(...) na List<String> i zwraca liczbę nagłówków przesłanych
         * przez klienta (list.size()). Zamapuj pod "/liczba-naglowkow", wykonaj GET z
         * dwoma dodatkowymi, własnymi nagłówkami i wypisz zwróconą liczbę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_GetInputStreamRawBytes {
        /*
         * 🧪 Zadanie 9:
         * Utwórz serwlet, którego doPost() czyta ciało żądania przez
         * req.getInputStream().readAllBytes() i zwraca liczbę odczytanych bajtów.
         * Zamapuj pod "/bajty", wyślij POST z ciałem "12345" (5 znaków ASCII = 5
         * bajtów) i wypisz odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ReaderAndInputStreamExclusive {
        /*
         * 🧪 Zadanie 10:
         * Utwórz serwlet, którego doPost() najpierw wywołuje req.getReader(), a
         * następnie (w tym samym wywołaniu doPost) próbuje wywołać
         * req.getInputStream() wewnątrz bloku try-catch. Zamapuj pod
         * "/wylaczne-api", wyślij dowolny POST i wypisz w odpowiedzi, czy druga próba
         * rzuciła IllegalStateException (zgodnie z wiedzą z lekcji).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ParametersAndHeaderCombined {
        /*
         * 🧪 Zadanie 11:
         * Utwórz serwlet, którego doGet() odczytuje parametr "uzytkownik" ORAZ
         * nagłówek "X-Sesja-Id", zwracając oba w jednej odpowiedzi w formacie
         * "uzytkownik=..., sesja=...". Zamapuj pod "/kombo-param-naglowek", wykonaj
         * GET na "/kombo-param-naglowek?uzytkownik=Tomek" z nagłówkiem
         * "X-Sesja-Id: sid-42" i wypisz odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FullEchoParametersHeadersBody {
        /*
         * 🧪 Zadanie 12:
         * Napisz serwlet analogiczny do RequestEchoServlet z teorii, ale dla własnego
         * zestawu danych: zamapuj pod "/pelne-echo", wyślij POST z parametrami w URL
         * ("?jezyk=polski"), własnym nagłówkiem "X-Klient: TestClient" i ciałem JSON
         * "{\"a\":1}". Wypisz pełną odpowiedź zawierającą sekcje parametrów,
         * nagłówków i ciała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ParameterMapMultipleKeys {
        /*
         * 🧪 Zadanie 13:
         * Utwórz serwlet, którego doGet() buduje z req.getParameterMap() posortowaną
         * (po kluczu) listę par "klucz=wartosci" i zwraca ją linia po linii. Zamapuj
         * pod "/sortowane-param", wykonaj GET na
         * "/sortowane-param?z=1&a=2&m=3" i wypisz odpowiedź - sprawdź, że klucze
         * pojawiają się w kolejności alfabetycznej (a, m, z).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConditionalLogicBasedOnHeader {
        /*
         * 🧪 Zadanie 14:
         * Utwórz serwlet, którego doGet() sprawdza nagłówek "X-Klient-Typ" - jeśli
         * wartość to "mobilny", zwraca "Widok mobilny", w przeciwnym razie (lub gdy
         * brak nagłówka) zwraca "Widok standardowy". Zamapuj pod "/responsywny",
         * wykonaj dwa żądania GET (z nagłówkiem "mobilny" i bez niego) i wypisz obie
         * odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BodyParsedAsKeyValuePairs {
        /*
         * 🧪 Zadanie 15:
         * Utwórz serwlet, którego doPost() czyta całe ciało żądania przez
         * req.getReader(), ręcznie dzieli je po znaku "&" i "=" (bez gotowego
         * parsera formularzy) na pary klucz-wartość, i zwraca liczbę znalezionych
         * par. Zamapuj pod "/parsuj-recznie", wyślij POST z ciałem "a=1&b=2&c=3" i
         * wypisz odpowiedź (oczekiwane 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CountHeadersStartingWithPrefix {
        /*
         * 🧪 Zadanie 16:
         * Utwórz serwlet, którego doGet() zlicza, ile nagłówków żądania zaczyna się
         * od prefiksu "X-" (case-insensitive) i zwraca tę liczbę jako tekst. Zamapuj
         * pod "/prefiks-x", wykonaj GET z trzema własnymi nagłówkami "X-A", "X-B",
         * "Inny-Naglowek" i wypisz odpowiedź (oczekiwane 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ParameterValidationWithError {
        /*
         * 🧪 Zadanie 17:
         * Utwórz serwlet, którego doGet() sprawdza obecność wymaganego parametru
         * "id" - jeśli brak (null), woła resp.sendError(400, "Brak parametru id"), w
         * przeciwnym razie zwraca "id=" + wartosc. Zamapuj pod "/wymagany-param",
         * wykonaj dwa żądania GET (z parametrem "id=7" i bez niego) i wypisz oba
         * statusy oraz ciała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BodyLengthVsContentLengthHeader {
        /*
         * 🧪 Zadanie 18:
         * Utwórz serwlet, którego doPost() porównuje długość faktycznie odczytanego
         * ciała (readAllBytes().length) z wartością nagłówka "Content-Length"
         * (req.getHeader("Content-Length"), sparsowaną na int) i zwraca wynik
         * porównania jako tekst ("zgodne"/"niezgodne"). Zamapuj pod
         * "/dlugosc-vs-naglowek", wyślij POST z dowolnym ciałem i wypisz odpowiedź.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MultipleRequestsDifferentParametersLoop {
        /*
         * 🧪 Zadanie 19:
         * Zamapuj serwlet pod "/kwadrat", którego doGet() odczytuje parametr "n"
         * (parsowany jako int) i zwraca jego kwadrat jako tekst. W pętli for wykonaj
         * GET dla wartości n = 1, 2, 3, 4, 5 (5 osobnych żądań z różnym query
         * stringiem) i wypisz wszystkie 5 zwróconych wyników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CookiesArrayNullWhenAbsent {
        /*
         * 🧪 Zadanie 20:
         * Utwórz serwlet, którego doGet() wywołuje req.getCookies() i zwraca tekst
         * "brak ciasteczek" jeśli wynik to null, albo liczbę ciasteczek w
         * przeciwnym razie. Zamapuj pod "/ciastka", wykonaj zwykłe GET (bez
         * ustawiania żadnych ciasteczek po stronie klienta) i wypisz odpowiedź -
         * zweryfikuj, że getCookies() może zwrócić null.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullRequestInspectorReport {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj serwlet "inspektor", którego doPost() zwraca pełny raport
         * tekstowy zawierający: metodę HTTP (req.getMethod()), wszystkie parametry z
         * getParameterMap(), wszystkie nagłówki z getHeaderNames(), oraz całe ciało
         * żądania z getReader(). Zamapuj pod "/inspektor", wyślij POST z parametrami w
         * URL, własnym nagłówkiem i ciałem tekstowym, i wypisz cały raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ParameterDrivenRoutingLogic {
        /*
         * 🧪 Zadanie 22:
         * Utwórz serwlet pod "/dzialanie", którego doGet() odczytuje parametry "a",
         * "b" (jako int) oraz "operacja" ("dodaj"/"mnoz") i zwraca wynik odpowiedniej
         * operacji arytmetycznej jako tekst. Wykonaj 3 żądania GET z różnymi
         * kombinacjami (np. a=2,b=3,operacja=dodaj -> 5; a=4,b=5,operacja=mnoz -> 20;
         * nieznana operacja -> status 400) i wypisz wszystkie wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_HeaderBasedAuthSimulation {
        /*
         * 🧪 Zadanie 23:
         * Utwórz serwlet symulujący prostą autoryzację: doGet() sprawdza nagłówek
         * "X-Api-Key" - jeśli równa się "tajny-klucz", zwraca "Dostep przyznany", w
         * przeciwnym razie (lub gdy brak nagłówka) woła resp.sendError(401, "Brak
         * dostepu"). Zamapuj pod "/chroniony", wykonaj 3 żądania (poprawny klucz,
         * zły klucz, brak nagłówka) i wypisz wszystkie 3 statusy i ciała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BodyJsonManualFieldExtraction {
        /*
         * 🧪 Zadanie 24:
         * Utwórz serwlet, którego doPost() odczytuje ciało jako uproszczony JSON
         * (np. dokładnie w formacie {"imie":"Ala","wiek":30}) i RĘCZNIE (bez
         * biblioteki JSON, przez indexOf/substring) wyciąga wartość pola "imie".
         * Zamapuj pod "/wyciagnij-imie", wyślij taki ciąg jako POST i wypisz wynik
         * ekstrakcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MultiValueParameterAggregation {
        /*
         * 🧪 Zadanie 25:
         * Utwórz serwlet pod "/suma-ocen", którego doGet() odczytuje WSZYSTKIE
         * wartości parametru "ocena" (getParameterValues), parsuje każdą jako int,
         * sumuje je i zwraca sumę oraz średnią jako tekst. Wykonaj GET na
         * "/suma-ocen?ocena=3&ocena=4&ocena=5" i wypisz odpowiedź (suma=12, srednia=4.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RequestSizeLimitWithDetailedError {
        /*
         * 🧪 Zadanie 26:
         * Utwórz serwlet, którego doPost() odrzuca (sendError 413) żądania, których
         * ciało przekracza 50 bajtów, podając w komunikacie błędu dokładną liczbę
         * odebranych bajtów. Zamapuj pod "/limit-bajtow", wyślij jedno krótkie
         * żądanie (akceptowane, status 200) i jedno przekraczające limit (status
         * 413) - wypisz oba statusy i treść komunikatu błędu dla drugiego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_HeaderDrivenLanguageSelection {
        /*
         * 🧪 Zadanie 27:
         * Utwórz serwlet symulujący i18n: doGet() odczytuje nagłówek
         * "Accept-Language" - dla wartości zawierającej "pl" zwraca "Witaj", dla "en"
         * zwraca "Hello", w przeciwnym razie zwraca "Hi" (domyślny). Zamapuj pod
         * "/i18n", wykonaj 3 żądania GET z różnymi wartościami nagłówka i wypisz
         * wszystkie 3 odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CombinedValidationPipeline {
        /*
         * 🧪 Zadanie 28:
         * Utwórz serwlet pod "/rejestracja" (doPost), który waliduje KOLEJNO:
         * (1) obecność parametru "email" w query stringu (400 jeśli brak),
         * (2) obecność nagłówka "Content-Type" równego dokładnie "text/plain"
         * (415 jeśli inny), (3) niepuste ciało żądania (400 jeśli puste). Jeśli
         * wszystkie warunki spełnione, zwraca "Zarejestrowano: " + email. Przetestuj
         * co najmniej 2 przypadki (poprawny i jeden naruszający walidację) i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_StatefulCounterPerParameterValue {
        /*
         * 🧪 Zadanie 29:
         * Utwórz serwlet z polem Map<String, Integer> licznikiKategorii. doGet()
         * odczytuje parametr "kategoria", zwiększa licznik dla tej kategorii o 1 i
         * zwraca aktualną wartość. Zamapuj pod "/licz-kategorie", wykonaj GET z
         * kategoria=owoce, kategoria=warzywa, kategoria=owoce (3 żądania) i wypisz
         * wszystkie 3 wyniki (powinno być: owoce=1, warzywa=1, owoce=2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullRequestDrivenCapstoneEndpoint {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj serwlet "zamowienie" (doPost) łączący: odczyt parametru "produkt" z
         * query stringu, odczyt nagłówka "X-Klient-Id", odczyt ciała żądania jako
         * ilość zamówienia (parsowana jako int), i zwracający pełne podsumowanie
         * tekstowe "Klient X zamowil N szt. produktu Y". Zamapuj pod
         * "/zamowienie?produkt=Ksiazka", wyślij POST z nagłówkiem "X-Klient-Id: K99"
         * i ciałem "3", wypisz pełne podsumowanie, oraz osobno przetestuj brak
         * wymaganego nagłówka (oczekiwany status 400).
         */
        public static void main(String[] args) { }
    }
}
