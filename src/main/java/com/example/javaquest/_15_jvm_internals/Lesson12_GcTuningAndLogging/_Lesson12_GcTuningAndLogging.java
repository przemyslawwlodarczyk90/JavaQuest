package com.example.javaquest._15_jvm_internals.Lesson12_GcTuningAndLogging;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson12_GcTuningAndLogging {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 12: TUNING GC I UNIFIED JVM LOGGING ===");

        Path workDir = Files.createTempDirectory("lesson12-gc-tuning");
        Path classesDir = compileHeapPrinterDemo(workDir);
        Path javaExe = Path.of(System.getProperty("java.home"), "bin", "java" + (isWindows() ? ".exe" : ""));

        /*
         * ============================================================
         * 🔹 -Xms I -Xmx: ROZMIAR STERTY (POCZATKOWY I MAKSYMALNY)
         * ============================================================
         * - -Xms<rozmiar> ustawia POCZATKOWY rozmiar sterty (np. -Xms64m).
         * - -Xmx<rozmiar> ustawia MAKSYMALNY rozmiar sterty (np. -Xmx512m).
         * - Jesli -Xms < -Xmx, sterta ROSNIE w trakcie dzialania programu,
         *   w miare potrzeb - kazde takie powiekszenie to DODATKOWA praca
         *   (i czasem pauza) dla GC.
         * - Powszechna praktyka w produkcji (zwlaszcza w kontenerach o
         *   ustalonym limicie pamieci): USTAWIC -Xms RAZEM z -Xmx (te sama
         *   wartosc) - sterta ma od razu docelowy rozmiar, bez kosztownych
         *   realokacji w trakcie dzialania.
         * - Ponizej uruchomimy REALNY proces z dwiema roznymi wartosciami
         *   -Xmx i odczytamy Runtime.getRuntime().maxMemory() - to
         *   NAJPRAWDZIWSZY mozliwy dowod na to, co dana flaga robi.
         */
        demonstrateHeapSizeFlags(javaExe, classesDir);

        /*
         * ============================================================
         * 🔹 -XX:NewRatio I -XX:SurvivorRatio: PROPORCJE GENERACJI
         * ============================================================
         * - Sterta dzieli sie na generacje: MLODA (young: Eden + 2 obszary
         *   Survivor) i STARA (old/tenured). Wiekszosc obiektow umiera
         *   MLODO (hipoteza generacyjna) - dlatego mlodej generacji
         *   poswieca sie osobna, czesta i szybka faze GC (minor GC).
         * - -XX:NewRatio=N ustawia proporcje STARA:MLODA = N:1 (np.
         *   NewRatio=2 oznacza: stara generacja jest 2x wieksza niz mloda -
         *   to wartosc domyslna dla kolektora Parallel).
         * - -XX:SurvivorRatio=N ustawia proporcje Eden:jeden-Survivor = N:1
         *   (np. SurvivorRatio=8, domyslne - Eden jest 8x wiekszy niz
         *   pojedynczy obszar Survivor).
         * - G1 (domyslny kolektor) w duzej mierze zarzadza tymi proporcjami
         *   ADAPTACYJNIE (na podstawie regionow), ale flagi te wciaz sa
         *   ROZPOZNAWANE przez JVM i mozna je podejrzec przez
         *   -XX:+PrintFlagsFinal - ponizej sprawdzimy to naprawde.
         */
        demonstrateGenerationRatios(javaExe);

        /*
         * ============================================================
         * 🔹 -XX:MaxGCPauseMillis: CEL PAUZY DLA G1
         * ============================================================
         * - G1 (Garbage First) to jedyny z "klasycznych" kolektorow, ktory
         *   przyjmuje EXPLICITNY CEL czasowy pauzy: -XX:MaxGCPauseMillis=N
         *   (domyslnie 200 ms).
         * - To NIE jest twarda gwarancja (w odroznieniu od "celu" ZGC/
         *   Shenandoah z Lekcji 11) - G1 stara sie dobrac rozmiar mlodej
         *   generacji i liczbe regionow sprzatanych naraz tak, by ZMIESCIC
         *   sie w tym celu, ale przy duzym obciazeniu MOZE go przekroczyc.
         * - Nizsza wartosc (np. 50ms) wymusza CZESTSZE, KROTSZE pauzy
         *   (kosztem przepustowosci); wyzsza (np. 500ms) pozwala na
         *   rzadsze, dluzsze pauzy (czesciej lepsza przepustowosc).
         */
        demonstrateMaxGcPauseMillis(javaExe);

        /*
         * ============================================================
         * 📌 -XX:+UseStringDeduplication: DEDUPLIKACJA TEKSTU (TYLKO G1)
         * ============================================================
         * - Wiele aplikacji tworzy tysiace obiektow String o IDENTYCZNEJ
         *   zawartosci (np. parsowanie JSON/CSV z powtarzajacymi sie
         *   kluczami) - kazdy z osobnym, duplikatowym char[]/byte[] w tle.
         * - -XX:+UseStringDeduplication (dostepne w G1, wymaga jednoczesnie
         *   -XX:+UseG1GC) pozwala G1 podczas wspolbieznego znakowania
         *   wykryc Stringi o tej samej tresci i zrobic, by dzielily JEDNA
         *   kopie tablicy bajtow w pamieci - oszczednosc pamieci kosztem
         *   dodatkowej pracy GC w tle.
         * - Deduplikacja dziala W TLE i wymaga czasu/cykli GC, zeby cokolwiek
         *   wykryc - w bardzo krotkim demo ponizej moze nie zdazyc wygenerowac
         *   zadnych logow - to normalne, NIE oznacza bledu.
         */
        demonstrateStringDeduplicationFlag(javaExe, classesDir);

        /*
         * ============================================================
         * 🔍 UNIFIED JVM LOGGING (JEP 158): SKLADNIA -Xlog
         * ============================================================
         * - Od Javy 9 WSZYSTKIE logi JVM (nie tylko GC) przechodza przez
         *   wspolny system: -Xlog:<tagi>:<output>:<dekoratory>:<opcje>.
         *     * <tagi>      - co logowac, np. "gc", "gc*" (wszystkie
         *                     podtagi zwiazane z gc, np. gc+heap, gc+phases),
         *                     "gc+stringdedup".
         *     * <output>    - gdzie: "stdout" (domyslnie), "stderr", albo
         *                     "file=nazwa.log".
         *     * <dekoratory>- jakie metadane doklejac do kazdej linii, np.
         *                     "time" (znacznik czasu), "uptime" (czas od
         *                     startu JVM), "level" (INFO/DEBUG/TRACE),
         *                     "tags" (nazwa tagu w nawiasach [gc]).
         * - Samo "-Xlog:gc" to WYGODNY SKROT dajacy podstawowe,
         *   jednoliniowe podsumowanie kazdego cyklu GC z domyslnymi
         *   dekoratorami. "-Xlog:gc*:stdout:time,level,tags" daje O WIELE
         *   wiecej szczegolow (podfazy) z pelnymi dekoratorami.
         * - Ponizej uruchomimy TEN SAM program z DWOMA wariantami -Xlog i
         *   realnie porownamy ilosc/format linii.
         */
        demonstrateLoggingVerbosity(javaExe, classesDir);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - -Xms/-Xmx: poczatkowy/maksymalny rozmiar sterty - ustawienie
         *   ich na te sama wartosc unika kosztownych realokacji sterty.
         * - -XX:NewRatio / -XX:SurvivorRatio: proporcje generacji mlodej i
         *   starej (oraz Eden/Survivor) - G1 zarzadza nimi w duzej mierze
         *   adaptacyjnie, ale flagi wciaz dzialaja.
         * - -XX:MaxGCPauseMillis: MIEKKI cel pauzy dla G1 - nizsza wartosc
         *   = czestsze/krotsze pauzy kosztem przepustowosci.
         * - -XX:+UseStringDeduplication: G1 dedupluje identyczne Stringi w
         *   tle, oszczedzajac pamiec.
         * - Unified JVM Logging (-Xlog) to jeden, spojny system logowania
         *   dla calej JVM od Javy 9 - skladnia
         *   tagi:output:dekoratory:opcje pozwala precyzyjnie kontrolowac,
         *   co i jak szczegolowo widac w logach GC.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private static Path compileHeapPrinterDemo(Path workDir) throws IOException {
        Path srcDir = workDir.resolve("src");
        Path classesDir = workDir.resolve("classes");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path heapPrinterSource = srcDir.resolve("HeapPrinterDemo.java");
        Files.writeString(heapPrinterSource, """
                public class HeapPrinterDemo {
                    public static void main(String[] args) {
                        long maxMemoryMb = Runtime.getRuntime().maxMemory() / (1024 * 1024);
                        System.out.println("Runtime.getRuntime().maxMemory() = " + maxMemoryMb + " MB");
                    }
                }
                """);

        Path allocatorSource = srcDir.resolve("SmallAllocatorDemo.java");
        Files.writeString(allocatorSource, """
                public class SmallAllocatorDemo {
                    public static void main(String[] args) {
                        java.util.List<String> duplicates = new java.util.ArrayList<>();
                        long totalBytes = 0;
                        for (int round = 0; round < 15; round++) {
                            byte[][] garbage = new byte[8_000][];
                            for (int i = 0; i < garbage.length; i++) {
                                garbage[i] = new byte[512];
                                totalBytes += garbage[i].length;
                            }
                            // celowo tworzymy wiele Stringow o IDENTYCZNEJ tresci przez
                            // "new String(...)" (osobny obiekt, ta sama zawartosc) - to
                            // material dla ewentualnej deduplikacji Stringow (G1).
                            duplicates.add(new String("powtarzalny-klucz-konfiguracji"));
                            garbage = null;
                        }
                        System.out.println("SmallAllocatorDemo: zaalokowano ok. " + (totalBytes / (1024 * 1024))
                                + " MB smieci, " + duplicates.size() + " duplikatow Stringow.");
                    }
                }
                """);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Brak systemowego kompilatora Javy - uruchom na JDK, nie na samym JRE.");
        }
        int result = compiler.run(null, System.out, System.err, "-d", classesDir.toString(),
                heapPrinterSource.toString(), allocatorSource.toString());
        if (result != 0) {
            throw new IllegalStateException("Kompilacja demo-programow lekcji 12 nie powiodla sie.");
        }
        return classesDir;
    }

    private static void demonstrateHeapSizeFlags(Path javaExe, Path classesDir) throws IOException, InterruptedException {
        System.out.println("\n--- -Xms/-Xmx: wplyw na Runtime.getRuntime().maxMemory() ---");

        ProcessResult small = runProcess("heap 64m",
                javaExe.toString(), "-Xms64m", "-Xmx64m", "-cp", classesDir.toString(), "HeapPrinterDemo");
        ProcessResult big = runProcess("heap 256m",
                javaExe.toString(), "-Xms256m", "-Xmx256m", "-cp", classesDir.toString(), "HeapPrinterDemo");

        System.out.println("Z -Xmx64m  -> " + firstNonBlankLine(small.output()));
        System.out.println("Z -Xmx256m -> " + firstNonBlankLine(big.output()));
        System.out.println("(maxMemory() bywa nieco nizsze niz -Xmx - JVM rezerwuje troche na wlasne potrzeby.)");
    }

    private static void demonstrateGenerationRatios(Path javaExe) throws IOException, InterruptedException {
        System.out.println("\n--- -XX:NewRatio i -XX:SurvivorRatio przez -XX:+PrintFlagsFinal ---");

        ProcessResult defaultFlags = runProcess("domyslne proporcje",
                javaExe.toString(), "-XX:+PrintFlagsFinal", "-version");
        ProcessResult customFlags = runProcess("wlasne proporcje",
                javaExe.toString(), "-XX:NewRatio=4", "-XX:SurvivorRatio=6", "-XX:+PrintFlagsFinal", "-version");

        System.out.println("Domyslnie:    " + findFlagLine(defaultFlags.output(), "NewRatio"));
        System.out.println("Domyslnie:    " + findFlagLine(defaultFlags.output(), "SurvivorRatio"));
        System.out.println("Po -XX:NewRatio=4 -XX:SurvivorRatio=6:");
        System.out.println("              " + findFlagLine(customFlags.output(), "NewRatio"));
        System.out.println("              " + findFlagLine(customFlags.output(), "SurvivorRatio"));
    }

    private static void demonstrateMaxGcPauseMillis(Path javaExe) throws IOException, InterruptedException {
        System.out.println("\n--- -XX:MaxGCPauseMillis przez -XX:+PrintFlagsFinal ---");

        ProcessResult defaultPause = runProcess("domyslny cel pauzy",
                javaExe.toString(), "-XX:+UseG1GC", "-XX:+PrintFlagsFinal", "-version");
        ProcessResult tightPause = runProcess("wlasny cel pauzy",
                javaExe.toString(), "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=50", "-XX:+PrintFlagsFinal", "-version");

        System.out.println("Domyslnie (G1):        " + findFlagLine(defaultPause.output(), "MaxGCPauseMillis"));
        System.out.println("Po -XX:MaxGCPauseMillis=50: " + findFlagLine(tightPause.output(), "MaxGCPauseMillis"));
    }

    private static void demonstrateStringDeduplicationFlag(Path javaExe, Path classesDir) throws IOException, InterruptedException {
        System.out.println("\n--- -XX:+UseStringDeduplication (tylko G1) ---");

        ProcessResult result = runProcess("string dedup",
                javaExe.toString(), "-XX:+UseG1GC", "-XX:+UseStringDeduplication",
                "-Xlog:gc+stringdedup=debug", "-cp", classesDir.toString(), "SmallAllocatorDemo");

        System.out.println("Kod wyjscia: " + result.exitCode());
        long dedupLines = result.output().lines().filter(line -> line.toLowerCase().contains("dedup")).count();
        if (dedupLines > 0) {
            System.out.println("Znaleziono " + dedupLines + " linii logu zwiazanych z deduplikacja Stringow, np.:");
            result.output().lines()
                    .filter(line -> line.toLowerCase().contains("dedup"))
                    .limit(3)
                    .forEach(line -> System.out.println("    " + line));
        } else {
            System.out.println("Brak logow deduplikacji w tym krotkim uruchomieniu - to NORMALNE, deduplikacja");
            System.out.println("dziala w tle podczas wspolbieznego znakowania i w tak krotkim demo moze nie zdazyc zadzialac.");
        }
    }

    private static void demonstrateLoggingVerbosity(Path javaExe, Path classesDir) throws IOException, InterruptedException {
        System.out.println("\n--- Porownanie: -Xlog:gc (skrot) vs -Xlog:gc*:stdout:time,level,tags (szczegolowy) ---");

        ProcessResult basic = runProcess("log podstawowy",
                javaExe.toString(), "-Xlog:gc", "-cp", classesDir.toString(), "SmallAllocatorDemo");
        ProcessResult detailed = runProcess("log szczegolowy",
                javaExe.toString(), "-Xlog:gc*:stdout:time,level,tags", "-cp", classesDir.toString(), "SmallAllocatorDemo");

        long basicLines = basic.output().lines().filter(l -> !l.isBlank()).count();
        long detailedLines = detailed.output().lines().filter(l -> !l.isBlank()).count();

        System.out.println("Liczba linii z '-Xlog:gc'                          : " + basicLines);
        System.out.println("Liczba linii z '-Xlog:gc*:stdout:time,level,tags'  : " + detailedLines);
        System.out.println("Przyklad linii z wersji podstawowej:");
        printFirstLines(basic.output(), 2);
        System.out.println("Przyklad linii z wersji szczegolowej (wiecej podfaz, dekorator [poziom][tagi]):");
        printFirstLines(detailed.output(), 4);
    }

    private static String findFlagLine(String output, String flagName) {
        return output.lines()
                .filter(line -> line.contains(flagName))
                .findFirst()
                .map(String::trim)
                .orElse("(nie znaleziono flagi " + flagName + " w wyjsciu -XX:+PrintFlagsFinal)");
    }

    private static String firstNonBlankLine(String output) {
        return output.lines().filter(l -> !l.isBlank()).findFirst().orElse("(brak wyjscia)");
    }

    private static void printFirstLines(String output, int maxLines) {
        long printed = output.lines().filter(l -> !l.isBlank()).limit(maxLines).count();
        output.lines().filter(l -> !l.isBlank()).limit(maxLines).forEach(l -> System.out.println("    " + l));
        if (printed == 0) {
            System.out.println("    (brak wyjscia procesu)");
        }
    }

    private record ProcessResult(int exitCode, String output, boolean timedOut) {
    }

    private static ProcessResult runProcess(String description, String... command) throws IOException, InterruptedException {
        System.out.println(">>> [" + description + "] " + String.join(" ", command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output;
        try (InputStream in = process.getInputStream()) {
            output = new String(in.readAllBytes());
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            System.out.println("(TIMEOUT - proces zabity)");
            return new ProcessResult(-1, output, true);
        }
        return new ProcessResult(process.exitValue(), output, false);
    }
}
