package com.example.javaquest._03_collections.Lesson23_SpecialMaps;

import java.util.*;

public class _Lesson23_SpecialMaps {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🗺️ SPECJALNE MAPY JAVY
         * ============================================================
         * Poza HashMap, LinkedHashMap i TreeMap istnieją wyspecjalizowane
         * implementacje Map do konkretnych zastosowań:
         *
         * - WeakHashMap       → klucze jako słabe referencje (GC może usunąć)
         * - IdentityHashMap   → porównuje klucze przez == zamiast equals()
         * - EnumMap           → zoptymalizowana Map z kluczami enum
         *
         * Wszystkie implementują Map<K,V> – ten sam interfejs.
         */

        /*
         * ============================================================
         * 🔹 ENUMMAP – MAPA Z KLUCZAMI ENUM
         * ============================================================
         * EnumMap<K extends Enum<K>, V>
         *
         * Cechy EnumMap:
         * ✅ Wewnętrznie oparta na tablicy (nie hashCode!)
         * ✅ Bardzo szybka – O(1) dla get/put bez hashowania
         * ✅ Iteracja w kolejności deklaracji enum
         * ❌ Klucze muszą być tego samego typu enum
         * ❌ Null klucz → NullPointerException
         *
         * Kiedy EnumMap? → ZAWSZE gdy klucze są enum!
         * Szybsza i bardziej czytelna niż HashMap z kluczami enum.
         */

        System.out.println("=== EnumMap ===");

        EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
        schedule.put(Day.MONDAY, "Java OOP");
        schedule.put(Day.WEDNESDAY, "Collections");
        schedule.put(Day.FRIDAY, "Spring Boot");
        schedule.put(Day.SATURDAY, "Projekt");

        System.out.println("Plan tygodnia:");
        schedule.forEach((day, task) -> System.out.println("  " + day + " → " + task));

        System.out.println("Środa: " + schedule.get(Day.WEDNESDAY));
        System.out.println("Wtorek: " + schedule.getOrDefault(Day.TUESDAY, "Wolne"));

        // Iteracja w kolejności enum (porządek deklaracji!)
        System.out.println("Kolejność: " + new ArrayList<>(schedule.keySet()));

        // Statystyki przez EnumMap
        EnumMap<Season, List<String>> seasonal = new EnumMap<>(Season.class);
        for (Season s : Season.values()) seasonal.put(s, new ArrayList<>());
        seasonal.get(Season.SPRING).add("Tulipan");
        seasonal.get(Season.SUMMER).add("Słonecznik");
        seasonal.get(Season.SUMMER).add("Mak");
        seasonal.get(Season.AUTUMN).add("Dalia");
        System.out.println("\nKwiaty per pora roku: " + seasonal);

        /*
         * ============================================================
         * 🔹 IDENTITYHASHMAP – PORÓWNYWANIE PRZEZ ==
         * ============================================================
         * IdentityHashMap<K,V>
         *
         * Cechy:
         * ✅ Używa == (referencja) zamiast equals() do porównywania kluczy
         * ✅ Używa System.identityHashCode() zamiast hashCode()
         * ❌ Niszczy kontrakt Map – dokumentacja zaznacza to jawnie!
         *
         * Kiedy IdentityHashMap?
         * - Serializacja/deserializacja – śledzenie już przetworzonych obiektów
         * - Graph traversal – śledzenie odwiedzonych węzłów przez referencję
         * - Gdy chcesz używać obiektów jako kluczy bez nadpisywania equals/hashCode
         */

        System.out.println("\n=== IdentityHashMap ===");

        IdentityHashMap<String, Integer> identityMap = new IdentityHashMap<>();

        String s1 = new String("klucz");  // nowy obiekt
        String s2 = new String("klucz");  // inny nowy obiekt (ale equals() = true)
        String s3 = s1;                    // ta sama referencja

        identityMap.put(s1, 1);
        identityMap.put(s2, 2); // to jest INNY klucz (inna referencja == IdentityHashMap)
        identityMap.put(s3, 3); // NADPISUJE s1 (ta sama referencja!)

