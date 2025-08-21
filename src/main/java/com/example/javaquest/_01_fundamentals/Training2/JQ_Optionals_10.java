package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_10 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstOverTen(List<Integer> numbers), która:
     * 1. Szuka pierwszej liczby większej niż 10.
     * 2. Jeśli znajdzie → zwraca ją jako String (np. "15").
     * 3. Jeśli nie znajdzie → rzuca wyjątek IllegalArgumentException z komunikatem "brak liczby > 10".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), map(), orElseThrow().
     */

    public static String getFirstOverTen(List<Integer> numbers) {
        // TODO: zaimplementuj tutaj
        return numbers.stream()
                .filter(a->a>10)
                .findFirst()
                .map(String::valueOf)
                .orElseThrow(()-> new IllegalArgumentException("brak liczby większej > 10"));
    }

    public static void main(String[] args) {
        List<Integer> numbers1 = Arrays.asList(3, 7, 12, 5);
        List<Integer> numbers2 = Arrays.asList(1, 2, 3);

        System.out.println(getFirstOverTen(numbers1)); // powinno zwrócić "12"
        System.out.println(getFirstOverTen(numbers2)); // powinno rzucić IllegalArgumentException
    }
}
