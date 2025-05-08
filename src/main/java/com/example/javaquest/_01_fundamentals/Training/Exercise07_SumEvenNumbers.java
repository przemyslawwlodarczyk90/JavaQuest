package com.example.javaquest._01_fundamentals.Training;

public class Exercise07_SumEvenNumbers {

    public static void sumEven(){
        int counter = 0;

        for (int i = 1; i<=100; i++){
            if(i%2==0){
                counter+=i;
            }
        }
        System.out.println(counter);
    }

    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 7 (ŁATWE+): Suma liczb parzystych
         *
         * Napisz program, który wypisze sumę wszystkich liczb parzystych od 1 do 100 (włącznie).
         *
         * Wynik powinien wyglądać tak:
         * Suma liczb parzystych od 1 do 100 wynosi: 2550
         *
         * Wskazówki:
         * - użyj pętli for
         * - sprawdzaj warunek parzystości: liczba % 2 == 0
         */

        sumEven();


    }
}
