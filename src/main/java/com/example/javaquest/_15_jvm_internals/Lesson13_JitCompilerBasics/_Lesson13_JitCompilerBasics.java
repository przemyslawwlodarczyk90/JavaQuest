package com.example.javaquest._15_jvm_internals.Lesson13_JitCompilerBasics;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson13_JitCompilerBasics {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: PODSTAWY KOMPILATORA JIT (C1/C2, TIERED COMPILATION) ===");

        /*
         * ============================================================
         * 🔹 INTERPRETER VS JIT: DLACZEGO JAVA JEST "SKOMPILOWANA W LOCIE"
         * ============================================================
         * - JVM domyslnie NAJPIERW interpretuje bajtkod (.class) linia po
         *   linii - to szybki START (bez czekania na kompilacje), ale
         *   WOLNIEJSZE wykonanie niz kod maszynowy.
         * - JIT (Just-In-Time compiler) obserwuje program W TRAKCIE
         *   dzialania i METODY, ktore sa wywolywane bardzo czesto ("hot
         *   methods" - goracy kod), kompiluje NAPRAWDE do kodu maszynowego
         *   danego procesora - od tego momentu wykonuja sie dużo szybciej.
         * - To kompromis: interpreter daje szybki start (bez pauzy na
         *   kompilacje), JIT daje wysoka wydajnosc dla kodu, ktory i tak
         *   wykona sie tysiace/miliony razy (kompilacja "kosztuje" czas
         *   procesora, wiec oplaca sie TYLKO dla goracego kodu).
         */
        System.out.println("Interpreter: szybki start, wolniejsze wykonanie. JIT: kompiluje 'goracy' kod do kodu maszynowego.");

        /*
         * ============================================================
         * 🔍 C1 (CLIENT COMPILER) VS C2 (SERVER COMPILER)
         * ============================================================
         * - HotSpot ma HISTORYCZNIE dwa rozne kompilatory JIT:
         *     * C1 (client compiler) - kompiluje SZYBKO, ale z MNIEJSZA
         *       liczba optymalizacji. Kiedys uzywany samodzielnie w
         *       trybie "-client" (dla aplikacji desktopowych, gdzie liczyl
         *       sie szybki start).
         *     * C2 (server compiler) - kompiluje WOLNIEJ (potrzebuje
         *       wiecej danych profilowania), ale generuje ZNACZNIE
         *       lepiej zoptymalizowany kod maszynowy. Kiedys uzywany
         *       samodzielnie w trybie "-server" (dla dlugo dzialajacych
         *       aplikacji serwerowych).
         * - WSPOLCZESNIE (od dawna domyslnie) HotSpot NIE wybiera
         *   jednego z nich - uzywa OBU RAZEM w modelu "tiered
         *   compilation" (kompilacja warstwowa), opisanym ponizej.
         */
        System.out.println("C1: szybka kompilacja, mniej optymalizacji. C2: wolniejsza kompilacja, dużo lepszy kod maszynowy.");

        /*
         * ============================================================
         * 📌 TIERED COMPILATION: POZIOMY 0-4
         * ============================================================
         * - Domyslny tryb HotSpota od dawna to "tiered compilation" -
         *   metoda przechodzi przez KOLEJNE poziomy w miare jak staje sie
         *   coraz "gorętsza":
         *     * Poziom 0 - czysty INTERPRETER (bez kompilacji, ale
         *       ZBIERA liczniki wywolan/petli).
         *     * Poziom 1 - C1 BEZ profilowania (dla bardzo prostych metod,
         *       gdzie profilowanie i tak nic by nie dalo).
         *     * Poziom 2 - C1 z PODSTAWOWYM profilowaniem (liczniki
         *       wywolan i petli, bez pelnych danych o typach).
         *     * Poziom 3 - C1 z PELNYM profilowaniem (najczestszy stan
         *       posredni - zbiera dane, ktore posluza C2 do lepszych
         *       optymalizacji).
         *     * Poziom 4 - C2, PELNA optymalizacja na podstawie danych
         *       zebranych na poziomie 3 (inlining, escape analysis -
         *       patrz Lekcja 14, spekulatywne zalozenia o typach).
         * - Typowa sciezka "goracej" metody: 0 -> 3 (C1 zbiera profil) ->
         *   4 (C2 kompiluje finalnie, w oparciu o zebrany profil).
         */
        System.out.println("Tiered compilation: 0 interpreter -> 1/2 C1 (bez/podstawowe profilowanie) -> 3 C1 (pelny profil) -> 4 C2.");

        /*
         * ============================================================
         * 🔹 PROG WYWOLAN: KIEDY METODA STAJE SIE "GORACA"
         * ============================================================
         * - W TRYBIE BEZ tiered compilation (-XX:-TieredCompilation,
         *   rzadko uzywane dzisiaj) prog okresla stara flaga
         *   -XX:CompileThreshold (domyslnie 10 000 wywolan metody, zanim
         *   trafi bezposrednio do C2).
         * - W DOMYSLNYM trybie tiered compilation uzywane sa INNE,
         *   bardziej szczegolowe progi per-poziom (np. Tier3InvocationThreshold,
         *   Tier4InvocationThreshold - rzedu kilku-kilkunastu tysiecy
         *   wywolan) - -XX:CompileThreshold w tym trybie w praktyce NIE
         *   jest uzywany bezposrednio, ale KONCEPCYJNIE chodzi o to samo:
         *   "wywolaj metode wystarczajaco duzo razy, zanim oplaci sie ja
         *   skompilowac".
         * - Ponizej PRAWDZIWIE zmierzymy ten efekt: ta sama metoda
         *   wywolana na "zimno" (pierwsze wywolania) vs po "rozgrzaniu"
         *   (dziesiatki tysiecy wywolan wczesniej).
         */
        demonstrateWarmupSpeedup();

        /*
         * ============================================================
         * 🔍 DEOPTYMALIZACJA: KIEDY JIT "COFA" SWOJA OPTYMALIZACJE
         * ============================================================
         * - C2 czesto optymalizuje SPEKULATYWNIE - na podstawie tego, co
         *   widzial DOTYCHCZAS. Przyklad: jesli w miejscu wywolania metody
         *   interfejsu przez cala historie programu widzial TYLKO JEDNA
         *   implementujaca klase, moze zalozyc "monomorficzne" wywolanie i
         *   wygenerowac kod tak, jakby to byla metoda finalna (bez pelnej
         *   dynamicznej dyspozycji) - to duzo szybsze.
         * - Jesli PROGRAM pozniej faktycznie napotka INNA implementacje
         *   (zalozenie okazuje sie bledne), JVM musi wykonac
         *   DEOPTYMALIZACJE: porzucic skompilowany kod dla tego miejsca,
         *   wrocic do interpretera, i (jesli metoda dalej jest goraca)
         *   skompilowac PONOWNIE - tym razem z uwzglednieniem nowych,
         *   szerszych zalozen (np. polimorficzna dyspozycja).
         * - To dlatego mikrobenchmarki bez odpowiedniego "rozgrzania" (i
         *   bez wielu roznych ksztaltow danych wejsciowych) czesto klamia -
         *   mierzysz kod, ktory jeszcze nie osiagnal swojego finalnego,
         *   ustabilizowanego stanu kompilacji.
         */
        System.out.println("\nDeoptymalizacja: JIT porzuca spekulatywnie skompilowany kod, gdy zalozenia (np. o typach) okazuja sie bledne.");

        /*
         * ============================================================
         * 🔍 REALNE DEMO: -XX:+PrintCompilation NA GORACEJ PETLI
         * ============================================================
         * - Uruchomimy OSOBNY proces JVM z flaga -XX:+PrintCompilation na
         *   programie z goraca petla - HotSpot wypisze KAZDA kompilacje
         *   metody wraz z jej POZIOMEM (1/2/3/4) w kolumnie.
         * - -XX:+PrintCompilation to zwykla (produktowa) flaga w
         *   wiekszosci wersji HotSpota - ale na wszelki wypadek, gdyby na
         *   danym buildzie wymagala odblokowania diagnostyki, najpierw
         *   probujemy BEZ -XX:+UnlockDiagnosticVMOptions, a w razie bledu
         *   ponawiamy Z nia.
         */
        demonstratePrintCompilation();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Interpreter startuje szybko, ale wykonuje wolno; JIT
         *   kompiluje "gorace" metody do kodu maszynowego dopiero, gdy
         *   sie to oplaca.
         * - C1 (szybka kompilacja, mniej optymalizacji) i C2 (wolniejsza
         *   kompilacja, duzo lepszy kod) wspolpracuja w modelu tiered
         *   compilation (poziomy 0-4): interpreter -> C1 (bez/z
         *   profilowaniem) -> C2 w pelni zoptymalizowany.
         * - Prog wywolan (koncepcyjnie -XX:CompileThreshold, w praktyce
         *   progi per-poziom w tiered compilation) decyduje, kiedy metoda
         *   trafia do kompilacji.
         * - Deoptymalizacja to "cofniecie" spekulatywnej optymalizacji,
         *   gdy zalozenia JIT (np. o jedynej implementacji interfejsu)
         *   okazuja sie bledne w runtime.
         * - Zmierzylismy REALNA roznice czasu miedzy "zimnym" a "cieplym"
         *   wywolaniem tej samej metody oraz zobaczylismy prawdziwy log
         *   -XX:+PrintCompilation z poziomami kompilacji.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    /**
     * Metoda z nietrywialna praca arytmetyczna - wystarczajaco "ciezka",
     * by JIT mial sens ja kompilowac, ale wystarczajaco prosta, by wynik
     * byl latwy do zweryfikowania.
     */
    private static long hotArithmetic(long seed) {
        long result = seed;
        for (int i = 0; i < 50; i++) {
            result = (result * 31 + i) % 1_000_000_007L;
        }
        return result;
    }

    private static long timeBatch(long startSeed, int count) {
        long start = System.nanoTime();
        long sink = 0; // zapobiega wyeliminowaniu "martwego kodu" przez JIT
        for (int i = 0; i < count; i++) {
            sink += hotArithmetic(startSeed + i);
        }
        long elapsedNanos = System.nanoTime() - start;
        if (sink == Long.MIN_VALUE) { // warunek prawie nigdy prawdziwy - tylko zeby uzyc "sink"
            System.out.println("nieoczekiwana wartosc: " + sink);
        }
        return elapsedNanos;
    }

    private static void demonstrateWarmupSpeedup() {
        System.out.println("\n--- Realny pomiar: 'zimne' vs 'rozgrzane' wywolania tej samej metody ---");

        int batchSize = 100;
        long coldNanos = timeBatch(0, batchSize);

        // "rozgrzewka" - wymuszamy wiele wywolan, by JIT zdazyl skompilowac hotArithmetic
        int warmupCalls = 50_000;
        for (int i = 0; i < warmupCalls; i++) {
            hotArithmetic(i);
        }

        long warmNanos = timeBatch(warmupCalls, batchSize);

        System.out.println("Czas " + batchSize + " 'zimnych' wywolan   : " + coldNanos + " ns");
        System.out.println("Czas " + batchSize + " 'rozgrzanych' wywolan: " + warmNanos + " ns (po " + warmupCalls + " wywolaniach rozgrzewajacych)");
        if (warmNanos < coldNanos) {
            double speedup = (double) coldNanos / Math.max(warmNanos, 1);
            System.out.printf("Przyspieszenie ok. %.1fx - typowy efekt kompilacji JIT po rozgrzaniu.%n", speedup);
        } else {
            System.out.println("W tym konkretnym uruchomieniu roznica nie jest widoczna (szum systemowy, JIT juz zdazyl");
            System.out.println("skompilowac metode wczesniej, albo za mala/za krotka petla) - pomiary JIT bywaja niestabilne.");
        }
    }

    private static void demonstratePrintCompilation() throws IOException, InterruptedException {
        Path workDir = Files.createTempDirectory("lesson13-print-compilation");
        Path srcDir = workDir.resolve("src");
        Path classesDir = workDir.resolve("classes");
        Files.createDirectories(srcDir);
        Files.createDirectories(classesDir);

        Path source = srcDir.resolve("HotLoopDemo.java");
        Files.writeString(source, """
                public class HotLoopDemo {
                    private static long hotArithmetic(long seed) {
                        long result = seed;
                        for (int i = 0; i < 50; i++) {
                            result = (result * 31 + i) % 1_000_000_007L;
                        }
                        return result;
                    }

                    public static void main(String[] args) {
                        long sink = 0;
                        for (int i = 0; i < 300_000; i++) {
                            sink += hotArithmetic(i);
                        }
                        System.out.println("HotLoopDemo zakonczony, suma kontrolna (bez znaczenia): " + sink);
                    }
                }
                """);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Brak systemowego kompilatora Javy - uruchom na JDK, nie na samym JRE.");
        }
        int compileResult = compiler.run(null, System.out, System.err, "-d", classesDir.toString(), source.toString());
        if (compileResult != 0) {
            throw new IllegalStateException("Kompilacja HotLoopDemo.java nie powiodla sie.");
        }

        String javaExe = Path.of(System.getProperty("java.home"), "bin",
                "java" + (isWindows() ? ".exe" : "")).toString();

        System.out.println("\n--- Proba 1: -XX:+PrintCompilation bez odblokowania diagnostyki ---");
        ProcessResult attempt1 = runProcess("PrintCompilation (bez unlock)",
                javaExe, "-XX:+PrintCompilation", "-cp", classesDir.toString(), "HotLoopDemo");

        ProcessResult finalResult = attempt1;
        if (looksLikeUnrecognizedOrLockedOption(attempt1)) {
            System.out.println("Flaga wymagala odblokowania diagnostyki na tym buildzie JDK - ponawiam z -XX:+UnlockDiagnosticVMOptions.");
            System.out.println("\n--- Proba 2: -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation ---");
            finalResult = runProcess("PrintCompilation (z unlock)",
                    javaExe, "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintCompilation",
                    "-cp", classesDir.toString(), "HotLoopDemo");
        }

        if (finalResult.timedOut()) {
            System.out.println("Proces nie zakonczyl sie w limicie czasu - zabito go (destroyForcibly).");
            return;
        }
        if (looksLikeUnrecognizedOrLockedOption(finalResult)) {
            System.out.println("Ta flaga diagnostyczna NIE jest dostepna w tym buildzie JDK na tej maszynie - pomijam realne demo logu.");
            System.out.println("Fragment komunikatu JVM: " + firstNonBlankLine(finalResult.output()));
            return;
        }

        System.out.println("Kod wyjscia: " + finalResult.exitCode() + ". Fragment logu kompilacji metody hotArithmetic (max 8 linii):");
        long shown = finalResult.output().lines()
                .filter(line -> line.contains("hotArithmetic"))
                .limit(8)
                .peek(line -> System.out.println("    " + line))
                .count();
        if (shown == 0) {
            System.out.println("    (brak linii z 'hotArithmetic' w wyjsciu - metoda mogla nie zdazyc sie skompilowac w tym przebiegu)");
        } else {
            System.out.println("Kolumna liczbowa (np. 1/2/3/4) po identyfikatorze kompilacji to POZIOM z tiered compilation.");
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private static boolean looksLikeUnrecognizedOrLockedOption(ProcessResult result) {
        if (result.exitCode() == 0) {
            return false;
        }
        String lower = result.output().toLowerCase();
        return lower.contains("unrecognized vm option")
                || lower.contains("error occurred during initialization")
                || lower.contains("could not create the java virtual machine")
                || lower.contains("requires -xx:+unlockdiagnosticvmoptions")
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
