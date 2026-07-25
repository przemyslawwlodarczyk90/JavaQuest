package com.example.javaquest._30_spring_messaging_and_async.Lesson01_AsyncMethodsWithEnableAsync;

public class _Exercises_Lesson01_AsyncMethodsWithEnableAsync {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddEnableAsyncToConfigurationClass {
        /* 🧪 Zadanie 1: Dodaj `@EnableAsync` DO klasy konfiguracji. */
        public static void main(String[] args) { }
    }

    static class Exercise02_MarkServiceMethodWithAsyncAnnotation {
        /* 🧪 Zadanie 2: OZNACZ metode serwisu adnotacja `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_MeasureTimeDifferenceBetweenCallingSiteAndMethodBody {
        /* 🧪 Zadanie 3: Zmierz ROZNICE czasu MIEDZY miejscem wywolania A CIALEM metody `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrintThreadNameInsideAsyncMethodToConfirmDifferentThread {
        /* 🧪 Zadanie 4: Wypisz nazwe watku wewnatrz metody `@Async`, zeby POTWIERDZIC INNY watek. */
        public static void main(String[] args) { }
    }

    static class Exercise05_CallMultipleAsyncMethodsAndObserveTheyRunConcurrently {
        /* 🧪 Zadanie 5: Wywolaj WIELE metod `@Async` I zaobserwuj, ze DZIALAJA rownolegle. */
        public static void main(String[] args) { }
    }

    static class Exercise06_DemonstrateSelfInvocationBypassesAsyncProxy {
        /* 🧪 Zadanie 6: Zademonstruj, ze self-invocation (`this.metoda()`) OMIJA proxy `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyAsyncRequiresSpringManagedBean {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO `@Async` WYMAGA beana ZARZADZANEGO przez Springa. */
        public static void main(String[] args) { }
    }

