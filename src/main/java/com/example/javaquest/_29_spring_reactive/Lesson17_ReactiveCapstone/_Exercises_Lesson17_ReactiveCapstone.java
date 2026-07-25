package com.example.javaquest._29_spring_reactive.Lesson17_ReactiveCapstone;

public class _Exercises_Lesson17_ReactiveCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddPutEndpointForUpdatingBookTitle {
        /* 🧪 Zadanie 1: Dodaj endpoint PUT DO aktualizacji tytulu ksiazki. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddValidationRejectingEmptyTitleOnCreate {
        /* 🧪 Zadanie 2: Dodaj walidacje ODRZUCAJACA PUSTY tytul PRZY tworzeniu. */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddSecondUserWithDifferentRole {
        /* 🧪 Zadanie 3: Dodaj DRUGIEGO uzytkownika Z INNA rola. */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddGetByAuthorQueryEndpoint {
        /* 🧪 Zadanie 4: Dodaj endpoint zapytania PO autorze. */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddStepVerifierTestForGetAllBooksPipeline {
        /* 🧪 Zadanie 5: Dodaj test `StepVerifier` DLA pipeline'u pobierania WSZYSTKICH ksiazek. */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddCountEndpointReturningTotalNumberOfBooks {
        /* 🧪 Zadanie 6: Dodaj endpoint zwracajacy CALKOWITA LICZBE ksiazek. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ProtectGetByIdEndpointRequiringAuthentication {
        /* 🧪 Zadanie 7: Zabezpiecz endpoint GET PO id, WYMAGAJAC uwierzytelnienia. */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddErrorHandlingFor404WhenBookNotFound {
        /* 🧪 Zadanie 8: Dodaj obsluge bledu 404, GDY ksiazka NIE ISTNIEJE. */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestFullScenarioWithTwoBooksCreatedAndOneDeleted {
        /* 🧪 Zadanie 9: Przetestuj PELNY scenariusz Z 2 UTWORZONYMI ksiazkami I 1 USUNIETA. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyDatabaseClientWasChosenOverR2dbcRepository {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO uzyto `DatabaseClient` ZAMIAST `R2dbcRepository`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddPaginationSupportToGetAllBooksEndpoint {
        /* 🧪 Zadanie 11: Dodaj WSPARCIE stronicowania DO endpointu WSZYSTKICH ksiazek. */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddCategoryFieldWithFilteringByCategoryQueryParam {
        /* 🧪 Zadanie 12: Dodaj pole kategorii Z FILTROWANIEM przez parametr zapytania. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementBulkImportEndpointAcceptingFluxOfBooks {
        /* 🧪 Zadanie 13: Zaimplementuj endpoint MASOWEGO importu przyjmujacy `Flux<Ksiazka>`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddOptimisticLockingUsingVersionColumnForConcurrentUpdates {
        /* 🧪 Zadanie 14: Powiaz z `_23_spring_data_jpa/Lesson08` - dodaj blokowanie optymistyczne DLA ROWNOLEGLYCH aktualizacji. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRoleBasedAuthorizationSeparatingReaderAndAdminPermissions {
        /* 🧪 Zadanie 15: Zaimplementuj autoryzacje OPARTA NA roli ROZDZIELAJACA uprawnienia czytelnika I administratora. */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddServerSentEventsEndpointStreamingNewlyAddedBooks {
        /* 🧪 Zadanie 16: Dodaj endpoint SSE streamujacy NOWO dodane ksiazki. */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildComprehensiveWebTestClientTestSuiteForAllEndpoints {
        /* 🧪 Zadanie 17: Powiaz z `_27_spring_test` - zbuduj PAKIET testow `WebTestClient` DLA WSZYSTKICH endpointow. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCachingLayerForFrequentlyAccessedBooks {
        /* 🧪 Zadanie 18: Zaimplementuj WARSTWE cache DLA CZESTO odczytywanych ksiazek. */
        public static void main(String[] args) { }
    }

    static class Exercise19_AddCustomExceptionHandlerReturningRfc7807ProblemDetails {
        /* 🧪 Zadanie 19: Powiaz z `_18_rest_api/Lesson12` - dodaj WLASNY handler bledow ZWRACAJACY RFC 7807 Problem Details. */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureThroughputOfCapstoneApiUnderConcurrentLoad {
        /* 🧪 Zadanie 20: Zmierz PRZEPUSTOWOSC API kapsztonu POD rownoleglym obciazeniem. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ExtendCapstoneWithAuthorEntityAndOneToManyRelationship {
        /* 🧪 Zadanie 21: Rozszerz kapszton O encje Autor Z relacja jeden-do-wielu. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullTextSearchEndpointUsingH2FullTextSearchOrLikeQuery {
        /* 🧪 Zadanie 22: Zaimplementuj endpoint wyszukiwania pelnotekstowego. */
        public static void main(String[] args) { }
    }

    static class Exercise23_AddDistributedTracingHeadersPropagatedThroughReactiveContext {
        /* 🧪 Zadanie 23: Powiaz z `_31_spring_cloud_microservices/Lesson11` - dodaj naglowki distributed tracing PROPAGOWANE PRZEZ Reactor Context. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRateLimitingPerUserUsingCustomWebFilter {
        /* 🧪 Zadanie 24: Zaimplementuj ograniczanie tempa PER uzytkownik uzywajac WLASNEGO `WebFilter`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildEventPublishingWhenBookIsCreatedUsingApplicationEventPublisher {
        /* 🧪 Zadanie 25: Powiaz z `_20_spring_core/Lesson20` - zbuduj publikacje zdarzenia PRZY utworzeniu ksiazki. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementFullAuditTrailForAllWriteOperationsUsingR2dbc {
        /* 🧪 Zadanie 26: Zaimplementuj PELNY dziennik audytu DLA WSZYSTKICH operacji zapisu uzywajac R2DBC. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildResilientVersionWithRetryFallbackAndTimeoutForAllDatabaseOperations {
        /* 🧪 Zadanie 27: Powiaz z `Lesson07` - zbuduj ODPORNA wersje Z retry+fallback+timeout DLA WSZYSTKICH operacji bazodanowych. */
        public static void main(String[] args) { }
    }

    static class Exercise28_MigrateCapstoneFromDatabaseClientToR2dbcRepositoryInterface {
        /* 🧪 Zadanie 28: ZMIGRUJ kapszton Z `DatabaseClient` NA interfejs `R2dbcRepository`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildCompleteIntegrationTestSuiteUsingTestcontainersPostgresqlInsteadOfH2 {
        /* 🧪 Zadanie 29: Powiaz z `_26_integration_testing` - zbuduj PAKIET testow Z Testcontainers PostgreSQL ZAMIAST H2. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullProductionReadyReactiveMicroserviceCombiningEntireChapter {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY, PRODUKCYJNY mikroserwis reaktywny LACZACY WIEDZE Z CALEGO rozdzialu. */
        public static void main(String[] args) { }
    }
}
