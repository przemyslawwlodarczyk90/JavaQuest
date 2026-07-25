package com.example.javaquest._30_spring_messaging_and_async.Lesson13_ErrorHandlingAndDeadLetterQueues;

public class _Exercises_Lesson13_ErrorHandlingAndDeadLetterQueues {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ImplementSimpleRetryLoopWithMaxAttempts {
        /* 🧪 Zadanie 1: Zaimplementuj PROSTA petle retry Z LIMITEM prob. */
        public static void main(String[] args) { }
    }

    static class Exercise02_MoveMessageToDlqAfterExhaustedRetries {
        /* 🧪 Zadanie 2: PRZENIES wiadomosc DO DLQ PO wyczerpaniu prob. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainWhyMessageWithoutAcknowledgeCanBlockQueue {
        /* 🧪 Zadanie 3: Bez terminala - wyjasnij, DLACZEGO wiadomosc BEZ acknowledge MOZE ZABLOKOWAC kolejke. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainWhyImmediateAcknowledgeOnErrorLosesMessages {
        /* 🧪 Zadanie 4: Bez terminala - wyjasnij, DLACZEGO NATYCHMIASTOWE acknowledge PRZY bledzie GUBI wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConfigureRabbitMqDeadLetterExchangeOnQueue {
        /* 🧪 Zadanie 5: Powiaz z `Lesson09` - skonfiguruj `x-dead-letter-exchange` NA kolejce RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ConfigureKafkaDeadLetterPublishingRecoverer {
        /* 🧪 Zadanie 6: Powiaz z `Lesson11` - skonfiguruj `DeadLetterPublishingRecoverer` DLA Kafki. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareRetryWithFixedDelayVsExponentialBackoff {
        /* 🧪 Zadanie 7: Porownaj retry Z STALYM opoznieniem A wykladniczym opoznieniem. */
        public static void main(String[] args) { }
    }

    static class Exercise08_DistinguishBetweenTransientAndPermanentErrors {
        /* 🧪 Zadanie 8: Bez terminala - rozroznij BLEDY PRZEJSCIOWE OD TRWALYCH. */
        public static void main(String[] args) { }
    }

    static class Exercise09_BuildSimpleDlqInspectorPrintingFailedMessages {
        /* 🧪 Zadanie 9: Zbuduj PROSTY "inspektor" DLQ WYPISUJACY nieudane wiadomosci. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyPermanentErrorsShouldSkipRetryAndGoDirectlyToDlq {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO TRWALE bledy POWINNY POMINAC retry I ISC BEZPOSREDNIO DO DLQ. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementExponentialBackoffRetryWithJitter {
        /* 🧪 Zadanie 11: Zaimplementuj retry Z wykladniczym opoznieniem I "jitter". */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildRetryClassifierRoutingDifferentExceptionsDifferently {
        /* 🧪 Zadanie 12: Zbuduj klasyfikator retry KIERUJACY ROZNE wyjatki INACZEJ (retry vs DLQ natychmiast). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementDlqReprocessingToolManuallyRetryingFailedMessages {
        /* 🧪 Zadanie 13: Zaimplementuj narzedzie ponownego przetwarzania DLQ (reczne PONOWIENIE nieudanych wiadomosci). */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildAlertingMechanismWhenDlqSizeExceedsThreshold {
        /* 🧪 Zadanie 14: Zbuduj mechanizm alertowania, GDY rozmiar DLQ PRZEKROCZY prog. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementPoisonMessageDetectionUsingMessageHeaderCounter {
        /* 🧪 Zadanie 15: Zaimplementuj WYKRYWANIE "zatrutej" wiadomosci uzywajac licznika W naglowku. */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareRetryStrategiesForDifferentFailureTypesNetworkVsValidation {
        /* 🧪 Zadanie 16: Porownaj strategie retry DLA ROZNYCH typow bledow (siec VS walidacja). */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildCircuitBreakerPreventingRetryStormDuringDownstreamOutage {
        /* 🧪 Zadanie 17: Zbuduj circuit breaker ZAPOBIEGAJACY "burzy retry" PODCZAS awarii podrzednego serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementIdempotentConsumerPreventingDuplicateProcessingAfterRetry {
        /* 🧪 Zadanie 18: Zaimplementuj IDEMPOTENTNEGO konsumenta ZAPOBIEGAJACEGO PODWOJNEMU przetworzeniu PO retry. */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildMetricsCollectorTrackingRetryCountsAndDlqRate {
        /* 🧪 Zadanie 19: Zbuduj kolektor metryk SLEDZACY LICZBE retry I WSKAZNIK trafien DO DLQ. */
        public static void main(String[] args) { }
    }

    static class Exercise20_TestErrorHandlingAndDlqBehaviorUsingJUnit5AndMockito {
        /* 🧪 Zadanie 20: Powiaz z `_25_unit_testing` - przetestuj zachowanie obslugi bledow I DLQ uzywajac JUnit5+Mockito. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullResilientMessageProcessingPipelineWithRetryDlqAndAlerting {
        /* 🧪 Zadanie 21: Zbuduj PELNY, ODPORNY pipeline przetwarzania wiadomosci Z retry+DLQ+alertowaniem. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementAutomaticDlqReprocessingWithBackoffAndMaxAttemptsLimit {
        /* 🧪 Zadanie 22: Zaimplementuj AUTOMATYCZNE ponowne przetwarzanie DLQ Z opoznieniem I LIMITEM prob. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildComprehensiveErrorTaxonomyAndRoutingStrategyForLargeSystem {
        /* 🧪 Zadanie 23: Zbuduj KOMPLEKSOWA TAKSONOMIE bledow I strategie routingu DLA DUZEGO systemu. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementSagaCompensationTriggeredByDlqMessageForFailedDistributedTransaction {
        /* 🧪 Zadanie 24: Powiaz z `Lesson14` - zaimplementuj KOMPENSACJE Saga WYWOLANA PRZEZ wiadomosc W DLQ. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildDashboardVisualizingRetryAndDlqMetricsAcrossMultipleQueues {
        /* 🧪 Zadanie 25: Zbuduj dashboard WIZUALIZUJACY metryki retry/DLQ DLA WIELU kolejek. */
        public static void main(String[] args) { }
    }

    static class Exercise26_DesignPoisonMessageQuarantineStrategyPreventingSystemWideOutage {
        /* 🧪 Zadanie 26: Zaprojektuj strategie KWARANTANNY "zatrutych" wiadomosci ZAPOBIEGAJACA AWARII CALEGO systemu. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementAdaptiveRetryStrategyLearningFromHistoricalFailurePatterns {
        /* 🧪 Zadanie 27: Zaimplementuj ADAPTACYJNA strategie retry UCZACA SIE Z HISTORYCZNYCH wzorcow bledow. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildFullOperationalRunbookForHandlingDlqIncidentsInProduction {
        /* 🧪 Zadanie 28: Zbuduj PELNY "runbook" operacyjny DO obslugi incydentow DLQ NA produkcji. */
        public static void main(String[] args) { }
    }

    static class Exercise29_DesignMultiTierRetryStrategyWithImmediateDelayedAndManualTiers {
        /* 🧪 Zadanie 29: Zaprojektuj WIELOPOZIOMOWA strategie retry (natychmiastowy/opozniony/reczny poziom). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullFailureHandlingArchitectureForMissionCriticalMessagingSystem {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA architekture obslugi bledow DLA systemu messagingu KRYTYCZNEGO DLA misji. */
        public static void main(String[] args) { }
    }
}
