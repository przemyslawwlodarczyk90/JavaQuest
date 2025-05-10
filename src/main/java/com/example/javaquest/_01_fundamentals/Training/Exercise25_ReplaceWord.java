package com.example.javaquest._01_fundamentals.Training;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Exercise25_ReplaceWord {

    public static void replaceWord(String pathFile){
        try {
            List<String> lines = Files.readAllLines(Path.of(pathFile));
            for (String s : lines){
                s.replaceAll("java", "kotlin");
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {



        /*
         * 🧪 Ćwiczenie 25: Zamień słowa w pliku
         *
         * Wczytaj plik "input.txt" i zamień wszystkie wystąpienia słowa "Java" na "Kotlin".
         * Zapisz zmodyfikowaną zawartość do pliku "modified.txt".
         *
         * Wskazówki:
         * - użyj Files.readAllLines() do wczytania pliku jako List<String>
         * - użyj .replaceAll("Java", "Kotlin") na każdej linii
         * - zapisz efekt do pliku używając Files.write()
         */
    }
}
