package com.example.javaquest._01_fundamentals.Training2;

import java.util.*;
import java.util.stream.*;

/**
 * JQ_Streams_04 — "Suma kwadratów"
 *
 * ZADANIE:
 * Masz listę liczb całkowitych.
 * 1) Podnieś każdą liczbę do kwadratu.
 * 2) Użyj reduce, aby zsumować wszystkie wartości.
 * 3) Zwróć wynik jako int.
 *
 * Przykład:
 * Wejście: [1, 2, 3, 4]
 * Wynik: 30  (1*1 + 2*2 + 3*3 + 4*4 = 30)
 */
public class JQ_Streams_04 {

    public static int sumOfSquares(List<Integer> numbers) {
        int result = numbers.stream()
                .map(a->a*a)
                .reduce(0,(a,b)->a+b);

        return result;
    }

    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1, 2, 3, 4);
        System.out.println(sumOfSquares(input)); // 30
    }
}
