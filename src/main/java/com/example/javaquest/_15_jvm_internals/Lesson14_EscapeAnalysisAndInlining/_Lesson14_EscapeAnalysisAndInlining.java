package com.example.javaquest._15_jvm_internals.Lesson14_EscapeAnalysisAndInlining;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson14_EscapeAnalysisAndInlining {

    /** Statyczne pole - celowo trzyma referencje, by "wypuscic" obiekt poza metode. */
    private static Point escapedHolder;

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 14: ESCAPE ANALYSIS I INLINING ===");

        /*
         * ============================================================
         * 🔹 ESCAPE ANALYSIS: CZY OBIEKT "UCIEKA" Z METODY?
         * ============================================================
         * - Escape analysis (analiza ucieczki) to technika JIT (glownie
         *   C2), ktora sprawdza, czy nowo utworzony obiekt moze zostac
         *   ZAOBSERWOWANY poza metoda/watkiem, w ktorym powstal.
         * - Obiekt "NIE ucieka" (no escape), jesli:
         *     * jest tworzony, uzywany i "umiera" CALKOWICIE wewnatrz
         *       jednej metody,
         *     * nigdy nie jest przypisany do pola instancji/statycznego,
         *       nie jest zwracany, nie jest przekazywany do innej metody,
         *       ktora moglaby go gdzies zachowac.
         * - Jesli JIT UDOWODNI (statycznie, analizujac kod), ze obiekt nie
         *   ucieka, ma pelna swobode: MOZE w ogole NIE alokowac go na
         *   stercie.
         */
        System.out.println("Escape analysis: JIT sprawdza, czy nowy obiekt 'ucieka' poza metode/watek, w ktorym powstal.");

        /*
         * ============================================================
         * 🔍 SCALAR REPLACEMENT: OBIEKT ROZLOZONY NA POLA
         * ============================================================
         * - Gdy escape analysis udowodni, ze obiekt NIE ucieka, JIT moze
         *   zastosowac "scalar replacement" (zastapienie skalarne) -
         *   zamiast tworzyc PRAWDZIWY obiekt na stercie, JIT "rozklada" go
         *   na osobne, POJEDYNCZE wartosci (skalary) trzymane w rejestrach
         *   procesora lub na stosie.
         * - Efekt: ZERO alokacji na stercie dla tego obiektu - a wiec
         *   mniej pracy dla GC (mniej smieci do posprzatania) i szybszy
         *   kod (dostep do rejestru jest dużo tanszy niz dostep do
         *   pamieci sterty).
         * - To NIE jest gwarancja jezyka - to OPTYMALIZACJA JIT, ktora
         *   MOZE (ale nie musi) zajsc, w zaleznosci od tego, czy analiza
         *   jednoznacznie udowodni brak ucieczki, oraz czy metoda jest
         *   wystarczajaco "gorąca", by JIT w ogole ja skompilowal do C2.
         */
        System.out.println("Scalar replacement: obiekt nieuciekajacy moze zostac 'rozlozony' na pola zamiast trafic na sterte.");

        /*
         * ============================================================
         * 🔍 REALNE DEMO: OBIEKT NIEUCIEKAJACY VS OBIEKT UCIEKAJACY
         * ============================================================
         * - Ponizej DWIE wersje tej samej, prostej operacji (utworzenie
         *   Point i zsumowanie jego wspolrzednych) w petli:
         *     * computeNonEscaping - Point jest CALKOWICIE lokalny, nigdzie
         *       nie jest zapisywany poza metoda -> KANDYDAT do scalar
         *       replacement.
         *     * computeEscaping - Point jest przypisywany do STATYCZNEGO
         *       pola escapedHolder -> ESCAPES (ucieka), MUSI trafic na
         *       sterte naprawde.
         * - Mierzymy czas wykonania ORAZ zuzycie sterty (MemoryMXBean)
         *   przed i po kazdym wariancie. WAZNE zastrzezenie: to pomiar
         *   HEURYSTYCZNY - zalezy od JIT, GC, i biezacego obciazenia
         *   maszyny - ale w wiekszosci uruchomien pokazuje realna,
         *   mierzalna roznice.
         */
        demonstrateEscapeAnalysisEffect();

        /*
         * ============================================================
         * 🔹 INLINING: WKLEJANIE CIALA MALYCH, GORACYCH METOD
         * ============================================================
         * - Inlining (inline'owanie) to zastapienie WYWOLANIA metody jej
         *   CIALEM, wprost w miejscu wywolania - eliminuje narzut samego
         *   wywolania (m.in. utworzenie ramki stosu, przekazanie
         *   argumentow, skok w kodzie).
         * - JIT chetnie inline'uje metody, ktore sa: MALE (niewielka
         *   liczba bajtkodu) i GORACE (czesto wywolywane) - to typowy
         *   przypadek prostych getterow/setterow, malych metod
         *   pomocniczych, lambd.
         * - Inlining WSPOLPRACUJE z escape analysis: gdy metoda tworzaca
         *   obiekt zostanie zinline'owana w miejscu wywolania, JIT MOZE
         *   wtedy latwiej udowodnic, ze obiekt nie ucieka (bo caly
         *   "zyciorys" obiektu jest teraz widoczny w JEDNYM, wiekszym
         *   fragmencie skompilowanego kodu) - to jeden z powodow, dla
         *   ktorych male, dobrze wydzielone metody pomocnicze CZESTO nie
         *   kosztuja nic w praktyce (JIT je "rozpuszcza" w miejscu
         *   wywolania).
         * - Metody zbyt DUZE (przekraczajace limit -XX:MaxInlineSize /
         *   -XX:FreqInlineSize) NIE zostana zinline'owane - JIT uzna, ze
         *   koszt "napuchniecia" skompilowanego kodu przewyzsza korzysc.
         */
        System.out.println("\nInlining: male, gorace metody sa wklejane w miejsce wywolania - eliminuje narzut wywolania i wspiera escape analysis.");

        /*
         * ============================================================
         * 🔍 REALNE DEMO (OPCJONALNE): -XX:+PrintInlining
         * ============================================================
         * - -XX:+PrintInlining (flaga DIAGNOSTYCZNA, wymaga
         *   -XX:+UnlockDiagnosticVMOptions) drukuje realne decyzje JIT o
         *   inline'owaniu kazdego wywolania metody - np. "inline (hot)"
         *   albo "too large" (za duza, by inline'owac).
         * - Uruchomimy osobny proces z ta flaga na programie z mala,
         *   gorąca metoda pomocnicza - jesli flaga niedostepna na tym
         *   buildzie JDK, drukujemy przyjazny fallback zamiast traktowac
         *   to jako awarie demo.
         */
        demonstratePrintInlining();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Escape analysis: JIT sprawdza, czy nowy obiekt "ucieka" poza
         *   metode/watek, w ktorym powstal.
         * - Scalar replacement: obiekt UDOWODNIONY jako nieuciekajacy moze
         *   zostac rozlozony na pola i NIGDY nie trafic na sterte -
         *   mniej pracy dla GC, szybszy kod.
         * - Inlining: male, gorace metody sa wklejane w miejsce wywolania,
         *   co eliminuje narzut wywolania i UŁATWIA escape analysis
         *   (widoczny caly "zyciorys" obiektu w jednym fragmencie kodu).
         * - Te optymalizacje sa SPEKULATYWNE i zalezne od JIT/JVM - nie
         *   da sie ich WYMUSIC z poziomu jezyka, ale mozna im POMOC
         *   pisząc male, dobrze wydzielone metody i unikajac
         *   niepotrzebnego "wypuszczania" obiektow poza metody, w ktorych
         *   naprawde sa potrzebne.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    /** Prosty, niemodyfikowalny "obiekt wartosci" uzywany w demo escape analysis. */
    private static final class Point {
        final int x;
        final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int sum() {
            return x + y;
        }
    }

    private static long computeNonEscaping(int iterations) {
        long total = 0;
        for (int i = 0; i < iterations; i++) {
            Point p = new Point(i, i + 1); // lokalny, NIGDZIE nie ucieka - kandydat do scalar replacement
            total += p.sum();
        }
        return total;
    }

    private static long computeEscaping(int iterations) {
        long total = 0;
        for (int i = 0; i < iterations; i++) {
            Point p = new Point(i, i + 1);
            escapedHolder = p; // UCIEKA do pola statycznego -> realna alokacja na stercie, gwarantowana
            total += p.sum();
        }
        return total;
    }

    private static void demonstrateEscapeAnalysisEffect() {
        System.out.println("\n--- Realny pomiar: obiekt nieuciekajacy vs obiekt uciekajacy ---");

        int warmupIterations = 2_000_000;
        int measuredIterations = 20_000_000;

        // rozgrzewka obu wariantow, zeby JIT zdazyl je skompilowac przed pomiarem
        computeNonEscaping(warmupIterations);
        computeEscaping(warmupIterations);

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        System.gc(); // WSKAZOWKA dla GC (advisory) - nie gwarancja, ale zwykle redukuje szum pomiaru
        long heapBeforeNonEscaping = memoryBean.getHeapMemoryUsage().getUsed();
        long startNonEscaping = System.nanoTime();
        long resultNonEscaping = computeNonEscaping(measuredIterations);
        long timeNonEscaping = System.nanoTime() - startNonEscaping;
        long heapAfterNonEscaping = memoryBean.getHeapMemoryUsage().getUsed();

        System.gc();
        long heapBeforeEscaping = memoryBean.getHeapMemoryUsage().getUsed();
        long startEscaping = System.nanoTime();
        long resultEscaping = computeEscaping(measuredIterations);
        long timeEscaping = System.nanoTime() - startEscaping;
        long heapAfterEscaping = memoryBean.getHeapMemoryUsage().getUsed();

        System.out.println("Wynik obliczen (bez znaczenia, tylko zeby uzyc wartosci): non-escaping=" + resultNonEscaping
                + ", escaping=" + resultEscaping);

        System.out.printf("Obiekt NIEUCIEKAJACY: czas=%d ms, przyrost sterty=%d KB%n",
                TimeUnit.NANOSECONDS.toMillis(timeNonEscaping),
                (heapAfterNonEscaping - heapBeforeNonEscaping) / 1024);
        System.out.printf("Obiekt UCIEKAJACY   : czas=%d ms, przyrost sterty=%d KB%n",
                TimeUnit.NANOSECONDS.toMillis(timeEscaping),
                (heapAfterEscaping - heapBeforeEscaping) / 1024);

        System.out.println("Uwaga: to pomiar heurystyczny (JIT/GC zalezny) - w wielu uruchomieniach wariant");
        System.out.println("nieuciekajacy pokazuje mniejszy przyrost sterty (mozliwy efekt scalar replacement),");
        System.out.println("ale roznica NIE jest gwarantowana w kazdym uruchomieniu/na kazdej maszynie.");
    }

    private static void demonstratePrintInlining() throws IOException, InterruptedException {
        Path workDir = Files.createTempDirectory("lesson14-print-inlining");
        Path srcDir = workDir.resolve("src");
        Path classesDir = workDir.resolve("classes");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path source = srcDir.resolve("InliningDemo.java");
        Files.writeString(source, """
                public class InliningDemo {
                    // mala, gorąca metoda - typowy kandydat do inline'owania
                    private static int addSmall(int a, int b) {
                        return a + b;
                    }

                    public static void main(String[] args) {
                        long sum = 0;
                        for (int i = 0; i < 300_000; i++) {
                            sum += addSmall(i, i + 1);
                        }
                        System.out.println("InliningDemo zakonczony, suma (bez znaczenia): " + sum);
                    }
                }
                """);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Brak systemowego kompilatora Javy - uruchom na JDK, nie na samym JRE.");
        }
        int compileResult = compiler.run(null, System.out, System.err, "-d", classesDir.toString(), source.toString());
        if (compileResult != 0) {
            throw new IllegalStateException("Kompilacja InliningDemo.java nie powiodla sie.");
        }

        String javaExe = Path.of(System.getProperty("java.home"), "bin",
                "java" + (isWindows() ? ".exe" : "")).toString();

        ProcessResult result = runProcess("PrintInlining",
                javaExe, "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintCompilation", "-XX:+PrintInlining",
                "-cp", classesDir.toString(), "InliningDemo");

        if (result.timedOut()) {
            System.out.println("Proces nie zakonczyl sie w limicie czasu - zabito go (destroyForcibly).");
            return;
        }
        if (looksLikeUnsupportedDiagnostic(result)) {
            System.out.println("Flagi diagnostyczne -XX:+PrintInlining/-XX:+UnlockDiagnosticVMOptions NIE sa dostepne");
            System.out.println("w tym buildzie JDK na tej maszynie - pomijam realne demo logu inliningu.");
            System.out.println("Fragment komunikatu JVM: " + firstNonBlankLine(result.output()));
            return;
        }

        System.out.println("\n--- Fragment realnego logu -XX:+PrintInlining dotyczacy metody addSmall (max 8 linii) ---");
        long shownForAddSmall = result.output().lines()
                .filter(line -> line.toLowerCase().contains("addsmall"))
                .limit(8)
                .peek(line -> System.out.println("    " + line))
                .count();

        if (shownForAddSmall == 0) {
            System.out.println("    (brak linii wprost o 'addSmall' w tym krotkim przebiegu - metoda mogla nie zdazyc sie skompilowac).");
            System.out.println("    Ponizej za to fragment logu inliningu dla INNYCH, wewnetrznych metod JDK - sam format komunikatow");
            System.out.println("    (\"inline\" / \"failed to inline: ...\") jest identyczny niezaleznie od tego, czyj to kod:");
            result.output().lines()
                    .filter(line -> line.toLowerCase().contains("inline"))
                    .limit(5)
                    .forEach(line -> System.out.println("    " + line));
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private static boolean looksLikeUnsupportedDiagnostic(ProcessResult result) {
        if (result.exitCode() == 0) {
            return false;
        }
        String lower = result.output().toLowerCase();
        return lower.contains("unrecognized vm option")
                || lower.contains("error occurred during initialization")
                || lower.contains("could not create the java virtual machine")
                || lower.contains("is a diagnostic option");
    }

    private static String firstNonBlankLine(String output) {
        return output.lines().filter(l -> !l.isBlank()).findFirst().orElse("(brak tresci bledu)");
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
