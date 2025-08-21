package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_04 {

    /*
     * ĆWICZENIE:
     * Napisz metodę findEvenNumber(List<Integer> numbers), która:
     * 1. Szuka pierwszej liczby parzystej.
     * 2. Jeśli znajdzie → zwraca ją jako napis (np. "4").
     * 3. Jeśli nie znajdzie → zwraca napis "brak parzystej".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), Optional.map(), orElse().
     */

    public static String findEvenNumber(List<Integer> numbers) {
        // TODO: zaimplementuj tutaj
        return numbers.stream()
                .filter(a->a%2==0)
                .findFirst()
                .map(String::valueOf)
                .orElse("brak parzystej");
    }

    public static void main(String[] args) {
        List<Integer> numbers1 = Arrays.asList(1, 3, 5, 7, 10, 11);
        List<Integer> numbers2 = Arrays.asList(1, 3, 5, 7, 9);

        System.out.println(findEvenNumber(numbers1)); // powinno zwrócić "10"
        System.out.println(findEvenNumber(numbers2)); // powinno zwrócić "brak parzystej"
    }
}
