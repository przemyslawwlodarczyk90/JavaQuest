package com.example.javaquest._04_io.Lesson24_ZIP;

public class _Exercises_Lesson24_ZIP {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SingleEntryZip {
        /*
         * 🧪 Zadanie 1:
         * Stwórz archiwum ZIP w katalogu tymczasowym z jednym wpisem "notatka.txt"
         * zawierającym dowolny tekst. Sprawdź, że plik zip istnieje i ma rozmiar > 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MultipleEntriesZip {
        /*
         * 🧪 Zadanie 2:
         * Stwórz archiwum ZIP z 3 wpisami tekstowymi ("a.txt","b.txt","c.txt") o różnej
         * treści, używając ZipOutputStream + ZipEntry w pętli. Wypisz nazwy zapisanych wpisów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ListEntryNames {
        /*
         * 🧪 Zadanie 3:
         * Otwórz archiwum z zadania 2 przez ZipInputStream i w pętli getNextEntry()
         * wypisz nazwy wszystkich wpisów (bez odczytywania ich zawartości).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PrintEntryContents {
        /*
         * 🧪 Zadanie 4:
         * Odczytaj archiwum z zadania 2 i dla każdego wpisu wypisz jego treść
         * (odczytaną ze strumienia ZipInputStream) na konsolę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_EntryInSubfolder {
        /*
         * 🧪 Zadanie 5:
         * Stwórz wpis ZipEntry o nazwie "docs/readme.txt" (z separatorem "/" symulującym
         * podfolder). Odczytaj archiwum z powrotem i sprawdź, że nazwa wpisu to dokładnie
         * "docs/readme.txt".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CountEntries {
        /*
         * 🧪 Zadanie 6:
         * Wczytaj dowolne archiwum ZIP i policz liczbę wpisów w nim zawartych
         * (licznik zwiększany w pętli getNextEntry()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExtractSingleEntry {
        /*
         * 🧪 Zadanie 7:
         * Mając archiwum z kilkoma wpisami, wyciągnij zawartość TYLKO jednego,
         * konkretnego wpisu po nazwie (pomijając pozostałe) jako String, bez wypisywania reszty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_VerifyRoundTrip {
        /*
         * 🧪 Zadanie 8:
         * Spakuj własny zestaw 2-3 plików tekstowych (Map<String,String> nazwa->treść) do ZIP,
         * odczytaj je z powrotem do drugiej mapy i porównaj obie mapy (equals) – potwierdź,
         * że zawartość się zgadza (jak w lekcji, ale z własnymi danymi).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SizeGrowsWithContent {
        /*
         * 🧪 Zadanie 9:
         * Stwórz archiwum z jednym małym plikiem, sprawdź jego rozmiar. Dodaj do niego
         * (przebudowując archiwum) czwarty, znacznie większy plik tekstowy i sprawdź,
         * jak zmienił się rozmiar pliku zip.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CleanupTempFiles {
        /*
         * 🧪 Zadanie 10:
         * Stwórz katalog tymczasowy i archiwum zip w nim. Po zakończeniu pracy usuń
         * plik zip oraz katalog tymczasowy (Files.deleteIfExists) i sprawdź (Files.exists),
         * że oba zniknęły.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ExtractToDisk {
        /*
         * 🧪 Zadanie 11:
         * Rozpakuj archiwum ZIP (zawierające wpis w podfolderze, np. "sub/plik.txt")
         * do prawdziwych plików na dysku w katalogu tymczasowym, tworząc brakujące
         * podkatalogi przez Files.createDirectories przed zapisem pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ZipRealDirectory {
        /*
         * 🧪 Zadanie 12:
         * Stwórz w katalogu tymczasowym kilka prawdziwych plików (i podkatalog z plikiem).
         * Spakuj całą tę strukturę katalogów rekurencyjnie (np. Files.walk) do jednego archiwum ZIP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_VerifyExtractedFilesOnDisk {
        /*
         * 🧪 Zadanie 13:
         * Rozpakuj archiwum z zadania 12 do innego katalogu i porównaj zawartość
         * rozpakowanych plików z zawartością oryginalnych plików na dysku (Files.readString),
         * potwierdzając ich zgodność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ZipSlipDetection {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę isZipSlipSafe(String entryName, Path targetDir), która wykrywa
         * próbę ataku Zip Slip (np. wpis "../../evil.txt") zanim plik zostanie
         * rozpakowany. Przetestuj na bezpiecznej i złośliwej nazwie wpisu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FilterByExtension {
        /*
         * 🧪 Zadanie 15:
         * Mając archiwum z mieszanymi wpisami (np. .txt i .log), rozpakuj/odczytaj
         * TYLKO wpisy z rozszerzeniem ".txt", pomijając pozostałe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AppendEntryToZip {
        /*
         * 🧪 Zadanie 16:
         * Mając istniejące archiwum ZIP, napisz metodę appendEntry(Path zip, String name, String content),
         * która odczytuje wszystkie istniejące wpisy, zapisuje je do NOWEGO archiwum razem
         * z dodatkowym nowym wpisem (ZipOutputStream nie pozwala dopisywać w miejscu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompressionRatio {
        /*
         * 🧪 Zadanie 17:
         * Spakuj długi, powtarzalny tekst (np. 10 000 razy powtórzone to samo zdanie)
         * do ZIP-a. Porównaj rozmiar oryginalnego tekstu (w bajtach) z rozmiarem archiwum
         * i wypisz procentowy współczynnik kompresji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RenameEntryOnRepack {
        /*
         * 🧪 Zadanie 18:
         * Odczytaj wszystkie wpisy z istniejącego archiwum źródłowego i zapisz je do
         * nowego archiwum, zmieniając nazwę każdego wpisu (np. dodając prefiks "backup_").
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_EmptyZipEdgeCase {
        /*
         * 🧪 Zadanie 19:
         * Stwórz archiwum ZIP bez żadnych wpisów (otwórz i od razu zamknij ZipOutputStream).
         * Odczytaj je z powrotem i potwierdź, że getNextEntry() od razu zwraca null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ManifestEntry {
        /*
         * 🧪 Zadanie 20:
         * Podczas pakowania kilku plików do archiwum, zbuduj dodatkowo wpis "manifest.txt"
         * zawierający listę nazw wszystkich pozostałych wpisów wraz z ich rozmiarem
         * w bajtach, i dołącz go jako ostatni wpis archiwum.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SafeUnzipUtility {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj metodę unzip(Path zipFile, Path targetDir), która rozpakowuje
         * WSZYSTKIE wpisy do targetDir, ale dla każdego wpisu waliduje jego docelową ścieżkę
         * względem targetDir (ochrona przed Zip Slip) i rzuca wyjątek, jeśli wpis próbuje
         * wyjść poza targetDir.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_BackupAndRestore {
        /*
         * 🧪 Zadanie 22:
         * Mając listę ścieżek do kilku plików, spakuj je wszystkie do archiwum z nazwą
         * zawierającą znacznik czasu (np. "backup_<timestamp>.zip"), a następnie
         * rozpakuj (przywróć) archiwum do osobnego katalogu i zweryfikuj bajt-po-bajcie
         * zgodność przywróconych plików z oryginałami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ZipInsideZip {
        /*
         * 🧪 Zadanie 23:
         * Stwórz "wewnętrzne" archiwum ZIP (z kilkoma wpisami), a następnie spakuj JE SAME
         * jako jeden wpis do "zewnętrznego" archiwum. Rozpakuj zewnętrzne archiwum,
         * wyodrębnij bajty wewnętrznego zipa i odczytaj również jego wpisy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_NaiveObfuscation {
        /*
         * 🧪 Zadanie 24:
         * java.util.zip nie wspiera archiwów chronionych hasłem. Zademonstruj (czysto
         * edukacyjnie, NIE jako bezpieczne rozwiązanie) prosty mechanizm: zakoduj bajty
         * każdego wpisu prostym XOR-em z kluczem przed zapisaniem do ZIP-a, a przy odczycie
         * zdekoduj je z powrotem tym samym kluczem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_IncrementalZipUpdate {
        /*
         * 🧪 Zadanie 25:
         * Mając istniejące archiwum oraz katalog źródłowy z plikami, napisz narzędzie,
         * które przebudowuje archiwum dodając tylko pliki nowe lub zmienione (np. inny
         * rozmiar niż odpowiadający wpis w starym archiwum), pomijając niezmienione pliki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SequentialAggregation {
        /*
         * 🧪 Zadanie 26:
         * Mając kilka niezależnie wygenerowanych fragmentów tekstu (np. z różnych "źródeł"
         * symulowanych jako lista Stringów), zapisz je jeden po drugim jako osobne wpisy
         * do JEDNEGO ZipOutputStream, pilnując poprawnej kolejności putNextEntry/write/closeEntry
         * (strumień ZIP musi być zapisywany sekwencyjnie, nie równolegle).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_IntegrityChecker {
        /*
         * 🧪 Zadanie 27:
         * Po utworzeniu archiwum napisz metodę checkIntegrity(Path zipFile), która otwiera
         * je ponownie i próbuje w pełni odczytać KAŻDY wpis (do końca strumienia),
         * zwracając true jeśli żaden wpis nie rzucił wyjątku, false w przeciwnym razie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ZipBombGuard {
        /*
         * 🧪 Zadanie 28:
         * Podczas rozpakowywania archiwum sprawdzaj deklarowany/odczytany rozmiar każdego
         * wpisu i pomijaj (z komunikatem ostrzegawczym) każdy wpis, którego rozmiar
         * przekracza ustalony limit (np. 10 MB) – podstawowa ochrona przed "zip bombą".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_InMemoryZip {
        /*
         * 🧪 Zadanie 29:
         * Mając Map<String, byte[]> danych w pamięci, zbuduj archiwum ZIP całkowicie
         * w pamięci przy pomocy ByteArrayOutputStream + ZipOutputStream (bez dotykania
         * dysku), a następnie odczytaj je z powrotem z ByteArrayInputStream, weryfikując
         * że wszystkie wpisy się zgadzają.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ProjectArchiverScenario {
        /*
         * 🧪 Zadanie 30:
         * W katalogu tymczasowym stwórz strukturę projektu (kilka plików + jeden podfolder
         * z plikiem). Spakuj całość zachowując strukturę katalogów, a następnie "rozdystrybuuj"
         * i rozpakuj archiwum do INNEGO katalogu docelowego korzystając z bezpiecznej metody
         * unzip() z zadania 21. Zweryfikuj zgodność zawartości KAŻDEGO oryginalnego pliku
         * z jego odpowiednikiem po rozpakowaniu.
         */
        public static void main(String[] args) { }
    }
}
