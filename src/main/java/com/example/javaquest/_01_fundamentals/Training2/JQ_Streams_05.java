package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_05 — "Grupowanie słów po pierwszej literze"
 *
 * ZADANIE:
 * Masz listę słów (String).
 * 1) Pogrupuj je według pierwszej litery.
 * 2) Wynik zwróć jako Map<Character, List<String>>.
 *
 * Przykład:
 * Wejście: ["kot", "kajak", "pies", "ala", "arma"]
 * Wynik:
 * {
 *   k = ["kot", "kajak"],
 *   p = ["pies"],
 *   a = ["ala", "arma"]
 * }
 */
public class JQ_Streams_05 {

    public static Map<Character, List<String>> groupByFirstLetter(List<String> words) {
        return words.stream()
                .filter(s -> s != null && !s.isEmpty()) // prosta ochrona na puste/null
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
    }


    public static void main(String[] args) {
        List<String> input = Arrays.asList("kot", "kajak", "pies", "ala", "arma");
        System.out.println(groupByFirstLetter(input));
        // {k=[kot, kajak], p=[pies], a=[ala, arma]}
    }
}
