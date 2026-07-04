package com.example.javaquest._05_multithreading.Lesson17_AtomicClasses;

public class _Exercises_Lesson17_AtomicClasses {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AtomicIntegerBasicIncrement {
        /*
         * 🧪 Zadanie 1:
         * Zadeklaruj AtomicInteger counter = new AtomicInteger(0). Uruchom 8 wątków,
         * z których każdy wywołuje incrementAndGet() 10 000 razy. Zrób bounded join
         * (5s) wszystkich wątków i porównaj oczekiwany wynik (8 * 10000) z counter.get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_GetAndIncrementVsIncrementAndGet {
        /*
         * 🧪 Zadanie 2:
         * W jednym wątku (bez wielowątkowości) na AtomicInteger value = new
         * AtomicInteger(5) wywołaj na przemian getAndIncrement() i incrementAndGet()
         * 4 razy każde, wypisując za każdym razem zwróconą wartość oraz aktualny
         * stan (value.get()). Skomentuj różnicę między "stara wartość" a "nowa wartość".
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AtomicLongBasicUsage {
        /*
         * 🧪 Zadanie 3:
         * Zadeklaruj AtomicLong total = new AtomicLong(0). Uruchom 4 wątki, z których
         * każdy wywołuje addAndGet(1000) dokładnie 100 razy. Bounded join (5s), wypisz
         * oczekiwaną wartość (4 * 100 * 1000) i rzeczywistą total.get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AtomicBooleanFlagOnce {
        /*
         * 🧪 Zadanie 4:
         * Zadeklaruj AtomicBoolean initialized = new AtomicBoolean(false). Uruchom
         * 5 wątków, z których każdy próbuje wykonać compareAndSet(false, true) i
         * wypisuje, czy się udało. Bounded join (5s). Zweryfikuj, że DOKŁADNIE JEDEN
         * wątek zgłosił sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AtomicReferenceSwap {
        /*
         * 🧪 Zadanie 5:
         * Zadeklaruj AtomicReference<String> ref = new AtomicReference<>("A"). Wywołaj
         * ref.compareAndSet("A", "B") i wypisz wynik oraz nową wartość. Następnie
         * wywołaj ref.compareAndSet("A", "C") (oczekiwana wartość już nieaktualna)
         * i wypisz, że się nie powiodło, a wartość pozostała "B".
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CompareAndSetExplicitDemo {
        /*
         * 🧪 Zadanie 6:
         * Zadeklaruj AtomicInteger value = new AtomicInteger(10). Wywołaj
         * value.compareAndSet(10, 20) (powinno się udać) i wypisz wynik oraz wartość.
         * Następnie wywołaj value.compareAndSet(10, 30) (wartość to już 20, więc
         * oczekiwana 10 się nie zgadza) i wypisz, że się nie powiodło.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_AtomicIntegerDecrement {
        /*
         * 🧪 Zadanie 7:
         * Zadeklaruj AtomicInteger stock = new AtomicInteger(100). Uruchom 4 wątki,
         * każdy wywołujący decrementAndGet() 20 razy (łącznie 80 dekrementacji).
         * Bounded join (5s), zweryfikuj że finalny stock.get() == 20.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddAndGetVsGetAndAdd {
        /*
         * 🧪 Zadanie 8:
         * Na AtomicInteger value = new AtomicInteger(0) (bez wielowątkowości) wywołaj
         * addAndGet(5) i wypisz zwróconą wartość, a następnie getAndAdd(5) i wypisz
         * zwróconą wartość ORAZ aktualny stan value.get() po obu operacjach. Wyjaśnij
         * różnicę w komentarzu/println.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AtomicVsPlainIntComparison {
        /*
         * 🧪 Zadanie 9:
         * Uruchom dwa równoległe eksperymenty (4 wątki x 10 000 inkrementacji każdy):
         * jeden na zwykłym `int plainCounter` (bez synchronizacji ++), drugi na
         * `AtomicInteger atomicCounter` (incrementAndGet()). Wypisz oba wyniki obok
         * siebie i porównaj z oczekiwaną wartością 40000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AtomicReferenceNullSafety {
        /*
         * 🧪 Zadanie 10:
         * Zadeklaruj AtomicReference<String> ref = new AtomicReference<>(null).
         * Wywołaj ref.compareAndSet(null, "READY") i wypisz wynik (powinno się udać).
         * Wywołaj dokładnie to samo compareAndSet(null, "READY") drugi raz i wypisz,
         * że się nie powiodło (wartość nie jest już null).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AtomicCounterWithMultipleOperations {
        /*
         * 🧪 Zadanie 11:
         * Zadeklaruj AtomicInteger counter = new AtomicInteger(0). Uruchom 2 wątki
         * wywołujące incrementAndGet() po 5000 razy oraz 2 wątki wywołujące
         * decrementAndGet() po 2000 razy. Bounded join (5s), oblicz oczekiwaną wartość
         * (2*5000 - 2*2000) i porównaj z counter.get().
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CasRetryLoopManualImplementation {
        /*
         * 🧪 Zadanie 12:
         * Zamiast incrementAndGet(), zaimplementuj RĘCZNIE atomowy increment metodą
         * manualIncrement(AtomicInteger counter) używającą pętli:
         * `do { current = counter.get(); next = current + 1; }
         * while (!counter.compareAndSet(current, next));` (jak w pseudokodzie z lekcji).
         * Uruchom 6 wątków x 5000 wywołań manualIncrement(), bounded join (5s),
         * zweryfikuj finalną wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AtomicBooleanOnceOnlyInitialization {
        /*
         * 🧪 Zadanie 13:
         * Zasymuluj wzorzec "inicjalizacja tylko raz": AtomicBoolean initialized =
         * new AtomicBoolean(false). Uruchom 10 wątków, z których każdy próbuje
         * compareAndSet(false, true); zwycięzca wypisuje "Wątek-N inicjalizuje system",
         * pozostali wypisują "Wątek-N: system już zainicjalizowany". Bounded join (5s),
         * zweryfikuj że dokładnie jeden wątek wygrał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AtomicReferenceLinkedStackPush {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj prosty, niemutowalny węzeł Node(int value, Node next) i pole
         * AtomicReference<Node> head = new AtomicReference<>(null). Zaimplementuj
         * push(int value) używający pętli CAS (compareAndSet na head). Uruchom
         * 5 wątków, każdy wypychający 100 wartości (łącznie 500). Bounded join (10s),
         * policz długość listy od head i zweryfikuj, że wynosi 500.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_AtomicLongIdGenerator {
        /*
         * 🧪 Zadanie 15:
         * Zadeklaruj AtomicLong idGenerator = new AtomicLong(0). Uruchom 6 wątków,
         * z których każdy generuje 500 unikalnych ID przez incrementAndGet() i dodaje
         * je do wspólnej, thread-safe listy (np. Collections.synchronizedList).
         * Bounded join (10s). Zweryfikuj, że wygenerowano 3000 ID i wszystkie są unikalne
         * (użyj Set do sprawdzenia duplikatów).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleAtomicFieldsCombinedStats {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj klasę Statistics z AtomicInteger successCount i
         * AtomicInteger failureCount. Uruchom 10 wątków, każdy wykonujący 100
         * operacji, losowo (Random) inkrementując successCount albo failureCount.
         * Bounded join (5s), zweryfikuj że successCount.get() + failureCount.get() == 1000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_AtomicIntegerArrayUsage {
        /*
         * 🧪 Zadanie 17:
         * Wykorzystaj java.util.concurrent.atomic.AtomicIntegerArray o rozmiarze 5.
         * Uruchom 10 wątków, z których każdy 1000 razy inkrementuje losowy slot
         * (indeks 0-4, incrementAndGet(index)). Bounded join (10s), zsumuj wszystkie
         * sloty i zweryfikuj, że suma wynosi 10 * 1000 = 10000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CompareAndSetStateTransition {
        /*
         * 🧪 Zadanie 18:
         * Zadeklaruj AtomicReference<String> state = new AtomicReference<>("IDLE").
         * Uruchom 8 wątków, z których każdy próbuje state.compareAndSet("IDLE",
         * "RUNNING") i wypisuje, czy się udało. Bounded join (5s), zweryfikuj, że
         * DOKŁADNIE JEDEN wątek zgłosił sukces, a state.get() to "RUNNING".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AtomicCounterWithBoundedRetryBackoff {
        /*
         * 🧪 Zadanie 19:
         * Zaimplementuj RĘCZNY increment z pętlą CAS ograniczoną do maksymalnie 1000
         * prób na jedno wywołanie (jako zabezpieczenie edukacyjne – w praktyce CAS
         * zawsze w końcu się powiedzie, ale tu demonstrujemy limit). Uruchom 8 wątków
         * x 2000 wywołań pod dużą rywalizacją, bounded join (10s), zweryfikuj poprawność
         * końcowego wyniku i wypisz, czy limit prób kiedykolwiek został osiągnięty.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PerformanceComparisonAtomicVsSynchronized {
        /*
         * 🧪 Zadanie 20:
         * Zmierz (System.nanoTime()) czas wykonania 4 wątków x 100 000 inkrementacji
         * dla dwóch wariantów: AtomicInteger.incrementAndGet() oraz zwykły int++
         * chroniony blokiem synchronized. Bounded join (10s) dla każdego wariantu
         * osobno. Wypisz oba czasy i oba finalne wyniki (powinny być poprawne w obu
         * przypadkach).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_LockFreeStackImplementation {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj pełny lock-free stos: AtomicReference<Node<Integer>> head,
         * metody push(int) i pop() (obie w pętli CAS, pop() zwraca Optional.empty()
         * gdy stos pusty). Uruchom 4 wątki push-ujące po 250 wartości (łącznie 1000),
         * zrób bounded join, a następnie 4 wątki pop-ujące dopóki stos nie jest pusty.
         * Zweryfikuj, że łącznie zdjęto dokładnie 1000 elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AtomicBasedBankAccountSimulation {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj konto bankowe z AtomicInteger balance = new AtomicInteger(1000).
         * Uruchom 5 wątków-wpłacających (każdy addAndGet(100) x 10 razy = 5000 łącznie)
         * i 5 wątków-wypłacających używających pętli CAS sprawdzającej, czy balance
         * pozwala na wypłatę 50 (bez zejścia poniżej 0), każdy próbujący 10 wypłat.
         * Bounded join (10s). Zweryfikuj, że balance nigdy nie spadł poniżej 0
         * i że finalna wartość zgadza się z sumą udanych operacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_CasBasedOptimisticLockingRetryCounter {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj RĘCZNY increment z pętlą CAS, licząc KAŻDĄ nieudaną próbę
         * compareAndSet w osobnym AtomicInteger casFailures. Uruchom 8 wątków
         * x 5000 wywołań (łącznie 40 000 udanych inkrementacji) pod dużą rywalizacją.
         * Bounded join (10s). Wypisz finalną wartość licznika, liczbę nieudanych prób
         * CAS oraz średnią liczbę prób na jedną udaną inkrementację.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_AtomicReferenceImmutableSnapshotUpdate {
        /*
         * 🧪 Zadanie 24:
         * Zamodeluj niemutowalną konfigurację jako mapę (java.util.Map<String,String>,
         * np. opakowaną w Collections.unmodifiableMap) trzymaną w AtomicReference.
         * 5 wątków-aktualizujących, każdy próbujący DODAĆ jeden nowy klucz przez
         * pętlę CAS (odczytaj bieżącą mapę, zbuduj nową kopię z dodatkowym wpisem,
         * spróbuj compareAndSet). Bounded join (10s). Zweryfikuj, że finalna mapa
         * zawiera wszystkie 5 kluczy dodanych przez wątki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ProducerConsumerWithAtomicCounters {
        /*
         * 🧪 Zadanie 25:
         * Wykorzystaj BoundedBuffer podobny do tego z Lesson15 (wait/notifyAll), ale
         * dodaj AtomicInteger producedCount i AtomicInteger consumedCount aktualizowane
         * atomowo przy każdym put()/take(). Uruchom 2 producentów (po 15 elementów =
         * 30) i 2 konsumentów. Bounded join (10s). Zweryfikuj, że producedCount.get() ==
         * consumedCount.get() == 30.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_AbaProblemDemo {
        /*
         * 🧪 Zadanie 26:
         * Zademonstruj problem ABA: AtomicReference<Integer> ref = new
         * AtomicReference<>(1). Zasymuluj (bez rzeczywistej rywalizacji, sekwencyjnie
         * w main) sekwencję: wątek A odczytuje wartość (1), "wątek B" zmienia
         * 1 -> 2 -> z powrotem 1 (ref.set), po czym wątek A wykonuje
         * compareAndSet(1, 99) – pokaż, że się UDAJE, mimo że wartość w międzyczasie
         * się zmieniała. Wypisz komentarz wyjaśniający ryzyko ABA i wspomnij o
         * AtomicStampedReference jako rozwiązaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ConcurrentCountersDashboard {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj "dashboard" z AtomicInteger requestsHandled, AtomicInteger errorsCount
         * i AtomicInteger activeThreads. Uruchom 10 wątków wykonujących łącznie 200 operacji
         * (każdy po 20), z których każda z 10% szansą (Random) symuluje błąd (increment
         * errorsCount w catch), a w przeciwnym razie sukces (increment requestsHandled).
         * activeThreads zwiększaj na starcie wątku i zmniejszaj na końcu. Bounded join
         * (10s), wypisz finalny raport dashboardu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_AtomicBasedRateLimiterSimulation {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj prosty rate limiter: AtomicInteger permitsUsed = new
         * AtomicInteger(0), limit MAX_PERMITS = 5 na "okno". Uruchom 10 wątków, każdy
         * próbujący zdobyć pozwolenie przez pętlę CAS (compareAndSet gwarantujące, że
         * permitsUsed nie przekroczy MAX_PERMITS), wypisując "zaakceptowano" lub
         * "odrzucono". Bounded join (5s), zweryfikuj że dokładnie 5 wątków dostało
         * pozwolenie, a pozostałych 5 zostało odrzuconych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SingleProducerSingleConsumerLockFreeQueue {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj minimalną kolejkę lock-free (scenariusz: jeden producent, jeden
         * konsument) z dwoma AtomicReference<Node<Integer>> (head i tail) i węzłami
         * łączonymi przez AtomicReference<Node>. Producent wstawia (enqueue) dokładnie
         * 100 wartości (1-100), konsument je zabiera (dequeue) w tej samej kolejności.
         * Bounded join (10s). Zweryfikuj, że kolejność odebranych wartości jest
         * identyczna z kolejnością wstawienia i żadna wartość nie zginęła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_CombinedAtomicStressTestSuite {
        /*
         * 🧪 Zadanie 30:
         * Połącz trzy techniki z lekcji w jednym teście: (1) AtomicInteger counter
         * inkrementowany przez 10 wątków po 1000 razy, (2) lock-free stos oparty na
         * AtomicReference (jak w Zadaniu 21) wypełniany przez te same wątki po
         * 10 elementów, (3) AtomicBoolean initialized do jednorazowej inicjalizacji
         * (compareAndSet) wykonywanej przez pierwszy wątek, który zdąży. Bounded join
         * (15s) na wszystkich wątkach. Wypisz skonsolidowany raport potwierdzający
         * poprawność wszystkich trzech struktur.
         */
        public static void main(String[] args) { }
    }
}
