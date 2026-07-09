package com.example.javaquest._13_libraries.Lesson07_CommonsIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

public class _Lesson07_CommonsIO {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 7: APACHE COMMONS IO ===");

        /*
         * ============================================================
         * 📦 CZYM JEST COMMONS-IO
         * ============================================================
         * - `commons-io` to biblioteka Apache Commons skupiona WYŁĄCZNIE
         *   na operacjach wejścia/wyjścia - plikach, strumieniach,
         *   nazwach ścieżek. Rozdział `_04_io` uczy standardowego API
         *   JDK (`File`, `Path`/`Files`, strumienie, `try-with-resources`
         *   z Lesson13) - TU akcent jest na to, co `commons-io` SKRACA i
         *   UPRASZCZA względem tego standardowego API, nie na
         *   powtarzanie podstaw I/O.
         * - Typowy wzorzec: to, co w `java.nio.file.Files` wymaga kilku
         *   linii (odczyt bajtów + konwersja na String w odpowiednim
         *   kodowaniu, obsługa wyjątków), `commons-io` daje jako JEDNĄ
         *   metodę statyczną.
         * - Wszystkie demo w tej lekcji operują na katalogu tymczasowym
         *   (`Files.createTempDirectory`), żeby nie zaśmiecać dysku -
         *   ten sam wzorzec co w rozdziale `_11_buildtools`.
         */
        Path tempDir = Files.createTempDirectory("lesson07_commonsio");
        System.out.println("Katalog roboczy demo: " + tempDir);

        /*
         * ============================================================
         * 🔹 FileUtils - OPERACJE NA PLIKACH W JEDNEJ LINII
         * ============================================================
         * - `FileUtils.writeStringToFile(file, text, charset)` zamiast
         *   `Files.writeString(path, text, charset)` (JDK 11+, samo w
         *   sobie już krótkie) - ale `FileUtils.readFileToString` +
         *   `writeStringToFile` mają IDENTYCZNĄ sygnaturę na starszym
         *   `File`, co bywa wygodne, gdy reszta kodu operuje na `File`,
         *   a nie na nowszym `Path`.
         * - `FileUtils.copyFile`/`copyDirectory` - w czystym `java.nio`
         *   kopiowanie CAŁEGO katalogu (rekurencyjnie, z podkatalogami)
         *   wymaga własnego `Files.walkFileTree` z `SimpleFileVisitor` -
         *   dziesiątki linii kodu. `FileUtils.copyDirectory(src, dest)`
         *   robi to w JEDNEJ linii.
         * - `FileUtils.deleteDirectory` - podobnie, usuwanie
         *   NIEPUSTEGO katalogu w JDK wymaga rekurencyjnego przejścia
         *   (`Files.walk` + sortowanie odwrotne + `Files.delete` na
         *   każdym pliku) - tu znowu jedna linia.
         * - `FileUtils.listFiles(dir, extensions, recursive)` - wyszukanie
         *   plików z konkretnym rozszerzeniem, opcjonalnie rekurencyjnie,
         *   bez ręcznego pisania `Files.walk().filter(...)`.
         */
        System.out.println("\n=== FileUtils: zapis/odczyt w jednej linii ===");
        File notatka = new File(tempDir.toFile(), "notatka.txt");
        FileUtils.writeStringToFile(notatka, "Apache Commons IO upraszcza I/O.", StandardCharsets.UTF_8);
        String odczytana = FileUtils.readFileToString(notatka, StandardCharsets.UTF_8);
        System.out.println("Odczytana zawartość: " + odczytana);
        System.out.println("Porównanie z JDK: Files.readString(path) robi to samo od Javy 11 - "
                + "przewaga FileUtils to spójne API na File w starszym/mieszanym kodzie oraz metody, "
                + "których Files.* NIE ma (copyDirectory, deleteDirectory, listFiles z filtrem).");

