package com.example.javaquest._03_collections.Lesson09_PriorityQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class _Lesson09_PriorityQueue {

    public static void main(String[] args) {

        /*
         * ============================================================
         * ⚡ PRIORITYQUEUE – CZYM JEST?
         * ============================================================
         * PriorityQueue to kolejka priorytetowa oparta na kopcu (min-heap).
         *
         * Cechy PriorityQueue:
         * ✅ Element o NAJWYŻSZYM priorytecie zawsze na początku
         * ✅ Domyślnie: kolejność naturalna (min-heap – najmniejszy = pierwszy)
         * ✅ Można zmienić kolejność przez Comparator
         * ❌ NIE gwarantuje kolejności przy iteracji (tylko peek/poll)
         * ❌ Brak dostępu po indeksie
         *
         * Operacje O(log n): add/offer, remove/poll
         * Operacje O(1): peek, element (podgląd szczytu)
         *
         * Interfejs Queue:
         * offer(e) / add(e)  → dodaj element
         * poll()   / remove()→ usuń i zwróć najważniejszy
         * peek()   / element()→ podejrzyj najważniejszy
         */

        /*
         * ============================================================
         * 🔹 KOLEJKA DOMYŚLNA (MIN-HEAP – rosnąco)
         * ============================================================
         */

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(30);
        minHeap.offer(10);
        minHeap.offer(50);
        minHeap.offer(20);
        minHeap.offer(40);

        System.out.println("Min-heap peek (minimum): " + minHeap.peek()); // 10
        System.out.println("Pobieranie w kolejności rosnącej:");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println();

        /*
         * ============================================================
         * 🔹 COMPARATOR – SORTOWANIE MALEJĄCO (MAX-HEAP)
         * ============================================================
         */

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.offer(30); maxHeap.offer(10); maxHeap.offer(50);
        maxHeap.offer(20); maxHeap.offer(40);

        System.out.println("\nMax-heap peek (maximum): " + maxHeap.peek()); // 50
        System.out.println("Pobieranie w kolejności malejącej:");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println();

        /*
         * ============================================================
         * 🔹 COMPARATOR – PO DŁUGOŚCI ŁAŃCUCHA
         * ============================================================
         */

        PriorityQueue<String> byLength = new PriorityQueue<>(
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        byLength.offer("banana");
        byLength.offer("fig");
        byLength.offer("apple");
        byLength.offer("kiwi");
        byLength.offer("cherry");

        System.out.println("\nPo długości (krótsze pierwsze):");
        while (!byLength.isEmpty()) {
            System.out.print(byLength.poll() + " ");
        }
        System.out.println();

        /*
         * ============================================================
         * 🔹 KOLEJKA DLA OBIEKTÓW Z COMPARABLE
         * ============================================================
         * Klasa Task implementuje Comparable<Task> po priorytecie.
         * PriorityQueue używa compareTo() automatycznie.
         */

        PriorityQueue<Task> taskQueue = new PriorityQueue<>();
        taskQueue.offer(new Task("Deploy na prod", 1));
        taskQueue.offer(new Task("Napisz testy", 3));
        taskQueue.offer(new Task("Fix krytyczny bug", 1));
        taskQueue.offer(new Task("Code review", 2));
        taskQueue.offer(new Task("Dokumentacja", 5));

        System.out.println("\nZadania według priorytetu (1=najwyższy):");
        while (!taskQueue.isEmpty()) {
            System.out.println("  " + taskQueue.poll());
        }

        /*
         * ============================================================
         * 🔹 KOLEJKA DLA OBIEKTÓW Z COMPARATOREM (zewnętrznym)
         * ============================================================
         */

        PriorityQueue<Task> byPriorityDesc = new PriorityQueue<>(
                Comparator.comparingInt(Task::getPriority).reversed()
        );
        byPriorityDesc.offer(new Task("Deploy", 1));
        byPriorityDesc.offer(new Task("Testy", 3));
        byPriorityDesc.offer(new Task("Docs", 5));

        System.out.println("\nZadania malejąco (5=najwyższy):");
        while (!byPriorityDesc.isEmpty()) {
            System.out.println("  " + byPriorityDesc.poll());
        }

        /*
         * ============================================================
         * 🔹 POZOSTAŁE OPERACJE
         * ============================================================
         * size()      → liczba elementów
         * isEmpty()   → czy pusta
         * contains(e) → czy element istnieje O(n)
         * remove(e)   → usuwa konkretny element O(n)
         * toArray()   → konwersja (kolejność nieokreślona!)
         * clear()     → czyści kolejkę
         */

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(5); pq.offer(1); pq.offer(3);

        System.out.println("\nRozmiar: " + pq.size());
        System.out.println("Zawiera 3? " + pq.contains(3));
        System.out.println("Zawiera 7? " + pq.contains(7));
        pq.remove(3);
        System.out.println("Po remove(3): " + pq);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * PriorityQueue<T> implements Queue<T>:
         * offer/add(e)       → dodaj (O(log n))
         * poll/remove()      → usuń najważniejszy (O(log n))
         * peek/element()     → podejrzyj najważniejszy (O(1))
         * size(), isEmpty()  → rozmiar i pustość
         *
         * Domyślnie: min-heap (naturalny porządek, Comparable)
         * Custom: PriorityQueue<T>(Comparator<T>)
         *
         * ⚠️ Iteracja (for-each) NIE gwarantuje kolejności!
         *    Zawsze używaj poll() do pobierania w kolejności.
         */
    }
}

class Task implements Comparable<Task> {
    private String name;
    private int priority;

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    int getPriority() { return priority; }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "[P" + priority + "] " + name;
    }
}
