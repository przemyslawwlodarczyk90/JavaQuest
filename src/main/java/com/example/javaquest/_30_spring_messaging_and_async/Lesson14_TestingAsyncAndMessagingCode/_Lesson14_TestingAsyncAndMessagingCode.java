package com.example.javaquest._30_spring_messaging_and_async.Lesson14_TestingAsyncAndMessagingCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class _Lesson14_TestingAsyncAndMessagingCode {

    // Symulowany serwis asynchroniczny (BEZ Springa - BEZPOSREDNIO uzywa executora,
    // ZEBY test byl SAMODZIELNY, BEZ potrzeby uruchamiania kontekstu Springa W tescie).
    static class PowiadomieniaAsync {
        private final List<String> wyslane = new CopyOnWriteArrayList<>();
        private final java.util.concurrent.ExecutorService executor = Executors.newCachedThreadPool();

        void wyslijAsynchronicznie(String tresc) {
            executor.submit(() -> {
                try {
                    Thread.sleep(50); // symulacja opoznienia
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                wyslane.add(tresc);
            });
        }

        List<String> getWyslane() {
            return wyslane;
        }

        void zamknij() {
            executor.shutdown();
        }
    }

    static class AsyncTest {
        @Test
        @DisplayName("Testowanie kodu asynchronicznego - CZEKAJ NA WYNIK, NIE zakladaj natychmiastowego zakonczenia")
        void testAsynchronicznegoWyslania() throws InterruptedException {
            PowiadomieniaAsync serwis = new PowiadomieniaAsync();
            serwis.wyslijAsynchronicznie("Powiadomienie testowe");

            // BLAD POCZATKUJACYCH: assertThat(serwis.getWyslane()).hasSize(1) OD RAZU PO wywolaniu
            // ZAWIODLBY (metoda asynchroniczna JESZCZE SIE NIE ZAKONCZYLA) - trzeba CZEKAC/POLLOWAC.
            boolean zakonczonoNaCzas = czekajAz(() -> !serwis.getWyslane().isEmpty(), Duration.ofSeconds(2));

            assertThat(zakonczonoNaCzas).isTrue();
            assertThat(serwis.getWyslane()).containsExactly("Powiadomienie testowe");

            serwis.zamknij();
        }

        @Test
        @DisplayName("Test negatywny - upewnij sie, ze zdarzenie NIE wystapilo (BEZ falszywego pozytywu)")
        void testBrakuNieoczekiwanegoZdarzenia() throws InterruptedException {
            PowiadomieniaAsync serwis = new PowiadomieniaAsync();
            // NIC nie wysylamy - oczekujemy, ze getWyslane() POZOSTANIE PUSTA.

            Thread.sleep(100); // krotkie oczekiwanie - upewnij sie, ze NIC sie NIE wydarzylo W tym czasie
            assertThat(serwis.getWyslane()).isEmpty();

            serwis.zamknij();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: Testowanie kodu asynchronicznego i messagingu ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_25_unit_testing`
         * ============================================================
         * `_25_unit_testing` uczyl JUnit5/AssertJ NA kodzie
         * SYNCHRONICZNYM - TAM `assertThat(wynik).isEqualTo(...)` DZIALA
         * NATYCHMIAST, bo wynik JEST JUZ gotowy. Kod asynchroniczny
         * (`@Async` Z Lesson01-05, `@JmsListener`/`@RabbitListener`/
         * `@KafkaListener` Z Lesson06-11) WYMAGA INNEGO podejscia -
         * NIE MOZNA sprawdzic wyniku ZARAZ PO wywolaniu (metoda
         * MOZE JESZCZE nie skonczyc dzialac NA INNYM watku).
         *
         * WZORZEC: "poll AZ DO warunku LUB timeout" (recznie NAPISANY
         * TU JAKO `czekajAz(...)`, W REALNYM projekcie CZESTO uzywa
         * sie biblioteki Awaitility - TEN SAM POMYSL, GOTOWE API).
         */
        System.out.println("Testowanie kodu asynchronicznego - WZORZEC 'poll az do warunku LUB timeout', NIE natychmiastowe assertThat.");

        runAndReport(AsyncTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - NIGDY nie zakladaj, ze kod asynchroniczny JUZ SKONCZYL
         *   dzialac ZARAZ PO wywolaniu - UZYJ `CountDownLatch`/pollingu/
         *   Awaitility.
         * - Test POZYTYWNY ("cos SIE wydarzylo") POTRZEBUJE
         *   pollingu Z timeout - jesli timeout MINIE, test PONOSI
         *   PORAZKE (blad, NIE "false positive").
         * - Test NEGATYWNY ("cos SIE NIE wydarzylo") POTRZEBUJE
         *   KROTKIEGO, STALEGO oczekiwania - NIE MA "wydarzenia",
         *   NA KTORE mozna by POLLOWAC.
         * - Powiazanie Z `_26_integration_testing`/`_27_spring_test` -
         *   TESTOWANIE `@JmsListener`/`@RabbitListener`/`@KafkaListener`
         *   W PRAWDZIWYM kontekscie Springa uzywa TEGO SAMEGO wzorca
         *   (CountDownLatch W beanie testowym, jak Lesson07/09/11
         *   tego rozdzialu).
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private static boolean czekajAz(BooleanSupplier warunek, Duration maksCzas) throws InterruptedException {
        Instant start = Instant.now();
        while (Duration.between(start, Instant.now()).compareTo(maksCzas) < 0) {
            if (warunek.getAsBoolean()) {
                return true;
            }
            Thread.sleep(10);
        }
        return warunek.getAsBoolean();
    }

    private static void runAndReport(Class<?> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(testClass))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        PrintWriter writer = new PrintWriter(System.out);
        summary.printTo(writer);
        summary.printFailuresTo(writer);
        writer.flush();

        System.out.println("Testy znalezione: " + summary.getTestsFoundCount() + ", sukcesy: " + summary.getTestsSucceededCount() + ", porazki: " + summary.getTestsFailedCount());
        assertThat(summary.getTestsFailedCount()).isEqualTo(0L);
    }
}
