package com.example.javaquest._22_spring_web.Lesson19_RestApiCapstone;

public class _Exercises_Lesson19_RestApiCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyCapstoneUsesSpringMvcNotHttpServer {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala (powiaz z `_18_rest_api/Lesson20`): wyjasnij,
         * co Spring MVC ZAOSZCZEDZILO wzgledem RECZNEJ implementacji
         * NA surowym `HttpServer`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddNewFieldToTaskEntity {
        /*
         * 🧪 Zadanie 2:
         * Dodaj NOWE pole DO `TaskEntity` (np. `priority`) -
         * przeprowadz je PRZEZ Request/Response DTO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImplementMarkTaskDoneEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj endpoint `PATCH /api/tasks/{id}/done` -
         * oznaczajacy zadanie jako WYKONANE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestAllSixScenariosIndependently {
        /*
         * 🧪 Zadanie 4:
         * Uruchom KAZDY z 6 scenariuszy Z teorii OSOBNO - zapisz
         * DOKLADNE statusy I body.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementGetAllTasksWithoutPagination {
        /*
         * 🧪 Zadanie 5:
         * Dodaj alternatywny endpoint zwracajacy WSZYSTKIE zadania BEZ
         * stronicowania - powiaz Z ryzykiem DLA duzych zbiorow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ImplementDeleteNonExistentTaskErrorHandling {
        /*
         * 🧪 Zadanie 6:
         * Wyslij DELETE NA NIEISTNIEJACE ID - zweryfikuj 404 (NIE
         * cichy sukces).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ImplementUpdateNonExistentTaskErrorHandling {
        /*
         * 🧪 Zadanie 7:
         * Wyslij PUT NA NIEISTNIEJACE ID - zweryfikuj 404.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementCreateTaskWithTooLongTitle {
        /*
         * 🧪 Zadanie 8:
         * Wyslij tytul PRZEKRACZAJACY 200 znakow - zweryfikuj 400 Z
         * komunikatem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementFilterByDoneStatus {
        /*
         * 🧪 Zadanie 9:
         * Zweryfikuj `?done=true`/`?done=false` NA endpoincie listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainRoleOfEachLayerInCapstone {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij ROLE kazdej warstwy kapsztonu
         * (kontroler/repozytorium/DTO/advice/interceptor/CORS-config).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementSortingParameterOnListEndpoint {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z Lesson13 - dodaj `?sort=title,desc` DO endpointu
         * listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementSearchByTitleSubstring {
        /*
         * 🧪 Zadanie 12:
         * Dodaj `?q=...` - filtruj zadania PO CZESCI tytulu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementOptimisticConcurrencySimulation {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_18_rest_api/Lesson11` (ETag) - dodaj PROSTY numer
         * wersji DO `TaskEntity`, ODRZUC aktualizacje Z NIEAKTUALNA
         * wersja (409).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementBatchCreateEndpoint {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj `POST /api/tasks/batch` PRZYJMUJACY LISTE
         * `CreateTaskRequest` - zwroc LISTE utworzonych zadan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementResponseCachingHeaderOnListEndpoint {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `_18_rest_api/Lesson11` - dodaj `Cache-Control` DO
         * odpowiedzi listy zadan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRequestValidationLoggingInterceptor {
        /*
         * 🧪 Zadanie 16:
         * Rozszerz `AccessLogInterceptor` - zaloguj TEZ status
         * odpowiedzi PRZEZ `afterCompletion`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementTaskCategoriesAsEnum {
        /*
         * 🧪 Zadanie 17:
         * Dodaj pole `category` (enum) DO `TaskEntity` - zwaliduj
         * DOZWOLONE wartosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementServiceLayerBetweenControllerAndRepository {
        /*
         * 🧪 Zadanie 18:
         * Powiaz z `_22_spring_web/Lesson04` (kontroler-serwis-
         * repozytorium) - wydziel WARSTWE serwisu (logika biznesowa)
         * MIEDZY kontrolerem A repozytorium.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureThroughputOfCapstoneApi {
        /*
         * 🧪 Zadanie 19:
         * Zmierz PRZEPUSTOWOSC (zadan/sekunde) API DLA endpointu listy
         * PRZY 100 zapisanych zadaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildFullApiReferenceDocumentation {
        /*
         * 🧪 Zadanie 20:
         * Powiaz z Lesson18 - dodaj springdoc DO tego kapsztonu -
         * wygeneruj PELNA dokumentacje Swagger DLA WSZYSTKICH
         * endpointow.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementRateLimitingOnCreateEndpoint {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_18_rest_api/Lesson16` - ogranicz CZESTOTLIWOSC
         * `POST /api/tasks` PER klient.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementIdempotencyKeyOnCreateEndpoint {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_18_rest_api/Lesson15` - dodaj obsluge naglowka
         * `Idempotency-Key` NA `POST /api/tasks`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementAuthenticationAndAuthorizationSimulation {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_19_security_basics` - dodaj SYMULOWANA autoryzacje
         * (naglowek `X-Api-Key`) - TYLKO uprawnieni MOGA usuwac zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementEventPublishingOnTaskCreated {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_20_spring_core/Lesson20_ApplicationEvents` -
         * opublikuj zdarzenie `TaskCreatedEvent` PO utworzeniu zadania,
         * SUBSKRYBOWANE przez WLASNY listener (np. logujacy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFullAuditTrailForAllMutations {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_19_security_basics/Lesson19` - zaloguj KAZDA
         * operacje ZAPISU (create/update/delete) DO dziennika audytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementMetricsForApiOperations {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_21_spring_boot/Lesson13` - zbierz metryki
         * (liczba/czas) DLA KAZDEJ operacji CRUD PRZEZ Micrometer.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementGracefulErrorForConcurrentModification {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_05_multithreading` - wyslij WIELE jednoczesnych
         * aktualizacji TEGO SAMEGO zadania - zweryfikuj SPOJNOSC danych
         * (`ConcurrentHashMap`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementApiVersioningForCapstone {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z `_18_rest_api/Lesson14` - dodaj `/api/v2/tasks` Z
         * ROZSZERZONYM ksztaltem odpowiedzi, ZACHOWUJAC `/api/tasks`
         * (v1) BEZ zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareThisCapstoneWithChapter18Capstone {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: NAPISZ PORÓWNANIE (linie kodu, czytelnosc,
         * latwosc rozszerzenia) miedzy TYM kapsztonem A
         * `_18_rest_api/Lesson20` - CO Spring MVC DAJE "za darmo"?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ExtendCapstoneWithAllRemainingChapterFeatures {
        /*
         * 🧪 Zadanie 30:
         * Rozszerz kapszton O WSZYSTKIE POZOSTALE mechanizmy Z
         * rozdzialu, KTORYCH JESZCZE tu NIE UZYTO (Lesson11 negocjacja
         * tresci, Lesson14 upload plikow, Lesson17 wywolanie
         * ZEWNETRZNEGO serwisu PRZEZ RestClient) - jeden, MAKSYMALNIE
         * kompletny system.
         */
        public static void main(String[] args) { }
    }
}
