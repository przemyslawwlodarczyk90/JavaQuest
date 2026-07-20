package com.example.javaquest._28_java_evolution.Lesson17_Java19To20VirtualThreadsPreview;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson17_Java19To20VirtualThreadsPreview {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 17: Java 19-20 (2022) - watki wirtualne I structured concurrency jako PREVIEW/INCUBATOR ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - droga watkow wirtualnych DO stabilizacji
         * ============================================================
         * Java 19 (wrzesien 2022): JEP 425 - watki wirtualne JAKO
         * PREVIEW (pierwszy raz W JDK, Project Loom). JEP 428 -
         * "structured concurrency" JAKO INKUBATOR (API JESZCZE
         * BARDZIEJ eksperymentalne NIZ preview - MOZE ZMIENIC PAKIET/
         * nazwe MIEDZY wersjami).
         * Java 20 (marzec 2023): JEP 436 - watki wirtualne, DRUGA
         * runda preview (drobne poprawki API). JEP 437 - structured
         * concurrency, DRUGA runda inkubatora.
         * Java 21 (wrzesien 2023, LTS): JEP 444 - watki wirtualne
         * OSTATECZNIE STABILNE (Lesson19 tego rozdzialu, pelna teoria:
         * `_05_multithreading/Lesson33`). Structured concurrency
         * NADAL preview W Javie 21 (I DALEJ, az DO finalizacji -
         * Lesson22 tego rozdzialu sprawdzi DOKLADNY status W
         * najnowszych wersjach).
         *
         * TA lekcja pokazuje watki wirtualne DZIALAJACE (bo SA JUZ
         * stabilne W Javie 21, ktorej ten projekt uzywa) - Z
         * ZAZNACZENIEM, ze W Javie 19-20 wymagalyby `--enable-preview`.
         * Structured concurrency jest TU OPISANA koncepcyjnie (JAKO
         * fragment kodu W komentarzu), bo `StructuredTaskScope` JEST
         * NADAL preview API rowniez W Javie 21 (projekt kompiluje sie
         * BEZ `--enable-preview`, wiec NIE MOZEMY jej TU uzyc W
         * `src/main/java`).
         */
        System.out.println("Java 19 (2022): watki wirtualne PREVIEW (JEP 425), structured concurrency INCUBATOR (JEP 428).");
        System.out.println("Java 20 (2023): druga runda obu (JEP 436, JEP 437). Java 21 (LTS): watki wirtualne STABILNE (Lesson19), structured concurrency WCIAZ preview.");

        demonstrateVirtualThreadsWereOnceExperimental();
        demonstrateScaleAdvantageOverPlatformThreads();
        explainStructuredConcurrencyConceptually();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Watki wirtualne PRZESZLY droge: 19 preview -> 20 preview
         *   (runda 2) -> 21 STABILNE (JEP 444, LTS).
         * - Structured concurrency PRZESZLA droge: 19 incubator ->
         *   20 incubator (runda 2) -> 21+ preview -> STATUS W
         *   NAJNOWSZYCH wersjach: Lesson22 (Java 24-25).
         * - "Incubator" TO WCZESNIEJSZY etap NIZ "preview" - API MOZE
         *   sie zmienic BARDZIEJ RADYKALNIE (WLACZNIE Z PRZENIESIENIEM
         *   DO INNEGO pakietu).
         * - Pelna teoria watkow wirtualnych (STABILNYCH): `Lesson19`
         *   tego rozdzialu, `_05_multithreading/Lesson33`.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 ===");
    }

    private static void demonstrateVirtualThreadsWereOnceExperimental() throws InterruptedException {
        System.out.println("\n--- Watki wirtualne - dzis stabilne (Java 21+), ale W 19-20 WYMAGALY --enable-preview ---");
        Thread watekWirtualny = Thread.ofVirtual()
                .name("watek-wirtualny-demo")
                .start(() -> System.out.println("Dzialam W watku: " + Thread.currentThread()));
        watekWirtualny.join(2000);

        System.out.println("W Javie 19/20 TEN SAM kod WYMAGALBY: 'javac --release 19 --enable-preview' I 'java --enable-preview'.");
        System.out.println("OD Javy 21 (JEP 444): dziala BEZ zadnej flagi - pelna teoria: Lesson19, _05_multithreading/Lesson33.");

        assertThat(watekWirtualny.isVirtual()).isTrue();
    }

    private static void demonstrateScaleAdvantageOverPlatformThreads() throws InterruptedException {
        System.out.println("\n--- Sedno Project Loom: MILIONY watkow wirtualnych, NIE dziesiatki tysiecy platformowych ---");
        int liczbaZadan = 10_000;

        Instant start = Instant.now();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> wyniki = new ArrayList<>();
            for (int i = 0; i < liczbaZadan; i++) {
                int indeks = i;
                wyniki.add(executor.submit(() -> {
                    Thread.sleep(1); // symulacja krotkiego I/O
                    return indeks;
                }));
            }
            int suma = 0;
            for (Future<Integer> wynik : wyniki) {
                try {
                    suma += wynik.get();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            System.out.println("Zakonczono " + liczbaZadan + " zadan (kazde Z 1ms 'I/O') NA watkach wirtualnych, suma indeksow=" + suma);
        }
        Duration czas = Duration.between(start, Instant.now());
        System.out.println("Czas calkowity: " + czas.toMillis() + "ms - " + liczbaZadan + " PLATFORMOWYCH watkow ZUZYLOBY setki MB pamieci I prawdopodobnie NIE wystartowaloby WCALE naraz.");

        assertThat(czas.toMillis()).isLessThan(15_000);
    }

    private static void explainStructuredConcurrencyConceptually() {
        System.out.println("\n--- Structured Concurrency - KONCEPCYJNIE (StructuredTaskScope wciaz preview NAWET w Javie 21) ---");
        System.out.println("Idea: grupa POWIAZANYCH zadan wspolbieznych TRAKTOWANA JAKO JEDNA jednostka - jesli 1 zadanie zawiedzie, RESZTA jest ANULOWANA, a blad PROPAGUJE sie DO rodzica.");
        System.out.println("""
                Przykladowy kod (koncepcyjny, WYMAGA --enable-preview NAWET w Javie 21+):

                try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                    Future<String> uzytkownik = scope.fork(() -> pobierzUzytkownika());
                    Future<String> zamowienia = scope.fork(() -> pobierzZamowienia());

                    scope.join();
                    scope.throwIfFailed();

                    return laczWynik(uzytkownik.resultNow(), zamowienia.resultNow());
                }""");
        System.out.println("Zaleta wobec 'goleg' CompletableFuture (_14_advancedjava/Lesson32): CZYTELNY, HIERARCHICZNY zasieg watkow (jak zagniezdzone bloki kodu), automatyczne ANULOWANIE 'braci' PRZY bledzie.");
        System.out.println("Dokladny status stabilizacji W najnowszych wersjach JDK: Lesson22 (Java 24-25) tego rozdzialu.");
    }
}
