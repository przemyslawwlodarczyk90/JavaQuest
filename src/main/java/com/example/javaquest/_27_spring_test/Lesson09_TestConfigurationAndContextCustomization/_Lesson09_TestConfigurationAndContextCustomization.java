package com.example.javaquest._27_spring_test.Lesson09_TestConfigurationAndContextCustomization;

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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.io.PrintWriter;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson09_TestConfigurationAndContextCustomization {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: @TestConfiguration - dostosowanie kontekstu do testu ===");

        /*
         * ============================================================
         * 📦 @TestConfiguration = "dodatkowa" konfiguracja WIDOCZNA TYLKO W testach
         * ============================================================
         * `@Configuration` (`_20_spring_core/Lesson09`) DEFINIUJE beany
         * DLA PRAWDZIWEJ aplikacji. `@TestConfiguration` TO TA SAMA
         * IDEA, ALE OZNACZONA jako "TYLKO DO testow" - Spring Boot
         * WYKLUCZA takie klasy Z NORMALNEGO component-scanu
         * aplikacji (main), wiec MOZNA je BEZPIECZNIE trzymac W
         * `src/main/java` (jak W tym kursie) BEZ ryzyka, ze
         * PRZYPADKIEM "wyciekna" DO prawdziwego rozruchu aplikacji.
         *
         * 🔍 NAJCZESTSZY przypadek uzycia: PODMIANA beana NA
         * TESTOWALNY ODPOWIEDNIK (np. DETERMINISTYCZNY `Clock` -
         * powiazanie Z `_26_integration_testing/Lesson12`, gdzie
         * uczylismy sie TEJ SAMEJ idei BEZ Springa) - `@Primary`
         * SPRAWIA, ze bean testowy WYGRYWA Z PRAWDZIWYM PRZY
         * wstrzykiwaniu.
         */
        System.out.println("@TestConfiguration = konfiguracja WIDOCZNA TYLKO w testach (wykluczona Z normalnego rozruchu aplikacji). @Primary = testowy bean WYGRYWA.");

        runAndReport(SubscriptionServiceTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@TestConfiguration` NA zagniezdzonej, STATYCZNEJ klasie
         *   WEWNATRZ testu + `@Import(KlasaTestConfig.class)` NA
         *   klasie testowej - JAWNE dolaczenie.
         * - `@Bean @Primary` W `@TestConfiguration` - PODMIENIA
         *   PRAWDZIWY bean NA testowy WSZEDZIE, GDZIE jest
         *   wstrzykiwany (NIE TYLKO W miejscu wskazanym).
         * - RÓZNICA WZGLEDEM `@MockitoBean` (Lesson08): `@TestConfiguration`
         *   DAJE PRAWDZIWY, DZIALAJACY obiekt (np. `Clock.fixed(...)`),
         *   `@MockitoBean` DAJE ATRAPE (mock) - UZYWAJ `@TestConfiguration`
         *   DLA "prawdziwych, ALE DETERMINISTYCZNYCH" zaleznosci.
         * - `@TestConfiguration` MOZE TEZ byc OSOBNA, ZEWNETRZNA
         *   klasa (NIE zagniezdzona) - PONOWNIE UZYWALNA W WIELU
         *   testach.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    static class SubscriptionService {
        private final Clock clock;

        SubscriptionService(Clock clock) {
            this.clock = clock;
        }

        boolean isExpired(Instant subscriptionEndsAt) {
            return clock.instant().isAfter(subscriptionEndsAt);
        }

        Instant now() {
            return clock.instant();
        }
    }

    @SpringBootApplication
    static class TestApp {
        @Bean
        Clock clock() {
            return Clock.systemUTC();
        }

        @Bean
        SubscriptionService subscriptionService(Clock clock) {
            return new SubscriptionService(clock);
        }
    }

    @TestConfiguration
    static class FixedClockTestConfig {
        @Bean
        @Primary
        Clock fixedClock() {
            // TEN SAM pomysl co "Clock.fixed" z _26_integration_testing/Lesson12,
            // TERAZ jako PRAWDZIWY bean Springa PODMIENIAJACY produkcyjny Clock.systemUTC().
            return Clock.fixed(Instant.parse("2026-06-15T12:00:00Z"), ZoneOffset.UTC);
        }
    }

    @SpringBootTest(classes = TestApp.class)
    @Import(FixedClockTestConfig.class)
    static class SubscriptionServiceTest {
        @Autowired
        SubscriptionService subscriptionService;

        @Autowired
        Clock clock;

        @Test
        void testConfigurationClockReplacesProductionClockWithFixedOne() {
            assertThat(subscriptionService.now()).isEqualTo(Instant.parse("2026-06-15T12:00:00Z"));
            System.out.println("SubscriptionService.now() zwraca STALA, DETERMINISTYCZNA date z @TestConfiguration: " + subscriptionService.now());
        }

        @Test
        void isExpiredWorksDeterministicallyRegardlessOfWhenTestRuns() {
            Instant pastDeadline = Instant.parse("2026-06-01T00:00:00Z");
            Instant futureDeadline = Instant.parse("2026-07-01T00:00:00Z");

            assertThat(subscriptionService.isExpired(pastDeadline)).isTrue();
            assertThat(subscriptionService.isExpired(futureDeadline)).isFalse();
            System.out.println("isExpired(...) daje IDENTYCZNY wynik NIEZALEZNIE OD tego, KIEDY faktycznie uruchomiono test.");
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
