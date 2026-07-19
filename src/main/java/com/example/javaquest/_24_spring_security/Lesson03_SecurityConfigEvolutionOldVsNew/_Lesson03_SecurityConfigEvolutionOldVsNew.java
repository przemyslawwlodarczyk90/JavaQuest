package com.example.javaquest._24_spring_security.Lesson03_SecurityConfigEvolutionOldVsNew;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class _Lesson03_SecurityConfigEvolutionOldVsNew {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 3: Ewolucja konfiguracji Spring Security - STARE vs NOWE API ===");

        /*
         * ============================================================
         * 📦 DLACZEGO TA LEKCJA ISTNIEJE - "DLACZEGO TEN KOD Z INTERNETU NIE DZIALA?"
         * ============================================================
         * Spring Security ma NAJWIECEJ zmian miedzywersyjnych z calego
         * Springa. WIEKSZOSC tutoriali/odpowiedzi na Stack Overflow W
         * internecie WCIAZ uczy STAREGO API (bo powstaly PRZED 2022) -
         * bez znajomosci TEJ ewolucji, kopiowanie takiego kodu DO
         * projektu na Spring Boot 3.4.4 (jak TEN kurs) KONCZY SIE
         * BLEDEM KOMPILACJI, nie bledem runtime. Celem tej lekcji jest
         * nauczenie CIEBIE rozpoznawania "PO SAMYM KODZIE", pod jaka
         * wersje Springa napisany jest dany fragment.
         */
        System.out.println("Ta lekcja = 'jak rozpoznac PO KODZIE, pod jaka wersje Springa jest napisany tutorial' - ZANIM stracisz czas na kopiowanie nieaktualnego API.");

        explainWebSecurityConfigurerAdapterHistory();
        explainAuthorizeRequestsVsAuthorizeHttpRequests();
        explainEnableGlobalMethodSecurityVsEnableMethodSecurity();
        demonstrateCurrentApiWorking();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE - JAK ROZPOZNAC WIEK TUTORIALA
         * ============================================================
         * - `import ...WebSecurityConfigurerAdapter` LUB
         *   `extends WebSecurityConfigurerAdapter` -> kod SPRZED 2022,
         *   NIE SKOMPILUJE SIE na Security 6.x (klasa USUNIETA).
         * - `.authorizeRequests()` / `.antMatchers(...)` -> STARY styl
         *   (deprecated od 5.7+), zadziala jeszcze na Security 5.x, ale
         *   oznaczony DO USUNIECIA W 7.0 - NIE uzywaj W NOWYM kodzie.
         * - `@EnableGlobalMethodSecurity(prePostEnabled = true)` -> STARE
         *   (deprecated od Security 6.0) - NOWE: `@EnableMethodSecurity`
         *   (Lesson10), `prePostEnabled=true` JUZ DOMYSLNIE.
         * - `.authorizeHttpRequests(auth -> auth...)` +
         *   `.requestMatchers(...)` + bean `SecurityFilterChain` (BEZ
         *   dziedziczenia) -> TO jest AKTUALNY styl (Lesson02) - JEDYNY
         *   uzywany W TYM kursie.
         */
        System.out.println("\n=== KONIEC LEKCJI 3 ===");
    }

    private static void explainWebSecurityConfigurerAdapterHistory() {
        System.out.println("\n=== HISTORIA: WebSecurityConfigurerAdapter -> SecurityFilterChain ===");
        /*
         * ============================================================
         * 🔹 STARY STYL (Spring Security PRZED 5.7, powszechny W tutorialach sprzed 2022)
         * ============================================================
         * Konfiguracja PRZEZ DZIEDZICZENIE:
         *
         *   public class SecurityConfig extends WebSecurityConfigurerAdapter {
         *       @Override
         *       protected void configure(HttpSecurity http) throws Exception {
         *           http.authorizeRequests()
         *               .antMatchers("/public/**").permitAll()
         *               .anyRequest().authenticated();
         *       }
         *   }
         *
         * TA KLASA NIE ISTNIEJE W wersji Springa uzywanej W TYM kursie -
         * `import org.springframework.security.config.annotation.web.
         * configuration.WebSecurityConfigurerAdapter;` DA BLAD KOMPILACJI
         * "package does not exist", bo:
         *
         * - DEPRECATED W Spring Security 5.7.0-M2 / Spring Boot 2.7.0
         *   (kwiecien 2022),
         * - CALKOWICIE USUNIETY W Spring Security 6.0 (listopad 2022) -
         *   czyli W KAZDYM projekcie na Boot 3.x (WLACZNIE Z TYM kursem,
         *   Boot 3.4.4).
         *
         * 🔍 NOWY STYL (JEDYNY dostepny OD Security 6.0, uzyty W Lesson02)
         * ============================================================
         * Konfiguracja PRZEZ BEAN (BEZ dziedziczenia) - metoda
         * OZNACZONA `@Bean`, zwracajaca `http.build()`. Wprowadzony
         * jako OPCJA JUZ W Security 5.4 (rownolegle Z adapterem), ale
         * OD 6.0 to JEDYNY sposob.
         */
        System.out.println("STARY: 'class X extends WebSecurityConfigurerAdapter' - USUNIETY W Security 6.0, NIE SKOMPILUJE SIE na Boot 3.x.");
        System.out.println("NOWY: '@Bean SecurityFilterChain filterChain(HttpSecurity http)' - JEDYNY sposob od Security 6.0 (wprowadzony jako opcja juz w 5.4).");
    }

    private static void explainAuthorizeRequestsVsAuthorizeHttpRequests() {
        System.out.println("\n=== authorizeRequests()+antMatchers() (STARE) vs authorizeHttpRequests()+requestMatchers() (NOWE) ===");
        /*
         * `.authorizeRequests()` + `.antMatchers(...)` - deprecated OD
         * Security 5.7+, OZNACZONE DO USUNIECIA W 7.0 (WCIAZ dzialaja W
         * 6.x, ale Z OSTRZEZENIEM kompilatora/IDE).
         *
         * `.authorizeHttpRequests()` + `.requestMatchers(...)` -
         * AKTUALNY standard OD 5.8/6.0 - uzyty W Lesson02. WAZNE: NIE
         * MIESZAJ obu W JEDNEJ konfiguracji (blad W runtime, NIE
         * kompilacji) - wybierz JEDEN styl I trzymaj sie go.
         */
        System.out.println("STARE: authorizeRequests()+antMatchers(...) - deprecated, do usuniecia W Security 7.0.");
        System.out.println("NOWE: authorizeHttpRequests()+requestMatchers(...) - AKTUALNY standard, uzyty W Lesson02 - NIGDY nie mieszaj obu stylow W 1 konfiguracji.");
    }

    private static void explainEnableGlobalMethodSecurityVsEnableMethodSecurity() {
        System.out.println("\n=== @EnableGlobalMethodSecurity (STARE) vs @EnableMethodSecurity (NOWE) ===");
        /*
         * `@EnableGlobalMethodSecurity(prePostEnabled = true)` -
         * deprecated OD Security 6.0. Wymagal JAWNEGO wlaczenia
         * `prePostEnabled = true`, zeby `@PreAuthorize`/`@PostAuthorize`
         * DZIALALY.
         *
         * `@EnableMethodSecurity` - AKTUALNY zamiennik, `prePostEnabled
         * = true` JEST JUZ DOMYSLNIE - NIE trzeba go jawnie ustawiac.
         * Pogłębione W Lesson10 (`MethodSecurity`), ktory uzywa SpEL
         * poznanego W `_20_spring_core/Lesson17_SpelBasics`.
         */
        System.out.println("STARE: @EnableGlobalMethodSecurity(prePostEnabled=true) - deprecated OD Security 6.0.");
        System.out.println("NOWE: @EnableMethodSecurity - prePostEnabled=true JUZ DOMYSLNIE, zapowiedz Lesson10.");
    }

    @RestController
    static class DemoController {
        @GetMapping("/api/ping")
        String ping() {
            return "pong (zabezpieczone AKTUALNYM API)";
        }
    }

    @Configuration
    static class ModernSecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // AKTUALNY styl: authorizeHttpRequests + requestMatchers (NIE authorizeRequests/antMatchers).
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/api/ping").authenticated()
                            .anyRequest().denyAll())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }

    @SpringBootApplication
    static class ModernApiApp {
    }

    private static void demonstrateCurrentApiWorking() throws Exception {
        System.out.println("\n=== DOWOD: AKTUALNE API (authorizeHttpRequests+requestMatchers) DZIALA NA TYM PROJEKCIE ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("spring.security.user.name", "user");
        props.setProperty("spring.security.user.password", "sekret123");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ModernApiApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();
            String encoded = java.util.Base64.getEncoder().encodeToString("user:sekret123".getBytes());
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/ping"))
                    .header("Authorization", "Basic " + encoded)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("GET /api/ping (AKTUALNE API) -> status: " + response.statusCode() + ", body: " + response.body());
            System.out.println("-> Skompilowalo sie I dziala NA Spring Security 6.x/Boot 3.4.4 - TO jest kod, ktory MOZESZ bezpiecznie kopiowac.");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }
}
