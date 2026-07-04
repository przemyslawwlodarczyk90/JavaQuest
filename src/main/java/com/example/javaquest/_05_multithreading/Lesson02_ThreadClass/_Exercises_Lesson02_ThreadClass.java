package com.example.javaquest._05_multithreading.Lesson02_ThreadClass;

public class _Exercises_Lesson02_ThreadClass {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimpleThreadSubclass {
        /*
         * 🧪 Zadanie 1:
         * Stwórz klasę dziedziczącą po Thread (np. HelloThread), nadpisz run() tak,
         * by wypisywała "Hello from " + Thread.currentThread().getName(). Utwórz
         * obiekt tej klasy, wywołaj start() i poczekaj na zakończenie (join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunVsStartDemo {
        /*
         * 🧪 Zadanie 2:
         * Dla klasy z Zadania 1 wywołaj najpierw run() BEZPOŚREDNIO (bez start())
         * i wypisz Thread.currentThread().getName() – potwierdź, że to "main".
         * Następnie wywołaj start()+join() i porównaj nazwę wątku wypisaną tym razem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CustomNameViaConstructor {
        /*
         * 🧪 Zadanie 3:
         * Rozszerz klasę Thread tak, by jej konstruktor przyjmował String name i
         * przekazywał go do super(name). Utwórz 3 instancje z nazwami "Alpha",
         * "Beta", "Gamma" i uruchom je PO KOLEI (start()+join() każdej), wypisując
         * nazwę wewnątrz run().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_OverrideRunWithField {
        /*
         * 🧪 Zadanie 4:
         * Stwórz klasę dziedziczącą po Thread z polem int liczba ustawianym w
         * konstruktorze; w run() wypisz kwadrat tej liczby. Przetestuj dla wartości
         * 3, 7 i 12 (3 osobne obiekty, uruchamiane po kolei: start()+join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DoubleStartException {
        /*
         * 🧪 Zadanie 5:
         * Utwórz wątek (podklasa Thread), uruchom go (start()+join()), a następnie
         * w bloku try-catch spróbuj wywołać start() drugi raz, przechwytując
         * IllegalThreadStateException i wypisując jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ThreadSubclassCounter {
        /*
         * 🧪 Zadanie 6:
         * Stwórz klasę dziedziczącą po Thread z polem statycznym (int) zliczającym,
         * ile obiektów tej klasy utworzono (inkrementowanym w konstruktorze).
         * Utwórz 5 obiektów (BEZ startowania) i wypisz wartość licznika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SequentialSubclassThreads {
        /*
         * 🧪 Zadanie 7:
         * Utwórz 4 wątki (podklasa Thread) o nazwach "T1".."T4". Uruchamiaj każdy
         * i OD RAZU wywołuj join() (sekwencyjnie), by zaobserwować deterministyczną
         * kolejność wypisów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RunCalledMultipleTimes {
        /*
         * 🧪 Zadanie 8:
         * Wywołaj metodę run() (NIE start()) na tym samym obiekcie (podklasa Thread)
         * 3 razy pod rząd w pętli. Zaobserwuj (println), że każde wywołanie działa
         * synchronicznie w main i można je dowolnie powtarzać, w przeciwieństwie
         * do start().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ThreadSubclassWithGetterAfterRun {
        /*
         * 🧪 Zadanie 9:
         * Stwórz klasę dziedziczącą po Thread, która w run() oblicza sumę liczb
         * od 1 do 1000 i zapisuje wynik do własnego pola. Po start()+join() odczytaj
         * wynik przez getter zdefiniowany w tej klasie i wypisz go.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareRunResultTimingWithStart {
        /*
         * 🧪 Zadanie 10:
         * Zmierz czas wykonania run() (zawierającego Thread.sleep(100)) wywołanego
         * BEZPOŚREDNIO oraz czas wykonania tego samego zadania przez start()+join().
         * Wypisz oba czasy (powinny być zbliżone) i skomentuj (println) różnicę
         * koncepcyjną: main jest zablokowany od razu przy run(), a między start()
         * i join() mógłby teoretycznie robić coś innego.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreadSubclassHierarchy {
        /*
         * 🧪 Zadanie 11:
         * Stwórz klasę bazową AbstractWorkerThread extends Thread z metodą
         * abstrakcyjną doWork(), wywoływaną z run(). Zaimplementuj dwie podklasy
         * (np. PrintingWorkerThread, SummingWorkerThread), uruchom po jednej
         * instancji każdej i poczekaj (join()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_IllegalThreadStateMultipleAttempts {
        /*
         * 🧪 Zadanie 12:
         * Utwórz wątek, uruchom go i poczekaj na zakończenie. Następnie w pętli
         * 3 razy spróbuj wywołać start() ponownie, licząc, ile razy złapano
         * IllegalThreadStateException (oczekiwane: 3). Wypisz licznik na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_NondeterministicOrderObservation {
        /*
         * 🧪 Zadanie 13:
         * Uruchom 5 wątków (podklasa Thread) BEZ natychmiastowego join() po każdym
         * (najpierw wszystkie start(), potem wszystkie join()). Każdy wątek dopisuje
         * swoją nazwę do wspólnej, zsynchronizowanej listy (np. blok synchronized
         * na liście). Wypisz zebraną kolejność zakończenia i skomentuj, że może się
         * różnić między uruchomieniami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ThreadSubclassWithSharedResource {
        /*
         * 🧪 Zadanie 14:
         * Stwórz 3 wątki (podklasa Thread) dzielące wspólną tablicę wyników
         * int[3]. Każdy wątek (indeks przekazany w konstruktorze) zapisuje swój
         * wynik pod swoim indeksem. Po joinach wszystkich wypisz zawartość tablicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExtendsThreadLimitationDemo {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj interfejs Reportable z metodą report(). Stwórz klasę
         * WorkerThread extends Thread implements Reportable (bo Java nie pozwala
         * dziedziczyć jednocześnie po dwóch klasach), której run() wykonuje
         * zadanie, a report() (wywołane po join()) wypisuje podsumowanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MeasureManyThreadSubclassInstances {
        /*
         * 🧪 Zadanie 16:
         * Utwórz 200 obiektów podklasy Thread (BEZ startowania). Zmierz czas
         * samego tworzenia obiektów i wypisz go, komentując, że to tylko koszt
         * alokacji obiektu Thread – start() jeszcze nie zaangażował wątku systemowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RunThrowsCheckedWorkaround {
        /*
         * 🧪 Zadanie 17:
         * Stwórz podklasę Thread, której run() wywołuje metodę parseValue(String)
         * rzucającą (checked) Exception dla nieprawidłowego wejścia (np. napisu
         * "abc"). Obsłuż wyjątek WEWNĄTRZ run() (bo run() nie deklaruje throws) i
         * wypisz komunikat błędu zamiast pozwolić wątkowi "zniknąć" z wyjątkiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ThreadSubclassPriorityAssignment {
        /*
         * 🧪 Zadanie 18:
         * Stwórz 3 obiekty podklasy Thread, ustaw im przed startem różne priorytety:
         * Thread.MIN_PRIORITY, Thread.NORM_PRIORITY, Thread.MAX_PRIORITY. Uruchom
         * wszystkie (start()+join() każdy) i wypisz priorytet odczytany wewnątrz
         * run() (Thread.currentThread().getPriority()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ThreadSubclassNameMutation {
        /*
         * 🧪 Zadanie 19:
         * Utwórz wątek podklasy Thread z domyślną nazwą (bez przekazywania jej w
         * konstruktorze), wypisz domyślną nazwę (np. "Thread-0"). Zmień nazwę
         * metodą setName("Zmieniona") PRZED wywołaniem start(), uruchom (start()+
         * join()) i potwierdź, że w run() widoczna jest już nowa nazwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MultipleThreadSubclassesCombinedReport {
        /*
         * 🧪 Zadanie 20:
         * Stwórz 3 różne podklasy Thread (do liczenia sumy, do wyszukiwania
         * maksimum, do zliczania liczb parzystych) operujące na TEJ SAMEJ,
         * współdzielonej tablicy int[20] (wypełnionej ustalonymi wartościami).
         * Uruchom wszystkie trzy, poczekaj (join()) i wypisz połączony raport
         * z wynikami wszystkich trzech.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ThreadSubclassFactoryPattern {
        /*
         * 🧪 Zadanie 21:
         * Napisz metodę fabrykującą createWorker(String type, int[] data)
         * zwracającą odpowiednią podklasę Thread ("SUM", "MAX" lub "MIN") gotową
         * do uruchomienia. Przetestuj dla wszystkich 3 typów na tej samej tablicy
         * danych (np. {4, 8, 15, 16, 23, 42}).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ChainedThreadSubclasses {
        /*
         * 🧪 Zadanie 22:
         * Stwórz podklasę Thread, której run() na końcu URUCHAMIA (start()+join())
         * KOLEJNY wątek tej samej klasy z innym parametrem (np. licznik odliczający
         * od N do 0). Ogranicz łańcuch do 5 poziomów zagnieżdżenia, by uniknąć
         * zbyt długiego łańcucha.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ThreadSubclassWithRetryLogic {
        /*
         * 🧪 Zadanie 23:
         * Stwórz podklasę Thread symulującą "niepewne" zadanie (metoda zwracająca
         * losowo sukces/porażkę na podstawie Random z ustalonym seedem), która
         * w run() próbuje wykonać zadanie do 3 razy (retry), zanim wypisze
         * komunikat o ostatecznej porażce.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ParentChildThreadSubclasses {
        /*
         * 🧪 Zadanie 24:
         * Stwórz podklasę Thread, która w swoim run() tworzy i uruchamia
         * (start()+join()) 3 "dzieci" – inne wątki tej samej podklasy. Wypisz
         * strukturę "rodzic -> dzieci" po zakończeniu wszystkiego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ThreadSubclassBenchmarkVsRunnable {
        /*
         * 🧪 Zadanie 25:
         * Zmierz czas utworzenia 1000 obiektów podklasy Thread (bez uruchamiania)
         * oraz czas utworzenia 1000 obiektów zwykłej klasy implementującej
         * Runnable (bez uruchamiania). Wypisz oba czasy i skomentuj (println)
         * różnicę – to demonstracja czysto obiektowego kosztu alokacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ThreadSubclassResultAggregation {
        /*
         * 🧪 Zadanie 26:
         * Stwórz podklasę Thread liczącą sumę zakresu liczb (od, do przekazane
         * w konstruktorze) i zapisującą wynik we własnym polu. Podziel zakres
         * 1..1_000_000 na 4 równe części, uruchom 4 wątki tej klasy, po joinach
         * zsumuj częściowe wyniki i porównaj z wynikiem liczonym w jednym wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ThreadSubclassWithCallback {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj podklasę Thread przyjmującą w konstruktorze Runnable
         * "callback", wywoływany na końcu run() (po zakończeniu głównej pracy,
         * wciąż w tym samym wątku). Główna praca to obliczenie silni z liczby
         * (np. 6), a callback wypisuje sformatowany komunikat z wynikiem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ThreadSubclassLifecycleLogger {
        /*
         * 🧪 Zadanie 28:
         * Stwórz podklasę Thread, która przy każdym uruchomieniu run() loguje
         * (do współdzielonej, zsynchronizowanej listy logów) znaczniki czasu
         * "start pracy" i "koniec pracy". Uruchom 3 instancje SEKWENCYJNIE
         * (start()+join() po każdej) i na końcu wypisz cały log w kolejności
         * chronologicznej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ThreadSubclassExceptionPropagationDemo {
        /*
         * 🧪 Zadanie 29:
         * Stwórz podklasę Thread, której run() rzuca (celowo, nieobsłużony
         * wewnątrz run()) RuntimeException. Ustaw na niej setUncaughtExceptionHandler(...)
         * PRZED startem, by przechwycić i wypisać informację o wyjątku. Zweryfikuj,
         * że main kontynuuje działanie normalnie po join().
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullThreadClassMiniFramework {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji w mini "framework": abstrakcyjna klasa
         * ResultThread<T> extends Thread z polem T result i metodą getResult(),
         * oraz co najmniej 2 konkretne podklasy (np. do liczenia sumy i do
         * liczenia iloczynu małego zakresu liczb, by uniknąć przepełnienia).
         * Uruchom obie, poczekaj (join()) i wypisz oba wyniki przez getResult().
         */
        public static void main(String[] args) { }
    }
}
