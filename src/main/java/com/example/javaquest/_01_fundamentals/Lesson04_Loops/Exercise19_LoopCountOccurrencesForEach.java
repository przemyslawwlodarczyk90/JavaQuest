package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise19_LoopCountOccurrencesForEach {
    public static void main(String[] args) {
        // 🔁 Zlicz ile razy liczba 7 występuje w tablicy używając pętli for-each

        int [] arr = {7, 5,4,4,56,7,3,2,5};

        int counter = 0;

        for (int num : arr){
            if (num == 7) {
                counter++;
            }

        }

        System.out.println(counter);
    }
}