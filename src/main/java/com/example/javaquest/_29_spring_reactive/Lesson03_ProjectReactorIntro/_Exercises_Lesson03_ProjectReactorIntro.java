package com.example.javaquest._29_spring_reactive.Lesson03_ProjectReactorIntro;

public class _Exercises_Lesson03_ProjectReactorIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateMonoWithJustAndSubscribeToIt {
        /* 🧪 Zadanie 1: Stworz `Mono.just(...)` I zasubskrybuj sie DO niego. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateFluxFromIterableAndPrintEachElement {
        /* 🧪 Zadanie 2: Stworz `Flux.fromIterable(...)` I WYPISZ KAZDY element. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ObserveThatCodeInsideMonoDoesNotRunWithoutSubscribe {
        /* 🧪 Zadanie 3: Zaobserwuj, ze kod wewnatrz `Mono.fromSupplier` NIE WYKONUJE SIE bez `subscribe()`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ChainMapAndFilterOnFluxRange {
        /* 🧪 Zadanie 4: Polacz `map`+`filter` NA `Flux.range(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseFluxIntervalWithTakeToLimitEmissions {
        /* 🧪 Zadanie 5: Uzyj `Flux.interval(...)` Z `take(n)` DO OGRANICZENIA emisji. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareMonoWithOptionalConceptually {
        /* 🧪 Zadanie 6: Bez terminala - porownaj `Mono<T>` Z `Optional<T>` - KIEDY podobienstwo sie KONCZY. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareFluxWithStreamConceptually {
        /* 🧪 Zadanie 7: Bez terminala - porownaj `Flux<T>` Z `Stream<T>` - KIEDY podobienstwo sie KONCZY. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyProjectReactorIsUsedBySpringWebFlux {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, DLACZEGO Spring WebFlux uzywa WLASNIE Project Reactor. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseBlockToGetValueOutOfMonoInMainMethodOnly {
        /* 🧪 Zadanie 9: Uzyj `.block()` DO WYCIAGNIECIA wartosci Z `Mono` (TYLKO W `main()`, NIGDY W prawdziwym kodzie reaktywnym). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyBlockShouldNeverBeUsedInsideReactiveController {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO `.block()` NIGDY nie powinien byc uzyty wewnatrz reaktywnego kontrolera (Lesson09+). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CreateMonoFromCallableWithLazyEvaluation {
        /* 🧪 Zadanie 11: Stworz `Mono.fromCallable(...)` demonstrujac LENIWA ewaluacje. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseFluxJustWithMultipleValuesAndCollectToList {
        /* 🧪 Zadanie 12: Uzyj `Flux.just(...)` Z WIELOMA wartosciami I ZBIERZ JE DO listy. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ChainMultipleSubscribersOnSameColdPublisherAndObserveReExecution {
        /* 🧪 Zadanie 13: Zasubskrybuj sie DWA razy DO TEGO SAMEGO "cold" Publishera I zaobserwuj PONOWNE wykonanie. */
        public static void main(String[] args) { }
    }

    static class Exercise14_UseDoOnNextAndDoOnCompleteForSideEffectLogging {
        /* 🧪 Zadanie 14: Uzyj `doOnNext`/`doOnComplete` DO logowania EFEKTOW UBOCZNYCH BEZ zmiany danych. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CreateFluxFromArrayAndApplyMultipleTransformations {
        /* 🧪 Zadanie 15: Stworz `Flux.fromArray(...)` I ZASTOSUJ WIELE transformacji. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareEagerCompletableFutureWithLazyMono {
        /* 🧪 Zadanie 16: Powiaz z `_14_advancedjava/Lesson32` - porownaj EAGER `CompletableFuture` Z LENIWYM `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_UseSubscribeWithAllFourCallbacksNextErrorComplete {
        /* 🧪 Zadanie 17: Uzyj `subscribe(...)` Z WSZYSTKIMI 3 callbackami (onNext/onError/onComplete). */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureTimeDifferenceBetweenCreatingAndSubscribingToMono {
        /* 🧪 Zadanie 18: Zmierz ROZNICE czasu MIEDZY UTWORZENIEM A SUBSKRYPCJA `Mono` (dowod na lazy evaluation). */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildSimpleDataPipelineUsingFluxFromDatabaseLikeSource {
        /* 🧪 Zadanie 19: Zbuduj PROSTY pipeline danych uzywajac `Flux` Z symulowanego zrodla "bazodanowego". */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainDifferenceBetweenColdAndHotPublishersConceptually {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij ROZNICE MIEDZY "cold" A "hot" Publisherem (koncepcyjnie, pogliebienie W dalszych lekcjach). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildCustomMonoWrappingLegacyCallbackBasedApi {
        /* 🧪 Zadanie 21: Zbuduj WLASNY `Mono` OPAKOWUJACY legacy API OPARTE NA callbackach (`Mono.create(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRetryWithBackoffUsingReactorRetrySpec {
        /* 🧪 Zadanie 22: Zaimplementuj retry Z opoznieniem uzywajac `Retry` spec Project Reactor. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildMultiStageDataTransformationPipelineWithErrorRecovery {
        /* 🧪 Zadanie 23: Zbuduj WIELOETAPOWY pipeline transformacji danych Z ODZYSKIWANIEM PO bledzie. */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareMemoryUsageOfEagerListVsLazyFluxForLargeDataset {
        /* 🧪 Zadanie 24: Porownaj (KONCEPCYJNIE) zuzycie pamieci EAGER listy Z LENIWYM `Flux` DLA DUZEGO zbioru danych. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCustomOperatorUsingTransformMethod {
        /* 🧪 Zadanie 25: Zaimplementuj WLASNY operator uzywajac `.transform(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildReactiveEventProcessingPipelineSimulatingRealTimeData {
        /* 🧪 Zadanie 26: Zbuduj REAKTYWNY pipeline przetwarzania zdarzen SYMULUJACY dane W CZASIE RZECZYWISTYM. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignArchitectureDecisionForCombiningReactorWithNonReactiveLegacyCode {
        /* 🧪 Zadanie 27: Powiaz z `_17_architecture/Lesson02` - zaprojektuj decyzje O LACZENIU Reactora Z NIE-reaktywnym legacy kodem. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCustomSchedulerAwareOperatorForCpuBoundTransformation {
        /* 🧪 Zadanie 28: Powiaz z `Lesson08` - zaimplementuj operator SWIADOMY schedulera DLA transformacji CPU-bound. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildBenchmarkComparingSynchronousStreamWithReactiveFluxForSameOperation {
        /* 🧪 Zadanie 29: Zbuduj BENCHMARK porownujacy SYNCHRONICZNY `Stream` Z REAKTYWNYM `Flux` DLA TEJ SAMEJ operacji. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullReactiveDataProcessingSystemUsingOnlyProjectReactorApi {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY system przetwarzania danych uzywajac WYLACZNIE API Project Reactor (bez Spring WebFlux). */
        public static void main(String[] args) { }
    }
}
