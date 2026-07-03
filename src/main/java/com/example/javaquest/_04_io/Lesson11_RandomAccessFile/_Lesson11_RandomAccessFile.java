package com.example.javaquest._04_io.Lesson11_RandomAccessFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson11_RandomAccessFile {

    public static void main(String[] args) throws IOException {

        /*
         * ============================================================
         * 📦 RANDOMACCESSFILE – DOSTĘP LOSOWY DO PLIKU
         * ============================================================
         * Klasyczne strumienie (InputStream/OutputStream, Reader/Writer)
         * czytają/piszą SEKWENCYJNIE – od początku do końca, bez możliwości
         * "przeskoczenia" do dowolnego miejsca i powrotu.
         *
         * RandomAccessFile (java.io, od Javy 1.0!) pozwala na DOSTĘP LOSOWY:
         * - można przesunąć się (seek) w dowolne miejsce pliku,
         * - odczytać lub NADPISAĆ fragment pliku BEZ przepisywania reszty,
         * - czytać i pisać w ramach TEGO SAMEGO obiektu (nie trzeba osobno
         *   otwierać strumienia do odczytu i osobno do zapisu).
         *
         * To nie jest część NIO.2, ale wciąż bardzo przydatna, klasyczna
         * klasa – szczególnie do plików z rekordami o STAŁEJ DŁUGOŚCI
         * (np. proste bazy danych, pliki indeksów, formaty binarne).
         */

        Path tempFile = Files.createTempFile("javaquest_lesson11_", ".dat");
        System.out.println("Plik demo: " + tempFile);

        /*
         * ============================================================
         * 🔹 TRYBY OTWARCIA: "r" I "rw"
         * ============================================================
         * "r"  → tylko odczyt (próba zapisu rzuci IOException)
         * "rw" → odczyt i zapis (jeśli plik nie istnieje – zostanie utworzony)
         * (istnieją też "rws" i "rwd" – wymuszają natychmiastowy zapis
         *  metadanych/danych na dysk, rzadko używane)
         */

        // Rekord o STAŁEJ DŁUGOŚCI: 20 znaków na imię (UTF-8, dopełnione spacjami)
        // + 4 bajty na wiek (int) = 24 bajty na rekord.
        final int NAME_BYTES = 20;
        final int RECORD_SIZE = NAME_BYTES + Integer.BYTES; // 24 bajty

        String[] names = {"Anna", "Bartek", "Celina", "Dawid"};
        int[] ages = {25, 31, 19, 42};

        System.out.println("\n=== ZAPIS REKORDÓW (tryb \"rw\") ===");
        try (RandomAccessFile raf = new RandomAccessFile(tempFile.toFile(), "rw")) {
            for (int i = 0; i < names.length; i++) {
                writeRecord(raf, names[i], ages[i], NAME_BYTES);
                System.out.println("Zapisano rekord " + i + ": " + names[i] + ", " + ages[i]
                        + " (pozycja po zapisie: " + raf.getFilePointer() + ")");
            }
            System.out.println("Rozmiar pliku: " + raf.length() + " B (4 rekordy x " + RECORD_SIZE + " B)");
        }

        /*
         * ============================================================
         * 🔍 SEEK() I GETFILEPOINTER() – NAWIGACJA W PLIKU
         * ============================================================
         * seek(pos)         → ustawia wskaźnik pliku na bajt o indeksie pos (od 0)
         * getFilePointer()  → zwraca AKTUALNĄ pozycję wskaźnika
         *
         * Żeby odczytać N-ty rekord bezpośrednio (bez czytania poprzednich),
         * przeskakujemy do: N * RECORD_SIZE
         */

        System.out.println("\n=== ODCZYT WYBRANEGO REKORDU (bez czytania całości) ===");
        try (RandomAccessFile raf = new RandomAccessFile(tempFile.toFile(), "r")) {
            int targetIndex = 2; // chcemy od razu rekord "Celina"
            long position = (long) targetIndex * RECORD_SIZE;

            System.out.println("Przed seek, getFilePointer(): " + raf.getFilePointer()); // 0
            raf.seek(position);
            System.out.println("Po seek(" + position + "), getFilePointer(): " + raf.getFilePointer());

            Record record = readRecord(raf, NAME_BYTES);
            System.out.println("Odczytany rekord " + targetIndex + ": " + record.name() + ", " + record.age());
        }

        /*
         * ============================================================
         * 🔹 NADPISANIE N-TEGO REKORDU – BEZ PRZEPISYWANIA CAŁEGO PLIKU
         * ============================================================
         * To jest kluczowa zaleta RandomAccessFile: możemy zmienić JEDEN
         * rekord "w środku" pliku, nie ruszając pozostałych bajtów.
         * Warunek: nowy rekord MUSI mieć dokładnie ten sam rozmiar
         * (stąd stała długość pól – dopełnianie spacjami).
         */

        System.out.println("\n=== NADPISANIE REKORDU NR 1 (Bartek -> Zenon, 31 -> 99) ===");
        try (RandomAccessFile raf = new RandomAccessFile(tempFile.toFile(), "rw")) {
            int targetIndex = 1;
            raf.seek((long) targetIndex * RECORD_SIZE);
            writeRecord(raf, "Zenon", 99, NAME_BYTES);
        }

        System.out.println("\n=== WSZYSTKIE REKORDY PO MODYFIKACJI ===");
        try (RandomAccessFile raf = new RandomAccessFile(tempFile.toFile(), "r")) {
            long totalRecords = raf.length() / RECORD_SIZE;
            for (int i = 0; i < totalRecords; i++) {
                raf.seek((long) i * RECORD_SIZE);
                Record record = readRecord(raf, NAME_BYTES);
                System.out.println("Rekord " + i + ": " + record.name() + ", " + record.age());
            }
        }
        // Oczekiwany wynik: Anna 25, Zenon 99, Celina 19, Dawid 42
        // – tylko rekord 1 się zmienił, reszta pliku pozostała nietknięta!

        /*
         * ============================================================
         * ⚠️ INNE PRZYDATNE METODY
         * ============================================================
         * length()      → aktualny rozmiar pliku w bajtach
         * setLength(n)  → obcina lub wydłuża plik do n bajtów
         * skipBytes(n)  → przesuwa wskaźnik o n bajtów do przodu
         * readInt/readUTF/writeInt/writeUTF... → odczyt/zapis typów prymitywnych
         */

        try (RandomAccessFile raf = new RandomAccessFile(tempFile.toFile(), "rw")) {
            System.out.println("\nDługość przed setLength: " + raf.length());
            raf.setLength(RECORD_SIZE); // obcinamy do JEDNEGO rekordu
            System.out.println("Długość po setLength(" + RECORD_SIZE + "): " + raf.length());
        }

        // Sprzątanie po demo
        Files.deleteIfExists(tempFile);
        System.out.println("\nPlik tymczasowy usunięty, istnieje? " + Files.exists(tempFile));

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * RandomAccessFile("r"/"rw") → jeden obiekt do odczytu i zapisu
         * seek(pos)          → skok do dowolnego bajtu w pliku
         * getFilePointer()   → aktualna pozycja wskaźnika
         * length() / setLength(n) → rozmiar pliku, obcinanie/wydłużanie
         * Kluczowe zastosowanie: pliki z rekordami o STAŁEJ DŁUGOŚCI –
         * można odczytać/nadpisać N-ty rekord w czasie O(1),
         * bez wczytywania i przepisywania całego pliku.
         * To klasyczne API (java.io), nie NIO.2 – ale bywa niezastąpione
         * przy prostych formatach binarnych i plikach indeksowych.
         */
    }

    // Rekord pomocniczy do zwracania odczytanych danych
    private record Record(String name, int age) {}

    // Zapisuje jeden rekord: imię dopełnione spacjami do stałej długości + wiek jako int
    private static void writeRecord(RandomAccessFile raf, String name, int age, int nameBytes) throws IOException {
        StringBuilder padded = new StringBuilder(name);
        while (padded.length() < nameBytes) {
            padded.append(' ');
        }
        raf.write(padded.substring(0, nameBytes).getBytes(StandardCharsets.UTF_8));
        raf.writeInt(age);
    }

    // Odczytuje jeden rekord z aktualnej pozycji wskaźnika pliku
    private static Record readRecord(RandomAccessFile raf, int nameBytes) throws IOException {
        byte[] nameBuffer = new byte[nameBytes];
        raf.readFully(nameBuffer);
        String name = new String(nameBuffer, StandardCharsets.UTF_8).strip();
        int age = raf.readInt();
        return new Record(name, age);
    }
}
