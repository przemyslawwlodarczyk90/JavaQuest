package com.example.javaquest._05_multithreading.Lesson33_VirtualThreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson33_VirtualThreads {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 WĄTKI WIRTUALNE (VIRTUAL THREADS, Project Loom, od Javy 21)
         * ============================================================
         * Do tej pory poznane wątki (new Thread(...)) to WĄTKI PLATFORMOWE
         * (platform threads) – każdy z nich jest cienką "nakładką" na
         * prawdziwy wątek systemu operacyjnego (OS thread). OS thready są
         * DROGIE: zajmują dużo pamięci (typowo ok. 1 MB stosu) i ich
         * tworzenie/przełączanie kontekstu jest kosztowne. Dlatego w
         * praktyce liczba platformowych wątków w aplikacji jest ograniczona
         * (pule po kilka/kilkadziesiąt wątków).
         *
         * WĄTKI WIRTUALNE (od Javy 21, JEP 444) to lekka implementacja
         * Thread zarządzana przez samą JVM, a nie system operacyjny:
         * - są BARDZO TANIE (kilkaset bajtów, nie megabajty) – można ich
         *   utworzyć MILIONY,
         * - JVM "podstawia" wiele wątków wirtualnych pod niewielką liczbę
         *   prawdziwych wątków platformowych (tzw. "carrier threads") –
         *   gdy wątek wirtualny blokuje się na operacji I/O (np. sleep,
         *   odczyt z sieci/pliku), JVM automatycznie "odczepia" go od
         *   wątku niosącego (carrier) i podstawia w jego miejsce inny,
         *   gotowy do pracy wątek wirtualny,
         * - idealne do zadań I/O-BOUND (dużo czekania: HTTP, baza danych,
         *   pliki) – NIE przyspieszają obliczeń CPU-bound (tam liczy się
         *   liczba rdzeni procesora, a nie liczba wątków).
         *
         * Sposoby tworzenia:
         * - Thread.ofVirtual().start(runnable)      -> tworzy i od razu startuje
         * - Thread.ofVirtual().unstarted(runnable)  -> tworzy, ale trzeba wywołać start()
         * - Executors.newVirtualThreadPerTaskExecutor() -> executor, który
         *   dla KAŻDEGO zadania tworzy nowy wątek wirtualny (zamiast puli
         *   stałych wątków jak newFixedThreadPool). Implementuje AutoCloseable
         *   -> używamy w try-with-resources.
         */

        System.out.println("=== 1) Thread.ofVirtual() – pojedynczy wątek wirtualny ===");
        demoSingleVirtualThread();

        System.out.println("\n=== 2) Executors.newVirtualThreadPerTaskExecutor() – 10 000 wątków wirtualnych ===");
        demoTenThousandVirtualThreads();

        /*
         * ============================================================
         * 🔍 DLACZEGO NIE 10 000 WĄTKÓW PLATFORMOWYCH?
         * ============================================================
         * (Poniższy kod NIE jest uruchamiany – to tylko wyjaśnienie w komentarzu,
         * żeby nie zawiesić/nie wysadzić pamięci tej maszyny).
         *
         *   for (int i = 0; i < 10_000; i++) {
         *       new Thread(() -> { ... }).start(); // ŹLE dla 10 000 wątków!
         *   }
         *
         * Każdy platformowy wątek to osobny wątek systemu operacyjnego:
         * - domyślny rozmiar stosu to ok. 512 KB - 1 MB -> 10 000 wątków to
         *   potencjalnie kilka-kilkanaście GB samej pamięci na stosy,
         * - system operacyjny ma ograniczony limit liczby wątków/procesów,
         *   przy dużej ich liczbie aplikacja może rzucić
         *   OutOfMemoryError: unable to create new native thread,
         * - przełączanie kontekstu (context switching) między tysiącami
         *   wątków OS jest kosztowne i degraduje wydajność całego systemu.
         * Wątki wirtualne rozwiązują dokładnie ten problem: JVM zarządza
         * nimi w pamięci "userland", a rzeczywistych wątków OS (carrier
         * threads) potrzeba tylko tyle, ile rdzeni procesora.
         */

        System.out.println("\n=== 3) Zastosowanie w backendzie ===");
        System.out.println(
                "Typowy backend (np. serwer HTTP) obsługujący tysiące jednoczesnych żądań\n" +
                "spędza większość czasu na CZEKANIU: na odpowiedź z bazy danych, na inne\n" +
                "mikroserwisy, na dysk. Model 'jeden wątek platformowy na jedno żądanie'\n" +
                "szybko wyczerpuje pulę wątków przy dużym ruchu. Wątki wirtualne pozwalają\n" +
                "utrzymać prosty styl programowania (1 wątek = 1 żądanie, kod czytany\n" +
                "'z góry na dół', bez reaktywnych callbacków) przy jednoczesnej obsłudze\n" +
                "SETEK TYSIĘCY równoległych żądań I/O-bound – to główny cel Project Loom.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Wątek platformowy = gruba nakładka na wątek systemu operacyjnego,
         *   drogi w tworzeniu i utrzymaniu (~1 MB stosu na wątek).
         * - Wątek wirtualny (Java 21+) = lekka konstrukcja zarządzana przez
         *   JVM, tania na tyle, że można utworzyć ich MILIONY.
         * - Thread.ofVirtual().start(runnable) / .unstarted(runnable) ->
         *   ręczne tworzenie pojedynczych wątków wirtualnych.
         * - Executors.newVirtualThreadPerTaskExecutor() -> executor tworzący
         *   NOWY wątek wirtualny dla każdego zadania; używać w
         *   try-with-resources (AutoCloseable).
         * - Wątki wirtualne = idealne dla zadań I/O-BOUND (dużo czekania).
         *   NIE pomagają przy zadaniach CPU-bound (tam liczy się liczba
         *   rdzeni procesora, użyj ForkJoinPool/parallelStream).
         * - Główne zastosowanie: backendy obsługujące bardzo dużą liczbę
         *   równoległych żądań (serwery HTTP, mikroserwisy) bez utraty
         *   prostoty kodu i bez wyczerpywania puli wątków systemowych.
         */
    }

    private static void demoSingleVirtualThread() throws InterruptedException {
        Thread virtualThread = Thread.ofVirtual()
                .name("virtual-demo")
                .unstarted(() -> {
                    System.out.println("[" + Thread.currentThread() + "] isVirtual=" + Thread.currentThread().isVirtual());
                });
        virtualThread.start();
        virtualThread.join(2000); // zawsze z timeoutem, dla bezpieczeństwa
    }

    /**
     * Tworzy 10 000 wątków wirtualnych, z których każdy symuluje krótką
     * operację I/O (Thread.sleep) i mierzy całkowity czas wykonania. Dzięki
     * try-with-resources executor jest automatycznie zamykany (close()
     * czeka aż wszystkie zadania się zakończą) – bez ryzyka zawieszenia JVM.
     */
    private static void demoTenThousandVirtualThreads() {
        int taskCount = 10_000;
        AtomicInteger completed = new AtomicInteger(0);

        long start = System.nanoTime();
        try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < taskCount; i++) {
                virtualExecutor.submit(() -> {
                    try {
                        Thread.sleep(50); // symulacja operacji I/O (np. zapytanie do sieci/DB)
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    completed.incrementAndGet();
                });
            }
            // Koniec bloku try-with-resources -> executor.close() czeka aż
            // wszystkie 10 000 zadań się zakończy (blokująco, ale z natury
            // krótko, bo wszystkie śpią RÓWNOLEGLE, nie sekwencyjnie).
        }
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        System.out.println("Uruchomiono i zakończono " + completed.get() + " z " + taskCount
                + " wątków wirtualnych (każdy spał 50 ms) w " + elapsedMs + " ms.");
        System.out.println("Gdyby każde z tych 'oczekiwań' wykonywało się SEKWENCYJNIE na 1 wątku,\n"
                + "zajęłoby to ok. " + (taskCount * 50L) + " ms – wątki wirtualne pozwoliły\n"
                + "wykonać to WSPÓŁBIEŻNIE przy minimalnym zużyciu zasobów.");
    }
}
