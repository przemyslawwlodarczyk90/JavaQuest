package com.example.javaquest._01_fundamentals.Training;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exercise06_LongestIncreasingSequence {
    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 6 (ŚREDNIE): Najdłuższy rosnący ciąg
         *
         * Wczytaj z konsoli 10 liczb całkowitych (int).
         * Znajdź i wypisz długość najdłuższego **ciągłego, rosnącego** fragmentu tego ciągu.
         *
         * Przykład:
         * Wejście:
         * 1 2 2 3 4 1 2 3 4 5
         *
         * Wynik:
         * Najdłuższy rosnący fragment ma długość: 5
         * (bo 1 2 3 4 5)
         *
         * Wskazówki:
         * - pamiętaj: ciąg musi być **ciągły** (czyli bez przerw)
         * - użyj dwóch zmiennych: currentLength i maxLength
         */

        Scanner scanner = new Scanner(System.in);
        List<Integer> digits = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            System.out.print("Podaj liczbę #" + (i + 1) + ": ");
            digits.add(scanner.nextInt());
        }

        int currentLength = 1;
        int maxLength = 1;

        for (int i = 1; i < digits.size(); i++) {
            if (digits.get(i) > digits.get(i - 1)) {
                currentLength++;
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                }
            } else {
                currentLength = 1;
            }
        }

        System.out.println("Najdłuższy rosnący fragment ma długość: " + maxLength);
        scanner.close();
    }
}