        System.out.println("\n=== FileUtils: kopiowanie i usuwanie katalogów ===");
        File zrodloDir = new File(tempDir.toFile(), "zrodlo");
        File kopiaDir = new File(tempDir.toFile(), "kopia");
        FileUtils.forceMkdir(zrodloDir);
        FileUtils.writeStringToFile(new File(zrodloDir, "a.txt"), "plik A", StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File(zrodloDir, "b.log"), "plik B", StandardCharsets.UTF_8);
        FileUtils.copyDirectory(zrodloDir, kopiaDir);
        System.out.println("Skopiowano katalog '" + zrodloDir.getName() + "' do '" + kopiaDir.getName()
                + "' - JEDNA linia zamiast Files.walkFileTree.");

        Collection<File> plikiTxt = FileUtils.listFiles(zrodloDir, new String[]{"txt"}, false);
        System.out.println("Pliki *.txt w katalogu źródłowym: " + plikiTxt.size() + " (pominięto .log dzięki filtrowi rozszerzeń)");

        FileUtils.deleteDirectory(kopiaDir);
        System.out.println("Usunięto katalog '" + kopiaDir.getName() + "' razem z zawartością (deleteDirectory).");

        /*
         * ============================================================
         * 🔹 IOUtils - OPERACJE NA STRUMIENIACH
         * ============================================================
         * - `IOUtils.toString(inputStream, charset)` - wczytanie CAŁEGO
         *   `InputStream` do `String` bez ręcznego bufora i pętli
         *   odczytu (`BufferedReader`+`StringBuilder` z `_04_io` Lesson03).
         * - `IOUtils.copy(input, output)` - przepisanie CAŁEGO strumienia
         *   wejściowego do wyjściowego bez ręcznej pętli z buforem
         *   bajtów (porównaj z `_04_io` Lesson04_BufferedStreams).
         * - `IOUtils.closeQuietly(closeable)` - zamyka zasób, ŁYKAJĄC
         *   ewentualny wyjątek przy zamykaniu. UCZCIWA UWAGA: od czasu
         *   `try-with-resources` (Java 7, `_04_io` Lesson13) ta metoda
         *   jest w praktyce RZADZIEJ potrzebna - `try-with-resources`
         *   załatwia bezpieczne zamykanie automatycznie i lepiej (nie
         *   ukrywa całkowicie wyjątków - dodaje je jako "suppressed").
         *   `closeQuietly` ma dziś sens głównie w starym kodzie sprzed
         *   Javy 7 lub gdy zasób jest zamykany w nietypowym miejscu
         *   (np. w bloku `finally` bez pełnego try-with-resources).
         */
        System.out.println("\n=== IOUtils: operacje na strumieniach ===");
        InputStream inputStream = new ByteArrayInputStream("dane ze strumienia".getBytes(StandardCharsets.UTF_8));
        String zStrumienia = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        System.out.println("IOUtils.toString(inputStream) = " + zStrumienia);

