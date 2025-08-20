package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_02 — "Długości słów"
 *
 * ZADANIE:
 * Masz listę słów (String).
 * 1) Użyj Stream API, aby obliczyć długość każdego słowa.
 * 2) Odfiltruj tylko te, które mają długość większą niż 3.
 * 3) Posortuj malejąco.
 * 4) Zwróć listę długości.
 *
 * Przykład:
 * Wejście: ["kot", "samochód", "pies", "oko"]
 * Wynik: [8, 4]
 */
public class JQ_Streams_02 {

    public static List<Integer> processWords(List<String> words) {
       return words.stream().
               filter(a->a.length()>3)
               .sorted(Comparator.reverseOrder())
               .map(a->a.length())
               .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList("kot", "samochód", "pies", "oko");
        System.out.println(processWords(input)); // [8, 4]
    }
}
