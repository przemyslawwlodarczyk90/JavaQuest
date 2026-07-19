package com.example.javaquest._25_unit_testing.Lesson08_ParameterizedTests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson08_ParameterizedTests {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: @ParameterizedTest - JEDEN test, WIELE danych ===");

        /*
         * ============================================================
         * 📦 KONIEC pisania 5 PRAWIE identycznych metod @Test
         * ============================================================
         * Lesson03 Zadanie 19 kazal Ci NAPISAC RECZNIE 5 osobnych
         * metod `@Test` sprawdzajacych TA SAMA logike Z ROZNYMI danymi.
         * `@ParameterizedTest` ROZWIAZUJE TO - JEDNA metoda, WYKONYWANA
         * WIELOKROTNIE Z ROZNYMI ARGUMENTAMI, KAZDE wywolanie LICZONE
         * jest jako OSOBNY test (WLASNY wpis W raporcie, WLASNY
         * PASS/FAIL).
         */
        System.out.println("@ParameterizedTest = JEDNA metoda testowa URUCHAMIANA WIELOKROTNIE Z ROZNYMI danymi - KAZDE wywolanie jako OSOBNY test W raporcie.");

        runAndReport(ParameterizedShowcaseTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@ValueSource(ints = {...}/strings = {...})` - PROSTA
         *   lista wartosci JEDNEGO typu.
         * - `@CsvSource({"a,b", "c,d"})` - WIELE parametrow NA
         *   wywolanie, oddzielone PRZECINKIEM.
         * - `@MethodSource("nazwaMetody")` - NAJELASTYCZNIEJSZY -
         *   dowolnie ZLOZONE dane (obiekty, `Stream<Arguments>`).
         * - KAZDE wywolanie parametryzowane LICZY SIE jako OSOBNY test -
         *   `TestExecutionSummary.getTestsFoundCount()` ROSNIE
         *   proporcjonalnie DO liczby zestawow danych.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    static boolean isPositive(int number) {
        return number > 0;
    }

    static boolean isPalindrome(String text) {
        String reversed = new StringBuilder(text).reverse().toString();
        return text.equalsIgnoreCase(reversed);
    }

    static int add(int a, int b) {
        return a + b;
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> additionProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(2, 3, 5),
                org.junit.jupiter.params.provider.Arguments.of(-1, 1, 0),
                org.junit.jupiter.params.provider.Arguments.of(0, 0, 0),
                org.junit.jupiter.params.provider.Arguments.of(100, 200, 300)
        );
    }

    static class ParameterizedShowcaseTest {
        @ParameterizedTest
        @ValueSource(ints = {1, 5, 100, 999})
        void valueSourceWithPositiveNumbers(int number) {
            assertThat(isPositive(number)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"kajak", "oko", "radar"})
        void valueSourceWithPalindromes(String word) {
            assertThat(isPalindrome(word)).isTrue();
        }

        @ParameterizedTest
        @CsvSource({
                "2, 3, 5",
                "-1, 1, 0",
                "0, 0, 0",
                "10, -10, 0"
        })
        void csvSourceForAddition(int a, int b, int expectedSum) {
            assertThat(add(a, b)).isEqualTo(expectedSum);
        }

        @ParameterizedTest
        @MethodSource("com.example.javaquest._25_unit_testing.Lesson08_ParameterizedTests._Lesson08_ParameterizedTests#additionProvider")
        void methodSourceForAddition(int a, int b, int expectedSum) {
            assertThat(add(a, b)).isEqualTo(expectedSum);
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
        System.out.println("Znalezione wywolania testowe: " + summary.getTestsFoundCount()
                + ", udane: " + summary.getTestsSucceededCount()
                + " (oczekiwane: 4+3+4+4=15 znalezionych, 15 udanych)");
    }
}
