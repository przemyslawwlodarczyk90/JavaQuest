package com.example.javaquest.Lesson04_Loops;

public class Lesson04_Loops {

    public static void main(String[] args) {

        /*
         * ========================================================
         * PĘTLA WHILE
         * ========================================================
         * Pętla typu „dopóki warunek jest spełniony”
         * - Sprawdza warunek przed pierwszym wykonaniem.
         * - Może nie wykonać się ani razu.
         */

        int i = 0;
        while (i < 5) {
            System.out.println("while: i = " + i);
            i++;
        }

        /*
         * ========================================================
         * PĘTLA DO-WHILE
         * ========================================================
         * - Wykonuje blok przynajmniej raz, a dopiero potem sprawdza warunek.
         * - Używana, gdy chcemy "zawsze wykonać minimum jedną iterację"
         */

        int j = 0;
        do {
            System.out.println("do-while: j = " + j);
            j++;
        } while (j < 5);

        /*
         * ========================================================
         * PĘTLA FOR
         * ========================================================
         * - Klasyczna pętla z licznikiem.
         * - Zawiera wszystko w jednym miejscu: inicjalizacja; warunek; krok.
         * - Idealna do pętli o ustalonej liczbie kroków.
         */

        for (int k = 0; k < 5; k++) {
            System.out.println("for: k = " + k);
        }

        /*
         * Pętla nieskończona (uwaga! zatrzymaj ją ręcznie)
         * for (;;) {
         *     System.out.println("To działa bez końca");
         * }
         */

        /*
         * ========================================================
         * INKREMENTACJA I DEKREMENTACJA W FOR
         * ========================================================
         */

        System.out.println("Inkrementacja:");
        for (int x = 1; x <= 5; x++) {
            System.out.println("x = " + x);
        }

        System.out.println("Dekrementacja:");
        for (int x = 5; x >= 1; x--) {
            System.out.println("x = " + x);
        }

        System.out.println("Inkrementacja co 2:");
        for (int x = 0; x <= 10; x += 2) {
            System.out.println("x = " + x);
        }

        /*
         * ========================================================
         * FOREACH (ENHANCED FOR LOOP)
         * ========================================================
         * - Idealna do przechodzenia przez tablice i kolekcje.
         * - Nie nadaje się do modyfikacji indeksów.
         */

        int[] numbers = {10, 20, 30, 40, 50};
        for (int number : numbers) {
            System.out.println("foreach: number = " + number);
        }

        /*
         * ========================================================
         * SUMOWANIE ELEMENTÓW Z TABLICY (ZŁOŻONY PRZYKŁAD)
         * ========================================================
         */

        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        System.out.println("Suma liczb z tablicy: " + sum);

        /*
         * ========================================================
         * BREAK I CONTINUE – PRZERYWANIE I POMIJANIE ITERACJI
         * ========================================================
         */

        System.out.println("Przykład continue:");
        for (int n = 0; n < 5; n++) {
            if (n == 2) continue; // pomiń tylko tę iterację
            System.out.println("n = " + n);
        }

        System.out.println("Przykład break:");
        for (int n = 0; n < 5; n++) {
            if (n == 3) break; // zakończ całą pętlę
            System.out.println("n = " + n);
        }

        /*
         * continue – pomija tylko jedną iterację i przechodzi dalej
         * break – przerywa całkowicie wykonywanie pętli
         */

        /*
         * ========================================================
         * ZAGNIEŻDŻONE PĘTLE – TABLICA DWUWYMIAROWA
         * ========================================================
         * Dla struktur typu matrix, plansza, macierz.
         */

        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Zagnieżdżona pętla - tablica 2D:");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println(); // nowa linia po każdym wierszu
        }

        /*
         * Uwaga:
         * - Pamiętaj o kolejności: najpierw rzędy, potem kolumny.
         * - Można to sobie „narysować w głowie” albo na kartce.
         * - Przy bardziej złożonych strukturach lepiej dodać komentarze.
         */

        /*
         * ========================================================
         * PODSUMOWANIE
         * ========================================================
         */

        System.out.println("\n📌 Pętle w Javie pozwalają powtarzać fragmenty kodu:");

        System.out.println("- while: dopóki warunek jest spełniony (na początku)");
        System.out.println("- do-while: gwarantuje przynajmniej 1 wykonanie");
        System.out.println("- for: najczęściej do iteracji liczbowych");
        System.out.println("- foreach: uproszczona forma dla kolekcji/tablic");
        System.out.println("- break: przerywa pętlę całkowicie");
        System.out.println("- continue: pomija daną iterację i idzie dalej");

        System.out.println("\n🧠 Zasada: wybieraj pętlę w zależności od celu, np.:");
        System.out.println("- for → licznik");
        System.out.println("- foreach → przegląd danych");
        System.out.println("- while → czekasz na warunek");
        System.out.println("- do-while → co najmniej 1 raz coś się wydarzy");
    }
}
