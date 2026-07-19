package com.example.javaquest._25_unit_testing.Lesson09_NestedAndDisplayNameTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class _Lesson09_NestedAndDisplayNameTests {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 9: @Nested i @DisplayName - CZYTELNA organizacja testow ===");

        /*
         * ============================================================
         * 📦 GRUPOWANIE testow WEDLUG scenariusza, NIE 1 PLASKA lista metod
         * ============================================================
         * KLASA testowa Z 20 metodami `@Test` (BEZ struktury) jest
         * TRUDNA DO PRZESZUKANIA. `@Nested` pozwala ZGRUPOWAC testy W
         * ZAGNIEZDZONE klasy WEDLUG scenariusza (np. "gdy koszyk jest
         * pusty" / "gdy koszyk MA produkty") - KAZDA zagniezdzona
         * klasa MOZE MIEC WLASNY `@BeforeEach` (DODATKOWY, NIE
         * ZASTEPUJACY tego Z klasy zewnetrznej). `@DisplayName` DAJE
         * CZYTELNA, opisowa nazwe (ZAMIAST nazwy metody Javy)
         * WIDOCZNA W raporcie.
         */
        System.out.println("@Nested = GRUPOWANIE testow WEDLUG scenariusza (zagniezdzone klasy). @DisplayName = CZYTELNA nazwa W raporcie ZAMIAST nazwy metody.");

        runAndReport(ShoppingCartTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@Nested` NA klasie WEWNETRZNEJ (NIE-statycznej!) -
         *   JUnit AUTOMATYCZNIE odkrywa I URUCHAMIA testy W SRODKU.
         * - `@BeforeEach` klasy ZAGNIEZDZONEJ WYKONUJE SIE PO
         *   `@BeforeEach` klasy ZEWNETRZNEJ (OBA, W TEJ kolejnosci).
         * - `@DisplayName("opis")` NA klasie I/LUB metodzie - widoczne
         *   W raporcie ZAMIAST nazwy Javy (np. "3 + 2 = 5" ZAMIAST
         *   "addsCorrectly").
         * - Struktura `@Nested` ODZWIERCIEDLA jezyk BDD ("given/when/
         *   then") - CZYTA SIE jak SPECYFIKACJA, NIE tylko kod.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    static class ShoppingCart {
        private final List<String> items = new ArrayList<>();

        void addItem(String item) {
            items.add(item);
        }

        int itemCount() {
            return items.size();
        }

        String checkout() {
            if (items.isEmpty()) {
                throw new IllegalStateException("Nie mozna zrealizowac pustego koszyka");
            }
            return "Zamowienie na " + items.size() + " przedmiotow";
        }
    }

    @DisplayName("Koszyk zakupowy")
    static class ShoppingCartTest {
        ShoppingCart cart;

        @BeforeEach
        void createEmptyCart() {
            cart = new ShoppingCart();
        }

        @Nested
        @DisplayName("Gdy koszyk jest pusty")
        class WhenCartIsEmpty {
            @Test
            @DisplayName("liczba przedmiotow wynosi 0")
            void itemCountIsZero() {
                assertThat(cart.itemCount()).isZero();
            }

            @Test
            @DisplayName("realizacja zamowienia RZUCA wyjatek")
            void checkoutThrowsException() {
                assertThatThrownBy(cart::checkout).isInstanceOf(IllegalStateException.class);
            }
        }

        @Nested
        @DisplayName("Gdy koszyk MA przedmioty")
        class WhenCartHasItems {
            @BeforeEach
            void addSampleItems() {
                // Wykonuje sie PO @BeforeEach klasy ZEWNETRZNEJ (createEmptyCart) - OBA razem.
                cart.addItem("Laptop");
                cart.addItem("Mysz");
            }

            @Test
            @DisplayName("liczba przedmiotow ODPOWIADA dodanym")
            void itemCountMatchesAdded() {
                assertThat(cart.itemCount()).isEqualTo(2);
            }

            @Test
            @DisplayName("realizacja zamowienia zwraca podsumowanie")
            void checkoutReturnsSummary() {
                assertThat(cart.checkout()).isEqualTo("Zamowienie na 2 przedmiotow");
            }
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
        System.out.println("Udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount() + " (oczekiwane: 4/4)");
    }
}
