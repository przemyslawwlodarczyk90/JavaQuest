package com.example.javaquest._27_spring_test.Lesson16_TestingSchedulingAndAsyncCode;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson16_TestingSchedulingAndAsyncCode {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 16: Testowanie kodu @Async i @Scheduled ===");

        /*
         * ============================================================
         * 📦 PROBLEM: @Async/@Scheduled URUCHAMIAJA sie W INNYM WATKU - test NIE MOZE po prostu "sprawdzic od razu"
         * ============================================================
         * Powiazanie Z `_26_integration_testing/Lesson12_FlakyTestsAndHowToFixThem`
         * (ta lekcja UCZYLA POLLINGU zamiast sztywnego `sleep` - TU
         * stosujemy DOKLADNIE TA SAMA technike, TERAZ DLA beanow
         * Springa `@Async`/`@Scheduled`, zapowiedz `_30_spring_
         * messaging_and_async`). Test metody `@Async` MUSI POCZEKAC
         * (POLLING Z timeoutem), AZ operacja W TLE sie ZAKONCZY -
         * PROBA natychmiastowego sprawdzenia BEDZIE "flaky"
         * (CZASAMI zdazy, CZASAMI nie).
         *
         * 🔍 `CompletableFuture` zwrocony PRZEZ metode `@Async`
         * (Lesson16) DAJE NAJLEPSZE rozwiazanie - `future.get()`
         * (Z timeoutem) BLOKUJE test AZ DO zakonczenia, BEZ
         * recznego pollingu.
         */
        System.out.println("@Async/@Scheduled dzialaja W INNYM WATKU - test MUSI czekac (CompletableFuture.get() LUB polling), NIE sprawdzac natychmiast.");

        runAndReport(AsyncServiceTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EnableAsync` + `@Async` NA metodzie zwracajacej
         *   `CompletableFuture<T>` - TEST uzywa `future.get(timeout,
         *   jednostka)` DO CZEKANIA NA wynik (BLOKUJACO, ALE Z
         *   LIMITEM).
         * - `@Async` NA metodzie `void` (BEZ `CompletableFuture`) -
         *   test MUSI uzyc POLLINGU (`awaitUntil` Z
         *   `_26_integration_testing/Lesson12`) NA WIDOCZNYM efekcie
         *   ubocznym (np. licznik).
         * - `@EnableScheduling` + `@Scheduled(fixedRate = ...)` -
         *   testy SPRAWDZAJA, ze metoda WYKONALA SIE co najmniej RAZ
         *   W ROZSADNYM czasie (POLLING, NIE dokladne odliczanie
         *   milisekund - HARMONOGRAM NIE jest 100% precyzyjny).
         * - `_30_spring_messaging_and_async` (przyszly rozdzial)
         *   POGLEBI ten temat O prawdziwe kolejki wiadomosci.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    static class NotificationCounterService {
        private final AtomicInteger scheduledRuns = new AtomicInteger(0);

        @Async
        CompletableFuture<String> processAsync(String input) {
            return CompletableFuture.completedFuture("PRZETWORZONO: " + input);
        }

        @Scheduled(fixedRate = 100)
        void scheduledHeartbeat() {
            scheduledRuns.incrementAndGet();
        }

        int getScheduledRunCount() {
            return scheduledRuns.get();
        }
    }

    @SpringBootApplication
    @EnableAsync
    @EnableScheduling
    static class TestApp {
        @Bean
        NotificationCounterService notificationCounterService() {
            return new NotificationCounterService();
        }
    }

    @SpringBootTest(classes = TestApp.class)
    static class AsyncServiceTest {
        @Autowired
        NotificationCounterService service;

        @Test
        void asyncMethodReturningCompletableFutureCanBeAwaitedDirectly() throws Exception {
            CompletableFuture<String> future = service.processAsync("zamowienie-123");

            // future.get(timeout) BLOKUJE test AZ DO zakonczenia metody @Async W INNYM watku -
            // NIE ma ryzyka "flaky" (sprawdzenia ZA WCZESNIE), bo CZEKAMY NA rzeczywisty wynik.
            String result = future.get(2, java.util.concurrent.TimeUnit.SECONDS);

            assertThat(result).isEqualTo("PRZETWORZONO: zamowienie-123");
            System.out.println("Metoda @Async zakonczyla sie W INNYM WATKU, future.get() POPRAWNIE poczekal na wynik: " + result);
        }

        @Test
        void scheduledMethodRunsAtLeastOnceWithinReasonableTime() throws Exception {
            int before = service.getScheduledRunCount();

            // POLLING (Z _26_integration_testing/Lesson12) - CZEKAMY, AZ @Scheduled
            // wykona sie co najmniej RAZ, ZAMIAST zgadywac sztywny czas snu.
            Instant deadline = Instant.now().plus(Duration.ofSeconds(2));
            while (service.getScheduledRunCount() <= before && Instant.now().isBefore(deadline)) {
                Thread.sleep(10);
            }

            assertThat(service.getScheduledRunCount()).isGreaterThan(before);
            System.out.println("@Scheduled(fixedRate=100) wykonal sie co najmniej raz (polling, BEZ sztywnego sleep 'na oko').");
        }
    }

    private static void runAndReport(Class<?> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println(testClass.getSimpleName() + " -> udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount());
    }
}
