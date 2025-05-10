package com.example.javaquest._01_fundamentals.Training;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Exercise19_WriteFile {
    public static void writer(String string) {
        try {
            Files.write(Path.of("output.txt"), string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 19: Zapisz tekst do pliku
         *
         * Napisz metodę, która przyjmuje jako argument String (zawartość)
         * i zapisuje go do pliku "output.txt", nadpisując go jeśli istnieje.
         *
         * Wskazówki:
         * - użyj Files.write(Path, byte[])
         * - do zamiany Stringa na bajty użyj content.getBytes()
         * - pamiętaj o IOException
         * - import: java.nio.file.Files, java.nio.file.Path
         */
    }
}
