package com.example.javaquest._01_fundamentals.Training;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Exercise20_AppendLine {

    public static void append(String text) {
        try {
            Files.write(
                    Path.of("output.txt"),
                    (text + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE // tworzy plik jeśli nie istnieje
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 20: Dopisz linię do pliku
         *
         * Napisz metodę, która dopisuje nową linię tekstu do pliku "output.txt"
         * (nie nadpisuj pliku – dopisuj na końcu).
         *
         * Wskazówki:
         * - użyj Files.write() z flagą StandardOpenOption.APPEND
         * - dodaj na końcu System.lineSeparator(), żeby każda linia była w osobnym wierszu
         * - importuj StandardOpenOption
         */
    }
}

