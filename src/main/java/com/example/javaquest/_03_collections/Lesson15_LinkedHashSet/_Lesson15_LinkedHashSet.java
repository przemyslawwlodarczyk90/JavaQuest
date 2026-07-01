package com.example.javaquest._03_collections.Lesson15_LinkedHashSet;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class _Lesson15_LinkedHashSet {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔗 LINKEDHASHSET – CZYM JEST?
         * ============================================================
         * LinkedHashSet = HashSet + doubly-linked list łącząca elementy.
         *
         * Cechy LinkedHashSet:
         * ✅ BRAK DUPLIKATÓW – jak każdy Set
         * ✅ ZACHOWUJE KOLEJNOŚĆ WSTAWIANIA (insertion order)
         * ✅ Szybkie operacje: add, remove, contains → O(1)
         * 💾 Więcej pamięci niż HashSet (dodatkowe wskaźniki prev/next)
         *
         * Hierarchia:
         * Set → HashSet → LinkedHashSet
         *
         * Kiedy LinkedHashSet?
         * - chcesz unikalności jak Set
         * - ale potrzebujesz przewidywalnej kolejności (kolejność wstawiania)
         * - np. zbiór odwiedzonych URL-ów, historia, kolejność menu
         *
         * Porównanie trzech Set:
         * ┌────────────────┬───────────┬──────────────┬──────────┐
         * │                │ HashSet   │ LinkedHashSet│ TreeSet  │
         * ├────────────────┼───────────┼──────────────┼──────────┤
         * │ Duplikaty      │ ❌ Nie    │ ❌ Nie       │ ❌ Nie   │
         * │ Kolejność      │ ❌ Żadna  │ ✅ Wstawiania│ ✅ Sorted│
         * │ add/remove     │ O(1)      │ O(1)         │ O(log n) │
         * │ contains       │ O(1)      │ O(1)         │ O(log n) │
         * └────────────────┴───────────┴──────────────┴──────────┘
         */

        /*
         * ============================================================
         * 🔹 RÓŻNICA MIĘDZY HashSet A LinkedHashSet
         * ============================================================
         */

        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("Banan");
        hashSet.add("Jabłko");
        hashSet.add("Gruszka");
        hashSet.add("Mango");
        hashSet.add("Kiwi");

        System.out.println("HashSet (brak kolejności): " + hashSet);

        LinkedHashSet<String> linkedSet = new LinkedHashSet<>();
        linkedSet.add("Banan");
        linkedSet.add("Jabłko");
        linkedSet.add("Gruszka");
        linkedSet.add("Mango");
        linkedSet.add("Kiwi");

        System.out.println("LinkedHashSet (kolejność wstawiania): " + linkedSet);

        /*
         * ============================================================
         * 🔹 BRAK DUPLIKATÓW
         * ============================================================
         */

        LinkedHashSet<String> langs = new LinkedHashSet<>();
        langs.add("Java");
        langs.add("Python");
        langs.add("Java");      // duplikat – ignorowany
        langs.add("C++");
        langs.add("Python");    // duplikat – ignorowany
        langs.add("Kotlin");

        System.out.println("\nJęzyki (bez duplikatów): " + langs);
        System.out.println("Rozmiar: " + langs.size()); // 4, nie 6

        /*
         * ============================================================
         * 🔹 OPERACJE
         * ============================================================
         */

        System.out.println("\nZawiera Java? " + langs.contains("Java"));
        System.out.println("Zawiera Rust? " + langs.contains("Rust"));

        langs.remove("C++");
        System.out.println("Po usunięciu C++: " + langs);

        /*
         * ============================================================
         * 🔹 USUWANIE DUPLIKATÓW Z LISTY (zachowując kolejność)
         * ============================================================
         * Klasyczne użycie: usuń duplikaty zachowując pierwsze wystąpienia.
         */

        java.util.List<Integer> withDups = java.util.List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        Set<Integer> unique = new LinkedHashSet<>(withDups); // zachowa kolejność pierwszych wystąpień
        System.out.println("\nZ duplikatami: " + withDups);
        System.out.println("Bez duplikatów (LinkedHashSet): " + unique);

        // Porównaj z HashSet (bez gwarancji kolejności):
        Set<Integer> uniqueHash = new HashSet<>(withDups);
        System.out.println("Bez duplikatów (HashSet): " + uniqueHash);

        /*
         * ============================================================
         * 🔹 OPERACJE ZBIOROWE
         * ============================================================
         * Takie same jak w HashSet: addAll, removeAll, retainAll, containsAll
         */

        LinkedHashSet<Integer> setA = new LinkedHashSet<>(java.util.List.of(1, 2, 3, 4, 5));
        LinkedHashSet<Integer> setB = new LinkedHashSet<>(java.util.List.of(3, 4, 5, 6, 7));

        LinkedHashSet<Integer> union = new LinkedHashSet<>(setA);
        union.addAll(setB);
        System.out.println("\nA ∪ B: " + union); // kolejność: najpierw setA, potem nowe z setB

        LinkedHashSet<Integer> intersection = new LinkedHashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("A ∩ B: " + intersection);

        /*
         * ============================================================
         * 🔹 ITERACJA (zawsze w kolejności wstawiania)
         * ============================================================
         */

        System.out.println("\nIteracja LinkedHashSet:");
        for (String lang : langs) {
            System.out.println("  " + lang);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * LinkedHashSet<T>:
         * - Extends HashSet – dziedziczy wszystkie metody Set
         * - Zachowuje kolejność wstawiania
         * - O(1) dla add/remove/contains
         * - Więcej pamięci niż HashSet (linked list overhead)
         *
         * Najlepsze do:
         * - Usuwanie duplikatów z zachowaniem kolejności
         * - Zbiory gdzie kolejność wstawiania ma znaczenie
         * - Historia, cache, deduplikacja logów
         */
    }
}
