package com.example.javaquest._31_spring_cloud_microservices.Lesson10_Resilience4jIntegration;

public class _Exercises_Lesson10_Resilience4jIntegration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddCircuitBreakerAnnotationToServiceMethod {
        /* 🧪 Zadanie 1: Dodaj `@CircuitBreaker` DO metody serwisu Z fallbackiem (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhyFallbackMethodSignatureMustMatch {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, DLACZEGO sygnatura metody fallback MUSI PASOWAC DO oryginalnej + `Throwable`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ConfigureCircuitBreakerThresholdViaProperties {
        /* 🧪 Zadanie 3: Skonfiguruj prog bledow circuit breakera PRZEZ wlasciwosci `resilience4j.circuitbreaker.instances.*`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddRetryAnnotationWithMaxAttempts {
        /* 🧪 Zadanie 4: Dodaj `@Retry` Z okreslona liczba prob (`max-attempts`). */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddRateLimiterAnnotationWithLimitForPeriod {
        /* 🧪 Zadanie 5: Dodaj `@RateLimiter` Z okreslonym limitem NA okres (`limit-for-period`). */
        public static void main(String[] args) { }
    }

    static class Exercise06_ObserveCircuitBreakerOpeningAfterThresholdExceeded {
        /* 🧪 Zadanie 6: ZAOBSERWUJ otwarcie circuit breakera PO przekroczeniu progu (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise07_ObserveRetrySucceedingAfterTransientFailures {
        /* 🧪 Zadanie 7: ZAOBSERWUJ retry KONCZACY SIE sukcesem PO chwilowych porazkach. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ObserveRateLimiterRejectingExcessCalls {
        /* 🧪 Zadanie 8: ZAOBSERWUJ rate limiter ODRZUCAJACY zadania PONAD limit. */
        public static void main(String[] args) { }
    }

    static class Exercise09_QueryCircuitBreakerStateProgrammatically {
        /* 🧪 Zadanie 9: Odpytaj stan circuit breakera PROGRAMOWO PRZEZ `CircuitBreakerRegistry`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyProxyBasedAnnotationsRequireSpringManagedBean {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO adnotacje OPARTE NA proxy WYMAGAJA beana ZARZADZANEGO przez Springa. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CombineRetryAndCircuitBreakerOnSameMethod {
        /* 🧪 Zadanie 11: Polacz `@Retry` I `@CircuitBreaker` NA TEJ SAMEJ metodzie - sprawdz KOLEJNOSC dzialania. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConfigureRetryToOnlyRetrySpecificExceptions {
        /* 🧪 Zadanie 12: Skonfiguruj `@Retry` DO ponawiania TYLKO okreslonych wyjatkow (`retry-exceptions`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureCircuitBreakerToIgnoreSpecificExceptions {
        /* 🧪 Zadanie 13: Skonfiguruj circuit breaker DO IGNOROWANIA okreslonych wyjatkow (np. 404 - NIE liczy sie jako awaria). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ObserveHalfOpenStateTransitionAfterWaitDuration {
        /* 🧪 Zadanie 14: ZAOBSERWUJ przejscie DO HALF_OPEN PO uplywie `wait-duration-in-open-state`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementBulkheadAnnotationLimitingConcurrentCalls {
        /* 🧪 Zadanie 15: Dodaj `@Bulkhead` ograniczajacy liczbe ROWNOCZESNYCH wywolan. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementTimeLimiterForAsyncMethod {
        /* 🧪 Zadanie 16: Dodaj `@TimeLimiter` DLA metody ASYNCHRONICZNEJ (`CompletableFuture`). */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareResultOfCircuitBreakerVsPlainTryCatch {
        /* 🧪 Zadanie 17: Porownaj wynik uzycia circuit breakera Z ZWYKLYM try-catch (BEZ odpornosci). */
        public static void main(String[] args) { }
    }

    static class Exercise18_RegisterCircuitBreakerEventListener {
        /* 🧪 Zadanie 18: Zarejestruj listener zdarzen circuit breakera (`onStateTransition`). */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConfigureExponentialBackoffForRetry {
        /* 🧪 Zadanie 19: Skonfiguruj rosnacy odstep MIEDZY probami retry (`exponential-backoff-multiplier`). */
        public static void main(String[] args) { }
    }

    static class Exercise20_CombineRateLimiterWithCircuitBreakerOnSameMethod {
        /* 🧪 Zadanie 20: Polacz `@RateLimiter` I `@CircuitBreaker` NA TEJ SAMEJ metodzie. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomResilience4jEventConsumerForMetrics {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY konsument zdarzen Resilience4j DO zbierania metryk. */
        public static void main(String[] args) { }
    }

    static class Exercise22_IntegrateResilience4jWithMicrometerForObservability {
        /* 🧪 Zadanie 22: Zintegruj Resilience4j Z Micrometer DLA obserwowalnosci (powiazanie Z Lesson11). */
        public static void main(String[] args) { }
    }

    static class Exercise23_ExplainSelfInvocationPitfallForResilience4jAnnotations {
        /* 🧪 Zadanie 23: Bez terminala - wyjasnij pulapke self-invocation DLA adnotacji Resilience4j (TA SAMA CO `@Async`/AOP). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignFallbackChainWithMultipleLevelsOfDegradation {
        /* 🧪 Zadanie 24: Zaprojektuj lancuch fallbackow Z WIELOMA poziomami degradacji (cache -> domyslna wartosc -> blad). */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareAnnotationBasedWithProgrammaticResilience4jApi {
        /* 🧪 Zadanie 25: Porownaj podejscie OPARTE NA adnotacjach Z programowym API Resilience4j (`CircuitBreaker.decorateSupplier`). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCircuitBreakerAwareHealthIndicator {
        /* 🧪 Zadanie 26: Zaimplementuj `HealthIndicator` SWIADOMY stanu circuit breakera (powiazanie Z `_21_spring_boot/Lesson12`). */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignChaosEngineeringTestUsingResilience4j {
        /* 🧪 Zadanie 27: Zaprojektuj test chaos engineering WYKORZYSTUJACY Resilience4j (symulacja losowych awarii). */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementPerInstanceCircuitBreakerForLoadBalancedCalls {
        /* 🧪 Zadanie 28: Zaimplementuj OSOBNY circuit breaker DLA KAZDEJ instancji (powiazanie Z Lesson08). */
        public static void main(String[] args) { }
    }

    static class Exercise29_MeasurePerformanceOverheadOfResilience4jProxies {
        /* 🧪 Zadanie 29: Zmierz narzut wydajnosciowy proxy Resilience4j WZGLEDEM bezposredniego wywolania. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionResiliencePolicyForCriticalDownstreamCall {
        /* 🧪 Zadanie 30: Zaprojektuj kompletna polityke odpornosci (circuit breaker+retry+rate limiter+bulkhead) DLA KRYTYCZNEGO wywolania. */
        public static void main(String[] args) { }
    }
}
