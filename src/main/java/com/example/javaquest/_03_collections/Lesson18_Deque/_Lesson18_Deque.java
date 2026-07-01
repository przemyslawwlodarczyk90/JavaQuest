package com.example.javaquest._03_collections.Lesson18_Deque;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class _Lesson18_Deque {

    public static void main(String[] args) {

        /*
         * ============================================================
         * 🔁 INTERFEJS DEQUE – DOUBLE ENDED QUEUE
         * ============================================================
         * Deque (Double-Ended Queue, czyt. "deck") = kolejka dwukierunkowa.
         * Można dodawać i usuwać elementy z OBU końców.
         *
         * Hierarchia:
         * Iterable → Collection → Queue → Deque
         *
         * Implementacje Deque:
         * - ArrayDeque  → oparta na tablicy (ZALECANA w większości przypadków)
         * - LinkedList  → oparta na liście powiązanej
         *
         * ArrayDeque vs LinkedList:
         * ✅ ArrayDeque szybsza (brak overhead węzłów)
         * ✅ ArrayDeque mniej pamięci
         * ❌ LinkedList ma dostęp przez indeks (List)
         *
         * Deque = Stos (Stack) + Kolejka (Queue) w jednym!
         *
         * Interfejs Queue (jedna strona):
         * offer/add → add to tail, poll/remove → remove from head, peek/element → peek head
         *
         * Interfejs Deque (obie strony):
         * ┌────────────┬───────────────┬───────────────┐
         * │ Operacja   │ Głowa (head)  │ Ogon (tail)   │
         * ├────────────┼───────────────┼───────────────┤
         * │ Wstaw      │ addFirst()    │ addLast()     │
         * │            │ offerFirst()  │ offerLast()   │
         * │ Usuń       │ removeFirst() │ removeLast()  │
         * │            │ pollFirst()   │ pollLast()    │
         * │ Podejrzyj  │ getFirst()    │ getLast()     │
         * │            │ peekFirst()   │ peekLast()    │
         * └────────────┴───────────────┴───────────────┘
         *
         * add/get/remove → rzuca wyjątek gdy pusta
         * offer/poll/peek → zwraca null gdy pusta
         */

        /*
         * ============================================================
         * 🔹 ARRAYDEQUE – PODSTAWOWE OPERACJE
         * ============================================================
         */

        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("Środek");
        deque.addFirst("Głowa");     // [Głowa, Środek]
        deque.addLast("Ogon");       // [Głowa, Środek, Ogon]
        deque.offerFirst("Bardzo pierwszy"); // [Bardzo pierwszy, Głowa, Środek, Ogon]

        System.out.println("Deque: " + deque);
        System.out.println("Pierwszy: " + deque.getFirst());
        System.out.println("Ostatni: " + deque.getLast());
        System.out.println("Rozmiar: " + deque.size());

        String first = deque.pollFirst();
        String last = deque.pollLast();
        System.out.println("Poll first: " + first + ", poll last: " + last);
        System.out.println("Po pollu: " + deque);

        /*
         * ============================================================
         * 🔹 JAKO STOS (LIFO – Last In, First Out)
         * ============================================================
         * push(e) = addFirst(e)  → na szczyt (głowa)
         * pop()   = removeFirst()→ ze szczytu
         * peek()  = peekFirst()  → podgląd szczytu
         *
         * ArrayDeque SZYBSZY od Stack (który jest synchronized)!
         */

        System.out.println("\n=== JAKO STOS ===");
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Stos: " + stack); // [30, 20, 10]
        System.out.println("Peek: " + stack.peek()); // 30
        System.out.println("Pop: " + stack.pop());   // 30
        System.out.println("Pop: " + stack.pop());   // 20
        System.out.println("Stos po: " + stack);

        /*
         * ============================================================
         * 🔹 JAKO KOLEJKA (FIFO – First In, First Out)
         * ============================================================
         * offer(e) = addLast(e)   → na koniec
         * poll()   = removeFirst()→ z początku
         * peek()   = peekFirst()  → podgląd początku
         */

        System.out.println("\n=== JAKO KOLEJKA ===");
        Queue<String> queue = new ArrayDeque<>();
        queue.offer("Pierwszy");
        queue.offer("Drugi");
        queue.offer("Trzeci");

        System.out.println("Kolejka: " + queue);
        System.out.println("Poll: " + queue.poll()); // Pierwszy
        System.out.println("Poll: " + queue.poll()); // Drugi
        System.out.println("Kolejka po: " + queue);

        /*
         * ============================================================
         * 🔹 PRAKTYCZNE PRZYKŁADY
         * ============================================================
         */

        // Sprawdzanie palindromu
        System.out.println("\n=== Palindrom ===");
        String word = "racecar";
        Deque<Character> chars = new ArrayDeque<>();
        for (char c : word.toCharArray()) chars.addLast(c);

        boolean isPalindrome = true;
        while (chars.size() > 1) {
            if (!chars.pollFirst().equals(chars.pollLast())) {
                isPalindrome = false;
                break;
            }
        }
        System.out.println(word + " palindrom? " + isPalindrome);

        // Historia nawigacji (Back/Forward)
        System.out.println("\n=== Historia nawigacji ===");
        ArrayDeque<String> history = new ArrayDeque<>();
        history.push("home.html");
        history.push("about.html");
        history.push("contact.html");
        System.out.println("Obecna strona: " + history.peek());
        System.out.println("Wróć: " + history.pop()); // contact
        System.out.println("Wróć: " + history.pop()); // about
        System.out.println("Teraz: " + history.peek()); // home

        // Sliding window maksimum
        System.out.println("\n=== Sliding window ===");
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3; // rozmiar okna
        System.out.print("Maksima okna " + k + ": ");
        Deque<Integer> window = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            while (!window.isEmpty() && window.peekFirst() < i - k + 1)
                window.pollFirst();
            while (!window.isEmpty() && nums[window.peekLast()] < nums[i])
                window.pollLast();
            window.offerLast(i);
            if (i >= k - 1)
                System.out.print(nums[window.peekFirst()] + " ");
        }
        System.out.println();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * ArrayDeque<T> implements Deque<T>:
         * - O(1) amortyzowane dla wszystkich operacji głowy i ogona
         * - Domyślna implementacja Deque (szybsza od LinkedList)
         *
         * Jako STOS:  push/pop/peek  (LIFO)
         * Jako KOLEJKA: offer/poll/peek  (FIFO)
         * Jako DEQUE: addFirst/addLast/pollFirst/pollLast (oba końce)
         *
         * Użyj ArrayDeque zamiast Stack<T> – Stack jest synchronized (wolny)!
         */
    }
}
