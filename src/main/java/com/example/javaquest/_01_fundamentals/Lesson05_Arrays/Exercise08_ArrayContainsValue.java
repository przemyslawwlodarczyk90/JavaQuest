package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise08_ArrayContainsValue {

    public static boolean checkTheThirteen(int [] array){
        boolean isThirteen = false;
        for (int num : array){
            if(num == 13){
                isThirteen = true;
            }
        }
    return isThirteen;}

    public static void main(String[] args) {
        /*
         * 🔹 Zadanie 8 (łatwe)
         *
         * Sprawdź, czy w tablicy int[] data = {4, 7, 12, 19}
         * znajduje się liczba 12.
         */

        int [] num = {4,7, 12, 19};

        System.out.println(checkTheThirteen(num));
    }
}
