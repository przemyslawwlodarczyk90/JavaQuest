package com.example.javaquest._30_spring_messaging_and_async.Lesson02_TaskExecutorConfiguration;

public class _Exercises_Lesson02_TaskExecutorConfiguration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateThreadPoolTaskExecutorBeanWithCustomCorePoolSize {
        /* 🧪 Zadanie 1: Stworz bean `ThreadPoolTaskExecutor` Z WLASNYM `corePoolSize`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_SetThreadNamePrefixAndVerifyThreadNamesInLogs {
        /* 🧪 Zadanie 2: Ustaw `threadNamePrefix` I zweryfikuj nazwy watkow W logach. */
        public static void main(String[] args) { }
    }

    static class Exercise03_UseAsyncWithExplicitExecutorNameParameter {
        /* 🧪 Zadanie 3: Uzyj `@Async("nazwa")` Z JAWNYM parametrem nazwy executora. */
        public static void main(String[] args) { }
    }

    static class Exercise04_ConfigureMaxPoolSizeAndObserveLimitOnConcurrentExecution {
        /* 🧪 Zadanie 4: Skonfiguruj `maxPoolSize` I zaobserwuj LIMIT rownoleglego wykonania. */
        public static void main(String[] args) { }
    }

    static class Exercise05_ConfigureQueueCapacityAndObserveQueueingBehavior {
        /* 🧪 Zadanie 5: Skonfiguruj `queueCapacity` I zaobserwuj zachowanie KOLEJKOWANIA. */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareDefaultSimpleAsyncTaskExecutorWithThreadPoolTaskExecutor {
        /* 🧪 Zadanie 6: Powiaz z `Lesson01` - porownaj domyslny `SimpleAsyncTaskExecutor` Z `ThreadPoolTaskExecutor`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_CreateTwoNamedExecutorsForDifferentTaskTypes {
        /* 🧪 Zadanie 7: Stworz 2 nazwane executory DLA ROZNYCH typow zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainWhatHappensWhenQueueAndPoolAreBothFull {
        /* 🧪 Zadanie 8: Bez terminala - wyjasnij, CO SIE DZIEJE, GDY kolejka I pula SA OBIE PELNE (RejectedExecutionHandler). */
        public static void main(String[] args) { }
    }

    static class Exercise09_ConfigureRejectionPolicyForOverflowingTaskExecutor {
        /* 🧪 Zadanie 9: Skonfiguruj POLITYKE odrzucania DLA PRZEPELNIONEGO executora. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyDefaultExecutorIsDangerousForProduction {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij, DLACZEGO domyslny executor jest NIEBEZPIECZNY NA produkcji. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TuneThreadPoolSizeBasedOnExpectedWorkloadCharacteristics {
        /* 🧪 Zadanie 11: DOSTROJ rozmiar puli watkow OPARTY NA oczekiwanym charakterze obciazenia. */
        public static void main(String[] args) { }
    }

    static class Exercise12_MonitorActiveThreadCountAndQueueSizeAtRuntime {
        /* 🧪 Zadanie 12: Monitoruj LICZBE aktywnych watkow I rozmiar kolejki W runtime. */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementGracefulShutdownWaitingForTasksToCompleteBeforeStop {
        /* 🧪 Zadanie 13: Zaimplementuj LAGODNE zamkniecie CZEKAJACE NA zakonczenie zadan (`setWaitForTasksToCompleteOnShutdown`). */
        public static void main(String[] args) { }
    }

    static class Exercise14_BuildSeparateExecutorPoolsForIoBoundAndCpuBoundTasks {
        /* 🧪 Zadanie 14: Zbuduj OSOBNE pule executorow DLA zadan I/O-bound I CPU-bound. */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareThroughputWithDifferentPoolSizeConfigurations {
        /* 🧪 Zadanie 15: Porownaj przepustowosc DLA ROZNYCH konfiguracji rozmiaru puli. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementCustomRejectedExecutionHandlerLoggingDroppedTasks {
        /* 🧪 Zadanie 16: Zaimplementuj WLASNY `RejectedExecutionHandler` LOGUJACY ODRZUCONE zadania. */
        public static void main(String[] args) { }
    }

    static class Exercise17_ConfigureAllowCoreThreadTimeOutForIdlePoolShrinking {
        /* 🧪 Zadanie 17: Skonfiguruj `allowCoreThreadTimeOut` DO ZMNIEJSZANIA puli W BEZCZYNNOSCI. */
        public static void main(String[] args) { }
    }

    static class Exercise18_BuildLoadTestMeasuringPoolSaturationPoint {
        /* 🧪 Zadanie 18: Zbuduj TEST OBCIAZENIOWY mierzacy PUNKT NASYCENIA puli. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementTaskDecoratorPropagatingMdcContextAcrossAsyncBoundary {
        /* 🧪 Zadanie 19: Zaimplementuj `TaskDecorator` PROPAGUJACY kontekst MDC (powiazanie Z `_19_security_basics/Lesson19`) PRZEZ granice `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_CompareThreadPoolTaskExecutorWithVirtualThreadPerTaskExecutorForAsync {
        /* 🧪 Zadanie 20: Powiaz z `_28_java_evolution/Lesson19` - porownaj `ThreadPoolTaskExecutor` Z executorem WATKOW WIRTUALNYCH DLA `@Async`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildProductionGradeExecutorConfigurationWithMonitoringAndAlerting {
        /* 🧪 Zadanie 21: Zbuduj konfiguracje executora klasy produkcyjnej Z monitoringiem I alertowaniem. */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementDynamicPoolResizingBasedOnRuntimeMetrics {
        /* 🧪 Zadanie 22: Zaimplementuj DYNAMICZNA zmiane rozmiaru puli OPARTA NA metrykach runtime. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildBulkheadIsolationUsingSeparateExecutorsPerDownstreamService {
        /* 🧪 Zadanie 23: Zbuduj IZOLACJE bulkhead uzywajac OSOBNYCH executorow PER podrzedny serwis. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementBackpressureAwareTaskSubmissionRejectingWhenQueueNearFull {
        /* 🧪 Zadanie 24: Zaimplementuj SWIADOME backpressure zglaszanie zadan ODRZUCAJACE, GDY kolejka BLISKA pelnej. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildComprehensiveThreadPoolTuningGuideBasedOnLittlesLaw {
        /* 🧪 Zadanie 25: Zbuduj KOMPLEKSOWY przewodnik tuningu puli watkow OPARTY NA prawie Little'a. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementPriorityBasedTaskExecutionUsingCustomThreadPoolExecutor {
        /* 🧪 Zadanie 26: Zaimplementuj wykonanie zadan Z PRIORYTETEM uzywajac WLASNEGO `ThreadPoolExecutor`. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildAdaptiveExecutorScalingBasedOnQueueLatency {
        /* 🧪 Zadanie 27: Zbuduj ADAPTACYJNE skalowanie executora OPARTE NA OPOZNIENIU kolejki. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignMultiTenantExecutorIsolationPreventingNoisyNeighborProblem {
        /* 🧪 Zadanie 28: Zaprojektuj IZOLACJE executorow WIELODZIERZAWCZA ZAPOBIEGAJACA problemowi "halasliwego sasiada". */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildFullObservabilityStackForAsyncExecutorPoolsUsingMicrometer {
        /* 🧪 Zadanie 29: Powiaz z `_21_spring_boot/Lesson13` - zbuduj PELNY stos observability DLA pul executorow uzywajac Micrometer. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullAsyncExecutorArchitectureForHighThroughputEnterpriseApplication {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA architekture executorow asynchronicznych DLA aplikacji enterprise WYSOKIEJ przepustowosci. */
        public static void main(String[] args) { }
    }
}
