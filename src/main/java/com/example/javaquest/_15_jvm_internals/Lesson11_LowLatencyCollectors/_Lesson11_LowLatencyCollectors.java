package com.example.javaquest._15_jvm_internals.Lesson11_LowLatencyCollectors;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson11_LowLatencyCollectors {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: KOLEKTORY NISKICH PAUZ - ZGC I SHENANDOAH ===");

        /*
         * ============================================================
         * 🔹 PROBLEM: PAUZY ROSNACE WRAZ Z ROZMIAREM STERTY
         * ============================================================
         * - Klasyczne kolektory (Parallel, a nawet domyslny G1) potrafia
         *   miec fazy STOP-THE-WORLD (STW), w ktorych WSZYSTKIE watki
         *   aplikacji sa zatrzymane, zeby GC mogl bezpiecznie posprzatac
         *   sterte.
         * - G1 stara sie trzymac pauzy w ryzach (cel -XX:MaxGCPauseMillis,
         *   patrz Lekcja 12), ale przy BARDZO duzych stertach (dziesiatki
         *   GB, setki GB) i duzym "zywym" zbiorze obiektow (live set),
         *   niektore fazy (np. finalna faza kompakcji) i tak moga
         *   przekroczyc cel i trwac dluzej niz chcielibysmy.
         * - Dla aplikacji, gdzie kazda milisekunda pauzy jest kosztowna
         *   (systemy transakcyjne, gry, systemy czasu rzeczywistego "soft",
         *   API z twardymi SLA na p99 latency) potrzeba czegos innego:
         *   kolektora, ktorego pauzy NIE rosna wraz z rozmiarem sterty.
         * - Odpowiedzia HotSpota sa dwa kolektory: ZGC i Shenandoah - oba
         *   celuja w pauzy PONIZEJ 1 milisekundy, NIEZALEZNIE od tego,
         *   czy sterta ma 1 GB, czy 1 TB.
         */
        System.out.println("\nCel ZGC/Shenandoah: pauzy submilisekundowe NIEZALEZNIE od rozmiaru sterty.");

        /*
         * ============================================================
         * 🔍 ZGC: COLORED POINTERS I LOAD BARRIERS (KONCEPCYJNIE)
         * ============================================================
         * - ZGC (Z Garbage Collector) osiaga niskie pauzy robiac PRAWIE
         *   CALA prace (znakowanie, przenoszenie/kompakcje obiektow)
         *   WSPOLBIEZNIE z watkami aplikacji - bez zatrzymywania ich.
         * - Sztuczka: "colored pointers" (kolorowane wskazniki) - ZGC
         *   wykorzystuje kilka NIEUZYWANYCH bitow w 64-bitowym wskazniku
         *   (referencji) do zakodowania METADANYCH o stanie obiektu
         *   wprost W SAMYM WSKAZNIKU (np. "czy ten obiekt zostal juz
         *   przeniesiony/zremapowany w tym cyklu GC"). To inny pomysl niz
         *   klasyczne podejscie z osobna tablica/naglowkiem obiektu.
         * - "Load barriers" (bariery odczytu) - male fragmenty kodu
         *   wstawiane przez JIT przy KAZDYM odczycie referencji z pamieci.
         *   Bariera sprawdza kolor wskaznika - jesli obiekt zostal juz
         *   przeniesiony w trakcie wspolbieznej kompakcji, bariera
         *   "naprawia" (self-healing) referencje w locie, zanim watek
         *   aplikacji w ogole zauwazy, ze cos sie zmienilo.
         * - Efekt: przenoszenie obiektow (kompakcja sterty) dzieje sie
         *   RZADKO wymagajac pauzy - a jesli juz, to KROTKIEJ i
         *   niezaleznej od tego, ile danych jest na stercie.
         */
        System.out.println("ZGC: colored pointers (metadane w bitach wskaznika) + load barriers (samo-naprawiajace odczyty).");

        Path workDir = Files.createTempDirectory("lesson11-low-latency-gc");
        Path classesDir = compileAllocatorDemo(workDir);
        Path javaExe = Path.of(System.getProperty("java.home"), "bin", "java" + (isWindows() ? ".exe" : ""));

        /*
         * ============================================================
         * 🔍 REALNE DEMO: URUCHOMIENIE JVM Z -XX:+UseZGC
         * ============================================================
         * - Odpalimy PRAWDZIWY, osobny proces JVM z flaga -XX:+UseZGC
         *   i -Xlog:gc, zeby zobaczyc rzeczywisty log ZGC (jesli ten
         *   kolektor jest dostepny w tym konkretnym buildzie JDK).
         * - WAZNE: nie kazdy build JDK ma kazdy kolektor wbudowany -
         *   ZGC jest produkcyjny od Javy 15 i zwykle dostepny w
         *   wiekszosci glownych dystrybucji (Temurin, Oracle OpenJDK),
         *   ale zawsze warto sprawdzic realny wynik zamiast zakladac.
         */
        demonstrateCollector("ZGC", "-XX:+UseZGC", javaExe, classesDir);

        /*
         * ============================================================
         * 🔹 SHENANDOAH: BROOKS POINTERS / FORWARDING POINTERS (KONCEPCYJNIE)
         * ============================================================
         * - Shenandoah (rozwijany glownie przez Red Hat) ma TEN SAM CEL
         *   co ZGC (pauzy niezalezne od rozmiaru sterty), ale osiaga go
         *   INNA technika: "Brooks pointers" (forwarding pointers).
         * - Kazdy obiekt na stercie dostaje DODATKOWE SLOWO (extra word)
         *   tuz przed swoimi danymi - to wskaznik do "aktualnej" kopii
         *   samego siebie. W normalnej sytuacji wskazuje sam na siebie.
         * - Gdy Shenandoah przenosi obiekt (wspolbieznie, bez pauzy),
         *   NAJPIERW aktualizuje ten wewnetrzny wskaznik (forwarding
         *   pointer) starej kopii, by wskazywal na NOWA lokalizacje.
         *   Kazdy odczyt obiektu przechodzi przez ten wskaznik ("pass
         *   through the Brooks pointer") - wiec nawet jesli watek
         *   aplikacji trzyma "stara" referencje, i tak trafi do
         *   aktualnych danych.
         * - Koncepcyjnie: ZGC koduje info o przenoszeniu W SAMYM
         *   WSKAZNIKU (bity adresu), Shenandoah trzyma je JAKO DODATKOWE
         *   POLE przy obiekcie - dwie rozne drogi do tego samego celu:
         *   umozliwic przenoszenie obiektow BEZ zatrzymywania aplikacji.
         */
        System.out.println("\nShenandoah: Brooks pointer (dodatkowe slowo per obiekt) zamiast kolorowanych bitow wskaznika.");

        /*
         * ============================================================
         * 🔍 REALNE DEMO: URUCHOMIENIE JVM Z -XX:+UseShenandoahGC
         * ============================================================
         * - Shenandoah CZESTO nie jest wbudowany w domyslne buildy
         *   niektorych dystrybucji JDK (np. bywa wylaczony w oficjalnym
         *   Oracle JDK) - jesli tak jest na tej maszynie, JVM zglosi
         *   blad typu "Unrecognized VM option" albo poinformuje, ze
         *   opcja jest niewspierana. Ponizej OBSLUGUJEMY to jako
         *   oczekiwany, przyjazny fallback - to NIE jest awaria demo,
         *   tylko realna wlasciwosc konkretnego buildu JDK.
         */
        demonstrateCollector("Shenandoah", "-XX:+UseShenandoahGC", javaExe, classesDir);

        /*
         * ============================================================
         * 📌 KIEDY UZYWAC (I JAKI JEST KOSZT)
         * ============================================================
         * - UZYJ ZGC/Shenandoah, gdy:
         *     * sterta jest bardzo duza (wiele GB do kilku TB),
         *     * masz twarde wymagania na pauzy (np. p99/p999 latency w
         *       systemach transakcyjnych, gamingowych, streamingowych),
         *     * wolisz PRZEWIDYWALNOSC pauz od maksymalnej przepustowosci.
         * - KOSZT (nic nie jest za darmo):
         *     * bariery (load barriers w ZGC, odczyt przez Brooks pointer
         *       w Shenandoah) wykonuja sie przy KAZDYM dostepie do
         *       referencji - to DODATKOWY narzut na kazda operacje,
         *       ktorego G1/Parallel nie maja w tym samym stopniu,
         *     * wiecej watkow GC dziala WSPOLBIEZNIE z aplikacja - to
         *       zabiera cykle CPU, ktore inaczej trafilyby do watkow
         *       roboczych aplikacji (nizsza przepustowosc/throughput),
         *     * dodatkowa pamiec: colored pointers wymagaja odpowiednio
         *       zarezerwowanej przestrzeni adresowej, Brooks pointer to
         *       dodatkowe slowo NA KAZDY obiekt na stercie.
         * - Dlatego G1 (domyslny kolektor od Javy 9) pozostaje dobrym
         *   wyborem DOMYSLNYM dla wiekszosci aplikacji - ZGC/Shenandoah
         *   to swiadomy kompromis: mniejsza przepustowosc w zamian za
         *   przewidywalnie niskie pauzy.
         */
        printLowLatencyTradeoffsSummary();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - ZGC i Shenandoah celuja w pauzy submilisekundowe NIEZALEZNIE
         *   od rozmiaru sterty - w odroznieniu od G1, ktory tylko "stara
         *   sie" trzymac cel czasowy (MaxGCPauseMillis).
         * - ZGC: colored pointers (metadane w bitach wskaznika) + load
         *   barriers (samo-naprawiajace odczyty referencji).
         * - Shenandoah: Brooks pointer / forwarding pointer - dodatkowe
         *   slowo per obiekt, przez ktore przechodzi kazdy odczyt.
         * - Koszt: dodatkowy narzut na kazdy dostep do referencji i
         *   nizsza przepustowosc w zamian za przewidywalne pauzy - wybor
         *   dla bardzo duzych stert i twardych wymagan na latency.
         * - Dostepnosc obu kolektorow ZALEZY OD BUILDU JDK - zawsze
         *   sprawdz realnie (tak jak zrobilismy powyzej), zamiast
         *   zakladac, ze flaga na pewno zadziala.
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    /**
     * Kompiluje maly, samodzielny program alokujacy duzo krotkotrwalych
     * obiektow (garbage churn) - uzywany jako "podopieczny" dla obu
     * kolektorow ponizej, zeby faktycznie mial co robic Garbage Collector.
     */
    private static Path compileAllocatorDemo(Path workDir) throws IOException {
        Path srcDir = workDir.resolve("src");
        Path classesDir = workDir.resolve("classes");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path source = srcDir.resolve("AllocatorDemo.java");
        Files.writeString(source, """
                public class AllocatorDemo {
                    public static void main(String[] args) {
                        long totalBytes = 0;
                        for (int round = 0; round < 30; round++) {
                            byte[][] garbage = new byte[10_000][];
                            for (int i = 0; i < garbage.length; i++) {
                                garbage[i] = new byte[1024]; // 1 KB - szybko staje sie smieciem
                                totalBytes += garbage[i].length;
                            }
                            garbage = null;
                        }
                        System.out.println("AllocatorDemo: zaalokowano lacznie ok. " + (totalBytes / (1024 * 1024)) + " MB smieciowych danych.");
                    }
                }
                """);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Brak systemowego kompilatora Javy - uruchom ten kod na JDK, nie na samym JRE.");
        }
        int result = compiler.run(null, System.out, System.err, "-d", classesDir.toString(), source.toString());
        if (result != 0) {
            throw new IllegalStateException("Kompilacja AllocatorDemo.java nie powiodla sie.");
        }
        return classesDir;
    }

    /**
     * Uruchamia AllocatorDemo w osobnym procesie JVM z podana flaga
     * kolektora GC i -Xlog:gc, a nastepnie ocenia wynik: jesli JVM
     * odmowila startu z powodu nieznanej/niewspieranej flagi, drukuje
     * przyjazny komunikat zamiast traktowac to jako blad demo.
     */
    private static void demonstrateCollector(String collectorName, String collectorFlag, Path javaExe, Path classesDir)
            throws IOException, InterruptedException {
        System.out.println("\n--- Test kolektora: " + collectorName + " (" + collectorFlag + ") ---");

        ProcessResult result = runProcess(collectorName,
                javaExe.toString(), collectorFlag, "-Xlog:gc", "-cp", classesDir.toString(), "AllocatorDemo");

        if (result.timedOut()) {
            System.out.println("Proces nie zakonczyl sie w 10 sekund - zabito go (destroyForcibly).");
            return;
        }
        if (looksLikeUnsupportedOption(result)) {
            System.out.println("Ten kolektor (" + collectorName + ") NIE jest dostepny w tym buildzie JDK na tej maszynie.");
            System.out.println("Fragment komunikatu JVM: " + firstNonBlankLine(result.output()));
            return;
        }
        System.out.println("Kolektor " + collectorName + " uruchomil sie poprawnie (kod wyjscia: " + result.exitCode() + ").");
        System.out.println("Fragment realnego logu GC (max 6 linii):");
        printFirstLines(result.output(), 6);
    }

    private static boolean looksLikeUnsupportedOption(ProcessResult result) {
        if (result.exitCode() == 0) {
            return false;
        }
        String lower = result.output().toLowerCase();
        return lower.contains("unrecognized vm option")
                || lower.contains("error occurred during initialization")
                || lower.contains("could not create the java virtual machine")
                || lower.contains("is unsupported")
                || lower.contains("unlock experimental vm options");
    }

    private static String firstNonBlankLine(String output) {
        for (String line : output.split("\\R")) {
            if (!line.isBlank()) {
                return line.trim();
            }
        }
        return "(brak tresci bledu)";
    }

    private static void printFirstLines(String output, int maxLines) {
        String[] lines = output.split("\\R");
        int printed = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            System.out.println("    " + line);
            printed++;
            if (printed >= maxLines) {
                break;
            }
        }
        if (printed == 0) {
            System.out.println("    (brak wyjscia procesu)");
        }
    }

    private static void printLowLatencyTradeoffsSummary() {
        System.out.println("\n=== KOMPROMISY: KOLEKTORY NISKICH PAUZ ===");
        System.out.println(" + Zaleta: pauzy submilisekundowe, niezalezne od rozmiaru sterty.");
        System.out.println(" - Koszt: bariery na kazdym dostepie do referencji (load barrier / Brooks pointer).");
        System.out.println(" - Koszt: wiecej watkow GC wspolbieznie z aplikacja -> nizsza przepustowosc.");
        System.out.println(" - Koszt: dodatkowa przestrzen adresowa/pamiec (kolorowane bity, dodatkowe slowo per obiekt).");
    }

    /**
     * Wynik uruchomienia procesu potomnego: kod wyjscia, pelne polaczone
     * wyjscie (stdout+stderr) oraz flaga, czy proces zostal zabity po
     * przekroczeniu limitu czasu.
     */
    private record ProcessResult(int exitCode, String output, boolean timedOut) {
    }

    /**
     * Uruchamia proces potomny (child JVM) z limitem czasu - jesli proces
     * nie zakonczy sie w 10 sekund, zostaje NASILNIE zabity
     * (destroyForcibly), zeby main() tej lekcji zawsze konczyl sie
     * samoistnie w rozsadnym czasie.
     */
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
            return new ProcessResult(-1, output, true);
        }
        return new ProcessResult(process.exitValue(), output, false);
    }
}
