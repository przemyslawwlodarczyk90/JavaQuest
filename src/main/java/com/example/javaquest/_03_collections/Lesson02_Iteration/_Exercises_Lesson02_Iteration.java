package com.example.javaquest._03_collections.Lesson02_Iteration;

public class _Exercises_Lesson02_Iteration {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ForLoop {
        /*
         * 🧪 Zadanie 1:
         * Lista: {10, 20, 30, 40, 50}.
         * Wypisz każdy element z jego indeksem używając pętli for z indeksem.
         * Wypisz też sumę wszystkich elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ForEach {
        /*
         * 🧪 Zadanie 2:
         * Lista imion: {"Anna", "Bartek", "Celina", "Damian"}.
         * Wypisz każde imię z użyciem for-each.
         * Wypisz tylko imiona dłuższe niż 5 liter.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IteratorBasic {
        /*
         * 🧪 Zadanie 3:
         * Lista: {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}.
         * Iteruj za pomocą Iteratora i wypisz tylko liczby parzyste.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_IteratorRemove {
        /*
         * 🧪 Zadanie 4:
         * Lista: {"java", "python", "js", "kotlin", "go", "c"}.
         * Użyj Iteratora.remove() aby usunąć wszystkie słowa krótsze niż 4 litery.
         * Wypisz listę po operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ListIteratorForward {
        /*
         * 🧪 Zadanie 5:
         * Lista: {1, 2, 3, 4, 5}.
         * Użyj ListIteratora aby zamienić każdą nieparzystą liczbę na jej kwadrat.
         * Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ListIteratorBackward {
        /*
         * 🧪 Zadanie 6:
         * Lista: {"A", "B", "C", "D", "E"}.
         * Przeiteruj naprzód. Potem przeiteruj wstecz używając ListIteratora.
         * Wypisz kolejność elementów w obu kierunkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ForEachLambda {
        /*
         * 🧪 Zadanie 7:
         * Lista liczb od 1 do 10.
         * Użyj forEach z lambdą aby wypisać kwadrat każdej liczby.
         * Użyj forEach z referencją do metody (System.out::println).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RemoveIf {
        /*
         * 🧪 Zadanie 8:
         * Lista: {-5, 3, -2, 7, -1, 0, 4, -8, 9}.
         * Użyj removeIf aby usunąć: ujemne, zero, liczby > 6.
         * Wypisz po każdej operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ReverseIteration {
        /*
         * 🧪 Zadanie 9:
         * Lista: {1, 2, 3, 4, 5}.
         * Wypisz listę w odwróconej kolejności (3 sposoby):
         * 1. for z indeksem wstecz
         * 2. Collections.reverse + for-each
         * 3. ListIterator.hasPrevious/previous
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConcurrentMod {
        /*
         * 🧪 Zadanie 10:
         * Pokaż ConcurrentModificationException:
         * Spróbuj usunąć element z for-each (złap wyjątek).
         * Potem zrób to poprawnie: 3 bezpieczne sposoby.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_EvenOddSplit {
        /*
         * 🧪 Zadanie 11:
         * Lista {1..20}. Podczas jednej iteracji podziel na:
         * - parzysteList i nieparzystzeList.
         * Bez usuwania elementów z oryginału.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_InsertDuring {
        /*
         * 🧪 Zadanie 12:
         * Lista: {1, 2, 3, 4, 5}.
         * Użyj ListIteratora aby po każdej liczby > 2 wstawić jej kwadratu.
         * Wynik: {1, 2, 3, 9, 4, 16, 5, 25}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WordFrequency {
        /*
         * 🧪 Zadanie 13:
         * String: "the quick brown fox jumps over the lazy dog the fox".
         * Podziel na słowa i zlicz ile razy każde słowo wystąpiło.
         * Wypisz wyniki (bez HashMap – użyj ArrayList par lub własnej logiki).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PeekPattern {
        /*
         * 🧪 Zadanie 14:
         * Lista: {3, 1, 4, 1, 5, 9, 2, 6}.
         * Iteruj i porównuj każdy element z następnym (peek-ahead).
         * Wypisz pary gdzie następny jest większy od bieżącego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ZipLists {
        /*
         * 🧪 Zadanie 15:
         * Lista A: {1, 2, 3}, Lista B: {"raz", "dwa", "trzy"}.
         * Iteruj przez obie jednocześnie (używając indeksu) i
         * twórz pary "1=raz", "2=dwa", "3=trzy" w nowej liście.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_Chunking {
        /*
         * 🧪 Zadanie 16:
         * Lista: {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, chunkSize=3.
         * Podziel przez iterację na listy: [[1,2,3],[4,5,6],[7,8,9],[10]].
         * Wypisz każdy chunk.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RunningAverage {
        /*
         * 🧪 Zadanie 17:
         * Lista: {2, 4, 6, 8, 10}.
         * Iteruj i wypisz średnią kroczącą po każdym elemencie.
         * Po el. 1: 2.0, po el. 2: 3.0, po el. 3: 4.0...
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NestedIteration {
        /*
         * 🧪 Zadanie 18:
         * Dwie listy: A={1,2,3}, B={4,5,6}.
         * Wypisz wszystkie pary (a,b) z A×B.
         * Zsumuj: ile par ma a+b > 7?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RingBuffer {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj nieskończoną iterację cykliczną po liście {A,B,C,D}
         * (bez ListIterator). Wypisz 12 pierwszych elementów.
         * Wskazówka: indeks % size().
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DualIterator {
        /*
         * 🧪 Zadanie 20:
         * Dwie posortowane listy. Scal je jak w merge sort:
         * iteruj przez dwie listy jednocześnie (dwa iteratory)
         * i scalaj do jednej posortowanej listy.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CustomIterator {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj klasę Range implements Iterable<Integer>
         * z polami start, end, step.
         * Range(1, 10, 2) → iteruje: 1, 3, 5, 7, 9.
         * Użyj wewnętrznej klasy RangeIterator implements Iterator<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FilterIterator {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj FilterIterator<T> który opakowuje inny Iterator<T>
         * i filtruje elementy przez Predicate<T>.
         * Przetestuj: FilterIterator z listą {1..10} i predykatem n%3==0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TransformIterator {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj MapIterator<T,R> który opakowuje Iterator<T>
         * i transformuje elementy przez Function<T,R>.
         * Przetestuj: Iterator<String> → MapIterator(String::length) → Iterator<Integer>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BidirectionalList {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj prostą dwukierunkową listę z własnym ListIteratorem.
         * Węzły: prev, next, value.
         * ListIterator: hasNext, next, hasPrevious, previous.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LazyIterator {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj "lazy" iterator generujący liczby Fibonacciego na żądanie
         * (nie obliczaj z góry, tylko w next()).
         * Wypisz pierwsze 15 liczb Fibonacciego przez ten iterator.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PeekableIterator {
        /*
         * 🧪 Zadanie 26:
         * PeekableIterator<T> dodaje metodę peek() – podgląd bez przesuwania.
         * Opakowuje Iterator<T>.
         * Przetestuj: peek() nie przesuwa kursora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ZipIterator {
        /*
         * 🧪 Zadanie 27:
         * ZipIterator<A,B> łączy dwa Iterator<A> i Iterator<B>
         * w Iterator<Object[]> zwracający pary {a, b}.
         * Zatrzymuje się gdy którykolwiek się wyczerpie.
         * Przetestuj: zip({1,2,3}, {"a","b","c"}) → {[1,"a"],[2,"b"],[3,"c"]}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CachingIterator {
        /*
         * 🧪 Zadanie 28:
         * CachingIterator<T> zapisuje ostatnio odwiedzone N elementów.
         * getCache() → List<T> elementów odwiedzonych do tej pory.
         * Przetestuj z N=3 na liście {1..10}.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TreeIterator {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj prosty węzeł binarny (value, left, right).
         * Napisz iterator in-order (lewy → root → prawy) używając Stosu (ArrayDeque).
         * Przetestuj na prostym BST.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FlatMapIterator {
        /*
         * 🧪 Zadanie 30:
         * FlatMappingIterator<T> przyjmuje Iterator<Iterable<T>>
         * i iteruje przez elementy wszystkich kolekcji jakby były jedną.
         * Przetestuj: iteruj przez [[1,2],[3],[4,5,6]] → 1,2,3,4,5,6.
         */
        public static void main(String[] args) { }
    }
}
