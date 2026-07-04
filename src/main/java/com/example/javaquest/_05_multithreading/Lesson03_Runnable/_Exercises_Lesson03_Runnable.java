package com.example.javaquest._05_multithreading.Lesson03_Runnable;

public class _Exercises_Lesson03_Runnable {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimpleRunnableClass {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę implementującą Runnable (np. GreetingTask) wypisującą
         * powitanie wraz z nazwą wątku. Przekaż jej instancję do new Thread(...),
         * uruchom (start()) i poczekaj (join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunnableViaConstructorField {
        /*
         * 🧪 Zadanie 2:
         * Stwórz klasę implementującą Runnable z polem String message ustawianym
         * w konstruktorze; run() wypisuje message. Utwórz 3 instancje z różnymi
         * komunikatami i uruchom każdą w osobnym wątku, po kolei (start()+join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SameRunnableTwoThreads {
        /*
         * 🧪 Zadanie 3:
         * Utwórz JEDNĄ instancję Runnable i podepnij ją pod DWA różne wątki o
         * nazwach "Watek-1" i "Watek-2" (start()+join() oba). Wypisz nazwę wątku
         * w run() dla obu uruchomień.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RunnableWithCounterField {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę implementującą Runnable z polem int counter incrementowanym
         * w run(). Uruchom TĘ SAMĄ instancję sekwencyjnie w 4 różnych wątkach
         * (start()+join() każdy) i wypisz wartość licznika po każdym uruchomieniu
         * (powinna rosnąć: 1, 2, 3, 4).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ThreadConstructorWithName {
        /*
         * 🧪 Zadanie 5:
         * Przekaż Runnable do konstruktora Thread(Runnable, String) z nazwą
         * "Zadanie-Nazwane". Wypisz getName() wątku przed startem i wewnątrz run().
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleRunnablesSequential {
        /*
         * 🧪 Zadanie 6:
         * Stwórz 5 różnych obiektów Runnable (lambdy lub osobne klasy) wypisujących
         * kolejne liczby 1-5. Uruchamiaj je sekwencyjnie: osobny wątek na każdy,
         * z join() zaraz po starcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RunnableAndComparableTogether {
        /*
         * 🧪 Zadanie 7:
         * Stwórz klasę PriorityTask implements Runnable, Comparable<PriorityTask>
         * z polem priorytet. Utwórz 3 zadania o priorytetach 3, 1, 2, posortuj je
         * (np. Arrays.sort z compareTo) i wypisz posortowaną kolejność PRZED
         * uruchomieniem jakichkolwiek wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RunnableSharedResultField {
        /*
         * 🧪 Zadanie 8:
         * Stwórz klasę implementującą Runnable z polem wynikowym (long wynik)
         * obliczanym w run() jako suma liczb 1..100_000. Uruchom zadanie w jednym
         * wątku (start()+join()), a następnie odczytaj wynik z pola po zakończeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareExtendsThreadVsRunnable {
        /*
         * 🧪 Zadanie 9:
         * Stwórz dwa równoważne zadania: jedno jako klasa extends Thread (jak w
         * Lesson02), drugie jako klasa implements Runnable (jak w tej lekcji) –
         * oba wypisujące to samo powitanie. Uruchom oba i porównaj (w komentarzu)
         * różnicę w API użytym do uruchomienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RunnableWithValidationInConstructor {
        /*
         * 🧪 Zadanie 10:
         * Stwórz klasę implementującą Runnable, której konstruktor waliduje
         * argument (dodatnia liczba) i rzuca IllegalArgumentException dla wartości
         * <= 0. Przetestuj utworzenie obiektu z poprawną wartością (5) oraz
         * przechwyć wyjątek dla niepoprawnej wartości (-3).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RunnableTaskQueueSequential {
        /*
         * 🧪 Zadanie 11:
         * Stwórz listę 6 obiektów Runnable (różne, proste zadania) i wykonaj je
         * sekwencyjnie, za każdym razem w NOWYM wątku (start()+join() w pętli po
         * liście). Zmierz łączny czas wykonania wszystkich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SharedMutableStateRaceAwareness {
        /*
         * 🧪 Zadanie 12:
         * Stwórz JEDNĄ instancję Runnable z polem int sharedCounter, którą
         * inkrementuje 1000 razy w pętli wewnątrz run(). Uruchom ją RÓWNOCZEŚNIE
         * (bez join między nimi) w 2 wątkach (start obu, potem join obu). Wypisz
         * finalną wartość licznika i skomentuj, czy jest zgodna z oczekiwaną
         * sumą 2000 (to wprowadzenie do tematu race condition – szczegóły w
         * Lesson07, tu tylko obserwacja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RunnableFactoryMethod {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę statyczną createLoggingTask(String label) zwracającą
         * Runnable (lambdę) wypisujący etykietę i nazwę wątku. Utwórz 4 zadania
         * z różnymi etykietami i uruchom każde w osobnym wątku (start()+join()
         * sekwencyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RunnableImplementingTwoInterfaces {
        /*
         * 🧪 Zadanie 14:
         * Stwórz klasę implements Runnable, AutoCloseable, gdzie run() wykonuje
         * "pracę", a close() (wywoływane ręcznie po join()) wypisuje komunikat
         * o zwolnieniu zasobów. Przetestuj pełen cykl: utworzenie, start, join, close.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RunnableListSortedByPriority {
        /*
         * 🧪 Zadanie 15:
         * Rozszerz PriorityTask z Zadania 7 o listę 6 zadań z ustalonymi
         * priorytetami ({5, 2, 8, 1, 9, 3}). Posortuj je rosnąco wg priorytetu i
         * uruchom w kolejności posortowanej (osobne wątki, sekwencyjnie start()+
         * join()), wypisując kolejność wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RunnableWrappingLambdaAndClass {
        /*
         * 🧪 Zadanie 16:
         * W jednej tablicy Runnable[4] umieść MIX: 2 zadania jako lambda, 2 jako
         * instancje klasy implementującej Runnable. Iteruj po tablicy, uruchamiając
         * każde w osobnym wątku (start()+join()), pokazując że Thread traktuje
         * je identycznie (polimorfizm interfejsu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RunnableTaskWithRetryOnException {
        /*
         * 🧪 Zadanie 17:
         * Stwórz klasę implements Runnable symulującą zadanie, które w run() rzuca
         * (przechwycony wewnątrz run()) wyjątek przy PIERWSZYM uruchomieniu (na
         * podstawie pola-licznika prób), a przy DRUGIM uruchomieniu (nowy Thread
         * z TĄ SAMĄ instancją) kończy się sukcesem. Zademonstruj oba uruchomienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImmutableRunnableTask {
        /*
         * 🧪 Zadanie 18:
         * Stwórz klasę implements Runnable z polami final (imię, wiek), bez setterów
         * – run() wypisuje dane. Utwórz 3 niemutowalne zadania i uruchom każde
         * w osobnym wątku, potwierdzając że dane się nie zmieniają między
         * uruchomieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RunnableBenchmarkManyInstances {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas utworzenia 10 000 obiektów klasy implementującej Runnable
         * (bez uruchamiania wątków). Wypisz zmierzony czas.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RunnableAdapterForLegacyTask {
        /*
         * 🧪 Zadanie 20:
         * Masz "starą" klasę z metodą void wykonajStareZadanie() (niezwiązaną z
         * Runnable). Napisz adapter: klasę implements Runnable, która w run()
         * wywołuje wykonajStareZadanie() na przekazanej instancji starej klasy,
         * umożliwiając uruchomienie jej w osobnym wątku.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ProducerLikeRunnableSequenceNoQueue {
        /*
         * 🧪 Zadanie 21:
         * Stwórz 3 obiekty Runnable reprezentujące etapy potoku (Etap1 -> Etap2 ->
         * Etap3), każdy zapisujący wynik do współdzielonej tablicy String[3] pod
         * swoim indeksem. Uruchom je SEKWENCYJNIE (start()+join() każdy), na końcu
         * wypisz połączony wynik wszystkich etapów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_RunnablePrioritySchedulerSimulation {
        /*
         * 🧪 Zadanie 22:
         * Rozbuduj PriorityTask (Comparable) o listę 8 zadań z ustalonymi
         * priorytetami. Posortuj wg priorytetu i uruchamiaj je JEDEN PO DRUGIM
         * (start()+join()), rejestrując rzeczywistą kolejność wykonania do listy
         * i porównując ją z kolejnością posortowaną (powinny się zgadzać).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SharedResultsArrayMultipleRunnables {
        /*
         * 🧪 Zadanie 23:
         * Stwórz 5 instancji Runnable, każda licząca silnię innej liczby (1-5) i
         * zapisująca wynik do współdzielonej tablicy long[5] pod swoim indeksem
         * (przekazanym w konstruktorze). Uruchom wszystkie równolegle (5 wątków,
         * start wszystkich, potem join wszystkich), wypisz kompletną tablicę
         * wyników po zakończeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RunnableWithBuilderPattern {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj klasę TaskBuilder z metodami fluent (withName, withDelay,
         * withMessage) budującą obiekt Runnable (lambdę domykającą ustawione pola).
         * Zbuduj 3 różne zadania przez builder i uruchom je (start()+join()
         * sekwencyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RunnableExceptionIsolation {
        /*
         * 🧪 Zadanie 25:
         * Uruchom 3 wątki z Runnable, z których ŚRODKOWY celowo rzuca (nieobsłużony)
         * RuntimeException wewnątrz run(). Za pomocą setUncaughtExceptionHandler
         * na tym Thread zademonstruj, że pozostałe dwa wątki kończą się normalnie,
         * niezależnie od wyjątku w trzecim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ChainedRunnablesViaThreadJoin {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj 4 zadania Runnable, gdzie KAŻDE kolejne mnoży wynik
         * poprzedniego przez ustaloną liczbę (wynik przekazywany przez
         * współdzieloną tablicę long[1]). Uruchamiaj je w kolejności, z join()
         * między każdym (by zachować zależności), i wypisz finalny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RunnableStatsCollector {
        /*
         * 🧪 Zadanie 27:
         * Stwórz 10 zadań Runnable symulujących "czas przetwarzania" (Thread.sleep
         * z ustaloną tablicą wartości 10-100ms), każde zapisujące swój faktyczny,
         * zmierzony czas trwania do współdzielonej, zsynchronizowanej listy.
         * Uruchom wszystkie (osobne wątki, start wszystkich, potem join wszystkich),
         * policz średni, minimalny i maksymalny czas trwania z listy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RunnableStateMachineTask {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj klasę implements Runnable modelującą prosty automat stanów
         * (enum Stan {INIT, PRZETWARZANIE, ZAKONCZONY}) – run() przechodzi przez
         * wszystkie stany, wypisując każdy krok, i zapisuje finalny stan do pola
         * dostępnego przez getter po join().
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DynamicRunnableDispatch {
        /*
         * 🧪 Zadanie 29:
         * Na podstawie tablicy String[] komend ({"SUMA", "MAX", "MIN", "SUMA"})
         * wybieraj (metoda fabrykująca zwracająca odpowiedni Runnable) i uruchamiaj
         * (sekwencyjnie, start()+join()) odpowiednie zadanie operujące na wspólnej,
         * ustalonej tablicy int[] {4, 8, 15, 16, 23, 42}. Wypisz wynik każdego
         * wywołania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullRunnableMiniPipeline {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji: zbuduj mini "pipeline" z 4 zadań Runnable
         * (wczytaj dane z ustalonej tablicy, przefiltruj liczby parzyste, policz
         * sumę, sformatuj raport jako String), komunikujących się przez
         * współdzielone pola. Uruchamiaj je SEKWENCYJNIE (każde zależy od
         * poprzedniego – start()+join() po każdym) i zakończ wypisaniem finalnego
         * raportu.
         */
        public static void main(String[] args) { }
    }
}
