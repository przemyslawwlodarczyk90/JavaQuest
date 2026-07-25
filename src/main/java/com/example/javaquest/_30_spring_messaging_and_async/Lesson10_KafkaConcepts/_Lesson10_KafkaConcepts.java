package com.example.javaquest._30_spring_messaging_and_async.Lesson10_KafkaConcepts;

public class _Lesson10_KafkaConcepts {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: Apache Kafka - koncepcje (topics/partitions/consumer groups/offset) ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - Kafka: LOG, NIE kolejka
         * ============================================================
         * Kafka (LinkedIn, 2011) TO FUNDAMENTALNIE INNY model NIZ
         * JMS (Lesson06)/AMQP (Lesson08): TO ROZPROSZONY, TRWALY LOG
         * ZDARZEN (append-only), NIE kolejka W tradycyjnym sensie.
         * Wiadomosci NIE SA usuwane PO odebraniu (W ODROZNIENIU OD
         * Queue) - SA przechowywane PRZEZ SKONFIGUROWANY czas
         * (retencja), NIEZALEZNIE OD tego, ILU konsumentow JE
         * PRZECZYTALO.
         *
         * KLUCZOWE pojecia:
         * - TOPIC - nazwana kategoria strumienia zdarzen (jak "temat"
         *   W JMS, ale ZAWSZE TRWALY log, NIE ulotny).
         * - PARTITION - topic jest PODZIELONY NA partycje (ROWNOLEGLOSC
         *   + skalowalnosc) - KAZDA partycja TO NIEZALEZNY, uporzadkowany
         *   log.
         * - OFFSET - POZYCJA W partycji (jak "numer strony W ksiazce")
         *   - konsument PAMIETA WLASNY offset, MOZE "cofnac sie"
         *   I ODCZYTAC PONOWNIE (W ODROZNIENIU OD JMS/AMQP, GDZIE
         *   wiadomosc ZNIKA PO odebraniu).
         * - CONSUMER GROUP - grupa konsumentow DZIELACYCH partycje
         *   MIEDZY soba (KAZDA partycja CZYTANA PRZEZ DOKLADNIE 1
         *   konsumenta W ramach GRUPY - rownowazenie obciazenia).
         */
        System.out.println("Kafka - ROZPROSZONY, TRWALY log zdarzen (NIE kolejka). Topic -> Partitions -> Offset. Consumer Group -> rownowazenie obciazenia.");

        explainTopicsAndPartitions();
        explainOffsetAndRetention();
        explainConsumerGroups();
        compareWithJmsAndAmqp();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Kafka = TRWALY, ROZPROSZONY log (NIE kolejka) - wiadomosci
         *   POZOSTAJA PRZEZ okres retencji, NIEZALEZNIE OD odczytu.
         * - Topic dzieli sie NA Partitions - ZRODLO rownoleglosci
         *   I skalowalnosci.
         * - Offset - POZYCJA konsumenta W partycji - MOZLIWY "REPLAY"
         *   (odtworzenie) historii.
         * - Consumer Group - WIELU konsumentow DZIELI partycje MIEDZY
         *   soba (rownowazenie obciazenia, NIE duplikacja).
         * - Pelna implementacja W Springu (KafkaTemplate/@KafkaListener):
         *   Lesson11.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static void explainTopicsAndPartitions() {
        System.out.println("\n--- Topic i Partition ---");
        System.out.println("Topic 'zamowienia' MOZE miec np. 3 partycje: partition-0, partition-1, partition-2.");
        System.out.println("KAZDA wiadomosc TRAFIA DO 1 partycji (NA PODSTAWIE klucza wiadomosci - TA SAMA wartosc klucza ZAWSZE TRAFIA DO TEJ SAMEJ partycji, ZAPEWNIAJAC kolejnosc DLA danego klucza).");
        System.out.println("WIECEJ partycji = WIEKSZA MOZLIWA rownoleglosc konsumowania (ALE TEZ WIEKSZE zuzycie zasobow brokera).");
    }

    private static void explainOffsetAndRetention() {
        System.out.println("\n--- Offset i Retencja - trwaly log, NIE 'znikajaca' kolejka ---");
        System.out.println("Konsument PAMIETA WLASNY offset (np. 'przeczytalem DO pozycji 42 W partition-0').");
        System.out.println("Wiadomosc POD offsetem 42 NADAL ISTNIEJE W logu (dopoki NIE MINIE czas retencji, np. 7 dni) - INNY konsument (LUB TEN SAM PONOWNIE) MOZE JA odczytac.");
        System.out.println("TO FUNDAMENTALNA roznica WOBEC JMS Queue (Lesson06): TAM wiadomosc ZNIKA PO odebraniu PRZEZ 1 konsumenta - W Kafka MOZNA 'PRZEWINAC' offset I ODTWORZYC historie (np. DO debugowania/reprocessing).");
    }

    private static void explainConsumerGroups() {
        System.out.println("\n--- Consumer Group - rownowazenie obciazenia MIEDZY konsumentami ---");
        System.out.println("Topic 'zamowienia' Z 3 partycjami + Consumer Group 'przetwarzanie-zamowien' Z 3 konsumentami:");
        System.out.println("  Konsument A -> partition-0, Konsument B -> partition-1, Konsument C -> partition-2 (KAZDA partycja CZYTANA PRZEZ DOKLADNIE 1 konsumenta W GRUPIE).");
        System.out.println("Gdyby BYLO WIECEJ konsumentow NIZ partycji (np. 4 konsumentow), 1 konsument BYLBY bezczynny (NIE MA partycji DO przypisania).");
        System.out.println("2. Consumer Group (np. 'raportowanie') MOZE NIEZALEZNIE odczytac TE SAME wiadomosci - KAZDA GRUPA MA WLASNY zestaw offsetow (odpowiednik JMS Topic - WIELU niezaleznych 'subskrybentow').");
    }

    private static void compareWithJmsAndAmqp() {
        System.out.println("\n--- Kafka vs JMS (Lesson06) vs AMQP (Lesson08) ---");
        System.out.println("JMS/AMQP: OPTYMALIZOWANE POD 'dostarcz I zapomnij' (niska latencja, KROTKOTRWALE wiadomosci, brokera NIE INTERESUJE historia).");
        System.out.println("Kafka: OPTYMALIZOWANA POD WYSOKA przepustowosc + TRWALY log (event sourcing, streaming analytics, replay historii) - NATYWNIE skaluje sie DO MILIONOW wiadomosci/s.");
        System.out.println("Wybor: Lesson15 tego rozdzialu POROWNA WSZYSTKIE 3 opcje PRAKTYCZNIE (KIEDY co wybrac).");
    }
}
