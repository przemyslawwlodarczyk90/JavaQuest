package com.example.javaquest._01_fundamentals.Lesson04_Loops;

public class Exercise25_LoopToBinaryWhile {
    public static void main(String[] args) {
        // 🔢 Zamień liczbę 156 na postać binarną używając pętli while (nie używaj Integer.toBinaryString)

        int number = 156;
        String binary = "";

        while (number > 0) {
            int bit = number % 2;      // reszta z dzielenia przez 2
            binary = bit + binary;     // dodajemy na początek (bo binarka czytana od końca!)
            number /= 2;               // dzielimy przez 2
        }

        System.out.println("Binarnie: " + binary); // powinno wypisać: 10011100
    }
}
