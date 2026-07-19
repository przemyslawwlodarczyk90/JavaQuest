package com.example.javaquest._25_unit_testing.Lesson12_TestTagsAndFiltering;

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

public class _Lesson12_TestTagsAndFiltering {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: Tagowanie testow (@Tag) i filtrowanie ich uruchamiania ===");

        /*
         * ============================================================
         * 📦 @Tag - ETYKIETA na tescie, NIE warunek (w odroznieniu od Lesson11)
         * ============================================================
         * `@Tag("fast")`/`@Tag("slow")`/`@Tag("integration")` NIE
         * DECYDUJE samo z siebie, CZY test sie wykona - to TYLKO
         * ETYKIETA. DOPIERO filtr (`-Dgroups=...`/`TagFilter` W
         * Launcher API / konfiguracja Maven Surefire) DECYDUJE, KTORE
         * tagi maja sie WYKONAC, a KTORE POMINAC.
         *
         * 🔍 TYPOWE uzycie W PRAWDZIWYCH projektach: `@Tag("unit")` NA
         * szybkich testach jednostkowych, `@Tag("integration")` NA
         * WOLNIEJSZYCH (baza/siec - powiazanie Z
         * `_26_integration_testing`) - PIPELINE CI URUCHAMIA "unit"
         * PRZY KAZDYM commicie, "integration" TYLKO PRZED mergem.
         */
        System.out.println("@Tag(\"nazwa\") = ETYKIETA na tescie. TagFilter.includeTags/excludeTags = DECYDUJE, ktore tagi faktycznie sie WYKONAJA.");

        runAllTests();
        runOnlyFastTests();
        runExcludingSlowTests();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Tag("nazwa")` MOZNA umiescic NA KLASIE (dziedziczy sie NA
         *   WSZYSTKIE metody) LUB NA POJEDYNCZEJ metodzie testowej.
         * - `TagFilter.includeTags("fast")` / `excludeTags("slow")` -
         *   programowe filtrowanie PRZEZ Launcher API (uzyte W TEJ
         *   LEKCJI).
         * - W PRAWDZIWYM projekcie Maven: `<groups>fast</groups>` /
         *   `<excludedGroups>slow</excludedGroups>` W konfiguracji
         *   `maven-surefire-plugin`, LUB flaga `-Dgroups=fast` Z linii
         *   polecen.
         * - Tagi MOZNA LACZYC wyrazeniami (`fast & !flaky`) - skladnia
         *   ZALEZY OD narzedzia (Surefire uzywa innej skladni niz
         *   `TagFilter`).
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    @Tag("account")
    static class CalculatorTaggedTest {
        @Test
        @Tag("fast")
        void additionIsFast() {
            assertThat(2 + 2).isEqualTo(4);
        }

        @Test
        @Tag("fast")
        void subtractionIsFast() {
            assertThat(5 - 3).isEqualTo(2);
        }

        @Test
        @Tag("slow")
        void simulatedSlowIntegrationCheck() throws InterruptedException {
            // W PRAWDZIWEJ integracji byloby to np. zapytanie DO bazy - TU tylko demo tagowania.
            Thread.sleep(50);
            assertThat(true).isTrue();
        }

        @Test
        @Tag("slow")
        @Tag("flaky")
        void simulatedFlakyNetworkCheck() throws InterruptedException {
            Thread.sleep(30);
            assertThat(true).isTrue();
        }
    }

    private static void runAllTests() {
        System.out.println("\n--- Uruchomienie 1: WSZYSTKIE testy (bez filtra) ---");
        run(LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(CalculatorTaggedTest.class))
                .build());
    }

    private static void runOnlyFastTests() {
        System.out.println("\n--- Uruchomienie 2: TYLKO tag \"fast\" (TagFilter.includeTags) ---");
        run(LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(CalculatorTaggedTest.class))
                .filters(TagFilter.includeTags("fast"))
                .build());
    }

    private static void runExcludingSlowTests() {
        System.out.println("\n--- Uruchomienie 3: WYKLUCZAJAC tag \"slow\" (TagFilter.excludeTags) ---");
        run(LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(CalculatorTaggedTest.class))
                .filters(TagFilter.excludeTags("slow"))
                .build());
    }

    private static void run(LauncherDiscoveryRequest request) {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println("Znalezione: " + summary.getTestsFoundCount() + ", udane: " + summary.getTestsSucceededCount());
    }
}
