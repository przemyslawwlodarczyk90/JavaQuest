package com.example.javaquest._21_spring_boot.Lesson01_WhatIsSpringBoot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class _Lesson01_WhatIsSpringBoot {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 1: CZYM JEST SPRING BOOT? ===");

        /*
         * ============================================================
         * 📦 Z `_20_spring_core` DO `_21_spring_boot`
         * ============================================================
         * Cały rozdział `_20_spring_core` celowo używał gołego
         * `AnnotationConfigApplicationContext` - Ty RĘCZNIE rejestrowałeś
         * klasy `@Configuration`, RĘCZNIE aktywowałeś profile
         * (`context.getEnvironment().setActiveProfiles(...)`), RĘCZNIE
         * wywoływałeś `refresh()`. Spring Boot to WARSTWA NAD tym samym
         * kontenerem - `@SpringBootApplication` + `SpringApplication.run()`
         * ROBI to wszystko ZA CIEBIE: auto-konfigurację (wykrywanie "co
         * jest na classpath" i konfigurowanie tego automatycznie),
         * aktywację profili z linii poleceń/zmiennych środowiskowych,
         * wbudowany serwer (Tomcat - już znasz go z `_07_servlets`).
         */
        System.out.println("Spring Boot = WARSTWA nad TYM SAMYM kontenerem z _20_spring_core - automatyzuje to, co tam robiles RECZNIE.");

        demonstrateSpringBootApplicationIsMetaAnnotation();
        demonstrateMinimalBootAppReturnsSameKindOfContext();

        /*
         * ============================================================
         * 🔹 EKOSYSTEM SPRINGA - PRZYPOMNIENIE Z _20_spring_core/Lesson01
         * ============================================================
         * Spring Framework (rdzeń: IoC/DI/AOP, `_20_spring_core`) - Spring
         * Boot (ta warstwa: auto-konfiguracja, wbudowany serwer, starter
         * POM-y) - Spring Data/Security (wyspecjalizowane moduły,
         * `_23`/`_24`). Boot NIE JEST nowym frameworkiem - to Spring
         * Framework "z sensownymi ustawieniami domyślnymi".
         */
        System.out.println("\nSpring Boot NIE jest nowym frameworkiem - to Spring Framework 'z sensownymi ustawieniami domyslnymi'.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@SpringBootApplication` to META-ADNOTACJA łącząca
         *   `@Configuration`, `@ComponentScan`, `@EnableAutoConfiguration`.
         * - `SpringApplication.run(...)` zwraca ZWYKŁY `ConfigurableApplicationContext` -
         *   TEN SAM typ kontenera co w `_20_spring_core`, TYLKO
         *   skonfigurowany za Ciebie.
         * - Kolejne lekcje rozłożą KAŻDY z automatyzmów Boota na czynniki
         *   pierwsze: starter POM-y (Lesson03), auto-konfigurację
         *   (Lesson04), profile (Lesson06), itd.
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    private static void demonstrateSpringBootApplicationIsMetaAnnotation() {
        System.out.println("\n=== @SpringBootApplication = META-ADNOTACJA (DOWOD REFLEKSJA) ===");

        System.out.println("@SpringBootApplication ma na sobie @EnableAutoConfiguration: "
                + SpringBootApplication.class.isAnnotationPresent(EnableAutoConfiguration.class));
        System.out.println("@SpringBootApplication ma na sobie @ComponentScan: "
                + SpringBootApplication.class.isAnnotationPresent(ComponentScan.class));

        boolean hasConfigurationTransitively = false;
        for (var annotation : SpringBootApplication.class.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(Configuration.class)
                    || annotation.annotationType().equals(Configuration.class)) {
                hasConfigurationTransitively = true;
            }
        }
        System.out.println("@SpringBootApplication prowadzi (posrednio, przez @SpringBootConfiguration) do @Configuration: " + hasConfigurationTransitively);
        System.out.println("-> TRZY adnotacje, ktore RECZNIE dodawales w _20_spring_core (@Configuration+@ComponentScan) PLUS auto-konfiguracja - w JEDNEJ adnotacji.");
    }

    @Component
    static class GreetingService {
        String greet() {
            return "Witaj ze Spring Boot!";
        }
    }

    @SpringBootApplication
    static class MinimalBootApp {
    }

    private static void demonstrateMinimalBootAppReturnsSameKindOfContext() {
        System.out.println("\n=== MINIMALNA APLIKACJA BOOT - TEN SAM RODZAJ KONTENERA ===");

        // WebApplicationType.NONE - ta lekcja NIE URUCHAMIA wbudowanego serwera (o TYM lekcja
        // pozniej) - skupiamy sie WYLACZNIE na kontenerze IoC, jak w _20_spring_core.
        ConfigurableApplicationContext context = new SpringApplicationBuilder(MinimalBootApp.class)
                .web(WebApplicationType.NONE)
                .run();

        try {
            GreetingService service = context.getBean(GreetingService.class);
            System.out.println(service.greet());
            System.out.println("Typ zwroconego kontekstu: " + context.getClass().getSimpleName());
            System.out.println("-> to WCIAZ ten sam rodzaj kontenera co 'AnnotationConfigApplicationContext' z _20_spring_core -");
            System.out.println("   'GreetingService' zostal znaleziony przez component-scan, DOKLADNIE jak w Lesson01 tamtego rozdzialu.");
        } finally {
            context.close();
        }
    }
}
