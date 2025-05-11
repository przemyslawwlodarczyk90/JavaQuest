package com.example.javaquest._01_fundamentals.Training;

public class Exercise33_FibonacciIterative {

    public static int fibonacci (int n){
        if (n==0) return 0;
        if (n==1) return 1;
        int sum = 0;
        int a = 0;
        int b = 1;

        for(int i= 2; i <=n; i++){
            sum = a+b;
            a = b;
            b =sum;
        }
        return sum;
    }
    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 33: Fibonacci (iteracyjnie)
         *
         * Napisz metodę:
         * public static void fibonacciIterative(int n)
         *
         * Która wypisze pierwsze n liczb ciągu Fibonacciego:
         * Przykład dla n = 6:
         * 0 1 1 2 3 5
         *
         * Wskazówki:
         * - użyj zmiennych: a = 0, b = 1
         * - użyj pętli for, która wypisze kolejne liczby
         */
    }


}
