package com.example.javaquest._05_multithreading.Lesson36_BestPractices;

public class _Exercises_Lesson36_BestPractices {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ThreadPerTaskToExecutor {
        /*
         * 🧪 Zadanie 1:
         * Przepisz pętlę tworzącą 20 surowych wątków (new Thread) wykonujących proste
         * wypisanie numeru zadania, tak aby zamiast tego korzystała z
         * Executors.newFixedThreadPool(4). Porównaj nazwy wątków przed i po refaktorze
         * i poprawnie zamknij pulę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SharedCounterToBlockingQueue {
        /*
         * 🧪 Zadanie 2:
         * Zamień parę producent/konsument opartą na wspólnej zmiennej chronionej blokiem
         * synchronized, na komunikację przez BlockingQueue (bez ręcznej synchronizacji).
         * Zweryfikuj, że wynik końcowy (przekazane wartości) jest identyczny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ImmutableMoneyConcurrentReads {
        /*
         * 🧪 Zadanie 3:
         * Zdefiniuj niemutowalny rekord Money(int amount, String currency). Uruchom 5 wątków
         * czytających tę samą instancję równolegle (bez żadnej synchronizacji) i wypisujących
         * jej wartość - pokaż, że jest to bezpieczne dzięki niemutowalności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ShortCriticalSectionRefactor {
        /*
         * 🧪 Zadanie 4:
         * Zaimplementuj dwie wersje aktualizacji współdzielonego licznika: "złą" (kosztowne
         * obliczenie WEWNĄTRZ synchronized) i "dobrą" (obliczenie POZA sekcją krytyczną,
         * w środku tylko aktualizacja). Zweryfikuj, że obie dają poprawny wynik końcowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_UnlockInFinallyCorrect {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj poprawne użycie ReentrantLock: lock() przed try, unlock() w finally.
         * Po wyjściu z sekcji krytycznej wypisz ((ReentrantLock) lock).isLocked(), potwierdzając
         * że lock zawsze zostaje zwolniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ShutdownAndWaitHelper {
        /*
         * 🧪 Zadanie 6:
         * Napisz metodę pomocniczą shutdownAndWait(ExecutorService pool) wykonującą
         * shutdown() + awaitTermination(timeout), z fallbackiem shutdownNow(). Prześlij
         * 5 zadań do puli, zamknij ją tą metodą i wypisz pool.isTerminated().
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VirtualExecutorAutoClose {
        /*
         * 🧪 Zadanie 7:
         * Użyj Executors.newVirtualThreadPerTaskExecutor() w try-with-resources do wykonania
         * 50 krótkich symulowanych zadań I/O (sleep 10 ms). Po bloku try spróbuj przesłać
         * kolejne zadanie i złap RejectedExecutionException, potwierdzając że executor
         * został automatycznie zamknięty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_UnsafeVsAtomicCounter {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj niebezpieczny licznik int inkrementowany przez 4 wątki (100 000 razy
         * każdy) oraz bezpieczny licznik AtomicInteger. Wypisz oba wyniki końcowe, potwierdzając
         * że tylko wersja z AtomicInteger zawsze daje poprawną wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImmutablePoint3DConcurrentReads {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj niemutowalny rekord Point3D(double x, double y, double z). Uruchom
         * 3 wątki liczące odległość punktu od początku układu współrzędnych na podstawie
         * tej samej instancji - zweryfikuj brak potrzeby synchronizacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CheckExecutorTerminatedHelper {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę checkBestPractice1(ExecutorService pool) zwracającą pool.isTerminated().
         * Utwórz małą pulę, poprawnie ją zamknij i wywołaj tę metodę, wypisując wynik.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ProducerConsumerViaExecutor {
        /*
         * 🧪 Zadanie 11:
         * Zamiast surowych wątków użyj ExecutorService (pula 2) do uruchomienia zadania
         * producenta i zadania konsumenta komunikujących się przez BlockingQueue. Przetwórz
         * 20 elementów i poprawnie zamknij pulę na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImmutableTaskThroughQueueAndPool {
        /*
         * 🧪 Zadanie 12:
         * Zdefiniuj niemutowalny rekord Task(int id, String payload). Prześlij taski przez
         * BlockingQueue do 3 konsumentów uruchomionych w ExecutorService, licząc przetworzone
         * zadania przez AtomicInteger. Poprawnie zamknij pulę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MinimizedLockAroundListAdd {
        /*
         * 🧪 Zadanie 13:
         * Użyj ReentrantLock chroniącego WYŁĄCZNIE wywołanie list.add(), podczas gdy kosztowne
         * "obliczenie linii raportu" wykonuje się poza sekcją krytyczną. Uruchom 4 wątki
         * dodające łącznie 400 linii i zweryfikuj końcowy rozmiar listy oraz że lock zawsze
         * jest zwalniany w finally.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImmutableConfigSharedAcrossPool {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj niemutowalny rekord DbConfig(String url, int poolSize). Uruchom
         * ExecutorService z kilkoma zadaniami, z których każde tylko odczytuje tę samą
         * instancję konfiguracji i loguje jej wartości. Poprawnie zamknij pulę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_LoopRefactorToVirtualPlusQueue {
        /*
         * 🧪 Zadanie 15:
         * Zrefaktoryzuj opisaną (tylko w komentarzu) złą praktykę tworzenia wątku na każdy
         * z 200 elementów, na Executors.newVirtualThreadPerTaskExecutor() zbierający wyniki
         * do bezpiecznej wątkowo kolejki. Wypisz łączną liczbę przetworzonych elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SafeAccumulatorWithLock {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj klasę SafeAccumulator z ReentrantLock i metodą addAndGet(int),
         * której sekcja krytyczna jest minimalna, a unlock() jest w finally. Przetestuj
         * z 8 wątkami dodającymi po 1000 razy i zweryfikuj dokładną sumę końcową.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImmutableEventsThroughQueueTally {
        /*
         * 🧪 Zadanie 17:
         * Zdefiniuj niemutowalny rekord Event(String type). 3 producenckie zadania
         * (ExecutorService) wrzucają zdarzenia do BlockingQueue, a 1 zadanie konsumenckie
         * zlicza je do Map<String,Integer> per typ. Poprawnie zamknij pulę i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ManagedExecutorAlwaysShutsDown {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj klasę ManagedExecutor z metodami start(tasks) i awaitAndShutdown(timeout),
         * gwarantującą zamknięcie puli NAWET jeśli jedno z zadań rzuci wyjątek (użyj try/finally).
         * Przetestuj z jednym zadaniem celowo rzucającym wyjątek i zweryfikuj, że pula i tak
         * zostaje poprawnie zamknięta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_LargeVsMinimalCriticalSectionTiming {
        /*
         * 🧪 Zadanie 19:
         * Zmierz czas i poprawność dla dwóch podejść: "złego" (wspólny licznik int, cała
         * praca w synchronized) i "dobrego" (AtomicInteger, minimalna sekcja krytyczna),
         * każde dla 4 wątków x 50 000 inkrementacji. Wypisz oba czasy i oba wyniki końcowe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_VirtualProducerConsumerSum {
        /*
         * 🧪 Zadanie 20:
         * Uruchom producenta (wątek wirtualny) wrzucającego 500 liczb do BlockingQueue oraz
         * 10 konsumentów (executor wątków wirtualnych) sumujących je do AtomicLong. Zweryfikuj,
         * że suma końcowa odpowiada sumie liczb 1-500, i poprawnie zamknij/dołącz wszystko.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_OrderServiceAllRulesCombined {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj mini-serwis przetwarzania zamówień łączący: niemutowalny rekord Order,
         * BlockingQueue jako wejście, ExecutorService workerów aktualizujących AtomicInteger
         * (krótka sekcja krytyczna) oraz poprawne zamknięcie puli. Przetwórz 100 zamówień
         * i wypisz raport końcowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReadWriteLockCacheShortSections {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj prostą pamięć podręczną chronioną ReentrantReadWriteLock, z minimalnymi
         * sekcjami krytycznymi i unlock() w finally zarówno dla odczytu jak i zapisu.
         * Uruchom 5 zadań zapisujących i 5 czytających jednocześnie (ExecutorService),
         * wypisz końcowy rozmiar cache i przykładową zawartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ResilientVirtualBatchProcessor {
        /*
         * 🧪 Zadanie 23:
         * Przetwórz 500 niemutowalnych rekordów Job wrzuconych do BlockingQueue przez
         * producenta, konsumowanych przez executor wątków wirtualnych (jedno zadanie na job).
         * Część jobów (o nieparzystym id) symuluje niepowodzenie - zbierz wynik sukces/porażka
         * do ConcurrentHashMap i zweryfikuj, że executor zamyka się poprawnie mimo wyjątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CompletableFutureOnDedicatedExecutor {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj pipeline CompletableFuture.supplyAsync(supplier, executor) dla 20 żądań,
         * gdzie executor to jawnie utworzony, ograniczony ExecutorService (nie domyślny
         * common pool). Połącz wyniki przez allOf(), a na końcu w bloku finally zamknij
         * executor zgodnie z zasadą 6.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_BoundedQueueCallerRunsPolicy {
        /*
         * 🧪 Zadanie 25:
         * Utwórz ThreadPoolExecutor z ograniczoną kolejką (ArrayBlockingQueue o pojemności 10)
         * i polityką CallerRunsPolicy dla 100 zadań. Wypisz, ile zadań wykonał wątek
         * zgłaszający (caller), a ile wątki puli, i poprawnie zamknij executor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SafeSingletonCounterWithSnapshot {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj klasę licznika z ReentrantLock chroniącym mutowalną sumę oraz
         * metodą getSnapshot() zwracającą niemutowalny rekord (bezpieczny do przekazania
         * innym wątkom). Przetestuj z 6 wątkami aktualizującymi i 6 wątkami odczytującymi
         * migawki równolegle, weryfikując poprawność końcowej sumy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MonitoringWithThreadDump {
        /*
         * 🧪 Zadanie 27:
         * Uruchom nazwaną pulę ExecutorService przetwarzającą 50 zadań. W trakcie działania
         * zrób migawkę Thread.getAllStackTraces() i wypisz raport nazw/stanów aktywnych
         * workerów puli, a po zakończeniu wszystkich zadań wypisz raport końcowy
         * i poprawnie zamknij pulę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GracefulTerminationWithinExecutor {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj 3 długo działające workery (pętla z flagą volatile, uruchomione
         * jako zadania w ExecutorService, NIE jako surowe wątki), które trzeba zatrzymać
         * flagą + shutdownNow() + awaitTermination w ograniczonym budżecie czasu.
         * Zweryfikuj i wypisz, że wszystkie zatrzymały się w limicie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_OrderProcessingServiceCapstone {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj OrderProcessingService łączący: niemutowalne rekordy Order, BlockingQueue
         * jako wejście, przetwarzanie na wątkach wirtualnych, liczniki AtomicLong (przetworzone/
         * odrzucone), ReentrantLock chroniący krótką sekcję dopisywania do dziennika audytu
         * oraz metodę shutdown(). Przetwórz 200 zamówień (część celowo niepoprawnych)
         * i wypisz raport metryk końcowych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_RefactorAllViolationsAtOnce {
        /*
         * 🧪 Zadanie 30:
         * Mając (opisany w komentarzu) fragment kodu łamiący jednocześnie kilka dobrych
         * praktyk (500 surowych wątków w pętli, ogromna sekcja krytyczna obejmująca kosztowne
         * obliczenia, brak zamknięcia executora, współdzielony mutowalny obiekt przekazywany
         * bez ostrożności), zaimplementuj POPRAWIONĄ wersję naprawiającą wszystkie te problemy
         * naraz i zweryfikuj poprawny wynik końcowy w rozsądnym czasie (do ok. 2 sekund).
         */
        public static void main(String[] args) { }
    }
}
