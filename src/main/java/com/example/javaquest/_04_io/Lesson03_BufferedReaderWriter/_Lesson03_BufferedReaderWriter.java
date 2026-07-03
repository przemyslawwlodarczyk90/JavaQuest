package com.example.javaquest._04_io.Lesson03_BufferedReaderWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson03_BufferedReaderWriter {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 BUFOROWANIE – PO CO?
         * ============================================================
         * FileReader/FileWriter (Lekcja 2) NIE mają wewnętrznego bufora –
         * każde wywołanie read()/write() może oznaczać osobne, kosztowne
         * wejście/wyjście na poziomie systemu operacyjnego (syscall).
         *
         * Odczyt/zapis znak-po-znaku dla dużego pliku = tysiące/miliony
         * małych operacji dyskowych → BARDZO wolno.
         *
         * BufferedReader/BufferedWriter DOKŁADAJĄ bufor w pamięci (domyślnie
         * kilka KB): dane czytane/pisane są zbierane w dużych porcjach,
         * a rzeczywiste operacje dyskowe wykonywane rzadziej, na większych
         * kawałkach danych. To klasyczny wzorzec DEKORATORA (Decorator) –
         * BufferedReader "owija" inny Reader (np. FileReader) i dokłada bufor.
         *
         * ✅ Zysk wydajności może być rzędu 10x-100x dla operacji znak-po-znak
         * na dużych plikach.
         */

        /*
         * ============================================================
         * 🔹 BUFFEREDWRITER – DODATKOWE METODY
         * ============================================================
         * BufferedWriter opakowuje Writer (np. FileWriter) i dodaje:
         * - newLine()  → zapisuje separator linii WŁAŚCIWY dla systemu operacyjnego
         *                (\n na Linux/Mac, \r\n na Windows) – lepsze niż ręczne "\n"!
         * - flush()    → wymusza zrzut bufora (odziedziczone z Writer)
         */

        Path plik = Files.createTempFile("javaquest_lesson03_", ".txt");
        System.out.println("Plik demo: " + plik);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(plik.toFile()))) {
            bw.write("Pierwsza linia");
            bw.newLine(); // przenośny separator linii
            bw.write("Druga linia");
            bw.newLine();
            bw.write("Trzecia linia");
        } // close() = flush() + zamknięcie zasobów

        System.out.println("Zawartość pliku:\n" + Files.readString(plik));

        /*
         * ============================================================
         * 🔍 BUFFEREDREADER – readLine()
         * ============================================================
         * readLine() → czyta CAŁĄ linię tekstu (bez znaku końca linii),
         * zwraca null gdy osiągnięto koniec pliku.
         *
         * To najpopularniejszy sposób czytania plików tekstowych linia po linii.
         */

        try (BufferedReader br = new BufferedReader(new FileReader(plik.toFile()))) {
            String linia;
            int numer = 1;
            while ((linia = br.readLine()) != null) {
                System.out.println("Linia " + numer + ": " + linia);
                numer++;
            }
        }

        /*
         * ============================================================
         * 📊 PORÓWNANIE WYDAJNOŚCI: BUFFERED vs NIEBUFOROWANY ODCZYT
         * ============================================================
         * Wygenerujmy większy plik testowy i porównajmy:
         * A) odczyt znak-po-znaku przez "goły" FileReader (bez bufora)
         * B) odczyt linia-po-linii przez BufferedReader (z buforem)
         *
         * Mierzymy czas przez System.nanoTime() – bardziej precyzyjny niż
         * currentTimeMillis() dla krótkich operacji.
         */

        Path duzyPlik = Files.createTempFile("javaquest_lesson03_big_", ".txt");
        int liczbaLinii = 20_000;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(duzyPlik.toFile()))) {
            for (int i = 0; i < liczbaLinii; i++) {
                bw.write("To jest linia numer " + i + " – przykładowy tekst testowy do pomiaru wydajności.");
                bw.newLine();
            }
        }
        System.out.println("\nWygenerowano plik testowy: " + liczbaLinii + " linii, "
                + Files.size(duzyPlik) + " bajtów");

        // A) Odczyt znak-po-znaku – FileReader BEZ bufora
        long startA = System.nanoTime();
        long licznikZnakowA = 0;
        try (FileReader fr = new FileReader(duzyPlik.toFile())) {
            int c;
            while ((c = fr.read()) != -1) {
                licznikZnakowA++;
            }
        }
        long czasA = System.nanoTime() - startA;

        // B) Odczyt linia-po-linii – BufferedReader
        long startB = System.nanoTime();
        long licznikLiniiB = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(duzyPlik.toFile()))) {
            while (br.readLine() != null) {
                licznikLiniiB++;
            }
        }
        long czasB = System.nanoTime() - startB;

        System.out.println("\n--- WYNIKI POMIARU ---");
        System.out.println("A) FileReader znak-po-znak: " + licznikZnakowA + " znaków w "
                + (czasA / 1_000_000) + " ms");
        System.out.println("B) BufferedReader linia-po-linii: " + licznikLiniiB + " linii w "
                + (czasB / 1_000_000) + " ms");

        if (czasA > czasB) {
            double przyspieszenie = (double) czasA / czasB;
            System.out.printf("✅ BufferedReader był ok. %.1fx szybszy%n", przyspieszenie);
        } else {
            System.out.println("ℹ️ Na tej maszynie różnica może być niewielka dla małych plików "
                    + "(system operacyjny/JVM też buforują na niższym poziomie), "
                    + "ale różnica rośnie drastycznie dla większych plików i wolniejszych dysków.");
        }

        // Sprzątanie plików demonstracyjnych
        Files.deleteIfExists(plik);
        Files.deleteIfExists(duzyPlik);
        System.out.println("\nPliki demo usunięte.");

        /*
         * ============================================================
         * ⚠️ TYPOWE BŁĘDY
         * ============================================================
         * ❌ Owijanie w BufferedReader/Writer, ale zapominanie o zamknięciu
         *    zewnętrznego strumienia (close() na BufferedX zamyka też strumień
         *    wewnętrzny – wystarczy zamknąć tylko ten zewnętrzny)
         * ❌ Ręczne pisanie "\n" zamiast newLine() – "\n" nie jest przenośne
         *    (Windows używa "\r\n")
         * ❌ Mieszanie odczytu przez BufferedReader i bezpośrednio przez
         *    opakowany FileReader – dane mogą zniknąć w buforze
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * BufferedReader/BufferedWriter to DEKORATORY nad Reader/Writer:
         * - new BufferedReader(new FileReader(plik))
         * - new BufferedWriter(new FileWriter(plik))
         *
         * BufferedReader:
         * - readLine() → czyta całą linię, null = koniec pliku
         *
         * BufferedWriter:
         * - newLine() → przenośny separator linii (zamiast ręcznego "\n")
         *
         * ✅ Zawsze używaj buforowania przy pracy z plikami większymi niż
         * kilka linijek – różnica wydajności rośnie wraz z rozmiarem pliku
         * i liczbą operacji read()/write().
         */
    }
}
