package com.example.javaquest._01_fundamentals.Training;

public class Exercise13_CalculateExpression {

    int a = 5;
    int b = 10;
    double c = 2.5;
    int d = 3;


    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 13 (ŚREDNIE): Oblicz wyrażenie matematyczne
         *
         * Oblicz wartość wyrażenia:
         * ((a + b) * c) / d
         *
         * Załóż przykładowe wartości:
         * a = 5, b = 10, c = 2.5, d = 3
         *
         * Wypisz wynik z dokładnością do dwóch miejsc po przecinku.
         *
         * Oczekiwany wynik:
         * Wynik: 12.50
         *
         * Wskazówki:
         * - zadbaj o rzutowanie typów (int, double)
         * - użyj `System.out.printf()` do formatowania
         */

        int a = 5;
        int b = 10;
        double c = 2.5;
        int d = 3;

        System.out.printf(String.valueOf(((a+b)) *c /d));

    }
}
