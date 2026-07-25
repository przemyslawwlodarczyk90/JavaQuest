package com.example.javaquest._30_spring_messaging_and_async.Lesson05_ApplicationEventsDeepDive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson05_ApplicationEventsDeepDive {

    record ZamowienieZlozone(String idZamowienia, double kwota) {
    }

    @Service
    static class ZamowieniaService {
        private final ApplicationEventPublisher publisher;

        ZamowieniaService(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        public void zlozZamowienie(String id, double kwota) {
            System.out.println("ZamowieniaService: zamowienie " + id + " ZLOZONE (" + kwota + " PLN).");
            publisher.publishEvent(new ZamowienieZlozone(id, kwota));
        }
    }

    @Component
    static class EmailListener {
        final List<String> wyslaneMaile = new CopyOnWriteArrayList<>();

        @EventListener
        public void naZlozenieZamowienia(ZamowienieZlozone zdarzenie) {
            wyslaneMaile.add("Email potwierdzajacy dla " + zdarzenie.idZamowienia());
            System.out.println("  EmailListener: wysylam email potwierdzajacy DLA " + zdarzenie.idZamowienia() + " NA watku: " + Thread.currentThread().getName());
        }

        public List<String> getWyslaneMaile() {
            return wyslaneMaile;
        }
    }

    @Component
    static class MagazynListener {
        final List<String> zarezerwowaneTowary = new CopyOnWriteArrayList<>();

        @Async
        @EventListener
        public void naZlozenieZamowienia(ZamowienieZlozone zdarzenie) {
            zarezerwowaneTowary.add(zdarzenie.idZamowienia());
            System.out.println("  MagazynListener (@Async): rezerwuje towar DLA " + zdarzenie.idZamowienia() + " NA watku: " + Thread.currentThread().getName());
        }

        public List<String> getZarezerwowaneTowary() {
            return zarezerwowaneTowary;
        }
    }

    @SpringBootApplication
    @EnableAsync
    static class EventsApp {
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 5: ApplicationEvents pogliebienie - jak kontener SAM 'podpina' sluchaczy ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE W `_20_spring_core/Lesson20`
         * ============================================================
         * `_20_spring_core/Lesson20_ApplicationEvents` uczyl PODSTAW:
         * `ApplicationEventPublisher.publishEvent(...)` + `@EventListener`.
         * BEZPOSREDNIE NAWIAZANIE DO `_17_architecture/Lesson18_
         * EventDrivenCommunicationBetweenModules` (TAM: RECZNY
         * publisher/listener CZYSTA Java; TU: Spring SAM "podpina"
         * sluchaczy ZA CIEBIE - kontener SKANUJE beany, ZNAJDUJE
         * metody `@EventListener`, I AUTOMATYCZNIE REJESTRUJE JE JAKO
         * subskrybentow ODPOWIEDNIEGO typu zdarzenia).
         *
         * TA lekcja POGLEBIA: (1) domyslnie zdarzenia SA
         * SYNCHRONICZNE (publisher CZEKA, az WSZYSCY sluchacze
         * skoncza), (2) `@Async` NA `@EventListener` ZMIENIA to -
         * MOZNA MIESZAC synchronicznych I asynchronicznych sluchaczy
         * DLA TEGO SAMEGO zdarzenia.
         */
        System.out.println("Pogliebienie _20_spring_core/Lesson20: domyslnie zdarzenia SA synchroniczne, @Async NA @EventListener ZMIENIA to.");

        try (ConfigurableApplicationContext context = SpringApplication.run(EventsApp.class, "--server.port=0", "--logging.level.root=WARN")) {
            ZamowieniaService serwis = context.getBean(ZamowieniaService.class);
            EmailListener emailListener = context.getBean(EmailListener.class);
            MagazynListener magazynListener = context.getBean(MagazynListener.class);

            demonstrateSyncVsAsyncListeners(serwis, emailListener, magazynListener);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EventListener` (BEZ `@Async`) - SYNCHRONICZNY - publisher
         *   CZEKA, az sluchacz SKONCZY, ZANIM `publishEvent()` sie
         *   ZWROCI.
         * - `@Async` + `@EventListener` NA TEJ SAMEJ metodzie - TEN
         *   KONKRETNY sluchacz staje sie ASYNCHRONICZNY (WYMAGA
         *   `@EnableAsync`, Lesson01).
         * - MOZNA MIESZAC oba style DLA ROZNYCH sluchaczy TEGO SAMEGO
         *   zdarzenia - KAZDY sluchacz DECYDUJE NIEZALEZNIE.
         * - Powiazanie Z `_17_architecture/Lesson18` - TO SAMA idea
         *   "luznego powiazania" (ZamowieniaService NIE WIE NIC o
         *   EmailListener/MagazynListener), ale TERAZ kontener Springa
         *   ROBI "podpinanie" AUTOMATYCZNIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    private static void demonstrateSyncVsAsyncListeners(ZamowieniaService serwis, EmailListener emailListener, MagazynListener magazynListener) throws InterruptedException {
        System.out.println("\n--- publishEvent() - EmailListener SYNCHRONICZNY, MagazynListener @Async ---");

        long start = System.currentTimeMillis();
        serwis.zlozZamowienie("Z100", 299.99);
        long czasPublikacji = System.currentTimeMillis() - start;

        System.out.println("publishEvent() zwrocil sie PO " + czasPublikacji + "ms - EmailListener (SYNCHRONICZNY) JUZ SKONCZYL, MagazynListener (@Async) MOZE JESZCZE DZIALAC.");

        assertThat(emailListener.getWyslaneMaile()).hasSize(1);

        Thread.sleep(100); // poczekaj na @Async listener
        assertThat(magazynListener.getZarezerwowaneTowary()).hasSize(1);
        System.out.println("PO oczekiwaniu: MagazynListener TEZ SKONCZYL - zarezerwowane towary: " + magazynListener.getZarezerwowaneTowary());
    }
}
