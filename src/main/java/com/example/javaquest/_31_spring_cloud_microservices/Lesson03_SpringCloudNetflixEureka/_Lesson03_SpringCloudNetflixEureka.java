package com.example.javaquest._31_spring_cloud_microservices.Lesson03_SpringCloudNetflixEureka;

import com.netflix.discovery.shared.Application;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

public class _Lesson03_SpringCloudNetflixEureka {

    @SpringBootApplication
    @EnableEurekaServer
    static class EurekaServerApp {
    }

    @SpringBootApplication
    static class ClientServiceApp {
        @RestController
        static class PingController {
            @GetMapping("/ping")
            String ping() {
                return "pong";
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== LEKCJA 3: Eureka - embedded server + klienci W JEDNYM JVM ===");

        /*
         * ============================================================
         * 📦 EUREKA - implementacja service registry Z Lesson02
         * ============================================================
         * `spring-cloud-starter-netflix-eureka-server` + adnotacja
         * `@EnableEurekaServer` ZAMIENIA zwykla aplikacje Spring Boot
         * W SERWER rejestru. `spring-cloud-starter-netflix-eureka-
         * client` (BEZ zadnej dodatkowej adnotacji - wystarczy starter
         * NA classpath) sprawia, ze KAZDA aplikacja Spring Boot SAMA
         * SIE REJESTRUJE W Eurece PRZY starcie.
         *
         * WAZNE (ten sam wzorzec CO `_19_security_basics/Lesson06`):
         * ten kurs demonstruje TO WSZYSTKO jako WIELE niezaleznych
         * kontekstow Spring W JEDNYM procesie JVM (kazdy NA WLASNYM,
         * losowym porcie) - W PRAWDZIWYM projekcie kazdy Z tych
         * kontekstow bylby OSOBNYM procesem/kontenerem.
         *
         * `eureka.client.enabled=false` jest USTAWIONE GLOBALNIE dla
         * calego tego projektu (`application.properties`) - inaczej
         * KAZDA lekcja W CALYM kursie probowalaby sie zarejestrowac W
         * (nieistniejacej) Eurece przy starcie. TA lekcja jawnie
         * PRZYWRACA `eureka.client.enabled=true` (System.setProperty
         * PRZED `.run()`, `clearProperty` W finally) - dokladnie TEN
         * SAM wzorzec CO `_24_spring_security` dla Spring Security.
         */
        System.out.println("eureka-server: @EnableEurekaServer. eureka-client: SAM starter na classpath rejestruje aplikacje. Oba wymagaja jawnego przywrocenia eureka.client.enabled=true.");

        System.setProperty("eureka.client.enabled", "true");
        ConfigurableApplicationContext serverContext = null;
        ConfigurableApplicationContext ordersContext = null;
        ConfigurableApplicationContext paymentsContext = null;
        try {
            serverContext = startEurekaServer();
            int serverPort = Integer.parseInt(serverContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("Eureka Server wystartowal NA porcie " + serverPort + " (http://localhost:" + serverPort + "/eureka/).");

            ordersContext = startClientService("orders-service", serverPort);
            paymentsContext = startClientService("payments-service", serverPort);

            demonstrateWaitForRegistration(serverContext, "orders-service");
            demonstrateWaitForRegistration(serverContext, "payments-service");
            demonstrateSelfPreservationConcept();
        } finally {
            closeQuietly(paymentsContext);
            closeQuietly(ordersContext);
            closeQuietly(serverContext);
            System.clearProperty("eureka.client.enabled");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EnableEurekaServer` + starter = serwer rejestru.
         * - Starter eureka-client (BEZ dodatkowej adnotacji) = klient
         *   SAM SIE REJESTRUJE (`spring.application.name` = nazwa
         *   pod jaka widoczny W Eurece).
         * - Rejestracja NIE jest NATYCHMIASTOWA - klient POTRZEBUJE
         *   chwili NA pierwsza rejestracje I heartbeat.
         * - `eureka.client.enabled=false` GLOBALNIE W tym projekcie -
         *   KAZDA lekcja demonstrujaca Euereke musi jawnie PRZYWROCIC
         *   ta wlasciwosc (System.setProperty).
         * - Lesson08 pokaze, JAK klient (Spring Cloud LoadBalancer)
         *   UZYWA tego rejestru DO wyboru instancji.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static ConfigurableApplicationContext startEurekaServer() {
        // WAZNE: `SpringApplicationBuilder.properties(...)` ma NIZSZY priorytet niz classpath'owy
        // `application.properties` (ten sam pulapka udokumentowana W _24_spring_security) - bez
        // TEGO globalne `spring.application.name=Java Quest` WYGRYWALOBY, a serwer FAKTYCZNIE
        // probowalby sie zarejestrowac SAM W SOBIE (zweryfikowane empirycznie). Argumenty
        // wiersza polecen (`run(String... args)`, styl "--klucz=wartosc") MAJA NAJWYZSZY
        // priorytet W Springu - dokladnie TEN SAM wzorzec CO `_30_spring_messaging_and_async/
        // Lesson01` (`SpringApplication.run(AsyncApp.class, "--server.port=0", ...)`).
        // UWAGA: "standalone" Eureka Server (jak TEN) ZAWSZE probuje replikowac sie DO "peera"
        // pod adresem `eureka.client.serviceUrl.defaultZone` (domyslnie `localhost:8761`,
        // klasyczny "domyslny port Eureki") - to ZUPELNIE NIEZALEZNE OD `register-with-eureka`/
        // `fetch-registry` (te flagi kontroluja TYLKO to, czy SERWER rejestruje SAM SIEBIE jako
        // instancje). Poniewaz uzywamy `server.port=0` (losowy port, zgodnie Z konwencja tego
        // kursu), serwer NIE MOZE wskazywac SAM NA SIEBIE jako peera - proby polaczenia Z
        // (nieistniejacym) peerem NA porcie 8761 KONCZA SIE NIESZKODLIWYMI, ALE halasliwymi
        // bledami W logu ("Network level connection to peer localhost"). Wyciszone ponizej -
        // ZWERYFIKOWANE EMPIRYCZNIE, ze NIE WPLYWA to NA rejestracje klientow (dziala normalnie).
        return new SpringApplicationBuilder(EurekaServerApp.class)
                .run(
                        "--spring.application.name=eureka-server",
                        "--server.port=0",
                        "--eureka.client.register-with-eureka=false",
                        "--eureka.client.fetch-registry=false",
                        "--eureka.server.enable-self-preservation=false",
                        "--eureka.server.response-cache-update-interval-ms=1000",
                        "--eureka.server.use-read-only-response-cache=false",
                        "--logging.level.root=WARN",
                        "--logging.level.com.netflix.eureka.cluster=OFF",
                        "--logging.level.org.springframework.cloud.netflix.eureka.server=OFF",
                        "--logging.level.com.netflix.discovery.shared.transport=OFF");
    }

    private static ConfigurableApplicationContext startClientService(String appName, int eurekaServerPort) {
        return new SpringApplicationBuilder(ClientServiceApp.class)
                .run(
                        "--spring.application.name=" + appName,
                        "--server.port=0",
                        "--eureka.client.serviceUrl.defaultZone=http://localhost:" + eurekaServerPort + "/eureka/",
                        // fetch-registry=false: TEN klient nie POTRZEBUJE pobierac REJESTRU (nie
                        // odpytuje INNYCH serwisow) - wylaczenie unika SZUMU W logu (agresywny
                        // fetch-interval ponizej domyslnego moze dawac TimedSupervisorTask
                        // TimeoutException, ZWERYFIKOWANE empirycznie). Rejestracja WLASNEJ
                        // instancji (heartbeat) DZIALA NIEZALEZNIE OD tej flagi.
                        "--eureka.client.fetch-registry=false",
                        "--eureka.instance.lease-renewal-interval-in-seconds=1",
                        "--eureka.instance.prefer-ip-address=true",
                        "--logging.level.root=WARN",
                        // Logowanie (Logback) jest GLOBALNE DLA calego JVM - kazdy KOLEJNY
                        // kontekst Spring Boot PODMIENIA globalna konfiguracje logowania, WIEC
                        // wyciszenie ustawione TYLKO W startEurekaServer() zostaje NADPISANE, GDY
                        // startuje TEN kontekst. Te SAME wpisy MUSZA byc powtorzone TUTAJ, inaczej
                        // wciaz-dzialajacy watek peer-replication serwera (patrz komentarz WYZEJ)
                        // ZNOWU zacznie halasliwie logowac PRZEZ nowa, globalna konfiguracje.
                        "--logging.level.com.netflix.eureka.cluster=OFF",
                        "--logging.level.org.springframework.cloud.netflix.eureka.server=OFF",
                        "--logging.level.com.netflix.discovery.shared.transport=OFF");
    }

    private static void demonstrateWaitForRegistration(ConfigurableApplicationContext serverContext, String appName) throws InterruptedException {
        System.out.println("\n--- Oczekiwanie NA rejestracje '" + appName + "' W rejestrze Eureki (bounded polling) ---");

        PeerAwareInstanceRegistry registry = serverContext.getBean(PeerAwareInstanceRegistry.class);
        Instant start = Instant.now();
        Instant deadline = start.plusSeconds(20);

        while (Instant.now().isBefore(deadline)) {
            Application application = registry.getApplication(appName.toUpperCase());
            if (application != null && !application.getInstances().isEmpty()) {
                Duration czasOczekiwania = Duration.between(start, Instant.now());
                System.out.println("'" + appName + "' ZAREJESTROWANY PO " + czasOczekiwania.toMillis() + "ms - instancji: " + application.getInstances().size());
                return;
            }
            Thread.sleep(300);
        }
        System.out.println("'" + appName + "' NIE zdazyl sie zarejestrowac W ciagu 20s (rzadkie, zaleznie od obciazenia maszyny).");
    }

    private static void demonstrateSelfPreservationConcept() {
        System.out.println("\n--- Tryb self-preservation (WYLACZONY W tym demo) ---");
        System.out.println("Domyslnie Eureka Server, GDY straci WIECEJ NIZ ~15% heartbeatow NARAZ, WCHODZI W tryb self-preservation:");
        System.out.println("PRZESTAJE WYRZUCAC instancje Z rejestru (zaklada, ze to PROBLEM SIECI, nie realna awaria WSZYSTKICH instancji naraz).");
        System.out.println("W tym demo WYLACZONE (eureka.server.enable-self-preservation=false) - inaczej krotki czas zycia demo NIE ZDAZYLBY nic wyewiktowac ANI TEZ tego pokazac.");
    }

    private static void closeQuietly(ConfigurableApplicationContext context) {
        if (context != null) {
            context.close();
        }
    }
}
