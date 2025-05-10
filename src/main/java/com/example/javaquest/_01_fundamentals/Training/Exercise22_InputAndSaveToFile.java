package com.example.javaquest._01_fundamentals.Training;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Exercise22_InputAndSaveToFile {

    public static void saveNoter(String word){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please writer text");
        String string = scanner.nextLine();

        try {
            Path file = Files.write(Path.of("file.txt"), string.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {




        /*
         * 🧪 Ćwiczenie 22: Zapisz dane od użytkownika do pliku
         *
         * Napisz metodę, która poprosi użytkownika o tekst z klawiatury
         * i zapisze go do pliku "output.txt".
         *
         * Wskazówki:
         * - użyj Scanner(System.in) do pobrania wejścia
         * - użyj Files.write(...) do zapisu danych
         * - konwersja tekstu: input.getBytes()
         * - pamiętaj o IOException
         */
    }
}