        System.out.println("s1 == s2: " + (s1 == s2));           // false
        System.out.println("s1.equals(s2): " + s1.equals(s2));   // true
        System.out.println("Rozmiar IdentityHashMap: " + identityMap.size()); // 2 (s1/s3 + s2)
        System.out.println("Wartość s1: " + identityMap.get(s1)); // 3 (nadpisana)
        System.out.println("Wartość s2: " + identityMap.get(s2)); // 2

        // Porównaj z HashMap:
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(s1, 1);
        hashMap.put(s2, 2); // NADPISUJE s1 (equals() = true → ten sam klucz!)
        System.out.println("\nHashMap rozmiar (equals): " + hashMap.size()); // 1

        // Praktyczny przykład: śledzenie odwiedzonych obiektów
        IdentityHashMap<Object, Boolean> visited = new IdentityHashMap<>();
        Object a = new Object(), b = new Object(), c = a;
        visited.put(a, true); visited.put(b, true);
        System.out.println("a odwiedzono: " + visited.containsKey(a));
        System.out.println("c (==a) odwiedzono: " + visited.containsKey(c));
        System.out.println("new Object() odwiedzono: " + visited.containsKey(new Object()));

        /*
         * ============================================================
         * 🔹 WEAKHASHMAP – KLUCZE ZE SŁABYMI REFERENCJAMI
         * ============================================================
         * WeakHashMap<K,V>
         *
         * Cechy:
         * ✅ Klucze są przechowywane jako WeakReference
         * ✅ Gdy klucz nie ma ŻADNEJ silnej referencji → GC może go usunąć
         * ✅ Wpis automatycznie znika z mapy przy GC klucza
         * ❌ Nieprzewidywalne zachowanie – wpisy mogą znikać!
         * ❌ Null klucz jest OK (jeden)
         *
         * Kiedy WeakHashMap?
         * - Cache który nie powinien blokować GC
         * - Metadane dla obiektów (np. klasy w JVM – java.lang.ClassLoader)
         * - WeakHashMap = "pamiętaj dopóki żywy obiekt"
         */

        System.out.println("\n=== WeakHashMap ===");

        WeakHashMap<Object, String> weakMap = new WeakHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();

        weakMap.put(key1, "Wartość 1");
        weakMap.put(key2, "Wartość 2");
        System.out.println("Przed GC: " + weakMap.size()); // 2

        // Po usunięciu silnej referencji do key2:
        key2 = null;
        System.gc(); // Sugestia dla GC (nie gwarantowana!)
        Thread.yield();

        // Po GC key2 może zniknąć z mapy:
        System.out.println("Po GC (przybliżone): rozmiar może być 1 lub 2");
        System.out.println("key1 nadal dostępny: " + weakMap.containsKey(key1));

        /*
         * ============================================================
         * 🔹 PORÓWNANIE WSZYSTKICH MAP
         * ============================================================
         */

        System.out.println("\n=== Porównanie Map ===");
        System.out.println("HashMap          → szybka, brak kolejności, null klucz OK");
        System.out.println("LinkedHashMap    → insertion/access order, null klucz OK");
        System.out.println("TreeMap          → posortowane klucze, null klucz ❌");
        System.out.println("EnumMap          → tylko enum klucze, szybka jak tablica");
        System.out.println("IdentityHashMap  → klucze przez ==, niszczy kontrakt");
        System.out.println("WeakHashMap      → GC może usunąć klucze, do cache");
        System.out.println("ConcurrentHashMap→ thread-safe, null ❌, segmentowany lock");
        System.out.println("Hashtable        → legacy, synchronized, null ❌");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE DECYZJI
         * ============================================================
         * Potrzebujesz szybkiej mapy?          → HashMap
         * Potrzebujesz kolejności wstawiania?  → LinkedHashMap
         * Potrzebujesz posortowanych kluczy?   → TreeMap
         * Klucze to enum?                      → EnumMap (zawsze!)
         * Thread-safe mapa?                    → ConcurrentHashMap
         * Cache który nie blokuje GC?          → WeakHashMap
         * Porównuj klucze przez == ?           → IdentityHashMap
         */
    }
}

enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }
enum Season { SPRING, SUMMER, AUTUMN, WINTER }
