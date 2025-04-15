package com.example.javaquest._01_fundamentals.Training;

import java.util.Scanner;

public class Exercise02_MultiplicationTable {
    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 2 (TRUDNE):
         *
         * Wczytaj liczbę całkowitą N (2 ≤ N ≤ 20).
         * Wypisz kolejne 10 wierszy tabliczki mnożenia dla tej liczby.
         * Każdy wiersz ma format:
         * N x i = wynik
         * gdzie i to kolejne liczby od 1 do 10.
         *
         * Przykładowe wejście:
         * 2
         *
         * Oczekiwany wynik:
         * 2 x 1 = 2
         * 2 x 2 = 4
         * 2 x 3 = 6
         * ...
         * 2 x 10 = 20
         *
         * Wskazówka:
         * Użyj pętli for oraz System.out.println lub printf
         */

        Scanner input = new Scanner(System.in);
        int N = input.nextInt();

        for (int i = 1; i <= 10; i++) {
            System.out.println(N + " x " + i + " = " + (N * i));
        }

        input.close();
    }
}
