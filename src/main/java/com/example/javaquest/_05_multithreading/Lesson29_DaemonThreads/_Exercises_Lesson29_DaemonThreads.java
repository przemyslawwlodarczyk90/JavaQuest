package com.example.javaquest._05_multithreading.Lesson29_DaemonThreads;

public class _Exercises_Lesson29_DaemonThreads {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_SetDaemonBeforeStart {
        /*
         * 🧪 Zadanie 1:
         * Utwórz wątek, wywołaj setDaemon(true) PRZED start(), a następnie
         * wypisz thread.isDaemon() zarówno przed, jak i po start(), potwierdzając
         * że flaga pozostaje true.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_SetDaemonAfterStartThrows {
        /*
         * 🧪 Zadanie 2:
         * Utwórz i wystartuj wątek (bez ustawiania daemon). Spróbuj następnie
         * wywołać setDaemon(true) PO start() i złap oczekiwany
         * IllegalThreadStateException, wypisując jego nazwę klasy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ShortMainWithDaemonMonitor {
        /*
         * 🧪 Zadanie 3:
         * Utwórz wątek demoniczny wypisujący "tick" co 100 ms w pętli while(true).
         * W main uśpij się na 350 ms, wypisz komunikat końcowy i zakończ main –
         * zaobserwuj, że program kończy się szybko mimo "nieskończonej" pętli
         * daemona (main NIE robi join() na tym wątku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_DefaultThreadIsNotDaemon {
        /*
         * 🧪 Zadanie 4:
         * Utwórz zwykły wątek (bez wywołania setDaemon) i wypisz jego isDaemon()
         * PRZED startem, potwierdzając że domyślnie każdy nowy wątek jest
         * wątkiem użytkownika (false).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ChildInheritsDaemonStatus {
        /*
         * 🧪 Zadanie 5:
         * Utwórz wątek demoniczny "Parent", który wewnątrz swojego Runnable
         * tworzy i startuje kolejny wątek "Child" (bez jawnego ustawiania
         * setDaemon). Wypisz child.isDaemon() – zaobserwuj, że dziedziczy status
         * demoniczny po wątku, który go utworzył.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_UserVsDaemonCompletionRequirement {
        /*
         * 🧪 Zadanie 6:
         * Utwórz 1 wątek UŻYTKOWNIKA wykonujący krótkie zadanie (Thread.sleep(300))
         * oraz 1 wątek DEMONICZNY z pętlą while(true) i sleep(100). Wypisz komunikat
         * końcowy main dopiero po join() na wątku użytkownika (bez join na demonie)
         * – potwierdź że program kończy się zaraz po zakończeniu wątku użytkownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_MainThreadIsNotDaemon {
        /*
         * 🧪 Zadanie 7:
         * Wypisz Thread.currentThread().isDaemon() na początku main – potwierdź,
         * że wątek main jest wątkiem UŻYTKOWNIKA (false), a nie demonicznym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_BackgroundTickCounter {
        /*
         * 🧪 Zadanie 8:
         * Utwórz wątek demoniczny zliczający tick-i w AtomicInteger co 50 ms
         * w pętli while(true). W main odczekaj 275 ms, wypisz aktualną wartość
         * licznika (spodziewaj się ok. 5 tick-ów) i zakończ main bez join().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_DaemonFlagIndependentOfPriority {
        /*
         * 🧪 Zadanie 9:
         * Utwórz wątek demoniczny z Thread.MAX_PRIORITY oraz wątek NIE-demoniczny
         * z Thread.MIN_PRIORITY. Wypisz dla obu isDaemon() i getPriority(),
         * pokazując że te dwie właściwości są od siebie niezależne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_BoundedDaemonDemoEndsQuickly {
        /*
         * 🧪 Zadanie 10:
         * Utwórz wątek demoniczny z pętlą while(true) wypisującą kolejne numery
         * iteracji co 80 ms. Zmierz czas trwania main (System.currentTimeMillis)
         * od startu do zakończenia, kończąc main po 400 ms – wypisz zmierzony
         * czas i potwierdź, że jest bliski 400 ms, a nie "nieskończony".
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_CacheEvictionDaemonSimulation {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj Map<String, Long> jako prosty cache (klucz -> czas wygaśnięcia
         * w ms). Uruchom wątek demoniczny usuwający co 100 ms wpisy, których czas
         * wygaśnięcia minął. Dodaj kilka wpisów z różnym TTL z main, odczekaj 500 ms
         * i wypisz zawartość cache – potwierdź że przeterminowane wpisy zniknęły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_CustomDaemonThreadFactoryForExecutor {
        /*
         * 🧪 Zadanie 12:
         * Napisz własny ThreadFactory tworzący wątki z setDaemon(true) i
         * przekaż go do Executors.newFixedThreadPool(2, factory) (ExecutorService
         * z Lekcji 21). Submituj kilka krótkich zadań, NIE wywołuj shutdown(),
         * a mimo to zakończ main szybko po odczekaniu na wyniki – wypisz, że
         * JVM i tak może się zakończyć, bo wątki puli są demoniczne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_MonitoringDaemonWithStats {
        /*
         * 🧪 Zadanie 13:
         * Utwórz wątek demoniczny zbierający "metryki" (np. losową wartość co
         * 100 ms do listy synchronizedList) przez czas trwania main = 450 ms.
         * Po zakończeniu wypisz zebrane próbki i ich liczbę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_UncaughtExceptionInDaemonDoesNotKillJvm {
        /*
         * 🧪 Zadanie 14:
         * Utwórz wątek demoniczny, który po 100 ms rzuca RuntimeException
         * (nieprzechwyconą). Ustaw setUncaughtExceptionHandler wypisujący
         * komunikat o błędzie. Main kontynuuje swoją pracę (sleep 300 ms)
         * i kończy się normalnie – wypisz, że wyjątek w daemonie nie zatrzymał
         * reszty programu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_NestedUserAndDaemonThreads {
        /*
         * 🧪 Zadanie 15:
         * Main uruchamia wątek użytkownika "Worker" (Thread.sleep(400)), który
         * z kolei uruchamia wątek demoniczny "Helper" z pętlą while(true)
         * i sleep(50). Main czeka join() TYLKO na Worker. Wypisz, że program
         * kończy się zaraz po zakończeniu Workera, mimo że Helper "chciał"
         * działać w nieskończoność.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_DaemonThreadFactoryVariationsCheck {
        /*
         * 🧪 Zadanie 16:
         * Napisz metodę newDaemonThread(Runnable r, String name) tworzącą wątek
         * z setDaemon(true) PRZED start(). Użyj jej do utworzenia 3 wątków
         * demonicznych o różnych nazwach, wystartuj je, wypisz isDaemon() każdego
         * i potwierdź poprawność fabryki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TickCounterMatchesExpectedInterval {
        /*
         * 🧪 Zadanie 17:
         * Uruchom wątek demoniczny z tickiem co 60 ms (AtomicInteger). Main czeka
         * dokładnie 630 ms, po czym wypisuje wartość licznika i porównuje ją
         * z oczekiwaną (630/60 ≈ 10), akceptując rozsądny margines błędu (+/-2).
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CriticalWorkShouldNotBeDaemon {
        /*
         * 🧪 Zadanie 18:
         * Zaimplementuj dwa warianty tego samego "krytycznego" zadania (zapis
         * 5 komunikatów do listy ze sleep(100) między każdym): raz jako wątek
         * UŻYTKOWNIKA z join() w main, raz jako wątek DEMONICZNY BEZ join()
         * (main kończy się po 150 ms). Wypisz zawartość obu list na końcu
         * i pokaż, że wersja demoniczna traci część "krytycznej" pracy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ScheduledDaemonCleanupTask {
        /*
         * 🧪 Zadanie 19:
         * Użyj ScheduledExecutorService (Lekcja 23) z własnym ThreadFactory
         * ustawiającym wątki jako demoniczne. Zaplanuj scheduleAtFixedRate co
         * 100 ms wypisujące komunikat "sprzatanie #N". Po 350 ms zakończ main
         * BEZ wywołania shutdown() na executorze – wypisz, że program mimo to
         * kończy się od razu (wątki demoniczne).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ThreeBackgroundMonitorsCutOffCleanly {
        /*
         * 🧪 Zadanie 20:
         * Używając metody z Zadania 16, uruchom 3 wątki demoniczne monitorujące
         * (każdy wypisujący co inny interwał: 50, 100, 150 ms). Main odczekuje
         * 400 ms i kończy się – wypisz, że wszystkie 3 zostały "ucięte" razem
         * z zakończeniem main, bez potrzeby ich jawnego zatrzymywania.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_CacheWithBackgroundEvictionDaemon {
        /*
         * 🧪 Zadanie 21:
         * Zaprojektuj prostą klasę SimpleCache<K,V> z put(key, value, ttlMs) i
         * get(key), przechowującą wpisy z czasem wygaśnięcia, oraz WEWNĘTRZNYM
         * wątkiem demonicznym uruchamianym w konstruktorze, usuwającym
         * przeterminowane wpisy co 50 ms. Przetestuj: dodaj kilka wpisów
         * z różnym TTL, odczekaj, wypisz stan cache przed zakończeniem main.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_LostDataRiskDemo {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj "bufor logów" (StringBuilder) opróżniany co 200 ms przez
         * wątek demoniczny (kopiujący i czyszczący bufor). Z main dopisz kilka
         * wpisów do bufora, a NASTĘPNIE natychmiast (bez czekania na kolejny
         * cykl flush) zakończ main. Wypisz, ile wpisów zostało bezpiecznie
         * "zapisanych" przez daemon, a ile mogło zostać utracone – i wyjaśnij
         * w komentarzu, dlaczego to ryzyko dyskwalifikuje daemony do zadań krytycznych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_DaemonExecutorWithoutShutdown {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj ExecutorService z własnym ThreadFactory (daemon=true), submituj
         * 4 zadania (każde ze sleep(100)). Zmierz czas do zakończenia main w
         * dwóch wariantach: (a) z wywołanym shutdown()+awaitTermination()
         * (dobra praktyka), (b) BEZ shutdown() (poleganie tylko na tym, że
         * wątki są demoniczne). Porównaj i wypisz oba czasy oraz zalecaną praktykę.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HeartbeatMonitoringSystem {
        /*
         * 🧪 Zadanie 24:
         * Zaimplementuj 3 "komponenty", każdy z własnym AtomicLong
         * lastHeartbeatMillis aktualizowanym przez osobny wątek demoniczny co
         * inny interwał (80, 120, 200 ms). Osobny demoniczny "Supervisor" co
         * 150 ms sprawdza wszystkie heartbeaty i wypisuje alert, jeśli któryś
         * nie odświeżył się od ponad 250 ms. Main działa 700 ms i kończy się,
         * wypisując finalny raport stanu wszystkich komponentów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_UserThreadsDetermineEndTimeNotDaemons {
        /*
         * 🧪 Zadanie 25:
         * Uruchom 5 wątków UŻYTKOWNIKA wykonujących zadania o różnym czasie
         * trwania (100-500 ms), z join() na wszystkich, ORAZ 5 wątków
         * DEMONICZNYCH z pętlami while(true) (bez join). Zmierz łączny czas
         * trwania main i wypisz, że jest on zdeterminowany przez NAJWOLNIEJSZY
         * wątek użytkownika, a nie przez "nieskończone" demony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ShutdownHookVsDaemonFinally {
        /*
         * 🧪 Zadanie 26:
         * Zarejestruj Runtime.getRuntime().addShutdownHook(new Thread(...))
         * wypisujący "shutdown hook: sprzatanie wykonane" – porównaj to
         * z wątkiem demonicznym mającym blok finally z podobnym komunikatem
         * wewnątrz pętli while(true) (który MOŻE nie zdążyć się wykonać przy
         * ucięciu przez JVM). Zakończ main po krótkim czasie i zaobserwuj,
         * który komunikat pojawia się niezawodnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_LoggingServiceDaemonDrain {
        /*
         * 🧪 Zadanie 27:
         * Zbuduj BlockingQueue<String> jako kolejkę logów oraz demoniczny wątek
         * "Logger" w pętli while(true) pobierający (poll z timeoutem) i
         * wypisujący wiadomości. Z main (wątek użytkownika) wyślij 10 wiadomości
         * w krótkich odstępach, a następnie zakończ main PRAWIE od razu (bez
         * czekania na opróżnienie kolejki) – wypisz, ile wiadomości zdążyło
         * zostać zalogowanych, a ile zostało w kolejce nieprzetworzonych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DaemonHealthCheckPoolThreeServices {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj 3 "serwisy" jako AtomicBoolean (status up/down, losowo
         * przełączany co jakiś czas przez osobne demoniczne wątki-symulatory).
         * Demoniczny wątek "HealthChecker" co 100 ms odczytuje statusy wszystkich
         * 3 i wypisuje raport. Main działa 800 ms i kończy się – wypisz finalny,
         * ostatni znany stan wszystkich serwisów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CompareManualVsFactoryDaemonSetup {
        /*
         * 🧪 Zadanie 29:
         * Zmierz czas zakończenia programu w dwóch wariantach z takim samym
         * zadaniem w tle (pętla while(true) ze sleep(50)): (a) ręcznie utworzony
         * Thread z setDaemon(true) przed start(), (b) wątek utworzony przez
         * ExecutorService z własnym ThreadFactory ustawiającym daemon=true.
         * Wypisz oba zmierzone czasy zakończenia i potwierdź, że oba podejścia
         * pozwalają programowi zakończyć się szybko.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BackgroundStatsCollectorCaseStudy {
        /*
         * 🧪 Zadanie 30:
         * Zaimplementuj klasę BackgroundStatsCollector uruchamiającą w
         * konstruktorze wątek demoniczny próbkujący co 100 ms losową wartość
         * (Random) i trzymający tylko ostatnie 5 próbek (np. w
         * ArrayDeque<Integer> z limitem rozmiaru). W main użyj kolektora przez
         * 600 ms, wypisz aktualne 5 próbek na końcu i skomentuj (w println),
         * dlaczego to typowy, BEZPIECZNY przypadek użycia daemon thread
         * (dane nie są krytyczne, utrata "ostatniego tiku" przy zamknięciu JVM
         * jest akceptowalna).
         */
        public static void main(String[] args) { }
    }
}
