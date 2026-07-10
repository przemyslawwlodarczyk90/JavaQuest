package com.example.javaquest._15_jvm_internals.Lesson20_JvmTuningAndBestPracticesCapstone;

import com.sun.management.HotSpotDiagnosticMXBean;
import jdk.jfr.Recording;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class _Lesson20_JvmTuningAndBestPracticesCapstone {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 20: STROJENIE JVM, DOBRE PRAKTYKI I PROJEKT PODSUMOWUJACY ===");

        /*
         * ============================================================
         * 📦 DOMYKAMY ROZDZIAL _15_jvm_internals - SZYBKIE PRZYPOMNIENIE
         * ============================================================
         * To OSTATNIA lekcja calego rozdzialu "JVM, pamiec i wydajnosc".
         * Zanim przejdziemy do praktycznego capstone, krotkie przypomnienie
         * (BEZ powtarzania szczegolow - po to sa lekcje 1-19) czego
         * dotyczyl caly rozdzial:
         *
         *  - Lekcje 1-5  (Classloading i bajtkod): czym rozni sie JDK/JRE/JVM
         *    i czym jest specyfikacja, jak Javac produkuje bajtkod .class,
         *    jak dziala mechanika ladowania klas, wlasne class loadery,
         *    classpath vs module path.
         *  - Lekcje 6-7  (Obszary pamieci): heap/stack/metaspace "od srodka"
         *    - realna introspekcja przez java.lang.management zamiast
         *      samej teorii.
         *  - Lekcje 8-12 (Garbage Collector): algorytmy GC (mark-sweep-
         *    -compact, generacyjny podzial), konkretne kolektory
         *    (Serial/Parallel/G1/ZGC/Shenandoah), strojenie i pomiar pauz.
         *  - Lekcje 13-14 (JIT): interpreter vs kompilatory C1/C2, inlining,
         *    escape analysis, deoptymalizacja.
         *  - Lekcja 15   (Wycieki pamieci): jak je rozpoznac i znalezc.
         *  - Lekcje 16-19 (Diagnostyka): heap dump (.hprof), thread dump +
         *    wykrywanie deadlockow, Java Flight Recorder (.jfr), sampling
         *    profiling.
         *
         * Ta lekcja spina WSZYSTKO powyzej w jedno praktyczne demo
         * diagnostyczne oraz podsumowuje najwazniejsze flagi JVM i
         * metodyke strojenia produkcyjnego.
         */
        System.out.println("Rozdzial: classloading -> pamiec -> GC -> JIT -> wycieki -> diagnostyka. Teraz: podsumowanie + capstone.\n");

        /*
         * ============================================================
         * 🔹 NAJWAZNIEJSZE FLAGI JVM Z CALEGO ROZDZIALU - JEDNO MIEJSCE
         * ============================================================
         * -Xms<rozmiar>                 - poczatkowy rozmiar sterty (heap)
         * -Xmx<rozmiar>                 - maksymalny rozmiar sterty (heap)
         * -Xss<rozmiar>                 - rozmiar stosu KAZDEGO watku (Lesson06)
         * -XX:MaxMetaspaceSize=<rozmiar> - twardy limit Metaspace (Lesson06)
         * -XX:+UseG1GC                  - wlacz kolektor G1 (domyslny od Javy 9)
         * -XX:+UseZGC                   - wlacz kolektor ZGC (bardzo krotkie pauzy)
         * -Xlog:gc                      - wlacz logi Garbage Collectora (Lesson08-12)
         * -XX:+PrintCompilation         - wypisuj kazda metode skompilowana przez JIT (Lesson13-14)
         * -XX:StartFlightRecording=...  - odpal JFR od razu przy starcie JVM (Lesson18)
         * -XX:+HeapDumpOnOutOfMemoryError - automatyczny heap dump przy OOM (Lesson16)
         */
        printKeyJvmFlags();

        /*
         * ============================================================
         * 🔍 METODYKA STROJENIA - "NAJPIERW ZMIERZ, POTEM ZMIENIAJ"
         * ============================================================
         * 1. ZMIERZ / ZAOBSERWUJ - zanim cokolwiek zmienisz, zbierz dane:
         *    logi GC (-Xlog:gc), MXBeany (Lesson06), JFR (Lesson18), heap/
         *    thread dump (Lesson16-17). Nigdy nie zgaduj "na oko".
         * 2. ZMIEN JEDNA RZECZ NA RAZ - np. tylko rozmiar sterty ALBO tylko
         *    kolektor GC, nigdy kilka zmiennych naraz - inaczej nie wiadomo,
         *    co faktycznie wplynelo na wynik.
         * 3. ZMIERZ PONOWNIE, W TYCH SAMYCH WARUNKACH - porownuj "jablka do
         *    jablek": to samo obciazenie, ta sama maszyna, ten sam czas trwania.
         * 4. POWTARZAJ - strojenie JVM to iteracyjny proces, nie jednorazowa
         *    czynnosc. Warunki produkcyjne (ruch, dane) zmieniaja sie w czasie.
         */
        printTuningMethodology();

        /*
         * ============================================================
         * 📌 CAPSTONE: JEDNO SPOJNE DEMO LACZACE LEKCJE 16-19
         * ============================================================
         * Dla TEGO SAMEGO scenariusza obciazeniowego laczymy:
         *  (a) MemoryMXBean PRZED obciazeniem (Lesson06)
         *  (b) start Recording JFR PRZED obciazeniem (Lesson18)
         *  (c) obciazenie: alokacje smieci + odrobina pracy CPU + prawdziwy,
         *      bezpieczny (daemon + bounded join) deadlock jak w Lesson17
         *  (d) thread dump + wykrycie deadlocka przez findDeadlockedThreads() (Lesson17)
         *  (e) zatrzymanie i zrzut JFR do pliku .jfr (Lesson18)
         *  (f) heap dump przez HotSpotDiagnosticMXBean do pliku .hprof (Lesson16)
         *  (g) MemoryMXBean PO obciazeniu + krotkie podsumowanie
         */
        runCapstoneDemo();

        System.out.println("\n=== KONIEC LEKCJI 20 - KONIEC ROZDZIALU _15_jvm_internals ===");
    }

    private static void printKeyJvmFlags() {
        System.out.println("=== KLUCZOWE FLAGI JVM (podsumowanie calego rozdzialu) ===");
        System.out.println(" -Xms<rozmiar>                    poczatkowy rozmiar sterty");
        System.out.println(" -Xmx<rozmiar>                    maksymalny rozmiar sterty");
        System.out.println(" -Xss<rozmiar>                    rozmiar stosu kazdego watku");
        System.out.println(" -XX:MaxMetaspaceSize=<rozmiar>   twardy limit Metaspace");
        System.out.println(" -XX:+UseG1GC / -XX:+UseZGC       wybor kolektora GC");
        System.out.println(" -Xlog:gc                         logi Garbage Collectora");
        System.out.println(" -XX:+PrintCompilation            log kompilacji JIT");
        System.out.println(" -XX:StartFlightRecording=...     JFR wlaczony od startu JVM");
        System.out.println(" -XX:+HeapDumpOnOutOfMemoryError  automatyczny heap dump przy OOM");
    }

    private static void printTuningMethodology() {
        System.out.println("\n=== METODYKA STROJENIA ===");
        System.out.println(" 1. Zmierz/zaobserwuj (logi GC, MXBeany, JFR, heap/thread dump)");
        System.out.println(" 2. Zmien JEDNA rzecz na raz");
        System.out.println(" 3. Zmierz ponownie w tych samych warunkach");
        System.out.println(" 4. Powtarzaj - to proces iteracyjny, nie jednorazowa czynnosc");
    }

    private static void runCapstoneDemo() throws InterruptedException {
        System.out.println("\n=== CAPSTONE: PELNE DEMO DIAGNOSTYCZNE (Lekcje 16-19 razem) ===");

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        // (a) Stan pamieci PRZED
        MemoryUsage heapBefore = memoryMXBean.getHeapMemoryUsage();
        System.out.println("(a) HEAP PRZED obciazeniem: used=" + formatBytes(heapBefore.getUsed()));

        Path jfrFile = null;
        Path heapDumpFile = null;

        try (Recording recording = new Recording()) {
            // (b) Start JFR PRZED obciazeniem
            recording.enable("jdk.CPULoad").withPeriod(Duration.ofMillis(100));
            recording.enable("jdk.GarbageCollection");
            recording.start();
            System.out.println("(b) Nagranie JFR wystartowane.");

            // (c) Obciazenie: alokacje + praca CPU + bezpieczny deadlock
            List<byte[]> garbage = allocateGarbageFor(Duration.ofMillis(400));
            System.out.println("    Zaalokowano " + garbage.size() + " tymczasowych bufferow (obciazenie pamieciowe).");

            DeadlockPair deadlockPair = startSafeDeadlock();

            // Dajemy watkom czas na wejscie w deadlock przed diagnostyka.
            sleepQuiet(600);

            // (d) Thread dump + wykrycie deadlocka
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            long[] deadlockedIds = threadMXBean.findDeadlockedThreads();
            if (deadlockedIds == null) {
                System.out.println("(d) Deadlock nie zostal wykryty na tej maszynie (timing moze sie roznic).");
            } else {
                System.out.println("(d) WYKRYTO deadlock - " + deadlockedIds.length + " zakleszczonych watkow:");
                ThreadInfo[] infos = threadMXBean.getThreadInfo(deadlockedIds, 5);
                for (ThreadInfo info : infos) {
                    System.out.println("     - \"" + info.getThreadName() + "\" stan=" + info.getThreadState()
                            + " czeka na=" + info.getLockName() + " trzymany przez=" + info.getLockOwnerName());
                }
            }

            // Bounded join - watki deadlocka sa daemon, wiec to bezpieczne.
            deadlockPair.threadOne.join(1500);
            deadlockPair.threadTwo.join(1500);

            recording.stop();
            System.out.println("(b/e) Nagranie JFR zatrzymane.");

            // (e) Zrzut JFR do pliku
            Path tempDir = Files.createTempDirectory("javaquest-lesson20-capstone");
            jfrFile = tempDir.resolve("lesson20-capstone.jfr");
            recording.dump(jfrFile);
            System.out.println("(e) Plik .jfr zapisany: " + jfrFile.toAbsolutePath()
                    + " (" + formatBytes(Files.size(jfrFile)) + ")");

            // (f) Heap dump
            HotSpotDiagnosticMXBean diagnosticBean =
                    ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);
            heapDumpFile = tempDir.resolve("lesson20-capstone.hprof");
            diagnosticBean.dumpHeap(heapDumpFile.toString(), true);
            System.out.println("(f) Plik .hprof zapisany: " + heapDumpFile.toAbsolutePath()
                    + " (" + formatBytes(Files.size(heapDumpFile)) + ")");

            // Referencja utrzymywana do tego miejsca, zeby "garbage" bylo zywe
            // podczas zrzutow powyzej.
            System.out.println("    (garbage nadal referencjonowane: " + (garbage.size() > 0) + ")");
        } catch (IOException e) {
            System.out.println("Blad podczas capstone demo (JFR/heap dump): " + e.getMessage());
        }

        // (g) Stan pamieci PO
        MemoryUsage heapAfter = memoryMXBean.getHeapMemoryUsage();
        System.out.println("\n(g) HEAP PO obciazeniu: used=" + formatBytes(heapAfter.getUsed()));

        System.out.println("\n=== PODSUMOWANIE CAPSTONE ===");
        System.out.println(" - Zmierzono heap PRZED/PO (MemoryMXBean, Lesson06)");
        System.out.println(" - Nagrano zdarzenia CPU/GC w JFR i zrzucono je do pliku .jfr (Lesson18)"
                + (jfrFile != null ? " -> " + jfrFile.toAbsolutePath() : ""));
        System.out.println(" - Wywolano i wykryto prawdziwy deadlock przez ThreadMXBean (Lesson17)");
        System.out.println(" - Zrzucono pelna sterte do pliku .hprof (Lesson16)"
                + (heapDumpFile != null ? " -> " + heapDumpFile.toAbsolutePath() : ""));
        System.out.println(" - Sampling profiling (Lesson19) uzupelnia ten zestaw, gdy potrzebna");
        System.out.println("   jest analiza 'ktora metoda zjada najwiecej czasu CPU' w czasie rzeczywistym.");
    }

    private static List<byte[]> allocateGarbageFor(Duration duration) {
        List<byte[]> garbage = new ArrayList<>();
        long end = System.currentTimeMillis() + duration.toMillis();
        while (System.currentTimeMillis() < end) {
            garbage.add(new byte[5_000]);
            if (garbage.size() > 300) {
                garbage.subList(0, 150).clear(); // odrzuc czesc, zeby GC mial co sprzatac
            }
        }
        return garbage;
    }

    private static class DeadlockPair {
        final Thread threadOne;
        final Thread threadTwo;

        DeadlockPair(Thread threadOne, Thread threadTwo) {
            this.threadOne = threadOne;
            this.threadTwo = threadTwo;
        }
    }

    private static DeadlockPair startSafeDeadlock() {
        final Object lockA = new Object();
        final Object lockB = new Object();

        Thread threadOne = new Thread(() -> {
            synchronized (lockA) {
                sleepQuiet(300);
                synchronized (lockB) {
                    System.out.println("Capstone-A: zdobyl obie blokady (nigdy tu nie dotrze)");
                }
            }
        }, "Lesson20-Capstone-A");

        Thread threadTwo = new Thread(() -> {
            synchronized (lockB) {
                sleepQuiet(300);
                synchronized (lockA) {
                    System.out.println("Capstone-B: zdobyl obie blokady (nigdy tu nie dotrze)");
                }
            }
        }, "Lesson20-Capstone-B");

        // KLUCZOWE: watki demoniczne, zeby JVM mogla zakonczyc program mimo deadlocka.
        threadOne.setDaemon(true);
        threadTwo.setDaemon(true);

        threadOne.start();
        threadTwo.start();

        return new DeadlockPair(threadOne, threadTwo);
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static String formatBytes(long bytes) {
        if (bytes < 0) {
            return "n/a";
        }
        double mb = bytes / (1024.0 * 1024.0);
        return String.format("%.2f MB (%d B)", mb, bytes);
    }
}
