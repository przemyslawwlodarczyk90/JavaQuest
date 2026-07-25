package com.example.javaquest._31_spring_cloud_microservices.Lesson02_ServiceDiscoveryConcepts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class _Lesson02_ServiceDiscoveryConcepts {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: Service Discovery - koncepcje ===");

        /*
         * ============================================================
         * 📦 PROBLEM: hardkodowane adresy NIE DZIALAJA W CHMURZE
         * ============================================================
         * W monolicie wywolanie metody jest ZAWSZE tym samym adresem
         * (pamiec procesu). W mikroserwisach wywolanie STAJE SIE
         * zadaniem sieciowym DO INNEGO procesu - a TEN proces:
         * - moze miec WIELE instancji (skalowanie poziome),
         * - instancje moga POWSTAWAC I ZNIKAC (autoskalowanie, restart
         *   po awarii, deployment),
         * - kazda instancja moze dostac INNY adres IP/port (kontenery).
         *
         * Zahardkodowany adres (`http://192.168.1.50:8080`) W TAKIM
         * srodowisku PRZESTAJE DZIALAC PO PIERWSZYM restarcie/skalowaniu.
         * Service discovery ROZWIAZUJE dokladnie TEN problem.
         */
        System.out.println("Problem: w mikroserwisach adresy instancji sa DYNAMICZNE (skalowanie/restart/deployment) - zahardkodowany adres nie dziala.");

        demonstrateServiceRegistryPattern();
        demonstrateClientSideVsServerSideDiscovery();
        demonstrateHeartbeatAndEviction();
        demonstrateDnsIsNotEnough();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Service registry = centralny "spis telefonow" instancji.
         * - Instancje SAME SIE REJESTRUJA (self-registration) I
         *   WYSYLAJA regularne "heartbeat" - brak heartbeatu = wyrejestrowanie.
         * - Client-side discovery (Eureka+LoadBalancer, Lesson03/08) vs
         *   server-side discovery (load balancer PRZED serwisami, jak
         *   K8s Service) - obydwa rozwiazuja TEN SAM problem, INACZEJ
         *   rozlozony miedzy klienta A infrastrukture.
         * - Lesson03 pokaze KONKRETNA implementacje: Eureka.
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateServiceRegistryPattern() {
        System.out.println("\n--- Wzorzec: service registry (centralny spis instancji) ---");

        // Symulacja rejestru - DOKLADNIE to robi Eureka Server (Lesson03), tylko jako
        // prawdziwy serwis HTTP zamiast prostej mapy w pamieci.
        Map<String, List<String>> registry = new ConcurrentHashMap<>();

        register(registry, "orders-service", "10.0.0.1:8081");
        register(registry, "orders-service", "10.0.0.2:8081");
        register(registry, "payments-service", "10.0.0.5:8082");

        System.out.println("Rejestr PO rejestracji 3 instancji: " + registry);

        String chosen = discover(registry, "orders-service");
        System.out.println("Klient PYTA rejestr o 'orders-service' -> dostaje JEDNA z dostepnych instancji: " + chosen);
    }

    private static void register(Map<String, List<String>> registry, String serviceName, String address) {
        registry.computeIfAbsent(serviceName, k -> new ArrayList<>()).add(address);
    }

    private static String discover(Map<String, List<String>> registry, String serviceName) {
        List<String> instances = registry.getOrDefault(serviceName, List.of());
        if (instances.isEmpty()) {
            throw new IllegalStateException("Brak dostepnych instancji dla " + serviceName);
        }
        return instances.get(0); // uproszczone - Lesson08 pokaze prawdziwy load balancing
    }

    private static void demonstrateClientSideVsServerSideDiscovery() {
        System.out.println("\n--- Client-side vs server-side discovery ---");
        System.out.println("CLIENT-SIDE (Eureka + Spring Cloud LoadBalancer, Lesson03/08):");
        System.out.println("  klient SAM pyta rejestr I SAM wybiera instancje - decyzja PO STRONIE klienta.");
        System.out.println("SERVER-SIDE (np. Kubernetes Service, zewnetrzny load balancer):");
        System.out.println("  klient wola JEDEN staly adres (np. DNS Service) - infrastruktura ZA NIM wybiera instancje.");
        System.out.println("Ten rozdzial uczy PODEJSCIA client-side (Eureka) - klasyczny wybor 'Spring Cloud Netflix'.");
    }

    private static void demonstrateHeartbeatAndEviction() {
        System.out.println("\n--- Heartbeat i eviction (wyrejestrowanie martwych instancji) ---");
        System.out.println("Kazda instancja WYSYLA okresowy 'jestem zywy' (heartbeat) DO rejestru.");
        System.out.println("Jesli rejestr NIE DOSTANIE heartbeatu przez okreslony czas -> USUWA instancje Z listy dostepnych.");
        System.out.println("Eureka (Lesson03): domyslnie heartbeat co 30s, eviction po ~90s braku heartbeatu.");
        System.out.println("BEZ tego mechanizmu rejestr trzymalby MARTWE instancje W NIESKONCZONOSC.");
    }

    private static void demonstrateDnsIsNotEnough() {
        System.out.println("\n--- Dlaczego samo DNS nie wystarcza ---");
        System.out.println("DNS ma AGRESYWNE cache'owanie (TTL) - zmiana adresu propaguje sie Z OPOZNIENIEM.");
        System.out.println("DNS ZWYKLE NIE zna stanu ZDROWIA instancji (czy odpowiada na /health) - service registry TAK.");
        System.out.println("DNS NIE dostarcza METADANYCH o instancji (wersja, strefa dostepnosci, wagi load balancingu) - Eureka TAK.");
    }
}
