package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise16_LoopPowerOfTwoDoWhile {
    public static void main(String[] args) {
        // ⚡ Wypisz kolejne potęgi dwójki od 1 do 1024 używając pętli do-while


        int value = 1;

        do {
            System.out.println(value);
            value *= 2;
        } while (value <= 1024);
    }
}
