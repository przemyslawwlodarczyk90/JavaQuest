package com.example.javaquest._27_spring_test.Lesson16_TestingSchedulingAndAsyncCode;

public class _Exercises_Lesson16_TestingSchedulingAndAsyncCode {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteAsyncMethodReturningCompletableFuture {
        /* 🧪 Zadanie 1: Napisz WLASNA metode `@Async` zwracajaca `CompletableFuture<T>`. */
        public static void main(String[] args) { }
    }

    static class Exercise02_TestAsyncMethodUsingFutureGet {
        /* 🧪 Zadanie 2: Przetestuj JA uzywajac `future.get(timeout, jednostka)`. */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteScheduledMethodWithFixedRate {
        /* 🧪 Zadanie 3: Napisz metode `@Scheduled(fixedRate = ...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise04_TestScheduledMethodWithPolling {
        /* 🧪 Zadanie 4: Przetestuj JA PRZEZ polling (BEZ sztywnego sleep). */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhyImmediateAssertionAfterAsyncCallIsFlaky {
        /* 🧪 Zadanie 5: Bez terminala - wyjasnij, DLACZEGO natychmiastowe sprawdzenie PO wywolaniu `@Async` jest "flaky". */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteVoidAsyncMethodAndTestWithPolling {
        /* 🧪 Zadanie 6: Napisz metode `@Async` typu `void` I przetestuj PRZEZ polling NA efekcie ubocznym. */
        public static void main(String[] args) { }
    }

    static class Exercise07_TestScheduledMethodWithFixedDelay {
        /* 🧪 Zadanie 7: Napisz I przetestuj `@Scheduled(fixedDelay = ...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise08_TestScheduledMethodWithCronExpression {
        /* 🧪 Zadanie 8: Napisz I przetestuj `@Scheduled(cron = "...")`. */
        public static void main(String[] args) { }
    }

    static class Exercise09_VerifyAsyncMethodRunsOnDifferentThread {
        /* 🧪 Zadanie 9: Zweryfikuj, ze metoda `@Async` DZIALA NA INNYM watku NIZ test (`Thread.currentThread()`). */
        public static void main(String[] args) { }
    }

    static class Exercise10_DocumentPollingVsFutureGetTradeoffs {
        /* 🧪 Zadanie 10: Bez terminala - porownaj polling Z `future.get(...)` - KIEDY ktore. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TestAsyncMethodThrowingExceptionPropagation {
        /* 🧪 Zadanie 11: Przetestuj PROPAGACJE wyjatku Z metody `@Async` (`ExecutionException`). */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConfigureCustomTaskExecutorForTests {
        /* 🧪 Zadanie 12: Powiaz z `_05_multithreading` - skonfiguruj WLASNY `TaskExecutor` DLA testow. */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestMultipleAsyncCallsRunningConcurrently {
        /* 🧪 Zadanie 13: Wywolaj 5 metod `@Async` NARAZ I zweryfikuj ROWNOLEGLE wykonanie. */
        public static void main(String[] args) { }
    }

    static class Exercise14_DisableSchedulingInTestUsingProperty {
        /* 🧪 Zadanie 14: Powiaz z Lesson11 - WYLACZ `@Scheduled` W tescie PRZEZ wlasciwosc. */
        public static void main(String[] args) { }
    }

    static class Exercise15_TestScheduledTaskInteractingWithDatabase {
        /* 🧪 Zadanie 15: Powiaz z Lesson06 - przetestuj `@Scheduled` metode ZAPISUJACA DO bazy. */
        public static void main(String[] args) { }
    }

    static class Exercise16_UseAwaitilityLikeHelperFromChapter26 {
        /* 🧪 Zadanie 16: Powiaz z `_26_integration_testing/Lesson12` - uzyj `awaitUntil(...)` DO testu asynchronicznego. */
        public static void main(String[] args) { }
    }

    static class Exercise17_TestAsyncMethodTimeoutBehavior {
        /* 🧪 Zadanie 17: Przetestuj `future.get(krotkiTimeout)` RZUCAJACY `TimeoutException`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_CombineAsyncTestWithMockitoBeanDependency {
        /* 🧪 Zadanie 18: Powiaz z Lesson08 - polacz test `@Async` Z `@MockitoBean` zaleznoscia. */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestSchedulerLockPreventingOverlappingExecutions {
        /* 🧪 Zadanie 19: Zbadaj (koncepcyjnie) mechanizm "shedlock" ZAPOBIEGAJACY NAKLADANIU sie uruchomien. */
        public static void main(String[] args) { }
    }

    static class Exercise20_DesignFullTestSuiteForAsyncNotificationService {
        /* 🧪 Zadanie 20: Zbuduj PELNY pakiet testow DLA serwisu powiadomien `@Async`. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomAsyncUncaughtExceptionHandler {
        /* 🧪 Zadanie 21: Zaimplementuj WLASNY `AsyncUncaughtExceptionHandler` DLA metod `void @Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise22_TestThreadPoolExhaustionUnderLoad {
        /* 🧪 Zadanie 22: Powiaz z `_05_multithreading` - przetestuj WYCZERPANIE puli watkow PRZY duzym obciazeniu. */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementDeterministicTestClockForScheduledTasks {
        /* 🧪 Zadanie 23: Powiaz z `_26_integration_testing/Lesson12` - zbadaj wstrzykiwalny `Clock` DLA `@Scheduled`. */
        public static void main(String[] args) { }
    }

    static class Exercise24_TestAsyncMethodChainingWithThenCompose {
        /* 🧪 Zadanie 24: Przetestuj LANCUCH `CompletableFuture.thenCompose(...)`. */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignSchedulerMonitoringAndAlertingTestStrategy {
        /* 🧪 Zadanie 25: Zaprojektuj strategie testow MONITOROWANIA zadan `@Scheduled` (metryki wykonania). */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementGracefulShutdownTestForAsyncTasksInProgress {
        /* 🧪 Zadanie 26: Powiaz z `_21_spring_boot/Lesson16` - przetestuj graceful shutdown PRZY trwajacych zadaniach `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise27_TestDistributedSchedulingAcrossMultipleInstancesConceptually {
        /* 🧪 Zadanie 27: Powiaz z `_31_spring_cloud_microservices` - opisz (koncepcyjnie) harmonogram ROZPROSZONY. */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementCircuitBreakerAroundAsyncExternalCall {
        /* 🧪 Zadanie 28: Powiaz z `_31_spring_cloud_microservices/Lesson10` - dodaj circuit breaker WOKOL wywolania `@Async`. */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildLoadTestForAsyncEndpointMeasuringThroughput {
        /* 🧪 Zadanie 29: Zbuduj test WYDAJNOSCIOWY DLA endpointu opartego NA `@Async` - zmierz przepustowosc. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullAsyncAndSchedulingTestingStandardForTeam {
        /* 🧪 Zadanie 30: Zaprojektuj PELNY standard testowania kodu asynchronicznego/harmonogramow DLA zespolu. */
        public static void main(String[] args) { }
    }
}
