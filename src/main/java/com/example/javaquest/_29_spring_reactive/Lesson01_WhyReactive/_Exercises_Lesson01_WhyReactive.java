package com.example.javaquest._29_spring_reactive.Lesson01_WhyReactive;

public class _Exercises_Lesson01_WhyReactive {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MeasureTimeForTenBlockingTasksOnTwoThreads {
        /* 🧪 Zadanie 1: Zmierz czas 10 BLOKUJACYCH zadan (50ms kazde) NA puli 2 watkow. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CompareTimeWithFourThreadsVsTwoThreads {
        /* 🧪 Zadanie 2: Porownaj czas Z Zadania 1 Z pula 4 watkow. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhatC10kProblemMeans {
        /* 🧪 Zadanie 3: Bez terminala - wyjasnij, CZYM jest "C10K problem". */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainDifferenceBetweenBlockingAndNonBlockingIo {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij ROZNICE MIEDZY blocking A non-blocking I/O. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhatBackpressureMeansWithSimpleAnalogy {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij backpressure UZYWAJAC PROSTEJ analogii (np. tama/rura). */
        public static void main(String[] args) { }
    }

    static class Exercise06_CalculateMemoryNeededForTenThousandPlatformThreads {
        /* 🧪 Zadanie 6: Bez terminala - oblicz PRZYBLIZONA pamiec potrzebna DLA 10 000 watkow platformowych (~1MB/watek). */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyOutOfMemoryErrorCanHappenWithoutBackpressure {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, JAK BRAK backpressure MOZE doprowadzic DO `OutOfMemoryError`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareVirtualThreadsApproachWithReactiveApproachConceptually {
        /* 🧪 Zadanie 8: Powiaz z `_28_java_evolution/Lesson19` - porownaj (koncepcyjnie) podejscie watkow wirtualnych Z reaktywnym. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListTradeoffsOfReactiveProgrammingStyle {
        /* 🧪 Zadanie 9: Bez terminala - wypisz KOMPROMISY (trade-offs) stylu reaktywnego (co ZYSKUJESZ, co TRACISZ). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainEventLoopConceptWithFewThreadsHandlingManyTasks {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij koncepcje "event loop" (MALO watkow, DUZO zadan). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SimulateSlowConsumerWithFastProducerUsingBlockingQueue {
        /* 🧪 Zadanie 11: Symuluj wolnego konsumenta I szybkiego producenta uzywajac `BlockingQueue` Z OGRANICZONA pojemnoscia. */
        public static void main(String[] args) { }
    }

    static class Exercise12_MeasureThreadPoolExhaustionUnderHighConcurrentBlockingLoad {
        /* 🧪 Zadanie 12: Zmierz WYCZERPANIE puli watkow POD DUZYM obciazeniem blokujacych zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareThroughputOfBlockingVsSimulatedNonBlockingApproach {
        /* 🧪 Zadanie 13: Porownaj PRZEPUSTOWOSC blokujacego podejscia Z SYMULOWANYM nieblokujacym (`CompletableFuture`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildBoundedBufferDemonstratingBackpressureManually {
        /* 🧪 Zadanie 14: Zbuduj OGRANICZONY bufor RECZNIE demonstrujacy zasade backpressure. */
        public static void main(String[] args) { }
    }

    static class Exercise15_AnalyzeWhenBlockingIoIsActuallyFineForSmallScaleApplications {
        /* 🧪 Zadanie 15: Bez terminala - przeanalizuj, KIEDY blocking I/O jest W PORZADKU DLA MALYCH aplikacji. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareThreadPerRequestModelWithEventLoopModelDiagram {
        /* 🧪 Zadanie 16: Narysuj (ASCII) porownanie modelu "watek NA zadanie" Z modelem "event loop". */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainWhyNodeJsUsesSingleThreadedEventLoop {
        /* 🧪 Zadanie 17: Bez terminala - wyjasnij (koncepcyjnie), DLACZEGO Node.js uzywa JEDNOWATKOWEGO event loop. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ListRealWorldSystemsThatBenefitFromReactiveArchitecture {
        /* 🧪 Zadanie 18: Bez terminala - wypisz REALNE systemy, KTORE korzystaja Z architektury reaktywnej (np. streaming, IoT, chat). */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainRelationshipBetweenReactiveAndAsynchronousProgramming {
        /* 🧪 Zadanie 19: Bez terminala - wyjasnij ZWIAZEK (i ROZNICE) MIEDZY "reaktywne" A "asynchroniczne". */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureContextSwitchingOverheadWithManyPlatformThreads {
        /* 🧪 Zadanie 20: Zmierz (przyblizeniowo) narzut PRZELACZANIA kontekstu PRZY WIELU watkach platformowych. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildSimplePushPullBackpressureSimulationBetweenTwoThreads {
        /* 🧪 Zadanie 21: Zbuduj PROSTA symulacje "push" vs "pull" backpressure MIEDZY 2 watkami. */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignArchitectureDecisionRecordForChoosingReactiveStackForNewProject {
        /* 🧪 Zadanie 22: Powiaz z `_17_architecture/Lesson02` - zaprojektuj ADR DLA wyboru stosu reaktywnego DLA NOWEGO projektu. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildLoadTestComparingBlockingServletModelWithSimulatedReactiveModel {
        /* 🧪 Zadanie 23: Zbuduj TEST obciazeniowy porownujacy model BLOKUJACY (Servlet) Z SYMULOWANYM reaktywnym. */
        public static void main(String[] args) { }
    }

    static class Exercise24_AnalyzeCostOfContextSwitchingWithThousandsOfBlockedThreads {
        /* 🧪 Zadanie 24: Przeanalizuj (KONCEPCYJNIE) koszt PRZELACZANIA kontekstu PRZY TYSIACACH zablokowanych watkow. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignHybridArchitectureUsingBothReactiveAndBlockingComponents {
        /* 🧪 Zadanie 25: Zaprojektuj HYBRYDOWA architekture LACZACA komponenty reaktywne I blokujace. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCustomThreadPoolMonitorTrackingBlockedVsActiveThreads {
        /* 🧪 Zadanie 26: Powiaz z `_15_jvm_internals` - zaimplementuj monitor puli watkow SLEDZACY ZABLOKOWANE vs AKTYWNE watki. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildCapacityPlanningModelForThreadPerRequestVsReactiveApproach {
        /* 🧪 Zadanie 27: Zbuduj MODEL planowania pojemnosci (ile serwerow potrzeba) DLA modelu "watek NA zadanie" WOBEC reaktywnego. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ResearchAndSummarizeReactiveManifestoPrinciples {
        /* 🧪 Zadanie 28: (WYMAGA dostepu DO internetu) Zbadaj I podsumuj zasady "Reactive Manifesto" (responsywnosc/odpornosc/elastycznosc/komunikacja zdarzeniowa). */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignMigrationStrategyFromBlockingServiceToReactiveStack {
        /* 🧪 Zadanie 29: Zaprojektuj STRATEGIE migracji ISTNIEJACEGO, blokujacego serwisu NA stos reaktywny. */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildComprehensiveComparisonMatrixOfConcurrencyModelsInJava {
        /* 🧪 Zadanie 30: Zbuduj KOMPLEKSOWA macierz porownawcza WSZYSTKICH modeli wspolbieznosci W Javie (watki platformowe, wirtualne, reaktywny, event loop). */
        public static void main(String[] args) { }
    }
}
