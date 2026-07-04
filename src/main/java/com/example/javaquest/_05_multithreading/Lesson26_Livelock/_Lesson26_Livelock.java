package com.example.javaquest._05_multithreading.Lesson26_Livelock;

public class _Lesson26_Livelock {

    public static void main(String[] args) throws Exception {

        /*
         * ============================================================
         * 📦 LIVELOCK – CZYM JEST?
         * ============================================================
         * Livelock to sytuacja, w której wątki NIE są zablokowane
         * technicznie (każdy z nich cały czas coś "robi", zużywa CPU,
         * zmienia swój stan), ale mimo to NIE ROBIĄ ŻADNEGO REALNEGO
         * POSTĘPU w wykonaniu swojego zadania.
         *
         * Klasyczna analogia: dwie osoby idą korytarzem naprzeciwko
         * siebie. Obie chcą się minąć, więc obie uprzejmie robią krok
         * w bok – w tę samą stronę. Nadal się blokują. Obie znowu robią
         * krok w drugą stronę – i znowu się blokują. W nieskończoność
         * "ustępują sobie miejsca", ale żadna nigdy nie przejdzie.
         *
         * 🔍 DEADLOCK vs LIVELOCK:
         * - Deadlock: wątki są ZABLOKOWANE (WAITING/BLOCKED) – stoją
         *   w miejscu, nic nie robią, czekają na zwolnienie zasobu.
         * - Livelock: wątki są AKTYWNE (RUNNABLE) – bez przerwy
         *   wykonują instrukcje, zużywają CPU – ale efektywnie stoją
         *   w miejscu, bo wciąż "reagują" na siebie nawzajem i cofają się.
         *
         * Livelock często powstaje właśnie jako "naiwna próba naprawy"
         * deadlocka – wątki, zamiast czekać w nieskończoność, wycofują
         * się przy kolizji... ale robią to w sposób, który prowadzi do
         * powtarzania się tej samej kolizji w kółko.
         *
         * ⚠️ UWAGA BEZPIECZEŃSTWA: prawdziwy livelock nigdy sam się nie
         * kończy. Żeby ten przykład bezpiecznie się zakończył, wątki są
         * DEMONICZNE (setDaemon(true)), a próby zdobycia zasobu są
         * OGRANICZONE do skończonej liczby (MAX_ATTEMPTS) – po ich
         * wyczerpaniu wątek się poddaje i kończy z komunikatem
         * diagnostycznym. main() dodatkowo czeka na wątki tylko przez
         * ograniczony czas (join(timeout)).
         */

        System.out.println("=== Demonstracja livelocka (bezpieczna, ograniczona liczba prob) ===");

        // Wspólny "korytarz" – dwa zasoby, każdy wątek próbuje zdobyć oba,
        // ale gdy wykryje, że drugi wątek właśnie "wszedł" na jego pozycję,
        // grzecznie się cofa (ustępuje) i próbuje ponownie.
        Corridor corridor = new Corridor();

        final int maxAttempts = 5;

        Thread personA = new Thread(
                () -> walkThroughCorridor("Osoba-A", corridor, maxAttempts, true),
                "Osoba-A");
        Thread personB = new Thread(
                () -> walkThroughCorridor("Osoba-B", corridor, maxAttempts, false),
                "Osoba-B");

        // KLUCZOWE: wątki demoniczne, żeby JVM mógł zakończyć program
        // nawet gdyby (w gorszym scenariuszu) coś poszło nie tak.
        personA.setDaemon(true);
        personB.setDaemon(true);

        personA.start();
        personB.start();

        // Czekamy TYLKO ograniczony czas – nigdy join() bez timeoutu.
        personA.join(3000);
        personB.join(3000);

        System.out.println();
        System.out.println("Wniosek: obie osoby byly caly czas AKTYWNE (nie zablokowane),");
        System.out.println("ale przez wzajemne \"ustepowanie miejsca\" zaden watek nie");
        System.out.println("zrobil realnego postepu az do wyczerpania limitu prob.");
        System.out.println("To jest istota livelocka – w odroznieniu od deadlocka,");
        System.out.println("gdzie watki po prostu staja w miejscu (BLOCKED/WAITING).");

        System.out.println();

        /*
         * ============================================================
         * 🔍 JAK UNIKAĆ LIVELOCKA
         * ============================================================
         * - Losowy (nie deterministyczny) czas oczekiwania przed ponowną
         *   próbą – jeśli oba wątki cofają się o LOSOWY czas, prędzej
         *   czy później ich próby "rozjadą się" w czasie i jeden z nich
         *   zdąży przejść.
         * - Priorytet / kolejność – np. wątek o niższym ID zawsze ma
         *   pierwszeństwo i NIE ustępuje, drugi zawsze się wycofuje.
         * - Ograniczona liczba prób + jasna strategia "kto ustępuje"
         *   zamiast symetrycznego zachowania obu stron.
         *
         * Poniżej ta sama sytuacja, ale z prostą regułą: w razie kolizji
         * ustępuje TYLKO ten wątek, który ma "wyższy" priorytet w tej
         * rundzie (na zmianę), więc któryś w końcu przejdzie.
         */

        System.out.println("=== Naprawiona wersja – jasna regula \"kto ustepuje\" ===");

        Corridor fixedCorridor = new Corridor();

        Thread fixedA = new Thread(
                () -> walkThroughCorridorFixed("Fixed-A", fixedCorridor, true),
                "Fixed-A");
        Thread fixedB = new Thread(
                () -> walkThroughCorridorFixed("Fixed-B", fixedCorridor, false),
                "Fixed-B");

        fixedA.setDaemon(true);
        fixedB.setDaemon(true);

        fixedA.start();
        fixedB.start();

        fixedA.join(2000);
        fixedB.join(2000);

        System.out.println("Wniosek: gdy tylko JEDEN watek ustepuje (a drugi nigdy),");
        System.out.println("livelock jest niemozliwy – zawsze ktos przejdzie.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Livelock: wątki są AKTYWNE, ale przez wzajemne reagowanie
         *   na siebie (np. "uprzejme" wycofywanie się) nie robią postępu.
         * - Różnica od deadlocka: tu wątki NIE są zablokowane (BLOCKED/
         *   WAITING) – cały czas wykonują kod, zużywają CPU.
         * - Typowa przyczyna: symetryczna, deterministyczna reakcja obu
         *   stron na kolizję (obie zawsze robią to samo w tym samym
         *   momencie).
         * - Rozwiązania: losowość (jitter) w czasie ponawiania prób,
         *   jasna hierarchia "kto ustępuje", ograniczona liczba prób
         *   z jawnym poddaniem się (fallback).
         * ⚠️ Testując livelocki – ZAWSZE ograniczaj liczbę prób/czas
         *    i używaj wątków daemon + join() z timeoutem!
         */
    }

    /**
     * Wątek sygnalizuje w korytarzu "chcę przejść" (ustawia swoją flagę),
     * po czym sprawdza, czy druga osoba też właśnie tego chce. Jeśli tak –
     * to KOLIZJA – obie strony grzecznie się cofają (zdejmują swoją flagę)
     * i próbują ponownie. Ponieważ OBIE strony reagują dokładnie tak samo
     * i w tym samym rytmie (ta sama logika, to samo opóźnienie), kolizja
     * powtarza się w kółko – to jest właśnie livelock. Ograniczamy liczbę
     * prób, by bezpiecznie zakończyć demonstrację.
     */
    private static void walkThroughCorridor(String name, Corridor corridor, int maxAttempts, boolean isPersonA) {
        int attempt = 0;
        boolean passed = false;

        while (attempt < maxAttempts && !passed) {
            attempt++;

            corridor.signalWantsToPass(isPersonA, true);
            // Krótka pauza, żeby dać drugiej stronie szansę zauważyć naszą
            // flagę – to właśnie synchronizuje obie strony w tym samym
            // rytmie i gwarantuje powtarzalną kolizję (do celów demonstracji).
            sleepQuiet(50);

            if (corridor.otherWantsToPass(isPersonA)) {
                System.out.println(name + ": kolizja, uprzejmie ustepuje miejsca (proba " + attempt + ")");
                corridor.signalWantsToPass(isPersonA, false);
                sleepQuiet(50);
            } else {
                passed = true;
            }
        }

        if (passed) {
            System.out.println(name + ": przeszedl korytarzem po " + attempt + " probie/probach.");
        } else {
            System.out.println(name + ": PODDAJE SIE po " + maxAttempts
                    + " probach – to jest livelock (brak postepu mimo aktywnosci).");
        }
    }

    /**
     * Naprawiona wersja: tylko jedna strona (yielding == true) kiedykolwiek
     * ustępuje, druga (yielding == false) zawsze idzie prosto przed siebie.
     * Dzięki jasnej regule "kto ustępuje" kolizja rozwiązuje się od razu.
     */
    private static void walkThroughCorridorFixed(String name, Corridor corridor, boolean yielding) {
        if (!yielding) {
            // Ta strona nigdy nie ustępuje – po prostu przechodzi.
            System.out.println(name + " (ma pierwszenstwo): przechodzi bez wahania.");
            return;
        }

        int attempt = 0;
        boolean passed = false;
        while (attempt < 5 && !passed) {
            attempt++;
            sleepQuiet(30);
            // Strona ustępująca zakłada, że druga może chcieć przejść,
            // więc po prostu czeka chwilę, a potem przechodzi.
            System.out.println(name + " (ustepujacy): czekam chwile (proba " + attempt + ")");
            passed = true;
        }
        System.out.println(name + ": przeszedl korytarzem po tym, jak druga strona miala pierwszenstwo.");
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Prosty "korytarz" z dwiema flagami intencji (po jednej na osobę).
     * Każda osoba ustawia swoją flagę, gdy chce przejść, i sprawdza flagę
     * drugiej – to pozwala wykryć "kolizję" analogiczną do dwóch osób
     * próbujących minąć się w wąskim przejściu.
     */
    private static class Corridor {
        private volatile boolean personAWantsToPass = false;
        private volatile boolean personBWantsToPass = false;

        void signalWantsToPass(boolean isPersonA, boolean wants) {
            if (isPersonA) {
                personAWantsToPass = wants;
            } else {
                personBWantsToPass = wants;
            }
        }

        boolean otherWantsToPass(boolean isPersonA) {
            return isPersonA ? personBWantsToPass : personAWantsToPass;
        }
    }
}
