package com.example.javaquest._04_io.Lesson11_RandomAccessFile;

public class _Exercises_Lesson11_RandomAccessFile {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_OpenModes {
        /*
         * 🧪 Zadanie 1:
         * Utwórz plik tymczasowy (Files.createTempFile()). Otwórz go w trybie
         * "r" i spróbuj zapisać przez writeInt() – złap wyjątek IOException
         * i wypisz komunikat. Następnie otwórz w trybie "rw" i zapisz
         * poprawnie jedną liczbę int.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteReadInt {
        /*
         * 🧪 Zadanie 2:
         * Otwórz RandomAccessFile w trybie "rw". Zapisz kolejno 5 liczb int:
         * {10, 20, 30, 40, 50} metodą writeInt(). Zamknij i otwórz ponownie
         * w trybie "r", odczytaj wszystkie przez readInt() w pętli i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SeekBasic {
        /*
         * 🧪 Zadanie 3:
         * Zapisz 5 liczb int (po 4 bajty każda). Użyj seek(8), żeby przeskoczyć
         * bezpośrednio do trzeciej liczby (indeks 2) i odczytaj ją przez
         * readInt() bez czytania poprzednich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetFilePointerAfterRead {
        /*
         * 🧪 Zadanie 4:
         * Zapisz 3 liczby int. Otwórz do odczytu i po każdym readInt()
         * wypisz getFilePointer(), żeby zaobserwować, jak wskaźnik przesuwa
         * się o 4 bajty po każdym odczycie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_LengthOfFile {
        /*
         * 🧪 Zadanie 5:
         * Zapisz 10 liczb int do pliku. Otwórz RandomAccessFile i wypisz
         * length() – powinno wynosić 40 bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SetLengthTruncate {
        /*
         * 🧪 Zadanie 6:
         * Zapisz 5 liczb int (20 bajtów). Użyj setLength(8), żeby obciąć
         * plik do dwóch pierwszych liczb. Wypisz length() przed i po,
         * a następnie odczytaj pozostałe liczby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SkipBytes {
        /*
         * 🧪 Zadanie 7:
         * Zapisz 4 liczby int. Otwórz do odczytu, użyj skipBytes(8), żeby
         * pominąć pierwsze dwie liczby, i odczytaj trzecią przez readInt().
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteReadUTF {
        /*
         * 🧪 Zadanie 8:
         * Zapisz String "Ala ma kota" metodą writeUTF(). Odczytaj go z
         * powrotem metodą readUTF() i wypisz. Sprawdź getFilePointer()
         * przed i po operacji writeUTF (zwróć uwagę, że writeUTF zapisuje
         * dodatkowe 2 bajty na długość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FixedRecordSingle {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj rekord o stałej długości: 15 znaków na nazwę produktu
         * (dopełnione spacjami, UTF-8) + 4 bajty na cenę (int, w groszach).
         * Zapisz jeden rekord: "Chleb", 450. Odczytaj go z powrotem i wypisz
         * nazwę (po strip()) oraz cenę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ReadFullyVsRead {
        /*
         * 🧪 Zadanie 10:
         * Zapisz tablicę 10 bajtów. Porównaj działanie read(byte[]) (może
         * zwrócić mniej bajtów niż długość tablicy) z readFully(byte[])
         * (zawsze wypełnia całą tablicę albo rzuca EOFException). Wypisz
         * liczbę odczytanych bajtów w obu przypadkach.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultipleRecordsSeek {
        /*
         * 🧪 Zadanie 11:
         * Używając formatu rekordu z Zadania 9 (15 znaków nazwy + 4 bajty
         * ceny), zapisz 6 produktów. Odczytaj bezpośrednio produkt o
         * indeksie 4, przeskakując seek(4 * RECORD_SIZE) bez czytania
         * poprzednich rekordów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_OverwriteRecord {
        /*
         * 🧪 Zadanie 12:
         * Zapisz 5 rekordów produktów (jak w Zadaniu 9). Nadpisz rekord
         * o indeksie 2 nowymi danymi (inna nazwa, inna cena) bez ruszania
         * pozostałych rekordów. Odczytaj wszystkie 5 rekordów i zweryfikuj,
         * że tylko rekord 2 się zmienił.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AppendRecord {
        /*
         * 🧪 Zadanie 13:
         * Zapisz 3 rekordy produktów. Dopisz nowy, czwarty rekord na końcu
         * pliku, przeskakując seek(raf.length()) zamiast liczyć pozycję
         * ręcznie. Zweryfikuj liczbę rekordów po dopisaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DeleteRecordBySwap {
        /*
         * 🧪 Zadanie 14:
         * Zapisz 5 rekordów produktów. "Usuń" rekord o indeksie 1, kopiując
         * na jego miejsce ostatni rekord (indeks 4), a następnie skracając
         * plik o jeden RECORD_SIZE metodą setLength(). Zweryfikuj wynikową
         * listę rekordów (powinno być 4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SearchRecordByField {
        /*
         * 🧪 Zadanie 15:
         * Zapisz 6 rekordów produktów o różnych nazwach. Napisz metodę
         * findRecordIndexByName(RandomAccessFile raf, String name), która
         * liniowo przeszukuje wszystkie rekordy (seek + readRecord w pętli)
         * i zwraca indeks pierwszego pasującego lub -1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UpdateFieldInRecord {
        /*
         * 🧪 Zadanie 16:
         * Dla rekordu z Zadania 9 zaktualizuj TYLKO pole ceny (bez dotykania
         * nazwy) w rekordzie o indeksie 3: seek do pozycji
         * indeks*RECORD_SIZE + NAME_BYTES (offset pola ceny w rekordzie),
         * writeInt(nowaCena). Zweryfikuj, że nazwa się nie zmieniła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CountRecords {
        /*
         * 🧪 Zadanie 17:
         * Zapisz nieznaną z góry liczbę rekordów (np. 7) do pliku. Napisz
         * metodę countRecords(RandomAccessFile raf, int recordSize), która
         * oblicza liczbę rekordów jako raf.length() / recordSize. Wypisz
         * wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ReverseReadRecords {
        /*
         * 🧪 Zadanie 18:
         * Zapisz 5 rekordów produktów. Odczytaj je w kolejności odwrotnej
         * (od ostatniego do pierwszego), używając seek() do obliczonej
         * pozycji każdego rekordu w pętli malejącej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_InsertRecordMiddle {
        /*
         * 🧪 Zadanie 19:
         * Zapisz 4 rekordy produktów. Wstaw nowy rekord na pozycję indeksu 2
         * (między istniejącym 1 i 2): przesuń wszystkie rekordy od indeksu 2
         * w górę o jeden RECORD_SIZE (czytając od końca i zapisując dalej),
         * a potem zapisz nowy rekord na zwolnionym miejscu. Zweryfikuj
         * kolejność wszystkich 5 rekordów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BinarySearchRecords {
        /*
         * 🧪 Zadanie 20:
         * Zapisz 8 rekordów posortowanych rosnąco po cenie. Napisz metodę
         * binarySearchByPrice(RandomAccessFile raf, int targetPrice), która
         * wykonuje wyszukiwanie binarne, przeskakując bezpośrednio (seek)
         * do środkowego rekordu w każdej iteracji zamiast czytać sekwencyjnie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SimpleIndexFile {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj dwa pliki: plik danych (rekordy produktów, jak w Zadaniu 9)
         * oraz osobny plik indeksu mapujący klucz (nazwę) na pozycję (long)
         * rekordu w pliku danych. Napisz metodę lookup(String name), która
         * najpierw szuka pozycji w indeksie, a potem od razu seek()-uje do
         * właściwego miejsca w pliku danych (bez skanowania liniowego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FixedSizeDatabase {
        /*
         * 🧪 Zadanie 22:
         * Napisz klasę SimpleFixedDb opartą na RandomAccessFile z rekordami
         * o stałym rozmiarze, z metodami: insert(name, price), update(index,
         * name, price), delete(index) (np. przez wyzerowanie/oznaczenie),
         * find(name). Zademonstruj pełny cykl: dodaj kilka rekordów,
         * zaktualizuj jeden, usuń jeden, wyszukaj po nazwie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FreeListRecycling {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz bazę z Zadania 22 o listę wolnych miejsc (free list) –
         * gdy rekord jest usuwany, jego pozycja trafia na listę wolnych
         * miejsc zamiast skracać plik. Przy kolejnym insert() najpierw
         * sprawdź, czy jest wolna pozycja do ponownego użycia, zanim
         * dopiszesz na końcu pliku. Zademonstruj oszczędność miejsca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ConcurrentAppendSimulation {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj dwa niezależne uchwyty RandomAccessFile do TEGO SAMEGO
         * pliku – jeden zapisuje rekordy na pozycjach parzystych indeksów,
         * drugi na nieparzystych (z góry zarezerwowany rozmiar pliku przez
         * setLength()). Po zakończeniu odczytaj cały plik jednym uchwytem
         * i zweryfikuj poprawność wszystkich rekordów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_VariableToFixedConverter {
        /*
         * 🧪 Zadanie 25:
         * Utwórz plik tekstowy z kilkoma liniami o różnej długości (przez
         * Files.writeString()). Napisz metodę convertToFixedRecords(Path
         * textFile, Path binaryFile, int fixedLength), która wczytuje linie
         * i zapisuje każdą jako rekord o stałej długości fixedLength
         * (przycinając dłuższe, dopełniając krótsze spacjami) do pliku
         * binarnego przez RandomAccessFile.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RecordChecksum {
        /*
         * 🧪 Zadanie 26:
         * Rozszerz format rekordu produktu o dodatkowy bajt sumy kontrolnej
         * (np. prosta suma XOR bajtów nazwy i ceny). Przy zapisie oblicz
         * i zapisz checksum, przy odczycie przelicz go ponownie i porównaj
         * z zapisanym. Ręcznie "zepsuj" jeden bajt w pliku (np. przez
         * RandomAccessFile.write() na konkretnej pozycji) i zweryfikuj,
         * że program wykrywa niezgodność sumy kontrolnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PagedRecordReader {
        /*
         * 🧪 Zadanie 27:
         * Zapisz 25 rekordów produktów. Napisz metodę readPage(RandomAccessFile
         * raf, int pageNumber, int pageSize), która zwraca listę rekordów
         * należących do danej "strony" (np. strona 2 przy pageSize=10 to
         * rekordy o indeksach 10-19), przeskakując od razu (seek) do
         * początku strony bez czytania wcześniejszych rekordów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CompactDeletedRecords {
        /*
         * 🧪 Zadanie 28:
         * Rozszerz rekord produktu o 1-bajtową flagę "usunięty"
         * (0 = aktywny, 1 = usunięty). Zapisz 6 rekordów, oznacz 2 z nich
         * jako usunięte (flaga=1). Napisz metodę compact(Path file), która
         * tworzy nowy plik zawierający TYLKO aktywne rekordy (bez dziur),
         * zachowując ich oryginalną kolejność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiFieldRecordCRUD {
        /*
         * 🧪 Zadanie 29:
         * Zdefiniuj rozszerzony rekord: 15 znaków nazwy + 4 bajty wieku (int)
         * + 8 bajtów pensji (double). Zaimplementuj pełne CRUD: create
         * (dopisanie), read (po indeksie), update (nadpisanie całego rekordu
         * lub pojedynczego pola przez seek na konkretny offset), delete
         * (przez flagę lub swap-and-truncate z Zadania 14). Zademonstruj
         * wszystkie operacje na przykładowych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_SimpleKeyValueStore {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj trwały magazyn klucz-wartość (Key-Value Store) na bazie
         * RandomAccessFile, gdzie klucz to String o stałej długości (np. 10
         * znaków) a wartość to String o stałej długości (np. 50 znaków).
         * Zaimplementuj metody put(key, value), get(key), delete(key),
         * z ponownym wykorzystaniem zwolnionych slotów (free list jak w
         * Zadaniu 23). Zademonstruj działanie na kilku wpisach, w tym
         * nadpisanie istniejącego klucza i usunięcie wpisu.
         */
        public static void main(String[] args) { }
    }
}
