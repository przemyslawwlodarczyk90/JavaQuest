package com.example.javaquest._25_unit_testing.Lesson17_TestingExceptionsAndEdgeCases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson17_TestingExceptionsAndEdgeCases {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 17: Testowanie wyjatkow i przypadkow brzegowych ===");

        /*
         * ============================================================
         * 📦 "Happy path" TO ZA MALO - przypadki BRZEGOWE ujawniaja BUGI
         * ============================================================
         * Test SPRAWDZAJACY TYLKO "normalne" dane (typowa wartosc,
         * poprawny format) TESTUJE TYLKO CZESC zachowania kodu. Bledy
         * NAJCZESCIEJ ukrywaja sie NA GRANICACH: `null`, PUSTY String,
         * ZERO, WARTOSC UJEMNA, MAKSYMALNA/MINIMALNA wartosc typu,
         * PUSTA kolekcja, JEDEN element (zamiast wielu).
         *
         * 🔍 Ta lekcja LACZY 2 nurty JUZ POZNANE W tym kursie:
         * `_01_fundamentals/Lesson16_Exceptions` (JAK dzialaja
         * wyjatki) + `_25_unit_testing/Lesson07` (JAK je testowac
         * AssertJ) - TERAZ Z NACISKIEM NA SYSTEMATYCZNE MYSLENIE O
         * PRZYPADKACH BRZEGOWYCH, NIE TYLKO O SKLADNI asercji.
         */
        System.out.println("Happy path to za malo - PRZYPADKI BRZEGOWE (null/pusty/zero/ujemny/max/min) najczesciej ujawniaja bledy.");

        runAndReport(EdgeCaseTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - LISTA KONTROLNA przypadkow brzegowych
         * ============================================================
         * DLA KAZDEGO parametru metody rozwaz:
         * - `null` (jesli typ referencyjny) - CO POWINNO sie stac?
         * - PUSTY String/kolekcja - traktowany jak "brak danych"?
         * - WARTOSC ZERO - graniczna, CZESTO zapomniana.
         * - WARTOSC UJEMNA (jesli logicznie NIE powinna wystapic).
         * - `Integer.MAX_VALUE`/`MIN_VALUE` - PRZEPELNIENIE
         *   (`_01_fundamentals/Lesson16` - `ArithmeticException`
         *   PRZY `Math.addExact`, ALBO CICHE "wrap-around" BEZ
         *   sprawdzenia).
         * - Znaki SPECJALNE/Unicode W Stringach.
         * - JEDEN element W kolekcji (odroznia OD "wielu" logiki).
         * - GRANICE zakresu (np. "0-100%": test 0, 100, -1, 101).
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    static class InvalidDiscountException extends RuntimeException {
        InvalidDiscountException(String message) {
            super(message);
        }
    }

    static class PriceCalculator {

        double applyDiscount(double price, int discountPercent) {
            if (price < 0) {
                throw new IllegalArgumentException("Cena nie moze byc ujemna: " + price);
            }
            if (discountPercent < 0 || discountPercent > 100) {
                throw new InvalidDiscountException("Znizka musi byc w zakresie 0-100: " + discountPercent);
            }
            return price - (price * discountPercent / 100.0);
        }

        String normalizeCouponCode(String rawCode) {
            if (rawCode == null || rawCode.isBlank()) {
                throw new IllegalArgumentException("Kod kuponu nie moze byc pusty");
            }
            return rawCode.trim().toUpperCase();
        }

        int sumQuantities(List<Integer> quantities) {
            if (quantities == null || quantities.isEmpty()) {
                return 0;
            }
            return quantities.stream().mapToInt(Integer::intValue).sum();
        }

        int safeAdd(int a, int b) {
            return Math.addExact(a, b);
        }
    }

    static class EdgeCaseTest {
        private final PriceCalculator calculator = new PriceCalculator();

        // --- GRANICE zakresu znizki (0-100) ---
        @Test
        void discountAtLowerBoundZeroIsValid() {
            assertThat(calculator.applyDiscount(100.0, 0)).isEqualTo(100.0);
        }

        @Test
        void discountAtUpperBound100MeansFree() {
            assertThat(calculator.applyDiscount(100.0, 100)).isZero();
        }

        @Test
        void discountBelowLowerBoundThrows() {
            assertThatThrownBy(() -> calculator.applyDiscount(100.0, -1))
                    .isInstanceOf(InvalidDiscountException.class);
        }

        @Test
        void discountAboveUpperBoundThrows() {
            assertThatThrownBy(() -> calculator.applyDiscount(100.0, 101))
                    .isInstanceOf(InvalidDiscountException.class);
        }

        // --- PRICE = 0 - graniczna wartosc, latwo pominac ---
        @Test
        void zeroPriceWithAnyDiscountStaysZero() {
            assertThat(calculator.applyDiscount(0.0, 50)).isZero();
        }

        @Test
        void negativePriceThrows() {
            assertThatThrownBy(() -> calculator.applyDiscount(-10.0, 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        // --- null i PUSTY String ---
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        void nullEmptyOrBlankCouponCodeThrows(String invalidCode) {
            assertThatThrownBy(() -> calculator.normalizeCouponCode(invalidCode))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void couponCodeIsTrimmedAndUppercased() {
            assertThat(calculator.normalizeCouponCode("  letnia20  ")).isEqualTo("LETNIA20");
        }

        // --- PUSTA kolekcja i null ---
        @Test
        void emptyQuantityListSumsToZero() {
            assertThat(calculator.sumQuantities(List.of())).isZero();
        }

        @Test
        void nullQuantityListSumsToZero() {
            assertThat(calculator.sumQuantities(null)).isZero();
        }

        @Test
        void singleElementQuantityListSumsToThatElement() {
            assertThat(calculator.sumQuantities(List.of(7))).isEqualTo(7);
        }

        // --- PRZEPELNIENIE (Integer.MAX_VALUE) ---
        @Test
        void addingWithinRangeWorksNormally() {
            assertThat(calculator.safeAdd(2, 3)).isEqualTo(5);
        }

        @Test
        void addingAtMaxValueOverflowsAndThrows() {
            assertThatThrownBy(() -> calculator.safeAdd(Integer.MAX_VALUE, 1))
                    .isInstanceOf(ArithmeticException.class)
                    .hasMessageContaining("overflow");
        }

        @Test
        void addingAtMinValueUnderflowsAndThrows() {
            assertThatThrownBy(() -> calculator.safeAdd(Integer.MIN_VALUE, -1))
                    .isInstanceOf(ArithmeticException.class);
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
