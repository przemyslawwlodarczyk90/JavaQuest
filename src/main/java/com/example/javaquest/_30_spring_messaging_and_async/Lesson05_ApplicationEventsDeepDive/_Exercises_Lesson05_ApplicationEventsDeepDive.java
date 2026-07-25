package com.example.javaquest._30_spring_messaging_and_async.Lesson05_ApplicationEventsDeepDive;

public class _Exercises_Lesson05_ApplicationEventsDeepDive {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PublishSimpleEventUsingApplicationEventPublisher {
        /* 🧪 Zadanie 1: Opublikuj PROSTE zdarzenie uzywajac `ApplicationEventPublisher`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ListenToEventUsingEventListenerAnnotation {
        /* 🧪 Zadanie 2: Nasluchuj zdarzenia uzywajac `@EventListener`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_MakeListenerAsyncUsingAsyncAnnotation {
        /* 🧪 Zadanie 3: Uczyn sluchacza asynchronicznym uzywajac `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_MeasureThatSyncListenerBlocksPublisherUntilFinished {
        /* 🧪 Zadanie 4: Zmierz, ze SYNCHRONICZNY sluchacz BLOKUJE publishera do zakonczenia. */
        public static void main(String[] args) { }
    }

    static class Exercise05_MeasureThatAsyncListenerDoesNotBlockPublisher {
        /* 🧪 Zadanie 5: Zmierz, ze ASYNCHRONICZNY sluchacz NIE BLOKUJE publishera. */
        public static void main(String[] args) { }
    }

    static class Exercise06_RegisterMultipleListenersForSameEventType {
        /* 🧪 Zadanie 6: Zarejestruj WIELU sluchaczy DLA TEGO SAMEGO typu zdarzenia. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CompareSpringApplicationEventsWithManualPublisherFromArchitectureChapter {
        /* 🧪 Zadanie 7: Powiaz z `_17_architecture/Lesson18` - porownaj Spring-owe zdarzenia Z RECZNYM publisherem. */
        public static void main(String[] args) { }
    }

    static class Exercise08_UseConditionAttributeOnEventListenerToFilterEvents {
        /* 🧪 Zadanie 8: Uzyj atrybutu `condition` NA `@EventListener` DO filtrowania zdarzen. */
        public static void main(String[] args) { }
    }

    static class Exercise09_PublishEventFromInsideTransactionalMethodAndObserveTiming {
        /* 🧪 Zadanie 9: Powiaz z `_20_spring_core/Lesson08` - opublikuj zdarzenie Z WNETRZA metody transakcyjnej. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhySpringEventsAreSynchronousByDefault {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO zdarzenia Springa SA SYNCHRONICZNE domyslnie. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_UseTransactionEventListenerToFireAfterCommitOnly {
        /* 🧪 Zadanie 11: Uzyj `@TransactionalEventListener` DO wywolania PO commicie. */
        public static void main(String[] args) { }
    }

    static class Exercise12_MixSyncAndAsyncListenersForSameEventAndObserveOrdering {
        /* 🧪 Zadanie 12: WYMIESZAJ synchronicznych I asynchronicznych sluchaczy DLA TEGO SAMEGO zdarzenia I zaobserwuj KOLEJNOSC. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementOrderedListenersUsingOrderAnnotation {
        /* 🧪 Zadanie 13: Zaimplementuj UPORZADKOWANYCH sluchaczy uzywajac `@Order`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildEventHierarchyWithBaseEventAndSpecificSubclasses {
        /* 🧪 Zadanie 14: Zbuduj HIERARCHIE zdarzen Z BAZOWYM zdarzeniem I SPECYFICZNYMI podklasami. */
        public static void main(String[] args) { }
    }

