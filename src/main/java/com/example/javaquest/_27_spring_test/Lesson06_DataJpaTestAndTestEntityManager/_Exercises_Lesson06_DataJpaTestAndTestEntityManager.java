package com.example.javaquest._27_spring_test.Lesson06_DataJpaTestAndTestEntityManager;

public class _Exercises_Lesson06_DataJpaTestAndTestEntityManager {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteDataJpaTestForSimpleEntity {
        /* 🧪 Zadanie 1: Napisz `@DataJpaTest` DLA WLASNEJ, prostej encji. */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseTestEntityManagerToPersistData {
        /* 🧪 Zadanie 2: Uzyj `TestEntityManager.persistAndFlush(...)` DO przygotowania danych. */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddCustomQueryMethodAndTestIt {
        /* 🧪 Zadanie 3: Dodaj WLASNA metode zapytania (`findByXxx`) I przetestuj JA. */
        public static void main(String[] args) { }
    }

    static class Exercise04_VerifyAutoRollbackBetweenTwoTestMethods {
        /* 🧪 Zadanie 4: Zweryfikuj auto-rollback MIEDZY 2 metodami testowymi. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyDataJpaTestReplacesConfiguredDatabase {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO `@DataJpaTest` PODMIENIA baze NA embedded. */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseTestEntityManagerFindMethod {
        /* 🧪 Zadanie 6: Uzyj `entityManager.find(...)` DO odczytu ENCJI PO ID. */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestOneToManyRelationshipWithDataJpaTest {
        /* 🧪 Zadanie 7: Powiaz z `_23_spring_data_jpa/Lesson07` - przetestuj relacje OneToMany. */
        public static void main(String[] args) { }
    }

    static class Exercise08_VerifyRepositoryCountAndDeleteMethods {
        /* 🧪 Zadanie 8: Zweryfikuj `count()`/`deleteById(...)` repozytorium. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseTestEntityManagerClearToDetachEntities {
        /* 🧪 Zadanie 9: Uzyj `entityManager.clear()` DO odlaczenia encji I zweryfikuj SWIEZE zapytanie. */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareDataJpaTestSpeedWithFullSpringBootTest {
        /* 🧪 Zadanie 10: Zmierz CZAS `@DataJpaTest` WZGLEDEM `@SpringBootTest` (Lesson02) DLA TEGO SAMEGO repozytorium. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TestPaginationWithDataJpaTest {
        /* 🧪 Zadanie 11: Powiaz z `_23_spring_data_jpa/Lesson06` - przetestuj stronicowanie `Pageable`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestCustomJpqlQueryAnnotation {
        /* 🧪 Zadanie 12: Powiaz z `_23_spring_data_jpa/Lesson05` - przetestuj `@Query` (JPQL). */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestNPlusOneProblemWithQueryCounting {
        /* 🧪 Zadanie 13: Powiaz z `_23_spring_data_jpa/Lesson09` - przetestuj problem N+1 W kontekscie `@DataJpaTest`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_DisableAutoConfigureTestDatabaseAndUseRealDataSource {
        /* 🧪 Zadanie 14: Uzyj `@AutoConfigureTestDatabase(replace = NONE)` I WLASNEGO `DataSource`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestOptimisticLockingWithDataJpaTest {
        /* 🧪 Zadanie 15: Powiaz z `_23_spring_data_jpa`/`_12_hibernate/Lesson25` - przetestuj `@Version`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestEntityValidationConstraints {
        /* 🧪 Zadanie 16: Powiaz z `_12_hibernate/Lesson28` - przetestuj naruszenie ograniczen Bean Validation. */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestCascadeDeleteBehavior {
        /* 🧪 Zadanie 17: Powiaz z `_12_hibernate/Lesson14` - przetestuj `CascadeType.REMOVE`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_UseTestEntityManagerGetIdForNewEntity {
        /* 🧪 Zadanie 18: Uzyj `entityManager.getId(encja)` PO zapisie NOWEJ encji. */
        public static void main(String[] args) { }
    }

    static class Exercise19_CombineDataJpaTestWithFlywayMigrations {
        /* 🧪 Zadanie 19: Powiaz z `_23_spring_data_jpa/Lesson14` - polacz `@DataJpaTest` Z Flyway. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullDataJpaTestSuiteForRepositoryLayer {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet `@DataJpaTest` DLA repozytorium (CRUD+zapytania wlasne). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TestSecondLevelCacheBehaviorInDataJpaTest {
        /* 🧪 Zadanie 21: Powiaz z `_12_hibernate/Lesson24` - zbadaj zachowanie cache'a L2 W `@DataJpaTest`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomTestDataBuilderForJpaEntities {
        /* 🧪 Zadanie 22: Powiaz z `_26_integration_testing/Lesson10` - zbuduj Test Data Builder DLA encji JPA. */
        public static void main(String[] args) { }
    }

    static class Exercise23_TestAuditingFieldsPopulatedCorrectly {
        /* 🧪 Zadanie 23: Powiaz z `_23_spring_data_jpa/Lesson13` - przetestuj pola audytu (`@CreatedDate` itd.). */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestSpecificationBasedDynamicQuery {
        /* 🧪 Zadanie 24: Powiaz z `_23_spring_data_jpa/Lesson12` - przetestuj `Specification`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasureQueryCountUsingStatementInspector {
        /* 🧪 Zadanie 25: Powiaz z `_23_spring_data_jpa/Lesson09` (trzecia pulapka) - zbuduj `StatementInspector` W tescie. */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestComplexProjectionWithDataJpaTest {
        /* 🧪 Zadanie 26: Powiaz z `_23_spring_data_jpa/Lesson11` - przetestuj OTWARTA projekcje. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCustomAutoConfigureTestDatabaseReplacement {
        /* 🧪 Zadanie 27: Zbadaj `@AutoConfigureTestDatabase(connection = ...)` DLA WLASNEGO silnika embedded. */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestTransactionalBehaviorAcrossMultipleRepositoryOperations {
        /* 🧪 Zadanie 28: Powiaz z `_23_spring_data_jpa/Lesson08` - przetestuj WIELE operacji W JEDNEJ transakcji. */
        public static void main(String[] args) { }
    }

    static class Exercise29_MigrateDataJpaTestToUseTestcontainers {
        /* 🧪 Zadanie 29: Powiaz z Lesson15 - zaprojektuj MIGRACJE `@DataJpaTest` NA PRAWDZIWY PostgreSQL. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullPersistenceLayerTestingStandardForTeam {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard testowania warstwy persystencji DLA zespolu. */
        public static void main(String[] args) { }
    }
}
