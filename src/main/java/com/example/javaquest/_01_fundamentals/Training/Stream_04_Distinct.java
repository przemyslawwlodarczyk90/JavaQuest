package com.example.javaquest._01_fundamentals.Training;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Stream_04_Distinct {
    public static void main(String[] args) {
        /*
         * 🧪 Zadanie: Usuń duplikaty z listy liczb
         *
         * List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5, 5, 5);
         *
         * Użyj stream() + distinct() + collect()
         * Wypisz nową listę na końcu
         */

        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 4, 5, 5, 5);

        List<Integer> numers2 = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
