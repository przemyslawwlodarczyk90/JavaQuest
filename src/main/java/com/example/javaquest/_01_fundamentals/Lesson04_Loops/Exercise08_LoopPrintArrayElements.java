package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise08_LoopPrintArrayElements {
    public static void main(String[] args) {
        // 📦 Zadeklaruj tablicę liczb i wypisz każdy element za pomocą pętli for-each

        int [] arr = {1,2,3,4,5,6,7,8};

        for (int num : arr){
            System.out.println(num);
        }
    }
}
