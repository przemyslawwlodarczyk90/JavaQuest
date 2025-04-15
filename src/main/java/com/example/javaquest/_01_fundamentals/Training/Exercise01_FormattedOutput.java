package com.example.javaquest._01_fundamentals.Training;

import java.util.Scanner;

public class Exercise01_FormattedOutput {
    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 1 (TRUDNE):
         *
         * Wczytaj trzy linie danych wejściowych.
         * Każda linia zawiera:
         * - nazwę języka programowania (String, max 15 znaków)
         * - oraz liczbę całkowitą z zakresu 0–999
         *
         * Wypisz każdą linię w dwóch kolumnach:
         * - pierwsza kolumna: tekst wyjustowany do lewej na szerokość 15 znaków
         * - druga kolumna: liczba zapisana na 3 cyfry, z wiodącymi zerami jeśli trzeba
         *
         * Przykładowe wejście:
         * java 100
         * cpp 65
         * python 50
         *
         * Oczekiwany wynik:
         * ================================
         * java           100
         * cpp            065
         * python         050
         * ================================
         *

         */

        Scanner input = new Scanner(System.in);

        System.out.println("================================");

        for (int i = 0; i < 3; i++) {
            String sentence = input.nextLine();

            String newString = ""; // część tekstowa
            int spaceIndex = sentence.indexOf(' ');

            // Zbierz tekst do spacji
            for (int j = 0; j < spaceIndex; j++) {
                newString += sentence.charAt(j);
            }

            // Zbierz liczby po spacji
            String digit = "";
            for (int j = spaceIndex + 1; j < sentence.length(); j++) {
                digit += sentence.charAt(j);
            }

            // Zamień liczbę na int, żeby móc ją sformatować jako 3-cyfrową
            int parsedNumber = Integer.parseInt(digit);

            // Wypisz dokładnie z odstępem jak trzeba
            System.out.printf("%-15s%03d%n", newString, parsedNumber);
        }

        System.out.println("================================");
    }
}