package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise11_ArrayCountSpecificValue {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 11 (średnie)
         *
         * Zlicz, ile razy liczba 3 występuje w tablicy:
         * int[] numbers = {1, 3, 5, 3, 3, 7};
         */

        int[] numbers = {1, 3, 5, 3, 3, 7};

        int sum = 0;
        for (int num : numbers){
            if(num == 3){
                sum++;
            }
        }

        System.out.println(sum);
    }
}
