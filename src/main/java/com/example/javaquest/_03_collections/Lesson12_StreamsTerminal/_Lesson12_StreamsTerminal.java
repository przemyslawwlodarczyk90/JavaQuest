package com.example.javaquest._03_collections.Lesson12_StreamsTerminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class _Lesson12_StreamsTerminal {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🏁 OPERACJE TERMINALNE STRUMIENIA
         * ============================================================
         * Terminalna operacja KONSUMUJE strumień – po niej stream jest zamknięty.
         * Uruchamia całe leniwe przetwarzanie pipeline'u.
         *
         * Omawiane tutaj:
         * min, max, count, reduce, findFirst, findAny,
         * anyMatch, allMatch, noneMatch, map + referencja do metody
         */

        List<Integer> numbers = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        List<String> names = List.of("Anna", "Bartek", "Celina", "Adam", "Beata");

        /*
         * ============================================================
         * 🔹 min() / max()
         * ============================================================
         * min(Comparator) → Optional<T> – najmniejszy element
         * max(Comparator) → Optional<T> – największy element
         */

        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);

        System.out.println("Min: " + min.orElse(-1));  // 1
        System.out.println("Max: " + max.orElse(-1));  // 9

        Optional<String> shortest = names.stream()
                .min((a, b) -> Integer.compare(a.length(), b.length()));
        System.out.println("Najkrótsze imię: " + shortest.orElse("?"));

        // Prymitywne strumienie mają min/max bez Comparatora:
        int minPrimitive = IntStream.of(3, 1, 4, 1, 5).min().getAsInt();
        System.out.println("Min (IntStream): " + minPrimitive);

        /*
         * ============================================================
         * 🔹 count()
         * ============================================================
         * Liczy elementy w strumieniu.
         */

        long countAll = numbers.stream().count();
        long countBig = numbers.stream().filter(n -> n > 4).count();
        System.out.println("\nLiczba wszystkich: " + countAll);
        System.out.println("Liczba > 4: " + countBig);

        /*
         * ============================================================
         * 🔹 reduce()
         * ============================================================
         * reduce(identity, BinaryOperator) → T
         * reduce(BinaryOperator)           → Optional<T>
         *
         * Kumuluje elementy w jeden wynik.
         */

        // Suma przez reduce
        int sum = numbers.stream()
                .reduce(0, (acc, n) -> acc + n);
        System.out.println("\nSuma przez reduce: " + sum);

        // Suma przez referencję do metody
        int sum2 = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("Suma (method ref): " + sum2);

        // Iloczyn
        int product = List.of(1, 2, 3, 4, 5).stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("Iloczyn 1*2*3*4*5: " + product);

        // Konkatenacja stringów
        String concat = names.stream()
                .reduce("", (a, b) -> a + (a.isEmpty() ? "" : ", ") + b);
        System.out.println("Concat: " + concat);

        /*
         * ============================================================
         * 🔹 findFirst() / findAny()
         * ============================================================
         * findFirst() → Optional<T> – pierwszy element (deterministyczny)
         * findAny()   → Optional<T> – dowolny (szybszy w parallel)
         */

        Optional<String> first = names.stream()
                .filter(n -> n.startsWith("B"))
                .findFirst();
        System.out.println("\nPierwsze zaczynające się na B: " + first.orElse("brak"));

        Optional<Integer> any = numbers.stream()
                .filter(n -> n > 5)
                .findAny();
        System.out.println("Dowolne > 5: " + any.orElse(-1));

        /*
         * ============================================================
         * 🔹 anyMatch / allMatch / noneMatch
         * ============================================================
         * anyMatch(Predicate)  → czy JAKIKOLWIEK element spełnia warunek
         * allMatch(Predicate)  → czy WSZYSTKIE spełniają warunek
         * noneMatch(Predicate) → czy ŻADEN nie spełnia warunku
         *
         * Zwracają boolean. Są krótko-zwierające (short-circuit).
         */

        System.out.println("\nanyMatch > 8: " + numbers.stream().anyMatch(n -> n > 8));
        System.out.println("allMatch > 0: " + numbers.stream().allMatch(n -> n > 0));
        System.out.println("noneMatch < 0: " + numbers.stream().noneMatch(n -> n < 0));

        boolean allLong = names.stream().allMatch(n -> n.length() > 3);
        System.out.println("Wszystkie imiona > 3 litery: " + allLong);

        boolean anyStartsZ = names.stream().anyMatch(n -> n.startsWith("Z"));
        System.out.println("Jakieś imię na Z: " + anyStartsZ);

        /*
         * ============================================================
         * 🔹 MAP + REFERENCJA DO METODY
         * ============================================================
         * Referencje do metod (::) – skrócona forma lambd:
         *
         * Typy referencji:
         * Klasa::metodaStatyczna    → n -> Integer.parseInt(n)
         * obiekt::metodaInstancyjna → s -> System.out.println(s)
         * Klasa::metodaInstancyjna  → s -> s.toUpperCase()
         * Klasa::new                → konstruktor
         */

        System.out.println("\nReferencje do metod:");

        // Klasa::metodaStatyczna
        List.of("1", "2", "3").stream()
                .map(Integer::parseInt)
                .forEach(System.out::println);

        // Klasa::metodaInstancyjna
        names.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // obiekt::metodaInstancyjna
        names.stream()
                .forEach(System.out::println);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * min(comp) / max(comp)         → Optional<T>
         * count()                        → long
         * reduce(identity, BinaryOp)    → T (z wartością startową)
         * reduce(BinaryOp)              → Optional<T> (bez wartości start.)
         * findFirst() / findAny()       → Optional<T>
         * anyMatch / allMatch / noneMatch → boolean (short-circuit)
         *
         * Referencje do metod (::) są zamienną formą lambd.
         */
    }
}
