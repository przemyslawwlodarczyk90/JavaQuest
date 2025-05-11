package com.example.javaquest._01_fundamentals.Training;

public class Exercise34_FactorialRecursive {

    public static int factorialRecursive(int n){
        if (n == 0) return 1;
        return n * factorialRecursive(n-1);

    }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 34: Silnia (rekurencja)
         *
         * Napisz metodę:
         * public static int factorial(int n)
         *
         * factorial(5) = 5 * 4 * 3 * 2 * 1 = 120
         *
         * Wskazówki:
         * - warunek brzegowy: n == 0 → zwróć 1
         * - wywołanie rekurencyjne: n * factorial(n - 1)
         */
    }
}
