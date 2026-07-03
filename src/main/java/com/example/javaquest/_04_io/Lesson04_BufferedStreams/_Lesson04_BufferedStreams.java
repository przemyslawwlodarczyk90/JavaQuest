package com.example.javaquest._04_io.Lesson04_BufferedStreams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class _Lesson04_BufferedStreams {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 BUFOROWANIE DANYCH BINARNYCH
         * ============================================================
         * Tak jak BufferedReader/BufferedWriter dokładają bufor do strumieni
         * ZNAKOWYCH (Reader/Writer), tak BufferedInputStream/BufferedOutputStream
         * dokładają bufor do strumieni BAJTOWYCH (InputStream/OutputStream).
         *
         * Zasada jest identyczna jak w Lekcji 3:
         * - FileInputStream/FileOutputStream BEZ bufora → wiele małych operacji I/O
         * - BufferedInputStream/BufferedOutputStream → dane zbierane w pamięci,
         *   rzeczywiste operacje dyskowe rzadsze i większe
         *
         * To znów wzorzec DEKORATORA: BufferedInputStream "owija" inny InputStream.
         *
         * Kiedy używać?
         * - Pliki binarne: obrazy, archiwa .zip, pliki wykonywalne, dane serializowane
         * - Kopiowanie plików bajt-po-bajcie lub blokami
         * - Każda sytuacja, gdzie czytamy/piszemy dane BINARNE (nie tekstowe)
         */

        /*
         * ============================================================
         * 🔹 PODSTAWOWE UŻYCIE
         * ============================================================
         * read()            → jeden bajt (0-255) jako int, -1 = koniec strumienia
         * read(byte[])       → wiele bajtów naraz do bufora, zwraca ile odczytano
         * write(int)          → jeden bajt
         * write(byte[])       → tablica bajtów
         * write(byte[], off, len) → fragment tablicy
         */

        Path zrodlo = Files.createTempFile("javaquest_lesson04_src_", ".bin");

        // Wygenerujmy plik binarny z losowymi bajtami (symulacja np. obrazu)
        Random random = new Random(42); // stały seed → powtarzalny wynik
        int rozmiarPliku = 2_000_000; // ok. 2 MB danych binarnych
        byte[] losoweDane = new byte[rozmiarPliku];
        random.nextBytes(losoweDane);
        Files.write(zrodlo, losoweDane);

        System.out.println("Plik źródłowy: " + zrodlo + " (" + Files.size(zrodlo) + " bajtów)");

        /*
         * ============================================================
         * 📊 KOPIOWANIE PLIKU – BEZ BUFORA vs Z BUFOREM
         * ============================================================
         * Skopiujemy ten sam plik na dwa sposoby i zmierzymy czas przez
         * System.nanoTime():
         * A) FileInputStream/FileOutputStream, bajt po bajcie (bez bufora)
         * B) BufferedInputStream/BufferedOutputStream, blokami przez read(byte[])
         */

        Path celBezBufora = Files.createTempFile("javaquest_lesson04_dst_plain_", ".bin");
        Path celZBuforem = Files.createTempFile("javaquest_lesson04_dst_buf_", ".bin");

        // A) Kopiowanie bajt po bajcie, BEZ bufora – celowo naiwna, wolna metoda
        long startA = System.nanoTime();
        try (FileInputStream in = new FileInputStream(zrodlo.toFile());
             FileOutputStream out = new FileOutputStream(celBezBufora.toFile())) {
            int bajt;
            while ((bajt = in.read()) != -1) {
                out.write(bajt);
            }
        }
        long czasA = System.nanoTime() - startA;

        // B) Kopiowanie blokami, Z BUFOREM
        long startB = System.nanoTime();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(zrodlo.toFile()));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(celZBuforem.toFile()))) {
            byte[] blok = new byte[8192]; // typowy rozmiar bloku (8 KB)
            int odczytano;
            while ((odczytano = in.read(blok)) != -1) {
                out.write(blok, 0, odczytano); // tylko faktycznie odczytany fragment!
            }
        }
        long czasB = System.nanoTime() - startB;

        // Weryfikacja poprawności kopii (czy dane się zgadzają)
        boolean identyczneA = Files.mismatch(zrodlo, celBezBufora) == -1;
        boolean identyczneB = Files.mismatch(zrodlo, celZBuforem) == -1;

        System.out.println("\n--- WYNIKI POMIARU ---");
        System.out.println("A) Bajt po bajcie (bez bufora): " + (czasA / 1_000_000) + " ms, "
                + "kopia poprawna: " + identyczneA);
        System.out.println("B) Blokami (z buforem, 8 KB):   " + (czasB / 1_000_000) + " ms, "
                + "kopia poprawna: " + identyczneB);

        if (czasA > czasB) {
            double przyspieszenie = (double) czasA / czasB;
            System.out.printf("✅ Wersja buforowana była ok. %.1fx szybsza%n", przyspieszenie);
        } else {
            System.out.println("ℹ️ Różnica zależy od systemu plików i JVM, ale dla dużych plików "
                    + "buforowanie blokowe niemal zawsze wygrywa z operacją bajt-po-bajcie.");
        }

        /*
         * ============================================================
         * ⚠️ TYPOWE BŁĘDY
         * ============================================================
         * ❌ Ignorowanie wartości zwróconej przez read(byte[]) – bufor może
         *    być tylko częściowo wypełniony (write(blok) zamiast write(blok, 0, odczytano)
         *    zapisałoby "śmieci" z poprzedniej iteracji na końcu pliku!)
         * ❌ Odczyt/zapis bajt-po-bajcie dla dużych plików binarnych – bardzo wolne
         * ❌ Używanie Reader/Writer do plików binarnych – zniszczy dane (błędne dekodowanie)
         * ✅ Do prostego kopiowania całych plików w praktyce często wystarczy
         *    Files.copy(zrodlo, cel) z java.nio.file – ale znajomość mechanizmu
         *    bufora jest kluczowa do zrozumienia JAK to działa "pod maską"
         *    i przydaje się przy strumieniach sieciowych czy niestandardowym I/O.
         */

        // Sprzątanie plików demonstracyjnych
        Files.deleteIfExists(zrodlo);
        Files.deleteIfExists(celBezBufora);
        Files.deleteIfExists(celZBuforem);
        System.out.println("\nPliki demo usunięte.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * BufferedInputStream/BufferedOutputStream to dekoratory nad
         * InputStream/OutputStream, analogiczne do BufferedReader/Writer
         * dla danych ZNAKOWYCH:
         * - new BufferedInputStream(new FileInputStream(plik))
         * - new BufferedOutputStream(new FileOutputStream(plik))
         *
         * - read(byte[]) / write(byte[], off, len) → operacje blokowe (wydajne)
         * - Zawsze uwzględniaj liczbę faktycznie odczytanych bajtów przy zapisie
         * - Buforowanie = mniej kosztownych operacji dyskowych = duży zysk wydajności
         *   przy pracy z większymi plikami binarnymi
         *
         * To domyka rozdział wprowadzający do I/O:
         * Lekcja 1 – teoria i hierarchia klas
         * Lekcja 2 – FileReader/FileWriter (znaki, bez bufora)
         * Lekcja 3 – BufferedReader/BufferedWriter (znaki, z buforem)
         * Lekcja 4 – BufferedInputStream/BufferedOutputStream (bajty, z buforem)
         */
    }
}
