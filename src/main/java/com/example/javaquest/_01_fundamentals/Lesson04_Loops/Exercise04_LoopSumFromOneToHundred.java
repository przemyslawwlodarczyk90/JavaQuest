package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise04_LoopSumFromOneToHundred {
    public static void main(String[] args) {
        // ➕ Oblicz sumę liczb od 1 do 100 (for loop)

        int counter = 0;

        for(int i = 1; i<=100; i++){
            counter+=i;
        }

        System.out.println(counter);
    }
}
