package com.example.javaquest._05_multithreading.Lesson19_ReadWriteLock;

public class _Exercises_Lesson19_ReadWriteLock {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicReadWriteLockSetup {
        /*
         * 🧪 Zadanie 1:
         * Utwórz ReentrantReadWriteLock rwLock, pobierz jego readLock() i writeLock().
         * Jednowątkowo: zdobądź writeLock, ustaw String value = "Hello", zwolnij
         * (finally); potem zdobądź readLock, wypisz value, zwolnij (finally).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MultipleReadersRunParallel {
        /*
         * 🧪 Zadanie 2:
         * Utwórz ReentrantReadWriteLock, użyj readLock() w 3 zadaniach (każde symuluje
         * odczyt trwający 150ms) uruchomionych na ExecutorService o rozmiarze 3.
         * Zmierz łączny czas (System.nanoTime()) – powinien wynosić ~150ms, a NIE
         * ~450ms. Zamknij pulę przez shutdown()+awaitTermination().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_WriterExclusiveBlocksReaders {
        /*
         * 🧪 Zadanie 3:
         * Uruchom 1 pisarza trzymającego writeLock przez 200ms. Po 30ms uruchom 2
         * czytelników próbujących zdobyć readLock. Wypisz komunikaty z timestampami
         * (rozpoczęcie/zakończenie) i zweryfikuj, że obaj czytelnicy weszli dopiero
         * PO zakończeniu zapisu. Zamknij pulę wątków poprawnie (shutdown+awaitTermination).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CompareReentrantLockVsReadWriteLockTiming {
        /*
         * 🧪 Zadanie 4:
         * Zmierz łączny czas 3 "odczytów" po 150ms każdy: raz używając zwykłego
         * ReentrantLock (serializacja, ~450ms), raz używając readLock()
         * z ReentrantReadWriteLock (równolegle, ~150ms). Wypisz oba czasy obok siebie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WriterMutualExclusionAmongWriters {
        /*
         * 🧪 Zadanie 5:
         * Uruchom 2 pisarzy, każdy trzymający writeLock przez 150ms. Zmierz łączny czas
         * wykonania obu zapisów – powinien wynosić ~300ms (serializacja pisarzy), a NIE
         * ~150ms (co świadczyłoby o błędnej – równoległej – pracy pisarzy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ReadLockUnlockInFinally {
        /*
         * 🧪 Zadanie 6:
         * Zdobądź readLock(), a wewnątrz try rzuć RuntimeException, zapewniając
         * unlock() w finally. Złap wyjątek na zewnątrz i wypisz
         * rwLock.getReadLockCount() (powinno wrócić do 0 po zwolnieniu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SimpleCacheWithReadWriteLock {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj klasę Cache z polem String value, metodą set(String)
         * używającą writeLock i metodą get() używającą readLock (obie z finally).
         * Jednowątkowo wywołaj set("dane"), a potem 3 razy get(), wypisując wynik
         * każdego odczytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ReadLockCountInspection {
        /*
         * 🧪 Zadanie 8:
         * Uruchom 3 wątki jednocześnie trzymające readLock() przez 200ms każdy.
         * W trakcie ich działania (np. po 50ms z osobnego wątku monitorującego)
         * odczytaj i wypisz rwLock.getReadLockCount() (powinno pokazać 3 aktywne
         * blokady odczytu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteLockHeldByCurrentThreadCheck {
        /*
         * 🧪 Zadanie 9:
         * Przed zdobyciem writeLock() wypisz rwLock.isWriteLockedByCurrentThread()
         * (powinno być false). Zdobądź writeLock(), wypisz tę samą wartość (true),
         * zwolnij w finally, wypisz ponownie (false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BasicExecutorServiceShutdownWithReadWriteLock {
        /*
         * 🧪 Zadanie 10:
         * Utwórz ExecutorService o rozmiarze 3, prześlij 3 zadania czytające (readLock,
         * symulowany odczyt 100ms). Zamknij pulę wzorcem shutdown() +
         * awaitTermination(5, TimeUnit.SECONDS) + shutdownNow() jako fallback. Wypisz
         * potwierdzenie poprawnego zamknięcia (pool.isTerminated()).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ReadHeavyWorkloadThroughputTest {
        /*
         * 🧪 Zadanie 11:
         * Uruchom 10 czytelników (każdy symuluje odczyt 150ms via readLock) na
         * ExecutorService o rozmiarze 10. Zmierz łączny czas – powinien być bliski
         * 150ms (wszyscy czytają równolegle), a nie 1500ms. Poprawnie zamknij pulę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriterStarvationRiskDemo {
        /*
         * 🧪 Zadanie 12:
         * Uruchom 5 czytelników wykonujących ŁĄCZNIE 15 krótkich odczytów (po 50ms,
         * z małymi przerwami) w tle, a RÓWNOLEGLE co 200ms (3 razy) próbę zapisu
         * (writeLock). Wypisz z timestampami, ile czasu pisarz czekał na zdobycie
         * blokady za każdym razem – zilustruj ryzyko "głodzenia" pisarza przy dużym
         * ruchu czytelników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CacheWithPeriodicRefreshReadWriteLock {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz Cache z Zadania 7. Wątek "odświeżający" 3 razy (co 300ms) ustawia
         * nową wartość przez writeLock(). 5 wątków-czytelników czyta wartość 5 razy
         * każdy (z losowym krótkim sleep) przez readLock(). Bounded join (10s),
         * zweryfikuj że żaden odczyt nie zwrócił null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReadLockThenWriteLockWorkaroundPattern {
        /*
         * 🧪 Zadanie 14:
         * ReentrantReadWriteLock NIE pozwala bezpiecznie "podnieść" readLock do
         * writeLock (ryzyko zakleszczenia). Zaimplementuj BEZPIECZNY obejście: jeśli
         * wątek trzyma readLock i chce zapisać, najpierw ZWALNIA readLock, dopiero
         * potem zdobywa writeLock, robi zapis, zwalnia writeLock, i (jeśli potrzeba)
         * odzyskuje readLock. Zademonstruj to na jednym scenariuszu odczyt->zapis->odczyt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ThreeReadersOneWriterOrderingTest {
        /*
         * 🧪 Zadanie 15:
         * Pisarz startuje jako pierwszy i trzyma writeLock przez 200ms. 30ms później
         * startują 2 czytelnicy próbujący zdobyć readLock. Zapisz (System.nanoTime())
         * moment rozpoczęcia czytania każdego czytelnika i zweryfikuj, że oba momenty
         * są PÓŹNIEJSZE niż moment zakończenia zapisu przez pisarza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleIndependentCachesWithSeparateLocks {
        /*
         * 🧪 Zadanie 16:
         * Utwórz DWIE niezależne instancje Cache, każda z WŁASNYM
         * ReentrantReadWriteLock. Jedna grupa wątków (2 pisarzy + 3 czytelników)
         * operuje na cache A, druga grupa (2 pisarzy + 3 czytelników) na cache B,
         * równolegle. Zweryfikuj (bounded join 10s), że operacje na obu cache nie
         * kolidują ze sobą (każdy cache ma spójną, oczekiwaną finalną wartość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ReadWriteLockWithExecutorServiceProperShutdown {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj mały "serwis konfiguracji" oparty o ReadWriteLock. Prześlij do
         * ExecutorService (rozmiar 7) 5 zadań czytających i 2 zadania piszące.
         * Zamknij pulę wzorcem shutdown()+awaitTermination(5s)+shutdownNow() jako
         * fallback (jak w lekcji). Zweryfikuj pool.isTerminated() == true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ReadWriteLockCounterConsistency {
        /*
         * 🧪 Zadanie 18:
         * Zadeklaruj int counter = 0 chroniony ReentrantReadWriteLock: increment()
         * pod writeLock, getValue() pod readLock. Uruchom 4 wątki-pisarzy (po 1000
         * inkrementacji każdy) oraz 4 wątki-czytelników sprawdzających w pętli (limit
         * odczytów), że wartość nigdy nie maleje. Bounded join (10s), zweryfikuj
         * finalny counter == 4000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TimedReadVsWriteThroughputComparison {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj obciążenie z 90% odczytów i 10% zapisów: 10 wątków, każdy wykonujący
         * 100 operacji (losowanych Random), na ReentrantReadWriteLock. Zmierz łączny
         * czas i porównaj z takim samym obciążeniem wykonanym na zwykłym
         * ReentrantLock (bez podziału read/write). Wypisz oba czasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GracefulReadWriteLockPoolShutdown {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę pomocniczą shutdownAndWait(ExecutorService pool) (jak
         * w lekcji: shutdown() + awaitTermination(5s) + shutdownNow() fallback).
         * Użyj jej w scenariuszu z 3 zadaniami piszącymi i 6 zadaniami czytającymi na
         * ExecutorService o rozmiarze 9. Zweryfikuj poprawne zamknięcie puli.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullConfigCacheSystemWithReadWriteLock {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj klasę ConfigCache (Map<String,String>) chronioną
         * ReentrantReadWriteLock. Uruchom 10 wątków-czytelników (po 100 odczytów =
         * 1000) i 2 wątki-pisarzy (po 5 aktualizacji = 10) na ExecutorService(12).
         * Zamknij pulę poprawnie (shutdown+awaitTermination+fallback). Zweryfikuj brak
         * wyjątków/nulli i że wszystkie aktualizacje pisarzy są ostatecznie widoczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReadWriteLockVsPlainLockThroughputBenchmark {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj obciążenie odczytowe: 20 wątków x 50 odczytów (symulowana praca 10ms
         * każdy, łącznie 1000 odczytów). Uruchom je DWA razy: raz z
         * ReentrantReadWriteLock.readLock(), raz ze zwykłym ReentrantLock. Zmierz
         * i wypisz oba czasy, demonstrując przewagę przepustowości ReadWriteLock.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_WriterPriorityWorkaroundSimulation {
        /*
         * 🧪 Zadanie 23:
         * ReentrantReadWriteLock domyślnie NIE gwarantuje priorytetu pisarzowi.
         * Zaimplementuj prosty workaround: volatile boolean writerWaiting sprawdzane
         * przez nowych czytelników PRZED zdobyciem readLock (jeśli true, czytelnik
         * czeka chwilę zamiast od razu wchodzić). Uruchom ciągły strumień 20 prób
         * odczytu w tle i 1 pisarza próbującego wejść w międzyczasie. Zweryfikuj
         * (bounded, maks. limit czasu), że pisarz zdobył blokadę w rozsądnym czasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MultiCacheReplicationSystem {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj system replikacji: "primary" Cache aktualizowany 5 razy (writeLock),
         * po każdej aktualizacji wartość jest kopiowana (odczyt z primary pod readLock,
         * zapis do 2 "replik" pod ich własnymi writeLock) przez zadania na
         * ExecutorService. Poprawnie zamknij pulę. Zweryfikuj, że po zakończeniu obie
         * repliki mają wartość identyczną z primary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ReadWriteLockBasedStatisticsAggregator {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj StatisticsAggregator z List<Integer> measurements chronioną
         * ReadWriteLock. 8 wątków "kolektorów" dodaje po 50 pomiarów (writeLock,
         * łącznie 400). 4 wątki "raportujące" co jakiś czas (po 5 razy każdy)
         * obliczają średnią i maksimum pod readLock, wypisując raport. Bounded
         * join/shutdown puli, zweryfikuj finalny rozmiar listy == 400.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FullThroughputStressTestWithMixedOperations {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj kompleksowy test: 20 wątków-czytelników (po 200 odczytów) i 5
         * wątków-pisarzy (po 20 zapisów) na ConfigCache, uruchomionych na
         * ExecutorService(25) z poprawnym zamknięciem (shutdown+awaitTermination(15s)+
         * shutdownNow fallback). Zbierz i wypisz statystyki: łączna liczba odczytów,
         * łączna liczba zapisów, łączny czas, średnie opóźnienie odczytu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DowngradingWriteToReadLockPattern {
        /*
         * 🧪 Zadanie 27:
         * Zademonstruj WSPIERANY wzorzec "obniżenia" blokady: zdobądź writeLock, zapisz
         * nową wartość, PRZED zwolnieniem writeLock zdobądź readLock (to jest
         * bezpieczne, w przeciwieństwie do podnoszenia read->write), zwolnij writeLock,
         * a NADAL trzymając readLock odczytaj i zweryfikuj właśnie zapisaną wartość,
         * na końcu zwolnij readLock. Powtórz ten wzorzec w 2 wątkach-pisarzach
         * wykonujących go po 5 razy każdy, zweryfikuj spójność odczytów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ReadWriteLockGuardedGraphStructure {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj strukturę Map<Integer, List<Integer>> reprezentującą listę sąsiedztwa
         * grafu, chronioną ReadWriteLock. 3 wątki-pisarzy dodają łącznie 30 krawędzi
         * (writeLock), 6 wątków-czytelników przechodzi graf i liczy krawędzie
         * (readLock, po 10 przejść każdy). Po zakończeniu pisarzy (bounded join)
         * zweryfikuj, że finalna liczba krawędzi odczytana pod readLock wynosi 30.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TimeBasedCacheExpirationWithReadWriteLock {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj Cache z parą (value, lastUpdatedTimestamp) aktualizowaną RAZEM pod
         * jednym writeLock, 5 razy co 300ms przez dedykowany wątek. 8 wątków-czytelników
         * czyta OBA pola razem pod jednym readLock (10 razy każdy), sprawdzając, że
         * para zawsze pochodzi z tego samego zapisu (np. przez sparowany numer wersji).
         * Bounded join/shutdown, zweryfikuj brak "rozdartych" (niespójnych) par.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullSystemBenchmarkThreeApproaches {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj końcowy benchmark porównujący TRZY podejścia dla tego samego
         * obciążenia (100 wątków, łącznie 2000 operacji, 90% odczytów/10% zapisów):
         * (a) synchronized dla wszystkiego, (b) zwykły ReentrantLock dla wszystkiego,
         * (c) ReentrantReadWriteLock z podziałem readLock/writeLock. Zmierz łączny
         * czas każdego wariantu (z poprawnie zamykaną pulą wątków) i wypisz tabelę
         * porównawczą wraz z wnioskiem, które podejście było najszybsze i dlaczego
         * (nawiązując do sylabusu tej lekcji – obciążenie read-heavy).
         */
        public static void main(String[] args) { }
    }
}
