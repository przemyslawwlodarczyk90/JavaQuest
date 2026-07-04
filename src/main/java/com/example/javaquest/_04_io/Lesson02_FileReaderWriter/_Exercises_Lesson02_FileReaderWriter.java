package com.example.javaquest._04_io.Lesson02_FileReaderWriter;

public class _Exercises_Lesson02_FileReaderWriter {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateAndWrite {
        /*
         * 🧪 Zadanie 1:
         * Utwórz plik tymczasowy (Files.createTempFile("lesson02_ex01_", ".txt")).
         * Zapisz do niego jedno zdanie za pomocą FileWriter, zamknij strumień,
         * a następnie wypisz zawartość pliku (Files.readString).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteMultipleLines {
        /*
         * 🧪 Zadanie 2:
         * Utwórz plik tymczasowy i zapisz do niego 3 linie tekstu FileWriterem,
         * każdą zakończoną ręcznie wpisanym "\n". Wypisz zawartość pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_OverwriteFile {
        /*
         * 🧪 Zadanie 3:
         * Utwórz plik tymczasowy, zapisz "Wersja 1", zamknij strumień.
         * Otwórz plik ponownie FileWriterem (bez append) i zapisz "Wersja 2".
         * Wypisz zawartość i sprawdź, że "Wersja 1" zniknęła (plik nadpisany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AppendMode {
        /*
         * 🧪 Zadanie 4:
         * Utwórz plik tymczasowy z treścią "Linia A\n". Otwórz go ponownie
         * przez new FileWriter(plik, true) (append=true) i dopisz "Linia B\n".
         * Wypisz całą zawartość – obie linie powinny być obecne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteSingleChars {
        /*
         * 🧪 Zadanie 5:
         * Utwórz plik tymczasowy. Za pomocą FileWriter zapisz pojedyncze znaki:
         * write('A'), write('B'), write('C'). Wypisz zawartość pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WriteCharArray {
        /*
         * 🧪 Zadanie 6:
         * Utwórz plik tymczasowy. Zapisz do niego tablicę char[] {'J','a','v','a'}
         * jednym wywołaniem write(char[]). Wypisz zawartość pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReadCharByChar {
        /*
         * 🧪 Zadanie 7:
         * Przygotuj plik tymczasowy z tekstem "Ala ma kota" (FileWriter).
         * Odczytaj go FileReaderem znak po znaku (read()) do StringBuildera
         * i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReadWithBuffer {
        /*
         * 🧪 Zadanie 8:
         * Ten sam plik z Zadania 7 odczytaj metodą read(char[]) z celowo małym
         * buforem (np. char[5]), pamiętając o uwzględnieniu zwróconej liczby
         * odczytanych znaków przy każdym wywołaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FlushBeforeClose {
        /*
         * 🧪 Zadanie 9:
         * Utwórz plik tymczasowy, zapisz do niego tekst FileWriterem, wywołaj flush()
         * (bez close()), sprawdź zawartość pliku (Files.readString), a dopiero
         * potem wywołaj close().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TryWithResources {
        /*
         * 🧪 Zadanie 10:
         * Przepisz zapis (Zadanie 1) i odczyt (Zadanie 7) używając try-with-resources
         * zamiast ręcznego wywołania close().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CopyFileCharByChar {
        /*
         * 🧪 Zadanie 11:
         * Utwórz plik źródłowy z przykładowym tekstem. Skopiuj jego zawartość
         * do drugiego pliku tymczasowego, czytając FileReaderem znak po znaku
         * i zapisując FileWriterem. Zweryfikuj, że oba pliki mają tę samą treść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CountCharsInFile {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę countChars(Path plik) zwracającą łączną liczbę znaków
         * w pliku, korzystając z FileReader.read() w pętli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CountVowels {
        /*
         * 🧪 Zadanie 13:
         * Wczytaj plik znak po znaku (FileReader) i policz, ile jest samogłosek
         * (a,e,i,o,u,y – małe i wielkie litery). Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AppendLog {
        /*
         * 🧪 Zadanie 14:
         * Symuluj prosty log: w pętli 5 razy dopisz linię "Log entry N" (append=true)
         * do jednego pliku tymczasowego, za każdym razem otwierając i zamykając
         * osobny FileWriter. Na końcu wypisz cały plik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ReplaceCharacter {
        /*
         * 🧪 Zadanie 15:
         * Wczytaj plik do String (przez FileReader), zamień wszystkie wystąpienia
         * znaku 'a' na '@' (w StringBuilderze) i zapisz wynik do NOWEGO pliku
         * tymczasowego (nie nadpisuj oryginału).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ReverseFileContent {
        /*
         * 🧪 Zadanie 16:
         * Wczytaj cały plik do String, odwróć kolejność znaków (np. new
         * StringBuilder(tekst).reverse()) i zapisz odwrócony tekst do innego pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareTwoFiles {
        /*
         * 🧪 Zadanie 17:
         * Utwórz dwa pliki tymczasowe z niemal identyczną zawartością, różniącą się
         * jednym znakiem. Porównaj je znak po znaku dwoma FileReaderami czytanymi
         * równolegle i wypisz pozycję pierwszej różnicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SplitIntoTwoFiles {
        /*
         * 🧪 Zadanie 18:
         * Wczytaj plik z dłuższym tekstem, podziel jego zawartość na pół
         * (wg liczby znaków) i zapisz każdą połowę do osobnego pliku tymczasowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_WordFrequencyBasic {
        /*
         * 🧪 Zadanie 19:
         * Wczytaj plik zawierający kilka zdań (FileReader, cała zawartość do Stringa),
         * rozdziel na słowa (String.split), policz częstość każdego słowa
         * do Map<String,Integer> i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_TempFileCleanupPattern {
        /*
         * 🧪 Zadanie 20:
         * Utwórz 3 pliki tymczasowe, zapisz do każdego inną treść FileWriterem.
         * W bloku try celowo rzuć wyjątek (np. throw new RuntimeException("test")),
         * złap go, a w bloku finally usuń wszystkie 3 pliki (Files.deleteIfExists)
         * – zademonstruj, że sprzątanie następuje nawet po błędzie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SimpleCsvWriter {
        /*
         * 🧪 Zadanie 21:
         * Mając listę 5 osób (imię, wiek) w tablicy/liście, zapisz je do pliku
         * tymczasowego w formacie CSV ("imię,wiek"), ręcznie budując linie tekstu
         * i zapisując FileWriterem (bez BufferedWriter – to dopiero Lekcja 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SimpleCsvReader {
        /*
         * 🧪 Zadanie 22:
         * Odczytaj plik CSV z Zadania 21 znak po znaku FileReaderem, samodzielnie
         * wykrywając koniec linii (znak '\n'), parsując każdą linię na imię i wiek,
         * i wypisując listę odczytanych rekordów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FileBackupUtility {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę backupFile(Path original), która tworzy kopię pliku pod nazwą
         * "original.bak" korzystając wyłącznie z FileReader/FileWriter (bez Files.copy)
         * i zwraca Path do utworzonej kopii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TemplateEngine {
        /*
         * 🧪 Zadanie 24:
         * Utwórz plik-szablon zawierający placeholdery "{{imie}}" i "{{wiek}}".
         * Wczytaj go, podmień placeholdery na konkretne wartości z Map<String,String>
         * i zapisz wynik do nowego pliku tymczasowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MergeFiles {
        /*
         * 🧪 Zadanie 25:
         * Utwórz 3 osobne pliki tekstowe z różną treścią. Scal ich zawartość
         * w jeden plik wynikowy, dodając separator "--- plik N ---" przed
         * zawartością każdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DuplicateLineRemover {
        /*
         * 🧪 Zadanie 26:
         * Utwórz plik z tekstem zawierającym powtarzające się linie (rozdzielone "\n").
         * Wczytaj go, usuń zduplikowane linie zachowując kolejność pierwszego
         * wystąpienia (np. LinkedHashSet<String>) i zapisz wynik do nowego pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_FileDiffReport {
        /*
         * 🧪 Zadanie 27:
         * Utwórz dwa pliki o różnej zawartości. Porównaj je znak po znaku i wygeneruj
         * "raport różnic" (pozycja + oba znaki, które się różnią), zapisując raport
         * do trzeciego pliku tekstowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SimpleEncryptDecrypt {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj prosty szyfr Cezara: wczytaj plik znak po znaku, przesuń
         * litery alfabetu o zadaną wartość (np. +3), zapisz zaszyfrowaną wersję
         * do nowego pliku. Następnie odszyfruj (-3) do kolejnego pliku i porównaj
         * wynik z oryginałem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_LineNumbering {
        /*
         * 🧪 Zadanie 29:
         * Wczytaj plik tekstowy (samodzielnie wykrywając "\n" znak po znaku, bez
         * BufferedReader), dodaj numerację na początku każdej linii (np. "1: ...")
         * i zapisz wynik do nowego pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_LogRotation {
        /*
         * 🧪 Zadanie 30:
         * Zasymuluj prostą rotację logów: plik "app.log" w katalogu tymczasowym.
         * W pętli dopisuj krótkie wpisy (append=true); gdy rozmiar pliku przekroczy
         * 100 znaków, przenieś jego zawartość do "app.log.1" (nadpisując poprzedni,
         * jeśli istnieje) i wyczyść "app.log" (nowy pusty plik), po czym kontynuuj
         * dopisywanie kolejnych wpisów.
         */
        public static void main(String[] args) { }
    }
}
