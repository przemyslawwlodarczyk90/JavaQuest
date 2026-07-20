package com.example.javaquest._29_spring_reactive.Lesson02_ReactiveStreamsSpecification;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson02_ReactiveStreamsSpecification {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 2: Specyfikacja Reactive Streams - Publisher/Subscriber/Subscription/Processor ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - SPECYFIKACJA, nie implementacja
         * ============================================================
         * "Reactive Streams" TO SPECYFIKACJA (2015, wspoltworzona przez
         * Netflix/Pivotal/Lightbend/Twitter) - 4 INTERFEJSY Z jasno
         * zdefiniowanym KONTRAKTEM (zasady wywolan, kolejnosc, obsluga
         * bledow) - NIE konkretna biblioteka. Project Reactor (Lesson03),
         * RxJava, Akka Streams - TO WSZYSTKO SA IMPLEMENTACJE tej SAMEJ
         * specyfikacji - MOGA sie ze soba KOMUNIKOWAC (interoperability),
         * bo mowia TYM SAMYM "jezykiem" interfejsow.
         *
         * OD Javy 9 specyfikacja jest CZESCIA samego JDK jako
         * `java.util.concurrent.Flow` (identyczne interfejsy, inny
         * pakiet - Reactive Streams API zostalo "wciagniete" DO JDK).
         * TA lekcja uzywa WLASNIE `java.util.concurrent.Flow` (czysty
         * JDK, ZERO zaleznosci) DO pokazania SUROWEGO kontraktu, ZANIM
         * Lesson03 pokaze Project Reactor (WYGODNIEJSZE API zbudowane
         * NA tym SAMYM kontrakcie).
         */
        System.out.println("Reactive Streams (2015) - SPECYFIKACJA (Publisher/Subscriber/Subscription/Processor), OD Javy 9 czesc JDK jako java.util.concurrent.Flow.");

        demonstrateFourInterfaces();
        demonstrateBackpressureViaRequestN();
        demonstrateSubmissionPublisherRealDemo();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Publisher<T>` - ZRODLO danych, MA 1 metode:
         *   `subscribe(Subscriber<T>)`.
         * - `Subscriber<T>` - ODBIORCA danych, MA 4 metody:
         *   `onSubscribe(Subscription)`, `onNext(T)`, `onError(Throwable)`,
         *   `onComplete()`.
         * - `Subscription` - "PILOT" LACZACY Publishera Z Subscriberem,
         *   MA 2 metody: `request(long n)` (backpressure!) I `cancel()`.
         * - `Processor<T,R>` - JEDNOCZESNIE `Subscriber<T>` I
         *   `Publisher<R>` (LACZNIK MIEDZY 2 etapami pipeline'u).
         * - `java.util.concurrent.Flow` (Java 9+) - TE SAME interfejsy
         *   W JDK, BEZ zewnetrznej zaleznosci - Project Reactor
         *   (Lesson03) IMPLEMENTUJE dokladnie TEN SAM kontrakt.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateFourInterfaces() {
        System.out.println("\n--- 4 interfejsy specyfikacji Reactive Streams (java.util.concurrent.Flow) ---");
        System.out.println("Flow.Publisher<T>   { void subscribe(Subscriber<? super T> subscriber); }");
        System.out.println("Flow.Subscriber<T>  { onSubscribe(Subscription), onNext(T), onError(Throwable), onComplete(); }");
        System.out.println("Flow.Subscription   { void request(long n); void cancel(); }");
        System.out.println("Flow.Processor<T,R> extends Subscriber<T>, Publisher<R> - LACZY 2 etapy pipeline'u.");
        System.out.println("KONTRAKT (najwazniejsze zasady): onNext MOZE byc wywolane MAKSYMALNIE tyle razy, ILE zazadano PRZEZ request(n); onError/onComplete SA OSTATECZNE (po nich - ZERO dalszych wywolan).");
    }

    private static void demonstrateBackpressureViaRequestN() throws InterruptedException {
        System.out.println("\n--- Backpressure W PRAKTYCE - Subscriber DECYDUJE, ILE elementow chce naraz ---");

        AtomicInteger licznikOtrzymanych = new AtomicInteger();
        java.util.concurrent.CountDownLatch zakonczono = new java.util.concurrent.CountDownLatch(1);

        try (SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>()) {
            Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {
                private Flow.Subscription subscription;
                private static final int ZADAJ_NARAZ = 2;

                @Override
                public void onSubscribe(Flow.Subscription subscription) {
                    this.subscription = subscription;
                    System.out.println("onSubscribe() - Subscriber ZADAJE " + ZADAJ_NARAZ + " elementy naraz (BACKPRESSURE - kontrola tempa).");
                    subscription.request(ZADAJ_NARAZ);
                }

                @Override
                public void onNext(Integer item) {
                    int liczba = licznikOtrzymanych.incrementAndGet();
                    System.out.println("onNext(" + item + ") - odebrano element #" + liczba);
                    if (liczba % ZADAJ_NARAZ == 0) {
                        subscription.request(ZADAJ_NARAZ); // dopiero TERAZ prosimy o KOLEJNA porcje
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("onError: " + throwable.getMessage());
                    zakonczono.countDown();
                }

                @Override
                public void onComplete() {
                    System.out.println("onComplete() - Publisher zakonczyl strumien, WSZYSTKIE elementy dostarczone.");
                    zakonczono.countDown();
                }
            };

            publisher.subscribe(subscriber);

            for (int i = 1; i <= 6; i++) {
                publisher.submit(i);
            }
        } // try-with-resources -> publisher.close() -> wywoluje onComplete()

        boolean zakonczoneNaCzas = zakonczono.await(5, java.util.concurrent.TimeUnit.SECONDS);
        System.out.println("Zakonczono na czas: " + zakonczoneNaCzas + ", laczna liczba odebranych elementow: " + licznikOtrzymanych.get());

        assertThat(zakonczoneNaCzas).isTrue();
        assertThat(licznikOtrzymanych.get()).isEqualTo(6);
    }

    private static void demonstrateSubmissionPublisherRealDemo() throws InterruptedException {
        System.out.println("\n--- SubmissionPublisher (java.util.concurrent) - GOTOWA implementacja Publishera Z SAMEGO JDK ---");
        System.out.println("Uzyty powyzej `SubmissionPublisher<Integer>` TO PRAWDZIWA, DZIALAJACA implementacja `Flow.Publisher` DOSTEPNA W KAZDYM JDK 9+ - BEZ zewnetrznej biblioteki.");
        System.out.println("Project Reactor (Lesson03) DOSTARCZA BOGATSZE API (operatory typu map/filter/flatMap) NAD TYM SAMYM kontraktem - `Mono`/`Flux` IMPLEMENTUJA `Publisher` z tej specyfikacji.");
    }
}
