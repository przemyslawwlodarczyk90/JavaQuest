package com.example.javaquest._30_spring_messaging_and_async.Lesson15_ChoosingRabbitVsKafka;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson15_ChoosingRabbitVsKafka {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 15: RabbitMQ vs Kafka - decyzja architektoniczna ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - PODSUMOWANIE architektoniczne (Lesson08-11)
         * ============================================================
         * PO poznaniu OBU technologii (RabbitMQ: Lesson08-09, Kafka:
         * Lesson10-11) NALEZY zadac PRAKTYCZNE pytanie: "KTORA
         * WYBRAC?". Powiazanie Z `_29_spring_reactive/Lesson16` -
         * TA SAMA idea PODSUMOWANIA architektonicznego PRZED
         * kapsztonem.
         */
        System.out.println("Po poznaniu RabbitMQ (Lesson08-09) i Kafki (Lesson10-11): KTORA technologie wybrac DLA KONKRETNEGO projektu?");

        explainRabbitMqStrengths();
        explainKafkaStrengths();
        demonstrateDecisionMatrix();
        explainCanUseBoth();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU (przed kapsztonem, Lesson16)
         * ============================================================
         * - RabbitMQ: ELASTYCZNY routing (exchange types, Lesson08),
         *   NIZSZA latencja DLA POJEDYNCZYCH wiadomosci, DOJRZALE
         *   wsparcie DLA klasycznych wzorcow "kolejka zadan".
         * - Kafka: WYSOKA przepustowosc, TRWALY log (replay,
         *   Lesson10), NATYWNE wsparcie DLA streaming
         *   analytics/event sourcing.
         * - NIE MA "jednej sluszej odpowiedzi" - WIELE FIRM uzywa
         *   OBU naraz (RabbitMQ DLA "poleceń"/zadan, Kafka DLA
         *   "zdarzen"/strumienia analityki).
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static void explainRabbitMqStrengths() {
        System.out.println("\n--- Mocne strony RabbitMQ ---");
        System.out.println("1. ELASTYCZNY routing (4 typy exchange, Lesson08) - LATWE modelowanie ZLOZONYCH regul dostarczania.");
        System.out.println("2. NISKA latencja DLA POJEDYNCZYCH, KROTKOTRWALYCH wiadomosci (kolejka zadan, RPC-podobne wzorce).");
        System.out.println("3. DOJRZALE wsparcie DLA priorytetow wiadomosci, TTL, dead-letter (Lesson13) 'z pudelka'.");
        System.out.println("4. LATWIEJSZY start DLA MALYCH/SREDNICH projektow (mniej koncepcji DO zrozumienia NIZ Kafka).");
    }

    private static void explainKafkaStrengths() {
        System.out.println("\n--- Mocne strony Kafki ---");
        System.out.println("1. OGROMNA przepustowosc (MILIONY wiadomosci/s) - NATYWNIE zaprojektowana DO SKALI.");
        System.out.println("2. TRWALY log Z RETENCJA (Lesson10) - MOZLIWOSC 'REPLAY' historii (debugowanie, reprocessing, NOWI konsumenci CZYTAJACY OD POCZATKU).");
        System.out.println("3. NATYWNE wsparcie DLA stream processing (Kafka Streams) - AGREGACJE/transformacje W CZASIE RZECZYWISTYM.");
        System.out.println("4. STANDARD DE FACTO DLA event sourcing/CQRS W duzej skali (log JAKO ZRODLO prawdy).");
    }

    private static void demonstrateDecisionMatrix() {
        System.out.println("\n--- Macierz decyzyjna ---");
        Map<String, String> macierz = new LinkedHashMap<>();
        macierz.put("Kolejka zadan (task queue) - przetworz KAZDE zadanie RAZ", "RabbitMQ - elastyczny routing, dojrzale DLQ/TTL/priorytety.");
        macierz.put("Strumien zdarzen DO analityki W czasie rzeczywistym", "Kafka - TRWALY log, natywny stream processing.");
        macierz.put("Event sourcing / CQRS - zdarzenia JAKO ZRODLO prawdy", "Kafka - REPLAY historii, retencja skonfigurowana NA 'zawsze'.");
        macierz.put("RPC-podobna komunikacja MIEDZY mikroserwisami (niska latencja)", "RabbitMQ - PROSTSZY model, NIZSZA latencja DLA POJEDYNCZYCH zadan.");
        macierz.put("MALY/sredni projekt, zespol BEZ doswiadczenia W messagingu", "RabbitMQ - MNIEJ koncepcji DO nauki NIZ partycje/consumer groups Kafki.");
        macierz.put("Bardzo WYSOKA skala (miliony zdarzen/s), WIELE zespolow czytajacych TE SAME dane", "Kafka - NATYWNIE zaprojektowana DO tej skali.");

        for (Map.Entry<String, String> wpis : macierz.entrySet()) {
            System.out.println("Scenariusz: " + wpis.getKey());
            System.out.println("  -> Rekomendacja: " + wpis.getValue());
        }

        assertThat(macierz).hasSize(6);
    }

    private static void explainCanUseBoth() {
        System.out.println("\n--- Mozna uzywac OBU naraz ---");
        System.out.println("Typowy wzorzec W duzych systemach: RabbitMQ DLA 'polecen' (commands - 'wykonaj TO konkretne zadanie', np. 'wyslij email'),");
        System.out.println("Kafka DLA 'zdarzen' (events - 'to sie WYDARZYLO', np. 'zamowienie utworzone') KONSUMOWANYCH PRZEZ WIELE niezaleznych systemow (analityka, audyt, integracje).");
        System.out.println("NIE MA potrzeby wybierac 'JEDNEJ technologii NA ZAWSZE' - RÓZNE czesci systemu MOGA MIEC RÓZNE wymagania.");
    }
}
