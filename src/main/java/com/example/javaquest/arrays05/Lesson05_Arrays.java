package com.example.javaquest.arrays05;

import java.util.Arrays;

public class Lesson05_Arrays {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🧱 WSTĘP: CZYM JEST TABLICA
         * ============================================================
         *
         * Tablica (ang. array) to zbiór elementów tego samego typu,
         * przechowywanych w jednej strukturze, dostępnych przez indeks.
         *
         * Indeksy zaczynają się od 0.
         * np. int[] numbers = {10, 20, 30};
         *          indeksy:    0   1   2
         *
         * Tablice mają stałą długość – nie można ich dynamicznie rozszerzać.
         */

        /*
         * ============================================================
         * 🔹 DEKLARACJA I INICJALIZACJA TABLICY
         * ============================================================
         */

        // Deklaracja + przypisanie od razu
        int[] numbers = {10, 20, 30, 40, 50};

        // Deklaracja i późniejsze przypisanie
        String[] fruits;
        fruits = new String[] {"jabłko", "banan", "gruszka"};

        // Tablica o określonym rozmiarze, ale bez wartości
        double[] measurements = new double[4]; // domyślnie: 0.0

        /*
         * ============================================================
         * 📦 ODCZYT I ZAPIS ELEMENTÓW
         * ============================================================
         */

        System.out.println("Pierwszy owoc: " + fruits[0]);
        fruits[1] = "kiwi"; // nadpisanie elementu
        System.out.println("Zmieniony drugi owoc: " + fruits[1]);

        /*
         * ============================================================
         * 🔁 ITERACJA PO TABLICY
         * ============================================================
         */

        System.out.println("Liczby w tablicy:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Index " + i + ": " + numbers[i]);
        }

        // Uproszczona forma (foreach)
        System.out.println("Owoce (foreach):");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        /*
         * ============================================================
         * 📏 DŁUGOŚĆ TABLICY
         * ============================================================
         * Dostępna przez pole `length` (bez nawiasów!)
         */

        System.out.println("Ilość pomiarów: " + measurements.length);

        /*
         * ============================================================
         * 🧼 DOMYŚLNE WARTOŚCI W TABLICACH
         * ============================================================
         *
         * - int, long → 0
         * - double, float → 0.0
         * - boolean → false
         * - char → \u0000 (niewidoczny znak)
         * - obiekty (np. String) → null
         */

        int[] emptyInts = new int[3];
        System.out.println("Domyślna wartość int: " + emptyInts[0]);

        String[] emptyStrings = new String[2];
        System.out.println("Domyślna wartość String: " + emptyStrings[0]);

        /*
         * ============================================================
         * 🛠️ METODY NARZĘDZIOWE Z `Arrays`
         * ============================================================
         */

        int[] original = {4, 2, 9, 1};
        Arrays.sort(original); // sortowanie rosnąco
        System.out.println("Posortowane: " + Arrays.toString(original));

        int[] copy = Arrays.copyOf(original, 6); // nowa tablica większa niż oryginał
        System.out.println("Kopia z dodatkowymi miejscami: " + Arrays.toString(copy));

        boolean equal = Arrays.equals(original, copy); // porównanie zawartości
        System.out.println("Czy tablice są równe? " + equal);

        /*
         * ============================================================
         * 🔄 TABLICE 2D – TABLICA TABLIC (macierz)
         * ============================================================
         */

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };

        System.out.println("Tablica 2D (ręczne wypisanie):");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }

        /*
         * Wiersze mogą mieć różną długość:
         */
        String[][] uneven = {
                {"a", "b"},
                {"c", "d", "e"}
        };

        System.out.println("Tablica 2D o różnych długościach wierszy:");
        for (String[] row : uneven) {
            for (String element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

        /*
         * ============================================================
         * PODSUMOWANIE
         * ============================================================
         */

        System.out.println("\n📌 TABLICE W JAVIE – NAJWAŻNIEJSZE PUNKTY:");
        System.out.println("- Stała długość, jeden typ danych");
        System.out.println("- Indeksowanie od zera");
        System.out.println("- Dostęp przez `[]`, długość przez `.length`");
        System.out.println("- Foreach do odczytu, for do kontroli indeksu");
        System.out.println("- Klasa Arrays oferuje przydatne narzędzia (sort, copyOf, equals)");
        System.out.println("- Tablice 2D to tablica tablic (każdy rząd może mieć inną długość)");
    }
}
