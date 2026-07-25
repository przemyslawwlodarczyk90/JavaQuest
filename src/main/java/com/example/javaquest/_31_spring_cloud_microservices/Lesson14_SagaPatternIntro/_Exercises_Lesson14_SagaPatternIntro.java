package com.example.javaquest._31_spring_cloud_microservices.Lesson14_SagaPatternIntro;

public class _Exercises_Lesson14_SagaPatternIntro {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhyDistributedTransactionsAreHardInMicroservices {
        /* 🧪 Zadanie 1: Bez terminala - wyjasnij, DLACZEGO transakcje rozproszone SA TRUDNE W mikroserwisach. */
        public static void main(String[] args) { }
    }

    static class Exercise02_ExplainWhatCompensatingTransactionMeans {
        /* 🧪 Zadanie 2: Bez terminala - wyjasnij, CZYM jest transakcja kompensujaca. */
        public static void main(String[] args) { }
    }

    static class Exercise03_BuildSimpleSagaWithThreeSuccessfulSteps {
        /* 🧪 Zadanie 3: Zbuduj prosta sage Z 3 UDANYMI krokami (jak W lekcji). */
        public static void main(String[] args) { }
    }

    static class Exercise04_BuildSagaWhereSecondStepFailsAndFirstIsCompensated {
        /* 🧪 Zadanie 4: Zbuduj sage, GDZIE DRUGI krok ZAWODZI I PIERWSZY jest kompensowany. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainDifferenceBetweenOrchestrationAndChoreography {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij ROZNICE MIEDZY orkiestracja A choreografia. */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListProsAndConsOfOrchestrationApproach {
        /* 🧪 Zadanie 6: Wymien ZALETY I WADY podejscia orkiestracji. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ListProsAndConsOfChoreographyApproach {
        /* 🧪 Zadanie 7: Wymien ZALETY I WADY podejscia choreografii. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhyEventualConsistencyIsAcceptedTradeoff {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, DLACZEGO eventual consistency jest AKCEPTOWALNYM kompromisem W sagach. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImplementFourStepSagaWithCompensationOnLastStep {
        /* 🧪 Zadanie 9: Zaimplementuj sage Z 4 krokami, GDZIE OSTATNI ZAWODZI (WSZYSTKIE 3 poprzednie kompensowane). */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConnectSagaToUnitOfWorkFromLesson19Dao {
        /* 🧪 Zadanie 10: Powiaz sage Z `_10_dao/Lesson19_UnitOfWork` - wyjasnij ROZNICE (JEDNA baza vs WIELE baz). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ImplementSagaWithLoggingOfEachStepTimestamp {
        /* 🧪 Zadanie 11: Zaimplementuj sage Z LOGOWANIEM znacznika czasu KAZDEGO kroku. */
        public static void main(String[] args) { }
    }

    static class Exercise12_HandleCompensationThatItselfFails {
        /* 🧪 Zadanie 12: Obsluz sytuacje, GDY sama kompensacja ZAWODZI (retry kompensacji). */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementIdempotentStepUsingRequestId {
        /* 🧪 Zadanie 13: Zaimplementuj IDEMPOTENTNY krok (powtorne wywolanie NIE powoduje podwojnego efektu). */
        public static void main(String[] args) { }
    }

    static class Exercise14_DesignSagaStateMachineWithExplicitStates {
        /* 🧪 Zadanie 14: Zaprojektuj maszyne stanow sagi Z JAWNYMI stanami (STARTED/STEP1_DONE/COMPENSATING/FAILED/COMPLETED). */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementChoreographyBasedSagaWithEventPublisher {
        /* 🧪 Zadanie 15: Zaimplementuj sage OPARTA NA choreografii Z prostym publisherem zdarzen (jak `_17_architecture/Lesson18`). */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareSagaWithTwoPhaseCommitTradeoffs {
        /* 🧪 Zadanie 16: Bez terminala - porownaj kompromisy sagi Z protokolem 2PC (two-phase commit). */
        public static void main(String[] args) { }
    }

    static class Exercise17_DesignSagaForECommerceOrderWithFourServices {
        /* 🧪 Zadanie 17: Zaprojektuj sage DLA zamowienia e-commerce Z 4 serwisami (magazyn/platnosci/wysylka/powiadomienia). */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementSagaTimeoutHandling {
        /* 🧪 Zadanie 18: Zaimplementuj obsluge timeoutu KROKU sagi (brak odpowiedzi W okreslonym czasie -> kompensacja). */
        public static void main(String[] args) { }
    }

    static class Exercise19_PersistSagaStateForCrashRecovery {
        /* 🧪 Zadanie 19: Zaprojektuj (koncepcyjnie) trwaly zapis stanu sagi DLA odzyskiwania PO awarii orchestratora. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareSagaPatternWithTccPattern {
        /* 🧪 Zadanie 20: Zbadaj I porownaj wzorzec Saga Z wzorcem TCC (Try-Confirm-Cancel). */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementSagaOrchestratorAsSeparateSpringService {
        /* 🧪 Zadanie 21: Zaimplementuj orchestrator sagi jako OSOBNY serwis Spring (Z REST API zarzadzania sagami). */
        public static void main(String[] args) { }
    }

    static class Exercise22_DesignOutboxPatternForReliableEventPublishing {
        /* 🧪 Zadanie 22: Zbadaj I zaprojektuj wzorzec "outbox" DLA NIEZAWODNEJ publikacji zdarzen (unikaj utraty zdarzen). */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementCompensationOrderingForComplexDependencyGraph {
        /* 🧪 Zadanie 23: Zaimplementuj kolejnosc kompensacji DLA ZLOZONEGO grafu zaleznosci (nie tylko liniowego). */
        public static void main(String[] args) { }
    }

    static class Exercise24_DesignSagaMonitoringDashboardConcept {
        /* 🧪 Zadanie 24: Zaprojektuj koncepcje dashboardu monitorujacego STAN wszystkich aktywnych sag. */
        public static void main(String[] args) { }
    }

    static class Exercise25_CompareChoreographyComplexityGrowthWithManyServices {
        /* 🧪 Zadanie 25: Zbadaj I opisz, JAK ROSNIE zlozonosc choreografii WRAZ Z liczba serwisow (N^2 potencjalnych powiazan). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementSagaWithParallelStepsInsteadOfSequential {
        /* 🧪 Zadanie 26: Zaimplementuj sage Z ROWNOLEGLYMI (NIE sekwencyjnymi) krokami. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignSemanticLockPatternToPreventDirtyReads {
        /* 🧪 Zadanie 27: Zbadaj I zaprojektuj wzorzec "semantic lock" ZAPOBIEGAJACY "brudnym odczytom" W trakcie sagi. */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompareSagaFrameworksAxonVsCustomImplementation {
        /* 🧪 Zadanie 28: Zbadaj I porownaj dedykowany framework (np. Axon) Z WLASNA implementacja sagi. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementSagaCorrelationIdPropagationAcrossServices {
        /* 🧪 Zadanie 29: Zaimplementuj propagacje "correlation ID" sagi MIEDZY serwisami (powiazanie Z Lesson11 traceId). */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignProductionSagaImplementationChecklist {
        /* 🧪 Zadanie 30: Zaprojektuj checkliste "production-ready" implementacji sagi (idempotencja/timeouty/monitoring/recovery). */
        public static void main(String[] args) { }
    }
}
