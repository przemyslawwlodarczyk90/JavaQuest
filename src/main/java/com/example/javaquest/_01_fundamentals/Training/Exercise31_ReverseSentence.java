package com.example.javaquest._01_fundamentals.Training;

public class Exercise31_ReverseSentence {

    public static void reverseSentence(String sentence){
        String [] words = sentence.split(" ");
        String reversedSentence = null;

        for (int i = sentence.length()-1; i>=0; i--){
            reversedSentence+=words[i];

        }
        System.out.println(reversedSentence);
    }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 31: Odwróć kolejność słów w zdaniu
         *
         * Wejście: "Ala ma kota"
         * Wynik: "kota ma Ala"
         *
         * Wskazówki:
         * - użyj split(" ") → tablica słów
         * - przejdź tablicę od końca lub użyj Collections.reverse()
         * - złącz wynik np. przez StringBuilder lub String.join()
         */
    }
}
