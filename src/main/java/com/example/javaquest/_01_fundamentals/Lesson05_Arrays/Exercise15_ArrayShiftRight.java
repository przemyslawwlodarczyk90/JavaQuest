package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise15_ArrayShiftRight {

    public static void arrayShifter(int[] array) {
        int last = array[array.length - 1]; // zapamiętujemy ostatni element

        for (int i = array.length - 1; i > 0; i--) {
            array[i] = array[i - 1]; // każdy element dostaje wartość poprzednika
        }

        array[0] = last; // pierwszy element dostaje dawny ostatni

        for (int numb : array) {
            System.out.println(numb);
        }
    }

    public static void main(String[] args) {
        /*
         * Przesuń elementy tablicy w prawo o 1 pozycję:
         * {1, 2, 3} → {3, 1, 2}
         */
        int [] number = {1,2,7,8};

        arrayShifter(number);

    }
}
