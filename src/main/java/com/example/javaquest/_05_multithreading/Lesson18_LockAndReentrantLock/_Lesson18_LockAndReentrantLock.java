package com.example.javaquest._05_multithreading.Lesson18_LockAndReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _Lesson18_LockAndReentrantLock {

    private static int sharedCounter = 0;

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 Lock / ReentrantLock – CO TO JEST?
         * ============================================================
         * Interfejs java.util.concurrent.locks.Lock to alternatywa dla
         * słowa kluczowego `synchronized`. Najczęstsza implementacja:
         * ReentrantLock. Podstawowe API:
         *
         * - lock()      - zdobądź blokadę (czekaj, aż będzie wolna)
         * - unlock()    - zwolnij blokadę
         * - tryLock()   - spróbuj zdobyć blokadę BEZ czekania - zwraca
         *                 od razu true/false
         * - tryLock(timeout, unit) - spróbuj zdobyć blokadę, ale czekaj
         *                 co najwyżej określony czas
         *
         * ⚠️ KLUCZOWA RÓŻNICA względem synchronized: `synchronized` zwalnia
         * blokadę AUTOMATYCZNIE (nawet przy wyjątku). Lock/unlock() NIE -
         * musimy to zrobić RĘCZNIE. Dlatego unlock() MUSI być w bloku
         * finally!
         */

        /*
         * ============================================================
         * ⚠️ DLACZEGO unlock() ZAWSZE W finally?
         * ============================================================
         * Jeśli kod między lock() a unlock() rzuci wyjątek, a unlock()
         * NIE jest w finally, blokada NIGDY nie zostanie zwolniona - inne
         * wątki będą czekać na nią W NIESKOŃCZONOŚĆ (deadlock).
         *
         * ❌ ŹLE:
         *   lock.lock();
         *   robCosCoMozeRzucicWyjatek();
         *   lock.unlock(); // <- NIGDY nie wykona się, jeśli powyżej wyjątek!
         *
         * ✅ DOBRZE:
         *   lock.lock();
         *   try {
         *       robCosCoMozeRzucicWyjatek();
         *   } finally {
         *       lock.unlock(); // <- wykona się ZAWSZE, nawet przy wyjątku
         *   }
         *
         * Pokażmy to na żywo: metoda symulująca błąd, ale mimo wyjątku
         * blokada zostaje poprawnie zwolniona dzięki finally.
         */

        System.out.println("=== Demo: unlock() w finally - blokada zwolniona mimo wyjątku ===");
        ReentrantLock demoLock = new ReentrantLock();
        try {
            demoLock.lock();
            try {
                throw new RuntimeException("Symulowany błąd wewnątrz sekcji krytycznej");
            } finally {
                demoLock.unlock();
                System.out.println("finally: unlock() wykonany, isLocked=" + demoLock.isLocked());
            }
        } catch (RuntimeException e) {
            System.out.println("Złapano wyjątek: " + e.getMessage());
        }
        System.out.println("Blokada wolna po wyjątku? " + !demoLock.isLocked());

        System.out.println();

        /*
         * ============================================================
         * 📦 PODSTAWOWE UŻYCIE: lock()/unlock() zamiast synchronized
         * ============================================================
         */

        System.out.println("=== Demo: ReentrantLock chroniący licznik ===");
        ReentrantLock counterLock = new ReentrantLock();
        sharedCounter = 0;
        int threadsCount = 4;
        int iterations = 10_000;

        Thread[] threads = new Thread[threadsCount];
        for (int t = 0; t < threadsCount; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    counterLock.lock();
                    try {
                        sharedCounter++;
                    } finally {
                        counterLock.unlock();
                    }
                }
            });
        }
        for (Thread th : threads) th.start();
        for (Thread th : threads) th.join(10_000); // bounded join

        System.out.println("Oczekiwany wynik: " + (threadsCount * iterations));
        System.out.println("Rzeczywisty wynik: " + sharedCounter);

        System.out.println();

        /*
         * ============================================================
         * 🔍 tryLock() – BEZ CZEKANIA I Z TIMEOUTEM
         * ============================================================
         * Przewaga nad synchronized: `synchronized` zawsze CZEKA (blokuje
         * wątek) aż blokada się zwolni. Lock daje alternatywę: tryLock()
         * pozwala SPRAWDZIĆ dostępność blokady i, jeśli jest zajęta, zrobić
         * coś innego zamiast czekać (np. zgłosić "zajęte, spróbuj później").
         */

        System.out.println("=== Demo: tryLock() bez timeoutu ===");
        ReentrantLock tryDemoLock = new ReentrantLock();
        tryDemoLock.lock(); // main "trzyma" blokadę
        try {
            Thread other = new Thread(() -> {
                boolean acquired = tryDemoLock.tryLock(); // od razu false, bo main trzyma lock
                System.out.println("[Inny wątek] tryLock() natychmiastowy -> zdobyto=" + acquired);
                if (acquired) {
                    tryDemoLock.unlock();
                }
            });
            other.start();
            other.join(5_000); // bounded join
        } finally {
            tryDemoLock.unlock();
        }

        System.out.println("=== Demo: tryLock(timeout) - czeka maksymalnie określony czas ===");
        // UWAGA: ReentrantLock pamięta WŁAŚCICIELA (wątek) - unlock() musi wywołać
        // TEN SAM wątek, który wywołał lock(). Dlatego "trzymający" lock i jego
        // zwolnienie muszą siedzieć w JEDNYM wątku (a nie: main.lock() + inny.unlock()).
        ReentrantLock timeoutLock = new ReentrantLock();
        Thread holderReleaser = new Thread(() -> {
            timeoutLock.lock();
            try {
                System.out.println("[Wątek trzymający] zdobył blokadę, trzyma ją przez 300ms");
                sleepQuietly(300);
            } finally {
                timeoutLock.unlock();
                System.out.println("[Wątek trzymający] zwolnił blokadę po 300ms");
            }
        });
        holderReleaser.start();
        sleepQuietly(30); // dajemy holderReleaser chwilę na zdobycie blokady jako pierwszy

        Thread waiter = new Thread(() -> {
            try {
                System.out.println("[Waiter] próbuję zdobyć blokadę z timeoutem 2s...");
                boolean acquired = timeoutLock.tryLock(2, TimeUnit.SECONDS);
                System.out.println("[Waiter] zdobyto blokadę w czasie timeoutu? " + acquired);
                if (acquired) {
                    timeoutLock.unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        waiter.start();

        holderReleaser.join(5_000); // bounded join
        waiter.join(5_000);

        System.out.println();

        /*
         * ============================================================
         * 🔍 Lock vs synchronized – PORÓWNANIE
         * ============================================================
         * | Cecha                       | synchronized | Lock/ReentrantLock |
         * |------------------------------|--------------|---------------------|
         * | Automatyczne zwolnienie      | ✅ tak       | ❌ nie (finally!)   |
         * | tryLock() (bez czekania)     | ❌ nie       | ✅ tak              |
         * | tryLock(timeout)             | ❌ nie       | ✅ tak              |
         * | Przerywalność oczekiwania    | ❌ nie       | ✅ lockInterruptibly()|
         * | Wiele "warunków" (Condition) | ❌ nie (1 monitor: wait/notify) | ✅ tak (newCondition()) |
         * | Prostota składni             | ✅ prostsza  | wymaga try/finally  |
         *
         * Condition - krótko: ReentrantLock.newCondition() tworzy obiekt
         * Condition, będący odpowiednikiem wait()/notify()/notifyAll(), ale
         * DLA KONKRETNEJ blokady Lock (a nie dla monitora obiektu jak w
         * synchronized). Pozwala mieć NIEZALEŻNE "kolejki oczekujących" w
         * ramach jednej blokady (np. osobno "bufor pełny" i "bufor pusty"),
         * czego zwykły synchronized/wait/notify nie oferuje wprost.
         *
         *   Lock lock = new ReentrantLock();
         *   Condition notFull  = lock.newCondition();
         *   Condition notEmpty = lock.newCondition();
         *   // notFull.await() / notFull.signalAll() - analogicznie do wait()/notifyAll()
         */

        System.out.println("=== Demo: Condition - odpowiednik wait/notify dla Lock ===");
        Lock conditionLock = new ReentrantLock();
        Condition readyCondition = conditionLock.newCondition();
        boolean[] ready = {false};

        Thread conditionWaiter = new Thread(() -> {
            conditionLock.lock();
            try {
                while (!ready[0]) {
                    try {
                        readyCondition.await(); // odpowiednik wait() na Condition
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("[ConditionWaiter] warunek spełniony, kontynuuję.");
            } finally {
                conditionLock.unlock();
            }
        });
        conditionWaiter.start();

        Thread.sleep(150);
        conditionLock.lock();
        try {
            ready[0] = true;
            readyCondition.signalAll(); // odpowiednik notifyAll()
        } finally {
            conditionLock.unlock();
        }
        conditionWaiter.join(5_000); // bounded join

        System.out.println();

        /*
         * ============================================================
         * 🔍 REENTRANCY – TEN SAM WĄTEK MOŻE ZDOBYĆ TĘ SAMĄ BLOKADĘ WIELOKROTNIE
         * ============================================================
         * "Reentrant" (ponownie wchodzący) oznacza: wątek, który JUŻ trzyma
         * blokadę, może zdobyć ją PONOWNIE (np. wywołując metodę, która
         * rekurencyjnie znów woła lock() na tej samej blokadzie) - BEZ
         * zakleszczenia samego siebie! JVM liczy, ile razy dany wątek
         * "wszedł" w blokadę (hold count) i wymaga tyle samo unlock(), by
         * ją faktycznie zwolnić.
         *
         * Gdyby blokada NIE była reentrant, wątek zablokowałby sam siebie
         * przy próbie ponownego lock() - klasyczny "self-deadlock".
         */

        System.out.println("=== Demo: reentrancy - rekurencyjne wejście w tę samą blokadę ===");
        ReentrantLock reentrantLock = new ReentrantLock();
        recursiveLock(reentrantLock, 3);
        System.out.println("Po zakończeniu rekurencji, holdCount=" + reentrantLock.getHoldCount() +
                " (0 = w pełni zwolniona)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Lock/ReentrantLock to alternatywa dla synchronized - trzeba
         *   RĘCZNIE wywołać unlock(), zawsze w finally!
         * - tryLock() - próba zdobycia blokady bez czekania (true/false od razu)
         * - tryLock(timeout, unit) - czekaj maksymalnie określony czas
         * - synchronized nie ma odpowiednika tryLock() - zawsze czeka
         *   w nieskończoność (albo do przerwania przez notify/interrupt)
         * - Condition (lock.newCondition()) - odpowiednik wait/notify/notifyAll,
         *   ale można mieć WIELE niezależnych Condition dla jednej blokady
         * - ReentrantLock jest REENTRANT - ten sam wątek może zdobyć tę samą
         *   blokadę wielokrotnie (np. rekurencja) bez zakleszczenia samego siebie
         * - Wybór: synchronized dla prostych przypadków (prostsza składnia,
         *   automatyczne zwolnienie), Lock/ReentrantLock gdy potrzebujesz
         *   tryLock(), timeoutów, przerywalności albo wielu Condition
         */
    }

    /**
     * Demonstracja reentrancy: każde wywołanie rekurencyjne ponownie
     * "wchodzi" w tę samą blokadę (ten sam wątek), co jest bezpieczne
     * właśnie dzięki temu, że ReentrantLock jest reentrant.
     */
    private static void recursiveLock(ReentrantLock lock, int depth) {
        lock.lock();
        try {
            System.out.println("Wejście na głębokość " + depth + ", holdCount=" + lock.getHoldCount());
            if (depth > 0) {
                recursiveLock(lock, depth - 1); // ten sam wątek zdobywa tę samą blokadę ponownie
            }
        } finally {
            lock.unlock();
        }
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
