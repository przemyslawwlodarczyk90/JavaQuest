package com.example.javaquest._29_spring_reactive.Lesson04_MonoBasics;

public class _Exercises_Lesson04_MonoBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateMonoJustAndBlockToGetValue {
        /* 🧪 Zadanie 1: Stworz `Mono.just(...)` I `.block()` DO odczytu wartosci. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateEmptyMonoAndObserveNullOnBlock {
        /* 🧪 Zadanie 2: Stworz `Mono.empty()` I zaobserwuj `null` PRZY `.block()`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseMapToTransformMonoValue {
        /* 🧪 Zadanie 3: Uzyj `map()` DO transformacji wartosci `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseFilterToConditionallyEmptyMono {
        /* 🧪 Zadanie 4: Uzyj `filter()` DO WARUNKOWEGO oproznienia `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseDefaultIfEmptyToProvideFallbackValue {
        /* 🧪 Zadanie 5: Uzyj `defaultIfEmpty()` DO dostarczenia wartosci ZASTEPCZEJ. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateMonoErrorAndCatchWithOnErrorResume {
        /* 🧪 Zadanie 6: Stworz `Mono.error(...)` I ZLAP go `onErrorResume`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_UseFlatMapToChainAsynchronousMonoOperations {
        /* 🧪 Zadanie 7: Uzyj `flatMap()` DO POLACZENIA 2 asynchronicznych operacji `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareMapWithFlatMapForMonoReturningMethod {
        /* 🧪 Zadanie 8: Porownaj `map` Z `flatMap` DLA metody ZWRACAJACEJ `Mono<T>`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConvertOptionalToMonoUsingJustOrEmpty {
        /* 🧪 Zadanie 9: SKONWERTUJ `Optional<T>` NA `Mono<T>` uzywajac `justOrEmpty`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhenToUseMonoVsFluxForMethodReturnType {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, KIEDY metoda POWINNA zwracac `Mono`, A KIEDY `Flux`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ChainMultipleMapOperationsOnMono {
        /* 🧪 Zadanie 11: Polacz WIELE operacji `map` NA `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseSwitchIfEmptyToCallAlternativeDataSource {
        /* 🧪 Zadanie 12: Uzyj `switchIfEmpty()` DO WYWOLANIA alternatywnego zrodla danych. */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseDoOnNextAndDoOnErrorForSideEffectLoggingWithoutModifyingValue {
        /* 🧪 Zadanie 13: Uzyj `doOnNext`/`doOnError` DO logowania BEZ modyfikacji wartosci. */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildUserLookupSimulationReturningMonoOfUser {
        /* 🧪 Zadanie 14: Zbuduj SYMULACJE wyszukiwania uzytkownika ZWRACAJACA `Mono<Uzytkownik>`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ChainTwoDependentMonoCallsUsingFlatMap {
        /* 🧪 Zadanie 15: Polacz 2 ZALEZNE wywolania `Mono` (wynik 1. UZYTY W 2.) uzywajac `flatMap`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_UseZipToCombineTwoIndependentMonoResults {
        /* 🧪 Zadanie 16: Uzyj `Mono.zip(...)` DO POLACZENIA 2 NIEZALEZNYCH wynikow `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementRetryOnErrorUsingRetrySpec {
        /* 🧪 Zadanie 17: Zaimplementuj `retry(...)` PRZY bledzie `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_UseTimeoutToLimitMonoExecutionTime {
        /* 🧪 Zadanie 18: Uzyj `.timeout(Duration)` DO OGRANICZENIA czasu wykonania `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareMonoJustWithMonoFromSupplierEagerVsLazy {
        /* 🧪 Zadanie 19: Porownaj `Mono.just(x)` (wartosc JUZ obliczona) Z `Mono.fromSupplier` (LENIWE obliczenie). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementCacheableMonoUsingCacheOperator {
        /* 🧪 Zadanie 20: Zaimplementuj "cache'owalny" `Mono` uzywajac `.cache()` - drugi `subscribe` NIE PONAWIA obliczenia. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildUserProfileAggregatorCombiningThreeIndependentMonoSources {
        /* 🧪 Zadanie 21: Zbuduj agregator profilu uzytkownika LACZACY 3 NIEZALEZNE zrodla `Mono` (`Mono.zip`). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCircuitBreakerLikeFallbackChainUsingOnErrorResume {
        /* 🧪 Zadanie 22: Zaimplementuj LANCUCH fallbackow PODOBNY DO circuit breaker uzywajac `onErrorResume`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildValidationPipelineReturningMonoErrorOnFirstFailure {
        /* 🧪 Zadanie 23: Zbuduj pipeline walidacji ZWRACAJACY `Mono.error(...)` PRZY PIERWSZYM naruszeniu. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementTimeoutWithFallbackValueInsteadOfException {
        /* 🧪 Zadanie 24: Zaimplementuj timeout Z wartoscia ZASTEPCZA ZAMIAST wyjatku. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildAuditLoggingWrapperAroundMonoUsingDoOnSuccessAndDoOnError {
        /* 🧪 Zadanie 25: Zbuduj wrapper audytujacy WOKOL `Mono` uzywajac `doOnSuccess`/`doOnError`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementConditionalFlatMapChainBasedOnBusinessRules {
        /* 🧪 Zadanie 26: Zaimplementuj WARUNKOWY lancuch `flatMap` OPARTY NA regulach biznesowych. */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareCachedMonoWithFreshMonoUnderConcurrentSubscriptions {
        /* 🧪 Zadanie 27: Porownaj CACHE'OWANY `Mono` Z SWIEZYM PRZY rownoleglych subskrypcjach. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildRequestResponseCorrelationUsingContextualMonoChain {
        /* 🧪 Zadanie 28: Zbuduj korelacje zadanie-odpowiedz uzywajac kontekstowego lancucha `Mono` (`Reactor Context`, koncepcyjnie). */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementFullServiceLayerMethodReturningMonoWithValidationCachingAndErrorHandling {
        /* 🧪 Zadanie 29: Zaimplementuj PELNA metode warstwy serwisu ZWRACAJACA `Mono` Z walidacja, cache'owaniem I obsluga bledow RAZEM. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignReactiveApiContractGuidelinesForWhenToReturnMonoOfVoidVsMonoOfResult {
        /* 🧪 Zadanie 30: Zaprojektuj WYTYCZNE kontraktu API reaktywnego - KIEDY zwracac `Mono<Void>`, A KIEDY `Mono<T>`. */
        public static void main(String[] args) { }
    }
}
