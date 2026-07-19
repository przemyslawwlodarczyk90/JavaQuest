package com.example.javaquest._27_spring_test.Lesson10_ActiveProfilesInTests;

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
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson10_ActiveProfilesInTests {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: @ActiveProfiles w testach ===");

        /*
         * ============================================================
         * 📦 @ActiveProfiles = testowy odpowiednik "spring.profiles.active"
         * ============================================================
         * `_20_spring_core/Lesson15` uczyl `@Profile("dev")`/
         * `@Profile("prod")` - RÓZNE beany DLA ROZNYCH srodowisk.
         * `_21_spring_boot/Lesson06` pokazal AKTYWACJE profilu PRZEZ
         * `spring.profiles.active` W `application.properties`. W
         * TESTACH `@ActiveProfiles("nazwa")` NA klasie testowej
         * ROBI DOKLADNIE TO SAMO - AKTYWUJE KONKRETNY profil TYLKO
         * DLA TEGO kontekstu testowego.
         *
         * 🔍 PONIZEJ 2 KLASY testowe (`@ActiveProfiles("dev")` I
         * `@ActiveProfiles("prod")`) LADUJA TEN SAM kod aplikacji,
         * ALE OTRZYMUJA ROZNE implementacje `PaymentGateway` - DOWOD,
         * ze profil AKTYWUJE ROZNE galezie `@Profile` W testach.
         */
        System.out.println("@ActiveProfiles(\"nazwa\") = aktywuje KONKRETNY profil TYLKO dla kontekstu testowego - DOKLADNIE jak spring.profiles.active w aplikacji.");

        runAndReport(DevProfileTest.class);
        runAndReport(ProdProfileTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@ActiveProfiles("dev")` NA klasie testowej - AKTYWUJE
         *   profil "dev" DLA tego KONKRETNEGO kontekstu.
         * - `@ActiveProfiles({"dev", "local"})` - WIELE profili
         *   NARAZ.
         * - RÓZNE `@ActiveProfiles` NA ROZNYCH klasach testowych
         *   OZNACZAJA RÓZNE "klucze cache'a" kontekstu (Lesson17) -
         *   Spring URUCHOMI OSOBNY kontekst DLA KAZDEJ UNIKALNEJ
         *   kombinacji profili.
         * - `application-{profile}.properties` (Lesson11 pogłębi
         *   TEMAT `@TestPropertySource`) - WLASCIWOSCI SPECYFICZNE
         *   DLA profilu SA WCZYTYWANE AUTOMATYCZNIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    interface PaymentGateway {
        String describe();
    }

    static class SandboxPaymentGateway implements PaymentGateway {
        @Override
        public String describe() {
            return "SANDBOX (dev) - zadne prawdziwe pieniadze";
        }
    }

    static class LivePaymentGateway implements PaymentGateway {
        @Override
        public String describe() {
            return "LIVE (prod) - PRAWDZIWE platnosci";
        }
    }

    @SpringBootApplication
    static class TestApp {
        @Bean
        @Profile("dev")
        PaymentGateway sandboxGateway() {
            return new SandboxPaymentGateway();
        }

        @Bean
        @Profile("prod")
        PaymentGateway liveGateway() {
            return new LivePaymentGateway();
        }
    }

    @SpringBootTest(classes = TestApp.class)
    @ActiveProfiles("dev")
    static class DevProfileTest {
        @Autowired
        PaymentGateway paymentGateway;

        @Test
        void devProfileActivatesSandboxGateway() {
            assertThat(paymentGateway).isInstanceOf(SandboxPaymentGateway.class);
            System.out.println("Profil 'dev': " + paymentGateway.describe());
        }
    }

    @SpringBootTest(classes = TestApp.class)
    @ActiveProfiles("prod")
    static class ProdProfileTest {
        @Autowired
        PaymentGateway paymentGateway;

        @Test
        void prodProfileActivatesLiveGateway() {
            assertThat(paymentGateway).isInstanceOf(LivePaymentGateway.class);
            System.out.println("Profil 'prod': " + paymentGateway.describe());
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