    static class Exercise08_CompareAsyncWithManuallyCreatedThread {
        /* 🧪 Zadanie 8: Powiaz z `_05_multithreading` - porownaj `@Async` Z RECZNIE tworzonym `Thread`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_ObserveDefaultSimpleAsyncTaskExecutorCreatingNewThreadPerCall {
        /* 🧪 Zadanie 9: Zaobserwuj, ze domyslny `SimpleAsyncTaskExecutor` TWORZY NOWY watek PRZY KAZDYM wywolaniu. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainMechanismBehindAsyncProxyCreation {
        /* 🧪 Zadanie 10: Bez terminala - wyjasnij MECHANIZM stojacy ZA tworzeniem proxy `@Async` (powiazanie Z Spring AOP). */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuildEmailNotificationServiceUsingAsyncForSlowSmtpCalls {
        /* 🧪 Zadanie 11: Zbuduj serwis powiadomien e-mail uzywajac `@Async` DLA WOLNYCH wywolan SMTP (symulowanych). */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementFireAndForgetLoggingUsingAsyncVoidMethod {
        /* 🧪 Zadanie 12: Zaimplementuj logowanie "fire-and-forget" uzywajac metody `@Async` typu `void`. */
        public static void main(String[] args) { }
    }

    static class Exercise13_HandleExceptionThrownFromAsyncVoidMethodUsingAsyncUncaughtExceptionHandler {
        /* 🧪 Zadanie 13: Obsluz wyjatek Z metody `@Async void` uzywajac `AsyncUncaughtExceptionHandler`. */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareAsyncMethodReturningVoidWithReturningCompletableFuture {
        /* 🧪 Zadanie 14: Powiaz z `Lesson03` - porownaj `@Async` metode ZWRACAJACA `void` Z ZWRACAJACA `CompletableFuture`. */
        public static void main(String[] args) { }
    }

    static class Exercise15_ConfigureCustomAsyncExecutorNameForSpecificAsyncMethod {
        /* 🧪 Zadanie 15: Powiaz z `Lesson02` - skonfiguruj WLASNA nazwe executora DLA konkretnej metody `@Async("nazwa")`. */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureThroughputImprovementFromParallelAsyncCallsVsSequential {
        /* 🧪 Zadanie 16: Zmierz POPRAWE przepustowosci Z ROWNOLEGLYCH wywolan `@Async` WOBEC sekwencyjnych. */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildBatchProcessingServiceDelegatingEachItemToAsyncMethod {
        /* 🧪 Zadanie 17: Zbuduj serwis przetwarzania wsadowego DELEGUJACY KAZDY element DO metody `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_DemonstrateAsyncMethodCalledFromAnotherSpringManagedBean {
        /* 🧪 Zadanie 18: Zademonstruj metode `@Async` wywolana Z INNEGO beana Springa (proxy DZIALA). */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareResourceUsageOfDefaultExecutorWithManyConcurrentAsyncCalls {
        /* 🧪 Zadanie 19: Porownaj zuzycie zasobow domyslnego executora PRZY WIELU rownoleglych wywolaniach `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ImplementConditionalAsyncExecutionBasedOnRuntimeFlag {
        /* 🧪 Zadanie 20: Zaimplementuj WARUNKOWE wykonanie asynchroniczne OPARTE NA fladze runtime. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildFullNotificationSystemWithAsyncEmailSmsAndPushChannelsInParallel {
        /* 🧪 Zadanie 21: Zbuduj PELNY system powiadomien Z ROWNOLEGLYMI kanalami email/SMS/push (WSZYSTKIE `@Async`). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementAsyncRetryMechanismWithExponentialBackoffForFailingAsyncCalls {
        /* 🧪 Zadanie 22: Zaimplementuj MECHANIZM retry Z wykladniczym opoznieniem DLA zawodzacych wywolan `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildAsyncPipelineChainingMultipleAsyncMethodsWithCompletableFuture {
        /* 🧪 Zadanie 23: Powiaz z `Lesson03` - zbuduj pipeline LACZACY WIELE metod `@Async` przez `CompletableFuture`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementCorrelationIdPropagationAcrossAsyncMethodBoundaries {
        /* 🧪 Zadanie 24: Zaimplementuj propagacje correlationId PRZEZ granice metod `@Async` (`TaskDecorator`). */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildComprehensiveErrorHandlingStrategyForAsyncMethodsAcrossEntireApplication {
        /* 🧪 Zadanie 25: Zbuduj KOMPLEKSOWA strategie obslugi bledow DLA metod `@Async` W CALEJ aplikacji. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementGracefulShutdownEnsuringAllAsyncTasksCompleteBeforeAppExit {
        /* 🧪 Zadanie 26: Zaimplementuj LAGODNE zamkniecie zapewniajace UKONCZENIE wszystkich zadan `@Async` PRZED wyjsciem aplikacji. */
        public static void main(String[] args) { }
    }

    static class Exercise27_BuildMonitoringDashboardTrackingAsyncMethodExecutionMetrics {
        /* 🧪 Zadanie 27: Zbuduj dashboard monitoringu SLEDZACY metryki wykonania metod `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignArchitectureDecisionForWhenToUseAsyncVsReactiveVsVirtualThreads {
        /* 🧪 Zadanie 28: Powiaz z `_29_spring_reactive/Lesson16` i `_28_java_evolution/Lesson19` - zaprojektuj decyzje architektoniczna KIEDY `@Async` VS reaktywnosc VS watki wirtualne. */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCircuitBreakerPatternWrappingAsyncMethodCallsToExternalService {
        /* 🧪 Zadanie 29: Zaimplementuj wzorzec circuit breaker WOKOL wywolan `@Async` DO zewnetrznego serwisu. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullAsyncArchitectureForHighVolumeNotificationProcessingSystem {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA architekture asynchroniczna DLA systemu przetwarzania powiadomien O DUZYM wolumenie. */
        public static void main(String[] args) { }
    }
}
