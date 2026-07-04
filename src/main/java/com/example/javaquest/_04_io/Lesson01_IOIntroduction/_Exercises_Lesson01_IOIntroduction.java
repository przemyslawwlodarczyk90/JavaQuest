package com.example.javaquest._04_io.Lesson01_IOIntroduction;

public class _Exercises_Lesson01_IOIntroduction {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CharsVsBytes {
        /*
         * 🧪 Zadanie 1:
         * Weź String "Zażółć gęślą jaźń". Wypisz liczbę znaków (length())
         * oraz liczbę bajtów po zakodowaniu w UTF-8 (getBytes(StandardCharsets.UTF_8).length).
         * Porównaj obie wartości i wyjaśnij różnicę w komentarzu/println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_EncodingSizeComparison {
        /*
         * 🧪 Zadanie 2:
         * Dla tego samego Stringa z polskimi znakami porównaj długość tablicy bajtów
         * dla kodowań UTF-8 i ISO-8859-1 (getBytes(Charset)). Wypisz obie długości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ByteArrayOutputStreamBasics {
        /*
         * 🧪 Zadanie 3:
         * Utwórz ByteArrayOutputStream, zapisz do niego pojedyncze bajty
         * write(65), write(66), write(67) (kody liter A,B,C).
         * Wypisz zawartość strumienia jako String (toString()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_StringWriterBasics {
        /*
         * 🧪 Zadanie 4:
         * Utwórz StringWriter, zapisz do niego pojedyncze znaki write('X'), write('Y')
         * oraz napis write(" - koniec"). Wypisz zawartość (toString()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_StringReaderLoop {
        /*
         * 🧪 Zadanie 5:
         * Utwórz StringReader dla tekstu "Hello World".
         * Czytaj znak po znaku metodą read() w pętli (aż zwróci -1),
         * zbierając wynik do StringBuildera i wypisując go na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ByteArrayInputStreamLoop {
        /*
         * 🧪 Zadanie 6:
         * Utwórz byte[] {72, 101, 108, 108, 111} i ByteArrayInputStream na jego podstawie.
         * Czytaj bajt po bajcie metodą read() w pętli, wypisując każdy odczytany bajt
         * jako int oraz jako znak (char).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_InputStreamReaderBridge {
        /*
         * 🧪 Zadanie 7:
         * Zakoduj String "Łódź, Kraków" do bajtów UTF-8, utwórz z nich ByteArrayInputStream,
         * a następnie owiń go w InputStreamReader z Charsetem UTF-8.
         * Odczytaj tekst z powrotem i sprawdź (equals), czy jest identyczny z oryginałem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WrongCharsetGarbage {
        /*
         * 🧪 Zadanie 8:
         * Zakoduj String "Łódź, Kraków" do bajtów UTF-8. Odczytaj te same bajty
         * dwa razy przez InputStreamReader: raz z Charsetem UTF-8 (poprawny wynik),
         * raz z Charsetem ISO-8859-1 (błędny wynik – "krzaki").
         * Wypisz oba wyniki obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ByteValueEdgeCase {
        /*
         * 🧪 Zadanie 9:
         * Utwórz byte[] zawierającą jeden bajt o wartości (byte) 0xFF (czyli -1 jako signed byte).
         * Odczytaj go przez ByteArrayInputStream.read() i wypisz zwróconą wartość int
         * (powinno być 255, a nie -1) – zademonstruj, że -1 z read() oznacza "koniec strumienia",
         * a nie literalną wartość bajtu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TryWithResourcesInMemory {
        /*
         * 🧪 Zadanie 10:
         * Przepisz odczyt ze StringReadera (Zadanie 5) i zapis do StringWritera (Zadanie 4)
         * z użyciem try-with-resources zamiast ręcznego wywołania close().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RoundTripEquals {
        /*
         * 🧪 Zadanie 11:
         * Napisz pełny "round trip": String -> bytes (UTF-8) -> ByteArrayInputStream
         * -> InputStreamReader -> StringBuilder -> String. Porównaj wynik z oryginałem
         * (equals) dla kilku różnych tekstów (ASCII, polskie znaki, znaki specjalne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GenericReaderToWriterCopy {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę copy(Reader in, Writer out) kopiującą dane znak po znaku
         * (read()/write(int) w pętli). Przetestuj ją z StringReader jako źródłem
         * i StringWriter jako celem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_GenericStreamToStreamCopy {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę copy(InputStream in, OutputStream out) kopiującą dane bajt po bajcie
         * (read()/write(int) w pętli). Przetestuj z ByteArrayInputStream i ByteArrayOutputStream.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ManualLineSplitInMemory {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj w StringWriter tekst z kilkoma liniami rozdzielonymi ręcznie wpisanym "\n"
         * (BufferedWriter.newLine() poznamy w kolejnej lekcji). Odczytaj ten tekst przez
         * StringReader znak po znaku, ręcznie rozpoznając "\n" i budując listę linii.
         * Wypisz każdą linię z numerem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CharVsByteCountTable {
        /*
         * 🧪 Zadanie 15:
         * Dla 5 różnych Stringów (czysto ASCII, polskie znaki, niemieckie znaki, emoji,
         * mieszany tekst) wypisz tabelę: liczba znaków, liczba bajtów w UTF-8,
         * liczba bajtów w UTF-16, liczba bajtów w ISO-8859-1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_EncodingMismatchDemo {
        /*
         * 🧪 Zadanie 16:
         * Zakoduj tekst z polskimi znakami do bajtów UTF-8. Zdekoduj te same bajty
         * najpierw przez InputStreamReader z ISO-8859-1 (błędny wynik), a potem poprawnie
         * z UTF-8. Wypisz oba wyniki i krótki komentarz, dlaczego pierwszy jest błędny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_HeuristicBinaryDetector {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę isProbablyText(byte[] data) sprawdzającą heurystycznie,
         * czy dane są tekstem: brak bajtów o wartości 0x00 wśród pierwszych 100 bajtów.
         * Przetestuj na byte[] z tekstu (np. getBytes) oraz na losowej tablicy bajtów
         * (Random.nextBytes) symulującej dane binarne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UppercaseWhileCopying {
        /*
         * 🧪 Zadanie 18:
         * Czytając znak po znaku ze StringReadera, zamień każdy znak na wielką literę
         * (Character.toUpperCase) i zapisz do StringWritera. Wypisz wynikowy String.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HexDumper {
        /*
         * 🧪 Zadanie 19:
         * Utwórz ByteArrayInputStream z kilkunastu bajtów. Czytaj bajt po bajcie
         * i wypisz każdy jako dwucyfrową wartość szesnastkową (String.format("%02X", b)),
         * naśladując prosty "hex dump".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ClassifyStreamHierarchy {
        /*
         * 🧪 Zadanie 20:
         * Mając tablicę nazw klas: {"FileInputStream","FileReader","ByteArrayOutputStream",
         * "StringWriter","BufferedReader","ObjectInputStream"}, napisz metodę klasyfikującą
         * każdą nazwę jako "BAJTOWY" lub "ZNAKOWY" (na podstawie tego, co wiesz o hierarchii
         * z tej lekcji) i wypisz wynik klasyfikacji dla każdej nazwy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WordFrequencyInMemory {
        /*
         * 🧪 Zadanie 21:
         * Używając StringReader, wczytaj dłuższy tekst znak po znaku i samodzielnie
         * (bez String.split) podziel go na słowa wg białych znaków. Policz częstość
         * każdego słowa do Map<String,Integer> i wypisz najczęściej występujące słowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ByteFrequencyHistogram {
        /*
         * 🧪 Zadanie 22:
         * Wygeneruj losową tablicę bajtów (Random z ustalonym seedem, 10 000 elementów),
         * odczytaj ją przez ByteArrayInputStream bajt po bajcie i policz częstość
         * wystąpień każdej wartości 0-255 do int[256]. Wypisz 5 najczęściej
         * występujących wartości bajtów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RoundTripCharsetReport {
        /*
         * 🧪 Zadanie 23:
         * Dla listy Stringów (polskie, niemieckie, emoji, ASCII) zbuduj
         * Map<String, Boolean> informującą, czy round trip String->bytes->InputStreamReader->String
         * jest bezstratny dla kodowania ISO-8859-1, a osobno dla UTF-8. Wypisz raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TextPipelineUtility {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę pomocniczą process(String input, Charset cs), która wykonuje
         * pełen łańcuch: String -> bytes(cs) -> ByteArrayInputStream -> InputStreamReader(cs)
         * -> StringBuilder -> String. Wywołaj ją dla tekstu z polskimi znakami z kilkoma
         * różnymi Charsetami i wypisz, które z nich są stratne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SimpleBinaryMessageFormat {
        /*
         * 🧪 Zadanie 25:
         * Zapisz do ByteArrayOutputStream prosty "komunikat": 1 bajt typu (np. 1),
         * 4 bajty długości payloadu (ręcznie, przez przesunięcia bitowe write(len >> 24) itd.)
         * oraz sam payload (bajty tekstu). Następnie odczytaj ten komunikat z powrotem
         * z ByteArrayInputStream, rekonstruując typ, długość i payload, i zweryfikuj poprawność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HexDumpTool {
        /*
         * 🧪 Zadanie 26:
         * Wygeneruj losowe dane binarne (np. 64 bajty). Czytaj je przez ByteArrayInputStream
         * blokami po 16 bajtów i wypisz w formacie zbliżonym do narzędzia "hexdump -C":
         * offset, 16 wartości szesnastkowych, reprezentacja ASCII (znaki niedrukowalne jako '.').
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ChecksumAppend {
        /*
         * 🧪 Zadanie 27:
         * Czytając bajty ze ByteArrayInputStream, policz prostą sumę kontrolną (XOR wszystkich
         * bajtów). Zapisz do ByteArrayOutputStream oryginalne bajty, a na końcu dopisz
         * jeden dodatkowy bajt z sumą kontrolną. Po stronie "odbiorcy" odczytaj dane,
         * przelicz sumę ponownie i porównaj z ostatnim bajtem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TokenizerOverReader {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj prosty tokenizer nad StringReader – czytaj znak po znaku i grupuj
         * znaki w tokeny rozdzielone białymi znakami (bez regex/split), zapisując tokeny
         * do List<String>. Wypisz liczbę tokenów, średnią długość tokenu i najdłuższy token.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MergeMultipleReaders {
        /*
         * 🧪 Zadanie 29:
         * Utwórz 3 StringReadery reprezentujące 3 "źródła" tekstu. Odczytaj je po kolei
         * (ręcznie, bez SequenceInputStream) i połącz ich zawartość w jeden StringWriter,
         * dodając separator "--- źródło N ---" przed zawartością każdego. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DataClassifier {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji w jedną metodę classify(byte[] data), zwracającą
         * "TEXT_UTF8", "TEXT_OTHER" lub "BINARY" na podstawie: obecności bajtów 0x00,
         * próby dekodowania jako UTF-8 (CharsetDecoder z opcją zgłaszania błędów) i heurystyk
         * z Zadania 17. Przetestuj na: czystym ASCII, tekście z polskimi znakami (UTF-8)
         * i losowych danych binarnych. Wypisz tabelę wyników klasyfikacji.
         */
        public static void main(String[] args) { }
    }
}
