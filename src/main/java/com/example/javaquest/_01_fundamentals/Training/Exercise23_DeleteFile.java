package com.example.javaquest._01_fundamentals.Training;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Exercise23_DeleteFile {

    public static void deleter(String filePath){
        try {
            boolean files = Files.deleteIfExists(Path.of(filePath));

        }catch (IOException e){
            e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        /*
         * 🧪 Ćwiczenie 23: Usuń plik
         *
         * Napisz metodę, która usuwa plik o podanej nazwie (np. "output.txt"),
         * ale tylko jeśli istnieje.
         *
         * Wskazówki:
         * - użyj Files.deleteIfExists(Path)
         * - pamiętaj o obsłudze IOException
         */
    }
}

