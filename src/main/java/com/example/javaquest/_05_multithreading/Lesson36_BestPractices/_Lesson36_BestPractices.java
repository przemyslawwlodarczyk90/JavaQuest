package com.example.javaquest._05_multithreading.Lesson36_BestPractices;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _Lesson36_BestPractices {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 DOBRE PRAKTYKI WIELOWĄTKOWOŚCI - ZBIORCZE PODSUMOWANIE
         * ============================================================
         * Ta lekcja NIE wprowadza nowych mechanizmów - to krótkie
         * zestawienie zasad, które poznaliśmy w poprzednich 35 lekcjach.
         * Dla każdej zasady: CO robić, DLACZEGO, i w KTÓREJ lekcji
         * poznaliśmy szczegóły. Każda zasada ma malutki ilustracyjny
         * przykład kodu.
         */

        System.out.println("=== Zasada 1: nie twórz setek wątków ręcznie ===");
        zasada1_UzywajExecutorServiceLubVirtualThreads();

        System.out.println("\n=== Zasada 2: minimalizuj stan współdzielony ===");
        zasada2_KomunikujSiePrzezBlockingQueue();

        System.out.println("\n=== Zasada 3: preferuj obiekty niemutowalne ===");
        zasada3_PreferujImmutableObjects();

        System.out.println("\n=== Zasada 4: nie trzymaj locka dłużej niż trzeba ===");
        zasada4_KrotkaSekcjaKrytyczna();

        System.out.println("\n=== Zasada 5: zawsze unlock() w finally ===");
        zasada5_UnlockWFinally();

        System.out.println("\n=== Zasada 6: zawsze zamykaj ExecutorService ===");
        zasada6_ZawszeZamykajExecutor();

        System.out.println("\n=== KONIEC LEKCJI 36 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * 1. Nie twórz ręcznie setek/tysięcy wątków (`new Thread()` w pętli)
         *    -> ExecutorService (Lesson21_ExecutorService) dla ograniczonej
         *       puli wątków wielokrotnego użytku, albo Virtual Threads
         *       (Lesson33_VirtualThreads) dla MASOWO równoległych zadań
         *       I/O-bound (tysiące/miliony wątków to dla wirtualnych OK).
         * 2. Minimalizuj stan współdzielony między wątkami
         *    -> komunikacja przez BlockingQueue (Lesson24) zamiast
         *       dzielenia mutowalnego stanu - producent/konsument zamiast
         *       wspólnej zmiennej chronionej blokadami.
         * 3. Preferuj obiekty niemutowalne (immutable)
         *    -> (Lesson10_ThreadSafety) - obiekt bez możliwości zmiany
         *       stanu po utworzeniu jest automatycznie thread-safe,
         *       zero synchronizacji potrzebne.
         * 4. Nie trzymaj locka dłużej niż to konieczne
         *    -> (Lesson13_CriticalSection) - im krótsza sekcja krytyczna,
         *       tym mniejsza szansa na blokowanie innych wątków.
         * 5. Zawsze zwalniaj lock w finally
         *    -> (Lesson18_LockAndReentrantLock) - inaczej wyjątek w
         *       sekcji krytycznej zostawi lock zablokowany NA ZAWSZE.
         * 6. Zawsze zamykaj ExecutorService (shutdown + awaitTermination)
         *    -> niezamknięta pula z wątkami non-daemon nie pozwoli JVM
         *       się zakończyć (patrz też Lesson37_CommonMistakes).
         */
    }

    /**
     * Zasada 1: nie twórz ręcznie setek wątków (`new Thread()` w pętli).
     * DLACZEGO: każdy wątek systemowy to narzut pamięci/OS. Setki
     * ręcznie tworzonych wątków = ryzyko OutOfMemoryError i chaotyczne
     * przełączanie kontekstu. Szczegóły: Lesson21_ExecutorService
     * (pule wątków) oraz Lesson33_VirtualThreads (dla zadań I/O-bound,
     * gdzie liczba "wątków" liczona w tysiącach jest normalna).
     */
    private static void zasada1_UzywajExecutorServiceLubVirtualThreads() throws InterruptedException {
        // ❌ ŹLE (tylko ilustracja, NIE wykonujemy): tworzenie wątku per zadanie
        //   for (int i = 0; i < 1000; i++) { new Thread(() -> doSomething()).start(); }

        // ✅ DOBRZE: ograniczona pula wątków wielokrotnego użytku
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 4; i++) {
            int taskId = i;
            pool.submit(() -> System.out.println("[Pool] zadanie " + taskId
                    + " na " + Thread.currentThread().getName()));
        }
        shutdownAndWait(pool);

        // ✅ DOBRZE (dla masowo równoległych zadań I/O-bound): Virtual Threads
        try (ExecutorService virtualPool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 4; i++) {
                int taskId = i;
                virtualPool.submit(() -> System.out.println("[Virtual] zadanie " + taskId
                        + " na " + Thread.currentThread()));
            }
        } // try-with-resources zamyka pulę automatycznie (ExecutorService implementuje AutoCloseable od Javy 19)
    }

    /**
     * Zasada 2: minimalizuj stan współdzielony - zamiast dzielić
     * mutowalną zmienną chronioną blokadami, komunikuj się przez
     * BlockingQueue. DLACZEGO: brak współdzielonego stanu = brak
     * wyścigów (race condition) i brak potrzeby synchronizacji ręcznej.
     * Szczegóły: Lesson24_ConcurrentCollectionsAndBlockingQueue.
     */
    private static void zasada2_KomunikujSiePrzezBlockingQueue() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    queue.put(i); // blokuje, jeśli kolejka pełna
                    System.out.println("[Producent] wyprodukował: " + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producent");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    int value = queue.take(); // blokuje, jeśli kolejka pusta
                    System.out.println("[Konsument] odebrał: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Konsument");

        producer.start();
        consumer.start();
        producer.join(2000);
        consumer.join(2000);
        // Żaden wątek nie dotyka wspólnej zmiennej bezpośrednio - cała
        // komunikacja idzie przez bezpieczną, wbudowaną BlockingQueue.
    }

    /**
     * Zasada 3: preferuj obiekty niemutowalne (immutable). DLACZEGO:
     * obiekt, którego stanu NIE da się zmienić po konstrukcji, jest
     * z definicji bezpieczny dla wielu wątków - nie ma czego chronić
     * synchronizacją. Szczegóły: Lesson10_ThreadSafety.
     */
    private static void zasada3_PreferujImmutableObjects() {
        ImmutablePoint point = new ImmutablePoint(3, 4);
        // Dowolna liczba wątków może bezpiecznie CZYTAĆ 'point' równolegle
        // - bez locka, bez volatile, bez ryzyka - bo nikt nie może go zmienić.
        Runnable reader = () -> System.out.println(Thread.currentThread().getName()
                + " czyta punkt: (" + point.x() + ", " + point.y() + ")");

        Thread t1 = new Thread(reader, "Czytelnik-1");
        Thread t2 = new Thread(reader, "Czytelnik-2");
        t1.start();
        t2.start();
        joinQuietly(t1, 1000);
        joinQuietly(t2, 1000);
    }

    /** Prosty, niemutowalny punkt - tylko final pola, brak setterów. */
    private record ImmutablePoint(int x, int y) {
    }

    /**
     * Zasada 4: nie trzymaj locka dłużej niż to konieczne - wykonuj
     * kosztowne obliczenia POZA sekcją krytyczną, w środku zostaw tylko
     * to, co faktycznie musi być chronione. DLACZEGO: dłuższy czas
     * trzymania locka = dłuższe blokowanie innych wątków, mniejsza
     * przepustowość. Szczegóły: Lesson13_CriticalSection.
     */
    private static void zasada4_KrotkaSekcjaKrytyczna() {
        final Object lock = new Object();
        int[] sharedCounter = {0};

        Runnable badStyle = () -> {
            synchronized (lock) {
                // ❌ ŹLE: kosztowne obliczenie WEWNĄTRZ sekcji krytycznej
                // - blokuje inne wątki dłużej niż potrzeba
                long result = expensiveComputation();
                sharedCounter[0] += result > 0 ? 1 : 0;
            }
        };

        Runnable goodStyle = () -> {
            // ✅ DOBRZE: kosztowne obliczenie POZA sekcją krytyczną
            long result = expensiveComputation();
            synchronized (lock) {
                // sekcja krytyczna zawiera TYLKO niezbędną modyfikację
                sharedCounter[0] += result > 0 ? 1 : 0;
            }
        };

        goodStyle.run();
        badStyle.run();
        System.out.println("sharedCounter = " + sharedCounter[0] + " (styl 'goodStyle' minimalizuje czas trzymania locka)");
    }

    private static long expensiveComputation() {
        long sum = 0;
        for (int i = 0; i < 100_000; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * Zasada 5: zawsze zwalniaj Lock w bloku finally. DLACZEGO: jeśli
     * kod w sekcji krytycznej rzuci wyjątek, a unlock() nie jest w
     * finally, blokada NIGDY nie zostanie zwolniona - inne wątki
     * zablokują się na niej na zawsze. Szczegóły: Lesson18_LockAndReentrantLock.
     */
    private static void zasada5_UnlockWFinally() {
        Lock lock = new ReentrantLock();

        // ✅ DOBRZE: lock() poza try, unlock() w finally - ZAWSZE się wykona
        lock.lock();
        try {
            System.out.println("Praca w sekcji krytycznej pod ReentrantLock");
            // gdyby tu poleciał wyjątek, finally i tak zwolni lock
        } finally {
            lock.unlock();
        }
        System.out.println("Lock zwolniony poprawnie, isLocked=" + ((ReentrantLock) lock).isLocked());

        /*
         * ❌ ŹLE (tylko ilustracja, NIE wykonujemy):
         *   lock.lock();
         *   doSomethingThatMightThrow(); // <- jeśli rzuci wyjątek,
         *   lock.unlock();                //    ta linia NIGDY się nie wykona!
         */
    }

    /**
     * Zasada 6: zawsze zamykaj ExecutorService. DLACZEGO: wątki w puli
     * domyślnie NIE są demonami (non-daemon) - jeśli nie wywołamy
     * shutdown(), JVM nigdy się nie zakończy, bo czeka na te wątki.
     * Wzorzec: shutdown() + awaitTermination(timeout), a jeśli pula
     * nie zdąży - dopiero wtedy shutdownNow() jako "plan B".
     */
    private static void zasada6_ZawszeZamykajExecutor() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(() -> System.out.println("Zadanie wykonane w puli"));
        shutdownAndWait(pool); // <- bez tego JVM mógłby "wisieć" w nieskończoność
        System.out.println("Pula zamknięta poprawnie (isTerminated=" + pool.isTerminated() + ")");
    }

    private static void shutdownAndWait(ExecutorService pool) throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }

    private static void joinQuietly(Thread thread, long millis) {
        try {
            thread.join(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
