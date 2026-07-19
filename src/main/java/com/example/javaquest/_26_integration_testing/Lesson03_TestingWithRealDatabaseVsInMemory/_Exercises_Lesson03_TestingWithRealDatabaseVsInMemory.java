package com.example.javaquest._26_integration_testing.Lesson03_TestingWithRealDatabaseVsInMemory;

public class _Exercises_Lesson03_TestingWithRealDatabaseVsInMemory {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteTestUsingH2MergeStatement {
        /*
         * 🧪 Zadanie 1:
         * Napisz test uzywajacy H2 `MERGE INTO` (upsert).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhatUpsertMeans {
        /*
         * 🧪 Zadanie 2:
         * Bez terminala: wyjasnij, CZYM jest "upsert" (insert-or-update).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListThreeSqlDialectDifferences {
        /*
         * 🧪 Zadanie 3:
         * Wypisz 3 ROZNICE dialektu SQL miedzy H2 A PostgreSQL/MySQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteTestUsingH2SpecificAutoIncrement {
        /*
         * 🧪 Zadanie 4:
         * Napisz test uzywajacy `AUTO_INCREMENT`/`IDENTITY` W H2
         * (powiazanie Z `_09_jdbc`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareStartupTimeH2VsSimulatedRealDb {
        /*
         * 🧪 Zadanie 5:
         * Zmierz CZAS startu H2 in-memory - porownaj (W komentarzu) Z
         * SZACOWANYM czasem startu kontenera Docker.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhenH2IsGoodEnough {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: opisz, KIEDY H2 in-memory WYSTARCZY (BEZ
         * potrzeby Testcontainers).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteTestForDecimalPrecisionInH2 {
        /*
         * 🧪 Zadanie 7:
         * Napisz test SPRAWDZAJACY precyzje `DECIMAL(10,2)` W H2
         * (powiazanie Z `_23_spring_data_jpa` - osma pulapka).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainDbCloseDelayFlagPurpose {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij, PO CO flaga
         * `DB_CLOSE_DELAY=-1` W URL H2 (powiazanie Z `_08_sql/Lesson01`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteTestUsingRealDatabaseSpecificDataType {
        /*
         * 🧪 Zadanie 9:
         * Napisz test uzywajacy TYPU danych, KTORY zachowuje sie
         * INACZEJ W H2 NIZ W PostgreSQL (np. `TIMESTAMP` ze strefa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentPersonalDecisionCriteriaForDbChoice {
        /*
         * 🧪 Zadanie 10:
         * Zaproponuj (W komentarzu) WLASNE kryteria wyboru H2 vs
         * PRAWDZIWEJ bazy DLA nowego projektu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteTestExposingH2VsPostgresLockingDifference {
        /*
         * 🧪 Zadanie 11:
         * Powiaz z `_12_hibernate/Lesson26` - napisz test
         * DEMONSTRUJACY roznice W honorowaniu `lock.timeout`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteMigrationTestRunnableOnBothH2AndPostgresSyntax {
        /*
         * 🧪 Zadanie 12:
         * Napisz migracje SQL (powiazanie Z `_10_dao/Lesson25`)
         * KOMPATYBILNA Z OBOMA dialektami NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDatabaseAgnosticRepositoryTestSuite {
        /*
         * 🧪 Zadanie 13:
         * Zaprojektuj pakiet testow repozytorium URUCHAMIALNY
         * ZAROWNO na H2, JAK I (koncepcyjnie) na PostgreSQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DocumentCaseWhereH2TestPassedButProductionFailed {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj (HIPOTETYCZNY) scenariusz, GDZIE test NA H2
         * PRZESZEDL, ALE na produkcji (PostgreSQL) BY ZAWIODL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteTestUsingH2CompatibilityModeForPostgres {
        /*
         * 🧪 Zadanie 15:
         * Zbadaj I uzyj trybu zgodnosci H2 (`MODE=PostgreSQL` W URL) -
         * sprawdz, CZY zmienia zachowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareTransactionIsolationLevelsAcrossEngines {
        /*
         * 🧪 Zadanie 16:
         * Bez terminala: porownaj domyslne poziomy izolacji transakcji
         * H2 vs PostgreSQL (KONCEPCYJNIE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WriteTestForCaseSensitivityDifferenceInIdentifiers {
        /*
         * 🧪 Zadanie 17:
         * Napisz test SPRAWDZAJACY wielkosc liter W nazwach
         * kolumn/tabel (RÓZNICA miedzy silnikami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DesignHybridStrategyH2ForUnitPostgresForIntegration {
        /*
         * 🧪 Zadanie 18:
         * Zaprojektuj HYBRYDOWA strategie - H2 DLA szybkich testow
         * DAO, PostgreSQL (Testcontainers) DLA "kotwiczacych".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainRiskOfFalseConfidenceFromInMemoryOnlyTesting {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: opisz RYZYKO "falszywej pewnosci" PRZY
         * testowaniu WYLACZNIE na H2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_WriteTestValidatingSchemaCompatibilityAcrossDialects {
        /*
         * 🧪 Zadanie 20:
         * Napisz test WALIDUJACY, ze schemat SQL NIE uzywa zadnej
         * funkcji WYLACZNIE-H2 (PRZEZ analize tekstu migracji).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildDualEngineTestRunnerH2AndSimulatedPostgres {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj mechanizm URUCHAMIAJACY TEN SAM pakiet testow DAO
         * DWUKROTNIE (RÓZNE URL/konfiguracja) I porownujacy wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasurePerformanceDifferenceBetweenEnginesForBulkInsert {
        /*
         * 🧪 Zadanie 22:
         * Zmierz CZAS masowego INSERT-a (1000 wierszy) NA H2 -
         * OSZACUJ (W komentarzu) roznice WZGLEDEM PRAWDZIWEJ bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSchemaValidationAgainstMultipleDialectRules {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj WALIDATOR schematu SQL SPRAWDZAJACY
         * ZGODNOSC Z LISTA "bezpiecznych" (przenosnych) konstrukcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignContractTestBetweenH2AndRealDatabaseBehavior {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj test KONTRAKTOWY (zapowiedz Lesson13) DLA
         * repozytorium - TEN SAM zestaw asercji NA H2 I (symulowanej)
         * PRAWDZIWEJ bazie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDatabaseSwitchingViaEnvironmentVariable {
        /*
         * 🧪 Zadanie 25:
         * Powiaz z `_10_dao/Lesson13` - zaimplementuj przelaczanie
         * H2/PRAWDZIWA baza PRZEZ zmienna srodowiskowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DocumentMigrationPathFromH2OnlyToTestcontainers {
        /*
         * 🧪 Zadanie 26:
         * Napisz (W komentarzu) PLAN migracji projektu Z "TYLKO H2" DO
         * "H2 + Testcontainers DLA testow krytycznych".
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildAutomatedDialectDivergenceDetector {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj mechanizm WYKRYWAJACY (PRZEZ analize
         * tekstowa SQL) uzycie funkcji SPECYFICZNYCH DLA H2 W
         * migracjach projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignCiPipelineRunningBothEnginesConditionally {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj pipeline CI (powiazanie Z `_11_buildtools`)
         * URUCHAMIAJACY H2 PRZY KAZDYM commicie, PostgreSQL TYLKO
         * PRZED mergem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFallbackStrategyWhenRealDatabaseUnavailable {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj mechanizm PRZELACZAJACY sie NA H2, GDY
         * PRAWDZIWA baza (Docker) jest NIEDOSTEPNA (zapowiedz
         * Lesson04, ta sama zasada co brak internetu W `_06_networking`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullDatabaseTestingPolicyForTeam {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNA polityke testowania bazodanowego DLA
         * zespolu (KIEDY H2, KIEDY PRAWDZIWA baza, JAK CZESTO KTORA).
         */
        public static void main(String[] args) { }
    }
}
