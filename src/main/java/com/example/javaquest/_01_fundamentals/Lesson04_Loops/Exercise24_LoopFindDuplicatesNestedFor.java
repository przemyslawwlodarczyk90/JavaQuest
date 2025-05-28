package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise24_LoopFindDuplicatesNestedFor {
    public static void main(String[] args) {
        // 🔁 Znajdź i wypisz duplikaty w tablicy liczb całkowitych używając zagnieżdżonych pętli for

        int[] arr = {1, 2, 4, 5, 5, 7, 7};

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    System.out.println("Duplikat: " + arr[i]);
                    break; // 🔒 unikamy wypisywania tego samego duplikatu kilka razy z rzędu
                }
            }
        }
    }
}
