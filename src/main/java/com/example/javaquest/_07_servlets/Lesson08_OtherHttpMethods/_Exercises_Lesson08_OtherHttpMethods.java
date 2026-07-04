package com.example.javaquest._07_servlets.Lesson08_OtherHttpMethods;

public class _Exercises_Lesson08_OtherHttpMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicPutServlet {
        /*
         * 🧪 Zadanie 1:
         * Uruchom embedded Tomcat (port 0) z serwletem mapowanym na "/produkt"
         * nadpisującym tylko doPut(). Wyślij żądanie PUT na "/produkt?id=1"
         * z ciałem "Laptop Dell". Serwlet ma odczytać ciało (getInputStream().readAllBytes())
         * i odpowiedzieć tekstem "Zapisano produkt id=1: Laptop Dell". Wypisz status i body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_BasicDeleteServlet {
        /*
         * 🧪 Zadanie 2:
         * W tym samym stylu co Zadanie 1 dodaj serwlet z doDelete() mapowany na "/produkt".
         * Wyślij DELETE na "/produkt?id=1" (użyj HttpRequest.Builder.DELETE()).
         * Serwlet ma odpowiedzieć "Usunieto produkt id=1". Wypisz status i body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_HeadRequestManual {
        /*
         * 🧪 Zadanie 3:
         * Utwórz serwlet z doGet() zwracającym tekst "Zawartosc strony glownej".
         * Wyślij żądanie HEAD (HttpRequest.newBuilder(uri).method("HEAD", HttpRequest.BodyPublishers.noBody())).
         * Wypisz status odpowiedzi oraz sprawdź, że body jest puste (mimo że doGet() zwraca tekst) –
         * to standardowe zachowanie doHead() dziedziczone z HttpServlet.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DefaultOptionsResponse {
        /*
         * 🧪 Zadanie 4:
         * Utwórz serwlet nadpisujący TYLKO doGet() i doPost() (bez żadnej innej metody).
         * Wyślij żądanie OPTIONS na jego ścieżkę "/zasob" (metoda .method("OPTIONS", noBody())).
         * Wypisz status odpowiedzi oraz wartość nagłówka "Allow" (response.headers().firstValue("Allow"))
         * – domyślna implementacja doOptions() powinna wylistować dozwolone metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SimplePatchViaServiceOverride {
        /*
         * 🧪 Zadanie 5:
         * Zbuduj serwlet z nadpisanym service(req, resp): jeśli "PATCH".equals(req.getMethod()),
         * odpowiedz "PATCH: aktualizacja czesciowa" i return, w przeciwnym razie wywołaj super.service(req, resp).
         * Wyślij żądanie PATCH (.method("PATCH", BodyPublishers.ofString("{}"))) na "/zasob" i wypisz body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_GetStillWorksAfterServiceOverride {
        /*
         * 🧪 Zadanie 6:
         * Do serwletu z Zadania 5 dodaj doGet() zwracający "GET dziala normalnie".
         * Wyślij zwykłe żądanie GET na "/zasob" i sprawdź, że mimo nadpisanego service()
         * odpowiedź pochodzi z doGet() (dzięki super.service(req, resp) dla metod innych niż PATCH).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PutIdempotencyDemo {
        /*
         * 🧪 Zadanie 7:
         * Wyślij TO SAMO żądanie PUT na "/produkt?id=5" z ciałem "Mysz" DWA RAZY pod rząd
         * do serwletu z Zadania 1 (lub analogicznego). Porównaj oba statusy i oba body –
         * powinny być identyczne, co ilustruje idempotentność PUT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DeleteMissingResource {
        /*
         * 🧪 Zadanie 8:
         * Rozszerz serwlet z doDelete() tak, aby dla id spoza zbioru {1,2,3}
         * wywoływał resp.sendError(404, "Zasob o id=" + id + " nie istnieje").
         * Wyślij DELETE na "/produkt?id=99" i wypisz status (powinien być 404) oraz body błędu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ReadingRequestBodyInPut {
        /*
         * 🧪 Zadanie 9:
         * W doPut() odczytaj ciało żądania jako String (UTF-8) metodą
         * new String(req.getInputStream().readAllBytes(), StandardCharsets.UTF_8).
         * Wyślij PUT z ciałem zawierającym polskie znaki, np. "Klawiatura – zażółć gęślą jaźń",
         * i sprawdź, że serwlet poprawnie odczytał i odesłał ten sam tekst w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_StatusCodeComparisonTable {
        /*
         * 🧪 Zadanie 10:
         * Zbuduj serwlet obsługujący GET (200), PUT (200), DELETE (200) oraz PATCH przez
         * nadpisany service() (200), a dla metody TRACE nic nie rób specjalnego (domyślne 405).
         * Wyślij po jednym żądaniu każdej z tych 4 metod plus TRACE, zbierz pary (metoda, status)
         * do listy i wypisz jako tabelę.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InMemoryCrudServlet {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj serwlet trzymający Map<Integer,String> produktów w polu instancyjnym.
         * doPut() dodaje/zastępuje wpis pod "id" parametrem query, doDelete() usuwa wpis,
         * doGet() zwraca zawartość mapy jako tekst. Wykonaj sekwencję: PUT id=1 "Telefon",
         * GET (sprawdź obecność), DELETE id=1, GET (sprawdź brak) – wypisz wszystkie odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_PatchPartialUpdate {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz serwlet z Zadania 11 o obsługę PATCH przez nadpisany service():
         * PATCH ma zmieniać WYŁĄCZNIE fragment istniejącej wartości (np. dopisując sufiks
         * " (PROMOCJA)" do nazwy produktu o danym id), bez usuwania i tworzenia od nowa.
         * Wykonaj: PUT id=2 "Zmywarka", PATCH id=2, GET id=2 – zweryfikuj wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_LoggingServiceOverride {
        /*
         * 🧪 Zadanie 13:
         * Nadpisz service(req, resp) tak, aby PRZED przetworzeniem żądania wypisywał na
         * konsoli linię "LOG: " + req.getMethod() + " " + req.getRequestURI(), a następnie
         * wołał super.service(req, resp) dla wszystkich metod. Wykonaj po jednym żądaniu
         * GET, PUT i DELETE i sprawdź, że każde zostało zalogowane przed obsłużeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PatchWithout405Comparison {
        /*
         * 🧪 Zadanie 14:
         * Zarejestruj DWA serwlety w tym samym kontekście: "patchServlet" (mapowany na
         * "/z-patch", z nadpisanym service() obsługującym PATCH) oraz "plainServlet"
         * (mapowany na "/bez-patch", zwykły HttpServlet TYLKO z doGet()). Wyślij PATCH
         * do OBU ścieżek i porównaj statusy – drugi powinien zwrócić 405 Method Not Allowed.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TraceRequestBehavior {
        /*
         * 🧪 Zadanie 15:
         * Wyślij żądanie TRACE (.method("TRACE", BodyPublishers.noBody())) do zwykłego
         * serwletu z samym doGet(). Wypisz status odpowiedzi i sprawdź w komentarzu/println,
         * czy odpowiedź to standardowe zachowanie doTrace() z HttpServlet, czy błąd.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleResourcesConcurrentMap {
        /*
         * 🧪 Zadanie 16:
         * Użyj java.util.concurrent.ConcurrentHashMap<Integer,String> jako magazynu w serwlecie
         * z doPut/doDelete/doGet. Wykonaj PUT dla trzech różnych id (10, 20, 30) z różnymi
         * nazwami, a następnie GET zwracający wszystkie wpisy posortowane po id – wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FullSequencePutPatchGetDelete {
        /*
         * 🧪 Zadanie 17:
         * Wykonaj pełną sekwencję na jednym zasobie o id=7: PUT (utworzenie), PATCH
         * (częściowa aktualizacja), GET (weryfikacja stanu po PATCH), DELETE (usunięcie),
         * GET (weryfikacja, że zasób zniknął – serwlet ma zwrócić 404 przez sendError).
         * Wypisz body i status każdego z 5 kroków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PutVsPatchSemanticsComparison {
        /*
         * 🧪 Zadanie 18:
         * Dla tego samego zasobu o wartości początkowej "Rower: kolor=czerwony, cena=1000"
         * wykonaj PUT z nowym pełnym opisem "Rower: cena=1200" (bez koloru) oraz osobno,
         * na kopii tego samego stanu początkowego, PATCH zmieniający TYLKO cenę na 1200.
         * Wypisz oba wynikowe stany i zaznacz różnicę: PUT nadpisuje całość, PATCH zachowuje resztę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TwoServletsOnePatchOneNot {
        /*
         * 🧪 Zadanie 19:
         * Zarejestruj serwlet "servletA" (nadpisany service() obsługujący PATCH i GET) pod
         * "/a" oraz serwlet "servletB" (bez nadpisanego service(), tylko domyślny HttpServlet
         * z doGet()) pod "/b". Wyślij PATCH do obu ścieżek i porównaj: pierwszy zwraca 200,
         * drugi 405. Wypisz oba wyniki obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TinyRestLikeCatalog {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj mały "katalog produktów" (Map<Integer,String> w polu serwletu): doGet() bez
         * parametru "id" zwraca całą listę, doGet() z "id" zwraca jeden produkt, doPut() tworzy
         * lub zastępuje produkt, doDelete() usuwa. Wykonaj: PUT id=1, PUT id=2, GET (lista),
         * GET id=1, DELETE id=2, GET (lista) – wypisz wynik każdego kroku.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCrudWithVersionedResource {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj klasę pomocniczą Produkt (nazwa, cena) trzymaną w Map<Integer,Produkt>
         * w polu serwletu. Zaimplementuj GET (lista i pojedynczy zasób po id), PUT (pełne
         * zastąpienie), PATCH przez service() (parsujący prosty format "cena=1234" i zmieniający
         * tylko cenę) oraz DELETE. Wykonaj pełny scenariusz z co najmniej 5 krokami i wypisz raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_IdempotencyLoopTest {
        /*
         * 🧪 Zadanie 22:
         * W pętli 5 razy wyślij TO SAMO żądanie PUT na "/produkt?id=3" z tym samym ciałem,
         * a potem w pętli 3 razy wyślij TO SAMO żądanie DELETE na "/produkt?id=3". Wypisz
         * status każdego wywołania i potwierdź (assert/println), że wynik końcowy jest zawsze
         * taki sam niezależnie od liczby powtórzeń – to właśnie oznacza idempotentność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LoggingAllMethodsBeforeDelegating {
        /*
         * 🧪 Zadanie 23:
         * Nadpisz service(req, resp) tak, aby zapisywał KAŻDE żądanie (metoda + ścieżka +
         * parametry) do wspólnej listy List<String> log (pole instancyjne serwletu), a potem
         * delegował do super.service(req, resp) niezależnie od metody. Wykonaj sekwencję
         * GET/PUT/PATCH/DELETE (4 żądania), a na końcu (z poziomu main, po zapytaniu specjalnym
         * GET "/log") wypisz zebrany log wszystkich żądań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_UnsupportedCustomMethod {
        /*
         * 🧪 Zadanie 24:
         * Wyślij żądanie z niestandardową metodą HTTP "FOO" (.method("FOO", BodyPublishers.noBody()))
         * do zwykłego HttpServlet (bez obsługi FOO). Sprawdź zwrócony status (oczekiwany 405).
         * Następnie zbuduj serwlet z nadpisanym service(), który dla nierozpoznanej metody
         * jawnie woła resp.sendError(501, "Metoda " + req.getMethod() + " nie jest obslugiwana")
         * i porównaj oba zachowania (405 domyślne vs 501 własne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ContentTypeReflectionOnPut {
        /*
         * 🧪 Zadanie 25:
         * W doPut() odczytaj nagłówek "Content-Type" żądania (req.getContentType()) i ustaw
         * DOKŁADNIE ten sam typ w odpowiedzi (resp.setContentType(...)) razem z odesłaniem
         * odebranego ciała. Wyślij PUT z Content-Type "application/json" i ciałem
         * "{\"cena\":999}", a następnie sprawdź, że odpowiedź ma ten sam Content-Type.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BulkPutThenSelectiveDelete {
        /*
         * 🧪 Zadanie 26:
         * Wykonaj PUT dla 5 produktów o id 1-5 (różne nazwy), następnie DELETE dla
         * WSZYSTKICH id nieparzystych (1, 3, 5), a na końcu GET zwracający listę
         * pozostałych produktów. Zweryfikuj, że pozostały dokładnie id 2 i 4.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PatchValidationBadRequest {
        /*
         * 🧪 Zadanie 27:
         * W obsłudze PATCH (przez service()) sprawdź, czy ciało żądania pasuje do prostego
         * formatu "cena=<liczba>" (regex). Jeśli nie pasuje (np. ciało "abc"), wywołaj
         * resp.sendError(400, "Nieprawidlowy format PATCH"). Przetestuj raz z poprawnym
         * ciałem "cena=500" (status 200) i raz z niepoprawnym "abc" (status 400).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_OptimisticVersioning {
        /*
         * 🧪 Zadanie 28:
         * Przechowuj dla każdego zasobu (Map<Integer,Produkt>, Produkt ma pole int version)
         * numer wersji zwiększany o 1 przy każdym PUT. doGet() ma zwracać wersję razem z danymi.
         * Wykonaj 3 kolejne PUT-y na tym samym id i zweryfikuj, że wersja rośnie 1 -> 2 -> 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_IfMatchConflictCheck {
        /*
         * 🧪 Zadanie 29:
         * Rozszerz serwlet z Zadania 28 tak, aby PUT wymagał nagłówka "If-Match" z aktualną
         * wersją zasobu (req.getIntHeader("If-Match")). Jeśli podana wersja NIE zgadza się
         * z bieżącą wersją zasobu, zwróć status 409 (Conflict). Przetestuj: poprawny PUT
         * z aktualną wersją (200), potem PUT z celowo nieaktualną wersją (409).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMethodReportTable {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny serwlet obsługujący GET, PUT, PATCH (przez service()), DELETE
         * oraz OPTIONS (domyślne). W jednym main() wykonaj sekwencję: GET, PUT, PATCH, GET,
         * DELETE, GET, OPTIONS – zbierz parę (metoda, oczekiwany status, faktyczny status)
         * dla każdego kroku do listy i wypisz na końcu czytelną tabelę podsumowującą,
         * potwierdzającą zgodność oczekiwań z rzeczywistością.
         */
        public static void main(String[] args) { }
    }
}
