package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise02_LoopEvenNumbers {
    public static void main(String[] args) {
        // 🔁 Wypisz liczby parzyste od 2 do 20 za pomocą pętli while

        for (int i = 2; i<= 20; i++){

            if (i%2 ==0){
                System.out.println(i);
            }

        }
    }
}
