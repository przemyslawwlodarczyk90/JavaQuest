package com.example.javaquest._01_fundamentals.Lesson01_Variables;

import java.util.Arrays;

public class Exercise0125_MutateReferenceInsideMethod {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        mutate(arr);
        System.out.println("Po mutacji: " + Arrays.toString(arr));
    }

    static void mutate(int[] input) {
        // 🔧 Zmień element 0 na 99
        // Czy ta zmiana wpłynie na oryginalną tablicę?
    }
}
