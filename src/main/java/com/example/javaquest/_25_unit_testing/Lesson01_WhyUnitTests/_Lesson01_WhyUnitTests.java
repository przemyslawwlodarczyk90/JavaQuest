package com.example.javaquest._25_unit_testing.Lesson01_WhyUnitTests;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class _Lesson01_WhyUnitTests {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: Po co testy jednostkowe? ===");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAL - NOWY WZORZEC URUCHAMIANIA LEKCJI
         * ============================================================
         * Cala reszta kursu miala main(), ktory COS ROBIL I WYPISYWAL
         * wynik. TEN rozdzial jest O PISANIU TESTOW - wiec teoria PISZE
         * PRAWDZIWE klasy Z adnotacja `@Test` (JUnit 5, biblioteka
         * `spring-boot-starter-test` byla juz zaleznoscia projektu OD
         * DAWNA, ale W SCOPE `test` - TERAZ dodana TEZ bez tego scope,
         * zeby dzialala w `src/main/java` jak reszta kursu). main()
         * URUCHAMIA te testy PROGRAMOWO przez JUnit Platform Launcher
         * API (`LauncherFactory`) - DOKLADNIE TAK, jak robi to Twoje IDE
         * pod maska, gdy klikasz "Run Test". To TEN SAM duch "embeduj i
         * NAPRAWDE uruchom", ktory juz znasz Z Anta (`_11_buildtools`)
         * czy PMD/SpotBugs (`_16_clean_code/Lesson20`).
         */
        System.out.println("Testy JEDNOSTKOWE = najmniejsza, najszybsza warstwa testow - sprawdzaja POJEDYNCZA jednostke kodu (metode/klase) W IZOLACJI.");

        demonstrateRunningRealJUnitTests();

        /*
         * ============================================================
         * 🔹 DLACZEGO TESTY - KOSZT BLEDU ROSNIE Z CZASEM
         * ============================================================
         * Blad znaleziony PODCZAS pisania kodu kosztuje SEKUNDY (jeszcze
         * pamietasz kontekst). Ten sam blad znaleziony PRZEZ testera
         * TYDZIEN pozniej kosztuje GODZINY. Znaleziony PRZEZ klienta NA
         * PRODUKCJI - moze kosztowac REPUTACJE firmy. Testy jednostkowe
         * LAPIA bledy NAJWCZESNIEJ, jak sie da - W TRAKCIE pisania kodu,
         * automatycznie, bez czlowieka klikajacego w aplikacje.
         *
         * 🔍 PIRAMIDA TESTOW
         * ============================================================
         * Testy jednostkowe (WIELE, SZYBKIE, TANIE) -> Testy
         * integracyjne (`_26_integration_testing`, MNIEJ, WOLNIEJSZE) ->
         * Testy end-to-end (NAJMNIEJ, NAJWOLNIEJSZE, NAJDROZSZE W
         * utrzymaniu). Wiekszosc testow W dobrze zaprojektowanym
         * projekcie POWINNA byc NA DOLE piramidy.
         *
         * 🔹 ZASADA F.I.R.S.T. - CECHY DOBREGO TESTU JEDNOSTKOWEGO
         * ============================================================
         * - Fast (SZYBKI) - milisekundy, NIE sekundy.
         * - Independent (NIEZALEZNY) - kolejnosc uruchamiania NIE MA
         *   znaczenia, test NIE zalezy OD innego testu.
         * - Repeatable (POWTARZALNY) - TEN SAM wynik ZA KAZDYM razem,
         *   NIEZALEZNIE OD srodowiska (bez zaleznosci OD sieci/zegara).
         * - Self-validating (SAMOWERYFIKUJACY) - test SAM stwierdza
         *   PASS/FAIL (asercja), NIE wymaga recznego czytania logow.
         * - Timely (NA CZAS) - pisany BLISKO W CZASIE Z kodem, KTORY
         *   testuje (idealnie: PRZED LUB zaraz PO).
         */
        System.out.println("F.I.R.S.T. = Fast, Independent, Repeatable, Self-validating, Timely - 5 cech DOBREGO testu jednostkowego.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Testy jednostkowe = NAJTANSZA, NAJSZYBSZA warstwa piramidy
         *   testow - testuja JEDNA jednostke kodu W IZOLACJI.
         * - `@Test` (JUnit 5) OZNACZA metode jako test - WYKRYWANA I
         *   URUCHAMIANA automatycznie przez silnik testowy.
         * - `assertEquals`/`assertTrue` (I inne `Assertions.*`) to
         *   WBUDOWANE asercje JUnit 5 - Lesson05 pokaze LEPSZA
         *   alternatywe (AssertJ).
         * - `LauncherFactory`/`LauncherDiscoveryRequestBuilder` -
         *   PROGRAMOWE uruchamianie testow (TO, co robi Twoje IDE) -
         *   uzywane W TEJ lekcji, zeby main() mogl "naprawde
         *   uruchomic" testy I pokazac wynik, jak reszta kursu.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    static class Calculator {
        int add(int a, int b) {
            return a + b;
        }

        int divide(int a, int b) {
            return a / b;
        }
    }

    static class CalculatorTest {
        @Test
        void addsTwoPositiveNumbers() {
            Calculator calculator = new Calculator();
            assertEquals(5, calculator.add(2, 3));
        }

        @Test
        void addsNegativeNumbers() {
            Calculator calculator = new Calculator();
            assertTrue(calculator.add(-2, -3) == -5);
        }

        @Test
        void deliberatelyFailingTest() {
            // CELOWO zly test - demonstruje, JAK WYGLADA raport PORAZKI, NIE TYLKO sukcesu.
            Calculator calculator = new Calculator();
            assertEquals(100, calculator.add(2, 3), "2 + 3 CELOWO porownane Z blednym oczekiwaniem 100");
        }
    }

    private static void demonstrateRunningRealJUnitTests() {
        System.out.println("\n=== PRAWDZIWE testy JUnit 5, URUCHOMIONE programowo przez Launcher API ===");

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(CalculatorTest.class))
                .build();

        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        PrintWriter writer = new PrintWriter(System.out);
        summary.printTo(writer);
        summary.printFailuresTo(writer);
        writer.flush();

        System.out.println("Znalezione testy: " + summary.getTestsFoundCount()
                + ", udane: " + summary.getTestsSucceededCount()
                + ", nieudane: " + summary.getTestsFailedCount()
                + " (oczekiwane: 3 znalezione, 2 udane, 1 nieudany - CELOWO, patrz CalculatorTest.deliberatelyFailingTest)");
    }
}
