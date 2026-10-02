package com.example.javaquest.liveCodeVersion._27_spring_test.Lesson15_TestcontainersServiceConnection;

public class _Exercises_Lesson15_TestcontainersServiceConnection {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddServiceConnectionToPostgresContainer {
        /* 🧪 Zadanie 1: Dodaj `@ServiceConnection` DO WLASNEGO `PostgreSQLContainer`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifyRepositoryWorksWithoutManualPropertyConfiguration {
        /* 🧪 Zadanie 2: Zweryfikuj, ze repozytorium DZIALA BEZ ZADNEJ recznej konfiguracji wlasciwosci. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CompareServiceConnectionWithDynamicPropertySource {
        /* 🧪 Zadanie 3: Bez terminala - porownaj `@ServiceConnection` Z `@DynamicPropertySource` (Lesson11). */
        public static void main(String[] args) { }
    }

    static class Exercise04_CheckDockerAvailabilityBeforeRunningTest {
        /* 🧪 Zadanie 4: Sprawdz dostepnosc Dockera PRZED uruchomieniem testu. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainRoleOfContainersAnnotation {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij role `@Testcontainers` (rozszerzenie JUnit 5). */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseStaticContainerFieldForClassLevelSharing {
        /* 🧪 Zadanie 6: Uzyj `static` pola kontenera DLA WSPOLDZIELENIA W CALEJ klasie. */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestMultipleRepositoriesAgainstSameServiceConnectionContainer {
        /* 🧪 Zadanie 7: Przetestuj 2 repozytoria PRZECIW TEMU SAMEMU kontenerowi. */
        public static void main(String[] args) { }
    }

    static class Exercise08_VerifyContainerImageTagIsExplicit {
        /* 🧪 Zadanie 8: Zweryfikuj, ze TAG obrazu (`postgres:16-alpine`) jest JAWNY (NIE `latest`). */
        public static void main(String[] args) { }
    }

    static class Exercise09_HandleDockerUnavailableGracefullyInThisContext {
        /* 🧪 Zadanie 9: Napisz kod OBSLUGUJACY brak Dockera W kontekscie `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListOtherContainerTypesSupportingServiceConnection {
        /* 🧪 Zadanie 10: Bez terminala - wypisz INNE typy kontenerow wspierajace `@ServiceConnection` (Redis/Kafka/RabbitMQ). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CombineServiceConnectionWithFlywayMigrations {
        /* 🧪 Zadanie 11: Powiaz z `_23_spring_data_jpa/Lesson14` - polacz `@ServiceConnection` Z Flyway. */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestServiceConnectionWithNonInstanceMethodContainer {
        /* 🧪 Zadanie 12: Zbadaj NIE-`static` pole kontenera (kontener NA TEST zamiast NA klase). */
        public static void main(String[] args) { }
    }

    static class Exercise13_CombineServiceConnectionWithActiveProfiles {
        /* 🧪 Zadanie 13: Powiaz z Lesson10 - polacz `@ServiceConnection` Z `@ActiveProfiles`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestTransactionalRollbackWithRealPostgresContainer {
        /* 🧪 Zadanie 14: Powiaz z Lesson14 - przetestuj `@Transactional` auto-rollback NA PRAWDZIWYM PostgreSQL. */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureStartupCostOfServiceConnectionApproach {
        /* 🧪 Zadanie 15: Zmierz CZAS startu testu Z `@ServiceConnection` (JESLI Docker dostepny). */
        public static void main(String[] args) { }
    }

    static class Exercise16_CombineServiceConnectionWithMockitoBean {
        /* 🧪 Zadanie 16: Powiaz z Lesson08 - polacz `@ServiceConnection` (baza) Z `@MockitoBean` (INNA zaleznosc). */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestPessimisticLockingWithRealPostgresServiceConnection {
        /* 🧪 Zadanie 17: Powiaz z `_12_hibernate/Lesson26` - przetestuj `SELECT FOR UPDATE` PRZEZ `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementCustomServiceConnectionForUnsupportedContainer {
        /* 🧪 Zadanie 18: Zbadaj, JAK dodac WLASNE wsparcie `@ServiceConnection` DLA NIEWSPIERANEGO kontenera. */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestDialectSpecificSqlWithServiceConnection {
        /* 🧪 Zadanie 19: Powiaz z `_26_integration_testing/Lesson03` - przetestuj funkcje SPECYFICZNA DLA PostgreSQL. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullServiceConnectionTestSuiteForPersistenceLayer {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet testow warstwy persystencji uzywajac `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementSharedContainerAcrossMultipleTestClassesWithReuse {
        /* 🧪 Zadanie 21: Powiaz z `_26_integration_testing/Lesson06` - polacz `@ServiceConnection` Z `withReuse(true)`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestMultiContainerSetupWithServiceConnectionForTwoServices {
        /* 🧪 Zadanie 22: Zbuduj test Z 2 KONTENERAMI (`@ServiceConnection`) NARAZ (np. Postgres+Redis). */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomContainerConfigurationBeforeServiceConnection {
        /* 🧪 Zadanie 23: Skonfiguruj kontener (`.withEnv(...)`) PRZED wykryciem PRZEZ `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignCiConfigurationRunningServiceConnectionTests {
        /* 🧪 Zadanie 24: Powiaz z `_26_integration_testing/Lesson14` - zaprojektuj etap CI DLA testow `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementFallbackToH2WhenDockerUnavailableInServiceConnectionTest {
        /* 🧪 Zadanie 25: Zaprojektuj mechanizm PRZELACZAJACY NA H2, GDY Docker NIEDOSTEPNY. */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestSchemaValidationAgainstRealPostgresViaServiceConnection {
        /* 🧪 Zadanie 26: Powiaz z `_23_spring_data_jpa` (siodma pulapka) - zweryfikuj `ddl-auto=validate` NA PRAWDZIWYM PostgreSQL. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementPerformanceComparisonH2VsServiceConnectionPostgres {
        /* 🧪 Zadanie 27: Powiaz z `_26_integration_testing/Lesson03` - porownaj WYDAJNOSC H2 vs `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignMigrationPlanFromManualTestcontainersToServiceConnection {
        /* 🧪 Zadanie 28: Zaprojektuj PLAN migracji Z recznego Testcontainers (`_26`) DO `@ServiceConnection`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementContractTestBetweenServiceConnectionAndProductionSchema {
        /* 🧪 Zadanie 29: Powiaz z `_26_integration_testing/Lesson13` - zbuduj test kontraktowy schematu. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullTestcontainersIntegrationStandardForOrganization {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard integracji Testcontainers DLA organizacji. */
        public static void main(String[] args) { }
    }
}
