package com.example.javaquest._05_multithreading.Lesson12_Monitor;

public class _Exercises_Lesson12_Monitor {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_TwoThreadsDifferentMonitorsParallel {
        /*
         * 🧪 Zadanie 1:
         * Utwórz dwa osobne obiekty monitorA i monitorB. Uruchom 2 wątki, każdy
         * wykonujący `synchronized (swojObiekt) { Thread.sleep(300); }`. Zmierz czas
         * całkowity (join(5_000)) i wypisz go – powinien być bliski ~300ms (równolegle).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_TwoThreadsSameMonitorSerialized {
        /*
         * 🧪 Zadanie 2:
         * Utwórz JEDEN wspólny obiekt monitor. Uruchom 2 wątki, każdy wykonujący
         * `synchronized (monitor) { Thread.sleep(300); }`. Zmierz czas całkowity
         * (join(5_000)) i wypisz go – powinien być bliski ~600ms (sekwencyjnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ReentrantMonitorSingleThread {
        /*
         * 🧪 Zadanie 3:
         * Napisz synchronized metodę A() wywołującą inną synchronized metodę B() na TYM
         * SAMYM obiekcie. Wywołaj A() z jednego wątku 1000 razy (join(5_000)) i potwierdź
         * brak blokady (monitor jest reentrant).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ThreeObjectsThreeMonitorsParallel {
        /*
         * 🧪 Zadanie 4:
         * Utwórz 3 osobne obiekty. Uruchom 3 wątki, każdy synchronizujący się na INNYM
         * obiekcie i wykonujący Thread.sleep(300). Zmierz czas całkowity (join(5_000))
         * i potwierdź, że jest bliski ~300ms (wszystkie równolegle).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ThreeThreadsSameMonitorFullySerialized {
        /*
         * 🧪 Zadanie 5:
         * Utwórz JEDEN wspólny obiekt monitor. Uruchom 3 wątki, każdy synchronizujący się
         * na nim i wykonujący Thread.sleep(300). Zmierz czas całkowity (join(5_000))
         * i potwierdź, że jest bliski ~900ms (pełna serializacja).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NestedSynchronizedThisReentrancy {
        /*
         * 🧪 Zadanie 6:
         * Napisz synchronized metodę instancyjną, która wewnątrz zawiera dodatkowo
         * `synchronized (this) { ... }`. Wywołaj ją z jednego wątku 1000 razy (join(3_000))
         * i potwierdź, że zagnieżdżenie na tym samym monitorze działa bez problemu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TwoCountersTwoMonitorsNoBlocking {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj dwa liczniki, każdy chroniony INNYM obiektem-monitorem. Uruchom 2 wątki
         * (jeden per licznik) wykonujące po 100_000 inkrementacji (join(10_000)).
         * Wypisz oba wyniki (oczekiwane 100_000 każdy) i potwierdź brak wzajemnego
         * blokowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_TwoInstancesOwnMonitorsTiming {
        /*
         * 🧪 Zadanie 8:
         * Zbuduj klasę z synchronized metodą doWork() (Thread.sleep(200)). Utwórz DWIE
         * instancje, użyte przez 2 osobne wątki. Zmierz czas całkowity (join(3_000))
         * i potwierdź, że jest bliski ~200ms (różne monitory instancji = brak blokowania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_NestedTwoDistinctMonitorsSingleThread {
        /*
         * 🧪 Zadanie 9:
         * Napisz kod, w którym jeden wątek wchodzi w `synchronized (obiektA)`, a wewnątrz
         * dodatkowo w `synchronized (obiektB)` (dwa RÓŻNE obiekty). Wywołaj to 1000 razy
         * (join(3_000)) i potwierdź poprawne zakończenie bez blokady.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ReproduceLessonDemoOwnDurations {
        /*
         * 🧪 Zadanie 10:
         * Odtwórz demo z lekcji z własnymi czasami: wątki X/Y na różnych monitorach
         * (Thread.sleep(250) każdy) oraz wątki P/Q na WSPÓLNYM monitorze (Thread.sleep(250)
         * każdy). Zmierz oba scenariusze (join(5_000)) i wypisz zmierzone czasy obok siebie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FourPrintersOwnMonitorsOverlapCheck {
        /*
         * 🧪 Zadanie 11:
         * Utwórz tablicę 4 obiektów "drukarek" (każda to osobny monitor). Uruchom 8
         * wątków, każdy losowo (ustalony seed) wybierający jedną z 4 drukarek i wykonujący
         * na jej monitorze Thread.sleep(100), zapisując czas start/end (join(10_000)).
         * Po zakończeniu sprawdź (post-hoc, porównując przedziały czasowe), że użycia TEJ
         * SAMEJ drukarki nigdy się nie nakładały, a różnych drukarek mogły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReentrantRecursiveDepthCheck {
        /*
         * 🧪 Zadanie 12:
         * Napisz rekurencyjną metodę synchronized (this) wywołującą samą siebie do
         * ograniczonej głębokości (np. 5). Wywołaj ją z jednego wątku i wypisz osiągniętą
         * głębokość, potwierdzając że reentrancy pozwala na rekurencję bez blokady.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FixedLockOrderTwoMonitorsNoHang {
        /*
         * 🧪 Zadanie 13:
         * Utwórz monitorA i monitorB. Napisz 2 wątki, z których KAŻDY zawsze najpierw
         * synchronizuje się na monitorA, POTEM na monitorB (ta sama, ustalona kolejność
         * dla obu wątków), wykonując krótką pracę (Thread.sleep(10)) 100 razy (join(5_000)).
         * Potwierdź brak zawieszenia dzięki spójnej kolejności blokad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MonitorStressTestFourSharedMonitors {
        /*
         * 🧪 Zadanie 14:
         * Utwórz 4 obiekty-monitory z osobnymi licznikami. Uruchom 8 wątków, każdy
         * synchronizujący się na monitorze (threadIndex % 4) i inkrementujący
         * odpowiadający mu licznik ustaloną liczbę razy (join(10_000)). Zsumuj wszystkie
         * 4 liczniki i porównaj z całkowitą liczbą wykonanych operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ContentionRatioBenchmark {
        /*
         * 🧪 Zadanie 15:
         * Zmierz czas dla N=4 wątków wykonujących identyczną sekcję krytyczną
         * (Thread.sleep(50)) w dwóch wariantach: (a) N osobnych monitorów, (b) 1 wspólny
         * monitor. Wypisz oba czasy oraz iloraz (b)/(a) ilustrujący liczbowo koszt
         * rywalizacji o wspólny monitor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ResourcePoolMaxTwoConcurrent {
        /*
         * 🧪 Zadanie 16:
         * Utwórz 2 obiekty-zasoby (2 monitory) i licznik "aktywnych użyć" chroniony
         * WŁASNYM, osobnym monitorem. Uruchom 4 wątki, każdy synchronizujący się na
         * zasobie (threadIndex % 2), inkrementujący licznik aktywnych na wejściu
         * i dekrementujący na wyjściu z krótką pracą pomiędzy (join(5_000)). Zweryfikuj,
         * że licznik aktywnych nigdy (w miarę możliwości obserwacji) nie przekroczył 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_WalletTransferNestedReentrancy {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj klasę Wallet z synchronized metodą transfer(int amount) wywołującą
         * wewnętrznie synchronized (this) blok do aktualizacji salda (celowe zagnieżdżenie).
         * Uruchom 4 wątki wykonujące ustaloną liczbę operacji na WSPÓLNYM Wallet
         * (join(10_000)) i zweryfikuj poprawność finalnego salda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CachedIntegerAutoboxingLockPitfall {
        /*
         * 🧪 Zadanie 18:
         * Zsynchronizuj się na `Integer.valueOf(100)` (wartość w zakresie cache -128..127)
         * z dwóch NIEZWIĄZANYCH ze sobą fragmentów kodu i pokaż (przez timing z
         * Thread.sleep), że przypadkowo dzielą ten sam monitor. Popraw, używając
         * dedykowanego `private final Object lock` w każdym fragmencie i pokaż,
         * że teraz działają równolegle.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AccidentalSharedStaticLockCollision {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj dwie NIEPOWIĄZANE klasy, które przez pomyłkę synchronizują się na tym
         * samym statycznym obiekcie. Pokaż (timing 2 wątków, po jednym z każdej klasy,
         * Thread.sleep(200) każdy) że ich operacje nieoczekiwanie się serializują.
         * Popraw, nadając każdej klasie WŁASNY prywatny lock i zmierz ponownie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MonitorEntryExitLogger {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj mały logger zapisujący (do współdzielonej, osobno zsynchronizowanej
         * listy) wpisy "wątek X wszedł/wyszedł z monitora Y" z znacznikiem czasu
         * względnego (ms). Uruchom 3 wątki i 2 monitory w ustalonym scenariuszu
         * (join(5_000)), wypisz posortowany log i wskaż w komentarzu, które wpisy
         * (ten sam monitor) nigdy nie mogły się nakładać.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ChatRoomsOwnMonitorPerRoom {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj klasę Room z listą wiadomości (List<String>) chronioną synchronized(this).
         * Utwórz 3 instancje Room, po 3 wątki na każdą, dodające ustaloną liczbę wiadomości
         * (join(10_000)). Zweryfikuj finalny rozmiar listy każdego pokoju i że wiadomości
         * z różnych pokoi nigdy się nie wymieszały.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiAccountBankAdjustVerified {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj kilka obiektów Account, każdy z `synchronized void adjust(int delta)`
         * (blokada na `this`). Podaj ustaloną (seed) listę operacji wpłat/wypłat
         * rozdzieloną losowo między konta i wykonaj ją wieloma wątkami (join(10_000)).
         * Porównaj finalne saldo każdego konta z wartością obliczoną sekwencyjnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_NestedBuildingRoomLockProtocol {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj obiekt Building (monitor zewnętrzny, sprawdza limit wejść) i kilka
         * obiektów Room (monitory wewnętrzne). Wątek "wchodzący" najpierw synchronizuje
         * się na Building, potem na konkretnym Room, wykonuje krótką pracę i zwalnia oba.
         * Uruchom kilka wątków przez ten protokół z bounded join(5_000) i zweryfikuj,
         * że wszystkie się zakończyły bez zawieszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ScalingContentionTable {
        /*
         * 🧪 Zadanie 24:
         * Dla liczby wątków 2, 4, 8 uruchamiających identyczną krótką sekcję krytyczną
         * (Thread.sleep(30)) na JEDNYM wspólnym monitorze, zmierz i wypisz tabelę czasów
         * całkowitych, ilustrującą jak rośnie czas wraz z liczbą wątków rywalizujących
         * o ten sam monitor.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SortedLockOrderTwoResourcesSafe {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj dwa obiekty-zasoby z przypisanym numerycznym id. Napisz metodę
         * useBoth(resourceX, resourceY), która ZAWSZE synchronizuje się najpierw na
         * zasobie o MNIEJSZYM id, potem na drugim (niezależnie od kolejności argumentów).
         * Uruchom kilka wątków wywołujących useBoth z różną kolejnością argumentów
         * (join(5_000) jako bezpiecznik) i potwierdź brak zawieszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ShardedCounterFourMonitorsSum {
        /*
         * 🧪 Zadanie 26:
         * Podziel logiczny licznik na 4 fragmenty (shardy), każdy z WŁASNYM monitorem.
         * Uruchom wiele wątków inkrementujących losowy (hash threadId % 4) shard ustaloną
         * liczbę razy (join(10_000)). Zsumuj 4 shardy i porównaj z oczekiwaną sumą,
         * komentując (print) zmniejszoną rywalizację względem jednego wspólnego monitora.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_EventBusSubscribePublishVerified {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj EventBus z listą subskrybentów (Runnable) chronioną synchronized(this)
         * dla subscribe()/publish(). Uruchom kilka wątków subskrybujących ustalone
         * handlery i kilka wątków publikujących ustaloną liczbę zdarzeń (join(10_000)).
         * Zweryfikuj, że każdy handler otrzymał dokładnie oczekiwaną liczbę wywołań.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FairnessObservationExperiment {
        /*
         * 🧪 Zadanie 28:
         * Uruchom 1 "zachłanny" wątek wielokrotnie (ograniczona liczba, np. 100_000 razy)
         * ponownie wchodzący na TEN SAM monitor w ciasnej pętli, obok kilku "normalnych"
         * wątków próbujących wejść na ten sam monitor niewiele razy każdy. Zmierz
         * (z bounded join(10_000)) średni czas oczekiwania normalnych wątków i wypisz
         * wynik, komentując że synchronized nie gwarantuje kolejności FIFO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_LockMapSortedAcquisitionOrder {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj Map<String,Object> z dedykowanymi obiektami-blokadami dla ustalonego
         * zestawu nazw zasobów. Napisz metodę acquireBoth(nazwaA, nazwaB) synchronizującą
         * się ZAWSZE w kolejności alfabetycznej nazw (nigdy bezpośrednio na Stringach!).
         * Uruchom kilka wątków potrzebujących różnych par zasobów (join(5_000)) i
         * potwierdź brak zawieszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_SeatReservationSystemCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj tablicę N obiektów "miejsc" (Seat), każdy z polem `boolean reserved`
         * chronionym własnym monitorem (synchronized(this) w metodzie tryReserve()).
         * Uruchom wiele wątków próbujących zarezerwować losowe (ustalony seed), czasem
         * pokrywające się miejsca ustaloną liczbę razy (join(10_000)). Zweryfikuj (przez
         * osobno zsynchronizowany log) że żadne miejsce nie zostało zarezerwowane przez
         * dwa wątki jednocześnie, i wypisz finalną liczbę udanych rezerwacji per miejsce.
         */
        public static void main(String[] args) { }
    }
}
