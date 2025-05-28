package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise15_LoopDrawRectangleFor {

    // 🟦 Narysuj prostokąt o szerokości 10 i wysokości 5 za pomocą zagnieżdżonych pętli for.
// Oczekiwany wynik:
// **********
// **********
// **********
// **********
// **********

    public static void main(String[] args) {
        for (int i = 0; i<5; i++){
            System.out.print("//");
            for (int j =0; j<10; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }

   }
