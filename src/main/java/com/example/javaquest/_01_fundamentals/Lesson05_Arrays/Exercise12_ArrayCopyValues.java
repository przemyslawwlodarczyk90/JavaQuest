package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise12_ArrayCopyValues {

    public static void main(String[] args) {
        /*
         * Skopiuj wszystkie wartości z jednej tablicy int[] do drugiej.
         */

        int[] num = {1, 2, 5, 6, 4, 5, 6, 7};

        // Tworzymy nową tablicę o tej samej długości
        int[] num2 = new int[num.length];

        // Kopiujemy elementy
        for (int i = 0; i < num.length; i++) {
            num2[i] = num[i];
        }

        // Wypisujemy skopiowaną tablicę
        for (int n : num2) {
            System.out.println(n);
        }
    }
}
