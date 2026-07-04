package com.example.javaquest._05_multithreading.Lesson06_ThreadLifecycleAndStates;

import java.util.concurrent.locks.LockSupport;

public class _Lesson06_ThreadLifecycleAndStates {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 CYKL ŻYCIA WĄTKU – Thread.State
         * ============================================================
         * Każdy wątek w Javie w danym momencie znajduje się w JEDNYM
         * z 6 stanów opisanych przez enum `Thread.State`. Aktualny stan
         * odczytujemy metodą `getState()`.
         *
         * Stany:
         *   1) NEW            - utworzony (`new Thread(...)`), ale start()
         *                        jeszcze nie zostało wywołane
         *   2) RUNNABLE       - gotowy do wykonania lub aktualnie wykonywany
         *                        przez procesor (Java NIE rozróżnia tych
         *                        dwóch podstanów - oba to RUNNABLE)
         *   3) BLOCKED        - czeka na wejście do monitora `synchronized`
         *                        zajętego przez inny wątek
         *   4) WAITING        - czeka W NIESKOŃCZONOŚĆ (bez timeoutu) na
         *                        akcję innego wątku: wait(), join(),
         *                        LockSupport.park()
         *   5) TIMED_WAITING  - jak WAITING, ale z limitem czasu: sleep(),
         *                        wait(timeout), join(timeout),
         *                        LockSupport.parkNanos()
         *   6) TERMINATED     - run() zakończone, wątku nie da się
         *                        ponownie uruchomić
         *
         * ============================================================
         * 🔍 ASCII DIAGRAM PRZEJŚĆ MIĘDZY STANAMI
         * ============================================================
         *
         *   new Thread()
         *        |
         *        v
         *     [ NEW ]
         *        | start()
         *        v
         *   [ RUNNABLE ] <-----------------------------+
         *     |   |   |                                |
         *     |   |   | czeka na monitor synchronized   |
         *     |   |   v                                 |
         *     |   | [ BLOCKED ] --- monitor zwolniony ---+
         *     |   |
         *     |   | wait()/join()/park() BEZ timeoutu
         *     |   v
         *     | [ WAITING ] --- notify()/notifyAll()/    |
         *     |               watek zakonczony/unpark() -+
         *     |
         *     | sleep()/wait(t)/join(t)/parkNanos(t)
         *     v
         *   [ TIMED_WAITING ] --- uplynal czas LUB       |
         *                          notify()/zakonczenie  |
         *                          -----------------------+
         *        (z RUNNABLE, po zakonczeniu run())
         *        v
         *   [ TERMINATED ]   <- stan koncowy, BEZ POWROTU
         *
         * Uwaga: z BLOCKED/WAITING/TIMED_WAITING wątek zawsze wraca
         * do RUNNABLE (nigdy nie przeskakuje bezpośrednio do innego
         * stanu oczekiwania ani do TERMINATED).
         */

        /*
         * ============================================================
         * 🔹 1) NEW – utworzony, start() jeszcze nie wywołane
         * ============================================================
         */

        System.out.println("=== 1) NEW ===");
        Thread nowyWatek = new Thread(() -> { });
        System.out.println("Stan zaraz po 'new Thread(...)': " + nowyWatek.getState());
        // -> NEW

        /*
         * ============================================================
         * 🔹 2) RUNNABLE – gotowy/wykonywany
         * ============================================================
         * Po start() wątek przechodzi w RUNNABLE. Żeby "złapać" go
         * właśnie w tym stanie, dajemy mu trochę pracy (pętlę) i sprawdzamy
         * stan z main tuż po starcie - w praktyce, dopóki wątek nie skończy
         * pracy, powinien być RUNNABLE.
         */

        System.out.println("\n=== 2) RUNNABLE ===");
        Thread pracujacy = new Thread(() -> {
            long suma = 0;
            for (long i = 0; i < 200_000_000L; i++) {
                suma += i;
            }
            if (suma < 0) System.out.println(suma); // zeby JIT nie wyrzucil petli
        }, "Watek-Pracujacy");
        pracujacy.start();
        Thread.sleep(20); // krotka chwila, by watek zdazyl wejsc w wykonywanie
        System.out.println("Stan w trakcie pracy: " + pracujacy.getState());
        // -> RUNNABLE (z bardzo duzym prawdopodobienstwem)
        pracujacy.join(5_000); // bezpiecznik

        /*
         * ============================================================
         * 🔹 3) BLOCKED – czeka na zajęty monitor synchronized
         * ============================================================
         * Dwa wątki "walczą" o tę samą blokadę: wątek A wchodzi pierwszy
         * i trzyma blokadę przez chwilę (sleep wewnątrz synchronized).
         * Wątek B próbuje wejść do TEGO SAMEGO bloku synchronized - dopóki
         * A nie zwolni blokady, B jest w stanie BLOCKED.
         */

        System.out.println("\n=== 3) BLOCKED ===");
        Object wspolnyMonitor = new Object();

        Thread watekA = new Thread(() -> {
            synchronized (wspolnyMonitor) {
                try {
                    Thread.sleep(400); // trzyma monitor przez 400ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Watek-A");

        Thread watekB = new Thread(() -> {
            synchronized (wspolnyMonitor) {
                // nic - samo wejscie wystarczy do demonstracji
            }
        }, "Watek-B");

        watekA.start();
        Thread.sleep(50); // upewniamy sie, ze A zdazyl wejsc do synchronized
        watekB.start();
        Thread.sleep(50); // dajemy B chwile, zeby "utknal" probujac wejsc
        System.out.println("Stan watku B (probuje wejsc do zajetego monitora): " + watekB.getState());
        // -> BLOCKED
        watekA.join(5_000);
        watekB.join(5_000);

        /*
         * ============================================================
         * 🔹 4) WAITING – czeka bez limitu czasu
         * ============================================================
         * Klasyczne źródła WAITING: wait() bez argumentu, join() bez
         * argumentu, LockSupport.park(). Poniżej demo z wait()/notify():
         * wątek wchodzi do synchronized i wywołuje lock.wait() - zawiesza
         * się w NIESKOŃCZONOŚĆ, dopóki main go nie obudzi przez notify().
         */

        System.out.println("\n=== 4) WAITING (Object.wait() bez timeoutu) ===");
        Object waitLock = new Object();
        Thread czekajacy = new Thread(() -> {
            synchronized (waitLock) {
                try {
                    waitLock.wait(); // WAITING, dopoki ktos nie wywola notify()
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Watek-Czekajacy");

        czekajacy.start();
        Thread.sleep(100); // dajemy czas na wejscie w wait()
        System.out.println("Stan przed notify(): " + czekajacy.getState());
        // -> WAITING

        synchronized (waitLock) {
            waitLock.notify(); // budzimy watek - bezpiecznik, zeby main sie nie zawiesil
        }
        czekajacy.join(5_000);
        System.out.println("Stan po notify() i zakonczeniu: " + czekajacy.getState());
        // -> TERMINATED

        // Alternatywne źródła WAITING (bez osobnej demonstracji, ten sam mechanizm):
        // - t.join()                (bez timeoutu, na wątku wywołującym)
        // - LockSupport.park()      (niższopoziomowy mechanizm - patrz przyklad ponizej)

        System.out.println("\n(dodatkowo) LockSupport.park() rowniez daje WAITING:");
        Thread zaparkowany = new Thread(() -> {
            LockSupport.park(); // WAITING, dopoki ktos nie wywola unpark()
        }, "Watek-Zaparkowany");
        zaparkowany.start();
        Thread.sleep(100);
        System.out.println("Stan zaparkowanego watku: " + zaparkowany.getState());
        // -> WAITING
        LockSupport.unpark(zaparkowany); // budzimy - bezpiecznik
        zaparkowany.join(5_000);

        /*
         * ============================================================
         * 🔹 5) TIMED_WAITING – czeka z limitem czasu
         * ============================================================
         * Źródła: sleep(millis), wait(millis), join(millis),
         * LockSupport.parkNanos(). Poniżej demo z sleep():
         */

        System.out.println("\n=== 5) TIMED_WAITING (sleep(millis)) ===");
        Thread spiacy = new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Watek-Spiacy");
        spiacy.start();
        Thread.sleep(50); // dajemy czas, zeby watek wszedl w sleep()
        System.out.println("Stan w trakcie sleep(400): " + spiacy.getState());
        // -> TIMED_WAITING
        spiacy.join(5_000);

        /*
         * ============================================================
         * 🔹 6) TERMINATED – run() zakończone
         * ============================================================
         * Po zakończeniu run() wątek trwale przechodzi w TERMINATED.
         * Nie da się go "wskrzesić" - ponowne wywołanie start() rzuca
         * IllegalThreadStateException.
         */

        System.out.println("\n=== 6) TERMINATED ===");
        Thread krotkotrwaly = new Thread(() -> { });
        krotkotrwaly.start();
        krotkotrwaly.join(5_000);
        System.out.println("Stan po zakonczeniu run(): " + krotkotrwaly.getState());
        // -> TERMINATED

        try {
            krotkotrwaly.start(); // ponowny start() na zakonczonym watku
            System.out.println("To sie nie powinno wydarzyc!");
        } catch (IllegalThreadStateException e) {
            System.out.println("❌ Ponowny start() rzucil: " + e.getClass().getSimpleName());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Thread.State: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING,
         *   TERMINATED - getState() zwraca aktualny stan
         * - NEW -> RUNNABLE po start()
         * - RUNNABLE -> BLOCKED przy probie wejscia do zajetego synchronized
         * - RUNNABLE -> WAITING przy wait()/join()/park() BEZ timeoutu
         * - RUNNABLE -> TIMED_WAITING przy sleep()/wait(t)/join(t)/parkNanos(t)
         * - Z BLOCKED/WAITING/TIMED_WAITING wątek zawsze wraca do RUNNABLE
         * - RUNNABLE -> TERMINATED po zakonczeniu run() - stan koncowy,
         *   ponowny start() rzuca IllegalThreadStateException
         */
    }
}
