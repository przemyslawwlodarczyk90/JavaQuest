package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_11 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstLongWord(List<String> words), która:
     * 1. Szuka pierwszego słowa dłuższego niż 8 znaków.
     * 2. Jeśli znajdzie → zwraca je DUŻYMI literami.
     * 3. Jeśli nie znajdzie → zwraca napis "brak długiego słowa".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), map(), orElse().
     */

    public static String getFirstLongWord(List<String> words) {
        // TODO: zaimplementuj tutaj
        return words.stream()
                .filter(a->a.length()>8)
                .findFirst()
                .map(String::toUpperCase)
                .orElse("brak długiego słowa");
    }

    public static void main(String[] args) {
        List<String> words1 = Arrays.asList("kot", "encyklopedia", "pies", "informatyka");
        List<String> words2 = Arrays.asList("dom", "las", "auto");

        System.out.println(getFirstLongWord(words1)); // powinno zwrócić "ENCYKLOPEDIA"
        System.out.println(getFirstLongWord(words2)); // powinno zwrócić "brak długiego słowa"
    }
}
