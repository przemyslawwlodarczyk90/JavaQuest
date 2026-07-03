package com.example.javaquest._04_io.Lesson10_FilesClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

public class _Lesson10_FilesClass {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 JAVA.NIO.FILE.FILES – NOWOCZESNE OPERACJE NA PLIKACH
         * ============================================================
         * Klasa Files (java.nio.file.Files) to zbiór statycznych metod
         * do pracy z plikami i katalogami. Zastępuje starą klasę
         * java.io.File, która miała wiele wad:
         *
         * ❌ java.io.File:
         * - metody typu delete()/mkdir() zwracają boolean – trudno poznać
         *   PRZYCZYNĘ błędu (brak uprawnień? plik nie istnieje? zajęty?)
         * - brak wsparcia dla symlinków
         * - toList()/exists() były wolne (dużo wywołań systemowych)
         *
         * ✅ java.nio.file.Files + Path:
         * - metody rzucają IOException z konkretnym komunikatem błędu
         * - bogate API: copy, move, walk, readAllBytes, readString...
         * - lepsza wydajność i wsparcie dla atrybutów plików
         *
         * Do testów użyjemy katalogu tymczasowego systemu – NIE zapisujemy
         * niczego w drzewie projektu, a na końcu posprzątamy po sobie.
         */

        Path tempDir = Files.createTempDirectory("javaquest_lesson10_");
        System.out.println("Katalog roboczy demo: " + tempDir);

        /*
         * ============================================================
         * 🔹 CREATEFILE() I CREATEDIRECTORIES()
         * ============================================================
         * createFile(path)        → tworzy PUSTY plik (błąd, jeśli już istnieje)
         * createDirectory(path)   → tworzy JEDEN katalog (błąd, jeśli rodzic nie istnieje)
         * createDirectories(path) → tworzy CAŁĄ ścieżkę katalogów (bez błędu, gdy już istnieją)
         */

        Path subDir = tempDir.resolve("raporty").resolve("2026");
        Files.createDirectories(subDir); // tworzy "raporty" i "raporty/2026" naraz
        System.out.println("\nUtworzono katalogi: " + subDir);
        System.out.println("Files.exists(subDir): " + Files.exists(subDir));

        Path emptyFile = subDir.resolve("pusty.txt");
        Files.createFile(emptyFile);
        System.out.println("Utworzono pusty plik: " + emptyFile);
        System.out.println("Rozmiar: " + Files.size(emptyFile) + " B"); // 0

        /*
         * ============================================================
         * 🔍 EXISTS() I SIZE()
         * ============================================================
         */

        System.out.println("\nexists(emptyFile): " + Files.exists(emptyFile)); // true
        System.out.println("exists(nieistniejący): " + Files.exists(tempDir.resolve("brak.txt"))); // false

        /*
         * ============================================================
         * 🔹 ZAPIS I ODCZYT TEKSTU – WRITESTRING() / READSTRING()
         * ============================================================
         * Od Javy 11: Files.writeString() i Files.readString() to
         * najprostszy sposób pracy z plikami tekstowymi – bez strumieni!
         */

        Path textFile = tempDir.resolve("notatka.txt");
        Files.writeString(textFile, "Ala ma kota.\nKot ma Alę.\n", StandardCharsets.UTF_8);

        String content = Files.readString(textFile, StandardCharsets.UTF_8);
        System.out.println("\nZawartość notatka.txt:\n" + content);

        // Dopisywanie do istniejącego pliku:
        Files.writeString(textFile, "Dopisana linia.\n", StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.APPEND);
        System.out.println("Po dopisaniu:\n" + Files.readString(textFile));

        // readAllLines() – wygodne, gdy chcemy listę linii
        List<String> lines = Files.readAllLines(textFile, StandardCharsets.UTF_8);
        System.out.println("Liczba linii: " + lines.size());

        /*
         * ============================================================
         * 🔹 ZAPIS I ODCZYT BAJTÓW – WRITE() / READALLBYTES()
         * ============================================================
         * Gdy pracujemy z danymi binarnymi (nie tekstem), używamy wersji
         * bajtowych zamiast String-owych.
         */

        Path binFile = tempDir.resolve("dane.bin");
        byte[] bytesToWrite = {1, 2, 3, 4, 5, 100, -1};
        Files.write(binFile, bytesToWrite);

