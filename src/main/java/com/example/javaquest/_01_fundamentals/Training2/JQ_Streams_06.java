package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_06 — "Ile słów na literę?"
 *
 * ZADANIE:
 * Masz listę słów (String).
 * 1) Pogrupuj słowa po pierwszej literze (jak w poprzednim zadaniu),
 * 2) ale tym razem zwróć liczbę słów w każdej grupie.
 * 3) Zwróć Map<Character, Long>.
 *    (Użyj Collectors.counting()).
 *
 * Przykład:
 * Wejście: ["kot", "kajak", "pies", "ala", "arma"]
 * Wynik: {k=2, p=1, a=2}
 */
public class JQ_Streams_06 {

    public static Map<Character, Long> countByFirstLetter(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(
                        s -> s.charAt(0),       // klucz = pierwsza litera
                        Collectors.counting()   // wartość = licznik elementów
                ));
    }


        public static void main(String[] args) {
        List<String> input = Arrays.asList("kot", "kajak", "pies", "ala", "arma");
        System.out.println(countByFirstLetter(input)); // {k=2, p=1, a=2}
    }
}
