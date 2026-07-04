package com.example.javaquest._05_multithreading.Lesson08_VisibilityProblem;

public class _Exercises_Lesson08_VisibilityProblem {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_VolatileFlagDeclaration {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj statyczne pole `private static volatile boolean running = true;`.
         * Uruchom wątek roboczy z pętlą `while (running) {}` liczącą iteracje,
         * ustaw `running = false` z wątku main po Thread.sleep(200), a następnie
         * wywołaj join(2000). Wypisz liczbę iteracji i potwierdź, że wątek się zakończył.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_NonVolatileFlagBounded {
        /*
         * 🧪 Zadanie 2:
         * Powtórz demo z lekcji: statyczne pole `boolean running` BEZ volatile,
         * wątek daemon w pętli while(running), main ustawia running=false po 200ms.
         * Użyj join(2000) i sprawdź isAlive() – wypisz, czy wątek zauważył zmianę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_VolatileVsNonVolatileComparison {
        /*
         * 🧪 Zadanie 3:
         * Zbuduj dwie klasy pomocnicze: jedną z polem `boolean flag` (bez volatile)
         * i drugą z `volatile boolean flag`. Dla każdej uruchom wątek daemon czytający
         * flagę w pętli, ustaw flagę na false po 100ms z main i porównaj (join(1000) + isAlive())
         * zachowanie obu wariantów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_MultipleVolatileFields {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj dwa pola: `volatile boolean paused` i `volatile boolean stopped`.
         * Wątek roboczy w pętli sprawdza oba pola (jeśli stopped - kończy, jeśli paused -
         * pomija pracę). Main ustawia paused=true po 100ms, potem stopped=true po kolejnych
         * 100ms. Użyj join(2000) i wypisz finalny stan.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VolatileDoesNotFixAtomicity {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj `volatile int counter = 0`. Uruchom 4 wątki, każdy wykonujący
         * counter++ 100_000 razy (join(10_000)). Wypisz oczekiwany (400_000) i rzeczywisty
         * wynik – pokaż, że mimo volatile wynik wciąż może być błędny (volatile daje
         * tylko widoczność, nie atomowość).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SleepBasedPolling {
        /*
         * 🧪 Zadanie 6:
         * Napisz wątek roboczy, który w pętli co 50ms (Thread.sleep) sprawdza
         * `volatile boolean signal` i wypisuje komunikat, gdy zauważy zmianę na true.
         * Main ustawia signal=true po 300ms. Ogranicz pętlę licznikiem max 20 sprawdzeń
         * jako bezpiecznik i użyj join(3000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IsAliveAfterBoundedJoin {
        /*
         * 🧪 Zadanie 7:
         * Uruchom wątek daemon z pętlą `while (!volatileStop) {}`. Wywołaj join(500)
         * PRZED ustawieniem flagi (celowo za wcześnie) i wypisz isAlive() (powinno być true).
         * Następnie ustaw flagę, wywołaj join(2000) ponownie i wypisz isAlive() (powinno być false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DaemonFlagStop {
        /*
         * 🧪 Zadanie 8:
         * Utwórz wątek daemon liczący iteracje w pętli sterowanej `volatile boolean`.
         * Zademonstruj setDaemon(true) PRZED start() i wyjaśnij (w komentarzu/printcie),
         * dlaczego to zabezpieczenie jest istotne w tej lekcji. Zatrzymaj pętlę po 300ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_PauseResumeWithVolatile {
        /*
         * 🧪 Zadanie 9:
         * Zbuduj prosty mechanizm pauzy: `volatile boolean paused = false` sprawdzane
         * w pętli roboczej (jeśli true - Thread.sleep(10) i kontynuuj, w przeciwnym razie
         * zwiększ licznik). Main: poczekaj 200ms, ustaw paused=true, poczekaj 200ms,
         * ustaw paused=false, poczekaj 200ms, zatrzymaj wątek flagą stop i join(3000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareIterationCounts {
        /*
         * 🧪 Zadanie 10:
         * Uruchom TĘ SAMĄ pętlę liczącą iteracje (while na fladze) w dwóch wariantach:
         * z `volatile boolean` i bez. W obu przypadkach main ustawia flagę po 200ms
         * i wywołuje join(2000). Wypisz liczbę iteracji obu wątków i skomentuj różnicę
         * (albo jej brak – zjawisko jest niedeterministyczne).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoWorkersOneFlag {
        /*
         * 🧪 Zadanie 11:
         * Uruchom DWA wątki robocze czytające WSPÓLNĄ `volatile boolean running` w pętli
         * zliczającej iteracje. Main ustawia running=false po 200ms i wywołuje join(2000)
         * na obu. Wypisz liczby iteracji obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_VolatileProgressCounter {
        /*
         * 🧪 Zadanie 12:
         * Zadeklaruj `volatile int progress = 0`. Wątek roboczy inkrementuje progress
         * w pętli o stałej liczbie kroków (np. 100), z Thread.sleep(10) między krokami.
         * Wątek monitorujący co 30ms odczytuje progress i wypisuje go, dopóki nie osiągnie 100.
         * Użyj join(5000) na obu wątkach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_VolatileImmutableReferenceSwap {
        /*
         * 🧪 Zadanie 13:
         * Zdefiniuj niemutowalny record Config(int limit). Zadeklaruj
         * `volatile Config current = new Config(10)`. Wątek roboczy w pętli (max 500 iteracji)
         * odczytuje current.limit() i sumuje odczyty. Main po 100ms podmienia
         * current na `new Config(20)`. Po join(2000) wypisz sumę i finalny limit.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_VolatileCompoundOpStillBroken {
        /*
         * 🧪 Zadanie 14:
         * Zadeklaruj `volatile long total = 0`. Uruchom 4 wątki wykonujące `total += 3`
         * po 50_000 razy każdy (join(10_000)). Wypisz oczekiwany i rzeczywisty wynik
         * i wyjaśnij (print/komentarz), dlaczego volatile nie chroni operacji złożonej `+=`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ProducerConsumerDataReadyFlag {
        /*
         * 🧪 Zadanie 15:
         * Zadeklaruj `volatile boolean dataReady = false` i `volatile String data = null`.
         * Wątek producenta po 150ms ustawia data="wynik" a POTEM dataReady=true.
         * Wątek konsumenta odpytuje dataReady w pętli (max 200 prób co 10ms) i po jego
         * zauważeniu wypisuje wartość data. Użyj join(3000) na obu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_GracefulShutdownMultipleWorkers {
        /*
         * 🧪 Zadanie 16:
         * Uruchom 3 wątki robocze, każdy w pętli `while (!volatileStop)` zliczający
         * własne iteracje. Main po 300ms ustawia stop=true i wywołuje join(2000)
         * na każdym z nich. Wypisz iteracje każdego wątku oraz potwierdzenie,
         * że wszystkie 3 się zakończyły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_VolatileArraySnapshotSwap {
        /*
         * 🧪 Zadanie 17:
         * Zadeklaruj `volatile int[] snapshot = new int[]{1,2,3}`. Wątek czytający
         * co 20ms (max 20 razy) wypisuje sumę elementów aktualnego snapshotu.
         * Main po 100ms podmienia całą referencję na `new int[]{10,20,30,40}`.
         * Zwróć uwagę, że podmiana referencji jest atomowa – czytelnik zawsze widzi
         * kompletną tablicę (starą lub nową), nigdy "mieszankę".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureNoticeDelay {
        /*
         * 🧪 Zadanie 18:
         * Zmierz (System.nanoTime) czas między ustawieniem `volatile boolean flag=true`
         * w main a momentem, gdy wątek roboczy zauważy zmianę w swojej pętli pollingowej
         * (bez volatile na osobnym polu czasu – po prostu porównaj timestampy z obu wątków
         * po zakończeniu). Ogranicz czekanie join(2000) i wypisz zmierzone opóźnienie w ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TimedStopPattern {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj wzorzec "zatrzymaj po N milisekundach": 2 wątki robocze z `volatile
         * boolean stop`, main śpi 250ms, ustawia stop=true, wywołuje join(2000) na obu
         * i wypisuje, czy oba się zakończyły w oczekiwanym czasie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ConfigHotReload {
        /*
         * 🧪 Zadanie 20:
         * Zdefiniuj niemutowalny record Settings(int threshold, String mode).
         * Zadeklaruj `volatile Settings settings = new Settings(5, "NORMAL")`.
         * Uruchom 2 wątki odczytujące settings co 20ms (max 15 odczytów) i wypisujące je.
         * Main po 150ms podmienia settings na `new Settings(10, "FAST")`. Po join(2000)
         * podsumuj, które odczyty widziały starą, a które nową konfigurację.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_StatusMonitorClass {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj klasę StatusMonitor z polem `private volatile String status = "IDLE"`
         * i metodami setStatus(String)/getStatus(). Uruchom 3 wątki robocze, każdy co 50ms
         * (max 5 razy) ustawiający inny status ("RUNNING","PAUSED","DONE"), oraz 1 wątek
         * obserwatora odczytujący status co 20ms (max 30 razy) i logujący zmiany.
         * Użyj join(3000) na wszystkich i wypisz log zaobserwowanych zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PublishSubscribeLatestMessage {
        /*
         * 🧪 Zadanie 22:
         * Zadeklaruj `volatile String latestMessage = null`. Wątek publikujący ustawia
         * 5 kolejnych komunikatów co 100ms. Trzy wątki subskrybenta odpytują
         * latestMessage co 15ms (max 50 prób) i zliczają, ile RÓŻNYCH komunikatów
         * zaobserwowały (porównując z poprzednio widzianą wartością). Po join(3000)
         * wypisz liczby zaobserwowanych zmian dla każdego subskrybenta.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImmutableSharedWithoutVolatile {
        /*
         * 🧪 Zadanie 23:
         * Zadeklaruj (BEZ volatile) `static final Config config = new Config(42)` –
         * pole final, ustawiane raz przed startem wątków i nigdy potem modyfikowane.
         * Uruchom kilka wątków odczytujących config.limit() wielokrotnie i wypisz,
         * że wszystkie zawsze widzą tę samą, poprawną wartość – wyjaśnij w komentarzu,
         * dlaczego brak volatile tu nie szkodzi (stan nigdy się nie zmienia po starcie wątków).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_BoundedWorkerPoolStopFlag {
        /*
         * 🧪 Zadanie 24:
         * Utwórz tablicę 5 wątków, każdy w pętli `while (!volatileStop)` zliczający
         * własne iteracje. Main po 300ms ustawia stopRequested=true, wywołuje join(2000)
         * na każdym wątku po kolei i raportuje (dla każdego), czy zakończył się w terminie
         * oraz ile iteracji wykonał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_HeartbeatTimestamp {
        /*
         * 🧪 Zadanie 25:
         * Zadeklaruj `volatile long lastHeartbeat`. Wątek roboczy w pętli (max 50 iteracji,
         * Thread.sleep(20) między nimi) aktualizuje lastHeartbeat = System.currentTimeMillis().
         * Wątek main co 100ms (max 10 razy) sprawdza, czy różnica między teraz a lastHeartbeat
         * przekracza 200ms (uznając wątek za "martwy") i wypisuje status "ALIVE"/"STALE".
         * Zakończ przez join(3000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TwoFlagsPauseResumeBounded {
        /*
         * 🧪 Zadanie 26:
         * Zadeklaruj `volatile boolean paused` i `volatile boolean stop`. Wątek roboczy
         * wykonuje maks. 1000 "kroków pracy", pomijając krok (Thread.sleep(5)) gdy paused.
         * Main naprzemiennie ustawia paused true/false kilka razy co 100ms, a na końcu
         * ustawia stop=true. Użyj join(5000) i wypisz liczbę faktycznie wykonanych kroków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_MultiTrialVisibilityHarness {
        /*
         * 🧪 Zadanie 27:
         * Powtórz demo z lekcji (non-volatile flag, wątek daemon liczący iteracje,
         * main ustawia flagę po 200ms, join(2000)) w pętli 5 prób. Dla każdej próby
         * wypisz, czy wątek "zauważył" zmianę w terminie, a na końcu podsumuj
         * (ile prób z 5 zakończyło się zauważeniem) – zilustruj niedeterminizm.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_SafePublicationImmutableList {
        /*
         * 🧪 Zadanie 28:
         * Zadeklaruj `volatile List<String> items = List.of()`. Wątek producencki
         * co 100ms (3 razy) podmienia items na NOWĄ niemutowalną listę (List.copyOf
         * poprzedniej + jeden nowy element). Kilka wątków czytelników iteruje co 30ms
         * (max 20 razy) po aktualnej referencji items bez żadnej dodatkowej synchronizacji
         * i wypisuje jej rozmiar. Po join(3000) wyjaśnij, dlaczego to bezpieczne (safe
         * publication przez volatile + niemutowalność listy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SafetyNetIterationCap {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj wątek roboczy z pętlą sterowaną `volatile boolean stop`, ale DODATKOWO
         * z twardym licznikiem bezpieczeństwa (np. max 5_000_000 iteracji) – pętla kończy
         * się, gdy stop==true LUB licznik osiągnie limit, co pierwsze. Main ustawia stop
         * po 200ms. Wypisz, który warunek faktycznie zakończył pętlę oraz liczbę iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_ComprehensiveVisibilityReport {
        /*
         * 🧪 Zadanie 30:
         * Połącz elementy tej lekcji w jeden raport: uruchom 3 warianty tego samego
         * scenariusza (flaga stopu odczytywana w pętli) – (a) bez volatile, (b) z volatile,
         * (c) z volatile + limit bezpieczeństwa iteracji – każdy z bounded join(2000).
         * Dla każdego wariantu wypisz: czy wątek się zakończył w terminie, po ilu
         * iteracjach, i krótkie podsumowanie różnic między wariantami.
         */
        public static void main(String[] args) { }
    }
}
