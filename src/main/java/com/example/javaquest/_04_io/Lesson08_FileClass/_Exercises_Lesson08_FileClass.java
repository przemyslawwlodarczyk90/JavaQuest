package com.example.javaquest._04_io.Lesson08_FileClass;

public class _Exercises_Lesson08_FileClass {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CheckExists {
        /*
         * 🧪 Zadanie 1:
         * Utwórz obiekt File wskazujący na nieistniejący plik
         * "cwiczenie01.txt" w katalogu tymczasowym
         * (System.getProperty("java.io.tmpdir")) i sprawdź exists()
         * – powinno zwrócić false. Następnie faktycznie utwórz plik
         * (createNewFile()) i sprawdź exists() ponownie – powinno być true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MkdirSingle {
        /*
         * 🧪 Zadanie 2:
         * Utwórz obiekt File dla pojedynczego katalogu
         * "cwiczenie02_katalog" wewnątrz katalogu tymczasowego i wywołaj
         * mkdir(). Sprawdź wynik wywołania oraz exists() po utworzeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_MkdirsNested {
        /*
         * 🧪 Zadanie 3:
         * Utwórz obiekt File dla zagnieżdżonej ścieżki
         * "cwiczenie03/podkatalog/glebiej" (rodzice nie istnieją). Porównaj
         * wynik wywołania mkdir() (powinno zwrócić false, bo rodzice nie
         * istnieją) z wynikiem mkdirs() (powinno zwrócić true i utworzyć
         * całą ścieżkę).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IsFileIsDirectory {
        /*
         * 🧪 Zadanie 4:
         * Utwórz jeden plik i jeden katalog wewnątrz katalogu
         * tymczasowego. Sprawdź dla obu isFile() i isDirectory() i wypisz
         * wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_GetAbsolutePathAndName {
        /*
         * 🧪 Zadanie 5:
         * Utwórz obiekt File wskazujący na plik "dane.txt" wewnątrz
         * katalogu tymczasowego. Wypisz getName(), getAbsolutePath()
         * i getParent().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateAndDeleteFile {
        /*
         * 🧪 Zadanie 6:
         * Utwórz nowy pusty plik metodą createNewFile(), sprawdź exists()
         * (powinno być true). Następnie usuń plik metodą delete()
         * i sprawdź exists() ponownie (powinno być false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FileLength {
        /*
         * 🧪 Zadanie 7:
         * Utwórz plik i zapisz do niego kilka linii tekstu (FileWriter).
         * Sprawdź jego rozmiar w bajtach metodą length() i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListFilesBasic {
        /*
         * 🧪 Zadanie 8:
         * Utwórz katalog z 4 plikami wewnątrz o różnych rozszerzeniach
         * (np. "a.txt", "b.log", "c.csv", "d.txt"). Wywołaj listFiles()
         * bez filtra i wypisz nazwy wszystkich znalezionych plików.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_RenameToBasic {
        /*
         * 🧪 Zadanie 9:
         * Utwórz plik "stary.txt", zmień jego nazwę na "nowy.txt" metodą
         * renameTo(). Sprawdź exists() dla obu nazw przed i po operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DeleteEmptyVsNonEmptyDir {
        /*
         * 🧪 Zadanie 10:
         * Utwórz pusty katalog oraz osobny katalog zawierający jeden
         * plik wewnątrz. Wywołaj delete() na obu katalogach i porównaj
         * wyniki – pusty katalog powinien się usunąć, niepusty nie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FileFilterExtension {
        /*
         * 🧪 Zadanie 11:
         * Utwórz katalog z 6 plikami o różnych rozszerzeniach (.txt,
         * .log, .csv, wymieszane). Użyj listFiles(FileFilter) z lambdą
         * filtrującą tylko pliki .log i wypisz ich nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FileFilterSize {
        /*
         * 🧪 Zadanie 12:
         * Utwórz kilka plików o różnej zawartości (a więc różnym
         * rozmiarze). Użyj listFiles(FileFilter), by wybrać tylko pliki
         * większe niż określony rozmiar w bajtach (np. 50 bajtów), wypisz
         * ich nazwy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CountFilesVsDirectories {
        /*
         * 🧪 Zadanie 13:
         * W katalogu z mieszaną zawartością (kilka plików i kilka
         * podkatalogów) policz osobno liczbę plików i liczbę katalogów,
         * używając listFiles() oraz isFile()/isDirectory(). Wypisz obie
         * liczby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RecursiveListing {
        /*
         * 🧪 Zadanie 14:
         * Utwórz wielopoziomową strukturę katalogów (katalog główny
         * z 2 podkatalogami, każdy z kilkoma plikami). Napisz metodę
         * rekurencyjną wypisującą WSZYSTKIE pliki na wszystkich
         * poziomach, z wcięciem odpowiadającym głębokości zagnieżdżenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RecursiveDeleteImplementation {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj samodzielnie metodę
         * deleteDirectoryRecursively(File dir) usuwającą katalog wraz
         * z całą zawartością (pliki i podkatalogi). Przetestuj na
         * katalogu zawierającym pliki i zagnieżdżone podkatalogi,
         * zweryfikuj exists() po usunięciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FindLargestFile {
        /*
         * 🧪 Zadanie 16:
         * W katalogu z kilkoma plikami o różnej zawartości znajdź plik
         * o największym rozmiarze (length()), używając listFiles()
         * i porównania rozmiarów. Wypisz nazwę i rozmiar tego pliku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RenameWithCollisionCheck {
        /*
         * 🧪 Zadanie 17:
         * Przed wywołaniem renameTo() sprawdź, czy plik docelowy już
         * istnieje (exists()). Jeśli tak, wypisz ostrzeżenie i NIE
         * wykonuj zmiany nazwy; jeśli nie, wykonaj renameTo()
         * i zweryfikuj wynik. Przetestuj oba scenariusze (kolizja
         * i brak kolizji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MoveFileBetweenDirectories {
        /*
         * 🧪 Zadanie 18:
         * Utwórz dwa katalogi (źródłowy i docelowy) oraz plik w katalogu
         * źródłowym. Użyj renameTo(), by "przenieść" plik do katalogu
         * docelowego (podając File ze zmienioną ścieżką rodzica).
         * Zweryfikuj, że plik zniknął ze źródłowego i pojawił się
         * w docelowym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FilterByNamePattern {
        /*
         * 🧪 Zadanie 19:
         * Użyj listFiles(FileFilter) z lambdą sprawdzającą, czy nazwa
         * pliku zaczyna się od konkretnego prefiksu (np. "raport_").
         * Przetestuj na katalogu z mieszanymi nazwami plików (część
         * pasujących, część nie) i wypisz tylko pasujące.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DirectorySizeCalculation {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę obliczającą łączny rozmiar wszystkich plików
         * w katalogu i jego podkatalogach (rekurencyjnie) w bajtach,
         * zwracającą sumę. Przetestuj na strukturze z kilkoma plikami
         * i przynajmniej jednym podkatalogiem, wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DuplicateFileFinder {
        /*
         * 🧪 Zadanie 21:
         * W katalogu z kilkoma plikami tekstowymi (niektóre o identycznej
         * zawartości, inne różnej) znajdź pary plików o TAKIEJ SAMEJ
         * zawartości (porównując np. długość pliku oraz treść) i wypisz
         * je jako pary duplikatów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FileTreeVisualizer {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj metodę rekurencyjną wypisującą strukturę
         * katalogów w formie "drzewa" tekstowego (z wcięciami, np.
         * używając myślników "- " lub znaków "├── "/"└── "), dla struktury
         * katalogów z co najmniej 2 poziomami zagnieżdżenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_OrganizeFilesByExtension {
        /*
         * 🧪 Zadanie 23:
         * Mając katalog z plikami o różnych rozszerzeniach (.txt, .log,
         * .csv, .jpg), napisz program, który tworzy podkatalogi o nazwach
         * rozszerzeń (mkdirs) i PRZENOSI (renameTo) każdy plik do
         * odpowiadającego mu podkatalogu. Zweryfikuj wynikową strukturę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CleanupOldFiles {
        /*
         * 🧪 Zadanie 24:
         * Utwórz kilka plików i ustaw im różne czasy modyfikacji metodą
         * setLastModified() (np. część "starsza niż 7 dni" – odejmij dni
         * wyrażone w milisekundach od aktualnego czasu). Napisz metodę
         * usuwającą pliki starsze niż podana liczba dni (na podstawie
         * lastModified()), wypisz które pliki zostały usunięte.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BackupBeforeOverwrite {
        /*
         * 🧪 Zadanie 25:
         * Napisz metodę safeWrite(File target, String content), która
         * przed nadpisaniem istniejącego pliku najpierw tworzy jego kopię
         * zapasową (np. renameTo pliku na "nazwa.bak" albo skopiowanie
         * zawartości), a dopiero potem zapisuje nową zawartość do
         * oryginalnego pliku. Przetestuj i zweryfikuj obecność kopii
         * zapasowej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DiskUsageReportByExtension {
        /*
         * 🧪 Zadanie 26:
         * Przeanalizuj katalog (rekurencyjnie) i zbuduj raport
         * Map<String,Long> mapujący rozszerzenie pliku na łączny rozmiar
         * w bajtach wszystkich plików o tym rozszerzeniu. Wypisz raport
         * posortowany malejąco według rozmiaru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SynchronizeDirectories {
        /*
         * 🧪 Zadanie 27:
         * Mając dwa katalogi (źródłowy z kilkoma plikami, docelowy pusty
         * lub częściowo wypełniony), napisz metodę porównującą listy
         * plików (po nazwie) i wypisującą: pliki istniejące TYLKO
         * w źródłowym (do skopiowania), TYLKO w docelowym (nadmiarowe)
         * oraz istniejące w obu katalogach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FileNameSanitizer {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę renameSanitized(File dir), która przechodzi po
         * wszystkich plikach w katalogu i zmienia (renameTo) nazwy
         * zawierające spacje lub polskie znaki diakrytyczne na wersję
         * "bezpieczną" (spacje -> podkreślniki, ą->a, ę->e, ż->z itd.),
         * zachowując rozszerzenie pliku. Przetestuj na plikach z nazwami
         * typu "raport końcowy.txt".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_EmptyDirectoryPruner {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj rekurencyjną metodę usuwającą WSZYSTKIE puste
         * podkatalogi (katalogi bez żadnych plików, które mogą zawierać
         * inne puste podkatalogi) wewnątrz podanego katalogu głównego,
         * pozostawiając nietknięte katalogi zawierające jakiekolwiek
         * pliki. Przetestuj na strukturze z mieszanką pustych
         * i niepustych podkatalogów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FileSystemSnapshotDiff {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj mechanizm "migawki" (snapshot) stanu katalogu:
         * zbierz mapę Map<String,Long> (ścieżka względna pliku -> np.
         * rozmiar+lastModified jako wartość porównawcza) przed i po
         * wprowadzeniu zmian (dodanie/usunięcie/modyfikacja plików).
         * Napisz metodę porównującą dwie migawki i wypisującą listę
         * plików DODANYCH, USUNIĘTYCH i ZMODYFIKOWANYCH między nimi.
         */
        public static void main(String[] args) { }
    }
}
