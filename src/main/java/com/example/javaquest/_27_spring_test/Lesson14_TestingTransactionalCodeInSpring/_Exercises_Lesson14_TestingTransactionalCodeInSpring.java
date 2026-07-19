package com.example.javaquest._27_spring_test.Lesson14_TestingTransactionalCodeInSpring;

public class _Exercises_Lesson14_TestingTransactionalCodeInSpring {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddTransactionalToSpringBootTestClass {
        /* 🧪 Zadanie 1: Dodaj `@Transactional` DO WLASNEJ klasy `@SpringBootTest`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_VerifyRollbackBetweenTwoTestMethods {
        /* 🧪 Zadanie 2: Zweryfikuj auto-rollback MIEDZY 2 metodami testowymi. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseTestTransactionFlagForCommit {
        /* 🧪 Zadanie 3: Uzyj `TestTransaction.flagForCommit()` + `end()`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseTestTransactionStartAfterEnd {
        /* 🧪 Zadanie 4: Uzyj `TestTransaction.start()` PO `end()` I zweryfikuj widoczne dane. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainDefaultRollbackVsProductionCommitBehavior {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO `@Transactional` NA TESCIE domyslnie ROLLBACKUJE, A NA kodzie produkcyjnym COMMITUJE. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CleanUpAfterProgrammaticCommit {
        /* 🧪 Zadanie 6: PO `flagForCommit()`, POSPRZATAJ dane I TEZ zacommituj sprzatanie. */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseRollbackFalseAnnotationOnSingleMethod {
        /* 🧪 Zadanie 7: Uzyj `@Rollback(false)` NA POJEDYNCZEJ metodzie testowej. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareTransactionalTestWithDataJpaTestAutoRollback {
        /* 🧪 Zadanie 8: Bez terminala - porownaj `@Transactional` NA tescie Z auto-rollbackiem `@DataJpaTest` (Lesson06). */
        public static void main(String[] args) { }
    }

    static class Exercise09_VerifyTestTransactionIsActiveMethod {
        /* 🧪 Zadanie 9: Zweryfikuj `TestTransaction.isActive()` W ROZNYCH momentach testu. */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentWhenManualTestTransactionControlIsNeeded {
        /* 🧪 Zadanie 10: Bez terminala - opisz, KIEDY potrzebna jest PROGRAMOWA kontrola `TestTransaction`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TestDatabaseTriggerRequiringActualCommit {
        /* 🧪 Zadanie 11: Zaprojektuj test WYMAGAJACY FAKTYCZNEGO commitu (np. TRIGGER SQL). */
        public static void main(String[] args) { }
    }

    static class Exercise12_TestTransactionalServiceMethodWithRuntimeExceptionRollback {
        /* 🧪 Zadanie 12: Powiaz z `_23_spring_data_jpa/Lesson08` - przetestuj rollback PRZY `RuntimeException`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestTransactionalServiceMethodWithCheckedExceptionNoRollback {
        /* 🧪 Zadanie 13: Przetestuj BRAK rollbacku PRZY CHECKED wyjatku (BEZ `rollbackFor`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestSelfInvocationPitfallWithTransactional {
        /* 🧪 Zadanie 14: Powiaz z `_20_spring_core/Lesson22` - zademonstruj pulapke self-invocation NA `@Transactional`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CombineTransactionalTestWithMockitoBean {
        /* 🧪 Zadanie 15: Powiaz z Lesson08 - polacz `@Transactional` test Z `@MockitoBean`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestNestedTransactionPropagation {
        /* 🧪 Zadanie 16: Powiaz z `_23_spring_data_jpa/Lesson08` - przetestuj propagacje `REQUIRES_NEW`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_VerifyMultipleSavesInSingleTransactionAllRollBackTogether {
        /* 🧪 Zadanie 17: Zapisz 5 encji W JEDNEJ metodzie testowej - zweryfikuj, ze WSZYSTKIE SA COFANE RAZEM. */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestBeforeTransactionAndAfterTransactionAnnotations {
        /* 🧪 Zadanie 18: Zbadaj `@BeforeTransaction`/`@AfterTransaction` (URUCHAMIANE POZA transakcja testu). */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestOptimisticLockExceptionWithinTransactionalTest {
        /* 🧪 Zadanie 19: Powiaz z `_12_hibernate/Lesson25` - przetestuj `OptimisticLockException` W transakcyjnym tescie. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullTransactionalTestSuiteForServiceLayer {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet testow transakcyjnych DLA warstwy serwisowej. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomTransactionManagerForTest {
        /* 🧪 Zadanie 21: Zbadaj WLASNY `PlatformTransactionManager` W kontekscie testowym. */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestDistributedTransactionSimulationAcrossTwoDataSources {
        /* 🧪 Zadanie 22: Zaprojektuj (KONCEPCYJNIE) test transakcji ROZPROSZONEJ MIEDZY 2 zrodlami danych. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCustomTransactionalTestListener {
        /* 🧪 Zadanie 23: Zaimplementuj WLASNY `TestExecutionListener` reagujacy NA cykl zycia transakcji testowej. */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestSagaCompensationWithinTransactionalBoundaries {
        /* 🧪 Zadanie 24: Powiaz z `_31_spring_cloud_microservices/Lesson14` - przetestuj KOMPENSACJE Saga W granicach transakcji. */
        public static void main(String[] args) { }
    }

    static class Exercise25_MeasurePerformanceImpactOfTransactionalTestWrapping {
        /* 🧪 Zadanie 25: Zmierz WPLYW `@Transactional` (opakowanie testu) NA CZAS wykonania pakietu. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementAuditTrailVerificationRequiringRealCommit {
        /* 🧪 Zadanie 26: Powiaz z `_19_security_basics/Lesson19` - przetestuj dziennik audytu WYMAGAJACY PRAWDZIWEGO commitu. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignHybridTestSuiteMixingRollbackAndCommitTests {
        /* 🧪 Zadanie 27: Zaprojektuj pakiet MIESZAJACY testy Z rollbackiem I Z jawnym commitem. */
        public static void main(String[] args) { }
    }

    static class Exercise28_TestConcurrentTransactionIsolationLevels {
        /* 🧪 Zadanie 28: Powiaz z `_09_jdbc` - przetestuj RÓZNE poziomy izolacji transakcji. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCleanupVerificationToolDetectingLeakedCommits {
        /* 🧪 Zadanie 29: Zbuduj narzedzie WYKRYWAJACE "wyciekle" (NIE posprzatane) zacommitowane dane PO pakiecie testow. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullTransactionalTestingStandardForOrganization {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard testowania kodu transakcyjnego DLA organizacji. */
        public static void main(String[] args) { }
    }
}
