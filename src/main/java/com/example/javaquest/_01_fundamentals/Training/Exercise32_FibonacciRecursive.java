package com.example.javaquest._01_fundamentals.Training;

public class Exercise32_FibonacciRecursive {

    private int fibonnaciRecursive(int number){
        if(number==0) return 0;
        if(number==1) return 1;
        return fibonnaciRecursive(number - 1) + fibonnaciRecursive(number-2);


    }


    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 32: Fibonacci (rekurencyjnie)
         *
         * Napisz metodę:
         * public static int fib(int n)
         *
         * Przykład:
         * fib(0) = 0
         * fib(1) = 1
         * fib(5) = 5
         * fib(6) = 8
         *
         * Wskazówki:
         * - użyj warunku bazowego: n == 0 lub n == 1
         * - rekurencja: fib(n-1) + fib(n-2)
         */
    }
}

