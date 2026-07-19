package com.example.javaquest._27_spring_test.Lesson11_TestPropertySourceAndDynamicProperties;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson11_TestPropertySourceAndDynamicProperties {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: @TestPropertySource i @DynamicPropertySource ===");

        /*
         * ============================================================
         * 📦 2 sposoby NADPISANIA wlasciwosci W testach - STATYCZNE vs DYNAMICZNE
         * ============================================================
         * 1. `@TestPropertySource(properties = {"klucz=wartosc"})` -
         *    STATYCZNE, ZNANE Z GORY wartosci (np. wylaczenie
         *    zewnetrznego wywolania, wlasny limit) - NAJWYZSZY
         *    priorytet (nadpisuje NAWET `application.properties`).
         * 2. `@DynamicPropertySource` - wartosci ZNANE DOPIERO W
         *    RUNTIME (np. LOSOWY port embedded serwera, port
         *    kontenera Testcontainers - Lesson15 pogłębi TEN
         *    dokladny przypadek) - metoda STATYCZNA rejestrujaca
         *    wlasciwosc PRZEZ `DynamicPropertyRegistry.add(klucz,
         *    Supplier)`.
         *
         * 🔍 PONIZEJ: `@TestPropertySource` PODMIENIA STALA
         * wlasciwosc, `@DynamicPropertySource` OBLICZA wartosc W
         * RUNTIME (symulowany "port" zewnetrznej uslugi wykryty
         * DOPIERO PO starcie JVM).
         */
        System.out.println("@TestPropertySource = STATYCZNE wartosci znane z gory. @DynamicPropertySource = wartosci obliczone W RUNTIME (Supplier).");

        runAndReport(StaticPropertyOverrideTest.class);
        runAndReport(DynamicPropertyTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@TestPropertySource(properties = "app.limit=5")` -
         *   NAJWYZSZY priorytet W hierarchii zrodel wlasciwosci
         *   Springa - PRZYDATNE DO WYMUSZENIA KONKRETNEJ wartosci W
         *   tescie.
         * - `@TestPropertySource(locations = "classpath:test.properties")` -
         *   WCZYTANIE CALEGO OSOBNEGO pliku wlasciwosci.
         * - `@DynamicPropertySource static void register(DynamicPropertyRegistry registry)` -
         *   METODA STATYCZNA (WYMAGANE), URUCHAMIANA PRZED startem
         *   kontekstu - `registry.add("klucz", () -> obliczonaWartosc)`.
         * - GLOWNE zastosowanie `@DynamicPropertySource` (Lesson15):
         *   przekazanie `container.getJdbcUrl()` Z Testcontainers DO
         *   `spring.datasource.url` PRZED startem Springa.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    @SpringBootApplication
    static class TestApp {
        @Bean
        String marker() {
            return "marker-bean";
        }
    }

    @SpringBootTest(classes = TestApp.class)
    @TestPropertySource(properties = "app.discount.max-percent=25")
    static class StaticPropertyOverrideTest {
        @Value("${app.discount.max-percent}")
        int maxDiscountPercent;

        @Test
        void testPropertySourceOverridesValueAtHighestPriority() {
            assertThat(maxDiscountPercent).isEqualTo(25);
            System.out.println("@TestPropertySource ustawil app.discount.max-percent=" + maxDiscountPercent);
        }
    }

    @SpringBootTest(classes = TestApp.class)
    static class DynamicPropertyTest {

        // Symulacja "wykrytego w runtime" portu zewnetrznej uslugi (w PRAWDZIWYM projekcie
        // czesciej byloby to np. container.getMappedPort(...) z Testcontainers - Lesson15).
        static int detectedPortAtRuntime() {
            return 40000 + (int) (System.nanoTime() % 1000);
        }

        @DynamicPropertySource
        static void registerDynamicProperties(DynamicPropertyRegistry registry) {
            int port = detectedPortAtRuntime();
            registry.add("app.external-service.port", () -> port);
            System.out.println("@DynamicPropertySource zarejestrowal app.external-service.port=" + port + " PRZED startem kontekstu.");
        }

        @Value("${app.external-service.port}")
        int externalServicePort;

        @Test
        void dynamicPropertySourceInjectsRuntimeComputedValue() {
            assertThat(externalServicePort).isGreaterThanOrEqualTo(40000);
            System.out.println("Wstrzykniety port (obliczony W RUNTIME): " + externalServicePort);
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
