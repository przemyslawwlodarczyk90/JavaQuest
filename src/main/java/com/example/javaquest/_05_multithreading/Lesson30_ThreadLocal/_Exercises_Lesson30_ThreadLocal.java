package com.example.javaquest._05_multithreading.Lesson30_ThreadLocal;

public class _Exercises_Lesson30_ThreadLocal {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IndependentValuesThreeThreads {
        /*
         * 🧪 Zadanie 1:
         * Utwórz ThreadLocal<Integer> perThreadValue (ThreadLocal.withInitial(() -> 0)).
         * Uruchom 3 wątki, z których każdy ustawia SWOJĄ wartość (np. 10, 20, 30)
         * i po krótkim sleep(50) wypisuje odczytaną wartość. Potwierdź, że każdy
         * widzi tylko swoją własną wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_WithInitialDefaultValue {
        /*
         * 🧪 Zadanie 2:
         * Utwórz ThreadLocal<String> ThreadLocal.withInitial(() -> "BRAK").
         * W nowym wątku odczytaj get() BEZ wcześniejszego set() i wypisz wynik –
         * potwierdź, że zwraca wartość domyślną z dostawcy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SetGetRemoveLifecycle {
        /*
         * 🧪 Zadanie 3:
         * Na jednym wątku wykonaj kolejno: get() (wartość domyślna), set("X"),
         * get() (powinno zwrócić "X"), remove(), get() (znów wartość domyślna).
         * Wypisz wynik każdego kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_PerThreadDateFormatterTwoThreads {
        /*
         * 🧪 Zadanie 4:
         * Odtwórz przykład z lekcji: ThreadLocal<SimpleDateFormat> z formatem
         * "dd-MM-yyyy". Uruchom 2 wątki, każdy formatujący aktualną datę własną
         * instancją SimpleDateFormat pobraną z ThreadLocal. Wypisz wynik obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SharedThreadLocalObjectIsolation {
        /*
         * 🧪 Zadanie 5:
         * Utwórz JEDEN, wspólny (static) obiekt ThreadLocal<Integer>. Uruchom
         * 4 wątki, każdy ustawiający wartość równą swojemu indeksowi * 100.
         * Po sleep(50) każdy wątek odczytuje get() – wypisz, że mimo wspólnego
         * obiektu ThreadLocal, wartości są w pełni izolowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_RemoveRestoresInitialValue {
        /*
         * 🧪 Zadanie 6:
         * Ustaw ThreadLocal<Integer> na wartość 42, wypisz get(), wywołaj remove(),
         * a następnie ponownie wypisz get() – potwierdź, że po remove() wraca
         * wartość domyślna (initialValue/withInitial), a nie null czy stare 42.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ContrastWithSharedMutableField {
        /*
         * 🧪 Zadanie 7:
         * Zdefiniuj zwykłe pole static int sharedCounter (BEZ ThreadLocal) oraz
         * ThreadLocal<Integer> perThreadCounter. Uruchom 3 wątki inkrementujące
         * oba 100 razy każdy. Wypisz finalną wartość sharedCounter (może być
         * błędna z powodu race condition) oraz wartości perThreadCounter dla
         * każdego wątku (zawsze poprawne, bo izolowane).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SimplePerThreadRequestId {
        /*
         * 🧪 Zadanie 8:
         * Utwórz ThreadLocal<String> REQUEST_ID. Uruchom 3 wątki, każdy
         * ustawiający swój identyfikator jako "req-" + nazwa wątku, po czym
         * wypisujący go z REQUEST_ID.get() w kilku miejscach kodu (symulując
         * dostęp bez przekazywania parametru).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PlainThreadLocalWithoutInitial {
        /*
         * 🧪 Zadanie 9:
         * Utwórz zwykły new ThreadLocal<String>() (bez withInitial). W nowym
         * wątku wywołaj get() PRZED jakimkolwiek set() i wypisz wynik – potwierdź
         * że zwraca null (brak initialValue).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_InitialValueSupplierCalledOncePerThread {
        /*
         * 🧪 Zadanie 10:
         * Utwórz ThreadLocal.withInitial(...) z dostawcą, który przy każdym
         * wywołaniu zwiększa AtomicInteger supplierCallCount. W jednym wątku
         * wywołaj get() TRZY razy pod rząd (bez set() pomiędzy) i wypisz wartość
         * supplierCallCount – potwierdź, że dostawca został wywołany tylko RAZ.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreadPoolLeakWithoutRemove {
        /*
         * 🧪 Zadanie 11:
         * Odtwórz scenariusz z lekcji: ExecutorService z 1 wątkiem, statyczny
         * ThreadLocal<String> REQUEST_ID, 3 zadania submitowane po kolei, KAŻDE
         * ustawiające REQUEST_ID BEZ wywołania remove(). Wypisz dla każdego
         * zadania, jaką wartość REQUEST_ID widziało na starcie (powinien być
         * widoczny "wyciek" z poprzedniego zadania od 2. zadania wzwyż).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ThreadPoolFixedWithRemove {
        /*
         * 🧪 Zadanie 12:
         * Powtórz Zadanie 11, ale tym razem każde zadanie ustawia wartość w
         * try i wywołuje REQUEST_ID.remove() w finally. Wypisz dla każdego
         * zadania wartość widzianą na starcie – potwierdź brak wycieku (zawsze null/brak).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TwoThreadPoolTaskToWorkerMapping {
        /*
         * 🧪 Zadanie 13:
         * Użyj puli 2 wątków i submituj 6 zadań, z których każde ustawia
         * w ThreadLocal<String> parę "worker=" + Thread.currentThread().getName()
         * i loguje ją. Wypisz mapowanie zadań na wątki robocze puli (który
         * worker obsłużył które zadania) i sprawdź poprawność izolacji na obu wątkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PerThreadRandomInstance {
        /*
         * 🧪 Zadanie 14:
         * Utwórz ThreadLocal<Random> withInitial(Random::new) (każdy wątek ma
         * własną instancję Random, unikając współdzielenia jednego obiektu).
         * Uruchom 3 wątki, każdy generujący 5 liczb losowych ze swojej instancji,
         * i wypisz wygenerowane ciągi – potwierdź że są niezależne między wątkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_PerThreadTaskCounterAcrossPoolTasks {
        /*
         * 🧪 Zadanie 15:
         * W puli 2 wątków wykonaj 8 zadań, gdzie każde zadanie zwiększa
         * ThreadLocal<Integer> tasksHandledByThisWorker (BEZ remove() – tu
         * celowo chcemy zliczać PRZEZ CAŁY czas życia wątku roboczego).
         * Na końcu (z poziomu samych zadań) wypisz, ile zadań obsłużył
         * każdy z 2 wątków roboczych puli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ThreadScopedTransactionContext {
        /*
         * 🧪 Zadanie 16:
         * Zdefiniuj prostą klasę TransactionContext (np. z polem txId). Użyj
         * ThreadLocal<TransactionContext> ustawianego na początku "transakcji"
         * w 3 zwykłych wątkach (z losowym sleep symulującym przeplot) i
         * usuwanego na końcu. Wypisz, że każdy wątek widzi tylko własny
         * TransactionContext przez cały czas trwania swojej "transakcji".
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_InheritableThreadLocalBasics {
        /*
         * 🧪 Zadanie 17:
         * Użyj InheritableThreadLocal<String> zamiast zwykłego ThreadLocal.
         * Ustaw wartość "wartosc-z-main" w main PRZED utworzeniem nowego wątku
         * potomnego, a następnie w potomku odczytaj get() – wypisz, że wątek
         * potomny ODZIEDZICZYŁ wartość ustawioną w main w momencie jego utworzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SharedVsThreadLocalDateFormatCorrectness {
        /*
         * 🧪 Zadanie 18:
         * Uruchom 4 wątki formatujące po 20 dat KAŻDY, raz współdzieląc JEDNĄ
         * instancję SimpleDateFormat (bez synchronizacji – SimpleDateFormat nie
         * jest thread-safe), raz przez ThreadLocal<SimpleDateFormat> (własna
         * instancja na wątek). Zbierz wyniki i sprawdź (np. porównując z
         * oczekiwanym wzorcem długości/formatu), czy w wersji współdzielonej
         * pojawiają się błędne/uszkodzone wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_GenericRunInThreadLocalScope {
        /*
         * 🧪 Zadanie 19:
         * Napisz generyczną metodę <T> void runInThreadLocalScope(ThreadLocal<T>
         * tl, T value, Runnable task), ustawiającą wartość, uruchamiającą task
         * i usuwającą wartość w finally. Użyj jej z ThreadLocal<String>
         * REQUEST_ID w 3 wątkach, wypisując wartość widzianą wewnątrz task
         * oraz potwierdzenie braku wycieku po zakończeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_FourThreadPoolEightTasksIsolationCheck {
        /*
         * 🧪 Zadanie 20:
         * W puli 4 wątków wykonaj 8 zadań, z których każde ustawia
         * ThreadLocal<String> requestId ("req-" + numer zadania), robi krótki
         * sleep, wypisuje wątek+requestId, i usuwa wartość w finally. Wypisz
         * pełen log wykonania i potwierdź poprawną izolację między zadaniami
         * na tym samym, reużywanym wątku puli.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LeakDetectionOverManyTasks {
        /*
         * 🧪 Zadanie 21:
         * Uruchom 20 zadań na puli 2 wątków, gdzie zadania NIE wywołują
         * remove(). Policz (przez porównanie wartości widzianej na starcie
         * z wartością poprzedniego zadania na tym samym wątku), ile razy
         * wystąpił "wyciek" starej wartości. Powtórz eksperyment z remove()
         * w finally i porównaj liczbę wykrytych wycieków (powinna wynosić 0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MdcStyleLoggingContext {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj mini odpowiednik MDC (Mapped Diagnostic Context): ThreadLocal<
         * Map<String,String>> z metodami put(key, value), get(key), clear().
         * Zasymuluj obsługę 5 "żądań" na puli 2 wątków, gdzie każde żądanie
         * ustawia "userId" i "requestId" w kontekście, "loguje" kilka linii
         * z prefiksem [userId=...,requestId=...] i czyści kontekst na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PerThreadReusableStringBuilder {
        /*
         * 🧪 Zadanie 23:
         * Utwórz ThreadLocal<StringBuilder> withInitial(StringBuilder::new)
         * reużywany między wywołaniami tego samego wątku (czyszczony przez
         * setLength(0) zamiast tworzenia nowej instancji). Uruchom 3 wątki,
         * każdy wykonujący 5 wywołań budujących inny napis w swoim
         * StringBuilderze, i zweryfikuj brak wzajemnego "zanieczyszczenia"
         * między wątkami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_LeakOnExceptionPath {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj BŁĘDNĄ wersję zadania w puli 1 wątku, w której
         * ThreadLocal.remove() jest wywoływane PO ryzykownym kodzie (poza
         * finally), więc gdy kod rzuci wyjątek, remove() się nie wykona –
         * zademonstruj wyciek do kolejnego zadania. Następnie popraw kod,
         * przenosząc remove() do finally, i pokaż że wyciek znika nawet gdy
         * zadanie rzuca wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PerThreadMemoizationCache {
        /*
         * 🧪 Zadanie 25:
         * Utwórz ThreadLocal<Map<Integer,Long>> jako pamięć podręczną wyników
         * (np. silni lub Fibonacciego) per wątek. Uruchom 3 wątki liczące
         * wartości dla kilku argumentów (z użyciem cache), wypisz rozmiar
         * mapy cache każdego wątku po zakończeniu i potwierdź, że cache jednego
         * wątku nie jest widoczny dla innych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_InheritableThreadLocalOverrideInChild {
        /*
         * 🧪 Zadanie 26:
         * Ustaw InheritableThreadLocal<String> correlationId = "root-123" w main.
         * Utwórz 2 wątki potomne: pierwszy odczytuje odziedziczoną wartość bez
         * zmian, drugi NADPISUJE ją własną wartością ("child-override"). Po
         * zakończeniu obu wątków sprawdź w main, że jego własna wartość
         * correlationId ("root-123") pozostała niezmieniona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PooledDateFormatServiceTwentyTasks {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj "serwis formatowania dat" na bazie ExecutorService (4 wątki)
         * i ThreadLocal<SimpleDateFormat>. Submituj 20 zadań formatujących
         * różne daty (np. z przesunięciem o N dni). Zweryfikuj (proste
         * sprawdzenie długości/wzorca stringa) poprawność wszystkich 20
         * wyników i wypisz podsumowanie "wszystkie poprawne / liczba błędów".
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AutoCloseableThreadContextScope {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj klasę ThreadContext z metodą statyczną open(String value)
         * zwracającą obiekt implementujący AutoCloseable, który w close()
         * wywołuje ThreadLocal.remove(). Użyj jej w try-with-resources w 3
         * wątkach: try (var scope = ThreadContext.open("ctx-" + i)) { ... }
         * i potwierdź automatyczne, poprawne sprzątanie po wyjściu z bloku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DiagnosticReportThirtyTasksThreePool {
        /*
         * 🧪 Zadanie 29:
         * Uruchom 30 zadań na puli 3 wątków. Każde zadanie ustawia
         * ThreadLocal<Integer> taskId, wykonuje krótki sleep, zapisuje do
         * współdzielonej, bezpiecznej wątkowo listy parę (nazwa wątku, taskId)
         * ZARAZ po ustawieniu (przed remove()), a na końcu wywołuje remove()
         * w finally. Po zakończeniu przeanalizuj listę i wypisz raport: ile
         * zadań obsłużył każdy wątek puli oraz czy któryś taskId powtórzył
         * się nieoczekiwanie (co świadczyłoby o błędzie izolacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WebRequestSimulatorCaseStudy {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj mini symulator obsługi żądań web: pula 4 wątków, 50
         * symulowanych żądań. Każde żądanie ustawia kontekst (requestId,
         * userId, ThreadLocal<SimpleDateFormat> na start) w try, wykonuje
         * "prace" (krótki sleep), formatuje linię logu z aktualnym czasem
         * i kontekstem, po czym w finally usuwa WSZYSTKIE wartości ThreadLocal.
         * Na końcu wypisz: liczbę obsłużonych żądań, liczbę wykrytych wycieków
         * requestId między żądaniami (powinno być 0) oraz przykładowe 5 linii logu.
         */
        public static void main(String[] args) { }
    }
}
