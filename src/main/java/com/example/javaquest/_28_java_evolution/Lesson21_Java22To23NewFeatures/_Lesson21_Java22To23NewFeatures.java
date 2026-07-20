package com.example.javaquest._28_java_evolution.Lesson21_Java22To23NewFeatures;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class _Lesson21_Java22To23NewFeatures {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 21: Java 22-23 (2024) - unnamed variables/patterns, FFM API, Stream Gatherers ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - PRZEKRACZA baseline projektu (Java 21) - WYMAGA PODPROCESU
         * ============================================================
         * Projekt (`pom.xml`) celuje W Java 21 (`--release 21`) - kod
         * uzywajacy skladni Z Javy 22+ NIE SKOMPILUJE SIE bezposrednio
         * W `src/main/java` tego projektu. TA lekcja uzywa TEGO SAMEGO
         * wzorca "embeduj I NAPRAWDE uruchom W PODPROCESIE" co
         * `_14_advancedjava/Lesson27-28` (JPMS) I `_15_jvm_internals`
         * (child JVM Z flagami) - PRAWDZIWY `javac --release 22`
         * kompiluje ODDZIELNY plik zrodlowy W katalogu tymczasowym,
         * PRAWDZIWY `java` go URUCHAMIA - projekt glowny NIE ZMIENIA
         * swojego baseline.
         *
         * Java 22 (marzec 2024) FINALIZUJE 2 funkcje BEZ potrzeby
         * `--enable-preview`:
         * - JEP 456 - Unnamed Variables & Patterns (`_` JAKO
         *   "wyrzucam te wartosc, NIE potrzebuje jej nazywac").
         * - JEP 454 - Foreign Function & Memory API (`Arena`,
         *   `MemorySegment` - bezpieczny dostep DO pamieci POZA stertą
         *   Javy, ZASTEPUJE stary, NIEBEZPIECZNY JNI).
         * Java 23 (wrzesien 2024) KONTYNUUJE preview:
         * - JEP 473 - Stream Gatherers, DRUGA runda preview (finalne
         *   dopiero W Javie 24 - patrz Lesson22).
         */
        System.out.println("Java 22 (marzec 2024): Unnamed variables/patterns (JEP 456) i FFM API (JEP 454) FINALNE. Java 23: Stream Gatherers nadal preview.");

        Path workDir = Files.createTempDirectory("lesson21-java22");
        try {
            demonstrateUnnamedVariablesAndPatterns(workDir);
            demonstrateForeignFunctionAndMemoryApi(workDir);
        } finally {
            deleteRecursively(workDir);
        }

        explainStreamGatherersStatus();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `_` (unnamed variable) - UZYWANE, GDY wartosc MUSI
         *   istniec SKLADNIOWO (np. parametr lambdy, zmienna catch),
         *   ale NIGDY NIE JEST uzywana - JASNY sygnal DLA czytelnika
         *   "ta wartosc jest CELOWO ignorowana".
         * - FFM API (`Arena`/`MemorySegment`) - BEZPIECZNY dostep DO
         *   pamieci NATYWNEJ (POZA sterta Javy) - ZASTEPUJE stary,
         *   NIEBEZPIECZNY `sun.misc.Unsafe`/JNI.
         * - Stream Gatherers - NADAL preview W 22/23, FINALIZACJA
         *   dopiero W Javie 24 (JEP 485, Lesson22).
         * - Kazda funkcja Z tej lekcji WYMAGA `--release 22` (LUB
         *   WYZEJ) - NIE dziala W `src/main/java` tego projektu
         *   (baseline: 21) BEZ podprocesu.
         */
        System.out.println("\n=== KONIEC LEKCJI 21 ===");
    }

    private static void demonstrateUnnamedVariablesAndPatterns(Path workDir) throws IOException, InterruptedException {
        System.out.println("\n--- Unnamed variables & patterns (JEP 456, Java 22) - subprocess z --release 22 ---");

        Path srcDir = workDir.resolve("src-unnamed");
        Path classesDir = workDir.resolve("classes-unnamed");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path zrodlo = srcDir.resolve("UnnamedDemo.java");
        Files.writeString(zrodlo, """
                public class UnnamedDemo {

                    record Punkt(int x, int y) {}

                    public static void main(String[] args) {
                        // 1) Zmienna catch, ktorej NIGDY nie uzywamy - '_' zamiast 'e'.
                        try {
                            Integer.parseInt("nie-liczba");
                        } catch (NumberFormatException _) {
                            System.out.println("Zlapano blad parsowania (przyczyna CELOWO zignorowana przez '_').");
                        }

                        // 2) Record pattern, gdzie interesuje nas TYLKO 'x', nie 'y'.
                        Punkt p = new Punkt(10, 20);
                        if (p instanceof Punkt(var x, var _)) {
                            System.out.println("Interesuje nas TYLKO x=" + x + " (y celowo zignorowany przez '_')");
                        }

                        // 3) Petla for-each, gdzie element NIGDY nie jest czytany w ciele - liczymy tylko wystapienia.
                        java.util.List<String> zdarzenia = java.util.List.of("start", "krok", "koniec");
                        int liczbaZdarzen = 0;
                        for (String _ : zdarzenia) {
                            liczbaZdarzen++;
                        }
                        System.out.println("Policzono " + liczbaZdarzen + " zdarzen (element petli CELOWO nienazwany '_', bo nie jest uzywany w ciele): " + liczbaZdarzen);
                    }
                }
                """);

        String javaHome = System.getProperty("java.home");
        String javacExe = javaHome + File.separator + "bin" + File.separator + "javac";
        String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

        int compileExit = runProcess("javac --release 22 (unnamed variables/patterns)",
                javacExe, "--release", "22", "-d", classesDir.toString(), zrodlo.toString());
        System.out.println("Wynik kompilacji (0 = sukces): " + compileExit);

        runProcess("java (UnnamedDemo)", javaExe, "-cp", classesDir.toString(), "UnnamedDemo");

        System.out.println("W Javie 21 (--release 21, baseline projektu) TEN SAM kod DALBY blad kompilacji: \"as of release 9, '_' is a keyword, and may not be used as an identifier\" / brak wsparcia unnamed patterns.");
    }

    private static void demonstrateForeignFunctionAndMemoryApi(Path workDir) throws IOException, InterruptedException {
        System.out.println("\n--- Foreign Function & Memory API (JEP 454, Java 22) - subprocess z --release 22 ---");

        Path srcDir = workDir.resolve("src-ffm");
        Path classesDir = workDir.resolve("classes-ffm");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path zrodlo = srcDir.resolve("FfmDemo.java");
        Files.writeString(zrodlo, """
                import java.lang.foreign.Arena;
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.ValueLayout;

                public class FfmDemo {
                    public static void main(String[] args) {
                        // Arena zarzadza CYKLEM ZYCIA pamieci NATYWNEJ (POZA sterta Javy) -
                        // 'ofConfined()' zwalnia PAMIEC automatycznie przy zamknieciu (try-with-resources).
                        try (Arena arena = Arena.ofConfined()) {
                            MemorySegment segment = arena.allocate(4 * Integer.BYTES);

                            for (int i = 0; i < 4; i++) {
                                segment.setAtIndex(ValueLayout.JAVA_INT, i, (i + 1) * 10);
                            }

                            int suma = 0;
                            for (int i = 0; i < 4; i++) {
                                suma += segment.getAtIndex(ValueLayout.JAVA_INT, i);
                            }
                            System.out.println("Zapisano 4 inty do pamieci NATYWNEJ (poza sterta JVM), odczytana suma: " + suma);
                            System.out.println("Rozmiar segmentu: " + segment.byteSize() + " bajtow");
                        }
                        System.out.println("Arena zamknieta - pamiec natywna ZWOLNIONA automatycznie (bez recznego free()/malloc() jak w C, bez ryzyk JNI).");
                    }
                }
                """);

        String javaHome = System.getProperty("java.home");
        String javacExe = javaHome + File.separator + "bin" + File.separator + "javac";
        String javaExe = javaHome + File.separator + "bin" + File.separator + "java";

        int compileExit = runProcess("javac --release 22 (Foreign Function & Memory API)",
                javacExe, "--release", "22", "-d", classesDir.toString(), zrodlo.toString());
        System.out.println("Wynik kompilacji (0 = sukces): " + compileExit);

        runProcess("java (FfmDemo)", javaExe, "-cp", classesDir.toString(), "FfmDemo");

        System.out.println("FFM API ZASTEPUJE stary JNI (Java Native Interface, KRUCHY I NIEBEZPIECZNY) oraz sun.misc.Unsafe - BEZPIECZNY, standardowy sposob DOSTEPU DO pamieci NATYWNEJ.");
    }

    private static void explainStreamGatherersStatus() {
        System.out.println("\n--- Stream Gatherers - status W Javie 22/23 ---");
        System.out.println("JEP 461 (Java 22, PIERWSZA runda preview), JEP 473 (Java 23, DRUGA runda preview) - `Stream.gather(Gatherer)` POZWALA NA WLASNE, POSREDNIE operacje strumieniowe (jak `windowFixed`/`windowSliding`), ktorych NIE dalo sie wyrazic PRZEZ `map`/`filter`/`reduce`.");
        System.out.println("W Javie 22/23 WYMAGA `--enable-preview` - ta lekcja go NIE URUCHAMIA (unikamy dodatkowej flagi obok --release), bo dopiero JEP 485 (Java 24) go FINALIZUJE - pelne, dzialajace demo: Lesson22.");
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
