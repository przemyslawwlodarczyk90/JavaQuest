package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;

public class JQ_Optionals_05 {

    /*
     * ĆWICZENIE:
     * Napisz metodę getFirstLongName(List<String> names), która:
     * 1. Szuka pierwszego imienia, które ma więcej niż 5 znaków.
     * 2. Jeśli znajdzie → zwraca je DUŻYMI LITERAMI.
     * 3. Jeśli nie znajdzie → zwraca napis "brak długiego imienia".
     *
     * Podpowiedź: użyj stream(), filter(), findFirst(), map(), orElse().
     */

    public static String getFirstLongName(List<String> names) {
        // TODO: zaimplementuj tutaj
        return names.stream()
                .filter(a->a.length()>5)
                .findFirst()
                .map(String::toUpperCase)
                .orElse("brak długiego imienia");
    }

    public static void main(String[] args) {
        List<String> names1 = Arrays.asList("Kuba", "Ola", "Przemek", "Dorota");
        List<String> names2 = Arrays.asList("Ala", "Ola", "Jan");

        System.out.println(getFirstLongName(names1)); // powinno zwrócić "PRZEMEK"
        System.out.println(getFirstLongName(names2)); // powinno zwrócić "brak długiego imienia"
    }
}
