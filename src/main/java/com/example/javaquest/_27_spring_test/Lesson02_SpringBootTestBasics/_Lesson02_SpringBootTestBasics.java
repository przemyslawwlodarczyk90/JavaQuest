package com.example.javaquest._27_spring_test.Lesson02_SpringBootTestBasics;

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

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson02_SpringBootTestBasics {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: @SpringBootTest - podstawy ===");

        /*
         * ============================================================
         * 📦 @SpringBootTest = PELNE rusztowanie Spring Boota W tescie
         * ============================================================
         * Lesson01 uzyl "surowego" `SpringExtension`+`@ContextConfiguration`
         * (RECZNE wskazanie klas konfiguracyjnych, BEZ pelnego
         * bootstrapu Boota). `@SpringBootTest` idzie DALEJ - DZIALA
         * DOKLADNIE jak `SpringApplication.run(...)` (Lesson01
         * `_21_spring_boot`): wczytuje `application.properties`,
         * URUCHAMIA CALA auto-konfiguracje, OPCJONALNIE stawia
         * embedded serwer (Lesson03) - TO jest NAJBARDZIEJ "pelny",
         * ALE TEZ NAJWOLNIEJSZY sposob testowania aplikacji Spring
         * Boot.
         *
         * 🔍 `@SpringBootTest` SZUKA klasy `@SpringBootApplication`
         * (LUB `@SpringBootConfiguration`) W PAKIECIE testu LUB
         * PAKIETACH NADRZEDNYCH - MOZNA TEZ wskazac JAWNIE PRZEZ
         * `classes = {...}` (jak ponizej, zgodnie Z konwencja kursu -
         * KAZDA lekcja jest samodzielna, WLASNA, zagniezdzona klasa
         * `@SpringBootApplication`).
         */
        System.out.println("@SpringBootTest = PELNE rusztowanie Boota (application.properties + CALA auto-konfiguracja) - wolniejsze, ale najbardziej realistyczne.");

        runAndReport(FullContextTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@SpringBootTest` BEZ argumentow = domyslnie
         *   `webEnvironment = MOCK` (BEZ prawdziwego serwera - Lesson03
         *   pokaze WSZYSTKIE opcje).
         * - `@SpringBootTest(classes = MojaAplikacja.class)` - JAWNE
         *   wskazanie klasy startowej (zamiast automatycznego
         *   wyszukiwania).
         * - `@Autowired` W polu testowym - DZIALA IDENTYCZNIE jak W
         *   PRAWDZIWEJ aplikacji (Lesson01 `_20_spring_core`).
         * - `@SpringBootTest` JEST WOLNIEJSZY NIZ testy "wycinkowe"
         *   (`@WebMvcTest`/`@DataJpaTest` - Lesson04-06) - UZYWAJ GO,
         *   GDY FAKTYCZNIE potrzebujesz CALEGO kontekstu (np. test
         *   end-to-end WEWNATRZ jednego procesu).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    interface GreetingService {
        String greet(String name);
    }

    static class DefaultGreetingService implements GreetingService {
        @Override
        public String greet(String name) {
            return "Witaj, " + name + "!";
        }
    }

    @SpringBootApplication
    static class GreetingApp {
        @Bean
        GreetingService greetingService() {
            return new DefaultGreetingService();
        }
    }

    @SpringBootTest(classes = GreetingApp.class)
    static class FullContextTest {
        @Autowired
        GreetingService greetingService;

        @Test
        void springBootTestWiresRealBeanFromFullContext() {
            String result = greetingService.greet("Kursant");
            assertThat(result).isEqualTo("Witaj, Kursant!");
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
