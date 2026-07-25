package com.example.javaquest._30_spring_messaging_and_async.Lesson09_SpringAmqpBasics;

public class _Exercises_Lesson09_SpringAmqpBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_DeclareQueueExchangeAndBindingAsSpringBeans {
        /* 🧪 Zadanie 1: Zadeklaruj `Queue`/`Exchange`/`Binding` JAKO beany Springa. */
        public static void main(String[] args) { }
    }

    static class Exercise02_SendMessageUsingRabbitTemplateConvertAndSend {
        /* 🧪 Zadanie 2: Wyslij wiadomosc uzywajac `rabbitTemplate.convertAndSend(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateRabbitListenerMethodForSpecificQueue {
        /* 🧪 Zadanie 3: Stworz metode `@RabbitListener` DLA KONKRETNEJ kolejki. */
        public static void main(String[] args) { }
    }

    static class Exercise04_UseFanoutExchangeWithMultipleQueues {
        /* 🧪 Zadanie 4: Uzyj exchange typu FANOUT Z WIELOMA kolejkami. */
        public static void main(String[] args) { }
    }

    static class Exercise05_UseTopicExchangeWithWildcardRoutingKeyPattern {
        /* 🧪 Zadanie 5: Uzyj exchange typu TOPIC Z WZORCEM routing key (gwiazdka/hash). */
        public static void main(String[] args) { }
    }

    static class Exercise06_HandleConnectionFailureGracefullyWhenBrokerUnavailable {
        /* 🧪 Zadanie 6: Obsluz LAGODNIE BLAD polaczenia, GDY broker jest niedostepny. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareRabbitTemplateWithJmsTemplateFromLesson07 {
        /* 🧪 Zadanie 7: Powiaz z `Lesson07` - porownaj `RabbitTemplate` Z `JmsTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ConfigureRabbitConnectionFactoryWithCustomHostAndPort {
        /* 🧪 Zadanie 8: Skonfiguruj `ConnectionFactory` RabbitMQ Z WLASNYM hostem I portem. */
        public static void main(String[] args) { }
    }

    static class Exercise09_SendCustomObjectAsMessageUsingJsonMessageConverter {
        /* 🧪 Zadanie 9: Wyslij WLASNY obiekt JAKO wiadomosc uzywajac `Jackson2JsonMessageConverter`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhatEnableRabbitAnnotationActivates {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, CO WLACZA adnotacja `@EnableRabbit`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConfigureRabbitListenerContainerFactoryWithConcurrency {
        /* 🧪 Zadanie 11: Skonfiguruj `SimpleRabbitListenerContainerFactory` Z WSPOLBIEZNOSCIA. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementManualAcknowledgmentModeForReliableProcessing {
        /* 🧪 Zadanie 12: Zaimplementuj RECZNY tryb potwierdzania DLA NIEZAWODNEGO przetwarzania. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureDeadLetterExchangeForFailedMessages {
        /* 🧪 Zadanie 13: Powiaz z `Lesson13` - skonfiguruj "dead letter exchange" DLA nieudanych wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementRequestReplyPatternUsingRabbitTemplateConvertSendAndReceive {
        /* 🧪 Zadanie 14: Zaimplementuj wzorzec request-reply uzywajac `convertSendAndReceive`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_SetMessagePriorityAndObserveDeliveryOrder {
        /* 🧪 Zadanie 15: Ustaw PRIORYTET wiadomosci I zaobserwuj kolejnosc dostarczenia. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConfigureRabbitAdminToDeclareTopologyProgrammatically {
        /* 🧪 Zadanie 16: Skonfiguruj `RabbitAdmin` DO deklarowania topologii PROGRAMOWO. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementErrorHandlerForRabbitListenerContainer {
        /* 🧪 Zadanie 17: Zaimplementuj `ErrorHandler` DLA kontenera `@RabbitListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildOrderNotificationServiceUsingRabbitTemplateAndListener {
        /* 🧪 Zadanie 18: Zbuduj serwis powiadomien O zamowieniach uzywajac `RabbitTemplate`+`@RabbitListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConfigurePublisherConfirmsForReliableMessageDelivery {
        /* 🧪 Zadanie 19: Skonfiguruj "publisher confirms" DLA NIEZAWODNEGO dostarczania wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestRabbitListenerBehaviorUsingTestcontainersRabbitMqModule {
        /* 🧪 Zadanie 20: Powiaz z `_26_integration_testing` - przetestuj `@RabbitListener` uzywajac modulu RabbitMQ Testcontainers. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullOrderProcessingPipelineUsingRabbitTemplateAndMultipleListeners {
        /* 🧪 Zadanie 21: Zbuduj PELNY pipeline przetwarzania zamowien uzywajac `RabbitTemplate` I WIELU sluchaczy. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementRetryWithExponentialBackoffForFailingRabbitListener {
        /* 🧪 Zadanie 22: Zaimplementuj retry Z wykladniczym opoznieniem DLA zawodzacego `@RabbitListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildResilientMessageProcessingWithDeadLetterAndAlerting {
        /* 🧪 Zadanie 23: Zbuduj ODPORNE przetwarzanie wiadomosci Z "dead letter" I alertowaniem. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementMessageDrivenSagaOrchestrationUsingRabbitMq {
        /* 🧪 Zadanie 24: Powiaz z `_31_spring_cloud_microservices/Lesson14` - zaimplementuj orkiestracje Saga STEROWANA RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildComprehensiveMonitoringForRabbitListenerThroughputAndErrors {
        /* 🧪 Zadanie 25: Zbuduj KOMPLEKSOWY monitoring przepustowosci I bledow `@RabbitListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementIdempotentMessageProcessingPreventingDuplicateOrderCreation {
        /* 🧪 Zadanie 26: Zaimplementuj IDEMPOTENTNE przetwarzanie ZAPOBIEGAJACE PODWOJNEMU utworzeniu zamowienia. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildLoadTestMeasuringRabbitListenerThroughputUnderHighMessageVolume {
        /* 🧪 Zadanie 27: Zbuduj TEST OBCIAZENIOWY mierzacy przepustowosc `@RabbitListener` PRZY DUZYM wolumenie wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDynamicRoutingBasedOnMessageContentUsingHeadersExchange {
        /* 🧪 Zadanie 28: Zaimplementuj DYNAMICZNY routing OPARTY NA tresci wiadomosci uzywajac exchange HEADERS. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullMicroserviceCommunicationLayerUsingRabbitMqForAsyncMessaging {
        /* 🧪 Zadanie 29: Powiaz z `_31_spring_cloud_microservices` - zbuduj PELNA warstwe komunikacji mikroserwisow uzywajac RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMessagingArchitectureComparingJmsAmqpAndKafkaForNewSystem {
        /* 🧪 Zadanie 30: Powiaz z `Lesson10-11` - zaprojektuj PELNA architekture messagingu porownujaca JMS/AMQP/Kafka DLA NOWEGO systemu. */
        public static void main(String[] args) { }
    }
}
