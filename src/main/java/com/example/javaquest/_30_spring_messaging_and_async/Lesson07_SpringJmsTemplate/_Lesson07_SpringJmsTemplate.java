package com.example.javaquest._30_spring_messaging_and_async.Lesson07_SpringJmsTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson07_SpringJmsTemplate {

    @Component
    static class OdbiorcaZamowien {
        final List<String> odebrane = new CopyOnWriteArrayList<>();
        CountDownLatch zatrzask = new CountDownLatch(1);

        @JmsListener(destination = "zamowienia.spring")
        public void naZamowienie(String tresc) {
            odebrane.add(tresc);
            System.out.println("  @JmsListener odebral: " + tresc + " NA watku: " + Thread.currentThread().getName());
            zatrzask.countDown();
        }
    }

    @SpringBootApplication
    @EnableJms
    static class JmsTemplateApp {
        // BRAK jawnego @Bean dla OdbiorcaZamowien - JUZ jest @Component, wiec @SpringBootApplication
        // (implicit component-scan) go ZNAJDZIE SAM. BRAK TEZ jawnego ConnectionFactory/JmsTemplate -
        // spring-boot-starter-artemis Z "spring.artemis.mode=embedded" AUTOMATYCZNIE konfiguruje
        // WSZYSTKO (broker+ConnectionFactory+JmsTemplate) - PROBY reczne (TransportConfiguration,
        // NettyAcceptorFactory/InVMAcceptorFactory) dawaly ciche "ActiveMQNotConnectedException"
        // (odkryte empirycznie, PRZYCZYNA NIEJASNA), auto-konfiguracja Spring Boota DZIALA NIEZAWODNIE.
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: Spring JmsTemplate + @JmsListener - upraszczanie surowego JMS ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE (surowe API) W Lesson06
         * ============================================================
         * Lesson06 pokazal SUROWE API JMS (`Connection`/`Session`/
         * `MessageProducer`/`MessageConsumer` - WERBALNE, DUZO
         * boilerplate'u). Spring `JmsTemplate` (`org.springframework.
         * jms.core.JmsTemplate`) UPRASZCZA to DOKLADNIE TAK, JAK
         * `JdbcTemplate` (`_09_jdbc`) upraszcza surowe JDBC - 1 LINIA
         * `jmsTemplate.convertAndSend(...)` ZAMIAST 5-6 linii
         * tworzenia Connection/Session/Producer.
         *
         * `@JmsListener` (analogicznie DO `@KafkaListener` Z Lesson11)
         * TO DEKLARATYWNY sposob nasluchiwania - Spring SAM TWORZY
         * kontener konsumenta W TLE, wywoluje TWOJA metode PRZY
         * KAZDEJ nowej wiadomosci.
         *
         * UWAGA techniczna: TA lekcja uzywa `spring-boot-starter-artemis`
         * Z wlasciwoscia `spring.artemis.mode=embedded` - Spring Boot
         * SAM embeduje I konfiguruje broker Artemis (`jakarta.jms`-
         * natywny, W ODROZNIENIU OD `javax.jms`-owego ActiveMQ Classic
         * Z Lesson06 - Spring `JmsTemplate` WYMAGA `jakarta.jms.
         * ConnectionFactory`). Auto-konfiguracja Boota jest UZYTA
         * ZAMIAST recznego `EmbeddedActiveMQ`/`TransportConfiguration`,
         * bo reczna konfiguracja transportu (in-VM I TCP) dawala
         * ciche bledy polaczenia (odkryte empirycznie PRZY pisaniu
         * tej lekcji) - Boot ROBI to poprawnie ZA nas.
         */
        System.out.println("JmsTemplate/@JmsListener - Springowe uproszczenie surowego JMS (jak JdbcTemplate dla JDBC). Broker: Artemis (spring.artemis.mode=embedded).");

        try (ConfigurableApplicationContext context = SpringApplication.run(JmsTemplateApp.class,
                "--server.port=0", "--logging.level.root=WARN", "--spring.artemis.mode=embedded")) {
            JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
            OdbiorcaZamowien odbiorca = context.getBean(OdbiorcaZamowien.class);

            demonstrateJmsTemplateSendAndListenerReceive(jmsTemplate, odbiorca);
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `JmsTemplate.convertAndSend(destination, obiekt)` - 1
         *   LINIA WYSYLA wiadomosc (Spring SAM konwertuje String/
         *   obiekt NA `Message`).
         * - `@JmsListener(destination = "...")` - DEKLARATYWNY
         *   odbiorca, Spring TWORZY kontener konsumenta W TLE.
         * - `@EnableJms` - WLACZA przetwarzanie `@JmsListener`
         *   (analogicznie DO `@EnableAsync`/`@EnableScheduling`).
         * - `spring.artemis.mode=embedded` - Spring Boot SAM
         *   embeduje I konfiguruje broker (`ConnectionFactory`,
         *   `JmsTemplate`) - ZERO recznej konfiguracji transportu.
         * - Kontrast Z Lesson06: TO SAMO zadanie (wyslij+odbierz),
         *   ZNACZNIE mniej kodu.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    private static void demonstrateJmsTemplateSendAndListenerReceive(JmsTemplate jmsTemplate, OdbiorcaZamowien odbiorca) throws InterruptedException {
        System.out.println("\n--- jmsTemplate.convertAndSend(...) - 1 linia WYSYLA wiadomosc ---");
        jmsTemplate.convertAndSend("zamowienia.spring", "Zamowienie Z200 (przez Spring JmsTemplate)");
        System.out.println("Wyslano PRZEZ JmsTemplate (kontrast Z Lesson06: TAM 5-6 linii Connection/Session/Producer).");

        boolean odebranoNaCzas = odbiorca.zatrzask.await(5, TimeUnit.SECONDS);
        System.out.println("@JmsListener odebral WIADOMOSC W tle: " + odbiorca.odebrane);

        assertThat(odebranoNaCzas).isTrue();
        assertThat(odbiorca.odebrane).containsExactly("Zamowienie Z200 (przez Spring JmsTemplate)");
    }
}
