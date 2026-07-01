package com.example.javaquest._03_collections.Lesson06_TreeSet;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

public class _Lesson06_TreeSet {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🌳 TREESET – CZYM JEST?
         * ============================================================
         * TreeSet to implementacja SortedSet/NavigableSet oparta na drzewie
         * czerwono-czarnym (Red-Black Tree).
         *
         * Cechy TreeSet:
         * ✅ BRAK DUPLIKATÓW – jak każdy Set
         * ✅ POSORTOWANE elementy – zawsze w kolejności naturalnej lub Comparatora
         * ✅ NavigableSet – bogaty zestaw metod nawigacyjnych
         * ❌ WOLNIEJSZY od HashSet – O(log n) zamiast O(1)
         *
         * Kiedy TreeSet?
         * - gdy potrzebujesz elementów w posortowanej kolejności
         * - gdy chcesz szybko znaleźć min/max, sąsiadów elementu
         * - gdy chcesz iterować w określonej kolejności
         *
         * Klasy w TreeSet MUSZĄ implementować Comparable lub dostarczyć Comparator!
         */

        /*
         * ============================================================
         * 🔹 PODSTAWOWE OPERACJE
         * ============================================================
         */

        TreeSet<Integer> numbers = new TreeSet<>();
        numbers.add(5); numbers.add(2); numbers.add(8); numbers.add(1); numbers.add(9);
        numbers.add(3); numbers.add(5); // duplikat – ignorowany

        System.out.println("TreeSet: " + numbers); // zawsze posortowany!
        System.out.println("Rozmiar: " + numbers.size()); // 6, nie 7
        System.out.println("Zawiera 8? " + numbers.contains(8));

        TreeSet<String> words = new TreeSet<>();
        words.add("banana"); words.add("apple"); words.add("cherry"); words.add("avocado");
        System.out.println("Słowa: " + words); // alfabetycznie

        /*
         * ============================================================
         * 🔍 METODY NAVIGABLESET
         * ============================================================
         * first()   → najmniejszy element
         * last()    → największy element
         * floor(e)  → największy element <= e (lub null)
         * ceiling(e)→ najmniejszy element >= e (lub null)
         * lower(e)  → największy element < e (ściśle mniejszy, lub null)
         * higher(e) → najmniejszy element > e (ściśle większy, lub null)
         */

        TreeSet<Integer> nav = new TreeSet<>(java.util.List.of(10, 20, 30, 40, 50));

        System.out.println("\nPierwszy: " + nav.first());    // 10
        System.out.println("Ostatni: " + nav.last());        // 50
        System.out.println("floor(25): " + nav.floor(25));   // 20
        System.out.println("ceiling(25): " + nav.ceiling(25)); // 30
        System.out.println("lower(30): " + nav.lower(30));   // 20
        System.out.println("higher(30): " + nav.higher(30)); // 40

        // Gdy nie ma dopasowania → null
        System.out.println("lower(10): " + nav.lower(10));   // null
        System.out.println("higher(50): " + nav.higher(50)); // null

        /*
         * ============================================================
         * 📋 WIDOKI (subSet, headSet, tailSet)
         * ============================================================
         * headSet(to)          → elementy < to
         * headSet(to, inclusive) → elementy <= to (jeśli true)
         * tailSet(from)          → elementy >= from
         * tailSet(from, inclusive) → elementy > from (jeśli false)
         * subSet(from, to)       → elementy >= from i < to
         * subSet(from, fromIncl, to, toIncl) → pełna kontrola zakresów
         *
         * Widoki to LIVE views – zmiany w widoku wpływają na oryginał!
         */

        System.out.println("\nheadSet(30): " + nav.headSet(30));         // [10, 20]
        System.out.println("headSet(30, true): " + nav.headSet(30, true)); // [10, 20, 30]
        System.out.println("tailSet(30): " + nav.tailSet(30));           // [30, 40, 50]
        System.out.println("tailSet(30, false): " + nav.tailSet(30, false)); // [40, 50]
        System.out.println("subSet(20, 40): " + nav.subSet(20, 40));    // [20, 30]
        System.out.println("subSet(20,true,40,true): " + nav.subSet(20, true, 40, true)); // [20,30,40]

        /*
         * ============================================================
         * 🔄 ODWRÓCONA KOLEJNOŚĆ
         * ============================================================
         * descendingSet() → widok w odwróconej kolejności
         * descendingIterator() → iterator malejący
         */

        System.out.println("\nMalejąco: " + nav.descendingSet());

        /*
         * ============================================================
         * ❌ POLLOWANIE (usuwanie ekstrema)
         * ============================================================
         * pollFirst() → usuwa i zwraca najmniejszy element
         * pollLast()  → usuwa i zwraca największy element
         */

        NavigableSet<Integer> poll = new TreeSet<>(java.util.List.of(1, 2, 3, 4, 5));
        System.out.println("\nPollFirst: " + poll.pollFirst() + ", set: " + poll);
        System.out.println("PollLast: " + poll.pollLast() + ", set: " + poll);

        /*
         * ============================================================
         * 🔹 TREESET Z COMPARATOREM
         * ============================================================
         * TreeSet(Comparator) – sortuje wg podanego Comparatora
         */

        TreeSet<String> byLength = new TreeSet<>(Comparator.comparingInt(String::length)
                                                              .thenComparing(Comparator.naturalOrder()));
        byLength.add("banana"); byLength.add("fig"); byLength.add("apple");
        byLength.add("kiwi"); byLength.add("cherry");

        System.out.println("\nPo długości: " + byLength);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * TreeSet<T> implements NavigableSet<T>:
         * - add/remove/contains: O(log n)
         * - first(), last()       → min/max
         * - floor/ceiling/lower/higher → sąsiednie elementy
         * - headSet/tailSet/subSet → przedziały (live views)
         * - descendingSet()       → widok malejący
         * - pollFirst/pollLast    → usuwanie ekstrema
         *
         * vs HashSet:
         * HashSet → szybszy (O(1)), brak kolejności
         * TreeSet → wolniejszy (O(log n)), zawsze posortowany
         */
    }
}
