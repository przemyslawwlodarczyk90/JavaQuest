package com.example.javaquest._01_fundamentals.Lesson05_Arrays;

public class Exercise13_ArraySwapFirstLast {

    public static void main(String[] args) {
        /*
         * Zamień miejscami pierwszy i ostatni element tablicy:
         * String[] values = {"a", "b", "c", "d"};
         */

        String[] values = {"a", "b", "c", "d"};

        String temp = values[0];
        values[0] = values[values.length - 1];
        values[values.length - 1] = temp;

        for (String s : values) {
            System.out.println(s);
        }
    }
}
