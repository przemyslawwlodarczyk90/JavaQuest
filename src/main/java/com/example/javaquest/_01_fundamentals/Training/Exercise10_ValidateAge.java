package com.example.javaquest._01_fundamentals.Training;

import java.util.Scanner;

public class Exercise10_ValidateAge {

    public static void validateAge(int age){
        Scanner scanner = new Scanner(System.in);
        if (age<0){
            System.out.println("Błędny wiek");
        } else if (age>=0 && age<=12){
            System.out.println("Dziecko");
        }else if (age>=13 && age<=17){
            System.out.println("Nastolatek");
        }else if (age>=18 && age<=64){
        System.out.println("Dorosły");
        }else if (age>=65){
            System.out.println("Senior");
    }
        scanner.close();
    }

    public static void main(String[] args) {
        /*
         * 🧪 Zadanie 10 (ŁATWE/ŚREDNIE): Walidacja wieku
         *
         * Wczytaj wiek użytkownika z konsoli jako liczbę całkowitą.
         * Jeśli wiek jest:
         * - mniejszy niż 0 → wypisz: "Błędny wiek"
         * - od 0 do 12 → wypisz: "Dziecko"
         * - od 13 do 17 → wypisz: "Nastolatek"
         * - od 18 do 64 → wypisz: "Dorosły"
         * - 65 i więcej → wypisz: "Senior"
         *
         * Wskazówki:
         * - użyj Scanner do pobrania danych
         * - zastosuj strukturę if/else if/else
         */
    }
}
