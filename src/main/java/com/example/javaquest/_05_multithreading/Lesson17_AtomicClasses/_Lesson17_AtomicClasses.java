package com.example.javaquest._05_multithreading.Lesson17_AtomicClasses;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class _Lesson17_AtomicClasses {

    // Demo 1: zwykły int - podatny na race condition
    private static int plainCounter = 0;

    // Demo 2: AtomicInteger - rozwiązanie bez synchronized
    private static final AtomicInteger atomicCounter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 PROBLEM: count++ TO RACE CONDITION
         * ============================================================
         * Znamy już problem z Lesson14_Volatile: `counter++` to tak naprawdę
         * TRZY kroki (odczytaj, dodaj 1, zapisz), które NIE są atomowe.
         * Gdy kilka wątków robi to jednocześnie na tym samym polu, część
         * inkrementacji "gubi się" - końcowy wynik jest NIŻSZY niż oczekiwany.
         *
         * Znane dotąd rozwiązanie: synchronized (Lesson07/09/13) - działa,
         * ale wymaga blokady (mutex), więc wątki czekają na siebie w kolejce.
         */

        System.out.println("=== Demo: race condition na zwykłym int ===");
        int threadsCount = 8;
        int iterations = 10_000;
        int expected = threadsCount * iterations;

        plainCounter = 0;
        Thread[] plainThreads = new Thread[threadsCount];
        for (int t = 0; t < threadsCount; t++) {
            plainThreads[t] = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    plainCounter++; // ❌ NIE atomowe - race condition
                }
            });
        }
        for (Thread th : plainThreads) th.start();
        for (Thread th : plainThreads) th.join(10_000); // bounded join

        System.out.println("Oczekiwany wynik: " + expected);
        System.out.println("Rzeczywisty wynik (plain int): " + plainCounter);
        if (plainCounter != expected) {
            System.out.println("-> Race condition potwierdzona: zgubione inkrementacje!");
        } else {
            System.out.println("-> Tym razem się udało, ale to i tak NIE jest bezpieczne.");
        }

        System.out.println();

        /*
         * ============================================================
         * ✅ ROZWIĄZANIE: java.util.concurrent.atomic - klasy Atomic*
         * ============================================================
         * Pakiet java.util.concurrent.atomic dostarcza klasy opakowujące
         * pojedynczą wartość, na której operacje odczytaj-zmodyfikuj-zapisz
         * są ATOMOWE - bez używania synchronized/Lock!
         *
         * Najważniejsze klasy:
         * - AtomicInteger  - atomowy int
         * - AtomicLong     - atomowy long
         * - AtomicBoolean  - atomowy boolean
         * - AtomicReference<T> - atomowa referencja do dowolnego obiektu
         *
         * Kluczowe metody AtomicInteger:
         * - incrementAndGet()      - zwiększ o 1, zwróć NOWĄ wartość
         * - getAndIncrement()      - zwiększ o 1, zwróć STARĄ wartość
         * - decrementAndGet() / getAndDecrement() - analogicznie, -1
         * - addAndGet(int delta)   - dodaj deltę, zwróć nową wartość
         * - get() / set(int value) - odczyt / zapis
         * - compareAndSet(expected, newValue) - patrz niżej: CAS
         */

        System.out.println("=== Demo: AtomicInteger - naprawiona wersja ===");
        atomicCounter.set(0);
        Thread[] atomicThreads = new Thread[threadsCount];
        for (int t = 0; t < threadsCount; t++) {
            atomicThreads[t] = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    atomicCounter.incrementAndGet(); // ✅ atomowe, bez synchronized!
                }
            });
        }
        for (Thread th : atomicThreads) th.start();
        for (Thread th : atomicThreads) th.join(10_000); // bounded join

        System.out.println("Oczekiwany wynik: " + expected);
        System.out.println("Rzeczywisty wynik (AtomicInteger): " + atomicCounter.get());
        System.out.println("-> Wynik zawsze DETERMINISTYCZNY i poprawny, bez synchronized!");

        System.out.println();

        /*
         * ============================================================
         * 🔍 JAK TO DZIAŁA? CAS - COMPARE-AND-SWAP (bez blokady!)
         * ============================================================
         * Klasy Atomic* NIE używają synchronized/Lock (to byłoby "blocking").
         * Zamiast tego korzystają ze sprzętowej instrukcji procesora CAS
         * (Compare-And-Swap), dostępnej dzięki klasom niskiego poziomu
         * (sun.misc.Unsafe / VarHandle pod spodem).
         *
         * CAS w uproszczeniu: "zamień wartość pola na nową, ALE TYLKO jeśli
         * jej aktualna wartość wciąż równa się tej, którą widziałem wcześniej.
         * Jeśli ktoś inny już ją zmienił w międzyczasie - nie rób nic, tylko
         * daj mi znać, żebym spróbował jeszcze raz."
         *
         * Pseudokod incrementAndGet():
         *   do {
         *       int current = get();          // odczytaj aktualną wartość
         *       int next = current + 1;        // oblicz nową
         *   } while (!compareAndSet(current, next)); // spróbuj podmienić atomowo
         *   return next;
         *
         * To podejście nazywa się "lock-free" (bez blokad): żaden wątek nigdy
         * nie CZEKA na inny (nie ma monitora do zdobycia) - w najgorszym razie
         * wątek po prostu PONAWIA próbę CAS, jeśli ktoś go "wyprzedził". Przy
         * niskiej/średniej rywalizacji jest to zwykle SZYBSZE niż synchronized,
         * bo nie ma kosztu usypiania/budzenia wątków przez system operacyjny.
         */

        System.out.println("=== Demo: compareAndSet() - jawne użycie CAS ===");
        AtomicInteger casDemo = new AtomicInteger(10);
        boolean success1 = casDemo.compareAndSet(10, 20); // oczekujemy 10 -> ustawiamy 20
        System.out.println("compareAndSet(10, 20) gdy wartość=10 -> sukces=" + success1 +
                ", nowa wartość=" + casDemo.get());

        boolean success2 = casDemo.compareAndSet(10, 99); // oczekujemy 10, ale jest już 20!
        System.out.println("compareAndSet(10, 99) gdy wartość=20 -> sukces=" + success2 +
                ", wartość bez zmian=" + casDemo.get());

        System.out.println();

        /*
         * ============================================================
         * 🔍 INNE KLASY ATOMIC: AtomicLong, AtomicBoolean, AtomicReference
         * ============================================================
         */

        System.out.println("=== Demo: AtomicLong, AtomicBoolean, AtomicReference ===");

        AtomicLong atomicLong = new AtomicLong(1_000_000_000L);
        atomicLong.addAndGet(5);
        System.out.println("AtomicLong po addAndGet(5): " + atomicLong.get());

        AtomicBoolean atomicFlag = new AtomicBoolean(false);
        boolean casFlag = atomicFlag.compareAndSet(false, true); // typowy wzorzec "ustaw raz"
        System.out.println("AtomicBoolean compareAndSet(false, true) -> sukces=" + casFlag +
                ", wartość=" + atomicFlag.get());
        boolean casFlagAgain = atomicFlag.compareAndSet(false, true); // teraz już nie zadziała
        System.out.println("Druga próba compareAndSet(false, true) -> sukces=" + casFlagAgain +
                " (bo wartość to już true)");

        AtomicReference<String> atomicRef = new AtomicReference<>("start");
        atomicRef.compareAndSet("start", "zaktualizowane");
        System.out.println("AtomicReference po compareAndSet: " + atomicRef.get());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - count++ na zwykłym polu (nawet volatile) to race condition przy
         *   wielu wątkach - operacja odczytaj-zmodyfikuj-zapisz nie jest atomowa
         * - java.util.concurrent.atomic: AtomicInteger, AtomicLong,
         *   AtomicBoolean, AtomicReference - operacje atomowe BEZ synchronized
         * - incrementAndGet()/getAndIncrement()/addAndGet() - atomowe modyfikacje
         * - compareAndSet(expected, newValue) - jawne CAS: podmień TYLKO gdy
         *   aktualna wartość zgadza się z oczekiwaną
         * - Mechanizm pod spodem: CAS (Compare-And-Swap) - sprzętowa instrukcja
         *   procesora, podejście "lock-free" (bez blokad, bez czekania wątków)
         * - Zwykle szybsze niż synchronized przy niskiej/średniej rywalizacji,
         *   bo nie ma kosztu zarządzania kolejką czekających wątków
         */
    }
}
