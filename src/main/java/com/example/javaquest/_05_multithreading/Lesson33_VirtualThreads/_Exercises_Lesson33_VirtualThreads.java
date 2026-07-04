package com.example.javaquest._05_multithreading.Lesson33_VirtualThreads;

public class _Exercises_Lesson33_VirtualThreads {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SingleVirtualThreadStart {
        /*
         * 🧪 Zadanie 1:
         * Utwórz i uruchom pojedynczy wątek wirtualny metodą Thread.ofVirtual().start(runnable),
         * który wypisuje swoją nazwę oraz wynik Thread.currentThread().isVirtual().
         * Zaczekaj na jego zakończenie metodą join(1000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_UnstartedThenStart {
        /*
         * 🧪 Zadanie 2:
         * Utwórz wątek wirtualny metodą Thread.ofVirtual().name("MojWatek").unstarted(runnable),
         * a następnie osobno wywołaj start(). Wypisz nazwę wątku i isVirtual() wewnątrz runnable.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_PlatformVsVirtualCompare {
        /*
         * 🧪 Zadanie 3:
         * Utwórz jeden zwykły wątek platformowy (new Thread) i jeden wątek wirtualny,
         * oba wykonujące to samo zadanie wypisujące isVirtual(). Porównaj wyniki obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_VirtualExecutorFiveTasks {
        /*
         * 🧪 Zadanie 4:
         * Użyj Executors.newVirtualThreadPerTaskExecutor() w try-with-resources i prześlij
         * 5 zadań, z których każde wypisuje swój numer oraz nazwę bieżącego wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_HundredSleepingTasks {
        /*
         * 🧪 Zadanie 5:
         * Prześlij 100 zadań do wątków wirtualnych, z których każde śpi 10 ms i zwiększa
         * współdzielony AtomicInteger. Po zamknięciu executora wypisz łączną liczbę
         * ukończonych zadań (powinno być 100).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NamedVirtualThreadsLoop {
        /*
         * 🧪 Zadanie 6:
         * W pętli utwórz 5 nazwanych wątków wirtualnych ("Worker-1".."Worker-5"), uruchom
         * wszystkie, a następnie zaczekaj na każdy metodą join(1000) i wypisz jego nazwę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TimingFiveHundredTasks {
        /*
         * 🧪 Zadanie 7:
         * Zmierz czas wykonania 500 zadań (wątki wirtualne), z których każde śpi 20 ms.
         * Wypisz zmierzony czas w milisekundach - powinien być bliski 20 ms, a nie 500*20 ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UnstartedIsAliveCheck {
        /*
         * 🧪 Zadanie 8:
         * Utwórz wątek wirtualny metodą unstarted() i sprawdź isAlive() (powinno być false).
         * Następnie wywołaj start(), sprawdź isAlive() ponownie (powinno być true) i na końcu
         * zaczekaj join(1000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ThreadToStringComparison {
        /*
         * 🧪 Zadanie 9:
         * Wewnątrz zadania uruchomionego na wątku wirtualnym oraz wewnątrz zadania na wątku
         * platformowym wypisz Thread.currentThread().toString(). Porównaj oba napisy
         * i skomentuj różnicę w formacie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CallableViaVirtualExecutor {
        /*
         * 🧪 Zadanie 10:
         * Prześlij do Executors.newVirtualThreadPerTaskExecutor() zadanie typu Callable<Integer>
         * zwracające wartość 42. Odbierz wynik przez Future.get() i wypisz go.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FixedPoolVsVirtualTiming {
        /*
         * 🧪 Zadanie 11:
         * Porównaj czas wykonania 1000 zadań (każde śpi 5 ms) uruchomionych przez
         * Executors.newFixedThreadPool(10) oraz przez Executors.newVirtualThreadPerTaskExecutor().
         * Wypisz oba czasy i skomentuj różnicę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SimulatedHttpRequests {
        /*
         * 🧪 Zadanie 12:
         * Za pomocą wątków wirtualnych obsłuż 200 symulowanych "żądań HTTP" - każde zadanie
         * śpi 30 ms i zwraca String "Request-N obsłużony przez <wątek>". Zbierz wyniki
         * do listy (Future.get() dla każdego) i wypisz pierwsze 5 z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FiveThousandCounterTasks {
        /*
         * 🧪 Zadanie 13:
         * Prześlij 5000 zadań do wątków wirtualnych, z których każde śpi 5 ms i zwiększa
         * AtomicInteger. Po zamknięciu executora wypisz wartość licznika (powinna wynosić 5000)
         * oraz całkowity czas wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_VirtualExecutorWithCompletableFuture {
        /*
         * 🧪 Zadanie 14:
         * Użyj CompletableFuture.supplyAsync(supplier, virtualExecutor) dla 10 zadań liczących
         * kwadraty liczb 1-10, gdzie virtualExecutor to Executors.newVirtualThreadPerTaskExecutor().
         * Połącz wszystkie przez allOf(), zsumuj wyniki i wypisz sumę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RunIoBoundUtilityMethod {
        /*
         * 🧪 Zadanie 15:
         * Napisz metodę runIoBoundTasks(int count, long sleepMillis) zwracającą czas trwania
         * (long) uruchomienia "count" zadań na wątkach wirtualnych, z których każde śpi
         * sleepMillis. Wywołaj ją dla (2000, 10) i (2000, 50) i porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CpuBoundNoSpeedup {
        /*
         * 🧪 Zadanie 16:
         * Uruchom identyczne obliczeniowo zadanie (np. sumę kwadratów liczb do 5 000 000)
         * na puli platformowej o rozmiarze równym liczbie rdzeni procesora oraz na executorze
         * wątków wirtualnych, dla tej samej liczby zadań. Zmierz i porównaj czasy, wypisując
         * komentarz, że wątki wirtualne nie przyspieszają zadań CPU-bound.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExceptionHandlingInVirtualTask {
        /*
         * 🧪 Zadanie 17:
         * Prześlij do executora wątków wirtualnych zadanie, które rzuca RuntimeException.
         * Odbierz Future.get() w bloku try/catch(ExecutionException), złap wyjątek
         * i wypisz komunikat jego przyczyny (getCause().getMessage()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_NamedThreadFactoryPattern {
        /*
         * 🧪 Zadanie 18:
         * Użyj Thread.ofVirtual().name("vt-", 0).factory() jako ThreadFactory dla
         * Executors.newThreadPerTaskExecutor(factory). Prześlij 5 zadań wypisujących
         * nazwę bieżącego wątku i sprawdź, że nazwy są kolejno "vt-0".."vt-4".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ManualShutdownPattern {
        /*
         * 🧪 Zadanie 19:
         * Utwórz Executors.newVirtualThreadPerTaskExecutor() BEZ try-with-resources,
         * prześlij 10 zadań, a następnie ręcznie wywołaj shutdown() i awaitTermination(5, TimeUnit.SECONDS).
         * Wypisz isTerminated() po zamknięciu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ThreadSafeJobResults {
        /*
         * 🧪 Zadanie 20:
         * Dla listy 50 napisów (jobów) uruchom dla każdego zadanie na wątku wirtualnym
         * obliczające String.length(), zbierając wyniki do bezpiecznej wątkowo kolekcji
         * (np. ConcurrentLinkedQueue). Po zakończeniu wszystkich zadań wypisz sumę
         * wszystkich długości.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_WebCrawlerSimulation {
        /*
         * 🧪 Zadanie 21:
         * Dla listy 50 symulowanych adresów URL uruchom dla każdego zadanie Callable
         * (wątek wirtualny), symulujące pobranie strony (Thread.sleep losowe 10-50 ms)
         * i zwracające losową "długość treści". Użyj invokeAll(), zsumuj wszystkie
         * długości i wypisz sumę oraz całkowity czas wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FixedPoolVsVirtualAtScale {
        /*
         * 🧪 Zadanie 22:
         * Porównaj obsługę 2000 zadań I/O-bound (każde śpi 20 ms) przez pulę platformową
         * o rozmiarze 50 wątków oraz przez executor wątków wirtualnych. Wypisz oba czasy
         * i skomentuj, dlaczego pula platformowa skaluje się gorzej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_SemaphoreLimitedConcurrency {
        /*
         * 🧪 Zadanie 23:
         * Połącz java.util.concurrent.Semaphore (limit 50 zezwoleń) z executorem wątków
         * wirtualnych, aby ograniczyć liczbę jednocześnie wykonywanych "połączeń" spośród
         * 1000 zadań łącznie. Wypisz maksymalną liczbę zaobserwowanych równoległych zadań
         * (nie powinna przekroczyć 50).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CancelWithInvokeAllTimeout {
        /*
         * 🧪 Zadanie 24:
         * Utwórz listę 10 zadań (Callable), z których część śpi krótko (50 ms), a część
         * długo (2000 ms). Użyj invokeAll(tasks, 200, TimeUnit.MILLISECONDS), aby przerwać
         * zadania, które nie zdążyły się zakończyć w limicie czasu. Wypisz, ile zadań
         * zakończyło się poprawnie, a ile zostało anulowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BulkheadTwoServices {
        /*
         * 🧪 Zadanie 25:
         * Utwórz dwa niezależne executory wątków wirtualnych reprezentujące dwie "usługi"
         * (szybka: sleep 10 ms, wolna: sleep 100 ms). Prześlij po 500 zadań do każdej,
         * zmierz średni czas obsługi w każdej usłudze i wypisz porównanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_PrimeCountingBatch {
        /*
         * 🧪 Zadanie 26:
         * Dla listy 1000 liczb całkowitych sprawdź metodą prostego dzielenia próbnego,
         * czy każda jest liczbą pierwszą - uruchom to jako 1000 zadań Callable<Boolean>
         * na executorze wątków wirtualnych przez invokeAll(). Wypisz łączną liczbę
         * znalezionych liczb pierwszych oraz czas wykonania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_TwoStageLatchPipeline {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj dwuetapowy pipeline z użyciem CountDownLatch: etap 1 to 100 zadań na
         * wątkach wirtualnych, które muszą się WSZYSTKIE zakończyć, zanim wystartuje
         * etap 2 (kolejne 100 zadań). Użyj dwóch osobnych latchy do synchronizacji
         * i wypisz komunikat po zakończeniu każdego etapu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_InterruptCleanupOnVirtualThreads {
        /*
         * 🧪 Zadanie 28:
         * Prześlij 20 zadań na wątkach wirtualnych, z których każde w bloku finally
         * zwiększa licznik "sprzątań". Po krótkim opóźnieniu wywołaj shutdownNow() na
         * executorze, przerywając część zadań w trakcie wykonania. Wypisz wartość licznika
         * sprzątań, potwierdzając że finally wykonało się nawet dla przerwanych zadań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ThreeApproachesBenchmark {
        /*
         * 🧪 Zadanie 29:
         * Porównaj wykonanie 3000 zadań I/O-bound (każde śpi 2 ms) na trzy sposoby:
         * (a) sekwencyjnie w zwykłej pętli, (b) pula platformowa o rozmiarze 100,
         * (c) executor wątków wirtualnych. Wypisz wszystkie trzy czasy w formie tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_SimulatedHttpServerThroughput {
        /*
         * 🧪 Zadanie 30:
         * Napisz metodę simulateHttpServer(int requestCount), w której każde "żądanie"
         * (uruchamiane na wątku wirtualnym) wykonuje: symulowaną autoryzację (sleep 5 ms),
         * symulowany odczyt z bazy (sleep 10 ms) i formatowanie odpowiedzi. Wywołaj ją dla
         * 5000 żądań, zmierz łączny czas i wypisz przepustowość (żądania/s).
         */
        public static void main(String[] args) { }
    }
}
