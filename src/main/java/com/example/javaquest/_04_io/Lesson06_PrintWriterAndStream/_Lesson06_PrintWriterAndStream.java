package com.example.javaquest._04_io.Lesson06_PrintWriterAndStream;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class _Lesson06_PrintWriterAndStream {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 PRINTWRITER I PRINTSTREAM – CZYM SĄ?
         * ============================================================
         * PrintWriter i PrintStream to klasy, które NIE służą do zapisu
         * "surowych" danych – służą do wygodnego, sformatowanego zapisu
         * TEKSTU, podobnego do System.out.println(...).
         *
         * Obie klasy udostępniają te same, znajome metody:
         * - print(...)    – zapisuje bez nowej linii
         * - println(...)  – zapisuje z nową linią
         * - printf(...) / format(...) – zapis sformatowany (jak String.format)
         *
         * Różnica między nimi:
         * - PrintStream  → zapisuje BAJTY (opakowuje OutputStream)
         * - PrintWriter  → zapisuje ZNAKI (opakowuje Writer) – lepiej radzi
         *   sobie z kodowaniem znaków (np. polskimi literami)
         *
         * 🔍 CIEKAWOSTKA: System.out i System.err to obiekty typu PrintStream!
         */

        System.out.println("Czy System.out to PrintStream? "
                + (System.out instanceof PrintStream)); // true
        System.out.println("Czy System.err to PrintStream? "
                + (System.err instanceof PrintStream)); // true

        /*
         * ============================================================
         * 🔹 PRINTWRITER(WRITER) VS PRINTWRITER(OUTPUTSTREAM)
         * ============================================================
         * PrintWriter ma dwa "rodzaje" konstruktorów:
         *
         * 1) PrintWriter(Writer)       → np. PrintWriter(new FileWriter(file))
         *    - działa na poziomie ZNAKÓW, korzysta z kodowania Writer'a
         *
         * 2) PrintWriter(OutputStream) → np. PrintWriter(new FileOutputStream(file))
         *    - PrintWriter sam konwertuje znaki na bajty (domyślne kodowanie
         *      platformy, chyba że podamy Charset)
         *
         * W praktyce obie formy działają podobnie, ale PrintWriter(Writer)
         * jest bardziej jawny co do kodowania, gdy Writer (np. OutputStreamWriter)
         * ma podany konkretny Charset.
         */

        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File textFile = new File(tempDir, "lesson06_printwriter_demo.txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(textFile, StandardCharsets.UTF_8))) {
            pw.println("Pierwsza linia");
            pw.print("Druga linia bez nowej linii... ");
            pw.println("dokończona.");
            pw.printf("Liczba pi to w przybliżeniu %.2f%n", Math.PI);
            pw.format("%s ma %d lat.%n", "Kasia", 27);
        }

        System.out.println("\n--- Zawartość pliku zapisanego przez PrintWriter ---");
        try (var reader = new java.io.BufferedReader(new FileReader(textFile, StandardCharsets.UTF_8))) {
            reader.lines().forEach(System.out::println);
        }

        /*
         * ============================================================
         * 🔹 AUTOFLUSH
         * ============================================================
         * Konstruktor PrintWriter(Writer writer, boolean autoFlush) – jeśli
         * autoFlush=true, bufor jest opróżniany (flush) automatycznie po
         * każdym println(), printf() zakończonym '\n' – ALE NIE po print().
         *
         * Bez autoFlush (lub bez jawnego pw.flush()/pw.close()) dane mogą
         * zostać "uwięzione" w buforze i nie trafić od razu do pliku/konsoli!
         */

        File autoFlushFile = new File(tempDir, "lesson06_autoflush_demo.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(autoFlushFile), true)) { // autoFlush=true
            pw.println("Ta linia jest natychmiast zapisywana dzięki autoFlush=true");
        }
        System.out.println("\nautoFlush zapisany rozmiar pliku: " + autoFlushFile.length() + " bajtów");

        /*
         * ============================================================
         * 🔍 PRZEKIEROWANIE System.setOut() DO PLIKU
         * ============================================================
         * System.out to zwykłe pole statyczne typu PrintStream – można je
         * podmienić metodą System.setOut(PrintStream), by przekierować
         * wszystkie println() w programie do pliku zamiast konsoli.
         *
         * ⚠️ Po zakończeniu trzeba przywrócić oryginalny System.out,
         * inaczej reszta programu (i logi) "zniknie" w pliku!
         */

        PrintStream originalOut = System.out; // zapamiętujemy oryginalną konsolę
        File redirectFile = new File(tempDir, "lesson06_redirect_demo.txt");

        try (PrintStream filePrintStream = new PrintStream(redirectFile, StandardCharsets.UTF_8)) {
            System.setOut(filePrintStream);
            System.out.println("Ten tekst trafia do PLIKU, nie do konsoli!");
            System.out.println("Kolejna linia przekierowana do pliku.");
        } finally {
            System.setOut(originalOut); // przywracamy konsolę
        }

        System.out.println("\nSystem.out przywrócony – ta linia znów widoczna w konsoli.");
        System.out.println("--- Zawartość pliku z przekierowania ---");
        Files.readAllLines(redirectFile.toPath()).forEach(System.out::println);

        /*
         * ============================================================
         * 🔹 KIEDY PRINTWRITER ZAMIAST FILEWRITER?
         * ============================================================
         * FileWriter ma tylko write(String)/write(char[]) – brak formatowania.
         *
         * PrintWriter dodaje wygodne print/println/printf – ZAWSZE, gdy
         * zapisujesz tekst i chcesz go łatwo formatować (liczby, daty,
         * wielokrotne wartości w jednej linii), PrintWriter jest wygodniejszy:
         *
         *   FileWriter fw = new FileWriter(file);
         *   fw.write("Wynik: " + wynik + "\n");          // niewygodne
         *
         *   PrintWriter pw = new PrintWriter(new FileWriter(file));
         *   pw.printf("Wynik: %d%n", wynik);              // czytelniejsze
         *
         * PrintWriter NIE rzuca sprawdzanych wyjątków (checked exceptions)
         * przy zapisie – zamiast tego ustawia wewnętrzną flagę błędu,
         * odczytywaną metodą checkError().
         */

        try (PrintWriter pw = new PrintWriter(new FileWriter(textFile))) {
            pw.println("Test błędu:");
            System.out.println("Czy wystąpił błąd zapisu? " + pw.checkError()); // false
        }

        // Sprzątanie plików tymczasowych
        textFile.delete();
        autoFlushFile.delete();
        redirectFile.delete();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - PrintStream → zapis BAJTÓW, PrintWriter → zapis ZNAKÓW.
         * - System.out i System.err są typu PrintStream.
         * - PrintWriter(Writer) vs PrintWriter(OutputStream) – oba działają,
         *   ale Writer daje pełniejszą kontrolę nad kodowaniem znaków.
         * - autoFlush=true opróżnia bufor po println()/format() zakończonym '\n'.
         * - System.setOut(PrintStream) pozwala przekierować cały standardowy
         *   wyjście programu – pamiętaj o przywróceniu oryginału!
         * - PrintWriter > FileWriter, gdy potrzebujesz print/println/printf
         *   zamiast samego write(String).
         * - PrintWriter nie rzuca checked exceptions – sprawdź checkError().
         */
    }
}
