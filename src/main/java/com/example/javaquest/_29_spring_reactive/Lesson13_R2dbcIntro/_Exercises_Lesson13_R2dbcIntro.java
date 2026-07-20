package com.example.javaquest._29_spring_reactive.Lesson13_R2dbcIntro;

public class _Exercises_Lesson13_R2dbcIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateConnectionFactoryForH2InMemoryDatabase {
        /* 🧪 Zadanie 1: Stworz `ConnectionFactory` DLA H2 in-memory uzywajac `ConnectionFactories.get(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateTableReactivelyUsingCreateStatement {
        /* 🧪 Zadanie 2: Stworz TABELE reaktywnie uzywajac `connection.createStatement(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_InsertSingleRowReactivelyAndVerify {
        /* 🧪 Zadanie 3: Wstaw 1 wiersz reaktywnie I zweryfikuj wynik. */
        public static void main(String[] args) { }
    }

    static class Exercise04_QueryAllRowsAndMapToRecordUsingResultMap {
        /* 🧪 Zadanie 4: Wykonaj zapytanie NA WSZYSTKICH wierszach I zmapuj JE NA rekord uzywajac `result.map(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseMonoUsingWhenToProperlyCloseConnection {
        /* 🧪 Zadanie 5: Uzyj `Mono.usingWhen(...)` DO PRAWIDLOWEGO zamkniecia polaczenia. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhatR2dbcSpecificationDefines {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, CO OKRESLA specyfikacja R2DBC. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareBlockingJdbcResultSetWithReactiveResult {
        /* 🧪 Zadanie 7: Powiaz z `_09_jdbc` - porownaj BLOKUJACY `ResultSet` Z REAKTYWNYM `Result`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseBindParametersInParameterizedR2dbcQuery {
        /* 🧪 Zadanie 8: Uzyj `.bind(...)` W parametryzowanym zapytaniu R2DBC (ochrona PRZED SQL injection). */
        public static void main(String[] args) { }
    }

    static class Exercise09_QuerySingleRowByIdAndHandleNotFoundCase {
        /* 🧪 Zadanie 9: Wykonaj zapytanie O 1 wiersz PO ID I obsluz przypadek BRAKU wyniku. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyR2dbcIsNotSimplyFasterJdbc {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO R2DBC NIE JEST PO PROSTU "szybszym JDBC". */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementUpdateStatementReactivelyAndVerifyAffectedRows {
        /* 🧪 Zadanie 11: Zaimplementuj UPDATE reaktywnie I zweryfikuj LICZBE zmienionych wierszy. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementDeleteStatementReactively {
        /* 🧪 Zadanie 12: Zaimplementuj DELETE reaktywnie. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ChainMultipleInsertsUsingThenAndVerifyAllSucceeded {
        /* 🧪 Zadanie 13: Polacz WIELE wstawien uzywajac `.then()` I zweryfikuj, ze WSZYSTKIE sie POWIODLY. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementTransactionUsingConnectionBeginTransactionAndCommit {
        /* 🧪 Zadanie 14: Zaimplementuj TRANSAKCJE uzywajac `connection.beginTransaction()`+`commitTransaction()`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementRollbackOnErrorWithinR2dbcTransaction {
        /* 🧪 Zadanie 15: Zaimplementuj ROLLBACK PRZY bledzie wewnatrz transakcji R2DBC. */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildJoinQueryAcrossTwoTablesReturningFluxOfDto {
        /* 🧪 Zadanie 16: Zbuduj zapytanie JOIN MIEDZY 2 tabelami ZWRACAJACE `Flux<Dto>`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementConnectionPoolingUsingR2dbcPool {
        /* 🧪 Zadanie 17: Zaimplementuj PULOWANIE polaczen uzywajac `r2dbc-pool` (koncepcyjnie/opisowo). */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareResultSetSizeHandlingBetweenJdbcAndR2dbc {
        /* 🧪 Zadanie 18: Porownaj obsluge DUZEGO wyniku zapytania MIEDZY JDBC (cala lista naraz) A R2DBC (streamowanie). */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementBatchInsertReactivelyForMultipleRows {
        /* 🧪 Zadanie 19: Zaimplementuj WSADOWE wstawienie WIELU wierszy reaktywnie. */
        public static void main(String[] args) { }
    }

    static class Exercise20_HandleR2dbcExceptionUsingOnErrorResume {
        /* 🧪 Zadanie 20: Obsluz wyjatek R2DBC (np. naruszenie unikalnosci) uzywajac `onErrorResume`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullReactiveRepositoryPatternWrappingR2dbcCallsInServiceMethods {
        /* 🧪 Zadanie 21: Zbuduj PELNY wzorzec repozytorium OPAKOWUJACY wywolania R2DBC W metodach serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementReactiveOptimisticLockingUsingVersionColumn {
        /* 🧪 Zadanie 22: Powiaz z `_23_spring_data_jpa/Lesson08` - zaimplementuj REAKTYWNE blokowanie optymistyczne (kolumna wersji). */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildStreamingReportGeneratorProcessingMillionsOfRowsWithoutLoadingAllIntoMemory {
        /* 🧪 Zadanie 23: Zbuduj STREAMINGOWY generator raportu przetwarzajacy MILIONY wierszy BEZ wczytywania WSZYSTKICH DO pamieci. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementReactiveUnitOfWorkPatternForMultiTableTransaction {
        /* 🧪 Zadanie 24: Powiaz z `_10_dao/Lesson19` - zaimplementuj REAKTYWNY wzorzec Unit of Work DLA transakcji NA WIELU tabelach. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildFullCrudServiceUsingSpringDataR2dbcRepositoryInterface {
        /* 🧪 Zadanie 25: Zbuduj PELNY serwis CRUD uzywajac interfejsu `R2dbcRepository` (Spring Data R2DBC). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementReactiveCachingLayerInFrontOfR2dbcQueries {
        /* 🧪 Zadanie 26: Zaimplementuj REAKTYWNA warstwe cache PRZED zapytaniami R2DBC. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildMigrationStrategyFromBlockingJdbcRepositoryToR2dbc {
        /* 🧪 Zadanie 27: Zaprojektuj STRATEGIE migracji Z blokujacego repozytorium JDBC (`_10_dao`) NA R2DBC. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementConnectionPoolTuningForHighConcurrencyR2dbcWorkload {
        /* 🧪 Zadanie 28: Zaimplementuj TUNING puli polaczen DLA obciazenia R2DBC O WYSOKIEJ wspolbieznosci. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveIntegrationTestSuiteForR2dbcRepositoryUsingTestcontainers {
        /* 🧪 Zadanie 29: Powiaz z `_26_integration_testing` - zbuduj PAKIET testow integracyjnych DLA repozytorium R2DBC uzywajac Testcontainers. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullReactiveDataAccessArchitectureForHighThroughputMicroservice {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA architekture dostepu DO danych DLA mikroserwisu WYSOKIEJ przepustowosci (R2DBC end-to-end). */
        public static void main(String[] args) { }
    }
}
