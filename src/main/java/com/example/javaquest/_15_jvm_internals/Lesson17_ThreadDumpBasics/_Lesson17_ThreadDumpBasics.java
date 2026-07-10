package com.example.javaquest._15_jvm_internals.Lesson17_ThreadDumpBasics;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class _Lesson17_ThreadDumpBasics {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 17: THREAD DUMP - ZRZUT STANU WSZYSTKICH WATKOW ===");

        /*
         * ============================================================
         * 📦 CO TO JEST THREAD DUMP I PO CO SIE GO ROBI
         * ============================================================
         * - Thread dump to snapshot ("zdjecie") stanu WSZYSTKICH watkow
         *   dzialajacych w danej chwili w JVM: ich nazwa, stan
         *   (Thread.State - patrz _05_multithreading/Lesson06_ThreadLifecycleAndStates),
         *   pelny stack trace (jaka metoda w jakiej klasie jest wlasnie
         *   wykonywana i przez co zostala wywolana), oraz informacje
         *   o LOCKACH - ktora blokade watek trzyma, a na ktora czeka.
         * - Glowne zastosowanie w praktyce: diagnoza "aplikacja sie
         *   zawiesila" / "aplikacja nie odpowiada" - thread dump pokazuje
         *   DOKLADNIE, na czym kazdy watek utknal w danym momencie.
         * - Drugie, bardzo konkretne zastosowanie: WYKRYWANIE DEADLOCKOW
         *   (nawiazanie do _05_multithreading/Lesson25_Deadlock) - JVM
         *   potrafi SAMA przeanalizowac graf "kto na kogo czeka" i
         *   zwrocic liste watkow bedacych w zakleszczeniu.
         */
        System.out.println("Thread dump to zrzut stanu WSZYSTKICH watkow JVM - stack trace + stan + locki.\n");

        /*
         * ============================================================
         * 🔹 ThreadMXBean.dumpAllThreads(...) - PODSTAWOWY THREAD DUMP
         * ============================================================
         * - ManagementFactory.getThreadMXBean() zwraca ThreadMXBean -
         *   glowny punkt wejscia do introspekcji watkow.
         * - dumpAllThreads(lockedMonitors, lockedSynchronizers) zwraca
         *   tablice ThreadInfo - po jednym obiekcie na kazdy zywy watek:
         *     * lockedMonitors=true      -> dolacz info o obiektach
         *       zablokowanych przez synchronized
         *     * lockedSynchronizers=true -> dolacz info o blokadach
         *       java.util.concurrent.locks (np. ReentrantLock)
         * - Kazdy ThreadInfo udostepnia m.in.: getThreadName(),
         *   getThreadState(), getStackTrace() (StackTraceElement[]).
         */
        demonstrateBasicThreadDump();

        /*
         * ============================================================
         * 🔍 PRAWDZIWY DEADLOCK + PROGRAMOWE WYKRYCIE PRZEZ JVM
         * ============================================================
         * Odtwarzamy DOKLADNIE ten sam scenariusz co w
         * _05_multithreading/Lesson25_Deadlock (dwa zamki, odwrotna
         * kolejnosc zdobywania, watki demoniczne + bounded join), ale
         * tym razem patrzymy na niego z perspektywy DIAGNOSTYKI: zamiast
         * tylko "zgadywac" po isAlive(), pytamy JVM WPROST, przez
         * ThreadMXBean.findDeadlockedThreads(), ktore watki sa
         * zakleszczone - dokladnie tak, jak zrobilby to prawdziwy
         * thread dump (np. z `jstack <PID>` albo z pliku wygenerowanego
         * przy Ctrl+Break/SIGQUIT).
         *
         * ⚠️ Tak jak w Lesson25_Deadlock: oba watki sa DEMONICZNE, a
         * main() czeka na nie tylko ograniczony czas (join(2000)) -
         * program konczy sie normalnie, mimo ze deadlock formalnie
         * nadal trwa.
         */
        demonstrateDeadlockDetection();

        /*
         * ============================================================
         * 📌 SKAD WZIAC THREAD DUMP BEZ ZMIANY KODU (PRODUKCJA)
         * ============================================================
         * Tak jak przy heap dumpie (Lesson16), thread dump mozna zrobic
         * tez z ZEWNATRZ dzialajacego procesu:
         *
         *   jstack <PID>                     - klasyczne narzedzie JDK
         *   jcmd <PID> Thread.print           - nowsza alternatywa
         *   kill -3 <PID> (Linux) / Ctrl+Break (Windows w konsoli)
         *                                     - wypisuje dump na stdout/stderr
         *
         * Dokladnie te same informacje (nazwa watku, stan, stack trace,
         * ewentualny deadlock) co przez ThreadMXBean z poziomu kodu.
         */
        explainCliAlternatives();

        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static void demonstrateBasicThreadDump() {
        System.out.println("=== ThreadMXBean.dumpAllThreads(...) - WSZYSTKIE WATKI TEJ JVM ===");

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] allThreads = threadMXBean.dumpAllThreads(true, true);

        System.out.println("Ta JVM zglasza " + allThreads.length + " watkow. Pierwsze kilka (max 5):");

        int printed = 0;
        for (ThreadInfo info : allThreads) {
            if (printed >= 5) {
                break;
            }
            System.out.println(" - \"" + info.getThreadName() + "\" stan=" + info.getThreadState());
            StackTraceElement[] stack = info.getStackTrace();
            int depth = Math.min(2, stack.length);
            for (int i = 0; i < depth; i++) {
                System.out.println("     at " + stack[i]);
            }
            printed++;
        }
    }

    private static void demonstrateDeadlockDetection() throws InterruptedException {
        System.out.println("\n=== PRAWDZIWY DEADLOCK + WYKRYCIE PRZEZ findDeadlockedThreads() ===");

        final Object lockA = new Object();
        final Object lockB = new Object();

        Thread threadOne = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Watek-A: zdobyl lockA, czeka na lockB...");
                sleepQuiet(300);
                synchronized (lockB) {
                    System.out.println("Watek-A: zdobyl lockB (nigdy tu nie dotrze)");
                }
            }
        }, "Lesson17-Watek-A");

        Thread threadTwo = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Watek-B: zdobyl lockB, czeka na lockA...");
                sleepQuiet(300);
                synchronized (lockA) {
                    System.out.println("Watek-B: zdobyl lockA (nigdy tu nie dotrze)");
                }
            }
        }, "Lesson17-Watek-B");

        // KLUCZOWE: watki demoniczne - JVM zakonczy sie mimo trwajacego deadlocka.
        threadOne.setDaemon(true);
        threadTwo.setDaemon(true);

        threadOne.start();
        threadTwo.start();

        // Dajemy watkom czas na wejscie w faktyczny deadlock (kazdy spi 300ms
        // po zdobyciu pierwszej blokady, potem probuje zdobyc druga i utyka).
        sleepQuiet(600);

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedIds = threadMXBean.findDeadlockedThreads();

        if (deadlockedIds == null) {
            System.out.println("Na tej maszynie akurat nie wykryto deadlocka (timing moze sie roznic).");
        } else {
            System.out.println("JVM WYKRYLA deadlock! Liczba zakleszczonych watkow: " + deadlockedIds.length);
            ThreadInfo[] deadlockedInfos = threadMXBean.getThreadInfo(deadlockedIds, Integer.MAX_VALUE);

            for (ThreadInfo info : deadlockedInfos) {
                System.out.println("\n --- Watek: \"" + info.getThreadName() + "\" ---");
                System.out.println("     Stan: " + info.getThreadState());
                System.out.println("     Czeka na lock: " + info.getLockName());
                System.out.println("     Ten lock trzyma watek: " + info.getLockOwnerName());
                System.out.println("     Stack trace:");
                for (StackTraceElement element : info.getStackTrace()) {
                    System.out.println("       at " + element);
                }
            }
        }

        // Bounded join - NIGDY nie czekamy w nieskonczonosc na watki, ktore
        // moga byc zakleszczone.
        threadOne.join(2000);
        threadTwo.join(2000);

        System.out.println("\nWatki sa demoniczne - JVM zakonczy program normalnie, mimo ze deadlock nadal trwa.");
    }

    private static void explainCliAlternatives() {
        System.out.println("\n=== ALTERNATYWY CLI (bez zmiany kodu aplikacji) ===");
        System.out.println(" jstack <PID>          - klasyczny thread dump z zewnatrz procesu");
        System.out.println(" jcmd <PID> Thread.print - nowsza alternatywa dla jstack");
        System.out.println(" kill -3 <PID> (Linux) / Ctrl+Break (Windows)  - dump na stdout/stderr procesu");
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
