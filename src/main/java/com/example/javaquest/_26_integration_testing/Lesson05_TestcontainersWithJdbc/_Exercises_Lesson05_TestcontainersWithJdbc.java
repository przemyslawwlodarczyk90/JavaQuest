package com.example.javaquest._26_integration_testing.Lesson05_TestcontainersWithJdbc;

public class _Exercises_Lesson05_TestcontainersWithJdbc {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddDeleteMethodToProductRepository {
        /*
         * 🧪 Zadanie 1:
         * Dodaj metode `delete(int id)` DO `ProductRepository` I
         * NAPISZ test integracyjny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddUpdateMethodToProductRepository {
        /*
         * 🧪 Zadanie 2:
         * Dodaj metode `update(Product)` I NAPISZ test integracyjny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TestFindByIdOnEmptyTable {
        /*
         * 🧪 Zadanie 3:
         * Napisz test `findById` NA PUSTEJ tabeli (przypadek
         * brzegowy, powiazanie Z `_25_unit_testing/Lesson17`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestSaveDuplicateIdThrowsSqlException {
        /*
         * 🧪 Zadanie 4:
         * Napisz test `save` Z ZDUPLIKOWANYM ID (naruszenie PRIMARY
         * KEY) - zweryfikuj wyjatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddCountMethodReturningTotalProducts {
        /*
         * 🧪 Zadanie 5:
         * Dodaj metode `count()` (SELECT COUNT(*)) I przetestuj JA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TestFindAllCheaperThanWithNoMatches {
        /*
         * 🧪 Zadanie 6:
         * Napisz test `findAllCheaperThan` DLA ceny, KTOREJ ZADEN
         * produkt NIE SPELNIA.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyRepositoryDoesNotKnowAboutContainer {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, DLACZEGO `ProductRepository` NIE
         * "WIE", ze dziala PRZECIW kontenerowi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddCategoryColumnAndTestFiltering {
        /*
         * 🧪 Zadanie 8:
         * Dodaj kolumne `category` I NAPISZ metode/test FILTRUJACA
         * PO NIEJ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TestTransactionCommitAcrossMultipleSaves {
        /*
         * 🧪 Zadanie 9:
         * Napisz test SPRAWDZAJACY, ze WIELE `save(...)` W JEDNEJ
         * transakcji jest widoczne PO commit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareRepositoryTestAgainstH2FromChapter25 {
        /*
         * 🧪 Zadanie 10:
         * Powiaz z `_25_unit_testing` - porownaj TEN test (kontener)
         * Z ANALOGICZNYM testem NA H2 (Lesson03).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TestTransactionRollbackOnConstraintViolation {
        /*
         * 🧪 Zadanie 11:
         * Napisz test SPRAWDZAJACY ROLLBACK CALEJ transakcji PRZY
         * naruszeniu ograniczenia W SRODKU.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementPaginationInRepositoryAndTestIt {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_18_rest_api/Lesson10` - dodaj stronicowanie
         * (LIMIT/OFFSET) DO repozytorium I przetestuj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestConcurrentInsertsFromTwoConnections {
        /*
         * 🧪 Zadanie 13:
         * Powiaz z `_05_multithreading` - napisz test Z DWOMA
         * ROWNOLEGLYMI polaczeniami wstawiajacymi dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementBatchInsertAndMeasurePerformance {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj `saveAll(List<Product>)` PRZEZ
         * `addBatch()`/`executeBatch()` I ZMIERZ wydajnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestPostgresSpecificReturningClause {
        /*
         * 🧪 Zadanie 15:
         * Uzyj klauzuli `RETURNING` (PostgreSQL) PRZY INSERT I
         * przetestuj (powiazanie Z Lesson03).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRepositoryUsingTryWithResourcesConsistently {
        /*
         * 🧪 Zadanie 16:
         * PRZEJRZYJ `ProductRepository` POD KATEM konsekwentnego
         * try-with-resources - ZNAJDZ (jesli sa) BRAKI.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteTestUsingCustomPostgresInitScript {
        /*
         * 🧪 Zadanie 17:
         * Uzyj `.withInitScript(...)` DO zaladowania schematu PRZY
         * starcie kontenera (zamiast recznego `createSchema()`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestNullableColumnBehavior {
        /*
         * 🧪 Zadanie 18:
         * Dodaj OPCJONALNA kolumne (`NULL`-owalna) I przetestuj
         * zachowanie `getXxx()` NA `null`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareQueryPerformanceWithAndWithoutIndex {
        /*
         * 🧪 Zadanie 19:
         * Porownaj CZAS zapytania PRZED I PO dodaniu indeksu (`CREATE
         * INDEX`) NA PRAWDZIWYM PostgreSQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementUnitOfWorkPatternAndTestWithContainer {
        /*
         * 🧪 Zadanie 20:
         * Powiaz z `_10_dao/Lesson19_UnitOfWork` - zaimplementuj I
         * PRZETESTUJ wzorzec PRZECIW PRAWDZIWEMU PostgreSQL.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementOptimisticLockingAndTestWithContainer {
        /*
         * 🧪 Zadanie 21:
         * Powiaz z `_12_hibernate/Lesson25` - zaimplementuj wersje
         * OPTYMISTYCZNEGO blokowania (kolumna `version`) I przetestuj
         * KONFLIKT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestPessimisticLockingWithRealPostgresTimeout {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_12_hibernate/Lesson26` - przetestuj `SELECT FOR
         * UPDATE` NA PRAWDZIWYM PostgreSQL (KTORY, W ODROZNIENIU OD
         * H2, PELNI honoruje timeout).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSchemaVersioningWithFlywayAgainstContainer {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_10_dao/Lesson25`/`_23_spring_data_jpa/Lesson14` -
         * URUCHOM PELNY zestaw migracji Flyway PRZECIW kontenerowi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BuildGenericRepositoryTestSuiteParametrizedByDataSource {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_25_unit_testing/Lesson08` - zbuduj
         * `@ParameterizedTest` URUCHAMIANY NA WIELU `DataSource`
         * (H2 I kontener) Z TYM SAMYM zestawem asercji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementConnectionPoolingWithHikariAgainstContainer {
        /*
         * 🧪 Zadanie 25:
         * Skonfiguruj HikariCP (juz obecny W projekcie) DO laczenia
         * sie Z kontenerem I przetestuj PULA polaczen.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TestDeadlockScenarioAgainstRealPostgres {
        /*
         * 🧪 Zadanie 26:
         * Powiaz z `_05_multithreading/Lesson25` - odtworz REALNY
         * deadlock NA PRAWDZIWYM PostgreSQL I zweryfikuj wykrycie
         * PRZEZ silnik bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSoftDeleteAndTestQueryFiltering {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj "soft delete" (kolumna `deleted_at`) I
         * NAPISZ testy FILTRUJACE usuniete rekordy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildDataSeedingUtilityForLargeDatasetsInContainer {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj narzedzie GENERUJACE 10000 rekordow W kontenerze DO
         * testow WYDAJNOSCIOWYCH (powiazanie Z Lesson10).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementReadReplicaSimulationWithTwoContainers {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_31_spring_cloud_microservices` - zasymuluj
         * primary/replica DWOMA kontenerami I przetestuj OPOZNIONA
         * spojnosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullRepositoryTestSuiteMirroringProductionSchema {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNY pakiet testow repozytorium ODZWIERCIEDLAJACY
         * REALNY schemat produkcyjny (powiazanie Z
         * `_17_architecture/Lesson20`).
         */
        public static void main(String[] args) { }
    }
}
