package com.example.javaquest._30_spring_messaging_and_async.Lesson06_JmsIntro;

public class _Exercises_Lesson06_JmsIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StartEmbeddedActiveMqBrokerUsingBrokerService {
        /* 🧪 Zadanie 1: Uruchom embedded ActiveMQ Broker uzywajac `BrokerService`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_SendTextMessageToQueue {
        /* 🧪 Zadanie 2: Wyslij `TextMessage` DO kolejki. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReceiveMessageFromQueueUsingMessageConsumer {
        /* 🧪 Zadanie 3: Odbierz wiadomosc Z kolejki uzywajac `MessageConsumer`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_PublishMessageToTopicWithTwoSubscribers {
        /* 🧪 Zadanie 4: Opublikuj wiadomosc W temacie Z 2 subskrybentami. */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareQueueBehaviorWithTopicBehaviorForSameMessage {
        /* 🧪 Zadanie 5: Porownaj zachowanie Queue Z Topic DLA TEJ SAMEJ wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise06_SendMultipleMessagesToQueueAndConsumeInOrder {
        /* 🧪 Zadanie 6: Wyslij WIELE wiadomosci DO kolejki I ODBIERZ JE W kolejnosci FIFO. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainDifferenceBetweenQueueAndTopicWithRealWorldAnalogy {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij ROZNICE Queue A Topic UZYWAJAC analogii Z zycia (np. bilet W kolejce vs radio). */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseObjectMessageToSendSerializableJavaObject {
        /* 🧪 Zadanie 8: Powiaz z `_04_io/Lesson16` - uzyj `ObjectMessage` DO wyslania obiektu Java (`Serializable`). */
        public static void main(String[] args) { }
    }

    static class Exercise09_SetMessageTimeoutOnReceiveCallAndObserveNullOnTimeout {
        /* 🧪 Zadanie 9: Ustaw timeout NA wywolaniu `receive(...)` I zaobserwuj `null` PRZY braku wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyJmsIsSpecificationNotImplementation {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO JMS TO specyfikacja, NIE implementacja. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementMessageListenerForAsynchronousMessageConsumption {
        /* 🧪 Zadanie 11: Zaimplementuj `MessageListener` DLA asynchronicznego odbioru wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise12_UseMessageSelectorToFilterMessagesByProperty {
        /* 🧪 Zadanie 12: Uzyj selektora wiadomosci (`MessageSelector`) DO filtrowania PO wlasciwosci. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDurableSubscriberForTopicSurvivingDisconnection {
        /* 🧪 Zadanie 13: Zaimplementuj TRWALEGO subskrybenta tematu PRZETRWAJACEGO rozlaczenie. */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareAutoAcknowledgeWithClientAcknowledgeMode {
        /* 🧪 Zadanie 14: Porownaj `AUTO_ACKNOWLEDGE` Z `CLIENT_ACKNOWLEDGE` trybem potwierdzania. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementTransactedSessionRollingBackOnError {
        /* 🧪 Zadanie 15: Zaimplementuj TRANSAKCYJNA sesje Z ROLLBACK PRZY bledzie. */
        public static void main(String[] args) { }
    }

    static class Exercise16_SetMessagePropertiesAndUseThemForRouting {
        /* 🧪 Zadanie 16: Ustaw WLASCIWOSCI wiadomosci I uzyj ich DO routingu. */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildRequestReplyPatternUsingTemporaryQueue {
        /* 🧪 Zadanie 17: Zbuduj wzorzec request-reply uzywajac TYMCZASOWEJ kolejki. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementMultipleConsumersCompetingForMessagesFromSameQueue {
        /* 🧪 Zadanie 18: Zaimplementuj WIELU konsumentow KONKURUJACYCH O wiadomosci Z TEJ SAMEJ kolejki. */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureMessageThroughputForQueueVsTopicWithMultipleConsumers {
        /* 🧪 Zadanie 19: Zmierz przepustowosc wiadomosci DLA Queue A Topic Z WIELOMA konsumentami. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementMessagePriorityAffectingDeliveryOrder {
        /* 🧪 Zadanie 20: Zaimplementuj PRIORYTET wiadomosci WPLYWAJACY NA kolejnosc dostarczenia. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullOrderProcessingSystemUsingMultipleQueuesForDifferentStages {
        /* 🧪 Zadanie 21: Zbuduj PELNY system przetwarzania zamowien uzywajac WIELU kolejek DLA ROZNYCH etapow. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDeadLetterQueueHandlingForPoisonMessages {
        /* 🧪 Zadanie 22: Powiaz z `Lesson13` - zaimplementuj obsluge "dead letter queue" DLA "zatrutych" wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildLoadBalancedConsumerGroupUsingCompetingConsumersPattern {
        /* 🧪 Zadanie 23: Zbuduj grupe konsumentow Z ROWNOWAZENIEM obciazenia uzywajac wzorca "konkurujacych konsumentow". */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementReliableMessagingWithPersistentDeliveryAndTransactions {
        /* 🧪 Zadanie 24: Zaimplementuj NIEZAWODNY messaging Z TRWALYM dostarczaniem I transakcjami. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildFullPublishSubscribeNotificationSystemWithMultipleTopics {
        /* 🧪 Zadanie 25: Zbuduj PELNY system powiadomien publish-subscribe Z WIELOMA tematami. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementMessageBridgeBetweenTwoDifferentBrokerInstances {
        /* 🧪 Zadanie 26: Zaimplementuj most wiadomosci MIEDZY 2 ROZNYMI instancjami brokera. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildComprehensiveMonitoringForQueueDepthAndConsumerLag {
        /* 🧪 Zadanie 27: Zbuduj KOMPLEKSOWY monitoring GLEBOKOSCI kolejki I opoznienia konsumentow. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementIdempotentMessageProcessingUsingMessageIdDeduplication {
        /* 🧪 Zadanie 28: Zaimplementuj IDEMPOTENTNE przetwarzanie wiadomosci uzywajac deduplikacji PO `JMSMessageID`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFailoverConfigurationForBrokerHighAvailability {
        /* 🧪 Zadanie 29: Zbuduj (koncepcyjnie) konfiguracje failover DLA WYSOKIEJ dostepnosci brokera. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullMessagingArchitectureChoosingBetweenJmsAndAmqpForNewSystem {
        /* 🧪 Zadanie 30: Powiaz z `Lesson08-09` - zaprojektuj PELNA architekture messagingu WYBIERAJAC MIEDZY JMS A AMQP DLA NOWEGO systemu. */
        public static void main(String[] args) { }
    }
}
