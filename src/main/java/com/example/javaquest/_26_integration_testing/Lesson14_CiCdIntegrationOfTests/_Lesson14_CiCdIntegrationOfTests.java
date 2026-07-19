package com.example.javaquest._26_integration_testing.Lesson14_CiCdIntegrationOfTests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson14_CiCdIntegrationOfTests {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 14: Testy w pipeline CI/CD ===");

        /*
         * ============================================================
         * 📦 WIELOETAPOWY pipeline - RÓZNE testy NA RÓZNYCH etapach
         * ============================================================
         * Powiazanie Z `_11_buildtools` (Ant/Maven/Gradle URUCHAMIAJA
         * BUILD) + `_25_unit_testing/Lesson12` (`@Tag`) - REALNY
         * pipeline CI (GitHub Actions/GitLab CI/Jenkins) TYPOWO MA
         * WIELE etapow, KAZDY Z INNYM ZESTAWEM testow:
         * 1. "fast" (jednostkowe, KAZDY commit/PR) - SEKUNDY.
         * 2. "integration" (TEN rozdzial, PRZED mergem DO main) -
         *    MINUTY.
         * 3. "e2e"/smoke (PO wdrozeniu NA srodowisko staging) -
         *    NAJDLUZSZE, NAJRZADSZE.
         *
         * 🔍 PONIZEJ symulujemy DOKLADNIE taki 2-etapowy pipeline
         * (fast + integration) PRZEZ Launcher API +
         * `TagFilter` (`_25_unit_testing/Lesson12`), wypisujac WYNIK
         * KAZDEGO etapu OSOBNO - DOKLADNIE tak, jak wygladalby LOG
         * REALNEGO pipeline'u CI.
         */
        System.out.println("Pipeline CI = WIELE etapow, KAZDY Z INNYM zestawem testow (fast->integration->e2e), COraZ RZADZIEJ uruchamiany.");

        runPipelineStage("ETAP 1: FAST (kazdy commit)", "fast");
        runPipelineStage("ETAP 2: INTEGRATION (przed mergem)", "integration");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - "fail fast" - ETAP "fast" URUCHAMIA sie NAJPIERW - JESLI
         *   ZAWIEDZIE, pipeline PRZERYWA sie NATYCHMIAST (NIE MA sensu
         *   URUCHAMIAC WOLNIEJSZYCH testow, GDY podstawowa logika juz
         *   nie dziala).
         * - ETAP "integration" (TEN rozdzial) URUCHAMIA SIE RZADZIEJ
         *   (np. TYLKO PRZY PR do main), bo jest WOLNIEJSZY
         *   (Lesson02) I MOZE WYMAGAC Dockera/WireMock/prawdziwych
         *   zasobow.
         * - REALNA konfiguracja (GitHub Actions, przyklad YAML):
         *   `mvn test -Dgroups=fast` (etap 1), `mvn test
         *   -Dgroups=integration` (etap 2, W OSOBNYM jobie, PO
         *   sukcesie etapu 1).
         * - Powiazanie Z `_11_buildtools/Lesson08_AntAdvanced`+
         *   `Lesson16_MavenTestingAndCoverage` - TEN SAM `mvn test`
         *   uzyty W CALYM kursie, TERAZ Z FLAGA `-Dgroups`
         *   filtrujaca WEDLUG tagow.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    @Tag("fast")
    static class UnitLikeChecksTest {
        @Test
        void additionWorks() {
            assertThat(2 + 2).isEqualTo(4);
        }

        @Test
        void stringConcatenationWorks() {
            assertThat("a" + "b").isEqualTo("ab");
        }
    }

    @Tag("integration")
    static class IntegrationLikeChecksTest {
        @Test
        void simulatedDatabaseCheck() throws Exception {
            try (var connection = java.sql.DriverManager.getConnection(
                    "jdbc:h2:mem:lesson14;DB_CLOSE_DELAY=-1", "sa", "");
                 var statement = connection.createStatement()) {
                statement.execute("CREATE TABLE ping (id INT)");
                statement.execute("INSERT INTO ping VALUES (1)");
                assertThat(true).isTrue();
            }
        }
    }

    private static void runPipelineStage(String stageName, String tag) {
        System.out.println("\n--- " + stageName + " (tag: \"" + tag + "\") ---");
        long start = System.currentTimeMillis();

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        DiscoverySelectors.selectClass(UnitLikeChecksTest.class),
                        DiscoverySelectors.selectClass(IntegrationLikeChecksTest.class))
                .filters(TagFilter.includeTags(tag))
                .build();

        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));

        long elapsedMillis = System.currentTimeMillis() - start;
        boolean stagePassed = summary.getTestsFailedCount() == 0;

        System.out.println("Wynik etapu: " + (stagePassed ? "SUKCES" : "PORAZKA")
                + " (" + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount()
                + " testow, " + elapsedMillis + " ms)");

        if (!stagePassed) {
            throw new IllegalStateException("Pipeline PRZERWANY na etapie: " + stageName);
        }
    }
}
