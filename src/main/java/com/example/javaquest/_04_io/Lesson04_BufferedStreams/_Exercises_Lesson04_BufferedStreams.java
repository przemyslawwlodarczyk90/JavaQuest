package com.example.javaquest._04_io.Lesson04_BufferedStreams;

public class _Exercises_Lesson04_BufferedStreams {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateBinaryFile {
        /*
         * 🧪 Zadanie 1:
         * Utwórz plik tymczasowy .bin (Files.createTempFile("lesson04_ex01_", ".bin")).
         * Wypełnij go losowymi bajtami (new Random(42), rozmiar np. 10 000 bajtów)
         * za pomocą Files.write. Wypisz rozmiar pliku (Files.size).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ReadByteByByte {
        /*
         * 🧪 Zadanie 2:
         * Odczytaj plik binarny z Zadania 1 "gołym" FileInputStream, bajt po bajcie
         * (read() w pętli). Policz, ile bajtów odczytano i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WrapInBuffered {
        /*
         * 🧪 Zadanie 3:
         * Odczytaj ten sam plik binarny, tym razem owijając FileInputStream
         * w BufferedInputStream, również bajt po bajcie. Porównaj liczbę odczytanych
         * bajtów z wynikiem z Zadania 2 – powinny być identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_BufferedWriteBasic {
        /*
         * 🧪 Zadanie 4:
         * Utwórz tablicę byte[] z przykładowymi danymi. Zapisz ją do pliku
         * tymczasowego jednym wywołaniem BufferedOutputStream.write(byte[]).
         * Odczytaj plik z powrotem (Files.readAllBytes) i porównaj z oryginałem
         * (Arrays.equals).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ReadWithBlock {
        /*
         * 🧪 Zadanie 5:
         * Odczytaj plik binarny z Zadania 1 blokami po 1024 bajty przez
         * BufferedInputStream.read(byte[]), pamiętając o uwzględnieniu zwróconej
         * liczby faktycznie odczytanych bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CopyPlain {
        /*
         * 🧪 Zadanie 6:
         * Skopiuj plik binarny na nowy plik tymczasowy, używając "gołych"
         * FileInputStream/FileOutputStream, bajt po bajcie. Zweryfikuj poprawność
         * kopii (Files.mismatch == -1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CopyBuffered {
        /*
         * 🧪 Zadanie 7:
         * Skopiuj ten sam plik binarny na inny nowy plik, tym razem używając
         * BufferedInputStream/BufferedOutputStream blokami (np. 8192 bajty).
         * Zweryfikuj poprawność kopii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteSingleBytes {
        /*
         * 🧪 Zadanie 8:
         * Utwórz plik tymczasowy. Zapisz do niego BufferedOutputStream pojedyncze
         * bajty write(int) w pętli (wartości 0-9). Odczytaj plik z powrotem
         * i wypisz odczytane wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FlushBuffered {
        /*
         * 🧪 Zadanie 9:
         * Zapisz dane do pliku przez BufferedOutputStream, wywołaj flush()
         * przed close() i sprawdź, że dane są już widoczne w pliku
         * (Files.readAllBytes) zanim strumień zostanie zamknięty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TryWithResourcesCopy {
        /*
         * 🧪 Zadanie 10:
         * Przepisz kopiowanie z Zadania 7 z użyciem try-with-resources dla obu
         * strumieni (wejściowego i wyjściowego) w jednym bloku.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PerformanceCompareCopy {
        /*
         * 🧪 Zadanie 11:
         * Wygeneruj większy plik binarny (min. 5 MB). Porównaj czas kopiowania:
         * FileInputStream/FileOutputStream bajt po bajcie vs BufferedInputStream/
         * BufferedOutputStream blokami. Zmierz System.nanoTime() i wypisz
         * przyspieszenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ByteFrequency {
        /*
         * 🧪 Zadanie 12:
         * Policz częstość występowania każdej wartości bajtu (0-255) w pliku
         * binarnym (int[256]), czytając plik BufferedInputStream. Wypisz
         * 5 najczęściej występujących wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FindByteValue {
        /*
         * 🧪 Zadanie 13:
         * Znajdź pozycję (offset) pierwszego wystąpienia konkretnej wartości bajtu
         * (np. 0) w pliku binarnym, czytając go strumieniem buforowanym i licząc
         * odczytane bajty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CompareTwoBinaryFiles {
        /*
         * 🧪 Zadanie 14:
         * Utwórz dwa pliki binarne różniące się kilkoma bajtami. Porównaj je
         * bajt po bajcie przez BufferedInputStream (bez Files.mismatch) i wypisz
         * offset pierwszej różnicy lub informację, że są identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AppendBinaryData {
        /*
         * 🧪 Zadanie 15:
         * Dopisz dodatkowe bajty na koniec istniejącego pliku binarnego, otwierając
         * new FileOutputStream(plik, true) (append) owinięty w BufferedOutputStream.
         * Sprawdź, że oryginalna zawartość została zachowana.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SplitBinaryFile {
        /*
         * 🧪 Zadanie 16:
         * Podziel plik binarny na 2 (prawie) równe części, zapisując każdą połowę
         * do osobnego pliku tymczasowego przy pomocy strumieni buforowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MergeBinaryFiles {
        /*
         * 🧪 Zadanie 17:
         * Scal dwa pliki binarne w jeden (najpierw zawartość pierwszego, potem
         * drugiego) przy pomocy BufferedInputStream/BufferedOutputStream.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_XorObfuscate {
        /*
         * 🧪 Zadanie 18:
         * Wczytaj plik binarny blokami przez BufferedInputStream, wykonaj XOR
         * każdego bajtu z prostym jednobajtowym kluczem i zapisz "zaszyfrowany"
         * wynik do nowego pliku buforowanym strumieniem wyjściowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_XorDeobfuscate {
        /*
         * 🧪 Zadanie 19:
         * Odszyfruj plik z Zadania 18 (ponowny XOR tym samym kluczem) i porównaj
         * wynik z oryginałem (Files.mismatch == -1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ChecksumSimple {
        /*
         * 🧪 Zadanie 20:
         * Odczytaj plik binarny blokami przez BufferedInputStream, oblicz prostą
         * sumę kontrolną (suma wszystkich bajtów modulo 256) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BlockSizeBenchmark {
        /*
         * 🧪 Zadanie 21:
         * Zmierz czas kopiowania tego samego dużego pliku binarnego dla różnych
         * rozmiarów bufora (np. 256, 1024, 8192, 65536 bajtów). Wypisz tabelę
         * wyników i wniosek, który rozmiar bufora był najbardziej efektywny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BinaryFileDiffReport {
        /*
         * 🧪 Zadanie 22:
         * Dla dwóch podobnych plików binarnych o tym samym rozmiarze wygeneruj
         * raport różnic (lista offsetów, na których bajty się różnią) i zapisz go
         * do pliku tekstowego (strumień znakowy z poprzednich lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ChunkedHashLike {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj prosty algorytm "hash-podobny" (np. rolling XOR z przesunięciem
         * bitowym) liczony blokami po 4096 bajtów dla dużego pliku binarnego –
         * uproszczoną wersję sumy kontrolnej pliku (podobną w duchu do CRC).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BinaryFileSignatureCheck {
        /*
         * 🧪 Zadanie 24:
         * Sprawdź "magic number" pliku binarnego (pierwsze kilka bajtów) i na tej
         * podstawie sklasyfikuj plik jako jeden z wymyślonych formatów (np. jeśli
         * zaczyna się od bajtów {0x50,0x4B} → "ZIP-like", w przeciwnym razie
         * "UNKNOWN"). Przetestuj na kilku wygenerowanych plikach z różnymi nagłówkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_StreamingCopyWithProgress {
        /*
         * 🧪 Zadanie 25:
         * Skopiuj duży plik binarny blokami, wypisując po każdym bloku procent
         * postępu kopiowania (na podstawie znanego całkowitego rozmiaru pliku
         * i sumy dotychczas skopiowanych bajtów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiFileConcatenator {
        /*
         * 🧪 Zadanie 26:
         * Napisz narzędzie łączące dowolną liczbę plików binarnych (np. 5 fragmentów)
         * w jeden plik wynikowy, zachowując kolejność, korzystając wyłącznie
         * z buforowanych strumieni (symulacja łączenia fragmentów pobranego pliku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BinarySearchInStream {
        /*
         * 🧪 Zadanie 27:
         * Zapisz posortowaną tablicę int jako surowe bajty w pliku (po 4 bajty
         * na liczbę). Zaimplementuj funkcję wyszukującą wartość, czytając
         * odpowiednie bloki bajtów (np. przez skip()/read()) i rekonstruując int,
         * bez wczytywania całego pliku do pamięci naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ThrottledCopy {
        /*
         * 🧪 Zadanie 28:
         * Skopiuj plik binarny blokami z sztucznym ograniczeniem "przepustowości"
         * (np. Thread.sleep(10) po każdym bloku), symulując wolne łącze sieciowe.
         * Zmierz łączny czas operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CorruptionSimulatorAndDetector {
        /*
         * 🧪 Zadanie 29:
         * Skopiuj plik binarny, celowo "uszkadzając" losowo kilka bajtów w kopii
         * (np. co 1000-ny bajt zamień na losową wartość). Następnie porównaj
         * oryginał z uszkodzoną kopią blokami przez BufferedInputStream i wypisz
         * listę uszkodzonych offsetów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullBackupUtility {
        /*
         * 🧪 Zadanie 30:
         * Napisz narzędzie backupujące katalog plików binarnych (kilka wygenerowanych
         * plików w katalogu tymczasowym) do jednego "archiwum" – pliku zawierającego
         * kolejno: dla każdego pliku jego nazwę (zapisaną jako bajty poprzedzone
         * długością), rozmiar i zawartość. Następnie napisz "odtwarzacz", który
         * z takiego archiwum odtwarza oryginalne pliki w innym katalogu i weryfikuje
         * ich zgodność z oryginałami (Files.mismatch).
         */
        public static void main(String[] args) { }
    }
}
