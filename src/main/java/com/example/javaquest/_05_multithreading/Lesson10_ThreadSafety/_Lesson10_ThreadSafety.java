package com.example.javaquest._05_multithreading.Lesson10_ThreadSafety;

public class _Lesson10_ThreadSafety {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 CZYM JEST THREAD-SAFETY (BEZPIECZEŃSTWO WĄTKOWE)?
         * ============================================================
         * Kod jest THREAD-SAFE (bezpieczny wątkowo), jeśli zachowuje się
         * POPRAWNIE (daje zgodne z oczekiwaniem, spójne wyniki) niezależnie
         * od tego, ile wątków go jednocześnie wykonuje i w jakiej
         * kolejności/przeplocie (interleaving) te wątki zostaną
         * zaplanowane przez scheduler.
         *
         * Innymi słowy: NIE MA znaczenia "kto pierwszy, kto drugi" ani
         * "ile wątków na raz" - wynik zawsze jest poprawny i deterministyczny
         * (z punktu widzenia logiki biznesowej).
         */

        /*
         * ============================================================
         * 🔍 STAN WSPÓŁDZIELONY (SHARED MUTABLE STATE) - ŹRÓDŁO PROBLEMÓW
         * ============================================================
         * Problemy z bezpieczeństwem wątkowym (race condition, widoczność
         * zmian - patrz Lesson07/Lesson08) pojawiają się PRAWIE ZAWSZE
         * wtedy, gdy mamy:
         *
         *   1) STAN (dane) - jakieś pole/zmienną,
         *   2) WSPÓŁDZIELONY między wątkami (więcej niż jeden wątek ma
         *      do niego dostęp),
         *   3) MUTOWALNY (można go zmieniać PO utworzeniu).
         *
         * Jeśli brakuje CHOĆ JEDNEGO z tych trzech elementów - problemu
         * nie ma:
         *   - stan LOKALNY (zmienne lokalne metody) - każdy wątek ma
         *     WŁASNĄ kopię na swoim stosie, więc nie jest współdzielony,
         *   - stan NIEMUTOWALNY - nawet jeśli współdzielony, nie da się
         *     go zepsuć, bo nie da się go w ogóle zmienić po utworzeniu.
         *
         * Przykład problematycznej klasy - MUTOWALNY licznik współdzielony
         * między wątkami, modyfikowany bez synchronizacji:
         */

        System.out.println("=== Mutowalny stan wspoldzielony - RYZYKOWNE ===");

        MutableCounter mutableCounter = new MutableCounter();
        int threadCount = 4;
        int incrementsPerThread = 200_000;
        int expected = threadCount * incrementsPerThread;

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    mutableCounter.increment(); // brak synchronizacji!
                }
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join(10_000); // bezpiecznik czasowy

        System.out.println("Oczekiwany wynik : " + expected);
        System.out.println("Rzeczywisty wynik: " + mutableCounter.getValue());
        System.out.println("(patrz Lesson07_RaceCondition/Lesson09_Atomicity po pelne wyjasnienie)");

        /*
         * ============================================================
         * ✅ NAJPROSTSZE ROZWIĄZANIE: NIEMUTOWALNOŚĆ (IMMUTABILITY)
         * ============================================================
         * Obiekt NIEMUTOWALNY (immutable) to taki, którego stanu NIE DA
         * SIĘ zmienić po utworzeniu - każda "zmiana" tworzy NOWY obiekt.
         *
         * Jeśli obiekt jest niemutowalny, to jest AUTOMATYCZNIE thread-safe
         * - nie ma znaczenia ile wątków go czyta jednocześnie, bo żaden
         * wątek nie może zmienić jego stanu (więc nie ma czego "wyścigać").
         *
         * Najłatwiejszy sposób w nowoczesnej Javie: `record` - pola są
         * `final` z automatu, brak setterów, każde "modyfikowanie" zwraca
         * NOWĄ instancję.
         */

        System.out.println("\n=== Niemutowalny rekord - AUTOMATYCZNIE thread-safe ===");

        ImmutablePoint punkt = new ImmutablePoint(1, 2);
        Runnable czytelnik = () -> {
            // Wiele watkow moze CZYTAC ten sam obiekt bezpiecznie -
            // nie da się go zmienić, więc nie ma wyścigu.
            int suma = punkt.x() + punkt.y();
            if (suma != 3) {
                System.out.println("❌ To nie powinno sie zdarzyc!");
            }
        };

        Thread[] czytelnicy = new Thread[4];
        for (int i = 0; i < czytelnicy.length; i++) {
            czytelnicy[i] = new Thread(czytelnik);
        }
        for (Thread t : czytelnicy) t.start();
        for (Thread t : czytelnicy) t.join(5_000);

        // "Modyfikacja" niemutowalnego punktu = tworzymy NOWY obiekt
        ImmutablePoint przesuniety = punkt.przesun(10, 10);
        System.out.println("Oryginalny punkt (bez zmian): " + punkt);
        System.out.println("Nowy, przesuniety punkt: " + przesuniety);
        System.out.println("✅ Kazdy watek widzi tylko kompletny, niezmienny stan - brak wyscigu.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Thread-safe = kod daje poprawne wyniki niezaleznie od liczby
         *   watkow i kolejnosci ich wykonania.
         * - Zrodlo problemow: STAN + WSPOLDZIELONY + MUTOWALNY (brak
         *   ktoregokolwiek z tych trzech elementow eliminuje problem).
         * - Stan lokalny (zmienne w metodzie) nie jest wspoldzielony -
         *   kazdy watek ma wlasna kopie na swoim stosie.
         * - Niemutowalnosc (immutability) = najprostsze rozwiazanie -
         *   obiekt, ktorego nie da sie zmienic, jest automatycznie
         *   thread-safe (np. `record` z polami final).
         * - Mutowalny stan wspoldzielony WYMAGA jawnej synchronizacji
         *   (synchronized, Lock, Atomic*) - patrz kolejne lekcje.
         */
    }

    /**
     * Mutowalny, CELOWO niesynchronizowany licznik - podatny na race condition.
     */
    static class MutableCounter {
        private int value = 0;

        void increment() {
            value++; // nieatomowe, brak synchronizacji - patrz Lesson09_Atomicity
        }

        int getValue() {
            return value;
        }
    }

    /**
     * Niemutowalny punkt 2D - klasyczny rekord: pola final, brak setterow,
     * "modyfikacja" zwraca NOWY obiekt. Dzieki temu jest thread-safe
     * bez zadnej dodatkowej synchronizacji.
     */
    record ImmutablePoint(int x, int y) {
        ImmutablePoint przesun(int dx, int dy) {
            return new ImmutablePoint(x + dx, y + dy); // nowy obiekt, oryginal bez zmian
        }
    }
}
