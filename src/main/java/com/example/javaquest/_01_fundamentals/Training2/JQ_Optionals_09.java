package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_09 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstShortName(List<String> names), która:
     * 1. Szuka pierwszego imienia krótszego niż 4 znaki.
     * 2. Jeśli znajdzie → zwraca je DUŻYMI LITERAMI.
     * 3. Jeśli nie znajdzie → zwraca wynik lambdy w orElseGet(),
     *    np. "brak krótkiego imienia".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), map(), orElseGet().
     */

    public static String getFirstShortName(List<String> names) {
        // TODO: zaimplementuj tutaj
        return names.stream()
                .filter(a->a.length()<4)
                .findFirst()
                .map(String::toUpperCase).orElseGet(()->"brak krótkiego imienia");
    }

    public static void main(String[] args) {
        List<String> names1 = Arrays.asList("Ola", "Przemek", "Kuba");
        List<String> names2 = Arrays.asList("Dorota", "Marcin");

        System.out.println(getFirstShortName(names1)); // powinno zwrócić "OLA"
        System.out.println(getFirstShortName(names2)); // powinno zwrócić "brak krótkiego imienia"
    }
}
