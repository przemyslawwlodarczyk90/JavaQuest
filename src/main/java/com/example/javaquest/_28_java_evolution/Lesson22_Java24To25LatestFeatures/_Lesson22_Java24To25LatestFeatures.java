package com.example.javaquest._28_java_evolution.Lesson22_Java24To25LatestFeatures;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class _Lesson22_Java24To25LatestFeatures {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 22: Java 24-25 (2025) - NAJNOWSZE funkcje, zweryfikowane WebSearch 2026-07-20 ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - fakty ZWERYFIKOWANE WebSearch (NIE Z pamieci)
         * ============================================================
         * Java 24 (marzec 2025, NIE-LTS):
         * - JEP 485 - Stream Gatherers OSTATECZNIE FINALNE (PO 2 rundach
         *   preview W 22-23, Lesson21) - `Stream.gather(Gatherer)`
         *   DZIALA BEZ `--enable-preview`.
         * - Scoped Values - 4. runda preview (JESZCZE NIE finalne).
         * - Structured Concurrency - KONTYNUACJA preview.
         *
         * Java 25 (wrzesien 2025, LTS - PIERWSZA OD Javy 21!):
         * - JEP 506 - Scoped Values OSTATECZNIE FINALNE.
         * - JEP 511 - Module Import Declarations FINALNE (`import module
         *   java.base;` - IMPORTUJE WSZYSTKIE pakiety modulu naraz).
         * - JEP 512 - Compact Source Files and Instance Main Methods
         *   FINALNE (uproszczony "Hello World" DLA POCZATKUJACYCH - BEZ
         *   `public class`/`static`/`String[] args`).
         * - JEP 513 - Flexible Constructor Bodies FINALNE (kod PRZED
         *   `super(...)`, jesli NIE odwoluje sie DO `this`).
         * - Structured Concurrency: **NADAL preview** (JEP 505, 5. runda)
         *   - NIE zostala sfinalizowana NAWET W Javie 25 (WBREW
         *     wczesniejszemu zalozeniu Z planu tego rozdzialu W
         *     CLAUDE.md - PRAWDA jest ZASKAKUJACO inna: mimo 5 rund
         *     preview OD Javy 19, JVM ZESPOL NADAL zbiera feedback).
         *
         * 🔍 KAZDA Z tych funkcji WYMAGA `--release 24` LUB `25` -
         * PROJEKT (baseline: 21) uzywa TEGO SAMEGO wzorca podprocesu
         * co Lesson21.
         */
        System.out.println("Java 24 (marzec 2025): Stream Gatherers FINALNE (JEP 485). Scoped Values/Structured Concurrency nadal preview.");
        System.out.println("Java 25 (wrzesien 2025, LTS): Scoped Values (JEP 506), Module Imports (JEP 511), Compact Source Files (JEP 512), Flexible Constructors (JEP 513) FINALNE.");
        System.out.println("NIESPODZIANKA (zweryfikowane WebSearch): Structured Concurrency JEST WCIAZ preview NAWET W Javie 25 (JEP 505, 5. runda)!");

        Path workDir = Files.createTempDirectory("lesson22-java24-25");
        try {
            demonstrateStreamGatherersFinalizedInJava24(workDir);
            demonstrateScopedValuesFinalizedInJava25(workDir);
            demonstrateCompactSourceFilesInJava25(workDir);
        } finally {
            deleteRecursively(workDir);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Stream Gatherers (Java 24) - WLASNE, POSREDNIE operacje
         *   strumieniowe (`windowFixed`, `windowSliding`, WLASNE
         *   implementacje `Gatherer`).
         * - Scoped Values (Java 25) - BEZPIECZNE dzielenie NIEZMIENNYCH
         *   danych MIEDZY watkami (WLACZNIE Z wirtualnymi) - ZAMIENNIK
         *   `ThreadLocal` UNIKAJACY jego pulapek (mutowalnosc,
         *   wyciek pamieci PRZY zle zarzadzanych pulach watkow).
         * - Compact Source Files (Java 25) - "Hello World" BEZ
         *   `public class`/`static void main(String[] args)` -
         *   ULATWIA PIERWSZE kroki W nauce Javy (powiazanie Z
         *   `_01_fundamentals/Lesson01_HelloWorld`).
         * - Structured Concurrency - WCIAZ preview NAWET W Javie 25 -
         *   PRZYKLAD, ze NIE KAZDA funkcja stabilizuje sie SZYBKO
         *   (5 rund preview OD Javy 19 DO 25!).
         */
        System.out.println("\n=== KONIEC LEKCJI 22 ===");
    }

    private static void demonstrateStreamGatherersFinalizedInJava24(Path workDir) throws IOException, InterruptedException {
        System.out.println("\n--- Stream Gatherers (JEP 485, FINALNE w Javie 24) - subprocess z --release 24 ---");

        Path srcDir = workDir.resolve("src-gatherers");
        Path classesDir = workDir.resolve("classes-gatherers");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path zrodlo = srcDir.resolve("GatherersDemo.java");
        Files.writeString(zrodlo, """
                import java.util.List;
                import java.util.stream.Gatherers;

                public class GatherersDemo {
                    public static void main(String[] args) {
                        List<Integer> liczby = List.of(1, 2, 3, 4, 5, 6, 7);

                        // windowFixed(3) - dzieli strumien NA STALE okna po 3 elementy (ostatnie MOZE byc krotsze).
                        List<List<Integer>> okna = liczby.stream()
                                .gather(Gatherers.windowFixed(3))
                                .toList();

                        System.out.println("liczby.stream().gather(Gatherers.windowFixed(3)) -> " + okna);
                        System.out.println("Bez Gatherers TRZEBA by RECZNIE implementowac Collector/Spliterator - TERAZ jest to WBUDOWANA, gotowa operacja posrednia.");
                    }
                }
                """);

        runSubprocessDemo(zrodlo, classesDir, "24", "GatherersDemo");
    }

    private static void demonstrateScopedValuesFinalizedInJava25(Path workDir) throws IOException, InterruptedException {
        System.out.println("\n--- Scoped Values (JEP 506, FINALNE w Javie 25) - subprocess z --release 25 ---");

        Path srcDir = workDir.resolve("src-scoped");
        Path classesDir = workDir.resolve("classes-scoped");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path zrodlo = srcDir.resolve("ScopedValueDemo.java");
        Files.writeString(zrodlo, """
                import java.util.NoSuchElementException;

                public class ScopedValueDemo {

                    static final ScopedValue<String> IDENTYFIKATOR_ZADANIA = ScopedValue.newInstance();

                    public static void main(String[] args) throws InterruptedException {
                        Runnable zadanie = () -> {
                            System.out.println("Wewnatrz zadania, IDENTYFIKATOR_ZADANIA.get() = " + IDENTYFIKATOR_ZADANIA.get());
                            wykonajPodzadanie();
                        };

                        // ScopedValue.where(...).run(...) - wartosc widoczna TYLKO wewnatrz tego wywolania (i wywolan zagniezdzonych),
                        // NIEZMIENNA, automatycznie 'sprzatana' po wyjsciu z run() - BEZ recznego remove() jak przy ThreadLocal.
                        ScopedValue.where(IDENTYFIKATOR_ZADANIA, "zadanie-42").run(zadanie);

                        System.out.println("Poza zasiegiem run(): probojemy odczytac ponownie...");
                        try {
                            IDENTYFIKATOR_ZADANIA.get();
                        } catch (NoSuchElementException e) {
                            System.out.println("Zlapano " + e.getClass().getSimpleName() + " - wartosc NIE jest juz dostepna POZA zasiegiem 'run()'.");
                        }
                    }

                    static void wykonajPodzadanie() {
                        // ODCZYT z metody ZAGNIEZDZONEJ - wartosc WCIAZ widoczna, bo JESTESMY wewnatrz run().
                        System.out.println("Wewnatrz zagniezdzonego wywolania, IDENTYFIKATOR_ZADANIA.get() = " + IDENTYFIKATOR_ZADANIA.get());
                    }
                }
                """);

        runSubprocessDemo(zrodlo, classesDir, "25", "ScopedValueDemo");

        System.out.println("Zaleta wobec ThreadLocal (_05_multithreading/Lesson30): NIEZMIENNOSC (BRAK set() PO utworzeniu), automatyczne czyszczenie, BEZPIECZNE dla watkow wirtualnych (Lesson19).");
    }

    private static void demonstrateCompactSourceFilesInJava25(Path workDir) throws IOException, InterruptedException {
        System.out.println("\n--- Compact Source Files and Instance Main Methods (JEP 512, FINALNE w Javie 25) - subprocess z --release 25 ---");

        Path srcDir = workDir.resolve("src-compact");
        Path classesDir = workDir.resolve("classes-compact");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path zrodlo = srcDir.resolve("CompactHello.java");
        Files.writeString(zrodlo, """
                void main() {
                    IO.println("Witaj z Compact Source File!");
                    IO.println("BEZ 'public class CompactHello', BEZ 'static', BEZ 'String[] args' - idealne DLA PIERWSZEJ lekcji Javy.");
                }
                """);

        String javaHome = System.getProperty("java.home");
        String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

        System.out.println("UWAGA: Compact Source Files sa URUCHAMIANE BEZPOSREDNIO przez 'java Plik.java' (jak JEP 330 z Lesson09), BEZ osobnego kroku 'javac'.");
        runProcess("java --release 25 (Compact Source File, uruchomienie bezposrednie)",
                javaExe, "--source", "25", zrodlo.toString());

        System.out.println("Nowa klasa 'java.lang.IO' (JEP 512) - NIEJAWNIE dostepna W kazdym Compact Source File, ale JEJ metody statyczne WYMAGAJA kwalifikacji ('IO.println', NIE samo 'println') - inaczej blad kompilacji 'cannot find symbol'.");
        System.out.println("Powiazanie: `_01_fundamentals/Lesson01_HelloWorld` uczyl PELNEJ skladni ('public class' + 'public static void main(String[] args)') - TA funkcja jest PRZEZNACZONA GLOWNIE DLA nauki/skryptow, NIE dla duzych, wieloplikowych projektow produkcyjnych.");
    }

    private static void runSubprocessDemo(Path zrodlo, Path classesDir, String release, String glownaKlasa) throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javacExe = javaHome + File.separator + "bin" + File.separator + "javac";
        String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

        int compileExit = runProcess("javac --release " + release + " (" + glownaKlasa + ")",
                javacExe, "--release", release, "-d", classesDir.toString(), zrodlo.toString());
        System.out.println("Wynik kompilacji (0 = sukces): " + compileExit);

        runProcess("java (" + glownaKlasa + ")", javaExe, "-cp", classesDir.toString(), glownaKlasa);
    }

    private static int runProcess(String description, String... command) throws IOException, InterruptedException {
        System.out.println(">>> [" + description + "] " + String.join(" ", command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output;
        try (InputStream in = process.getInputStream()) {
            output = new String(in.readAllBytes());
        }
        if (!output.isEmpty()) {
            System.out.print(output);
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            System.out.println("(TIMEOUT - proces zabity)");
            return -1;
        }
        return process.exitValue();
    }

    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) {
            return;
        }
        try (Stream<Path> walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder()).forEach(sciezka -> {
                try {
                    Files.deleteIfExists(sciezka);
                } catch (IOException ignored) {
                    // sprzatanie katalogu tymczasowego - blad pomijalny w demo
                }
            });
        }
    }
}
