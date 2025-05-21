package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise11_LoopMultiplicationTable {
    public static void main(String[] args) {
        // 📊 Wypisz tabliczkę mnożenia od 1 do 10 za pomocą zagnieżdżonych pętli for

        for (int i= 1; i<=10; i++){
            for (int j = 1; j<=10; j++){
                System.out.println(i+ " x "+ j + " = "+ i * j);
            }
            System.out.println();
        }



    }
}