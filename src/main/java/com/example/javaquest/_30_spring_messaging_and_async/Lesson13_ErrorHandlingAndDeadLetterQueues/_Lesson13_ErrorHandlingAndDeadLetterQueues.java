package com.example.javaquest._30_spring_messaging_and_async.Lesson13_ErrorHandlingAndDeadLetterQueues;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson13_ErrorHandlingAndDeadLetterQueues {

    record Zadanie(String id, String tresc, boolean symulujBlad) {
    }

    // Symulacja BROKERA Z RETRY + DLQ - W REALNYM RabbitMQ/Kafka TA logika jest
    // KONFIGUROWANA (dead-letter-exchange, retry topics), NIE pisana recznie - TU
    // UPROSZCZONE DO klas Javy, zeby pokazac SAM WZORZEC.
    static class BrokerZRetryIDlq {
        private static final int MAX_PROB = 3;
        private final Map<String, Integer> licznikProb = new ConcurrentHashMap<>();
        private final List<Zadanie> dlq = new CopyOnWriteArrayList<>();
        private final List<Zadanie> przetworzone = new CopyOnWriteArrayList<>();

        void przetworz(Zadanie zadanie, java.util.function.Consumer<Zadanie> handler) {
            int proba = licznikProb.merge(zadanie.id(), 1, Integer::sum);
            try {
                if (zadanie.symulujBlad() && proba < MAX_PROB) {
                    throw new RuntimeException("Symulowany blad przetwarzania (proba " + proba + "/" + MAX_PROB + ")");
                }
                handler.accept(zadanie);
                przetworzone.add(zadanie);
                System.out.println("  Zadanie " + zadanie.id() + " PRZETWORZONE POMYSLNIE (proba " + proba + ").");
            } catch (RuntimeException e) {
                System.out.println("  Zadanie " + zadanie.id() + " NIEUDANE (proba " + proba + "/" + MAX_PROB + "): " + e.getMessage());
                if (proba >= MAX_PROB) {
                    dlq.add(zadanie);
                    System.out.println("  Zadanie " + zadanie.id() + " WYCZERPALO proby - PRZENIESIONE DO Dead Letter Queue.");
                } else {
                    System.out.println("  Zadanie " + zadanie.id() + " ZOSTANIE PONOWIONE.");
                    przetworz(zadanie, handler); // symulacja RETRY (W REALNYM brokerze: NOWA proba dostarczenia, NIE rekurencja)
                }
            }
        }

        List<Zadanie> getDlq() {
            return dlq;
        }

        List<Zadanie> getPrzetworzone() {
            return przetworzone;
        }
    }

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: Obsluga bledow i Dead Letter Queue - odporne przetwarzanie wiadomosci ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - retry + DLQ
         * ============================================================
         * CO SIE DZIEJE, GDY przetwarzanie wiadomosci ZAWIEDZIE?
         * (np. baza danych chwilowo niedostepna, blad walidacji).
         * KAZDY broker (RabbitMQ, Kafka, JMS) MA WLASNY mechanizm
         * PONAWIANIA (retry) I "MARTWYCH LISTOW" (Dead Letter Queue/
         * Topic) - miejsce, GDZIE TRAFIAJA wiadomosci, KTORE
         * WYCZERPALY WSZYSTKIE proby.
         *
         * BEZ tego mechanizmu: (1) wiadomosc MOZE byc UTRACONA
         * (BLAD -> "acknowledge" -> wiadomosc znika NA ZAWSZE), LUB
         * (2) wiadomosc BLOKUJE CALA kolejke (BLAD -> BRAK acknowledge
         *     -> wiadomosc WRACA NA POCZATEK -> PONOWNY blad -> PETLA
         *     W NIESKONCZONOSC).
         *
         * DLQ ROZWIAZUJE TO: PO N nieudanych probach, wiadomosc
         * TRAFIA DO OSOBNEJ kolejki/topicu ("dead letter") -
         * PRZETWARZANIE GLOWNEJ kolejki NIE JEST zablokowane,
         * a WIADOMOSC NIE GINIE (MOZNA JA PRZEANALIZOWAC/naprawic
         * recznie POZNIEJ).
         */
        System.out.println("Retry + Dead Letter Queue - CO ROBIC, GDY przetwarzanie wiadomosci ZAWIEDZIE? Bez tego: UTRATA wiadomosci LUB zablokowana kolejka.");

        demonstrateRetryEventuallySucceeds();
        demonstrateExhaustedRetriesGoToDlq();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Retry Z LIMITEM prob - PONOW przetwarzanie N razy PRZED
         *   poddaniem sie.
         * - Dead Letter Queue/Topic - "poczekalnia" DLA wiadomosci,
         *   KTORE WYCZERPALY WSZYSTKIE proby - NIE GINA, MOZNA JE
         *   PRZEANALIZOWAC.
         * - RabbitMQ: `x-dead-letter-exchange`/`x-dead-letter-routing-key`
         *   NA kolejce (Lesson09) - broker ROBI to AUTOMATYCZNIE.
         * - Kafka: "dead letter topic" (KONWENCJA, NIE wbudowany
         *   mechanizm) - Spring Kafka DAJE `DeadLetterPublishingRecoverer`.
         * - Powiazanie Z `_29_spring_reactive/Lesson07` (retry
         *   reaktywny) - TA SAMA idea, INNY kontekst (streaming
         *   reaktywny vs messaging asynchroniczny).
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static void demonstrateRetryEventuallySucceeds() {
        System.out.println("\n--- Retry - zadanie OSTATECZNIE SIE UDAJE PRZED wyczerpaniem prob ---");
        BrokerZRetryIDlq broker = new BrokerZRetryIDlq();

        Zadanie zadanie = new Zadanie("Z1", "Wyslij email", true); // symulujBlad=true, ale MAX_PROB=3 wystarczy (blad TYLKO PRZY proba<3)
        broker.przetworz(zadanie, z -> System.out.println("    (Rzeczywiste przetwarzanie: " + z.tresc() + ")"));

        assertThat(broker.getPrzetworzone()).hasSize(1);
        assertThat(broker.getDlq()).isEmpty();
        System.out.println("Zadanie OSTATECZNIE przetworzone POMYSLNIE (PRZY 3. probie) - DLQ POZOSTAJE PUSTA.");
    }

    private static void demonstrateExhaustedRetriesGoToDlq() {
        System.out.println("\n--- Wyczerpane proby - zadanie TRAFIA DO Dead Letter Queue ---");
        BrokerZRetryIDlq broker = new BrokerZRetryIDlq();

        // Handler, ktory ZAWSZE rzuca wyjatek - symuluje TRWALY blad (np. NIEPOPRAWNE dane).
        Zadanie zadanieZTrwalymBledem = new Zadanie("Z2", "Nieprawidlowe dane", true);
        broker.przetworz(zadanieZTrwalymBledem, z -> {
            throw new RuntimeException("TRWALY blad - dane SA NIEPOPRAWNE, retry NIC NIE ZMIENI");
        });

        System.out.println("Stan DLQ PO wyczerpaniu prob: " + broker.getDlq());
        assertThat(broker.getDlq()).hasSize(1);
        assertThat(broker.getDlq().get(0).id()).isEqualTo("Z2");
        System.out.println("Zadanie Z2 TRAFILO DO DLQ PO 3 nieudanych probach - MOZNA JE teraz PRZEANALIZOWAC/naprawic RECZNIE, BEZ blokowania glownej kolejki.");
    }
}
