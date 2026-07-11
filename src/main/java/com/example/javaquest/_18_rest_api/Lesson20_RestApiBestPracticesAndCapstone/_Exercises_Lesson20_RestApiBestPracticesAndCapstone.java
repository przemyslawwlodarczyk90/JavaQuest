package com.example.javaquest._18_rest_api.Lesson20_RestApiBestPracticesAndCapstone;

public class _Exercises_Lesson20_RestApiBestPracticesAndCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListElevenBestPracticesFromChecklist {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz z pamieci (bez podgladania teorii) min. 7
         * z 11 punktow checklisty najlepszych praktyk z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunCapstoneAndObserveAllScenarios {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `_Lesson20_RestApiBestPracticesAndCapstone.main()` i
         * przeanalizuj output KAZDEGO z 6 scenariuszy - dla kazdego
         * zapisz w komentarzu, KTOREJ lekcji rozdzialu dotyczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddNewFieldToTaskRecord {
        /*
         * 🧪 Zadanie 3:
         * Dodaj nowe pole "dueDate" (String) do rekordu `Task` - upewnij
         * sie, ze WSZYSTKIE endpointy (GET/POST/PUT) nadal dzialaja
         * poprawnie z nowym polem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddSortByPriorityQueryParam {
        /*
         * 🧪 Zadanie 4:
         * Dodaj do GET /tasks obsluge `?sort=-priority` (sortowanie
         * malejace, por. Lesson10) - zweryfikuj dzialanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TestValidationWithMultipleClientRequests {
        /*
         * 🧪 Zadanie 5:
         * Wyslij 3 rozne niepoprawne POST-y (rozne kombinacje bledow) do
         * kapsztonu - zweryfikuj, ze KAZDY zwraca odpowiedni zestaw
         * bledow walidacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TriggerRateLimitByExceeding20Requests {
        /*
         * 🧪 Zadanie 6:
         * Wyslij 25 kolejnych requestow (tym samym X-Client-Id) do
         * kapsztonu - zweryfikuj, ze od 21. requesty dostaja 429.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestOptimisticLockingConflict {
        /*
         * 🧪 Zadanie 7:
         * Pobierz ETag zadania, "zmien" je w tle (inny PUT), a nastepnie
         * sprobuj PUT z NIEAKTUALNYM If-Match - zweryfikuj 412.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddNewEndpointForMarkingTaskDone {
        /*
         * 🧪 Zadanie 8:
         * Dodaj endpoint `PATCH /tasks/{id}/done` oznaczajacy zadanie
         * jako ukonczone (BEZ zmiany innych pol) - por. Lesson04 (PATCH
         * jako czesciowa aktualizacja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_VerifyIdempotencyAcrossThreeRetries {
        /*
         * 🧪 Zadanie 9:
         * Wyslij TEN SAM POST z TYM SAMYM kluczem idempotencji 3 razy -
         * zweryfikuj, ze powstalo TYLKO 1 zadanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IdentifyMissingBestPracticeInCapstone {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: przeanalizuj kod kapsztonu i znajdz min. 1
         * praktyke z checklisty (np. wersjonowanie, OpenAPI), ktora NIE
         * jest zaimplementowana w kapsztonie - wyjasnij, dlaczego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddPathVersioningToCapstone {
        /*
         * 🧪 Zadanie 11:
         * Dodaj wersjonowanie w URI (Lesson14) do kapsztonu - `/v1/tasks`
         * zwraca obecny ksztalt, `/v2/tasks` zwraca rozszerzony (np. z
         * "dueDate") - oba dzialajace rownolegle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GenerateOpenApiSpecForCapstone {
        /*
         * 🧪 Zadanie 12:
         * Napisz (Lesson18) pelna specyfikacje OpenAPI dla WSZYSTKICH
         * endpointow kapsztonu (GET/POST/PUT/DELETE na /tasks) wlacznie
         * z schematami bledow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddCacheControlHeaderToListEndpoint {
        /*
         * 🧪 Zadanie 13:
         * Dodaj naglowek "Cache-Control: no-cache" do GET /tasks (dane
         * zmienne, ale WARTO zweryfikowac ETag przed uzyciem) - w
         * odroznieniu od "max-age" dla stabilnych zasobow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementBulkTaskCreationEndpoint {
        /*
         * 🧪 Zadanie 14:
         * Dodaj `POST /tasks/bulk` przyjmujacy tablice zadan - kazde
         * walidowane NIEZALEZNIE (collect-all na poziomie CALEJ tablicy) -
         * zwroc liste wynikow (sukces/blad) per element.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddCorrelationIdPropagationAcrossCapstone {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj obsluge naglowka "X-Correlation-Id" WYSYLANEGO
         * przez klienta (Lesson12) - jesli obecny, uzyj go w KAZDYM
         * bledzie zamiast generowac nowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementDeprecationForV1AfterAddingV2 {
        /*
         * 🧪 Zadanie 16:
         * Po ukonczeniu Zadania 11, dodaj naglowki "Deprecation"/"Sunset"
         * do odpowiedzi z `/v1/tasks` - zweryfikuj obecnosc w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementContentNegotiationForCsvExport {
        /*
         * 🧪 Zadanie 17:
         * Dodaj do GET /tasks obsluge "Accept: text/csv" (Lesson07)
         * zwracajaca liste zadan jako CSV zamiast JSON.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WriteAutomatedTestSuiteForCapstone {
        /*
         * 🧪 Zadanie 18:
         * Napisz "test suite" (metoda w `main()`) automatycznie
         * weryfikujaca WSZYSTKICH 6 scenariuszy z teorii przez asercje
         * (rzuc wyjatek przy niezgodnosci) zamiast tylko wypisywac wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AddTaskPriorityFilteringWithRangeOperator {
        /*
         * 🧪 Zadanie 19:
         * Dodaj do GET /tasks obsluge `?priority[gte]=2` (Lesson10,
         * operatory zakresu) - zweryfikuj poprawne filtrowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorValidationIntoReusableValidatorClass {
        /*
         * 🧪 Zadanie 20:
         * Wydziel logike walidacji z `TasksApi` do osobnej, reuzywalnej
         * klasy `TaskValidator` (Lesson13, Zadanie 16) - zweryfikuj
         * identyczne zachowanie po refaktoryzacji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_AddSecondResourceWithRelationship {
        /*
         * 🧪 Zadanie 21:
         * Dodaj drugi zasob "Category" i relacje `/tasks?categoryId=X`
         * ORAZ zagniezdzony `/categories/{id}/tasks` (Lesson03) -
         * zweryfikuj oba sposoby dostepu do tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullRfc7807ComplianceAcrossAllErrors {
        /*
         * 🧪 Zadanie 22:
         * Zweryfikuj (i popraw, jesli trzeba) ze WSZYSTKIE bledy w
         * kapsztonie (400/404/405/412/422/429) maja DOKLADNIE ten sam
         * zestaw kluczy JSON zgodny z RFC 7807 (Lesson12) - napisz test
         * kontraktu sprawdzajacy to automatycznie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementConcurrentAccessStressTest {
        /*
         * 🧪 Zadanie 23:
         * Uzywajac wielu watkow, wyslij wspolbiezne PUT-y do TEGO SAMEGO
         * zadania z tym samym poczatkowym ETag - zweryfikuj, ze DOKLADNIE
         * 1 zakonczyl sie sukcesem (Lesson11, optymistyczna kontrola
         * wspolbieznosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementHateoasLinksInTaskResponse {
        /*
         * 🧪 Zadanie 24:
         * Rozszerz odpowiedz `/tasks/{id}` o pole "_links" (Lesson02,
         * Poziom 3 Richardsona) z linkami "self" i "delete" - zweryfikuj
         * poprawnosc struktury.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFullMigrationFromV1ToV2WithFieldMapping {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj automatyczny mapper miedzy ksztaltem v1 i v2
         * kapsztonu (Lesson14, Zadanie 23) - uzyj go, zeby v1 API bylo
         * "cienka warstwa" nad logika v2, zamiast duplikowac kod.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementRealTokenBucketRateLimitingInsteadOfSimpleCounter {
        /*
         * 🧪 Zadanie 26:
         * Zastap prosty licznik rate limitingu w kapsztonie PRAWDZIWYM
         * `TokenBucket` z Lesson16 (z odnawianiem w czasie) - zweryfikuj
         * identyczne zachowanie testow z teorii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GenerateFullPostmanCollectionForCapstone {
        /*
         * 🧪 Zadanie 27:
         * Wygeneruj (Lesson17) pelna kolekcje Postmana dla WSZYSTKICH
         * endpointow kapsztonu, wlacznie ze zmiennymi srodowiskowymi i
         * testami sprawdzajacymi kody statusu - zapisz jako plik .json.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementGraphQlLikeFieldSelectionOverCapstone {
        /*
         * 🧪 Zadanie 28:
         * Dodaj do GET /tasks obsluge `?fields=id,title` (Lesson19,
         * czesciowe rozwiazanie over-fetchingu) - zwroc TYLKO wskazane
         * pola dla kazdego zadania w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullIntegrationTestSuiteCoveringAllChapterTopics {
        /*
         * 🧪 Zadanie 29:
         * Napisz KOMPLETNY "test suite" (min. 15 asercji) weryfikujacy
         * WSZYSTKIE mechanizmy z tego rozdzialu dzialajace RAZEM w
         * kapsztonie - traktuj to jako "final exam" calego `_18_rest_api`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ExtendCapstoneIntoFullProjectWithDocumentation {
        /*
         * 🧪 Zadanie 30:
         * Rozbuduj kapszton o WSZYSTKIE brakujace praktyki z checklisty
         * (wersjonowanie, OpenAPI, content negotiation, prawdziwy token
         * bucket) w 1 SPOJNYM projekcie - napisz podsumowanie (komentarz)
         * opisujace KAZDA zastosowana technike i lekcje, z ktorej pochodzi.
         */
        public static void main(String[] args) { }
    }
}
