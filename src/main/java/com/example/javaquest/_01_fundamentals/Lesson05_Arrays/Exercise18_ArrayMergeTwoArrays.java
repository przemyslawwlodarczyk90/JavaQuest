package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

import java.util.Arrays;

public class Exercise18_ArrayMergeTwoArrays {

    public static void main(String[] args) {
        /*
         * Połącz dwie tablice:
         * int[] a = {1, 2, 3};
         * int[] b = {4, 5, 6};
         */

        int[] a = {1, 2, 3};
        int[] b = {4, 5, 6};

        int[] c = new int[a.length+b.length];

        for (int i =0; i<3; i++){
            c[i]=a[i];
        }

        // Kopiujemy tablicę b od miejsca, gdzie skończyła się tablica a
        for (int i = 0; i < b.length; i++) {
            c[a.length + i] = b[i];
        }

        System.out.println(Arrays.toString(c));



    }
}
