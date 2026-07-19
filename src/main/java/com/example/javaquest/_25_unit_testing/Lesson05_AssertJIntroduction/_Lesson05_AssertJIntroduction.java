package com.example.javaquest._25_unit_testing.Lesson05_AssertJIntroduction;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson05_AssertJIntroduction {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 5: AssertJ - PLYNNE, CZYTELNE asercje ===");

        /*
         * ============================================================
         * 📦 assertThat(x).metoda1().metoda2()... - LANCUCH, NIE OSOBNE WYWOLANIA
         * ============================================================
         * Lesson03 pokazal `Assertions.assertEquals(oczekiwane, rzeczywiste)` -
         * DZIALA, ale KOLEJNOSC argumentow LATWO POMYLIC, a SPRAWDZENIE
         * KILKU WLASCIWOSCI JEDNEGO obiektu WYMAGA WIELU OSOBNYCH linii.
         * AssertJ DAJE JEDEN punkt wejscia - `assertThat(wartosc)` -
         * KTORY zwraca obiekt Z METODAMI DOPASOWANYMI DO TYPU (String
         * MA `.startsWith()`, Integer MA `.isGreaterThan()`...) -
         * MOZNA je LACZYC W LANCUCH, CZYTA SIE JAK ZDANIE PO ANGIELSKU.
         */
        System.out.println("assertThat(wartosc).metoda1().metoda2() - LANCUCH czytelnych sprawdzen, ZAMIAST osobnych assertXxx(...).");

        runAndReport(AssertJShowcaseTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `assertThat(wartosc)` - JEDEN, UNIWERSALNY punkt wejscia
         *   (W odroznieniu OD `assertEquals`/`assertTrue`/`assertNull`...
         *   jako OSOBNYCH metod).
         * - Metody DOPASOWANE DO TYPU: String -> `.startsWith()`/
         *   `.contains()`/`.isEqualToIgnoringCase()`; liczby ->
         *   `.isGreaterThan()`/`.isBetween()`; boolean -> `.isTrue()`.
         * - `.as("opis")` - WLASNY opis, WYSWIETLANY W komunikacie bledu
         *   ZAMIAST domyslnego.
         * - `assertThatThrownBy(...)` - PLYNNY odpowiednik `assertThrows`.
         * - Kolejnosc argumentow NIGDY nie budzi watpliwosci - `assertThat(
         *   RZECZYWISTA_WARTOSC).isEqualTo(OCZEKIWANA)` - ZAWSZE
         *   rzeczywista NAJPIERW.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    static class AssertJShowcaseTest {
        @Test
        void stringAssertions() {
            String greeting = "Witaj, JavaQuest!";
            assertThat(greeting)
                    .startsWith("Witaj")
                    .endsWith("!")
                    .contains("JavaQuest")
                    .hasSize(17);
        }

        @Test
        void numberAssertions() {
            int age = 25;
            assertThat(age)
                    .isPositive()
                    .isGreaterThan(18)
                    .isBetween(20, 30)
                    .isNotEqualTo(0);
        }

        @Test
        void booleanAssertionWithCustomDescription() {
            boolean isAdult = true;
            assertThat(isAdult).as("uzytkownik powinien byc pelnoletni").isTrue();
        }

        @Test
        void exceptionAssertionWithAssertThatThrownBy() {
            assertThatThrownBy(() -> {
                throw new IllegalStateException("nieprawidlowy stan");
            })
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("nieprawidlowy stan");
        }

        @Test
        void chainedAssertionsOnSameValue() {
            // JEDEN lancuch ZAMIAST 4 osobnych wywolan assertEquals/assertTrue z Lesson03.
            String email = "kontakt@javaquest.pl";
            assertThat(email)
                    .isNotBlank()
                    .contains("@")
                    .endsWith(".pl")
                    .doesNotContain(" ");
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
        System.out.println("Udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount() + " (oczekiwane: 5/5)");
    }
}
