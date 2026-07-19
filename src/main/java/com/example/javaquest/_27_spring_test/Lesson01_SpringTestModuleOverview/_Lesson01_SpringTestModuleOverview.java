package com.example.javaquest._27_spring_test.Lesson01_SpringTestModuleOverview;

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
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson01_SpringTestModuleOverview {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: Modul Spring Test - co jest w spring-boot-starter-test ===");

        /*
         * ============================================================
         * 📦 CALY rozdzial _25/_26 UZYWAL "goldego" JUnit5/AssertJ/Mockito - TERAZ dochodzi SPRING
         * ============================================================
         * Kursant MA JUZ `_25_unit_testing` (JUnit5/AssertJ/Mockito -
         * fundamenty) i `_26_integration_testing` (Testcontainers/
         * WireMock - PRAWDZIWE zaleznosci). TEN rozdzial UCZY, JAK
         * Spring Boot AUTOMATYZUJE testowanie WLASNYCH APLIKACJI
         * Springowych (`_20_spring_core`-`_24_spring_security`) -
         * ZAMIAST recznie budowac `ApplicationContext` W kazdym
         * tescie, Spring Test DAJE GOTOWE adnotacje.
         *
         * 🔍 `spring-boot-starter-test` (JUZ obecny W `pom.xml`, scope
         * `test`) TO "STARTER" - PAKIET WIELU bibliotek NARAZ:
         * - `spring-test`/`spring-boot-test`/`spring-boot-test-
         *   autoconfigure` - RDZEN (TEN rozdzial).
         * - JUnit 5, AssertJ, Mockito - JUZ znane Z `_25`.
         * - Hamcrest, JSONassert, `jayway.jsonpath` (JsonPath) -
         *   dodatkowe narzedzia DO asercji NA JSON (Lesson07).
         * - `xmlunit-core` - asercje NA XML (rzadziej uzywane W
         *   tym kursie).
         */
        System.out.println("spring-boot-starter-test = PAKIET (JUnit5+AssertJ+Mockito+JsonPath+Hamcrest+spring-test/spring-boot-test) - fundament rozdzialu.");

        runAndReport(SpringContextSmokeTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `spring-test` (modul Spring Framework) - `SpringExtension`,
         *   `TestContextManager`, cachowanie kontekstu (Lesson17).
         * - `spring-boot-test` - `@SpringBootTest`, `TestRestTemplate`
         *   (Lesson02/12).
         * - `spring-boot-test-autoconfigure` - "wycinki" testowe
         *   (`@WebMvcTest`/`@DataJpaTest`/`@JsonTest` - Lesson04-07) -
         *   URUCHAMIAJA TYLKO CZESC kontekstu, NIE CALA aplikacje.
         * - `@ExtendWith(SpringExtension.class)` - NAJBARDZIEJ
         *   PODSTAWOWA integracja Spring+JUnit5 (`@SpringBootTest`
         *   JUZ NIESIE TA adnotacje "pod maska" - rzadko trzeba
         *   pisac ja RECZNIE).
         * - WAZNE: W TYM kursie testy ZYJA W `src/main/java`
         *   (`_25_unit_testing` juz to wyjasnil) - `spring-test`
         *   dodany DO `pom.xml` BEZ scope `test`, zeby byl widoczny.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    @SpringBootApplication
    static class MinimalApp {
    }

    @ExtendWith(SpringExtension.class)
    @ContextConfiguration(classes = MinimalApp.class)
    static class SpringContextSmokeTest {

        // SpringExtension WSTRZYKUJE kontekst DO pola testowego - NAJBARDZIEJ "surowa"
        // (podstawowa) integracja Spring+JUnit5, BEZ pelnego rusztowania @SpringBootTest (Lesson02).
        @Autowired
        ApplicationContext context;

        @Test
        void springContextLoadsSuccessfullyUsingSpringExtension() {
            assertThat(context).isNotNull();
            assertThat(context.getBeanDefinitionCount()).isGreaterThan(0);
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
