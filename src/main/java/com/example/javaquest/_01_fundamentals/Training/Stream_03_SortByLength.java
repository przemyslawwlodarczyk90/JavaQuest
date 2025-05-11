package com.example.javaquest._01_fundamentals.Training;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Stream_03_SortByLength {
    public static void main(String[] args) {
        /*
         * 🧪 Zadanie: Posortuj listę imion według długości
         *
         * List<String> names = Arrays.asList("Anna", "Zofia", "Bo", "Magdalena");
         *
         * Użyj stream() + sorted() + Comparator.comparing()
         * Zbierz wynik do Listy i wypisz na końcu
         */

        List<String> names = Arrays.asList("Anna", "Zofia", "Bo", "Magdalena");

        List<String> names2 = names.stream()
                .sorted(Comparator.comparing(String::length))
                .collect(Collectors.toList());
    }
}
