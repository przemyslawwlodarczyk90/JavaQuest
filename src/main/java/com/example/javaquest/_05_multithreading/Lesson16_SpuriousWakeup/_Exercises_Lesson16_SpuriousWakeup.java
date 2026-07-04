package com.example.javaquest._05_multithreading.Lesson16_SpuriousWakeup;

public class _Exercises_Lesson16_SpuriousWakeup {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_IfVsWhileConditionCheck {
        /*
         * 🧪 Zadanie 1:
         * Zaimplementuj wątek-czekający używający BŁĘDNEGO wzorca `if (!ready) lock.wait(500);`
         * (pojedynczy wait z timeoutem, bez pętli). Main woła notifyAll() BEZ ustawienia
         * ready = true (symulacja fałszywego obudzenia). Wypisz, że wątek kontynuował
         * pracę mimo niespełnionego warunku – to demonstracja błędu z `if`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SimulatedSpuriousWakeupWithWhileLoop {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj to samo co w Zadaniu 1, ale POPRAWNIE: `while (!ready) lock.wait(500);`.
         * Main najpierw woła notifyAll() bez ustawienia ready (fałszywe obudzenie), a po
         * 300ms ustawia ready = true i woła notifyAll() ponownie. Wypisz, że wątek wrócił
         * do wait() po pierwszym (fałszywym) obudzeniu i zakończył pracę dopiero po drugim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_BoundedWaitWithTimeoutSafety {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj czekającego z `while (!ready) lock.wait(200);` i licznikiem
         * wywołań wait(). Main ustawia ready = true dopiero po 3 "fałszywych" cyklach
         * (main po prostu nic nie robi przez te 3 timeouty), a przy 4-tym cyklu ustawia
         * ready i woła notifyAll(). Wypisz liczbę wykonanych wait() przed zakończeniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_CountSpuriousWakeupAttempts {
        /*
         * 🧪 Zadanie 4:
         * Main woła notifyAll() DWA razy bez ustawiania warunku (symulacja dwóch
         * fałszywych obudzeń, w odstępie 100ms), a za trzecim razem ustawia flagę
         * i woła notifyAll(). Czekający (pętla while) zlicza, ile razy obudził się
         * i sprawdził warunek zanim ten był spełniony – wypisz tę liczbę (powinna wynosić 3).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SingleFalseNotifyThenRealNotify {
        /*
         * 🧪 Zadanie 5:
         * Odtwórz demo z teorii lekcji: wątek Waiter czeka w `while (!conditionReady)
         * lock.wait(2000)`. Main po 200ms woła notifyAll() BEZ ustawienia warunku,
         * po kolejnych 200ms ustawia conditionReady = true i woła notifyAll() ponownie.
         * Wypisz komunikat "wykryto fałszywe obudzenie" przy pierwszym przebudzeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_WaitTimeoutActsAsSpuriousWakeupSubstitute {
        /*
         * 🧪 Zadanie 6:
         * Zaimplementuj czekającego z `while (!ready) lock.wait(200)` (timeout jako
         * "sztuczny" odpowiednik spurious wakeup – każdy powrót z timeoutu traktujemy
         * jak nieoczekiwane obudzenie). Main ustawia ready = true w losowym momencie
         * między 300ms a 1500ms (Random z ustalonym seedem) i woła notify().
         * Wypisz liczbę "sztucznych obudzeń" (timeoutów) przed prawdziwym ustawieniem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MultipleFlagsWhileLoopRobustness {
        /*
         * 🧪 Zadanie 7:
         * Czekający czeka na SPEŁNIENIE OBU warunków: `while (!(condA && condB)) lock.wait();`.
         * Main najpierw ustawia condA = true i woła notifyAll() (condB wciąż false – czekający
         * NIE powinien kontynuować), po 200ms ustawia condB = true i woła notifyAll()
         * ponownie. Wypisz, że czekający zakończył pracę dopiero po drugim przebudzeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_SafeWaitHelperMethod {
        /*
         * 🧪 Zadanie 8:
         * Napisz statyczną metodę pomocniczą `awaitCondition(Object lock,
         * java.util.function.BooleanSupplier condition, long timeoutMillis)`, która
         * implementuje wzorzec `synchronized(lock) { while (!condition.getAsBoolean())
         * lock.wait(timeoutMillis); }`. Użyj jej w prostym scenariuszu 1 producent/
         * 1 konsument wymieniającym dokładnie jeden sygnał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_IfBugVsWhileFixSideBySideCount {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj DWIE niezależne klasy pomocnicze: jedną z błędnym `if`
         * (ograniczoną do jednego wait(500), by uniknąć zawieszenia), drugą z poprawnym
         * `while`. Uruchom obie 5 razy z tym samym scenariuszem (1 fałszywe notifyAll
         * przed prawdziwym), zliczając ile razy wersja z `if` dała BŁĘDNY wynik,
         * a ile razy wersja z `while` dała POPRAWNY wynik (powinno być 0/5 vs 5/5).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BoundedRetryLoopPattern {
        /*
         * 🧪 Zadanie 10:
         * Zaimplementuj czekającego z maksymalnie 20 próbami `lock.wait(100)` (budżet
         * czasowy ~2s). Main NIGDY nie ustawia warunku. Zweryfikuj, że wątek po
         * wyczerpaniu 20 prób loguje "timeout - warunek nie spełniony" i kończy się
         * samodzielnie (bez zawieszenia programu).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ChaosNotifierWithBoundedBuffer {
        /*
         * 🧪 Zadanie 11:
         * Wykorzystaj BoundedBuffer (jak w Lesson15) o pojemności 3. Uruchom producenta
         * (10 elementów) i konsumenta (10 elementów) korzystających z pętli while, oraz
         * DODATKOWY wątek "chaos" wołający notifyAll() na monitorze bufora 5 razy w
         * losowych odstępach, BEZ zmiany stanu bufora. Zweryfikuj (bounded join 10s),
         * że mimo chaosu skonsumowano dokładnie 10 elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_IfVersionBoundedComparison {
        /*
         * 🧪 Zadanie 12:
         * Zaimplementuj dwa warianty czekającego na jedną flagę: (a) `if (!ready)
         * lock.wait(500);` (pojedynczy, ograniczony czasowo wait – bezpieczny, ale
         * BŁĘDNY logicznie), (b) `while (!ready) lock.wait(500);` (poprawny, do 10 prób).
         * W obu przypadkach main woła jedno fałszywe notifyAll() (bez ustawienia ready),
         * a potem prawdziwe. Wypisz końcowy stan obu wariantów – (a) powinien
         * zareagować przedwcześnie, (b) poprawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_TimeoutBasedPseudoSpuriousInjector {
        /*
         * 🧪 Zadanie 13:
         * Napisz wątek "injector", który przez 1 sekundę co 100ms woła notifyAll() na
         * wspólnym monitorze BEZ zmiany strzeżonej flagi (10 fałszywych obudzeń).
         * Czekający (pętla while) musi przetrwać wszystkie 10 fałszywych obudzeń
         * i zakończyć się dopiero, gdy main ustawi prawdziwy warunek po 1200ms.
         * Bounded join (5s) obu wątków.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_MultiWaiterRobustnessUnderChaos {
        /*
         * 🧪 Zadanie 14:
         * Uruchom 3 wątki czekające na wspólnym monitorze, każdy na WŁASNEJ fladze
         * (condA, condB, condC), plus wątek "chaos" wołający notifyAll() 5 razy bez
         * zmiany żadnej flagi. Main ustawia flagi w różnych momentach (np. condA po
         * 100ms, condB po 250ms, condC po 400ms), za każdym razem wołając notifyAll().
         * Zweryfikuj (bounded join 5s), że żaden wątek nie zakończył się przedwcześnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RetryBudgetExhaustionScenario {
        /*
         * 🧪 Zadanie 15:
         * Czekający ma budżet maks. 5 prób `lock.wait(200)` (~1s). Warunek NIGDY nie
         * zostaje ustawiony przez main. Zweryfikuj, że po wyczerpaniu budżetu wątek
         * loguje "budżet wyczerpany, warunek niespełniony" i kończy się samodzielnie,
         * a program NIE zawiesza się mimo braku prawdziwego notify.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_WhileLoopWithCounterAndLogging {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj pętlę while zliczającą KAŻDE przebudzenie (prawdziwe i fałszywe)
         * w polu wakeupCount. Scenariusz: 2 fałszywe notifyAll (bez ustawienia warunku)
         * + 1 prawdziwe (z ustawieniem warunku). Po zakończeniu wypisz wakeupCount
         * (powinno wynosić 3) oraz finalny stan warunku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SafeAwaitUtilityReuse {
        /*
         * 🧪 Zadanie 17:
         * Wykorzystaj metodę awaitCondition() z Zadania 8 do zbudowania DWÓCH niezależnych
         * par producent-konsument, każda na WŁASNYM obiekcie monitora, działających
         * RÓWNOLEGLE, każda wymieniająca dokładnie 5 sygnałów (łącznie 10). Zweryfikuj
         * (bounded join 10s), że obie pary poprawnie zakończyły wszystkie wymiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_StressTestManySpuriousInjections {
        /*
         * 🧪 Zadanie 18:
         * Wątek "chaos" wykonuje w ciasnej, ale OGRANICZONEJ pętli (50 iteracji) 50
         * wywołań notifyAll() na monitorze strzegącym jednej flagi, bez zmiany flagi.
         * Wątek ustawiający czeka (bounded join na chaos, maks. 5s) aż chaos zakończy
         * wszystkie 50 wywołań, DOPIERO WTEDY ustawia flagę i woła notifyAll(). Czekający
         * (pętla while) musi przetrwać cały chaos i zakończyć się poprawnie po sygnale.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TimedWaitFallbackWithMaxAttemptsReport {
        /*
         * 🧪 Zadanie 19:
         * Czekający ma budżet maks. 8 prób `lock.wait(250)` (~2s). Uruchom scenariusz
         * 5 razy: za każdym razem (na podstawie Random z ustalonym seedem) losuj, czy
         * prawdziwy sygnał przyjdzie W CZASIE budżetu, czy wcale. Dla każdej z 5 prób
         * wypisz wynik: "SUKCES" (warunek spełniony w budżecie) albo "TIMEOUT"
         * (budżet wyczerpany) – wątek zawsze kończy się samodzielnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_CombinedIfBugAndWhileFixSideBySide {
        /*
         * 🧪 Zadanie 20:
         * Uruchom RÓWNOLEGLE dwa niezależne scenariusze z identycznym wzorcem sygnałów
         * (1 fałszywe notifyAll + 1 prawdziwe po 300ms): jeden z czekającym używającym
         * `if` (ograniczony do jednego wait(500), bezpieczny czasowo), drugi z `while`.
         * Po zakończeniu obu (bounded join 5s) wypisz tabelę porównawczą: wariant,
         * finalny zaobserwowany stan warunku, czy wynik jest poprawny.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RobustBoundedBufferAgainstChaosNotifications {
        /*
         * 🧪 Zadanie 21:
         * Wykorzystaj BoundedBuffer (Lesson15) o pojemności 4. Uruchom 2 producentów
         * (po 10 elementów, łącznie 20) i 2 konsumentów, plus wątek "chaos" wołający
         * 20 losowo rozłożonych w czasie notifyAll() na monitorze bufora bez zmiany
         * jego zawartości. Zweryfikuj (bounded join 15s), że mimo chaosu dostarczono
         * dokładnie 20 elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiConditionGatekeeperWithChaos {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj "bramkarza" czekającego w `while (!(flagA && flagB && flagC))
         * lock.wait();`, przepuszczającego dalej TYLKO gdy wszystkie trzy flagi są true.
         * Wątek "chaos" 10 razy woła notifyAll() w losowych momentach, gdy TYLKO
         * NIEKTÓRE flagi są ustawione. Main ustawia flagi jedna po drugiej (w odstępach
         * 150ms) z prawdziwym notifyAll() po każdej. Zweryfikuj, że bramkarz przepuścił
         * dopiero gdy WSZYSTKIE trzy były rzeczywiście true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_RetryBudgetWithExponentialBackoff {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj czekającego z rosnącymi timeoutami `lock.wait()`: 100ms, 200ms,
         * 400ms, 800ms (maks. 4 próby, budżet ~1.5s), sprawdzając warunek w pętli
         * while po każdej próbie. Main ustawia warunek po ok. 600ms. Wypisz, przy
         * której próbie (i po jakim łącznym czasie) warunek został wykryty jako spełniony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_FullSystemStressWithLogging {
        /*
         * 🧪 Zadanie 24:
         * Zbuduj scenariusz: BoundedBuffer o pojemności 5, 3 producentów (po 10
         * elementów, łącznie 30), 3 konsumentów, wątek "chaos" wołający notifyAll()
         * 30 razy rozłożonych w czasie działania testu. Zbieraj statystyki: liczbę
         * prawdziwych notifyAll (z produkcji/konsumpcji) i liczbę fałszywych (chaos).
         * Po zakończeniu (bounded join 15s) wypisz raport końcowy z tymi statystykami
         * i potwierdzeniem, że dostarczono 30 elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_SpuriousWakeupUnitTestHarness {
        /*
         * 🧪 Zadanie 25:
         * Napisz metodę verifySafeWaitPattern(), która uruchamia poprawny wzorzec
         * `while(!ready) wait()` z jednym wstrzykniętym fałszywym notifyAll() przed
         * prawdziwym, i zwraca true/false czy wynik był poprawny. Wywołaj ją 20 razy
         * pod rząd, wypisując PASS/FAIL dla każdej próby oraz podsumowanie
         * "X/20 poprawnych" na końcu (oczekiwane: 20/20).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_TimeBoundedElectionSimulation {
        /*
         * 🧪 Zadanie 26:
         * Zasymuluj prostą "elekcję lidera": 5 wątków-kandydatów czeka
         * (`while (!electionDone) lock.wait(200)`, maks. 10 prób) na wspólnym monitorze.
         * Osobny wątek po 300ms losowo (Random z ustalonym seedem) wybiera ID lidera
         * (1-5), ustawia electionDone = true i wywołuje notifyAll(). Zweryfikuj
         * (bounded join 5s), że wszyscy kandydaci "poznali" to samo ID lidera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_NestedWaitConditionsWithCleanupOnTimeout {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj czekającego z DWOMA zagnieżdżonymi warunkami: zewnętrznym
         * (serviceStarted, budżet 5 prób wait(200)) i wewnętrznym (dataLoaded, budżet
         * 5 prób wait(200)), sprawdzanymi w osobnych pętlach while. Przetestuj DWA
         * przebiegi main(): (a) oba warunki ustawione w budżecie – pełny sukces,
         * (b) tylko serviceStarted ustawiony, dataLoaded nigdy – wypisz komunikat
         * "częściowa awaria, sprzątanie" i zakończ się bez zawieszenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CombinedRaceConditionAndSpuriousWakeupDemo {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj scenariusz: 4 wątki robocze czekają w `while (!started) lock.wait()`
         * na wspólny sygnał startowy, a PO otrzymaniu go każdy zwiększa (WEWNĄTRZ tego
         * samego bloku synchronized, chroniącego przed race condition) wspólny licznik
         * o 1000 w pętli. Dodatkowy wątek "chaos" woła notifyAll() 5 razy przed
         * prawdziwym sygnałem startowym. Zweryfikuj (bounded join 10s), że żaden
         * wątek nie zaczął liczyć przed prawdziwym startem i że finalny licznik = 4000.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_GracefulDegradationUnderPersistentChaos {
        /*
         * 🧪 Zadanie 29:
         * Wątek "chaos" woła notifyAll() co 10ms PRZEZ CAŁY CZAS TRWANIA testu (2 sekundy,
         * kontrolowane własną flagą stopChaos, którą main ustawia po 2s), bez zmiany
         * strzeżonej flagi. Czekający (pętla while, bez sztywnego limitu prób, ale
         * z timeoutem 100ms na każdy wait – więc zawsze może sprawdzić warunek) musi
         * poprawnie wykryć prawdziwy warunek ustawiony przez main po ok. 1s, mimo
         * ciągłego "szumu". Bounded join (5s) obu wątków po zakończeniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullSpuriousWakeupResilienceSuite {
        /*
         * 🧪 Zadanie 30:
         * Połącz wszystkie techniki z lekcji: BoundedBuffer (pojemność 5) z 2
         * producentami (po 10 elementów = 20) i 2 konsumentami działającymi wg
         * poprawnego wzorca while, wątek "chaos" wołający notifyAll() w losowych
         * odstępach przez cały czas trwania testu, oraz budżetowane oczekiwanie
         * z timeoutem tam, gdzie potrzebne. Na koniec (bounded join 15s na WSZYSTKICH
         * wątkach, w tym chaos) wypisz raport: liczba wyprodukowanych elementów,
         * liczba skonsumowanych, liczba fałszywych notyfikacji chaosu, oraz
         * potwierdzenie braku błędnych zachowań (0 przedwczesnych zakończeń).
         */
        public static void main(String[] args) { }
    }
}
