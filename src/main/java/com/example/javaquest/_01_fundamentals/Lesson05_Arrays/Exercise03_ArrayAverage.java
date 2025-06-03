package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise03_ArrayAverage {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 3 (łatwe)
         *
         * Utwórz tablicę double z wartościami: 1.5, 2.0, 3.5, 4.0.
         * Oblicz i wypisz średnią arytmetyczną.
         */

    double[]nums = {1.5, 2.0, 3.5, 4.0};

    double sum = 0;

    for (double num : nums){
        sum+=num;

    }
        System.out.println(sum/nums.length);
    }
}
