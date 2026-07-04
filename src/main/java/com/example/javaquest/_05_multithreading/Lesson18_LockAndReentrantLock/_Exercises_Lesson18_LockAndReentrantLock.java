package com.example.javaquest._05_multithreading.Lesson18_LockAndReentrantLock;

public class _Exercises_Lesson18_LockAndReentrantLock {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicLockUnlockFinally {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj ReentrantLock lock i int counter = 0. W jednym wątku (main)
         * wykonaj 10 razy blok `lock.lock(); try { counter++; } finally { lock.unlock(); }`.
         * Wypisz finalną wartość counter oraz lock.isLocked() (powinno być false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_LockProtectedCounterMultithreaded {
        /*
         * 🧪 Zadanie 2:
         * Zadeklaruj ReentrantLock counterLock i int sharedCounter = 0. Uruchom
         * 4 wątki, każdy wykonujący 10 000 razy `counterLock.lock(); try {
         * sharedCounter++; } finally { counterLock.unlock(); }`. Bounded join (10s),
         * zweryfikuj że sharedCounter == 40000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_UnlockInFinallyDespiteException {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj ReentrantLock demoLock. Wykonaj demoLock.lock(), a w bloku try
         * rzuć RuntimeException, zapewniając unlock() w finally. Złap wyjątek na
         * zewnątrz i wypisz, że lock.isLocked() == false mimo wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TryLockImmediateFalse {
        /*
         * 🧪 Zadanie 4:
         * Main przejmuje ReentrantLock lock.lock() i TRZYMA go (nie zwalnia od razu).
         * Uruchom inny wątek wywołujący lock.tryLock() – powinien natychmiast zwrócić
         * false. Wypisz wynik, a następnie main zwalnia blokadę w finally.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TryLockImmediateTrueWhenFree {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj wolny ReentrantLock lock (nikt go nie trzyma). Uruchom wątek
         * wywołujący lock.tryLock() – powinien natychmiast zwrócić true. Wypisz wynik
         * i pamiętaj o zwolnieniu blokady (unlock() w finally, jeśli zdobyta).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TryLockWithTimeoutSuccess {
        /*
         * 🧪 Zadanie 6:
         * Wątek-trzymający zdobywa lock, trzyma go 200ms, po czym zwalnia (w finally).
         * Wątek-czekający wywołuje lock.tryLock(2, TimeUnit.SECONDS) – powinien zdobyć
         * blokadę w ramach timeoutu (bo trzymający zwolni ją po 200ms). Wypisz wynik
         * i zmierzony czas oczekiwania. Bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_TryLockWithTimeoutFailure {
        /*
         * 🧪 Zadanie 7:
         * Wątek-trzymający zdobywa lock i trzyma go 3000ms (w finally zwalnia).
         * Wątek-czekający wywołuje lock.tryLock(500, TimeUnit.MILLISECONDS) – timeout
         * jest krótszy niż czas trzymania, więc powinien zwrócić false. Wypisz wynik.
         * Bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_IsLockedAndHoldCountBasics {
        /*
         * 🧪 Zadanie 8:
         * Zadeklaruj ReentrantLock lock (bez wielowątkowości). Wywołaj lock.lock() raz
         * i wypisz isLocked() oraz getHoldCount() (powinno być 1). Wywołaj lock.lock()
         * PONOWNIE w tym samym wątku (rekurencyjne wejście) i wypisz getHoldCount()
         * (powinno być 2). Wywołaj unlock() dwa razy i wypisz finalny holdCount (0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_BasicConditionAwaitSignal {
        /*
         * 🧪 Zadanie 9:
         * Zadeklaruj Lock lock = new ReentrantLock() i Condition ready =
         * lock.newCondition(), oraz boolean[] flag = {false}. Wątek-czekający wchodzi
         * w lock.lock(), w pętli `while (!flag[0]) ready.await()`, zwalnia w finally.
         * Main po 200ms ustawia flag[0] = true i woła ready.signalAll() (w bloku
         * lock.lock()/finally unlock()). Bounded join (5s).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_LockVsSynchronizedSimpleComparison {
        /*
         * 🧪 Zadanie 10:
         * Uruchom dwa równoległe eksperymenty (4 wątki x 10 000 inkrementacji każdy):
         * jeden na int chronionym przez synchronized, drugi na int chronionym przez
         * ReentrantLock (lock/unlock w finally). Bounded join (10s) obu wariantów.
         * Wypisz oba wyniki i porównaj z oczekiwaną wartością 40000.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultipleTryLockAttemptsWithRetry {
        /*
         * 🧪 Zadanie 11:
         * Wątek-trzymający trzyma lock przez 250ms. Wątek-czekający próbuje
         * lock.tryLock() w pętli maks. 5 razy, śpiąc 100ms między próbami, aż się uda
         * albo wyczerpie próby. Wypisz, przy której próbie się udało (spodziewane:
         * po ok. 3 próbach). Bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_TwoIndependentLocksNoInterference {
        /*
         * 🧪 Zadanie 12:
         * Zadeklaruj DWA niezależne ReentrantLock chroniące dwa osobne liczniki
         * (counterA, counterB). Uruchom 2 wątki inkrementujące counterA (po 10 000
         * razy) i 2 wątki inkrementujące counterB (po 10 000 razy), każdy przez
         * WŁASNY lock. Bounded join (10s), zweryfikuj oba wyniki (po 20000) niezależnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConditionNotFullNotEmptyBoundedBuffer {
        /*
         * 🧪 Zadanie 13:
         * Zaimplementuj BoundedBuffer (pojemność 3) używając Lock + DWÓCH Condition
         * (notFull, notEmpty) zamiast synchronized/wait/notify z Lesson15. Producent
         * produkuje dokładnie 10 elementów, konsument konsumuje dokładnie 10. Bounded
         * join (10s), zweryfikuj sumę skonsumowanych wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ReentrancyRecursiveMethodDemo {
        /*
         * 🧪 Zadanie 14:
         * Napisz metodę rekurencyjną recursiveWork(ReentrantLock lock, int depth),
         * która za każdym wywołaniem robi lock.lock(), wypisuje getHoldCount(), wywołuje
         * się rekurencyjnie z depth-1 (jeśli depth > 0), po czym unlock() w finally.
         * Wywołaj ją z depth=5 i zweryfikuj, że po zakończeniu holdCount wynosi 0.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_TryLockFallbackAlternativeAction {
        /*
         * 🧪 Zadanie 15:
         * Wątek-trzymający trzyma lock przez 400ms w kontrolowanym oknie czasowym.
         * 3 wątki-próbujące co 100ms (przez 500ms, czyli maks. 5 prób) wywołują
         * tryLock(); jeśli się nie uda, wypisują "zajęte, robię coś innego" zamiast
         * czekać. Zweryfikuj (bounded join 5s), że przynajmniej jedna próba w każdym
         * wątku zakończyła się fallbackiem, a przynajmniej jedna zdobyciem blokady.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_LockGuardedSharedListModification {
        /*
         * 🧪 Zadanie 16:
         * Zadeklaruj ReentrantLock listLock i zwykłą ArrayList<Integer> (NIE
         * synchronizedList). Uruchom 5 wątków, każdy dodający 200 elementów do listy
         * wewnątrz listLock.lock()/finally unlock(). Bounded join (10s), zweryfikuj
         * że lista ma dokładnie 1000 elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TimedTryLockRaceForResource {
        /*
         * 🧪 Zadanie 17:
         * Jeden "zasób" chroniony przez ReentrantLock; jeden wątek trzyma go 300ms.
         * 3 wątki-konkurenci wywołują tryLock(1, TimeUnit.SECONDS) w różnych momentach
         * (staggered start). Wypisz dla każdego, czy zdobył blokadę w ramach timeoutu.
         * Bounded join (10s), zweryfikuj że przynajmniej jeden wątek się powiódł.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ConditionMultipleWaitersSignalAll {
        /*
         * 🧪 Zadanie 18:
         * Lock lock = new ReentrantLock(), Condition ready = lock.newCondition(),
         * boolean[] flag = {false}. Uruchom 3 wątki czekające w pętli `while
         * (!flag[0]) ready.await()`. Main po 200ms ustawia flag[0] = true i woła
         * ready.signalAll(). Zweryfikuj (bounded join 5s), że wszystkie 3 wątki się
         * obudziły i zakończyły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_NestedLocksConsistentOrder {
        /*
         * 🧪 Zadanie 19:
         * Zadeklaruj dwa ReentrantLock: lockA, lockB. Napisz metodę incrementBoth(),
         * która ZAWSZE zdobywa najpierw lockA, potem lockB (stały, spójny porządek),
         * inkrementuje wspólny licznik chroniony obiema blokadami, i zwalnia je w
         * odwrotnej kolejności w finally. Uruchom 4 wątki x 100 wywołań, bounded join
         * (10s), zweryfikuj poprawność licznika (400) – to wprowadzenie do unikania
         * deadlocków przez spójny porządek blokad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_LockBasedCacheWithTryLockRefresh {
        /*
         * 🧪 Zadanie 20:
         * Zaimplementuj prostą klasę Cache z ReentrantLock i polem String value. Wątek
         * "writer" co 200ms (3 razy) zdobywa lock na 100ms i aktualizuje value. 5 wątków
         * "reader" próbują tryLock() do odczytu – jeśli się uda, czytają i zwalniają;
         * jeśli nie, wypisują "zajęte, pomijam ten odczyt" (bez czekania). Bounded join
         * (10s), zweryfikuj że żaden odczyt nie zwrócił null/wartości niespójnej.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ProducerConsumerWithLockAndTwoConditions {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj BoundedBuffer (pojemność 5) na bazie Lock + Condition
         * notFull/notEmpty. Uruchom 2 producentów (po 10 elementów = 20) i 2
         * konsumentów. Bounded join (15s). Zweryfikuj, że suma skonsumowanych
         * elementów wynosi 20.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_GracefulPoolShutdownWithLock {
        /*
         * 🧪 Zadanie 22:
         * Zadeklaruj pulę 5 wątków roboczych dzielących ReentrantLock chroniący
         * wspólny licznik wykonanych "prac" oraz boolean shutdownRequested (odczyt/zapis
         * też pod lockiem). Każdy wątek w pętli `while (!shutdownRequested)` inkrementuje
         * licznik i śpi 5ms. Main po 300ms zdobywa lock, ustawia shutdownRequested = true.
         * Bounded join (10s) wszystkich 5, wypisz łączną liczbę wykonanych prac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ReadHeavyCacheWithTryLockAndFallback {
        /*
         * 🧪 Zadanie 23:
         * Rozszerz Cache z Zadania 20: wątek "writer" odświeża wartość 3 razy (co
         * 500ms), trzymając lock przez 150ms za każdym razem. 10 wątków "reader",
         * każdy czytający 5 razy, używa tryLock() z fallbackiem do OSTATNIEJ znanej
         * wartości (przechowywanej lokalnie w wątku), gdy lock jest zajęty. Bounded
         * join (15s). Zweryfikuj, że żaden odczyt (ani bezpośredni, ani fallback) nie
         * zwrócił null.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FairnessComparisonFairVsUnfairLock {
        /*
         * 🧪 Zadanie 24:
         * Utwórz dwa ReentrantLock: fairLock = new ReentrantLock(true) (sprawiedliwy)
         * i unfairLock = new ReentrantLock() (domyślny, niesprawiedliwy). Uruchom
         * identyczny scenariusz rywalizacji (5 wątków x 1000 inkrementacji każdy) na
         * obu, mierząc czas wykonania (System.nanoTime()). Wypisz oba czasy i skomentuj
         * różnicę – fair jest zwykle wolniejszy, ale bardziej przewidywalny w kolejności.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConditionBasedTurnstileForFixedRounds {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj "turniej" dla 5 wątków przez DOKŁADNIE 3 rundy: w każdej
         * rundzie każdy wątek zdobywa lock, inkrementuje licznik przybyłych, i jeśli
         * licznik < 5, czeka na Condition (`while (arrived < 5) allArrived.await()`);
         * gdy wszystkie 5 przybędą, ostatni resetuje licznik do 0 i woła
         * allArrived.signalAll(). Wypisz numer rundy przy każdym przejściu. Bounded
         * join (10s).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_DeadlockAvoidanceWithTryLockBothOrNone {
        /*
         * 🧪 Zadanie 26:
         * Zadeklaruj dwa ReentrantLock: lockA, lockB. Napisz metodę
         * acquireBothOrRetry(), która próbuje tryLock(200ms) na jednym, potem na
         * drugim; jeśli drugi się nie uda, ZWALNIA pierwszy i próbuje ponownie
         * (maks. 10 razy). Uruchom 2 wątki próbujące zdobyć oba locki w PRZECIWNEJ
         * kolejności (klasyczny scenariusz deadlocka) i zweryfikuj (bounded join 10s),
         * że oba wątki mimo to kończą pracę bez zawieszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LockProtectedInventorySystem {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj magazyn: ReentrantLock chroniący Map<String,Integer> stock
         * (zainicjalizowany np. {"A":100, "B":100}). Uruchom 8 wątków, każdy
         * wykonujący 50 losowych operacji restock(+10)/sell(-5), przy czym sell()
         * pod lockiem sprawdza, czy stock wystarcza, i pomija operację jeśli nie.
         * Bounded join (15s), zweryfikuj że żadna wartość w stock nigdy nie spadła
         * poniżej 0, a suma zmian zgadza się z finalnym stanem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ConditionAwaitWithTimeoutFallback {
        /*
         * 🧪 Zadanie 28:
         * Lock lock, Condition ready, boolean[] flag = {false}. Czekający wywołuje
         * `ready.await(500, TimeUnit.MILLISECONDS)` w pętli ograniczonej do maks.
         * 5 prób (~2.5s budżetu), sprawdzając flag[0] po każdej próbie. Przetestuj DWA
         * scenariusze: (a) main ustawia flag[0]=true po 600ms (sukces w budżecie),
         * (b) main NIGDY nie ustawia flagi (czekający kończy się z komunikatem
         * "timeout", bez zawieszenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MultiStageAssemblyLineWithLocksAndConditions {
        /*
         * 🧪 Zadanie 29:
         * Zbuduj 3-etapową "linię montażową" (Stage1, Stage2, Stage3), każdy etap to
         * osobny wątek z WŁASNYM Lock+Condition. Stage1 przetwarza paczkę 10 "produktów"
         * i sygnalizuje Stage2 (Condition.signalAll()) po każdym gotowym produkcie;
         * Stage2 czeka (await() w pętli), przetwarza i przekazuje do Stage3 analogicznie.
         * Stage3 zlicza łączną liczbę odebranych produktów. Bounded join (15s), wypisz
         * finalną liczbę (powinno być 10) i kolejność przetworzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullBankTransferSystemWithLocks {
        /*
         * 🧪 Zadanie 30:
         * Zasymuluj system bankowy: Map<String, ReentrantLock> locks i Map<String,
         * Integer> balances dla 5 kont (każde startowe saldo 1000). Zaimplementuj
         * transfer(from, to, amount) zdobywający locki DLA OBU KONT w spójnym,
         * globalnym porządku (np. alfabetycznym wg nazwy konta), z unlock() obu w
         * finally. Uruchom 10 wątków, każdy wykonujący 20 losowych transferów między
         * kontami. Bounded join (15s), zweryfikuj że suma wszystkich sald na końcu
         * wciąż wynosi 5000 (zasada zachowania).
         */
        public static void main(String[] args) { }
    }
}
