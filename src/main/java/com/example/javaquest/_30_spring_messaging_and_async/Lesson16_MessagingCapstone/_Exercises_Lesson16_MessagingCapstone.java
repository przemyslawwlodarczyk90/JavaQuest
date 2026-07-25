package com.example.javaquest._30_spring_messaging_and_async.Lesson16_MessagingCapstone;

public class _Exercises_Lesson16_MessagingCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddFourthOrderAndVerifyStatisticsUpdate {
        /* 🧪 Zadanie 1: Dodaj 4. zamowienie I zweryfikuj aktualizacje statystyk. */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddNewAsyncListenerForSmsNotifications {
        /* 🧪 Zadanie 2: Dodaj NOWEGO `@Async` sluchacza DLA powiadomien SMS. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ChangeScheduledReportIntervalAndObserveTimingDifference {
        /* 🧪 Zadanie 3: Zmien interwal raportu `@Scheduled` I zaobserwuj ROZNICE czasowa. */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddValidationRejectingOrdersWithNegativeAmount {
        /* 🧪 Zadanie 4: Dodaj walidacje ODRZUCAJACA zamowienia Z UJEMNA kwota. */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddCancelOrderEventAndCorrespondingListener {
        /* 🧪 Zadanie 5: Dodaj zdarzenie anulowania zamowienia I ODPOWIADAJACEGO sluchacza. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhichJavaVersionEachMechanismInCapstoneComesFrom {
        /* 🧪 Zadanie 6: Bez terminala - dopasuj KAZDY mechanizm uzyty W kapsztonie DO lekcji, W ktorej ZOSTAL wprowadzony. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReplaceAsyncListenerWithSynchronousAndCompareTiming {
        /* 🧪 Zadanie 7: ZASTAP `@Async` sluchacza SYNCHRONICZNYM I porownaj CZAS wykonania. */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddCounterTrackingNumberOfFailedRabbitAttempts {
        /* 🧪 Zadanie 8: Dodaj licznik SLEDZACY LICZBE nieudanych prob RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddSecondSchedulerJobForDailyStatisticsReset {
        /* 🧪 Zadanie 9: Dodaj DRUGIE zadanie `@Scheduled` DO CODZIENNEGO resetu statystyk. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyRabbitTemplateCallIsWrappedInTryCatch {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO wywolanie `RabbitTemplate` jest OPAKOWANE W try-catch. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddPriorityFieldToOrdersAndProcessHighPriorityFirst {
        /* 🧪 Zadanie 11: Dodaj pole PRIORYTETU DO zamowien I PRZETWARZAJ najpierw WYSOKI priorytet. */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddRetryLogicForFailedEmailNotificationsUsingLesson13Pattern {
        /* 🧪 Zadanie 12: Powiaz z `Lesson13` - dodaj LOGIKE retry DLA nieudanych powiadomien e-mail. */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddDlqForOrdersThatFailProcessingRepeatedly {
        /* 🧪 Zadanie 13: Dodaj DLQ DLA zamowien, KTORE WIELOKROTNIE ZAWODZA przetwarzanie. */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildSummaryStatisticsUsingStreamCollectorsOnOrderHistory {
        /* 🧪 Zadanie 14: Zbuduj statystyki podsumowujace (Stream Collectors) NA historii zamowien. */
        public static void main(String[] args) { }
    }

    static class Exercise15_AddCustomTaskExecutorWithLimitedPoolSizeFromLesson02 {
        /* 🧪 Zadanie 15: Powiaz z `Lesson02` - dodaj WLASNY `TaskExecutor` Z OGRANICZONYM rozmiarem puli. */
        public static void main(String[] args) { }
    }

    static class Exercise16_RefactorProcessingLogicToUseCompletableFutureChaining {
        /* 🧪 Zadanie 16: Powiaz z `Lesson03` - PRZEBUDUJ logike przetwarzania NA lancuch `CompletableFuture`. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExportOrderHistoryToJsonLikeTextBlockFormat {
        /* 🧪 Zadanie 17: Wyeksportuj historie zamowien DO formatu PODOBNEGO DO JSON. */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddCircuitBreakerAroundRabbitMqAttemptPreventingRepeatedSlowFailures {
        /* 🧪 Zadanie 18: Dodaj circuit breaker WOKOL proby RabbitMQ ZAPOBIEGAJACY POWTARZAJACYM sie WOLNYM niepowodzeniom. */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildReversedHistoryViewUsingSequencedCollectionFromJava21 {
        /* 🧪 Zadanie 19: Powiaz z `_28_java_evolution/Lesson20` - zbuduj widok ODWROCONY historii uzywajac Sequenced Collections. */
        public static void main(String[] args) { }
    }

    static class Exercise20_MeasureAndCompareThroughputForDifferentBatchSizesOfOrders {
        /* 🧪 Zadanie 20: Zmierz I porownaj PRZEPUSTOWOSC DLA ROZNYCH rozmiarow partii zamowien. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullEventDrivenModuleCommunicationUsingArchitectureChapterPatterns {
        /* 🧪 Zadanie 21: Powiaz z `_17_architecture/Lesson18` - PRZEBUDUJ komunikacje modulow NA WZORCE Z rozdzialu architektury. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementSagaLikeCompensationForFailedOrderProcessing {
        /* 🧪 Zadanie 22: Powiaz z `Lesson14` (przyszly rozdzial 31) - zaimplementuj KOMPENSACJE PODOBNA DO Saga DLA NIEUDANEGO przetwarzania zamowienia. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildFullRestApiExposingOrderProcessingSystemUsingHttpServer {
        /* 🧪 Zadanie 23: Powiaz z `_18_rest_api` - wystaw system przetwarzania zamowien JAKO PROSTE REST API. */
        public static void main(String[] args) { }
    }

    static class Exercise24_AddPersistentOrderHistoryUsingR2dbcFromLesson29 {
        /* 🧪 Zadanie 24: Powiaz z `_29_spring_reactive/Lesson13` - ZASTAP historie W pamieci trwalym zapisem R2DBC. */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementGracefulShutdownForAllAsyncAndScheduledTasksOnPartialFailure {
        /* 🧪 Zadanie 25: Zaimplementuj LAGODNE zamkniecie WSZYSTKICH zadan `@Async`/`@Scheduled` PRZY CZESCIOWYM niepowodzeniu. */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildMultiStageProcessingPipelineWithValidationNotificationAndPersistence {
        /* 🧪 Zadanie 26: Zbuduj WIELOETAPOWY pipeline (walidacja -> powiadomienie -> zapis) DLA zamowien. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignFullMigrationOfCapstoneToUseKafkaInsteadOfRabbitMq {
        /* 🧪 Zadanie 27: Powiaz z `Lesson11` - zaprojektuj (koncepcyjnie) migracje kapsztonu NA Kafke ZAMIAST RabbitMQ. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildComprehensiveTestSuiteForCapstoneUsingJUnit5AndAwaitingPatterns {
        /* 🧪 Zadanie 28: Powiaz z `Lesson14` - zbuduj KOMPLETNY pakiet testow JUnit5 DLA kapsztonu uzywajac wzorcow oczekiwania. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementMetricsCollectionUsingMicrometerForAllAsyncOperations {
        /* 🧪 Zadanie 29: Powiaz z `_21_spring_boot/Lesson13` - dodaj zbieranie metryk Micrometer DLA WSZYSTKICH operacji asynchronicznych. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignCompleteProductionReadyVersionOfOrderProcessingCombiningEntireChapter {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA, produkcyjna wersje TEGO systemu, LACZAC WIEDZE Z CALEGO rozdzialu (async, scheduling, eventy, messaging, obsluga bledow). */
        public static void main(String[] args) { }
    }
}
