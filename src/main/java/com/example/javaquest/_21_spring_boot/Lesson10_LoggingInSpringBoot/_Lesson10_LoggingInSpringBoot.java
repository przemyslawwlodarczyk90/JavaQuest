package com.example.javaquest._21_spring_boot.Lesson10_LoggingInSpringBoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;

public class _Lesson10_LoggingInSpringBoot {

    private static final Logger log = LoggerFactory.getLogger(_Lesson10_LoggingInSpringBoot.class);

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 10: LOGOWANIE W SPRING BOOT ===");

        /*
         * ============================================================
         * 📦 LOGBACK "ZA DARMO" - BEZ RECZNEJ KONFIGURACJI
         * ============================================================
         * `_13_libraries/Lesson16` uczyl RECZNEJ konfiguracji Logbacka
         * (`JoranConfigurator`, `logback.xml`). `spring-boot-starter`
         * (Lesson03) AUTO-KONFIGURUJE Logback z SENSOWNYMI ustawieniami
         * domyslnymi (kolorowy output w konsoli, poziom INFO) - BEZ
         * ZADNEGO pliku `logback.xml`. Poziomy logowania steruje sie
         * PROSTYMI wlasciwosciami `logging.level.<pakiet>=<POZIOM>` w
         * `application.properties`/`.yml` (Lesson05).
         */
        System.out.println("spring-boot-starter auto-konfiguruje Logback (kolorowy output, poziom INFO) - BEZ zadnego logback.xml.");

        demonstrateDefaultLogLevelIsInfo();
        demonstrateChangingLogLevelViaProperty();

        /*
         * ============================================================
         * 🔹 KIEDY WLASNY logback.xml/logback-spring.xml JEST NADAL POTRZEBNY
         * ============================================================
         * Domyslna konfiguracja Boota WYSTARCZA dla wiekszosci
         * przypadkow - `logback-spring.xml` (SPECJALNA nazwa, rozumiana
         * przez auto-konfiguracje Boota - MOZE uzywac `${...}` z
         * `application.properties`, w przeciwienstwie do zwyklego
         * `logback.xml`) jest potrzebny dopiero przy ZAAWANSOWANYCH
         * potrzebach - appendery do plikow z rotacja, format JSON dla
         * agregatorow logow (ELK/Splunk), MDC (`_13_libraries/Lesson17`).
         */
        System.out.println("\nlogback-spring.xml (NIE zwykly logback.xml) - potrzebny TYLKO przy zaawansowanych potrzebach (appendery/JSON/MDC) - domyslna konfiguracja Boota WYSTARCZA na poczatek.");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Logback dziala "od razu", bez `logback.xml` - Boot
         *   auto-konfiguruje sensowne ustawienia domyslne.
         * - `logging.level.<pakiet>=<POZIOM>` w `application.properties`
         *   ZMIENIA poziom logowania BEZ zmiany kodu Javy.
         * - `logback-spring.xml` (NIE `logback.xml`) - dla ZAAWANSOWANYCH
         *   potrzeb, ROZUMIE placeholdery `${...}` Springa.
         * - Wszystko, czego nauczyles sie w `_13_libraries/Lesson15-17`
         *   (SLF4J, MDC, dobre praktyki) DZIALA identycznie - Boot
         *   TYLKO automatyzuje POCZATKOWA konfiguracje.
         */
        System.out.println("\n=== KONIEC LEKCJI 10 ===");
    }

    private static void demonstrateDefaultLogLevelIsInfo() {
        System.out.println("\n=== DOMYSLNY POZIOM LOGOWANIA: INFO (DEBUG NIE JEST WIDOCZNY) ===");

        log.debug("To komunikat DEBUG - domyslnie NIE powinien byc widoczny w konsoli");
        log.info("To komunikat INFO - domyslnie WIDOCZNY");
        log.warn("To komunikat WARN - domyslnie WIDOCZNY");
        System.out.println("-> zauwaz w konsoli: linia DEBUG powyzej NIE POJAWILA SIE w logach (poziom domyslny to INFO) - linie INFO/WARN TAK.");
    }

    @SpringBootApplication
    static class LoggingLevelApp {
    }

    private static void demonstrateChangingLogLevelViaProperty() {
        System.out.println("\n=== ZMIANA POZIOMU LOGOWANIA PRZEZ logging.level.* (BEZ ZMIANY KODU) ===");

        Properties props = new Properties();
        props.setProperty("logging.level.com.example.javaquest", "DEBUG");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(LoggingLevelApp.class)
                .web(WebApplicationType.NONE)
                .properties(props)
                .run();
        try {
            System.out.println("Ustawiono 'logging.level.com.example.javaquest=DEBUG' - TERAZ wywoluje log.debug(...) PONOWNIE:");
            log.debug("To komunikat DEBUG - TERAZ POWINIEN byc widoczny, bo poziom zostal PODNIESIONY dla tego pakietu");
            System.out.println("-> ta sama linia kodu (log.debug(...)) co wyzej - RÓZNICA w widocznosci wynika WYLACZNIE z wlasciwosci konfiguracyjnej.");
        } finally {
            context.close();
        }
    }
}
