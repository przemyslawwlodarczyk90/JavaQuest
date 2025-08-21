package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_06 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstNegative(List<Integer> numbers), która:
     * 1. Szuka pierwszej liczby ujemnej.
     * 2. Jeśli znajdzie → zwraca ją jako String (np. "-5").
     * 3. Jeśli nie znajdzie → zwraca napis "brak ujemnej".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), map(), orElse().
     */

    public static String getFirstNegative(List<Integer> numbers) {
        // TODO: zaimplementuj tutaj
        return numbers.stream()
                .filter(a->a<0)
                .findFirst()
                .map(String::valueOf)
                .orElse("brak ujemnej");
    }

    public static void main(String[] args) {
        List<Integer> numbers1 = Arrays.asList(3, 7, -5, 2, -8);
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4);

        System.out.println(getFirstNegative(numbers1)); // powinno zwrócić "-5"
        System.out.println(getFirstNegative(numbers2)); // powinno zwrócić "brak ujemnej"
    }
}
