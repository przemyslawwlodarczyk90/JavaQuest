package com.example.javaquest._30_spring_messaging_and_async.Lesson10_KafkaConcepts;

public class _Exercises_Lesson10_KafkaConcepts {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyKafkaIsCalledDistributedLogNotQueue {
        /* 🧪 Zadanie 1: Bez terminala - wyjasnij, DLACZEGO Kafka jest nazywana "rozproszonym logiem", NIE kolejka. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainRelationshipBetweenTopicAndPartitions {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij ZWIAZEK MIEDZY topic A partycjami. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhatOffsetRepresents {
        /* 🧪 Zadanie 3: Bez terminala - wyjasnij, CO reprezentuje offset. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhyMessagesWithSameKeyGoToSamePartition {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij, DLACZEGO wiadomosci Z TYM SAMYM kluczem TRAFIAJA DO TEJ SAMEJ partycji. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainHowConsumerGroupsDistributePartitions {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, JAK grupy konsumentow ROZDZIELAJA partycje. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareKafkaRetentionWithJmsMessageDeletion {
        /* 🧪 Zadanie 6: Powiaz z `Lesson06` - porownaj retencje Kafki Z USUWANIEM wiadomosci W JMS. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhatHappensWhenMoreConsumersThanPartitions {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, CO SIE DZIEJE, GDY konsumentow jest WIECEJ NIZ partycji. */
        public static void main(String[] args) { }
    }

    static class Exercise08_DesignPartitioningKeyStrategyForOrderEvents {
        /* 🧪 Zadanie 8: Zaprojektuj strategie klucza partycjonowania DLA zdarzen zamowien. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExplainReplayCapabilityUniqueToKafka {
        /* 🧪 Zadanie 9: Bez terminala - wyjasnij MOZLIWOSC "replay" UNIKALNA DLA Kafki. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListUseCasesWhereKafkaOutperformsTraditionalMessageQueues {
        /* 🧪 Zadanie 10: Bez terminala - wypisz przypadki uzycia, GDZIE Kafka PRZEWYZSZA tradycyjne kolejki. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DesignMultiPartitionTopicForHighThroughputOrderProcessing {
        /* 🧪 Zadanie 11: Zaprojektuj topic Z WIELOMA partycjami DLA przetwarzania zamowien O WYSOKIEJ przepustowosci. */
        public static void main(String[] args) { }
    }

    static class Exercise12_DesignMultipleConsumerGroupsForSameTopicDifferentPurposes {
        /* 🧪 Zadanie 12: Zaprojektuj WIELE grup konsumentow DLA TEGO SAMEGO topicu, ROZNE CELE. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExplainLeaderAndReplicaConceptForPartitionFaultTolerance {
        /* 🧪 Zadanie 13: Bez terminala - wyjasnij POJECIE lidera I repliki DLA odpornosci partycji NA awarie. */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareAtLeastOnceAtMostOnceAndExactlyOnceSemantics {
        /* 🧪 Zadanie 14: Porownaj semantyke "at-least-once"/"at-most-once"/"exactly-once". */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignRebalancingStrategyWhenConsumerJoinsOrLeavesGroup {
        /* 🧪 Zadanie 15: Zaprojektuj strategie rebalansowania, GDY konsument DOLACZA/OPUSZCZA grupe. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplainCompactedTopicsForKeyValueStateStorage {
        /* 🧪 Zadanie 16: Bez terminala - wyjasnij "compacted topics" DO przechowywania stanu klucz-wartosc. */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignSchemaEvolutionStrategyForKafkaEventsUsingAvroOrJsonSchema {
        /* 🧪 Zadanie 17: Zaprojektuj strategie EWOLUCJI schematu zdarzen Kafki uzywajac Avro/JSON Schema. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ExplainDifferenceBetweenKafkaAndTraditionalPubSubModel {
        /* 🧪 Zadanie 18: Bez terminala - wyjasnij ROZNICE MIEDZY Kafka A TRADYCYJNYM modelem pub-sub. */
        public static void main(String[] args) { }
    }

    static class Exercise19_DesignPartitionCountForExpectedThroughputAndConsumerParallelism {
        /* 🧪 Zadanie 19: Zaprojektuj LICZBE partycji DLA oczekiwanej przepustowosci I rownoleglosci konsumentow. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareKafkaStreamsWithSimpleConsumerProducerPattern {
        /* 🧪 Zadanie 20: Porownaj Kafka Streams Z PROSTYM wzorcem producent-konsument. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DesignFullEventSourcingArchitectureUsingKafkaAsSourceOfTruth {
        /* 🧪 Zadanie 21: Zaprojektuj PELNA architekture event sourcing uzywajac Kafki JAKO ZRODLA prawdy. */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignMultiDatacenterKafkaReplicationStrategy {
        /* 🧪 Zadanie 22: Zaprojektuj strategie replikacji Kafki MIEDZY centrami danych. */
        public static void main(String[] args) { }
    }

    static class Exercise23_DesignExactlyOnceProcessingPipelineUsingKafkaTransactions {
        /* 🧪 Zadanie 23: Zaprojektuj pipeline przetwarzania "exactly-once" uzywajac transakcji Kafki. */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignCapacityPlanningModelForKafkaClusterBasedOnThroughputRequirements {
        /* 🧪 Zadanie 24: Zaprojektuj MODEL planowania pojemnosci klastra Kafki NA PODSTAWIE wymagan przepustowosci. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignDisasterRecoveryStrategyForKafkaClusterFailure {
        /* 🧪 Zadanie 25: Zaprojektuj strategie odzyskiwania PO awarii klastra Kafki. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignComprehensiveMonitoringForConsumerLagAndPartitionSkew {
        /* 🧪 Zadanie 26: Zaprojektuj KOMPLEKSOWY monitoring OPOZNIENIA konsumentow I NIEROWNOMIERNOSCI partycji. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignMultiTenantKafkaTopicNamingAndIsolationStrategy {
        /* 🧪 Zadanie 27: Zaprojektuj strategie nazewnictwa I IZOLACJI topicow Kafki DLA WIELODZIERZAWCZOSCI. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignStreamProcessingArchitectureUsingKafkaStreamsForRealTimeAnalytics {
        /* 🧪 Zadanie 28: Zaprojektuj architekture przetwarzania strumieniowego uzywajac Kafka Streams DLA analityki W czasie rzeczywistym. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignSecurityModelForKafkaWithSaslAndAclAuthorization {
        /* 🧪 Zadanie 29: Zaprojektuj model bezpieczenstwa Kafki Z SASL I autoryzacja ACL. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullEventDrivenPlatformArchitectureUsingKafkaAsBackbone {
        /* 🧪 Zadanie 30: Powiaz z `_31_spring_cloud_microservices` - zaprojektuj PELNA platforme STEROWANA zdarzeniami Z Kafka JAKO "krgoslupem". */
        public static void main(String[] args) { }
    }
}
