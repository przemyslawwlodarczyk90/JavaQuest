package com.example.javaquest._01_fundamentals.Training;

import java.util.*;

public class Exercise28_FindDuplicates {

    public static void duplicateCounter (List<Integer> list){
        List<Integer> uniqe = new ArrayList<>();
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> distinctetDuplicates = new HashSet<>();

        for (int i = 0; i<list.size(); i++){
            uniqe.add(list.get(i));
            if(uniqe.contains(list.get(i)))
                duplicates.add(list.get(i));
        }

        distinctetDuplicates = (Set<Integer>) duplicates;

        for (int number : distinctetDuplicates){
            System.out.println(number);
        }


    }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 28: Znajdź duplikaty w liście
         *
         * Dla listy: Arrays.asList(1, 2, 3, 2, 4, 1, 5)
         * wypisz liczby, które występują więcej niż raz.
         *
         * Oczekiwany wynik:
         * 1
         * 2
         *
         * Wskazówki:
         * - użyj dwóch zbiorów: jeden do śledzenia unikalnych, drugi do powtórek
         * - możesz też użyć streamów i kolektora toMap()
         */
    }
}
