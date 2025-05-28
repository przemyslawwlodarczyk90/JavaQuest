package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise13_LoopReverseNumberDoWhile {
    public static void main(String[] args) {
        // 🔁 Odwróć liczbę całkowitą np. 1234 → 4321 używając pętli do-while

        int number = 1234;
        String newNumber ="";

        do{


           newNumber+= number%10;
           number /= 10;

        }while (number>0);

        System.out.println(newNumber);

    }
}