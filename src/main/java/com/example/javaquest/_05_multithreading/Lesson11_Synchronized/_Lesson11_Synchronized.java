package com.example.javaquest._05_multithreading.Lesson11_Synchronized;

public class _Lesson11_Synchronized {

    // Wspoldzielony obiekt-blokada do demonstracji synchronized block
    private static final Object blokadaStatyczna = new Object();
    private static int licznikStatyczny = 0;

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 SŁOWO KLUCZOWE synchronized
         * ============================================================
         * `synchronized` to wbudowany w Javę mechanizm wzajemnego wykluczania
         * (mutual exclusion) - pozwala oznaczyć fragment kodu (metodę albo
         * blok) jako SEKCJĘ KRYTYCZNĄ, do której w danym momencie może
         * wejść TYLKO JEDEN wątek. Inne wątki próbujące wejść czekają
         * (stan BLOCKED - patrz Lesson06_ThreadLifecycleAndStates), aż
         * blokada zostanie zwolniona.
         *
         * Mechanizm ten opiera się na MONITORZE - patrz Lesson12_Monitor.
         */

        /*
         * ============================================================
         * 🔹 SYNCHRONIZED METHOD - INSTANCYJNA vs STATYCZNA
         * ============================================================
         * 1) Synchronized METODA INSTANCYJNA:
         *
         *      public synchronized void metoda() { ... }
         *
         *    Blokada zakładana jest na `this` - czyli na KONKRETNYM
         *    OBIEKCIE, na którym metoda jest wywoływana. Dwa wątki
         *    wywołujące tę metodę na DWÓCH RÓŻNYCH obiektach NIE blokują
         *    się nawzajem (różne monitory!). Blokują się tylko wtedy,
         *    gdy wywołują ją na TYM SAMYM obiekcie.
         *
         * 2) Synchronized METODA STATYCZNA:
         *
         *      public static synchronized void metoda() { ... }
         *
         *    Blokada zakładana jest na obiekcie klasy - `NazwaKlasy.class`
         *    (tzw. Class object). Jest TYLKO JEDEN taki obiekt na całą
         *    JVM (per classloader), więc wszystkie wątki wywołujące
         *    dowolną synchronized metodę STATYCZNĄ tej klasy rywalizują
         *    o TĘ SAMĄ, WSPÓLNĄ blokadę - niezależnie od tego, na ilu
         *    instancjach "operują".
         *
         * ⚠️ WAŻNE: blokada z metody instancyjnej (this) i blokada z
         * metody statycznej (NazwaKlasy.class) to DWIE RÓŻNE blokady -
         * nie wykluczają się nawzajem!
         */

        System.out.println("=== synchronized method: instancyjna (this) ===");
        LicznikInstancyjny licznikA = new LicznikInstancyjny();
        LicznikInstancyjny licznikB = new LicznikInstancyjny();

        long startRozne = System.nanoTime();
        Thread t1 = new Thread(() -> incrementuj(licznikA::increment, 500_000));
        Thread t2 = new Thread(() -> incrementuj(licznikB::increment, 500_000));
        // t1 i t2 operuja na ROZNYCH obiektach (licznikA vs licznikB) -
        // ROZNE monitory (this), wiec NIE blokuja sie wzajemnie.
        t1.start();
        t2.start();
        t1.join(10_000);
        t2.join(10_000);
        long czasRozne = (System.nanoTime() - startRozne) / 1_000_000;
        System.out.println("Dwa rozne obiekty rownolegle, czas: " + czasRozne + " ms");
        System.out.println("licznikA=" + licznikA.getValue() + ", licznikB=" + licznikB.getValue());

        System.out.println("\n=== synchronized method: statyczna (NazwaKlasy.class) ===");
        licznikStatyczny = 0;
        Thread s1 = new Thread(() -> incrementujStatycznie(500_000));
        Thread s2 = new Thread(() -> incrementujStatycznie(500_000));
        // s1 i s2 wywoluja synchronized STATYCZNA metode - obie rywalizuja
        // o TA SAMA blokade (klasa .class), wiec wykonuja sie po kolei
        // w sekcji krytycznej (mimo ze to "rozne watki").
        s1.start();
        s2.start();
        s1.join(10_000);
        s2.join(10_000);
        System.out.println("Wynik synchronized static: " + licznikStatyczny +
                " (oczekiwane: " + (2 * 500_000) + ")");

