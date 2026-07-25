package com.example.javaquest._30_spring_messaging_and_async.Lesson16_MessagingCapstone;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson16_MessagingCapstone {

    record ZamowienieUtworzone(String id, String klient, double kwota) {
    }

    @Service
    static class ZamowieniaService {
        private final ApplicationEventPublisher publisher;

        ZamowieniaService(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }

        public void zlozZamowienie(String id, String klient, double kwota) {
            System.out.println("[ZamowieniaService] Zamowienie " + id + " OD " + klient + " (" + kwota + " PLN) ZLOZONE.");
            publisher.publishEvent(new ZamowienieUtworzone(id, klient, kwota));
        }
    }

    @Component
    static class PowiadomieniaListener {
        // PRYWATNE pola + gettery (NIE publiczny dostep bezposredni) - Spring OPAKOWUJE ten bean
        // W proxy CGLIB (potrzebne DLA @Async), tworzony PRZEZ Objenesis Z POMINIECIEM konstruktora -
        // inicjalizatory pol NA SAMEJ POWLOCE proxy NIGDY sie NIE wykonuja. Bezposredni dostep DO
        // pola publicznego czytalby WIEC pole PROXY (null) - TA SAMA pulapka co W `Lesson02`
        // (`_30_spring_messaging_and_async`), odkryta empirycznie (NullPointerException).
        private final List<String> wyslaneEmaile = new CopyOnWriteArrayList<>();
        private final CountDownLatch zatrzask = new CountDownLatch(1);

        @Async
        @EventListener
        public void naZamowienie(ZamowienieUtworzone zdarzenie) {
            wyslaneEmaile.add("Email DO " + zdarzenie.klient() + ": potwierdzenie zamowienia " + zdarzenie.id());
            System.out.println("  [PowiadomieniaListener @Async] " + wyslaneEmaile.get(wyslaneEmaile.size() - 1));
            zatrzask.countDown();
        }

        List<String> getWyslaneEmaile() {
            return wyslaneEmaile;
        }

        CountDownLatch getZatrzask() {
            return zatrzask;
        }
    }

    @Component
    static class AnalitykaListener {
        final AtomicInteger licznikZamowien = new AtomicInteger();
        final AtomicInteger sumaKwot = new AtomicInteger();

        @EventListener
        public void naZamowienie(ZamowienieUtworzone zdarzenie) {
            licznikZamowien.incrementAndGet();
            sumaKwot.addAndGet((int) zdarzenie.kwota());
            System.out.println("  [AnalitykaListener SYNC] Zaktualizowano statystyki: " + licznikZamowien.get() + " zamowien, suma=" + sumaKwot.get() + " PLN.");
        }
    }

    @Component
    static class RaportScheduler {
        private final AnalitykaListener analityka;
        private final CountDownLatch zatrzaskRaportu = new CountDownLatch(1);
        private List<String> ostatniRaport = List.of();

        RaportScheduler(AnalitykaListener analityka) {
            this.analityka = analityka;
        }

        @Scheduled(fixedDelay = 100)
        public void generujRaport() {
            if (analityka.licznikZamowien.get() > 0 && zatrzaskRaportu.getCount() > 0) {
                ostatniRaport = List.of("RAPORT: " + analityka.licznikZamowien.get() + " zamowien, suma=" + analityka.sumaKwot.get() + " PLN");
                System.out.println("  [RaportScheduler @Scheduled] " + ostatniRaport.get(0));
                zatrzaskRaportu.countDown();
            }
        }

        List<String> getOstatniRaport() {
            return ostatniRaport;
        }
    }

    @SpringBootApplication
    @EnableAsync
    @EnableScheduling
    static class CapstoneApp {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16 (KAPSZTON): JavaQuest Order Processing - @Async + @Scheduled + Events + AMQP ===");

        /*
         * ============================================================
         * 🏁 KAPSZTON ROZDZIALU "_30_spring_messaging_and_async"
         * ============================================================
         * Ten kapszton LACZY WSZYSTKIE mechanizmy poznane W tym
         * rozdziale W JEDNYM, spojnym systemie "JavaQuest Order
         * Processing":
         * - `@Async` (Lesson01-03) - asynchroniczne wysylanie
         *   powiadomien e-mail, BEZ blokowania glownego przeplywu,
         * - `@Scheduled` (Lesson04) - cykliczny raport statystyk,
         * - `ApplicationEvent`/`@EventListener` (Lesson05) - LUZNE
         *   powiazanie MIEDZY `ZamowieniaService` A modulami
         *   powiadomien/analityki (SYNCHRONICZNY I ASYNCHRONICZNY
         *   sluchacz RAZEM, jak W Lesson05),
         * - RabbitTemplate (Lesson08-09) - PROBA wyslania zdarzenia
         *   TEZ NA zewnetrzny broker (Z fallbackiem, jesli Docker
         *   niedostepny - jak Lesson09).
         *
         * Scenariusz W `main()`: zlozenie 3 zamowien, obserwacja
         * WSZYSTKICH mechanizmow DZIALAJACYCH RAZEM.
         */
        System.out.println("Laczy: @Async (Lesson01-03) + @Scheduled (Lesson04) + Events (Lesson05) + proba AMQP (Lesson08-09, z fallbackiem).");

        try (ConfigurableApplicationContext context = SpringApplication.run(CapstoneApp.class,
                "--server.port=0", "--logging.level.root=WARN",
                "--spring.rabbitmq.host=localhost", "--spring.rabbitmq.port=5672",
                "--spring.rabbitmq.connection-timeout=1000")) {

            ZamowieniaService serwis = context.getBean(ZamowieniaService.class);
            PowiadomieniaListener powiadomienia = context.getBean(PowiadomieniaListener.class);
            AnalitykaListener analityka = context.getBean(AnalitykaListener.class);
            RaportScheduler scheduler = context.getBean(RaportScheduler.class);

            demonstrateFullOrderProcessingWorkflow(serwis, powiadomienia, analityka, scheduler);
            attemptExternalMessagingWithFallback(context);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU "_30_spring_messaging_and_async"
         * ============================================================
         * Rozdzial przeszedl droge OD asynchronicznosci in-process
         * (`@Async`/`@Scheduled`/zdarzenia, Lesson01-05) PRZEZ
         * komunikacje MIEDZY procesami (JMS/AMQP/Kafka, Lesson06-11)
         * DO wzorcow architektonicznych I obslugi bledow (Lesson12-13)
         * I ZAKONCZYL PRAKTYCZNA decyzja (Lesson15): RabbitMQ vs Kafka
         * NIE MAJA "jednej sluszej odpowiedzi".
         *
         * Nastepny rozdzial (`_31_spring_cloud_microservices`) buduje
         * NA tej WIEDZY - mikroserwisy CZESTO komunikuja sie WLASNIE
         * PRZEZ komunikaty (Event-Driven Microservices, powiazanie
         * Z Lesson15 tamtego rozdzialu).
         */
        System.out.println("\n=== KONIEC LEKCJI 16 I ROZDZIALU _30_spring_messaging_and_async ===");
    }

    private static void demonstrateFullOrderProcessingWorkflow(ZamowieniaService serwis, PowiadomieniaListener powiadomienia, AnalitykaListener analityka, RaportScheduler scheduler) throws InterruptedException {
        System.out.println("\n--- Pelny przeplyw: zlozenie 3 zamowien ---");

        serwis.zlozZamowienie("Z1", "Kasia", 100.0);
        serwis.zlozZamowienie("Z2", "Marek", 200.0);
        serwis.zlozZamowienie("Z3", "Ania", 150.0);

        System.out.println("\n--- Oczekiwanie NA @Async powiadomienia I @Scheduled raport ---");
        boolean powiadomieniaNaCzas = powiadomienia.getZatrzask().await(3, TimeUnit.SECONDS);
        Thread.sleep(200); // upewnij sie, ze @Scheduled zdazyl WYGENEROWAC raport

        System.out.println("\n--- Stan koncowy ---");
        System.out.println("Wyslane e-maile (@Async): " + powiadomienia.getWyslaneEmaile().size());
        System.out.println("Statystyki (SYNC @EventListener): " + analityka.licznikZamowien.get() + " zamowien, suma=" + analityka.sumaKwot.get() + " PLN");
        System.out.println("Ostatni raport (@Scheduled): " + scheduler.getOstatniRaport());

        assertThat(powiadomieniaNaCzas).isTrue();
        assertThat(powiadomienia.getWyslaneEmaile()).hasSize(3);
        assertThat(analityka.licznikZamowien.get()).isEqualTo(3);
        assertThat(analityka.sumaKwot.get()).isEqualTo(450);
        assertThat(scheduler.getOstatniRaport()).isNotEmpty();
    }

    private static void attemptExternalMessagingWithFallback(ConfigurableApplicationContext context) {
        System.out.println("\n--- Proba wyslania zdarzenia TEZ NA zewnetrzny broker RabbitMQ (Lesson09) ---");
        try {
            RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
            rabbitTemplate.convertAndSend("zamowienia.exchange.capstone", "zamowienie.utworzone", "Zamowienie Z1 (kapszton)");
            System.out.println("Wyslano NA broker RabbitMQ.");
        } catch (Exception e) {
            System.out.println("Broker RabbitMQ NIEDOSTEPNY (Docker niedostepny NA tej maszynie) - " + e.getClass().getSimpleName());
            System.out.println("W PRODUKCJI (Z dzialajacym brokerem) TA SAMA linia wyslalaby zdarzenie NA zewnetrzny system (np. analityke W INNYM mikroserwisie).");
        }
    }
}
