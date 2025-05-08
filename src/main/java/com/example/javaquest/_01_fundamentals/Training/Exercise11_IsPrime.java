package com.example.javaquest._01_fundamentals.Training;

import java.util.Scanner;



public class Exercise11_IsPrime {

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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj liczbę: ");
        int number = scanner.nextInt();

        if (isPrime(number)) {
            System.out.println("Liczba pierwsza");
        } else {
            System.out.println("Nie jest liczbą pierwszą");
        }
    }
}





        /*
         * 🧪 Zadanie 11 (ŚREDNIE): Czy liczba jest pierwsza?
         *
         * Wczytaj liczbę całkowitą z konsoli.
         * Sprawdź, czy jest to liczba pierwsza.
         * Liczba pierwsza to taka, która jest większa od 1 i dzieli się tylko przez 1 i samą siebie.
         *
         * Przykład 1:
         * Wejście: 7
         * Wynik: Liczba pierwsza
         *
         * Przykład 2:
         * Wejście: 9
         * Wynik: Nie jest liczbą pierwszą
         *
         * Wskazówki:
         * - liczby mniejsze niż 2 są od razu niepierwsze
         * - sprawdzaj dzielenie od 2 do pierwiastka z n (lub n-1)
         */


