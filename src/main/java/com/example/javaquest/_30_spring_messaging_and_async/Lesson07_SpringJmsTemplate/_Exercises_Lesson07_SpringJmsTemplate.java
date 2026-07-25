package com.example.javaquest._30_spring_messaging_and_async.Lesson07_SpringJmsTemplate;

public class _Exercises_Lesson07_SpringJmsTemplate {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SendMessageUsingJmsTemplateConvertAndSend {
        /* 🧪 Zadanie 1: Wyslij wiadomosc uzywajac `jmsTemplate.convertAndSend(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateJmsListenerMethodForSpecificDestination {
        /* 🧪 Zadanie 2: Stworz metode `@JmsListener` DLA KONKRETNEGO celu. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReceiveMessageSynchronouslyUsingJmsTemplateReceiveAndConvert {
        /* 🧪 Zadanie 3: Odbierz wiadomosc SYNCHRONICZNIE uzywajac `receiveAndConvert()`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareLinesOfCodeBetweenRawJmsAndJmsTemplate {
        /* 🧪 Zadanie 4: Powiaz z `Lesson06` - porownaj ILOSC linii kodu MIEDZY surowym JMS A `JmsTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise05_SendCustomObjectAsMessageUsingJacksonConversion {
        /* 🧪 Zadanie 5: Wyslij WLASNY obiekt JAKO wiadomosc uzywajac konwersji (Jackson/`MessageConverter`). */
        public static void main(String[] args) { }
    }

    static class Exercise06_ConfigureJmsListenerToTopicInsteadOfQueue {
        /* 🧪 Zadanie 6: Skonfiguruj `@JmsListener` DO tematu (Topic) ZAMIAST kolejki. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyArtemisWasChosenOverActiveMqClassicForThisLesson {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO Artemis zostal wybrany ZAMIAST ActiveMQ Classic DLA tej lekcji. */
        public static void main(String[] args) { }
    }

    static class Exercise08_SendMultipleMessagesInLoopUsingJmsTemplate {
        /* 🧪 Zadanie 8: Wyslij WIELE wiadomosci W petli uzywajac `JmsTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_HandleExceptionThrownFromJmsListenerMethod {
        /* 🧪 Zadanie 9: Obsluz wyjatek Z metody `@JmsListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhatEnableJmsAnnotationActivates {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, CO WLACZA adnotacja `@EnableJms`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ConfigureJmsListenerContainerFactoryWithConcurrency {
        /* 🧪 Zadanie 11: Skonfiguruj `DefaultJmsListenerContainerFactory` Z WSPOLBIEZNOSCIA. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementRequestReplyPatternUsingJmsTemplateConvertSendAndReceive {
        /* 🧪 Zadanie 12: Zaimplementuj wzorzec request-reply uzywajac `convertSendAndReceive`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_SendMessageWithCustomHeadersUsingMessagePostProcessor {
        /* 🧪 Zadanie 13: Wyslij wiadomosc Z WLASNYMI naglowkami uzywajac `MessagePostProcessor`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_ConfigureErrorHandlerForJmsListenerContainer {
        /* 🧪 Zadanie 14: Skonfiguruj `ErrorHandler` DLA kontenera `@JmsListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementTransactionalJmsListenerRollingBackOnProcessingError {
        /* 🧪 Zadanie 15: Zaimplementuj TRANSAKCYJNY `@JmsListener` Z ROLLBACK PRZY bledzie przetwarzania. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareJmsTemplateWithRabbitTemplateFromLesson09Conceptually {
        /* 🧪 Zadanie 16: Powiaz z `Lesson09` - porownaj (KONCEPCYJNIE) `JmsTemplate` Z `RabbitTemplate`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementSelectorBasedJmsListenerFilteringMessages {
        /* 🧪 Zadanie 17: Zaimplementuj `@JmsListener(selector = ...)` FILTRUJACY wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildOrderNotificationServiceUsingJmsTemplateAndListener {
        /* 🧪 Zadanie 18: Zbuduj serwis powiadomien O zamowieniach uzywajac `JmsTemplate`+`@JmsListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ConfigureJmsTemplateDefaultDestinationToAvoidRepeatingDestinationName {
        /* 🧪 Zadanie 19: Skonfiguruj DOMYSLNY cel `JmsTemplate` (`setDefaultDestinationName`). */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestJmsListenerBehaviorUsingEmbeddedBrokerInIntegrationTest {
        /* 🧪 Zadanie 20: Powiaz z `_26_integration_testing` - przetestuj zachowanie `@JmsListener` uzywajac embedded brokera W tescie integracyjnym. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullOrderProcessingPipelineUsingJmsTemplateAndMultipleListeners {
        /* 🧪 Zadanie 21: Zbuduj PELNY pipeline przetwarzania zamowien uzywajac `JmsTemplate` I WIELU sluchaczy. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDeadLetterHandlingForJmsListenerUsingRedeliveryPolicy {
        /* 🧪 Zadanie 22: Powiaz z `Lesson13` - zaimplementuj obsluge "dead letter" DLA `@JmsListener` uzywajac polityki ponownego dostarczenia. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildResilientMessageProcessingWithRetryAndExponentialBackoff {
        /* 🧪 Zadanie 23: Zbuduj ODPORNE przetwarzanie wiadomosci Z retry I wykladniczym opoznieniem. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementMessageDrivenSagaOrchestrationUsingJms {
        /* 🧪 Zadanie 24: Powiaz z `_31_spring_cloud_microservices/Lesson14` - zaimplementuj orkiestracje Saga STEROWANA wiadomosciami. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildComprehensiveMonitoringForJmsListenerThroughputAndErrors {
        /* 🧪 Zadanie 25: Zbuduj KOMPLEKSOWY monitoring przepustowosci I bledow `@JmsListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementIdempotentMessageProcessingPreventingDuplicateOrderCreation {
        /* 🧪 Zadanie 26: Zaimplementuj IDEMPOTENTNE przetwarzanie ZAPOBIEGAJACE PODWOJNEMU utworzeniu zamowienia. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildLoadTestMeasuringJmsListenerThroughputUnderHighMessageVolume {
        /* 🧪 Zadanie 27: Zbuduj TEST OBCIAZENIOWY mierzacy przepustowosc `@JmsListener` PRZY DUZYM wolumenie wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementDynamicDestinationRoutingBasedOnMessageContent {
        /* 🧪 Zadanie 28: Zaimplementuj DYNAMICZNY routing celu OPARTY NA tresci wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullMicroserviceCommunicationLayerUsingJmsForAsyncMessaging {
        /* 🧪 Zadanie 29: Powiaz z `_31_spring_cloud_microservices` - zbuduj PELNA warstwe komunikacji mikroserwisow uzywajac JMS. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMessagingArchitectureDecidingBetweenJmsAmqpAndKafkaForDifferentUseCases {
        /* 🧪 Zadanie 30: Powiaz z `Lesson08-11` - zaprojektuj PELNA architekture messagingu DECYDUJAC MIEDZY JMS/AMQP/Kafka DLA ROZNYCH przypadkow uzycia. */
        public static void main(String[] args) { }
    }
}
