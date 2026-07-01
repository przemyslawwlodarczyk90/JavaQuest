package com.example.javaquest._03_collections.Lesson04_LinkedList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class _Lesson04_LinkedList {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔗 LINKEDLIST – CZYM JEST?
         * ============================================================
         * LinkedList to dwukierunkowa lista powiązana (doubly linked list).
         *
         * Implementuje jednocześnie:
         * - List<E>   → dostęp przez indeks
         * - Deque<E>  → kolejka dwukierunkowa (Deque = Double-Ended Queue)
         * - Queue<E>  → kolejka FIFO
         *
         * Różnica vs ArrayList:
         * ┌─────────────────┬───────────┬───────────┐
         * │ Operacja        │ ArrayList │ LinkedList│
         * ├─────────────────┼───────────┼───────────┤
         * │ get(i)          │  O(1) ✅  │  O(n) ❌  │
         * │ add (na końcu)  │  O(1)     │  O(1)     │
         * │ add (na środku) │  O(n)     │  O(1) ✅  │
         * │ remove (środek) │  O(n)     │  O(1) ✅  │
         * │ Pamięć          │  mniej    │  więcej   │
         * └─────────────────┴───────────┴───────────┘
         *
         * Kiedy LinkedList?
         * - częste wstawianie/usuwanie na początku lub środku
         * - implementacja stosu / kolejki
         */

        /*
         * ============================================================
         * 🔹 PODSTAWOWE OPERACJE (jak List)
         * ============================================================
         */

        LinkedList<String> list = new LinkedList<>();
        list.add("Banan");
        list.add("Gruszka");
        list.add("Mango");
        list.add(1, "Kiwi");

        System.out.println("Lista: " + list);
        System.out.println("Element [1]: " + list.get(1));
        System.out.println("Rozmiar: " + list.size());

        /*
         * ============================================================
         * 🔹 OPERACJE DEQUE – PRACA Z POCZĄTKIEM I KOŃCEM
         * ============================================================
         * addFirst(e) / offerFirst(e)  → dodaj na początku
         * addLast(e)  / offerLast(e)   → dodaj na końcu
         * getFirst()  / peekFirst()     → podejrzyj pierwszy (bez usuwania)
         * getLast()   / peekLast()      → podejrzyj ostatni
         * removeFirst()/ pollFirst()    → usuń i zwróć pierwszy
         * removeLast() / pollLast()     → usuń i zwróć ostatni
         *
         * get/remove rzucają NoSuchElementException gdy pusta
         * peek/poll  zwracają null gdy pusta
         */

        LinkedList<String> deque = new LinkedList<>();
        deque.addFirst("Środek");
        deque.addFirst("Pierwszy");   // → [Pierwszy, Środek]
        deque.addLast("Ostatni");     // → [Pierwszy, Środek, Ostatni]

        System.out.println("\nDeque: " + deque);
        System.out.println("Pierwszy: " + deque.getFirst());
        System.out.println("Ostatni: "  + deque.getLast());

        String removed = deque.removeFirst();
        System.out.println("Usunięty pierwszy: " + removed + ", deque: " + deque);

        /*
         * ============================================================
         * 🔹 JAKO STOS (Stack – LIFO)
         * ============================================================
         * push(e)  = addFirst(e)  → na szczyt
         * pop()    = removeFirst() → ze szczytu (NoSuchElementException gdy pusta)
         * peek()   = getFirst()    → podejrzyj szczyt
         */

        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("\nStos: " + stack);
        System.out.println("Pop: " + stack.pop());
        System.out.println("Peek: " + stack.peek());
        System.out.println("Stos po operacjach: " + stack);

        /*
         * ============================================================
         * 🔹 JAKO KOLEJKA (Queue – FIFO)
         * ============================================================
         * offer(e)   = addLast(e)   → do kolejki
         * poll()     = removeFirst() → z kolejki (null gdy pusta)
         * peek()     = getFirst()    → podejrzyj (null gdy pusta)
         */

        LinkedList<String> queue = new LinkedList<>();
        queue.offer("Pierwszy w kolejce");
        queue.offer("Drugi w kolejce");
        queue.offer("Trzeci w kolejce");

        System.out.println("\nKolejka: " + queue);
        System.out.println("Poll: " + queue.poll());  // usuwa i zwraca pierwszy
        System.out.println("Peek: " + queue.peek());  // tylko podgląd
        System.out.println("Kolejka: " + queue);

        /*
         * ============================================================
         * 🔹 MODYFIKACJA PODCZAS ITERACJI (ListIterator)
         * ============================================================
         * Iterator jest BEZPIECZNY dla modyfikacji przez swoje metody.
         * ListIterator pozwala add() i set() podczas iteracji.
         */

        LinkedList<Integer> nums = new LinkedList<>();
        for (int i = 1; i <= 5; i++) nums.add(i);

        System.out.println("\nPrzed modyfikacją: " + nums);

        ListIterator<Integer> lit = nums.listIterator();
        while (lit.hasNext()) {
            int n = lit.next();
            if (n % 2 == 0) {
                lit.set(n * 10);    // zastąp parzyste przez n*10
            } else {
                lit.add(0);         // wstaw 0 po nieparzystych
            }
        }
        System.out.println("Po modyfikacji: " + nums);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * LinkedList implementuje List + Deque + Queue
         *
         * List:   get/set/add/remove po indeksie
         * Deque:  addFirst/addLast/getFirst/getLast/removeFirst/removeLast
         * Stack:  push/pop/peek
         * Queue:  offer/poll/peek
         *
         * Wybieraj ArrayList gdy:
         * - często czytasz po indeksie
         * - dużo operacji na końcu
         *
         * Wybieraj LinkedList gdy:
         * - implementujesz stos lub kolejkę
         * - często wstawiasz/usuwasz na początku/środku
         */
    }
}
