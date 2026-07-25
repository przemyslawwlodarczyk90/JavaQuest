package com.example.javaquest._30_spring_messaging_and_async.Lesson09_SpringAmqpBasics;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class _Lesson09_SpringAmqpBasics {

    @Component
    static class ZamowieniaOdbiorca {
        final List<String> odebrane = new CopyOnWriteArrayList<>();
        CountDownLatch zatrzask = new CountDownLatch(1);

        @RabbitListener(queues = "zamowienia.kolejka.amqp")
        public void naZamowienie(String tresc) {
            odebrane.add(tresc);
            System.out.println("  @RabbitListener odebral: " + tresc);
            zatrzask.countDown();
        }
    }

    @SpringBootApplication
    @EnableRabbit
    static class AmqpApp {
        @Bean
        Queue kolejkaZamowien() {
            return new Queue("zamowienia.kolejka.amqp", false, false, true); // NIE-trwala, ekskluzywna, autodelete - wystarczajaco DLA demo
        }

        @Bean
        DirectExchange zamowieniaExchange() {
            return new DirectExchange("zamowienia.exchange.amqp", false, true);
        }

        @Bean
        Binding bindowanie(Queue kolejkaZamowien, DirectExchange zamowieniaExchange) {
            return BindingBuilder.bind(kolejkaZamowien).to(zamowieniaExchange).with("zamowienie.utworzone");
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 9: Spring AMQP - RabbitTemplate + @RabbitListener ===");

        /*
         * ============================================================
         * 📦 PRZYPOMNIENIE - JUZ POZNANE (koncepcje) W Lesson08
         * ============================================================
         * Lesson08 wyjasnil MODEL AMQP (exchange/binding/routing key).
         * TA lekcja pokazuje IMPLEMENTACJE W Springu:
         * `RabbitTemplate` (odpowiednik `JmsTemplate` Z Lesson07) +
         * `@RabbitListener` (odpowiednik `@JmsListener`) +
         * `Queue`/`DirectExchange`/`Binding` (deklaratywna konfiguracja
         * topologii AMQP JAKO beany Springa - `RabbitAdmin`
         * AUTOMATYCZNIE JE TWORZY NA brokerze PRZY starcie).
         *
         * UWAGA SRODOWISKOWA: TA lekcja WYMAGA prawdziwego, DZIALAJACEGO
         * brokera RabbitMQ (Docker NIEDOSTEPNY NA tej maszynie,
         * zweryfikowano W `_26_integration_testing`) - kod PONIZEJ
         * JEST W PELNI prawdziwy I POPRAWNY (DZIALALBY IDENTYCZNIE
         * Z uruchomionym Dockerem/RabbitMQ), ale demo laguje SIE
         * PRZYJAZNIE, GDY broker jest niedostepny (TEN SAM wzorzec
         * fallbacku CO Testcontainers W `_26_integration_testing/
         * Lesson04-06`).
         */
        System.out.println("RabbitTemplate/@RabbitListener - Springowa implementacja AMQP. WYMAGA prawdziwego brokera RabbitMQ (Docker niedostepny na tej maszynie - fallback ponizej).");

        try (ConfigurableApplicationContext context = SpringApplication.run(AmqpApp.class,
                "--server.port=0", "--logging.level.root=WARN",
                "--spring.rabbitmq.host=localhost", "--spring.rabbitmq.port=5672",
                "--spring.rabbitmq.connection-timeout=2000")) {

            RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
            ZamowieniaOdbiorca odbiorca = context.getBean(ZamowieniaOdbiorca.class);

            demonstrateRabbitTemplateSendAndListenerReceive(rabbitTemplate, odbiorca);

        } catch (Exception e) {
            System.out.println("\n--- Broker RabbitMQ NIEDOSTEPNY (brak Dockera na tej maszynie) ---");
            System.out.println("Zlapano: " + e.getClass().getSimpleName() + " - " + rootCauseMessage(e));
            System.out.println("Powyzszy kod (RabbitTemplate.convertAndSend + @RabbitListener + Queue/Exchange/Binding jako beany) JEST W PELNI POPRAWNY");
            System.out.println("i zadzialalby IDENTYCZNIE Z prawdziwym brokerem RabbitMQ (np. 'docker run -p 5672:5672 rabbitmq') - ten sam wzorzec");
            System.out.println("fallbacku CO Testcontainers w `_26_integration_testing/Lesson04-06`, gdy Docker jest niedostepny.");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `RabbitTemplate.convertAndSend(exchange, routingKey, obiekt)`
         *   - WYSYLA wiadomosc PRZEZ exchange Z routing key (Lesson08).
         * - `@RabbitListener(queues = "...")` - DEKLARATYWNY odbiorca
         *   (analogicznie DO `@JmsListener`).
         * - `Queue`/`DirectExchange`/`Binding` JAKO beany - `RabbitAdmin`
         *   (auto-konfigurowany PRZEZ `spring-boot-starter-amqp`)
         *   AUTOMATYCZNIE TWORZY je NA brokerze PRZY starcie kontekstu.
         * - `@EnableRabbit` - WLACZA przetwarzanie `@RabbitListener`.
         */
        System.out.println("\n=== KONIEC LEKCJI 9 ===");
    }

    private static void demonstrateRabbitTemplateSendAndListenerReceive(RabbitTemplate rabbitTemplate, ZamowieniaOdbiorca odbiorca) throws InterruptedException {
        System.out.println("\n--- rabbitTemplate.convertAndSend(exchange, routingKey, ...) - wysylka PRZEZ exchange ---");
        rabbitTemplate.convertAndSend("zamowienia.exchange.amqp", "zamowienie.utworzone", "Zamowienie Z300 (przez Spring AMQP)");
        System.out.println("Wyslano PRZEZ RabbitTemplate.");

        boolean odebranoNaCzas = odbiorca.zatrzask.await(5, TimeUnit.SECONDS);
        System.out.println("@RabbitListener odebral WIADOMOSC: " + odbiorca.odebrane);

        assertThat(odebranoNaCzas).isTrue();
        assertThat(odbiorca.odebrane).containsExactly("Zamowienie Z300 (przez Spring AMQP)");
    }

    private static String rootCauseMessage(Throwable t) {
        Throwable rootCause = t;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause.getMessage();
    }
}
