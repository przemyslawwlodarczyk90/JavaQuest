package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise14_LoopCountDownEvenWhile {
    public static void main(String[] args) {
        // ⏬ Wypisz liczby parzyste w dół od 100 do 50 za pomocą pętli while


        int number = 100;

        while(number>=50){
            System.out.println(number);
            number--;
        }
    }
}