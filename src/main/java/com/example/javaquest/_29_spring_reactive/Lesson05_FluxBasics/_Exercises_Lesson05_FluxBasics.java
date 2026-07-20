package com.example.javaquest._29_spring_reactive.Lesson05_FluxBasics;

public class _Exercises_Lesson05_FluxBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateFluxJustWithFiveElementsAndCollectToList {
        /* 🧪 Zadanie 1: Stworz `Flux.just(...)` Z 5 elementami I ZBIERZ DO listy. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateFluxRangeAndCountElements {
        /* 🧪 Zadanie 2: Stworz `Flux.range(...)` I POLICZ elementy (`count()`). */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseFluxIntervalWithTakeToAvoidInfiniteStream {
        /* 🧪 Zadanie 3: Uzyj `Flux.interval(...)` Z `take(n)` UNIKAJAC nieskonczonego strumienia. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReduceFluxOfNumbersToSum {
        /* 🧪 Zadanie 4: ZREDUKUJ `Flux` liczb DO sumy uzywajac `reduce()`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConcatTwoFluxAndVerifyOrderIsPreserved {
        /* 🧪 Zadanie 5: POLACZ 2 `Flux` uzywajac `concat` I zweryfikuj ZACHOWANIE kolejnosci. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ZipTwoFluxAndCombineElementsPairwise {
        /* 🧪 Zadanie 6: POLACZ 2 `Flux` uzywajac `zipWith` PAROWANIEM element PO elemencie. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareFluxJustWithFluxFromIterableForSameData {
        /* 🧪 Zadanie 7: Porownaj `Flux.just(...)` Z `Flux.fromIterable(...)` DLA TYCH SAMYCH danych. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyFluxIntervalNeverCompletesWithoutTake {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, DLACZEGO `Flux.interval()` NIGDY sie NIE KONCZY BEZ `take()`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseFirstWithValueToGetFirstEmittingFlux {
        /* 🧪 Zadanie 9: Uzyj `Flux.firstWithValue(...)` DO uzyskania PIERWSZEGO emitujacego strumienia. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToUseFluxVsMonoAsMethodReturnType {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, KIEDY zwracac `Flux`, A KIEDY `Mono` Z metody serwisu. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MergeThreeFluxAndObserveInterleavedOrdering {
        /* 🧪 Zadanie 11: POLACZ 3 `Flux` uzywajac `merge` I zaobserwuj PRZEPLATANA kolejnosc. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementCustomStreamOfSimulatedSensorReadingsUsingFluxInterval {
        /* 🧪 Zadanie 12: Zaimplementuj SYMULOWANY strumien odczytow czujnika uzywajac `Flux.interval`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseDistinctToRemoveDuplicateElementsFromFlux {
        /* 🧪 Zadanie 13: Uzyj `distinct()` DO usuniecia DUPLIKATOW Z `Flux`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseSortToOrderFluxElements {
        /* 🧪 Zadanie 14: Uzyj `sort()` DO uporzadkowania elementow `Flux`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CombineFluxWithScanForRunningTotal {
        /* 🧪 Zadanie 15: Uzyj `scan()` DO obliczenia SUMY KROCZACEJ. */
        public static void main(String[] args) { }
    }

    static class Exercise16_UseBufferToGroupElementsIntoFixedSizeBatches {
        /* 🧪 Zadanie 16: Uzyj `buffer(n)` DO GRUPOWANIA elementow W partie O STALYM rozmiarze. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseWindowToCreateOverlappingOrNonOverlappingSubStreams {
        /* 🧪 Zadanie 17: Uzyj `window(n)` DO utworzenia PODSTRUMIENI. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementRealTimeAveragingOfSimulatedFluxDataStream {
        /* 🧪 Zadanie 18: Zaimplementuj OBLICZANIE sredniej Z SYMULOWANEGO strumienia danych W CZASIE RZECZYWISTYM. */
        public static void main(String[] args) { }
    }

    static class Exercise19_UseSampleToThrottleHighFrequencyFluxEmissions {
        /* 🧪 Zadanie 19: Uzyj `sample(Duration)` DO OGRANICZENIA CZESTOTLIWOSCI emisji `Flux`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareMergeConcatAndZipBehaviorSideBySide {
        /* 🧪 Zadanie 20: Porownaj `merge`/`concat`/`zip` OBOK siebie NA TYCH SAMYCH danych. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildRealTimeStockPriceAggregatorUsingFluxAndWindow {
        /* 🧪 Zadanie 21: Zbuduj agregator cen akcji W CZASIE RZECZYWISTYM uzywajac `Flux` + `window`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementBackpressureAwareBatchProcessorUsingFluxBufferAndRequest {
        /* 🧪 Zadanie 22: Zaimplementuj procesor wsadowy SWIADOMY backpressure uzywajac `buffer` + REGULACJI `request(n)`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildMultiSourceDataAggregationPipelineUsingMergeAndFilter {
        /* 🧪 Zadanie 23: Zbuduj pipeline agregacji danych Z WIELU zrodel uzywajac `merge` + `filter`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSlidingWindowAnomalyDetectionUsingFluxWindow {
        /* 🧪 Zadanie 24: Zaimplementuj WYKRYWANIE ANOMALII W PRZESUWNYM oknie uzywajac `Flux.window`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildRateLimitedEventProcessorUsingFluxSampleAndDelayElements {
        /* 🧪 Zadanie 25: Zbuduj procesor zdarzen Z OGRANICZONYM tempem uzywajac `sample`/`delayElements`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementDeduplicationWithTimeWindowUsingDistinctAndBuffer {
        /* 🧪 Zadanie 26: Zaimplementuj DEDUPLIKACJE Z OKNEM czasowym LACZAC `distinct` + `buffer`. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildComplexEventProcessingPipelineCombiningMultipleFluxOperators {
        /* 🧪 Zadanie 27: Zbuduj ZLOZONY pipeline przetwarzania zdarzen LACZACY WIELE operatorow `Flux` NARAZ. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementFluxBasedRateLimiterEnforcingMaxThroughputPerSecond {
        /* 🧪 Zadanie 28: Zaimplementuj OGRANICZNIK tempa OPARTY NA `Flux` WYMUSZAJACY MAX przepustowosc/s. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFluxBasedSimulationOfProducerConsumerWithVaryingSpeeds {
        /* 🧪 Zadanie 29: Zbuduj SYMULACJE producent-konsument O ROZNYCH tempach uzywajac `Flux` + backpressure. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullStreamingDataPipelineArchitectureUsingOnlyFluxOperators {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA architekture pipeline'u streamingowego uzywajac WYLACZNIE operatorow `Flux`. */
        public static void main(String[] args) { }
    }
}
