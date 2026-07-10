package com.example.javaquest._15_jvm_internals.Lesson17_ThreadDumpBasics;

public class _Exercises_Lesson17_ThreadDumpBasics {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PrintThreadCount {
        /*
         * 🧪 Zadanie 1:
         * Przez ThreadMXBean.getThreadCount() wypisz aktualną liczbę żywych
         * wątków w tej JVM.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise02_DumpAllThreadsAndPrintNames {
        /*
         * 🧪 Zadanie 2:
         * Wywołaj ThreadMXBean.dumpAllThreads(false, false) i wypisz same
         * nazwy wszystkich zwróconych wątków (bez stack trace).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise03_PrintThreadStatesDistribution {
        /*
         * 🧪 Zadanie 3:
         * Dla wszystkich wątków z dumpAllThreads policz, ile jest w każdym
         * Thread.State (NEW/RUNNABLE/BLOCKED/WAITING/TIMED_WAITING/
         * TERMINATED) i wypisz to jako mapę licznik->stan.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise04_PrintStackTraceOfCurrentThread {
        /*
         * 🧪 Zadanie 4:
         * Znajdź w wyniku dumpAllThreads wpis odpowiadający
         * Thread.currentThread() (po nazwie) i wypisz jego pełny stack trace.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise05_SimpleTwoThreadDeadlock {
        /*
         * 🧪 Zadanie 5:
         * Odtwórz klasyczny 2-wątkowy deadlock z lekcji (dwie blokady,
         * odwrotna kolejność, watki daemon, sleep(300) między zdobyciem
         * pierwszej a druga blokada). Uruchom go z bounded join(2000) na
         * obu wątkach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise06_DetectDeadlockAfterDelay {
        /*
         * 🧪 Zadanie 6:
         * Rozszerz Zadanie 5: po starcie wątków odczekaj 600ms, a następnie
         * wywołaj ThreadMXBean.findDeadlockedThreads() i wypisz, czy zwrócił
         * null czy tablicę identyfikatorów wątków.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise07_PrintLockNameAndOwner {
        /*
         * 🧪 Zadanie 7:
         * Dla wykrytych zakleszczonych wątków z Zadania 6 wypisz dla każdego
         * getLockName() (na czym czeka) i getLockOwnerName() (kto to trzyma).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise08_CompareRunningVsBlockedStack {
        /*
         * 🧪 Zadanie 8:
         * Porównaj stack trace zwykłego, aktywnie liczącego wątku (pętla
         * obliczeniowa) ze stack trace wątku zablokowanego na synchronized -
         * wypisz oba i wskaż (println) widoczną różnicę (np. metoda z
         * java.lang.Object.wait/monitor na szczycie).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise09_ExplainJstackVsJcmdOnPaper {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: w komentarzu zapisz dokładną komendę `jstack` oraz
         * `jcmd` do pobrania thread dumpu procesu o PID 54321, i wyjaśnij
         * jedną różnicę między nimi.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise10_VerifyDaemonThreadsAllowExit {
        /*
         * 🧪 Zadanie 10:
         * Uruchom scenariusz deadlocka z Zadania 5, wypisz Thread.isDaemon()
         * dla obu wątków (powinno być true) i skomentuj (println), dlaczego
         * to pozwala main() zakończyć się mimo trwającego zakleszczenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ThreeThreadCircularDeadlockDetection {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj cykliczny deadlock 3 wątków (A->B, B->C, C->A, trzy
         * blokady), uruchom z watkami daemon, odczekaj 600ms i wykryj go
         * przez findDeadlockedThreads() - wypisz wszystkie 3 zakleszczone
         * wątki z ich stack trace.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise12_NoDeadlockControlCase {
        /*
         * 🧪 Zadanie 12:
         * Uruchom DWA wątki, które zdobywają te same 2 blokady w TEJ SAMEJ
         * kolejności (bez deadlocka), poczekaj 600ms i zweryfikuj, że
         * findDeadlockedThreads() zwraca null - jako "przypadek kontrolny"
         * potwierdzający, że wykrywanie nie daje fałszywych alarmów.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise13_ThreadDumpReportBuilder {
        /*
         * 🧪 Zadanie 13:
         * Napisz metodę `String buildThreadDumpReport()`, która zwraca
         * sformatowany tekst: dla każdego wątku nazwa, stan i pierwsze 3
         * linie stack trace (jeśli dostępne). Wywołaj ją i wypisz wynik.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise14_WatchdogPollingForDeadlock {
        /*
         * 🧪 Zadanie 14:
         * Uruchom scenariusz deadlocka z Zadania 5, a obok wątek-obserwator
         * (daemon), który co 200ms (maks. 2 sekundy) sprawdza
         * findDeadlockedThreads() i jak tylko wykryje deadlock, wypisuje
         * "wykryto po X ms" i kończy obserwację.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise15_CompareLockedMonitorsFlagImpact {
        /*
         * 🧪 Zadanie 15:
         * Wywołaj dumpAllThreads raz z lockedMonitors=false, raz z true dla
         * wątków trzymających synchronized blokadę. Wypisz różnicę w
         * dostępnych informacjach (np. getLockedMonitors().length).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise16_FilterThreadsByNamePrefix {
        /*
         * 🧪 Zadanie 16:
         * Uruchom 5 wątków nazwanych "Worker-1".."Worker-5" (proste zadanie
         * ze sleep) oraz kilka innych o innych nazwach. Z dumpAllThreads
         * wyfiltruj i wypisz TYLKO te zaczynające się od "Worker-".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise17_DeadlockRegressionTestHelper {
        /*
         * 🧪 Zadanie 17:
         * Napisz metodę `boolean isDeadlocked(Runnable taskA, Runnable
         * taskB, long waitMs)`, która uruchamia oba zadania jako wątki
         * daemon, czeka waitMs i zwraca true, jeśli
         * findDeadlockedThreads() wykrył deadlock. Przetestuj ją na
         * scenariuszu z Zadania 5 (oczekiwane true) i Zadania 12 (oczekiwane
         * false).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise18_StackTraceDepthComparison {
        /*
         * 🧪 Zadanie 18:
         * Zbuduj wątek wykonujący 5 zagnieżdżonych wywołań metod (A wywołuje
         * B wywołuje C wywołuje D wywołuje E, gdzie E śpi 500ms), zrób jego
         * dump w trakcie snu i wypisz pełny stack trace - zweryfikuj, że
         * widać wszystkie 5 metod w kolejności wywołań.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise19_CombineThreadDumpWithMemoryUsage {
        /*
         * 🧪 Zadanie 19:
         * Zbuduj "mini raport diagnostyczny" łączący liczbę wątków
         * (ThreadMXBean.getThreadCount()) z aktualnym used heap
         * (MemoryMXBean, nawiązanie do Lesson06) - wypisz oba w jednej
         * metodzie wywoływanej co 300ms przez 1.2 sekundy obciążenia.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise20_ExplainCliOutputFormatOnPaper {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: w komentarzu naszkicuj (jako tekst) jak wyglądałby
         * fragment realnego outputu `jstack` dla dwóch zakleszczonych
         * wątków, na wzór tego co wypisuje Twój kod z ThreadInfo w tej
         * lekcji (nazwa, stan, "waiting to lock", "which is held by").
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_DiningPhilosophersWithDetection {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj naiwną symulację "ucztujących filozofów" (3 filozofów, 3
         * widelce, ta sama naiwna kolejność), uruchom jako wątki daemon i
         * wykryj deadlock przez findDeadlockedThreads() zamiast tylko
         * bounded join - wypisz pełne informacje o wszystkich zakleszczonych
         * wątkach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise22_ExecutorServiceDeadlockDetection {
        /*
         * 🧪 Zadanie 22:
         * Wyślij do ExecutorService (Lesson21 z _05_multithreading) dwa
         * zadania odtwarzające klasyczny deadlock. Zweryfikuj, że pula NIE
         * zamyka się w rozsądnym czasie (awaitTermination z timeoutem
         * zwraca false), wykryj deadlock przez ThreadMXBean, a na końcu
         * wymuś shutdownNow().
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise23_PeriodicThreadDumpToFile {
        /*
         * 🧪 Zadanie 23:
         * Podczas 2-sekundowego obciążenia zapisuj co 500ms tekstowy
         * "thread dump" (wynik buildThreadDumpReport z Zadania 13) do 4
         * kolejnych plików w katalogu tymczasowym. Wypisz listę utworzonych
         * plików i ich rozmiary.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise24_MultipleDeadlockClustersDetection {
        /*
         * 🧪 Zadanie 24:
         * Uruchom RÓWNOCZEŚNIE dwie NIEZALEŻNE pary wątków, z których każda
         * tworzy własny deadlock (dwie różne pary blokad). Zweryfikuj, że
         * findDeadlockedThreads() wykrywa WSZYSTKIE 4 zakleszczone wątki
         * naraz, i pogrupuj je (println) po parach.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise25_LivelockVsDeadlockComparison {
        /*
         * 🧪 Zadanie 25:
         * Zbuduj scenariusz livelocku (dwa wątki grzecznie się "ustępują",
         * tryLock+natychmiastowe wycofanie w pętli bez postępu przez 1
         * sekundę, bounded) obok klasycznego deadlocka. Zweryfikuj, że
         * findDeadlockedThreads() wykrywa TYLKO wątki deadlocka, nie
         * livelocku - wyjaśnij (println) dlaczego.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise26_ThreadDumpDiffBetweenTwoMoments {
        /*
         * 🧪 Zadanie 26:
         * Zrób dwa dumpAllThreads w odstępie 500ms podczas zmiennego
         * obciążenia (różne wątki startują/kończą się między pomiarami).
         * Napisz metodę porównującą oba zestawy nazw wątków i wypisującą,
         * które wątki się pojawiły, a które zniknęły.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise27_LockChainThreeLevels {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj scenariusz z 3 wątkami, gdzie Wątek1 czeka na blokadę
         * trzymaną przez Wątek2, a Wątek2 czeka na blokadę trzymaną przez
         * Wątek3 (bez cyklu - to NIE jest deadlock). Zweryfikuj, że
         * findDeadlockedThreads() zwraca null, ale ręcznie (z getLockName/
         * getLockOwnerName) odtwórz i wypisz cały "łańcuch oczekiwania".
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise28_AutomatedDeadlockAlertSystem {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj wątek-strażnika (daemon) monitorujący aplikację przez
         * maksymalnie 3 sekundy, który przy pierwszym wykryciu deadlocka
         * automatycznie wykonuje PEŁNY raport: heap dump (nawiązanie do
         * Lesson16) ORAZ pełny thread dump wszystkich zakleszczonych
         * wątków, oba do plików w jednym katalogu tymczasowym.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise29_ChaosTestDeadlockDetectionReliability {
        /*
         * 🧪 Zadanie 29:
         * Uruchom 10 rund (bounded) klasycznego scenariusza deadlocka z
         * lekko losowym czasem snu, i w KAŻDEJ rundzie zweryfikuj przez
         * findDeadlockedThreads(), czy wykrycie zadziałało. Wypisz, w ilu z
         * 10 rund wykrycie się powiodło (oczekiwane: we wszystkich, bo
         * detekcja JVM nie zależy od timingu tak jak isAlive()).
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }

    static class Exercise30_FullDiagnosticCapstoneThreadsAndLocks {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny scenariusz: 2 wątki robocze liczące (bez
         * problemów), 2 wątki w klasycznym deadlocku i 1 wątek-obserwator.
         * Wygeneruj jeden spójny raport tekstowy zawierający: łączną liczbę
         * wątków, listę wątków RUNNABLE z ich stack trace, oraz pełne
         * informacje o wykrytym deadlocku (locki, właściciele, stack
         * trace). Zapisz raport do pliku w katalogu tymczasowym i wypisz
         * jego ścieżkę.
         */
        public static void main(String[] args) {
            // TODO: twój kod tutaj
        }
    }
}
