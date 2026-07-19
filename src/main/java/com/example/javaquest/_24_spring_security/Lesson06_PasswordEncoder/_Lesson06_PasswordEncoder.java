package com.example.javaquest._24_spring_security.Lesson06_PasswordEncoder;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Properties;

public class _Lesson06_PasswordEncoder {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 6: PasswordEncoder - BCrypt PRZEZ Spring, NIE recznie ===");

        /*
         * ============================================================
         * 📦 TEN SAM ALGORYTM, INNY INTERFEJS
         * ============================================================
         * `_19_security_basics/Lesson03_BCrypt` uzywal bezposrednio
         * biblioteki `org.mindrot:jbcrypt` (`BCrypt.hashpw(...)`/
         * `BCrypt.checkpw(...)`). Spring Security OPAKOWUJE dokladnie
         * TEN SAM algorytm BCrypt W interfejs `PasswordEncoder`
         * (`encode(String)`/`matches(String, String)`) - KONCEPCYJNIE
         * IDENTYCZNY, tylko inny interfejs, ZINTEGROWANY Z resztą
         * Security (`UserDetailsService`, `AuthenticationProvider`).
         */
        System.out.println("BCryptPasswordEncoder = TEN SAM algorytm BCrypt co _19_security_basics/Lesson03 (jBCrypt), TYLKO opakowany W interfejs Springa.");

        demonstrateBCryptEncoderBasics();
        demonstrateDelegatingPasswordEncoderForMigration();
        demonstrateFullLoginFlowWithRealBCryptHashes();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `BCryptPasswordEncoder.encode(raw)` - KAZDE wywolanie daje
         *   INNY hash (losowa sol), ale `matches(raw, hash)` DZIALA
         *   DLA WSZYSTKICH z nich.
         * - `PasswordEncoderFactories.createDelegatingPasswordEncoder()` -
         *   DOMYSLNIE koduje NOWE hasla BCryptem (prefiks `{bcrypt}`),
         *   ale ROZPOZNAJE I OBSLUGUJE INNE prefiksy (`{noop}`, `{sha256}`...) -
         *   UMOZLIWIA STOPNIOWA MIGRACJE starych hasel BEZ wymuszania
         *   natychmiastowej zmiany WSZYSTKICH.
         * - W praktyce (Spring Boot 3.x): `PasswordEncoderFactories.
         *   createDelegatingPasswordEncoder()` to DOMYSLNY encoder,
         *   jesli SAM nie zarejestrujesz WLASNEGO beana `PasswordEncoder`.
         * - `{noop}` (Lesson05) BYLO uproszczeniem TYLKO DLA demo -
         *   PRAWDZIWA aplikacja ZAWSZE uzywa BCrypt (LUB silniejszego
         *   algorytmu) przez `PasswordEncoder`.
         */
        System.out.println("\n=== KONIEC LEKCJI 6 ===");
    }

    private static void demonstrateBCryptEncoderBasics() {
        System.out.println("\n=== BCryptPasswordEncoder - PODSTAWY (ta sama idea co jBCrypt Z _19_security_basics/Lesson03) ===");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "sekret123";
        String hash1 = encoder.encode(raw);
        String hash2 = encoder.encode(raw);

        System.out.println("hash1=" + hash1);
        System.out.println("hash2=" + hash2);
        System.out.println("hash1 != hash2 (rozna sol PRZY KAZDYM wywolaniu encode()): " + !hash1.equals(hash2));
        System.out.println("matches(\"sekret123\", hash1): " + encoder.matches(raw, hash1) + " (oczekiwane: true)");
        System.out.println("matches(\"sekret123\", hash2): " + encoder.matches(raw, hash2) + " (oczekiwane: true)");
        System.out.println("matches(\"zlehaslo\", hash1): " + encoder.matches("zlehaslo", hash1) + " (oczekiwane: false)");
    }

    private static void demonstrateDelegatingPasswordEncoderForMigration() {
        System.out.println("\n=== DelegatingPasswordEncoder - OBSLUGA WIELU formatow naraz (migracja) ===");

        PasswordEncoder delegating = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String freshlyEncoded = delegating.encode("sekret123");
        System.out.println("delegating.encode(\"sekret123\") -> " + freshlyEncoded + " (DOMYSLNIE prefiks {bcrypt})");
        System.out.println("matches(\"sekret123\", freshlyEncoded): " + delegating.matches("sekret123", freshlyEncoded) + " (oczekiwane: true)");

        // Symulacja "starego" hasla zapisanego W bazie DAWNO TEMU W formacie {noop} (jak W Lesson05) -
        // DelegatingPasswordEncoder ROZPOZNAJE prefiks I DALEJ POTRAFI je zweryfikowac, BEZ
        // wymuszania natychmiastowej migracji WSZYSTKICH kont.
        String legacyPlainPassword = "{noop}starehaslo";
        System.out.println("Haslo 'legacy' Z bazy: " + legacyPlainPassword);
        System.out.println("matches(\"starehaslo\", legacyPlainPassword): " + delegating.matches("starehaslo", legacyPlainPassword)
                + " (oczekiwane: true - DelegatingPasswordEncoder OBSLUGUJE STARY prefiks {noop} OBOK nowego {bcrypt})");
    }

    @RestController
    static class DemoController {
        @GetMapping("/api/secure-data")
        String secureData() {
            return "Dane dostepne PO uwierzytelnieniu PRAWDZIWYM hashem BCrypt";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        UserDetailsService userDetailsService(PasswordEncoder encoder) {
            // InMemoryUserDetailsManager (implementacja UserDetailsService+UserDetailsManager
            // Z samego Springa) - kodujemy haslo PRZEZ TEN SAM PasswordEncoder, ktory bedzie
            // uzyty PRZY WERYFIKACJI logowania (SPOJNOSC MIEDZY kodowaniem I sprawdzaniem).
            return new InMemoryUserDetailsManager(
                    User.withUsername("dorota")
                            .password(encoder.encode("realneHaslo!"))
                            .roles("USER")
                            .build()
            );
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }

    @SpringBootApplication
    static class BCryptLoginApp {
    }

    private static void demonstrateFullLoginFlowWithRealBCryptHashes() throws Exception {
        System.out.println("\n=== PELNY LOGIN Z PRAWDZIWYM hashem BCrypt (NIE {noop} jak W Lesson05) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(BCryptLoginApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> correct = httpGet(port, "dorota:realneHaslo!");
            System.out.println("GET /api/secure-data Z poprawnym haslem -> status: " + correct.statusCode() + " (oczekiwane: 200)");

            HttpResponse<String> wrong = httpGet(port, "dorota:zlehaslo");
            System.out.println("GET /api/secure-data Z BLEDNYM haslem -> status: " + wrong.statusCode() + " (oczekiwane: 401)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> httpGet(int port, String basicAuthCredentials) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String encoded = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes());
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/secure-data"))
                .header("Authorization", "Basic " + encoded)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
