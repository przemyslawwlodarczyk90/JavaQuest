package com.example.javaquest._29_spring_reactive.Lesson07_ErrorHandlingInReactiveStreams;

public class _Exercises_Lesson07_ErrorHandlingInReactiveStreams {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_UseOnErrorReturnForFallbackValue {
        /* 🧪 Zadanie 1: Uzyj `onErrorReturn()` DLA wartosci zastepczej. */
        public static void main(String[] args) { }
    }

    static class Exercise02_UseOnErrorResumeToSwitchToAlternativeMono {
        /* 🧪 Zadanie 2: Uzyj `onErrorResume()` DO przelaczenia NA alternatywny `Mono`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseOnErrorMapToWrapTechnicalExceptionInBusinessException {
        /* 🧪 Zadanie 3: Uzyj `onErrorMap()` DO opakowania technicznego wyjatku W biznesowy. */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseRetryToRetryFailedMonoThreeTimes {
        /* 🧪 Zadanie 4: Uzyj `retry(3)` DO ponowienia NIEUDANEGO `Mono` 3 razy. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseRetryWhenWithBackoffForExponentialDelay {
        /* 🧪 Zadanie 5: Uzyj `retryWhen(Retry.backoff(...))` DLA wykladniczego opoznienia. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CatchAndLogErrorUsingDoOnError {
        /* 🧪 Zadanie 6: Zlap I zaloguj blad uzywajac `doOnError()` (BEZ modyfikacji sygnalu bledu). */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyErrorIsTerminalSignalInReactiveStreams {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO blad jest sygnalem TERMINALNYM W Reactive Streams. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareOnErrorReturnWithOnErrorResumeUseCases {
        /* 🧪 Zadanie 8: Bez terminala - porownaj przypadki uzycia `onErrorReturn` Z `onErrorResume`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseOnErrorCompleteToSilentlyEndStreamOnError {
        /* 🧪 Zadanie 9: Uzyj `onErrorComplete()` DO CICHEGO zakonczenia strumienia PRZY bledzie. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyRetryWithoutBackoffCanOverwhelmFailingService {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO `retry()` BEZ opoznienia MOZE PRZECIAZYC zawodzacy serwis. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementFallbackChainWithMultipleOnErrorResumeSteps {
        /* 🧪 Zadanie 11: Zaimplementuj LANCUCH fallbackow Z WIELOMA krokami `onErrorResume`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseOnErrorContinueToSkipFailingElementsInFlux {
        /* 🧪 Zadanie 12: Uzyj `onErrorContinue()` DO POMINIECIA problematycznych elementow W `Flux` (Z KOMENTARZEM o ostroznosci). */
        public static void main(String[] args) { }
    }

    static class Exercise13_DifferentiateBetweenRetryableAndNonRetryableExceptions {
        /* 🧪 Zadanie 13: Rozroznij WYJATKI PODLEGAJACE ponowieniu OD tych, ktore NIE (`retryWhen` Z filtrem). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementTimeoutWithFallbackUsingTimeoutAndOnErrorResume {
        /* 🧪 Zadanie 14: Zaimplementuj timeout Z fallbackiem LACZAC `.timeout()` + `onErrorResume`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_LogRetryAttemptsUsingDoBeforeRetry {
        /* 🧪 Zadanie 15: Zaloguj KAZDA probe ponowienia uzywajac `doBeforeRetry` (Retry Builder). */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildResilientHttpCallSimulationWithRetryAndFallback {
        /* 🧪 Zadanie 16: Zbuduj ODPORNA SYMULACJE wywolania HTTP Z retry + fallback RAZEM. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementMaxRetryAttemptsWithFinalFallbackAfterExhaustion {
        /* 🧪 Zadanie 17: Zaimplementuj MAKSYMALNA liczbe prob Z KONCOWYM fallbackiem PO WYCZERPANIU. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareRetryBehaviorForMonoVsFlux {
        /* 🧪 Zadanie 18: Porownaj zachowanie `retry()` DLA `Mono` A `Flux` (CALY strumien Flux zaczyna sie OD NOWA). */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildValidationErrorAggregatorCollectingAllFailuresInFlux {
        /* 🧪 Zadanie 19: Zbuduj agregator bledow walidacji ZBIERAJACY WSZYSTKIE niepowodzenia W `Flux` (BEZ przerywania NA pierwszym). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementCircuitBreakerLikeBehaviorUsingErrorCountingAndOnErrorResume {
        /* 🧪 Zadanie 20: Zaimplementuj zachowanie PODOBNE DO circuit breaker LICZAC bledy I uzywajac `onErrorResume`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullResilientServiceCallChainWithRetryFallbackAndTimeout {
        /* 🧪 Zadanie 21: Zbuduj PELNY, ODPORNY lancuch wywolania serwisu (retry + fallback + timeout RAZEM). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementExponentialBackoffWithJitterUsingCustomRetrySpec {
        /* 🧪 Zadanie 22: Zaimplementuj wykladnicze opoznienie Z "jitter" uzywajac WLASNEGO `RetrySpec`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildErrorClassificationSystemRoutingDifferentExceptionsToDifferentHandlers {
        /* 🧪 Zadanie 23: Zbuduj system KLASYFIKACJI bledow KIERUJACY ROZNE wyjatki DO ROZNYCH handlerow. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementBulkheadPatternLimitingConcurrentErrorProneOperations {
        /* 🧪 Zadanie 24: Zaimplementuj wzorzec bulkhead OGRANICZAJACY rownolegle, PODATNE NA bledy operacje. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildDeadLetterQueueSimulationForPermanentlyFailingElements {
        /* 🧪 Zadanie 25: Zbuduj SYMULACJE "dead letter queue" DLA TRWALE zawodzacych elementow (powiazanie Z `_30_spring_messaging_and_async`). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementAdaptiveRetryStrategyBasedOnErrorTypeAndAttemptCount {
        /* 🧪 Zadanie 26: Zaimplementuj ADAPTACYJNA strategie retry OPARTA NA typie bledu I liczbie prob. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildComprehensiveErrorMetricsCollectorForReactivePipeline {
        /* 🧪 Zadanie 27: Zbuduj KOMPLEKSOWY kolektor metryk bledow DLA pipeline'u reaktywnego. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignErrorHandlingArchitectureForMultiStageReactivePipeline {
        /* 🧪 Zadanie 28: Powiaz z `_17_architecture/Lesson16` - zaprojektuj architekture obslugi bledow DLA WIELOETAPOWEGO pipeline'u reaktywnego. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementGracefulDegradationChainWithMultipleFallbackTiers {
        /* 🧪 Zadanie 29: Zaimplementuj LAGODNA degradacje Z WIELOMA POZIOMAMI fallbackow. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullResiliencyPlaybookForProductionReactiveMicroservice {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY "playbook" odpornosci DLA PRODUKCYJNEGO mikroserwisu reaktywnego. */
        public static void main(String[] args) { }
    }
}
