package com.example.javaquest._01_fundamentals.Training;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Exercise24_CountWord {

    public static int countJavaOccurrences(String filePath) {
        int count = 0;
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            for (String line : lines) {
                String[] words = line.split(" ");
                for (String word : words) {
                    if (word.equalsIgnoreCase("java")) {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }


    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 24: Zlicz wystąpienia słowa
         *
         * Napisz metodę, która wczyta plik "input.txt"
         * i zliczy, ile razy pojawia się w nim słowo "Java" (niezależnie od wielkości liter).
         *
         * Wskazówki:
         * - użyj Files.readAllLines() i List<String>
         * - rozbij linie na słowa (split(" "))
         * - porównuj z equalsIgnoreCase("Java")
         * - zliczaj za pomocą zmiennej lub stream().count()
         */
    }
}
