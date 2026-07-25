package com.example.javaquest._31_spring_cloud_microservices.Lesson06_ApiGatewayIntro;

public class _Exercises_Lesson06_ApiGatewayIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateBackendAndGatewayRoutingOneRequestPath {
        /* 🧪 Zadanie 1: Stworz backend + Gateway trasujacy JEDNA sciezke DO niego (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainProblemGatewaySolves {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij PROBLEM, KTORY rozwiazuje Gateway. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhyThisChapterUsesGatewayMvcNotReactiveGateway {
        /* 🧪 Zadanie 3: Bez terminala - wyjasnij, DLACZEGO ten rozdzial uzywa 'gateway-mvc', NIE klasycznego 'gateway'. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhySpringCloudGatewayMvcEnabledIsGloballyFalse {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij, DLACZEGO `spring.cloud.gateway.mvc.enabled=false` jest GLOBALNIE W tym projekcie. */
        public static void main(String[] args) { }
    }

    static class Exercise05_RouteTwoDifferentPathsToTwoDifferentBackends {
        /* 🧪 Zadanie 5: Stworz DWA backendy I Gateway trasujacy DWIE rozne sciezki DO nich. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareDirectCallWithCallThroughGateway {
        /* 🧪 Zadanie 6: Porownaj bezposrednie wywolanie backendu Z wywolaniem PRZEZ Gateway (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainRouterFunctionsRouteGetSyntax {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij skladnie `RouterFunctions.route().GET(sciezka, handler).build()`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhatHttpHandlerFunctionDoes {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, CO ROBI `HandlerFunctions.http(url)`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_RouteBasedOnHttpMethodNotJustPath {
        /* 🧪 Zadanie 9: Stworz trase rozrozniajaca METODE HTTP (GET vs POST), NIE TYLKO sciezke. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyClientDoesNotNeedToKnowBackendAddress {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO klient NIE MUSI znac adresu backendu PRZY uzyciu Gateway. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RouteBasedOnHeaderValue {
        /* 🧪 Zadanie 11: Stworz trase, KTORA kieruje NA PODSTAWIE wartosci naglowka (`GatewayRequestPredicates.header`). */
        public static void main(String[] args) { }
    }

    static class Exercise12_RouteBasedOnQueryParameter {
        /* 🧪 Zadanie 12: Stworz trase, KTORA kieruje NA PODSTAWIE parametru zapytania (`query`). */
        public static void main(String[] args) { }
    }

    static class Exercise13_HandleBackendNotAvailableGracefully {
        /* 🧪 Zadanie 13: Obsluz sytuacje, GDY backend NIE ODPOWIADA (zamknij jego kontekst PRZED wywolaniem PRZEZ Gateway). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExplainWhatHappensWhenNoRouteMatches {
        /* 🧪 Zadanie 14: Bez terminala - wyjasnij, CO SIE DZIEJE, GDY ZADNA trasa NIE PASUJE DO zadania. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementFallbackRouteForUnmatchedRequests {
        /* 🧪 Zadanie 15: Zaimplementuj trase "catch-all" DLA niedopasowanych zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareGatewayMvcWithManualReverseProxyImplementation {
        /* 🧪 Zadanie 16: Porownaj Gateway MVC Z RECZNA implementacja reverse proxy (`HttpClient` przekazujacy zadania). */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureLatencyOverheadOfGoingThroughGateway {
        /* 🧪 Zadanie 17: Zmierz narzut czasowy PRZEJSCIA PRZEZ Gateway WZGLEDEM bezposredniego wywolania. */
        public static void main(String[] args) { }
    }

    static class Exercise18_RouteMultiplePathsToSameBackendWithDifferentPrefixes {
        /* 🧪 Zadanie 18: Stworz WIELE tras DO TEGO SAMEGO backendu Z ROZNYMI prefiksami sciezek. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ExplainWhyGatewayIsBuiltOnFunctionalRouterApiNotAnnotations {
        /* 🧪 Zadanie 19: Bez terminala - wyjasnij, DLACZEGO Gateway MVC uzywa FUNKCYJNEGO API (`RouterFunction`), NIE adnotacji `@GetMapping`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareGatewayResponseWithDirectResponseByteForByte {
        /* 🧪 Zadanie 20: Porownaj odpowiedz PRZEZ Gateway Z odpowiedzia bezposrednia BAJT PO BAJCIE (naglowki/cialo). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementGatewayRoutingToThreeDifferentMicroservices {
        /* 🧪 Zadanie 21: Zaimplementuj Gateway trasujacy DO TRZECH roznych mikroserwisow (rozne sciezki bazowe). */
        public static void main(String[] args) { }
    }

    static class Exercise22_CombineGatewayWithEurekaForDynamicRouting {
        /* 🧪 Zadanie 22: Powiaz Z Lesson03/08 - opisz (koncepcyjnie), jak Gateway MOZE trasowac DYNAMICZNIE PRZEZ Eureke (zamiast stalego adresu). */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignGatewayHighAvailabilityDeployment {
        /* 🧪 Zadanie 23: Zaprojektuj wdrozenie Gateway W trybie wysokiej dostepnosci (WIELE instancji + load balancer PRZED nimi). */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCustomRequestPredicateForCanaryRouting {
        /* 🧪 Zadanie 24: Zaimplementuj WLASNY `RequestPredicate` DO trasowania canary (np. procent ruchu DO nowej wersji). */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareGatewayMvcWithNginxIngressControllerConceptually {
        /* 🧪 Zadanie 25: Zbadaj I porownaj Gateway MVC Z Nginx Ingress Controller (Kubernetes). */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignGatewayAsAggregationLayerForMultipleBackendCalls {
        /* 🧪 Zadanie 26: Zaprojektuj Gateway jako warstwe AGREGUJACA WIELE wywolan backendowych W JEDNA odpowiedz. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ExplainSecurityRisksOfExposingGatewayWithoutAuthentication {
        /* 🧪 Zadanie 27: Bez terminala - omow ryzyka WYSTAWIENIA Gateway BEZ uwierzytelniania (powiazanie Z Lesson16). */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementRouteLevelTimeoutConfiguration {
        /* 🧪 Zadanie 28: Zaimplementuj konfiguracje timeoutu NA POZIOMIE pojedynczej trasy. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignBlueGreenDeploymentUsingGatewayRouting {
        /* 🧪 Zadanie 29: Zaprojektuj wdrozenie blue-green WYKORZYSTUJACE trasowanie Gateway. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionGatewayArchitectureChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" DLA wdrozenia Gateway (HA, bezpieczenstwo, timeouty, monitoring). */
        public static void main(String[] args) { }
    }
}
