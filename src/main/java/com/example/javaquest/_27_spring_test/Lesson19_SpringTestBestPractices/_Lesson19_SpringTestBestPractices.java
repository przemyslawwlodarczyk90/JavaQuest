package com.example.javaquest._27_spring_test.Lesson19_SpringTestBestPractices;

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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class _Lesson19_SpringTestBestPractices {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 19: Dobre praktyki testow Spring - podsumowanie ===");

        /*
         * ============================================================
         * 📦 6 zasad Z CALEGO rozdzialu - JEDNO miejsce, ZEBY je zobaczyc RAZEM
         * ============================================================
         * 1. PREFERUJ wycinki (`@WebMvcTest`/`@DataJpaTest`/`@JsonTest`
         *    - Lesson04-07) NAD `@SpringBootTest` (Lesson02) - SZYBSZE,
         *    WIEKSZOSC testow NIE potrzebuje CALEGO kontekstu.
         * 2. GRUPUJ testy Z PODOBNA konfiguracja RAZEM (Lesson17) -
         *    MAKSYMALIZUJ trafienia cache'a kontekstu.
         * 3. `@MockitoBean`/`@TestConfiguration` (Lesson08-09) DLA
         *    zaleznosci ZEWNETRZNYCH - NIE testuj PRAWDZIWEJ integracji
         *    W kazdym tescie (OD TEGO jest `_26_integration_testing`).
         * 4. UNIKAJ `@DirtiesContext` "na wszelki wypadek" - NISZCZY
         *    zysk Z cache'owania (Lesson17).
         * 5. IZOLUJ dane (auto-rollback `@Transactional`/`@DataJpaTest` -
         *    Lesson06/14) - NIE polegaj NA kolejnosci testow.
         * 6. `@ArchTest`/ArchUnit (Lesson18) DLA regul, KTORYCH
         *    ZWYKLE testy NIE WYLAPIA (STRUKTURA, NIE zachowanie).
         */
        System.out.println("6 zasad: preferuj wycinki, grupuj konfiguracje, mockuj zewnetrzne zaleznosci, unikaj @DirtiesContext, izoluj dane, uzyj ArchUnit na strukture.");

        runAndReport(BestPracticeExampleTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - lista kontrolna PRZED napisaniem testu Spring
         * ============================================================
         * [ ] Czy TEN test FAKTYCZNIE potrzebuje CALEGO `@SpringBootTest`,
         *     CZY wystarczy wycinek (`@WebMvcTest`/`@DataJpaTest`)?
         * [ ] Czy konfiguracja (profile/wlasciwosci/mocki) jest SPOJNA
         *     Z INNYMI testami W klasie (cache'owanie)?
         * [ ] Czy zewnetrzne zaleznosci SA zamockowane
         *     (`@MockitoBean`)/podmienione (`@TestConfiguration`)?
         * [ ] Czy dane testowe SA izolowane (auto-rollback LUB
         *     `_26_integration_testing/Lesson11` UUID)?
         * [ ] Czy `@DirtiesContext` jest UZASADNIONY (a NIE "na
         *     wszelki wypadek")?
         * [ ] Czy STRUKTURALNE reguly (architektura) SA sprawdzane
         *     ArchUnitem, NIE TYLKO recznym code review?
         */
        System.out.println("\n=== KONIEC LEKCJI 19 ===");
    }

    interface WeatherService {
        String getForecast();
    }

    @RestController
    static class WeatherController {
        private final WeatherService weatherService;

        WeatherController(WeatherService weatherService) {
            this.weatherService = weatherService;
        }

        @GetMapping("/weather")
        Map<String, String> forecast() {
            return Map.of("forecast", weatherService.getForecast());
        }
    }

    @SpringBootApplication
    static class TestApp {
        @Bean
        WeatherService weatherService() {
            // W PRAWDZIWEJ aplikacji: PRAWDZIWE wywolanie zewnetrznego API pogody
            // (powiazanie Z `_26_integration_testing/Lesson07_WireMock`).
            return () -> {
                throw new UnsupportedOperationException("PRAWDZIWE API pogody NIE MOZE dzialac w tym wycinku testowym.");
            };
        }
    }

    // WZORCOWY test STOSUJACY zasady 1+3: WYCINEK (@WebMvcTest, NIE @SpringBootTest) +
    // zamockowana zewnetrzna zaleznosc (@MockitoBean, NIE prawdziwe API).
    @WebMvcTest(WeatherController.class)
    static class BestPracticeExampleTest {
        @Autowired
        MockMvc mockMvc;

        @MockitoBean
        WeatherService weatherService;

        @Test
        void wellDesignedTestUsesSliceAndMocksExternalDependency() throws Exception {
            when(weatherService.getForecast()).thenReturn("slonecznie, 22C");

            mockMvc.perform(get("/weather")).andExpect(status().isOk());
            System.out.println("Test STOSUJE zasady: wycinek @WebMvcTest (SZYBKI) + @MockitoBean (BEZ prawdziwego API pogody).");
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
