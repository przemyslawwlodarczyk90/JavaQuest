package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_07 — "Długości słów na literę"
 *
 * ZADANIE:
 * Masz listę słów (String).
 * 1) Pogrupuj słowa po pierwszej literze,
 * 2) ale w każdej grupie zapisz **długości słów** zamiast samych słów.
 * 3) Zwróć Map<Character, List<Integer>>.
 *
 * Przykład:
 * Wejście: ["kot", "kajak", "pies", "ala", "arma"]
 * Wynik:
 * {
 *   k = [3, 5],
 *   p = [4],
 *   a = [3, 4]
 * }
 */
import java.util.*;
import java.util.stream.*;

public class JQ_Streams_07 {

    public static Map<Character, List<Integer>> lengthsByFirstLetter(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(
                        s -> s.charAt(0), // klucz = pierwsza litera
                        Collectors.mapping(
                                String::length, // przekształcamy słowo na jego długość
                                Collectors.toList() // zbieramy jako listę
                        )
                ));
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList("kot", "kajak", "pies", "ala", "arma");
        System.out.println(lengthsByFirstLetter(input));
        // {k=[3, 5], p=[4], a=[3, 4]}
    }
}
