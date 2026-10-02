package com.example.javaquest.liveCodeVersion._29_spring_reactive.Lesson02_ReactiveStreamsSpecification;

public class _Exercises_Lesson02_ReactiveStreamsSpecification {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ImplementSimpleFlowSubscriberPrintingEachItem {
        /* 🧪 Zadanie 1: Zaimplementuj PROSTY `Flow.Subscriber` WYPISUJACY KAZDY element. */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateSubmissionPublisherAndSubmitFiveItems {
        /* 🧪 Zadanie 2: Stworz `SubmissionPublisher` I WYSLIJ 5 elementow. */
        public static void main(String[] args) { }
    }

    static class Exercise03_CallRequestOneAtATimeInOnSubscribe {
        /* 🧪 Zadanie 3: Wywolaj `request(1)` W `onSubscribe` (odbieraj PO 1 elemencie naraz). */
        public static void main(String[] args) { }
    }

    static class Exercise04_HandleOnErrorCallbackWhenPublisherFails {
        /* 🧪 Zadanie 4: Obsluz `onError` GDY Publisher ZAWODZI (`closeExceptionally`). */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainFourMethodsOfSubscriberInterface {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij 4 metody interfejsu `Subscriber`. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ExplainWhySubscriptionHasRequestAndCancelMethods {
        /* 🧪 Zadanie 6: Bez terminala - wyjasnij, PO CO `Subscription` MA metody `request`/`cancel`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareFlowApiWithReactiveStreamsOrgSpecification {
        /* 🧪 Zadanie 7: Bez terminala - porownaj `java.util.concurrent.Flow` Z org.reactivestreams (identyczne interfejsy, inny pakiet). */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhatProcessorInterfaceCombines {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, CO LACZY interfejs `Processor`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_CancelSubscriptionAfterReceivingThreeItems {
        /* 🧪 Zadanie 9: ANULUJ subskrypcje (`cancel()`) PO odebraniu 3 elementow. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyOnCompleteAndOnErrorAreMutuallyExclusive {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO `onComplete`/`onError` WZAJEMNIE SIE wykluczaja (kontrakt specyfikacji). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementSubscriberRequestingInBatchesOfThree {
        /* 🧪 Zadanie 11: Zaimplementuj `Subscriber` ZADAJACY elementy W PARTIACH PO 3. */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildCustomProcessorTransformingIntegersToStrings {
        /* 🧪 Zadanie 12: Zbuduj WLASNY `Processor<Integer, String>` KONWERTUJACY liczby NA stringi. */
        public static void main(String[] args) { }
    }

    static class Exercise13_DemonstrateBackpressureViolationDetectionByPublisher {
        /* 🧪 Zadanie 13: Zademonstruj, JAK Publisher WYKRYWA naruszenie backpressure (WIECEJ onNext NIZ zazadano). */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSlowSubscriberAndObserveBufferBehaviorOfSubmissionPublisher {
        /* 🧪 Zadanie 14: Zaimplementuj WOLNEGO Subscribera I zaobserwuj zachowanie bufora `SubmissionPublisher`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_HandleMultipleSubscribersOnSameSubmissionPublisher {
        /* 🧪 Zadanie 15: Obsluz WIELU Subscriberow NA TYM SAMYM `SubmissionPublisher`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementSubscriberThatCancelsOnFirstError {
        /* 🧪 Zadanie 16: Zaimplementuj Subscriber, ktory ANULUJE subskrypcje PRZY PIERWSZYM bledzie przetwarzania. */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareThroughputWithDifferentRequestBatchSizes {
        /* 🧪 Zadanie 17: Porownaj przepustowosc DLA ROZNYCH rozmiarow partii `request(n)`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildFilteringProcessorPassingOnlyEvenNumbers {
        /* 🧪 Zadanie 18: Zbuduj filtrujacy `Processor` PRZEPUSZCZAJACY TYLKO parzyste liczby. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementCustomPublisherFromScratchWithoutSubmissionPublisher {
        /* 🧪 Zadanie 19: Zaimplementuj WLASNY `Flow.Publisher` OD ZERA (BEZ `SubmissionPublisher`). */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhyReactiveStreamsInteroperabilityMattersAcrossLibraries {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, DLACZEGO interoperability MIEDZY bibliotekami (Reactor/RxJava/Akka) jest WAZNA. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildChainOfThreeProcessorsFormingProcessingPipeline {
        /* 🧪 Zadanie 21: Zbuduj LANCUCH 3 `Processor` TWORZACY pipeline przetwarzania. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomBackpressureStrategyDroppingExcessItems {
        /* 🧪 Zadanie 22: Zaimplementuj WLASNA strategie backpressure ODRZUCAJACA nadmiarowe elementy ("drop"). */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildThreadSafePublisherHandlingConcurrentSubmitCalls {
        /* 🧪 Zadanie 23: Zbuduj WATKOWO BEZPIECZNY Publisher OBSLUGUJACY rownolegle wywolania `submit()`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRetryLogicAtSubscriberLevelOnError {
        /* 🧪 Zadanie 24: Zaimplementuj LOGIKE ponawiania NA poziomie Subscribera PRZY `onError`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareRawFlowApiComplexityWithProjectReactorSimplicity {
        /* 🧪 Zadanie 25: Powiaz z `Lesson03` - porownaj ZLOZONOSC surowego `Flow` API Z PROSTOTA Project Reactor. */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildCustomExecutorBasedPublisherWithConfigurableBufferSize {
        /* 🧪 Zadanie 26: Zbuduj WLASNY Publisher OPARTY NA `Executor` Z KONFIGUROWALNYM rozmiarem bufora. */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementMergingProcessorCombiningTwoPublisherStreams {
        /* 🧪 Zadanie 27: Zaimplementuj `Processor` LACZACY 2 strumienie Z ROZNYCH Publisherow. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildTestHarnessVerifyingReactiveStreamsContractCompliance {
        /* 🧪 Zadanie 28: Zbuduj HARNESS testowy WERYFIKUJACY ZGODNOSC Z kontraktem specyfikacji (np. NIE WIECEJ onNext NIZ zazadano). */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementRateLimitingSubscriberUsingRequestNTiming {
        /* 🧪 Zadanie 29: Zaimplementuj Subscriber OGRANICZAJACY tempo przez CZASOWE opoznianie `request(n)`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullCustomReactiveStreamsImplementationForDomainSpecificUseCase {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA, WLASNA implementacje Reactive Streams DLA konkretnego przypadku domenowego (np. przetwarzanie zdarzen IoT). */
        public static void main(String[] args) { }
    }
}
