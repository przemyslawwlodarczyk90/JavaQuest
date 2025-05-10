package com.example.javaquest._01_fundamentals.Training;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Exercise17_ReadFile {

    /*
     * 🧪 Zadanie 17 (ŚREDNIE): Odczytaj zawartość pliku tekstowego
     *
     * Napisz program, który wczytuje plik tekstowy `input.txt`
     * znajdujący się w katalogu głównym projektu.
     *
     * Program ma wypisać:
     * - wszystkie linie po kolei
     * - liczbę wierszy
     *
     * Wskazówki:
     * - użyj Files.readAllLines(Paths.get("input.txt"))
     * - pamiętaj o obsłudze wyjątku IOException
     */

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("input.txt"));

            System.out.println("Zawartość pliku:");
            for (String line : lines) {
                System.out.println(line);
            }

            System.out.println("Liczba wierszy: " + lines.size());

        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }
}




