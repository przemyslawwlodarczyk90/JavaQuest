package com.example.javaquest._01_fundamentals.Training;

public class Exercise12_FindLongestWord {


    public String longestWord(String [] words){
        String longestWord = words[0];

        for (int i = 0; i<words.length; i++){
            if (words[i].length() > words[0].length()){
                longestWord = words[i];
            }
        }


    return longestWord;

    }



    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 12 (ŚREDNIE): Znajdź najdłuższe słowo
         *
         * Masz zdefiniowaną tablicę Stringów:
         * String[] words = {"kot", "samochód", "pies", "telewizor", "oko"};
         *
         * Znajdź i wypisz najdłuższe słowo z tablicy.
         *
         * Oczekiwany wynik:
         * Najdłuższe słowo: telewizor
         *
         * Wskazówki:
         * - przejdź przez tablicę w pętli
         * - porównuj długości słów metodą `.length()`
         */
    }
}
