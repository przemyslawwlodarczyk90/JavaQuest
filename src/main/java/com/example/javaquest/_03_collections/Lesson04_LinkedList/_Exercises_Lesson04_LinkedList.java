package com.example.javaquest._03_collections.Lesson04_LinkedList;

public class _Exercises_Lesson04_LinkedList {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddRemove {
        /*
         * 🧪 Zadanie 1:
         * Stwórz LinkedList<String>. Dodaj "A","B","C" na końcu.
         * Dodaj "X" na początku, "Y" na pozycji 2.
         * Wypisz, pobierz get(0), get(2), rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_DequeOps {
        /*
         * 🧪 Zadanie 2:
         * Użyj LinkedList jako Deque:
         * addFirst, addLast, getFirst, getLast, removeFirst, removeLast.
         * Wypisz stan po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AsStack {
        /*
         * 🧪 Zadanie 3:
         * Używając LinkedList jako stosu (push/pop/peek):
         * Wcześnij 5 liczb (1-5). Wypisz szczyt.
         * Pop i wypisz po każdym popu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AsQueue {
        /*
         * 🧪 Zadanie 4:
         * Kolejka klientów (LinkedList jako Queue):
         * offer 5 klientów, peek na pierwszego, poll 2 klientów.
         * Wypisz kolejkę po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_PollPeek {
        /*
         * 🧪 Zadanie 5:
         * Pokaż różnicę między poll/peek (null gdy puste) a remove/element (wyjątek).
         * Przetestuj na pustej LinkedList.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IterateListIterator {
        /*
         * 🧪 Zadanie 6:
         * LinkedList: {10, 20, 30, 40, 50}.
         * Użyj ListIteratora aby każdy element mnożyć przez 2 podczas iteracji.
         * Wypisz przed i po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ModifyDuring {
        /*
         * 🧪 Zadanie 7:
         * LinkedList: {1,2,3,4,5}.
         * Podczas iteracji ListIteratorem: po każdej liczbie parzystej dodaj ją × (-1).
         * Wypisz wynik: {1, 2, -2, 3, 4, -4, 5}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RemoveDuring {
        /*
         * 🧪 Zadanie 8:
         * LinkedList: {"keep","remove","keep","remove","keep"}.
         * Używając iteratora usuń wszystkie "remove".
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_Contains {
        /*
         * 🧪 Zadanie 9:
         * LinkedList<Integer>: {5,10,15,20,25}.
         * Sprawdź: contains(15), contains(7), indexOf(20), lastIndexOf(15).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ToList {
        /*
         * 🧪 Zadanie 10:
         * Stwórz LinkedList z 5 elementów.
         * Skonwertuj do ArrayList (new ArrayList<>(linkedList)).
         * Sprawdź czy obie mają te same elementy (equals).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BrowserHistory {
        /*
         * 🧪 Zadanie 11:
         * Symuluj historię przeglądarki:
         * visit(url) – dodaj na przód, back() – cofnij, forward() – naprzód.
         * Używaj LinkedList jako Deque z dwoma kolekcjami (back/forward).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TextEditor {
        /*
         * 🧪 Zadanie 12:
         * LinkedList<String> reprezentuje kolejkę tasków do procesowania.
         * addTask(task) – dodaje na koniec
         * processNext() – pobiera i procesuje pierwszy
         * addPriorityTask(task) – dodaje na początek
         * Przetestuj sekwencję operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PalindromeCheck {
        /*
         * 🧪 Zadanie 13:
         * Sprawdź czy LinkedList<Character> jest palindromem
         * używając Deque: pollFirst i pollLast porównuj.
         * Przetestuj: "racecar", "hello".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_Interleave {
        /*
         * 🧪 Zadanie 14:
         * Dwie LinkedListy A={1,2,3} i B={a,b,c}.
         * Przeplataj je: 1,a,2,b,3,c używając Deque.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SlidingWindow {
        /*
         * 🧪 Zadanie 15:
         * Używając LinkedList jako okna o rozmiarze K:
         * Oblicz sumę kroczącą (sliding sum) dla {1,2,3,4,5,6} K=3.
         * Po każdym dodaniu nowego i usunięciu najstarszego wypisz sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LRUSimple {
        /*
         * 🧪 Zadanie 16:
         * Prosta LRU-like struktura: LinkedList o maks rozmiarze N.
         * access(element): jeśli istnieje → przesuń na front, jeśli nie → dodaj na front
         * i usuń ogon gdy rozmiar > N.
         * Przetestuj z N=3: access A,B,C,A,D.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_Josephus {
        /*
         * 🧪 Zadanie 17:
         * Problem Józefa: N osób w kółku, co K-ta jest eliminowana.
         * Użyj LinkedList: poll z przodu, k-1 razy offerLast, raz poll-eliminacja.
         * Przetestuj N=7, K=3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BlockingStream {
        /*
         * 🧪 Zadanie 18:
         * Symulacja streaming: LinkedList jako bufor.
         * Producent dodaje dane na koniec, konsument pobiera z przodu.
         * Bufor ma max pojemność 5. Wypisz stany bufora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_InsertSorted {
        /*
         * 🧪 Zadanie 19:
         * Wstawiaj do LinkedList tak, aby zachować posortowaną kolejność.
         * insertSorted(list, value): znajdź odpowiednie miejsce, wstaw przez ListIterator.
         * Przetestuj: wstaw {5,3,7,1,4} po kolei.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_Shuffle {
        /*
         * 🧪 Zadanie 20:
         * Przetasuj LinkedList (implementacja Fisher-Yates) bez konwersji na ArrayList.
         * Używaj listIterator().set() do zamiany.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ReverseInGroups {
        /*
         * 🧪 Zadanie 21:
         * Odwróć LinkedList partiami po K elementów.
         * {1,2,3,4,5,6}, K=2 → {2,1,4,3,6,5}.
         * {1,2,3,4,5,6,7}, K=3 → {3,2,1,6,5,4,7}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MergeSorted {
        /*
         * 🧪 Zadanie 22:
         * Scal dwie posortowane LinkedListy w jedną posortowaną.
         * A={1,3,5,7}, B={2,4,6,8} → {1,2,3,4,5,6,7,8}.
         * Bez tworzenia nowej listy – przenoś węzły przez addLast.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TwoStacks {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj dwa stosy używając jednej LinkedList (podziałów).
         * stack1 używa addFirst/removeFirst (głowa).
         * stack2 używa addLast/removeLast (ogon).
         * Przetestuj niezależność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DequeSort {
        /*
         * 🧪 Zadanie 24:
         * Posortuj LinkedList używając tylko operacji Deque (addFirst,addLast,pollFirst,peekFirst).
         * Wskazówka: insertion sort przez Deque.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_Flatten {
        /*
         * 🧪 Zadanie 25:
         * Zagnieżdżona LinkedList: [[1,2],[3],[4,5,6]].
         * Spłaszcz do jednej LinkedList przez addAll i Deque.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RunningMedian {
        /*
         * 🧪 Zadanie 26:
         * Dla każdego nowego elementu dodanego do LinkedList
         * wypisz aktualną medianę wszystkich elementów.
         * Wstaw po kolei: 5, 10, 3, 8, 1.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DoubleBucket {
        /*
         * 🧪 Zadanie 27:
         * Użyj LinkedList jako dwóch kolejek priorytetowych (min + max)
         * przez wybór strony dodawania. Implementuj: add(v), min(), max().
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TimeWindow {
        /*
         * 🧪 Zadanie 28:
         * Klasa TimeWindow przechowuje zdarzenia z timestampem w LinkedList.
         * addEvent(time, data): dodaj.
         * evict(cutoffTime): usuń starsze niż cutoff z ogona.
         * getCount(): ile zdarzeń w oknie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiQueue {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj kolejkę wielopoziomową (3 priorytety) na bazie
         * 3 LinkedList. poll() zawsze pobiera z najwyższego niepustego priorytetu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ChainOfQueues {
        /*
         * 🧪 Zadanie 30:
         * Pipeline przetwarzania: 3 kolejki (etapy).
         * Element przechodzi: queue1 → process1 → queue2 → process2 → queue3 → done.
         * Symuluj pipeline z 5 elementami i różnymi transformacjami na każdym etapie.
         */
        public static void main(String[] args) { }
    }
}
