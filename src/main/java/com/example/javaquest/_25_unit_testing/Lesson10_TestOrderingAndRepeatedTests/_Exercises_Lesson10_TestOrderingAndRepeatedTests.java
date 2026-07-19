package com.example.javaquest._25_unit_testing.Lesson10_TestOrderingAndRepeatedTests;

public class _Exercises_Lesson10_TestOrderingAndRepeatedTests {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyOrderIsNormallyIrrelevant {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: powiaz z Lesson01 (F.I.R.S.T.) - wyjasnij,
         * DLACZEGO kolejnosc testow NORMALNIE NIE powinna miec
         * znaczenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteOrderedTestSequence {
        /*
         * 🧪 Zadanie 2:
         * Napisz WLASNA sekwencje 3 testow Z `@Order`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_TryRandomOrdererAndObserveFailure {
        /*
         * 🧪 Zadanie 3:
         * Zamien `OrderAnnotation` NA `MethodOrderer.Random` NA
         * `OrderedStepsTest` - zaobserwuj PORAZKE (UJAWNIA UKRYTA
         * zaleznosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteRepeatedTestWithFixedCount {
        /*
         * 🧪 Zadanie 4:
         * Napisz `@RepeatedTest(10)` sprawdzajacy PROSTA operacje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseRepetitionInfoInAssertion {
        /*
         * 🧪 Zadanie 5:
         * Uzyj `RepetitionInfo.getCurrentRepetition()` W tresci
         * asercji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CustomizeRepeatedTestDisplayName {
        /*
         * 🧪 Zadanie 6:
         * Uzyj `@RepeatedTest(name = "...")` Z WLASNYM formatem nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteFlakyTestAndDetectWithRepeated {
        /*
         * 🧪 Zadanie 7:
         * Napisz CELOWO "niestabilny" test (np. losowa wartosc
         * czasami NIE PRZECHODZI) I wykryj TO PRZEZ `@RepeatedTest`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainMethodOrdererOptions {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wypisz WSZYSTKIE wbudowane `MethodOrderer`
         * (OrderAnnotation/MethodName/Random/DisplayName).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareOrderedWithIndependentTests {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: porownaj testy Z `@Order` (Lesson10) Z
         * NIEZALEZNYMI testami (Lesson01-09) - KIEDY kazdy STYL
         * jest UZASADNIONY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_WriteRepeatedTestWithoutRepetitionInfo {
        /*
         * 🧪 Zadanie 10:
         * Napisz `@RepeatedTest` BEZ parametru `RepetitionInfo`
         * (parametr jest OPCJONALNY).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCustomMethodOrderer {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj WLASNY `MethodOrderer` (np. WEDLUG DLUGOSCI
         * nazwy metody).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseOrderForMigrationLikeTests {
        /*
         * 🧪 Zadanie 12:
         * Powiaz z `_10_dao/Lesson25`/`_23_spring_data_jpa/Lesson14` -
         * zaprojektuj `@Order`-owana sekwencje testujaca MIGRACJE (V1
         * -> V2 -> V3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CombineRepeatedTestWithParameterizedLogic {
        /*
         * 🧪 Zadanie 13:
         * Napisz `@RepeatedTest` GENERUJACY ROZNE dane WEJSCIOWE NA
         * KAZDE powtorzenie (NA PODSTAWIE numeru powtorzenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TestThreadSafetyWithRepeatedTest {
        /*
         * 🧪 Zadanie 14:
         * Powiaz z `_05_multithreading` - uzyj `@RepeatedTest` DO
         * wielokrotnego przetestowania KODU WSPOLBIEZNEGO (wykrywanie
         * race conditions).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureRepeatedTestExecutionTimeVariance {
        /*
         * 🧪 Zadanie 15:
         * Zmierz CZAS KAZDEGO powtorzenia (`@RepeatedTest(20)`) I
         * oblicz WARIANCJE - wykryj NIESTABILNA WYDAJNOSC.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DesignOrderedIntegrationLikeScenario {
        /*
         * 🧪 Zadanie 16:
         * Zaprojektuj (jawnie oznaczony komentarzem "TO WYJATEK OD
         * normalnej zasady") scenariusz, GDZIE `@Order` jest
         * UZASADNIONY (np. test SEKWENCJI stanu maszyny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseBeforeEachWithRepeatedTest {
        /*
         * 🧪 Zadanie 17:
         * Polacz `@RepeatedTest` Z `@BeforeEach` - zweryfikuj, ze
         * `@BeforeEach` WYKONUJE SIE PRZED KAZDYM powtorzeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainRandomOrdererSeed {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: wyjasnij, jak `MethodOrderer.Random` UZYWA
         * ziarna (`junit.jupiter.execution.order.random.seed`) DLA
         * POWTARZALNOSCI losowej kolejnosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareRepeatedTestWithLoopInsideTest {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: porownaj `@RepeatedTest(10)` Z petla `for`
         * WEWNATRZ 1 metody `@Test` - CO ZYSKUJESZ W raportowaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignLoadTestSimulationWithRepeatedTest {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj PROSTA symulacje obciazenia (`@RepeatedTest(100)`
         * wywolujacy metode I MIERZACY sredni czas).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementDeterministicRandomOrdererWithSeed {
        /*
         * 🧪 Zadanie 21:
         * Skonfiguruj WLASNE ziarno DLA `MethodOrderer.Random` W
         * `junit-platform.properties` I zweryfikuj POWTARZALNOSC
         * kolejnosci miedzy uruchomieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignTestSuiteDetectingOrderDependency {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj NARZEDZIE (WLASNY listener) URUCHAMIAJACE KAZDA klase
         * testowa 2 RAZY (RAZ Z `Random`, RAZ Z `OrderAnnotation`) I
         * PORONUJACE wyniki - WYKRYWA ukryte zaleznosci AUTOMATYCZNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementFlakyTestQuarantineStrategy {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: zaprojektuj STRATEGIE "kwarantanny" DLA
         * testow WYKRYTYCH jako "flaky" PRZEZ `@RepeatedTest`
         * (oznaczanie, IZOLOWANIE, PRIORYTET naprawy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BenchmarkOrderingOverheadAtScale {
        /*
         * 🧪 Zadanie 24:
         * Zmierz narzut SORTOWANIA WEDLUG `@Order` NA 500 metodach
         * testowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCustomRepetitionExtension {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj WLASNE rozszerzenie (`TestTemplateInvocationContextProvider`)
         * DAJACE WIECEJ kontroli NIZ `@RepeatedTest` (np. ROZNE dane NA
         * KAZDE powtorzenie, Z NAZWA opisujaca dane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignStatisticalFlakinessDetection {
        /*
         * 🧪 Zadanie 26:
         * Uzyj `@RepeatedTest(100)` DO oszacowania PRAWDOPODOBIENSTWA
         * niepowodzenia testu "niestabilnego" (procent porazek NA 100
         * powtorzen).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementOrderedMigrationTestSuiteForFlyway {
        /*
         * 🧪 Zadanie 27:
         * Powiaz z `_23_spring_data_jpa/Lesson14` - zbuduj REALNA,
         * uporzadkowana (`@Order`) sekwencje testow migracji Flyway
         * (V1 tworzy, V2 zmienia, V3 usuwa).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareJUnit5OrderingWithTestNGDependsOn {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: porownaj `@Order` (JUnit 5) Z
         * `@Test(dependsOnMethods=...)` (TestNG) - RÓŻNE FILOZOFIE
         * wyrazania zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignChaosEngineeringStyleRepeatedTest {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj `@RepeatedTest` symulujacy LOSOWE awarie
         * (opoznienia/wyjatki) W CELU sprawdzenia ODPORNOSCI kodu
         * (powiazanie Z `_31_spring_cloud_microservices/Lesson09`
         * circuit breaker, gdy powstanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullOrderedAndRepeatedTestSuiteForStateMachine {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj PELNY zestaw: `@Order`-owane testy PRZEJSC stanu +
         * `@RepeatedTest` sprawdzajacy STABILNOSC KAZDEGO przejscia
         * (min. 5 przejsc, min. 10 powtorzen KAZDE).
         */
        public static void main(String[] args) { }
    }
}
