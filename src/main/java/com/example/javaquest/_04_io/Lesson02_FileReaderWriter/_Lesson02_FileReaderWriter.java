package com.example.javaquest._04_io.Lesson02_FileReaderWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson02_FileReaderWriter {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 FILEREADER I FILEWRITER – ODCZYT I ZAPIS ZNAKÓW
         * ============================================================
         * FileReader i FileWriter to najprostsze klasy do pracy z PLIKAMI TEKSTOWYMI.
         * Należą do hierarchii Reader/Writer (patrz Lekcja 1) – operują na ZNAKACH,
         * nie na bajtach.
         *
         * FileReader  → odczyt znaków z pliku
         * FileWriter  → zapis znaków do pliku
         *
         * ⚠️ Obie klasy używają domyślnego kodowania platformy, chyba że podamy
         * Charset jawnie (o czym więcej przy InputStreamReader/OutputStreamWriter).
         * W praktyce produkcyjnej częściej sięga się po Files.newBufferedReader/Writer
         * z jawnym Charset – tu poznajemy klasyczne, "podręcznikowe" API.
         *
         * ❌ FileReader/FileWriter NIE mają wewnętrznego bufora – każde wywołanie
         * read()/write() może oznaczać osobną operację na dysku. Dla wydajności
         * owija się je w BufferedReader/BufferedWriter (Lekcja 3).
         */

        // Plik demonstracyjny w katalogu tymczasowym systemu – nie zaśmiecamy projektu
        Path plik = Files.createTempFile("javaquest_lesson02_", ".txt");
        System.out.println("Plik demo: " + plik);

        /*
         * ============================================================
         * 🔹 ZAPIS – FILEWRITER
         * ============================================================
         * Konstruktor FileWriter(File/String) → NADPISUJE istniejący plik
         * Konstruktor FileWriter(..., append=true) → DOPISUJE na końcu pliku
         *
         * write(String)   → zapisuje cały napis
         * write(int)       → zapisuje pojedynczy znak
         * write(char[])   → zapisuje tablicę znaków
         * flush()          → wymusza zrzut bufora wewnętrznego systemu na dysk
         * close()          → zamyka strumień (też wywołuje flush())
         */

        // 1) Zapis podstawowy (nadpisanie pliku)
        FileWriter writer = new FileWriter(plik.toFile());
        writer.write("Pierwsza linia\n");
        writer.write("Druga linia\n");
        writer.flush(); // wymuś zapis na dysk (opcjonalne przed close(), ale demonstracyjnie pokazujemy)
        writer.close(); // ZAWSZE zamykaj strumień – zwalnia uchwyt pliku

        System.out.println("Zawartość po pierwszym zapisie:");
        System.out.println(Files.readString(plik));

        // 2) Zapis z append=true – dopisywanie, bez kasowania istniejącej treści
        FileWriter appendWriter = new FileWriter(plik.toFile(), true); // true = append
        appendWriter.write("Trzecia linia (dopisana)\n");
        appendWriter.close();

        System.out.println("Zawartość po dopisaniu (append=true):");
        System.out.println(Files.readString(plik));

        // 3) Zapis pojedynczych znaków i tablicy znaków
        FileWriter charWriter = new FileWriter(plik.toFile()); // znów nadpisuje (append domyślnie = false)
        charWriter.write('A');           // pojedynczy znak
        charWriter.write('B');
        charWriter.write('C');
        char[] tablicaZnakow = {'-', 'X', 'Y', 'Z'};
        charWriter.write(tablicaZnakow); // cała tablica naraz
        charWriter.close();

        System.out.println("Zawartość po zapisie znak-po-znaku i tablicą: "
                + Files.readString(plik)); // ABC-XYZ

        /*
         * ============================================================
         * 🔍 ODCZYT – FILEREADER
         * ============================================================
         * read()          → czyta JEDEN znak (jako int), -1 = koniec pliku
         * read(char[])    → czyta wiele znaków naraz do bufora, zwraca ile odczytano (-1 = koniec)
         *
         * Wzorzec pętli odczytu znak-po-znak:
         *   int c;
         *   while ((c = reader.read()) != -1) { ... }
         */

        // Przygotuj plik z tekstem do odczytu
        FileWriter przygotuj = new FileWriter(plik.toFile());
        przygotuj.write("Ala ma kota");
        przygotuj.close();

        // 1) Odczyt znak po znaku
        FileReader reader = new FileReader(plik.toFile());
        StringBuilder odczytanyTekst = new StringBuilder();
        int znak;
        while ((znak = reader.read()) != -1) {
            odczytanyTekst.append((char) znak);
        }
        reader.close();
        System.out.println("\nOdczyt znak po znaku: " + odczytanyTekst);

        // 2) Odczyt przez bufor read(char[]) – wydajniejszy, mniej wywołań read()
        FileReader bufferedStyleReader = new FileReader(plik.toFile());
        char[] bufor = new char[4]; // celowo mały bufor, żeby zobaczyć wiele odczytów
        StringBuilder zBufora = new StringBuilder();
        int iloscOdczytana;
        while ((iloscOdczytana = bufferedStyleReader.read(bufor)) != -1) {
            zBufora.append(bufor, 0, iloscOdczytana); // tylko odczytane znaki (0..iloscOdczytana)!
        }
        bufferedStyleReader.close();
        System.out.println("Odczyt przez read(char[]): " + zBufora);

        /*
         * ============================================================
         * ⚠️ TYPOWE BŁĘDY
         * ============================================================
         * ❌ Zapomnienie o close() → wyciek zasobów (uchwyt pliku pozostaje zajęty)
         * ❌ Użycie read(char[]) bez uwzględnienia zwróconej długości
         *    (bufor może być tylko częściowo wypełniony przy ostatnim odczycie!)
         * ❌ Brak flush() przed odczytem tego samego pliku w tym samym programie
         *    (dane mogą jeszcze siedzieć w buforze i nie być zapisane na dysk)
         * ❌ Konstruktor bez append=true nadpisuje cały plik – łatwo stracić dane
         *
         * ✅ Zawsze zamykaj strumienie (najlepiej try-with-resources – patrz niżej)
         */

        // Idiomatyczny zapis z try-with-resources – automatyczne close() nawet przy wyjątku
        try (FileWriter tryWriter = new FileWriter(plik.toFile())) {
            tryWriter.write("Zapis przez try-with-resources");
        } // close() wywołane automatycznie

        try (FileReader tryReader = new FileReader(plik.toFile())) {
            char[] caly = new char[128];
            int n = tryReader.read(caly);
            System.out.println("Try-with-resources odczyt: " + new String(caly, 0, n));
        }

        // Sprzątanie – usuwamy plik demonstracyjny, żeby nie zostawiać śmieci
        Files.deleteIfExists(plik);
        System.out.println("\nPlik demo usunięty: " + !Files.exists(plik));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * FileWriter:
         * - new FileWriter(plik)             → nadpisuje plik
         * - new FileWriter(plik, true)       → dopisuje (append)
         * - write(String/char/char[])         → zapis danych
         * - flush()                           → wymusza zapis bufora na dysk
         * - close()                           → zamyka strumień (wywołuje flush())
         *
         * FileReader:
         * - read()                            → jeden znak (int), -1 = koniec
         * - read(char[])                      → wiele znaków, zwraca ile odczytano
         *
         * ✅ Zawsze zamykaj strumienie – najlepiej przez try-with-resources
         * ⚠️ Brak bufora → dla dużych plików użyj BufferedReader/BufferedWriter (Lekcja 3)
         */
    }
}
