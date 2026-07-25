package com.example.javaquest._30_spring_messaging_and_async.Lesson12_MessageDrivenArchitecturePatterns;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson12_MessageDrivenArchitecturePatterns {

    sealed interface ZdarzenieZamowienia permits ZamowienieUtworzone, ZamowienieAnulowane {
    }

    record ZamowienieUtworzone(String id, double kwota) implements ZdarzenieZamowienia {
    }

    record ZamowienieAnulowane(String id, String powod) implements ZdarzenieZamowienia {
    }

    // Symulacja BROKERA in-memory - W REALNYM systemie TO BYLBY RabbitMQ/Kafka (Lesson08-11),
    // TU celowo UPROSZCZONE DO List<Consumer>, ZEBY skupic sie NA SAMYM WZORCU, nie mechanice.
    static class SymulowanyBroker {
        private final Map<Class<?>, List<Consumer<Object>>> subskrybenci = new java.util.HashMap<>();

        <T> void subskrybuj(Class<T> typ, Consumer<T> obsluga) {
            subskrybenci.computeIfAbsent(typ, k -> new CopyOnWriteArrayList<>()).add((Consumer<Object>) obsluga);
        }

        void opublikuj(Object zdarzenie) {
            subskrybenci.getOrDefault(zdarzenie.getClass(), List.of()).forEach(s -> s.accept(zdarzenie));
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 12: Wzorce architektury sterowanej wiadomosciami ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - wzorce, NIE mechanika
         * ============================================================
         * Lesson06-11 uczyly MECHANIKI (JMS/AMQP/Kafka - JAK wyslac/
         * odebrac wiadomosc). TA lekcja uczy WZORCOW - JAK
         * ORGANIZOWAC komunikacje MIEDZY komponentami/serwisami
         * uzywajac tej mechaniki. BEZPOSREDNIE NAWIAZANIE DO
         * `_17_architecture/Lesson18_EventDrivenCommunicationBetweenModules`
         * - TAM: RECZNY publisher/listener CZYSTA Java, W JEDNYM
         *   procesie (monolit modularny).
         * TU: TE SAME WZORCE (Publish-Subscribe, Point-to-Point,
         *   Competing Consumers), ale MIEDZY PROCESAMI/mikroserwisami,
         *   PRZEZ prawdziwy broker (RabbitMQ/Kafka).
         */
        System.out.println("Wzorce komunikacji: Publish-Subscribe, Point-to-Point, Competing Consumers. Powiazanie: _17_architecture/Lesson18 (in-process) -> TU (miedzy procesami).");

        demonstratePublishSubscribePattern();
        demonstrateCompetingConsumersPattern();
        explainChoreographyVsOrchestration();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Publish-Subscribe (Fanout/Topic exchange, Kafka Topic +
         *   WIELE grup konsumentow) - WIELU niezaleznych ODBIORCOW
         *   TEGO SAMEGO zdarzenia.
         * - Point-to-Point (Queue, Direct exchange) - 1 zadanie = 1
         *   przetworzenie (rozdzielenie obciazenia).
         * - Competing Consumers - WIELU konsumentow NA TEJ SAMEJ
         *   kolejce/grupie - KONKURUJA O wiadomosci (rownowazenie
         *   obciazenia).
         * - Choreography (KAZDY serwis reaguje NA zdarzenia
         *   NIEZALEZNIE) vs Orchestration (CENTRALNY "dyrygent"
         *   koordynuje kroki) - Lesson14 (Saga) POGLEBI TEN temat.
         */
        System.out.println("\n=== KONIEC LEKCJI 12 ===");
    }

    private static void demonstratePublishSubscribePattern() {
        System.out.println("\n--- Publish-Subscribe - WIELU niezaleznych odbiorcow TEGO SAMEGO zdarzenia ---");

        SymulowanyBroker broker = new SymulowanyBroker();
        List<String> logEmail = new CopyOnWriteArrayList<>();
        List<String> logMagazyn = new CopyOnWriteArrayList<>();
        List<String> logAnalityka = new CopyOnWriteArrayList<>();

        broker.subskrybuj(ZamowienieUtworzone.class, z -> logEmail.add("Email: potwierdzenie DLA " + z.id()));
        broker.subskrybuj(ZamowienieUtworzone.class, z -> logMagazyn.add("Magazyn: rezerwacja DLA " + z.id()));
        broker.subskrybuj(ZamowienieUtworzone.class, z -> logAnalityka.add("Analityka: zapis zdarzenia " + z.id()));

        broker.opublikuj(new ZamowienieUtworzone("Z500", 150.0));

        System.out.println("3 NIEZALEZNE moduly (Email/Magazyn/Analityka) ODEBRALY TO SAMO zdarzenie: " + logEmail + ", " + logMagazyn + ", " + logAnalityka);
        System.out.println("W PRAKTYCE (RabbitMQ/Kafka): KAZDY modul MA WLASNA kolejke/grupe konsumentow ZBINDOWANA DO TEGO SAMEGO exchange/topicu.");

        assertThat(logEmail).hasSize(1);
        assertThat(logMagazyn).hasSize(1);
        assertThat(logAnalityka).hasSize(1);
    }

    private static void demonstrateCompetingConsumersPattern() {
        System.out.println("\n--- Competing Consumers - WIELU konsumentow KONKURUJE O te SAME zadania ---");

        java.util.concurrent.atomic.AtomicInteger przetworzonePrzezA = new java.util.concurrent.atomic.AtomicInteger();
        java.util.concurrent.atomic.AtomicInteger przetworzonePrzezB = new java.util.concurrent.atomic.AtomicInteger();

        List<String> zadania = List.of("Z1", "Z2", "Z3", "Z4", "Z5", "Z6");
        int i = 0;
        for (String zadanie : zadania) {
            // SYMULACJA round-robin (W PRAWDZIWYM RabbitMQ/Kafka broker/rebalancing ROBI to SAM)
            if (i++ % 2 == 0) {
                przetworzonePrzezA.incrementAndGet();
            } else {
                przetworzonePrzezB.incrementAndGet();
            }
        }

        System.out.println("6 zadan ROZDZIELONYCH MIEDZY 2 konsumentow: Konsument A=" + przetworzonePrzezA.get() + ", Konsument B=" + przetworzonePrzezB.get());
        System.out.println("W RabbitMQ: WIELU konsumentow NA TEJ SAMEJ kolejce (Lesson09). W Kafka: WIELU konsumentow W TEJ SAMEJ grupie (Lesson10-11) - KAZDA partycja CZYTANA przez 1.");

        assertThat(przetworzonePrzezA.get() + przetworzonePrzezB.get()).isEqualTo(6);
    }

    private static void explainChoreographyVsOrchestration() {
        System.out.println("\n--- Choreography vs Orchestration ---");
        System.out.println("CHOREOGRAPHY: KAZDY serwis SUBSKRYBUJE zdarzenia I reaguje NIEZALEZNIE - BRAK centralnego 'dyrygenta' (jak balet, GDZIE KAZDY tancerz ZNA SWOJA role).");
        System.out.println("  Zaleta: LUZNE powiazanie, LATWE dodawanie NOWYCH subskrybentow. Wada: TRUDNO 'zobaczyc' CALY przeplyw W 1 miejscu.");
        System.out.println("ORCHESTRATION: CENTRALNY komponent (orchestrator) JAWNIE wywoluje KAZDY krok W okreslonej kolejnosci (jak dyrygent orkiestry).");
        System.out.println("  Zaleta: CALY przeplyw WIDOCZNY W 1 miejscu, LATWIEJSZA obsluga bledow/kompensacji. Wada: orchestrator STAJE SIE 'punktem centralnym'.");
        System.out.println("Pelne rozwiniecie (wzorzec Saga): Lesson14 tego rozdzialu.");
    }
}