        byte[] readBytes = Files.readAllBytes(binFile);
        System.out.println("\nOdczytane bajty: " + java.util.Arrays.toString(readBytes));

        /*
         * ============================================================
         * 🔍 COPY() I MOVE()
         * ============================================================
         * copy(source, target, options...) → kopiuje plik/katalog
         * move(source, target, options...) → przenosi (lub zmienia nazwę)
         *
         * Domyślnie oba rzucą wyjątek, jeśli cel już istnieje –
         * trzeba jawnie podać StandardCopyOption.REPLACE_EXISTING.
         */

        Path copyTarget = tempDir.resolve("notatka_kopia.txt");
        Files.copy(textFile, copyTarget);
        System.out.println("\nSkopiowano do: " + copyTarget);
        System.out.println("Zawartość kopii: " + Files.readString(copyTarget).strip());

        // Ponowna kopia bez REPLACE_EXISTING rzuci FileAlreadyExistsException:
        try {
            Files.copy(textFile, copyTarget);
        } catch (java.nio.file.FileAlreadyExistsException e) {
            System.out.println("Błąd przy ponownej kopii: " + e.getMessage());
        }
        // Z REPLACE_EXISTING zadziała bez problemu:
        Files.copy(textFile, copyTarget, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Nadpisano kopię (REPLACE_EXISTING) – OK");

        Path movedTarget = tempDir.resolve("notatka_przeniesiona.txt");
        Files.move(copyTarget, movedTarget);
        System.out.println("Przeniesiono kopię do: " + movedTarget);
        System.out.println("Stara ścieżka istnieje? " + Files.exists(copyTarget)); // false

        /*
         * ============================================================
         * 🔹 WALK() – PRZECHODZENIE DRZEWA KATALOGÓW
         * ============================================================
         * Files.walk(start) zwraca Stream<Path> ze WSZYSTKIMI plikami
         * i katalogami w drzewie (rekurencyjnie), łącznie z katalogiem
         * startowym. To strumień "leniwy" – trzeba go zamknąć (try-with-resources)!
         */

        System.out.println("\nCałe drzewo katalogu " + tempDir + ":");
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted()
                .forEach(path -> System.out.println("  " + tempDir.relativize(path)));
        }

        // Częsty wzorzec: znajdź wszystkie pliki *.txt w drzewie
        System.out.println("\nTylko pliki *.txt:");
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(path -> System.out.println("  " + tempDir.relativize(path)));
        }

        /*
         * ============================================================
         * ❌ DELETE() I DELETEIFEXISTS()
         * ============================================================
         * delete(path)          → rzuci NoSuchFileException, jeśli plik nie istnieje
         * deleteIfExists(path)  → zwraca boolean, NIE rzuca błędu gdy brak pliku
         *
         * ⚠️ Files.delete() NIE usuwa katalogów z zawartością –
         * katalog musi być pusty!
         */

        boolean deleted = Files.deleteIfExists(binFile);
        System.out.println("\ndeleteIfExists(dane.bin): " + deleted); // true
        boolean deletedAgain = Files.deleteIfExists(binFile);
        System.out.println("deleteIfExists ponownie: " + deletedAgain); // false, bez wyjątku

        /*
         * ============================================================
         * 📌 SPRZĄTANIE PO DEMO
         * ============================================================
         * Usuwamy cały katalog tymczasowy – od najgłębszych plików do
         * korzenia (walk + sorted odwrotnie, żeby usuwać pliki przed
         * katalogami nadrzędnymi).
         */

        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(java.util.Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        System.out.println("Nie udało się usunąć: " + path);
                    }
                });
        }
        System.out.println("\nPo sprzątaniu, katalog istnieje? " + Files.exists(tempDir)); // false

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Files.createFile / createDirectories → tworzenie plików i katalogów
         * Files.exists / size                  → sprawdzanie stanu
         * Files.readString / writeString        → tekst (Java 11+)
         * Files.readAllBytes / write             → dane binarne
         * Files.copy / move (+ StandardCopyOption.REPLACE_EXISTING)
         * Files.walk(path) → Stream<Path>, leniwy, wymaga zamknięcia (try-with-resources)
         * Files.delete / deleteIfExists → usuwanie (różnica w obsłudze braku pliku)
         * Files + Path = nowoczesny, zalecany zamiennik starego java.io.File.
         */
    }
}
