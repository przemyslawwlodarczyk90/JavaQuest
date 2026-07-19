package com.example.javaquest._27_spring_test.Lesson04_TestSlicesConcept;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson04_TestSlicesConcept {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: Koncepcja 'wycinkow' testowych (test slices) ===");

        /*
         * ============================================================
         * 📦 PROBLEM: @SpringBootTest laduje WSZYSTKO - kontroler, serwis, repozytorium, baze, security...
         * ============================================================
         * Test SPRAWDZAJACY TYLKO logike JEDNEGO kontrolera (routing/
         * walidacja/serializacja JSON) NIE POTRZEBUJE URUCHAMIAC
         * CALEJ bazy danych I bezpieczenstwa - `@SpringBootTest`
         * (Lesson02) ZROBI to I TAK, MARNUJAC CZAS. "Wycinek" (test
         * slice) TO SPECJALNA adnotacja (`@WebMvcTest`/`@DataJpaTest`/
         * `@JsonTest` - Lesson05-07) LADUJACA TYLKO WARSTWE ZWIAZANA
         * Z DANYM TESTEM - RESZTA jest WYLACZONA (Spring Boot
         * "wylacza" auto-konfiguracje NIEPOTRZEBNA DLA danego
         * wycinka).
         *
         * 🔍 PONIZEJ POROWNUJEMY LICZBE BEANOW W kontekscie: PELNY
         * `@SpringBootTest` (aplikacja Z kontrolerem + JdbcTemplate)
         * WZGLEDEM tego, CO faktycznie potrzebuje test JEDNEJ
         * warstwy - Lesson05+ pokaza PRAWDZIWE adnotacje wycinkow
         * (`@WebMvcTest` itd.), TU rozumiemy SAMA IDEE.
         */
        System.out.println("Test slice = LADUJE TYLKO warstwe zwiazana z testem (np. Web), WYLACZA reszte (baze/security) - SZYBSZY start.");

        runAndReport(FullContextComparisonTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@WebMvcTest` - TYLKO warstwa Spring MVC (kontrolery,
         *   `@ControllerAdvice`, konwertery) - BEZ repozytoriow/bazy
         *   (Lesson05).
         * - `@DataJpaTest` - TYLKO warstwa JPA (encje, repozytoria,
         *   `TestEntityManager`) - BEZ kontrolerow (Lesson06).
         * - `@JsonTest` - TYLKO serializacja/deserializacja JSON
         *   (Lesson07).
         * - KAZDY wycinek AUTOMATYCZNIE konfiguruje `@MockitoBean`
         *   DLA zaleznosci Z INNYCH warstw (Lesson08) - NIE MUSISZ
         *   ich RECZNIE mockowac.
         * - REGULA KCIUKA: UZYWAJ wycinkow DLA WIEKSZOSCI testow
         *   (SZYBKIE), `@SpringBootTest` TYLKO DLA nielicznych,
         *   PRAWDZIWIE end-to-end scenariuszy (Lesson19/20).
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    @RestController
    static class GreetingController {
        @GetMapping("/hello")
        String hello() {
            return "hello";
        }
    }

    @SpringBootApplication
    static class FullApp {
        @Bean
        JdbcTemplate jdbcTemplate(javax.sql.DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @SpringBootTest(classes = FullApp.class)
    static class FullContextComparisonTest {
        @Autowired
        org.springframework.context.ApplicationContext context;

        @Test
        void fullSpringBootTestLoadsControllerAndDatabaseInfrastructureTogether() {
            // PELNY kontekst ZAWIERA i kontroler, i JdbcTemplate/DataSource - CHOCIAZ TEN
            // KONKRETNY test interesuje sie TYLKO kontrolerem.
            assertThat(context.getBean(GreetingController.class)).isNotNull();
            assertThat(context.getBean(JdbcTemplate.class)).isNotNull();

            long beanCount = context.getBeanDefinitionCount();
            System.out.println("Pelny kontekst @SpringBootTest zaladowal " + beanCount
                    + " definicji beanow - WIECEJ niz potrzebowalby test SAMEGO kontrolera (Lesson05 pokaze wycinek).");
            assertThat(beanCount).isGreaterThan(10);
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
