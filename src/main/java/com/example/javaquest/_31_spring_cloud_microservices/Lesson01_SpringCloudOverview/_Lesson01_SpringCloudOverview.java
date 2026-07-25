package com.example.javaquest._31_spring_cloud_microservices.Lesson01_SpringCloudOverview;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class _Lesson01_SpringCloudOverview {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 1: Spring Cloud - mapa modulow ===");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAL - _31_spring_cloud_microservices
         * ============================================================
         * Domyka `_17_architecture/Lesson19_WhenMicroservicesMakeSense`
         * W PRAKTYCE - TAM poznales KOSZTY mikroserwisow (sieciowosc,
         * eventual consistency, operacyjna zlozonosc), TU zobaczysz,
         * JAK sie je FAKTYCZNIE buduje przy pomocy Spring Cloud.
         *
         * WAZNE zalozenie tego rozdzialu: Spring Cloud TO NIE JEST
         * jeden framework - to PARASOL projektow, z ktorych KAZDY
         * rozwiazuje INNY problem systemu rozproszonego. Wspolna
         * cecha: KAZDY z nich integruje sie ZE Spring Bootem PRZEZ
         * starter + auto-konfiguracje (dokladnie TA SAMA mechanika
         * CO `_21_spring_boot` - `@ConditionalOnClass`,
         * `spring.factories`/`AutoConfiguration.imports`).
         *
         * Spring Cloud NIE JEST zarzadzany przez `spring-boot-starter-
         * parent` BOM (w przeciwienstwie do WIEKSZOSCI zaleznosci w tym
         * projekcie) - wymaga WLASNEGO importu BOM
         * (`spring-cloud-dependencies`), bo Spring Cloud ma WLASNY,
         * NIEZALEZNY cykl wydawniczy (tzw. "release trains" Z NAZWAMI
         * jak "2024.0.x" / kodowymi jak "Moorgate", NIE numerami
         * wersji jak Spring Boot). Ten projekt uzywa release trainu
         * "2024.0.1", zgodnego ze Spring Boot 3.4.4 (zweryfikowane
         * NA oficjalnej macierzy kompatybilnosci Spring Cloud).
         */
        System.out.println("Spring Cloud = PARASOL projektow (nie 1 framework), kazdy integruje sie ze Spring Bootem przez WLASNY starter + auto-konfiguracje.");

        demonstrateReleaseTrainVersion();
        demonstrateModuleMap();
        demonstrateWhyOwnBom();
        demonstrateChapterRoadmap();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Spring Cloud = zbior NIEZALEZNYCH projektow rozwiazujacych
         *   KONKRETNE problemy systemow rozproszonych (discovery,
         *   config, routing, resilience, tracing, komunikacja).
         * - Wlasny BOM (`spring-cloud-dependencies`), WLASNY cykl
         *   wydawniczy (release trains), integracja PRZEZ starter +
         *   auto-konfiguracje (jak `_21_spring_boot`).
         * - ZADEN z modulow tego rozdzialu NIE WYMAGA prawdziwej
         *   chmury (AWS/GCP) - WSZYSTKO da sie uruchomic LOKALNIE,
         *   czesto jako WIELE kontekstow Spring w JEDNYM procesie JVM
         *   (wzorzec juz znany z `_19_security_basics/Lesson06`).
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void demonstrateReleaseTrainVersion() throws IOException {
        System.out.println("\n--- Release train Spring Cloud uzywany W TYM projekcie ---");

        // Ten sam wzorzec co _20_spring_core/Lesson02 - odczytaj FAKTYCZNA wersje z classpath,
        // zamiast opisywac ja z pamieci.
        String springCloudVersion = readImplementationVersion(
                org.springframework.cloud.client.discovery.DiscoveryClient.class);
        System.out.println("org.springframework.cloud:spring-cloud-commons na classpath -> wersja: " + springCloudVersion);
        System.out.println("Release train tego projektu: 2024.0.1 (\"Moorgate\") - zgodny ze Spring Boot 3.4.4.");
    }

    private static String readImplementationVersion(Class<?> markerClass) {
        Package pkg = markerClass.getPackage();
        String version = pkg == null ? null : pkg.getImplementationVersion();
        return version != null ? version : "(niedostepna w manifescie tego builda)";
    }

    private static void demonstrateModuleMap() {
        System.out.println("\n--- Mapa modulow Spring Cloud omawianych W TYM rozdziale ---");

        record Modul(String nazwa, String problem, String lekcja) {
        }

        Modul[] moduly = {
                new Modul("Eureka (Netflix OSS)", "Service discovery - jak serwis A ZNAJDUJE serwis B bez zahardkodowanego adresu", "Lesson03"),
                new Modul("Config Server", "Centralna, zewnetrzna konfiguracja DLA WIELU serwisow naraz", "Lesson04-05"),
                new Modul("Gateway", "Pojedynczy punkt wejscia (routing/filtry) PRZED wieloma serwisami", "Lesson06-07"),
                new Modul("LoadBalancer", "Wybor JEDNEJ z WIELU instancji tego samego serwisu po stronie klienta", "Lesson08"),
                new Modul("Circuit Breaker (Resilience4j)", "Ochrona przed KASKADOWA awaria, gdy jeden serwis przestaje odpowiadac", "Lesson09-10"),
                new Modul("Micrometer Tracing + Zipkin", "Sledzenie JEDNEGO zadania PRZEZ wiele serwisow (distributed tracing)", "Lesson11-12"),
                new Modul("OpenFeign", "Deklaratywny klient HTTP - interfejs Java ZAMIAST recznego WebClient/RestClient", "Lesson13"),
                new Modul("Stream", "Abstrakcja NAD Kafka/RabbitMQ (znanymi Z _30_spring_messaging_and_async)", "Lesson15"),
        };

        for (Modul m : moduly) {
            System.out.println("  " + m.nazwa() + " -> " + m.problem() + " (" + m.lekcja() + ")");
        }
    }

    private static void demonstrateWhyOwnBom() {
        System.out.println("\n--- Dlaczego Spring Cloud NIE jest w spring-boot-starter-parent BOM ---");
        System.out.println("Spring Boot BOM zarzadza wersjami dla SWOJEGO cyklu wydawniczego (3.4.x).");
        System.out.println("Spring Cloud sklada sie Z KILKUNASTU niezaleznych projektow (Netflix OSS, OpenFeign, Resilience4j-integracja, ...),");
        System.out.println("ktore MUSZA byc wydawane RAZEM, zeby zagwarantowac wzajemna zgodnosc - stad WLASNY release train (\"2024.0.x\").");
        System.out.println("W pom.xml: <dependencyManagement><dependencies><dependency> spring-cloud-dependencies:2024.0.1 (scope=import, type=pom) </dependency>...");
    }

    private static void demonstrateChapterRoadmap() {
        System.out.println("\n--- Plan rozdzialu (19 lekcji) ---");
        System.out.println("01-02: wprowadzenie i service discovery koncepcyjnie.");
        System.out.println("03: Eureka (embedded server + klienci W JEDNYM main()).");
        System.out.println("04-05: Config Server + klient.");
        System.out.println("06-08: Gateway + load balancing.");
        System.out.println("09-10: Circuit breaker (koncepcyjnie + Resilience4j naprawde).");
        System.out.println("11-12: distributed tracing (Micrometer + Zipkin, z fallbackiem bez Dockera).");
        System.out.println("13: Feign.");
        System.out.println("14-15: saga pattern + Spring Cloud Stream.");
        System.out.println("16-18: bezpieczenstwo, konteneryzacja, observability MIEDZY serwisami.");
        System.out.println("19: kapszton - KILKA wspolpracujacych mikroserwisow W JEDNYM main().");
        System.out.println("ZADNA lekcja NIE wymaga prawdziwej chmury - wszystko lokalnie (Docker TYLKO tam, gdzie jest to niezbedne, Z fallbackiem, gdy niedostepny).");
    }
}
