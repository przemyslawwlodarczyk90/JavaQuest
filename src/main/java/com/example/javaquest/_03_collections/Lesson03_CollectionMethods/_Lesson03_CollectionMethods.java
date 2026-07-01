package com.example.javaquest._03_collections.Lesson03_CollectionMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class _Lesson03_CollectionMethods {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🛠️ KLASA UTILITY: java.util.Collections
         * ============================================================
         * Collections to klasa z samymi metodami statycznymi
         * do operacji na kolekcjach (głównie List).
         *
         * Najważniejsze metody:
         * sort, reverse, shuffle, min, max, frequency, copy, fill,
         * unmodifiableList, singletonList, nCopies...
         */

        ArrayList<Integer> numbers = new ArrayList<>(List.of(5, 2, 8, 1, 9, 3, 7, 4, 6));

        /*
         * ============================================================
         * 📊 SORTOWANIE
         * ============================================================
         * Collections.sort(list)          → naturalne (Comparable)
         * Collections.sort(list, comp)    → z Comparatorem
         * list.sort(comp)                 → metoda instancyjna (Java 8+)
         */

        ArrayList<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);
        System.out.println("Rosnąco: " + sorted);

        ArrayList<Integer> desc = new ArrayList<>(numbers);
        Collections.sort(desc, Comparator.reverseOrder());
        System.out.println("Malejąco: " + desc);

        ArrayList<String> names = new ArrayList<>(List.of("Zofia", "Adam", "Maria", "Bartek", "Celina"));
        Collections.sort(names);
        System.out.println("Alfabetycznie: " + names);

        names.sort(Comparator.comparingInt(String::length));
        System.out.println("Po długości: " + names);

        names.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        System.out.println("Długość, potem alfa: " + names);

        /*
         * ============================================================
         * 🔄 ODWRACANIE I MIESZANIE
         * ============================================================
         */

        ArrayList<Integer> rev = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Collections.reverse(rev);
        System.out.println("Odwrócone: " + rev);

        ArrayList<Integer> shuffled = new ArrayList<>(numbers);
        Collections.shuffle(shuffled);
        System.out.println("Przetasowane: " + shuffled);

        /*
         * ============================================================
         * 🔍 MIN / MAX / FREQUENCY
         * ============================================================
         */

        System.out.println("Min: " + Collections.min(numbers));
        System.out.println("Max: " + Collections.max(numbers));

        ArrayList<String> words = new ArrayList<>(List.of("java", "python", "java", "c++", "java", "python"));
        System.out.println("Ile razy 'java': " + Collections.frequency(words, "java"));

        /*
         * ============================================================
         * 📋 WYPEŁNIANIE I KOPIOWANIE
         * ============================================================
         * fill(list, obj)       → wypełnia listę tym samym elementem
         * copy(dest, src)       → kopiuje src do dest (dest musi mieć >= size)
         * nCopies(n, obj)       → immutable lista z n kopiami obiektu
         */

        ArrayList<String> filled = new ArrayList<>(List.of("a", "b", "c", "d"));
        Collections.fill(filled, "X");
        System.out.println("Po fill: " + filled);

        List<String> copies = Collections.nCopies(5, "hello");
        System.out.println("5 kopii: " + copies);

        ArrayList<Integer> dest = new ArrayList<>(List.of(0, 0, 0, 0, 0));
        ArrayList<Integer> src = new ArrayList<>(List.of(10, 20, 30, 40, 50));
        Collections.copy(dest, src);
        System.out.println("Po copy: " + dest);

        /*
         * ============================================================
         * 🔒 WYSZUKIWANIE BINARNE
         * ============================================================
         * Collections.binarySearch(sortedList, key) → index lub -(insertion point) - 1
         * UWAGA: lista MUSI być posortowana!
         */

        ArrayList<Integer> bsNums = new ArrayList<>(List.of(1, 3, 5, 7, 9, 11, 13));
        int idx = Collections.binarySearch(bsNums, 7);
        System.out.println("BinarySearch 7: indeks = " + idx);
        int missing = Collections.binarySearch(bsNums, 6);
        System.out.println("BinarySearch 6 (brak): " + missing); // ujemny

        /*
         * ============================================================
         * 🔐 NIEMODYFIKOWALNE WIDOKI
         * ============================================================
         * unmodifiableList / unmodifiableSet / unmodifiableMap
         * Zwraca widok, który rzuca UnsupportedOperationException przy mutacji.
         * Użyj List.of() / Set.of() / Map.of() dla prawdziwie immutable (Java 9+).
         */

        ArrayList<String> mutable = new ArrayList<>(List.of("a", "b", "c"));
        List<String> immutable = Collections.unmodifiableList(mutable);
        System.out.println("Niemodyfikowalna: " + immutable);
        // immutable.add("d"); // ❌ UnsupportedOperationException

        /*
         * ============================================================
         * 🔄 ZAMIANA MIEJSCAMI (swap)
         * ============================================================
         */

        ArrayList<String> swapList = new ArrayList<>(List.of("pierwszy", "drugi", "trzeci"));
        Collections.swap(swapList, 0, 2);
        System.out.println("Po swap(0,2): " + swapList);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Collections.sort(list)           → sortowanie
         * Collections.reverse(list)        → odwrócenie kolejności
         * Collections.shuffle(list)        → losowe przetasowanie
         * Collections.min/max(coll)        → ekstremum
         * Collections.frequency(coll, obj) → liczba wystąpień
         * Collections.fill(list, obj)      → wypełnienie
         * Collections.copy(dest, src)      → kopiowanie
         * Collections.binarySearch(list,k) → wyszukiwanie binarne
         * Collections.unmodifiableList(l)  → widok read-only
         * Collections.swap(list, i, j)     → zamiana miejscami
         */
    }
}
