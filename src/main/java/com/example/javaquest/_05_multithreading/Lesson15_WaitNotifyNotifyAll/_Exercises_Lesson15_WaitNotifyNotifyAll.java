package com.example.javaquest._05_multithreading.Lesson15_WaitNotifyNotifyAll;

public class _Exercises_Lesson15_WaitNotifyNotifyAll {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IllegalMonitorStateExceptionDemo {
        /*
         * 🧪 Zadanie 1:
         * Utwórz zwykły obiekt Object monitor = new Object(). Wywołaj na nim monitor.wait()
         * BEZ bloku synchronized i złap IllegalMonitorStateException, wypisując komunikat
         * wyjątku. Zrób to samo dla monitor.notify().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SimpleWaitNotifyHandoff {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj wspólny obiekt monitor i pole boolean ready = false. Wątek A po 200ms
         * wchodzi w synchronized(monitor), ustawia ready = true i woła notify(). Wątek B
         * wcześniej wchodzi w synchronized(monitor) i w pętli `while (!ready) monitor.wait()`
         * czeka na sygnał, po czym wypisuje "otrzymano sygnał". Bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BoundedBufferCapacityOne {
        /*
         * 🧪 Zadanie 3:
         * Wykorzystaj klasę BoundedBuffer z tej lekcji (lub własną kopię) z pojemnością 1.
         * Producent dokłada dokładnie 3 elementy (1, 2, 3), konsument zabiera dokładnie
         * 3 elementy. Wypisz kolejność operacji put/take. Bounded join (10s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ProducerConsumerFixedCount {
        /*
         * 🧪 Zadanie 4:
         * Wykorzystaj BoundedBuffer o pojemności 5. Producent produkuje dokładnie
         * 10 elementów (liczby 1-10), konsument konsumuje dokładnie 10 elementów.
         * Bounded join (10s) obu wątków. Wypisz sumę skonsumowanych wartości i porównaj
         * z oczekiwaną sumą 1+2+...+10.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NotifyVsNotifyAllSingleWaiter {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj wspólny monitor i JEDNEGO czekającego (while(!ready) wait()).
         * Zademonstruj, że przy dokładnie jednym czekającym wątku notify() budzi go
         * równie skutecznie jak notifyAll() – uruchom dwa niezależne testy (jeden
         * z notify(), jeden z notifyAll()) po 3 razy każdy i wypisz, czy za każdym razem
         * wątek się obudził (bounded join 5s na każdą próbę).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_MultipleConsumersOneProducer {
        /*
         * 🧪 Zadanie 6:
         * Wykorzystaj BoundedBuffer o pojemności 4. Jeden producent produkuje dokładnie
         * 12 elementów. Trzej konsumenci dzielą się pracą, każdy konsumując po 4 elementy
         * (łącznie 12). Bounded join (10s) wszystkich czterech wątków. Zweryfikuj, że
         * suma skonsumowanych elementów przez wszystkich konsumentów wynosi 12.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_WaitWithTimeoutBasic {
        /*
         * 🧪 Zadanie 7:
         * Wątek-ustawiający ustawia warunek ready = true po 300ms. Wątek-czekający
         * wywołuje synchronized monitor.wait(1000) (wersja z timeoutem) w pętli
         * `while (!ready)`. Zmierz (System.nanoTime()) czas oczekiwania i wypisz go –
         * powinien być bliski 300ms, a NIE równy pełnemu timeoutowi 1000ms.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountdownStyleWaiter {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj wspólny monitor i int count = 0. Wątek A 5 razy (co 100ms) wchodzi
         * w synchronized, zwiększa count o 1 i woła notifyAll(). Wątek B czeka w pętli
         * `while (count < 5) monitor.wait()` i po przebudzeniu z osiągniętym warunkiem
         * wypisuje finalną wartość count. Bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_TwoConditionsSameMonitorSafely {
        /*
         * 🧪 Zadanie 9:
         * Na jednym wspólnym monitorze zbuduj DWA rodzaje czekających wątków: jeden czeka
         * na warunek `stageA == true`, drugi na `stageB == true` (każdy w swojej pętli
         * while sprawdzającej WŁASNY warunek). Wątek-ustawiający ustawia stageA=true,
         * woła notifyAll(), po 200ms ustawia stageB=true i znów notifyAll(). Zweryfikuj
         * (bounded join 5s), że obaj czekający poprawnie się kończą mimo współdzielonego
         * monitora – dzięki pętli while, a nie if.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BasicPingPongWithWaitNotify {
        /*
         * 🧪 Zadanie 10:
         * Dwa wątki na wspólnym monitorze i polu boolean pingTurn = true na przemian
         * wypisują "ping" i "pong" – łącznie dokładnie 10 wypisań (5 ping + 5 pong),
         * używając synchronized, wait() i notify()/notifyAll() do przekazywania sobie
         * "tury". Bounded join (5s) obu wątków po zakończeniu 10 wypisań.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BoundedBufferMultipleProducersConsumers {
        /*
         * 🧪 Zadanie 11:
         * Wykorzystaj BoundedBuffer o pojemności 4 z dwoma producentami (każdy produkuje
         * po 5 elementów, łącznie 10) i dwoma konsumentami (każdy konsumuje po 5,
         * łącznie 10). Bounded join (10s) wszystkich czterech wątków. Zweryfikuj sumę
         * skonsumowanych elementów wśród obu konsumentów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WaitTimeoutFallbackPattern {
        /*
         * 🧪 Zadanie 12:
         * Producent po LOSOWYM opóźnieniu (0-800ms) ustawia warunek i woła notifyAll().
         * Konsument czeka w pętli `while (!ready)` używając monitor.wait(200) (timeout),
         * z licznikiem prób ograniczonym do 10 (czyli maks. ~2s łącznego oczekiwania).
         * Wypisz, po ilu próbach (wait-ach) warunek został zauważony. Bounded join (5s).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_PoisonPillShutdown {
        /*
         * 🧪 Zadanie 13:
         * Zbuduj kolejkę (na bazie BoundedBuffer<Integer> lub podobnej struktury)
         * i specjalną wartość-sentinel POISON_PILL (np. Integer.MIN_VALUE). Producent
         * dokłada 8 elementów, a na końcu dokłada POISON_PILL. Konsument w pętli bierze
         * elementy metodą take() dopóki nie otrzyma POISON_PILL, wtedy kończy pętlę.
         * Zweryfikuj, że skonsumowano dokładnie 8 "prawdziwych" elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NotifyAllWithMultipleDistinctConditions {
        /*
         * 🧪 Zadanie 14:
         * Na wspólnym monitorze uruchom 3 wątki czekające na 3 RÓŻNE warunki (flagi
         * conditionA, conditionB, conditionC), każdy w swojej pętli while. Wątek-sterujący
         * ustawia wszystkie trzy flagi na true JEDNYM notifyAll() (po ustawieniu wszystkich
         * flag w tym samym bloku synchronized). Zweryfikuj (bounded join 5s), że wszystkie
         * trzy wątki poprawnie się kończą po jednym wspólnym przebudzeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MeasureWaitDuration {
        /*
         * 🧪 Zadanie 15:
         * Wykonaj 5 rund: w każdej rundzie wątek-czekający wchodzi w wait() (bez
         * timeoutu) o czasie t0 (System.nanoTime()), a wątek-budzący po LOSOWYM
         * opóźnieniu (50-400ms) ustawia warunek i woła notify(). Zmierz i wypisz
         * rzeczywisty czas oczekiwania każdej rundy oraz średnią z 5 rund.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SharedCounterThresholdWaiter {
        /*
         * 🧪 Zadanie 16:
         * Wątek zwiększa wspólny licznik (w bloku synchronized) o 1 co 10ms, aż osiągnie
         * 100, wołając notifyAll() po KAŻDEJ zmianie. Wątek-czekający czeka w pętli
         * `while (counter < 50) wait()` i kończy się natychmiast po przekroczeniu progu
         * 50 (NIE czekając na 100). Wypisz wartość licznika w momencie, gdy czekający
         * się obudził i zakończył pętlę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultiStageHandoffPipeline {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj 3-etapowy potok: StageA -> StageB -> StageC, każdy etap to osobny
         * wątek z WŁASNYM monitorem i flagą "batchReady". StageA przetwarza paczkę
         * 5 zadań i notifyAll() budzi StageB (czekający w pętli while), StageB
         * przetwarza tę samą paczkę i budzi StageC. StageC wypisuje łączną liczbę
         * przetworzonych zadań (powinno być 5). Bounded join (10s) wszystkich etapów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_BoundedBufferDrainAllBatch {
        /*
         * 🧪 Zadanie 18:
         * Wykorzystaj BoundedBuffer o pojemności 5. Producent produkuje dokładnie
         * 20 elementów (liczby 1-20). Jeden konsument zabiera je wszystkie po kolei.
         * Po zakończeniu (bounded join 10s) zsumuj skonsumowane wartości i porównaj
         * z oczekiwaną sumą 1+2+...+20 (czyli 210).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_RendezvousPattern {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj prosty "rendezvous" dla 2 wątków bez CyclicBarrier: obaj muszą
         * "spotkać się" DOKŁADNIE 3 razy. W każdej rundzie każdy wątek wchodzi w
         * synchronized, zwiększa licznik przybyłych, a jeśli licznik < 2, czeka
         * (while(arrived < 2) wait()); gdy oboje przybędą, notifyAll() budzi drugiego
         * i licznik resetuje się do kolejnej rundy. Wypisz numer rundy przy każdym spotkaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_NotifyAllWakesMultipleWaitingConsumers {
        /*
         * 🧪 Zadanie 20:
         * Wykorzystaj BoundedBuffer o pojemności 2. Uruchom 4 wątki-konsumentów, które
         * od razu zaczynają czekać (bufor jest pusty). Producent dokłada 4 elementy
         * jeden po drugim (każdy put() woła notifyAll()). Zweryfikuj (bounded join 10s),
         * że łącznie skonsumowano dokładnie 4 elementy (rozłożone między 4 konsumentów,
         * niekoniecznie po jednym na każdego).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BoundedBufferStressTest {
        /*
         * 🧪 Zadanie 21:
         * Wykorzystaj BoundedBuffer o pojemności 10 z 4 producentami (każdy produkuje
         * po 25 elementów, łącznie 100) i 4 konsumentami (każdy konsumuje po 25, łącznie
         * 100). Bounded join (15s) wszystkich 8 wątków. Zweryfikuj, że suma skonsumowanych
         * elementów przez wszystkich konsumentów wynosi dokładnie 100.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_PoisonPillMultiConsumer {
        /*
         * 🧪 Zadanie 22:
         * Jeden producent produkuje 30 elementów, a następnie dokłada dokładnie
         * 3 sentinele POISON_PILL (po jednym na każdego z 3 konsumentów). Każdy z 3
         * konsumentów bierze elementy dopóki nie odbierze WŁASNEGO poison pill, po czym
         * kończy pracę. Zweryfikuj (bounded join 15s), że suma skonsumowanych
         * "prawdziwych" elementów wynosi 30 i wszystkie 3 wątki się zakończyły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TimedWaitWithRetryBudget {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj czekającego z ograniczonym "budżetem": maks. 5 prób
         * monitor.wait(300) (łącznie do ~1.5s). Przetestuj DWA scenariusze: (a) warunek
         * zostaje ustawiony po 400ms (czekający powinien go zauważyć w ramach budżetu),
         * (b) warunek NIGDY nie zostaje ustawiony (czekający wyczerpuje budżet i kończy
         * się z komunikatem "timeout - rezygnuję", bez zawieszenia programu). Wypisz
         * wynik obu scenariuszy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_TwoWayHandoffProtocol {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj ścisły handoff "tury" między dokładnie 2 wątkami przez 10 rund:
         * w każdej rundzie wątek trzymający turę wypisuje swój numer, przekazuje turę
         * (zmienia flagę na wspólnym monitorze) i woła notifyAll(), po czym sam czeka
         * (while (turn != myTurn) wait()) na swoją kolejną turę. Zweryfikuj w wypisanym
         * logu, że tura ściśle się przeplata (1,2,1,2,...) przez wszystkie 10 rund.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_PriorityQueueStyleBoundedBuffer {
        /*
         * 🧪 Zadanie 25:
         * Zmodyfikuj BoundedBuffer tak, by wewnątrz używał java.util.PriorityQueue<Integer>
         * zamiast LinkedList (reszta logiki wait/notifyAll bez zmian). Producent dokłada
         * 10 losowych liczb (Random z ustalonym seedem), konsument je zabiera. Wypisz
         * kolejność konsumowanych wartości i zweryfikuj, że wychodzą w kolejności
         * rosnącej (od najmniejszej), mimo że zostały dodane w losowej kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MultiRoundProducerConsumerWithStats {
        /*
         * 🧪 Zadanie 26:
         * Wykorzystaj BoundedBuffer o pojemności 5 przez 3 kolejne "rundy" po 10
         * elementów każda (łącznie 30). Zmierz (System.nanoTime()) czas spędzony
         * wewnątrz wait() przy każdym put()/take() (jeśli w ogóle czekano) i zbierz
         * do listy. Po zakończeniu wypisz raport: łączna liczba oczekiwań, średni
         * i maksymalny czas oczekiwania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SynchronizedStateMachineWithWaitNotify {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj klasę TaskProcessor ze stanem synchronized String state = "NEW".
         * Wątek-producent wykonuje 5 "zadań" (Thread.sleep(50) każde), po każdym zadaniu
         * zmienia state na "RUNNING" i woła notifyAll(), a po ukończeniu WSZYSTKICH
         * 5 zadań ustawia state = "DONE" i woła notifyAll(). Wątek-konsument czeka
         * w pętli `while (!"DONE".equals(state)) wait()` i po przebudzeniu wypisuje
         * raport końcowy. Bounded join (10s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GracefulShutdownOfProducerConsumerSystem {
        /*
         * 🧪 Zadanie 28:
         * Rozszerz BoundedBuffer o dodatkową flagę `shutdownRequested`, sprawdzaną
         * w pętlach while zarówno w put(), jak i take() (obok warunków pełności/pustości).
         * Producent dokłada 15 elementów, po czym woła shutdown() (ustawia flagę
         * i notifyAll()). Konsument bierze elementy dopóki bufor niepusty LUB dopóki
         * !shutdownRequested, kończąc pracę gdy oba warunki są spełnione. Zweryfikuj,
         * że skonsumowano dokładnie 15 elementów i żaden wątek się nie zawiesił
         * (bounded join 10s).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiProducerPoisonPillCoordination {
        /*
         * 🧪 Zadanie 29:
         * Uruchom 3 producentów, każdy produkujący 10 elementów (łącznie 30) do
         * wspólnego BoundedBuffer. Wątek-koordynator robi bounded join na wszystkich
         * 3 producentach, a DOPIERO POTEM dokłada DOKŁADNIE JEDEN poison pill. Jeden
         * konsument bierze elementy dopóki nie odbierze poison pill. Zweryfikuj, że
         * skonsumowano dokładnie 30 "prawdziwych" elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullProducerConsumerSystemWithMonitoring {
        /*
         * 🧪 Zadanie 30:
         * Połącz techniki z lekcji: BoundedBuffer o pojemności 5, 2 producentów
         * (po 10 elementów każdy = 20), 2 konsumentów. Wątek-koordynator po zakończeniu
         * obu producentów (bounded join) dokłada 2 poison pille (po jednym na każdego
         * konsumenta). Dodatkowy wątek-monitor co 50ms (limit 200 sprawdzeń, kończy się
         * gdy oba producenty i konsumenty zakończą pracę) wypisuje bieżące zapełnienie
         * bufora. Zweryfikuj, że skonsumowano łącznie 20 elementów i wszystkie wątki
         * zakończyły się (bounded join 15s wszędzie).
         */
        public static void main(String[] args) { }
    }
}
