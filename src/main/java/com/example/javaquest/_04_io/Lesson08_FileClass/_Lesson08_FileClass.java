package com.example.javaquest._04_io.Lesson08_FileClass;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class _Lesson08_FileClass {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 KLASA FILE – CO TO JEST?
         * ============================================================
         * java.io.File reprezentuje ŚCIEŻKĘ do pliku lub katalogu w systemie
         * plików – NIE reprezentuje samej zawartości pliku! Obiekt File
         * może wskazywać na coś, co jeszcze nie istnieje na dysku.
         *
         * File umożliwia:
         * - sprawdzanie czy plik/katalog istnieje
         * - tworzenie/usuwanie/zmienianie nazwy plików i katalogów
         * - przeglądanie zawartości katalogów
         * - pobieranie informacji (ścieżka, rozmiar, uprawnienia)
         *
         * ⚠️ WAŻNE: java.io.File jest klasą "starszą" (od Javy 1.0).
         * Od Javy 7 istnieje nowocześniejsze API: java.nio.file.Path
         * i java.nio.file.Files (osobna, późniejsza lekcja), które oferuje
         * lepszą obsługę błędów, symlinki, atrybuty plików itd.
         * File wciąż spotyka się w starszym kodzie i prostych skryptach –
         * warto go znać, ale w nowym kodzie preferuj Path/Files.
         */

        File tempDir = new File(System.getProperty("java.io.tmpdir"), "lesson08_file_demo");

        /*
         * ============================================================
         * 🔹 exists(), mkdir() vs mkdirs()
         * ============================================================
         * exists()  → czy plik/katalog istnieje fizycznie na dysku
         * mkdir()   → tworzy JEDEN katalog; zwraca false, jeśli katalog
         *             nadrzędny nie istnieje!
         * mkdirs()  → tworzy katalog WRAZ Z całą brakującą ścieżką
         *             nadrzędną (jak mkdir -p w Linuksie)
         */

        System.out.println("Czy istnieje przed utworzeniem? " + tempDir.exists()); // false

        File zagniezdzonyKatalog = new File(tempDir, "podkatalog/glebiej");
        boolean mkdirResult = zagniezdzonyKatalog.mkdir(); // ❌ false – rodzice nie istnieją
        System.out.println("mkdir() na zagnieżdżonej ścieżce: " + mkdirResult); // false

        boolean mkdirsResult = zagniezdzonyKatalog.mkdirs(); // ✅ tworzy całą ścieżkę
        System.out.println("mkdirs() na zagnieżdżonej ścieżce: " + mkdirsResult); // true
        System.out.println("Czy istnieje teraz? " + tempDir.exists()); // true

        /*
         * ============================================================
         * 🔹 isDirectory() / isFile() / getAbsolutePath()
         * ============================================================
         */

        System.out.println("\ntempDir.isDirectory(): " + tempDir.isDirectory()); // true
        System.out.println("tempDir.isFile(): " + tempDir.isFile());             // false
        System.out.println("Ścieżka bezwzględna: " + tempDir.getAbsolutePath());

        // Tworzymy kilka plików wewnątrz katalogu do dalszych przykładów
        File plikTxt1 = new File(tempDir, "raport.txt");
        File plikTxt2 = new File(tempDir, "notatki.txt");
        File plikLog = new File(tempDir, "system.log");

        for (File f : new File[]{plikTxt1, plikTxt2, plikLog}) {
            try (FileWriter fw = new FileWriter(f)) {
                fw.write("Przykładowa zawartość pliku " + f.getName());
            }
        }

        System.out.println("\nplikTxt1.isFile(): " + plikTxt1.isFile());   // true
        System.out.println("plikTxt1.isDirectory(): " + plikTxt1.isDirectory()); // false

        /*
         * ============================================================
         * 🔍 listFiles() – BEZ FILTRA
         * ============================================================
         * listFiles() zwraca tablicę File[] reprezentującą zawartość
         * katalogu (pliki i podkatalogi), albo null jeśli to nie katalog
         * lub wystąpił błąd wejścia/wyjścia.
         */

        System.out.println("\n--- listFiles() bez filtra ---");
        File[] wszystkie = tempDir.listFiles();
        if (wszystkie != null) {
            Arrays.sort(wszystkie); // dla powtarzalnej kolejności wypisywania
            for (File f : wszystkie) {
                System.out.println("  " + f.getName() + (f.isDirectory() ? " [katalog]" : " [plik]"));
            }
        }

        /*
         * ============================================================
         * 🔍 listFiles(FileFilter) – Z FILTREM
         * ============================================================
         * FileFilter to interfejs funkcyjny z jedną metodą: accept(File).
         * Można podać lambdę – np. żeby wyfiltrować tylko pliki .txt.
         */

        System.out.println("\n--- listFiles(FileFilter) – tylko *.txt ---");
        FileFilter txtFilter = f -> f.isFile() && f.getName().endsWith(".txt");
        File[] plikiTxt = tempDir.listFiles(txtFilter);
        if (plikiTxt != null) {
            Arrays.sort(plikiTxt);
            for (File f : plikiTxt) {
                System.out.println("  " + f.getName());
            }
        }

        /*
         * ============================================================
         * 🔹 renameTo() – ZMIANA NAZWY / PRZENIESIENIE
         * ============================================================
         * renameTo(File dest) zmienia nazwę pliku (lub przenosi go, jeśli
         * dest wskazuje na inny katalog). Zwraca boolean – sukces/porażka.
         * ⚠️ Zachowanie bywa zależne od systemu operacyjnego (np. czy
         * nadpisuje istniejący plik docelowy) – dla poważniejszych operacji
         * lepiej użyć Files.move() z java.nio.file.
         */

        File plikPoZmianie = new File(tempDir, "raport_finalny.txt");
        boolean zmienionoNazwe = plikTxt1.renameTo(plikPoZmianie);
        System.out.println("\nZmieniono nazwę raport.txt -> raport_finalny.txt: " + zmienionoNazwe);
        System.out.println("Czy stary plik nadal istnieje? " + plikTxt1.exists()); // false
        System.out.println("Czy nowy plik istnieje? " + plikPoZmianie.exists());   // true

        /*
         * ============================================================
         * ❌ delete() – USUWANIE PLIKÓW I KATALOGÓW
         * ============================================================
         * delete() usuwa POJEDYNCZY plik lub PUSTY katalog.
         * ⚠️ Nie usunie katalogu, który zawiera pliki – trzeba najpierw
         * usunąć zawartość (rekurencyjnie), a dopiero potem sam katalog.
         */

        System.out.println("\n--- Sprzątanie (delete) ---");

        // Próba usunięcia niepustego katalogu – nie powiedzie się
        boolean usunietoNiepusty = tempDir.delete();
        System.out.println("Próba delete() na niepustym katalogu: " + usunietoNiepusty); // false

        // Poprawne sprzątanie: najpierw pliki, potem podkatalogi, na końcu główny katalog
        deleteRecursively(tempDir);
        System.out.println("Czy katalog istnieje po pełnym posprzątaniu? " + tempDir.exists()); // false

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - File reprezentuje ŚCIEŻKĘ, niekoniecznie istniejący plik.
         * - exists()      → czy istnieje na dysku
         * - mkdir()       → tworzy JEDEN katalog (rodzic musi istnieć)
         * - mkdirs()      → tworzy całą brakującą ścieżkę katalogów
         * - isFile()/isDirectory() → typ wpisu
         * - getAbsolutePath()      → pełna ścieżka bezwzględna
         * - listFiles()            → zawartość katalogu (może zwrócić null!)
         * - listFiles(FileFilter)  → zawartość z filtrowaniem
         * - renameTo(File)         → zmiana nazwy / przeniesienie
         * - delete()               → usuwa plik LUB PUSTY katalog
         *
         * ⚠️ java.io.File jest w dużej mierze zastąpione przez
         * java.nio.file.Path/Files (nowocześniejsze, lepsza obsługa błędów) –
         * poznamy je w kolejnej lekcji. File wciąż warto znać – spotyka się
         * go w starszym kodzie i prostych skryptach.
         */
    }

    /**
     * Pomocnicza metoda do rekurencyjnego usuwania katalogu wraz z zawartością –
     * potrzebna, bo File.delete() nie usuwa niepustych katalogów.
     */
    private static void deleteRecursively(File file) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                deleteRecursively(child);
            }
        }
        file.delete();
    }
}
