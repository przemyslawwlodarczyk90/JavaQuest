package com.example.javaquest._03_collections.Lesson08_HashMap;

import java.util.HashMap;
import java.util.Map;

public class _Lesson08_HashMap {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🗺️ HASHMAP – CZYM JEST?
         * ============================================================
         * HashMap to implementacja interfejsu Map oparta na tablicy hashowej.
         *
         * Map: przechowuje pary KLUCZ → WARTOŚĆ
         * - klucze są UNIKALNE (nie może być duplikatów kluczy)
         * - wartości mogą się powtarzać
         * - jeden klucz = jedna wartość
         *
         * Cechy HashMap:
         * ✅ Szybki dostęp po kluczu: O(1) amortyzowane
         * ✅ null jako klucz (dokładnie jeden raz) i jako wartość
         * ❌ Brak gwarancji kolejności
         *
         * Inne implementacje Map:
         * - LinkedHashMap → zachowuje kolejność wstawiania
         * - TreeMap       → sortuje po kluczach
         */

        /*
         * ============================================================
         * 🔹 TWORZENIE I WSTAWIANIE
         * ============================================================
         * put(key, value)   → wstawia lub zastępuje
         * putIfAbsent(k, v) → wstawia tylko jeśli klucz nie istnieje
         * putAll(map)       → wstawia całą mapę
         */

        HashMap<String, Integer> scores = new HashMap<>();
        scores.put("Anna", 95);
        scores.put("Bartek", 78);
        scores.put("Celina", 88);
        scores.put("Damian", 72);
        scores.put("Anna", 99);    // nadpisuje – Anna teraz ma 99

        System.out.println("Wyniki: " + scores);
        System.out.println("Rozmiar: " + scores.size());

        scores.putIfAbsent("Ewa", 85);     // Ewa nie istnieje – zostanie dodana
        scores.putIfAbsent("Anna", 0);     // Anna istnieje – nie zmieni się
        System.out.println("Po putIfAbsent: " + scores);

        /*
         * ============================================================
         * 🔍 ODCZYT
         * ============================================================
         * get(key)              → wartość lub null jeśli brak klucza
         * getOrDefault(k, def)  → wartość lub domyślna jeśli brak klucza
         * containsKey(key)      → czy klucz istnieje
         * containsValue(value)  → czy wartość istnieje (wolne! O(n))
         */

        System.out.println("\nWynik Anny: " + scores.get("Anna"));
        System.out.println("Wynik Zbyszka (brak): " + scores.get("Zbyszek"));
        System.out.println("getOrDefault Zbyszek: " + scores.getOrDefault("Zbyszek", 0));
        System.out.println("Czy jest Anna? " + scores.containsKey("Anna"));
        System.out.println("Czy jest wynik 88? " + scores.containsValue(88));

        /*
         * ============================================================
         * ✏️ MODYFIKACJA
         * ============================================================
         * replace(key, value)        → zastępuje wartość jeśli klucz istnieje
         * replace(key, old, new)     → zastępuje tylko jeśli stara wartość pasuje
         * compute(k, (k,v) -> ...)  → oblicza nową wartość na podstawie starej
         * merge(k, v, (old,v) → ...) → łączy starą i nową wartość
         */

        scores.replace("Bartek", 90);
        System.out.println("\nPo replace Bartek → 90: " + scores.get("Bartek"));

        // compute – przydatne np. do liczenia wystąpień
        HashMap<String, Integer> wordCount = new HashMap<>();
        String[] tekst = {"java", "python", "java", "java", "c++", "python"};
        for (String word : tekst) {
            wordCount.compute(word, (k, v) -> (v == null) ? 1 : v + 1);
        }
        System.out.println("\nWordCount: " + wordCount);

        // merge – podobne do compute ale prostsze do +1
        HashMap<String, Integer> wordCount2 = new HashMap<>();
        for (String word : tekst) {
            wordCount2.merge(word, 1, Integer::sum);
        }
        System.out.println("WordCount2: " + wordCount2);

        /*
         * ============================================================
         * ❌ USUWANIE
         * ============================================================
         * remove(key)          → usuwa parę, zwraca wartość (lub null)
         * remove(key, value)   → usuwa tylko jeśli wartość pasuje (boolean)
         * clear()              → usuwa wszystkie wpisy
         */

        Integer removed = scores.remove("Damian");
        System.out.println("\nUsunięty Damian, wartość: " + removed);

        /*
         * ============================================================
         * 🔄 ITERACJA
         * ============================================================
         * keySet()    → Set<K>   – zbiór kluczy
         * values()    → Collection<V> – kolekcja wartości
         * entrySet()  → Set<Map.Entry<K,V>> – pary (klucz+wartość)
         * forEach((k,v) -> ...) – lambda (Java 8+)
         */

        System.out.println("\nKlucze: " + scores.keySet());
        System.out.println("Wartości: " + scores.values());

        System.out.println("\nIteracja przez entrySet:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }

        System.out.println("\nIteracja forEach:");
        scores.forEach((name, score) -> System.out.println("  " + name + ": " + score));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * HashMap<K, V>:
         * put(k,v), putIfAbsent, putAll     → wstawianie
         * get(k), getOrDefault(k,def)       → odczyt
         * containsKey, containsValue        → sprawdzanie
         * replace, compute, merge           → modyfikacja
         * remove(k), remove(k,v)            → usuwanie
         * keySet(), values(), entrySet()    → iteracja
         *
         * Reguła: klucze muszą mieć poprawne equals() + hashCode()!
         * String, Integer i inne wbudowane typy już je mają.
         */
    }
}