        /*
         * ============================================================
         * 🔹 SYNCHRONIZED BLOCK - blokada na wybranym obiekcie
         * ============================================================
         * Zamiast synchronizować CAŁĄ metodę, można zsynchronizować tylko
         * WYBRANY FRAGMENT kodu, blokując się na DOWOLNIE wybranym obiekcie:
         *
         *      synchronized (jakisObiekt) { ... }
         *
         * KORZYŚĆ: węższy zakres blokady = mniejsza rywalizacja (contention),
         * bo poza blokiem synchronized wątki mogą pracować RÓWNOLEGLE
         * (patrz też Lesson13_CriticalSection o minimalizowaniu sekcji
         * krytycznej).
         */

        System.out.println("\n=== synchronized block - blokada na wybranym obiekcie ===");
        licznikStatyczny = 0;
        Thread b1 = new Thread(() -> {
            for (int i = 0; i < 500_000; i++) {
                synchronized (blokadaStatyczna) {
                    licznikStatyczny++;
                }
            }
        });
        Thread b2 = new Thread(() -> {
            for (int i = 0; i < 500_000; i++) {
                synchronized (blokadaStatyczna) {
                    licznikStatyczny++;
                }
            }
        });
        b1.start();
        b2.start();
        b1.join(10_000);
        b2.join(10_000);
        System.out.println("Wynik synchronized block: " + licznikStatyczny +
                " (oczekiwane: " + (2 * 500_000) + ")");

        /*
         * ============================================================
         * ✅ NAPRAWA RACE CONDITION Z Lesson07 PRZY UŻYCIU synchronized
         * ============================================================
         * Przypomnienie z Lesson07_RaceCondition: wiele wątków inkrementuje
         * współdzielony `int counter` bez synchronizacji - wynik jest
         * BŁĘDNY i NIEDETERMINISTYCZNY. Poniżej ta sama sytuacja, ale
         * NAPRAWIONA przy pomocy synchronized - wynik jest teraz zawsze
         * POPRAWNY (deterministyczny).
         */

        System.out.println("\n=== NAPRAWIONY race condition - synchronized ===");

        int threadCount = 4;
        int incrementsPerThread = 500_000;
        int expected = threadCount * incrementsPerThread;

        SafeCounter safeCounter = new SafeCounter();
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    safeCounter.increment(); // teraz synchronized - bezpieczne
                }
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join(10_000);

        System.out.println("Oczekiwany wynik : " + expected);
        System.out.println("Rzeczywisty wynik: " + safeCounter.getValue());
        if (safeCounter.getValue() == expected) {
            System.out.println("✅ Wynik POPRAWNY i DETERMINISTYCZNY - dzieki synchronized!");
        } else {
            System.out.println("❌ To nie powinno sie zdarzyc przy poprawnym uzyciu synchronized.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - synchronized method instancyjna - blokada na `this` (rozne
         *   obiekty = rozne blokady, nie blokuja sie nawzajem)
         * - synchronized method statyczna - blokada na `NazwaKlasy.class`
         *   (jedna, wspolna blokada dla calej klasy)
         * - synchronized block - blokada na DOWOLNIE wybranym obiekcie,
         *   pozwala zawezic sekcje krytyczna (mniejsza rywalizacja)
         * - synchronized naprawia race condition: operacje w sekcji
         *   krytycznej wykonuja sie sekwencyjnie, wiec wynik jest
         *   POPRAWNY i DETERMINISTYCZNY
         */
    }

    private static void incrementuj(Runnable increment, int razy) {
        for (int i = 0; i < razy; i++) {
            increment.run();
        }
    }

    private static synchronized void incrementujStatycznie(int razy) {
        // CALA metoda jest synchronized - w praktyce serializuje wywolania
        // z roznych watkow (uproszczony przyklad, w realu wolelibysmy
        // synchronized block wewnatrz petli - patrz Lesson13_CriticalSection)
        for (int i = 0; i < razy; i++) {
            licznikStatyczny++;
        }
    }

    /**
     * Licznik z synchronized METODA INSTANCYJNA - blokada na `this`.
     */
    static class LicznikInstancyjny {
        private int value = 0;

        synchronized void increment() { // blokada na `this`
            value++;
        }

        synchronized int getValue() {
            return value;
        }
    }

    /**
     * Bezpieczny licznik - naprawiona wersja Counter z Lesson07_RaceCondition.
     */
    static class SafeCounter {
        private int value = 0;

        synchronized void increment() { // sekcja krytyczna - jeden watek na raz
            value++;
        }

        synchronized int getValue() {
            return value;
        }
    }
}
