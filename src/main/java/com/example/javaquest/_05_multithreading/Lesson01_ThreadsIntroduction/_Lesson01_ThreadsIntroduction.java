package com.example.javaquest._05_multithreading.Lesson01_ThreadsIntroduction;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class _Lesson01_ThreadsIntroduction {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 PROCES vs WĄTEK
         * ============================================================
         * PROCES (process) – uruchomiony program, ma WŁASNĄ, odizolowaną
         * przestrzeń pamięci (nie dzieli jej z innymi procesami).
         * Przykład: uruchomiona przeglądarka, uruchomiona JVM z Twoim
         * programem Java – to osobne procesy w systemie operacyjnym.
         *
         * WĄTEK (thread) – "lekki" ciąg wykonania WEWNĄTRZ procesu.
         * Wątki tego samego procesu DZIELĄ tę samą pamięć (np. te same
         * pola statyczne, ten sam sterty obiektów) – to duża zaleta
         * (łatwa komunikacja) i duże ryzyko (wyścigi, niespójne dane).
         *
         * Jeden proces może mieć wiele wątków – to WIELOWĄTKOWOŚĆ
         * (multithreading). Każdy program w Javie ma co najmniej jeden
         * wątek: wątek "main".
         *
         * 🔹 ANALOGIA
         * Proces = budynek biurowy (własny adres, własne wyposażenie).
         * Wątek = pracownik w tym budynku – wielu pracowników (wątków)
         * korzysta z tych samych zasobów budynku (pamięci procesu).
         */

        System.out.println("=== PROCES vs WĄTEK ===");
        System.out.println("Nazwa biezacego watku: " + Thread.currentThread().getName());
        // Wypisze: Nazwa biezacego watku: main

        /*
         * ============================================================
         * 🔹 JVM MA JUŻ WIELE WĄTKÓW DOMYŚLNIE
         * ============================================================
         * Nawet najprostszy program "Hello World" nie działa w JEDNYM
         * wątku – JVM od razu uruchamia wątki pomocnicze, np.:
         * - main               – nasz kod
         * - Garbage Collector  – sprząta nieużywane obiekty
         * - Finalizer          – (historycznie) obsługa finalize()
         * - Signal Dispatcher  – obsługa sygnałów systemowych
         * - Reference Handler  – obsługa referencji (WeakReference itp.)
         *
         * Możemy to zobaczyć np. przez ThreadMXBean (API do introspekcji
         * wątków JVM).
         */

        System.out.println("\n=== WĄTKI DZIAŁAJĄCE W JVM ===");
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();
        for (long id : threadIds) {
            ThreadInfo info = threadMXBean.getThreadInfo(id);
            if (info != null) {
                System.out.println(" - " + info.getThreadName());
            }
        }
        // Przykładowy wynik (lista zależy od JVM/wersji, ZAWSZE >1 wątek):
        // - main
        // - Reference Handler
        // - Finalizer
        // - Signal Dispatcher
        // - Common-Cleaner
        // ... (dokładna lista jest niedeterministyczna i zależy od JVM)

        /*
         * ============================================================
         * 🔍 WSPÓŁBIEŻNOŚĆ (CONCURRENCY) vs RÓWNOLEGŁOŚĆ (PARALLELISM)
         * ============================================================
         * To DWA RÓŻNE pojęcia, często mylone:
         *
         * WSPÓŁBIEŻNOŚĆ (concurrency):
         * - Wiele zadań JEST W TRAKCIE realizacji w tym samym okresie
         *   czasu, ale niekoniecznie wykonują się fizycznie w tej samej
         *   chwili.
         * - Na JEDNYM rdzeniu procesora osiągamy współbieżność przez
         *   szybkie PRZEŁĄCZANIE (time-slicing) między wątkami – z
         *   naszej perspektywy "dzieje się to jednocześnie", ale
         *   naprawdę procesor robi mikro-kawałek zadania A, potem
         *   mikro-kawałek zadania B, itd.
         *
         * RÓWNOLEGŁOŚĆ (parallelism):
         * - Wiele zadań wykonuje się DOSŁOWNIE w tym samym momencie,
         *   fizycznie na różnych rdzeniach procesora.
         * - Wymaga sprzętu wielordzeniowego (co dziś jest normą).
         *
         * 📌 RÓŻNICA W SKRÓCIE:
         * - Współbieżność = STRUKTURA programu (radzenie sobie z wieloma
         *   zadaniami na raz – niekoniecznie w tym samym momencie)
         * - Równoległość = WYKONANIE fizyczne (dosłownie w tym samym
         *   momencie, na różnych rdzeniach)
         *
         * Można mieć współbieżność BEZ równoległości (1 rdzeń, wiele
         * wątków przełączanych w czasie) – a nawet równoległość bez
         * "świadomej" współbieżności programisty (np. dwa niezależne
         * procesy jednocześnie na różnych rdzeniach).
         *
         * Liczbę dostępnych rdzeni (a więc realny potencjał
         * równoległości) sprawdzimy przez Runtime:
         */

        int dostepneRdzenie = Runtime.getRuntime().availableProcessors();
        System.out.println("\nDostepne rdzenie (availableProcessors): " + dostepneRdzenie);

        /*
         * ============================================================
         * 🔍 PRZYKŁAD OBRAZUJĄCY RÓŻNICĘ
         * ============================================================
         * Wyobraź sobie kucharza gotującego 2 dania:
         *
         * WSPÓŁBIEŻNOŚĆ (1 kucharz, przełącza się między daniami):
         * - Wstawia wodę na makaron, w międzyczasie kroi warzywa,
         *   wraca do makaronu, miesza sos... JEDEN kucharz (1 rdzeń),
         *   ale oba dania "postępują" w tym samym okresie czasu.
         *
         * RÓWNOLEGŁOŚĆ (2 kucharzy, każdy robi jedno danie):
         * - Kucharz A robi makaron, kucharz B w TYM SAMYM MOMENCIE
         *   robi sos. Fizycznie równocześnie (2 rdzenie/2 kucharzy).
         *
         * Poniżej symulacja: 2 "zadania" wykonywane WSPÓŁBIEŻNIE przez
         * osobne wątki. Czy będą też równoległe (na różnych rdzeniach)
         * zależy od JVM/OS/liczby rdzeni – z poziomu Javy nie mamy
         * pełnej kontroli/gwarancji, który wątek trafi na który rdzeń.
         */

        System.out.println("\n=== SYMULACJA: 2 ZADANIA WSPÓŁBIEŻNIE ===");
        Thread makaron = new Thread(() -> gotujDanie("Makaron", 3), "Kucharz-Makaron");
        Thread sos = new Thread(() -> gotujDanie("Sos", 3), "Kucharz-Sos");

        long start = System.currentTimeMillis();
        makaron.start();
        sos.start();
        makaron.join(); // main czeka aż oba dania będą gotowe (patrz Lesson05)
        sos.join();
        long czasCalkowity = System.currentTimeMillis() - start;

        System.out.println("Oba dania gotowe po ok. " + czasCalkowity + " ms");
        // Gdyby gotowanie było SEKWENCYJNE (jeden po drugim), zajęłoby to
        // sumę czasów obu dań. Dzięki wątkom zajmuje to w przybliżeniu
        // czas NAJDŁUŻSZEGO dania (bo gotowały się "naraz").
        // Dokładna wartość ms jest niedeterministyczna - zależy od
        // obciążenia maszyny, ale będzie bliska ~300ms, a nie ~600ms.

        /*
         * ============================================================
         * 🔹 PO CO UŻYWAĆ WIELU WĄTKÓW?
         * ============================================================
         * 1) ZADANIA I/O-BOUND (ograniczone wejściem/wyjściem):
         *    - Program czeka na dysk, sieć, bazę danych, użytkownika.
         *    - Podczas oczekiwania CPU jest "bezczynne" – można w tym
         *      czasie wykonać inne wątki (np. obsłużyć kolejnego
         *      klienta serwera, podczas gdy inny wątek czeka na
         *      odpowiedź z bazy danych).
         *    - Przykład: serwer WWW obsługujący wielu klientów
         *      jednocześnie, pobieranie wielu plików z internetu naraz.
         *
         * 2) ZADANIA CPU-BOUND (ograniczone mocą obliczeniową):
         *    - Program intensywnie liczy (np. przetwarzanie obrazu,
         *      sortowanie ogromnych danych, obliczenia matematyczne).
         *    - Tu wielowątkowość pomaga TYLKO jeśli mamy wiele rdzeni
         *      (prawdziwa równoległość) – dzielimy pracę na kawałki
         *      i liczymy je jednocześnie na różnych rdzeniach.
         *    - Na 1 rdzeniu wielowątkowość zadań CPU-bound NIE przyspiesza
         *      obliczeń (a nawet może je spowolnić przez narzut na
         *      przełączanie kontekstu)!
         *
         * ⚠️ WNIOSEK: dobór strategii zależy od charakteru zadania:
         * - I/O-bound -> wielowątkowość pomaga nawet na 1 rdzeniu
         *   (bo nakładają się okresy oczekiwania)
         * - CPU-bound -> wielowątkowość pomaga głównie przy wielu
         *   rdzeniach (prawdziwa równoległość obliczeń)
         */

        System.out.println("\n=== KONIEC LEKCJI 1 ===");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Proces = odizolowany, uruchomiony program (własna pamięć)
         * - Wątek = ciąg wykonania wewnątrz procesu, wątki DZIELĄ pamięć
         * - Wielowątkowość = wiele wątków w jednym procesie
         * - JVM od startu ma wiele wątków (main + wątki pomocnicze: GC,
         *   Reference Handler, Finalizer, Signal Dispatcher...)
         * - Współbieżność (concurrency) = wiele zadań "w toku" w tym
         *   samym okresie czasu (może być na 1 rdzeniu, przez przełączanie)
         * - Równoległość (parallelism) = wiele zadań DOSŁOWNIE w tym
         *   samym momencie (wymaga wielu rdzeni)
         * - I/O-bound -> zyskuje na wielowątkowości nawet na 1 rdzeniu
         * - CPU-bound -> zyskuje głównie przy realnej równoległości
         *   (wiele rdzeni)
         */
    }

    /**
     * Symuluje "gotowanie dania" – zadanie trwające pewien czas.
     * Używamy Thread.sleep, by symulować pracę bez realnego obciążania CPU.
     */
    private static void gotujDanie(String nazwa, int sekundy) {
        System.out.println(Thread.currentThread().getName() + ": zaczynam przygotowywac " + nazwa);
        try {
            Thread.sleep(sekundy * 100L); // skrócone dla szybkości demo (300ms)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ": " + nazwa + " gotowy!");
    }
}
