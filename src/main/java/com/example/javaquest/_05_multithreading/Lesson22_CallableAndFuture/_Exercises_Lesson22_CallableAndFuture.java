package com.example.javaquest._05_multithreading.Lesson22_CallableAndFuture;

public class _Exercises_Lesson22_CallableAndFuture {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SimpleCallableResult {
        /*
         * 🧪 Zadanie 1:
         * Utwórz Callable<Integer> zwracające wynik 10*10. Wyślij je do
         * newFixedThreadPool(1) przez submit(), pobierz wynik przez future.get()
         * i wypisz go. Pamiętaj o poprawnym zamknięciu puli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunnableVsCallableComparison {
        /*
         * 🧪 Zadanie 2:
         * W jednej puli wyślij Runnable przez execute() (który tylko wypisuje
         * komunikat, nic nie zwraca) oraz Callable<String> przez submit()
         * (zwracające "wynik gotowy"). Wypisz wynik Callable po future.get()
         * i porównaj oba podejścia w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CallableWithSleepMeasureTime {
        /*
         * 🧪 Zadanie 3:
         * Utwórz Callable<String>, które śpi 100ms i zwraca "gotowe". Zmierz
         * (System.nanoTime) czas oczekiwania na future.get() i wypisz go w
         * milisekundach - powinien wynosić w przybliżeniu 100ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_GetWithTimeoutException {
        /*
         * 🧪 Zadanie 4:
         * Utwórz Callable<String>, które śpi 300ms. Wywołaj future.get(50,
         * TimeUnit.MILLISECONDS). Złap TimeoutException, wypisz komunikat, a w
         * bloku finally wywołaj future.cancel(true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExecutionExceptionUnwrap {
        /*
         * 🧪 Zadanie 5:
         * Utwórz Callable<Integer>, które rzuca new ArithmeticException("dzielenie
         * przez zero"). Wywołaj future.get(), złap ExecutionException i wypisz
         * e.getCause() aby pokazać oryginalny wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IsDoneCheck {
        /*
         * 🧪 Zadanie 6:
         * Wyślij szybkie Callable (bez sleep) i sprawdź future.isDone() po krótkim
         * sleep(50ms) w main - powinno być true. Wyślij osobne wolne Callable
         * (sleep 500ms) i sprawdź isDone() OD RAZU po submit - powinno być false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CancelLongRunningTask {
        /*
         * 🧪 Zadanie 7:
         * Wyślij Callable śpiące 1000ms. Po 50ms wywołaj future.cancel(true) i
         * wypisz future.isCancelled(). Spróbuj wywołać future.get() po anulowaniu
         * i złap CancellationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CollectResultsFromMultipleTasks {
        /*
         * 🧪 Zadanie 8:
         * Utwórz 3 niezależne Callable<Integer> liczące kwadraty liczb 2, 5, 9.
         * Zbierz Future do List<Future<Integer>>, a następnie w pętli wywołaj get()
         * na każdym i wypisz wszystkie wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CancelBeforeStart {
        /*
         * 🧪 Zadanie 9:
         * Na newSingleThreadExecutor() wyślij 3 Callable (pierwsze śpi 500ms,
         * blokując wątek). Zanim drugie zadanie zdąży wystartować, wywołaj na jego
         * Future metodę cancel(false) i wypisz isCancelled() (true) oraz isDone() (true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_GetOrDefaultHelper {
        /*
         * 🧪 Zadanie 10:
         * Napisz metodę generyczną `<T> T getOrDefault(Future<T> future, T
         * defaultValue)`, która próbuje future.get(100, TimeUnit.MILLISECONDS) i
         * zwraca defaultValue przy TimeoutException lub ExecutionException.
         * Przetestuj ją na szybkim i wolnym Callable.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FactorialsInParallel {
        /*
         * 🧪 Zadanie 11:
         * Wyślij 5 Callable<Long> liczących silnię liczb 1-5. Zbierz Future do
         * listy, odbierz wszystkie wyniki przez get() i wypisz sumę wszystkich
         * silni.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ParallelDownloadSimulation {
        /*
         * 🧪 Zadanie 12:
         * Zasymuluj 4 "pobrania plików" jako Callable<String> śpiące losowo
         * (50-200ms) i zwracające nazwę pobranego pliku. Zmierz łączny czas
         * (System.nanoTime) potrzebny na zebranie wszystkich 4 wyników i porównaj
         * go (w komentarzu/println) z hipotetyczną sumą sekwencyjną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CustomCheckedExceptionChain {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj własny checked wyjątek OrderValidationException. Utwórz
         * Callable<Boolean>, które go rzuca. Złap ExecutionException z future.get(),
         * odzyskaj oryginalny wyjątek przez getCause() i wypisz jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ValidationPipeline {
        /*
         * 🧪 Zadanie 14:
         * Utwórz 4 Callable<Boolean> sprawdzające różne warunki (np. liczba > 0,
         * napis niepusty, lista niepusta, wiek >= 18 - z ustalonymi z góry
         * wartościami wejściowymi). Zbierz Future do listy, pobierz wszystkie
         * wyniki i wypisz logiczną koniunkcję (AND) wszystkich rezultatów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UnequalRangeSums {
        /*
         * 🧪 Zadanie 15:
         * Podziel zakres 1-10000 na 4 NIERÓWNE fragmenty (np. 1-1000, 1001-3000,
         * 3001-6000, 6001-10000), policz sumę każdego fragmentu w osobnym
         * Callable<Long>, zbierz wyniki i zweryfikuj że suma wszystkich fragmentów
         * zgadza się z sekwencyjnie policzoną sumą całego zakresu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_PartialFailureHandling {
        /*
         * 🧪 Zadanie 16:
         * Wyślij 5 Callable<Integer>, z których jeden (np. 3-ci) rzuca wyjątek.
         * W pętli po Future wywołuj get() z osobnym try/catch dla KAŻDEGO
         * elementu, zbierając udane wyniki do jednej listy, a informacje o błędzie
         * do drugiej - tak aby jedna porażka nie przerwała zbierania reszty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_RetryWithFutureManual {
        /*
         * 🧪 Zadanie 17:
         * Utwórz Callable<String>, które rzuca wyjątek dla pierwszych 2 wywołań
         * (licznik prób we współdzielonym AtomicInteger) i zwraca "sukces" za 3.
         * razem. Ręcznie wysyłaj je ponownie (submit) w pętli do 3 prób, łapiąc
         * ExecutionException, i wypisz finalny wynik oraz liczbę prób.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_TimeoutWithFallbackTask {
        /*
         * 🧪 Zadanie 18:
         * Wyślij "wolne" Callable (sleep 300ms). Wywołaj get(100, TimeUnit.MILLISECONDS);
         * jeśli wystąpi TimeoutException, anuluj je (cancel(true)) i wyślij
         * zamiast niego "szybkie" Callable (sleep 20ms) jako fallback, którego wynik
         * ostatecznie wypisujesz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RaceBetweenTwoCallables {
        /*
         * 🧪 Zadanie 19:
         * Uruchom 2 Callable<Long> liczące tę samą sumę 1..100000 różnymi metodami
         * (pętla iteracyjna vs wzór n*(n+1)/2). W pętli polling z krótkim sleep(5ms)
         * sprawdzaj isDone() obu Future, wypisz który skończył pierwszy i anuluj
         * (cancel) ten, który jeszcze nie skończył.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AggregateResultsIntoMap {
        /*
         * 🧪 Zadanie 20:
         * Utwórz 4 Callable<Integer> oznaczone kluczami "A", "B", "C", "D",
         * zwracające różne stałe wartości. Wyślij je i po odebraniu wyników przez
         * get() zbuduj Map<String, Integer> łączącą klucz z wynikiem, a na końcu
         * wypisz całą mapę.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ParallelSumWithFormulaVerification {
        /*
         * 🧪 Zadanie 21:
         * Podziel tablicę 1..100000 na 4 fragmenty, policz sumę każdego przez
         * Callable<Long> na newFixedThreadPool(4), zsumuj wyniki cząstkowe i
         * zweryfikuj wynik ze wzorem arytmetycznym n*(n+1)/2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CallableWithLatchSignal {
        /*
         * 🧪 Zadanie 22:
         * Połącz Future z CountDownLatch (Lekcja 20): 5 Callable<Integer> liczy
         * wynik i DODATKOWO odlicza wspólny CountDownLatch(5) po zakończeniu pracy.
         * Main najpierw czeka na latch.await(5, TimeUnit.SECONDS), a DOPIERO POTEM
         * pobiera wyniki przez future.get() (powinno być natychmiastowe, bo zadania
         * już się skończyły).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ParallelWordCounter {
        /*
         * 🧪 Zadanie 23:
         * Podziel ustalony tekst (kilka zdań) na 3 fragmenty. Każdy fragment
         * przetwarzany jest przez Callable<Map<String,Integer>> liczące częstość
         * słów. Zbierz wszystkie Future, scal częściowe mapy w jedną finalną mapę
         * częstości i wypisz najczęściej występujące słowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ExceptionAggregationReport {
        /*
         * 🧪 Zadanie 24:
         * Wyślij 6 Callable, z których 2 rzucają różne typy wyjątków (np.
         * IllegalArgumentException i IllegalStateException). Zbierz Future do
         * listy, w pętli osobno gromadź listę udanych wyników i listę przyczyn
         * błędów (getCause()), a na końcu wypisz pełny raport obu list.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FirstSuccessfulHelper {
        /*
         * 🧪 Zadanie 25:
         * Napisz metodę `<T> T firstSuccessful(List<Callable<T>> tasks, ExecutorService pool)`,
         * która wysyła wszystkie zadania, po czym w pętli polling (isDone() z
         * krótkim sleep, ograniczoną liczbą prób lub limitem czasu) znajduje
         * PIERWSZY zakończony sukcesem wynik i anuluje resztę. Przetestuj na 4
         * Callable o różnych, ustalonych czasach snu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TimeoutProtectedBatchRunner {
        /*
         * 🧪 Zadanie 26:
         * Dla listy 6 Callable<Integer> o różnych czasach wykonania wyślij
         * wszystkie, a następnie dla każdego Future wywołaj get(200,
         * TimeUnit.MILLISECONDS); przy TimeoutException anuluj zadanie i zapisz
         * null w liście wyników zamiast rzucać wyjątek dalej. Wypisz finalną listę
         * wyników (mieszankę wartości i null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SemaphoreLimitedCallables {
        /*
         * 🧪 Zadanie 27:
         * Wyślij 8 Callable<Integer> do puli, ale ogranicz liczbę RÓWNOCZEŚNIE
         * WYKONUJĄCYCH SIĘ zadań do 2 za pomocą Semaphore(2) (acquire/release
         * wewnątrz treści Callable, wokół właściwej pracy). Zweryfikuj, że mimo to
         * wszystkie 8 wyników zostaje poprawnie zebranych przez future.get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MiniMapReduce {
        /*
         * 🧪 Zadanie 28:
         * Podziel tablicę int[1000] wypełnioną losowymi liczbami na 4 fragmenty.
         * Faza map: 4 Callable<Integer> liczą maksimum swojego fragmentu. Faza
         * reduce: w main, po zebraniu wyników przez get(), policz ogólne
         * maksimum i zweryfikuj je z sekwencyjnym Arrays.stream(...).max().
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DependentTasksChain {
        /*
         * 🧪 Zadanie 29:
         * Wyślij Callable A liczące wartość bazową (np. sumę cyfr ustalonej
         * liczby). Poczekaj na jego wynik przez future.get(), a DOPIERO POTEM
         * wyślij Callable B, którego zadaniem jest podniesienie wyniku A do
         * kwadratu. Wypisz oba wyniki, pokazując zależność B od A.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_OrderPricingServiceCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mały "serwis wyceny zamówień": zdefiniuj prostą klasę/rekord
         * OrderResult(int orderId, double price, String error). Utwórz 10
         * Callable<OrderResult> przetwarzających zamówienia o id 1-10, gdzie co
         * czwarte zamówienie (4, 8) rzuca wyjątek symulujący błąd. Zbierz wszystkie
         * Future, obsłuż każdy get() osobnym try/catch, wypisz raport końcowy:
         * sumę przychodu z udanych zamówień oraz listę id nieudanych. Zamknij pulę.
         */
        public static void main(String[] args) { }
    }
}
