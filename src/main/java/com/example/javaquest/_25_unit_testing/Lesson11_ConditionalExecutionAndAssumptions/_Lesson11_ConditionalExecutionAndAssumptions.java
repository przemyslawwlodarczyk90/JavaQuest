package com.example.javaquest._25_unit_testing.Lesson11_ConditionalExecutionAndAssumptions;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson11_ConditionalExecutionAndAssumptions {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 11: Warunkowe wykonanie testow (@EnabledOnOs) i Assumptions ===");

        /*
         * ============================================================
         * 📦 2 RODZAJE "warunkowosci" testow w JUnit 5
         * ============================================================
         * 1. ADNOTACJE `@Enabled*`/`@Disabled*` - DECYZJA PODEJMOWANA
         *    PRZED URUCHOMIENIEM testu (na etapie DISCOVERY) - test,
         *    ktory NIE spelnia warunku, jest oznaczony jako "disabled"
         *    W RAPORCIE (NIE liczy sie jako "failed", TYLKO "skipped").
         * 2. `Assumptions.assumeXxx(...)` - DECYZJA PODEJMOWANA
         *    W TRAKCIE dzialania testu (kod juz zaczal sie wykonywac) -
         *    jesli zalozenie ZAWIEDZIE, test jest PRZERYWANY jako
         *    "aborted" (TEZ NIE jako "failed").
         *
         * 🔍 KIEDY ktorego uzyc: adnotacje - GDY warunek jest znany Z
         * GORY (system operacyjny, wersja Javy, flaga konfiguracyjna).
         * `Assumptions` - GDY warunek zalezy OD CZEGOS sprawdzanego
         * DOPIERO W trakcie testu (np. dostepnosc bazy danych, sieci -
         * powiazanie Z `_26_integration_testing`).
         */
        System.out.println("@EnabledOnOs/@DisabledOnOs = decyzja PRZED uruchomieniem (skipped). Assumptions.assumeXxx = decyzja W TRAKCIE testu (aborted).");

        runAndReport(OsAndJreConditionsTest.class);
        runAndReport(SystemPropertyConditionTest.class);
        runAndReport(AssumptionsShowcaseTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EnabledOnOs(OS.WINDOWS)` / `@DisabledOnOs(OS.LINUX)` -
         *   warunkowanie SYSTEMEM operacyjnym.
         * - `@EnabledOnJre(JRE.JAVA_21)` - warunkowanie WERSJA Javy.
         * - `@EnabledIfSystemProperty(named = "...", matches = "...")` -
         *   warunkowanie WLASCIWOSCIA systemowa (`-Dklucz=wartosc`).
         * - `Assumptions.assumeTrue(warunek)` - JESLI `false`, test jest
         *   PRZERYWANY (status "aborted", NIE "failed" - odroznia
         *   "test nie mial szansy sie wykonac" OD "test wykryl blad").
         * - `Assumptions.assumingThat(warunek, () -> { ... })` -
         *   WARUNKOWY FRAGMENT testu (reszta testu I TAK sie wykonuje).
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    static class OsAndJreConditionsTest {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void onlyRunsOnWindows() {
            System.out.println("Ten test URUCHAMIA SIE tylko na Windows.");
            assertThat(System.getProperty("os.name")).containsIgnoringCase("windows");
        }

        @Test
        @DisabledOnOs(OS.LINUX)
        void neverRunsOnLinux() {
            System.out.println("Ten test NIGDY nie uruchamia sie na Linuxie.");
            assertThat(true).isTrue();
        }

        @Test
        @EnabledOnJre(JRE.JAVA_21)
        void onlyRunsOnJava21() {
            // UWAGA: projekt celuje w Jave 21 (--release 21), ALE maszyna deweloperska MOZE
            // miec zainstalowany NOWSZY JDK (np. 25) do faktycznego URUCHAMIANIA (java -version)
            // - ten test MOZE wiec zostac "skipped" w praktyce, co jest ZAMIERZONYM przykladem
            // roznicy miedzy wersja DOCELOWA (release) a wersja URUCHOMIENIOWA (runtime).
            System.out.println("Ten test URUCHAMIA SIE tylko pod JRE 21.");
            assertThat(true).isTrue();
        }
    }

    static class SystemPropertyConditionTest {
        @Test
        @EnabledIfSystemProperty(named = "os.arch", matches = ".*")
        void alwaysRunsBecauseOsArchAlwaysExists() {
            // os.arch ISTNIEJE zawsze - ten test demonstruje SKLADNIE,
            // NIE PRAWDZIWE warunkowanie srodowiskowe.
            System.out.println("Wlasciwosc systemowa os.arch = " + System.getProperty("os.arch"));
            assertThat(System.getProperty("os.arch")).isNotBlank();
        }
    }

    static class AssumptionsShowcaseTest {
        @Test
        void assumeTrueSkipsRestOfTestWhenFalse() {
            String environment = "local";
            Assumptions.assumeTrue(environment.equals("local"), "Test wymaga srodowiska 'local'");
            System.out.println("Zalozenie SPELNIONE - reszta testu sie wykonuje.");
            assertThat(environment).isEqualTo("local");
        }

        @Test
        void assumingThatRunsPartConditionally() {
            int discountPercent = 10;
            assertThat(discountPercent).isPositive();

            Assumptions.assumingThat(discountPercent > 5, () -> {
                System.out.println("Zniozka > 5% - dodatkowe sprawdzenie WYKONANE.");
                assertThat(discountPercent).isLessThanOrEqualTo(100);
            });

            // TA linia I TAK sie wykona, niezaleznie od wyniku assumingThat.
            System.out.println("Reszta testu WYKONUJE SIE zawsze po assumingThat.");
        }

        @Test
        void assumeFalseAbortsWhenConditionTrue() {
            boolean maintenanceMode = false;
            Assumptions.assumeFalse(maintenanceMode, "Pomijamy test w trybie konserwacji");
            System.out.println("Tryb konserwacji WYLACZONY - test dziala normalnie.");
            assertThat(maintenanceMode).isFalse();
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
        System.out.println(testClass.getSimpleName() + " -> udane: " + summary.getTestsSucceededCount()
                + "/" + summary.getTestsFoundCount() + ", pominiete: " + summary.getTestsSkippedCount());
    }
}
