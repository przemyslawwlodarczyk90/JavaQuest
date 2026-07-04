package com.example.javaquest._05_multithreading.Lesson07_RaceCondition;

public class _Lesson07_RaceCondition {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 RACE CONDITION – WYŚCIG WĄTKÓW
         * ============================================================
         * Race condition (wyścig wątków) to sytuacja, w której WYNIK programu
         * zależy od nieprzewidywalnej kolejności (szybkości) wykonywania
         * instrukcji przez różne wątki, które operują na tych samych,
         * współdzielonych danych.
         *
         * Klasyczny przykład: kilka wątków inkrementuje tę samą zmienną
         * `int counter` bez żadnej synchronizacji. Intuicyjnie wynik
         * powinien być prostą sumą wszystkich inkrementacji – w praktyce
         * często jest INNY (mniejszy) i RÓŻNY przy każdym uruchomieniu.
         *
         * Powód: `counter++` NIE jest jedną, niepodzielną operacją.
         */

        /*
         * ============================================================
         * 🔍 DLACZEGO count++ NIE JEST ATOMOWE?
         * ============================================================
         * Pozornie jedna linijka kodu:
         *
         *     counter++;
         *
         * w rzeczywistości (na poziomie JVM / procesora) to TRZY oddzielne
         * kroki:
         *
         *     1. ODCZYT   – pobierz aktualną wartość `counter` do rejestru
         *     2. DODANIE  – dodaj 1 do wartości w rejestrze
         *     3. ZAPIS    – zapisz nową wartość z powrotem do `counter`
         *
         * Jeżeli dwa wątki wykonają te kroki "na przemian" (interleaving),
         * jedna inkrementacja może zostać ZGUBIONA:
         *
         *     counter = 0
         *     Wątek A: ODCZYT  counter -> 0
         *     Wątek B: ODCZYT  counter -> 0      (jeszcze przed zapisem A!)
         *     Wątek A: DODANIE 0 + 1 = 1
         *     Wątek B: DODANIE 0 + 1 = 1
         *     Wątek A: ZAPIS   counter = 1
         *     Wątek B: ZAPIS   counter = 1        <- nadpisuje wynik A!
         *
         *     Wynik: counter == 1, a powinno być 2 (dwie inkrementacje!)
         *
         * To właśnie jest RACE CONDITION – dwa wątki "ścigają się"
         * o dostęp do wspólnych danych, a przegrana strona traci swoją
         * zmianę bezpowrotnie.
         */

        System.out.println("=== Race condition – niezsynchronizowany licznik ===");

        int threadCount = 4;
        int incrementsPerThread = 500_000;
        int expected = threadCount * incrementsPerThread;

        Counter counter = new Counter();
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            }, "Watek-" + i);
        }

        for (Thread t : threads) {
            t.start();
        }
        // ⏱️ join z limitem czasu – bezpiecznik, nigdy nie zawiesimy się
        // w nieskończoność, nawet gdyby coś poszło nie tak.
        for (Thread t : threads) {
            t.join(10_000);
        }

        int actual = counter.getValue();
        System.out.println("Oczekiwany wynik : " + expected);
        System.out.println("Rzeczywisty wynik: " + actual);

        if (actual != expected) {
            System.out.println("❌ RACE CONDITION! Brakuje " + (expected - actual) +
                    " inkrementacji – zostały \"zgubione\" przez nieatomowe count++.");
        } else {
            System.out.println("⚠️ Tym razem wynik się zgadza – ale to PRZYPADEK.");
            System.out.println("   Race condition jest NIEDETERMINISTYCZNY – uruchom program");
            System.out.println("   kilka razy albo zwiększ liczbę wątków/iteracji, a zobaczysz błędny wynik.");
        }

        /*
         * ============================================================
         * ❌ CZEGO NIE ROBIĆ
         * ============================================================
         * - Nie zakładaj, że proste operacje (++, +=, itp.) na współdzielonych
         *   polach są bezpieczne wątkowo – prawie nigdy nie są.
         * - Nie "testuj" wątkowego bezpieczeństwa jednym uruchomieniem –
         *   race condition może nie ujawnić się za każdym razem.
         *
         * ✅ JAK TO NAPRAWIĆ? (zobacz kolejne lekcje)
         * - synchronized (Lesson11_Synchronized)
         * - klasy z pakietu java.util.concurrent.atomic, np. AtomicInteger
         * - unikanie współdzielonego stanu mutowalnego (Lesson10_ThreadSafety)
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Race condition = wynik zależy od nieprzewidywalnej kolejności
         *   wykonywania wątków operujących na wspólnych danych.
         * - `counter++` to trzy kroki: odczyt -> dodanie -> zapis –
         *   operacja NIEATOMOWA.
         * - Przy interleavingu dwóch wątków część inkrementacji może
         *   zostać nadpisana i zgubiona.
         * - Objaw jest niedeterministyczny: raz wynik może się zgadzać,
         *   innym razem nie.
         * - Rozwiązania: synchronized, klasy Atomic*, unikanie
         *   współdzielonego stanu mutowalnego – patrz kolejne lekcje.
         */
    }

    /**
     * Prosty, CELOWO niezsynchronizowany licznik – do demonstracji
     * race condition.
     */
    static class Counter {
        private int value = 0;

        void increment() {
            value++; // ❌ NIEATOMOWE: odczyt + dodanie + zapis
        }

        int getValue() {
            return value;
        }
    }
}
