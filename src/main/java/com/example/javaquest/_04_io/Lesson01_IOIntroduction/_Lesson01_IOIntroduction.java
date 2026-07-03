package com.example.javaquest._04_io.Lesson01_IOIntroduction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class _Lesson01_IOIntroduction {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 CZYM JEST INPUT/OUTPUT (I/O)?
         * ============================================================
         * Input/Output to mechanizm wymiany danych między programem
         * a światem zewnętrznym:
         * - INPUT  → dane WCHODZĄ do programu (plik, klawiatura, sieć, baza danych...)
         * - OUTPUT → dane WYCHODZĄ z programu (plik, ekran, sieć, drukarka...)
         *
         * Przykłady źródeł/celów I/O:
         * - pliki na dysku
         * - konsola (System.in / System.out)
         * - połączenia sieciowe (sockets)
         * - pamięć (tablice bajtów, String)
         * - inne procesy
         *
         * Java abstrahuje wszystkie te źródła za pomocą wspólnego API – STRUMIENI (streams).
         * Dzięki temu kod odczytujący dane z pliku wygląda bardzo podobnie do kodu
         * odczytującego dane z sieci czy z pamięci.
         */

        /*
         * ============================================================
         * 🔹 STRUMIEŃ (STREAM) – MODEL MENTALNY
         * ============================================================
         * Strumień to sekwencja danych płynąca w JEDNYM kierunku:
         *
         *   Źródło danych  --->  [STRUMIEŃ]  --->  Program (odczyt = InputStream/Reader)
         *   Program        --->  [STRUMIEŃ]  --->  Cel danych (zapis = OutputStream/Writer)
         *
         * Ważne cechy strumieni w Javie:
         * - są JEDNOKIERUNKOWE – albo czytasz, albo piszesz (nie oba naraz jednym obiektem)
         * - są SEKWENCYJNE – dane płyną krok po kroku, zwykle nie da się "przewinąć" w dowolne miejsce
         * - trzeba je ZAMYKAĆ (close()), żeby zwolnić zasoby systemowe (uchwyty plików, gniazda...)
         * - to NIE to samo co java.util.stream.Stream (Streams API z Lekcji 10-13!)
         *   – tu "strumień" oznacza strumień I/O (bajtów lub znaków), zupełnie inny temat
         *
         * ⚠️ Nazewnictwo w tym rozdziale zawsze dotyczy strumieni I/O (java.io),
         * a nie Stream<T> ze Streams API.
         */

        /*
         * ============================================================
         * 🔍 DANE TEKSTOWE vs BINARNE
         * ============================================================
         * DANE BINARNE (bajty, byte):
         * - surowe bajty (0-255), bez żadnej interpretacji
         * - obrazy, wideo, pliki .zip, .exe, serializowane obiekty
         * - obsługiwane przez InputStream / OutputStream
         *
         * DANE TEKSTOWE (znaki, char):
         * - bajty zinterpretowane jako znaki wg KODOWANIA (np. UTF-8, ISO-8859-2)
         * - pliki .txt, .csv, .json, .xml, kod źródłowy
         * - obsługiwane przez Reader / Writer
         *
         * Kluczowa różnica: tekst to bajty + kodowanie znaków.
         * Ten sam plik binarny odczytany jako tekst w złym kodowaniu da "krzaki".
         */

        // Demonstracja: te same dane jako bajty i jako znaki
        String tekst = "Zażółć gęślą jaźń"; // polskie znaki – dobry test kodowania
        byte[] bajty = tekst.getBytes(StandardCharsets.UTF_8);

        System.out.println("Tekst: " + tekst);
        System.out.println("Liczba znaków: " + tekst.length());
        System.out.println("Liczba bajtów (UTF-8): " + bajty.length); // więcej niż znaków! (polskie znaki > 1 bajt)

        /*
         * ============================================================
         * 🔹 HIERARCHIA KLAS I/O – DWIE RÓWNOLEGŁE GAŁĘZIE
         * ============================================================
         * Java ma DWIE równoległe hierarchie klas I/O – jedną dla bajtów,
         * jedną dla znaków:
         *
         *   BAJTY (dane binarne)              ZNAKI (dane tekstowe)
         *   ────────────────────              ──────────────────────
         *   InputStream  (abstrakcyjna)        Reader  (abstrakcyjna)
         *   ├── FileInputStream                ├── FileReader
         *   ├── ByteArrayInputStream            ├── StringReader
         *   ├── BufferedInputStream             ├── BufferedReader
         *   └── ObjectInputStream               └── InputStreamReader (most bajty→znaki)
         *
         *   OutputStream (abstrakcyjna)        Writer  (abstrakcyjna)
         *   ├── FileOutputStream                ├── FileWriter
         *   ├── ByteArrayOutputStream            ├── StringWriter
         *   ├── BufferedOutputStream             ├── BufferedWriter
         *   └── ObjectOutputStream               └── OutputStreamWriter (most znaki→bajty)
         *
         * Metody podstawowe:
         * - InputStream:  int read()          → zwraca JEDEN bajt (0-255) lub -1 na końcu
         * - OutputStream: void write(int b)    → zapisuje JEDEN bajt
         * - Reader:       int read()          → zwraca JEDEN znak (jako int) lub -1 na końcu
         * - Writer:       void write(int c)    → zapisuje JEDEN znak
         *
         * Obie hierarchie mają też warianty "tablicowe" i "buforowane",
         * które poznamy w kolejnych lekcjach (FileReader/Writer, BufferedReader/Writer,
         * BufferedInputStream/OutputStream).
         */

        /*
         * ============================================================
         * ⚠️ DLACZEGO DWIE RÓWNOLEGŁE HIERARCHIE?
         * ============================================================
         * Historia: w Javie 1.0 istniały TYLKO strumienie bajtowe (InputStream/OutputStream).
         * Problem: obsługa tekstu w wielu kodowaniach (nie tylko ASCII) była niewygodna
         * i podatna na błędy – trzeba było ręcznie dekodować bajty na znaki.
         *
         * W Javie 1.1 dodano Reader/Writer – strumienie ZNAKOWE, które automatycznie
         * dbają o kodowanie (Unicode/UTF-16 wewnątrz JVM, konwersja na wybrane
         * kodowanie przy zapisie/odczycie).
         *
         * Zasada wyboru:
         * ✅ Dane TEKSTOWE (pliki .txt, .csv, .json)  → używaj Reader/Writer
         * ✅ Dane BINARNE (obrazy, pliki .zip, sieć)  → używaj InputStream/OutputStream
         * ❌ NIE czytaj pliku binarnego przez Reader – zniszczysz dane (błędne dekodowanie)
         * ❌ NIE czytaj pliku tekstowego przez InputStream, jeśli można użyć Reader
         *    (musiałbyś ręcznie dekodować bajty na znaki)
         */

        // Mini-przykład: most między bajtami a znakami (InputStreamReader)
        InputStream binarneZrodlo = new ByteArrayInputStream(bajty);
        java.io.Reader most = new java.io.InputStreamReader(binarneZrodlo, StandardCharsets.UTF_8);
        StringBuilder odczytany = new StringBuilder();
        int znak;
        while ((znak = most.read()) != -1) {
            odczytany.append((char) znak);
        }
        most.close();
        System.out.println("Odczytane przez most bajty→znaki: " + odczytany);

        // Mini-przykład: zapis do pamięci strumieniem bajtowym vs znakowym
        try (OutputStream bajtowyOut = new ByteArrayOutputStream()) {
            bajtowyOut.write(65); // zapisuje bajt = litera 'A'
            System.out.println("Zapisano bajt 65 → " + bajtowyOut);
        }

        try (StringWriter znakowyOut = new StringWriter()) {
            znakowyOut.write('A'); // zapisuje znak 'A'
            znakowyOut.write(" – witaj!");
            System.out.println("Zapisano znaki → " + znakowyOut);
        }

        // StringReader – Reader działający na Stringu w pamięci (bez pliku)
        try (StringReader sr = new StringReader("abc")) {
            int c;
            System.out.print("StringReader znak po znaku: ");
            while ((c = sr.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - I/O = wymiana danych między programem a otoczeniem (pliki, sieć, pamięć...)
         * - Strumień (stream I/O) = sekwencja danych płynąca w jedną stronę, wymaga close()
         * - Dwie równoległe hierarchie:
         *     BAJTY  → InputStream / OutputStream (dane binarne)
         *     ZNAKI  → Reader / Writer (dane tekstowe, ze świadomością kodowania)
         * - InputStreamReader / OutputStreamWriter to "mosty" łączące obie hierarchie
         * - Wybór hierarchii zależy od TYPU danych, nie od źródła (plik może być
         *   czytany i jako bajty, i jako znaki – zależy co w nim jest)
         * - Kolejne lekcje: FileReader/FileWriter, BufferedReader/BufferedWriter,
         *   BufferedInputStream/BufferedOutputStream – konkretne, praktyczne klasy
         *   zbudowane na tych dwóch hierarchiach.
         */
    }
}
