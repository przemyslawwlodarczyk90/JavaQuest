package com.example.javaquest._31_spring_cloud_microservices.Lesson04_ConfigServerIntro;

public class _Lesson04_ConfigServerIntro {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 4: Config Server - centralna konfiguracja ===");

        /*
         * ============================================================
         * 📦 PROBLEM: konfiguracja ROZPROSZONA PO dziesiatkach serwisow
         * ============================================================
         * Kazdy serwis Z `_21_spring_boot` mial WLASNY
         * `application.properties`/`application.yml` - W monolicie
         * TO WYSTARCZA. W systemie Z DZIESIATKAMI mikroserwisow:
         * - ta sama wartosc (np. adres bazy danych, klucz API)
         *   POWTARZA SIE W WIELU plikach - zmiana WYMAGA
         *   redeploymentu KAZDEGO serwisu Z OSOBNA,
         * - BRAK jednego miejsca, GDZIE zobaczysz CALA konfiguracje
         *   systemu naraz,
         * - trudno WERSJONOWAC zmiany konfiguracji NIEZALEZNIE OD kodu.
         *
         * Spring Cloud Config Server ROZWIAZUJE to: JEDEN, centralny
         * serwis HTTP, KTORY serwuje pliki konfiguracyjne DLA
         * WSZYSTKICH pozostalych serwisow - najczesciej Z repozytorium
         * Git (wersjonowanie za darmo!), ale rowniez Z systemu plikow
         * (uzyte W tym demo - "native" profile, prostsze DLA lokalnej
         * demonstracji).
         */
        System.out.println("Problem: konfiguracja rozproszona po dziesiatkach serwisow - Config Server DAJE JEDNO, centralne, wersjonowane zrodlo prawdy.");

        demonstrateConfigServerRole();
        demonstrateNativeVsGitBackend();
        demonstratePropertyResolutionOrder();
        demonstrateSecurityImplication();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EnableConfigServer` + starter `spring-cloud-config-
         *   server` = wystaw HTTP endpoint serwujacy konfiguracje.
         * - Backend "native" (pliki lokalne) DLA demo, "git" (najczesciej
         *   uzywany W PRODUKCJI) DLA wersjonowania.
         * - Klucz nazewnictwa plikow: `{application}-{profile}.yml`
         *   (np. `orders-service-prod.yml`) - Lesson05 pokaze klienta,
         *   ktory FAKTYCZNIE odczytuje TAKA konfiguracje ZDALNIE.
         * - Config Server serwuje konfiguracje W POSTACI JSON/YAML/
         *   properties PRZEZ zwykle REST API - MOZNA je odpytac
         *   NAWET zwyklym curl/przegladarka.
         */
        System.out.println("\n=== KONIEC LEKCJI 4 ===");
    }

    private static void demonstrateConfigServerRole() {
        System.out.println("\n--- Rola Config Servera ---");
        System.out.println("Config Server TO ZWYKLA aplikacja Spring Boot Z `@EnableConfigServer` (spring-cloud-config-server).");
        System.out.println("Wystawia REST API: GET /{application}/{profile}[/{label}] -> zwraca konfiguracje DLA danego serwisu+profilu.");
        System.out.println("Przyklad: GET /orders-service/prod -> polaczona konfiguracja Z application.yml + orders-service.yml + orders-service-prod.yml.");
    }

    private static void demonstrateNativeVsGitBackend() {
        System.out.println("\n--- Backend: 'native' (pliki lokalne) vs 'git' (produkcyjny standard) ---");
        System.out.println("`spring.profiles.active=native` + `spring.cloud.config.server.native.search-locations=file:...`:");
        System.out.println("  Config Server czyta pliki Z LOKALNEGO systemu plikow - proste DLA demo/testow, BRAK historii zmian.");
        System.out.println("`spring.cloud.config.server.git.uri=https://github.com/...`:");
        System.out.println("  Config Server KLONUJE repozytorium Git - KAZDA zmiana konfiguracji TO commit (pelna historia, code review, rollback).");
        System.out.println("Lesson05 uzyje backendu 'native' (bez zaleznosci OD zewnetrznego Gita W demo).");
    }

    private static void demonstratePropertyResolutionOrder() {
        System.out.println("\n--- Kolejnosc laczenia plikow konfiguracyjnych ---");
        System.out.println("DLA zadania GET /orders-service/prod, Config Server LACZY (od NAJNIZSZEGO do NAJWYZSZEGO priorytetu):");
        System.out.println("  1. application.yml           (wspolne DLA WSZYSTKICH serwisow)");
        System.out.println("  2. application-prod.yml      (wspolne DLA WSZYSTKICH serwisow, profil 'prod')");
        System.out.println("  3. orders-service.yml        (specyficzne DLA 'orders-service')");
        System.out.println("  4. orders-service-prod.yml   (specyficzne DLA 'orders-service' W profilu 'prod' - NAJWYZSZY priorytet)");
    }

    private static void demonstrateSecurityImplication() {
        System.out.println("\n--- Bezpieczenstwo: Config Server MOZE zawierac SEKRETY ---");
        System.out.println("Config Server CZESTO serwuje hasla/klucze API - W PRODUKCJI MUSI byc zabezpieczony (Spring Security, Lesson16).");
        System.out.println("Powiazanie Z `_19_security_basics/Lesson18_SecretsManagement` - Config Server TO JEDNA Z form 'menedzera sekretow',");
        System.out.println("choc bez wbudowanego szyfrowania W spoczynku (chyba ze dodasz np. Vault jako backend zamiast git/native).");
    }
}
