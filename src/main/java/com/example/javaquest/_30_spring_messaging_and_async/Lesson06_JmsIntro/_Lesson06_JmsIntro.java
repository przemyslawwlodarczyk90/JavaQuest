package com.example.javaquest._30_spring_messaging_and_async.Lesson06_JmsIntro;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson06_JmsIntro {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 6: JMS (Jakarta Message Service) - podstawy, Queue vs Topic ===");

        /*
         * ============================================================
         * 📦 NOWY MATERIAL - JMS
         * ============================================================
         * JMS (Java/Jakarta Message Service, 1.0 od 2001) TO STANDARDOWE
         * API DO komunikacji asynchronicznej PRZEZ KOLEJKI/tematy
         * wiadomosci - dokladny odpowiednik Reactive Streams (`_29_
         * spring_reactive/Lesson02`) DLA swiata "messaging": SPECYFIKACJA,
         * NIE implementacja - ActiveMQ/Artemis/IBM MQ/RabbitMQ (przez
         * wtyczke) SA RÓZNYMI implementacjami TEGO SAMEGO API.
         *
         * TA lekcja EMBEDUJE PRAWDZIWY broker ActiveMQ "Classic"
         * (`BrokerService`, transport `vm://` - W PAMIECI, BEZ
         * zewnetrznego procesu/Dockera) - ten sam duch "embeduj I
         * NAPRAWDE uruchom" co embedded Tomcat W `_07_servlets`.
         * UWAGA techniczna: ActiveMQ Classic 5.19.x (broker+klient) jest
         * W CALOSCI oparty NA STARYM `javax.jms` (NIE `jakarta.jms`) -
         * swiadomie uzyty TU W surowej postaci (BEZ Springa), zeby
         * NIE mieszac namespace'ow Z Artemis (`jakarta.jms`-natywny),
         * ktorego Lesson07 uzyje RAZEM ze Spring `JmsTemplate`
         * (WYMAGAJACYM jakarta.jms) - powiazanie Z historia migracji
         * `javax`->`jakarta` omowiona W `_20_spring_core/Lesson02`.
         *
         * 2 MODELE komunikacji:
         * - QUEUE (kolejka, point-to-point) - KAZDA wiadomosc
         *   ODEBRANA PRZEZ DOKLADNIE 1 konsumenta (jak "bilet W
         *   kolejce" - KTOKOLWIEK go weznie PIERWSZY, INNI go NIE
         *   dostana).
         * - TOPIC (temat, publish-subscribe) - KAZDA wiadomosc
         *   ODEBRANA PRZEZ WSZYSTKICH aktywnych subskrybentow
         *   (analogicznie DO `ApplicationEventPublisher`, Lesson05,
         *   ale MIEDZY PROCESAMI/aplikacjami, NIE wewnatrz 1 JVM).
         */
        System.out.println("JMS - STANDARDOWE API DLA messagingu (jak Reactive Streams DLA reaktywnosci). Queue (1 konsument) vs Topic (WSZYSCY subskrybenci).");

        BrokerService broker = uruchomEmbeddedBroker();
        try {
            demonstrateQueuePointToPoint();
            demonstrateTopicPublishSubscribe();
        } finally {
            broker.stop();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `ConnectionFactory` -> `Connection` -> `Session` ->
         *   `MessageProducer`/`MessageConsumer` - HIERARCHIA obiektow
         *   JMS (podobna DO JDBC: `DataSource` -> `Connection` ->
         *   `Statement`).
         * - `Queue` (point-to-point) - 1 wiadomosc = 1 konsument.
         * - `Topic` (publish-subscribe) - 1 wiadomosc = WSZYSCY
         *   AKTYWNI subskrybenci.
         * - `TextMessage`/`ObjectMessage`/`BytesMessage` - typy
         *   wiadomosci JMS.
         * - Surowe API JMS jest WERBALNE (duzo boilerplate'u -
         *   try/catch NA KAZDYM kroku, JMSException) - Lesson07
         *   pokaze `JmsTemplate` Springa, KTORY to UPRASZCZA
         *   (analogicznie DO `JdbcTemplate` Z `_09_jdbc`).
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static BrokerService uruchomEmbeddedBroker() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("lesson06-embedded-broker");
        broker.setPersistent(false); // W PAMIECI, bez zapisu na dysk - wystarczajace DO demo
        broker.setUseJmx(false);
        broker.addConnector("vm://lesson06-embedded-broker");
        broker.start();
        System.out.println("Embedded ActiveMQ Broker URUCHOMIONY (transport vm://, BEZ Dockera/zewnetrznego procesu).");
        return broker;
    }

    private static void demonstrateQueuePointToPoint() throws JMSException {
        System.out.println("\n--- Queue (point-to-point) - wiadomosc ODEBRANA przez DOKLADNIE 1 konsumenta ---");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://lesson06-embedded-broker");

        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue kolejka = session.createQueue("zamowienia.kolejka");

            MessageProducer producer = session.createProducer(kolejka);
            TextMessage wiadomosc = session.createTextMessage("Zamowienie Z100 zlozone");
            producer.send(wiadomosc);
            System.out.println("Wyslano wiadomosc DO kolejki 'zamowienia.kolejka'.");

            MessageConsumer consumer = session.createConsumer(kolejka);
            TextMessage odebrana = (TextMessage) consumer.receive(2000);

            System.out.println("Konsument ODEBRAL: " + odebrana.getText());
            assertThat(odebrana.getText()).isEqualTo("Zamowienie Z100 zlozone");
        }
    }

    private static void demonstrateTopicPublishSubscribe() throws JMSException, InterruptedException {
        System.out.println("\n--- Topic (publish-subscribe) - wiadomosc ODEBRANA przez WSZYSTKICH subskrybentow ---");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://lesson06-embedded-broker");

        try (Connection connection = connectionFactory.createConnection()) {
            connection.setClientID("lesson06-klient");
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic temat = session.createTopic("powiadomienia.temat");

            // OBAJ subskrybenci MUSZA byc zarejestrowani PRZED wyslaniem wiadomosci (Topic NIE buforuje
            // dla subskrybentow, ktorzy NIE SLUCHALI W momencie wyslania - w odroznieniu OD Queue).
            MessageConsumer subskrybent1 = session.createConsumer(temat);
            MessageConsumer subskrybent2 = session.createConsumer(temat);

            MessageProducer producer = session.createProducer(temat);
            producer.send(session.createTextMessage("Nowa promocja: -20% na wszystko!"));
            System.out.println("Opublikowano wiadomosc W temacie 'powiadomienia.temat' (2 aktywni subskrybenci).");

            TextMessage odebrana1 = (TextMessage) subskrybent1.receive(2000);
            TextMessage odebrana2 = (TextMessage) subskrybent2.receive(2000);

            System.out.println("Subskrybent 1 ODEBRAL: " + odebrana1.getText());
            System.out.println("Subskrybent 2 ODEBRAL: " + odebrana2.getText());

            assertThat(odebrana1.getText()).isEqualTo("Nowa promocja: -20% na wszystko!");
            assertThat(odebrana2.getText()).isEqualTo(odebrana1.getText());
            System.out.println("OBAJ subskrybenci odebrali TA SAMA wiadomosc - w odroznieniu OD Queue, gdzie TYLKO 1 konsument by ja dostal.");
        }
    }
}
