package com.example.javaquest._03_collections.Lesson20_ConcurrentCollections;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson20_ConcurrentCollections {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 🔒 CONCURRENT COLLECTIONS – WIELOWĄTKOWE KOLEKCJE
         * ============================================================
         * java.util.concurrent – pakiet z kolekcjami bezpiecznymi
         * dla wielu wątków (thread-safe).
         *
         * PROBLEM ze zwykłymi kolekcjami:
         * ArrayList, HashMap → NIE są thread-safe!
         * Jednoczesny odczyt/zapis z wielu wątków → ConcurrentModificationException
         * lub uszkodzone dane.
         *
         * ROZWIĄZANIA:
         * 1. Collections.synchronizedXxx()  → wrapper sync (blokuje cały obiekt)
         * 2. java.util.concurrent.*         → zoptymalizowane concurrent kolekcje
         *
         * Najważniejsze klasy concurrent:
         * - ConcurrentHashMap         → thread-safe HashMap (bez full lock)
         * - CopyOnWriteArrayList      → thread-safe ArrayList (kopiuje przy zapisie)
         * - CopyOnWriteArraySet       → thread-safe HashSet
         * - BlockingQueue (interfejs) → kolejka blokująca wątki
         *   └── ArrayBlockingQueue    → ograniczona pojemność
         *   └── LinkedBlockingQueue  → opcjonalna pojemność
         *   └── PriorityBlockingQueue→ priorytetowa thread-safe
         * - ConcurrentLinkedQueue     → nieblokująca thread-safe Queue
         * - ConcurrentSkipListMap     → thread-safe TreeMap
         * - ConcurrentSkipListSet     → thread-safe TreeSet
         */

        /*
         * ============================================================
         * 🔹 ConcurrentHashMap
         * ============================================================
         * - Segmentowany (segment locking) – nie blokuje całej mapy
         * - Wiele wątków może czytać i pisać równolegle
         * - computeIfAbsent, merge, getOrDefault – atomowe operacje
         * - Nie pozwala null kluczy ani wartości!
         */

        System.out.println("=== ConcurrentHashMap ===");

        ConcurrentHashMap<String, AtomicInteger> wordCount = new ConcurrentHashMap<>();

        String[] words = {"java", "python", "java", "java", "c++", "python", "java"};
        for (String word : words) {
            wordCount.computeIfAbsent(word, k -> new AtomicInteger(0)).incrementAndGet();
        }
        System.out.println("Zliczanie słów: " + wordCount);

        // Wielowątkowe zliczanie
        ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
        counter.merge("hits", 1, Integer::sum);
        counter.merge("hits", 1, Integer::sum);
        counter.merge("misses", 1, Integer::sum);
        System.out.println("Counter: " + counter);

        // putIfAbsent – atomowe
        counter.putIfAbsent("errors", 0);
        System.out.println("PutIfAbsent: " + counter);

        /*
         * ============================================================
         * 🔹 CopyOnWriteArrayList
         * ============================================================
         * - Przy każdym add/set/remove → tworzy KOPIĘ tablicy
         * - Iteratory są MIGAWKĄ (snapshot) – nie rzucą ConcurrentModificationException
         * - Bezpieczne dla wielu czytelników i rzadkich zapisów
         * - WOLNE dla częstych zapisów (O(n) za każdy zapis)
         */

        System.out.println("\n=== CopyOnWriteArrayList ===");

        CopyOnWriteArrayList<String> cowal = new CopyOnWriteArrayList<>();
        cowal.add("Java");
        cowal.add("Python");
        cowal.add("Kotlin");

        // Iteracja jest bezpieczna nawet gdy inny wątek dodaje
        for (String s : cowal) {
            System.out.print(s + " ");
            cowal.add("C++"); // nie rzuci ConcurrentModificationException!
            break; // dla demo – dodajemy raz
        }
        System.out.println();
        System.out.println("COWAL rozmiar: " + cowal.size());

        /*
         * ============================================================
         * 🔹 ArrayBlockingQueue – producent/konsument
         * ============================================================
         * BlockingQueue: blokuje wątek gdy:
         * - put() → kolejka pełna (czeka aż się zwolni miejsce)
         * - take() → kolejka pusta (czeka aż pojawi się element)
         *
         * Metody:
         * put(e)     → wstawia, BLOKUJE jeśli pełna
         * take()     → pobiera, BLOKUJE jeśli pusta
         * offer(e)   → wstawia (false jeśli pełna, nie blokuje)
         * poll()     → pobiera (null jeśli pusta, nie blokuje)
         * offer(e,t,u)→ wstawia z timeoutem
         */

        System.out.println("\n=== ArrayBlockingQueue (Producer/Consumer) ===");

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            String[] tasks = {"Task1", "Task2", "Task3", "Task4", "Task5"};
            for (String task : tasks) {
                try {
                    queue.put(task);
                    System.out.println("[Producer] Dodano: " + task);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    String task = queue.take();
                    System.out.println("[Consumer] Pobrano: " + task);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        /*
         * ============================================================
         * 🔹 PriorityBlockingQueue
         * ============================================================
         * Thread-safe PriorityQueue. Nie ma górnej granicy pojemności.
         * take() blokuje gdy pusta.
         */

        System.out.println("\n=== PriorityBlockingQueue ===");

        PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
        pbq.offer(30); pbq.offer(10); pbq.offer(20);
        System.out.print("PriorityBlockingQueue (min-first): ");
        while (!pbq.isEmpty()) System.out.print(pbq.poll() + " ");
        System.out.println();

        /*
         * ============================================================
         * 🔹 ConcurrentLinkedQueue – nieblokująca kolejka FIFO
         * ============================================================
         * Używa CAS (Compare-And-Swap) bez mutexów – szybka i thread-safe.
         * Brak blokowania – poll() zwraca null gdy pusta.
         */

        System.out.println("\n=== ConcurrentLinkedQueue ===");

        ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<>();
        clq.offer("A"); clq.offer("B"); clq.offer("C");
        System.out.println("CLQ: " + clq);
        System.out.println("Poll: " + clq.poll()); // A
        System.out.println("Peek: " + clq.peek()); // B

        /*
         * ============================================================
         * 🔹 Collections.synchronizedXxx() – stary sposób
         * ============================================================
         * Opakowuje kolekcję w synchronized wrapper.
         * Przy iteracji MUSISZ ręcznie synchronizować!
         */

        System.out.println("\n=== synchronizedList ===");
        java.util.List<String> syncList = java.util.Collections.synchronizedList(
                new java.util.ArrayList<>()
        );
        syncList.add("A"); syncList.add("B"); syncList.add("C");

        // Iteracja wymaga ręcznej synchronizacji!
        synchronized (syncList) {
            for (String s : syncList) System.out.print(s + " ");
        }
        System.out.println();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * ConcurrentHashMap      → thread-safe Map, wysoka wydajność
         * CopyOnWriteArrayList   → thread-safe List, bezpieczna iteracja
         * ArrayBlockingQueue     → bounded blocking queue, producer/consumer
         * PriorityBlockingQueue  → thread-safe PriorityQueue
         * ConcurrentLinkedQueue  → nieblokująca FIFO queue
         * ConcurrentSkipListMap  → thread-safe TreeMap
         * ConcurrentSkipListSet  → thread-safe TreeSet
         *
         * synchronizedXxx() → stary styl, konieczna ręczna sync przy iteracji
         * Preferuj: java.util.concurrent.* (nowoczesne, lepsze)
         */
    }
}
