package com.example.javaquest._04_io.Lesson09_PathAndPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

public class _Lesson09_PathAndPaths {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📦 PATH I PATHS – REPREZENTACJA ŚCIEŻEK W JAVA NIO.2
         * ============================================================
         * Od Javy 7 mamy pakiet java.nio.file (tzw. NIO.2), który wprowadza
         * interfejs Path jako nowoczesny zamiennik starej klasy java.io.File.
         *
         * Path NIE reprezentuje pliku ani katalogu na dysku – to tylko
         * reprezentacja ŚCIEŻKI (ciągu segmentów). Plik pod tą ścieżką
         * może w ogóle nie istnieć!
         *
         * Jak utworzyć Path?
         * - Path.of("...")       → nowoczesny sposób (Java 11+), zalecany
         * - Paths.get("...")     → starszy sposób (Java 7+), wciąż działa
         * Oba dają identyczny efekt – Path.of() jest tylko krótszym zapisem.
         */

        Path p1 = Path.of("dane", "raporty", "2026", "styczen.txt");
        Path p2 = Paths.get("dane/raporty/2026/styczen.txt");

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p1.equals(p2)? " + p1.equals(p2)); // true – ta sama ścieżka

        /*
         * ============================================================
         * 🔹 ŚCIEŻKI WZGLĘDNE VS BEZWZGLĘDNE
         * ============================================================
         * Ścieżka bezwzględna (absolute) – zaczyna się od korzenia
         * systemu plików (np. "C:\" na Windows, "/" na Unix).
         *
         * Ścieżka względna (relative) – jest liczona od bieżącego
         * katalogu roboczego (working directory) programu.
         */

        Path relative = Path.of("dane", "plik.txt");
        Path absoluteWindowsStyle = Path.of("C:\\Users\\student\\plik.txt");

        System.out.println("\nrelative: " + relative);
        System.out.println("relative.isAbsolute()? " + relative.isAbsolute()); // false

        System.out.println("absolute: " + absoluteWindowsStyle);
        System.out.println("absolute.isAbsolute()? " + absoluteWindowsStyle.isAbsolute()); // true (na Windows)

        // toAbsolutePath() – zamienia ścieżkę względną na bezwzględną,
        // doklejając bieżący katalog roboczy programu (user.dir)
        System.out.println("relative.toAbsolutePath(): " + relative.toAbsolutePath());

        /*
         * ============================================================
         * 🔍 RESOLVE() – SKŁADANIE ŚCIEŻEK
         * ============================================================
         * resolve(other) – "dokleja" other do bieżącej ścieżki, tak jakby
         * other był podkatalogiem/plikiem względem this.
         *
         * ⚠️ Jeśli other jest ścieżką BEZWZGLĘDNĄ, resolve() ZWRACA other
         * (ignoruje this)! To częsty błąd początkujących.
         */

        Path base = Path.of("projekt", "src");
        Path resolved = base.resolve("main/Main.java");
        System.out.println("\nbase: " + base);
        System.out.println("resolve(\"main/Main.java\"): " + resolved); // projekt/src/main/Main.java

        Path resolvedAbsolute = base.resolve(Path.of("C:\\inny\\katalog"));
        System.out.println("resolve(absolute): " + resolvedAbsolute); // C:\inny\katalog – base zignorowany!

        // resolveSibling() – zastępuje OSTATNI element ścieżki
        Path sibling = base.resolveSibling("test");
        System.out.println("resolveSibling(\"test\"): " + sibling); // projekt/test

        /*
         * ============================================================
         * 🔹 NORMALIZE() – CZYSZCZENIE ŚCIEŻKI
         * ============================================================
         * normalize() usuwa zbędne elementy "." (bieżący katalog)
         * oraz redukuje ".." (katalog nadrzędny), BEZ dostępu do dysku –
         * to czysto tekstowa operacja, nie sprawdza czy ścieżka istnieje.
         */

        Path messy = Path.of("projekt/./src/../src/main/./Main.java");
        System.out.println("\nprzed normalize: " + messy);
        System.out.println("po normalize: " + messy.normalize()); // projekt/src/main/Main.java

        Path goingUp = Path.of("a/b/../../c");
        System.out.println("a/b/../../c → normalize: " + goingUp.normalize()); // c

        /*
         * ============================================================
         * 🔍 RELATIVIZE() – ŚCIEŻKA WZGLĘDNA MIĘDZY DWOMA ŚCIEŻKAMI
         * ============================================================
         * a.relativize(b) – zwraca ścieżkę, która "prowadzi" od a do b.
         * Obie ścieżki muszą być tego samego typu (obie względne albo
         * obie bezwzględne), inaczej rzuci wyjątek.
         */

