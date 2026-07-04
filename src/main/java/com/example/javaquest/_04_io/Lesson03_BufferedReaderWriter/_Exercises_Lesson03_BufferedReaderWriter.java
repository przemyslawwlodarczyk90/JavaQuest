package com.example.javaquest._04_io.Lesson03_BufferedReaderWriter;

public class _Exercises_Lesson03_BufferedReaderWriter {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicWriteReadLine {
        /*
         * 🧪 Zadanie 1:
         * Utwórz plik tymczasowy. Za pomocą BufferedWriter zapisz 3 linie tekstu,
         * używając write() + newLine() po każdej. Odczytaj plik BufferedReaderem
         * metodą readLine() w pętli i wypisz każdą linię z numerem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NewLineVsManual {
        /*
         * 🧪 Zadanie 2:
         * Zapisz ten sam zestaw linii do dwóch plików: jednego używając newLine(),
         * drugiego ręcznie wpisując "\n". Porównaj oba pliki bajt po bajcie
         * (Files.mismatch) i wypisz, czy są identyczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReadLineNull {
        /*
         * 🧪 Zadanie 3:
         * Utwórz plik z kilkoma liniami. Odczytaj go BufferedReaderem aż readLine()
         * zwróci null, licząc po drodze liczbę odczytanych linii. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_WriteWithBufferedWriter {
        /*
         * 🧪 Zadanie 4:
         * Owiń FileWriter w BufferedWriter i w pętli zapisz 100 krótkich linii
         * (np. "Linia " + i). Na końcu odczytaj plik i sprawdź, że ma dokładnie
         * 100 linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CountLines {
        /*
         * 🧪 Zadanie 5:
         * Napisz metodę countLines(Path plik) zwracającą liczbę linii pliku,
         * korzystając z BufferedReader.readLine() w pętli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PrintFirstNLines {
        /*
         * 🧪 Zadanie 6:
         * Utwórz plik z co najmniej 10 liniami. Wypisz tylko pierwsze 5 linii,
         * przerywając pętlę readLine() po 5 iteracjach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_PrintLastLine {
        /*
         * 🧪 Zadanie 7:
         * Odczytaj cały plik linia po linii (readLine()), zapamiętując tylko
         * ostatnią odczytaną linię. Wypisz ją na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_EmptyLinesCount {
        /*
         * 🧪 Zadanie 8:
         * Utwórz plik zawierający kilka pustych linii wymieszanych z niepustymi.
         * Policz, ile pustych linii ("") jest w pliku (BufferedReader.readLine()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteThenReadImmediately {
        /*
         * 🧪 Zadanie 9:
         * Zapisz kilka linii BufferedWriterem, zamknij strumień, natychmiast
         * odczytaj ten sam plik BufferedReaderem i porównaj odczytaną treść
         * z oczekiwaną (np. przez List<String> z oczekiwanymi liniami).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TryWithResourcesBoth {
        /*
         * 🧪 Zadanie 10:
         * Przepisz zapis i odczyt z Zadania 1 tak, aby zarówno BufferedWriter,
         * jak i BufferedReader były otwierane w osobnych blokach try-with-resources.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LineNumbering {
        /*
         * 🧪 Zadanie 11:
         * Odczytaj plik linia po linii (readLine()) i zapisz do nowego pliku
         * każdą linię poprzedzoną numerem (np. "1: treść"), używając
         * BufferedWriter + newLine().
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FilterLines {
        /*
         * 🧪 Zadanie 12:
         * Odczytaj plik i zapisz do nowego pliku tylko te linie, które zawierają
         * określone słowo (np. "błąd"). Wypisz też, ile linii zostało przefiltrowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_UppercaseFile {
        /*
         * 🧪 Zadanie 13:
         * Wczytaj plik linia po linii, zamień każdą linię na wielkie litery
         * (String.toUpperCase()) i zapisz wynik do nowego pliku BufferedWriterem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WordCountPerLine {
        /*
         * 🧪 Zadanie 14:
         * Dla każdej linii pliku policz liczbę słów (String.split) i zapisz raport
         * w formacie "Linia N: X słów" do nowego pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LongestLine {
        /*
         * 🧪 Zadanie 15:
         * Znajdź najdłuższą linię w pliku (po liczbie znaków) i wypisz jej numer
         * oraz treść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ReverseLinesOrder {
        /*
         * 🧪 Zadanie 16:
         * Wczytaj wszystkie linie pliku do List<String>, odwróć ich kolejność
         * i zapisz do nowego pliku (ostatnia linia staje się pierwszą itd.,
         * efekt jak polecenie "tac").
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MergeSortedFiles {
        /*
         * 🧪 Zadanie 17:
         * Utwórz dwa pliki z posortowanymi alfabetycznie słowami (po jednym na linię).
         * Scal je w jeden posortowany plik wynikowy, czytając linie z obu naraz
         * (algorytm merge jak w merge sort).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_PerformanceCompare {
        /*
         * 🧪 Zadanie 18:
         * Wygeneruj duży plik (min. 50 000 linii). Porównaj czas odczytu:
         * BufferedReader.readLine() vs "goły" FileReader.read() znak po znaku.
         * Zmierz System.nanoTime() i wypisz przyspieszenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CsvToMap {
        /*
         * 🧪 Zadanie 19:
         * Utwórz plik CSV, gdzie każda linia ma format "klucz,wartość". Wczytaj go
         * BufferedReaderem (readLine() + split(",")), zbuduj Map<String,String>
         * i wypisz jej zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_UniqueLines {
        /*
         * 🧪 Zadanie 20:
         * Utwórz plik z powtarzającymi się liniami. Wczytaj go, zachowaj tylko
         * unikalne linie z zachowaniem kolejności (LinkedHashSet<String>) i zapisz
         * wynik BufferedWriterem + newLine() do nowego pliku.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LogLevelCounter {
        /*
         * 🧪 Zadanie 21:
         * Wygeneruj plik logu w formacie "2024-01-01 12:00:00 [POZIOM] wiadomość"
         * (kilkaset linii z losowym poziomem INFO/WARN/ERROR). Policz, ile linii
         * ma każdy poziom (Map<String,Integer>) i wypisz raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ErrorExtractor {
        /*
         * 🧪 Zadanie 22:
         * Z pliku logu w stylu z Zadania 21 wyciągnij tylko linie zawierające
         * "[ERROR]" i zapisz je do osobnego pliku "errors.log", zachowując
         * oryginalny format linii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CsvValidator {
        /*
         * 🧪 Zadanie 23:
         * Utwórz plik CSV, w którym niektóre linie mają błędną liczbę kolumn.
         * Wczytaj go linia po linii, zwaliduj, że każda linia ma dokładnie N kolumn
         * (rozdzielonych przecinkiem), zbierz numery niepoprawnych linii i wypisz
         * raport błędów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TopNWords {
        /*
         * 🧪 Zadanie 24:
         * Wczytaj większy plik tekstowy (kilkaset linii) linia po linii, policz
         * częstość wszystkich słów (Map<String,Integer>) i wypisz 10 najczęstszych
         * słów posortowanych malejąco po liczbie wystąpień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PagedReader {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj metodę readPage(Path plik, int numerStrony, int liniiNaStrone)
         * zwracającą List<String> odpowiadającą danej "stronie" pliku (np. strona 3
         * po 20 linii = linie 41-60), czytając plik BufferedReaderem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_LogRotationMerge {
        /*
         * 🧪 Zadanie 26:
         * Utwórz 3 pliki logów symulujące rotację ("app.log", "app.log.1", "app.log.2").
         * Scal je w poprawnej kolejności chronologicznej (od najstarszego do
         * najnowszego) do jednego pliku "combined.log", dodając separator
         * z nazwą pliku źródłowego przed każdą częścią.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DiffLineByLine {
        /*
         * 🧪 Zadanie 27:
         * Utwórz dwa pliki o tej samej liczbie linii, ale różniące się kilkoma z nich.
         * Porównaj je linia po linii (readLine() na obu naraz, w tej samej pętli)
         * i wypisz numery linii, które się różnią, wraz z obiema wersjami treści.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ChecksumPerLine {
        /*
         * 🧪 Zadanie 28:
         * Dla każdej linii pliku oblicz prostą sumę kontrolną (suma kodów znaków
         * modulo 997) i zapisz do nowego pliku w formacie "treść_linii|suma",
         * tworząc prosty mechanizm wykrywania uszkodzeń danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ConfigFileParser {
        /*
         * 🧪 Zadanie 29:
         * Utwórz plik konfiguracyjny w formacie "klucz=wartość", z liniami komentarzy
         * zaczynającymi się od '#' (do pominięcia). Wczytaj go BufferedReaderem,
         * zbuduj Map<String,String> i wypisz sparsowaną konfigurację.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_StreamingWordFrequencyReport {
        /*
         * 🧪 Zadanie 30:
         * Wczytaj duży plik tekstowy linia po linii (bez wczytywania całości
         * do pamięci naraz), licząc częstość słów w Map<String,Integer>.
         * Wynikowy raport (słowo + liczba wystąpień, posortowane malejąco) zapisz
         * BufferedWriterem do nowego pliku "report.txt", mierząc czas przetwarzania
         * przez System.nanoTime().
         */
        public static void main(String[] args) { }
    }
}
