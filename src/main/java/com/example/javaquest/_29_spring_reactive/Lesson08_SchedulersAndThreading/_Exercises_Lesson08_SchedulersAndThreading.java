package com.example.javaquest._29_spring_reactive.Lesson08_SchedulersAndThreading;

public class _Exercises_Lesson08_SchedulersAndThreading {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PrintCurrentThreadNameInsideMonoWithoutScheduler {
        /* 🧪 Zadanie 1: Wypisz nazwe BIEZACEGO watku wewnatrz `Mono` BEZ zadnego Schedulera. */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseSubscribeOnBoundedElasticAndPrintThreadName {
        /* 🧪 Zadanie 2: Uzyj `subscribeOn(Schedulers.boundedElastic())` I WYPISZ nazwe watku. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UsePublishOnParallelAndPrintThreadName {
        /* 🧪 Zadanie 3: Uzyj `publishOn(Schedulers.parallel())` I WYPISZ nazwe watku. */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareSubscribeOnPlacementAtBeginningVsEndOfChain {
        /* 🧪 Zadanie 4: Porownaj umieszczenie `subscribeOn` NA POCZATKU A NA KONCU lancucha - wynik JEST TAKI SAM. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseMultiplePublishOnCallsAndObserveThreadChangesAtEachPoint {
        /* 🧪 Zadanie 5: Uzyj WIELU wywolan `publishOn` I zaobserwuj ZMIANY watku W KAZDYM MIEJSCU. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhyBlockingCallShouldUseBoundedElasticNotParallel {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, DLACZEGO blokujace wywolanie POWINNO uzywac `boundedElastic`, NIE `parallel`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainDifferenceBetweenSubscribeOnAndPublishOn {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij ROZNICE MIEDZY `subscribeOn` A `publishOn`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseSchedulersImmediateForSynchronousExecution {
        /* 🧪 Zadanie 8: Uzyj `Schedulers.immediate()` DLA SYNCHRONICZNEGO wykonania (BEZ zmiany watku). */
        public static void main(String[] args) { }
    }

    static class Exercise09_CreateCustomBoundedElasticSchedulerWithNamedThreads {
        /* 🧪 Zadanie 9: Stworz WLASNY `Schedulers.newBoundedElastic(...)` Z NAZWANYMI watkami. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyReactorEventLoopThreadsShouldNeverBeBlocked {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO watki event-loop Reactora NIGDY NIE POWINNY byc zablokowane. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WrapLegacyBlockingJdbcCallWithSubscribeOnBoundedElastic {
        /* 🧪 Zadanie 11: Opakuj legacy BLOKUJACE wywolanie JDBC (symulowane) `.subscribeOn(boundedElastic())`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ParallelizeComputationallyIntensiveTaskUsingParallelFluxAndRunOn {
        /* 🧪 Zadanie 12: Zrownolegl obliczeniowo INTENSYWNE zadanie uzywajac `parallel()` + `runOn()`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_MeasurePerformanceGainFromParallelSchedulerForCpuBoundWork {
        /* 🧪 Zadanie 13: Zmierz ZYSK wydajnosci Z `Schedulers.parallel()` DLA obliczen CPU-bound. */
        public static void main(String[] args) { }
    }

    static class Exercise14_CombineBoundedElasticForIoAndParallelForCpuInSamePipeline {
        /* 🧪 Zadanie 14: POLACZ `boundedElastic` (I/O) I `parallel` (CPU) W TYM SAMYM pipeline. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCustomThreadFactoryForSchedulerWithDescriptiveNames {
        /* 🧪 Zadanie 15: Zaimplementuj WLASNA `ThreadFactory` DLA Schedulera Z OPISOWYMI nazwami watkow. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareThroughputWithDifferentSchedulerConfigurations {
        /* 🧪 Zadanie 16: Porownaj PRZEPUSTOWOSC DLA ROZNYCH konfiguracji Schedulera (rozne rozmiary pul). */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementBlockingHedgeExampleShowingWhatHappensWhenYouBlockOnEventLoop {
        /* 🧪 Zadanie 17: Zademonstruj (OSTROZNIE, Z komentarzem), CO SIE DZIEJE, GDY blokujesz watek event-loop. */
        public static void main(String[] args) { }
    }

    static class Exercise18_UseSchedulersSingleForSequentialNonBlockingTasks {
        /* 🧪 Zadanie 18: Uzyj `Schedulers.single()` DLA SEKWENCYJNYCH, nieblokujacych zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise19_DisposeCustomSchedulerProperlyAfterUse {
        /* 🧪 Zadanie 19: PRAWIDLOWO zwolnij WLASNY Scheduler (`.dispose()`) PO uzyciu. */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildHybridPipelineMixingBlockingLegacyCallsWithReactiveOperators {
        /* 🧪 Zadanie 20: Zbuduj HYBRYDOWY pipeline LACZACY blokujace legacy wywolania Z reaktywnymi operatorami. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildProductionGradeJdbcIntegrationUsingBoundedElasticWithProperSizing {
        /* 🧪 Zadanie 21: Zbuduj integracje JDBC klasy produkcyjnej Z PRAWIDLOWO dobranym rozmiarem `boundedElastic`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomSchedulerMonitoringActiveThreadsAndQueueSize {
        /* 🧪 Zadanie 22: Powiaz z `_15_jvm_internals` - zaimplementuj MONITOROWANIE aktywnych watkow/kolejki WLASNEGO Schedulera. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildBenchmarkComparingParallelSchedulerWithVirtualThreadsForIoBoundTasks {
        /* 🧪 Zadanie 23: Powiaz z `_28_java_evolution/Lesson19` - zbuduj BENCHMARK `parallel()` A watkow wirtualnych DLA zadan I/O-bound. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementDynamicSchedulerSelectionBasedOnOperationTypeAtRuntime {
        /* 🧪 Zadanie 24: Zaimplementuj DYNAMICZNY wybor Schedulera OPARTY NA typie operacji W runtime. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DiagnoseAndFixBlockingCallAccidentallyRunningOnEventLoopThread {
        /* 🧪 Zadanie 25: ZDIAGNOZUJ I NAPRAW blokujace wywolanie PRZYPADKOWO dzialajace NA watku event-loop. */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildCustomBackpressureAwareSchedulerForRateLimitedExternalApi {
        /* 🧪 Zadanie 26: Zbuduj WLASNY Scheduler SWIADOMY backpressure DLA zewnetrznego API Z LIMITEM zapytan. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementThreadPoolSizingStrategyBasedOnLittlesLawForBoundedElastic {
        /* 🧪 Zadanie 27: Zaimplementuj strategie DOBORU rozmiaru puli OPARTA NA prawie Little'a DLA `boundedElastic`. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildComprehensiveThreadingDiagnosticToolForReactivePipelines {
        /* 🧪 Zadanie 28: Zbuduj KOMPLEKSOWE narzedzie DIAGNOSTYCZNE watkowosci DLA pipeline'ow reaktywnych. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignSchedulerIsolationStrategyForMultiTenantReactiveApplication {
        /* 🧪 Zadanie 29: Zaprojektuj strategie IZOLACJI Schedulerow DLA aplikacji WIELODZIERZAWCZEJ (multi-tenant). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullThreadingArchitectureGuidelinesForLargeReactiveApplication {
        /* 🧪 Zadanie 30: Zaprojektuj PELNE WYTYCZNE architektury watkowosci DLA DUZEJ aplikacji reaktywnej. */
        public static void main(String[] args) { }
    }
}
