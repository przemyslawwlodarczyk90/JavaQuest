package com.example.javaquest._04_io.Lesson07_Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class _Lesson07_Scanner {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 SCANNER – CO TO JEST?
         * ============================================================
         * Scanner to klasa z java.util (nie java.io!), która ułatwia
         * parsowanie tekstu – zarówno z klawiatury (System.in), z pliku,
         * jak i ze zwykłego Stringa.
         *
         * Najczęstsze źródła danych dla Scannera:
         * - new Scanner(System.in)  → wczytywanie z klawiatury (konsola)
         * - new Scanner(File)       → wczytywanie z pliku tekstowego
         * - new Scanner(String)     → parsowanie tekstu z pamięci
         *
         * Scanner umie rozpoznawać typy: nextInt(), nextDouble(),
         * nextBoolean(), next() (pojedyncze słowo), nextLine() (cała linia).
         *
         * 🔍 W tej lekcji, żeby przykłady dały się uruchomić automatycznie
         * (bez czekania na wpisanie czegoś w konsoli), symulujemy dane
         * wejściowe za pomocą Scanner(String) zamiast Scanner(System.in).
         * Sposób użycia jest identyczny – zmienia się tylko źródło danych.
         */

        /*
         * ============================================================
         * 🔹 SCANNER(SYSTEM.IN) – WCZYTYWANIE Z KONSOLI
         * ============================================================
         * W prawdziwej, interaktywnej aplikacji użylibyśmy:
         *
         *   Scanner scanner = new Scanner(System.in);
         *   System.out.print("Podaj swoje imię: ");
         *   String imie = scanner.nextLine();
         *
         * ⚠️ UWAGA: jeśli zamkniesz taki Scanner metodą scanner.close(),
         * ZAMYKASZ TAKŻE System.in! Każda kolejna próba odczytu z konsoli
         * (nawet nowym Scannerem) zakończy się błędem (NoSuchElementException
         * albo strumień będzie już zamknięty). Dlatego zwykle NIE zamyka się
         * Scannera opartego na System.in – zostawia się go otwartego do
         * końca działania programu (JVM i tak zamknie strumienie na wyjściu).
         */

        /*
         * ============================================================
         * 🔹 SCANNER(FILE) – WCZYTYWANIE Z PLIKU
         * ============================================================
         */

        File tempFile = new File(System.getProperty("java.io.tmpdir"), "lesson07_scanner_demo.txt");
        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write("Jan Kowalski 25\n");
            fw.write("Anna Nowak 31\n");
        }

        System.out.println("--- Scanner(File): next() i nextLine() ---");
        try (Scanner fileScanner = new Scanner(tempFile)) {
            while (fileScanner.hasNext()) {
                String imie = fileScanner.next();     // pojedyncze słowo
                String nazwisko = fileScanner.next();  // kolejne słowo
                int wiek = fileScanner.nextInt();       // liczba
                System.out.println(imie + " " + nazwisko + " ma " + wiek + " lat.");
            }
        }

        tempFile.delete();

        /*
         * ============================================================
         * ❌ PUŁAPKA SCANNERA #1: nextInt() + nextLine()
         * ============================================================
         * Klasyczny błąd początkujących: po nextInt() (albo nextDouble()
         * itd.) wywołanie nextLine() zwraca PUSTY STRING, a nie kolejną linię!
         *
         * Dlaczego? nextInt() czyta TYLKO liczbę – nie konsumuje znaku
         * nowej linii '\n', który został po liczbie. Kolejne nextLine()
         * "widzi" tylko resztę bieżącej linii (czyli sam znak \n) i od razu
         * zwraca pusty String, zamiast czekać na kolejną, prawdziwą linię.
         */

        String wejscieBledne = "25\nJan Kowalski\n";
        System.out.println("\n--- ❌ Zepsuty przykład (nextInt + nextLine) ---");
        try (Scanner scannerBledny = new Scanner(wejscieBledne)) {
            int wiek = scannerBledny.nextInt();
            String imieNazwisko = scannerBledny.nextLine(); // ❌ zwróci "" (pusty string)!
            System.out.println("Wiek: " + wiek);
            System.out.println("Imię i nazwisko: \"" + imieNazwisko + "\""); // pusty string!
        }

        /*
         * ============================================================
         * ✅ NAPRAWA: DODATKOWE nextLine() PO nextInt()
         * ============================================================
         * Rozwiązanie: po nextInt()/nextDouble()/... dodaj jedno "puste"
         * wywołanie nextLine(), które skonsumuje pozostały znak nowej linii.
         * Dopiero KOLEJNE nextLine() zwróci właściwą, następną linię tekstu.
         */

        System.out.println("\n--- ✅ Naprawiony przykład ---");
        try (Scanner scannerOk = new Scanner(wejscieBledne)) {
            int wiek = scannerOk.nextInt();
            scannerOk.nextLine(); // konsumuje pozostały '\n' po liczbie
            String imieNazwisko = scannerOk.nextLine(); // teraz zwróci "Jan Kowalski"
            System.out.println("Wiek: " + wiek);
            System.out.println("Imię i nazwisko: \"" + imieNazwisko + "\""); // "Jan Kowalski"
        }

        /*
         * ============================================================
         * ❌ PUŁAPKA SCANNERA #2: InputMismatchException
         * ============================================================
         * Jeśli poprosisz o nextInt(), a w danych wejściowych jest tekst,
         * który nie da się sparsować jako liczba, Scanner rzuci
         * InputMismatchException. WAŻNE: błędny token NIE jest konsumowany –
         * pozostaje w strumieniu, więc kolejne next()/nextLine() go odczyta.
         */

        System.out.println("\n--- ❌ InputMismatchException ---");
        try (Scanner scannerZly = new Scanner("dwadziescia piec")) {
            int wiek = scannerZly.nextInt(); // ❌ "dwadziescia" to nie liczba!
            System.out.println("Wiek: " + wiek); // nie zostanie wykonane
        } catch (InputMismatchException e) {
            System.out.println("Błąd: oczekiwano liczby, otrzymano tekst! (" + e + ")");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Scanner(System.in) → konsola, Scanner(File) → plik,
         *   Scanner(String) → tekst z pamięci.
         * - next() → jedno słowo, nextLine() → cała linia,
         *   nextInt()/nextDouble()/nextBoolean() → sparsowana wartość.
         * - ❌ PUŁAPKA #1: nextInt() (lub inne next*()) zostawia w strumieniu
         *   znak '\n' → kolejne nextLine() zwraca pusty String.
         *   ✅ NAPRAWA: dodaj "pochłaniające" scanner.nextLine() zaraz po nextInt().
         * - ❌ PUŁAPKA #2: niezgodny typ danych → InputMismatchException,
         *   a błędny token zostaje w strumieniu (nie jest konsumowany).
         * - ⚠️ Zamknięcie Scanner(System.in) ZAMYKA System.in dla całej
         *   reszty aplikacji – zwykle nie zamyka się takiego Scannera.
         */
    }
}
