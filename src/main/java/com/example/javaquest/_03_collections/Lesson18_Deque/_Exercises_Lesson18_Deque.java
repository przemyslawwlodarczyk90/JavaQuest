package com.example.javaquest._03_collections.Lesson18_Deque;

public class _Exercises_Lesson18_Deque {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicDeque {
        /*
         * 🧪 Zadanie 1:
         * ArrayDeque<String>: dodaj "B" przez addFirst, "C" przez addLast,
         * potem "A" przez addFirst i "D" przez addLast.
         * Wypisz zawartość deque (powinno być [A, B, C, D]).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FirstLast {
        /*
         * 🧪 Zadanie 2:
         * ArrayDeque<Integer>: {10,20,30,40,50}.
         * Wypisz getFirst() i getLast().
         * Wypisz peekFirst() i peekLast() (bez usuwania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PollBothEnds {
        /*
         * 🧪 Zadanie 3:
         * ArrayDeque<String>: {"a","b","c","d","e"}.
         * Wywołaj pollFirst() i pollLast() na przemian, wypisując wynik
         * i stan deque po każdym wywołaniu, aż deque będzie puste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_EmptyDequeException {
        /*
         * 🧪 Zadanie 4:
         * Puste ArrayDeque<Integer>.
         * Wywołaj removeFirst() – złap wyjątek (NoSuchElementException) i wypisz komunikat.
         * Wywołaj pollFirst() na pustym – sprawdź że zwraca null (bez wyjątku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AsStack {
        /*
         * 🧪 Zadanie 5:
         * Deque<Integer> jako stos: push(1), push(2), push(3).
         * Wypisz stos, wykonaj peek() i pop() dwa razy, wypisz stan po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AsQueue {
        /*
         * 🧪 Zadanie 6:
         * Queue<String> zaimplementowane przez ArrayDeque: offer("Ala"), offer("Bob"), offer("Ola").
         * Wypisz kolejkę, wykonaj poll() dwa razy, wypisz stan po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SizeIsEmpty {
        /*
         * 🧪 Zadanie 7:
         * ArrayDeque<Integer>: dodaj 5 elementów (addLast).
         * Wypisz size() i isEmpty().
         * Usuń wszystkie elementy przez pollFirst() w pętli, sprawdzaj isEmpty() na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ContainsElement {
        /*
         * 🧪 Zadanie 8:
         * ArrayDeque<String>: {"kot","pies","chomik","papuga"}.
         * Sprawdź contains("pies") i contains("ryba").
         * Usuń "chomik" metodą remove(Object) i wypisz wynik oraz stan deque.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IterateDeque {
        /*
         * 🧪 Zadanie 9:
         * ArrayDeque<Integer>: {1,2,3,4,5}.
         * Iteruj for-each (od głowy do ogona).
         * Iteruj przez descendingIterator() (od ogona do głowy) i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LinkedListVsArrayDeque {
        /*
         * 🧪 Zadanie 10:
         * Stwórz Deque<Integer> raz jako new ArrayDeque<>() i raz jako new LinkedList<>().
         * Dodaj te same elementy do obu (addFirst/addLast) i wypisz zawartość obu –
         * pokaż że zachowują się identycznie mimo różnej implementacji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_PalindromeCheck {
        /*
         * 🧪 Zadanie 11:
         * Napisz isPalindrome(String s) używając Deque<Character>:
         * addLast dla każdego znaku, potem porównuj pollFirst() z pollLast()
         * aż zostanie 0 lub 1 element. Przetestuj "kajak" i "hello".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_UndoRedoHistory {
        /*
         * 🧪 Zadanie 12:
         * Symuluj historię przeglądarki jako stos (Deque): push kolejnych stron
         * "home", "products", "cart", "checkout".
         * Zaimplementuj back() (pop + wypisz nową bieżącą stronę przez peek).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ReverseWithDeque {
        /*
         * 🧪 Zadanie 13:
         * Odwróć kolejność elementów listy {1,2,3,4,5} używając Deque
         * (wrzuć wszystko przez push, potem zdejmuj przez pop do nowej listy).
         * Wypisz wynik: {5,4,3,2,1}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_BalancedBrackets {
        /*
         * 🧪 Zadanie 14:
         * Sprawdź poprawność nawiasów w Stringu używając Deque jako stosu.
         * Przetestuj: "([{}])" (poprawny), "([)]" (niepoprawny), "(()" (niepoprawny).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MaxSlidingWindow {
        /*
         * 🧪 Zadanie 15:
         * int[] nums = {1,3,-1,-3,5,3,6,7}, k=3.
         * Znajdź maksimum w każdym oknie o rozmiarze k używając Deque<Integer>
         * (przechowuj indeksy, monotoniczna dekrementująca kolejka).
         * Oczekiwany wynik: [3,3,5,5,6,7].
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MinSlidingWindow {
        /*
         * 🧪 Zadanie 16:
         * Analogicznie do zadania 15, ale znajdź minimum w każdym oknie
         * (monotoniczna rosnąca kolejka). Dla {2,1,3,4,0,5}, k=3
         * oczekiwany wynik: [1,1,0,0].
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FirstNegativeInWindow {
        /*
         * 🧪 Zadanie 17:
         * int[] nums = {-8,2,3,-6,10}, k=2.
         * Dla każdego okna o rozmiarze k znajdź pierwszą liczbę ujemną
         * (0 jeśli brak) używając Deque do przechowywania indeksów ujemnych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_DequeToArray {
        /*
         * 🧪 Zadanie 18:
         * Deque<Integer>: {10,20,30,40}.
         * Skonwertuj do tablicy przez toArray().
         * Skonwertuj do ArrayList przez konstruktor przyjmujący Collection.
         * Wypisz oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RotateDeque {
        /*
         * 🧪 Zadanie 19:
         * Deque<Integer>: {1,2,3,4,5}.
         * Napisz metodę rotateLeft(deque, n) – przesuwa elementy o n w lewo
         * (pollFirst + addLast n razy). Dla n=2 wynik: {3,4,5,1,2}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DoubleEndedTaskQueue {
        /*
         * 🧪 Zadanie 20:
         * Zamodeluj kolejkę zadań, gdzie zwykłe zadania trafiają na koniec (addLast),
         * a priorytetowe (VIP) na początek (addFirst).
         * Dodaj: "task1"(zwykłe), "URGENT"(VIP), "task2"(zwykłe), "CRITICAL"(VIP).
         * Wypisz kolejność przetwarzania (pollFirst aż pusta).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DecodeStringWithStack {
        /*
         * 🧪 Zadanie 21:
         * Zdekoduj napis w formacie "3[a]2[bc]" → "aaabcbc" używając dwóch
         * Deque (jeden na liczby, jeden na fragmenty Stringów) jako stosów.
         * Przetestuj też "2[a3[b]]" → "abbbabbb".
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_NextGreaterElement {
        /*
         * 🧪 Zadanie 22:
         * int[] nums = {4,5,2,10,8}.
         * Dla każdego elementu znajdź następny większy element na prawo
         * (-1 jeśli brak) używając Deque jako monotonicznego stosu.
         * Oczekiwany wynik: [5,10,10,-1,-1].
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_EvaluatePostfix {
        /*
         * 🧪 Zadanie 23:
         * Oblicz wyrażenie w notacji ONP (postfix) "5 3 + 2 *" → 16
         * używając Deque<Integer> jako stosu operandów.
         * Obsłuż +, -, *, /.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DequeBasedLRUCache {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prosty LRU cache o pojemności 3 używając Deque<Integer>
         * do śledzenia kolejności użycia (najświeższy na przodzie) + HashSet do sprawdzania obecności.
         * access(key): jeśli klucz istnieje, przenieś na przód; jeśli cache pełny, usuń najstarszy z ogona.
         * Przetestuj sekwencję dostępów: 1,2,3,1,4,5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ZigzagDeque {
        /*
         * 🧪 Zadanie 25:
         * Mając posortowaną tablicę {1,2,3,4,5,6,7}, użyj Deque żeby
         * ułożyć elementy w kolejności zygzakowatej: najmniejszy, największy,
         * drugi najmniejszy, drugi największy, itd. → {1,7,2,6,3,5,4}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SlidingWindowAverage {
        /*
         * 🧪 Zadanie 26:
         * Klasa MovingAverage(int windowSize) z metodą next(int val),
         * która zwraca średnią kroczącą z ostatnich windowSize wartości.
         * Użyj Deque do przechowywania aktualnego okna. Przetestuj wartości: 1,10,3,5.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DequeVsStackPerformance {
        /*
         * 🧪 Zadanie 27:
         * Porównaj czas wykonania 1 000 000 operacji push/pop na
         * java.util.Stack (synchronized) vs ArrayDeque jako stos.
         * Zmierz System.nanoTime() i wypisz różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MultiThreadedDequeAccess {
        /*
         * 🧪 Zadanie 28:
         * Pokaż że ArrayDeque NIE jest thread-safe: dwa wątki jednocześnie
         * wykonują addLast na współdzielonym ArrayDeque (po 10000 razy każdy).
         * Sprawdź czy finalny rozmiar zgadza się z oczekiwanym 20000
         * (spróbuj kilku uruchomień).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ExpressionValidatorWithDeque {
        /*
         * 🧪 Zadanie 29:
         * Napisz walidator wyrażeń arytmetycznych sprawdzający poprawność
         * nawiasów ORAZ że między nawiasami nie ma pustych par typu "()".
         * Użyj Deque jako stosu pozycji nawiasów otwierających.
         * Przetestuj: "(1+2)*(3-4)", "()", "(1+(2*3)".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_DequeBasedGraphBFS {
        /*
         * 🧪 Zadanie 30:
         * Graf jako Map<Integer, List<Integer>>. Zaimplementuj BFS
         * używając ArrayDeque jako kolejki (offer/poll) i HashSet odwiedzonych.
         * Zwróć kolejność odwiedzania wierzchołków zaczynając od wierzchołka 1
         * dla grafu: 1-[2,3], 2-[4], 3-[4], 4-[].
         */
        public static void main(String[] args) { }
    }
}
