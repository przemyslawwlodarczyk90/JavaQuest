package com.example.javaquest._02_oop.Lesson12_InnerClasses;

public class _Exercises_Lesson12_InnerClasses {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StaticNested {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę Outer z static nested class Inner.
         * Inner ma metodę greet() → "Cześć od Inner".
         * Utwórz obiekt Inner bez instancji Outer i wywołaj greet().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_InnerClass {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę Engine z private polem int horsepower.
         * Wewnętrzna klasa Turbo ma metodę boost() zwiększającą horsepower o 50.
         * Utwórz Engine, potem Engine.Turbo i wywołaj boost(). Wypisz horsepower.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_AnonymousClass {
        /*
         * 🧪 Zadanie 3:
         * Stwórz interfejs Greeter z metodą String greet(String name).
         * Utwórz anonimową klasę implementującą Greeter: "Dzień dobry, [name]!".
         * Wywołaj greet("Panie Kowalski").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_LocalClass {
        /*
         * 🧪 Zadanie 4:
         * W metodzie main zdefiniuj lokalną klasę Counter z polem count i metodami:
         * increment() i getCount().
         * Utwórz obiekt i policz do 5.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_AccessOuter {
        /*
         * 🧪 Zadanie 5:
         * Stwórz klasę BankVault z private polem String secretCode.
         * Wewnętrzna klasa Auditor może odczytać secretCode przez metodę audit().
         * Utwórz Auditor i wywołaj audit().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_Builder {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę Pizza z private polami size, crust (String).
         * Statyczna klasa Builder z metodami fluent size(String) i crust(String) i build().
         * Zbuduj pizzę i wypisz.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_AnonymousRunnable {
        /*
         * 🧪 Zadanie 7:
         * Stwórz tablicę Runnable[] z 3 anonimowymi klasami, każda wypisuje inną wiadomość.
         * Uruchom każdy Runnable w pętli.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_LocalClassCapture {
        /*
         * 🧪 Zadanie 8:
         * W metodzie static String processText(String input, int times):
         * Zdefiniuj lokalną klasę Repeater z metodą String repeat().
         * Repeater używa effectively final: input i times.
         * Zwróć wynik Repeater.repeat().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_NestedRecord {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę OrderSystem z statyczną klasą wewnętrzną Order:
         * Order(int id, String item, double price).
         * OrderSystem przechowuje tablicę Order[] i metody: addOrder, totalRevenue().
         * Dodaj 4 zamówienia i oblicz przychód.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_Comparator {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę Student z polami name (String) i gpa (double).
         * Utwórz anonimową implementację java.util.Comparator<Student>.
         * Posortuj tablicę studentów według GPA malejąco przez własny bubble sort.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BuilderFull {
        /*
         * 🧪 Zadanie 11:
         * Pełny wzorzec Builder dla klasy Person:
         * - private pola: name, email, age, phone (String), active (boolean)
         * - static class Builder z wszystkimi polami
         * - validate() w build() rzuca IllegalStateException gdy name lub email null
         * Przetestuj poprawne i niepoprawne dane.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_InnerIterator {
        /*
         * 🧪 Zadanie 12:
         * Stwórz klasę NumberList przechowującą int[].
         * Wewnętrzna klasa Iterator z metodami hasNext() i next().
         * Iterator ma dostęp do prywatnej tablicy NumberList.
         * Przetestuj iterację po liście 1-10.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_AnonymousStrategy {
        /*
         * 🧪 Zadanie 13:
         * Interfejs SortStrategy z metodą void sort(int[] arr).
         * Utwórz 3 anonimowe implementacje: bubble, selection, insertion sort.
         * Klasa Sorter przyjmuje SortStrategy i sortuje.
         * Przetestuj każdą strategię.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_NestedEnum {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę Planet z statycznym enumem wewnętrznym Atmosphere:
         * Atmosphere { NONE, THIN, THICK }
         * Planet ma pola: name, distanceAU, Atmosphere atmosphere.
         * Wypisz planety i ich atmosfery.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_InnerObserver {
        /*
         * 🧪 Zadanie 15:
         * Interfejs Observer z metodą void update(String event).
         * Klasa EventSource z polem Observer[] i metodami:
         * addObserver(Observer), emit(String event).
         * Stwórz 3 obserwatorów jako anonimowe klasy i zarejestruj ich.
         * Emituj 2 zdarzenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_MultiLevel {
        /*
         * 🧪 Zadanie 16:
         * Stwórz klasy zagnieżdżone na 3 poziomach:
         * Outer.Middle.Inner
         * Każda ma metodę name() zwracającą swoje imię.
         * Inner ma dostęp do pól Outer i Middle.
         * Utwórz instancję Inner i wywołaj name() wszystkich poziomów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_LinkedList {
        /*
         * 🧪 Zadanie 17:
         * Stwórz klasę LinkedList z private static nested class Node(value, next).
         * LinkedList z metodami: add(int), remove(int), contains(int), size(), toArray().
         * Przetestuj: dodaj 5, usuń jeden, sprawdź contains.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_AnonymousEvent {
        /*
         * 🧪 Zadanie 18:
         * @FunctionalInterface EventHandler z metodą void handle(String event).
         * Metoda static void registerHandlers(EventHandler[] handlers, String[] events).
         * Wywołuje każdy handler dla zdarzenia z tej samej pozycji.
         * Utwórz 3 handlery przez anonimowe klasy (nie lambdy) i 3 zdarzenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_LocalClassMethod {
        /*
         * 🧪 Zadanie 19:
         * Stwórz metodę int[] generateFibonacci(int count) używającą lokalnej klasy FibGenerator.
         * FibGenerator ma pola: a=0, b=1 i metodę next().
         * Zwróć tablicę pierwszych count liczb Fibonacciego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_EncapsulatedTree {
        /*
         * 🧪 Zadanie 20:
         * Stwórz klasę BinaryTree z private static nested class TreeNode(value, left, right).
         * Metody publiczne: insert(int), contains(int), int[] inOrder().
         * Przetestuj wstawiając: 5, 3, 7, 1, 4 i wypisz inOrder (powinno być posortowane).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_Closure {
        /*
         * 🧪 Zadanie 21:
         * Symuluj "closure" przez anonimową klasę:
         * Interfejs Counter z metodami: increment(), decrement(), get().
         * Metoda static Counter makeCounter(int start) zwraca anonimową implementację
         * z lokalną zmienną count (musi być tablicą int[1] – effectively final trick).
         * Stwórz 2 niezależne liczniki.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_Monad {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj Optional-like monadę przez klasy wewnętrzne:
         * Klasa Maybe<T> z private static nested class: Just<T>(value) i Nothing<T>.
         * Metody statyczne: Maybe.of(T value), Maybe.empty().
         * Metody instancyjne: boolean isPresent(), T get(), Maybe<R> map(Transformer<T,R>).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_InnerProxy {
        /*
         * 🧪 Zadanie 23:
         * Stwórz klasę Service z metodą execute(String task) wypisującą wynik.
         * Wewnętrzna klasa CachedProxy przechowuje cache (String[]) wyników.
         * CachedProxy.execute: jeśli w cache → cache hit, wpp wywołaj Service.execute i zapamiętaj.
         * Przetestuj z powtarzającymi się zadaniami.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_AbstractInner {
        /*
         * 🧪 Zadanie 24:
         * Stwórz klasę DataStructure z protected abstract static nested class Comparator.
         * Klasy: SortedList i MaxHeap rozszerzają DataStructure.
         * Każda ma swoją statyczną nested klasę Impl extends Comparator.
         * Porównaj użycie obu struktur danych.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_StateMachine {
        /*
         * 🧪 Zadanie 25:
         * Klasa TrafficLight z private static nested klasami stanów:
         * RedState, GreenState, YellowState – każda implements LightState.
         * LightState: void enter(), void exit(), LightState next().
         * TrafficLight.cycle() przechodzi przez stany.
         * Symuluj 6 przejść.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_Iterable {
        /*
         * 🧪 Zadanie 26:
         * Stwórz klasę Range implementującą uproszczony Iterable.
         * Wewnętrzna klasa RangeIterator implements Iterator z hasNext() i next().
         * Range(int from, int to, int step).
         * Przetestuj w for-each style (ręczna iteracja, bez java.util.Iterator).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_Command {
        /*
         * 🧪 Zadanie 27:
         * System poleceń z klasami wewnętrznymi:
         * Klasa TextEditor z private StringBuilder content.
         * Static nested Command z execute() i undo().
         * Wewnętrzne klasy: InsertCommand, DeleteCommand, ReplaceCommand.
         * CommandHistory przechowuje historię, undoLast() cofa ostatnią komendę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_Graph {
        /*
         * 🧪 Zadanie 28:
         * Klasa Graph z private static nested class Edge(from, to, weight).
         * Graph przechowuje Edge[] i Vertex[] (też static nested).
         * Metody: addVertex(String), addEdge(String from, String to, int weight).
         * Metoda shortestPath(String from, String to) – uproszczony Dijkstra.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_Memoization {
        /*
         * 🧪 Zadanie 29:
         * Klasa Memoize z static nested class Cache(keys[], values[], int size).
         * Interfejs Function z metodą int apply(int n).
         * Metoda static Function memoize(Function fn) zwraca anonimową implementację
         * która cachuje wyniki w Cache.
         * Przetestuj z wolną funkcją (n*n + sleep symulowany przez counter).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_DSL {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj DSL do tworzenia HTML przez klasy wewnętrzne:
         * Klasa Html z static nested: Head, Body, Div, P, Span.
         * Każda ma metody content(String) i build() → String.
         * Zbuduj stronę HTML:
         * new Html().head("Tytuł").body().div().p("Paragraf 1").p("Paragraf 2").build()
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
