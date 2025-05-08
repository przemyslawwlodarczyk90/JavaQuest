package com.example.javaquest._01_fundamentals.Training;

import java.util.Scanner;

public class Exercise08_EvenOrOdd {

    public static void evenOddCounter(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz liczbę, sprawdzimmy czy jest parzysta czy nie parzysta");
        int number = scanner.nextInt();

        if(number%2==0){
            System.out.println("Liczba jest parzysta");
        }else if(number%2!=0){
            System.out.println("Liczba jest nieparzysta");
        }

    }

    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 8 (ŁATWE+): Parzysta czy nieparzysta?
         *
         * Wczytaj liczbę całkowitą z konsoli i wypisz:
         * - "Parzysta", jeśli liczba jest podzielna przez 2
         * - "Nieparzysta", jeśli nie
         *
         * Przykład 1:
         * Wejście: 8
         * Wynik: Parzysta
         *
         * Przykład 2:
         * Wejście: 7
         * Wynik: Nieparzysta
         *
         * Wskazówka:
         * - użyj operatora % i instrukcji if/else
         */

    }
}
