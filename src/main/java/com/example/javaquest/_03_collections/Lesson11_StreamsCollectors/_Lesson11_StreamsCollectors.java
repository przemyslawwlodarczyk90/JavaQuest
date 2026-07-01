package com.example.javaquest._03_collections.Lesson11_StreamsCollectors;

import java.util.*;
import java.util.stream.Collectors;

public class _Lesson11_StreamsCollectors {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🪣 COLLECTORS – ZBIERANIE WYNIKÓW STRUMIENIA
         * ============================================================
         * Collectors to klasa utility z metodami fabrycznymi dla Collector.
         * Używana w metodzie .collect() strumienia.
         *
         * Najważniejsze:
         * toList, toSet, toUnmodifiableList, toMap,
         * groupingBy, partitioningBy, joining,
         * summingInt, averagingInt, counting, summarizingInt
         */

        List<String> names = List.of("Anna", "Bartek", "Celina", "Adam", "Anna", "Beata");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /*
         * ============================================================
         * 🔹 toList / toSet / toUnmodifiableList
         * ============================================================
         */

        List<String> filteredList = names.stream()
                .filter(n -> n.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println("toList: " + filteredList);

        Set<String> uniqueNames = names.stream()
                .collect(Collectors.toSet());
        System.out.println("toSet (bez duplikatów): " + uniqueNames.size() + " el.");

        List<String> immutableList = names.stream()
                .collect(Collectors.toUnmodifiableList());
        System.out.println("toUnmodifiableList: " + immutableList);

        /*
         * ============================================================
         * 🔹 Collectors.toSet()
         * ============================================================
         */

        Set<String> nameSet = names.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        System.out.println("\ntoSet (lowercase): " + nameSet);

        /*
         * ============================================================
         * 🔹 Collectors.summingInt / averagingInt / counting
         * ============================================================
         */

        int totalLength = names.stream()
                .collect(Collectors.summingInt(String::length));
        System.out.println("\nSuma długości: " + totalLength);

        double avgLength = names.stream()
                .collect(Collectors.averagingInt(String::length));
        System.out.println("Średnia długość: " + avgLength);

        long count = names.stream()
                .filter(n -> n.length() > 4)
                .collect(Collectors.counting());
        System.out.println("Liczba z długością > 4: " + count);

        IntSummaryStatistics stats = names.stream()
                .collect(Collectors.summarizingInt(String::length));
        System.out.println("Statystyki: min=" + stats.getMin() +
                ", max=" + stats.getMax() + ", avg=" + stats.getAverage());

        /*
         * ============================================================
         * 🔹 Collectors.toMap(keyMapper, valueMapper)
         * ============================================================
         * Uwaga: duplikaty kluczy → IllegalStateException!
         * Użyj toMap z mergeFunction dla duplikatów.
         */

        Map<String, Integer> nameLengths = List.of("Anna", "Bartek", "Celina").stream()
                .collect(Collectors.toMap(
                        name -> name,          // klucz
                        String::length         // wartość
                ));
        System.out.println("\ntoMap: " + nameLengths);

        // Gdy mogą być duplikaty kluczy:
        Map<Integer, String> lengthToName = names.stream()
                .collect(Collectors.toMap(
                        String::length,
                        name -> name,
                        (existing, replacement) -> existing + ", " + replacement // merge
                ));
        System.out.println("toMap z merge: " + lengthToName);

        /*
         * ============================================================
         * 🔹 Collectors.groupingBy
         * ============================================================
         * Grupuje elementy według klucza → Map<K, List<T>>
         */

        Map<Integer, List<String>> byLength = names.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("\ngroupingBy length: " + byLength);

        // groupingBy z downstream collectorem
        Map<Integer, Long> countByLength = names.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println("Liczba per długość: " + countByLength);

        Map<Character, List<String>> byFirstLetter = names.stream()
                .collect(Collectors.groupingBy(n -> n.charAt(0)));
        System.out.println("Przez pierwszą literę: " + byFirstLetter);

        /*
         * ============================================================
         * 🔹 Collectors.partitioningBy
         * ============================================================
         * Dzieli na dwie grupy: true i false → Map<Boolean, List<T>>
         */

        Map<Boolean, List<Integer>> evenOdd = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("\nParzyste: " + evenOdd.get(true));
        System.out.println("Nieparzyste: " + evenOdd.get(false));

        Map<Boolean, List<String>> longShort = names.stream()
                .collect(Collectors.partitioningBy(n -> n.length() > 4));
        System.out.println("Długie (>4): " + longShort.get(true));
        System.out.println("Krótkie (<=4): " + longShort.get(false));

        /*
         * ============================================================
         * 🔹 Collectors.joining
         * ============================================================
         * Łączy stringi z opcjonalnym separatorem, prefiksem, sufiksem.
         */

        String joined = names.stream()
                .collect(Collectors.joining());
        System.out.println("\njoining: " + joined);

        String joinedSep = names.stream()
                .collect(Collectors.joining(", "));
        System.out.println("joining(,): " + joinedSep);

        String joinedFull = names.stream()
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("joining(,,[]): " + joinedFull);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * collect(Collectors.toList())                    → do listy
         * collect(Collectors.toSet())                     → do zbioru
         * collect(Collectors.toMap(k, v))                 → do mapy
         * collect(Collectors.summingInt(fn))              → suma
         * collect(Collectors.averagingInt(fn))            → średnia
         * collect(Collectors.counting())                  → liczba
         * collect(Collectors.summarizingInt(fn))          → statystyki
         * collect(Collectors.groupingBy(fn))              → grupowanie
         * collect(Collectors.partitioningBy(pred))        → podział
         * collect(Collectors.joining(sep, prefix, suffix))→ łączenie
         */
    }
}
