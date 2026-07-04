package com.example.javaquest._05_multithreading.Lesson14_Volatile;

public class _Exercises_Lesson14_Volatile {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_VolatileFlagStop {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj `private static volatile boolean running = true`. Uruchom wątek,
         * który w pętli `while (running)` zlicza iteracje (dodaj też górny limit
         * bezpieczeństwa np. 100_000_000, na wypadek błędu). W main() poczekaj 200ms
         * (Thread.sleep), ustaw running = false, wykonaj bounded join (5s) i wypisz
         * liczbę wykonanych iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_VolatileCounterNotAtomic {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj `private static volatile int counter = 0`. Uruchom 5 wątków,
         * z których każdy wykonuje counter++ 20 000 razy. Po bounded join (5s) każdego
         * wątku porównaj oczekiwany wynik (5 * 20000) z rzeczywistą wartością counter
         * i wypisz, czy wystąpiła race condition mimo volatile.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VolatileBooleanToggle {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj `volatile boolean toggle = false`. Jeden wątek 10 razy zmienia
         * toggle na przeciwną wartość co 50ms. Drugi wątek w pętli z limitem 1000
         * sprawdzeń odczytuje toggle i wypisuje każdą zauważoną zmianę wraz z numerem
         * sprawdzenia. Zakończ oba wątki bounded joinem (5s).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_VolatileLongTimestamp {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj `volatile long timestamp = 0`. Jeden wątek 5 razy (co 100ms)
         * ustawia timestamp = System.currentTimeMillis(). Main po każdym takim
         * ustawieniu (np. co 100ms w osobnej pętli monitorującej z limitem 20 prób)
         * odczytuje i wypisuje aktualną wartość timestamp.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VolatileReferenceStatus {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj `volatile String status = "INIT"`. Wątek roboczy po 100ms
         * ustawia status = "DONE". Main w pętli z limitem 10 000 sprawdzeń odczytuje
         * status i przerywa pętlę, gdy status.equals("DONE"), po czym wypisuje liczbę
         * wykonanych sprawdzeń przed wykryciem zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VolatileArrayReferenceSwap {
        /*
         * 🧪 Zadanie 6:
         * Zadeklaruj `volatile int[] data = {1, 2, 3}`. Wątek roboczy po 100ms
         * PODMIENIA CAŁĄ referencję: data = new int[]{9, 9, 9} (nie modyfikuje
         * pojedynczego elementu!). Main w pętli z limitem odczytuje data[0] i wypisuje
         * moment wykrycia zmiany. W komentarzu/println wyjaśnij, że volatile chroni
         * podmianę referencji, a nie modyfikacje elementów wewnątrz tablicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ShutdownRequestedPattern {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj klasę SimpleWorker z polem `volatile boolean shutdownRequested`,
         * metodą run() zawierającą pętlę `while (!shutdownRequested)` z Thread.sleep(10)
         * i licznikiem cykli, oraz metodą requestShutdown(). Uruchom wątek na tej klasie,
         * poczekaj 300ms, wywołaj requestShutdown(), zrób bounded join (5s) i wypisz,
         * czy wątek się zakończył oraz ile cykli wykonał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_MultipleReadersOneFlag {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj `volatile boolean dataReady = false`. Uruchom 3 wątki-czytelniki,
         * z których każdy w pętli z limitem 100 000 sprawdzeń czeka na dataReady == true,
         * po czym wypisuje "Czytelnik-N widzi dataReady" i kończy. Main po 150ms ustawia
         * dataReady = true. Zrób bounded join (5s) na wszystkich trzech wątkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_VolatileProgressMonitor {
        /*
         * 🧪 Zadanie 9:
         * Zadeklaruj `volatile int progress = 0`. Wątek roboczy 10 razy (co 50ms)
         * zwiększa progress o 10. Osobny wątek-monitor co 20ms (limit 50 sprawdzeń)
         * wypisuje aktualną wartość progress. Zakończ oba wątki bounded joinem (5s)
         * i wypisz finalną wartość progress.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_VisibilityVsJoinHappensBefore {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj dwa warianty: (a) wątek inkrementujący pole volatile int
         * 100 000 razy, (b) wątek inkrementujący ZWYKŁE (nie-volatile) pole int
         * 100 000 razy. W obu przypadkach main woła bounded join(5s) i DOPIERO POTEM
         * odczytuje wynik. Wypisz oba wyniki i skomentuj (println), że po join()
         * oba są poprawne – różnica volatile ujawnia się tylko przy odczycie
         * W TRAKCIE działania drugiego wątku, a nie po jego zakończeniu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_VolatileFlagWithSynchronizedCounter {
        /*
         * 🧪 Zadanie 11:
         * Zadeklaruj `volatile boolean stopRequested = false` oraz zwykłe pole
         * `int syncCounter = 0` chronione blokiem synchronized. Wątek roboczy w pętli
         * `while (!stopRequested)` inkrementuje syncCounter wewnątrz synchronized
         * i śpi 1ms. Main po 300ms ustawia stopRequested = true, robi bounded join (5s)
         * i wypisuje finalną wartość syncCounter.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TwoWorkersSharedFlag {
        /*
         * 🧪 Zadanie 12:
         * Zadeklaruj jedno wspólne `volatile boolean running = true`. Uruchom 2 wątki
         * robocze, każdy zliczający własne, lokalne iteracje w pętli `while (running)`
         * (z górnym limitem bezpieczeństwa). Main po 250ms ustawia running = false,
         * bounded join (5s) obu wątków, po czym wypisz liczby iteracji obu wątków
         * (będą różne – to normalne, brak gwarancji synchronizacji liczby iteracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_VolatileStateMachine {
        /*
         * 🧪 Zadanie 13:
         * Zadeklaruj `volatile String state = "NEW"`. Wątek-kontroler ustawia kolejno
         * (co 100ms): "RUNNING", "STOPPING", "STOPPED". Wątek-obserwator w pętli
         * z limitem 100 000 sprawdzeń wypisuje KAŻDĄ zauważoną zmianę stanu (porównując
         * z ostatnio zaobserwowaną wartością) i kończy się, gdy zauważy "STOPPED".
         * Zrób bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_RaceConditionExperimentTable {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę runExperiment() uruchamiającą 4 wątki x 10 000 inkrementacji
         * na volatile int counter (reset przed każdym uruchomieniem) i zwracającą
         * rzeczywisty wynik. Wywołaj ją 5 razy, zbierz wyniki do tablicy/listy i wypisz
         * tabelę: numer próby, oczekiwany wynik, rzeczywisty wynik, czy wystąpiła
         * race condition (tak/nie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_SafetyLimitPreventsInfiniteLoop {
        /*
         * 🧪 Zadanie 15:
         * Uruchom wątek z pętlą `while (running && loops < SAFETY_LIMIT)` (SAFETY_LIMIT
         * np. 5_000_000), ale w main() CELOWO NIE ustawiaj running = false (symulacja
         * "zapomnianego" wyłączenia). Zrób bounded join (10s) i wypisz, czy wątek
         * zakończył się dzięki fladze, czy dzięki osiągnięciu limitu bezpieczeństwa
         * (loops == SAFETY_LIMIT).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PauseAndStopFlags {
        /*
         * 🧪 Zadanie 16:
         * Zadeklaruj dwie flagi: `volatile boolean paused = false` i
         * `volatile boolean stopped = false`. Wątek roboczy w pętli `while (!stopped)`:
         * jeśli paused, śpi 10ms i pomija pracę; w przeciwnym razie zwiększa lokalny
         * licznik. Main: po 100ms ustawia paused = true, po kolejnych 100ms
         * paused = false, po kolejnych 100ms stopped = true. Bounded join (5s),
         * wypisz finalny licznik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_VolatileVsSynchronizedCounter {
        /*
         * 🧪 Zadanie 17:
         * Uruchom dwa równoległe eksperymenty (4 wątki x 20 000 inkrementacji każdy):
         * jeden na `volatile int counterA` (bez synchronizacji operacji ++),
         * drugi na `int counterB` inkrementowanym wewnątrz bloku synchronized.
         * Wypisz oba wyniki obok siebie i skomentuj różnicę – przygotowanie do
         * Lesson17_AtomicClasses.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_VisibilityLatencyMeasurement {
        /*
         * 🧪 Zadanie 18:
         * Zadeklaruj `volatile long signalSentAt = 0` i `volatile long signalSeenAt = 0`.
         * Main zapisuje signalSentAt = System.nanoTime() i ustawia volatile flagę
         * sygnalizującą zmianę. Wątek-obserwator w pętli monitorującej (limit
         * 10 000 000 sprawdzeń) wykrywa zmianę i zapisuje signalSeenAt = System.nanoTime().
         * Po bounded join (5s) wypisz różnicę signalSeenAt - signalSentAt w mikrosekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ProducerFlagConsumerPolling {
        /*
         * 🧪 Zadanie 19:
         * Zadeklaruj `volatile int itemsAvailable = 0`. Producent 5 razy (co 100ms)
         * zwiększa itemsAvailable o losową wartość 1-3 (Random). Konsument w pętli
         * monitorującej (limit 1000 sprawdzeń) wypisuje każdą zauważoną zmianę wartości
         * itemsAvailable i kończy, gdy suma osiągnie oczekiwane minimum (np. >= 5).
         * Bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_VolatileVsSynchronizedSummaryTable {
        /*
         * 🧪 Zadanie 20:
         * Na podstawie eksperymentów z tej lekcji (4 wątki x 50 000 inkrementacji)
         * zbierz dane: czas wykonania (System.nanoTime()) i poprawność wyniku osobno
         * dla wersji z volatile int (bez synchronizacji ++) i wersji z int chronionym
         * przez synchronized. Wypisz sformatowaną tabelę porównawczą (czas, wynik
         * oczekiwany, wynik rzeczywisty, poprawność) dla obu wariantów.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WorkerPoolGracefulShutdown {
        /*
         * 🧪 Zadanie 21:
         * Utwórz ręcznie (bez ExecutorService) 5 wątków roboczych, każdy z pętlą
         * `while (!shutdownRequested)` (wspólne `volatile boolean shutdownRequested`),
         * wykonującą Thread.sleep(5) i zwiększającą WŁASNY, lokalny licznik prac.
         * Main po 300ms ustawia flagę, robi bounded join (5s) na wszystkich 5 wątkach,
         * a następnie sumuje i wypisuje łączną liczbę wykonanych "prac" ze wszystkich
         * wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiWorkerCoordinatedShutdown {
        /*
         * 🧪 Zadanie 22:
         * Uruchom 3 workery, każdy z WŁASNYM polem `volatile boolean alive = true`
         * ustawianym na false tuż przed zakończeniem pętli pracy. Main ustawia wspólne
         * `volatile boolean shutdownAll = true` po 250ms, a następnie w pętli monitorującej
         * (limit 100 000 sprawdzeń) czeka, aż WSZYSTKIE trzy flagi alive będą false,
         * mierząc i wypisując czas zamykania każdego workera osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_VisibilityBugStatistics {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj metodę measureShutdownIterations(boolean useVolatile), która
         * uruchamia wątek z pętlą liczącą iteracje do momentu ustawienia flagi stop
         * (volatile lub zwykłej – w zależności od parametru), z górnym limitem
         * bezpieczeństwa 50_000_000. Wywołaj ją po 5 razy dla obu wariantów, zbierz
         * liczby iteracji do tablic i wypisz statystyki (min, max, średnia) dla obu
         * wariantów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThreeStagePipelineWithFlags {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj 3-etapowy pipeline (Stage1, Stage2, Stage3) jako 3 osobne wątki.
         * Każdy etap ma własne `volatile boolean stageXDone`. Stage2 czeka (pętla
         * monitorująca z limitem) aż stage1Done będzie true, zanim rozpocznie pracę;
         * analogicznie Stage3 czeka na stage2Done. Main uruchamia wszystkie trzy,
         * robi bounded join (10s) i wypisuje kolejność zakończenia etapów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CancelableTaskWithVolatile {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj klasę CancelableTask implementującą Runnable, z metodą
         * cancel() ustawiającą `volatile boolean cancelled = true` i metodą run()
         * wykonującą pętlę liczenia liczb pierwszych (dopóki !cancelled, z limitem
         * bezpieczeństwa 10_000_000 sprawdzanych liczb) i zliczającą, ile liczb
         * pierwszych znaleziono. Uruchom zadanie w wątku, anuluj je po 200ms, bounded
         * join (5s), wypisz liczbę znalezionych liczb pierwszych przed anulowaniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_HeartbeatMonitor {
        /*
         * 🧪 Zadanie 26:
         * Wątek roboczy 5 razy (co 50ms) aktualizuje `volatile long lastHeartbeat =
         * System.currentTimeMillis()`, po czym ustawia `volatile boolean workerDone = true`.
         * Osobny wątek-monitor co 20ms (dopóki !workerDone, z limitem bezpieczeństwa)
         * sprawdza różnicę między aktualnym czasem a lastHeartbeat i wypisuje status
         * "OK" (różnica < 200ms) lub "STALE" (różnica >= 200ms). Bounded join obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CombinedVolatileAndSynchronizedStats {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj klasę Statistics z polem `volatile boolean collecting = true`
         * oraz synchronized metodami increment() i getCount() operującymi na prywatnym
         * int count. Uruchom 6 wątków, z których każdy w pętli `while (collecting)`
         * woła increment(). Main po 300ms ustawia collecting = false, robi bounded
         * join (5s) na wszystkich wątkach i wypisuje finalny getCount() (powinien być
         * w pełni poprawny dzięki synchronized, mimo że collecting jest tylko flagą
         * odczytywaną w pętli).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RaceConditionStressTest {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę runExperiment(int threadsCount, int iterations) zwracającą
         * boolean – czy wystąpiła race condition na volatile int counter (bez
         * synchronizacji ++) dla podanej liczby wątków i iteracji. Wywołaj ją 10 razy
         * z rosnącą liczbą wątków (2, 4, 6, 8, ... 20) przy stałej liczbie iteracji
         * (10 000), zbierz wyniki do tabeli (liczba wątków, oczekiwany wynik,
         * rzeczywisty wynik, race condition tak/nie) i wypisz, w ilu z 10 uruchomień
         * wystąpiła race condition.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TwoPhaseBarrierWithPolling {
        /*
         * 🧪 Zadanie 29:
         * Zasymuluj prostą barierę dwufazową dla 4 wątków (bez CyclicBarrier – to
         * temat Lesson20_Synchronizers). Każdy wątek wykonuje "fazę 1" (Thread.sleep
         * losowy 10-50ms), potem zwiększa wspólny licznik arrivedCount wewnątrz bloku
         * synchronized, po czym CZEKA (pętla monitorująca z limitem, odczytująca
         * `volatile int arrivedCount` LUB synchronized getter) aż arrivedCount == 4,
         * zanim przejdzie do "fazy 2" (println). Wypisz kolejność wejścia w fazę 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullWorkerLifecycleDemo {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z lekcji: klasa Worker implementująca Runnable, z polami
         * `volatile boolean running = true` i `volatile String state` (kolejno "NEW",
         * "RUNNING", "STOPPING", "STOPPED"), metodą run() aktualizującą state i pracującą
         * dopóki running, oraz metodą stop() ustawiającą running = false. Main uruchamia
         * 3 workery, w osobnym wątku-monitorze (limit sprawdzeń) wypisuje każdą zmianę
         * stanu każdego workera, zatrzymuje wszystkie po 300ms, robi bounded join (5s)
         * i wypisuje finalne stany wszystkich workerów.
         */
        public static void main(String[] args) { }
    }
}
