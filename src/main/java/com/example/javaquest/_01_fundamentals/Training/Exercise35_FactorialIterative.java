package com.example.javaquest._01_fundamentals.Training;

public class Exercise35_FactorialIterative {

    public static int factorial(int n){
        int sum = 1;

        for (int i =1 ; i<=n; i++){
            sum*=i;
        }
        return sum;
    }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 35: Silnia (iteracyjnie)
         *
         * Napisz metodę:
         * public static int factorialIterative(int n)
         *
         * factorial(5) = 5 * 4 * 3 * 2 * 1 = 120
         *
         * Wskazówki:
         * - użyj pętli for (od 1 do n)
         * - przechowuj wynik w zmiennej typu int
         */
    }
}
