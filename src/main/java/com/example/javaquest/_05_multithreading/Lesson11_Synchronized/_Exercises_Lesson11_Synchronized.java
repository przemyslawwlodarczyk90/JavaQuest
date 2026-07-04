package com.example.javaquest._05_multithreading.Lesson11_Synchronized;

public class _Exercises_Lesson11_Synchronized {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SynchronizedInstanceMethodSharedObject {
        /*
         * 🧪 Zadanie 1:
         * Zbuduj klasę Licznik z synchronized metodami increment()/getValue(). Utwórz
         * JEDNĄ instancję i uruchom 2 wątki wywołujące increment() na niej 100_000 razy
         * każdy (join(10_000)). Wypisz oczekiwany i rzeczywisty wynik – potwierdź poprawność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TwoInstancesNoBlocking {
        /*
         * 🧪 Zadanie 2:
         * Użyj klasy Licznik z Zadania 1. Utwórz DWIE osobne instancje, każda używana
         * przez własny wątek (100_000 increment() każdy, join(10_000)). Wypisz oba
         * wyniki (poprawne) i skomentuj, że różne obiekty = różne monitory = wątki
         * się nie blokują.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SynchronizedStaticMethod {
        /*
         * 🧪 Zadanie 3:
         * Napisz `static synchronized void incrementStatic()` modyfikującą statyczne
         * pole `static int total`. Uruchom 2 wątki wywołujące ją 100_000 razy każdy
         * (join(10_000)). Wypisz oczekiwany i rzeczywisty wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FixRaceConditionSnippet {
        /*
         * 🧪 Zadanie 4:
         * Weź niesynchronizowaną metodę increment() (jak w Lesson10 MutableCounter),
         * dodaj słowo kluczowe synchronized. Uruchom 4 wątki x 50_000 increment()
         * (join(10_000)) i potwierdź, że problem z race condition zniknął.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SynchronizedBlockDedicatedLock {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj `private static final Object lock = new Object();` i statyczne pole
         * `int counter`. Napisz metodę inkrementującą counter wewnątrz `synchronized
         * (lock) { counter++; }`. Uruchom 2 wątki x 100_000 wywołań (join(10_000))
         * i zweryfikuj poprawność wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SynchronizedBankAccount {
        /*
         * 🧪 Zadanie 6:
         * Zbuduj klasę BankAccount z synchronized metodami deposit(int)/withdraw(int)/
         * getBalance(). Uruchom kilka wątków wykonujących ustaloną liczbę wpłat i wypłat
         * o stałych kwotach (join(10_000)). Wypisz oczekiwane i rzeczywiste saldo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SameObjectDifferentMethodsBlockEachOther {
        /*
         * 🧪 Zadanie 7:
         * W jednej klasie zdefiniuj dwie synchronized metody instancyjne A() i B(),
         * każda z Thread.sleep(200). Wywołaj obie na TEJ SAMEJ instancji z 2 wątków
         * i zmierz czas całkowity (join(5_000)) – powinien wynosić ok. sumy obu
         * (blokują się nawzajem, bo dzielą monitor `this`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_StaticAndInstanceIndependentLocks {
        /*
         * 🧪 Zadanie 8:
         * W jednej klasie zdefiniuj synchronized metodę statyczną modyfikującą pole
         * statyczne ORAZ synchronized metodę instancyjną modyfikującą pole instancyjne.
         * Uruchom wątki wywołujące obie równolegle (po 50_000 razy, join(10_000))
         * i zweryfikuj, że każde pole osobno jest poprawne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FixLesson10WideCounterDemo {
        /*
         * 🧪 Zadanie 9:
         * Przepisz demo MutableCounter z Lesson10 (4 wątki x 200_000 increment())
         * dodając synchronized do increment()/getValue(). Wypisz oczekiwany i rzeczywisty
         * wynik, potwierdzając naprawę problemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LockOnDedicatedObjectVsRiskyReference {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj dwa warianty synchronized block: (a) blokada na dedykowanym
         * `private final Object lock`, (b) blokada na Stringu pola (np. `synchronized
         * (someField)` gdzie someField to String). Uruchom po 2 wątki x 50_000
         * inkrementacji na obu wariantach (join(10_000)) i skomentuj (print), dlaczego
         * wariant (a) jest bezpieczniejszy (String może być nieświadomie współdzielony
         * z innym kodem).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoMethodsSameMonitorUnnecessaryContention {
        /*
         * 🧪 Zadanie 11:
         * Zdefiniuj klasę z polami `int a, int b` i synchronized metodami incrementA()/
         * incrementB() (obie na monitorze `this`). Zmierz czas wykonania 2 wątków
         * wywołujących odpowiednio incrementA() i incrementB() po 500_000 razy
         * (join(10_000)) – zaobserwuj (print czasu), że mimo dotyczenia różnych pól,
         * metody się serializują (współdzielony monitor).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SeparateLocksPerField {
        /*
         * 🧪 Zadanie 12:
         * Przerób klasę z Zadania 11, dodając DWA osobne obiekty-blokady (lockA, lockB)
         * i synchronized block wokół odpowiednich pól zamiast synchronized na całej
         * metodzie. Zmierz czas dla tych samych parametrów i porównaj z wynikiem
         * z Zadania 11 – powinien być krótszy (brak zbędnej rywalizacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ThreadSafeStackOverArrayList {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj ThreadSafeStack<Integer> opakowującą ArrayList z synchronized metodami
         * push(T)/pop()/size(). Uruchom kilka wątków push-ujących ustaloną liczbę
         * elementów (join(10_000)), sprawdź finalny size() i brak wyjątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LazySingletonSynchronizedStatic {
        /*
         * 🧪 Zadanie 14:
         * Napisz klasę z prywatnym statycznym polem `instance` i `static synchronized
         * Config getInstance()` tworzącą obiekt tylko przy pierwszym wywołaniu (sprawdzenie
         * wewnątrz bloku sync). Uruchom 8 wątków wywołujących getInstance() (join(5_000))
         * i potwierdź (przez ==), że wszystkie dostały TEN SAM obiekt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ReentrantSynchronizedCallChain {
        /*
         * 🧪 Zadanie 15:
         * Napisz synchronized metodę A(), która wewnątrz wywołuje inną synchronized
         * metodę B() na TYM SAMYM obiekcie. Uruchom kilka wątków wywołujących A()
         * wielokrotnie (join(5_000)) i potwierdź brak zakleszczenia (reentrancy monitora).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_NarrowBlockVsWholeMethodTiming {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj dwie wersje pętli inkrementującej wspólny licznik: (a) synchronized
         * obejmuje TYLKO `counter++`, (b) synchronized obejmuje też sztuczny, niekrytyczny
         * Thread.sleep(1) w każdej iteracji. Uruchom obie z 4 wątkami (mniejsza liczba
         * iteracji, np. 200, join(10_000)) i porównaj zmierzone czasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FixLesson09AtomicityDemo {
        /*
         * 🧪 Zadanie 17:
         * Weź demo z Lesson09 (4 wątki x 200_000 count++, niesynchronizowane, błędne).
         * Dodaj synchronized (metoda lub blok) i uruchom ponownie z tymi samymi
         * parametrami (join(10_000)). Wypisz oczekiwany i rzeczywisty wynik, potwierdzając
         * naprawę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TwoIndependentCountersSeparateLocks {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj klasę TwoCounters z polami counterA/counterB, każde chronione WŁASNYM
         * synchronized blockiem na osobnym obiekcie-blokadzie. Uruchom 4 wątki (2 na
         * counterA, 2 na counterB, po 100_000 wywołań, join(10_000)). Wypisz oba wyniki
         * i skomentuj (print), dlaczego jedna wspólna blokada dla obu byłaby zbędnym
         * ograniczeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SimpleSynchronizedBoundedBuffer {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj klasę BoundedBuffer (tablica o stałym rozmiarze, np. 100) z synchronized
         * metodami addIfRoom(int)/removeIfAvailable() (bez wait/notify – jeśli brak
         * miejsca/elementów, po prostu zwróć false). Uruchom wątek producenta i wątek
         * konsumenta wykonujące ustaloną liczbę prób (join(5_000)) i zweryfikuj brak
         * uszkodzenia danych (suma dodanych vs usuniętych spójna).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ThreeLockGranularitiesComparison {
        /*
         * 🧪 Zadanie 20:
         * Zmierz czas wykonania tego samego obciążenia (4 wątki x 100_000 inkrementacji)
         * dla 3 wariantów: (a) cała metoda synchronized, (b) wąski synchronized block,
         * (c) osobne blokady per pole (jeśli aktualizowane są 2 niezależne pola).
         * Wypisz tabelę czasów przy zachowaniu identycznej poprawności wyników.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ParkingLotBoundedOccupancy {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj klasę ParkingLot(int capacity) z synchronized metodami enter()/exit()
         * pilnującymi, by `occupiedSpots` nigdy nie spadło poniżej 0 ani nie przekroczyło
         * capacity (enter() nie wchodzi, jeśli brak miejsca). Uruchom kilka wątków-"aut"
         * wywołujących enter()/exit() ustaloną liczbę razy (join(10_000)) i zweryfikuj
         * finalne occupiedSpots mieści się w [0, capacity].
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SynchronizedStatisticsGroupUpdate {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj klasę Statistics z polami count/sum/min/max i JEDNĄ synchronized metodą
         * addValue(double) aktualizującą wszystkie 4 pola razem atomowo. Podaj ustaloną
         * (seed) tablicę losowych wartości podzieloną między 4 wątki (join(10_000)).
         * Porównaj wynik z wartościami obliczonymi sekwencyjnie na tych samych danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ThreadSafeLRUCacheWrapper {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj Cache<K,V> opakowującą LinkedHashMap z synchronized metodami get(K)/
         * put(K,V). Wygeneruj ustaloną sekwencję operacji put/get z wielu wątków
         * (join(10_000)) i zweryfikuj brak ConcurrentModificationException oraz
         * oczekiwany finalny rozmiar mapy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TransferBetweenTwoAccountsFixedLockOrder {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj dwa obiekty BankAccount (każdy z unikalnym polem id) i statyczną metodę
         * transfer(a, b, amount) synchronizującą się na OBU kontach w kolejności ustalonej
         * wg id (zawsze najpierw mniejsze id), by uniknąć zakleszczenia. Uruchom wiele
         * wątków wykonujących ustalone transfery między kontami (join(10_000)) i zweryfikuj,
         * że suma sald obu kont pozostaje stała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ShardedCounterGroupWithOwnLocks {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj ThreadSafeCounterGroup z tablicą N=4 liczników, każdy chroniony WŁASNYM
         * obiektem-blokadą. Uruchom wiele wątków inkrementujących losowy (ustalony seed)
         * indeks z tablicy ustaloną liczbę razy (join(10_000)). Zsumuj wszystkie liczniki
         * i porównaj z całkowitą liczbą wykonanych inkrementacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CombinedContentionBenchmark {
        /*
         * 🧪 Zadanie 26:
         * Odtwórz w jednym mini-benchmarku 3 scenariusze z tej lekcji: "ten sam obiekt"
         * (jedna instancja Licznika dzielona przez wątki), "różne obiekty" (osobna
         * instancja per wątek), "statyczna metoda" (globalna blokada klasy) – dla tego
         * samego łącznego obciążenia. Wypisz tabelę zmierzonych czasów dla wszystkich
         * trzech wariantów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_UniqueIdGeneratorNoDuplicates {
        /*
         * 🧪 Zadanie 27:
         * Napisz `static synchronized long nextId()` zwiększającą i zwracającą statyczny
         * licznik. Uruchom wiele wątków, każdy pobierający ustaloną liczbę ID i zapisujący
         * je do WŁASNEJ lokalnej listy (join(10_000)). Po zakończeniu scal wszystkie ID
         * i zweryfikuj brak duplikatów (np. przez rozmiar Set vs rozmiar listy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SynchronizedMovingAverage {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj MovingAverage(int windowSize) z synchronized metodami addValue(double)/
         * getAverage() utrzymującymi bufor kołowy ostatnich N wartości. Podaj ustaloną
         * sekwencję wartości rozdzieloną między kilka wątków w KONTROLOWANY (przewidywalny)
         * sposób (join(10_000)) i zweryfikuj finalną średnią z ręcznie wyliczoną wartością
         * oczekiwaną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ReadHeavyWriterVsReadersConsistency {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj klasę z synchronized increment() i synchronized getValue() na tym samym
         * obiekcie. Uruchom 1 wątek-pisarza (kilkaset increment()) i kilka wątków-czytelników
         * (dużo więcej wywołań getValue() każdy, join(10_000)). Zweryfikuj, że żaden odczyt
         * nie zwrócił wartości spoza możliwego zakresu i finalna wartość zgadza się
         * z liczbą wykonanych inkrementacji. Skomentuj (print) koszt synchronizowania
         * też odczytów (zapowiedź ReadWriteLock z późniejszej lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ThreadSafeInventoryCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj system magazynowy z Map<String,Integer> stock chronioną JEDNYM obiektem-
         * blokadą, z synchronized metodami reserve(String item, int qty) (zwraca false,
         * jeśli brak wystarczającego stanu – nie pozwala zejść poniżej 0) i restock(String
         * item, int qty). Uruchom wiele wątków wykonujących ustaloną, z góry zdefiniowaną
         * mieszankę operacji reserve/restock (join(10_000)) i zweryfikuj finalne stany
         * magazynowe względem wartości obliczonej sekwencyjnie dla tej samej listy operacji.
         */
        public static void main(String[] args) { }
    }
}
