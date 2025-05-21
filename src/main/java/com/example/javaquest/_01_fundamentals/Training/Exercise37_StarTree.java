package com.example.javaquest._01_fundamentals.Training;

public class Exercise37_StarTree {

    public static void christmasTree(int number){
        for (int i = 1; i<number-1; i++){
            for (int j =  number-1; j>0; i--){
                System.out.println(" ");
            }
            for (int k = 1; k<(number/2)-1; k++){
                System.out.println("*");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 37: Choinka z gwiazdek
         *
         * Napisz metodę:
         * public static void drawTree(int height)
         *
         * Która wypisze choinkę z gwiazdek o zadanej wysokości.
         *
         * Przykład dla height = 4:
         *    *
         *   ***
         *  *****
         * *******
         *
         * Wskazówki:
         * - w każdej linii:
         *   - najpierw wypisz spacje: (height - i)
         *   - potem gwiazdki: (2 * i - 1)
         * - użyj pętli for i String.repeat()
         */
    }
}
