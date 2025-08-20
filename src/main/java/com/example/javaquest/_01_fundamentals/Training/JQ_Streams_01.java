package com.example.javaquest._01_fundamentals.Training;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_01 — "Filtr liczb"
 *
 * ZADANIE:
 * Masz listę liczb całkowitych.
 * 1) Użyj Stream API, aby wybrać tylko liczby parzyste.
 * 2) Każdą liczbę pomnóż przez 2.
 * 3) Posortuj rosnąco.
 * 4) Zwróć jako List<Integer>.
 *
 * Przykład:
 * Wejście: [5, 2, 8, 3, 1]
 * Wynik: [4, 16]
 */
public class JQ_Streams_01 {

    public static List<Integer> processNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(a -> a % 2 == 0)   // tylko parzyste
                .map(a -> a * 2)           // pomnóż przez 2
                .sorted()                  // posortuj rosnąco
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(5, 2, 8, 3, 1);
        System.out.println(processNumbers(input)); // [4, 16]
    }
}
