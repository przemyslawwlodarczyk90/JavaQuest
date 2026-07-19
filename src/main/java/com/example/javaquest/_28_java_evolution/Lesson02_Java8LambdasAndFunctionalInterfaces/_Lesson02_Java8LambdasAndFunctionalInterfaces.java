package com.example.javaquest._28_java_evolution.Lesson02_Java8LambdasAndFunctionalInterfaces;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson02_Java8LambdasAndFunctionalInterfaces {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: Java 8 (2014) - Lambdy i interfejsy funkcyjne ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_14_advancedjava/Lesson08-11`
         * ============================================================
         * Java 8 TO NAJWIEKSZA rewolucja W historii jezyka - LAMBDY
         * (wyrazenia funkcyjne) + `java.util.function` (wbudowane
         * interfejsy funkcyjne) + Streamy (Lesson03) NAPRAWDE
         * ZMIENILY STYL pisania Javy - Z IMPERATYWNEGO (petle+`if`)
         * NA CZESCIOWO FUNKCYJNY (transformacje danych). TEN
         * rozdzial NIE POWTARZA mechaniki (`_14_advancedjava` JUZ to
         * zrobil SZCZEGOLOWO) - TU umieszczamy TO NA OSI CZASU:
         * "PRZED Java 8, Javy NIE dalo sie tak pisac".
         *
         * 🔍 PRZED Java 8: JEDYNYM sposobem "przekazania zachowania"
         * byla ANONIMOWA klasa implementujaca interfejs Z JEDNA
         * metoda - WIELOKROTNIE dluzszy zapis.
         */
        System.out.println("Java 8 (marzec 2014): lambdy + java.util.function + Streamy - NAJWIEKSZA zmiana W historii jezyka.");

        demonstrateBeforeAndAfterJava8();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PRZED Java 8: anonimowa klasa `new Comparator<String>() {
         *   public int compare(...) {...} }` - 4+ linie NA prosta
         *   logike.
         * - Java 8: `(a, b) -> a.length() - b.length()` - 1 linia.
         * - `@FunctionalInterface` - interfejs Z DOKLADNIE JEDNA
         *   metoda abstrakcyjna (SAM) - lambda MOZE go
         *   IMPLEMENTOWAC.
         * - `java.util.function` - GOTOWE interfejsy (`Function`,
         *   `Predicate`, `Supplier`, `Consumer`) - NIE TRZEBA
         *   definiowac WLASNYCH DLA typowych przypadkow (powiazanie
         *   Z `_14_advancedjava/Lesson11`).
         * - POGLEBIONA teoria: `_14_advancedjava/Lesson08-11` (JUZ
         *   znasz TE mechanike SZCZEGOLOWO).
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateBeforeAndAfterJava8() {
        List<String> words = List.of("Java", "Kotlin", "C", "Python", "Go");

        System.out.println("\n--- PRZED Java 8: anonimowa klasa (Java 7 i starsze) ---");
        Predicate<String> longWordOld = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() > 3;
            }
        };
        List<String> longWordsOld = words.stream().filter(longWordOld).toList();
        System.out.println("Anonimowa klasa (4 linie kodu DLA prostego warunku): " + longWordsOld);

        System.out.println("\n--- PO Java 8: lambda (1 linia) ---");
        Predicate<String> longWordNew = s -> s.length() > 3;
        List<String> longWordsNew = words.stream().filter(longWordNew).toList();
        System.out.println("Lambda (1 linia, TA SAMA logika): " + longWordsNew);

        assertThat(longWordsOld).isEqualTo(longWordsNew);

        System.out.println("\n--- Wbudowane interfejsy funkcyjne (java.util.function) ---");
        Function<String, Integer> wordLength = String::length;
        int totalLength = words.stream().mapToInt(wordLength::apply).sum();
        System.out.println("Suma dlugosci WSZYSTKICH slow (Function<String,Integer> + referencja do metody): " + totalLength);

        assertThat(totalLength).isEqualTo(words.stream().mapToInt(String::length).sum());
    }
}
