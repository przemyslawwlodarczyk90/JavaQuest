package com.example.javaquest._31_spring_cloud_microservices.Lesson08_ClientSideLoadBalancing;

public class _Exercises_Lesson08_ClientSideLoadBalancing {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MarkRestTemplateWithLoadBalancedAnnotation {
        /* 🧪 Zadanie 1: Oznacz `RestTemplate` adnotacja `@LoadBalanced` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhatLoadBalancedAnnotationDoes {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, CO ROBI `@LoadBalanced`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CallServiceByLogicalNameInsteadOfAddress {
        /* 🧪 Zadanie 3: Wywolaj serwis PO LOGICZNEJ nazwie (`http://orders-service/...`) ZAMIAST adresu. */
        public static void main(String[] args) { }
    }

    static class Exercise04_RunTwoInstancesOfSameServiceAndObserveRoundRobin {
        /* 🧪 Zadanie 4: Uruchom DWIE instancje TEGO SAMEGO serwisu I ZAOBSERWUJ round-robin. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyClientNeedsDiscoveryClientToUseLoadBalancer {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO Load Balancer WYMAGA `DiscoveryClient` (Eureka). */
        public static void main(String[] args) { }
    }

    static class Exercise06_UseHttpServletRequestToIdentifyRespondingInstance {
        /* 🧪 Zadanie 6: Uzyj `HttpServletRequest.getLocalPort()` W kontrolerze, zeby ZIDENTYFIKOWAC odpowiadajaca instancje (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise07_CountResponseDistributionAcrossInstances {
        /* 🧪 Zadanie 7: Policz rozklad odpowiedzi WEDLUG instancji PO WIELU wywolaniach. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainDifferenceBetweenRestTemplateAndLoadBalancedRestClient {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij ROZNICE MIEDZY `@LoadBalanced RestTemplate` A `@LoadBalanced RestClient.Builder`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ObserveWhatHappensWhenOnlyOneInstanceIsRegistered {
        /* 🧪 Zadanie 9: ZAOBSERWUJ zachowanie, GDY zarejestrowana jest TYLKO JEDNA instancja. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainRelationshipBetweenLoadBalancerAndEurekaFromLesson03 {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij RELACJE MIEDZY Load Balancerem A Eureka Z Lesson03. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RunThreeInstancesAndVerifyEvenDistribution {
        /* 🧪 Zadanie 11: Uruchom TRZY instancje I zweryfikuj (W PRZYBLIZENIU) rowny rozklad zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise12_SimulateInstanceGoingDownAndObserveLoadBalancerAdapting {
        /* 🧪 Zadanie 12: Zasymuluj ZAMKNIECIE JEDNEJ instancji (`context.close()`) I ZAOBSERWUJ, ze Load Balancer PRZESTAJE jej wysylac zadania. */
        public static void main(String[] args) { }
    }

    static class Exercise13_UseLoadBalancedRestClientInsteadOfRestTemplate {
        /* 🧪 Zadanie 13: Powtorz demo UZYWAJAC `@LoadBalanced RestClient.Builder` ZAMIAST `RestTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementCustomLoadBalancerChoosingSpecificInstance {
        /* 🧪 Zadanie 14: Zaimplementuj WLASNY `ReactorServiceInstanceLoadBalancer` (np. "zawsze pierwsza instancja"). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainZoneAwareLoadBalancingStrategy {
        /* 🧪 Zadanie 15: Bez terminala - wyjasnij strategie load balancingu SWIADOMA stref dostepnosci. */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureResponseTimeVariationAcrossInstances {
        /* 🧪 Zadanie 16: Zmierz roznice czasu odpowiedzi MIEDZY instancjami (jedna sztucznie SPOWOLNIONA). */
        public static void main(String[] args) { }
    }

    static class Exercise17_CombineLoadBalancerWithGatewayFromLesson06 {
        /* 🧪 Zadanie 17: Powiaz Z Lesson06 - opisz (koncepcyjnie), jak Gateway MOZE UZYWAC Load Balancera zamiast stalego adresu backendu. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainWhatHappensDuringInstanceRegistrationRaceCondition {
        /* 🧪 Zadanie 18: Bez terminala - wyjasnij, CO SIE DZIEJE, GDY klient wola serwis ZANIM instancja zdazyla sie ZAREJESTROWAC. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementRetryOnLoadBalancerFailure {
        /* 🧪 Zadanie 19: Zaimplementuj ponowienie (retry) PRZY niepowodzeniu wywolania load-balanced. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareClientSideLoadBalancingWithServerSideFromLesson02 {
        /* 🧪 Zadanie 20: Powiaz Z Lesson02 - porownaj client-side load balancing Z server-side (Kubernetes Service). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementWeightedLoadBalancingStrategy {
        /* 🧪 Zadanie 21: Zaimplementuj WAZONY load balancing (jedna instancja dostaje WIECEJ ruchu). */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignCircuitBreakerIntegrationWithLoadBalancer {
        /* 🧪 Zadanie 22: Zaprojektuj (na papierze) integracje circuit breakera (Lesson10) Z Load Balancerem - wykluczanie NIEZDROWYCH instancji. */
        public static void main(String[] args) { }
    }

    static class Exercise23_CompareRoundRobinWithRandomAndLeastConnectionsStrategies {
        /* 🧪 Zadanie 23: Zbadaj I porownaj round-robin Z 'random' I 'least connections' strategiami load balancingu. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementStickySessionAwareLoadBalancing {
        /* 🧪 Zadanie 24: Zaimplementuj load balancing SWIADOMY "sticky session" (ten sam klient -> ta sama instancja). */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignLoadBalancerCachingStrategyForServiceInstanceList {
        /* 🧪 Zadanie 25: Zaprojektuj strategie cache'owania LISTY instancji PO STRONIE Load Balancera. */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureImpactOfCacheStalenessOnLoadBalancingDecisions {
        /* 🧪 Zadanie 26: Zmierz wplyw "zastarzalosci" cache'u NA decyzje load balancingu PO usunieciu instancji. */
        public static void main(String[] args) { }
    }

    static class Exercise27_CompareSpringCloudLoadBalancerWithRibbonHistorically {
        /* 🧪 Zadanie 27: Zbadaj I opisz historyczny Netflix Ribbon (poprzednik Spring Cloud LoadBalancer). */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementHealthCheckAwareServiceInstanceFiltering {
        /* 🧪 Zadanie 28: Zaimplementuj filtrowanie instancji SWIADOME statusu zdrowia (BEZ czekania NA ewikcje Eureki). */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignLoadTestComparingWithAndWithoutLoadBalancing {
        /* 🧪 Zadanie 29: Zaprojektuj test obciazeniowy porownujacy przepustowosc Z Load Balancerem I BEZ niego (1 instancja). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionLoadBalancingArchitectureChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" DLA architektury load balancingu (health-check, retry, circuit breaker, monitoring). */
        public static void main(String[] args) { }
    }
}
