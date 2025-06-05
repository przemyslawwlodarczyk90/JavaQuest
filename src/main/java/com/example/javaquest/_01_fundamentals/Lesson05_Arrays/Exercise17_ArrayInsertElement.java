package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

import java.util.Arrays;

public class Exercise17_ArrayInsertElement {

    public static void main(String[] args) {
        /*
         * Wstaw liczbę 99 na indeksie 2 w tablicy:
         * int[] values = {10, 20, 30, 40}
         */

        int[] values = {10, 20, 30, 40};
        int insertIndex = 2;
        int insertValue = 99;

        // Tworzymy nową tablicę o jeden element większą
        int[] result = new int[values.length + 1];

        for (int i = 0, j = 0; i < result.length; i++) {
            if (i == insertIndex) {
                result[i] = insertValue;
            } else {
                result[i] = values[j];
                j++;
            }
        }

        System.out.println("Nowa tablica: " + Arrays.toString(result));
    }
}