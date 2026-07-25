package com.example.javaquest._31_spring_cloud_microservices.Lesson19_MicroservicesCapstone;

public class _Exercises_Lesson19_MicroservicesCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RunTheCapstoneDemoAndIdentifyEachMechanism {
        /* 🧪 Zadanie 1: Uruchom kapszton I dla KAZDEGO scenariusza wskaz, KTORY mechanizm (Eureka/LoadBalancer/Gateway/JWT/tracing) go obsluguje. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddThirdOrdersServiceInstance {
        /* 🧪 Zadanie 2: Dodaj TRZECIA instancje 'orders-service' I zweryfikuj, ze Load Balancer JA UWZGLEDNIA. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ChangeGatewayRouteToPointToSecondInstance {
        /* 🧪 Zadanie 3: Zmien trase Gateway, zeby WSKAZYWALA NA DRUGA instancje (port2 zamiast port1). */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddFourthSecurityScenarioWithValidTokenButWrongRole {
        /* 🧪 Zadanie 4: Dodaj CZWARTY scenariusz bezpieczenstwa - WAZNY token, ALE ZLA rola (403, nie 401). */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyGatewayUsesFixedAddressNotLoadBalancerInThisCapstone {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO Gateway W tym kapsztonie uzywa STALEGO adresu, NIE Load Balancera (swiadome uproszczenie). */
        public static void main(String[] args) { }
    }

    static class Exercise06_MeasureTotalStartupTimeOfAllFiveContexts {
        /* 🧪 Zadanie 6: Zmierz LACZNY czas startu WSZYSTKICH 5 kontekstow (server+2 backendy+gateway+client). */
        public static void main(String[] args) { }
    }

    static class Exercise07_AddLoggingOfTraceIdToEveryPrintedLineInCapstone {
        /* 🧪 Zadanie 7: Dodaj `traceId` DO KAZDEJ wypisywanej linii W kapsztonie (spojnosc Z Lesson18). */
        public static void main(String[] args) { }
    }

    static class Exercise08_QueryActuatorMetricsForOrdersRequestsCounter {
        /* 🧪 Zadanie 8: Odpytaj `/actuator/metrics/orders.requests` NA jednej Z instancji backendu. */
        public static void main(String[] args) { }
    }

    static class Exercise09_SimulateOneInstanceCrashingMidDemo {
        /* 🧪 Zadanie 9: Zasymuluj AWARIE JEDNEJ instancji W POLOWIE demo (`context.close()`) I ZAOBSERWUJ Load Balancer. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAllSpringCloudMechanismsUsedInThisCapstone {
        /* 🧪 Zadanie 10: Wypisz WSZYSTKIE mechanizmy Spring Cloud UZYTE W tym kapsztonie Z JEDNYM zdaniem opisu KAZDEGO. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddResilience4jCircuitBreakerToOrdersController {
        /* 🧪 Zadanie 11: Dodaj `@CircuitBreaker` (Lesson10) DO `OrdersController` DLA symulowanego wywolania ZALEZNOSCI. */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddConfigServerServingSharedPropertyToAllInstances {
        /* 🧪 Zadanie 12: Dodaj Config Server (Lesson04-05) serwujacy WSPOLNA wlasciwosc DLA obu instancji backendu. */
        public static void main(String[] args) { }
    }

    static class Exercise13_MakeGatewayLoadBalancerAwareUsingLbFilterFunction {
        /* 🧪 Zadanie 13: Przerob Gateway, zeby uzywal `lb()` (Load Balancer-aware routing) ZAMIAST stalego adresu - zweryfikuj CZY dziala niezawodnie. */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddZipkinExportIfDockerAvailable {
        /* 🧪 Zadanie 14: Jesli MASZ Dockera - dodaj eksport DO Zipkina (Lesson12) I obejrzyj PELNE drzewo wywolan. */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddSecondDownstreamServicePaymentsCalledFromOrders {
        /* 🧪 Zadanie 15: Dodaj DRUGI serwis (payments-service) WOLANY Z orders-service (propagacja JWT DALEJ, jak Lesson16). */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementGracefulShutdownOrderForAllContexts {
        /* 🧪 Zadanie 16: Zaimplementuj PRZEMYSLANA kolejnosc zamykania WSZYSTKICH kontekstow (klient->gateway->backendy->serwer). */
        public static void main(String[] args) { }
    }

    static class Exercise17_AddRetryAroundLoadBalancedCallForTransientFailures {
        /* 🧪 Zadanie 17: Dodaj `@Retry` (Lesson10) WOKOL wywolania load-balanced DLA chwilowych bledow. */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureAndCompareLatencyDirectVsThroughGateway {
        /* 🧪 Zadanie 18: Zmierz I porownaj latencje BEZPOSREDNIEGO wywolania A PRZEZ Gateway. */
        public static void main(String[] args) { }
    }

    static class Exercise19_AddHealthCheckAggregationAcrossAllServices {
        /* 🧪 Zadanie 19: Dodaj AGREGACJE statusu zdrowia (health) WSZYSTKICH serwisow W JEDNYM miejscu. */
        public static void main(String[] args) { }
    }

    static class Exercise20_RefactorCapstoneToUseFeignClientInsteadOfRestTemplate {
        /* 🧪 Zadanie 20: Przerob kapszton, zeby uzywal Feign (Lesson13) ZAMIAST `RestTemplate`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ExtractCapstoneIntoSeparateProcessesInsteadOfOneJvm {
        /* 🧪 Zadanie 21: Zaprojektuj (na papierze), JAK ten kapszton wygladalby jako OSOBNE procesy/kontenery, NIE JEDEN JVM. */
        public static void main(String[] args) { }
    }

    static class Exercise22_AddDockerComposeFileRunningAllServicesAsContainers {
        /* 🧪 Zadanie 22: Napisz `docker-compose.yml` (Lesson17) uruchamiajacy WSZYSTKIE serwisy jako OSOBNE kontenery. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSagaAcrossOrdersAndPaymentsFromExercise15 {
        /* 🧪 Zadanie 23: Zaimplementuj sage (Lesson14) MIEDZY orders-service I payments-service Z Zadania 15. */
        public static void main(String[] args) { }
    }

    static class Exercise24_AddEventDrivenNotificationServiceUsingSpringCloudStream {
        /* 🧪 Zadanie 24: Dodaj serwis powiadomien SUBSKRYBUJACY zdarzenia PRZEZ Spring Cloud Stream (Lesson15). */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignFullProductionArchitectureDiagramForThisCapstone {
        /* 🧪 Zadanie 25: Zaprojektuj PELNY diagram architektury PRODUKCYJNEJ DLA tego kapsztonu (WSZYSTKIE 18 poprzednich lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementChaosTestKillingRandomInstanceDuringLoad {
        /* 🧪 Zadanie 26: Zaimplementuj test chaos - losowo "zabij" instancje W TRAKCIE obciazenia I zmierz WPLYW. */
        public static void main(String[] args) { }
    }

    static class Exercise27_AddDistributedRateLimitingAcrossGatewayInstances {
        /* 🧪 Zadanie 27: Zaprojektuj rate limiting ROZPROSZONY MIEDZY WIELOMA instancjami Gateway (wspolny licznik, np. Redis). */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignBlueGreenDeploymentForOrdersServiceUsingGateway {
        /* 🧪 Zadanie 28: Zaprojektuj wdrozenie blue-green DLA orders-service WYKORZYSTUJACE Gateway (Lesson06-07). */
        public static void main(String[] args) { }
    }

    static class Exercise29_WriteRetrospectiveComparingThisChapterToWhatMicroservicesActuallyCost {
        /* 🧪 Zadanie 29: Napisz retrospektywe LACZACA ten rozdzial Z `_17_architecture/Lesson19_WhenMicroservicesMakeSense` - czy WARTO? */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignYourOwnMicroservicesSystemUsingEveryLessonInThisChapter {
        /* 🧪 Zadanie 30: Zaprojektuj WLASNY system mikroserwisow (INNY NIZ zamowienia), WYKORZYSTUJACY KAZDA lekcje TEGO rozdzialu. */
        public static void main(String[] args) { }
    }
}
