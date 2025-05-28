package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise17_LoopCheckPrimeWhile {
    public static void main(String[] args) {
        // 🔍 Sprawdź czy liczba 29 jest liczbą pierwszą używając pętli while

        int number = 29;
        boolean isPrime = true;

        int i = 2;
        while (i <= Math.sqrt(number)) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
            i++;
        }

        System.out.println(number + " is prime? " + isPrime);
    }
}