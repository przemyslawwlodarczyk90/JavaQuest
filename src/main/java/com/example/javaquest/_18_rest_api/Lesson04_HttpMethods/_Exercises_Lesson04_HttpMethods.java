package com.example.javaquest._18_rest_api.Lesson04_HttpMethods;

public class _Exercises_Lesson04_HttpMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListSixHttpMethodsWithPurpose {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wymien 6 glownych metod HTTP (GET/POST/PUT/PATCH/
         * DELETE/HEAD/OPTIONS) - dla kazdej 1 zdanie, do czego sluzy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementGetEndpointForCollection {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `HttpServer` z endpointem `/tasks` obslugujacym GET,
         * zwracajacym liste min. 3 zadan (Mapa w pamieci) jako JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementPostEndpointCreatingNewResource {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz serwer z Zadania 2 o POST /tasks - tworzy NOWE zadanie,
         * serwer SAM nadaje ID - zwroc 201 Created z nowym zasobem w ciele.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ClassifySafeVsUnsafeMethods {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: podziel 6 metod na "bezpieczne" (nie zmieniaja
         * stanu) i "niebezpieczne" - dla kazdej z niebezpiecznych podaj,
         * JAKA zmiane stanu powoduje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementDeleteEndpoint {
        /*
         * 🧪 Zadanie 5:
         * Rozszerz serwer z Zadania 2/3 o DELETE /tasks/{id} - usuwa
         * zadanie, zwraca 204 No Content BEZ ciala odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainPutVsPatchDifference {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: wyjasnij roznice miedzy PUT (calkowita zamiana)
         * a PATCH (czesciowa aktualizacja) - podaj przyklad sytuacji,
         * gdzie uzycie PUT zamiast PATCH przypadkowo "wyzeruje" pole,
         * ktorego klient nie przeslal.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementPutEndpointReplacingWholeResource {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj PUT /tasks/{id} zastepujacy CALY zasob nowa
         * reprezentacja - zweryfikuj, ze pola NIEOBECNE w requescie znikaja
         * (bo caly obiekt zostal zastapiony).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementHeadEndpointReturningNoBody {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj obsluge HEAD dla `/tasks/{id}` - zwroc TE SAME
         * naglowki co GET, ale bez ciala - zweryfikuj po stronie klienta,
         * ze `body()` jest pusty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementOptionsEndpointWithAllowHeader {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj obsluge OPTIONS dla `/tasks` - zwroc naglowek
         * "Allow" z lista dozwolonych metod dla tego zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainIdempotencyInOwnWords {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij wlasnymi slowami pojecie idempotencji -
         * podaj 1 przyklad metody idempotentnej i 1 nie-idempotentnej z
         * uzasadnieniem.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementPatchWithPartialFieldUpdate {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj PRAWDZIWY PATCH (nie uproszczony jak w teorii) -
         * odbierz JSON z TYLKO wybranymi polami i zaktualizuj WYLACZNIE
         * je, pozostawiajac reszte zasobu BEZ ZMIAN - zweryfikuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DemonstratePutIdempotencyWithRepeatedCalls {
        /*
         * 🧪 Zadanie 12:
         * Wyslij 3x TEN SAM PUT do tego samego zasobu i zweryfikuj, ze
         * stan koncowy jest identyczny po kazdym wywolaniu (odczytaj
         * zasob przez GET po kazdym PUT i porownaj).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_DemonstratePostNonIdempotencyWithCounter {
        /*
         * 🧪 Zadanie 13:
         * Wyslij 3x TEN SAM POST i zweryfikuj, ze powstaly 3 ROZNE zasoby
         * (rozne ID) - policz i wypisz finalna liczbe elementow w kolekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_Return405ForUnsupportedMethod {
        /*
         * 🧪 Zadanie 14:
         * Rozszerz handler tak, zeby dla metody NIEOBSLUGIWANEJ przez dany
         * endpoint (np. PATCH na endpoincie obslugujacym tylko GET/POST)
         * zwracal 405 Method Not Allowed z naglowkiem "Allow" listujacym
         * dozwolone metody.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementConditionalGetBasedOnQueryParam {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj GET /tasks obslugujacy opcjonalny parametr query
         * "?completed=true/false" filtrujacy liste - zweryfikuj dla obu
         * wartosci i braku parametru (zwroc wszystko).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareResponseBodyPresenceAcrossMethods {
        /*
         * 🧪 Zadanie 16:
         * Wywolaj GET, POST, PUT, DELETE, HEAD na tym samym zasobie i
         * zbierz do tabeli (wypisz), ktore z nich zwrocily NIEPUSTE cialo
         * odpowiedzi, a ktore nie - porownaj z tabela z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementUpsertSemanticsWithPut {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj PUT /tasks/{id}, ktory TWORZY zasob, jesli ID
         * jeszcze nie istnieje (upsert - update-or-insert) - zweryfikuj:
         * status 201 dla nowego ID, 200 dla istniejacego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ValidateContentTypeBeforeProcessingBody {
        /*
         * 🧪 Zadanie 18:
         * Rozszerz POST/PUT tak, zeby odrzucaly (415 Unsupported Media
         * Type) requesty BEZ naglowka "Content-Type: application/json" -
         * zweryfikuj dla poprawnego i niepoprawnego naglowka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementDeleteIdempotencyWithSecondCallReturning404 {
        /*
         * 🧪 Zadanie 19:
         * Wywolaj DELETE /tasks/{id} DWUKROTNIE - pierwszy raz powinien
         * zwrocic 204, drugi 404 (juz nie istnieje) - w komentarzu wyjasnij,
         * dlaczego to WCIAZ liczy sie jako "idempotentne" (efekt koncowy
         * - brak zasobu - jest identyczny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildMethodDecisionTreeForNewFeature {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: dla funkcji "zmiana hasla uzytkownika" zaprojektuj
         * odpowiedni endpoint (URI+metoda) - uzasadnij wybor miedzy PUT,
         * PATCH i POST na podstawie semantyki operacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementJsonMergePatchSemantics {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj PATCH zgodny z semantyka "JSON Merge Patch"
         * (RFC 7396): pole z wartoscia `null` w ciele PATCH oznacza
         * "usun to pole", brak pola oznacza "zostaw bez zmian" - zweryfikuj
         * dla obu przypadkow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BuildGenericHttpMethodRouter {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj generyczny "router" mapujacy pary
         * (metoda, wzorzec sciezki) na funkcje obslugujace - obsluz min.
         * 5 kombinacji metoda+sciezka, wlacznie z automatycznym 405 dla
         * nieobslugiwanych metod na istniejacej sciezce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementConcurrentPutRaceConditionDemo {
        /*
         * 🧪 Zadanie 23:
         * Uzywajac 2 watkow wyslij "jednoczesnie" 2 rozne PUT do TEGO
         * SAMEGO zasobu (rozne dane) - zaobserwuj i opisz w komentarzu,
         * ktora wersja "wygrala" (ostatni zapis) - to wprowadzenie do
         * problemu race condition przy braku kontroli wersji (ETag wraca
         * w Lesson11).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementBulkDeleteEndpoint {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj DELETE /tasks?ids=1,2,3 usuwajacy WIELE zasobow
         * naraz - w komentarzu przedyskutuj, czy taki endpoint jest
         * "czysto RESTful", czy to pragmatyczny wyjatek (por. Lesson03
         * Zadanie 25).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementOptionsForEntireApiDiscovery {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj OPTIONS dla WIELU roznych zasobow w 1 aplikacji
         * (np. /tasks, /users) - kazdy zwraca WLASCIWA dla siebie liste
         * dozwolonych metod w naglowku Allow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementPatchWithArrayOperationsJsonPatch {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj (uproszczona wersje) semantyki "JSON Patch"
         * (RFC 6902) - PATCH z cialem w formie listy operacji
         * `[{"op":"replace","path":"/title","value":"Nowy tytul"}]` -
         * obsluz min. operacje "replace" i "remove".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MeasureAndCompareLatencyAcrossMethods {
        /*
         * 🧪 Zadanie 27:
         * Zmierz sredni czas odpowiedzi (min. 20 wywolan kazdej) dla GET,
         * POST, PUT, DELETE na tym samym serwerze - wypisz porownanie -
         * w komentarzu wyjasnij ewentualne roznice (np. koszt parsowania
         * ciala requestu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementIdempotencyKeyForNonIdempotentPost {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj POST respektujacy naglowek "Idempotency-Key" -
         * jesli 2 requesty maja TEN SAM klucz, zwroc TEN SAM juz utworzony
         * zasob zamiast tworzyc duplikat (wprowadzenie do Lesson15).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildMethodOverrideViaHeaderForRestrictedClients {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj wsparcie dla naglowka "X-HTTP-Method-Override" -
         * klient wysyla POST z tym naglowkiem ustawionym na "DELETE", a
         * serwer traktuje to jako faktyczne DELETE - w komentarzu wyjasnij,
         * KIEDY taki wzorzec bywa potrzebny (starsze proxy/firewalle
         * blokujace niektore metody).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullCrudResourceWithAllSevenMethods {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY zasob `/articles` obslugujacy WSZYSTKICH 7 metod
         * (GET kolekcja, GET element, POST, PUT, PATCH, DELETE, HEAD,
         * OPTIONS) z poprawnymi kodami statusu dla kazdej - napisz krotki
         * "test" w `main()` wywolujacy kazda z nich i weryfikujacy wynik.
         */
        public static void main(String[] args) { }
    }
}
