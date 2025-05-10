package com.example.javaquest._01_fundamentals.Training;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

    public class Exercise21_CopyFile {
        public static void fileCopier(String file) {
            try {
                List<String> lines = Files.readAllLines(Path.of(file));
                Files.write(Path.of("output.txt"), lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 21: Skopiuj zawartość pliku
         *
         * Napisz metodę, która kopiuje zawartość pliku "input.txt" do pliku "output.txt".
         *
         * Wskazówki:
         * - użyj Files.readAllLines(...) do pobrania zawartości jako List<String>
         * - użyj Files.write(...) do zapisania do drugiego pliku
         * - pamiętaj o IOException
         */
    }
}
