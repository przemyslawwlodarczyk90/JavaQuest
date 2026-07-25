package com.example.javaquest._31_spring_cloud_microservices.Lesson15_EventDrivenMicroservicesWithSpringCloudStream;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.function.Consumer;

public class _Lesson15_EventDrivenMicroservicesWithSpringCloudStream {

    @SpringBootApplication
    static class DemoApp {
        @Bean
        Consumer<String> orderConsumer() {
            return msg -> System.out.println("  [orderConsumer] Odebrano PRZEZ RabbitMQ (binder Spring Cloud Stream): " + msg);
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 15: Spring Cloud Stream - event-driven mikroserwisy ===");

        /*
         * ============================================================
         * 📦 ABSTRAKCJA NAD Kafka/RabbitMQ (`_30_spring_messaging_and_async`)
         * ============================================================
         * `_30_spring_messaging_and_async/Lesson08-11` uczyl RECZNEGO
         * uzycia `RabbitTemplate`/`@RabbitListener` I `KafkaTemplate`/
         * `@KafkaListener` - kod ROZNI SIE W ZALEZNOSCI OD brokera.
         * Spring Cloud Stream DODAJE WARSTWE ABSTRAKCJI: PISZESZ
         * ZWYKLE `java.util.function.Function`/`Consumer`/`Supplier`
         * - SAM kod NIE WIE, czy pod spodem jest RabbitMQ czy Kafka
         * ("binder" - implementacja DLA konkretnego brokera, dobierana
         * PRZEZ zaleznosc NA classpath).
         *
         * `spring.cloud.function.definition=orderConsumer` WIAZE bean
         * `Consumer<String>` Z kanalem `orderConsumer-in-0`, KTORY
         * (przez binder RabbitMQ) ODPOWIADA realnej kolejce/exchange.
         */
        System.out.println("Spring Cloud Stream: piszesz Function/Consumer/Supplier - 'binder' (Rabbit/Kafka) TLUMACZY to NA konkretny broker.");

        boolean rabbitDostepny = isRabbitReachable();
        if (!rabbitDostepny) {
            System.out.println("\nRabbitMQ NIEDOSTEPNY NA localhost:5672 (Docker niedostepny NA tej maszynie, jak W");
            System.out.println("`_30_spring_messaging_and_async/Lesson09`) - pokazuje KOD, ktory FAKTYCZNIE wyslalby/odebralby");
            System.out.println("komunikat, Z przyjaznym komunikatem ZAMIAST proby polaczenia (unikamy dlugiego oczekiwania NA timeout).");
            demonstrateWhatWouldHappenWithRabbit();
        } else {
            demonstrateRealMessageFlow();
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `Function<T,R>`/`Consumer<T>`/`Supplier<T>` = model
         *   programowania Spring Cloud Stream - BEZ zaleznosci OD
         *   konkretnego API brokera W kodzie biznesowym.
         * - "Binder" TLUMACZY funkcyjny model NA konkretny broker
         *   (Rabbit/Kafka/Kinesis/...) - PODMIANA brokera = PODMIANA
         *   ZALEZNOSCI Maven, BEZ zmiany kodu.
         * - `StreamBridge` pozwala WYSLAC komunikat PROGRAMOWO (BEZ
         *   dedykowanego beana `Supplier`).
         * - Powiazanie Z `_17_architecture/Lesson18_
         *   EventDrivenCommunicationBetweenModules` - TAM: in-memory
         *   publisher W JEDNYM procesie; TU: TO SAMO, ALE PRZEZ
         *   PRAWDZIWY broker MIEDZY OSOBNYMI procesami/serwisami.
         */
        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }

    private static boolean isRabbitReachable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 5672), 500);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void demonstrateRealMessageFlow() throws InterruptedException {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoApp.class)
                .run(
                        "--spring.application.name=stream-demo",
                        "--server.port=0",
                        "--spring.cloud.function.definition=orderConsumer",
                        "--spring.cloud.stream.bindings.orderConsumer-in-0.destination=orders",
                        "--logging.level.root=WARN");
        try {
            System.out.println("\n--- Wysylanie komunikatu PRZEZ StreamBridge (BEZ dedykowanego Supplier) ---");
            StreamBridge bridge = context.getBean(StreamBridge.class);
            bridge.send("orderConsumer-in-0", "Zamowienie #42");
            Thread.sleep(1000); // krotka chwila NA asynchroniczne dostarczenie komunikatu
        } finally {
            context.close();
        }
    }

    private static void demonstrateWhatWouldHappenWithRabbit() {
        System.out.println("\n--- Kod, KTORY DZIALALBY Z prawdziwym RabbitMQ ---");
        System.out.println("@Bean Consumer<String> orderConsumer() { return msg -> System.out.println(\"Odebrano: \" + msg); }");
        System.out.println("spring.cloud.function.definition=orderConsumer");
        System.out.println("spring.cloud.stream.bindings.orderConsumer-in-0.destination=orders");
        System.out.println("StreamBridge.send(\"orderConsumer-in-0\", \"Zamowienie #42\") -> RabbitMQ -> orderConsumer PRZYJMUJE komunikat.");
    }
}
