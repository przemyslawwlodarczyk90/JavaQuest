package com.example.javaquest._03_collections.Lesson10_StreamsIntro;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class _Lesson10_StreamsIntro {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🌊 STREAMS – CZYM SĄ?
         * ============================================================
         * Stream (java.util.stream) to potok przetwarzania danych
         * wprowadzony w Java 8.
         *
         * Stream NIE JEST kolekcją:
         * - nie przechowuje danych (tylko je przetwarza)
         * - można go skonsumować tylko raz
         * - operacje są leniwe (lazy) – wykonują się tylko przy operacji terminalnej
         *
         * Anatomia pipeline:
         * źródło → [operacje pośrednie]... → operacja terminalna
         *
         * Operacje POŚREDNIE (intermediate) – lazy, zwracają Stream:
         *   filter, map, sorted, distinct, peek, limit, skip, flatMap
         *
         * Operacje TERMINALNE (terminal) – eager, konsumują Stream:
         *   forEach, collect, count, min, max, reduce, findFirst,
         *   anyMatch, allMatch, noneMatch, toList (Java 16+)
         */

        /*
         * ============================================================
         * 🔹 TWORZENIE STRUMIENIA
         * ============================================================
         */

        // Ze kolekcji (najczęściej)
        List<String> names = List.of("Anna", "Bartek", "Celina", "Adam", "Beata");
        Stream<String> s1 = names.stream();

        // Z wartości bezpośrednio
        Stream<String> s2 = Stream.of("a", "b", "c");

        // Z tablicy
        String[] arr = {"x", "y", "z"};
        Stream<String> s3 = Arrays.stream(arr);

        // Pusty stream
        Stream<String> empty = Stream.empty();

        /*
         * ============================================================
         * 🔹 FILTER – filtrowanie elementów
         * ============================================================
         * filter(Predicate<T>) → zostawia elementy spełniające warunek
         */

        System.out.println("=== filter ===");
        names.stream()
             .filter(name -> name.startsWith("A"))
             .forEach(System.out::println); // Anna, Adam

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .forEach(n -> System.out.print(n + " ")); // 2 4 6 8 10
        System.out.println();

        /*
         * ============================================================
         * 🔹 MAP – transformacja elementów
         * ============================================================
         * map(Function<T, R>) → przekształca każdy element
         * mapToInt, mapToDouble, mapToLong → prymitywne strumienie
         */

        System.out.println("\n=== map ===");
        names.stream()
             .map(String::toUpperCase)
             .forEach(System.out::println);

        names.stream()
             .map(name -> name + " (" + name.length() + " liter)")
             .forEach(System.out::println);

        // map do zmiany typu
        List<String> numStrings = List.of("1", "2", "3", "4", "5");
        numStrings.stream()
                  .map(Integer::parseInt)     // String → Integer
                  .map(n -> n * n)            // Integer → Integer (kwadrat)
                  .forEach(n -> System.out.print(n + " "));
        System.out.println();

        /*
         * ============================================================
         * 🔹 SORTED – sortowanie
         * ============================================================
         * sorted()              → naturalne sortowanie
         * sorted(Comparator)    → niestandardowe
         */

        System.out.println("\n=== sorted ===");
        names.stream()
             .sorted()
             .forEach(System.out::println);

        names.stream()
             .sorted((a, b) -> Integer.compare(a.length(), b.length()))
             .forEach(n -> System.out.print(n + " "));
        System.out.println();

        /*
         * ============================================================
         * 🔹 DISTINCT – unikalne elementy
         * ============================================================
         */

        System.out.println("\n=== distinct ===");
        List<Integer> withDups = List.of(1, 2, 2, 3, 3, 3, 4, 1);
        withDups.stream()
                .distinct()
                .forEach(n -> System.out.print(n + " ")); // 1 2 3 4
        System.out.println();

        /*
         * ============================================================
         * 🔹 PEEK – podgląd bez modyfikacji (debug)
         * ============================================================
         * Zwraca ten sam Stream, ale pozwala podejrzeć elementy.
         * Używane głównie do debugowania.
         */

        System.out.println("\n=== peek (debug) ===");
        long count = names.stream()
                          .peek(n -> System.out.print("[before filter] " + n + " "))
                          .filter(n -> n.length() > 4)
                          .peek(n -> System.out.print("[after filter] " + n + " "))
                          .count();
        System.out.println("\nLiczba: " + count);

        /*
         * ============================================================
         * 🔹 ŁĄCZENIE OPERACJI (pipeline)
         * ============================================================
         */

        System.out.println("\n=== pipeline ===");
        List<String> result = names.stream()
                .filter(n -> n.length() > 4)       // tylko dłuższe niż 4
                .map(String::toUpperCase)            // na wielkie litery
                .sorted()                            // posortuj
                .toList();                           // zbierz do listy (Java 16+)
        System.out.println("Wynik pipeline: " + result);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Stream pipeline:
         * collection.stream()           → otwórz strumień
         * .filter(predicate)            → filtruj
         * .map(function)                → transformuj
         * .sorted() / sorted(comp)      → posortuj
         * .distinct()                   → usuń duplikaty
         * .peek(consumer)               → podejrzyj (debug)
         * .forEach / .collect / .count  → operacja terminalna
         *
         * Strumień wykonuje się leniwie:
         * Wszystkie operacje pośrednie są "zapamiętywane", dopiero
         * terminalna uruchamia całe przetwarzanie.
         */
    }
}
