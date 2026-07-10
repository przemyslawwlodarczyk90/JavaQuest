package com.example.javaquest._15_jvm_internals.Lesson09_GarbageCollectorAlgorithms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class _Lesson09_GarbageCollectorAlgorithms {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("=== LEKCJA 9: ALGORYTMY KOLEKTOROW GC ===");

        /*
         * ============================================================
         * 🔹 PO CO WIELE ROZNYCH KOLEKTOROW GC?
         * ============================================================
         * - Lesson08 pokazal WSPOLNE fundamenty (hipoteza generacyjna,
         *   GC roots, mark-sweep-compact). Ale KONKRETNA implementacja
         *   tych fundamentow rozni sie miedzy kolektorami - kazdy robi
         *   inny KOMPROMIS miedzy 3 sprzecznymi celami:
         *     * THROUGHPUT (przepustowosc) - jak duzy % czasu CPU idzie
         *       na prace aplikacji, a jak maly na GC.
         *     * LATENCY (opoznienie/pauzy) - jak KROTKIE sa pojedyncze
         *       pauzy stop-the-world (istotne dla aplikacji
         *       interaktywnych/real-time).
         *     * FOOTPRINT (narzut pamieciowy) - ile DODATKOWEJ pamieci
         *       (poza samymi danymi) kolektor potrzebuje na wlasne
         *       struktury ksiegowe.
         * - Nie da sie zoptymalizowac wszystkich 3 na raz - stad wybor
         *   kolektora to swiadoma decyzja architektoniczna, zalezna od
         *   TEGO, na czym Twojej aplikacji najbardziej zalezy.
         */
        printCollectorComparisonTable();

        /*
         * ============================================================
         * 📌 SEROWY (Serial) GC - -XX:+UseSerialGC
         * ============================================================
         * - Najprostszy, JEDNOWATKOWY kolektor - CALA praca GC (mark,
         *   sweep, compact) wykonuje JEDEN watek, podczas gdy WSZYSTKIE
         *   watki aplikacji sa zatrzymane (stop-the-world).
         * - Brak narzutu na koordynacje wielu watkow GC = najmniejszy
         *   FOOTPRINT, ale najdluzsze pauzy przy wiekszych stertach.
         * - Sensowny wybor: male aplikacje, malo pamieci (np. kontenery
         *   z limitem 1 rdzenia CPU), narzedzia CLI dzialajace krotko.
         */
        System.out.println("\nSerial GC: 1 watek GC, najmniejszy narzut, najdluzsze pauzy - dobry dla malych/prostych aplikacji.");

        /*
         * ============================================================
         * 📌 PARALLEL (Throughput) GC - -XX:+UseParallelGC
         * ============================================================
         * - Ta sama idea co Serial, ale praca GC jest dzielona miedzy
         *   WIELE watkow GC (rownoleglych) - nadal stop-the-world, ale
         *   pauza jest KROTSZA (wiecej watkow = szybciej skonczone).
         * - Zoptymalizowany pod THROUGHPUT - maksymalizuje % czasu, jaki
         *   CPU spedza na pracy aplikacji, kosztem WIEKSZYCH (choc
         *   rzadszych) pauz niz nowoczesne kolektory low-latency.
         * - Sensowny wybor: przetwarzanie wsadowe (batch processing),
         *   zadania danych, gdzie liczy sie CALKOWITY czas wykonania, a
         *   NIE pojedyncze pauzy.
         */
        System.out.println("Parallel GC: wiele watkow GC, zoptymalizowany pod PRZEPUSTOWOSC (throughput) - dobry dla batchy.");

        /*
         * ============================================================
         * 📌 G1 (Garbage First) GC - -XX:+UseG1GC (DOMYSLNY OD JAVY 9)
         * ============================================================
         * - Dzieli sterte na WIELE malych REGIONOW (zamiast ciaglych
         *   generacji) i sprzata NAJPIERW regiony z NAJWIECEJ smieciem
         *   (stad nazwa "Garbage First").
         * - Celuje w PRZEWIDYWALNE, KROTKIE pauzy (konfigurowalne przez
         *   -XX:MaxGCPauseMillis) - dobry KOMPROMIS miedzy throughput a
         *   latency, dlatego jest domyslnym kolektorem od Javy 9.
         * - Szczegoly G1 (regiony, remembered sets, mixed GC) to temat
         *   calej nastepnej lekcji (Lesson10).
         */
        System.out.println("G1 GC: sterta podzielona na regiony, celuje w przewidywalne pauzy - DOMYSLNY od Javy 9.");

        /*
         * ============================================================
         * 🔍 ZGC I SHENANDOAH - KOLEKTORY "LOW-LATENCY" (WZMIANKA)
         * ============================================================
         * - ZGC (-XX:+UseZGC) i Shenandoah (-XX:+UseShenandoahGC) to
         *   nowsze kolektory, ktorych GLOWNYM celem sa PAUZY RZEDU
         *   POJEDYNCZYCH MILISEKUND, NIEZALEZNIE od rozmiaru sterty
         *   (nawet dla sterty rzedu setek GB).
         * - Osiagaja to wykonujac wiekszosc pracy WSPOLBIEZNIE (concurrent)
         *   z watkami aplikacji, zamiast w pelnym stop-the-world.
         * - Kompromis: WIEKSZY footprint (dodatkowa ksiegowosc na
         *   wspolbieznosc) i zazwyczaj nizszy surowy throughput niz
         *   Parallel GC.
         * - Ten kurs NIE uruchamia ich w praktyce (wymagalyby dluzej
         *   dzialajacej aplikacji, zeby zobaczyc realna roznice) -
         *   ponizsza demonstracja skupia sie na 3 kolektorach latwych
         *   do porownania w krotkim, bezpiecznym demo (Serial/Parallel/G1).
         */
        System.out.println("ZGC / Shenandoah: pauzy rzedu milisekund niezaleznie od rozmiaru sterty (kosztem footprintu).");

        /*
         * ============================================================
         * 📌 REALNE DEMO: TEN SAM PROGRAM, 3 ROZNE KOLEKTORY, REALNE LOGI GC
         * ============================================================
         * - Generujemy maly, samodzielny plik zrodlowy Javy (uruchamiany
         *   bezposrednio jako "source-file launch", bez osobnej
         *   kompilacji - `java Plik.java`), ktory alokuje 200 000 malych,
         *   krotko zyjacych obiektow.
         * - Odpalamy go TRZY razy jako OSOBNY proces potomny (child JVM),
         *   za kazdym razem z INNA flaga kolektora oraz -Xlog:gc, i
         *   porownujemy REALNE linie logu GC - roznia sie miedzy soba,
         *   bo kazdy kolektor loguje wlasny format/nazwe faz.
         */
        runGcAlgorithmComparisonDemo();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Serial - 1 watek, najmniejszy footprint, najdluzsze pauzy.
         * - Parallel - wiele watkow GC, throughput, wieksze (rzadsze) pauzy.
         * - G1 - regiony, przewidywalne pauzy, DOMYSLNY od Javy 9.
         * - ZGC/Shenandoah - pauzy rzedu ms niezaleznie od rozmiaru sterty.
         * - Wybor kolektora ustawia sie flaga JVM (-XX:+Use...GC) przy
         *   starcie - w kolejnej lekcji (Lesson10) zaglebimy sie w G1,
         *   bo to domyslny, najczesciej spotykany w praktyce kolektor.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void printCollectorComparisonTable() {
        System.out.println("\n=== TABELA PORONAWCZA KOLEKTOROW GC ===");
        System.out.printf("%-12s | %-10s | %-10s | %-10s | %s%n", "Kolektor", "Throughput", "Latency", "Footprint", "Kiedy uzyc");
        System.out.println("-".repeat(90));
        System.out.printf("%-12s | %-10s | %-10s | %-10s | %s%n", "Serial", "niski", "wysoka pauza", "najmniejszy", "male aplikacje, 1 CPU");
        System.out.printf("%-12s | %-10s | %-10s | %-10s | %s%n", "Parallel", "wysoki", "srednia", "maly", "batch/przetwarzanie danych");
        System.out.printf("%-12s | %-10s | %-10s | %-10s | %s%n", "G1", "sredni-wysoki", "niska (cel ms)", "sredni", "domyslny, ogolnego zastosowania");
        System.out.printf("%-12s | %-10s | %-10s | %-10s | %s%n", "ZGC", "sredni", "b. niska", "wiekszy", "wielkie sterty, twarde SLA opoznien");
        System.out.printf("%-12s | %-10s | %-10s | %-10s | %s%n", "Shenandoah", "sredni", "b. niska", "wiekszy", "podobnie do ZGC, inna implementacja");
    }

    private static void runGcAlgorithmComparisonDemo() throws IOException, InterruptedException {
        System.out.println("\n=== REALNE URUCHOMIENIE: TEN SAM KOD, 3 KOLEKTORY, REALNE LOGI GC ===");

        Path tempDir = Files.createTempDirectory("lesson09-gc-demo");
        Path sourceFile = tempDir.resolve("GcDemoApp.java");
        Files.writeString(sourceFile, buildGcDemoAppSource(), StandardCharsets.UTF_8);

        String javaExecutable = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        try {
            runProcess("Serial GC", javaExecutable, "-Xlog:gc", "-XX:+UseSerialGC", sourceFile.toString());
            runProcess("Parallel GC", javaExecutable, "-Xlog:gc", "-XX:+UseParallelGC", sourceFile.toString());
            runProcess("G1 GC (domyslny od Javy 9)", javaExecutable, "-Xlog:gc", "-XX:+UseG1GC", sourceFile.toString());

            System.out.println("\nZauwaz: kazdy kolektor generuje logi GC w NIECO INNYM formacie/z inna liczba");
            System.out.println("cykli - dokladna tresc zalezy od wersji JDK, ale SAM FAKT roznic potwierdza,");
            System.out.println("ze to NAPRAWDE inne implementacje, a nie tylko inna nazwa tej samej rzeczy.");
        } finally {
            Files.deleteIfExists(sourceFile);
            Files.deleteIfExists(tempDir);
        }
    }

    private static String buildGcDemoAppSource() {
        return """
                public class GcDemoApp {
                    public static void main(String[] args) {
                        long checksum = 0;
                        for (int i = 0; i < 200_000; i++) {
                            byte[] junk = new byte[64];
                            checksum += junk.length + (i % 5);
                        }
                        System.out.println("GcDemoApp zakonczony, checksum=" + checksum);
                    }
                }
                """;
    }

    /**
     * Uruchamia podany program jako proces potomny (child JVM) i wypisuje
     * jego polaczony output (stdout+stderr). Zawsze ograniczony czasowo -
     * przy przekroczeniu limitu proces jest zabijany, zeby main() nigdy
     * sie nie zawiesil.
     */
    private static int runProcess(String description, String... command) throws IOException, InterruptedException {
        System.out.println("\n>>> [" + description + "] " + String.join(" ", command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output;
        try (InputStream in = process.getInputStream()) {
            output = new String(in.readAllBytes(), StandardCharsets.UTF_8);
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
}
