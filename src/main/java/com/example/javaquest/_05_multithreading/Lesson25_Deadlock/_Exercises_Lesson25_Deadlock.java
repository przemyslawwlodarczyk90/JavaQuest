package com.example.javaquest._05_multithreading.Lesson25_Deadlock;

public class _Exercises_Lesson25_Deadlock {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ClassicTwoLockDeadlock {
        /*
         * 🧪 Zadanie 1:
         * Odtwórz klasyczny scenariusz zakleszczenia z lekcji, ale z dwoma
         * obiektami "kasa" i "magazyn" oraz wątkami "Kasjer" i "Magazynier".
         * Kasjer: synchronized(kasa) -> sleep(300) -> synchronized(magazyn).
         * Magazynier: synchronized(magazyn) -> sleep(300) -> synchronized(kasa).
         * Użyj wątków daemon i join(2000) jak w lekcji, wypisz czy wykryto deadlock.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FixedConsistentOrdering {
        /*
         * 🧪 Zadanie 2:
         * Napraw scenariusz z Zadania 1 tak, by OBA wątki zdobywały blokady w TEJ
         * SAMEJ kolejności (najpierw "kasa", potem "magazyn"). Zweryfikuj przez
         * join(2000), że oba wątki kończą się normalnie (isAlive() == false po join).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainCoffmanConditions {
        /*
         * 🧪 Zadanie 3:
         * Dla kodu z Zadania 1 wypisz (przez println) 4 krótkie zdania mapujące
         * konkretne linie/fragmenty tego kodu na 4 warunki konieczne deadlocka:
         * mutual exclusion, hold and wait, no preemption, circular wait.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ThreeLockCircularDeadlock {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj zakleszczenie cykliczne z 3 blokadami i 3 wątkami: Wątek1
         * (lockA->lockB), Wątek2 (lockB->lockC), Wątek3 (lockC->lockA). Użyj
         * wątków daemon i bounded join(2000) dla wszystkich trzech, wypisz wynik
         * wykrycia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FixThreeLockScenario {
        /*
         * 🧪 Zadanie 5:
         * Napraw scenariusz z Zadania 4, wymuszając spójną globalną kolejność
         * zdobywania blokad (zawsze A, potem B, potem C - niezależnie od wątku).
         * Zweryfikuj przez bounded join, że wszystkie 3 wątki kończą się normalnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_TryLockImmediateBackoff {
        /*
         * 🧪 Zadanie 6:
         * Zamiast synchronized użyj ReentrantLock.tryLock() (bez timeoutu, od
         * razu zwraca true/false) dla scenariusza z 2 blokadami: jeśli wątek nie
         * zdobędzie drugiej blokady od razu, zwalnia pierwszą i wypisuje
         * "wycofuję się". Uruchom kilka rund i potwierdź (bounded join), że żaden
         * wątek nigdy się nie zawiesza.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_BankTransferTryLockTimeout {
        /*
         * 🧪 Zadanie 7:
         * Zaimplementuj scenariusz "przelew bankowy" jak w lekcji (tryLock z
         * timeoutem), ale z własnymi nazwami: przelew z konta "A" na "B" i
         * jednocześnie z "B" na "A", oba z tryLock(100, TimeUnit.MILLISECONDS).
         * Wypisz, który przelew się udał, a który się wycofał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BoundedJoinHelper {
        /*
         * 🧪 Zadanie 8:
         * Napisz metodę `boolean runWithBoundedJoin(Thread t, long timeoutMs)`,
         * która startuje wątek (ustawiony wcześniej jako daemon) i zwraca true,
         * jeśli zdążył się zakończyć w podanym czasie. Użyj jej zarówno dla
         * zwykłego, bezpiecznego zadania (powinno zwrócić true), jak i dla
         * zakleszczającego się wątku z Zadania 1 (powinno zwrócić false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SingleLockPreventsDeadlock {
        /*
         * 🧪 Zadanie 9:
         * Zamiast dwóch osobnych blokad użyj JEDNEGO wspólnego obiektu-monitora do
         * ochrony dwóch "sald kont" w scenariuszu przelewu między 2 wątkami.
         * Zweryfikuj (bounded join), że przy jednej blokadzie deadlock jest
         * strukturalnie niemożliwy i oba wątki kończą się normalnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_DetectBlockedStateViaGetState {
        /*
         * 🧪 Zadanie 10:
         * Uruchom klasyczny scenariusz 2-wątkowego deadlocka (jak w lekcji, daemon
         * + bounded join). Po upływie czasu join (niezależnie od wyniku) wypisz
         * Thread.getState() obu wątków i zweryfikuj, że wynoszą BLOCKED (nawiązanie
         * do stanów wątku z Lekcji 6).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_DiningPhilosophersNaive {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj symulację "ucztujących filozofów" z 3 filozofami i 3 widelcami
         * (obiekty-blokady), gdzie KAŻDY filozof podnosi najpierw lewy, potem
         * prawy widelec w tej samej naiwnej kolejności. Uruchom wszystkich jako
         * wątki daemon z bounded join(2000) i wypisz, którzy filozofowie zdążyli
         * skończyć jeść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DiningPhilosophersFixedAsymmetric {
        /*
         * 🧪 Zadanie 12:
         * Napraw scenariusz z Zadania 11, sprawiając że OSTATNI filozof podnosi
         * widelce w ODWROTNEJ kolejności (najpierw prawy, potem lewy) - klasyczna
         * "asymetryczna" naprawa. Zweryfikuj (bounded join), że tym razem wszyscy
         * 3 filozofowie kończą jeść.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TryLockWithRetryLoop {
        /*
         * 🧪 Zadanie 13:
         * Dwa wątki rywalizują o 2 blokady w przeciwnej kolejności, każdy używając
         * ReentrantLock.tryLock(50, TimeUnit.MILLISECONDS) i ponawiając próbę do 5
         * razy przy porażce (zwalniając pierwszą blokadę między próbami). Wypisz
         * liczbę prób potrzebnych każdemu wątkowi i zweryfikuj (bounded join), że
         * oba ostatecznie kończą się sukcesem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UnrelatedThirdLockNoImpact {
        /*
         * 🧪 Zadanie 14:
         * Odtwórz klasyczny 2-wątkowy deadlock (lockA/lockB), a obok uruchom
         * TRZECI, niezwiązany wątek pomocniczy korzystający z zupełnie innej
         * blokady "lockC" (bez udziału w cyklu). Zweryfikuj (bounded join), że
         * trzeci wątek kończy się normalnie, podczas gdy dwa pierwsze nadal się
         * zakleszczają - dodanie niezwiązanej blokady niczego nie zmienia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_WatchdogDeadlockDetector {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj wątek-obserwatora (daemon), który co 200ms (maks. przez 2
         * sekundy) sprawdza Thread.getState() dwóch wątków roboczych z klasycznego
         * scenariusza deadlocka. Jeśli oba są BLOCKED w dwóch kolejnych
         * sprawdzeniach z rzędu, wypisz "podejrzewam deadlock!" i zakończ
         * obserwację wcześniej (wątki robocze i tak są daemon, więc JVM zakończy
         * program normalnie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareThreeFixesSideBySide {
        /*
         * 🧪 Zadanie 16:
         * Uruchom po kolei 3 warianty naprawy TEGO SAMEGO zagrożenia
         * deadlockiem: (a) spójna kolejność blokad, (b) tryLock z timeoutem, (c)
         * jedna wspólna blokada. Dla każdego wariantu zweryfikuj bounded joinem, że
         * kończy się poprawnie, i wypisz krótką tabelę podsumowującą który
         * mechanizm zastosowano.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ResourceAllocationGraphPrintout {
        /*
         * 🧪 Zadanie 17:
         * Dla klasycznego scenariusza 2-wątkowego deadlocka wypisz (w momencie
         * wykrycia zakleszczenia) tekstowy opis przypominający graf alokacji
         * zasobów: który wątek trzyma którą blokadę i na którą blokadę czeka
         * (np. "Watek-1 trzyma lockA, czeka na lockB").
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_FourThreadBankTransferTryLock {
        /*
         * 🧪 Zadanie 18:
         * Rozszerz scenariusz przelewu z tryLock(timeout) (Zadanie 7) do 4
         * współbieżnych wątków przelewów pomiędzy różnymi parami z 3 kont, każdy z
         * mechanizmem ponawiania (backoff). Zweryfikuj (bounded joiny), że
         * wszystkie 4 przelewy ostatecznie kończą się bez trwałego zawieszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SleepTimingAffectsReproducibility {
        /*
         * 🧪 Zadanie 19:
         * Uruchom (w ograniczonej pętli, 3 próby) wariant BEZ sleep między
         * zdobyciem pierwszej a próbą zdobycia drugiej blokady oraz wariant ZE
         * sleep(300ms) (jak w lekcji). Dla każdej próby użyj bounded join i
         * wypisz, w ilu próbach każdy wariant "wyglądał" na zakleszczony,
         * ilustrując że timing wpływa na odtwarzalność deadlocka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AcquireInOrderHelper {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę `acquireInOrder(Object first, Object second, Runnable
         * action)`, która zawsze synchronizuje się najpierw na obiekcie o
         * MNIEJSZYM System.identityHashCode(), niezależnie od kolejności podanej
         * przez wywołującego. Użyj jej w scenariuszu 2 wątków wywołujących ją z
         * ODWRÓCONĄ kolejnością argumentów (lockA,lockB) i (lockB,lockA), i
         * zweryfikuj (bounded join) brak deadlocka.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BankingSystemWithIdentityHashOrdering {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj system 5 kont i 8 współbieżnych wątków przelewów między
         * ustalonymi z góry parami kont, korzystających z pomocnika
         * acquireInOrder (Zadanie 20) opartego o identityHashCode. Zweryfikuj
         * (bounded joiny), że wszystkie 8 przelewów się kończy oraz że suma sald
         * wszystkich kont przed i po jest identyczna (zachowanie spójności).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ExecutorServiceCallableTryLockTransfers {
        /*
         * 🧪 Zadanie 22:
         * Połącz ExecutorService (Lekcja 21) z Callable (Lekcja 22): wyślij 6
         * Callable<String> reprezentujących przelewy między 4 kontami do puli
         * fixed, każdy używający tryLock(timeout) z ponawianiem. Zbierz wyniki
         * (sukces/porażka/liczba ponowień) przez Future.get() i wypisz raport
         * końcowy. Poprawnie zamknij pulę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DeadlockRegressionTestHarness {
        /*
         * 🧪 Zadanie 23:
         * Napisz metodę `boolean detectsDeadlock(Runnable taskA, Runnable taskB,
         * long timeoutMs)` uruchamiającą oba zadania jako wątki daemon i
         * zwracającą true, jeśli oba są nadal żywe po timeoutMs. Użyj jej jak
         * automatycznego testu: zweryfikuj że zwraca true dla naiwnej wersji
         * (Zadanie 1) i false dla wersji naprawionej (Zadanie 2), wypisując wynik
         * w stylu raportu testowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_LivelockAdjacentThrashingPattern {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj dwa wątki, które przy porażce tryLock(krótki timeout) na drugiej
         * blokadzie ZAWSZE natychmiast zwalniają pierwszą i ponawiają próbę BEZ
         * żadnego losowego opóźnienia (backoff), przez ograniczoną liczbę
         * iteracji (np. 20). Wypisz, czy udało im się kiedyś obu zdobyć komplet
         * blokad, i wyjaśnij (println) dlaczego dodanie losowego jittera (jak we
         * wcześniejszych zadaniach) pomaga uniknąć takiego "młócenia".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_GlobalLockOrderRegistry {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj rejestr kanonicznej kolejności blokad: klasa trzymająca
         * ustaloną List<Object> z 5 nazwanymi blokadami. Napisz metodę, która dla
         * podanego podzbioru potrzebnych blokad zdobywa je ŚCIŚLE w kolejności z
         * rejestru (niezależnie od kolejności żądania). Przetestuj z kilkoma
         * wątkami żądającymi nakładających się podzbiorów w różnej kolejności i
         * zweryfikuj (bounded joiny) brak deadlocka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TwoPhaseCommitAcrossThreeManagers {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj scenariusz z 3 "menedżerami zasobów" i 2 konkurującymi
         * "transakcjami", z których każda potrzebuje WSZYSTKICH 3 zasobów, ale
         * żąda ich w różnej kolejności. Uruchom najpierw wersję NAIWNĄ (bounded
         * wykrycie potencjalnego deadlocka), a potem wersję NAPRAWIONĄ (rejestr
         * kolejności z Zadania 25), porównując oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LatchGatedReconciliation {
        /*
         * 🧪 Zadanie 27:
         * Połącz CountDownLatch(6) (Lekcja 20) z systemem przelewów o spójnej
         * kolejności blokad: 6 wątków przelewów musi się zakończyć (countDown())
         * zanim krok "rekoncyliacji" (sprawdzenie zachowania sumy sald) zostanie
         * wykonany. Użyj latch.await() z bounded timeout jako zabezpieczenia i
         * wypisz wynik rekoncyliacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ChaosTestNaiveVersion {
        /*
         * 🧪 Zadanie 28:
         * Uruchom 10 rund (bounded) klasycznej naiwnej pary wątków
         * zakleszczających się (z lekko losowym czasem snu w każdej rundzie),
         * używając bounded-join wykrycia w każdej rundzie. Zlicz i wypisz, w ilu
         * rundach wykryto deadlock, a w ilu oba wątki zdążyły się zakończyć -
         * jako dowód na "niebezpieczną" niedeterministyczność naiwnej wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ChaosTestFixedVersion {
        /*
         * 🧪 Zadanie 29:
         * Powtórz dokładnie ten sam eksperyment chaos-test co w Zadaniu 28 (10
         * rund, lekko losowy czas snu), ale dla wersji NAPRAWIONEJ (spójna
         * kolejność blokad). Zweryfikuj i wypisz, że we WSZYSTKICH 10 rundach nie
         * wykryto ani jednego deadlocka - empiryczne potwierdzenie skuteczności naprawy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WarehouseSystemThreeStrategiesCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj symulację "systemu magazynowego" z 4 współdzielonymi zasobami
         * (blokadami) i 6 wątkami roboczymi, z których każdy potrzebuje 2-3
         * zasobów w różnej, ustalonej z góry kolejności żądania, by ukończyć
         * zadanie "kompletacji zamówienia". Zaimplementuj i porównaj TRZY pełne
         * przebiegi: (1) naiwne zdobywanie w żądanej kolejności (bounded wykrycie
         * potencjalnego deadlocka), (2) zdobywanie wg globalnego rejestru kolejności
         * (Zadanie 25, bezpieczne), (3) tryLock z timeoutem i ponawianiem (Zadanie
         * 13, również bezpieczne, ale innym mechanizmem). Dla każdego przebiegu
         * wypisz, czy wszyscy 6 pracownicy ukończyli zadanie w ograniczonym
         * czasie, oraz jednozdaniowy wniosek porównujący te 3 strategie.
         */
        public static void main(String[] args) { }
    }
}
