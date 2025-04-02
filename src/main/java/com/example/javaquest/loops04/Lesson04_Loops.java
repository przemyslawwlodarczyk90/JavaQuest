package com.example.javaquest.loops04;

public class Lesson04_Loops {

    public static void main(String[] args) {

        // ========================
        // PĘTLA WHILE
        // ========================
        // Wykonuje blok kodu, dopóki warunek jest prawdziwy.
        int i = 0;
        while (i < 5) {
            System.out.println("while: i = " + i);
            i++; // inkrementacja
        }

        // ========================
        // PĘTLA DO-WHILE
        // ========================
        // Najpierw wykonuje blok kodu, potem sprawdza warunek.
        // Gwarantuje przynajmniej jedno wykonanie.
        int j = 0;
        do {
            System.out.println("do-while: j = " + j);
            j++;
        } while (j < 5);

        // ========================
        // PĘTLA FOR
        // ========================
        // Używana, gdy wiemy ile razy chcemy coś powtórzyć.
        for (int k = 0; k < 5; k++) {
            System.out.println("for: k = " + k);
        }

        // ========================
        // PĘTLA FOR - INKREMENTACJA I DEKREMENTACJA
        // ========================
        // Przykład inkrementacji i dekrementacji w pętli:
        System.out.println("Inkrementacja:");
        for (int x = 1; x <= 5; x++) {
            System.out.println("x = " + x);
        }

        System.out.println("Dekrementacja:");
        for (int x = 5; x >= 1; x--) {
            System.out.println("x = " + x);
        }

        // Można też inkrementować o więcej niż 1:
        System.out.println("Inkrementacja co 2:");
        for (int x = 0; x <= 10; x += 2) {
            System.out.println("x = " + x);
        }

        // ========================
        // PĘTLA FOREACH (dla kolekcji/array)
        // ========================
        // Służy do prostego przechodzenia po elementach tablicy lub kolekcji
        int[] numbers = {10, 20, 30, 40, 50};
        for (int number : numbers) {
            System.out.println("foreach: number = " + number);
        }

        // ========================
        // PRZYKŁAD ZŁOŻONY: SUMOWANIE LICZB W TABLICY
        // ========================
        int sum = 0;
        for (int number : numbers) {
            sum += number; // sumowanie wartości z tablicy
        }
        System.out.println("Suma liczb z tablicy: " + sum);

        // ========================
        // UŻYCIE BREAK I CONTINUE
        // ========================

        // continue – pomija daną iterację i przechodzi do następnej
        System.out.println("Przykład continue:");
        for (int n = 0; n < 5; n++) {
            if (n == 2) continue; // pomiń, jeśli n == 2
            System.out.println("n = " + n);
        }

        // break – przerywa całkowicie pętlę
        System.out.println("Przykład break:");
        for (int n = 0; n < 5; n++) {
            if (n == 3) break; // zakończ, jeśli n == 3
            System.out.println("n = " + n);
        }

        // ========================
        // ZAGNIEŻDŻONE PĘTLE (np. tablica 2D)
        // ========================
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Zagnieżdżona pętla - wypisanie tablicy 2D:");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println(); // nowa linia po każdej kolumnie
        }

        // ========================
        // PODSUMOWANIE
        // ========================
        System.out.println("\nPętle w Javie pozwalają powtarzać fragmenty kodu:");
        System.out.println("- while/do-while: dopóki warunek jest spełniony");
        System.out.println("- for: licznik iteracji");
        System.out.println("- foreach: przechodzenie przez kolekcję/tablicę");
        System.out.println("- break: zakończ pętlę");
        System.out.println("- continue: pomiń tę iterację");
    }
}
