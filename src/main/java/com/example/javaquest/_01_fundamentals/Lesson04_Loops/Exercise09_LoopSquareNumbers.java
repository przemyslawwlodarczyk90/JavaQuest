package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise09_LoopSquareNumbers {
    public static void main(String[] args) {
        // 🟩 Wypisz kwadraty liczb od 1 do 10 (1, 4, 9, ..., 100)

        int i = 1;

        do{
            System.out.println(Math.pow(i,2));
            i++;
        }while (i<=100);

    }
}
