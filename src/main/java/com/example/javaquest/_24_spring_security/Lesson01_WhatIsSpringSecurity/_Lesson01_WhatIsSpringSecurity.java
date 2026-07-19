package com.example.javaquest._24_spring_security.Lesson01_WhatIsSpringSecurity;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Properties;

public class _Lesson01_WhatIsSpringSecurity {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 1: Czym jest Spring Security ===");

        /*
         * ============================================================
         * 📦 NOWY, OSTATNI ROZDZIAL SPRING - "JAK TO AUTOMATYZUJE FRAMEWORK"
         * ============================================================
         * `_19_security_basics` (21 lekcji) nauczyl Cie UWIERZYTELNIANIA
         * (`_19/Lesson01`) i AUTORYZACJI RECZNIE, czysta Java - wlasny
         * hashing BCrypt, wlasne generowanie/parsowanie JWT, wlasna
         * obsluga sesji/ciasteczek, wlasne sprawdzanie rol. Ten rozdzial
         * pokazuje, jak Spring Security AUTOMATYZUJE dokladnie TE SAME
         * mechanizmy - jedna zaleznosc Maven (`spring-boot-starter-
         * security`, juz dodana do `pom.xml`) i CALA aplikacja jest
         * domyslnie zabezpieczona, BEZ napisania ani linijki kodu
         * bezpieczenstwa.
         *
         * Spring Security TO, W ISTOCIE: LANCUCH FILTROW SERVLETOWYCH
         * (`jakarta.servlet.Filter`, znanych z `_07_servlets/Lesson14_
         * Filters`) zarejestrowany PRZED `DispatcherServlet` Spring MVC.
         * KAZDE zadanie HTTP MUSI NAJPIERW przejsc PRZEZ TEN lancuch
         * (uwierzytelnianie, potem autoryzacja), ZANIM w ogole dotrze DO
         * Twojego `@RestController`. W praktyce ten "lancuch filtrow" to
         * JEDEN zarejestrowany filtr servletowy (`FilterChainProxy`),
         * ktory WEWNATRZ deleguje DO calej serii mniejszych, Spring-owych
         * filtrow (uwierzytelniania, autoryzacji, CSRF, sesji...) -
         * kazda kolejna lekcja tego rozdzialu pogłębia JEDEN Z NICH.
         */
        System.out.println("Spring Security = lancuch Filter'ow (_07_servlets/Lesson14) PRZED DispatcherServlet - AUTOMATYZUJE to, co _19_security_basics uczyl robic recznie.");

        demonstrateAllEndpointsSecuredByDefault();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `spring-boot-starter-security` NA classpath = KAZDY
         *   endpoint jest DOMYSLNIE zabezpieczony (401 bez uwierzytelnienia).
         * - Domyslnie: JEDEN wbudowany uzytkownik "user" (haslo losowo
         *   generowane PRZY KAZDYM starcie, wypisywane W logu) - w tej
         *   lekcji NADPISANE na stale (`spring.security.user.name`/
         *   `-password`) DLA deterministycznego demo.
         * - Uwierzytelnianie DOMYSLNIE odbywa sie PRZEZ HTTP Basic Auth
         *   (naglowek `Authorization: Basic base64(user:haslo)`).
         * - TO WSZYSTKO dzialalo BEZ napisania ANI JEDNEJ linijki kodu
         *   Security - kolejne lekcje pokaza, JAK to SKONFIGUROWAC
         *   (wlasnych uzytkownikow, wlasne role, wlasny format tokenu...).
         * - Ważne dla CALEGO tego rozdzialu: `application.properties`
         *   tego projektu WYLACZA auto-konfiguracje Security DOMYSLNIE
         *   (`spring.autoconfigure.exclude=...SecurityAutoConfiguration...`),
         *   zeby NIE zablokowac 401-kami WSZYSTKICH innych lekcji Spring
         *   w tym repo (`_20`-`_22`) - KAZDA lekcja tego rozdzialu MUSI
         *   jawnie NADPISAC te wlasciwosc PRZEZ `System.setProperty(...)`
         *   (NIE przez `SpringApplicationBuilder.properties(...)` - TE
         *   maja NIZSZY priorytet NIZ classpath'owy `application.properties`
         *   i NIE nadpisza go, patrz komentarz PRZY `System.setProperty`
         *   nizej).
         */
        System.out.println("\n=== KONIEC LEKCJI 1 ===");
    }

    @RestController
    static class HelloController {
        @GetMapping("/hello")
        String hello() {
            return "Witaj w zabezpieczonym API!";
        }
    }

    @SpringBootApplication
    static class SecuredApp {
    }

    private static void demonstrateAllEndpointsSecuredByDefault() throws Exception {
        System.out.println("\n=== WSZYSTKIE endpointy SA domyslnie zabezpieczone - ZERO kodu ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        // Stale, znane poswiadczenia ZAMIAST losowo generowanego hasla (domyslne zachowanie
        // Security bez tej wlasciwosci) - dla deterministycznego, powtarzalnego demo.
        props.setProperty("spring.security.user.name", "user");
        props.setProperty("spring.security.user.password", "sekret123");

        /*
         * PULAPKA: `SpringApplicationBuilder.properties(...)` ustawia tzw. "default properties"
         * (`SpringApplication.setDefaultProperties`) - MAJA one NIZSZY priorytet NIZ classpath'owy
         * `application.properties`! Ustawienie TU `spring.autoconfigure.exclude=""` (Properties
         * powyzej) NIE nadpisaloby globalnego wylaczenia Security zdefiniowanego W
         * `application.properties` - zweryfikowane empirycznie (GET /hello dawal 200 BEZ
         * poswiadczen, mimo pozornego "nadpisania"). Java System properties MAJA WYZSZY priorytet
         * NIZ classpath'owy `application.properties`, wiec TU rzeczywiscie DZIALA nadpisanie.
         */
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SecuredApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> withoutAuth = httpGet(port, "/hello", null);
            System.out.println("GET /hello BEZ poswiadczen -> status: " + withoutAuth.statusCode()
                    + " (oczekiwane: 401) - Security ZABLOKOWAL zadanie, ZANIM dotarlo do HelloController.");

            HttpResponse<String> withCorrectAuth = httpGet(port, "/hello", "user:sekret123");
            System.out.println("GET /hello Z poprawnymi poswiadczeniami (HTTP Basic Auth) -> status: " + withCorrectAuth.statusCode()
                    + ", body: " + withCorrectAuth.body() + " (oczekiwane: 200)");

            HttpResponse<String> withWrongAuth = httpGet(port, "/hello", "user:zlehaslo");
            System.out.println("GET /hello Z BLEDNYM haslem -> status: " + withWrongAuth.statusCode() + " (oczekiwane: 401)");
        } finally {
            context.close();
            // SPRZATAMY System property - inaczej "wyciekloby" DO kolejnych demo/lekcji
            // URUCHAMIANYCH W TYM SAMYM procesie JVM.
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> httpGet(int port, String path, String basicAuthCredentials) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET();
        if (basicAuthCredentials != null) {
            String encoded = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes());
            builder.header("Authorization", "Basic " + encoded);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
