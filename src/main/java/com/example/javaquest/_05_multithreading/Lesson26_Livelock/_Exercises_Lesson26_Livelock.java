package com.example.javaquest._05_multithreading.Lesson26_Livelock;

public class _Exercises_Lesson26_Livelock {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PoliteStepAsideSimulation {
        /*
         * 🧪 Zadanie 1:
         * Utwórz dwa wątki "Osoba-A" i "Osoba-B" oraz dwie zmienne volatile boolean
         * reprezentujące ich intencję przejścia. Zaimplementuj pętlę z maksymalnie
         * 5 próbami: jeśli obie flagi są ustawione jednocześnie – obie "strony"
         * grzecznie się cofają (jak w przykładzie z lekcji) i próbują ponownie.
         * Wypisuj numer próby przy każdej kolizji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CountAttemptsBeforeGivingUp {
        /*
         * 🧪 Zadanie 2:
         * Ustaw MAX_ATTEMPTS = 8. Uruchom symulację livelocka z dwoma wątkami
         * (jak w teorii) i policz, ile prób wykonał każdy wątek zanim się poddał
         * lub przeszedł. Wypisz "PRZESZEDL po N probach" albo "PODDAL SIE po
         * 8 probach" dla każdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BusyVsBlockedDemo {
        /*
         * 🧪 Zadanie 3:
         * Uruchom dwa wątki: jeden wykonujący pętlę livelockową (ciągle aktywny),
         * drugi zablokowany na zwykłym synchronized czekający na zajęty obiekt-lock.
         * Co 50 ms przez 500 ms próbkuj Thread.getState() obu wątków i wypisz
         * sekwencję stanów, pokazując że jeden to głównie RUNNABLE, a drugi BLOCKED.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TwoLocksNaiveOrdering {
        /*
         * 🧪 Zadanie 4:
         * Utwórz dwa ReentrantLock: lockA i lockB. Wątek-1 próbuje tryLock(lockA),
         * potem tryLock(lockB); jeśli drugi się nie uda, zwalnia lockA i próbuje
         * ponownie (maksymalnie 6 razy). Wątek-2 robi to samo w odwrotnej kolejności.
         * Wypisz wynik (sukces/porazka) po zakończeniu obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VolatileFlagCollisionCounter {
        /*
         * 🧪 Zadanie 5:
         * Rozszerz symulację korytarza o AtomicInteger collisionCounter,
         * zwiększany za każdym razem, gdy obie flagi "chce przejsc" są ustawione
         * jednocześnie. Ogranicz demonstrację do 10 prób na wątek i wypisz
         * finalną wartość licznika kolizji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SleepQuietHelper {
        /*
         * 🧪 Zadanie 6:
         * Napisz metodę pomocniczą sleepQuiet(long millis) (łapiącą InterruptedException
         * i przywracającą flagę przerwania). Użyj jej do wstawienia 40 ms pauzy
         * pomiędzy kolejnymi (maks. 5) próbami w prostej pętli livelockowej
         * jednego wątku, wypisując numer każdej próby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DaemonThreadsForSafeDemo {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj demonstrację livelocka z dwoma wątkami DEMONICZNYMI
         * (setDaemon(true) przed start()) i join(2000) na obu. Zmierz całkowity
         * czas wykonania (System.currentTimeMillis) i wypisz go, dowodząc że
         * demonstracja kończy się bezpiecznie w rozsądnym czasie (poniżej 3 s).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_WhoStepsAsideAlways {
        /*
         * 🧪 Zadanie 8:
         * Zaimplementuj regułę: wątek "A" ZAWSZE ustępuje przy kolizji, wątek "B"
         * NIGDY nie ustępuje. Uruchom obie strony i wypisz, że B przechodzi od razu
         * (bez ustępowania), a A robi to dopiero po ustąpieniu miejsca B.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PrintFinalOutcome {
        /*
         * 🧪 Zadanie 9:
         * Uruchom naiwną (symetryczną) symulację livelocka z limitem 5 prób na
         * wątek. Dla każdego z dwóch wątków wypisz jedno z dwóch podsumowań:
         * "PASSED po X probach" lub "GAVE_UP po 5 probach", a na końcu łączny
         * czas trwania demonstracji w milisekundach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareAttemptsAcrossRuns {
        /*
         * 🧪 Zadanie 10:
         * Uruchom tę samą naiwną symulację livelocka (limit 5 prób) 5 razy pod
         * rząd. Dla każdego przebiegu zapisz liczbę prób zużytych przez wątek A
         * do listy, a na końcu wypisz wszystkie wyniki oraz ich średnią.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_RandomJitterFix {
        /*
         * 🧪 Zadanie 11:
         * Zmodyfikuj pętlę retry z kolizją tak, by po wykryciu kolizji wątek
         * czekał LOSOWY czas (Random z ustalonym seedem, zakres 10-100 ms)
         * zamiast stałego. Ustaw limit 10 prób i wypisz, po ilu próbach udało
         * się rozwiązać kolizję dzięki rozjechaniu się w czasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_PriorityBasedResolution {
        /*
         * 🧪 Zadanie 12:
         * Uruchom 4 wątki o ID 0-3 rywalizujące o wspólny "korytarz" (jedna
         * flaga zajętości). Zasada: wątek o NIŻSZYM ID nigdy nie ustępuje,
         * wątek o wyższym ID zawsze ustępuje niższemu. Zweryfikuj (i wypisz),
         * że żaden wątek nie potrzebuje więcej niż 1 próby na rozstrzygnięcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ThreeThreadCorridor {
        /*
         * 🧪 Zadanie 13:
         * Rozszerz klasę Corridor z lekcji do obsługi 3 wątków (boolean[3]
         * z flagami intencji). Każdy wątek ma limit 6 prób. Wypisz, który
         * wątek przeszedł pierwszy, oraz statystyki prób dla każdego z nich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_WatchdogStopsDemo {
        /*
         * 🧪 Zadanie 14:
         * Utwórz osobny wątek-watchdog (demoniczny), który po 1500 ms wypisuje
         * "WATCHDOG: zatrzymuje demonstracje livelocka" i wywołuje interrupt()
         * na dwóch wątkach roboczych. Wątki robocze w pętli livelockowej
         * sprawdzają Thread.currentThread().isInterrupted() i kończą się czysto.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureCpuUsageDuringLivelock {
        /*
         * 🧪 Zadanie 15:
         * Uruchom naiwną symetryczną symulację livelocka z limitem czasowym
         * 1000 ms (deadline = System.currentTimeMillis() + 1000, tak jak
         * w przykładzie starvation z Lekcji 27) zamiast limitu liczby prób.
         * Zlicz sumaryczną liczbę wykonanych iteracji obu wątków i wypisz ją
         * jako dowód, że wątki były cały czas AKTYWNE (RUNNABLE), a nie zablokowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_TryLockBasedLivelock {
        /*
         * 🧪 Zadanie 16:
         * Utwórz ReentrantLock lockA i lockB. Dwa wątki próbują zdobyć oba locki
         * przez tryLock() w przeciwnej kolejności; przy niepowodzeniu zwalniają
         * to, co zdobyły, i próbują ponownie (limit 10 prób). Wypisz wynik,
         * a następnie napraw problem, wymuszając na obu wątkach TĘ SAMĄ kolejność
         * zdobywania locków (np. zawsze lockA przed lockB) i pokaż, że wtedy
         * rozwiązanie następuje od razu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExponentialBackoffRetry {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj pętlę retry z rosnącym opóźnieniem: 10 ms, 20 ms, 40 ms,
         * 80 ms... (podwajane), maksymalnie 6 prób. Wypisz użyte opóźnienie
         * przy każdej próbie oraz finalny wynik (sukces lub poddanie się).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareSymmetricVsAsymmetricStrategy {
        /*
         * 🧪 Zadanie 18:
         * Uruchom scenariusz korytarza dwukrotnie z limitem 5 prób: raz z regułą
         * symetryczną (obie strony ustępują, jak w teorii – najpewniej obie
         * się poddadzą) i raz z regułą asymetryczną (tylko jedna strona ustępuje).
         * Wypisz liczbę zużytych prób w obu wariantach i krótki wniosek porównawczy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AtomicCountersForBothThreads {
        /*
         * 🧪 Zadanie 19:
         * Użyj dwóch AtomicInteger do niezależnego zliczania prób wykonanych
         * przez każdy z 2 wątków w naiwnej symulacji livelocka (limit 5 prób).
         * Po zakończeniu wypisz obie wartości i sprawdź, czy są w przybliżeniu
         * równe (co potwierdza symetryczne zachowanie obu stron).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GenericPoliteYieldUtility {
        /*
         * 🧪 Zadanie 20:
         * Napisz generyczną metodę attemptWithPoliteYield(Supplier<Boolean>
         * tryAcquire, Runnable onYield, int maxAttempts) zwracającą boolean
         * (czy się udało). Użyj jej dwukrotnie: raz symulując próbę wejścia
         * do korytarza, raz próbę zdobycia locka przez tryLock(), z maxAttempts = 6.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DiningPhilosophersTryLockBounded {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj wariant "ucztujących filozofów" (3 filozofów, 3 widelce
         * jako ReentrantLock) używając WYŁĄCZNIE tryLock() (nigdy blokującego
         * lock()) z limitem 8 rund na filozofa – jeśli nie zdobędzie obu widelców,
         * odkłada te które ma i próbuje w kolejnej rundzie. Wypisz, ile razy
         * każdy filozof zjadł, a ile razy musiał się wycofać.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FairnessMetricReport {
        /*
         * 🧪 Zadanie 22:
         * Uruchom naiwną symetryczną symulację korytarza (limit 5 prób) 10 razy
         * z rzędu, za każdym razem z innym seedem Random do losowych opóźnień.
         * Zbuduj raport: ile z 10 przebiegów zakończyło się PASSED dla strony A,
         * ile dla strony B, a ile obie strony się poddały. Wypisz tabelę wyników.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_LivelockVsDeadlockComparativeDemo {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj obok siebie dwie bezpieczne demonstracje: (1) klasyczny
         * 2-lockowy deadlock, ale zabezpieczony przez tryLock(timeout) tak, by
         * na pewno się zakończył, (2) livelock z korytarza (limit prób). Podczas
         * obu demonstracji próbkuj Thread.getState() wątków co 50 ms przez 500 ms
         * i wypisz sekwencje stanów, porównując wzorzec BLOCKED/WAITING (deadlock)
         * z wzorcem RUNNABLE (livelock).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ThroughputComparisonNaiveVsFixed {
        /*
         * 🧪 Zadanie 24:
         * W oknie czasowym 2000 ms (deadline-based, jak w Lekcji 27) policz,
         * ile razy w sumie udało się "przejść korytarzem" przy naiwnej strategii
         * symetrycznej, a ile przy strategii naprawionej (jedna strona zawsze
         * ustępuje). Wypisz porównanie przepustowości obu podejść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MultiResourceLivelockSimulation {
        /*
         * 🧪 Zadanie 25:
         * Zasymuluj 4 wątki-pracowników, z których każdy potrzebuje 2 z 3
         * współdzielonych zasobów (ReentrantLock[3]) zdobywanych przez tryLock()
         * z losowym opóźnieniem między próbami (limit 8 prób na pracownika).
         * Wypisz, którzy pracownicy zdobyli komplet zasobów, a którzy się poddali.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SelfHealingRetryStrategy {
        /*
         * 🧪 Zadanie 26:
         * Zaimplementuj strategię retry, która przez pierwsze 3 próby jest
         * symetryczna (obie strony ustępują przy kolizji), a od 4. próby
         * automatycznie przełącza się w tryb asymetryczny (strona o niższym
         * hashCode() nazwy wątku przestaje ustępować). Wypisz przebieg prób
         * i pokaż, że dzięki "samo-naprawie" livelock zawsze zostaje rozwiązany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LivelockDetectionHeuristic {
        /*
         * 🧪 Zadanie 27:
         * Za pomocą AtomicInteger śledź liczbę nieudanych prób każdego z 2
         * wątków. Gdy licznik danego wątku przekroczy próg THRESHOLD = 4,
         * oznacz go jako "possibly livelocked" i zmień jego strategię na
         * "przestań ustępować" dla pozostałych prób (limit 10). Wypisz moment
         * wykrycia i finalny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_StatisticalRunComparison {
        /*
         * 🧪 Zadanie 28:
         * Uruchom scenariusz korytarza 20 razy z losowym jitterem (naprawiona
         * wersja) i 20 razy z deterministyczną strategią naiwną (limit 5 prób
         * w obu przypadkach). Oblicz i wypisz średnią liczbę prób potrzebnych
         * do rozstrzygnięcia oraz procent przebiegów zakończonych "poddaniem się"
         * dla każdej z dwóch strategii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_WatchdogWithGracefulShutdownReport {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj wątek-watchdog (demoniczny), który co 100 ms sprawdza
         * dwa AtomicInteger z licznikami prób wątków roboczych. Gdy suma prób
         * przekroczy 30 (sygnał prawdopodobnego livelocka), watchdog wywołuje
         * interrupt() na obu wątkach i wypisuje raport diagnostyczny: liczbę
         * prób każdego wątku, łączny czas trwania i werdykt "LIVELOCK WYKRYTY".
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullLivelockToolkit {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z tej lekcji (ograniczone próby, losowy jitter,
         * priorytetowy fallback po przekroczeniu progu, watchdog) w jedną
         * klasę narzędziową LivelockGuard, użytą w scenariuszu "rezerwacja
         * sali na to samo spotkanie" przez 2 wątki. Wypisz pełny raport
         * diagnostyczny rozwiązania kolizji (liczba prób, zastosowana strategia,
         * kto zarezerwował salę, czas trwania).
         */
        public static void main(String[] args) { }
    }
}
