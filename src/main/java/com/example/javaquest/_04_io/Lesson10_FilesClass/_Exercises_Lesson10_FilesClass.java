package com.example.javaquest._04_io.Lesson10_FilesClass;

public class _Exercises_Lesson10_FilesClass {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateTempDir {
        /*
         * 🧪 Zadanie 1:
         * Utwórz katalog tymczasowy przez Files.createTempDirectory("cwiczenie01_").
         * Wypisz jego ścieżkę oraz sprawdź Files.exists() i Files.isDirectory().
         * Na koniec usuń go (Files.deleteIfExists()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateDirectories {
        /*
         * 🧪 Zadanie 2:
         * W katalogu tymczasowym utwórz zagnieżdżoną strukturę
         * "magazyn/2026/marzec" jednym wywołaniem Files.createDirectories().
         * Sprawdź exists() dla każdego z 3 poziomów. Posprzątaj po sobie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreateEmptyFile {
        /*
         * 🧪 Zadanie 3:
         * W katalogu tymczasowym utwórz pusty plik "pusty.dat" przez
         * Files.createFile(). Wypisz Files.size() (powinno być 0).
         * Posprzątaj po sobie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CheckExists {
        /*
         * 🧪 Zadanie 4:
         * Sprawdź Files.exists() dla pliku, który właśnie utworzysz,
         * oraz dla pliku "nieistniejacy.txt", którego nigdy nie tworzysz.
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriteReadString {
        /*
         * 🧪 Zadanie 5:
         * Zapisz do pliku "notatka.txt" tekst "Pierwsza notatka." przez
         * Files.writeString() (UTF-8), a następnie odczytaj przez
         * Files.readString() i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AppendText {
        /*
         * 🧪 Zadanie 6:
         * Do istniejącego pliku tekstowego dopisz drugą linię, używając
         * Files.writeString() z opcją StandardOpenOption.APPEND.
         * Wypisz całą zawartość po dopisaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReadAllLines {
        /*
         * 🧪 Zadanie 7:
         * Zapisz plik z 4 liniami tekstu (rozdzielonymi \n).
         * Odczytaj go metodą Files.readAllLines() i wypisz liczbę linii
         * oraz każdą linię z numerem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WriteReadBytes {
        /*
         * 🧪 Zadanie 8:
         * Zapisz tablicę bajtów {10, 20, 30, 40, 50} do pliku "dane.bin"
         * przez Files.write(). Odczytaj przez Files.readAllBytes()
         * i wypisz tablicę (Arrays.toString).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CopyFile {
        /*
         * 🧪 Zadanie 9:
         * Skopiuj plik tekstowy do "kopia.txt" przez Files.copy().
         * Spróbuj skopiować drugi raz bez opcji – złap
         * FileAlreadyExistsException, potem powtórz z
         * StandardCopyOption.REPLACE_EXISTING (powinno się udać).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MoveFile {
        /*
         * 🧪 Zadanie 10:
         * Przenieś plik "kopia.txt" do "przeniesiony.txt" przez Files.move().
         * Sprawdź, że stara ścieżka już nie istnieje, a nowa istnieje.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WalkAndCollectTxt {
        /*
         * 🧪 Zadanie 11:
         * W katalogu tymczasowym utwórz kilka plików o różnych rozszerzeniach
         * (.txt, .csv, .bin) w 2 podkatalogach. Użyj Files.walk() (w
         * try-with-resources), przefiltruj tylko pliki *.txt i wypisz ich
         * ścieżki względem katalogu głównego (relativize).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CountFilesAndDirs {
        /*
         * 🧪 Zadanie 12:
         * Zbuduj drzewo katalogów z plikami. Przy pomocy Files.walk()
         * policz osobno liczbę plików (Files.isRegularFile) i liczbę
         * katalogów (Files.isDirectory). Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TotalSizeOfTree {
        /*
         * 🧪 Zadanie 13:
         * Utwórz kilka plików o znanych rozmiarach (zapisz różne teksty).
         * Przejdź całe drzewo Files.walk() i zsumuj Files.size() wszystkich
         * plików. Wypisz sumę w bajtach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_DeleteVsDeleteIfExists {
        /*
         * 🧪 Zadanie 14:
         * Usuń nieistniejący plik metodą Files.delete() – złap
         * NoSuchFileException i wypisz komunikat. Następnie wywołaj
         * Files.deleteIfExists() na tym samym pliku – sprawdź, że zwraca
         * false bez wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CopyDirectoryShallow {
        /*
         * 🧪 Zadanie 15:
         * Utwórz katalog źródłowy z 3 plikami tekstowymi (bez podkatalogów).
         * Skopiuj wszystkie pliki (Files.list() + pętla + Files.copy())
         * do drugiego, pustego katalogu docelowego. Sprawdź, że oba katalogi
         * mają tę samą liczbę plików.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FindLargestFile {
        /*
         * 🧪 Zadanie 16:
         * Utwórz kilka plików o różnych rozmiarach (różne długości tekstu).
         * Przejdź drzewo Files.walk() i znajdź ścieżkę pliku o największym
         * Files.size(). Wypisz jego nazwę i rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ListDirectoryOnly {
        /*
         * 🧪 Zadanie 17:
         * Utwórz katalog z podkatalogiem i kilkoma plikami na obu poziomach.
         * Porównaj wynik Files.list(katalog) (tylko jeden poziom) z wynikiem
         * Files.walk(katalog) (całe drzewo) – wypisz oba zestawy i policz
         * różnicę w liczbie elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RenameExtension {
        /*
         * 🧪 Zadanie 18:
         * Utwórz 3 pliki "*.txt" w katalogu. Przejdź Files.walk(), znajdź
         * pliki kończące się na ".txt" i zmień im rozszerzenie na ".bak"
         * przez Files.move() (resolveSibling na nową nazwę).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CreateManyTempFiles {
        /*
         * 🧪 Zadanie 19:
         * W pętli utwórz 10 plików tymczasowych przez Files.createTempFile()
         * w jednym katalogu. Zweryfikuj Files.list() zwraca dokładnie 10
         * elementów, a potem usuń je wszystkie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RecursiveDelete {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj drzewo katalogów z plikami na 3 poziomach zagnieżdżenia.
         * Usuń całe drzewo metodą Files.walk() + sorted(Comparator.reverseOrder())
         * + Files.delete() (od najgłębszych elementów). Zweryfikuj, że katalog
         * główny już nie istnieje.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DirectoryTreePrinter {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj wielopoziomowe drzewo katalogów i plików w katalogu
         * tymczasowym. Napisz metodę printTree(Path root), która wypisuje
         * całą strukturę z wcięciem proporcjonalnym do głębokości
         * (jak polecenie "tree"), np. używając Files.walk() posortowanego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DuplicateFileFinder {
        /*
         * 🧪 Zadanie 22:
         * Utwórz kilka plików, wśród których 2-3 mają identyczną zawartość
         * (skopiowane). Napisz metodę findDuplicates(Path root), która
         * przechodzi Files.walk(), porównuje zawartości (Files.readAllBytes()
         * + Arrays.equals lub grupowanie po zawartości) i zwraca grupy
         * duplikatów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BackupUtility {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj katalog źródłowy z podkatalogami i plikami. Napisz metodę
         * backup(Path source, Path targetBackupDir), która kopiuje CAŁĄ
         * strukturę (katalogi + pliki) zachowując układ, używając
         * Files.walk() + Files.createDirectories() + Files.copy().
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_LogRotator {
        /*
         * 🧪 Zadanie 24:
         * Symulacja rotacji logów: masz "app.log" z zawartością. Napisz
         * metodę rotate(Path logFile, int maxBackups), która przesuwa
         * app.log.2 -> app.log.3, app.log.1 -> app.log.2, app.log -> app.log.1
         * (Files.move z REPLACE_EXISTING), a potem tworzy nowy pusty app.log.
         * Przetestuj wywołując rotate() kilka razy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WordCountAcrossFiles {
        /*
         * 🧪 Zadanie 25:
         * Utwórz kilka plików *.txt z różną zawartością tekstową w drzewie
         * katalogów. Przy pomocy Files.walk() + Files.readString() policz
         * łączną liczbę słów we wszystkich plikach *.txt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FileFilterBySize {
        /*
         * 🧪 Zadanie 26:
         * Utwórz zestaw plików o różnych rozmiarach (różna długość treści).
         * Napisz metodę findFilesLargerThan(Path root, long minBytes), która
         * zwraca listę ścieżek plików większych niż podany próg, używając
         * Files.walk() i Files.size().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SyncDirectories {
        /*
         * 🧪 Zadanie 27:
         * Utwórz katalog "source" i "target" z częściowo pokrywającą się
         * zawartością (niektóre pliki tylko w source, niektóre w obu z
         * różną treścią). Napisz metodę sync(Path source, Path target),
         * która kopiuje do target pliki brakujące lub o innej zawartości
         * (porównanie przez Files.readAllBytes()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TempWorkspaceManager {
        /*
         * 🧪 Zadanie 28:
         * Napisz klasę TempWorkspace z metodami: setup() (tworzy katalog
         * tymczasowy), addFile(String name, String content) (tworzy plik
         * tekstowy w workspace), listFiles() (zwraca listę nazw plików),
         * cleanup() (usuwa cały workspace rekurencyjnie). Zademonstruj
         * pełny cykl życia obiektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CsvMerger {
        /*
         * 🧪 Zadanie 29:
         * Utwórz 3 pliki CSV z tym samym nagłówkiem w pierwszej linii i
         * różnymi wierszami danych. Napisz metodę mergeCsvFiles(Path dir,
         * Path outputFile), która znajduje wszystkie *.csv (Files.walk()),
         * zapisuje nagłówek raz, a potem dopisuje wiersze danych ze
         * wszystkich plików (bez powtarzania nagłówka) do jednego pliku
         * wynikowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ArchiveOldFiles {
        /*
         * 🧪 Zadanie 30:
         * Utwórz katalog z plikami, których nazwy zawierają rok, np.
         * "raport_2023.txt", "raport_2024.txt", "raport_2026.txt".
         * Napisz metodę archiveOlderThan(Path dir, int year), która
         * tworzy podkatalog "archiwum" (Files.createDirectories()) i
         * przenosi (Files.move()) tam wszystkie pliki z rokiem w nazwie
         * mniejszym niż podany próg. Zweryfikuj wynik przez Files.list().
         */
        public static void main(String[] args) { }
    }
}
