package com.example.javaquest._05_multithreading.Lesson08_VisibilityProblem;

public class _Lesson08_VisibilityProblem {

    // Pole współdzielone między wątkami – CELOWO bez `volatile`,
    // żeby zademonstrować problem widoczności (visibility problem).
    private static boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        /*
         * ============================================================
         * 📦 PROBLEM WIDOCZNOŚCI (VISIBILITY PROBLEM)
         * ============================================================
         * Wielordzeniowe procesory mają PAMIĘCI PODRĘCZNE (cache) per
         * rdzeń. Kiedy wątek A modyfikuje zmienną, zmiana może przez
         * jakiś czas istnieć TYLKO w cache rdzenia, na którym działa
         * wątek A – i nie od razu trafić do pamięci głównej (RAM),
         * z której korzysta wątek B.
         *
         * Dodatkowo kompilator (JIT) oraz procesor mogą PRZESTAWIAĆ
         * kolejność instrukcji (reordering) w ramach optymalizacji,
         * o ile nie zmienia to zachowania pojedynczego wątku – ale MOŻE
         * to zmienić to, co "widzi" inny wątek.
         *
         * Skutek: wątek B może NIGDY nie zauważyć zmiany dokonanej przez
         * wątek A, mimo że "z zewnątrz" wygląda to na prosty przypisanie
         * `running = false`.
         *
         * ⚠️ To jest INNY problem niż race condition (Lesson07)!
         * - Race condition: dwa wątki jednocześnie MODYFIKUJĄ tę samą
         *   daną i część zmian ginie.
         * - Visibility problem: jeden wątek modyfikuje daną, a drugi
         *   wątek nie widzi tej modyfikacji (albo widzi ją z dużym
         *   opóźnieniem) – nawet gdy nie ma tu żadnej "kolizji" zapisów.
         */

        /*
         * ============================================================
         * 🔍 JAVA MEMORY MODEL (JMM) – SKRÓT
         * ============================================================
         * Java Memory Model definiuje zasady, KIEDY zmiana dokonana przez
         * jeden wątek jest gwarantowanie widoczna dla innego wątku.
         *
         * Bez dodatkowych mechanizmów synchronizacji Java NIE GWARANTUJE:
         * - że zapis w jednym wątku będzie od razu (albo w ogóle) widoczny
         *   w drugim,
         * - że instrukcje wykonają się w kolejności "programistycznej"
         *   z punktu widzenia innego wątku.
         *
         * Mechanizmy, które WYMUSZAJĄ widoczność (tworzą tzw. "happens-before"):
         * - słowo kluczowe `volatile` – każdy zapis do pola volatile jest
         *   od razu zapisywany do pamięci głównej, każdy odczyt czyta
         *   "świeżą" wartość (bariera pamięci),
         * - `synchronized` (wejście/wyjście z monitora – Lesson11, Lesson12),
         * - klasy z java.util.concurrent (Atomic*, ExecutorService, itp.),
         * - uruchomienie wątku (Thread.start()) i zakończenie (Thread.join()).
         *
         * ✅ Rozwiązanie problemu widoczności z tej lekcji: oznaczyć pole
         *    jako `volatile boolean running` – wtedy zmiana w wątku
         *    głównym byłaby GWARANTOWANO widoczna dla wątku roboczego.
         */

        System.out.println("=== Watek roboczy sprawdzajacy flage BEZ volatile ===");

        Thread worker = new Thread(() -> {
            long iterations = 0;
            // Pętla czyta pole `running` w kółko. Bez volatile / synchronized
            // kompilator (JIT) MOŻE (choć nie musi) uznać, że skoro żaden kod
            // w tym wątku nie zmienia `running`, to może odczytać je RAZ
            // i trzymać w rejestrze – wtedy wątek NIGDY nie zauważy zmiany
            // dokonanej przez inny wątek.
            while (running) {
                iterations++;
            }
            System.out.println("Watek roboczy: zauwazylem zmiane po " + iterations + " iteracjach petli.");
        });
        // 🛡️ BEZPIECZNIK: wątek jako daemon – JVM zakończy proces nawet
        // gdyby ta pętla (teoretycznie) nigdy się nie skończyła.
        worker.setDaemon(true);
        worker.start();

        Thread.sleep(200); // dajemy wątkowi chwilę na start i wejście w pętlę
        System.out.println("Main: ustawiam running = false (bez volatile, bez synchronized)");
        running = false;

        // ⏱️ join z limitem czasu – NIE czekamy w nieskończoność.
        // To kluczowe zabezpieczenie: nawet jeśli wątek roboczy nigdy
        // nie zobaczy zmiany, program i tak zakończy się poprawnie.
        worker.join(2000);

        if (worker.isAlive()) {
            System.out.println("⚠️ Watek roboczy WCIĄŻ DZIAŁA po 2 sekundach!");
            System.out.println("   To jest właśnie problem widoczności w praktyce – zmiana");
            System.out.println("   `running = false` mogła nie zostać zauważona przez ten wątek.");
            System.out.println("   Program mimo to bezpiecznie się kończy, bo watek to daemon.");
        } else {
            System.out.println("✅ Watek roboczy zauwazyl zmiane i zakonczyl petle.");
            System.out.println("   UWAGA: ten objaw jest NIEDETERMINISTYCZNY – zależy od JVM,");
            System.out.println("   trybu JIT (interpreter/C1/C2), architektury CPU i obciążenia.");
            System.out.println("   Na wielu współczesnych JVM tak krótka pętla akurat \"zadziała\",");
            System.out.println("   ale NIE MA na to żadnej gwarancji bez volatile/synchronized –");
            System.out.println("   w dłuższych/innych programach brak volatile realnie prowadzi");
            System.out.println("   do wątków, które nigdy nie kończą pętli.");
        }

        /*
         * ============================================================
         * ✅ POPRAWKA – volatile boolean running
         * ============================================================
         * Wystarczyłoby zadeklarować:
         *
         *     private static volatile boolean running = true;
         *
         * i mielibyśmy GWARANCJĘ, że zapis `running = false` w jednym
         * wątku będzie natychmiast widoczny w drugim wątku (bariera
         * pamięci przy każdym odczycie/zapisie pola volatile).
         *
         * `volatile` NIE rozwiązuje race condition (nie czyni operacji
         * złożonych, np. `count++`, atomowymi) – rozwiązuje WYŁĄCZNIE
         * problem widoczności prostych odczytów/zapisów pojedynczej
         * zmiennej. Zobacz Lesson09_Atomicity po szczegóły.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Visibility problem: jeden wątek może nie widzieć (albo
         *   widzieć z opóźnieniem) zmian dokonanych przez inny wątek –
         *   przez cache CPU per-rdzeń, reordering instrukcji i brak
         *   bariery pamięci.
         * - Java Memory Model określa, kiedy zmiana jest gwarantowanie
         *   widoczna między wątkami ("happens-before").
         * - Bez volatile/synchronized/klas z java.util.concurrent NIE MA
         *   takiej gwarancji – nawet dla prostego `boolean running`.
         * - `volatile` wymusza widoczność zapisów/odczytów pojedynczego
         *   pola – to nie to samo, co atomowość operacji złożonych.
         * - Objawy problemu widoczności bywają niedeterministyczne –
         *   zależą od JVM, JIT i architektury sprzętu.
         */
    }
}
