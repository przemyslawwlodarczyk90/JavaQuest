package com.example.javaquest._20_spring_core.Lesson02_SpringVersionsAndCompatibilityOverview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import java.util.List;

public class _Lesson02_SpringVersionsAndCompatibilityOverview {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 2: MAPA WERSJI SPRINGA - CO DZIALA INACZEJ, GDZIE I DLACZEGO ===");

        /*
         * ============================================================
         * 📦 PO CO TA LEKCJA JEST TUTAJ, NA SAMYM POCZATKU ROZDZIALU
         * ============================================================
         * Spring rozwija sie od ponad 20 lat (pierwsze wydanie: 2004).
         * W tym czasie NIEKTORE mechanizmy zostaly ZASTAPIONE nowszymi —
         * a internet (Stack Overflow, blogi, stare tutoriale na YouTube)
         * jest PELEN kodu pisanego pod RÓZNE wersje na raz. Zanim
         * zaczniesz uczyc sie KONKRETNYCH mechanizmow (Lesson03+), warto
         * wiedziec, ze pytanie "jak to sie robi w Springu?" CZESTO ma
         * odpowiedz "zalezy, ktora wersja". Ta lekcja to MAPA — kazdy
         * wiersz odsyla do lekcji (w TYM kursie), ktora idzie w dany
         * temat glebiej. Nie musisz zapamietac wszystkiego teraz —
         * wracaj tu, kiedy natrafisz na kod, ktorego nie rozpoznajesz.
         */
        System.out.println("Spring rozwija sie od 2004 roku - ta lekcja to mapa mechanizmow, ktore ZMIENILY sie miedzy wersjami, zanim zaczniesz szczegoly.");

