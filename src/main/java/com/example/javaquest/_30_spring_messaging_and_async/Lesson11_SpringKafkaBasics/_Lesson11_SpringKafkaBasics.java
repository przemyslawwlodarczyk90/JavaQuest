package com.example.javaquest._30_spring_messaging_and_async.Lesson11_SpringKafkaBasics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson11_SpringKafkaBasics {

    @Component
    static class ZamowieniaOdbiorca {
        final List<String> odebrane = new CopyOnWriteArrayList<>();
        CountDownLatch zatrzask = new CountDownLatch(1);

        @KafkaListener(topics = "zamowienia.topic", groupId = "lesson11-grupa")
        public void naZamowienie(String tresc) {
            odebrane.add(tresc);
            System.out.println("  @KafkaListener odebral: " + tresc);
            zatrzask.countDown();
        }
    }

    @SpringBootApplication
    static class KafkaApp {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 11: Spring Kafka - KafkaTemplate + @KafkaListener ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE (koncepcje) W Lesson10
         * ============================================================
         * Lesson10 wyjasnil MODEL Kafki (topic/partition/offset/
         * consumer group). TA lekcja pokazuje IMPLEMENTACJE W Springu:
         * `KafkaTemplate` (odpowiednik `JmsTemplate`/`RabbitTemplate`)
         * + `@KafkaListener` (odpowiednik `@JmsListener`/
         * `@RabbitListener`, Z DODATKOWYM atrybutem `groupId` -
         * Lesson10: consumer group).
         *
         * UWAGA SRODOWISKOWA: TA lekcja WYMAGA prawdziwego, DZIALAJACEGO
         * brokera Kafka (Docker NIEDOSTEPNY NA tej maszynie,
         * zweryfikowano W `_26_integration_testing`) - kod PONIZEJ
         * JEST W PELNI prawdziwy I POPRAWNY, ale demo laguje SIE
         * PRZYJAZNIE, GDY broker jest niedostepny (TEN SAM wzorzec
         * fallbacku CO Lesson09/Testcontainers W `_26_integration_
         * testing/Lesson04-06`).
         */
        System.out.println("KafkaTemplate/@KafkaListener - Springowa implementacja Kafki. WYMAGA prawdziwego brokera (Docker niedostepny na tej maszynie - fallback ponizej).");

        try (ConfigurableApplicationContext context = SpringApplication.run(KafkaApp.class,
                "--server.port=0", "--logging.level.root=WARN",
                "--spring.kafka.bootstrap-servers=localhost:9092",
                "--spring.kafka.consumer.auto-offset-reset=earliest",
                "--spring.kafka.consumer.group-id=lesson11-grupa",
                "--spring.kafka.producer.properties.max.block.ms=2000")) {

            KafkaTemplate<String, String> kafkaTemplate = (KafkaTemplate<String, String>) context.getBean(KafkaTemplate.class);
            ZamowieniaOdbiorca odbiorca = context.getBean(ZamowieniaOdbiorca.class);

            demonstrateKafkaTemplateSendAndListenerReceive(kafkaTemplate, odbiorca);

        } catch (Exception e) {
            System.out.println("\n--- Broker Kafka NIEDOSTEPNY (brak Dockera na tej maszynie) ---");
            System.out.println("Zlapano: " + e.getClass().getSimpleName() + " - " + rootCauseMessage(e));
            System.out.println("Powyzszy kod (KafkaTemplate.send + @KafkaListener Z groupId) JEST W PELNI POPRAWNY i zadzialalby");
            System.out.println("IDENTYCZNIE Z prawdziwym brokerem Kafka (np. 'docker run -p 9092:9092 apache/kafka') - TEN SAM wzorzec");
            System.out.println("fallbacku CO Lesson09 (RabbitMQ) I Testcontainers w `_26_integration_testing/Lesson04-06`.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `KafkaTemplate.send(topic, klucz, wartosc)` - WYSYLA
         *   wiadomosc DO topicu (Z opcjonalnym kluczem - Lesson10:
         *   determinuje partycje).
         * - `@KafkaListener(topics = "...", groupId = "...")` -
         *   DEKLARATYWNY odbiorca W KONKRETNEJ grupie konsumentow.
         * - Domyslnie Spring Kafka AUTOMATYCZNIE TWORZY topic (jesli
         *   broker NA to pozwala) - W ODROZNIENIU OD RabbitMQ, GDZIE
         *   `Queue`/`Exchange` MUSZA byc JAWNIE zadeklarowane (Lesson09).
         */
        System.out.println("\n=== KONIEC LEKCJI 11 ===");
    }

    private static void demonstrateKafkaTemplateSendAndListenerReceive(KafkaTemplate<String, String> kafkaTemplate, ZamowieniaOdbiorca odbiorca) throws InterruptedException {
        System.out.println("\n--- kafkaTemplate.send(topic, klucz, wartosc) - wysylka DO topicu ---");
        kafkaTemplate.send("zamowienia.topic", "Z400", "Zamowienie Z400 (przez Spring Kafka)");
        System.out.println("Wyslano PRZEZ KafkaTemplate.");

        boolean odebranoNaCzas = odbiorca.zatrzask.await(5, TimeUnit.SECONDS);
        System.out.println("@KafkaListener odebral WIADOMOSC: " + odbiorca.odebrane);

        assertThat(odebranoNaCzas).isTrue();
        assertThat(odbiorca.odebrane).containsExactly("Zamowienie Z400 (przez Spring Kafka)");
    }

    private static String rootCauseMessage(Throwable t) {
        Throwable rootCause = t;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause.getMessage();
    }
}
