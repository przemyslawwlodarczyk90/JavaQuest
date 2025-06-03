package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise16_ArrayRemoveElement {


    public static void digitRemover(int[] array, int digit) {
        int count = 0;

        // Liczymy, ile elementów NIE równa się digit
        for (int num : array) {
            if (num != digit) {
                count++;
            }
        }

        // Tworzymy nową tablicę tylko z tymi elementami
        int[] newArray = new int[count];
        int index = 0;

        for (int num : array) {
            if (num != digit) {
                newArray[index] = num;
                index++;
            }
        }

        // Wyświetlamy wynik
        for (int numb1 : newArray) {
            System.out.println(numb1);
        }
    }


    public static void main(String[] args) {
        /*
         * Usuń wszystkie wystąpienia liczby 5 z tablicy:
         * int[] data = {2, 5, 7, 5, 9}
         */

        int[] data = {2, 5, 7, 5, 9};

        digitRemover(data, 5);

        System.out.println();

    }
}
