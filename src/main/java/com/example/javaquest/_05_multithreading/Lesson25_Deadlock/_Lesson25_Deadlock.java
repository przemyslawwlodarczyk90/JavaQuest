package com.example.javaquest._05_multithreading.Lesson25_Deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class _Lesson25_Deadlock {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 DEADLOCK (ZAKLESZCZENIE) – CZYM JEST?
         * ============================================================
         * Deadlock to sytuacja, w której dwa (lub więcej) wątki są
         * ZABLOKOWANE NA ZAWSZE, ponieważ każdy z nich czeka na zasób
         * (np. locka), który trzyma inny wątek z tej samej grupy –
         * i żaden z nich nigdy go nie zwolni.
         *
         * Klasyczny przykład:
         * - Wątek A trzyma Lock1 i czeka na Lock2
         * - Wątek B trzyma Lock2 i czeka na Lock1
         * → żaden z wątków nie może kontynuować – zakleszczenie.
         *
         * ⚠️ UWAGA BEZPIECZEŃSTWA: prawdziwy deadlock blokuje wątki
         * NA ZAWSZE. Żeby ten przykład bezpiecznie się zakończył,
         * wątki demonstrujące deadlock są wątkami DEMONICZNYMI
         * (setDaemon(true)) i main() czeka na nie tylko ograniczony
         * czas (join(2000)) zamiast czekać w nieskończoność.
         */

        /*
         * ============================================================
         * 🔹 4 WARUNKI KONIECZNE DEADLOCKA (Coffman conditions)
         * ============================================================
         * Deadlock może wystąpić TYLKO, gdy spełnione są jednocześnie
         * wszystkie 4 warunki:
         *
         * 1. Mutual exclusion (wzajemne wykluczanie)
         *    – zasób może być trzymany tylko przez jeden wątek naraz.
         * 2. Hold and wait (trzymaj i czekaj)
         *    – wątek trzyma już jeden zasób i czeka na kolejny.
         * 3. No preemption (brak wywłaszczenia)
         *    – zasobu nie można wątkowi odebrać siłą, musi go zwolnić sam.
         * 4. Circular wait (cykliczne oczekiwanie)
         *    – istnieje cykl wątków, z których każdy czeka na zasób
         *      trzymany przez kolejny w cyklu (A czeka na B, B na A).
         *
         * Aby ZAPOBIEC deadlockowi, wystarczy złamać JEDEN z tych warunków
         * – najłatwiej złamać "circular wait" (patrz niżej).
         */

        System.out.println("=== Demonstracja deadlocka (bezpieczna, wątki daemon) ===");

        final Object lockA = new Object();
        final Object lockB = new Object();

        Thread threadOne = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Watek-1: zdobyl lockA, czeka na lockB...");
                sleepQuiet(300);
                synchronized (lockB) {
                    System.out.println("Watek-1: zdobyl lockB (nigdy tu nie dotrze)");
                }
            }
        }, "Watek-1");

        Thread threadTwo = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Watek-2: zdobyl lockB, czeka na lockA...");
                sleepQuiet(300);
                synchronized (lockA) {
                    System.out.println("Watek-2: zdobyl lockA (nigdy tu nie dotrze)");
                }
            }
        }, "Watek-2");

        // KLUCZOWE: wątki demoniczne, żeby JVM mógł zakończyć program,
        // nawet jeśli te wątki zostaną zablokowane na zawsze.
        threadOne.setDaemon(true);
        threadTwo.setDaemon(true);

        threadOne.start();
        threadTwo.start();

        // Czekamy TYLKO ograniczony czas – nigdy join() bez timeoutu
        // na wątki, które mogą się zakleszczyć!
        threadOne.join(2000);
        threadTwo.join(2000);

        if (threadOne.isAlive() && threadTwo.isAlive()) {
            System.out.println(
                    "Po 2 sekundach oczekiwania oba watki nadal sa zablokowane – to jest deadlock.\n" +
                    "Watek-1 trzyma lockA i czeka na lockB, Watek-2 trzyma lockB i czeka na lockA.\n" +
                    "Poniewaz sa to watki daemon, JVM i tak zakonczy dzialanie programu normalnie.");
        } else {
            System.out.println("Watki zakonczyly sie (na tej maszynie akurat nie doszlo do deadlocka).");
        }

        System.out.println();

        /*
         * ============================================================
         * 🔍 JAK UNIKAĆ DEADLOCKA – SPÓJNA KOLEJNOŚĆ ZDOBYWANIA BLOKAD
         * ============================================================
         * Jeśli WSZYSTKIE wątki zawsze zdobywają blokady w TEJ SAMEJ
         * kolejności (np. zawsze najpierw lockA, potem lockB), to
         * "circular wait" nie może wystąpić – łamiemy warunek konieczny.
         *
         * Poniżej ten sam scenariusz, ale oba wątki zdobywają blokady
         * w tej samej kolejności (lockA -> lockB) – deadlock niemożliwy.
         */

        System.out.println("=== Naprawiona wersja – spójna kolejnosc lockA -> lockB ===");

        Thread fixedOne = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Fixed-1: zdobyl lockA");
                sleepQuiet(100);
                synchronized (lockB) {
                    System.out.println("Fixed-1: zdobyl lockB – gotowe");
                }
            }
        }, "Fixed-1");

        Thread fixedTwo = new Thread(() -> {
            synchronized (lockA) { // <- ta sama kolejnosc co Fixed-1: najpierw lockA
                System.out.println("Fixed-2: zdobyl lockA");
                sleepQuiet(100);
                synchronized (lockB) {
                    System.out.println("Fixed-2: zdobyl lockB – gotowe");
                }
            }
        }, "Fixed-2");

        fixedOne.start();
        fixedTwo.start();
        fixedOne.join(2000);
        fixedTwo.join(2000);

        System.out.println();

        /*
         * ============================================================
         * 🔹 tryLock() Z TIMEOUTEM ZAMIAST lock() – DRUGI SPOSÓB
         * ============================================================
         * ReentrantLock.tryLock(timeout, unit) próbuje zdobyć blokadę
         * przez określony czas – jeśli się nie uda, wątek może się
         * WYCOFAĆ (zwolnić już trzymane blokady) zamiast czekać wiecznie.
         * To łamie warunek "no preemption" – wątek sam "oddaje" zasób.
         */

        System.out.println("=== tryLock() z timeoutem – unikanie deadlocka ===");

        Lock reentrantA = new ReentrantLock();
        Lock reentrantB = new ReentrantLock();

        Runnable safeTask = () -> {
            try {
                boolean gotA = reentrantA.tryLock(200, TimeUnit.MILLISECONDS);
                if (gotA) {
                    try {
                        sleepQuiet(50);
                        boolean gotB = reentrantB.tryLock(200, TimeUnit.MILLISECONDS);
                        if (gotB) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ": zdobyl obie blokady!");
                            } finally {
                                reentrantB.unlock();
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName()
                                    + ": nie udalo sie zdobyc lockB w czasie – wycofuje sie (bez deadlocka!)");
                        }
                    } finally {
                        reentrantA.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ": nie udalo sie zdobyc lockA");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread trySafeOne = new Thread(safeTask, "TrySafe-1");
        Thread trySafeTwo = new Thread(safeTask, "TrySafe-2");
        trySafeOne.start();
        trySafeTwo.start();
        trySafeOne.join(2000);
        trySafeTwo.join(2000);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Deadlock: wątki są ZABLOKOWANE na zawsze, czekając na siebie
         *   nawzajem w cyklu (circular wait).
         * - 4 warunki konieczne: mutual exclusion, hold and wait,
         *   no preemption, circular wait – złamanie JEDNEGO wystarczy,
         *   by zapobiec deadlockowi.
         * - Najprostsza obrona: SPÓJNA kolejność zdobywania blokad
         *   we wszystkich wątkach (np. zawsze wg jednego globalnego porządku).
         * - Alternatywa: ReentrantLock.tryLock(timeout) – wątek może się
         *   wycofać, zamiast czekać w nieskończoność.
         * ⚠️ Testując deadlocki – ZAWSZE używaj wątków daemon i join()
         *    z timeoutem, nigdy nie blokuj main() bezterminowo!
         */
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
