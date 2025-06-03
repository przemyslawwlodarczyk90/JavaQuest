package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise07_ArrayCountEvenNumbers {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 7 (łatwe)
         *
         * W tablicy int[] numbers = {3, 6, 8, 11, 14, 17};
         * zlicz, ile elementów jest parzystych.
         */

        int sum = 0;

        int [] numbers = {3,6,8,11,13,17};

        for (int num : numbers){
            if (num%2==0){
                sum++;
            }
        }

        System.out.println(sum);
    }
}
