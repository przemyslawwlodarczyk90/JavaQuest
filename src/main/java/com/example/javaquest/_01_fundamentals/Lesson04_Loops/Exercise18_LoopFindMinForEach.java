package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise18_LoopFindMinForEach {
    public static void main(String[] args) {
        // 🔽 Znajdź najmniejszą liczbę w tablicy używając pętli for-each

        int [] array = {4,2,4,56,7};

        int min = array[0];

        for (int num : array){
            if (num<min){
                min = num;
            }
        }
        System.out.println(min);


    }
}
