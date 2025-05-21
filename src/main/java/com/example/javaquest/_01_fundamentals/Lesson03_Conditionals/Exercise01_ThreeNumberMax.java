package com.example.javaquest._01_fundamentals.Lesson03_Conditionals;

public class Exercise01_ThreeNumberMax {

    public static int findMax (int a, int b, int c){
        int max = a;

        if(b>a){
            max = b;
        } else if (c>max){
            max =c;
        }

        return max;
    }

    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 1 (TRUDNE):
         * Masz trzy liczby całkowite: a, b, c.
         * Znajdź i wypisz największą z nich bez użycia Math.max().
         * Uwzględnij przypadki równości.
         */


    }
}
