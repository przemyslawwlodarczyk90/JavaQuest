package com.example.javaquest._05_multithreading.Lesson27_Starvation;

public class _Exercises_Lesson27_Starvation {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PlainSynchronizedRace {
        /*
         * 🧪 Zadanie 1:
         * Odtwórz przykład z lekcji: 1 wątek "Ofiara" i 3 wątki "Chciwy-1..3"
         * rywalizujące o ten sam obiekt-lock przez zwykły synchronized, przez
         * 1500 ms (deadline = System.currentTimeMillis() + 1500). Policz przez
         * AtomicInteger, ile razy każdy wątek wszedł do sekcji krytycznej i wypisz wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FairReentrantLockBasics {
        /*
         * 🧪 Zadanie 2:
         * Powtórz Zadanie 1, ale zamiast synchronized użyj new ReentrantLock(true)
         * (tryb fair). Wypisz liczbę wejść każdego wątku i porównaj rozkład
         * z tym, czego oczekujesz po zwykłym synchronized.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ComparePlainVsFairSideBySide {
        /*
         * 🧪 Zadanie 3:
         * Uruchom jedno po drugim: eksperyment z synchronized (1500 ms) i eksperyment
         * z ReentrantLock(true) (1500 ms), z identyczną konfiguracją 1 ofiary + 3
         * chciwych wątków. Wypisz obie tabele wyników jedna pod drugą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ThreadPriorityBasics {
        /*
         * 🧪 Zadanie 4:
         * Utwórz wątek "Ofiara" z priorytetem Thread.MIN_PRIORITY i 3 wątki
         * "Chciwy" z priorytetem Thread.MAX_PRIORITY, rywalizujące o zwykły
         * synchronized przez 1500 ms. Policz wejścia do sekcji krytycznej
         * i wypisz, czy priorytet miał zauważalny wpływ na rozkład.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_BusyWaitHelper {
        /*
         * 🧪 Zadanie 5:
         * Zaimplementuj metodę busyWaitNanos(long nanos) (zajęta pętla bez sleep,
         * jak w lekcji). Użyj jej do symulacji 100 000 ns "pracy" wewnątrz
         * synchronized bloku dla 4 wątków rywalizujących o wspólny lock przez
         * 1000 ms, i wypisz liczbę wejść każdego wątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_PerThreadAtomicCounters {
        /*
         * 🧪 Zadanie 6:
         * Utwórz tablicę AtomicInteger[5] (po jednym na wątek). 5 wątków
         * rywalizuje o wspólny synchronized lock przez 2000 ms, każdy zwiększa
         * swój własny licznik przy każdym wejściu. Po zakończeniu wypisz
         * wszystkie 5 wartości w jednej tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_VictimSharePercentage {
        /*
         * 🧪 Zadanie 7:
         * Na podstawie eksperymentu jak w Zadaniu 1 (1 ofiara + 3 chciwych,
         * synchronized, 1500 ms) oblicz procentowy udział "Ofiary" we wszystkich
         * wejściach do sekcji krytycznej (jej wejścia / suma wszystkich wejść * 100)
         * i wypisz go z dokładnością do 1 miejsca po przecinku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_VaryingGreedyThreadCount {
        /*
         * 🧪 Zadanie 8:
         * Powtórz eksperyment z synchronized dla 3 konfiguracji: 2, 4 i 8 wątków
         * "chciwych" (zawsze + 1 "Ofiara"), każdy przez 1000 ms. Wypisz dla każdej
         * konfiguracji liczbę wejść "Ofiary" i zaobserwuj, jak maleje wraz ze
         * wzrostem liczby rywali.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ReusableDeadlineHelper {
        /*
         * 🧪 Zadanie 9:
         * Napisz metodę pomocniczą runUntilDeadline(long runTimeMs, Runnable
         * criticalSectionWork) zwracającą liczbę wykonanych iteracji jednego
         * wątku w zadanym oknie czasowym (wzorzec deadline z lekcji). Użyj jej
         * do policzenia, ile razy pojedynczy wątek (bez rywali) wchodzi do
         * pustej sekcji krytycznej w 1000 ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_RepeatSameExperimentTwice {
        /*
         * 🧪 Zadanie 10:
         * Uruchom eksperyment z synchronized (1 ofiara + 3 chciwych, 1500 ms)
         * dwa razy pod rząd i wypisz obie tabele wyników obok siebie, komentując
         * czy wyniki się znacząco różnią między przebiegami.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_VaryingCriticalSectionDuration {
        /*
         * 🧪 Zadanie 11:
         * Powtórz eksperyment synchronized (1 ofiara + 3 chciwych, 1500 ms) dla
         * trzech długości pracy w sekcji krytycznej: busyWaitNanos(10_000),
         * busyWaitNanos(50_000), busyWaitNanos(200_000). Wypisz udział "Ofiary"
         * dla każdej długości i skomentuj wpływ na sprawiedliwość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_SixGreedyThreadsFullTable {
        /*
         * 🧪 Zadanie 12:
         * Uruchom 1 wątek "Ofiara" i 6 wątków "Chciwy-1..6" rywalizujących
         * o synchronized przez 2000 ms. Wypisz pełną tabelę wejść wszystkich
         * 7 wątków, posortowaną malejąco wg liczby wejść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PriorityWithSynchronizedOnly {
        /*
         * 🧪 Zadanie 13:
         * Ustaw "Ofierze" Thread.MIN_PRIORITY, a 4 "chciwym" wątkom Thread.MAX_PRIORITY,
         * używając wyłącznie synchronized (bez fair locka), przez 2000 ms.
         * Wypisz wyniki i skomentuj, czy priorytety realnie wpłynęły na rozkład
         * (JVM/OS może je ignorować w praktyce).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FairVsUnfairTotalThroughput {
        /*
         * 🧪 Zadanie 14:
         * Porównaj SUMĘ wszystkich wejść (nie tylko ofiary) w oknie 2000 ms
         * dla synchronized vs ReentrantLock(true), z 1 ofiarą i 4 chciwymi.
         * Wypisz obie sumy i skomentuj, czy fair lock kosztuje coś w postaci
         * niższej całkowitej przepustowości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FairnessStandardDeviation {
        /*
         * 🧪 Zadanie 15:
         * Dla 5 wątków rywalizujących przez 2000 ms oblicz odchylenie standardowe
         * liczby wejść jako "wskaźnik niesprawiedliwości". Porównaj tę wartość
         * dla synchronized i dla ReentrantLock(true) – niższe odchylenie = bardziej
         * sprawiedliwy rozkład.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_FairLockWithTryLockTimeout {
        /*
         * 🧪 Zadanie 16:
         * Użyj ReentrantLock(true) z tryLock(long, TimeUnit) zamiast zwykłego
         * lock(). Ofiara i 3 chciwe wątki próbują wejść z limitem czasu 200 ms
         * na próbę przez 2000 ms. Policz, ile razy każdy wątek doczekał się
         * wejścia, a ile razy przekroczył limit czasu (timeout).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_FiveRunsAggregatedStats {
        /*
         * 🧪 Zadanie 17:
         * Uruchom eksperyment synchronized (1 ofiara + 3 chciwych, 1000 ms) 5 razy
         * pod rząd. Zbierz udział procentowy "Ofiary" z każdego przebiegu i wypisz
         * minimum, maksimum oraz średnią z tych 5 wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SemaphoreBoundedAccess {
        /*
         * 🧪 Zadanie 18:
         * Użyj Semaphore(2) (poznanego w Lekcji 20 – Synchronizers) zamiast
         * zwykłego locka, ograniczając liczbę wątków MOGĄCYCH JEDNOCZEŚNIE pracować
         * do 2, przy 1 ofierze i 4 chciwych rywalizujących przez 2000 ms. Wypisz
         * liczbę wejść każdego wątku i porównaj rozkład z wersją bez semafora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_PriorityEffectDiminishesWithFairLock {
        /*
         * 🧪 Zadanie 19:
         * Połącz priorytety wątków (Ofiara = MIN_PRIORITY, chciwe = MAX_PRIORITY)
         * z ReentrantLock(true) zamiast synchronized, przez 2000 ms. Porównaj
         * wynik z Zadaniem 13 (te same priorytety, ale zwykły synchronized)
         * i wypisz wniosek, czy fair lock "niweluje" wpływ priorytetów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_GenericFairnessExperimentRunner {
        /*
         * 🧪 Zadanie 20:
         * Napisz generyczną metodę runFairnessExperiment(String lockType, int
         * greedyThreadCount, long runTimeMs) zwracającą int[] z liczbą wejść
         * każdego wątku, obsługującą lockType "synchronized" oraz "fair".
         * Wywołaj ją dla obu typów z greedyThreadCount = 3 i runTimeMs = 1500,
         * wypisując oba wyniki.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullComparisonReport {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj pełny raport porównawczy dla identycznej konfiguracji (1 ofiara
         * + 4 chciwych, 2000 ms) w trzech wariantach: synchronized, new
         * ReentrantLock() (niesprawiedliwy) oraz new ReentrantLock(true) (fair).
         * Wypisz tabelę: wariant / liczba wejść ofiary / suma wejść / procent ofiary.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PriorityInversionSimulation {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj "odwrócenie priorytetów": wątek o niskim priorytecie trzyma
         * zwykły synchronized lock przez długi czas (busyWaitNanos(500_000)),
         * podczas gdy wątek o wysokim priorytecie czeka na ten sam lock. Zmierz
         * i wypisz, ile czasu wątek wysokopriorytetowy musiał czekać na swoje
         * pierwsze wejście mimo wyższego priorytetu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_AdaptiveLockSwitching {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj mechanizm, który po "okresie rozgrzewkowym" 500 ms ze
         * zwykłym synchronized sprawdza udział procentowy "Ofiary" – jeśli jest
         * poniżej 10%, automatycznie przełącza dalszą część eksperymentu (kolejne
         * 1500 ms) na ReentrantLock(true). Wypisz moment przełączenia i wyniki przed/po.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MultiResourceStarvation {
        /*
         * 🧪 Zadanie 24:
         * Utwórz 3 współdzielone zasoby (osobne obiekty-locki) i 5 wątków, z których
         * każdy losowo (Random z ustalonym seedem) wybiera jeden zasób do zdobycia
         * przy każdej iteracji, przez 2000 ms, używając synchronized. Wypisz łączną
         * liczbę wejść każdego wątku do dowolnego zasobu i wskaż najbardziej
         * "głodzony" wątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_FairLockQueueLengthMonitoring {
        /*
         * 🧪 Zadanie 25:
         * Użyj ReentrantLock(true) z 1 ofiarą i 4 chciwymi wątkami przez 2000 ms.
         * Co 200 ms (osobny wątek monitorujący) odczytuj lock.getQueueLength()
         * i wypisuj liczbę wątków aktualnie czekających w kolejce, budując
         * przebieg w czasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TimeToFirstEntryComparison {
        /*
         * 🧪 Zadanie 26:
         * Zmierz, ile milisekund mija od startu wątków do PIERWSZEGO wejścia
         * "Ofiary" do sekcji krytycznej, osobno dla synchronized i dla
         * ReentrantLock(true), z tą samą konfiguracją (1 ofiara + 4 chciwych).
         * Wypisz oba czasy i porównaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_GreedyWithSmallRandomPause {
        /*
         * 🧪 Zadanie 27:
         * Zmodyfikuj wątki "chciwe" tak, by po każdym wejściu do sekcji krytycznej
         * (synchronized) czekały losowe 1-5 ms przed ponowną próbą (zamiast
         * natychmiastowego powrotu). Porównaj udział "Ofiary" w tym wariancie
         * z wariantem bez pauzy (Zadanie 1) i wypisz wniosek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AggregatedFairnessReportSixThreads {
        /*
         * 🧪 Zadanie 28:
         * Uruchom eksperyment synchronized z 6 wątkami (1 ofiara + 5 chciwych,
         * 1500 ms) 5 razy pod rząd. Dla każdego przebiegu zapisz udział procentowy
         * ofiary, a na końcu wypisz minimum, maksimum i średnią z tych 5 wartości
         * w formie tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_TicketBasedFairQueue {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj własny mechanizm sprawiedliwej kolejki na bazie
         * BlockingQueue<Integer> jako "biletomatu" (każdy wątek pobiera kolejny
         * numerek i wchodzi do sekcji krytycznej dopiero, gdy jego numerek jest
         * aktualnie obsługiwany). Zweryfikuj z 1 ofiarą i 4 chciwymi przez 2000 ms,
         * że każdy wątek dostaje gwarantowaną, w miarę równą liczbę wejść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_PrinterQueueCaseStudy {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj studium przypadku "kolejka do drukarki": 1 wątek o niskim
         * priorytecie i 4 wątki o wysokim priorytecie rywalizują o dostęp do
         * wspólnego zasobu przez 3000 ms. Porównaj cztery podejścia: zwykły
         * synchronized, niesprawiedliwy ReentrantLock(), sprawiedliwy
         * ReentrantLock(true) oraz własną kolejkę biletową z Zadania 29.
         * Wypisz końcowy ranking podejść od najmniej do najbardziej sprawiedliwego.
         */
        public static void main(String[] args) { }
    }
}
