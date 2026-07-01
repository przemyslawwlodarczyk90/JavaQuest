package com.example.javaquest._03_collections.Lesson07_Comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class _Lesson07_Comparator {

    public static void main(String[] args) {

        /*
         * ============================================================
         * ⚖️ COMPARABLE VS COMPARATOR
         * ============================================================
         * COMPARABLE (java.lang):
         *   - interfejs implementowany przez klasę
         *   - metoda: int compareTo(T other)
         *   - definiuje NATURALNE porządkowanie
         *   - klasa może mieć tylko jeden porządek
         *
         * COMPARATOR (java.util):
         *   - osobny obiekt (zewnętrzna strategia sortowania)
         *   - metoda: int compare(T a, T b)
         *   - definiuje NIESTANDARDOWE porządkowanie
         *   - możemy mieć wiele różnych Comparatorów
         *
         * Wynik compareTo/compare:
         *   < 0  → a jest PRZED b
         *   = 0  → a równe b
         *   > 0  → a jest PO b
         */

        /*
         * ============================================================
         * 🔹 TWORZENIE COMPARATORA
         * ============================================================
         */

        // 1. Anonimowa klasa (stary styl)
        Comparator<String> byLengthAnon = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(a.length(), b.length());
            }
        };

        // 2. Lambda
        Comparator<String> byLengthLambda = (a, b) -> Integer.compare(a.length(), b.length());

        // 3. Comparator.comparing (najczęstszy i najprostszy – Java 8+)
        Comparator<String> byLengthFactory = Comparator.comparingInt(String::length);

        List<String> words = new ArrayList<>(List.of("banana", "fig", "apple", "kiwi", "cherry"));
        words.sort(byLengthFactory);
        System.out.println("Po długości: " + words);

        /*
         * ============================================================
         * 🔗 ŁĄCZENIE COMPARATORÓW (CHAINING)
         * ============================================================
         * .thenComparing()         → kolejny kryterium gdy pierwsze równe
         * .reversed()              → odwrócona kolejność
         * Comparator.reverseOrder()→ malejąco dla Comparable
         * Comparator.naturalOrder()→ rosnąco dla Comparable
         */

        Comparator<String> byLengthThenAlpha = Comparator
                .comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder());

        words.sort(byLengthThenAlpha);
        System.out.println("Długość, potem alfa: " + words);

        Comparator<String> byLengthDesc = Comparator.comparingInt(String::length).reversed();
        words.sort(byLengthDesc);
        System.out.println("Malejąco po długości: " + words);

        /*
         * ============================================================
         * 🔹 COMPARATOR Z WŁASNYMI OBIEKTAMI
         * ============================================================
         */

        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 3500.0, 3));
        products.add(new Product("Mysz", 150.0, 50));
        products.add(new Product("Monitor", 2000.0, 10));
        products.add(new Product("Klawiatura", 300.0, 25));
        products.add(new Product("USB Hub", 150.0, 100));

        // Sortuj po cenie rosnąco
        products.sort(Comparator.comparingDouble(Product::getPrice));
        System.out.println("\nPo cenie rosnąco:");
        products.forEach(p -> System.out.println("  " + p));

        // Sortuj po cenie malejąco, potem po nazwie
        products.sort(Comparator.comparingDouble(Product::getPrice)
                                .reversed()
                                .thenComparing(Product::getName));
        System.out.println("\nPo cenie malejąco, potem po nazwie:");
        products.forEach(p -> System.out.println("  " + p));

        // Sortuj po liczbie sztuk rosnąco
        products.sort(Comparator.comparingInt(Product::getQuantity));
        System.out.println("\nPo ilości:");
        products.forEach(p -> System.out.println("  " + p));

        /*
         * ============================================================
         * 🌳 TREESET Z COMPARATOREM
         * ============================================================
         */

        TreeSet<Product> byPrice = new TreeSet<>(Comparator.comparingDouble(Product::getPrice)
                                                            .thenComparing(Product::getName));
        byPrice.addAll(products);
        System.out.println("\nTreeSet po cenie:");
        byPrice.forEach(p -> System.out.println("  " + p));

        /*
         * ============================================================
         * 🚫 NULL SAFE COMPARATOR
         * ============================================================
         * Comparator.nullsFirst(comparator) → null elementy na początku
         * Comparator.nullsLast(comparator)  → null elementy na końcu
         */

        List<String> withNulls = new ArrayList<>();
        withNulls.add("banana"); withNulls.add(null); withNulls.add("apple"); withNulls.add(null);

        withNulls.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        System.out.println("\nNulls first: " + withNulls);

        withNulls.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        System.out.println("Nulls last: " + withNulls);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Comparator.comparing(getter)          → po polu
         * Comparator.comparingInt/Double/Long   → po prymitywnym polu
         * .thenComparing(getter)                → drugie kryterium
         * .reversed()                           → odwróć kolejność
         * Comparator.reverseOrder()             → malejąco naturalne
         * Comparator.naturalOrder()             → rosnąco naturalne
         * Comparator.nullsFirst/nullsLast       → obsługa null
         */
    }
}

class Product {
    private String name;
    private double price;
    private int quantity;

    Product(String name, double price, int quantity) {
        this.name = name; this.price = price; this.quantity = quantity;
    }
    String getName() { return name; }
    double getPrice() { return price; }
    int getQuantity() { return quantity; }
    public String toString() { return name + "($" + price + ", qty:" + quantity + ")"; }
}
