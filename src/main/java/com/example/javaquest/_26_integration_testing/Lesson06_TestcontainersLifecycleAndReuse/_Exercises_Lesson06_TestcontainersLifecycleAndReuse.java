package com.example.javaquest._26_integration_testing.Lesson06_TestcontainersLifecycleAndReuse;

public class _Exercises_Lesson06_TestcontainersLifecycleAndReuse {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StartContainerOncePerSimulatedClass {
        /*
         * 🧪 Zadanie 1:
         * Uruchom kontener RAZ I uzyj go W 2 "testach" (metodach).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StartFreshContainerPerSimulatedTest {
        /*
         * 🧪 Zadanie 2:
         * Uruchom NOWY kontener DLA KAZDEGO Z 2 "testow" - porownaj
         * CZAS Z Zadaniem 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ConfigureWithReuseFlag {
        /*
         * 🧪 Zadanie 3:
         * Skonfiguruj `withReuse(true)` NA kontenerze (BEZ
         * uruchamiania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainTradeoffBetweenPerTestAndPerClass {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wyjasnij KOMPROMIS miedzy "kontener NA test"
         * A "kontener NA klase".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ImplementTableTruncationBetweenSimulatedTests {
        /*
         * 🧪 Zadanie 5:
         * Dodaj `TRUNCATE TABLE` MIEDZY "testami" WSPOLDZIELACYMI
         * kontener (izolacja przy reuse na klase).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MeasureContainerStartupCostAcrossThreeApproaches {
        /*
         * 🧪 Zadanie 6:
         * Zmierz CALKOWITY czas DLA 5 "testow" - PODEJSCIE 1
         * (na test) vs PODEJSCIE 2 (na klase).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainRoleOfTestcontainersPropertiesFile {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij ROLE pliku
         * `~/.testcontainers.properties`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DocumentRiskOfReuseAcrossUnrelatedTestRuns {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: opisz RYZYKO `withReuse(true)` MIEDZY
         * NIEZWIAZANYMI uruchomieniami testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SimulateJUnit5ContainerAnnotationPattern {
        /*
         * 🧪 Zadanie 9:
         * Napisz (jako komentarz Z kodem) wzorzec `@Testcontainers`+
         * `@Container` DLA klasy testowej JUnit 5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareLifecycleWithSpringBootTestContext {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj cykl zycia kontenera Z cyklem
         * zycia kontekstu Springa (`_20_spring_core/Lesson07`).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementCleanupHelperRunningAfterEachSimulatedTest {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj metode pomocnicza URUCHAMIANA PO KAZDYM
         * "tescie" CZYSZCZACA WSZYSTKIE tabele.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ShareContainerAcrossTwoDifferentRepositoryTests {
        /*
         * 🧪 Zadanie 12:
         * Uzyj JEDNEGO kontenera DO testowania 2 ROZNYCH repozytoriow
         * (Lesson05) NARAZ.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementContainerStartupTimeoutHandling {
        /*
         * 🧪 Zadanie 13:
         * Skonfiguruj `.withStartupTimeout(Duration)` I zbadaj
         * zachowanie PRZY zbyt krotkim limicie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignStrategyForSlowVsFastTestSuites {
        /*
         * 🧪 Zadanie 14:
         * Zaprojektuj (W komentarzu), KIEDY WARTO uzyc reuse (duzy
         * pakiet) A KIEDY NIE (maly, CI z jednorazowym runnerem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementPerClassContainerWithStaticInitializerBlock {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj kontener uruchamiany W statycznym bloku
         * inicjalizujacym (`static { ... }`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TestDataIsolationFailureWhenSharingContainerCarelessly {
        /*
         * 🧪 Zadanie 16:
         * CELOWO POMIN czyszczenie danych MIEDZY 2 "testami" NA
         * WSPOLDZIELONYM kontenerze - zaobserwuj INTERFERENCJE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementUniquePrefixStrategyForTestDataIsolation {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj strategie UNIKALNYCH prefiksow ID (zamiast
         * TRUNCATE) DO izolacji danych NA WSPOLDZIELONYM kontenerze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareResourceUsageOfMultipleContainersVsOneShared {
        /*
         * 🧪 Zadanie 18:
         * Porownaj (koncepcyjnie) ZUZYCIE pamieci 5 OSOBNYCH
         * kontenerow WZGLEDEM 1 WSPOLDZIELONEGO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementContainerFactoryReturningSingletonInstance {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj WLASNA "fabryke" zwracajaca SINGLETON
         * instancje kontenera DLA CALEGO pakietu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentCiConsiderationsForReuseFlag {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: opisz, DLACZEGO `withReuse(true)` jest
         * MNIEJ przydatne NA typowym, EFEMERYCZNYM runnerze CI.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementBaseTestClassWithSharedStaticContainer {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj (jako komentarz Z kodem) WSPOLNA klase bazowa
         * DLA WIELU klas testowych, WSZYSTKIE WSPOLDZIELACE JEDEN
         * statyczny kontener.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementHealthCheckBeforeReusingExistingContainer {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj sprawdzenie "zdrowia" WSPOLDZIELONEGO
         * kontenera PRZED KAZDYM uzyciem (na wypadek, GDYBY padl
         * MIEDZY testami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MeasureFullSuiteTimeWithAndWithoutReuse {
        /*
         * 🧪 Zadanie 23:
         * Zmierz CALKOWITY czas SYMULOWANEGO pakietu 10 testow Z
         * reuse WZGLEDEM BEZ reuse.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementParallelTestExecutionWithSeparateContainersPerThread {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_05_multithreading` - zaimplementuj PULE
         * kontenerow (1 NA watek) DO bezpiecznego rownoleglego
         * uruchamiania testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignContainerLifecycleForMultiModuleProject {
        /*
         * 🧪 Zadanie 25:
         * Zaprojektuj strategie cyklu zycia kontenerow DLA projektu
         * WIELOMODULOWEGO (powiazanie Z
         * `_17_architecture/Lesson17_ModularMonolith`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementGracefulDegradationWhenContainerCrashesMidSuite {
        /*
         * 🧪 Zadanie 26:
         * Zaprojektuj mechanizm WYKRYWAJACY AWARIE kontenera W POLOWIE
         * pakietu testow I ODTWARZAJACY go AUTOMATYCZNIE.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildMetricsCollectorForContainerLifecycleEvents {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj LICZNIK zdarzen cyklu zycia (start/stop/reuse-hit)
         * DLA CALEGO pakietu testow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignHybridStrategyMixingReuseAndFreshContainers {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj HYBRYDOWA strategie - REUSE DLA "szybkich"
         * testow, SWIEZY kontener DLA testow WYMAGAJACYCH pelnej
         * izolacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementAutomaticContainerCleanupOnJvmShutdown {
        /*
         * 🧪 Zadanie 29:
         * Zbadaj mechanizm Ryuk (Testcontainers) I zaimplementuj
         * WLASNY `Runtime.getRuntime().addShutdownHook(...)` jako
         * DODATKOWE zabezpieczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullContainerLifecyclePolicyForOrganization {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj PELNA polityke cyklu zycia kontenerow testowych
         * DLA CALEJ organizacji (lokalnie/CI/reuse/sprzatanie).
         */
        public static void main(String[] args) { }
    }
}
