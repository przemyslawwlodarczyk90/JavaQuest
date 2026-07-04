package com.example.javaquest._05_multithreading.Lesson06_ThreadLifecycleAndStates;

public class _Exercises_Lesson06_ThreadLifecycleAndStates {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_StateRightAfterCreation {
        /*
         * 🧪 Zadanie 1:
         * Utwórz wątek (bez wywoływania start()) i wypisz jego getState() –
         * oczekiwana wartość to NEW.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_StateAfterStart {
        /*
         * 🧪 Zadanie 2:
         * Utwórz wątek z pętlą roboczą (licząca sumę do 100_000_000), wywołaj
         * start() i OD RAZU (bez sleep) sprawdź getState() – prawdopodobnie
         * RUNNABLE. Dociągnij join(5000) na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_StateAfterTermination {
        /*
         * 🧪 Zadanie 3:
         * Utwórz krótki wątek (pusta lambda), wywołaj start()+join(2000) i
         * sprawdź getState() – oczekiwana wartość to TERMINATED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TimedWaitingDuringSleep {
        /*
         * 🧪 Zadanie 4:
         * Utwórz wątek wykonujący Thread.sleep(400). Main po krótkim sleep(50)
         * sprawdza getState() tego wątku – oczekiwana wartość to TIMED_WAITING.
         * Dociągnij join(2000) na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WaitingDuringInfiniteJoin {
        /*
         * 🧪 Zadanie 5:
         * Utwórz wątek A wywołujący t.join() (bez timeoutu) na innym, dłużej
         * trwającym wątku B (sleep 500ms). Main po krótkim opóźnieniu sprawdza
         * getState() wątku A – oczekiwana wartość to WAITING. Dociągnij join(2000)
         * na obu wątkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_BlockedOnSynchronized {
        /*
         * 🧪 Zadanie 6:
         * Analogicznie do przykładu z lekcji: dwa wątki walczą o wspólny monitor
         * (synchronized). Pierwszy trzyma go przez sleep(300) wewnątrz
         * synchronized, drugi próbuje wejść. Sprawdź getState() drugiego wątku
         * w trakcie (oczekiwane BLOCKED). Dociągnij join(2000) na obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AllStatesEnumValues {
        /*
         * 🧪 Zadanie 7:
         * Wypisz wszystkie wartości enuma Thread.State (Thread.State.values())
         * w pętli, po jednej wartości na linię.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IllegalThreadStateOnRestart {
        /*
         * 🧪 Zadanie 8:
         * Utwórz i zakończ wątek (start()+join()). Spróbuj wywołać start()
         * drugi raz, przechwyć IllegalThreadStateException i wypisz getState()
         * PRZED próbą oraz informację o złapanym wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ParkAndUnpark {
        /*
         * 🧪 Zadanie 9:
         * Utwórz wątek wywołujący LockSupport.park(). Main po sleep(100) sprawdza
         * getState() (oczekiwane WAITING), następnie wywołuje
         * LockSupport.unpark(watek) i join(2000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_StateSequenceCollector {
        /*
         * 🧪 Zadanie 10:
         * Dla jednego wątku (sleep 200ms) zbierz w main sekwencję stanów w kilku
         * punktach w czasie (zaraz po utworzeniu, po start(), po krótkim
         * opóźnieniu, po join()) do listy i wypisz ją na końcu jako "historię
         * stanów".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WaitNotifyStateTransition {
        /*
         * 🧪 Zadanie 11:
         * Analogicznie do przykładu z lekcji (Object.wait()/notify()), zaimplementuj
         * własny wariant z innym obiektem-monitorem i innym komunikatem. Sprawdź
         * getState() wątku przed i po wywołaniu notify().
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TimedWaitViaWaitWithTimeout {
        /*
         * 🧪 Zadanie 12:
         * Utwórz wątek wywołujący lock.wait(500) (wait z timeoutem) wewnątrz
         * synchronized. Main sprawdza getState() w trakcie oczekiwania
         * (oczekiwane TIMED_WAITING). Dociągnij join(2000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MultipleThreadsBlockedOnSameMonitor {
        /*
         * 🧪 Zadanie 13:
         * 3 wątki próbują wejść do TEGO SAMEGO synchronized bloku, trzymanego
         * przez wątek "właściciela" przez 400ms. Sprawdź getState() wszystkich 3
         * "oczekujących" w trakcie (oczekiwane BLOCKED dla wszystkich). Dociągnij
         * join(2000) dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_StateMonitoringLoop {
        /*
         * 🧪 Zadanie 14:
         * Dla wątku wykonującego dłuższą, mieszaną pracę (sleep(100) + pętla
         * licząca + sleep(100)), w main w pętli co 30ms (bezpiecznik max 20
         * iteracji) odczytuj i zbieraj getState() do listy, aż wątek osiągnie
         * TERMINATED. Wypisz zebraną sekwencję stanów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ParkNanosTimedWaiting {
        /*
         * 🧪 Zadanie 15:
         * Utwórz wątek wywołujący LockSupport.parkNanos(500_000_000L) (0.5s).
         * Main sprawdza getState() w trakcie (oczekiwane TIMED_WAITING). Dociągnij
         * join(2000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LifecycleDiagramValidator {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę isValidTransition(Thread.State from, Thread.State to)
         * sprawdzającą (na podstawie diagramu z lekcji), czy dane przejście jest
         * DOZWOLONE (np. NEW -> RUNNABLE: tak, BLOCKED -> WAITING: nie). Przetestuj
         * dla kilku par stanów i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TwoThreadsDifferentStatesSameTime {
        /*
         * 🧪 Zadanie 17:
         * Uruchom jednocześnie 3 wątki: jeden śpiący (TIMED_WAITING), jeden
         * czekający na monitor zajęty przez czwarty, pomocniczy wątek (BLOCKED),
         * jeden wykonujący pętlę obliczeniową (RUNNABLE). Po ustalonym opóźnieniu
         * sprawdź i wypisz stan wszystkich trzech naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NotifyAllWakesMultipleWaiters {
        /*
         * 🧪 Zadanie 18:
         * 3 wątki wywołują wspólne lock.wait() na tym samym obiekcie monitora.
         * Main po chwili sprawdza, że wszystkie są WAITING, następnie wywołuje
         * notifyAll() (nie notify()) i sprawdza (przez join(2000) każdego), że
         * wszystkie kończą się TERMINATED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_StateBasedRetryUntilRunnable {
        /*
         * 🧪 Zadanie 19:
         * Utwórz wątek i wystartuj go. W pętli (max 100 iteracji, bez lub z bardzo
         * krótkim sleep) sprawdzaj getState() aż przestanie być NEW – zlicz, ile
         * iteracji to zajęło i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CombinedBlockedWaitingTimedWaitingDemo {
        /*
         * 🧪 Zadanie 20:
         * Zaprojektuj scenariusz z 3 wątkami demonstrujący WSZYSTKIE TRZY stany
         * oczekiwania jednocześnie (jeden BLOCKED na synchronized, jeden WAITING
         * na wait() bez timeoutu, jeden TIMED_WAITING na sleep). Wypisz stan
         * każdego w jednym punkcie czasu i wyjaśnij różnicę w komentarzu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullLifecycleLogger {
        /*
         * 🧪 Zadanie 21:
         * Stwórz pomocniczy wątek "obserwator" monitorujący w tle (pętla z
         * krótkim sleep, bezpiecznik max iteracji) INNY wątek, zapisujący KAŻDĄ
         * zmianę stanu (porównując z poprzednio odczytanym) do listy. Użyj go
         * dla wątku wykonującego sekwencję: sleep(100) -> praca -> wait(200) na
         * monitorze -> zakończenie. Wypisz pełną, zaobserwowaną sekwencję stanów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_StarvationlikeManyBlockedThreads {
        /*
         * 🧪 Zadanie 22:
         * 5 wątków próbuje wejść do tego samego synchronized bloku, trzymanego
         * przez "właściciela" przez 500ms. Sprawdź (przez getState() w pętli
         * monitorującej z bezpiecznikiem), ile z nich jest BLOCKED w danym
         * momencie, a po zwolnieniu monitora – że wszystkie kończą się TERMINATED
         * (join(3000) na każdym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TimedWaitingRaceBetweenSleepAndParkNanos {
        /*
         * 🧪 Zadanie 23:
         * Uruchom 2 wątki: jeden w sleep(300), drugi w
         * LockSupport.parkNanos(300_000_000L). Sprawdź, że OBA raportują
         * TIMED_WAITING (mimo różnych mechanizmów leżących u podstaw).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WaitingChainAcrossThreeThreads {
        /*
         * 🧪 Zadanie 24:
         * Wątek C czeka (join() bez timeoutu) na wątek B, wątek B czeka (join()
         * bez timeoutu) na wątek A, wątek A wykonuje krótką pracę (sleep 300ms).
         * Sprawdź w trakcie, że B i C są WAITING, a po zakończeniu A cały łańcuch
         * się rozwiązuje (wszystkie TERMINATED po join(3000) w main).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_StateHistogramUnderLoad {
        /*
         * 🧪 Zadanie 25:
         * Uruchom 10 wątków o zróżnicowanych zachowaniach (część sleep, część
         * synchronized, część czysta praca CPU). W pętli monitorującej
         * (bezpiecznik max 30 iteracji co 30ms) zbierz histogram
         * (Map<Thread.State,Integer>) pokazujący, ile wątków jest w każdym
         * stanie w danym momencie – wypisz histogram z kilku "migawek" czasowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_RestartAttemptsCounterAcrossManyThreads {
        /*
         * 🧪 Zadanie 26:
         * Utwórz 5 zakończonych wątków (start()+join() każdy). Dla każdego
         * spróbuj wywołać start() ponownie w try-catch, policz łączną liczbę
         * złapanych IllegalThreadStateException (oczekiwane 5) i zweryfikuj,
         * że getState() każdego pozostaje TERMINATED mimo próby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_DeadlockFreeBlockedDemoWithTimeout {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj scenariusz z 2 wątkami próbującymi wejść do 2 RÓŻNYCH
         * monitorów w TEJ SAMEJ kolejności (celowo UNIKAJĄC klasycznego deadlocku
         * – ten poznamy dopiero w dalszej lekcji, tu tylko obserwacja BLOCKED).
         * Zmierz i wypisz, że oba wątki kończą się poprawnie (join z timeoutem
         * jako bezpiecznik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_LifecycleAwareTaskScheduler {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj prosty "scheduler" uruchamiający 4 zadania o różnym
         * charakterze (sleep, wait/notify, praca CPU, natychmiastowe zakończenie)
         * i buduj końcowy raport z getState() każdego PO zakończeniu (wszystkie
         * powinny być TERMINATED) – potwierdź w raporcie, że żaden wątek nie
         * "utknął".
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CustomStateWatcherThread {
        /*
         * 🧪 Zadanie 29:
         * Stwórz osobny wątek "obserwator", który w pętli (bezpiecznik: max 50
         * iteracji co 20ms) monitoruje stan innego, przekazanego w konstruktorze
         * wątku roboczego, zliczając ile razy zaobserwował każdy ze stanów. Po
         * zakończeniu wątku roboczego (join) wypisz zliczone występowanie
         * każdego stanu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullLifecycleExperimentReport {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji: zaprojektuj eksperyment z 6 wątkami
         * reprezentującymi różne ścieżki cyklu życia (NEW->RUNNABLE->TERMINATED,
         * TIMED_WAITING via sleep, WAITING via wait/notify, BLOCKED via
         * synchronized, TIMED_WAITING via parkNanos, próba nielegalnego
         * restartu). Zbuduj i wypisz końcowy raport pokazujący dla każdego wątku
         * zaobserwowane stany w kolejności chronologicznej.
         */
        public static void main(String[] args) { }
    }
}
