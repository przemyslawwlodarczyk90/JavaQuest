package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_12 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstWordWithDigit(List<String> words), która:
     * 1. Szuka pierwszego słowa, które zawiera w sobie cyfrę (0–9).
     * 2. Jeśli znajdzie → zwraca je w oryginalnej postaci.
     * 3. Jeśli nie znajdzie → zwraca napis "brak cyfry".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), orElse().
     * Do sprawdzenia użyj np. regexa: word.matches(".*\\d.*")
     */

    public static String getFirstWordWithDigit(List<String> words) {
        // TODO: zaimplementuj tutaj
        return words.stream()
                .filter(a->a.matches(".*\\d.*"))
                .findFirst()
                .orElse("brak cyfry");
    }

    public static void main(String[] args) {
        List<String> words1 = Arrays.asList("kot123", "pies", "dom");
        List<String> words2 = Arrays.asList("ala", "ola", "tomek");

        System.out.println(getFirstWordWithDigit(words1)); // powinno zwrócić "kot123"
        System.out.println(getFirstWordWithDigit(words2)); // powinno zwrócić "brak cyfry"
    }
}
