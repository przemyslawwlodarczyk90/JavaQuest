package com.example.javaquest._05_multithreading.Lesson28_Interrupt;

public class _Exercises_Lesson28_Interrupt {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicInterruptLoop {
        /*
         * 🧪 Zadanie 1:
         * Utwórz wątek z pętlą while (!Thread.currentThread().isInterrupted()) { }.
         * Uśpij main na 100 ms, wywołaj interrupt() na wątku, poczekaj join(1000)
         * i wypisz komunikat potwierdzający, że wątek zauważył flagę i się zakończył.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ThreadInterruptedClearsFlag {
        /*
         * 🧪 Zadanie 2:
         * Na wątku main wywołaj Thread.currentThread().interrupt(), a następnie
         * dwukrotnie wywołaj statyczne Thread.interrupted(). Wypisz wynik obu
         * wywołań i wyjaśnij w komentarzu/println, dlaczego drugie zwraca false
         * (metoda statyczna CZYŚCI flagę przy odczycie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_IsInterruptedDoesNotClearFlag {
        /*
         * 🧪 Zadanie 3:
         * Utwórz wątek, wywołaj na nim interrupt(), a następnie na obiekcie tego
         * wątku wywołaj isInterrupted() TRZY razy pod rząd. Wypisz wszystkie trzy
         * wyniki i pokaż, że instancyjna metoda isInterrupted() NIE czyści flagi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_SleepInterruptedExceptionCatch {
        /*
         * 🧪 Zadanie 4:
         * Utwórz wątek wykonujący Thread.sleep(2000) wewnątrz try-catch. Po 300 ms
         * wywołaj na nim interrupt(). W bloku catch (InterruptedException e)
         * przywróć flagę (Thread.currentThread().interrupt()) i wypisz komunikat
         * o przechwyceniu wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JoinInterrupted {
        /*
         * 🧪 Zadanie 5:
         * Utwórz wątek roboczy wykonujący Thread.sleep(3000). W main wywołaj
         * worker.join() (bez timeoutu) na wątku pomocniczym, który czeka na
         * workera, a następnie przerwij TEN wątek pomocniczy przez interrupt()
         * po 200 ms. Złap InterruptedException i wypisz, że join() został przerwany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WaitInterrupted {
        /*
         * 🧪 Zadanie 6:
         * Utwórz wspólny obiekt-lock. Wątek wchodzi w synchronized(lock) i wywołuje
         * lock.wait(3000). Po 200 ms z main wywołaj interrupt() na tym wątku.
         * Złap InterruptedException wewnątrz wątku, przywróć flagę i wypisz
         * komunikat, że oczekiwanie zostało przerwane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BasicGracefulShutdown {
        /*
         * 🧪 Zadanie 7:
         * Odtwórz wzorzec z lekcji: wątek w pętli while (!isInterrupted())
         * wypisuje numer iteracji i robi Thread.sleep(150). Po 500 ms main
         * wysyła interrupt() i czeka join(2000). Wypisz liczbę wykonanych iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_InterruptDoesNotStopBusyLoop {
        /*
         * 🧪 Zadanie 8:
         * Utwórz wątek z czystą pętlą liczącą (np. sumowanie liczb w int) BEZ
         * sprawdzania isInterrupted(). Wywołaj na nim interrupt() po 100 ms
         * i sprawdź (isInterrupted()) że flaga jest ustawiona, mimo że wątek
         * nadal działa – wypisz wniosek, że sam interrupt() nie zatrzymuje wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_InterruptBeforeStart {
        /*
         * 🧪 Zadanie 9:
         * Utwórz wątek (jeszcze niewystartowany), wywołaj na nim interrupt(),
         * a dopiero potem start(). Sprawdź w pętli roboczej wątku, czy od razu
         * widzi isInterrupted() == true i kończy się natychmiast. Wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CountIterationsBeforeAndAfterInterrupt {
        /*
         * 🧪 Zadanie 10:
         * Utwórz wątek zliczający iteracje (AtomicInteger) w pętli while
         * (!isInterrupted()) z Thread.sleep(50). Po 400 ms wywołaj interrupt(),
         * poczekaj join(1000) i wypisz, ile iteracji zdążył wykonać wątek.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_InterruptViaCapturedThreadReference {
        /*
         * 🧪 Zadanie 11:
         * Wewnątrz Runnable pobierz Thread.currentThread() i zapisz w polu
         * volatile poza lambdą (np. AtomicReference<Thread>). Z main, po starcie
         * wątku, odczytaj tę referencję i wywołaj na niej interrupt(). Wypisz
         * potwierdzenie zakończenia wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FlagPropagationThroughNestedCalls {
        /*
         * 🧪 Zadanie 12:
         * Napisz metodę innerSleep() łapiącą InterruptedException i przywracającą
         * flagę, wywoływaną z metody outerLoop() sprawdzającej isInterrupted()
         * w pętli. Uruchom to w wątku, przerwij go po 300 ms i wypisz, że
         * outerLoop() poprawnie zauważa przywróconą flagę mimo że wyjątek
         * został złapany "głębiej".
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_InterruptMultipleThreads {
        /*
         * 🧪 Zadanie 13:
         * Utwórz listę 5 wątków, każdy w pętli while (!isInterrupted()) ze
         * sleep(100). Po 400 ms wywołaj interrupt() na WSZYSTKICH, poczekaj
         * join(1000) na każdym i wypisz, że wszystkie 5 zakończyło się czysto.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WatchdogSendsInterruptAfterTimeout {
        /*
         * 🧪 Zadanie 14:
         * Utwórz wątek roboczy liczący iteracje w nieskończonej pętli ze sleep(100)
         * oraz osobny wątek-watchdog, który po 700 ms wywołuje interrupt() na
         * wątku roboczym. Po zakończeniu wypisz liczbę wykonanych iteracji
         * i potwierdź, że watchdog skutecznie zatrzymał pracownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_InterruptibleTaskInterfacePattern {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj interfejs functional InterruptibleTask { void run() throws
         * InterruptedException; } i metodę pomocniczą runInLoop(InterruptibleTask
         * task, int maxIterations), która w pętli (do maxIterations lub do
         * przerwania) wywołuje task.run() i łapie InterruptedException, kończąc
         * pętlę. Przetestuj z zadaniem robiącym Thread.sleep(100).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ThreadInterruptedBugVsFix {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj BŁĘDNĄ wersję pętli roboczej, która zamiast
         * isInterrupted() używa statycznego Thread.interrupted() jako warunku
         * pętli (co czyści flagę przy każdym sprawdzeniu) – pokaż, że w pewnych
         * warunkach wątek "gubi" informację o przerwaniu. Następnie napraw kod,
         * używając isInterrupted(), i porównaj zachowanie obu wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WaitInSynchronizedWithRestore {
        /*
         * 🧪 Zadanie 17:
         * W pętli while (!isInterrupted()) wątek wchodzi w synchronized(lock)
         * i wywołuje lock.wait(500) (czekając na notify, którego nigdy nie
         * będzie). Po 250 ms main wysyła interrupt(). W catch przywróć flagę
         * i zakończ pętlę – wypisz, że wątek zakończył się czysto mimo bycia
         * "w środku" wait().
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NestedThreadJoinInterrupt {
        /*
         * 🧪 Zadanie 18:
         * Utwórz wątek zewnętrzny "Outer", który tworzy i startuje wątek
         * wewnętrzny "Inner" (śpiący 3000 ms), po czym wywołuje inner.join().
         * Z main przerwij wątek "Outer" po 300 ms. Zaobserwuj (i wypisz),
         * że Outer dostaje InterruptedException z join(), a Inner nadal działa
         * niezależnie, chyba że jawnie go też przerwiesz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BeltAndSuspendersCancellation {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj anulowanie współpracujące z DWOMA mechanizmami naraz:
         * volatile boolean stopRequested ORAZ interrupt(). Pętla robocza
         * sprawdza oba (!stopRequested && !isInterrupted()), a Thread.sleep()
         * łapie InterruptedException. Przetestuj ustawiając tylko stopRequested,
         * potem tylko interrupt(), i wypisz który mechanizm zadziałał w każdym przypadku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FutureCancelTrueInterrupts {
        /*
         * 🧪 Zadanie 20:
         * Użyj ExecutorService (poznanego w Lekcji 21) i submituj Callable<Void>
         * wykonujący pętlę ze sleep(100) sprawdzającą isInterrupted(). Po 400 ms
         * wywołaj future.cancel(true) i wypisz, że zadanie zakończyło się
         * w odpowiedzi na wywołany przez cancel(true) interrupt.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TaskManagerBulkInterrupt {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj klasę TaskManager potrafiącą uruchomić N wątków roboczych
         * (np. 4), a po zadanym czasie (startAll(), potem stopAll() wywołujące
         * interrupt() na wszystkich i join(timeout)) zwrócić raport: ile wątków
         * zakończyło się w limicie czasu, a ile nie zdążyło (isAlive() == true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_UninterruptibleSegmentThenCheckpoint {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj wątek z "nieprzerywalnym" segmentem kodu: pętlą liczącą przez
         * ustaloną liczbę iteracji BEZ sprawdzania isInterrupted(), po której
         * następuje "checkpoint" sprawdzający flagę. Wywołaj interrupt() w trakcie
         * segmentu nieprzerywalnego i wypisz, że wątek zauważa przerwanie
         * dopiero przy checkpointcie, a nie natychmiast.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ProducerConsumerCancelViaInterrupt {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj producenta i konsumenta połączonych przez BlockingQueue<Integer>.
         * Konsument blokuje się na queue.take(). Po wyprodukowaniu kilku elementów
         * wywołaj interrupt() na konsumencie w trakcie oczekiwania na take().
         * Złap InterruptedException, przywróć flagę i zakończ konsumenta czysto –
         * wypisz, ile elementów zdążył przetworzyć.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_DeadlineAndInterruptCombinedSleep {
        /*
         * 🧪 Zadanie 24:
         * Napisz metodę sleepUntilDeadlineOrInterrupt(long deadlineMillis),
         * śpiącą w małych krokach (np. po 50 ms) i sprawdzającą PRZED każdym
         * krokiem zarówno upływ czasu (System.currentTimeMillis() >= deadline)
         * jak i isInterrupted(), przerywając pętlę na to, co nastąpi pierwsze.
         * Przetestuj oba scenariusze zakończenia (czasem i przerwaniem).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_InterruptStormIdempotency {
        /*
         * 🧪 Zadanie 25:
         * Utwórz wątek w pętli roboczej i wywołaj na nim interrupt() PIĘĆ razy
         * pod rząd bez opóźnień. Wypisz, że flaga zachowuje się spójnie (wątek
         * kończy się dokładnie raz, bez błędów), mimo wielokrotnego wywołania interrupt().
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FiveWorkersShutdownReport {
        /*
         * 🧪 Zadanie 26:
         * Uruchom 5 wątków roboczych, każdy zliczający własne iteracje
         * (AtomicInteger) w pętli ze sleep(80). Po 600 ms wywołaj interrupt()
         * na wszystkich i join(1000). Wypisz raport: nazwa wątku, liczba
         * wykonanych iteracji, całkowity czas trwania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ScheduledExecutorInterruptCancellation {
        /*
         * 🧪 Zadanie 27:
         * Użyj ScheduledExecutorService (Lekcja 23) do zaplanowania zadania
         * cyklicznego (scheduleAtFixedRate) co 100 ms, wypisującego kolejny numer
         * cyklu. Po 500 ms anuluj zadanie przez future.cancel(true) i zamknij
         * executor (shutdown+awaitTermination). Wypisz liczbę wykonanych cykli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_RetryWithInterruptCheck {
        /*
         * 🧪 Zadanie 28:
         * Napisz metodę retryWithInterruptCheck(int maxRetries, long delayMs,
         * Supplier<Boolean> operation), która próbuje wykonać operację do skutku
         * lub do maxRetries razy, ale PRZERYWA się wcześniej (z przywróceniem
         * flagi), jeśli w trakcie Thread.sleep(delayMs) między próbami zostanie
         * przerwana. Przetestuj z operacją zawsze zwracającą false i przerwaniem
         * wątku w połowie prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CancellablePipelineOfThreads {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj potok 3 wątków (Etap1 -> Etap2 -> Etap3) połączonych dwiema
         * BlockingQueue, gdzie każdy etap w pętli robi take()/put() ze sprawdzaniem
         * isInterrupted() i łapaniem InterruptedException. Z kontrolera (main)
         * po 500 ms wywołaj interrupt() na wszystkich trzech etapach na raz
         * i wypisz raport potwierdzający czyste zamknięcie całego potoku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_InterruptibleWorkerLifecycleClass {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj klasę InterruptibleWorker z metodami start(), requestStop()
         * (wywołuje interrupt() na wewnętrznym wątku) oraz awaitTermination(long
         * timeoutMs) (join z timeoutem, zwraca boolean czy zdążył). Utwórz 3
         * instancje wykonujące różne zadania (pętla ze sleep o różnych odstępach),
         * uruchom je, po chwili wywołaj requestStop() na wszystkich i wypisz
         * pełny raport zamknięcia (czy każdy worker zdążył się zakończyć w limicie).
         */
        public static void main(String[] args) { }
    }
}
