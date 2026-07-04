package com.example.javaquest._07_servlets.Lesson06_HttpServletResponse;

public class _Exercises_Lesson06_HttpServletResponse {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SetStatusExplicit200 {
        /*
         * 🧪 Zadanie 1:
         * Utwórz serwlet, którego doGet() jawnie wywołuje
         * resp.setStatus(HttpServletResponse.SC_OK) przed zapisaniem treści "Wszystko
         * OK". Zamapuj pod "/status-ok", wykonaj GET i wypisz statusCode() oraz ciało
         * odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SetStatus201Created {
        /*
         * 🧪 Zadanie 2:
         * Utwórz serwlet, którego doPost() ustawia
         * resp.setStatus(HttpServletResponse.SC_CREATED) (201) i zwraca tekst "Zasob
         * utworzony". Zamapuj pod "/utworz", wykonaj POST z dowolnym ciałem i wypisz
         * statusCode() oraz ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SendErrorNotFound {
        /*
         * 🧪 Zadanie 3:
         * Utwórz serwlet, którego doGet() wywołuje resp.sendError(404, "Nie znaleziono
         * strony"). Zamapuj pod "/nieistnieje-endpoint", wykonaj GET i wypisz
         * statusCode() oraz wynik sprawdzenia, czy ciało odpowiedzi zawiera podciąg
         * "Nie znaleziono strony".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SetHeaderSingleValue {
        /*
         * 🧪 Zadanie 4:
         * Utwórz serwlet, którego doGet() ustawia resp.setHeader("X-Autor",
         * "JavaQuest") i zwraca treść "Sprawdz naglowek". Zamapuj pod "/naglowek-autor",
         * wykonaj GET i wypisz wartość nagłówka X-Autor z odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SetHeaderOverwrites {
        /*
         * 🧪 Zadanie 5:
         * Utwórz serwlet, którego doGet() wywołuje resp.setHeader("X-Wersja", "1") a
         * zaraz potem resp.setHeader("X-Wersja", "2") (druga wartość nadpisuje
         * pierwszą). Zamapuj pod "/nadpisz-naglowek", wykonaj GET i wypisz wartość
         * nagłówka X-Wersja - zweryfikuj, że to "2".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddHeaderMultipleValues {
        /*
         * 🧪 Zadanie 6:
         * Utwórz serwlet, którego doGet() wywołuje resp.addHeader("X-Tag", "a"),
         * resp.addHeader("X-Tag", "b"), resp.addHeader("X-Tag", "c"). Zamapuj pod
         * "/wiele-tagow", wykonaj GET i wypisz WSZYSTKIE wartości nagłówka X-Tag przez
         * response.headers().allValues("X-Tag") - zweryfikuj, że są 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SendRedirectBasic {
        /*
         * 🧪 Zadanie 7:
         * Zarejestruj serwlet pod "/stara" wywołujący resp.sendRedirect("/nowa") oraz
         * serwlet pod "/nowa" zwracający "Jestes na nowej stronie". Wykonaj GET na
         * "/stara" klientem HttpClient BEZ podążania za przekierowaniami
         * (HttpClient.Redirect.NEVER) i wypisz status (oczekiwane 302) oraz nagłówek
         * Location.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ContentTypePlainVsHtml {
        /*
         * 🧪 Zadanie 8:
         * Utwórz dwa serwlety: pod "/jako-tekst" ustawiający Content-Type
         * "text/plain" ze zwykłym tekstem, pod "/jako-html" ustawiający Content-Type
         * "text/html" z treścią "<p>Akapit</p>". Wykonaj GET na obie ścieżki i wypisz
         * nagłówek Content-Type oraz ciało obu odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriterVsOutputStreamExclusive {
        /*
         * 🧪 Zadanie 9:
         * Utwórz serwlet, którego doGet() wywołuje resp.getWriter(), a następnie w
         * bloku try-catch próbuje wywołać resp.getOutputStream() na tej samej
         * odpowiedzi. Zamapuj pod "/wylaczne-response-api", wykonaj GET i wypisz w
         * odpowiedzi (lub w konsoli), czy druga próba rzuciła IllegalStateException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_SendErrorInternalServerError {
        /*
         * 🧪 Zadanie 10:
         * Utwórz serwlet, którego doGet() wywołuje
         * resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cos poszlo
         * nie tak"). Zamapuj pod "/blad-serwera", wykonaj GET i wypisz statusCode()
         * odpowiedzi - zweryfikuj wartość 500.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_StatusAndHeaderAndBodyCombined {
        /*
         * 🧪 Zadanie 11:
         * Utwórz serwlet, którego doGet() ustawia status 200, nagłówek "X-Zrodlo:
         * cwiczenie11", Content-Type "text/plain" oraz zapisuje ciało "Pelna
         * odpowiedz". Zamapuj pod "/pelna-odpowiedz", wykonaj GET i wypisz wszystkie
         * trzy elementy odpowiedzi naraz (status, nagłówek, ciało).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConditionalStatusBasedOnParameter {
        /*
         * 🧪 Zadanie 12:
         * Utwórz serwlet pod "/warunkowy", którego doGet() odczytuje parametr "id"
         * - jeśli równy "0", woła sendError(404, "Nie znaleziono"), w przeciwnym
         * razie zwraca status 200 z treścią "Znaleziono zasob " + id. Wykonaj dwa
         * żądania GET (id=0 i id=5) i wypisz oba statusy oraz ciała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RedirectChainTwoHops {
        /*
         * 🧪 Zadanie 13:
         * Zarejestruj 3 serwlety: "/etap1" przekierowujący na "/etap2", "/etap2"
         * przekierowujący na "/etap3", "/etap3" zwracający "Koniec podrozy". Wykonaj
         * GET na "/etap1" klientem z HttpClient.Redirect.NORMAL (podążającym za
         * przekierowaniami) i wypisz finalny status, finalny URI oraz ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultipleCustomHeadersReport {
        /*
         * 🧪 Zadanie 14:
         * Utwórz serwlet, którego doGet() ustawia 3 różne własne nagłówki
         * ("X-Request-Id", "X-Powered-By", "X-Timestamp" - dowolne wartości) oraz
         * zwraca treść "Sprawdz wszystkie naglowki". Zamapuj pod "/wiele-naglowkow",
         * wykonaj GET i wypisz wartości wszystkich 3 nagłówków z odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddHeaderVsSetHeaderComparison {
        /*
         * 🧪 Zadanie 15:
         * Utwórz dwa serwlety: pod "/set-wersja" wywołujący dwukrotnie
         * setHeader("X-Test", ...) (druga wartość nadpisuje), pod "/add-wersja"
         * wywołujący dwukrotnie addHeader("X-Test", ...) (obie wartości zostają).
         * Wykonaj GET na obie ścieżki i wypisz liczbę wartości nagłówka X-Test
         * (allValues().size()) dla każdej - powinno być 1 i 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ErrorBodyContainsCustomMessage {
        /*
         * 🧪 Zadanie 16:
         * Utwórz serwlet, którego doGet() sprawdza parametr "kod" i woła
         * resp.sendError(Integer.parseInt(kod), "Blad numer " + kod). Zamapuj pod
         * "/dynamiczny-blad", wykonaj GET z "?kod=403" i wypisz status oraz wynik
         * sprawdzenia, czy ciało zawiera "Blad numer 403".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RedirectWithQueryParameterPreserved {
        /*
         * 🧪 Zadanie 17:
         * Zarejestruj serwlet pod "/przekieruj-z-danymi", którego doGet() odczytuje
         * parametr "id" i woła resp.sendRedirect("/cel?id=" + id), oraz serwlet pod
         * "/cel" zwracający "Otrzymano id=" + req.getParameter("id"). Wykonaj GET na
         * "/przekieruj-z-danymi?id=77" klientem podążającym za przekierowaniami i
         * wypisz finalne ciało odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BinaryResponseViaOutputStream {
        /*
         * 🧪 Zadanie 18:
         * Utwórz serwlet, którego doGet() ustawia Content-Type
         * "application/octet-stream" i zapisuje przez resp.getOutputStream() tablicę
         * bajtów {1,2,3,4,5} (BEZ użycia getWriter()). Zamapuj pod "/bajty-odpowiedzi",
         * wykonaj GET pobierając odpowiedź jako byte[]
         * (HttpResponse.BodyHandlers.ofByteArray()) i wypisz jej długość oraz zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_StatusCodeConstantsTable {
        /*
         * 🧪 Zadanie 19:
         * Zarejestruj 4 serwlety zwracające kolejno statusy: SC_OK (200),
         * SC_CREATED (201), SC_NO_CONTENT (204), SC_NOT_FOUND (404) - każdy pod inną
         * ścieżką ("/s200", "/s201", "/s204", "/s404"). Wykonaj GET na wszystkie 4 i
         * wypisz otrzymane statusy jako tabelę (ścieżka -> status).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_HeadersDependingOnAcceptedFormat {
        /*
         * 🧪 Zadanie 20:
         * Utwórz serwlet pod "/format", którego doGet() sprawdza parametr "format" -
         * dla "json" ustawia Content-Type "application/json" i zwraca
         * "{\"ok\":true}", dla innych wartości (lub braku) ustawia "text/plain" i
         * zwraca "ok=true". Wykonaj dwa żądania GET (?format=json i bez parametru) i
         * wypisz Content-Type oraz ciało obu odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullResponseBuilderPipeline {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj serwlet pod "/zamowienie-status" (doGet), który na podstawie
         * parametru "stan" ustawia różne kombinacje status+nagłówek+ciało: "nowe" ->
         * 201 + nagłówek "X-Stan: nowe" + ciało "Zamowienie zlozone"; "brak" -> 404 +
         * sendError; "anulowane" -> 200 + nagłówek "X-Stan: anulowane" + ciało
         * "Zamowienie anulowane". Przetestuj wszystkie 3 warianty i wypisz pełne wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RedirectLoopDetectionManual {
        /*
         * 🧪 Zadanie 22:
         * Zarejestruj 2 serwlety tworzące PĘTLĘ przekierowań: "/petla-a"
         * przekierowuje na "/petla-b", a "/petla-b" przekierowuje z powrotem na
         * "/petla-a". Wykonaj GET na "/petla-a" klientem z ustawionym
         * followRedirects(NORMAL) - klient HttpClient sam przerwie zbyt długi
         * łańcuch przekierowań (rzuci wyjątek). Przechwyć go w try-catch i wypisz
         * jego typ/komunikat jako demonstrację ryzyka pętli przekierowań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ContentNegotiationWithHeaderAndStatusMatrix {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj serwlet pod "/matryca" (doGet), którego zachowanie zależy od DWÓCH
         * parametrów: "typ" ("json"/"text") ustawiającego Content-Type, oraz "wynik"
         * ("ok"/"blad") ustawiającego odpowiednio status 200 lub wywołującego
         * sendError(400). Przetestuj wszystkie 4 kombinacje (2x2) i wypisz status
         * oraz Content-Type dla każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CustomErrorPageWithHeaders {
        /*
         * 🧪 Zadanie 24:
         * Utwórz serwlet, którego doGet() PRZED wywołaniem sendError(404, "Zasob
         * usuniety") ustawia dodatkowy nagłówek "X-Error-Source: mojaAplikacja".
         * Zamapuj pod "/blad-z-naglowkiem", wykonaj GET i wypisz status, nagłówek
         * X-Error-Source oraz sprawdzenie, czy ciało zawiera "Zasob usuniety".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RedirectVsDirectResponseComparison {
        /*
         * 🧪 Zadanie 25:
         * Zarejestruj 2 serwlety zwracające tę samą, ostateczną treść "Wynik
         * koncowy" - jeden bezpośrednio (status 200 od razu), drugi przez
         * sendRedirect na ten pierwszy. Wykonaj GET na oba warianty (bezpośredni i
         * przez przekierowanie, klientem z NORMAL) i porównaj (println equals)
         * finalne ciała oraz zauważ różnicę w response.uri() między nimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_StreamingLargeBinaryResponse {
        /*
         * 🧪 Zadanie 26:
         * Utwórz serwlet, którego doGet() generuje tablicę 1000 bajtów (wartości
         * 0-255 cyklicznie) i zapisuje ją przez getOutputStream() z Content-Type
         * "application/octet-stream". Zamapuj pod "/duze-dane", pobierz odpowiedź
         * jako byte[] i zweryfikuj (println porównania) że jej długość to dokładnie
         * 1000 oraz że pierwszy i ostatni bajt mają oczekiwane wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_HeaderBasedVersioningRouting {
        /*
         * 🧪 Zadanie 27:
         * Utwórz serwlet pod "/wersjonowane" (doGet), który na podstawie nagłówka
         * "X-Api-Version" ("v1" lub "v2") ustawia różne ciała odpowiedzi i różny
         * nagłówek "X-Response-Version" w odpowiedzi. Wykonaj 2 żądania GET (v1 i
         * v2) oraz jedno bez nagłówka (oczekiwana domyślna wersja "v1") i wypisz
         * wszystkie 3 wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullErrorRecoveryFlow {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj scenariusz: serwlet pod "/waliduj" (doPost) sprawdza, czy ciało
         * żądania da się sparsować jako int - jeśli nie, sendError(400, "Niepoprawne
         * dane") z nagłówkiem "X-Blad: parsowanie"; jeśli tak, ale wartość jest
         * ujemna, sendError(422, "Wartosc musi byc nieujemna"); w przeciwnym razie
         * status 200 z ciałem "Zaakceptowano: " + wartosc. Przetestuj 3 przypadki
         * (poprawna liczba dodatnia, tekst niebędący liczbą, liczba ujemna) i wypisz
         * wszystkie statusy i ciała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ResponseHeaderAuditTrail {
        /*
         * 🧪 Zadanie 29:
         * Utwórz serwlet, którego doGet() dodaje (addHeader, nie setHeader) 5
         * kolejnych wpisów nagłówka "X-Audit-Step" ("krok1".."krok5") symulujących
         * ślad przetwarzania żądania, a na końcu zwraca status 200 z ciałem "Audyt
         * zakonczony". Zamapuj pod "/audyt", wykonaj GET i wypisz WSZYSTKIE wartości
         * nagłówka X-Audit-Step w kolejności, w jakiej zostały dodane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CapstoneStatusHeaderRedirectCombo {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny scenariusz "logowanie": serwlet pod "/login" (doPost)
         * sprawdza parametr "haslo" z ciała żądania - jeśli równe "tajne123", ustawia
         * nagłówek "X-Zalogowany: tak" i wywołuje sendRedirect("/panel"); w
         * przeciwnym razie sendError(401, "Bledne haslo"). Serwlet pod "/panel"
         * zwraca "Witaj w panelu". Przetestuj oba przypadki (poprawne i błędne
         * hasło) klientem NORMAL (podążającym za przekierowaniami dla przypadku
         * poprawnego) i wypisz pełne wyniki obu ścieżek.
         */
        public static void main(String[] args) { }
    }
}
