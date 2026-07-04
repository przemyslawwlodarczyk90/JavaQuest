package com.example.javaquest._05_multithreading.Lesson37_CommonMistakes;

public class _Exercises_Lesson37_CommonMistakes {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_RunVsStartMistake {
        /*
         * 🧪 Zadanie 1:
         * Utwórz wątek i wywołaj na nim run() bezpośrednio - wypisz Thread.currentThread().getName()
         * wewnątrz, pokazując że wykonało się w wątku main. Następnie utwórz drugi wątek
         * i wywołaj poprawnie start()+join(), pokazując że tym razem nazwa wątku jest inna.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MissingJoinMistake {
        /*
         * 🧪 Zadanie 2:
         * Uruchom wątek ustawiający wynik (int[] result) po sleep(150) i odczytaj wynik
         * NATYCHMIAST bez join() (pokaż błędną, nieaktualną wartość). Następnie powtórz
         * z join(1000) przed odczytem i pokaż poprawną wartość.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RaceConditionSynchronizedFix {
        /*
         * 🧪 Zadanie 3:
         * Zaimplementuj count++ na zwykłym int wykonywane przez 2 wątki x 50 000 razy,
         * pokazując że wynik bywa błędny. Popraw to opakowując inkrementację w blok
         * synchronized i zweryfikuj, że wynik zawsze jest poprawny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_RaceConditionAtomicFix {
        /*
         * 🧪 Zadanie 4:
         * Napraw ten sam problem wyścigu z Zadania 3, tym razem używając AtomicInteger.
         * Wypisz poprawny wynik końcowy i porównaj (w komentarzu/println) to podejście
         * z poprawką przez synchronized z poprzedniego zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_WaitOutsideSynchronizedMistake {
        /*
         * 🧪 Zadanie 5:
         * Wywołaj lock.wait() BEZ synchronized(lock) i złap IllegalMonitorStateException,
         * wypisując jego komunikat. Następnie popraw kod, wywołując lock.wait(200)
         * wewnątrz synchronized(lock) i pokaż, że tym razem nie ma wyjątku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_NotifySingleWaiterMistake {
        /*
         * 🧪 Zadanie 6:
         * Utwórz 2 wątki-demony czekające (wait()) na tym samym monitorze. Wywołaj notify()
         * (budzi tylko jeden) i po join() z limitem czasu wypisz, który z wątków się
         * zakończył, a który prawdopodobnie nadal czeka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_NotifyAllFix {
        /*
         * 🧪 Zadanie 7:
         * Powtórz scenariusz z Zadania 6, ale tym razem użyj notifyAll() zamiast notify().
         * Zweryfikuj i wypisz, że OBA wątki się zakończyły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ProperExecutorShutdownDemo {
        /*
         * 🧪 Zadanie 8:
         * Utwórz ExecutorService, prześlij jedno zadanie i POPRAWNIE wywołaj shutdown()
         * oraz awaitTermination(). Wypisz pool.isTerminated(), potwierdzając czysty stan
         * (kontrastując w komentarzu z pominięciem shutdown(), którego NIE wykonujesz).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_LostInterruptStatusMistake {
        /*
         * 🧪 Zadanie 9:
         * Zaimplementuj workera, który w catch(InterruptedException) NIE przywraca statusu
         * przerwania (błąd). Po zakończeniu zweryfikuj i wypisz, że status został utracony.
         * Popraw kod dodając Thread.currentThread().interrupt() i wypisz, że tym razem
         * status jest zachowany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_MismatchedLockObjectsMistake {
        /*
         * 🧪 Zadanie 10:
         * Utwórz 2 wątki synchronizujące się na RÓŻNYCH obiektach blokady przy aktualizacji
         * wspólnego licznika (mimo pozornej synchronizacji wynik bywa błędny). Popraw to,
         * używając JEDNEGO wspólnego obiektu blokady, i porównaj wyniki obu wersji.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BankAccountTransferBug {
        /*
         * 🧪 Zadanie 11:
         * Zaimplementuj "konto bankowe" z niesynchronizowanymi metodami deposit/withdraw
         * wywoływanymi równolegle 100 000 razy w drobnych transferach - pokaż, że saldo
         * końcowe bywa błędne. Napraw dodając synchronized do obu metod i zweryfikuj
         * poprawność salda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_MultipleMissingJoins {
        /*
         * 🧪 Zadanie 12:
         * Uruchom 5 workerów liczących cząstkowe sumy do wspólnej tablicy. Zsumuj wynik
         * BEZ czekania na wszystkie join() (błędny, niekompletny wynik). Popraw dodając
         * join() na każdy z 5 wątków przed sumowaniem i porównaj oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_RawThreadsVsExecutorLeak {
        /*
         * 🧪 Zadanie 13:
         * Opisz (w komentarzu) problem "wycieku wątków" przy tworzeniu nowego surowego
         * wątku dla każdego z wielu zadań bez żadnego zarządzania. Zaimplementuj POPRAWNĄ
         * wersję używającą ExecutorService z properowym shutdown() i porównaj rezultat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_NotifyAllWrongLockObject {
        /*
         * 🧪 Zadanie 14:
         * Wywołaj notifyAll() na INNYM obiekcie niż ten, na którym czekający wątek wywołał
         * wait(300) (z timeoutem, dla bezpieczeństwa) - pokaż, że wątek czekający "wybudza się"
         * dopiero po timeout, a nie od razu. Popraw kod, wywołując notifyAll() na właściwym,
         * współdzielonym obiekcie i pokaż natychmiastowe wybudzenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_UnsafeHashMapToConcurrentHashMap {
        /*
         * 🧪 Zadanie 15:
         * Zaimplementuj 4 wątki zapisujące równolegle do zwykłej (niebezpiecznej wątkowo)
         * HashMap, obserwując i bezpiecznie obsługując ewentualne niespójności lub wyjątki.
         * Popraw kod, zamieniając na ConcurrentHashMap, i zweryfikuj poprawny końcowy rozmiar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_JoinTimeoutTooShortMistake {
        /*
         * 🧪 Zadanie 16:
         * Uruchom zadanie potrzebujące 300 ms i wywołaj join(50) (zbyt krótki limit) -
         * wypisz, że wynik jest jeszcze niekompletny. Popraw kod, używając odpowiednio
         * długiego limitu (np. join(1000)), i wypisz kompletny wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SpuriousWakeupIfVsWhile {
        /*
         * 🧪 Zadanie 17:
         * Zaimplementuj konsumenta używającego "if (warunek) wait();" zamiast pętli while
         * - wskaż w komentarzu ryzyko spurious wakeup. Popraw kod na "while (warunek) wait();"
         * i zweryfikuj poprawność działania producenta-konsumenta w ograniczonej liczbie iteracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_SwallowedExceptionExecuteVsSubmit {
        /*
         * 🧪 Zadanie 18:
         * Prześlij do ExecutorService zadanie rzucające wyjątek metodą execute() (wyjątek
         * "znika" - jest tylko wypisany w domyślnym handlerze). Popraw kod, używając submit()
         * i Future.get() w try/catch(ExecutionException), poprawnie przechwytując wyjątek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_SynchronizedOnThisVsStaticLock {
        /*
         * 🧪 Zadanie 19:
         * Utwórz 2 instancje klasy z metodą synchronized (blokującą na "this") aktualizującą
         * WSPÓLNY licznik statyczny z osobnych wątków - pokaż, że synchronizacja na instancji
         * nie chroni pola statycznego. Popraw kod, używając wspólnej statycznej blokady,
         * i zweryfikuj poprawność wyniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_MistakeDemoRunner {
        /*
         * 🧪 Zadanie 20:
         * Napisz metodę runMistakeDemo(String name, Runnable badVersion, Runnable goodVersion)
         * wypisującą etykiety "PRZED (błąd)" / "PO (poprawka)" wokół uruchomienia obu wersji.
         * Użyj jej do połączenia w jedną demonstrację dwóch wcześniejszych par błąd/poprawka
         * (np. race condition i brakujący join).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InventoryServiceThreeMistakesFixed {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj serwis magazynowy naprawiający naraz 3 błędy: (a) wyścig na liczniku stanu
         * magazynowego naprawiony AtomicInteger, (b) brakujący join przed raportem naprawiony
         * przez zaczekanie na wszystkie wątki/zadania, (c) niezamknięty ExecutorService
         * naprawiony shutdown()+awaitTermination(). Przetwórz 200 symulowanych sprzedaży
         * na 4 workerach i wypisz spójny stan końcowy magazynu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_DiagnoseHangingThreadsWithDump {
        /*
         * 🧪 Zadanie 22:
         * Uruchom 3 nazwane wątki wykonujące ograniczone czasowo operacje (NIE prawdziwe
         * zawieszenie) o różnej długości. Za pomocą Thread.getAllStackTraces() zrób migawkę
         * po 150 ms i zidentyfikuj, który wątek(i) wciąż działają. Zaczekaj na wszystkie
         * z limitem czasu na końcu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_NotifyThenNotifyAllPipeline {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj pipeline z wieloma producentami i konsumentami dzielącymi jeden lock/kolejkę.
         * Pierwsza wersja używa notify() (część konsumentów może "głodować" - zabezpiecz
         * demonstrację wątkami-demonami i timeoutami jak w lekcji). Druga wersja używa
         * notifyAll() oraz pętli while na warunku - zweryfikuj, że WSZYSCY konsumenci kończą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SafeVsStopStyleConsistency {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj workera aktualizującego dwupolowy stan (np. fromBalance/toBalance
         * reprezentujące przelew) z kooperacyjnym zamykaniem (flaga+interrupt), sprawdzanym
         * WYŁĄCZNIE między parami aktualizacji. Po wielu wymuszonych, wczesnych zamknięciach
         * zweryfikuj, że suma obu pól zawsze pozostaje stała (spójny stan) - NIGDY nie
         * wywołuj prawdziwego Thread.stop().
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_LeakedExecutorRegistry {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj klasę TrackingExecutorFactory tworzącą pule i rejestrującą je
         * w statycznej liście niezamkniętych executorów, oraz metodę shutdownAllTracked()
         * zamykającą wszystkie zarejestrowane pule. Utwórz 3 pule przez tę fabrykę, zapomnij
         * ręcznie zamknąć jedną z nich, a na końcu wywołaj shutdownAllTracked() i zweryfikuj,
         * że wszystkie 3 są terminated.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MissingExceptionallyInCompletableFuture {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj pipeline CompletableFuture BEZ exceptionally() dla 10 wejść (część powoduje
         * symulowany błąd) i pokaż, że get() rzuca ExecutionException dla feralnych wejść.
         * Popraw kod dodając exceptionally() z wartością zastępczą i porównaj, które wejścia
         * "wywaliły" pierwszą wersję, a które druga wersja obsłużyła łagodnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_UnsafeSharedListWithVirtualThreads {
        /*
         * 🧪 Zadanie 27:
         * Uruchom tysiące zadań na wątkach wirtualnych dodających do WSPÓLNEJ, niebezpiecznej
         * wątkowo zwykłej ArrayList (bezpiecznie obsłuż ewentualne wyjątki). Popraw kod,
         * używając Collections.synchronizedList() lub bezpiecznej kolekcji, i zweryfikuj,
         * że rozmiar końcowy zawsze odpowiada oczekiwanej liczbie elementów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_FiveMistakesInOnePipelineIncrementalFix {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj pipeline przetwarzania zamówień z kilkoma połączonymi (ale bezpiecznie
         * ograniczonymi w czasie) błędami naraz, dającą błędny raport końcowy. Napraw je
         * KOLEJNO w trzech wersjach (v1 -> v2 -> v3), wypisując raport po każdej poprawce
         * i pokazując stopniową zbieżność do poprawnych, oczekiwanych liczb.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_SelfCheckTestHarness {
        /*
         * 🧪 Zadanie 29:
         * Napisz prostą metodę assertEquals(String label, Object expected, Object actual)
         * (oparta na println, bez frameworka testowego) i użyj jej do zweryfikowania
         * poprawionych (wolnych od błędów) wersji co najmniej 4 wcześniejszych zadań z tej
         * lekcji (race condition, brak join, notifyAll, zamknięcie executora) w jednym
         * uruchomieniu, wypisując PASS/FAIL dla każdego sprawdzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_MysteryBugWordCounter {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj "tajemniczy błąd": licznik słów w tekście liczony przez 5 wątków do
         * wspólnej, niesynchronizowanej HashMap<String,Integer>, gdzie main odczytuje wynik
         * BEZ join() - pokaż, że wynik bywa niepoprawny/niekompletny. Napraw to (ConcurrentHashMap
         * lub synchronizowane inkrementacje ORAZ poprawne join()) i zweryfikuj, że wynik
         * dokładnie odpowiada ręcznie policzonemu oczekiwanemu rozkładowi słów dla ustalonego
         * tekstu wejściowego.
         */
        public static void main(String[] args) { }
    }
}
