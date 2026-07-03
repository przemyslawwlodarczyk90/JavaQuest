package com.example.javaquest._04_io.Lesson23_CSV;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class _Lesson23_CSV {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 CSV – COMMA-SEPARATED VALUES
         * ============================================================
         * CSV to prosty format tekstowy do przechowywania danych tabelarycznych:
         * - każda LINIA to jeden wiersz (rekord)
         * - wartości w wierszu rozdzielone SEPARATOREM (najczęściej przecinek ",",
         *   czasem średnik ";" – zwłaszcza w polskim Excelu)
         * - pierwsza linia to zwykle NAGŁÓWEK (nazwy kolumn)
         *
         * Przykład:
         * name,age,city
         * Jan,30,Warszawa
         * Anna,25,Krakow
         *
         * CSV nie ma formalnej specyfikacji tak rygorystycznej jak JSON – stąd
         * biorą się problemy przy ręcznym parsowaniu (patrz sekcja ⚠️ niżej).
         */

        Path csvFile = Files.createTempFile("people", ".csv");

        /*
         * ============================================================
         * 🔹 ZAPIS OBIEKTÓW DO CSV
         * ============================================================
         */

        List<Person> people = List.of(
                new Person("Jan Kowalski", 30, "Warszawa"),
                new Person("Anna Nowak", 25, "Krakow"),
                new Person("Piotr Zielinski", 42, "Gdansk")
        );

        try (BufferedWriter writer = Files.newBufferedWriter(csvFile)) {
            writer.write("name,age,city");
            writer.newLine();
            for (Person p : people) {
                writer.write(p.name() + "," + p.age() + "," + p.city());
                writer.newLine();
            }
        }

        System.out.println("Zapisano CSV: " + csvFile);
        System.out.println("--- zawartość pliku ---");
        System.out.println(Files.readString(csvFile));

        /*
         * ============================================================
         * 🔍 ODCZYT CSV LINIA PO LINII
         * ============================================================
         * 1. wczytujemy nagłówek osobno (żeby go pominąć albo wykorzystać
         *    do nazwania kolumn)
         * 2. każdą kolejną linię dzielimy metodą split(",") na tablicę pól
         */

        List<String[]> rawRows = new ArrayList<>();
        String header;

        try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
            header = reader.readLine(); // pierwsza linia = nagłówek
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue; // pomijamy puste linie
                rawRows.add(line.split(","));
            }
        }

        System.out.println("\nNagłówek: " + header);
        System.out.println("Liczba wczytanych wierszy: " + rawRows.size());

        /*
         * ============================================================
         * 🔹 MAPOWANIE List<String[]> -> List<Person>
         * ============================================================
         */

        List<Person> parsedPeople = new ArrayList<>();
        for (String[] row : rawRows) {
            String name = row[0];
            int age = Integer.parseInt(row[1]);
            String city = row[2];
            parsedPeople.add(new Person(name, age, city));
        }

        System.out.println("\nObiekty odtworzone z CSV:");
        parsedPeople.forEach(p -> System.out.println("  " + p));

        /*
         * ============================================================
         * ⚠️ PUŁAPKI RĘCZNEGO PARSOWANIA CSV
         * ============================================================
         * Prosty split(",") działa dla prostych danych, ale ŁAMIE SIĘ gdy:
         *
         * 1. Wartość zawiera przecinek, np.:
         *      "Kowalski, Jan",30,Warszawa
         *    split(",") potnie "Kowalski, Jan" na dwa osobne pola!
         *
         * 2. Wartość jest w cudzysłowach z escapowanym cudzysłowem w środku:
         *      "Jan ""Wielki"" Kowalski",30,Warszawa
         *
         * 3. Wartość zawiera znak nowej linii wewnątrz cudzysłowów
         *    (wielolinijkowa komórka – wtedy readLine() też nie wystarczy!).
         *
         * Poprawna obsługa tych przypadków wymaga pełnego parsera zgodnego
         * ze specyfikacją CSV (RFC 4180) – w praktyce używa się do tego
         * sprawdzonych bibliotek, np.:
         * - Apache Commons CSV (org.apache.commons:commons-csv)
         * - OpenCSV (com.opencsv:opencsv)
         * Ręczny split() wystarczy tylko do prostych, kontrolowanych danych
         * (np. własny eksport, bez przecinków w treści).
         */

        String trickyLine = "\"Kowalski, Jan\",30,Warszawa";
        System.out.println("\nPrzykład problemu – naiwny split(\",\"):");
        System.out.println("Wejście: " + trickyLine);
        System.out.println("Wynik split: " + java.util.Arrays.toString(trickyLine.split(",")));
        // ["Kowalski, "Jan"", "30", "Warszawa"] – 4 elementy zamiast 3! Błąd.

        // Sprzątanie
        Files.deleteIfExists(csvFile);
        System.out.println("\nPlik CSV usunięty: " + !Files.exists(csvFile));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - CSV = tekst, wiersze = linie, kolumny rozdzielone separatorem (najczęściej ",")
         * - Zapis: BufferedWriter + newLine(), łączenie pól separatorem
         * - Odczyt: BufferedReader + readLine() w pętli, pierwsza linia = nagłówek
         * - Parsowanie wiersza: line.split(",") → String[] → mapowanie na obiekt
         * - ⚠️ Ręczny split() zawodzi przy przecinkach/cudzysłowach w danych –
         *   w produkcyjnym kodzie użyj Apache Commons CSV lub OpenCSV.
         */
    }

    record Person(String name, int age, String city) {
    }
}
