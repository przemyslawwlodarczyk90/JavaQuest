package com.example.javaquest._01_fundamentals.Training;

public class Exercise36_IsPrime {

    public static boolean isPrime(int number) {
        if (number < 2) return false;

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 36: Czy liczba jest pierwsza?
         *
         * Napisz metodę:
         * public static boolean isPrime(int n)
         *
         * Liczba pierwsza to taka, która dzieli się tylko przez 1 i samą siebie
         * i jest większa od 1.
         *
         * Wskazówki:
         * - odrzuć n < 2
         * - sprawdzaj dzielniki od 2 do Math.sqrt(n)
         */
    }
}
