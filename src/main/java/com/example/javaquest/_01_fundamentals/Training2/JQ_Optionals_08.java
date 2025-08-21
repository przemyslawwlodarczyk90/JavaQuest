package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_08 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstUppercase(List<String> words), która:
     * 1. Szuka pierwszego słowa zaczynającego się wielką literą.
     * 2. Jeśli znajdzie → zwraca je.
     * 3. Jeśli nie znajdzie → zwraca napis "brak wielkiej litery".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), orElse().
     */

    public static String getFirstUppercase(List<String> words) {
        // TODO: zaimplementuj tutaj
        return words.stream()
                .filter(a->Character.isUpperCase(a.charAt(0)))
                .findFirst()
                .orElse("brak wielkiej litery");
    }

    public static void main(String[] args) {
        List<String> words1 = Arrays.asList("kot", "pies", "Dom", "las");
        List<String> words2 = Arrays.asList("auto", "rower", "samolot");

        System.out.println(getFirstUppercase(words1)); // powinno zwrócić "Dom"
        System.out.println(getFirstUppercase(words2)); // powinno zwrócić "brak wielkiej litery"
    }
}
