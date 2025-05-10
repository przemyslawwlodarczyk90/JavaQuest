package com.example.javaquest._01_fundamentals.Training;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Exercise18_CountLines {


    public static int countLiner (String file){
        int linesSize = 0;

        try{
            List<String> lines = Files.readAllLines(Path.of(file));
             linesSize = lines.size();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    return linesSize; }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 18: Wczytaj plik i wypisz liczbę linii
         *
         * Otwórz plik "input.txt" i wypisz na ekran liczbę linii, jakie zawiera.
         *
         * Wskazówki:
         * - użyj Files.readAllLines(Paths.get("input.txt")) do pobrania zawartości jako List<String>
         * - aby policzyć linie, użyj lista.size()
         * - pamiętaj o IOException
         */
    }
}
