package com.example.javaquest._30_spring_messaging_and_async.Lesson08_RabbitMqConcepts;

public class _Lesson08_RabbitMqConcepts {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 8: RabbitMQ - koncepcje AMQP (exchange/queue/binding/routing key) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - AMQP i model RabbitMQ
         * ============================================================
         * AMQP (Advanced Message Queuing Protocol) TO INNA specyfikacja
         * NIZ JMS (Lesson06) - stworzona OD ZERA JAKO PROTOKOL SIECIOWY
         * (NIE tylko API Javy) - DZIEKI temu RabbitMQ MOZE komunikowac
         * sie Z klientami W Pythonie/.NET/Node.js/Ruby ITD. BEZ
         * potrzeby "mostkow" - WSZYSCY mowia TYM SAMYM protokolem NA
         * poziomie SIECI.
         *
         * KLUCZOWA roznica modelu WOBEC JMS: W JMS producent WYSYLA
         * BEZPOSREDNIO DO kolejki/tematu. W AMQP producent WYSYLA
         * DO "EXCHANGE" (WYMIENNIKA) - EXCHANGE DECYDUJE (na podstawie
         * "ROUTING KEY" I typu wymiennika), DO KTORYCH kolejek
         * wiadomosc TRAFI. TO DODATKOWA WARSTWA POSREDNICTWA DAJE
         * OGROMNA elastycznosc routingu.
         *
         * 4 typy Exchange:
         * - DIRECT - wiadomosc TRAFIA DO kolejki, GDZIE routing key
         *   DOKLADNIE PASUJE DO klucza bindowania.
         * - FANOUT - wiadomosc TRAFIA DO WSZYSTKICH powiazanych
         *   kolejek (IGNORUJE routing key) - odpowiednik JMS Topic.
         * - TOPIC - wiadomosc TRAFIA DO kolejek Z WZORCEM PASUJACYM
         *   DO routing key (np. "zamowienia.*.utworzone").
         * - HEADERS - routing NA PODSTAWIE naglowkow (rzadziej uzywany).
         */
        System.out.println("AMQP - PROTOKOL sieciowy (NIE tylko API Javy jak JMS). Model: Producent -> EXCHANGE -> (routing key) -> Kolejki -> Konsumenci.");

        explainExchangeTypes();
        explainBindingAndRoutingKey();
        compareWithJmsFromLesson06();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - AMQP TO PROTOKOL sieciowy (jezykowo NIEZALEZNY), JMS TO
         *   API Javy/Jakarta (SPECYFICZNE DLA JVM).
         * - Producent -> Exchange -> (routing key + binding) ->
         *   Kolejka -> Konsument - DODATKOWA warstwa POSREDNICTWA
         *   WOBEC prostszego modelu JMS (Queue/Topic BEZPOSREDNIO).
         * - Direct/Fanout/Topic/Headers - 4 typy exchange, KAZDY Z
         *   INNA logika routingu.
         * - Pelna implementacja W Springu (RabbitTemplate/
         *   @RabbitListener): Lesson09.
         */
        System.out.println("\n=== KONIEC LEKCJI 8 ===");
    }

    private static void explainExchangeTypes() {
        System.out.println("\n--- 4 typy Exchange w AMQP ---");
        System.out.println("DIRECT  - routing key MUSI DOKLADNIE pasowac DO klucza bindowania kolejki (np. 'zamowienia.nowe' -> TYLKO kolejka zbindowana TYM kluczem).");
        System.out.println("FANOUT  - IGNORUJE routing key, wiadomosc TRAFIA DO WSZYSTKICH zbindowanych kolejek (odpowiednik JMS Topic, Lesson06).");
        System.out.println("TOPIC   - routing key DOPASOWANY WZORCEM (np. 'zamowienia.*.utworzone' pasuje DO 'zamowienia.PL.utworzone' I 'zamowienia.DE.utworzone').");
        System.out.println("HEADERS - routing NA PODSTAWIE naglowkow wiadomosci, NIE routing key (rzadziej uzywany W praktyce).");
    }

    private static void explainBindingAndRoutingKey() {
        System.out.println("\n--- Binding i Routing Key - jak dziala routing W praktyce ---");
        System.out.println("1. Kolejka 'kolejka.email' JEST ZBINDOWANA DO exchange 'zamowienia.exchange' Z routing key 'zamowienie.utworzone'.");
        System.out.println("2. Producent WYSYLA wiadomosc DO exchange 'zamowienia.exchange' Z routing key 'zamowienie.utworzone'.");
        System.out.println("3. Exchange (typu DIRECT) SPRAWDZA WSZYSTKIE swoje bindingi - ZNAJDUJE dopasowanie DO 'kolejka.email' - wiadomosc TAM TRAFIA.");
        System.out.println("4. Gdyby ISTNIALA DRUGA kolejka ZBINDOWANA Z INNYM routing key (np. 'zamowienie.anulowane'), wiadomosc TAM NIE TRAFI.");
    }

    private static void compareWithJmsFromLesson06() {
        System.out.println("\n--- Porownanie z JMS (Lesson06) ---");
        System.out.println("JMS Queue -> AMQP: Exchange (direct) + 1 Queue zbindowana 1 routing key (PODOBNY efekt, ale Z DODATKOWA warstwa posrednictwa).");
        System.out.println("JMS Topic -> AMQP: Exchange (fanout) + WIELE kolejek zbindowanych DO tego SAMEGO exchange (KAZDY subskrybent MA WLASNA kolejke).");
        System.out.println("AMQP topic exchange NIE MA odpowiednika W prostym JMS - TO WZBOGACENIE (wzorce Z gwiazdkami/hashami W routing key).");
        System.out.println("Pelna implementacja W Springu (RabbitTemplate/@RabbitListener/RabbitAdmin): Lesson09.");
    }
}
