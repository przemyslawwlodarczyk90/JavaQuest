package com.example.javaquest._25_unit_testing.Lesson02_JUnit5ArchitectureOverview;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class _Lesson02_JUnit5ArchitectureOverview {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: Architektura JUnit 5 - Platform, Jupiter, Vintage ===");

        /*
         * ============================================================
         * 📦 JUNIT 5 TO 3 WSPOLPRACUJACE PROJEKTY, NIE JEDNA BIBLIOTEKA
         * ============================================================
         * "JUnit 5" (nazwa marketingowa) SKLADA SIE Z 3 warstw:
         *
         * 1) JUnit PLATFORM - FUNDAMENT. Definiuje `TestEngine` (interfejs
         *    silnika testowego) I `Launcher` (URUCHAMIA silniki, ZBIERA
         *    wyniki) - TO wlasnie uzylismy W Lesson01
         *    (`LauncherFactory.create()`).
         * 2) JUnit JUPITER - NOWE API (`@Test`, `@BeforeEach`,
         *    `Assertions`...), KTOREGO uzywamy W TYM rozdziale - to
         *    WLASNY `TestEngine` (`JupiterTestEngine`), URUCHAMIANY
         *    PRZEZ Platform.
         * 3) JUnit VINTAGE - INNY `TestEngine`, URUCHAMIAJACY STARE
         *    testy JUnit 3/4 (`@org.junit.Test`, STARY pakiet) - DZIEKI
         *    NIEMU projekt MOZE miec RAZEM stare I nowe testy PODCZAS
         *    stopniowej migracji. TEN kurs NIE MA `junit-vintage-engine`
         *    NA classpath (zweryfikowane PONIZEJ) - piszemy WYLACZNIE
         *    Jupiter.
         */
        System.out.println("Platform (fundament, Launcher) + Jupiter (nowe API, @Test) + Vintage (stare testy JUnit 3/4) = 'JUnit 5'.");

        demonstrateDiscoveredTestEngines();
        demonstrateJupiterEngineRunsOurTest();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Launcher` (Platform) NIC SAM nie wie O `@Test` - DELEGUJE
         *   DO zarejestrowanych `TestEngine` (Jupiter/Vintage/inne).
         * - `ServiceLoader` (mechanizm Javy, `_02_oop`/`_14_advancedjava/
         *   Lesson26`) automatycznie ZNAJDUJE silniki NA classpath -
         *   `junit-jupiter-engine` jest transytywna zaleznoscia
         *   `junit-jupiter` (dodanego W `pom.xml` W tej lekcji), WIEC
         *   Jupiter DZIALA "z automatu".
         * - Ta architektura (PLUGIN'owe silniki) POZWALA na WLASNE,
         *   NIESTANDARDOWE `TestEngine` (np. JUnit 5 wspiera Cucumber,
         *   Spock jako DODATKOWE silniki) - Platform NIE jest
         *   ZWIAZANY Z JEDNYM stylem pisania testow.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateDiscoveredTestEngines() {
        System.out.println("\n=== ServiceLoader - JAKIE TestEngine SA NA classpath (BEZ Vintage) ===");

        ServiceLoader<TestEngine> engines = ServiceLoader.load(TestEngine.class);
        for (TestEngine engine : engines) {
            System.out.println("Znaleziony silnik: " + engine.getId() + " (" + engine.getClass().getName() + ")");
        }
        System.out.println("-> `junit-vintage-engine` CELOWO NIE jest zaleznoscia tego projektu - piszemy WYLACZNIE nowym API Jupiter.");
    }

    static class SimpleTest {
        @Test
        void jupiterEngineExecutesThisMethod() {
            assertEquals(4, 2 + 2);
        }
    }

    private static void demonstrateJupiterEngineRunsOurTest() {
        System.out.println("\n=== JupiterTestEngine FAKTYCZNIE wykonuje nasz test ===");

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(SimpleTest.class))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printTo(new PrintWriter(System.out));
        System.out.println("Testy udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount() + " (oczekiwane: 1/1)");
    }
}
