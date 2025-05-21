package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise12_LoopSumDigitsWhile {
    public static void main(String[] args) {
        // ➕ Dla liczby np. 1234 oblicz sumę jej cyfr używając pętli while

        int number = 1234;
        int sum = 0;

        while (number > 0) {
            int digit = number % 10;   // wyciągamy ostatnią cyfrę
            sum += digit;              // dodajemy ją do sumy
            number = number / 10;      // obcinamy ostatnią cyfrę
        }

        System.out.println("Suma cyfr: " + sum);
    }
}
