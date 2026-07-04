package com.example.javaquest._05_multithreading.Lesson21_ExecutorService;

public class _Exercises_Lesson21_ExecutorService {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_FixedThreadPoolBasics {
        /*
         * 🧪 Zadanie 1:
         * Utwórz Executors.newFixedThreadPool(3) i za pomocą execute() wyślij 5 zadań,
         * z których każde wypisuje swój numer (1-5) oraz Thread.currentThread().getName().
         * Zamknij pulę poprawnie przez shutdown()+awaitTermination(5, TimeUnit.SECONDS).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SingleThreadExecutorOrder {
        /*
         * 🧪 Zadanie 2:
         * Utwórz Executors.newSingleThreadExecutor() i wyślij 5 zadań wypisujących
         * swój numer i nazwę wątku. Zweryfikuj (po logach), że wszystkie 5 zadań
         * wykonało się na TYM SAMYM wątku i w kolejności zgłoszenia (FIFO).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CachedThreadPoolBasics {
        /*
         * 🧪 Zadanie 3:
         * Utwórz Executors.newCachedThreadPool() i wyślij 5 krótkich zadań
         * (bez sleep) wypisujących swój numer i nazwę wątku, obserwując, że pula
         * tworzy wątki na żądanie. Zamknij pulę poprawnym wzorcem shutdown+await.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExecuteVsSubmit {
        /*
         * 🧪 Zadanie 4:
         * W jednej puli newFixedThreadPool(2): wyślij jedno zadanie przez execute()
         * (bez odbierania wyniku) i jedno przez submit() zwracające Future<?>.
         * Wywołaj future.get() i wypisz future.isDone() po zakończeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ShutdownGraceful {
        /*
         * 🧪 Zadanie 5:
         * Utwórz newFixedThreadPool(1), wyślij 3 zadania (każde śpi 50ms i wypisuje
         * start/koniec), zaraz po ich zgłoszeniu wywołaj shutdown() (nie shutdownNow()).
         * Wywołaj awaitTermination(2, TimeUnit.SECONDS) i wypisz jego wynik - wszystkie
         * 3 zadania powinny się jednak wykonać do końca.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_ShutdownNowInterrupts {
        /*
         * 🧪 Zadanie 6:
         * Utwórz newFixedThreadPool(1), wyślij zadanie śpiące 1000ms z obsługą
         * InterruptedException (wypisz "przerwane"). Po 50ms wywołaj shutdownNow()
         * i wypisz rozmiar listy niewystartowanych zadań oraz wynik awaitTermination(2s).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ShutdownAndWaitHelper {
        /*
         * 🧪 Zadanie 7:
         * Napisz własną metodę shutdownAndWait(ExecutorService pool) (jak w lekcji:
         * shutdown() + awaitTermination(5s) + ewentualnie shutdownNow()). Użyj jej
         * do zamknięcia 3 różnych pul (fixed, single, cached) po wysłaniu do
         * każdej po 2 prostych zadań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountAllExecutedTasks {
        /*
         * 🧪 Zadanie 8:
         * Utwórz newFixedThreadPool(2), wyślij 10 zadań, z których każde
         * inkrementuje współdzielony AtomicInteger. Po shutdownAndWait() wypisz
         * finalną wartość licznika i zweryfikuj, że wynosi dokładnie 10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IsShutdownIsTerminated {
        /*
         * 🧪 Zadanie 9:
         * Utwórz pulę, wypisz isShutdown() i isTerminated() PRZED wysłaniem zadań,
         * potem wyślij 3 proste zadania, wywołaj shutdown(), wypisz isShutdown()
         * od razu po, a po awaitTermination(5s) wypisz isTerminated().
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RejectedExecutionAfterShutdown {
        /*
         * 🧪 Zadanie 10:
         * Utwórz pulę, wywołaj shutdown() od razu (bez wysyłania zadań), a następnie
         * spróbuj wysłać nowe zadanie przez execute(). Złap RejectedExecutionException
         * i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FixedVsCachedTiming {
        /*
         * 🧪 Zadanie 11:
         * Porównaj czas wykonania 6 zadań (każde śpi 100ms) na newFixedThreadPool(2)
         * oraz na newCachedThreadPool(). Zmierz czas całkowity (System.nanoTime) dla
         * obu przypadków i wypisz, który był szybszy oraz dlaczego (mniej wątków = więcej rund).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MaxConcurrentTasksGauge {
        /*
         * 🧪 Zadanie 12:
         * Na newFixedThreadPool(3) uruchom 8 zadań, z których każde inkrementuje
         * AtomicInteger "active" na starcie i dekrementuje na końcu, a AtomicInteger
         * "maxObserved" aktualizuje się (compareAndSet w pętli lub Math.max) do
         * największej zaobserwowanej wartości "active". Wypisz maxObserved (powinno
         * być <= 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CollectFuturesInList {
        /*
         * 🧪 Zadanie 13:
         * Wyślij 5 zadań przez submit() do listy List<Future<?>>. W pętli wywołaj
         * get() na każdym Future, a po przejściu całej listy wypisz "wszystkie
         * zadania zakończone".
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_TryFinallyShutdown {
        /*
         * 🧪 Zadanie 14:
         * Napisz kod wysyłający zadania do puli wewnątrz bloku try, gdzie jedno
         * z zadań (submit) celowo powoduje wyjątek podczas przygotowania danych
         * PRZED wysłaniem (np. dzielenie przez zero poza pulą). Zapewnij (finally),
         * że shutdownAndWait() ZAWSZE zostanie wywołane, nawet gdy wystąpi wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_BatchJobRunnerWithStats {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj "batch runner": 10 zadań generujących raport, z których 3 losowo
         * (na podstawie ustalonego z góry indeksu, np. 2, 5, 8) rzucają wyjątek
         * wewnątrz try/catch w treści zadania. Zlicz sukcesy i porażki przez dwa
         * AtomicInteger i wypisz podsumowanie po shutdownAndWait().
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CpuBoundTasksOnAvailableProcessors {
        /*
         * 🧪 Zadanie 16:
         * Utwórz newFixedThreadPool(Runtime.getRuntime().availableProcessors()) i
         * wyślij tyle samo zadań, ile jest procesorów, z których każde liczy sumę
         * liczb w swoim zakresie (np. 1..1_000_000 podzielone na równe części) i
         * wypisuje wynik cząstkowy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WorkerPoolProcessingList {
        /*
         * 🧪 Zadanie 17:
         * Podziel listę 12 Stringów na 4 równe fragmenty (po 3) i wyślij każdy
         * fragment jako osobne zadanie do newFixedThreadPool(4). Każde zadanie
         * wypisuje, które elementy przetworzył i na jakim wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FixedPoolTwoAtATime {
        /*
         * 🧪 Zadanie 18:
         * Utwórz newFixedThreadPool(2) i wyślij 6 zadań (każde śpi 100ms i wypisuje
         * start/koniec z sygnaturą czasową). Przeanalizuj logi, by potwierdzić, że
         * w danej chwili wykonują się maksymalnie 2 zadania naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RunWithTimeoutHelper {
        /*
         * 🧪 Zadanie 19:
         * Napisz metodę `boolean runWithTimeout(ExecutorService pool, long timeoutSeconds)`
         * wykonującą shutdown() + awaitTermination(timeoutSeconds) i w razie potrzeby
         * shutdownNow(), zwracającą true/false czy pula zdążyła się zamknąć. Przetestuj
         * ją na puli z szybkimi zadaniami (powinno zwrócić true) i na puli z jednym
         * bardzo długim zadaniem oraz krótkim timeoutem (powinno zwrócić false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ManualRetryMechanism {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj zadanie, które "zawodzi" (rzuca wyjątek) dla pierwszych 2 wywołań
         * (licznik w AtomicInteger współdzielony między próbami) i odnosi sukces za
         * 3. razem. Wyślij je do puli, łap wyjątek i ręcznie wysyłaj ponownie (max 3
         * próby), wypisując numer próby i finalny wynik.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_SynchronizedCounterAcrossExecutor {
        /*
         * 🧪 Zadanie 21:
         * Na newFixedThreadPool(4) wyślij 1000 zadań, z których każde inkrementuje
         * współdzielony licznik int chroniony blokiem synchronized (poznanym w
         * lekcji 11). Po shutdownAndWait() zweryfikuj, że finalna wartość licznika
         * wynosi dokładnie 1000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_WebServerSimulation {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj serwer WWW: newFixedThreadPool(4) jako pula "handlerów"
         * obsługuje 20 "żądań" (zadania śpiące losowo 10-50ms). Zbieraj do
         * AtomicLong sumę czasów przetwarzania i AtomicInteger licznik obsłużonych
         * żądań, a po zakończeniu wypisz średni czas przetwarzania żądania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LatchSignaledCompletion {
        /*
         * 🧪 Zadanie 23:
         * Połącz ExecutorService z CountDownLatch (znanym z poprzedniej lekcji):
         * wyślij 6 zadań do puli, każde na końcu wywołuje countDown() na wspólnym
         * latchu. Main czeka na latch.await(5, TimeUnit.SECONDS), a DOPIERO PO TYM
         * wywołuje shutdownAndWait(), by mieć pewność że wszystkie zadania faktycznie
         * się zakończyły przed zamknięciem puli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_WorkStealingPoolComparison {
        /*
         * 🧪 Zadanie 24:
         * Uruchom te same 20 krótkich zadań obliczeniowych (sumowanie zakresu liczb)
         * najpierw na Executors.newFixedThreadPool(4), a potem na
         * Executors.newWorkStealingPool(). Wypisz nazwy wątków użytych w obu
         * przypadkach i krótki komentarz o zaobserwowanej różnicy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_TwoStagePipeline {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj dwuetapowy potok: pula "stage1" (newFixedThreadPool(2)) przetwarza
         * 10 Stringów na wielkie litery i zapisuje do współdzielonej,
         * zsynchronizowanej listy, sygnalizując zakończenie przez CountDownLatch(10).
         * Dopiero po latch.await() pula "stage2" (newFixedThreadPool(2)) agreguje
         * wyniki (np. łączy je w jeden String) i wypisuje rezultat. Zamknij obie
         * pule poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SemaphoreLimitedExternalCalls {
        /*
         * 🧪 Zadanie 26:
         * Utwórz newFixedThreadPool(5) obsługującą 10 "żądań do zewnętrznego API",
         * ale ogranicz maksymalnie 2 RÓWNOCZESNE wywołania zewnętrzne za pomocą
         * Semaphore(2) (acquire przed "wywołaniem", release po). Wypisz komunikaty
         * potwierdzające, że nigdy więcej niż 2 wywołania nie są aktywne jednocześnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_CustomThreadFactoryUncaughtHandler {
        /*
         * 🧪 Zadanie 27:
         * Utwórz Executors.newFixedThreadPool(2, threadFactory), gdzie threadFactory
         * ustawia każdemu tworzonemu wątkowi Thread.UncaughtExceptionHandler
         * wypisujący "Złapano nieobsłużony wyjątek: <komunikat>". Wyślij zadanie
         * przez execute() (nie submit()), które rzuca RuntimeException, i zweryfikuj,
         * że handler go przechwycił.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_HandwrittenThreadPoolWithQueue {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj bardzo uproszczoną własną "pulę wątków" bez
         * ExecutorService: 2 wątki robocze pobierające Runnable ze współdzielonej
         * kolejki (np. ArrayDeque chronionej synchronized+wait/notifyAll) i
         * wykonujące je. Wyślij 5 zadań i 2 "pigułki trucizny" (po jednej na
         * wątek roboczy), by czysto zakończyć oba wątki robocze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ThreadPoolExecutorMonitoring {
        /*
         * 🧪 Zadanie 29:
         * Utwórz Executors.newFixedThreadPool(3) i rzutuj go na ThreadPoolExecutor.
         * Wyślij 12 zadań (każde śpi 50-150ms), a w międzyczasie (osobny wątek
         * monitorujący, sleep co 100ms, maks. 5 iteracji) wypisuj
         * getActiveCount() i getCompletedTaskCount(). Zamknij pulę na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_OrderProcessingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini-system przetwarzania zamówień: newFixedThreadPool(4)
         * przetwarza 25 zamówień (zadania), z których co 5. (np. indeksy 4, 9, 14,
         * 19, 24) rzuca symulowany wyjątek złapany wewnątrz zadania. Użyj
         * CountDownLatch(25) do sygnalizacji zakończenia wszystkich zamówień oraz
         * dwóch AtomicInteger (sukces/porażka) do statystyk. Po latch.await()
         * wypisz raport końcowy i zamknij pulę.
         */
        public static void main(String[] args) { }
    }
}
