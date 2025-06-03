package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise06_ArrayReversePrint {

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 6 (łatwe)
         *
         * Utwórz tablicę String[] names = {"Anna", "Bartek", "Celina"};
         * Wypisz elementy tablicy w odwrotnej kolejności.

         */


        String [] names = {"Anna", "Bartek", "Celina"};

        for(int i = names.length-1; i>0; i--){
            System.out.println(names[i]);

        }

    }

}
