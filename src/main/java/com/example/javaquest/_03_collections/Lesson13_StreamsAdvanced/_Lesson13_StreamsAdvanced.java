package com.example.javaquest._03_collections.Lesson13_StreamsAdvanced;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class _Lesson13_StreamsAdvanced {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🌊 ZAAWANSOWANE OPERACJE STRUMIENIOWE
         * ============================================================
         * limit, skip, iterate, generate, flatMap
         */

        /*
         * ============================================================
         * 🔹 limit(n) – ogranicz do n elementów
         * ============================================================
         */

        System.out.println("=== limit(5) ===");
        Stream.iterate(1, n -> n + 1)     // nieskończony strumień 1, 2, 3, ...
              .limit(5)                    // weź tylko 5
              .forEach(n -> System.out.print(n + " ")); // 1 2 3 4 5
        System.out.println();

        /*
         * ============================================================
         * 🔹 skip(n) – pomiń n pierwszych elementów
         * ============================================================
         */

        System.out.println("\n=== skip(3) ===");
        List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .stream()
            .skip(3)
            .forEach(n -> System.out.print(n + " ")); // 4 5 6 7 8 9 10
        System.out.println();

        // limit + skip = paginacja
        System.out.println("\nPaginacja (strona 2, po 3):");
        List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .stream()
            .skip(3)          // pomiń stronę 1
            .limit(3)         // weź 3 elementy strony 2
            .forEach(n -> System.out.print(n + " ")); // 4 5 6
        System.out.println();

        /*
         * ============================================================
         * 🔹 iterate(seed, UnaryOperator) – nieskończony strumień
         * ============================================================
         * iterate(seed, fn) → seed, fn(seed), fn(fn(seed)), ...
         * iterate(seed, Predicate, fn) → z warunkiem zakończenia (Java 9+)
         */

        System.out.println("\n=== iterate ===");

        // Potęgi 2
        Stream.iterate(1, n -> n * 2)
              .limit(10)
              .forEach(n -> System.out.print(n + " ")); // 1 2 4 8 16 ...
        System.out.println();

        // Z warunkiem stopu (Java 9+)
        Stream.iterate(0, n -> n < 100, n -> n + 15)
              .forEach(n -> System.out.print(n + " ")); // 0 15 30 45 60 75 90
        System.out.println();

        // Ciąg Fibonacciego
        Stream.iterate(new int[]{0, 1}, arr -> new int[]{arr[1], arr[0] + arr[1]})
              .limit(10)
              .map(arr -> arr[0])
              .forEach(n -> System.out.print(n + " ")); // 0 1 1 2 3 5 8 13 21 34
        System.out.println();

        /*
         * ============================================================
         * 🔹 generate(Supplier) – nieskończony strumień z generatora
         * ============================================================
         */

        System.out.println("\n=== generate ===");

        // Losowe liczby
        Stream.generate(Math::random)
              .limit(5)
              .map(d -> String.format("%.3f", d))
              .forEach(n -> System.out.print(n + " "));
        System.out.println();

        // Stała wartość
        Stream.generate(() -> "hello")
              .limit(3)
              .forEach(s -> System.out.print(s + " "));
        System.out.println();

        /*
         * ============================================================
         * 🔹 flatMap – spłaszczanie strumienia strumieni
         * ============================================================
         * map:     Stream<Stream<T>> – każdy element → nowy strumień
         * flatMap: Stream<T>         – "spłaszcza" do jednego strumienia
         */

        System.out.println("\n=== flatMap ===");

        List<List<Integer>> nested = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8, 9)
        );

        // Bez flatMap: Stream<List<Integer>> – nie możemy sumować
        // Z flatMap: Stream<Integer> – każdy element dostępny
        int sum = nested.stream()
                .flatMap(List::stream)    // List<Integer> → Stream<Integer>
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Suma zagnieżdżonych list: " + sum);

        // Przykład ze słowami w zdaniach
        List<String> sentences = List.of(
                "Java jest fajny",
                "Kolekcje są przydatne",
                "Strumienie upraszczają kod"
        );

        long wordCount = sentences.stream()
                .flatMap(sentence -> Stream.of(sentence.split(" ")))
                .distinct()
                .count();
        System.out.println("Unikalnych słów: " + wordCount);

        sentences.stream()
                .flatMap(sentence -> Stream.of(sentence.split(" ")))
                .filter(word -> word.length() > 4)
                .sorted()
                .forEach(w -> System.out.print(w + " "));
        System.out.println();

        /*
         * ============================================================
         * 🔹 IntStream, LongStream, DoubleStream – prymitywne strumienie
         * ============================================================
         * Unikają boxing/unboxing, mają dodatkowe metody: sum, average, range
         */

        System.out.println("\n=== IntStream ===");

        // Zakres (range)
        IntStream.range(1, 6)          // [1, 2, 3, 4, 5]
                 .forEach(n -> System.out.print(n + " "));
        System.out.println();

        IntStream.rangeClosed(1, 5)    // [1, 2, 3, 4, 5] – z końcem włącznie
                 .forEach(n -> System.out.print(n + " "));
        System.out.println();

        // Szybka suma/średnia
        int rangeSum = IntStream.rangeClosed(1, 100).sum();
        System.out.println("Suma 1..100: " + rangeSum);

        double avg = IntStream.of(1, 2, 3, 4, 5).average().getAsDouble();
        System.out.println("Średnia: " + avg);

        // mapToObj – prymitywny → obiektowy strumień
        IntStream.rangeClosed(1, 5)
                 .mapToObj(n -> "Element " + n)
                 .forEach(System.out::println);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * limit(n)            → weź maksymalnie n elementów
         * skip(n)             → pomiń n pierwszych elementów
         * iterate(seed, fn)   → nieskończony strumień sekwencyjny
         * generate(supplier)  → nieskończony strumień z generatora
         * flatMap(fn)         → spłaszczanie Stream<Stream<T>> → Stream<T>
         * IntStream.range     → zakresy int bez boxing
         * .sum/.average       → szybka agregacja prymitywów
         */
    }
}
