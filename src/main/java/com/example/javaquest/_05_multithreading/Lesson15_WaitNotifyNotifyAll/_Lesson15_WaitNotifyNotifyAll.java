package com.example.javaquest._05_multithreading.Lesson15_WaitNotifyNotifyAll;

import java.util.LinkedList;
import java.util.Queue;

public class _Lesson15_WaitNotifyNotifyAll {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 wait() / notify() / notifyAll() – CO TO JEST?
         * ============================================================
         * To metody klasy Object (KAŻDY obiekt w Javie je posiada), które
         * pozwalają wątkom "rozmawiać" ze sobą wokół wspólnej blokady:
         *
         * - wait()       – wątek ODDAJE monitor (blokadę) i zasypia, czekając
         *                  aż ktoś go obudzi. Podczas snu NIE trzyma blokady!
         * - notify()     – budzi JEDEN, losowo wybrany wątek czekający na
         *                  tym samym obiekcie (jeśli jakiś czeka).
         * - notifyAll()  – budzi WSZYSTKIE czekające wątki (one i tak muszą
         *                  potem po kolei zdobyć z powrotem blokadę).
         *
         * Typowe zastosowanie: koordynacja producent-konsument, kiedy jeden
         * wątek czeka aż inny przygotuje dane (albo zwolni miejsce w buforze).
         */

        /*
         * ============================================================
         * ⚠️ WYMÓG: wait()/notify()/notifyAll() TYLKO w bloku synchronized
         * ============================================================
         * Te metody operują na "monitorze" obiektu (tym samym, który
         * zdobywamy przez `synchronized (obj)`). Wywołanie ich BEZ trzymania
         * tej blokady jest błędem wykrywanym w RUNTIME:
         * java.lang.IllegalMonitorStateException.
         *
         * Pokażmy to na żywo:
         */

        System.out.println("=== Demo: IllegalMonitorStateException (błędne użycie) ===");
        Object monitor = new Object();
        try {
            // ❌ BŁĄD: wywołujemy wait() bez synchronized (monitor) !
            monitor.wait();
        } catch (IllegalMonitorStateException e) {
            System.out.println("Złapano wyjątek: " + e.getClass().getSimpleName() +
                    " -> wait() wywołane bez posiadania blokady na obiekcie 'monitor'.");
        }

        try {
            // ❌ To samo dotyczy notify()
            monitor.notify();
        } catch (IllegalMonitorStateException e) {
            System.out.println("Złapano wyjątek: " + e.getClass().getSimpleName() +
                    " -> notify() wywołane bez posiadania blokady na obiekcie 'monitor'.");
        }

        System.out.println("Poprawnie: obie operacje muszą być wewnątrz synchronized (monitor) { ... }");

        System.out.println();

        /*
         * ============================================================
         * 📦 KLASYCZNY PRZYKŁAD: PRODUCENT-KONSUMENT NA WSPÓLNYM BUFORZE
         * ============================================================
         * Mały, ograniczony bufor (pojemność 3). Producent dokłada elementy,
         * konsument je zabiera. Jeśli bufor jest PEŁNY, producent czeka
         * (wait()). Jeśli bufor jest PUSTY, konsument czeka (wait()).
         * Po każdej zmianie stanu wołamy notifyAll(), by obudzić drugą stronę.
         *
         * Demo jest MAŁE i DETERMINISTYCZNE: producent wyprodukuje dokładnie
         * 6 elementów, konsument skonsumuje dokładnie 6 elementów, po czym
         * oba wątki kończą pracę same - main() nigdy nie zawiśnie.
         */

