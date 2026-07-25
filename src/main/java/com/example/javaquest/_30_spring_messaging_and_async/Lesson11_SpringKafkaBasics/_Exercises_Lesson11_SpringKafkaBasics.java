package com.example.javaquest._30_spring_messaging_and_async.Lesson11_SpringKafkaBasics;

public class _Exercises_Lesson11_SpringKafkaBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SendMessageUsingKafkaTemplateSend {
        /* 🧪 Zadanie 1: Wyslij wiadomosc uzywajac `kafkaTemplate.send(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateKafkaListenerMethodForSpecificTopic {
        /* 🧪 Zadanie 2: Stworz metode `@KafkaListener` DLA KONKRETNEGO topicu. */
        public static void main(String[] args) { }
    }

    static class Exercise03_SendMessageWithKeyAndObserveConsistentPartitioning {
        /* 🧪 Zadanie 3: Wyslij wiadomosc Z kluczem I zaobserwuj SPOJNE partycjonowanie. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ConfigureMultipleConsumersInSameGroupId {
        /* 🧪 Zadanie 4: Skonfiguruj WIELU konsumentow W TEJ SAMEJ `groupId`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConfigureAutoOffsetResetPolicy {
        /* 🧪 Zadanie 5: Skonfiguruj polityke `auto-offset-reset`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_HandleConnectionFailureGracefullyWhenKafkaUnavailable {
        /* 🧪 Zadanie 6: Obsluz LAGODNIE BLAD polaczenia, GDY Kafka jest niedostepna. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareKafkaTemplateWithJmsTemplateAndRabbitTemplate {
        /* 🧪 Zadanie 7: Powiaz z `Lesson07`/`Lesson09` - porownaj `KafkaTemplate` Z `JmsTemplate`/`RabbitTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_SendCustomObjectAsMessageUsingJsonSerializer {
        /* 🧪 Zadanie 8: Wyslij WLASNY obiekt JAKO wiadomosc uzywajac `JsonSerializer`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConfigureMultipleTopicsInSingleKafkaListener {
        /* 🧪 Zadanie 9: Skonfiguruj WIELE topicow W JEDNYM `@KafkaListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyGroupIdIsRequiredForKafkaListener {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO `groupId` jest WYMAGANY DLA `@KafkaListener`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConfigureConcurrencyOnKafkaListenerForParallelConsumption {
        /* 🧪 Zadanie 11: Skonfiguruj `concurrency` NA `@KafkaListener` DLA ROWNOLEGLEGO konsumowania. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementManualAcknowledgmentUsingAckModeManual {
        /* 🧪 Zadanie 12: Zaimplementuj RECZNE potwierdzanie uzywajac `AckMode.MANUAL`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureErrorHandlerWithDeadLetterTopicRecovery {
        /* 🧪 Zadanie 13: Powiaz z `Lesson13` - skonfiguruj `ErrorHandler` Z odzyskiwaniem DO "dead letter topic". */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReadPartitionAndOffsetFromConsumerRecordMetadata {
        /* 🧪 Zadanie 14: Odczytaj partycje I offset Z METADANYCH `ConsumerRecord`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCustomPartitionerForCustomRoutingLogic {
        /* 🧪 Zadanie 15: Zaimplementuj WLASNY `Partitioner` DLA WLASNEJ logiki routingu. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConfigureProducerAcksForDurabilityGuarantees {
        /* 🧪 Zadanie 16: Skonfiguruj `acks` producenta DLA GWARANCJI trwalosci. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementSeekToBeginningToReplayAllMessagesFromTopic {
        /* 🧪 Zadanie 17: Zaimplementuj "seek to beginning" DO odtworzenia WSZYSTKICH wiadomosci Z topicu. */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildOrderNotificationServiceUsingKafkaTemplateAndListener {
        /* 🧪 Zadanie 18: Zbuduj serwis powiadomien O zamowieniach uzywajac `KafkaTemplate`+`@KafkaListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConfigureIdempotentProducerToPreventDuplicates {
        /* 🧪 Zadanie 19: Skonfiguruj IDEMPOTENTNEGO producenta ZAPOBIEGAJACEGO duplikatom. */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestKafkaListenerBehaviorUsingTestcontainersKafkaModule {
        /* 🧪 Zadanie 20: Powiaz z `_26_integration_testing` - przetestuj `@KafkaListener` uzywajac modulu Kafka Testcontainers. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullOrderProcessingPipelineUsingKafkaTemplateAndMultipleListeners {
        /* 🧪 Zadanie 21: Zbuduj PELNY pipeline przetwarzania zamowien uzywajac `KafkaTemplate` I WIELU sluchaczy. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementExactlyOnceProcessingUsingKafkaTransactions {
        /* 🧪 Zadanie 22: Powiaz z `Lesson10` - zaimplementuj przetwarzanie "exactly-once" uzywajac transakcji Kafki. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildResilientConsumerWithRetryAndDeadLetterTopicHandling {
        /* 🧪 Zadanie 23: Zbuduj ODPORNEGO konsumenta Z retry I obsluga "dead letter topic". */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementEventDrivenSagaOrchestrationUsingKafka {
        /* 🧪 Zadanie 24: Powiaz z `_31_spring_cloud_microservices/Lesson14` - zaimplementuj orkiestracje Saga STEROWANA Kafka. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildComprehensiveMonitoringForConsumerLagAndThroughput {
        /* 🧪 Zadanie 25: Zbuduj KOMPLEKSOWY monitoring OPOZNIENIA konsumentow I przepustowosci. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementStreamProcessingPipelineUsingKafkaStreamsDsl {
        /* 🧪 Zadanie 26: Zaimplementuj pipeline przetwarzania strumieniowego uzywajac Kafka Streams DSL. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildLoadTestMeasuringKafkaProducerConsumerThroughput {
        /* 🧪 Zadanie 27: Zbuduj TEST OBCIAZENIOWY mierzacy przepustowosc producenta/konsumenta Kafki. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDynamicTopicRoutingBasedOnMessageContent {
        /* 🧪 Zadanie 28: Zaimplementuj DYNAMICZNY routing topicow OPARTY NA tresci wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullMicroserviceCommunicationLayerUsingKafkaForAsyncMessaging {
        /* 🧪 Zadanie 29: Powiaz z `_31_spring_cloud_microservices` - zbuduj PELNA warstwe komunikacji mikroserwisow uzywajac Kafki. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMessagingArchitectureFinalizingChoiceAmongJmsAmqpKafka {
        /* 🧪 Zadanie 30: Powiaz z `Lesson12-15` - zaprojektuj PELNA architekture messagingu FINALIZUJAC wybor MIEDZY JMS/AMQP/Kafka. */
        public static void main(String[] args) { }
    }
}
