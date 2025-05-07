package com.example.javaquest._01_fundamentals.Training;


import java.util.List;

public class Exercise05_CountVowels {

    public int countVowels(String word){
        List<Character> vowels = List.of('a', 'e', 'i', 'o', 'u', 'y' );
        int counter =0;

        for (int i = 0; i < (word.length()- 1); i++){
            if (vowels.contains(word.charAt(i))){
                counter++;
            }
        }
    return counter;
    }

    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 5 (ŁATWE): Zlicz samogłoski
         *
         * Napisz program, który przyjmuje dowolny łańcuch znaków (String)
         * i wypisuje, ile zawiera **samogłosek** (a, e, i, o, u, y — niezależnie od wielkości liter).
         *
         * Przykład:
         * Wejście: "Ala ma kota"
         * Wynik: 5
         *
         * Wskazówki:
         * - użyj metody `toLowerCase()`
         * - przejdź przez znaki w pętli
         * - sprawdzaj, czy znak należy do zbioru samogłosek
         */
    }


}
