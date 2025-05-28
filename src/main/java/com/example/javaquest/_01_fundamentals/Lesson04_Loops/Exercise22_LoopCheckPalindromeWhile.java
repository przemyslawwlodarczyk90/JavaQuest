package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise22_LoopCheckPalindromeWhile {
    public static void main(String[] args) {
        // 🔄 Sprawdź czy liczba 12321 jest palindromem używając pętli while

        int num = 12321;
        String original = Integer.toString(num);
        String reversed = "";

        int i = num;
        while (i > 0) {
            reversed += i % 10;
            i /= 10;
        }

        if (original.equals(reversed)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }}