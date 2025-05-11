package com.example.javaquest._01_fundamentals.Training;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Stream_02_MapToUppercase {
    public static void main(String[] args) {
        /*
         * 🧪 Zadanie: Zamień wszystkie imiona na wielkie litery
         *
         * List<String> names = Arrays.asList("Ala", "Ola", "Ela", "Iza");
         *
         * Użyj stream() + map() + collect() + wypisz nową listę
         *
         * Wskazówka:
         * - metoda .toUpperCase()
         * - użyj collect(Collectors.toList())
         */

        List<String> names = Arrays.asList("Ala", "Ola", "Ela", "Iza");

        List<String> newList= names.stream()
                .map(a->a.toUpperCase())
                .collect(Collectors.toUnmodifiableList());
    }
}