        demonstrateActualRunningVersionsInThisProject();
        demonstrateBootFrameworkJavaMapping();
        demonstrateMechanismsThatChangedAcrossVersions();
        demonstrateImplicitConstructorInjectionSince43();
        demonstrateHeuristicTutorialVersionDetector();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Ten kurs uczy Spring Boot 3.4.x / Spring Framework 6.x /
         *   `jakarta.*` / Java 21 — sprawdzone TERAZ, w KODZIE (nie na
         *   pamiec), przez `SpringBootVersion.getVersion()`.
         * - Kazdy mechanizm z tabeli wyzej ma WLASNA, glebsza lekcje w
         *   dalszej czesci bloku rozdzialow poswieconych Springowi — ta
         *   lekcja to TYLKO mapa, wracaj do niej w razie watpliwosci.
         * - Praktyczna umiejetnosc: zanim skopiujesz kod ze Stack
         *   Overflow/starego tutoriala, sprawdz SYGNALY wersji (import
         *   `javax.*`, `WebSecurityConfigurerAdapter`, `spring.factories`)
         *   — oszczedzi Ci to godzin debugowania "dlaczego to sie nie
         *   kompiluje".
         */
        System.out.println("\n=== KONIEC LEKCJI 2 ===");
    }

    private static void demonstrateActualRunningVersionsInThisProject() {
        System.out.println("\n=== JAKIEJ WERSJI SPRINGA NAPRAWDE UZYWA TEN PROJEKT (SPRAWDZONE W KODZIE) ===");

        String frameworkVersion = SpringVersion.getVersion();
        String bootVersion = SpringBootVersion.getVersion();

        System.out.println("Spring Framework (org.springframework.core.SpringVersion): "
                + (frameworkVersion != null ? frameworkVersion : "nieznana (manifest JAR-a nie ma atrybutu Implementation-Version w tym trybie uruchomienia)"));
        System.out.println("Spring Boot (org.springframework.boot.SpringBootVersion): "
                + (bootVersion != null ? bootVersion : "nieznana (jak wyzej)"));
        System.out.println("-> to NIE jest wiedza 'z pamieci' - odczytana WPROST z manifestu JAR-a na classpath TEGO uruchomienia.");
    }

    private record VersionMapping(String springBoot, String springFramework, String javaBaseline, String namespace) {
    }

    private static void demonstrateBootFrameworkJavaMapping() {
        System.out.println("\n=== MAPOWANIE: SPRING BOOT -> SPRING FRAMEWORK -> JAVA -> NAMESPACE ===");

        List<VersionMapping> mappings = List.of(
                new VersionMapping("Spring Boot 1.x (2014-2019, EOL)", "Spring Framework 4.x", "Java 6/7/8", "javax.*"),
                new VersionMapping("Spring Boot 2.x (2018-2023, w wygasajacym wsparciu)", "Spring Framework 5.x", "Java 8+", "javax.*"),
                new VersionMapping("Spring Boot 3.x (2022+, TEN KURS: 3.4.4)", "Spring Framework 6.x", "Java 17+ (kurs: 21)", "jakarta.*")
        );

        for (VersionMapping m : mappings) {
            System.out.println("  " + m.springBoot());
            System.out.println("    -> " + m.springFramework() + ", Java: " + m.javaBaseline() + ", namespace: " + m.namespace());
        }
        System.out.println("-> KAZDY tutorial z importem 'javax.servlet'/'javax.persistence'/'javax.validation' celuje w Spring Boot 2.x lub starszy - w TYM projekcie (Boot 3.4.4) te importy NIE ZADZIALAJA.");
    }

    private record MechanismChange(String mechanism, String older, String newer, String lesson) {
    }

    private static void demonstrateMechanismsThatChangedAcrossVersions() {
        System.out.println("\n=== TABELA: MECHANIZMY, KTORE DZIALAJA INACZEJ W ZALEZNOSCI OD WERSJI ===");

        List<MechanismChange> changes = List.of(
                new MechanismChange(
                        "Styl konfiguracji beanow",
                        "XML <beans><bean id=... class=...> (Spring 1.x-2.x)",
                        "Java Config @Configuration+@Bean / component-scanning (mainstream od 2.5-3.0, JEDYNY styl w Boot)",
                        "_20_spring_core/Lesson05_ConfigurationStylesXmlVsJavaVsAnnotations"),
                new MechanismChange(
                        "Wstrzykiwanie przez konstruktor",
                        "Wymagane jawne @Autowired na konstruktorze",
                        "Od Spring 4.3 (2016): @Autowired OPCJONALNE przy DOKLADNIE 1 konstruktorze",
                        "_20_spring_core/Lesson10_ConstructorInjection"),
                new MechanismChange(
                        "Domyslna obsluga zaleznosci cyklicznych",
                        "spring.main.allow-circular-references=true (przed Boot 2.6)",
                        "Domyslnie false od Boot 2.6 (2021) - cykl rzuca wyjatek od razu",
                        "_20_spring_core/Lesson13_CircularDependencies"),
                new MechanismChange(
                        "Rejestracja auto-konfiguracji Boota",
                        "META-INF/spring.factories (klucz EnableAutoConfiguration)",
                        "META-INF/spring/...AutoConfiguration.imports + @AutoConfiguration (od Boot 2.7)",
                        "_21_spring_boot/Lesson04_AutoConfiguration"),
                new MechanismChange(
                        "Konfiguracja Spring Security",
                        "WebSecurityConfigurerAdapter (dziedziczenie) - deprecated 5.7/Boot 2.7, USUNIETY w Security 6.x",
                        "Bean SecurityFilterChain (metoda, bez dziedziczenia) - dostepny od 5.4, JEDYNY w 6.x",
                        "_24_spring_security/Lesson03_SecurityConfigEvolutionOldVsNew"),
                new MechanismChange(
                        "Wyrazenia autoryzacji URL",
                        "authorizeRequests()+antMatchers() - deprecated od 5.7+",
                        "authorizeHttpRequests()+requestMatchers() - aktualny standard",
                        "_24_spring_security/Lesson03_SecurityConfigEvolutionOldVsNew"),
                new MechanismChange(
                        "Bezpieczenstwo metod",
                        "@EnableGlobalMethodSecurity(prePostEnabled=true) - deprecated od Security 6.0",
                        "@EnableMethodSecurity - prePostEnabled=true DOMYSLNIE",
                        "_24_spring_security/Lesson10_MethodSecurity"),
                new MechanismChange(
                        "Synchroniczny klient HTTP",
                        "RestTemplate (Spring 3.0, 2009 - dzis w trybie utrzymaniowym)",
                        "RestClient (Spring Framework 6.1/Boot 3.2, 2023) - plynne API bez narzutu reaktywnego",
                        "_22_spring_web/Lesson17_HttpClientsRestTemplateWebClientRestClient")
        );

        for (MechanismChange c : changes) {
            System.out.println("\n  * " + c.mechanism());
            System.out.println("    STARE: " + c.older());
            System.out.println("    NOWE:  " + c.newer());
            System.out.println("    Wiecej: " + c.lesson());
        }
    }

    // Klasyczny styl - jawny @Autowired, poprawny w KAZDEJ wersji Springa od kiedy DI w ogole istnieje.
    @Component
    static class LegacyStyleRepository {
    }

    @Component
    static class LegacyStyleService {
        private final LegacyStyleRepository repository;

        @Autowired
        LegacyStyleService(LegacyStyleRepository repository) {
            this.repository = repository;
        }
    }

    // Wspolczesny styl - BEZ @Autowired, dziala TYLKO bo klasa ma dokladnie 1 konstruktor (Spring 4.3+).
    @Component
    static class ModernStyleRepository {
    }

    @Component
    static class ModernStyleService {
        private final ModernStyleRepository repository;

        ModernStyleService(ModernStyleRepository repository) {
            this.repository = repository;
        }
    }

    @Configuration
    @ComponentScan
    static class InjectionStylesConfig {
    }

    private static void demonstrateImplicitConstructorInjectionSince43() {
        System.out.println("\n=== DOWOD: OBA STYLE (JAWNY @Autowired I NIEJAWNY OD 4.3) DZIALAJA IDENTYCZNIE W TEJ WERSJI ===");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(InjectionStylesConfig.class)) {
            LegacyStyleService legacy = context.getBean(LegacyStyleService.class);
            ModernStyleService modern = context.getBean(ModernStyleService.class);

            System.out.println("LegacyStyleService (jawny @Autowired) utworzony: " + (legacy != null));
            System.out.println("ModernStyleService (bez adnotacji, 1 konstruktor) utworzony: " + (modern != null));
            System.out.println("-> od Spring 4.3 (2016) OBA style sa rownowazne - jawne @Autowired na konstruktorze to dzis kwestia STYLU, nie WYMOGU.");
        }
    }

    private static String classifyTutorialSnippet(String snippet) {
        if (snippet.contains("javax.servlet") || snippet.contains("javax.persistence") || snippet.contains("javax.validation")) {
            return "Spring Boot 2.x lub starszy (namespace javax.*) - importy NIE zadzialaja w tym projekcie (Boot 3.4.4)";
        }
        if (snippet.contains("WebSecurityConfigurerAdapter")) {
            return "Spring Security sprzed 2022 (WebSecurityConfigurerAdapter USUNIETY w Security 6.x) - przetlumacz na SecurityFilterChain";
        }
        if (snippet.contains("authorizeRequests()") || snippet.contains("antMatchers(")) {
            return "Starszy styl Spring Security (przed authorizeHttpRequests/requestMatchers)";
        }
        if (snippet.contains("META-INF/spring.factories")) {
            return "Starsza biblioteka/starszy Boot (przed AutoConfiguration.imports z Boot 2.7)";
        }
        if (snippet.contains("jakarta.") || snippet.contains("SecurityFilterChain") || snippet.contains("authorizeHttpRequests")) {
            return "Wspolczesny styl (Spring Boot 3.x / Spring Security 6.x) - zgodny z tym kursem";
        }
        return "Brak jednoznacznych sygnalow wersji w tym fragmencie";
    }

    private static void demonstrateHeuristicTutorialVersionDetector() {
        System.out.println("\n=== PRAKTYCZNA UMIEJETNOSC: ROZPOZNAJ WERSJE PO SAMYM KODZIE ZE STACK OVERFLOW ===");

        String[] sampleSnippets = {
                "import javax.persistence.Entity;",
                "public class SecurityConfig extends WebSecurityConfigurerAdapter {",
                "http.authorizeRequests().antMatchers(\"/admin\").hasRole(\"ADMIN\");",
                "http.authorizeHttpRequests(auth -> auth.requestMatchers(\"/admin\").hasRole(\"ADMIN\"));",
                "System.getProperty(\"user.dir\");"
        };

        for (String snippet : sampleSnippets) {
            System.out.println("Fragment: \"" + snippet + "\"");
            System.out.println("  -> " + classifyTutorialSnippet(snippet));
        }
        System.out.println("\n-> ZANIM skopiujesz kod z internetu: poszukaj tych sygnalow - oszczedzi to godziny debugowania 'dlaczego moj kod z tutoriala sie nie kompiluje'.");
    }
}
