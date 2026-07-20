package com.example.javaquest._28_java_evolution.Lesson17_Java19To20VirtualThreadsPreview;

public class _Exercises_Lesson17_Java19To20VirtualThreadsPreview {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateAndStartSingleVirtualThread {
        /* 🧪 Zadanie 1: Stworz I uruchom POJEDYNCZY watek wirtualny (`Thread.ofVirtual()`). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ConfirmThreadIsVirtualUsingIsVirtualMethod {
        /* 🧪 Zadanie 2: Potwierdz, ze watek JEST wirtualny uzywajac `isVirtual()`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateVirtualThreadPerTaskExecutorAndSubmitTasks {
        /* 🧪 Zadanie 3: Stworz `Executors.newVirtualThreadPerTaskExecutor()` I ZLECMU 5 zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareThreadNamePrefixBetweenPlatformAndVirtualThread {
        /* 🧪 Zadanie 4: Porownaj `toString()` watku PLATFORMOWEGO Z watkiem WIRTUALNYM. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhatJep425IntroducedInJava19 {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, CO WPROWADZIL JEP 425 W Javie 19. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainDifferenceBetweenIncubatorAndPreviewFeature {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij ROZNICE MIEDZY "incubator module" A "preview feature". */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListWhatChangedBetweenJava19And20VirtualThreadsPreview {
        /* 🧪 Zadanie 7: Bez terminala - opisz, CO ZMIENILO SIE MIEDZY 1. (Java 19) A 2. (Java 20) runda preview watkow wirtualnych. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyStructuredConcurrencyGroupsRelatedTasks {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij IDEE "structured concurrency" (grupowanie POWIAZANYCH zadan). */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareLoomProjectGoalsWithTraditionalThreadPools {
        /* 🧪 Zadanie 9: Bez terminala - porownaj CELE Project Loom Z TRADYCYJNYMI pulami watkow. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyVirtualThreadsWereStabilizedInJava21Lts {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO watki wirtualne ZOSTALY USTABILIZOWANE WLASNIE W Javie 21 (LTS). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LaunchTenThousandVirtualThreadsAndMeasureTime {
        /* 🧪 Zadanie 11: Uruchom 10 000 watkow wirtualnych (Z krotkim `sleep`) I ZMIERZ czas calkowity. */
        public static void main(String[] args) { }
    }

    static class Exercise12_CompareMemoryFootprintConceptuallyBetweenPlatformAndVirtualThreads {
        /* 🧪 Zadanie 12: Bez terminala - opisz (koncepcyjnie) ROZNICE zuzycia pamieci MIEDZY watkami platformowymi A wirtualnymi. */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseVirtualThreadExecutorForIoBoundTasksSimulatedWithSleep {
        /* 🧪 Zadanie 13: Uzyj executora wirtualnego DLA zadan SYMULUJACYCH I/O (`Thread.sleep`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainWhyVirtualThreadsAreNotFasterForCpuBoundWork {
        /* 🧪 Zadanie 14: Bez terminala - wyjasnij, DLACZEGO watki wirtualne NIE PRZYSPIESZAJA zadan CPU-bound. */
        public static void main(String[] args) { }
    }

    static class Exercise15_DescribeStructuredTaskScopeShutdownOnFailureConceptually {
        /* 🧪 Zadanie 15: Bez terminala (koncepcyjnie) - opisz dzialanie `StructuredTaskScope.ShutdownOnFailure`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_DescribeStructuredTaskScopeShutdownOnSuccessConceptually {
        /* 🧪 Zadanie 16: Bez terminala (koncepcyjnie) - opisz dzialanie `StructuredTaskScope.ShutdownOnSuccess`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareCompletableFutureWithStructuredConcurrencyConceptually {
        /* 🧪 Zadanie 17: Powiaz z `_14_advancedjava/Lesson32` - porownaj `CompletableFuture` Z structured concurrency (koncepcyjnie). */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureVirtualThreadCreationOverheadVsPlatformThreadCreation {
        /* 🧪 Zadanie 18: Zmierz (PRZYBLIZENIOWO) czas UTWORZENIA 1000 watkow platformowych A 1000 wirtualnych. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainCarrierThreadsAndHowVirtualThreadsMapToThem {
        /* 🧪 Zadanie 19: Bez terminala - wyjasnij POJECIE "carrier thread" I JAK watki wirtualne SIE NA NIE MAPUJA. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyEnablePreviewFlagWasRequiredForJava19And20 {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, DLACZEGO flaga `--enable-preview` byla WYMAGANA W Javie 19-20. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildConcurrentHttpFetcherUsingVirtualThreadPerTaskExecutor {
        /* 🧪 Zadanie 21: Zbuduj ROWNOLEGLY "fetcher" HTTP (powiazanie Z `Lesson10`) uzywajac executora wirtualnego. */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareThroughputOfFixedThreadPoolVsVirtualThreadExecutorUnderHighConcurrency {
        /* 🧪 Zadanie 22: Porownaj PRZEPUSTOWOSC `FixedThreadPool` Z executorem wirtualnym PRZY WYSOKIEJ wspolbieznosci. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementConceptualStructuredConcurrencyUsingCompletableFutureAsWorkaround {
        /* 🧪 Zadanie 23: Zaimplementuj PODOBNY efekt DO structured concurrency (anulowanie 'braci' PRZY bledzie) uzywajac WYLACZNIE `CompletableFuture` (ZAMIENNIK, bo `StructuredTaskScope` jest preview). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignMigrationPlanFromThreadPoolToVirtualThreadsForExistingService {
        /* 🧪 Zadanie 24: Zaprojektuj PLAN migracji ISTNIEJACEGO serwisu Z puli watkow NA watki wirtualne. */
        public static void main(String[] args) { }
    }

    static class Exercise25_AnalyzePinningProblemWhenVirtualThreadUsesSynchronizedBlock {
        /* 🧪 Zadanie 25: Powiaz z `_05_multithreading` - przeanalizuj PROBLEM "pinning" PRZY uzyciu `synchronized` W watku wirtualnym. */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildLoadTestComparingResponseTimeUnderThousandsOfConcurrentRequests {
        /* 🧪 Zadanie 26: Zbuduj TEST OBCIAZENIOWY porownujacy CZAS odpowiedzi PRZY TYSIACACH rownoleglych "zadan". */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignArchitectureDecisionRecordForAdoptingVirtualThreadsInProduction {
        /* 🧪 Zadanie 27: Powiaz z `_17_architecture/Lesson02` - zaprojektuj ADR DLA adopcji watkow wirtualnych NA produkcji. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ExplainWhyStructuredConcurrencyTookLongerToStabilizeThanVirtualThreads {
        /* 🧪 Zadanie 28: Bez terminala - wyjasnij, DLACZEGO structured concurrency POTRZEBOWALA WIECEJ czasu NA stabilizacje NIZ watki wirtualne. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildHybridExecutorStrategyChoosingPlatformOrVirtualBasedOnWorkloadType {
        /* 🧪 Zadanie 29: Zaprojektuj HYBRYDOWA strategie wyboru executora (platformowy DLA CPU-bound, wirtualny DLA I/O-bound). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullConcurrencyModernizationRoadmapFromThreadPoolsToStructuredConcurrency {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA mape drogowa MODERNIZACJI wspolbieznosci (Z tradycyjnych puli watkow, PRZEZ watki wirtualne, DO structured concurrency, GDY sie ustabilizuje). */
        public static void main(String[] args) { }
    }
}
