package com.example.javaquest._27_spring_test.Lesson17_ContextCachingAndTestPerformance;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson17_ContextCachingAndTestPerformance {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: Cache'owanie kontekstu i wydajnosc testow ===");

        /*
         * ============================================================
         * 📦 Spring TEST CACHE'UJE ApplicationContext MIEDZY klasami testowymi Z IDENTYCZNA konfiguracja
         * ============================================================
         * URUCHOMIENIE `@SpringBootTest` KOSZTUJE (Lesson02: SEKUNDY,
         * NIE milisekundy) - Spring TEST unika PONOWNEGO tworzenia
         * TEGO SAMEGO kontekstu DLA KAZDEJ klasy testowej. "KLUCZ
         * cache'a" = UNIKALNA KOMBINACJA (`classes`, `@ActiveProfiles`
         * Z Lesson10, `@TestPropertySource` Z Lesson11, `@MockitoBean`
         * Z Lesson08 itd.) - JESLI DWIE klasy MAJA IDENTYCZNY klucz,
         * DRUGA klasa DOSTAJE GOTOWY, JUZ URUCHOMIONY kontekst
         * (BEZ ponownego startu).
         *
         * 🔍 PONIZEJ 2 klasy testowe Z IDENTYCZNA konfiguracja
         * (`classes = TestApp.class`) - LICZNIK `contextCreations`
         * (statyczne pole W `@Bean`) DOWODZI, ze kontekst zostal
         * UTWORZONY TYLKO RAZ, NIE DWA razy.
         */
        System.out.println("Spring CACHE'UJE ApplicationContext MIEDZY klasami Z IDENTYCZNYM 'kluczem' konfiguracji - unika PONOWNEGO tworzenia.");

        runAndReport(FirstContextUserTest.class);
        runAndReport(SecondContextUserTest.class);

        System.out.println("\nLicznik utworzen kontekstu: " + TestApp.contextCreations.get()
                + " (OCZEKIWANE: 1, mimo 2 URUCHOMIONYCH klas testowych - DOWOD cache'owania).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - IDENTYCZNA konfiguracja (`classes`+`@ActiveProfiles`+
         *   `@TestPropertySource`+`@MockitoBean` deklaracje) MIEDZY
         *   klasami = TEN SAM "klucz cache'a" = 1 WSPOLNY kontekst.
         * - KAZDA RÓZNICA (INNY profil - Lesson10, INNA wlasciwosc -
         *   Lesson11, DODATKOWY `@MockitoBean`) TWORZY NOWY klucz =
         *   NOWY, OSOBNY kontekst.
         * - PRAKTYCZNA implikacja: GRUPUJ testy Z PODOBNA konfiguracja
         *   RAZEM (NIE MIESZAJ losowo `@ActiveProfiles`/`@TestPropertySource`
         *   miedzy klasami) - MAKSYMALIZUJE trafienia cache'a =
         *   SZYBSZY CALY pakiet testow.
         * - `@DirtiesContext` - WYMUSZA usuniecie kontekstu Z cache'a
         *   PO tescie (GDY test ZMIENIL globalny stan W SPOSOB
         *   psujacy INNE testy) - UZYWAJ OSZCZEDNIE (NISZCZY zysk
         *   Z cache'owania).
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    static class MarkerBean {
    }

    @SpringBootApplication
    static class TestApp {
        static final AtomicInteger contextCreations = new AtomicInteger(0);

        @Bean
        MarkerBean markerBean() {
            contextCreations.incrementAndGet();
            return new MarkerBean();
        }
    }

    @SpringBootTest(classes = TestApp.class)
    static class FirstContextUserTest {
        @Autowired
        ApplicationContext context;

        @Test
        void firstTestClassTriggersContextCreation() {
            assertThat(context).isNotNull();
            System.out.println("FirstContextUserTest uzyl kontekstu (licznik po tym tescie: " + TestApp.contextCreations.get() + ").");
        }
    }

    @SpringBootTest(classes = TestApp.class)
    static class SecondContextUserTest {
        @Autowired
        ApplicationContext context;

        @Test
        void secondTestClassReusesCachedContextInsteadOfCreatingNewOne() {
            assertThat(context).isNotNull();
            System.out.println("SecondContextUserTest uzyl (PRAWDOPODOBNIE tego samego, cache'owanego) kontekstu (licznik: " + TestApp.contextCreations.get() + ").");
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
