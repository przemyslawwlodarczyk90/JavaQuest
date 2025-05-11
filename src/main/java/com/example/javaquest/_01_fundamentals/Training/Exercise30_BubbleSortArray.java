package com.example.javaquest._01_fundamentals.Training;

import java.util.List;

public class Exercise30_BubbleSortArray {

    private static Integer [] bubbleSort(Integer [] arr){
        for (int i = 0; i<arr.length; i++){
            for (int j = 0; j<arr.length-i; j++){
                if(arr[j]>arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1]=temp;

                }}
        }

    return arr;
    }


    public static void main(String[] args) {




        /*
         * 🧪 Ćwiczenie 30: Posortuj tablicę bez użycia Arrays.sort()
         *
         * Napisz metodę, która posortuje tablicę liczb całkowitych rosnąco,
         * używając sortowania bąbelkowego (bubble sort).
         *
         * Wejście: int[] arr = {5, 3, 1, 4, 2};
         * Wynik: [1, 2, 3, 4, 5]
         *
         * Wskazówki:
         * - użyj dwóch pętli for
         * - zamieniaj elementy miejscami, jeśli są w złej kolejności
         * - wypisz wynik po sortowaniu
         */
    }
}
