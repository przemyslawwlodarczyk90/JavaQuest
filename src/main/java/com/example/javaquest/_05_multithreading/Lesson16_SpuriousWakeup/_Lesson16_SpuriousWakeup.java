package com.example.javaquest._05_multithreading.Lesson16_SpuriousWakeup;

public class _Lesson16_SpuriousWakeup {

    // Wspólny warunek, na który czeka wątek konsumenta
    private static boolean conditionReady = false;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 SPURIOUS WAKEUP – "FAŁSZYWE" OBUDZENIE
         * ============================================================
         * W Lesson15_WaitNotifyNotifyAll poznaliśmy wait()/notify()/notifyAll().
         * Intuicja podpowiada: wątek, który wywołał wait(), obudzi się TYLKO
         * wtedy, gdy ktoś wywoła notify()/notifyAll() na tym samym obiekcie.
         *
         * To NIEPRAWDA! Specyfikacja JVM (i POSIX threads, na których JVM
         * bazuje pod spodem) DOPUSZCZA tzw. "spurious wakeup" - sytuację,
         * w której wątek czekający w wait() może zostać obudzony BEZ ŻADNEGO
         * notify()/notifyAll(), z przyczyn niskopoziomowych (implementacja
         * systemu operacyjnego, optymalizacje JVM). Jest to rzadkie, ale
         * OFICJALNIE DOZWOLONE i trzeba się przed tym bronić w kodzie.
         *
         * Dokumentacja Javadoc Object.wait() wprost mówi:
         * "A thread can also wake up without being notified, interrupted,
         *  or timing out, a so-called spurious wakeup."
         */

        /*
         * ============================================================
         * ⚠️ ŹLE – sprawdzanie warunku w if
         * ============================================================
         * Częsty błąd początkujących:
         *
         *   synchronized (lock) {
         *       if (!conditionReady) {   // ❌ sprawdzone RAZ
         *           lock.wait();
         *       }
         *       // ... praca zakładająca, że conditionReady == true
         *   }
         *
         * Problem: jeśli wątek obudzi się przez spurious wakeup (ZANIM
         * ktokolwiek naprawdę ustawił conditionReady = true), kod PO wait()
         * i tak zostanie wykonany - `if` nie sprawdza warunku PONOWNIE po
         * przebudzeniu! Wątek działa dalej na fałszywym założeniu, że
         * warunek jest spełniony, co prowadzi do błędów trudnych do
         * odtworzenia (występują rzadko, losowo, ciężko je zreprodukować
         * i zdebugować).
         *
         * To samo ryzyko dotyczy notify() (bez notifyAll()) z Lesson15:
         * nawet gdy notify() faktycznie kogoś budzi, przy `if` nie ma
         * żadnej "drugiej szansy" na ponowne sprawdzenie warunku.
         */

        /*
         * ============================================================
         * ✅ DOBRZE – sprawdzanie warunku w pętli while
         * ============================================================
         *   synchronized (lock) {
         *       while (!conditionReady) {   // ✅ sprawdzane PONOWNIE po każdym obudzeniu
         *           lock.wait();
         *       }
         *       // tu mamy PEWNOŚĆ, że conditionReady == true
         *   }
         *
         * Pętla while gwarantuje: niezależnie OD CZEGO wątek się obudził
         * (prawdziwy notify/notifyAll, spurious wakeup, a nawet "obudzenie
         * przez pomyłkę" gdy notifyAll() budzi też wątki czekające na coś
         * INNEGO - patrz Lesson15), warunek jest sprawdzany PONOWNIE. Jeśli
         * wciąż nie jest spełniony, wątek po prostu wraca do wait().
         *
         * To dokładnie ten sam wzorzec, którego użyliśmy w BoundedBuffer
         * w Lesson15_WaitNotifyNotifyAll (while (queue.size() == capacity) ...
         * i while (queue.isEmpty()) ...) - tam "while zamiast if" nie było
         * przypadkiem, tylko świadomym zastosowaniem tej właśnie zasady.
         */

        System.out.println("=== Demo: poprawne oczekiwanie z pętlą while ===");

        Thread waiter = new Thread(() -> {
            synchronized (lock) {
                int spuriousChecks = 0;
                while (!conditionReady) {
                    try {
                        System.out.println("[Waiter] warunek jeszcze niespełniony, wchodzę w wait()...");
                        lock.wait(2000); // bounded wait - bezpieczeństwo, na wypadek zagubionego notify
                        spuriousChecks++;
                        System.out.println("[Waiter] obudzony (próba " + spuriousChecks +
                                "), sprawdzam warunek ponownie: conditionReady=" + conditionReady);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("[Waiter] warunek SPEŁNIONY - kontynuuję pracę.");
            }
        }, "Waiter");

        waiter.start();

        // main symuluje "fałszywe obudzenie" - budzi wątek przez notifyAll(),
        // ale BEZ ustawiania warunku na true. Gdyby waiter używał `if`,
        // wyszedłby z wait() i błędnie założył, że warunek jest spełniony!
        Thread.sleep(200);
        synchronized (lock) {
            System.out.println("[main] symuluję fałszywe obudzenie (notifyAll BEZ ustawienia warunku)");
            lock.notifyAll();
        }

        Thread.sleep(200);
        synchronized (lock) {
            System.out.println("[main] tym razem NAPRAWDĘ ustawiam warunek i budzę wątek");
            conditionReady = true;
            lock.notifyAll();
        }

        waiter.join(5_000); // bounded join - bezpieczeństwo
        System.out.println("Waiter żyje? " + waiter.isAlive());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Spurious wakeup = wątek może obudzić się z wait() BEZ realnego
         *   notify()/notifyAll() - to dozwolone przez specyfikację JVM
         * - `if (!warunek) wait();` jest BŁĘDNE - warunek sprawdzany tylko
         *   raz, przed wait(); po obudzeniu kod leci dalej bez ponownej
         *   weryfikacji
         * - `while (!warunek) wait();` jest POPRAWNE - po KAŻDYM obudzeniu
         *   (prawdziwym czy fałszywym) warunek jest sprawdzany od nowa
         * - Zasada obowiązuje ZAWSZE, niezależnie czy budzimy przez
         *   notify() czy notifyAll() (patrz Lesson15_WaitNotifyNotifyAll)
         * - Wzorzec "while(!warunek) wait();" to jedyny bezpieczny sposób
         *   użycia wait() w Javie - stosuj go zawsze, bez wyjątków
         */
    }
}
