package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise04_ArrayFindMax {

    public static int findMax(int [] array){
        int max = array[0];
        for (int i = 0; i<=array.length-1; i++){
            if(max<array[i]){
                max = array[i];
            }
        }

    return max;}

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 4 (łatwe)
         *
         * W tablicy int[] nums = {4, 15, 2, 9, 23};
         * znajdź i wypisz największą wartość.
         */

        int [] nums = {4,15,2,9,23};

        System.out.println(findMax(nums));

    }
}