        InputStream zrodloStream = new ByteArrayInputStream("kopiowane bajty".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream celStream = new ByteArrayOutputStream();
        IOUtils.copy(zrodloStream, celStream);
        System.out.println("IOUtils.copy(input, output) = " + celStream.toString(StandardCharsets.UTF_8));

        // Preferowany dziś sposób zamykania zasobów - try-with-resources (Lesson13, _04_io):
        try (InputStream demoStream = new ByteArrayInputStream("zamykane bezpiecznie".getBytes(StandardCharsets.UTF_8))) {
            System.out.println("try-with-resources: " + IOUtils.toString(demoStream, StandardCharsets.UTF_8));
        }
        // closeQuietly - wciąż dostępne, przydatne poza pełnym try-with-resources:
        InputStream recznyStream = new ByteArrayInputStream("zamykany recznie".getBytes(StandardCharsets.UTF_8));
        IOUtils.closeQuietly(recznyStream);
        System.out.println("IOUtils.closeQuietly(recznyStream) - zamknięto bez rzucania wyjątku, nawet gdyby wystąpił.");

        /*
         * ============================================================
         * 🔹 FilenameUtils - BEZPIECZNA PRACA Z NAZWAMI ŚCIEŻEK
         * ============================================================
         * - Windows używa `\` jako separatora ścieżek, Linux/macOS `/`
         *   - ręczne parsowanie nazwy pliku/rozszerzenia stringowym
         *   `split` jest podatne na błędy międzyplatformowe.
         *   `FilenameUtils` działa POPRAWNIE niezależnie od systemu, na
         *   którym uruchamiasz kod.
         * - `getExtension` - rozszerzenie pliku, `getBaseName` - nazwa
         *   bez rozszerzenia, `getName` - sama nazwa pliku ze ścieżki.
         */
        System.out.println("\n=== FilenameUtils: nazwy niezależne od separatora OS ===");
        String sciezkaWindows = "C:\\dane\\raporty\\raport_2026.pdf";
        String sciezkaUnix = "/dane/raporty/raport_2026.pdf";
        System.out.println("getExtension (Windows) = " + FilenameUtils.getExtension(sciezkaWindows));
        System.out.println("getExtension (Unix)    = " + FilenameUtils.getExtension(sciezkaUnix));
        System.out.println("getBaseName (Windows)  = " + FilenameUtils.getBaseName(sciezkaWindows));
        System.out.println("getName (Unix)         = " + FilenameUtils.getName(sciezkaUnix));

        /*
         * ============================================================
         * 🔍 LineIterator - CZYTANIE DUŻYCH PLIKÓW LINIA PO LINII
         * ============================================================
         * - `FileUtils.readFileToString`/`readLines` wczytuje CAŁY plik
         *   do pamięci naraz - dla dużych plików (logi, eksporty CSV
         *   liczące gigabajty) to problem (OutOfMemoryError).
         * - `LineIterator` (z `FileUtils.lineIterator`) czyta plik LINIA
         *   PO LINII, trzymając w pamięci tylko jedną linię naraz -
         *   podobnie jak `BufferedReader.readLine()` w pętli (`_04_io`
         *   Lesson03), ale jako gotowy `Iterator<String>`, którego
         *   można użyć np. w pętli for-each po opakowaniu.
         * - Implementuje `Closeable`, więc działa z try-with-resources.
         */
        System.out.println("\n=== LineIterator: duże pliki bez wczytywania całości do pamięci ===");
        File logFile = new File(tempDir.toFile(), "log.txt");
        FileUtils.writeStringToFile(logFile, "linia 1\nlinia 2\nlinia 3\n", StandardCharsets.UTF_8);
        try (LineIterator lineIterator = FileUtils.lineIterator(logFile, "UTF-8")) {
            int licznik = 0;
            while (lineIterator.hasNext()) {
                licznik++;
                System.out.println("  wczytano: " + lineIterator.nextLine());
            }
            System.out.println("Wczytano " + licznik + " linie, po JEDNEJ naraz w pamięci.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `commons-io` NIE zastępuje `java.nio.file` - DOKŁADA
         *   operacje wysokopoziomowe (kopiowanie/usuwanie całych
         *   katalogów, filtrowanie po rozszerzeniu, iterowanie dużych
         *   plików) i skraca kod dla typowych, powtarzalnych zadań.
         * - `try-with-resources` (JDK 7+) pozostaje domyślnym sposobem
         *   zamykania zasobów - `IOUtils.closeQuietly` to narzędzie
         *   pomocnicze dla przypadków, gdzie pełny try-with-resources
         *   nie pasuje, a nie zamiennik dla niego.
         * - Sprzątanie po demo: katalog tymczasowy usuwany na końcu, by
         *   nie zostawiać śmieci na dysku.
         */
        FileUtils.deleteDirectory(tempDir.toFile());
        System.out.println("\n=== Katalog tymczasowy posprzątany ===");

        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }
}
