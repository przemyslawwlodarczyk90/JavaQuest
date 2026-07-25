package com.example.javaquest._31_spring_cloud_microservices.Lesson15_EventDrivenMicroservicesWithSpringCloudStream;

public class _Exercises_Lesson15_EventDrivenMicroservicesWithSpringCloudStream {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DefineConsumerFunctionBoundToDestination {
        /* 🧪 Zadanie 1: Zdefiniuj `Consumer<String>` ZWIAZANY Z destination (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhatBinderMeansInSpringCloudStream {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, CZYM jest "binder" W Spring Cloud Stream. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ConfigureSpringCloudFunctionDefinitionProperty {
        /* 🧪 Zadanie 3: Skonfiguruj `spring.cloud.function.definition` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise04_SendMessageUsingStreamBridge {
        /* 🧪 Zadanie 4: Wyslij komunikat uzywajac `StreamBridge` (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise05_CheckIfRabbitMqIsReachableBeforeStartingContext {
        /* 🧪 Zadanie 5: Sprawdz OSIAGALNOSC RabbitMQ (socket connect) PRZED startem kontekstu (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise06_DefineFunctionTransformingInputToOutput {
        /* 🧪 Zadanie 6: Zdefiniuj `Function<String,String>` TRANSFORMUJACY wejscie NA wyjscie. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainNamingConventionInOut0Suffix {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij konwencje nazewnictwa `-in-0`/`-out-0`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareSpringCloudStreamWithDirectRabbitTemplateFrom30 {
        /* 🧪 Zadanie 8: Powiaz Z `_30_spring_messaging_and_async/Lesson09` - porownaj Spring Cloud Stream Z bezposrednim `RabbitTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_DefineSupplierPeriodicallyEmittingMessages {
        /* 🧪 Zadanie 9: Zdefiniuj `Supplier<String>` OKRESOWO emitujacy komunikaty. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyBusinessCodeDoesNotReferenceRabbitOrKafkaApiDirectly {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO kod biznesowy NIE ODWOLUJE SIE bezposrednio DO API RabbitMQ/Kafka. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ChainTwoFunctionsIntoOnePipeline {
        /* 🧪 Zadanie 11: Polacz DWIE funkcje W JEDEN potok (`spring.cloud.function.definition=fn1|fn2`). */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConfigureCustomGroupForCompetingConsumers {
        /* 🧪 Zadanie 12: Skonfiguruj WLASNA "grupe" (`group`) DLA konkurujacych konsumentow. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureContentTypeForJsonMessages {
        /* 🧪 Zadanie 13: Skonfiguruj `content-type` DLA komunikatow JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementConsumerHandlingDeserializedDtoInsteadOfString {
        /* 🧪 Zadanie 14: Zaimplementuj `Consumer<TwojDto>` obslugujacy ZDESERIALIZOWANE DTO (NIE `String`). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConfigurePartitionKeyExpressionForKafkaBinder {
        /* 🧪 Zadanie 15: Skonfiguruj wyrazenie klucza partycji DLA bindera Kafka (jesli dostepny). */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareRabbitBinderWithKafkaBinderConfiguration {
        /* 🧪 Zadanie 16: Bez terminala - porownaj konfiguracje bindera RabbitMQ Z binderem Kafka. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementErrorHandlingWithDeadLetterQueueViaStream {
        /* 🧪 Zadanie 17: Zaimplementuj obsluge bledow Z dead letter queue PRZEZ Spring Cloud Stream (powiazanie Z `_30_spring_messaging_and_async/Lesson13`). */
        public static void main(String[] args) { }
    }

    static class Exercise18_TestFunctionInIsolationWithoutRealBroker {
        /* 🧪 Zadanie 18: Przetestuj funkcje W IZOLACJI (bez prawdziwego brokera) - wywolaj bezposrednio jako `Function`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConfigureMultipleDestinationsForSameFunction {
        /* 🧪 Zadanie 19: Skonfiguruj WIELE destination DLA TEJ SAMEJ funkcji. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyThisPatternFitsEventDrivenMicroservicesArchitecture {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, DLACZEGO ten wzorzec PASUJE DO architektury event-driven mikroserwisow. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomMessageConverterForBinaryFormat {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY konwerter komunikatow DLA formatu BINARNEGO (np. Avro/Protobuf). */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignSagaOrchestrationOverSpringCloudStream {
        /* 🧪 Zadanie 22: Zaprojektuj orkiestracje sagi (Lesson14) PRZEZ Spring Cloud Stream. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementExactlyOnceProcessingSemantics {
        /* 🧪 Zadanie 23: Zbadaj I zaimplementuj (koncepcyjnie) semantyke "dokladnie raz" (exactly-once processing). */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompareFunctionalModelWithAnnotationBasedRabbitListener {
        /* 🧪 Zadanie 24: Porownaj model funkcyjny Spring Cloud Stream Z adnotacyjnym `@RabbitListener` (`_30_spring_messaging_and_async/Lesson09`). */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignMultiBinderSetupUsingBothRabbitAndKafka {
        /* 🧪 Zadanie 25: Zaprojektuj konfiguracje Z WIELOMA binderami naraz (Rabbit I Kafka W TYM SAMYM serwisie). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementBackpressureAwareConsumer {
        /* 🧪 Zadanie 26: Zaimplementuj konsumenta SWIADOMEGO backpressure (kontrola tempa przetwarzania). */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignSchemaEvolutionStrategyForEventPayloads {
        /* 🧪 Zadanie 27: Zaprojektuj strategie ewolucji schematu komunikatow (dodawanie pol BEZ lamania konsumentow). */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementTestBinderForUnitTestingFunctionsWithoutBroker {
        /* 🧪 Zadanie 28: Uzyj test bindera (`spring-cloud-stream-test-binder`) DO testowania funkcji BEZ prawdziwego brokera. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignMonitoringStrategyForConsumerLagAcrossServices {
        /* 🧪 Zadanie 29: Zaprojektuj monitoring "consumer lag" (opoznienia przetwarzania) W CALYM systemie. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionEventDrivenArchitectureChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" architektury event-driven (DLQ/idempotencja/monitoring/schema evolution). */
        public static void main(String[] args) { }
    }
}
