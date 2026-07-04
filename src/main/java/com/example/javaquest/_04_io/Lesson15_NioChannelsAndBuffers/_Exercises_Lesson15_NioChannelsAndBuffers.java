package com.example.javaquest._04_io.Lesson15_NioChannelsAndBuffers;

public class _Exercises_Lesson15_NioChannelsAndBuffers {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AllocateAndPut {
        /*
         * 🧪 Zadanie 1:
         * Stwórz ByteBuffer.allocate(32). Wypisz position/limit/capacity.
         * Wykonaj put() z bajtami napisu "Hello Buffer" i ponownie wypisz
         * position/limit/capacity.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FlipAndGet {
        /*
         * 🧪 Zadanie 2:
         * Na buforze z Zadania 1 wywołaj flip(), wypisz position/limit,
         * następnie odczytaj zawartość get(byte[]) o rozmiarze remaining()
         * i wypisz odtworzony String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_Rewind {
        /*
         * 🧪 Zadanie 3:
         * Po jednokrotnym odczycie bufora (jak w Zadaniu 2) wywołaj rewind()
         * i odczytaj zawartość PONOWNIE. Sprawdź, że wynik jest identyczny
         * jak za pierwszym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_Clear {
        /*
         * 🧪 Zadanie 4:
         * Po odczycie bufora wywołaj clear(). Wypisz position/limit po
         * clear(). Wykonaj nowy put() z innym tekstem ("Nowe dane") i
         * sprawdź że bufor przyjmuje nowe dane od pozycji 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WrapExistingArray {
        /*
         * 🧪 Zadanie 5:
         * Stwórz byte[] z "Wrapped data".getBytes() i owiń go przez
         * ByteBuffer.wrap(). Porównaj position/limit/capacity od razu po
         * wrap() z buforem stworzonym przez allocate()+put() z Zadania 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteFileChannel {
        /*
         * 🧪 Zadanie 6:
         * Utwórz plik tymczasowy. Otwórz FileChannel z opcjami WRITE i
         * TRUNCATE_EXISTING, zapisz do niego (przez ByteBuffer) tekst
         * "Zapis przez kanal". Zweryfikuj Files.readString() że treść się
         * zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReadFileChannel {
        /*
         * 🧪 Zadanie 7:
         * Otwórz plik z Zadania 6 przez FileChannel z opcją READ, wczytaj
         * całość do ByteBuffer.allocate((int) channel.size()), wykonaj flip()
         * i wypisz odczytaną treść jako String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RemainingLoop {
        /*
         * 🧪 Zadanie 8:
         * Zapisz do pliku dłuższy tekst (np. 50 znaków). Odczytaj go przez
         * FileChannel małymi porcjami – ByteBuffer.allocate(8) w pętli
         * while (channel.read(buffer) != -1), za każdym razem flip(),
         * wypisz odczytany fragment i clear() przed kolejnym read().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PutByteArrayVsLoop {
        /*
         * 🧪 Zadanie 9:
         * Wypełnij jeden ByteBuffer przez put(byte[]) całego napisu naraz,
         * a drugi ByteBuffer o tej samej pojemności przez pętlę put(byte)
         * bajt-po-bajcie tego samego napisu. Porównaj oba bufory po flip()
         * (czy dają tę samą treść).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MarkAndReset {
        /*
         * 🧪 Zadanie 10:
         * Wczytaj do bufora tekst, wykonaj kilka get() przesuwających
         * position, następnie wywołaj mark() w wybranym miejscu, kontynuuj
         * czytanie dalej, potem reset() i sprawdź że position wróciło
         * do zaznaczonego miejsca.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ManualCopyLoop {
        /*
         * 🧪 Zadanie 11:
         * Skopiuj zawartość jednego pliku tekstowego do drugiego RĘCZNIE
         * przez pętlę FileChannel read/write z buforem 16 bajtów (bez
         * transferTo). Zweryfikuj że skopiowana treść jest identyczna
         * z oryginałem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TransferTo {
        /*
         * 🧪 Zadanie 12:
         * Skopiuj cały plik do drugiego pliku używając
         * source.transferTo(0, source.size(), destination). Wypisz liczbę
         * przesłanych bajtów i porównaj treść obu plików.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TransferFrom {
        /*
         * 🧪 Zadanie 13:
         * Wykonaj tę samą kopię co w Zadaniu 12, ale metodą
         * destination.transferFrom(source, 0, source.size()). Porównaj
         * wynik (treść i liczbę bajtów) z Zadaniem 12.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RandomAccessWrite {
        /*
         * 🧪 Zadanie 14:
         * Utwórz plik z zawartością "AAAAAAAAAA" (10 znaków 'A'). Otwórz
         * FileChannel do zapisu, ustaw position(3) i nadpisz 4 znaki
         * na "BBBB", nie ruszając reszty. Zweryfikuj końcową zawartość:
         * "AAABBBBAAA".
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ReadInReverseChunks {
        /*
         * 🧪 Zadanie 15:
         * Zapisz plik z tekstem podzielonym na porcje po 5 znaków
         * (np. "ABCDE" + "FGHIJ" + "KLMNO"). Odczytaj plik "od tyłu" –
         * ustawiając position() malejąco co 5 bajtów i wypisując kolejne
         * porcje w odwrotnej kolejności ("KLMNO", "FGHIJ", "ABCDE").
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AppendMode {
        /*
         * 🧪 Zadanie 16:
         * Utwórz plik z zawartością "Poczatek. ". Otwórz FileChannel z
         * opcjami WRITE i APPEND, dopisz "Koniec." na końcu pliku bez
         * nadpisywania istniejącej treści. Zweryfikuj pełną zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CharsetByteLength {
        /*
         * 🧪 Zadanie 17:
         * Weź String z polskimi znakami np. "Zażółć gęślą jaźń".
         * Zakoduj go do bajtów przez StandardCharsets.UTF_8 i osobno przez
         * StandardCharsets.UTF_16. Wypisz długość tablicy bajtów dla obu
         * i wyjaśnij różnicę w komentarzu w kodzie (println).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CharBufferDecode {
        /*
         * 🧪 Zadanie 18:
         * Zapisz plik tekstowy w UTF-8. Odczytaj go przez FileChannel do
         * ByteBuffer, następnie użyj StandardCharsets.UTF_8.decode(byteBuffer)
         * aby otrzymać CharBuffer, i wypisz jego zawartość jako String
         * (CharBuffer.toString()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MemoryMappedReadWrite {
        /*
         * 🧪 Zadanie 19:
         * Utwórz plik z zawartością "0000000000". Zmapuj go w trybie
         * READ_WRITE (FileChannel.map(READ_WRITE,...)), zmień pierwsze 4
         * bajty na "9999" bezpośrednio przez MappedByteBuffer.put(), a
         * następnie (po zwolnieniu referencji i System.gc()) odczytaj plik
         * ponownie z dysku i zweryfikuj zmianę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BenchmarkStreamsVsChannel {
        /*
         * 🧪 Zadanie 20:
         * Wygeneruj plik o rozmiarze kilku MB (np. powtarzając linię tekstu
         * w pętli). Zmierz System.nanoTime() dla kopiowania tego pliku
         * klasycznymi strumieniami (FileInputStream/FileOutputStream z
         * buforem bajtowym) oraz przez FileChannel.transferTo(). Wypisz
         * oba czasy w milisekundach.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FixedSizeRecordFile {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj format rekordu o stałym rozmiarze: int id (4 bajty) +
         * name (20 bajtów, UTF-8, dopełnione zerami). Zapisz 5 takich
         * rekordów do pliku przez FileChannel+ByteBuffer. Następnie odczytaj
         * i zaktualizuj rekord nr 3 BEZPOŚREDNIO przez position(3*rozmiarRekordu),
         * bez wczytywania całego pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AppendOnlyLogWithForce {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj prosty append-only log: metoda append(String linia)
         * dopisuje linię z timestampem (System.currentTimeMillis()) na
         * koniec pliku przez FileChannel (APPEND), a po każdym zapisie
         * wywołuje channel.force(true). Dopisz 5 linii i zweryfikuj
         * kolejność w pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_MergeFilesWithTransfer {
        /*
         * 🧪 Zadanie 23:
         * Utwórz 3 małe pliki tekstowe z różną zawartością. Scal je w
         * jeden plik wynikowy w zadanej kolejności, używając kolejnych
         * wywołań transferTo() z każdego pliku źródłowego do wspólnego
         * kanału docelowego. Zweryfikuj że kolejność treści się zgadza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ConfigurableBufferCopy {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę copyWithBufferSize(Path src, Path dst, int bufferSize),
         * kopiującą plik ręczną pętlą read/write z buforem o podanym
         * rozmiarze. Przetestuj z buforem 64 bajty i 4096 bajtów na tym
         * samym pliku, zweryfikuj identyczny wynik i porównaj czas kopiowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LineCounterStreaming {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj licznik linii pliku, który NIE wczytuje całego
         * pliku do pamięci naraz – czyta go porcjami przez FileChannel+
         * ByteBuffer (np. po 512 bajtów) i liczy wystąpienia bajtu '\n'.
         * Przetestuj na pliku wielolinijkowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_XorTransformInPlace {
        /*
         * 🧪 Zadanie 26:
         * Utwórz plik binarny z kilkoma bajtami danych. Zmapuj go w trybie
         * READ_WRITE i wykonaj operację XOR każdego bajtu z ustalonym
         * kluczem (np. 0x5A) bezpośrednio na MappedByteBuffer. Zweryfikuj
         * zmianę odczytując plik ponownie, a następnie wykonaj XOR jeszcze
         * raz i sprawdź że dane wróciły do oryginału.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BinaryIndexFile {
        /*
         * 🧪 Zadanie 27:
         * Zapisz dane tekstowe kilku "wpisów" do jednego pliku (content.dat),
         * a w osobnym pliku (index.dat) zbuduj indeks rekordów o stałym
         * rozmiarze (key:int, startOffset:long, length:int). Napisz metodę
         * pobierającą treść wpisu po kluczu, która najpierw czyta indeks,
         * a potem odczytuje właściwy fragment content.dat przez position()+limit().
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CircularRingBufferFile {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj "plik cykliczny" o ustalonym rozmiarze maksymalnym
         * (np. 100 bajtów) – writer dopisujący dane, który po osiągnięciu
         * końca pliku zawija position() z powrotem na 0 i nadpisuje najstarsze
         * dane. Zademonstruj zapis kilku porcji danych przekraczających
         * łącznie limit i pokaż że najstarsze dane zostały nadpisane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConcatenateAndVerifyMapped {
        /*
         * 🧪 Zadanie 29:
         * Połącz dwa pliki w trzeci używając transferTo/transferFrom
         * (kanał-do-kanału), a następnie zmapuj wynikowy plik w trybie
         * READ_ONLY i porównaj bajt-po-bajcie jego zawartość z oczekiwaną
         * konkatenacją (bajty pliku 1 + bajty pliku 2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniDatabaseOfPersonRecords {
        /*
         * 🧪 Zadanie 30:
         * Mini-projekt: zaimplementuj klasę PersonRecordFile obsługującą
         * plik binarny z rekordami o stałym rozmiarze: id:int, name:32 bajty
         * (UTF-8, dopełnione), age:int. Metody: insert(Person), Person
         * readAt(int index), void updateAt(int index, Person). Wszystko
         * WYŁĄCZNIE przez FileChannel/ByteBuffer (bez ObjectOutputStream).
         * Przetestuj wstawienie 5 rekordów i nadpisanie jednego z nich
         * w miejscu.
         */
        public static void main(String[] args) { }
    }
}
