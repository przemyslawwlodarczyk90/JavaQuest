package com.example.javaquest._04_io.Lesson09_PathAndPaths;

public class _Exercises_Lesson09_PathAndPaths {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreatePath {
        /*
         * 🧪 Zadanie 1:
         * Utwórz ścieżkę "dane/raporty/2026/luty.txt" na dwa sposoby:
         * Path.of("dane", "raporty", "2026", "luty.txt") oraz
         * Paths.get("dane/raporty/2026/luty.txt").
         * Wypisz obie i sprawdź p1.equals(p2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AbsoluteVsRelative {
        /*
         * 🧪 Zadanie 2:
         * Utwórz ścieżkę względną Path.of("dane", "plik.txt") oraz bezwzględną
         * Path.of("C:\\Users\\student\\plik.txt").
         * Wypisz isAbsolute() dla obu oraz toAbsolutePath() dla ścieżki względnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ResolveBasic {
        /*
         * 🧪 Zadanie 3:
         * base = Path.of("projekt", "src").
         * Doklej "main/Main.java" przez resolve() i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ResolveAbsoluteTrap {
        /*
         * 🧪 Zadanie 4:
         * base = Path.of("projekt", "src").
         * Wywołaj base.resolve(Path.of("D:\\inny\\katalog")).
         * Wypisz wynik i skomentuj (jako System.out.println) dlaczego "base"
         * został zignorowany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ResolveSibling {
        /*
         * 🧪 Zadanie 5:
         * base = Path.of("projekt", "src").
         * Użyj resolveSibling("test") i wypisz wynik ("projekt/test").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NormalizeDots {
        /*
         * 🧪 Zadanie 6:
         * Utwórz Path.of("a/./b/../b/c/../../d").
         * Wypisz ścieżkę przed i po normalize().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_Relativize {
        /*
         * 🧪 Zadanie 7:
         * from = Path.of("C:\\projekt\\src\\test").
         * to   = Path.of("C:\\projekt\\resources\\dane.csv").
         * Wypisz from.relativize(to).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_FileNameParentRoot {
        /*
         * 🧪 Zadanie 8:
         * file = Path.of("C:\\projekt\\src\\main\\App.java").
         * Wypisz getFileName(), getParent(), getRoot().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NoParentCase {
        /*
         * 🧪 Zadanie 9:
         * noParent = Path.of("plik.txt").
         * Wypisz getParent() (powinno być null) oraz obsłuż to bez NullPointerException
         * przy dalszym wypisywaniu (np. "brak rodzica").
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IterateSegments {
        /*
         * 🧪 Zadanie 10:
         * toIterate = Path.of("C:\\projekt\\src\\main\\resources\\app.properties").
         * Iteruj po segmentach pętlą for-each i wypisz każdy z numerem indeksu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_SubpathAndNameCount {
        /*
         * 🧪 Zadanie 11:
         * path = Path.of("C:\\projekt\\src\\main\\java\\App.java").
         * Wypisz getNameCount(), getName(0), subpath(1, 4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildPathFromArray {
        /*
         * 🧪 Zadanie 12:
         * String[] segments = {"a", "b", "c", "plik.txt"}.
         * Zbuduj Path krok po kroku, doklejając kolejne segmenty metodą resolve()
         * w pętli, zaczynając od Path.of("root"). Wypisz wynik końcowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CompareNormalizedPaths {
        /*
         * 🧪 Zadanie 13:
         * p1 = Path.of("a/b/../b/c").normalize().
         * p2 = Path.of("a/b/c").normalize().
         * Sprawdź p1.equals(p2) i wypisz obie ścieżki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RelativizeRoundTrip {
        /*
         * 🧪 Zadanie 14:
         * a = Path.of("C:\\projekt\\src").
         * b = Path.of("C:\\projekt\\resources\\config.txt").
         * rel = a.relativize(b). Sprawdź, że a.resolve(rel).normalize()
         * daje z powrotem b (equals).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExtractExtension {
        /*
         * 🧪 Zadanie 15:
         * Dla ścieżek: "raport.docx", "archiwum.tar.gz", "bezrozszerzenia".
         * Napisz sposób na wyciągnięcie rozszerzenia z getFileName().toString()
         * (operacje na String, np. lastIndexOf('.')). Wypisz wynik dla każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ChangeExtension {
        /*
         * 🧪 Zadanie 16:
         * original = Path.of("dane", "raport.txt").
         * Użyj resolveSibling(), żeby zamienić nazwę na "raport.csv".
         * Wypisz oryginał i nową ścieżkę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CommonPrefix {
        /*
         * 🧪 Zadanie 17:
         * a = Path.of("projekt", "src", "main", "java", "App.java").
         * b = Path.of("projekt", "src", "test", "java", "AppTest.java").
         * Napisz kod znajdujący liczbę wspólnych początkowych segmentów (tu: 2 –
         * "projekt", "src"), porównując element po elemencie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DepthCalculator {
        /*
         * 🧪 Zadanie 18:
         * Dla trzech ścieżek o różnej głębokości oblicz "głębokość" jako
         * getNameCount() (bez roota) i wypisz je posortowane od najpłytszej
         * do najgłębszej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PathValidator {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę isWindowsAbsolutePath(String s), która sprawdza (przez
         * Path.of(s).isAbsolute() oraz getRoot() != null), czy dany String
         * wygląda na bezwzględną ścieżkę Windows. Przetestuj na kilku przykładach,
         * np. "C:\\dane", "dane\\plik.txt", "\\\\serwer\\udzial".
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildRelativeLink {
        /*
         * 🧪 Zadanie 20:
         * Symulacja generowania linku względnego między dwoma "stronami":
         * strona1 = Path.of("site", "blog", "post1.html").
         * strona2 = Path.of("site", "assets", "style.css").
         * Wylicz relativize z perspektywy katalogu-rodzica strona1 do strona2
         * (czyli link, jaki trzeba wstawić w HTML na stronie1).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DirectoryTreeSimulation {
        /*
         * 🧪 Zadanie 21:
         * Zdefiniuj listę kilkunastu "wirtualnych" Path (bez dostępu do dysku),
         * reprezentujących drzewo katalogów projektu (np. różne głębokości
         * pod "projekt/..."). Wypisz każdą ścieżkę z wcięciem proporcjonalnym
         * do getNameCount() (np. 2 spacje na poziom), symulując wygląd drzewa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PathNormalizerUtility {
        /*
         * 🧪 Zadanie 22:
         * Napisz metodę normalizeToAbsolute(String messyPath), która przyjmuje
         * "brudny" String (z "." i ".." oraz mieszanymi separatorami przez
         * wieloargumentowy Path.of), zwraca znormalizowaną ścieżkę bezwzględną
         * (toAbsolutePath().normalize()). Przetestuj na kilku przykładach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RelativeLinkGenerator {
        /*
         * 🧪 Zadanie 23:
         * Mając mapę "stron" (nazwa -> Path) reprezentującą strukturę strony
         * WWW (np. 5-6 podstron w różnych podkatalogach), napisz metodę
         * generateLink(String fromPage, String toPage), która zwraca relatywny
         * link (String) między dwiema dowolnymi stronami z mapy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SafePathJoin {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę safeResolve(Path base, String userInput), która
         * dokleja userInput do base, normalizuje wynik i RZUCA wyjątek
         * (np. SecurityException), jeśli znormalizowany wynik "wychodzi"
         * poza base (np. userInput = "../../etc/passwd"). Przetestuj na
         * bezpiecznym i niebezpiecznym wejściu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PathComparator {
        /*
         * 🧪 Zadanie 25:
         * Mając listę kilku Path o różnej głębokości i nazwach, napisz
         * Comparator<Path> sortujący najpierw po getNameCount() (rosnąco),
         * a przy remisie alfabetycznie po toString(). Posortuj listę i wypisz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BreadcrumbBuilder {
        /*
         * 🧪 Zadanie 26:
         * Dla ścieżki Path.of("sklep", "kategoria", "podkategoria", "produkt.html")
         * zbuduj "breadcrumb" (okruszki) w formacie
         * "sklep > kategoria > podkategoria > produkt.html", iterując po
         * segmentach ścieżki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiRootRelativize {
        /*
         * 🧪 Zadanie 27:
         * a = Path.of("C:\\dane\\plik.txt").
         * b = Path.of("D:\\backup\\plik.txt").
         * Spróbuj a.relativize(b) – powinno rzucić IllegalArgumentException
         * (różne roota). Złap wyjątek i wypisz przyjazny komunikat zamiast
         * pozwolić programowi się wysypać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PathBasedRouter {
        /*
         * 🧪 Zadanie 28:
         * Napisz bardzo prosty "router" – metodę matches(Path pattern, Path actual),
         * gdzie segment "*" w pattern pasuje do dowolnego segmentu w actual
         * (liczba segmentów musi się zgadzać). Np. pattern
         * Path.of("api", "*", "details") pasuje do Path.of("api", "42", "details").
         * Przetestuj na kilku dopasowaniach i niedopasowaniach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DuplicateSegmentDetector {
        /*
         * 🧪 Zadanie 29:
         * Napisz metodę hasDuplicateSegment(Path path), która sprawdza, czy
         * jakaś nazwa katalogu powtarza się w ścieżce (np.
         * Path.of("a", "b", "a", "c") -> true, bo "a" powtórzone).
         * Przetestuj na kilku ścieżkach z duplikatem i bez.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MiniFileSystemSimulator {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj klasę pomocniczą symulującą mini system plików WYŁĄCZNIE na
         * obiektach Path (bez dotykania dysku): metody resolvePath(String base,
         * String... segments), normalizedEquals(Path a, Path b) oraz
         * relativeLink(Path from, Path to). Zademonstruj działanie wszystkich
         * trzech metod na zestawie przykładowych ścieżek.
         */
        public static void main(String[] args) { }
    }
}
