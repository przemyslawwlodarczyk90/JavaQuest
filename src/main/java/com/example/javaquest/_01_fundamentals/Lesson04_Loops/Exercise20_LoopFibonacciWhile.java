package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise20_LoopFibonacciWhile {
    public static void main(String[] args) {
        // 🌀 Wypisz pierwsze 10 liczb ciągu Fibonacciego używając pętli while



        int a = 0;
        int b = 1;
        int count = 0;

        while (count < 10) {
            System.out.println(a);

            int next = a + b;
            a = b;
            b = next;

            count++;
        }
    }
}