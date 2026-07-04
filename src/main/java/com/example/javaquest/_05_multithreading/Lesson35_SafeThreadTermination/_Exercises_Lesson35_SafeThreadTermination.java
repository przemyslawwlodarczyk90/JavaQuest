package com.example.javaquest._05_multithreading.Lesson35_SafeThreadTermination;

public class _Exercises_Lesson35_SafeThreadTermination {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FlagCounterWorker {
        /*
         * 🧪 Zadanie 1:
         * Zaimplementuj worker z polem volatile boolean running, zwiększający licznik
         * w pętli while(running). Uruchom go, po 100 ms wywołaj shutdown() (ustawienie
         * flagi na false), zaczekaj join(1000) i wypisz liczbę wykonanych iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FlagOnlyDelay {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj worker, który w pętli while(running) wywołuje Thread.sleep(50).
         * Zamknij go WYŁĄCZNIE flagą (bez interrupt()) i zmierz, ile czasu zajęło rzeczywiste
         * zakończenie wątku - powinno być opóźnione o czas trwania bieżącego sleep().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_FlagPlusInterruptFast {
        /*
         * 🧪 Zadanie 3:
         * Weź workera z Zadania 2, ale tym razem shutdown() ustawia flagę ORAZ wywołuje
         * thread.interrupt(). Zmierz czas zakończenia wątku i porównaj z wynikiem
         * z Zadania 2 - powinien być znacznie krótszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GracefulShutdownLongSleep {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj kompletny wzorzec graceful shutdown (flaga=false, interrupt(),
         * join(timeout)) dla workera usypiającego się na 3000 ms. Zweryfikuj, że całkowity
         * czas oczekiwania w main jest krótki, a nie bliski 3000 ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_RestoreInterruptStatus {
        /*
         * 🧪 Zadanie 5:
         * W bloku catch(InterruptedException) workera ustaw flagę running=false ORAZ
         * wywołaj Thread.currentThread().interrupt(), aby przywrócić status przerwania.
         * Po zakończeniu wątku wypisz komentarz potwierdzający, że status nie został utracony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CountdownWithEarlyShutdown {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj worker odliczający od 10 do 0 z Thread.sleep(200) między liczbami.
         * Wywołaj shutdown() po ok. 3 odliczeniach i zweryfikuj, że worker zatrzymał się
         * wcześniej, a nie doszedł do 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ThreeIndependentBusyWorkers {
        /*
         * 🧪 Zadanie 7:
         * Utwórz 3 niezależne instancje workera z flagą volatile boolean running (bez sleep,
         * jak BusyWorker z lekcji). Uruchom wszystkie, zamknij mniej więcej równocześnie
         * po 100 ms, zaczekaj join(2000) na każdy i wypisz liczbę iteracji każdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ShutdownReturnsSuccessFlag {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę gracefulShutdown(Thread t, Runnable shutdownAction) wykonującą
         * shutdownAction.run() i t.join(1000), zwracającą true jeśli wątek zakończył się
         * w limicie czasu. Przetestuj na zwykłym (niezawieszonym) workerze i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NaturalEndVsForcedShutdown {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj worker kończący pętlę albo gdy running=false, albo po osiągnięciu
         * 1 000 000 iteracji - co nastąpi pierwsze. Uruchom go raz pozwalając dobiec
         * naturalnie do końca, a raz wywołując shutdown() wcześnie - porównaj liczby iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_IsShutdownCleanlyHelper {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę isShutdownCleanly(Thread t, Runnable shutdownAction) wywołującą
         * shutdownAction.run(), potem t.join(2000), zwracającą !t.isAlive(). Przetestuj
         * ją na workerze zbliżonym do SleepyWorker z lekcji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultipleShortSleepsInterrupted {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj wersję SleepyWorker śpiącą w pętli 5 razy po 500 ms (zamiast jednego
         * długiego snu). Wywołaj graceful shutdown po ok. 1 śnie i zweryfikuj, że worker
         * zatrzymuje się szybko dzięki interrupt(), zamiast czekać na wszystkie 5 snów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BlockingQueueRequiresInterrupt {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj workera blokującego się na queue.take() (BlockingQueue) zamiast
         * sleep(). Pokaż, że sama flaga running nie wystarczy (take() blokuje), a dopiero
         * interrupt() budzi wątek (InterruptedException) i pozwala mu się zakończyć.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SharedAtomicShutdownFlag {
        /*
         * 🧪 Zadanie 13:
         * Utwórz 2 workery (styl busy-loop) współdzielące jeden AtomicBoolean shutdownRequested.
         * Uruchom oba, ustaw flagę raz i zaczekaj join(2000) na oba - zweryfikuj,
         * że obydwa się zatrzymały.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CleanupInFinallyBothPaths {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj workera, który w bloku finally pętli run() wypisuje "zamykam zasób".
         * Sprawdź, że komunikat pojawia się zarówno gdy pętla kończy się naturalnie
         * (osiągnięty limit iteracji), jak i gdy zostaje przerwana przez shutdown().
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_InterruptStatusPropagationCheck {
        /*
         * 🧪 Zadanie 15:
         * W workerze złap InterruptedException, wywołaj Thread.currentThread().interrupt(),
         * a PO zakończeniu pętli (przed końcem run()) sprawdź i wypisz
         * Thread.currentThread().isInterrupted() - powinno wynosić true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_StoppableWorkerInterface {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj interfejs StoppableWorker z metodami shutdown() i getThread(). Zaimplementuj
         * go dla 4 workerów usypiających się cyklicznie, napisz metodę shutdownAll(List<StoppableWorker>)
         * zamykającą wszystkie i zliczającą, ile zatrzymało się poprawnie w limicie czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TimingPrecisionComparison {
        /*
         * 🧪 Zadanie 17:
         * Zmierz dokładny czas graceful shutdown (flaga+interrupt) workera śpiącego 2000 ms
         * oraz czas zamknięcia samą flagą (bez interrupt()) innego workera również śpiącego
         * 2000 ms. Wypisz oba czasy obok siebie, uwypuklając różnicę ok. 2000 ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_WaitNotifyWithTimeoutInterrupt {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj workera blokującego się w synchronized(lock) { lock.wait(2000); }.
         * Zademonstruj dwa scenariusze: przerwanie go metodą interrupt() (budzi się od razu
         * z InterruptedException) oraz naturalne zakończenie wait(2000) po upływie czasu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GracefulWorkerEncapsulated {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj klasę GracefulWorker (implements Runnable) z metodami start(),
         * shutdown() i awaitTermination(long millis), ukrywającą cały wzorzec flaga+interrupt+join
         * za czystym API. Przetestuj: utwórz, uruchom, poczekaj 100 ms, zamknij i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PoolOfGracefulWorkers {
        /*
         * 🧪 Zadanie 20:
         * Utwórz 5 instancji GracefulWorker (z Zadania 19), uruchom wszystkie razem,
         * zamknij wszystkie w pętli i zweryfikuj, że WSZYSTKIE kończą się w łącznym
         * budżecie czasu poniżej 3 sekund.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_PoisonPillVsInterrupt {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj producenta/konsumenta na BlockingQueue, gdzie shutdown() konsumenta
         * odbywa się przez wstawienie specjalnej wartości wartowniczej ("poison pill")
         * zamiast interrupt(). Porównaj (w komentarzu/println) to podejście z podejściem
         * opartym na interrupt() z tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiStageCoordinatedShutdown {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj 3 połączone workery (stage1 -> kolejka -> stage2 -> kolejka -> stage3),
         * każdy z własną flagą running. Zaimplementuj shutdownAll() zatrzymujący najpierw
         * stage1, czekający na opróżnienie kolejki, potem stage2, na końcu stage3. Wypisz
         * kolejność zamknięć i porównaj liczbę wyprodukowanych z liczbą finalnie skonsumowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LastResortForceKillPattern {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę shutdown(Thread t) próbującą najpierw graceful (flaga+interrupt+join(500)),
         * a jeśli wątek nadal żyje po tym czasie, wypisującą ostrzeżenie "worker nie zatrzymał
         * się w limicie". Przetestuj na poprawnie zachowującym się workerze i wypisz,
         * którą ścieżką poszło zamknięcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RepeatingTaskFlagBased {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj RepeatingTask uruchamiającą przekazany Runnable co 100 ms w pętli
         * while(running). Uruchom ją na ok. 500 ms, zamknij flagą i wypisz, ile razy
         * wewnętrzne zadanie zdążyło się wykonać (powinno być ok. 5 razy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CooperativeProgressReporting {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj workera przetwarzającego listę 20 "elementów" (sleep 30 ms na element,
         * sprawdzając flagę running między elementami). Po przetworzeniu ok. 10 elementów
         * poproś o zamknięcie i zweryfikuj, że finalna liczba przetworzonych elementów
         * jest bliska 10, a nie 20.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ConsistentStateUnderShutdown {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj workera modyfikującego wspólną listę parami (dodaje 2 elementy na
         * raz reprezentujące "transakcję"), sprawdzającego flagę running WYŁĄCZNIE między
         * parami. Po wielu wymuszonych, wczesnych zamknięciach zweryfikuj, że lista zawsze
         * ma parzystą długość (spójny stan) - NIE używaj Thread.stop().
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ShutdownStatisticsReport {
        /*
         * 🧪 Zadanie 27:
         * Uruchom 5 nazwanych workerów o różnym obciążeniu, zamknij je wszystkie
         * gracefully i zbuduj raport Map<String, Long> (nazwa -> liczba przetworzonych
         * iteracji/elementów). Wypisz raport w formie czytelnej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_WatchdogAutoShutdown {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj wątek-watchdog (demon), który monitoruje workera i automatycznie
         * wywołuje jego graceful shutdown, jeśli przekroczy maksymalny czas działania
         * (np. 300 ms). Przetestuj z workerem, który bez watchdoga działałby znacznie
         * dłużej (np. 50 iteracji po 100 ms = 5 s) i zweryfikuj, że watchdog przerywa go
         * około 300 ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ProducerConsumerFullShutdown {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj pełny pipeline producent-konsument (BlockingQueue) przetwarzający stałą
         * partię 50 liczb, z gracefully zamykanymi ORAZ producentem, ORAZ konsumentem
         * (flaga+interrupt+poison pill łącznie). Zweryfikuj, że pipeline zawsze kończy się
         * w ok. 2 sekundy i wypisz sumę policzoną przez konsumenta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_RestartableGracefulService {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj klasę GracefulService z metodami start(), stop() (graceful:
         * flaga+interrupt+ograniczony join) i isRunning(). Napisz test uruchamiający,
         * zatrzymujący i weryfikujący stan (isRunning()==false, wątek nie żyje) po zatrzymaniu,
         * powtórzony 3 razy, aby potwierdzić że usługę można bezpiecznie restartować.
         */
        public static void main(String[] args) { }
    }
}
