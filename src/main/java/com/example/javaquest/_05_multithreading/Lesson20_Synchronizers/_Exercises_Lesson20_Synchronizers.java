package com.example.javaquest._05_multithreading.Lesson20_Synchronizers;

public class _Exercises_Lesson20_Synchronizers {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CountDownLatchBasics {
        /*
         * 🧪 Zadanie 1:
         * Utwórz CountDownLatch(3) i ExecutorService z 3 wątkami. Każdy z 3 workerów
         * niech "pracuje" (Thread.sleep 50-150ms) i na końcu wywoła countDown().
         * Wątek main niech wypisze "czekam..." i wywoła await(5, TimeUnit.SECONDS),
         * a po zakończeniu oczekiwania wypisze "wszyscy gotowi!". Pamiętaj o
         * shutdown()+awaitTermination() na koniec.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CountDownLatchFromMultipleThreads {
        /*
         * 🧪 Zadanie 2:
         * Utwórz CountDownLatch(5) i 5 wątków, z których każdy śpi losową liczbę
         * milisekund (100-300ms), wypisuje swój numer i wywołuje countDown().
         * Główny wątek wywołuje await(3, TimeUnit.SECONDS) i wypisuje wynik (true/false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CyclicBarrierBasics {
        /*
         * 🧪 Zadanie 3:
         * Utwórz CyclicBarrier(3) (bez akcji) i 3 wątki, z których każdy śpi losowo
         * (20-100ms), wypisuje "dotarłem do bariery" i wywołuje barrier.await(5, TimeUnit.SECONDS).
         * Po przejściu bariery każdy wątek wypisuje "ruszam dalej".
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CyclicBarrierWithAction {
        /*
         * 🧪 Zadanie 4:
         * Utwórz CyclicBarrier(4, akcja) gdzie akcja (Runnable) wypisuje
         * "Wszyscy 4 dotarli - rusza kolejny etap!". Uruchom 4 wątki, które
         * wywołują barrier.await() dokładnie raz każdy. Zweryfikuj, że akcja
         * wypisuje się dokładnie raz, dopiero gdy WSZYSTKIE wątki dotrą do bariery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SemaphoreMutex {
        /*
         * 🧪 Zadanie 5:
         * Utwórz Semaphore(1) (działa jak mutex) i 3 wątki próbujące jednocześnie
         * "wejść do sekcji krytycznej": acquire(), wypisać swój numer i "jestem w środku",
         * sleep(100ms), wypisać "wychodzę" i release(). Zweryfikuj (po logach), że
         * nigdy dwa wątki nie są "w środku" jednocześnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SemaphoreAvailablePermits {
        /*
         * 🧪 Zadanie 6:
         * Utwórz Semaphore(3). Przed i po każdym acquire()/release() wykonanym
         * przez 5 wątków (każdy raz) wypisuj wynik semaphore.availablePermits(),
         * aby zaobserwować jak zmienia się liczba dostępnych "biletów".
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_StartingGunLatch {
        /*
         * 🧪 Zadanie 7:
         * Zbuduj "strzał startowy": CountDownLatch(1) jako bramę startową dla 4 wątków
         * reprezentujących biegaczy. Każdy biegacz najpierw wywołuje startGate.await()
         * (czeka na start), a dopiero potem wypisuje "biegnę!". Main czeka 200ms,
         * po czym wywołuje startGate.countDown() jednorazowo, odpalając wszystkich naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CyclicBarrierTwoRounds {
        /*
         * 🧪 Zadanie 8:
         * Utwórz CyclicBarrier(3, akcja wypisująca numer rundy) i 3 wątki, z których
         * każdy wykonuje DWIE rundy: w każdej rundzie śpi losowo (20-80ms), wypisuje
         * "ukończyłem rundę X" i wywołuje barrier.await(). Zweryfikuj, że bariera
         * działa poprawnie w obu rundach (jest "cykliczna").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SemaphoreTryAcquire {
        /*
         * 🧪 Zadanie 9:
         * Utwórz Semaphore(1). Wątek A wywołuje acquire() i trzyma slot przez 300ms
         * (sleep). W międzyczasie wątek B wywołuje tryAcquire() (bez blokowania)
         * i wypisuje, czy udało się zdobyć slot (powinno być false, bo A go trzyma).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TwoPhaseLatchPattern {
        /*
         * 🧪 Zadanie 10:
         * Utwórz dwa CountDownLatch: startLatch(1) i doneLatch(3). 3 wątki najpierw
         * czekają na startLatch.await(), potem "pracują" (sleep 50ms), wypisują
         * "zrobione" i wywołują doneLatch.countDown(). Main odpala start (countDown na
         * startLatch), a potem czeka na doneLatch.await(5, TimeUnit.SECONDS) i wypisuje
         * "wszystkie zadania zakończone".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LatchWithExecutorTiming {
        /*
         * 🧪 Zadanie 11:
         * Za pomocą ExecutorService(4) i CountDownLatch(6) uruchom 6 zadań o różnym
         * czasie trwania (50-250ms). Zmierz (System.nanoTime) ile czasu main czekał
         * na latch.await() i wypisz to w milisekundach - powinno być zbliżone do
         * najdłużej trwającego zadania, a nie sumie wszystkich.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CyclicBarrierAtomicRounds {
        /*
         * 🧪 Zadanie 12:
         * Utwórz CyclicBarrier(4, akcja inkrementująca współdzielony AtomicInteger
         * "round" i wypisująca jego wartość). Uruchom 4 wątki wykonujące 3 rundy
         * każdy (pętla z barrier.await() w środku). Sprawdź, że po zakończeniu
         * wszystkich wątków AtomicInteger "round" ma wartość 3.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SemaphorePrinterPool {
        /*
         * 🧪 Zadanie 13:
         * Zasymuluj pulę 2 drukarek za pomocą Semaphore(2) i 6 zadań drukowania
         * (osobne wątki). Za pomocą współdzielonego AtomicInteger "activeNow"
         * (inkrementowanego po acquire(), dekrementowanego przed release()) wypisuj
         * bieżącą liczbę aktywnych drukowań i zweryfikuj, że nigdy nie przekracza 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_LatchThenSemaphore {
        /*
         * 🧪 Zadanie 14:
         * Użyj CountDownLatch(1) jako "sygnału startu systemu" - 5 wątków czeka na
         * jego otwarcie, a po starcie każdy z nich musi jeszcze zdobyć jeden
         * z 2 slotów Semaphore(2), zanim będzie mógł "korzystać z zasobu" (sleep 80ms).
         * Wypisz kolejność, w jakiej wątki uzyskują dostęp do zasobu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CyclicBarrierResettingState {
        /*
         * 🧪 Zadanie 15:
         * Utwórz CyclicBarrier(3, akcja resetująca współdzielony AtomicInteger
         * "sharedCounter" do 0 po każdej rundzie). 3 wątki w każdej z 2 rund
         * inkrementują sharedCounter kilka razy przed dotarciem do bariery. Wypisz
         * wartość sharedCounter tuż przed każdym resetem, aby zobaczyć efekt akcji bariery.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SemaphoreParkingLot {
        /*
         * 🧪 Zadanie 16:
         * Zamodeluj parking o 3 miejscach (Semaphore(3)). 8 "samochodów" (wątków)
         * po kolei próbuje wjechać (acquire()), zostaje na parkingu przez losowy
         * czas (50-150ms sleep) i wyjeżdża (release()). Wypisuj komunikaty
         * "wjazd" / "wyjazd" wraz z liczbą wolnych miejsc (availablePermits()).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_LatchForTwoIndependentGroups {
        /*
         * 🧪 Zadanie 17:
         * Utwórz dwa niezależne CountDownLatch: latchA(2) dla grupy A i latchB(3)
         * dla grupy B. Uruchom wątki obu grup (łącznie 5), każdy countDown() na
         * swoim latchu po zakończeniu pracy. Main czeka NAJPIERW na latchA.await(),
         * wypisuje "grupa A gotowa", potem na latchB.await(), wypisuje "grupa B gotowa".
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SemaphoreAsMutexVerification {
        /*
         * 🧪 Zadanie 18:
         * Użyj Semaphore(1) jako alternatywy dla synchronized do ochrony współdzielonego
         * licznika int (opakowanego np. w tablicę jednoelementową). 8 wątków inkrementuje
         * licznik 500 razy każdy (acquire()/increment/release()). Po zakończeniu
         * wszystkich wątków zweryfikuj, że finalna wartość licznika wynosi dokładnie 4000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CyclicBarrierTimeoutHandling {
        /*
         * 🧪 Zadanie 19:
         * Utwórz CyclicBarrier(3), ale uruchom tylko 2 wątki wywołujące await(300,
         * TimeUnit.MILLISECONDS) (celowo brakuje trzeciego uczestnika). Złap
         * TimeoutException w obu wątkach i wypisz komunikat "bariera nie została
         * osiągnięta w czasie" zamiast pozwolić programowi zawiesić się w nieskończoność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_RaceStartAndFinishGates {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj symulator wyścigu: CountDownLatch startGate(1) i CountDownLatch
         * finishGate(5) dla 5 biegaczy. Każdy biegacz czeka na start, potem "biegnie"
         * przez losowy czas (100-400ms) i wywołuje finishGate.countDown() zaraz po
         * dobiegnięciu, wypisując swoje miejsce w kolejności dobiegnięcia (1., 2., ...).
         * Main odpala start i czeka na finishGate.await(5, TimeUnit.SECONDS).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_MultiStagePipelineWithBarrier {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj potok 3-etapowy z CyclicBarrier(4) dla 4 workerów: w każdym z 3 etapów
         * każdy worker inkrementuje współdzielony AtomicInteger o wartość zależną od
         * swojego id, po czym czeka na barrier.await(). Po zakończeniu wszystkich
         * etapów zweryfikuj, że finalna wartość AtomicInteger zgadza się z wyliczoną
         * ręcznie sumą oczekiwaną.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_SemaphoreLimitedDownloadsWithFutures {
        /*
         * 🧪 Zadanie 22:
         * Zamodeluj 10 "pobierań plików" jako Callable<String> zwracające nazwę pliku,
         * ograniczone Semaphore(3) (max 3 równoległe pobrania). Zbierz wszystkie
         * Future do listy, odbierz wyniki przez get() i wypisz je w kolejności
         * zgłoszenia zadań, weryfikując że wszystkie 10 wyników zostało odebranych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FanOutFanInWithLatch {
        /*
         * 🧪 Zadanie 23:
         * Podziel tablicę int[100] (wartości 1-100) na 4 fragmenty przetwarzane
         * równolegle przez 4 wątki, z których każdy liczy sumę częściową i zapisuje
         * ją do współdzielonej tablicy long[4] (jeden slot na wątek), a na końcu
         * wywołuje CountDownLatch.countDown(). Main czeka na latch.await(), a potem
         * sumuje wszystkie 4 sumy częściowe i weryfikuje wynik ze wzorem n*(n+1)/2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ObjectPoolWithSemaphore {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj prostą pulę obiektów (np. 3 "połączenia" reprezentowane Stringami
         * w synchronizowanej liście) chronioną przez Semaphore(3) - acquire() przed
         * pobraniem obiektu z puli, release() po zwrocie. Uruchom 8 wątków, z których
         * każdy pobiera obiekt, "używa go" (sleep 50-100ms) i zwraca do puli. Wypisz,
         * które połączenie było użyte przez który wątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CyclicBarrierWithSlowThread {
        /*
         * 🧪 Zadanie 25:
         * Uruchom CyclicBarrier(4) z 4 wątkami, ale jeden z nich celowo śpi bardzo
         * długo (2000ms) przed wywołaniem await(). Pozostałe 3 wątki wywołują
         * barrier.await(500, TimeUnit.MILLISECONDS) - złap TimeoutException i
         * wypisz "bariera przerwana przez zbyt wolny wątek", zamiast czekać
         * bezterminowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TwoPhaseCommitSimulation {
        /*
         * 🧪 Zadanie 26:
         * Zasymuluj prosty "two-phase commit" dla 4 uczestników: CountDownLatch
         * prepareLatch(4) - każdy uczestnik wypisuje "gotowy do zatwierdzenia" i
         * odlicza. Main czeka na prepareLatch, wypisuje "faza commit" i odpala
         * CountDownLatch commitLatch(1), na który czekają wszyscy uczestnicy przed
         * wypisaniem "zatwierdzone". Zweryfikuj poprawną kolejność faz w logach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BoundedBufferWithSemaphores {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj własny bufor ograniczony (bez BlockingQueue) na bazie
         * synchronizowanej listy i dwóch semaforów: emptySlots(5) i fullSlots(0).
         * Producent (1 wątek) dodaje 20 elementów: acquire(emptySlots), dodaje do
         * listy, release(fullSlots). Konsument (1 wątek) odbiera 20 elementów:
         * acquire(fullSlots), zdejmuje z listy, release(emptySlots). Zweryfikuj że
         * wszystkie 20 elementów zostało odebranych w kolejności FIFO.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FullRaceSimulationAllThree {
        /*
         * 🧪 Zadanie 28:
         * Połącz wszystkie 3 synchronizatory w symulacji wyścigu z punktem
         * kontrolnym: CountDownLatch startGate(1) na start, CyclicBarrier(5) na
         * "punkcie kontrolnym" w połowie trasy (wszyscy muszą się zebrać, zanim
         * ruszą dalej), CountDownLatch finishGate(5) na mecie. Użyj ExecutorService
         * do zarządzania 5 wątkami-biegaczami i poprawnie zamknij pulę na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CustomLatchFromScratch {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj własną, uproszczoną klasę SimpleLatch używając synchronized/
         * wait/notifyAll (bez java.util.concurrent), z metodami countDown() i
         * await() działającymi analogicznie do CountDownLatch. Przetestuj ją w
         * scenariuszu z 4 wątkami countDown() i main czekającym przez await(),
         * porównując zachowanie z prawdziwym CountDownLatch(4) uruchomionym obok.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_SystemStartupSimulation {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj symulację startu systemu z modułami: Semaphore(2) ogranicza liczbę
         * jednocześnie ładowanych modułów (spośród 6 modułów-wątków), CountDownLatch
         * modulesLoaded(6) sygnalizuje że wszystkie moduły się załadowały, a na końcu
         * CyclicBarrier(6, akcja wypisująca "walidacja zakończona") synchronizuje
         * etap walidacji post-load wszystkich modułów przed wypisaniem finalnego
         * raportu "system gotowy".
         */
        public static void main(String[] args) { }
    }
}
