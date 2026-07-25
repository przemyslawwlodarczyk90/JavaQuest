package com.example.javaquest._30_spring_messaging_and_async.Lesson08_RabbitMqConcepts;

public class _Exercises_Lesson08_RabbitMqConcepts {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatAmqpStandsForAndWhyItIsProtocolNotJustApi {
        /* 🧪 Zadanie 1: Bez terminala - wyjasnij, CO oznacza AMQP I DLACZEGO TO protokol, NIE tylko API. */
        public static void main(String[] args) { }
    }

    static class Exercise02_DescribeDirectExchangeRoutingBehavior {
        /* 🧪 Zadanie 2: Bez terminala - opisz zachowanie routingu exchange typu DIRECT. */
        public static void main(String[] args) { }
    }

    static class Exercise03_DescribeFanoutExchangeRoutingBehavior {
        /* 🧪 Zadanie 3: Bez terminala - opisz zachowanie routingu exchange typu FANOUT. */
        public static void main(String[] args) { }
    }

    static class Exercise04_DescribeTopicExchangeRoutingBehaviorWithWildcardExample {
        /* 🧪 Zadanie 4: Bez terminala - opisz TOPIC exchange Z PRZYKLADEM wzorca Z gwiazdka/hashem. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhatBindingMeansInAmqpModel {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, CZYM jest "binding" W modelu AMQP. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareAmqpModelWithJmsModelFromLesson06 {
        /* 🧪 Zadanie 6: Powiaz z `Lesson06` - porownaj model AMQP Z modelem JMS. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyRabbitMqCanTalkToNonJavaClients {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO RabbitMQ MOZE rozmawiac Z klientami NIE-Java. */
        public static void main(String[] args) { }
    }

    static class Exercise08_DesignRoutingKeyNamingSchemeForOrderEvents {
        /* 🧪 Zadanie 8: Zaprojektuj SCHEMAT nazewnictwa routing key DLA zdarzen zamowien. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainWhenToChooseFanoutOverTopicExchange {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij, KIEDY wybrac FANOUT NAD TOPIC exchange. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListRealWorldUseCasesForEachExchangeType {
        /* 🧪 Zadanie 10: Bez terminala - wypisz REALNE przypadki uzycia DLA KAZDEGO typu exchange. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignMultiQueueTopicExchangeScenarioForOrderNotifications {
        /* 🧪 Zadanie 11: Zaprojektuj scenariusz WIELU kolejek NA exchange typu TOPIC DLA powiadomien O zamowieniach. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ExplainDurableVsTransientQueuesAndExchanges {
        /* 🧪 Zadanie 12: Bez terminala - wyjasnij TRWALE (durable) A ULOTNE kolejki/exchange. */
        public static void main(String[] args) { }
    }

    static class Exercise13_DesignDeadLetterExchangeStrategyForFailedMessages {
        /* 🧪 Zadanie 13: Powiaz z `Lesson13` - zaprojektuj strategie "dead letter exchange" DLA nieudanych wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareAmqpAcknowledgmentModesWithJmsAcknowledgmentModes {
        /* 🧪 Zadanie 14: Porownaj tryby potwierdzania AMQP Z trybami potwierdzania JMS. */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignExchangeToExchangeBindingScenario {
        /* 🧪 Zadanie 15: Zaprojektuj scenariusz bindowania exchange-DO-exchange. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainPrefetchCountAndItsImpactOnConsumerThroughput {
        /* 🧪 Zadanie 16: Bez terminala - wyjasnij "prefetch count" I JEGO wplyw NA przepustowosc konsumenta. */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignRoutingSchemeForMultiTenantSaasApplication {
        /* 🧪 Zadanie 17: Zaprojektuj SCHEMAT routingu DLA aplikacji SaaS WIELODZIERZAWCZEJ. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainDifferenceBetweenMessageTtlAndQueueTtl {
        /* 🧪 Zadanie 18: Bez terminala - wyjasnij ROZNICE MIEDZY TTL wiadomosci A TTL kolejki. */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignPublisherConfirmsStrategyForReliableMessaging {
        /* 🧪 Zadanie 19: Zaprojektuj strategie "publisher confirms" DLA NIEZAWODNEGO messagingu. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareRabbitMqClusteringWithSimpleSingleNodeSetup {
        /* 🧪 Zadanie 20: Porownaj klastrowanie RabbitMQ Z PROSTA konfiguracja jednowezlowa. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullEventDrivenMicroservicesArchitectureUsingRabbitMqTopicExchanges {
        /* 🧪 Zadanie 21: Powiaz z `_31_spring_cloud_microservices` - zaprojektuj PELNA architekture mikroserwisow STEROWANA zdarzeniami uzywajac exchange TOPIC. */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignHighAvailabilityRabbitMqClusterWithMirroredQueues {
        /* 🧪 Zadanie 22: Zaprojektuj klaster RabbitMQ WYSOKIEJ dostepnosci Z LUSTRZANYMI kolejkami. */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignSagaOrchestrationUsingRabbitMqForDistributedTransactions {
        /* 🧪 Zadanie 23: Powiaz z `_31_spring_cloud_microservices/Lesson14` - zaprojektuj orkiestracje Saga uzywajac RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignRateLimitingStrategyUsingQueueLengthLimitsAndBackpressure {
        /* 🧪 Zadanie 24: Zaprojektuj strategie ograniczania tempa uzywajac LIMITOW dlugosci kolejki I backpressure. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignMultiRegionMessageReplicationStrategyForGlobalApplication {
        /* 🧪 Zadanie 25: Zaprojektuj strategie replikacji wiadomosci MIEDZY REGIONAMI DLA aplikacji globalnej. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignComprehensiveMonitoringStrategyForRabbitMqClusterHealth {
        /* 🧪 Zadanie 26: Zaprojektuj KOMPLEKSOWA strategie monitorowania zdrowia klastra RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignMessageVersioningStrategyForEvolvingEventSchemas {
        /* 🧪 Zadanie 27: Zaprojektuj strategie wersjonowania wiadomosci DLA EWOLUUJACYCH schematow zdarzen. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignSecurityModelForRabbitMqWithVirtualHostsAndPermissions {
        /* 🧪 Zadanie 28: Zaprojektuj model bezpieczenstwa RabbitMQ Z wirtualnymi hostami I uprawnieniami. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignDisasterRecoveryPlanForRabbitMqBrokerFailure {
        /* 🧪 Zadanie 29: Zaprojektuj plan odzyskiwania PO awarii brokera RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMessagingInfrastructureArchitectureForEnterpriseEventDrivenSystem {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA architekture infrastruktury messagingu DLA systemu enterprise STEROWANEGO zdarzeniami. */
        public static void main(String[] args) { }
    }
}
