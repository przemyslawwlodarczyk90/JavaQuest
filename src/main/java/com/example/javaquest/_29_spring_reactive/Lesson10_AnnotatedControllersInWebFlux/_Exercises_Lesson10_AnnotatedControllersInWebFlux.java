package com.example.javaquest._29_spring_reactive.Lesson10_AnnotatedControllersInWebFlux;

public class _Exercises_Lesson10_AnnotatedControllersInWebFlux {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateRestControllerMethodReturningFluxOfStrings {
        /* 🧪 Zadanie 1: Stworz metode `@RestController` ZWRACAJACA `Flux<String>`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateRestControllerMethodReturningMonoOfResponseEntity {
        /* 🧪 Zadanie 2: Stworz metode ZWRACAJACA `Mono<ResponseEntity<T>>`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UsePathVariableInReactiveControllerMethod {
        /* 🧪 Zadanie 3: Uzyj `@PathVariable` W REAKTYWNEJ metodzie kontrolera. */
        public static void main(String[] args) { }
    }

    static class Exercise04_HandlePostRequestBodyAsMonoOfDto {
        /* 🧪 Zadanie 4: Obsluz `@RequestBody Mono<Dto>` W metodzie POST. */
        public static void main(String[] args) { }
    }

    static class Exercise05_Return404UsingMonoOfResponseEntityNotFound {
        /* 🧪 Zadanie 5: Zwroc 404 uzywajac `Mono.just(ResponseEntity.notFound().build())`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseWebClientToCallGetEndpointReturningFlux {
        /* 🧪 Zadanie 6: Uzyj `WebClient` DO wywolania endpointu GET zwracajacego `Flux`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareControllerSyntaxBetweenMvcAndWebFluxSideBySide {
        /* 🧪 Zadanie 7: Powiaz z `_22_spring_web` - porownaj SKLADNIE kontrolera MVC A WebFlux OBOK siebie. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImplementDeleteEndpointReturningMonoOfVoid {
        /* 🧪 Zadanie 8: Zaimplementuj endpoint DELETE ZWRACAJACY `Mono<Void>`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_UseRequestParamInReactiveControllerForFiltering {
        /* 🧪 Zadanie 9: Uzyj `@RequestParam` W REAKTYWNYM kontrolerze DO filtrowania. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhySpringSubscribesToReturnedMonoFluxAutomatically {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, JAK Spring SAM subskrybuje sie DO zwroconego `Mono`/`Flux`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementServerSentEventsEndpointWithDelayElements {
        /* 🧪 Zadanie 11: Zaimplementuj endpoint Server-Sent Events Z `delayElements`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildPaginatedReactiveEndpointUsingSkipAndTakeOnFlux {
        /* 🧪 Zadanie 12: Zbuduj STRONICOWANY endpoint reaktywny uzywajac `skip`/`take` NA `Flux`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementValidationInReactiveControllerUsingFilterAndOnErrorResume {
        /* 🧪 Zadanie 13: Zaimplementuj walidacje W REAKTYWNYM kontrolerze uzywajac `filter`/`onErrorResume`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildAggregatingEndpointCombiningTwoReactiveServiceCalls {
        /* 🧪 Zadanie 14: Zbuduj endpoint AGREGUJACY 2 wywolania REAKTYWNEGO serwisu (`Mono.zip`). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementGlobalExceptionHandlerForReactiveControllerUsingExceptionHandler {
        /* 🧪 Zadanie 15: Powiaz z `_22_spring_web/Lesson09` - zaimplementuj `@ExceptionHandler` DLA REAKTYWNEGO kontrolera. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareLatencyOfCollectListWithStreamingSse {
        /* 🧪 Zadanie 16: Porownaj OPOZNIENIE `collectList()` (CZEKAJ NA WSZYSTKO) Z streamingiem SSE (dane NA BIEZACO). */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildFileUploadEndpointUsingFluxOfDataBuffer {
        /* 🧪 Zadanie 17: Zbuduj endpoint UPLOAD pliku uzywajac `Flux<DataBuffer>` (powiazanie Z `_22_spring_web/Lesson14`). */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementConditionalResponseBasedOnAcceptHeader {
        /* 🧪 Zadanie 18: Zaimplementuj WARUNKOWA odpowiedz OPARTA NA naglowku `Accept` (powiazanie Z `_18_rest_api/Lesson07`). */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildReactiveHealthCheckEndpointCombiningMultipleDependencyChecks {
        /* 🧪 Zadanie 19: Zbuduj REAKTYWNY endpoint health-check LACZACY sprawdzenia WIELU zaleznosci. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementRequestLoggingUsingWebFilterForReactiveEndpoints {
        /* 🧪 Zadanie 20: Zaimplementuj logowanie zadan uzywajac `WebFilter` (reaktywny odpowiednik `Filter`, `_07_servlets`). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullReactiveCrudApiWithValidationAndErrorHandling {
        /* 🧪 Zadanie 21: Zbuduj PELNE reaktywne API CRUD Z walidacja I obsluga bledow. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRealTimeNotificationEndpointUsingFluxSink {
        /* 🧪 Zadanie 22: Zaimplementuj endpoint POWIADOMIEN W CZASIE RZECZYWISTYM uzywajac `Flux.create`/`Sinks`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildRateLimitedReactiveEndpointUsingCustomWebFilter {
        /* 🧪 Zadanie 23: Zbuduj endpoint Z OGRANICZONYM tempem (powiazanie Z `_18_rest_api/Lesson16`) uzywajac WLASNEGO `WebFilter`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCachingLayerForReactiveEndpointUsingMonoCache {
        /* 🧪 Zadanie 24: Zaimplementuj WARSTWE cache DLA REAKTYWNEGO endpointu uzywajac `.cache()`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildComplexAggregationEndpointCallingThreeDownstreamReactiveServices {
        /* 🧪 Zadanie 25: Zbuduj ZLOZONY endpoint agregujacy WYWOLUJACY 3 podrzedne serwisy reaktywne. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementGracefulErrorResponseForTimeoutInReactiveController {
        /* 🧪 Zadanie 26: Zaimplementuj LAGODNA odpowiedz bledu PRZY timeout W REAKTYWNYM kontrolerze. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildBackpressureAwareStreamingEndpointForLargeDataset {
        /* 🧪 Zadanie 27: Zbuduj endpoint streamingowy SWIADOMY backpressure DLA DUZEGO zbioru danych. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementMultiTenantReactiveApiUsingContextForTenantPropagation {
        /* 🧪 Zadanie 28: Zaimplementuj WIELODZIERZAWCZE (multi-tenant) API uzywajac `Context` DO propagacji tenanta. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveReactiveApiTestSuiteUsingWebTestClient {
        /* 🧪 Zadanie 29: Powiaz z `_27_spring_test` - zbuduj KOMPLEKSOWY pakiet testow uzywajac `WebTestClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullReactiveApiArchitectureForRealTimeDashboardApplication {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA architekture API reaktywnego DLA aplikacji dashboardu W CZASIE RZECZYWISTYM. */
        public static void main(String[] args) { }
    }
}
