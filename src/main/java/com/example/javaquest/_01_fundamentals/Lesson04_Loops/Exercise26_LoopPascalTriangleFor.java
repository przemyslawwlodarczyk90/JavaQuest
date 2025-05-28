package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise26_LoopPascalTriangleFor {
    public static void main(String[] args) {
        // 🔺 Wypisz pierwsze 5 wierszy trójkąta Pascala używając pętli for

        int rows = 5;

        for (int n = 0; n < rows; n++) {
            // Spacje dla wyrównania trójkąta
            for (int s = 0; s < rows - n; s++) {
                System.out.print(" ");
            }

            int number = 1;
            for (int k = 0; k <= n; k++) {
                System.out.print(number + " ");
                number = number * (n - k) / (k + 1); // obliczanie kolejnego elementu w wierszu
            }

            System.out.println(); // nowa linia
        }
    }
}
