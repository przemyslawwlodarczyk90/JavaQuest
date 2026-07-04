package com.example.javaquest._05_multithreading.Lesson12_Monitor;

public class _Lesson12_Monitor {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 MONITOR - CO TO JEST?
         * ============================================================
         * MONITOR to wbudowany w Javę mechanizm synchronizacji - KAŻDY
         * obiekt w Javie (dosłownie każdy - instancja dowolnej klasy)
         * ma "wbudowaną", niewidoczną blokadę zwaną MONITOREM (czasem:
         * "intrinsic lock" / "monitor lock").
         *
         * Monitor pozwala zagwarantować, że TYLKO JEDEN wątek na raz
         * może wykonywać kod będący wewnątrz sekcji chronionej tym
         * monitorem - to właśnie mechanizm stojący za słowem kluczowym
         * `synchronized`.
         *
         * 🔹 RELACJA monitor <-> synchronized:
         * `synchronized (obiekt) { ... }` to dokładnie: "wejdź do monitora
         * obiektu `obiekt`, wykonaj kod, opuść monitor". Podobnie
         * synchronized method instancyjna używa monitora `this`, a
         * synchronized method statyczna - monitora `NazwaKlasy.class`
         * (patrz Lesson11_Synchronized).
         */

        /*
         * ============================================================
         * 🔍 WEJŚCIE DO SEKCJI KRYTYCZNEJ I OPUSZCZENIE MONITORA
         * ============================================================
         * Gdy wątek napotyka `synchronized (obiekt) { ... }`:
         *
         *   1. Próbuje ZDOBYĆ monitor obiektu `obiekt`.
         *      - Jeśli monitor jest WOLNY (nikt go nie trzyma) - wątek
         *        od razu go zdobywa i wchodzi do środka (RUNNABLE).
         *      - Jeśli monitor jest ZAJĘTY przez inny wątek - nasz wątek
         *        czeka w stanie BLOCKED (patrz Lesson06_ThreadLifecycleAndStates).
         *   2. Wykonuje kod wewnątrz bloku (sekcja krytyczna).
         *   3. Po wyjściu z bloku (normalnie LUB przez wyjątek) - AUTOMATYCZNIE
         *      ZWALNIA monitor, co pozwala kolejnemu oczekującemu wątkowi
         *      go zdobyć.
         *
         * Monitor jest REENTRANT (wielokrotnego wejścia) - ten sam wątek
         * może wejść do monitora, który już trzyma (np. wywołując zagnieżdżoną
         * synchronized metodę na tym samym obiekcie), bez samo-zablokowania.
         */

        /*
         * ============================================================
         * 🔹 DWA WĄTKI, DWA RÓŻNE MONITORY - NIE BLOKUJĄ SIĘ
         * ============================================================
         * Jeśli dwa wątki synchronizują się na RÓŻNYCH obiektach, to
         * używają RÓŻNYCH monitorów - mogą wykonywać swoje sekcje krytyczne
         * RÓWNOLEGLE, bez czekania na siebie nawzajem.
         */

        System.out.println("=== Rozne obiekty = rozne monitory = BRAK blokowania ===");
        Object monitorX = new Object();
        Object monitorY = new Object();

        long startRozne = System.nanoTime();
        Thread watekX = new Thread(() -> pracaWMonitorze(monitorX, "Watek-X", 300));
        Thread watekY = new Thread(() -> pracaWMonitorze(monitorY, "Watek-Y", 300));
        watekX.start();
        watekY.start();
        watekX.join(5_000);
        watekY.join(5_000);
        long czasRozne = (System.nanoTime() - startRozne) / 1_000_000;
        System.out.println("Czas calkowity (rozne monitory, powinno byc ok. ~300ms): " + czasRozne + " ms");

        /*
         * ============================================================
         * 🔹 DWA WĄTKI, TEN SAM MONITOR - BLOKUJĄ SIĘ WZAJEMNIE
         * ============================================================
         * Jeśli oba wątki synchronizują się na TYM SAMYM obiekcie, drugi
         * wątek musi CZEKAĆ (BLOCKED), aż pierwszy opuści monitor.
         * Sekcje krytyczne wykonują się SEKWENCYJNIE (jedna po drugiej),
         * więc całkowity czas jest w przybliżeniu SUMĄ czasów obu zadań.
         */

        System.out.println("\n=== Ten sam obiekt = ten sam monitor = BLOKOWANIE ===");
        Object wspolnyMonitor = new Object();

        long startTenSam = System.nanoTime();
        Thread watekP = new Thread(() -> pracaWMonitorze(wspolnyMonitor, "Watek-P", 300));
        Thread watekQ = new Thread(() -> pracaWMonitorze(wspolnyMonitor, "Watek-Q", 300));
        watekP.start();
        Thread.sleep(20); // upewniamy sie, ze P zdazyl wejsc do monitora jako pierwszy
        watekQ.start();
        watekP.join(5_000);
        watekQ.join(5_000);
        long czasTenSam = (System.nanoTime() - startTenSam) / 1_000_000;
        System.out.println("Czas calkowity (ten sam monitor, powinno byc ok. ~600ms): " + czasTenSam + " ms");

        System.out.println("\nPorownanie: rozne monitory ~" + czasRozne +
                "ms vs ten sam monitor ~" + czasTenSam + "ms");
        System.out.println("(dokladne wartosci zaleza od maszyny, ale relacja powinna byc widoczna:");
        System.out.println(" rozne monitory ~ czas jednego zadania, ten sam monitor ~ suma obu zadan)");

        /*
         * ============================================================
         * ❌ CZEGO NIE ROBIĆ
         * ============================================================
         * - Nie zakładaj, że `synchronized` na RÓŻNYCH obiektach cokolwiek
         *   chroni względem siebie nawzajem - to zupełnie NIEZALEŻNE
         *   monitory, wątki NIE będą się wzajemnie blokować.
         * - Uważaj na synchronizowanie się na obiektach "przypadkowych"
         *   (np. String literały, boxed Integery z cache'a) - mogą być
         *   nieświadomie współdzielone z innym, niepowiązanym kodem.
         *
         * ✅ DOBRA PRAKTYKA
         * - Używaj DEDYKOWANEGO, prywatnego obiektu jako blokady
         *   (`private final Object lock = new Object();`), żeby mieć
         *   pełną kontrolę nad tym, kto się na niej synchronizuje.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Monitor = wbudowana blokada KAŻDEGO obiektu w Javie.
         * - `synchronized (obiekt)` = zdobądź monitor obiektu, wykonaj
         *   sekcję krytyczną, automatycznie zwolnij monitor.
         * - Monitor jest reentrant - ten sam wątek może wejść ponownie
         *   do monitora, który już trzyma.
         * - Rózne obiekty = rózne monitory = wątki NIE blokują się
         *   wzajemnie (mogą pracować równolegle).
         * - Ten sam obiekt = ten sam monitor = wątki blokują się
         *   wzajemnie (sekcje krytyczne wykonują się sekwencyjnie).
         */
    }

    /**
     * Symuluje prace wewnatrz sekcji krytycznej chronionej monitorem
     * podanego obiektu.
     */
    private static void pracaWMonitorze(Object monitor, String nazwaWatku, int millis) {
        synchronized (monitor) {
            System.out.println(nazwaWatku + ": wszedlem do monitora, pracuje...");
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(nazwaWatku + ": konczy prace, opuszczam monitor.");
        }
    }
}
