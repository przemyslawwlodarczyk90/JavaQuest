package com.example.javaquest._04_io.Lesson14_FileExceptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class _Lesson14_FileExceptions {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 WYJĄTKI PRZY PRACY Z PLIKAMI – HIERARCHIA
         * ============================================================
         * IOException to wyjątek BAZOWY (checked) dla wszystkich problemów
         * wejścia/wyjścia w Javie. Praktycznie każdy błąd związany z plikami
         * dziedziczy po IOException.
         *
         * java.lang.Exception
         *   └── java.io.IOException                     (bazowy, checked)
         *         ├── java.io.FileNotFoundException      (stare API – java.io)
         *         └── java.nio.file.FileSystemException  (nowe API – java.nio.file)
         *               ├── NoSuchFileException          (plik nie istnieje)
         *               ├── AccessDeniedException         (brak uprawnień)
         *               ├── FileAlreadyExistsException
         *               └── ...inne
         *
         * ⚠️ FileNotFoundException NIE JEST używany przez java.nio.file!
         * To dwie zupełnie różne rodziny wyjątków dla dwóch różnych API.
         */

        /*
         * ============================================================
         * 🔹 STARE API (java.io) – FileNotFoundException
         * ============================================================
         * FileReader/FileInputStream/FileOutputStream rzucają
         * FileNotFoundException gdy:
         * - plik do odczytu nie istnieje
         * - plik do zapisu istnieje jako katalog / nie da się utworzyć
         *
         * ⚠️ UWAGA: w starym API FileNotFoundException bywa też rzucany
         * przy problemach z UPRAWNIENIAMI (brak dostępu) – java.io NIE
         * rozróżnia "nie istnieje" od "brak dostępu", oba dają ten sam
         * wyjątek! To jedna z głównych wad starego API.
         */

        System.out.println("=== Stare API: FileNotFoundException ===");
        Path tempDir = Files.createTempDirectory("lesson14");
        Path missing = tempDir.resolve("nie_istnieje.txt");

        try (FileReader reader = new FileReader(missing.toFile())) {
            reader.read();
        } catch (FileNotFoundException e) {
            System.out.println("Zlapano FileNotFoundException: " + e.getMessage());
        }
        // FileNotFoundException extends IOException, wiec mozna tez lapac szerzej:
        try (FileReader reader = new FileReader(missing.toFile())) {
            reader.read();
        } catch (IOException e) {
            System.out.println("Zlapano jako IOException: " + e.getClass().getSimpleName());
        }

        System.out.println();

        /*
         * ============================================================
         * 🔍 NOWE API (java.nio.file) – NoSuchFileException
         * ============================================================
         * java.nio.file.Files rzuca NoSuchFileException zamiast
         * FileNotFoundException gdy plik nie istnieje.
         *
         * To bardziej precyzyjne API - NoSuchFileException oznacza
         * WYŁĄCZNIE brak pliku, a AccessDeniedException (niżej) oznacza
         * WYŁĄCZNIE problem z uprawnieniami. Rozdzielenie tych dwóch
         * przypadków to jedna z zalet java.nio.file nad java.io.
         */

        System.out.println("=== Nowe API: NoSuchFileException ===");
        try {
            Files.readAllBytes(missing);
        } catch (NoSuchFileException e) {
            System.out.println("Zlapano NoSuchFileException, plik: " + e.getFile());
        }

        try (var in = Files.newInputStream(missing)) {
            in.read();
        } catch (NoSuchFileException e) {
            System.out.println("Files.newInputStream tez rzuca NoSuchFileException: " + e.getFile());
        }

        System.out.println();

        /*
         * ============================================================
         * ❌ AccessDeniedException – PRÓBA ZAPISU DO CHRONIONEGO MIEJSCA
         * ============================================================
         * AccessDeniedException (java.nio.file) jest rzucany, gdy system
         * operacyjny odmawia dostępu do pliku/katalogu (np. plik tylko
         * do odczytu, brak uprawnień zapisu).
         */

        System.out.println("=== AccessDeniedException ===");
        Path readOnlyFile = tempDir.resolve("tylko_do_odczytu.txt");
        Files.writeString(readOnlyFile, "poczatkowa zawartosc");

        boolean marked = readOnlyFile.toFile().setWritable(false); // ustaw plik jako tylko do odczytu
        System.out.println("Plik oznaczony jako tylko-do-odczytu: " + marked);

        try {
            Files.writeString(readOnlyFile, "proba nadpisania");
            System.out.println("Zapis sie udal (niektore systemy/uprawnienia moga na to pozwalac)");
        } catch (AccessDeniedException e) {
            System.out.println("Zlapano AccessDeniedException dla: " + e.getFile());
        } finally {
            // przywroc mozliwosc zapisu, zeby dalo sie posprzatac plik
            readOnlyFile.toFile().setWritable(true);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * java.io (stare API):
         *   - FileNotFoundException dla braku pliku ORAZ dla braku dostepu
         *     (brak rozroznienia przyczyny!)
         *
         * java.nio.file (nowe API, od Java 7):
         *   - NoSuchFileException      -> plik/katalog nie istnieje
         *   - AccessDeniedException    -> brak uprawnien (odczyt/zapis)
         *   - obie dziedzicza po FileSystemException -> IOException
         *
         * ✅ W nowym kodzie preferuj java.nio.file (Files, Path) –
         *    daje precyzyjniejsze, łatwiejsze do rozroznienia wyjątki.
         * ✅ Zawsze lap konkretne wyjatki przed ogolnym IOException,
         *    aby moc inaczej reagowac na "brak pliku" i "brak dostepu".
         */

        // Sprzatanie po sobie
        Files.deleteIfExists(readOnlyFile);
        Files.deleteIfExists(tempDir);
    }
}
