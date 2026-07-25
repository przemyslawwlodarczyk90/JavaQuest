package com.example.javaquest._31_spring_cloud_microservices.Lesson07_GatewayRoutingAndFilters;

public class _Exercises_Lesson07_GatewayRoutingAndFilters {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddStripPrefixFilterToExistingRoute {
        /* 🧪 Zadanie 1: Dodaj filtr `stripPrefix(n)` DO trasy (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhatStripPrefixDoes {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, CO ROBI `stripPrefix(n)`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddRequestHeaderFilterAndVerifyBackendReceivesIt {
        /* 🧪 Zadanie 3: Dodaj filtr `addRequestHeader` I zweryfikuj, ze backend GO ODBIERA. */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddResponseHeaderFilterAndVerifyClientReceivesIt {
        /* 🧪 Zadanie 4: Dodaj filtr `addResponseHeader` I zweryfikuj, ze klient GO ODBIERA. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainDifferenceBetweenBeforeAfterAndFilterBuilderMethods {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij ROZNICE MIEDZY `.before()`/`.after()` A `.filter(...)` W builderze tras. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ChainMultipleFiltersOnSameRoute {
        /* 🧪 Zadanie 6: Polacz LANCUCHOWO WIELE filtrow NA JEDNEJ trasie. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ObserveFilterExecutionOrder {
        /* 🧪 Zadanie 7: ZAOBSERWUJ kolejnosc wykonania filtrow (deklaracja = kolejnosc). */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseRewritePathFilterInsteadOfStripPrefix {
        /* 🧪 Zadanie 8: Uzyj `rewritePath(regex, zamiennik)` ZAMIAST `stripPrefix` - porownaj elastycznosc. */
        public static void main(String[] args) { }
    }

    static class Exercise09_RemoveRequestHeaderBeforeForwarding {
        /* 🧪 Zadanie 9: Usun (nie dodaj) naglowek Z zadania PRZED przekazaniem DALEJ (`removeRequestHeader`). */
        public static void main(String[] args) { }
    }

    static class Exercise10_SetCustomResponseStatusViaFilter {
        /* 🧪 Zadanie 10: Ustaw WLASNY status odpowiedzi PRZEZ filtr (`setStatus`). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_VersionApiUsingStripPrefixForV1AndV2 {
        /* 🧪 Zadanie 11: Zwersjonuj API - trasy `/api/v1/...` I `/api/v2/...` OBIE Z `stripPrefix`, prowadzace DO INNYCH backendow. */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddTraceIdHeaderGeneratedPerRequest {
        /* 🧪 Zadanie 12: Dodaj naglowek trace-id GENEROWANY DYNAMICZNIE DLA kazdego zadania (UUID). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementRewriteResponseHeaderFilter {
        /* 🧪 Zadanie 13: Uzyj `rewriteResponseHeader` DO modyfikacji istniejacego naglowka odpowiedzi. */
        public static void main(String[] args) { }
    }

    static class Exercise14_CombineStripPrefixWithAddRequestHeaderOnSameRoute {
        /* 🧪 Zadanie 14: Polacz `stripPrefix` Z `addRequestHeader` NA TEJ SAMEJ trasie (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainWhyPathRewritingHappensBeforeForwarding {
        /* 🧪 Zadanie 15: Bez terminala - wyjasnij, DLACZEGO przepisanie sciezki MUSI zajsc PRZED przekazaniem zadania. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementRequestSizeLimitFilter {
        /* 🧪 Zadanie 16: Zaimplementuj limit rozmiaru zadania (`requestSize`) I zweryfikuj odrzucenie zbyt duzego zadania. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareFilterChainWithServletFilterChainFrom07Servlets {
        /* 🧪 Zadanie 17: Powiaz Z `_07_servlets/Lesson14_Filters` - porownaj lancuch filtrow Gateway Z lancuchem `Filter` Servlet API. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementRedirectFilterForDeprecatedEndpoint {
        /* 🧪 Zadanie 18: Zaimplementuj przekierowanie (`redirectTo`) DLA przestarzalego (deprecated) endpointu. */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignRouteThatAddsAuthorizationHeaderFromInternalSecret {
        /* 🧪 Zadanie 19: Zaprojektuj trase, KTORA dodaje naglowek `Authorization` Z WEWNETRZNEGO sekretu (Gateway jako "zaufany klient" WEWNETRZNY). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhenToUseGatewayFilterVsBackendMiddleware {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, KIEDY logika NALEZY DO filtra Gateway, A KIEDY DO middleware'u backendu. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomHandlerFilterFunctionFromScratch {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNA `HandlerFilterFunction` OD ZERA (BEZ gotowych `FilterFunctions`). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRequestLoggingFilterMeasuringLatencyPerRoute {
        /* 🧪 Zadanie 22: Zaimplementuj filtr logujacy CZAS przetwarzania KAZDEGO zadania NA danej trasie. */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignFilterOrderingStrategyForCrossCuttingConcerns {
        /* 🧪 Zadanie 23: Zaprojektuj strategie KOLEJNOSCI filtrow DLA cross-cutting concerns (auth PRZED logowaniem, itp.). */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementBodyModifyingFilterUsingModifyRequestBody {
        /* 🧪 Zadanie 24: Zaimplementuj filtr modyfikujacy CIALO zadania (`modifyRequestBody`). */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareGatewayLevelFilteringWithServiceMeshSidecarFiltering {
        /* 🧪 Zadanie 25: Zbadaj I porownaj filtrowanie NA POZIOMIE Gateway Z filtrowaniem service mesh (sidecar proxy). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementConditionalFilterBasedOnRequestPredicate {
        /* 🧪 Zadanie 26: Zaimplementuj filtr stosowany WARUNKOWO (na podstawie `RequestPredicate`). */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignApiVersioningStrategyWithMultipleActiveVersions {
        /* 🧪 Zadanie 27: Zaprojektuj strategie wersjonowania API Z WIELOMA JEDNOCZESNIE aktywnymi wersjami PRZEZ Gateway. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRateLimitingFilterUsingBucket4j {
        /* 🧪 Zadanie 28: Zaimplementuj rate limiting NA POZIOMIE Gateway (`Bucket4jFilterFunctions`). */
        public static void main(String[] args) { }
    }

    static class Exercise29_MeasureFilterChainOverheadWithManyChainedFilters {
        /* 🧪 Zadanie 29: Zmierz narzut WYDLUZONEGO lancucha filtrow (5+ filtrow NA JEDNEJ trasie). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionFilterChainForRealApiGateway {
        /* 🧪 Zadanie 30: Zaprojektuj REALISTYCZNY lancuch filtrow DLA produkcyjnego API Gateway (auth, rate-limit, tracing, CORS). */
        public static void main(String[] args) { }
    }
}