        System.out.println("=== Demo: producent-konsument (notifyAll) ===");
        BoundedBuffer buffer = new BoundedBuffer(3);
        int itemsToProduce = 6;

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= itemsToProduce; i++) {
                buffer.put(i);
            }
        }, "Producent");

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= itemsToProduce; i++) {
                buffer.take();
            }
        }, "Konsument");

        producer.start();
        consumer.start();

        producer.join(10_000); // bounded join - bezpieczeństwo
        consumer.join(10_000);

        System.out.println("Producent żyje? " + producer.isAlive() + ", Konsument żyje? " + consumer.isAlive());

        System.out.println();

        /*
         * ============================================================
         * 🔍 notify() vs notifyAll() – RÓŻNICA I DLACZEGO notifyAll()
         *    JEST ZWYKLE BEZPIECZNIEJSZE
         * ============================================================
         * notify() budzi TYLKO JEDEN, losowo wybrany wątek spośród
         * wszystkich czekających na danym monitorze. Problem: jeśli na tym
         * samym obiekcie czekają wątki z RÓŻNYCH powodów (np. część czeka
         * "bufor pełny", a część "bufor pusty"), notify() może obudzić
         * wątek, który i tak nie może teraz kontynuować - a wątek, który
         * naprawdę powinien się obudzić, śpi dalej. To może prowadzić do
         * tzw. "lost wakeup" / zakleszczenia logicznego (wątki czekają
         * w nieskończoność, mimo że warunek dawno się spełnił).
         *
         * notifyAll() budzi WSZYSTKIE czekające wątki. Każdy z nich, po
         * odzyskaniu blokady, sam sprawdza swój warunek w pętli while
         * (patrz: Lesson16_SpuriousWakeup) - ci, dla których warunek wciąż
         * nie jest spełniony, po prostu wracają do wait(). To kosztuje
         * trochę więcej (niepotrzebne "obudzenia"), ale jest ODPORNE na
         * pomyłkę wyboru "kogo obudzić".
         *
         * Zasada: używaj notifyAll(), chyba że MASZ PEWNOŚĆ, że:
         * - wszystkie czekające wątki czekają na dokładnie to samo,
         * - i obudzenie dowolnego z nich jest równie poprawne.
         * W naszym BoundedBuffer używamy notifyAll() właśnie dlatego, że
         * na monitorze czekają zarówno producenci, jak i konsumenci -
         * a to dwie różne grupy z różnymi warunkami.
         */

        System.out.println("=== Podsumowanie różnicy notify() vs notifyAll() ===");
        System.out.println("notify()    -> budzi JEDEN losowy wątek (ryzyko: może obudzić 'niewłaściwy')");
        System.out.println("notifyAll() -> budzi WSZYSTKIE wątki, każdy sam sprawdza swój warunek (bezpieczniejsze)");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - wait()/notify()/notifyAll() to metody Object, działają na
         *   monitorze obiektu - MUSZĄ być wywoływane wewnątrz
         *   synchronized(tenSamObiekt), inaczej IllegalMonitorStateException
         * - wait() oddaje blokadę i usypia wątek do czasu notify/notifyAll
         *   (lub przerwania / spurious wakeup - patrz Lesson16)
         * - Klasyczny wzorzec: bufor współdzielony producent-konsument,
         *   sprawdzanie warunku w pętli while
         * - notify() budzi jeden losowy wątek - ryzykowne przy wielu różnych
         *   warunkach oczekiwania
         * - notifyAll() budzi wszystkich - bezpieczniejsze, zalecane jako
         *   domyślny wybór, chyba że masz pewność że notify() wystarczy
         */
    }

    /**
     * Mały, ograniczony bufor współdzielony między producentem a konsumentem.
     * Klasyczny przykład użycia wait()/notifyAll() do koordynacji wątków.
     */
    static class BoundedBuffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        synchronized void put(int value) {
            while (queue.size() == capacity) {
                // bufor pełny - producent musi poczekać na miejsce
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            queue.add(value);
            System.out.println("[Producent] dodał " + value + " (rozmiar bufora: " + queue.size() + ")");
            notifyAll(); // budzimy ewentualnych czekających konsumentów (i producentów)
        }

        synchronized int take() {
            while (queue.isEmpty()) {
                // bufor pusty - konsument musi poczekać na dane
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return -1;
                }
            }
            int value = queue.poll();
            System.out.println("[Konsument] zabrał " + value + " (rozmiar bufora: " + queue.size() + ")");
            notifyAll(); // budzimy ewentualnych czekających producentów (i konsumentów)
            return value;
        }
    }
}
