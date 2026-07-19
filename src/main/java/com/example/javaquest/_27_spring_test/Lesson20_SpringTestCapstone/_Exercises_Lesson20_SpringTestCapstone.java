package com.example.javaquest._27_spring_test.Lesson20_SpringTestCapstone;

public class _Exercises_Lesson20_SpringTestCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddDeleteTaskEndpointAndTestIt {
        /* 🧪 Zadanie 1: Dodaj `DELETE /tasks/{id}` DO `TaskController` I NAPISZ test. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddGetSingleTaskEndpointAndTest404 {
        /* 🧪 Zadanie 2: Dodaj `GET /tasks/{id}` I przetestuj 404 DLA nieznanego ID. */
        public static void main(String[] args) { }
    }

    static class Exercise03_VerifyCreatedAtIsAlwaysFixedDateInTests {
        /* 🧪 Zadanie 3: Zweryfikuj, ze `createdAt` jest ZAWSZE TA SAMA data (@TestConfiguration). */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddSecondArchUnitRuleForServiceLayer {
        /* 🧪 Zadanie 4: Dodaj DRUGA regule ArchUnit (np. TaskService NIE zalezy OD TaskController). */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainRoleOfEachTechniqueInCapstone {
        /* 🧪 Zadanie 5: Bez terminala - opisz ROLE KAZDEJ techniki uzytej W kapsztonie. */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddValidationRejectingEmptyTitle {
        /* 🧪 Zadanie 6: Dodaj walidacje odrzucajaca PUSTY tytul I NAPISZ test 400. */
        public static void main(String[] args) { }
    }

    static class Exercise07_VerifyNotificationServiceCalledExactlyOncePerTask {
        /* 🧪 Zadanie 7: Zweryfikuj `verify(notificationService, times(1))` NA zamowienie. */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestNotificationServiceFailureDoesNotBreakTaskCreation {
        /* 🧪 Zadanie 8: Zaprogramuj `@MockitoBean` DO rzucania wyjatku - zaobserwuj wplyw NA test. */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddSecondActiveProfileAndVerifyDifferentBehavior {
        /* 🧪 Zadanie 9: Powiaz z Lesson10 - dodaj DRUGI profil Z INNYM zachowaniem. */
        public static void main(String[] args) { }
    }

    static class Exercise10_RunCapstoneTestsAndMeasureTotalExecutionTime {
        /* 🧪 Zadanie 10: Zmierz CALKOWITY czas wykonania kapsztonu. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddTaskCompletionStatusAndUpdateEndpoint {
        /* 🧪 Zadanie 11: Dodaj status ukonczenia zadania I endpoint AKTUALIZUJACY go. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPaginationForListTasksEndpoint {
        /* 🧪 Zadanie 12: Powiaz z `_23_spring_data_jpa/Lesson06` - dodaj stronicowanie DO `GET /tasks`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddWebMvcTestSliceVariantOfControllerTest {
        /* 🧪 Zadanie 13: Powiaz z Lesson05 - dodaj RÓWNOLEGLY test `@WebMvcTest` (SZYBSZY WARIANT). */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddDataJpaTestForTaskRepositoryDirectly {
        /* 🧪 Zadanie 14: Powiaz z Lesson06 - dodaj `@DataJpaTest` TESTUJACY `TaskRepository` BEZPOSREDNIO. */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestTransactionalRollbackOnTaskCreationFailure {
        /* 🧪 Zadanie 15: Powiaz z Lesson14 - przetestuj rollback PRZY BLEDZIE W TRAKCIE tworzenia zadania. */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureContextCacheHitAcrossMultipleTestClassesInThisPackage {
        /* 🧪 Zadanie 16: Powiaz z Lesson17 - zweryfikuj cache'owanie kontekstu MIEDZY testami Z Zadania 13/14 I glownym testem. */
        public static void main(String[] args) { }
    }

    static class Exercise17_AddSecuredEndpointRequiringAuthentication {
        /* 🧪 Zadanie 17: Powiaz z Lesson13 - dodaj `@WithMockUser` DO endpointu tworzenia zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddAsyncNotificationAndTestWithFuture {
        /* 🧪 Zadanie 18: Powiaz z Lesson16 - ZMIEN `NotificationService` NA `@Async` I dostosuj test. */
        public static void main(String[] args) { }
    }

    static class Exercise19_AddJsonTestForTaskDtoSerialization {
        /* 🧪 Zadanie 19: Powiaz z Lesson07 - dodaj `@JsonTest` DLA ksztaltu odpowiedzi JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullTestPyramidForTasksApiAcrossAllThreeChapters {
        /* 🧪 Zadanie 20: Zaprojektuj PELNA "piramide testow" DLA Tasks API - jednostkowe (`_25`) + integracyjne (`_26`) + Spring (`_27`). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MigrateCapstoneToUseTestcontainersServiceConnection {
        /* 🧪 Zadanie 21: Powiaz z Lesson15 - PRZEPISZ kapszton NA PRAWDZIWY PostgreSQL PRZEZ `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_AddEventPublishingOnTaskCreatedAndTestListener {
        /* 🧪 Zadanie 22: Powiaz z `_20_spring_core/Lesson20` - dodaj publikacje zdarzenia `TaskCreated`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementFullSagaStyleCompensationForFailedNotification {
        /* 🧪 Zadanie 23: Powiaz z `_31_spring_cloud_microservices/Lesson14` - zaimplementuj KOMPENSACJE PRZY awarii powiadomienia. */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildFullCiPipelineSimulationForCapstoneModule {
        /* 🧪 Zadanie 24: Powiaz z `_26_integration_testing/Lesson14` - zbuduj WIELOETAPOWY pipeline DLA CALEGO modulu. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementIdempotentTaskCreationWithIdempotencyKey {
        /* 🧪 Zadanie 25: Powiaz z `_18_rest_api/Lesson15` - dodaj klucz idempotencji. */
        public static void main(String[] args) { }
    }

    static class Exercise26_AddDistributedTracingAcrossControllerServiceRepository {
        /* 🧪 Zadanie 26: Powiaz z `_31_spring_cloud_microservices/Lesson11` - dodaj propagacje trace ID. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementChaosTestingForNotificationServiceFailures {
        /* 🧪 Zadanie 27: Powiaz z `_26_integration_testing/Lesson12` - wstrzyknij LOSOWE awarie powiadomien. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildFullAuditTrailForAllTaskOperations {
        /* 🧪 Zadanie 28: Powiaz z `_19_security_basics/Lesson19` - dodaj PELNY dziennik audytu. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignFullMicroserviceSplitBasedOnCapstoneModule {
        /* 🧪 Zadanie 29: Powiaz z `_17_architecture/Lesson19` - zaprojektuj PODZIAL tego modulu NA mikroserwisy. */
        public static void main(String[] args) { }
    }

    static class Exercise30_WriteRetrospectiveOnEntireChapterAndCapstoneDesign {
        /* 🧪 Zadanie 30: Bez terminala - napisz KRYTYCZNA retrospektywe CALEGO rozdzialu `_27_spring_test`. */
        public static void main(String[] args) { }
    }
}