    static class Exercise15_HandleExceptionThrownFromEventListenerAndObserveImpactOnPublisher {
        /* 🧪 Zadanie 15: Obsluz wyjatek Z `@EventListener` I zaobserwuj WPLYW NA publishera. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementApplicationListenerInterfaceInsteadOfAnnotation {
        /* 🧪 Zadanie 16: Zaimplementuj interfejs `ApplicationListener<T>` ZAMIAST adnotacji. */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildAuditLogListenerCapturingAllDomainEventsGenerically {
        /* 🧪 Zadanie 17: Zbuduj sluchacza dziennika audytu PRZECHWYTUJACEGO WSZYSTKIE zdarzenia domenowe GENERYCZNIE. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CombineEventListenerWithAsyncAndCustomExecutorFromLesson02 {
        /* 🧪 Zadanie 18: Powiaz z `Lesson02` - POLACZ `@EventListener` Z `@Async` UZYWAJACYM WLASNEGO executora. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementEventPayloadCarryingListOfChangesForAuditTrail {
        /* 🧪 Zadanie 19: Zaimplementuj payload zdarzenia NIOSACY LISTE zmian DLA dziennika audytu. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareGenericApplicationEventWithCustomEventClassDesign {
        /* 🧪 Zadanie 20: Porownaj `ApplicationEvent` (STARY, generyczny styl) Z WLASNA klasa zdarzenia (rekord, NOWY styl). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullOrderProcessingWorkflowWithMultipleEventDrivenModules {
        /* 🧪 Zadanie 21: Zbuduj PELNY przeplyw przetwarzania zamowien Z WIELOMA modulami STEROWANYMI zdarzeniami. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementEventSourcingLikePatternStoringAllEventsForReplay {
        /* 🧪 Zadanie 22: Zaimplementuj wzorzec PODOBNY DO event sourcing PRZECHOWUJACY WSZYSTKIE zdarzenia DO odtworzenia. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildResilientEventListenerWithRetryAndDeadLetterHandling {
        /* 🧪 Zadanie 23: Powiaz z `_30_spring_messaging_and_async/Lesson13` - zbuduj ODPORNEGO sluchacza Z retry I obsluga "martwych listow". */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCrossModuleEventBusSimulatingMicroservicesCommunication {
        /* 🧪 Zadanie 24: Powiaz z `_31_spring_cloud_microservices/Lesson15` - zaimplementuj SZYNE zdarzen MIEDZY modulami SYMULUJACA komunikacje mikroserwisow. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildComprehensiveEventMetricsCollectorTrackingLatencyPerListener {
        /* 🧪 Zadanie 25: Zbuduj KOMPLEKSOWY kolektor metryk zdarzen SLEDZACY OPOZNIENIE PER sluchacz. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementEventDrivenSagaOrchestrationForMultiStepBusinessProcess {
        /* 🧪 Zadanie 26: Zaimplementuj orkiestracje Saga STEROWANA zdarzeniami DLA WIELOETAPOWEGO procesu biznesowego. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildTestSuiteVerifyingAllListenersRespondCorrectlyToPublishedEvents {
        /* 🧪 Zadanie 27: Powiaz z `_27_spring_test` - zbuduj PAKIET testow WERYFIKUJACY, ze WSZYSCY sluchacze REAGUJA poprawnie. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignEventNamingAndVersioningConventionForLargeEventDrivenSystem {
        /* 🧪 Zadanie 28: Zaprojektuj KONWENCJE nazewnictwa I wersjonowania zdarzen DLA DUZEGO systemu STEROWANEGO zdarzeniami. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementIdempotentEventHandlingPreventingDuplicateProcessingOnRetry {
        /* 🧪 Zadanie 29: Zaimplementuj IDEMPOTENTNA obsluge zdarzen ZAPOBIEGAJACA PODWOJNEMU przetworzeniu PRZY retry. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullEventDrivenArchitectureBridgingInProcessEventsWithExternalMessageBroker {
        /* 🧪 Zadanie 30: Powiaz z `Lesson08-11` - zaprojektuj PELNA architekture STEROWANA zdarzeniami LACZACA zdarzenia in-process Z ZEWNETRZNYM brokerem wiadomosci. */
        public static void main(String[] args) { }
    }
}
