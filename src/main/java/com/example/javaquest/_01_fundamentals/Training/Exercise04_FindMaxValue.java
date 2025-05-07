package com.example.javaquest._01_fundamentals.Training;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exercise04_FindMaxValue {
    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 4 (ŁATWE+): Znajdź największą liczbę
         *
         * Wczytaj z konsoli 5 liczb całkowitych (int).
         * Wypisz największą z nich.
         *
         * Przykład:
         * Wejście:
         * 4 7 1 9 2
         *
         * Wynik:
         * Największa liczba to: 9
         *
         * Wskazówki:
         * - użyj pętli for
         * - przechowuj największą wartość w zmiennej pomocniczej
         */


        Scanner scanner = new Scanner(System.in);

        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i<5; i++){
            System.out.println("Podaj liczbę, do pięciu razy sztuka. ");
            digits.add(scanner.nextInt());
        }

        int max = digits.get(0);
        for (int i = 1; i < digits.size(); i++) {
            if (digits.get(i) > max) {
                max = digits.get(i);
            }
            }

        System.out.println("Największa podana liczba to: "+ max);
        }


    }

