package com.example.javaquest._05_multithreading.Lesson05_ThreadBasicMethods;

public class _Exercises_Lesson05_ThreadBasicMethods {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_GetAndSetName {
        /*
         * 🧪 Zadanie 1:
         * Utwórz wątek z lambdą, sprawdź jego domyślną nazwę (getName()), a
         * następnie ustaw nazwę setName("MojWatek") PRZED wywołaniem start().
         * Uruchom (start()+join()) i wypisz nazwę wewnątrz run().
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CurrentThreadInMain {
        /*
         * 🧪 Zadanie 2:
         * Wypisz Thread.currentThread().getName() w main oraz wewnątrz lambdy
         * uruchomionej w nowym wątku (start()+join()). Porównaj obie wartości.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SleepBasicTiming {
        /*
         * 🧪 Zadanie 3:
         * Zmierz (System.currentTimeMillis() przed/po) czas wykonania
         * Thread.sleep(300) w main. Wypisz zmierzony czas i porównaj go z
         * oczekiwanym (~300 ms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_JoinWithoutTimeout {
        /*
         * 🧪 Zadanie 4:
         * Utwórz wątek wykonujący Thread.sleep(200) i wypisujący komunikat na
         * końcu. Wywołaj join() (bez limitu) w main i wypisz komunikat "watek
         * na pewno zakonczony" DOPIERO PO join().
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_JoinWithTimeoutTooShort {
        /*
         * 🧪 Zadanie 5:
         * Utwórz wątek śpiący 1000ms, wywołaj join(100) (za krótki timeout) i
         * sprawdź isAlive() zaraz po – powinno zwrócić true (wątek wciąż działa).
         * Na koniec dociągnij pełny join(2000).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_JoinWithTimeoutEnough {
        /*
         * 🧪 Zadanie 6:
         * Dla wątku śpiącego 300ms wywołaj join(2000) (wystarczający timeout) i
         * sprawdź isAlive() po – powinno zwrócić false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IsAliveBeforeStart {
        /*
         * 🧪 Zadanie 7:
         * Utwórz wątek (bez startowania), wypisz isAlive() (oczekiwane false).
         * Następnie wystartuj i sprawdź ponownie (prawdopodobnie true), a na
         * końcu, po join(), sprawdź ponownie (oczekiwane false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_DefaultPriority {
        /*
         * 🧪 Zadanie 8:
         * Utwórz 3 wątki bez ustawiania priorytetu i wypisz ich getPriority()
         * (powinno być 5, czyli Thread.NORM_PRIORITY, dla każdego).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_SetMinAndMaxPriority {
        /*
         * 🧪 Zadanie 9:
         * Utwórz 2 wątki, ustaw jednemu Thread.MIN_PRIORITY, drugiemu
         * Thread.MAX_PRIORITY. Wypisz oba priorytety przed startem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_NamedThreadsList {
        /*
         * 🧪 Zadanie 10:
         * Utwórz tablicę 5 wątków z nazwami "Zadanie-1".."Zadanie-5" (przez
         * konstruktor Thread(Runnable, String)). Uruchom je sekwencyjnie
         * (start()+join() każdy), wypisując getName() wewnątrz run().
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultipleSleepsAccumulatedTiming {
        /*
         * 🧪 Zadanie 11:
         * Zmierz łączny czas 3 kolejnych Thread.sleep() (100ms, 150ms, 200ms)
         * wykonanych jeden po drugim w main. Porównaj sumę zmierzonego czasu z
         * oczekiwaną sumą (~450 ms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_JoinTimeoutPollingLoop {
        /*
         * 🧪 Zadanie 12:
         * Utwórz wątek śpiący 500ms. W pętli wywołuj join(100) MAKSYMALNIE 10 razy,
         * za każdym razem sprawdzając isAlive() i licząc, ile "cykli" zajęło
         * zakończenie wątku – przerwij pętlę wcześniej, jeśli isAlive() zwróci false.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_WorkerResultAfterJoin {
        /*
         * 🧪 Zadanie 13:
         * Utwórz wątek liczący silnię liczby 10 (zapis wyniku do współdzielonej
         * tablicy long[1]). Main czeka przez join() i DOPIERO PO NIM odczytuje
         * wynik z tablicy – wypisz go i potwierdź poprawność (10! = 3628800).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_PriorityDoesNotGuaranteeOrder {
        /*
         * 🧪 Zadanie 14:
         * Utwórz 5 wątków z różnymi priorytetami (1, 3, 5, 7, 10), każdy
         * wypisujący swoją nazwę i priorytet. Uruchom wszystkie naraz (bez
         * natychmiastowego join), zbierz kolejność zakończenia do wspólnej,
         * zsynchronizowanej listy. Po dociągnięciu joinów wypisz zebraną
         * kolejność i skomentuj, że NIE musi odpowiadać priorytetom.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_RenamingThreadDuringExecution {
        /*
         * 🧪 Zadanie 15:
         * Utwórz wątek, który w trakcie run() (po krótkim sleep) sam sobie zmienia
         * nazwę przez Thread.currentThread().setName("Zmieniona-W-Trakcie") i
         * wypisuje nazwę przed oraz po zmianie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultipleJoinsOnSameThread {
        /*
         * 🧪 Zadanie 16:
         * Utwórz jeden wątek (sleep 200ms), wywołaj na nim join() DWA razy pod
         * rząd z main. Zmierz czas obu wywołań join() i porównaj – drugie
         * wywołanie powinno zwrócić niemal natychmiast (bo wątek już się skończył).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SleepInterruptedHandling {
        /*
         * 🧪 Zadanie 17:
         * Utwórz wątek wywołujący Thread.sleep(2000) w try-catch. Main po 100ms
         * wywołuje na nim interrupt(). W catch (InterruptedException) wątek ma
         * wypisać komunikat i zakończyć się wcześniej niż zaplanowane 2000ms.
         * Zmierz faktyczny czas trwania wątku (powinien być bliski 100ms).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ThreadNameBasedRouting {
        /*
         * 🧪 Zadanie 18:
         * Utwórz 4 wątki o nazwach zawierających prefiksy "IO-" lub "CPU-" (np.
         * "IO-1", "CPU-1", "IO-2", "CPU-2"). W run() każdy sprawdza WŁASNĄ nazwę
         * (Thread.currentThread().getName()) i na tej podstawie wypisuje inny
         * komunikat w zależności od prefiksu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_JoinTimeoutReport {
        /*
         * 🧪 Zadanie 19:
         * Dla 3 wątków o różnym czasie trwania (100ms, 500ms, 1000ms) wywołaj
         * join(300) na KAŻDYM z nich i zbuduj raport informujący, które wątki
         * zdążyły się zakończyć w tym czasie (isAlive() po join(300)), a które
         * nie. Na końcu dociągnij (join z większym limitem) pozostałe.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PriorityAndNameCombinedReport {
        /*
         * 🧪 Zadanie 20:
         * Utwórz 6 wątków z różnymi kombinacjami nazwy i priorytetu (ustalona
         * tablica danych). Uruchom wszystkie sekwencyjnie (start()+join()),
         * budując tabelaryczny raport (nazwa, priorytet, czy zakończony)
         * wypisywany na końcu.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_TimedWorkerPoolSimulation {
        /*
         * 🧪 Zadanie 21:
         * Utwórz 5 "workerów" (wątki) z różnymi czasami trwania (sleep: 100, 200,
         * 300, 400, 500ms). Uruchom wszystkie naraz, a następnie w pętli co 50ms
         * (max 15 iteracji jako bezpiecznik) sprawdzaj przez isAlive() ile jeszcze
         * pracuje, wypisując "migawki" stanu, aż wszystkie się zakończą. Na
         * końcu dociągnij pozostałe przez join().
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MeasureActualSleepDrift {
        /*
         * 🧪 Zadanie 22:
         * Zmierz rzeczywisty czas trwania 10 kolejnych wywołań Thread.sleep(50)
         * (każde osobno mierzone). Policz średnie odchylenie od zadeklarowanych
         * 50ms (Thread.sleep nie gwarantuje dokładności) i wypisz raport (min,
         * max, średnia rzeczywistego czasu snu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_GracefulShutdownViaInterruptAndJoin {
        /*
         * 🧪 Zadanie 23:
         * Utwórz wątek wykonujący pętlę roboczą, sprawdzającą
         * Thread.currentThread().isInterrupted() co iterację (krótki sleep(50)
         * w pętli, max 100 iteracji jako bezpiecznik). Main po 300ms wywołuje
         * interrupt(), a następnie join(2000). Wypisz, po ilu iteracjach wątek
         * faktycznie się zatrzymał.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PriorityExperimentWithCpuBoundTasks {
        /*
         * 🧪 Zadanie 24:
         * Utwórz 2 wątki CPU-bound (pętla licząca sumę do 500_000_000), jednemu
         * ustaw MIN_PRIORITY, drugiemu MAX_PRIORITY. Uruchom oba naraz i zmierz,
         * który zakończy się pierwszy w 3 powtórzeniach eksperymentu. Wypisz
         * wyniki i skomentuj, że priorytet to tylko sugestia dla schedulera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_JoinTimeoutBasedTaskScheduler {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj prosty "scheduler": lista 5 zadań (wątki o różnym czasie
         * trwania). Uruchom je wszystkie naraz, ale odpytuj (join z krótkim
         * timeout np. 50ms w pętli) w kolejności ich uruchomienia, budując raport
         * "kolejność faktycznego zakończenia". Na końcu dociągnij wszystkie
         * (bezpiecznik join(5000) na każdym).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_NameBasedThreadRegistry {
        /*
         * 🧪 Zadanie 26:
         * Utwórz mapę Map<String, Thread> "rejestr" z 5 nazwanymi wątkami (różne
         * zadania). Napisz metodę waitForThread(Map<String,Thread> registry,
         * String name, long timeoutMs) używającą join(timeoutMs) i zwracającą
         * boolean (czy wątek zdążył się zakończyć). Przetestuj dla kilku
         * kombinacji nazwa/timeout.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_SleepBasedRateLimiter {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj prosty "rate limiter": wykonaj 5 "zadań" (proste println)
         * z ustalonym opóźnieniem Thread.sleep(200) między każdym. Zmierz łączny
         * czas i zweryfikuj, że jest bliski oczekiwanej wartości (uwzględnij w
         * komentarzu, czy liczysz 5 czy 4 odstępy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_CombinedPriorityAndJoinTimeoutDashboard {
        /*
         * 🧪 Zadanie 28:
         * Utwórz 8 wątków o zróżnicowanej nazwie, priorytecie i czasie trwania
         * (ustalona tabela danych). Uruchom wszystkie, a następnie w pętli
         * (bezpiecznik max 20 iteracji co 50ms) monitoruj przez isAlive(), które
         * wciąż działają, budując tekstowy "dashboard" stanu każdego wątku w
         * czasie, aż wszystkie się zakończą.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_InterruptDuringJoinItself {
        /*
         * 🧪 Zadanie 29:
         * Utwórz wątek A, który wywołuje join() na wątku B (śpiącym 2000ms).
         * Main po 200ms wywołuje A.interrupt(). Obsłuż w A InterruptedException
         * rzucony przez join() i wypisz, że A zakończył oczekiwanie przedwcześnie
         * z powodu przerwania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullThreadMethodsMiniMonitor {
        /*
         * 🧪 Zadanie 30:
         * Połącz wszystkie metody z tej lekcji w mini "monitor wątków": uruchom
         * 6 wątków o różnych nazwach, priorytetach i czasach trwania. Napisz
         * metodę buildStatusReport(Thread[] threads) zwracającą String z tabelą
         * (nazwa, priorytet, isAlive), wywoływaną cyklicznie (bezpiecznik: max 10
         * iteracji co 100ms), aż wszystkie wątki się zakończą (dociągnij
         * join(5000) na końcu dla pewności). Wypisz każdy raport w trakcie
         * monitorowania.
         */
        public static void main(String[] args) { }
    }
}
