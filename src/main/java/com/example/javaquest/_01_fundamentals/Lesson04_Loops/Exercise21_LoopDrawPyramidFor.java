package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise21_LoopDrawPyramidFor {
    public static void main(String[] args) {
        // 🔺 Narysuj piramidę z gwiazdek o wysokości 5 używając pętli for

        for (int i = 0; i<5; i++){
            for (int j=0; j<5-i-1; j++){
                System.out.print(" ");
            }
            for (int k = 0; k<i*2+1; k++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
