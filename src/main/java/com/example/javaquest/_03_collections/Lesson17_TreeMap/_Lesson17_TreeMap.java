package com.example.javaquest._03_collections.Lesson17_TreeMap;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class _Lesson17_TreeMap {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🌳 TREEMAP – CZYM JEST?
         * ============================================================
         * TreeMap to implementacja NavigableMap oparta na drzewie
         * czerwono-czarnym (Red-Black Tree).
         *
         * Cechy TreeMap:
         * ✅ Klucze ZAWSZE POSORTOWANE (natural order lub Comparator)
         * ✅ NavigableMap – bogaty zestaw metod nawigacyjnych
         * ✅ null wartości OK, null klucz → NPE (bo trzeba go porównywać)
         * ❌ Wolniejszy: O(log n) dla put/get/remove vs O(1) HashMap
         *
         * Kiedy TreeMap?
         * - gdy potrzebujesz mapy z posortowanymi kluczami
         * - gdy chcesz znaleźć zakres, min/max klucza
         * - słownik, mapa częstości w posortowanej kolejności
         */

        /*
         * ============================================================
         * 🔹 PODSTAWOWE OPERACJE
         * ============================================================
         */

        TreeMap<String, Integer> scores = new TreeMap<>();
        scores.put("Zofia", 85);
        scores.put("Anna", 95);
        scores.put("Marek", 78);
        scores.put("Bartek", 88);
        scores.put("Celina", 72);

        System.out.println("TreeMap (sorted): " + scores); // alfabetycznie po kluczach
        System.out.println("Anna: " + scores.get("Anna"));
        System.out.println("Zawiera Marek? " + scores.containsKey("Marek"));

        /*
         * ============================================================
         * 🔹 METODY NAVIGABLEMAP
         * ============================================================
         * firstKey()   → najmniejszy klucz
         * lastKey()    → największy klucz
         * floorKey(k)  → największy klucz <= k
         * ceilingKey(k)→ najmniejszy klucz >= k
         * lowerKey(k)  → największy klucz < k (ściśle)
         * higherKey(k) → najmniejszy klucz > k (ściśle)
         *
         * firstEntry() / lastEntry() → Map.Entry<K,V> z min/max klucza
         * floorEntry / ceilingEntry / lowerEntry / higherEntry → j.w.
         */

        System.out.println("\nPierwszy klucz: " + scores.firstKey()); // Anna
        System.out.println("Ostatni klucz: " + scores.lastKey());    // Zofia
        System.out.println("floorKey(Celina): " + scores.floorKey("Celina")); // Celina
        System.out.println("ceilingKey(Beata): " + scores.ceilingKey("Beata")); // Celina
        System.out.println("lowerKey(Marek): " + scores.lowerKey("Marek")); // Celina
        System.out.println("higherKey(Marek): " + scores.higherKey("Marek")); // Zofia

        Map.Entry<String, Integer> firstEntry = scores.firstEntry();
        System.out.println("firstEntry: " + firstEntry.getKey() + "=" + firstEntry.getValue());

        /*
         * ============================================================
         * 🔹 WIDOKI (subMap, headMap, tailMap)
         * ============================================================
         * headMap(toKey)               → klucze < toKey
         * headMap(toKey, inclusive)    → klucze <= toKey (jeśli true)
         * tailMap(fromKey)             → klucze >= fromKey
         * tailMap(fromKey, inclusive)  → klucze > fromKey (jeśli false)
         * subMap(from, to)             → klucze >= from i < to
         * subMap(from, fi, to, ti)     → pełna kontrola zakresów
         */

        System.out.println("\nheadMap(Marek): " + scores.headMap("Marek")); // klucze przed Marek
        System.out.println("tailMap(Marek): " + scores.tailMap("Marek")); // Marek i po nim
        System.out.println("subMap(Bartek, Marek): " + scores.subMap("Bartek", "Marek"));
        System.out.println("subMap(Bartek,true,Marek,true): " + scores.subMap("Bartek", true, "Marek", true));

        /*
         * ============================================================
         * 🔹 ODWRÓCONA MAPA
         * ============================================================
         */

        NavigableMap<String, Integer> desc = scores.descendingMap();
        System.out.println("\nMalejąco: " + desc);

        System.out.println("Klucze malejąco: " + scores.descendingKeySet());

        /*
         * ============================================================
         * 🔹 POLL (usuwanie ekstrema)
         * ============================================================
         * pollFirstEntry() → usuwa i zwraca wpis z najmniejszym kluczem
         * pollLastEntry()  → usuwa i zwraca wpis z największym kluczem
         */

        Map.Entry<String, Integer> polled = scores.pollFirstEntry();
        System.out.println("\nPollFirst: " + polled.getKey() + "=" + polled.getValue());
        System.out.println("Po pollFirst: " + scores);

        /*
         * ============================================================
         * 🔹 TREEMAP Z COMPARATOREM (niestandardowe sortowanie)
         * ============================================================
         */

        TreeMap<String, Integer> byLengthThenAlpha = new TreeMap<>(
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        byLengthThenAlpha.put("Zofia", 85);
        byLengthThenAlpha.put("Anna", 95);
        byLengthThenAlpha.put("Marek", 78);
        byLengthThenAlpha.put("Bartek", 88);

        System.out.println("\nPo długości klucza: " + byLengthThenAlpha);

        /*
         * ============================================================
         * 🔹 TREEMAP – MAPA CZĘSTOŚCI W KOLEJNOŚCI ALFABETYCZNEJ
         * ============================================================
         */

        String[] words = {"banana", "apple", "cherry", "apple", "banana", "date", "cherry", "apple"};
        TreeMap<String, Integer> frequency = new TreeMap<>();
        for (String word : words) {
            frequency.merge(word, 1, Integer::sum);
        }
        System.out.println("\nCzęstości (alfabetycznie): " + frequency);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * TreeMap<K,V> implements NavigableMap<K,V>:
         * - O(log n) dla put/get/remove/containsKey
         * - Klucze zawsze posortowane (Comparable lub Comparator)
         *
         * Metody nawigacyjne:
         * firstKey/lastKey             → ekstrema kluczy
         * floor/ceiling/lower/higher   → sąsiednie klucze
         * headMap/tailMap/subMap       → widoki zakresów (live)
         * descendingMap/keySet         → widoki odwrócone
         * pollFirstEntry/pollLastEntry → usuwanie ekstrema
         */
    }
}