        Path from = Path.of("C:\\projekt\\src\\main");
        Path to = Path.of("C:\\projekt\\resources\\config.properties");

        Path relativeFromTo = from.relativize(to);
        System.out.println("\nfrom: " + from);
        System.out.println("to: " + to);
        System.out.println("relativize: " + relativeFromTo); // ..\..\resources\config.properties

        /*
         * ============================================================
         * 📌 GETFILENAME() I GETPARENT()
         * ============================================================
         * getFileName() → ostatni element ścieżki (nazwa pliku/katalogu)
         * getParent()   → wszystko poza ostatnim elementem (może być null!)
         */

        Path file = Path.of("C:\\projekt\\src\\main\\Main.java");
        System.out.println("\nfile: " + file);
        System.out.println("getFileName(): " + file.getFileName());   // Main.java
        System.out.println("getParent(): " + file.getParent());       // C:\projekt\src\main
        System.out.println("getRoot(): " + file.getRoot());           // C:\

        Path noParent = Path.of("plik.txt");
        System.out.println("noParent.getParent(): " + noParent.getParent()); // null!

        /*
         * ============================================================
         * 🔹 ITEROWANIE PO ELEMENTACH ŚCIEŻKI
         * ============================================================
         * Path implementuje Iterable<Path> – można iterować po jego
         * segmentach (bez roota, jeśli ścieżka jest bezwzględna).
         */

        Path toIterate = Path.of("C:\\projekt\\src\\main\\java\\Main.java");
        System.out.println("\nElementy ścieżki " + toIterate + ":");
        for (Path segment : toIterate) {
            System.out.println("  - " + segment);
        }
        // Uwaga: root ("C:\") NIE jest zwracany jako element iteracji!

        System.out.println("getNameCount(): " + toIterate.getNameCount()); // 5
        System.out.println("getName(0): " + toIterate.getName(0));         // projekt
        System.out.println("subpath(1, 3): " + toIterate.subpath(1, 3));   // src\main

        /*
         * ============================================================
         * ⚠️ RÓŻNICE WINDOWS VS UNIX – SEPARATORY
         * ============================================================
         * Windows używa "\" jako separatora, Unix/Linux/macOS używa "/".
         * Path automatycznie dostosowuje się do systemu, na którym
         * uruchamiany jest program (FileSystem.getSeparator()).
         *
         * ✅ ZASADA: NIGDY nie wpisuj na sztywno "\" ani "/" w kodzie
         * przenośnym – używaj Path.of(segment1, segment2, ...) z osobnymi
         * argumentami, a Java sama dobierze właściwy separator.
         */

        System.out.println("\nSeparator w tym systemie: '" +
                java.io.File.separator + "'"); // "\" na Windows, "/" na Unix

        // ✅ Dobrze – przenośne między systemami:
        Path portable = Path.of("dane", "2026", "raport.csv");
        System.out.println("Path.of(\"dane\",\"2026\",\"raport.csv\"): " + portable);

        // ❌ Źle – zadziała tylko na jednym systemie:
        // Path bad = Path.of("dane\\2026\\raport.csv"); // nie zadziała poprawnie na Unix
        // Path bad2 = Path.of("dane/2026/raport.csv");  // nie zadziała poprawnie na starym Windows API

        // Mimo to Path.of("dane/2026/raport.csv") w praktyce działa też na Windows,
        // bo Java NIO.2 rozumie "/" jako separator uniwersalnie – ale "\" na Unix
        // potraktuje jako zwykły znak w nazwie pliku, więc lepiej używać wersji z wieloma argumentami.

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Path.of(...) / Paths.get(...) → tworzenie ścieżki (bez dostępu do dysku)
         * isAbsolute() / toAbsolutePath() → względna vs bezwzględna
         * resolve(other)     → doklejanie ścieżki (uwaga na ścieżki bezwzględne!)
         * resolveSibling(x)  → podmiana ostatniego elementu
         * normalize()        → czyszczenie "." i ".." tekstowo
         * relativize(other)  → ścieżka "z A do B"
         * getFileName() / getParent() / getRoot() → rozbicie na części
         * iteracja for(Path segment : path) → przechodzenie po segmentach
         * Path jest niezależny od systemu – używaj Path.of(a, b, c),
         * a nie ręcznie sklejanych stringów ze slashami!
         */
    }
}
