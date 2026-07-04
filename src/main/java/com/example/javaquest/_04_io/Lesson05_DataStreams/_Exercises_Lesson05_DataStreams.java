package com.example.javaquest._04_io.Lesson05_DataStreams;

public class _Exercises_Lesson05_DataStreams {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_WriteSingleInt {
        /*
         * 🧪 Zadanie 1:
         * Zapisz do pliku binarnego "cwiczenie01.bin" (w katalogu tymczasowym
         * System.getProperty("java.io.tmpdir")) pojedynczą liczbę int 100
         * za pomocą DataOutputStream.writeInt(). Odczytaj tę liczbę
         * DataInputStream.readInt() i wypisz ją na konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteReadDouble {
        /*
         * 🧪 Zadanie 2:
         * Zapisz do pliku "cwiczenie02.bin" cenę produktu jako double 99.99
         * metodą writeDouble(), a następnie odczytaj ją readDouble() i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriteReadBoolean {
        /*
         * 🧪 Zadanie 3:
         * Zapisz do pliku "cwiczenie03.bin" wartość boolean true (czy
         * użytkownik jest aktywny) metodą writeBoolean(), odczytaj ją
         * readBoolean() i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteReadUTFString {
        /*
         * 🧪 Zadanie 4:
         * Zapisz do pliku "cwiczenie04.bin" napis "Ala ma kota" metodą
         * writeUTF(), odczytaj go readUTF() i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteMultipleTypes {
        /*
         * 🧪 Zadanie 5:
         * W jednym pliku binarnym zapisz kolejno: int (wiek=30), double
         * (wzrost=175.5), String (imię="Kasia"), boolean (aktywny=true).
         * Odczytaj wszystkie wartości w TEJ SAMEJ kolejności i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WrongOrderBug {
        /*
         * 🧪 Zadanie 6:
         * Zapisz do pliku najpierw int (id=7), potem String ("Test").
         * Spróbuj odczytać w ODWROTNEJ kolejności: najpierw readUTF(),
         * potem readInt(). Zaobserwuj i wypisz co się stanie (wyjątek albo
         * "śmieciowe" dane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WriteLongAndFloat {
        /*
         * 🧪 Zadanie 7:
         * Zapisz do pliku long (liczba mieszkańców miasta = 1_800_000L)
         * i float (temperatura = 21.5f) w tej kolejności, a następnie
         * odczytaj je readLong()/readFloat() w tej samej kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteChar {
        /*
         * 🧪 Zadanie 8:
         * Zapisz do pliku trzy znaki oceny: 'A', 'B', 'C' metodą writeChar()
         * (po kolei), następnie odczytaj wszystkie trzy znaki readChar()
         * i wypisz je.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FileSizeCheck {
        /*
         * 🧪 Zadanie 9:
         * Zapisz do pliku int, double i boolean (w tej kolejności).
         * Sprawdź rozmiar pliku metodą file.length() – powinien wynosić
         * dokładnie 4 + 8 + 1 = 13 bajtów. Wypisz rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LoopWriteInts {
        /*
         * 🧪 Zadanie 10:
         * Zapisz do pliku binarnego 10 kolejnych liczb int (1..10) w pętli
         * writeInt(). Odczytaj je w pętli readInt(), zsumuj i wypisz sumę
         * (powinno być 55).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SingleRecordReadWrite {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj record Produkt(int id, String nazwa, double cena).
         * Zapisz jeden rekord Produkt(1, "Klawiatura", 149.99) do pliku
         * binarnego, odczytaj go z powrotem i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RecordArrayWithCount {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj record Student(int id, String imie, double ocena).
         * Zapisz tablicę 5 rekordów Student – najpierw zapisz liczbę
         * rekordów (writeInt), potem każdy rekord. Odczytaj wszystkie
         * i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RecordsWithoutCountEOF {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj record Ksiazka(String tytul, String autor, int rok).
         * Zapisz 4 rekordy Ksiazka BEZ zapisywania liczby na początku pliku.
         * Odczytaj je w pętli while(true), łapiąc EOFException na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TextVsBinarySize {
        /*
         * 🧪 Zadanie 14:
         * Zapisz liczby int od 1 do 100 najpierw jako tekst (FileWriter,
         * liczby oddzielone spacją) do pliku "cwiczenie14_tekst.txt",
         * a potem te same liczby jako dane binarne (writeInt w pętli)
         * do pliku "cwiczenie14_binarny.bin". Porównaj rozmiary obu plików
         * (file.length()) i wypisz różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NestedRecord {
        /*
         * 🧪 Zadanie 15:
         * Zapisz strukturę Zamowienie: int idZamowienia, String klient,
         * int liczbaProduktow, a następnie tyle par (String nazwaProduktu,
         * double cena), ile wynosi liczbaProduktow (np. 3 produkty).
         * Odczytaj całą strukturę z powrotem i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AppendRecords {
        /*
         * 🧪 Zadanie 16:
         * Zapisz do pliku 3 rekordy Pracownik(int id, String imie, double
         * pensja). Następnie otwórz ten sam plik w trybie dopisywania
         * (new FileOutputStream(file, true)) i dopisz na końcu 4-ty rekord,
         * bez naruszania poprzednich. Odczytaj wszystkie 4 rekordy na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SumSalariesFromFile {
        /*
         * 🧪 Zadanie 17:
         * Zapisz do pliku 5 rekordów Pracownik(String imie, double pensja)
         * z różnymi pensjami. Odczytaj je wszystkie i oblicz sumę oraz
         * średnią pensję, wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FindMaxRecord {
        /*
         * 🧪 Zadanie 18:
         * Zapisz do pliku 6 rekordów Produkt(String nazwa, double cena).
         * Odczytaj wszystkie rekordy i znajdź produkt o najwyższej cenie,
         * wypisz jego nazwę i cenę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FilterRecordsWhileReading {
        /*
         * 🧪 Zadanie 19:
         * Zapisz do pliku 8 rekordów Osoba(String imie, int wiek) z różnym
         * wiekiem (część poniżej, część powyżej 18). Podczas odczytu wypisz
         * tylko te osoby, które mają wiek >= 18.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CountRecordsUnknownLength {
        /*
         * 🧪 Zadanie 20:
         * Zapisz losową liczbę (od 3 do 7) rekordów Log(int kod, String
         * wiadomosc) BEZ zapisywania licznika na początku pliku. Odczytaj
         * je w pętli do EOFException i policz, ile rekordów faktycznie
         * odczytano – wypisz tę liczbę.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BinaryDatabaseCRUD {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj prostą "bazę" binarną klientów (record Klient(int id,
         * String imie, double saldo)) z operacjami: zapisz całą listę do
         * pliku "klienci.bin", wczytaj całą listę z pliku, dodaj nowego
         * klienta (wczytaj listę + dodaj + zapisz od nowa), usuń klienta
         * po id (wczytaj + odfiltruj + zapisz od nowa). Przetestuj wszystkie
         * operacje po kolei.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MergeTwoBinaryFiles {
        /*
         * 🧪 Zadanie 22:
         * Utwórz dwa osobne pliki binarne z rekordami Uczen(String imie,
         * double ocena) – po 3 rekordy w każdym ("uczniowie_a.bin" i
         * "uczniowie_b.bin"). Scal je w jeden plik wynikowy
         * "uczniowie_wszyscy.bin" zawierający wszystkie rekordy z obu
         * plików, odczytaj scalony plik i wypisz jego zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_UpdateRecordInPlace {
        /*
         * 🧪 Zadanie 23:
         * Zapisz 5 rekordów Produkt(int id, String nazwa, double cena)
         * do pliku i zaktualizuj cenę produktu o konkretnym id (np. id=3).
         * Ponieważ writeUTF ma zmienną długość, zaimplementuj aktualizację
         * przez: wczytanie całej listy do pamięci, zmianę pola cena
         * w odpowiednim rekordzie, i ponowny zapis CAŁEJ listy do pliku
         * od zera. Zweryfikuj zmianę przez ponowny odczyt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HeaderMagicNumber {
        /*
         * 🧪 Zadanie 24:
         * Zaprojektuj własny prosty format binarny z nagłówkiem: na
         * początku pliku zapisz magiczną liczbę int 0xCAFE oraz wersję
         * formatu int (1), a potem dane – listę 5 ocen (double). Przy
         * odczycie zweryfikuj nagłówek PRZED odczytaniem danych – jeśli
         * magic number się nie zgadza, wypisz komunikat błędu i przerwij
         * odczyt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_Statistics {
        /*
         * 🧪 Zadanie 25:
         * Zapisz do pliku binarnego 20 losowych liczb int z zakresu 0-100
         * (wyniki testu). Odczytaj wszystkie i oblicz: minimum, maksimum,
         * średnią oraz liczbę wyników powyżej 50. Wypisz wszystkie
         * statystyki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SearchByIdInBinaryFile {
        /*
         * 🧪 Zadanie 26:
         * Zapisz do pliku 10 rekordów Towar(int kodEAN, String nazwa,
         * double cena). Napisz metodę wyszukującą towar po konkretnym
         * kodEAN, czytającą plik SEKWENCYJNIE od początku aż do znalezienia
         * pasującego rekordu lub końca pliku (bez wczytywania wszystkiego
         * do listy w pamięci). Przetestuj wyszukiwanie istniejącego
         * i nieistniejącego kodu EAN.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConvertCsvToBinary {
        /*
         * 🧪 Zadanie 27:
         * Zapisz dane osób w formacie tekstowym CSV (imie,wiek,miasto)
         * do pliku "osoby.csv" (3-4 linie, np. "Jan,25,Warszawa"). Napisz
         * konwerter wczytujący ten CSV linia po linii, parsujący pola
         * i zapisujący je jako rekordy binarne (writeUTF/writeInt/writeUTF)
         * do nowego pliku "osoby.bin". Zweryfikuj wynik odczytując plik
         * binarny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_VersionedFormatMigration {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj dwie wersje formatu binarnego rekordu osoby: wersja 1
         * zapisuje (imie:String, wiek:int), wersja 2 dodaje pole
         * (email:String). Zapisz na początku każdego pliku numer wersji
         * (writeInt(1) lub writeInt(2)), a potem dane w odpowiednim
         * formacie. Napisz uniwersalną metodę odczytu, która sprawdza numer
         * wersji i odpowiednio parsuje rekord (dla wersji 1 ustawia
         * email na "brak").
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BinaryLinkedRecords {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj record Task(int id, String opis, boolean wykonane,
         * int priorytet). Zapisz do pliku binarnego 6 zadań. Odczytaj
         * wszystkie, a następnie wypisz tylko te NIEWYKONANE, posortowane
         * malejąco według priorytetu (sortowanie wykonaj w pamięci po
         * odczycie, np. listą + Comparator).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_TransactionLog {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj log transakcji bankowych jako plik binarny: każda
         * transakcja to (long timestamp, String typ – "WPLATA" lub
         * "WYPLATA", double kwota). Dopisuj kolejne transakcje w trybie
         * append (5 transakcji, saldo startowe 1000.0, np. WPLATA 200,
         * WYPLATA 50, ...). Odczytaj cały log i oblicz końcowe saldo
         * konta (WPLATA dodaje, WYPLATA odejmuje) oraz wypisz historię
         * transakcji w kolejności chronologicznej.
         */
        public static void main(String[] args) { }
    }
}
