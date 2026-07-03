package com.example.javaquest._04_io.Lesson05_DataStreams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class _Lesson05_DataStreams {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 DATAINPUTSTREAM / DATAOUTPUTSTREAM – CO TO JEST?
         * ============================================================
         * DataOutputStream i DataInputStream to strumienie BINARNE, które
         * pozwalają zapisywać i odczytywać typy proste (int, double, boolean...)
         * w ich natywnej, binarnej reprezentacji – a nie jako tekst.
         *
         * ✅ Zalety:
         * - kompaktowy format (int to zawsze 4 bajty, nie zależnie od liczby cyfr)
         * - szybki zapis/odczyt – brak parsowania tekstu
         * - zachowuje dokładny typ (nie trzeba parseInt/parseDouble)
         *
         * ⚠️ WAŻNE – KOLEJNOŚĆ MA ZNACZENIE!
         * Plik binarny nie ma "etykiet" pól – trzeba czytać DOKŁADNIE w tej
         * samej kolejności i tymi samymi typami, w jakich zapisywano.
         * Jeśli zapiszesz writeInt(), writeDouble(), writeUTF() – musisz
         * odczytać readInt(), readDouble(), readUTF() w TEJ SAMEJ kolejności.
         *
         * DataOutputStream/DataInputStream to strumienie "opakowujące"
         * (decorator) – nakłada się je na inny strumień, najczęściej
         * FileOutputStream/FileInputStream.
         */

        File tempFile = File.createTempFile("lesson05_data", ".bin");
        System.out.println("Plik tymczasowy: " + tempFile.getAbsolutePath());

        /*
         * ============================================================
         * 🔹 ZAPIS TYPÓW PROSTYCH – writeInt/writeDouble/writeBoolean...
         * ============================================================
         * Najważniejsze metody DataOutputStream:
         * - writeInt(int)        → 4 bajty
         * - writeLong(long)      → 8 bajtów
         * - writeDouble(double)  → 8 bajtów
         * - writeFloat(float)    → 4 bajty
         * - writeBoolean(boolean)→ 1 bajt
         * - writeChar(char)      → 2 bajty
         * - writeUTF(String)     → długość (2 bajty) + tekst w UTF-8
         */

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(tempFile))) {
            dos.writeInt(42);
            dos.writeDouble(3.14159);
            dos.writeBoolean(true);
            dos.writeUTF("Ala ma kota");
            System.out.println("Zapisano: int=42, double=3.14159, boolean=true, UTF=\"Ala ma kota\"");
        }

        /*
         * ============================================================
         * 🔍 ODCZYT – MUSI BYĆ W TEJ SAMEJ KOLEJNOŚCI
         * ============================================================
         * readInt/readDouble/readBoolean/readUTF – odczytują dokładnie
         * tyle bajtów, ile odpowiadający zapis "wyprodukował".
         */

        try (DataInputStream dis = new DataInputStream(new FileInputStream(tempFile))) {
            int i = dis.readInt();
            double d = dis.readDouble();
            boolean b = dis.readBoolean();
            String s = dis.readUTF();

            System.out.println("\nOdczytano: int=" + i + ", double=" + d
                    + ", boolean=" + b + ", UTF=\"" + s + "\"");
            // Odczytano: int=42, double=3.14159, boolean=true, UTF="Ala ma kota"
        }

        tempFile.delete();

        /*
         * ============================================================
         * 🔹 PRZYKŁAD: ZAPIS REKORDU (KILKA PÓL RÓŻNYCH TYPÓW)
         * ============================================================
         * Częsty scenariusz: zapisujemy kilka "rekordów" (np. pracowników)
         * jeden po drugim do tego samego pliku binarnego, a potem
         * odczytujemy je w pętli, aż skończy się plik.
         */

        record Employee(int id, String name, double salary, boolean active) {}

        Employee[] employees = {
                new Employee(1, "Anna Kowalska", 5500.50, true),
                new Employee(2, "Jan Nowak", 4200.00, false),
                new Employee(3, "Piotr Zieliński", 6100.75, true)
        };

        File recordsFile = File.createTempFile("lesson05_employees", ".bin");
        System.out.println("\nPlik z rekordami: " + recordsFile.getAbsolutePath());

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(recordsFile))) {
            // Zapisujemy najpierw liczbę rekordów – ułatwia odczyt
            dos.writeInt(employees.length);
            for (Employee e : employees) {
                dos.writeInt(e.id());
                dos.writeUTF(e.name());
                dos.writeDouble(e.salary());
                dos.writeBoolean(e.active());
            }
        }

        System.out.println("\n--- Odczyt rekordów (znana liczba rekordów) ---");
        try (DataInputStream dis = new DataInputStream(new FileInputStream(recordsFile))) {
            int count = dis.readInt();
            for (int n = 0; n < count; n++) {
                int id = dis.readInt();
                String name = dis.readUTF();
                double salary = dis.readDouble();
                boolean active = dis.readBoolean();
                System.out.println("Employee{id=" + id + ", name=" + name
                        + ", salary=" + salary + ", active=" + active + "}");
            }
        }

        /*
         * ============================================================
         * 🔹 ALTERNATYWA: ODCZYT BEZ ZNANEJ LICZBY REKORDÓW (EOFException)
         * ============================================================
         * Jeśli nie zapisaliśmy liczby rekordów na początku, czytamy
         * "w pętli aż do końca pliku" – DataInputStream sygnalizuje koniec
         * przez wyjątek EOFException (nie przez wartość -1 jak w byte-stream).
         */

        System.out.println("\n--- Odczyt rekordów w pętli do EOFException ---");
        try (DataInputStream dis = new DataInputStream(new FileInputStream(recordsFile))) {
            int count = dis.readInt(); // pomijamy licznik zapisany na początku
            try {
                while (true) {
                    int id = dis.readInt();
                    String name = dis.readUTF();
                    double salary = dis.readDouble();
                    boolean active = dis.readBoolean();
                    System.out.println("  -> " + name + " (id=" + id + ", pensja=" + salary
                            + ", aktywny=" + active + ")");
                }
            } catch (EOFException eof) {
                System.out.println("Koniec pliku – wszystkie rekordy odczytane.");
            }
        } catch (IOException ex) {
            System.out.println("Błąd odczytu: " + ex);
        }

        // Sprzątanie plików tymczasowych
        recordsFile.delete();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DataOutputStream/DataInputStream zapisują/odczytują typy proste
         *   w formacie BINARNYM (nie tekstowym).
         * - writeInt/writeDouble/writeBoolean/writeLong/writeFloat/writeChar
         *   ↔ readInt/readDouble/readBoolean/readLong/readFloat/readChar
         * - writeUTF(String) / readUTF() – zapisuje String w formacie
         *   (długość + bajty UTF-8) – NIE to samo co zwykły UTF-8!
         * - KOLEJNOŚĆ ZAPISU = KOLEJNOŚĆ ODCZYTU – to kluczowa zasada.
         * - Częsty wzorzec: zapisz liczbę rekordów na początku, albo czytaj
         *   w pętli aż do EOFException.
         * - To strumienie "opakowujące" – zwykle na FileOutputStream/
         *   FileInputStream.
         */
    }
}
