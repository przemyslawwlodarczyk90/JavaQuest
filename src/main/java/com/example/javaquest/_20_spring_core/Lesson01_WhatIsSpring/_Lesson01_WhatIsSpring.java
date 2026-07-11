package com.example.javaquest._20_spring_core.Lesson01_WhatIsSpring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class _Lesson01_WhatIsSpring {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: CZYM JEST SPRING? ===");

        /*
         * ============================================================
         * 📦 NOWY ROZDZIAŁ, NOWY, NAJWIĘKSZY BLOK TEGO KURSU
         * ============================================================
         * Ten rozdział otwiera ostatnią, 5-częściową serię kursu
         * poświęconą Springowi: `_20_spring_core` (ten rozdział — IDEA:
         * kontener, IoC, DI) → `_21_spring_boot` (jak Boot AUTOMATYZUJE
         * to, co tu poznasz) → `_22_spring_web` (REST na Springu — masz
         * już `_18_rest_api`/`_07_servlets`) → `_23_spring_data_jpa`
         * (masz już PEŁNE `_12_hibernate`) → `_24_spring_security` (masz
         * już PEŁNE `_19_security_basics`). CELOWO zaczynamy od Core, NIE
         * od Boota — zanim zobaczysz "magię" auto-konfiguracji, musisz
         * zrozumieć, CO ta magia właściwie robi za kulisami.
         *
         * 🔹 JAKI PROBLEM ROZWIĄZUJE SPRING?
         * ============================================================
         * W dużej aplikacji obiekty ZALEŻĄ od siebie nawzajem (serwis
         * potrzebuje repozytorium, repozytorium potrzebuje połączenia z
         * bazą, kontroler potrzebuje serwisu...). Ręczne tworzenie i
         * "drucikowanie" (wiring) tych obiektów przez `new` w setkach
         * miejsc jest KRUCHE i trudne w utrzymaniu. Spring to KONTENER,
         * który PRZEJMUJE tworzenie obiektów i wiązanie ich zależności —
         * Ty opisujesz CO ma powstać (adnotacjami), kontener decyduje,
         * KIEDY i JAK to złożyć. Poniższe demo pokazuje TEN SAM kawałek
         * logiki DWA razy — raz ręcznie, raz przez kontener Springa.
         */
        System.out.println("Spring = kontener, ktory tworzy obiekty i wiaze ich zaleznosci za Ciebie, zamiast Ciebie robiacego to recznie przez 'new'.");

        demonstrateManualWiringWithoutSpring();
        demonstrateSameCodeManagedBySpringContainer();

        /*
         * ============================================================
         * 🔍 EKOSYSTEM SPRINGA — MAPA, ŻEBY SIĘ NIE POGUBIĆ W NAZWACH
         * ============================================================
         * - Spring Framework — RDZEŃ: kontener IoC/DI (ten rozdział),
         *   AOP, dostęp do danych, Spring MVC (web). To FUNDAMENT, na
         *   którym stoi wszystko poniżej.
         * - Spring Boot — WARSTWA NAD Spring Frameworkiem: auto-
         *   konfiguracja, wbudowany serwer (Tomcat), "starter" POM-y,
         *   mniej boilerplate'u. NIE jest osobnym frameworkiem — to
         *   Spring Framework "z automatycznymi ustawieniami domyślnymi".
         * - Spring Data — ujednolicony sposób dostępu do danych
         *   (Spring Data JPA to jedna z wielu implementacji — są też
         *   Spring Data MongoDB, Redis, itd.) — `_23_spring_data_jpa`.
         * - Spring Security — uwierzytelnianie/autoryzacja jako filtr
         *   PRZED Spring MVC — `_24_spring_security`.
         * - Spring Cloud — narzędzia do systemów ROZPROSZONYCH
         *   (mikroserwisy, service discovery, config server) — POZA
         *   zakresem tego kursu (patrz `_17_architecture/Lesson19_
         *   WhenMicroservicesMakeSense` — ten kurs uczy modularnego
         *   monolitu jako domyślnego wyboru).
         */
        System.out.println("\nEkosystem: Spring Framework (rdzen) -> Spring Boot (auto-konfiguracja NAD rdzeniem) -> Spring Data/Security (wyspecjalizowane moduly) -> Spring Cloud (poza zakresem kursu).");

        /*
         * ============================================================
         * 📌 KTÓRA WERSJA SPRINGA W TYM KURSIE (WAŻNE ZASTRZEŻENIE)
         * ============================================================
         * `pom.xml` tego projektu ma `spring-boot-starter-parent` w
         * wersji 3.4.x — czyli uczymy się Spring Boot 3.x / Spring
         * Framework 6.x. To OZNACZA: namespace `jakarta.*` (NIE
         * `javax.*`), Java 17+ jako minimum (ten projekt celuje w Javę
         * 21, patrz `<java.version>` w `pom.xml`). Mnóstwo tutoriali w
         * internecie (zwłaszcza starszych niż ok. 2022-2023) uczy Spring
         * Boot 2.x z `javax.*` — te DWA światy różnią się w kilku
         * miejscach na tyle, że kod się PO PROSTU NIE SKOMPILUJE bez
         * zmiany importów. Następna lekcja (Lesson02) to CAŁA mapa
         * takich różnic — zanim zaczniesz szukać pomocy w necie, warto
         * wiedzieć, w którym świecie jesteś.
         */
        System.out.println("\nTen kurs: Spring Boot 3.4.x / Spring Framework 6.x / namespace jakarta.* / Java 21 - Lesson02 pokaze PELNA mape roznic wobec starszych wersji.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Spring = kontener IoC — TY opisujesz komponenty (adnotacje),
         *   kontener je TWORZY i WIĄŻE.
         * - Spring Framework (rdzeń) vs Spring Boot (auto-konfiguracja
         *   NAD rdzeniem) — to NIE są konkurencyjne technologie, Boot
         *   ROZSZERZA Framework.
         * - Ten kurs uczy Spring Boot 3.x/Spring Framework 6.x/
         *   `jakarta.*`/Java 17+ — zapamiętaj to, napotykając starsze
         *   materiały w sieci.
         * - Kolejne 22 lekcje tego rozdziału ROZBIJĄ dzisiejsze demo na
         *   czynniki pierwsze: DLACZEGO działa, JAK kontener znajduje
         *   klasy, JAK wybiera konstruktor, itd.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    // Ten sam komponent jest uzywany w OBU demo nizej - @Component nie zmienia
    // dzialania klasy, tylko oznacza ja jako "kandydata" do zarzadzania przez kontener.
    @Component
    static class MessageFormatter {
        String format(String name) {
            return "Witaj, " + name + "!";
        }
    }

    @Component
    static class GreetingService {
        private final MessageFormatter formatter;

        // Od Spring 4.3 (2016) @Autowired na konstruktorze jest OPCJONALNE,
        // gdy klasa ma DOKLADNIE JEDEN konstruktor (patrz Lesson10) - tu go
        // celowo pomijamy, zeby pokazac, ze to dziala "od razu".
        GreetingService(MessageFormatter formatter) {
            this.formatter = formatter;
        }

        String greet(String name) {
            return formatter.format(name);
        }
    }

    private static void demonstrateManualWiringWithoutSpring() {
        System.out.println("\n=== BEZ SPRINGA: RECZNE TWORZENIE I WIAZANIE OBIEKTOW ===");

        MessageFormatter formatter = new MessageFormatter();
        GreetingService service = new GreetingService(formatter);

        System.out.println(service.greet("Ala"));
        System.out.println("-> Ty (autor kodu) musiales wiedziec, ze GreetingService potrzebuje MessageFormatter, w jakiej kolejnosci je stworzyc, i recznie POLACZYC oba obiekty.");
    }

    @Configuration
    @ComponentScan
    static class AppConfig {
    }

    private static void demonstrateSameCodeManagedBySpringContainer() {
        System.out.println("\n=== ZE SPRINGIEM: KONTENER TWORZY I WIAZE OBIEKTY ZA CIEBIE ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            GreetingService service = context.getBean(GreetingService.class);
            System.out.println(service.greet("Jan"));
            System.out.println("-> Ty NIE wywolales ani razu 'new GreetingService(...)' - kontener PRZESKANOWAL pakiet w poszukiwaniu @Component,");
            System.out.println("   ROZPOZNAL ze GreetingService potrzebuje MessageFormatter (przez jedyny konstruktor) i sam je POLACZYL.");
        }
    }
}
