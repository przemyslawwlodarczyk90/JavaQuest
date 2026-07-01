package com.example.javaquest._03_collections.Lesson16_LinkedHashMap;

import java.util.LinkedHashMap;
import java.util.Map;

public class _Lesson16_LinkedHashMap {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔗 LINKEDHASHMAP – CZYM JEST?
         * ============================================================
         * LinkedHashMap = HashMap + doubly-linked list.
         *
         * Cechy LinkedHashMap:
         * ✅ Pary klucz → wartość jak HashMap
         * ✅ ZACHOWUJE KOLEJNOŚĆ – domyślnie: wstawiania (insertion order)
         * ✅ Opcjonalnie: kolejność dostępu (access order) – do LRU cache!
         * 💾 Więcej pamięci niż HashMap
         *
         * Trzy implementacje Map:
         * ┌────────────────┬──────────────┬────────────────┬──────────┐
         * │                │ HashMap      │ LinkedHashMap  │ TreeMap  │
         * ├────────────────┼──────────────┼────────────────┼──────────┤
         * │ Kolejność      │ ❌ Brak      │ ✅ Wstawiania  │ ✅ Sorted│
         * │ null klucz     │ ✅ Jeden     │ ✅ Jeden       │ ❌ Nie   │
         * │ put/get        │ O(1)         │ O(1)           │ O(log n) │
         * └────────────────┴──────────────┴────────────────┴──────────┘
         */

        /*
         * ============================================================
         * 🔹 INSERTION ORDER (domyślny)
         * ============================================================
         */

        LinkedHashMap<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Anna", 95);
        scores.put("Bartek", 78);
        scores.put("Celina", 88);
        scores.put("Damian", 72);

        System.out.println("Kolejność wstawiania:");
        scores.forEach((k, v) -> System.out.println("  " + k + " → " + v));

        // Porównaj z HashMap
        java.util.HashMap<String, Integer> hashMap = new java.util.HashMap<>(scores);
        System.out.println("\nHashMap (brak kolejności): " + hashMap);
        System.out.println("LinkedHashMap (wstawianie): " + scores);

        /*
         * ============================================================
         * 🔹 OPERACJE (identyczne jak HashMap)
         * ============================================================
         */

        scores.put("Ewa", 91);
        scores.putIfAbsent("Anna", 0);   // Anna istnieje – nie zmieni
        System.out.println("\nPo dodaniu Ewy: " + scores);

        System.out.println("Wynik Celiny: " + scores.get("Celina"));
        System.out.println("Wynik Zbyszka: " + scores.getOrDefault("Zbyszek", 0));
        System.out.println("Zawiera Bartek? " + scores.containsKey("Bartek"));

        scores.remove("Damian");
        System.out.println("Po usunięciu Damiana: " + scores);

        /*
         * ============================================================
         * 🔹 ACCESS ORDER – do implementacji LRU Cache
         * ============================================================
         * LinkedHashMap(capacity, loadFactor, accessOrder)
         * accessOrder = true → kolejność ostatniego dostępu (get/put)
         * Najdawniej używany element jest PIERWSZY (do usunięcia z LRU)
         *
         * LRU Cache = Least Recently Used – usuwa najdawniej używane
         */

        System.out.println("\n=== ACCESS ORDER (LRU Cache) ===");

        LinkedHashMap<String, Integer> lruCache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return size() > 3; // max 3 elementy
            }
        };

        lruCache.put("Page1", 1);
        lruCache.put("Page2", 2);
        lruCache.put("Page3", 3);
        System.out.println("Cache: " + lruCache.keySet());

        lruCache.get("Page1");  // używamy Page1 – przesuwa na koniec
        System.out.println("Po get(Page1): " + lruCache.keySet()); // Page2, Page3, Page1

        lruCache.put("Page4", 4); // pojemność przekroczona → usuwa najdawniej używany (Page2)
        System.out.println("Po dodaniu Page4: " + lruCache.keySet()); // Page3, Page1, Page4

        /*
         * ============================================================
         * 🔹 ITERACJA (zawsze w zdefiniowanej kolejności)
         * ============================================================
         */

        LinkedHashMap<String, String> capitals = new LinkedHashMap<>();
        capitals.put("PL", "Warszawa");
        capitals.put("DE", "Berlin");
        capitals.put("FR", "Paryż");
        capitals.put("UK", "Londyn");

        System.out.println("\nKoleje wstawiania:");
        for (Map.Entry<String, String> entry : capitals.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }

        System.out.println("\nKlucze: " + capitals.keySet());
        System.out.println("Wartości: " + capitals.values());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * LinkedHashMap<K,V>:
         * - Wszystkie metody HashMap (put, get, remove, containsKey...)
         * - Dwa tryby kolejności:
         *   insertion order (domyślny) → kolejność wstawiania
         *   access order              → kolejność ostatniego dostępu
         * - Nadpisanie removeEldestEntry() → własne zasady eksmisji
         * - Idealny do LRU Cache i wszędzie, gdzie potrzeba przewidywalnej
         *   iteracji przez Map
         */
    }
}
