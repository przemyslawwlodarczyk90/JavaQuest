package com.example.javaquest._29_spring_reactive.Lesson12_WebClientDeepDive;

public class _Exercises_Lesson12_WebClientDeepDive {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_UseSubscribeInsteadOfBlockForGetRequest {
        /* 🧪 Zadanie 1: Uzyj `.subscribe()` ZAMIAST `.block()` DLA zadania GET. */
        public static void main(String[] args) { }
    }

    static class Exercise02_SubscribeWithOnNextAndOnErrorCallbacks {
        /* 🧪 Zadanie 2: Zasubskrybuj sie Z callbackami `onNext` I `onError`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseCountDownLatchToWaitForAsyncResultInMain {
        /* 🧪 Zadanie 3: Uzyj `CountDownLatch` DO oczekiwania NA asynchroniczny wynik W `main()`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseFlatMapToRunMultipleWebClientCallsInParallel {
        /* 🧪 Zadanie 4: Uzyj `flatMap` DO uruchomienia WIELU wywolan `WebClient` ROWNOLEGLE. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseOnErrorResumeForWebClientResponseException {
        /* 🧪 Zadanie 5: Uzyj `onErrorResume` DLA `WebClientResponseException`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseExchangeToMonoToInspectStatusCodeBeforeReadingBody {
        /* 🧪 Zadanie 6: Uzyj `exchangeToMono` DO sprawdzenia kodu statusu PRZED odczytem ciala. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareBlockingUsageFromLesson22SpringWebWithReactiveUsageHere {
        /* 🧪 Zadanie 7: Powiaz z `_22_spring_web/Lesson17` - porownaj uzycie blokujace Z reaktywnym Z tej lekcji. */
        public static void main(String[] args) { }
    }

    static class Exercise08_MeasureThatSubscribeReturnsImmediatelyWithoutWaiting {
        /* 🧪 Zadanie 8: Zmierz, ze `.subscribe()` ZWRACA sie NATYCHMIAST BEZ oczekiwania. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseRetrieveWithOnStatusToHandleSpecificHttpStatusCodes {
        /* 🧪 Zadanie 9: Uzyj `.retrieve().onStatus(...)` DO obslugi KONKRETNYCH kodow statusu. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyBlockShouldOnlyBeUsedAtApplicationBoundary {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO `.block()` powinien byc uzyty TYLKO NA GRANICY aplikacji (np. `main()`, testy). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildFireAndForgetNotificationCallUsingSubscribeWithoutWaitingResult {
        /* 🧪 Zadanie 11: Zbuduj wywolanie "fire-and-forget" (powiadomienie) uzywajac `.subscribe()` BEZ oczekiwania NA wynik. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementParallelAggregationOfThreeDifferentEndpointsUsingZip {
        /* 🧪 Zadanie 12: Zaimplementuj rownolegla agregacje 3 ROZNYCH endpointow uzywajac `Mono.zip`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_BuildRetryLogicForWebClientCallsUsingRetryWhen {
        /* 🧪 Zadanie 13: Zbuduj LOGIKE retry DLA wywolan `WebClient` uzywajac `retryWhen`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementTimeoutForWebClientCallWithFallback {
        /* 🧪 Zadanie 14: Zaimplementuj timeout DLA wywolania `WebClient` Z fallbackiem. */
        public static void main(String[] args) { }
    }

    static class Exercise15_UseWebClientWithCustomHeadersAndQueryParameters {
        /* 🧪 Zadanie 15: Uzyj `WebClient` Z WLASNYMI naglowkami I parametrami zapytania. */
        public static void main(String[] args) { }
    }

    static class Exercise16_BuildStreamingConsumerForServerSentEventsUsingWebClient {
        /* 🧪 Zadanie 16: Zbuduj STREAMINGOWEGO konsumenta Server-Sent Events uzywajac `WebClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementCircuitBreakerLikeBehaviorForRepeatedlyFailingEndpoint {
        /* 🧪 Zadanie 17: Zaimplementuj zachowanie PODOBNE DO circuit breaker DLA WIELOKROTNIE zawodzacego endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareWebClientWithRestClientFromLesson17SpringWebForBlockingUseCase {
        /* 🧪 Zadanie 18: Powiaz z `_22_spring_web/Lesson17` - porownaj `WebClient` Z `RestClient` DLA przypadku blokujacego. */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildCompositeApiCallChainingResultOfFirstCallIntoSecond {
        /* 🧪 Zadanie 19: Zbuduj lancuch wywolan API, GDZIE wynik 1. jest UZYTY W 2. (`flatMap`). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementBulkheadPatternLimitingConcurrentWebClientCalls {
        /* 🧪 Zadanie 20: Zaimplementuj wzorzec bulkhead OGRANICZAJACY rownolegle wywolania `WebClient` (`flatMap` Z limitem concurrency). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullResilientApiClientCombiningRetryTimeoutAndFallback {
        /* 🧪 Zadanie 21: Zbuduj PELNY, ODPORNY klient API LACZACY retry+timeout+fallback. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRateLimitedWebClientWrapperRespectingExternalApiLimits {
        /* 🧪 Zadanie 22: Zaimplementuj wrapper `WebClient` RESPEKTUJACY LIMITY zewnetrznego API. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildAggregatingGatewayCallingMultipleBackendServicesInParallel {
        /* 🧪 Zadanie 23: Powiaz z `_31_spring_cloud_microservices` - zbuduj agregujacy "gateway" WYWOLUJACY WIELE backendow ROWNOLEGLE. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRequestResponseLoggingFilterForWebClientUsingExchangeFilterFunction {
        /* 🧪 Zadanie 24: Zaimplementuj filtr logowania zadan/odpowiedzi uzywajac `ExchangeFilterFunction`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildTokenRefreshingWebClientForAuthenticatedApiCalls {
        /* 🧪 Zadanie 25: Powiaz z `_24_spring_security` - zbuduj `WebClient` ODSWIEZAJACY token DLA uwierzytelnionych wywolan. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementCachingLayerForWebClientResponsesUsingCacheOperator {
        /* 🧪 Zadanie 26: Zaimplementuj WARSTWE cache DLA odpowiedzi `WebClient` uzywajac `.cache()`. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildComprehensiveMetricsCollectorForWebClientCallsUsingDoOnEachSignal {
        /* 🧪 Zadanie 27: Zbuduj KOMPLEKSOWY kolektor metryk DLA wywolan `WebClient` uzywajac `doOnEach`. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementConnectionPoolTuningForHighThroughputWebClientUsage {
        /* 🧪 Zadanie 28: Zaimplementuj TUNING puli polaczen DLA WebClient PRZY WYSOKIEJ przepustowosci. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullIntegrationTestSuiteForWebClientBasedServiceUsingWireMock {
        /* 🧪 Zadanie 29: Powiaz z `_26_integration_testing/Lesson07` - zbuduj PAKIET testow integracyjnych DLA serwisu OPARTEGO NA `WebClient` uzywajac WireMock. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullResilientHttpClientArchitectureForProductionMicroservice {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA, ODPORNA architekture klienta HTTP DLA PRODUKCYJNEGO mikroserwisu. */
        public static void main(String[] args) { }
    }
}
