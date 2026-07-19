package com.example.javaquest._25_unit_testing.Lesson06_AssertJForCollectionsAndMaps;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.tuple;

public class _Lesson06_AssertJForCollectionsAndMaps {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 6: AssertJ dla kolekcji i map ===");

        /*
         * ============================================================
         * 📦 KOLEKCJE - NAJCZESTSZY PRZYPADEK UZYCIA W TESTACH
         * ============================================================
         * Prawie kazdy test (repozytorium, serwis, endpoint REST -
         * powiazanie Z `_03_collections`/`_23_spring_data_jpa`) zwraca
         * LISTE/MAPE/ZBIOR. AssertJ MA BOGATY zestaw metod
         * DEDYKOWANYCH kolekcjom - sprawdzanie zawartosci BEZ WZGLEDU
         * NA kolejnosc, rozmiar, duplikaty, wyodrebnianie pol Z
         * obiektow W liscie ("extracting").
         */
        System.out.println("assertThat(lista/mapa) - dedykowane metody DLA rozmiaru/zawartosci/kolejnosci, BEZ recznego pisania petli W tescie.");

        runAndReport(CollectionAssertionsTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.hasSize(n)` / `.isEmpty()` / `.isNotEmpty()` - rozmiar.
         * - `.contains(...)` (KOLEJNOSC NIEWAZNA) vs
         *   `.containsExactly(...)` (KOLEJNOSC MA znaczenie).
         * - `.containsExactlyInAnyOrder(...)` - TA SAMA zawartosc,
         *   KOLEJNOSC bez znaczenia.
         * - `.extracting("pole")` - wyciaga JEDNO pole Z KAZDEGO
         *   elementu listy DO NOWEJ listy (BEZ petli `for`).
         * - `assertThat(mapa).containsEntry(klucz, wartosc)` /
         *   `.containsKey(...)` / `.containsValue(...)`.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    record Product(String name, double price) {
    }

    static class CollectionAssertionsTest {
        @Test
        void listBasicAssertions() {
            List<String> fruits = List.of("jablko", "banan", "gruszka");
            assertThat(fruits)
                    .hasSize(3)
                    .isNotEmpty()
                    .contains("banan")
                    .doesNotContain("ananas");
        }

        @Test
        void listOrderMatters() {
            List<Integer> numbers = List.of(1, 2, 3);
            assertThat(numbers).containsExactly(1, 2, 3);
        }

        @Test
        void listOrderDoesNotMatter() {
            List<Integer> numbers = List.of(3, 1, 2);
            assertThat(numbers).containsExactlyInAnyOrder(1, 2, 3);
        }

        @Test
        void extractingFieldFromListOfObjects() {
            List<Product> products = List.of(
                    new Product("Laptop", 3500.0),
                    new Product("Mysz", 49.9)
            );
            // extracting - wyciaga POLE Z KAZDEGO elementu, BEZ recznej petli.
            assertThat(products).extracting(Product::name).containsExactly("Laptop", "Mysz");
            assertThat(products).extracting(Product::name, Product::price)
                    .contains(tuple("Mysz", 49.9));
        }

        @Test
        void mapAssertions() {
            Map<String, Integer> stock = Map.of("jablko", 50, "banan", 30);
            assertThat(stock)
                    .hasSize(2)
                    .containsEntry("jablko", 50)
                    .containsKey("banan")
                    .containsValue(30)
                    .contains(entry("jablko", 50));
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
