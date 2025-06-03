package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise10_ArrayPrintIndexOfValue {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 10 (łatwe)
         *
         * W tablicy int[] numbers = {7, 14, 21, 28};
         * znajdź indeks liczby 21 i go wypisz.
         */

        int [] number = {7, 14, 21, 28};

        for (int num : number){
            if (num == 21){
                System.out.println(num);
            }

        }
    }
}
