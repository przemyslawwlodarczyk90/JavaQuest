package com.example.javaquest._25_unit_testing.Lesson03_FirstTestAndBuiltInAssertions;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class _Lesson03_FirstTestAndBuiltInAssertions {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 3: Kompletny przeglad wbudowanych asercji JUnit 5 ===");

        /*
         * ============================================================
         * 📦 Assertions.* - WSZYSTKO, CZEGO POTRZEBUJESZ, ZANIM POZNASZ AssertJ
         * ============================================================
         * Klasa `org.junit.jupiter.api.Assertions` DAJE komplet
         * statycznych metod DO sprawdzania oczekiwan. Lesson05 pokaze
         * AssertJ - CZYTELNIEJSZA, PLYNNA alternatywe - ale WARTO
         * znac WBUDOWANE asercje, bo SPOTKASZ JE W kazdym projekcie
         * JUnit 5 (czesc zespolow CELOWO NIE dodaje AssertJ).
         */
        System.out.println("Assertions.* = WBUDOWANY zestaw sprawdzen JUnit 5 - fundament, ZANIM poznasz plynniejsza skladnie AssertJ (Lesson05).");

        runAndReport(AssertionShowcaseTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `assertEquals`/`assertNotEquals` - rownosc WARTOSCI (`.equals()`).
         * - `assertSame`/`assertNotSame` - TOZSAMOSC referencji (`==`).
         * - `assertTrue`/`assertFalse` - warunek logiczny.
         * - `assertNull`/`assertNotNull` - sprawdzenie `null`.
         * - `assertArrayEquals` - porownanie TABLIC element-po-elemencie.
         * - `assertThrows` - oczekiwany WYJATEK (zwraca GO, MOZNA dalej
         *   sprawdzac tresc komunikatu).
         * - `assertTimeout` - test MUSI zakonczyc sie W ZADANYM czasie.
         * - `assertAll` - GRUPUJE WIELE asercji - WSZYSTKIE SA
         *   sprawdzone, NAWET jesli PIERWSZA zawiedzie (w odroznieniu
         *   OD pojedynczych asercji, KTORE PRZERYWAJA test NA
         *   PIERWSZYM niepowodzeniu).
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    static class AssertionShowcaseTest {
        @Test
        void equalityAssertions() {
            assertEquals(4, 2 + 2);
            assertNotSame(new Object(), new Object());
            String s1 = "shared";
            String s2 = s1;
            assertSame(s1, s2);
        }

        @Test
        void booleanAndNullAssertions() {
            assertTrue(5 > 3);
            assertFalse(3 > 5);
            assertNull(null);
            assertNotNull("nie null");
        }

        @Test
        void arrayAssertions() {
            int[] expected = {1, 2, 3};
            int[] actual = {1, 2, 3};
            assertArrayEquals(expected, actual);
        }

        @Test
        void exceptionAssertions() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                throw new IllegalArgumentException("zly argument");
            });
            assertEquals("zly argument", exception.getMessage());
        }

        @Test
        void timeoutAssertion() {
            assertTimeout(Duration.ofMillis(500), () -> Thread.sleep(10));
        }

        @Test
        void groupedAssertionsWithAssertAll() {
            // assertAll SPRAWDZA WSZYSTKIE asercje - raport bledu pokazalby WSZYSTKIE
            // niepowodzenia NARAZ, NIE tylko pierwsze napotkane (w odroznieniu OD zwyklych asercji).
            assertAll("Grupa powiazanych sprawdzen",
                    () -> assertEquals(2, 1 + 1),
                    () -> assertTrue(10 > 5),
                    () -> assertNotNull("wartosc"));
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
        summary.printTo(new PrintWriter(System.out));
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println("Udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount() + " (oczekiwane: 6/6)");
    }
}
