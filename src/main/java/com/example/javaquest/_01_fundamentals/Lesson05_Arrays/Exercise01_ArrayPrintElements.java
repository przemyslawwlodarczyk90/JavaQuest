package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise01_ArrayPrintElements {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 1 (łatwe)
         *
         * Utwórz tablicę typu int zawierającą liczby: 10, 20, 30, 40, 50.
         * Wypisz wszystkie elementy tablicy – każdy w osobnej linii.
         */

        int [] num = {10,20,30,40,50};

        for(int num1 : num){
            System.out.println(num1);
        }
    }
}
