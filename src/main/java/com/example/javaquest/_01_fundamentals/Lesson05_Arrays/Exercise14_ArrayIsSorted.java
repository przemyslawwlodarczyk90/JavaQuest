package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise14_ArrayIsSorted {

    public static boolean arrayIsGrowing(int[]array){
        boolean isGrowing = false;
        for (int i = 0; i<array.length-1; i++){
            if (array[i] < array[i+1]){
                return true;
            }
        }
    return isGrowing;}

    public static void main(String[] args) {
        /*
         * Sprawdź, czy tablica int[] = {1, 2, 3, 4, 5}
         * jest posortowana rosnąco.
         */

        int [] numbers = {1, 2, 3, 4, 5};

        System.out.println(arrayIsGrowing(numbers));
    }
}
