//package com.example.javaquest._01_fundamentals.Lesson05_Arrays;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class Exercise19_ArrayFindDuplicates {
//
//    public class Exercise19_ArrayFindDuplicates {
//
//        public static int[] findDuplicatesInArray(int[] array) {
//            Set<Integer> seen = new HashSet<>();
//            Set<Integer> duplicates = new HashSet<>();
//
//            for (int value : array) {
//                if (!seen.add(value)) {
//                    duplicates.add(value);
//                }
//            }
//
//            // Konwersja Set do tablicy int[]
//            int[] result = new int[duplicates.size()];
//            int i = 0;
//            for (int dup : duplicates) {
//                result[i++] = dup;
//            }
//
//            return result;
//        }
//
//    public static void main(String[] args) {
//        /*
//         * Znajdź duplikaty w tablicy:
//         * int[] numbers = {1, 2, 3, 2, 4, 3, 5};
//         */
//
//        int[] numbers = {1, 2, 3, 2, 4, 3, 5};
//
//
//    }
//}
