package com.example.javaquest._03_collections.Lesson22_Queue;

import java.util.*;
import java.util.concurrent.*;

public class _Lesson22_Queue {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 📬 INTERFEJS QUEUE – KOLEJKA FIFO
         * ============================================================
         * Queue (java.util.Queue<E>) = interfejs reprezentujący kolejkę.
         * FIFO = First In, First Out (pierwszy wchodzi, pierwszy wychodzi)
         *
         * Hierarchia:
         * Iterable → Collection → Queue → Deque
         *                       → BlockingQueue → ArrayBlockingQueue
         *                                       → LinkedBlockingQueue
         *                       → PriorityQueue (nie jest FIFO!)
         *
         * Metody Queue:
         * ┌─────────────────┬──────────────────────────────────┐
         * │ Operacja        │ Rzuca wyjątek │ Zwraca null/false│
         * ├─────────────────┼───────────────┼──────────────────┤
         * │ Wstaw           │ add(e)        │ offer(e)         │
         * │ Usuń i pobierz  │ remove()      │ poll()           │
         * │ Tylko podejrzyj │ element()     │ peek()           │
         * └─────────────────┴───────────────┴──────────────────┘
         *
         * Implementacje Queue:
         * - LinkedList         → FIFO, ogólna (też List)
         * - ArrayDeque         → FIFO, szybsza (też Deque)
         * - PriorityQueue      → heapowa, nie FIFO (po priorytecie)
         * - LinkedBlockingQueue → FIFO + blokowanie wątków
         * - ArrayBlockingQueue → ograniczona + blokowanie
         */

        /*
         * ============================================================
         * 🔹 PODSTAWY QUEUE – FIFO
         * ============================================================
         */

        System.out.println("=== Queue (FIFO) ===");

        // Typowo używamy ArrayDeque zamiast LinkedList dla Queue
        Queue<String> queue = new ArrayDeque<>();
        queue.offer("Pierwszy");    // dodaj na koniec
        queue.offer("Drugi");
        queue.offer("Trzeci");

        System.out.println("Queue: " + queue);
        System.out.println("Peek (bez usuwania): " + queue.peek()); // Pierwszy
        System.out.println("Poll (usuwa i zwraca): " + queue.poll()); // Pierwszy
        System.out.println("Po poll: " + queue);
        System.out.println("Rozmiar: " + queue.size());

        // add vs offer, remove vs poll, element vs peek
        Queue<Integer> q = new ArrayDeque<>();
        q.add(1); q.add(2); q.add(3);    // jak offer, ale rzuca jeśli ograniczona i pełna

        System.out.println("\nadd/remove/element:");
        System.out.println("element(): " + q.element()); // 1, rzuca NoSuchElementException gdy pusta
        System.out.println("remove(): " + q.remove());   // 1, usuwa, rzuca gdy pusta
        System.out.println("Queue: " + q);

        // peek/poll zwracają null gdy pusta:
        Queue<String> empty = new ArrayDeque<>();
        System.out.println("peek() na pustej: " + empty.peek()); // null
        System.out.println("poll() na pustej: " + empty.poll()); // null

        /*
         * ============================================================
         * 🔹 QUEUE DO SYMULACJI KOLEJKI (ŻYWOTNY PRZYKŁAD)
         * ============================================================
         */

        System.out.println("\n=== Kolejka klientów ===");
        Queue<String> clients = new LinkedList<>();
        clients.offer("Klient 1");
        clients.offer("Klient 2");
        clients.offer("Klient 3");

        System.out.println("W kolejce: " + clients.size() + " klientów");
        while (!clients.isEmpty()) {
            String next = clients.poll();
            System.out.println("Obsługuję: " + next);
        }
        System.out.println("Kolejka pusta: " + clients.isEmpty());

        /*
         * ============================================================
         * 🔹 QUEUE BFS – PRZESZUKIWANIE W SZERZ
         * ============================================================
         * BFS (Breadth-First Search) klasycznie używa Queue!
         */

        System.out.println("\n=== BFS z Queue ===");

        // Symulacja grafu jako mapa sąsiedztwa
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, List.of(2, 3));
        graph.put(2, List.of(4, 5));
        graph.put(3, List.of(6));
        graph.put(4, Collections.emptyList());
        graph.put(5, Collections.emptyList());
        graph.put(6, Collections.emptyList());

        Queue<Integer> bfsQueue = new ArrayDeque<>();
        Set<Integer> visited = new LinkedHashSet<>();

        bfsQueue.offer(1); // start
        while (!bfsQueue.isEmpty()) {
            int node = bfsQueue.poll();
            if (!visited.contains(node)) {
                visited.add(node);
                System.out.print(node + " ");
                for (int neighbor : graph.get(node)) {
                    if (!visited.contains(neighbor)) bfsQueue.offer(neighbor);
                }
            }
        }
        System.out.println();
        System.out.println("Odwiedzone (BFS): " + visited);

        /*
         * ============================================================
         * 🔹 LINKEDBLOCKINGQUEUE – QUEUE Z BLOKOWANIEM
         * ============================================================
         * Wzorzec producer/consumer z ograniczoną kolejką.
         * put()  → blokuje gdy pełna
         * take() → blokuje gdy pusta
         * Idealny dla wzorca producer–consumer.
         */

        System.out.println("\n=== LinkedBlockingQueue ===");

        LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>(3);
        System.out.println("Pojemność: " + lbq.remainingCapacity());

        lbq.offer("a"); lbq.offer("b"); lbq.offer("c");
        System.out.println("Pełna? " + (lbq.remainingCapacity() == 0));
        boolean added = lbq.offer("d"); // false – nie czeka
        System.out.println("offer na pełnej: " + added);
        System.out.println("Queue: " + lbq);
        System.out.println("Poll: " + lbq.poll());
        System.out.println("Pojemność po poll: " + lbq.remainingCapacity());

        /*
         * ============================================================
         * 🔹 PORÓWNANIE IMPLEMENTACJI QUEUE
         * ============================================================
         */

        System.out.println("\n=== Porównanie ===");
        // ArrayDeque – szybka, brak pojemności, null dozwolony w List ale nie Queue API
        Queue<String> ad = new ArrayDeque<>();
        System.out.println("ArrayDeque: offer/poll/peek – najszybsza, nieograniczona");

        // LinkedList – wolniejsza, null dozwolony
        Queue<String> ll = new LinkedList<>();
        System.out.println("LinkedList: też List – dostęp po indeksie, null OK");

        // PriorityQueue – nie FIFO!
        Queue<Integer> pq = new PriorityQueue<>();
        pq.offer(30); pq.offer(10); pq.offer(20);
        System.out.print("PriorityQueue (min first): ");
        while (!pq.isEmpty()) System.out.print(pq.poll() + " ");
        System.out.println();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * Queue<T>:
         * offer(e) / add(e)   → wstaw
         * poll()   / remove() → usuń i pobierz pierwszy
         * peek()   / element()→ podejrzyj pierwszy (bez usuwania)
         *
         * Implementacje:
         * ArrayDeque           → szybka, FIFO, zalecana
         * LinkedList           → FIFO + List (dostęp indeksy)
         * PriorityQueue        → nie FIFO, po priorytecie
         * LinkedBlockingQueue  → ograniczona, blokująca (producer/consumer)
         * ArrayBlockingQueue   → j.w. ale tablica (bounded)
         *
         * BFS = Queue, DFS = Stack!
         */
    }
}
