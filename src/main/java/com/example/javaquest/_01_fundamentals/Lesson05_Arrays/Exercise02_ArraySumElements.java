package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise02_ArraySumElements {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 2 (łatwe)
         *
         * Utwórz tablicę liczb typu int: {5, 10, 15, 20}.
         * Oblicz i wypisz sumę wszystkich elementów.
         *
         */

        int [] num = {5,10,15,20};

        int sum =0;

        for (int num1 : num){
            sum+=num1;
        }
        System.out.println(sum);
    }
}
