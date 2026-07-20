package com.example.javaquest._29_spring_reactive.Lesson11_FunctionalEndpointsRouterFunction;

public class _Exercises_Lesson11_FunctionalEndpointsRouterFunction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateSimpleRouterFunctionWithSingleGetRoute {
        /* 🧪 Zadanie 1: Stworz PROSTA `RouterFunction` Z 1 trasa GET. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementHandlerFunctionReturningServerResponseOk {
        /* 🧪 Zadanie 2: Zaimplementuj `HandlerFunction` ZWRACAJACY `ServerResponse.ok()`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExtractPathVariableFromServerRequest {
        /* 🧪 Zadanie 3: WYCIAGNIJ zmienna sciezki Z `ServerRequest.pathVariable(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ReadRequestBodyAsMonoInHandlerFunction {
        /* 🧪 Zadanie 4: Odczytaj cialo zadania JAKO `Mono` W `HandlerFunction`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_Return404UsingServerResponseNotFound {
        /* 🧪 Zadanie 5: Zwroc 404 uzywajac `ServerResponse.notFound().build()`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CombineMultipleRoutesUsingRouteBuilder {
        /* 🧪 Zadanie 6: POLACZ WIELE tras uzywajac buildera `route()`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareRouterFunctionSyntaxWithRestControllerSyntax {
        /* 🧪 Zadanie 7: Powiaz z `Lesson10` - porownaj SKLADNIE `RouterFunction` Z `@RestController`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseAcceptPredicateToRequireJsonContentType {
        /* 🧪 Zadanie 8: Uzyj predykatu `accept(MediaType.APPLICATION_JSON)` WYMAGAJACEGO typu JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementDeleteRouteReturningNoContent {
        /* 🧪 Zadanie 9: Zaimplementuj trase DELETE ZWRACAJACA `noContent()`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainAdvantagesAndDisadvantagesOfFunctionalVsAnnotatedStyle {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij ZALETY I WADY stylu funkcyjnego WOBEC adnotowanego. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_NestRoutesUsingPathPrefixInRouterFunction {
        /* 🧪 Zadanie 11: ZAGNIEZDZ trasy uzywajac WSPOLNEGO prefiksu sciezki (`.path(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementFilterOnRouterFunctionForRequestLogging {
        /* 🧪 Zadanie 12: Zaimplementuj `.filter(...)` NA `RouterFunction` DO logowania zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise13_HandleQueryParametersInServerRequest {
        /* 🧪 Zadanie 13: Obsluz parametry zapytania W `ServerRequest` (`request.queryParam(...)`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementValidationInsideHandlerFunctionReturning400OnFailure {
        /* 🧪 Zadanie 14: Zaimplementuj walidacje wewnatrz `HandlerFunction` ZWRACAJACA 400 PRZY niepowodzeniu. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ComposeMultipleRouterFunctionsUsingAndRoute {
        /* 🧪 Zadanie 15: POLACZ WIELE `RouterFunction` uzywajac `.andRoute(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementServerSentEventsRouteUsingFluxAndContentType {
        /* 🧪 Zadanie 16: Zaimplementuj trase SSE uzywajac `Flux` + `contentType(TEXT_EVENT_STREAM)`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildErrorHandlingWrapperAroundHandlerFunctionUsingOnErrorResume {
        /* 🧪 Zadanie 17: Zbuduj wrapper obslugi bledow WOKOL `HandlerFunction` uzywajac `onErrorResume`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementContentNegotiationInFunctionalEndpointForJsonAndXml {
        /* 🧪 Zadanie 18: Zaimplementuj negocjacje tresci (JSON/XML) W funkcyjnym endpoincie. */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildRouterFunctionOrganizedByFeatureInSeparateClasses {
        /* 🧪 Zadanie 19: Zorganizuj `RouterFunction` WEDLUG funkcjonalnosci (osobne klasy per zasob). */
        public static void main(String[] args) { }
    }

    static class Exercise20_CombineFunctionalEndpointsWithAnnotatedControllersInSameApp {
        /* 🧪 Zadanie 20: POLACZ funkcyjne endpointy Z adnotowanymi kontrolerami W TEJ SAMEJ aplikacji. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullFunctionalCrudApiWithNestedRoutesAndFilters {
        /* 🧪 Zadanie 21: Zbuduj PELNE funkcyjne API CRUD Z ZAGNIEZDZONYMI trasami I filtrami. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomHandlerFilterFunctionForAuthenticationCheck {
        /* 🧪 Zadanie 22: Zaimplementuj WLASNY `HandlerFilterFunction` DO sprawdzenia uwierzytelnienia. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildTestableHandlerFunctionsWithMockedServerRequest {
        /* 🧪 Zadanie 23: Powiaz z `_27_spring_test` - zbuduj TESTOWALNE `HandlerFunction` Z ZAMOCKOWANYM `ServerRequest`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementVersionedApiUsingSeparateRouterFunctionsPerVersion {
        /* 🧪 Zadanie 24: Powiaz z `_18_rest_api/Lesson14` - zaimplementuj WERSJONOWANE API uzywajac OSOBNYCH `RouterFunction` NA WERSJE. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildCompleteApiGatewayLikeRoutingUsingNestedRouterFunctions {
        /* 🧪 Zadanie 25: Powiaz z `_31_spring_cloud_microservices` - zbuduj routing PODOBNY DO API Gateway uzywajac ZAGNIEZDZONYCH `RouterFunction`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementRequestResponseLoggingMiddlewareUsingFilterChain {
        /* 🧪 Zadanie 26: Zaimplementuj middleware logowania zadan/odpowiedzi uzywajac lancucha filtrow. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildDynamicRoutingBasedOnRuntimeConfigurationOrFeatureFlags {
        /* 🧪 Zadanie 27: Zbuduj DYNAMICZNY routing OPARTY NA konfiguracji runtime/feature flags. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementGracefulFallbackRoutingForUnmatchedRequests {
        /* 🧪 Zadanie 28: Zaimplementuj LAGODNY fallback routing DLA NIEDOPASOWANYCH zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildPerformanceComparisonBetweenAnnotatedAndFunctionalStyleUnderLoad {
        /* 🧪 Zadanie 29: Zbuduj porownanie wydajnosci stylu adnotowanego I funkcyjnego POD obciazeniem. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignStyleGuideForWhenToUseFunctionalVsAnnotatedEndpointsInLargeCodebase {
        /* 🧪 Zadanie 30: Zaprojektuj STYLE GUIDE - KIEDY uzywac stylu funkcyjnego, A KIEDY adnotowanego W DUZYM projekcie. */
        public static void main(String[] args) { }
    }
}
