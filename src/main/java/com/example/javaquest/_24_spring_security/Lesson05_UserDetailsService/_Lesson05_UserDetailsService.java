package com.example.javaquest._24_spring_security.Lesson05_UserDetailsService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

public class _Lesson05_UserDetailsService {

    record AppUser(String username, String password, String role) {
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 5: UserDetailsService - WLASNE zrodlo uzytkownikow ===");

        /*
         * ============================================================
         * 📦 OD JEDNEGO WBUDOWANEGO UZYTKOWNIKA DO WLASNEJ "BAZY" UZYTKOWNIKOW
         * ============================================================
         * Lesson01/04 uzywaly JEDNEGO, wbudowanego uzytkownika
         * (`spring.security.user.name`/`-password`) - W praktyce
         * ZAWSZE masz WIELU uzytkownikow, przechowywanych GDZIES
         * (baza danych, plik, LDAP...). `UserDetailsService` to
         * INTERFEJS Z JEDNA metoda (`loadUserByUsername`), KTORY
         * Security WOLA, ZEBY dowiedziec sie, KIM jest uzytkownik O
         * PODANEJ nazwie - Ty decydujesz, SKAD te dane pochodza.
         *
         * Powiazanie Z `_19_security_basics/Lesson01`: tam `record
         * User(String username, String password, String role)` byl
         * ZWYKLYM rekordem Javy, ktory SAM musiales porownywac. Tu
         * `UserDetailsService` to ten sam pomysl, ale JAKO FORMALNY
         * KONTRAKT Springa - implementujesz GO RAZ, a Security
         * AUTOMATYCZNIE woła go PRZY KAZDEJ probie logowania.
         */
        System.out.println("UserDetailsService.loadUserByUsername(nazwa) = TWOJ sposob dostarczenia uzytkownikow Security - baza/plik/LDAP, cokolwiek chcesz.");

        demonstrateCustomUserDetailsService();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `UserDetailsService.loadUserByUsername(String)` -
         *   JEDYNA metoda DO zaimplementowania.
         * - `User.withUsername(...).password(...).roles(...).build()` -
         *   fabryka budujaca `UserDetails` (implementacja wbudowana
         *   W Security).
         * - Prefiks `{noop}` PRZED haslem = "BEZ hashowania" - CELOWO
         *   uproszczone DLA TEJ lekcji (Lesson06 pokaze PRAWDZIWY
         *   `PasswordEncoder`/BCrypt).
         * - `UsernameNotFoundException` DLA nieznanego uzytkownika -
         *   Security TLUMACZY GO NA TEN SAM ogolny blad logowania CO
         *   zle haslo (401, BEZ ujawniania, CZY uzytkownik ISTNIEJE -
         *   powiazanie Z `_19_security_basics/Lesson01`, zasada
         *   "nie ujawniaj, czy login istnieje").
         */
        System.out.println("\n=== KONIEC LEKCJI 5 ===");
    }

    static class InMemoryUserDetailsService implements UserDetailsService {
        private final Map<String, AppUser> users = Map.of(
                "alice", new AppUser("alice", "{noop}alicepass", "USER"),
                "bob", new AppUser("bob", "{noop}bobpass", "ADMIN")
        );

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AppUser user = users.get(username);
            if (user == null) {
                // CELOWO ta sama wiadomosc/typ wyjatku DLA "nie ma takiego uzytkownika" - Security
                // I TAK zwroci ogolny 401, NIE ujawniajac, CZY konto ISTNIEJE.
                throw new UsernameNotFoundException("Nie znaleziono uzytkownika: " + username);
            }
            return User.withUsername(user.username())
                    .password(user.password())
                    .roles(user.role())
                    .build();
        }
    }

    @RestController
    static class DemoController {
        @GetMapping("/api/whoami")
        String whoami() {
            return "Uwierzytelniony PRZEZ wlasny UserDetailsService";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsService();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }

    @SpringBootApplication
    static class UserDetailsApp {
    }

    private static void demonstrateCustomUserDetailsService() throws Exception {
        System.out.println("\n=== WLASNY UserDetailsService Z 2 uzytkownikami (alice, bob) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(UserDetailsApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> aliceCorrect = httpGet(port, "alice:alicepass");
            System.out.println("GET /api/whoami jako 'alice' Z poprawnym haslem -> status: " + aliceCorrect.statusCode() + " (oczekiwane: 200)");

            HttpResponse<String> aliceWrongPassword = httpGet(port, "alice:zlehaslo");
            System.out.println("GET /api/whoami jako 'alice' Z BLEDNYM haslem -> status: " + aliceWrongPassword.statusCode() + " (oczekiwane: 401)");

            HttpResponse<String> unknownUser = httpGet(port, "carol:cokolwiek");
            System.out.println("GET /api/whoami jako NIEISTNIEJACY 'carol' -> status: " + unknownUser.statusCode()
                    + " (oczekiwane: 401 - TA SAMA odpowiedz CO zle haslo, ZERO ujawnienia informacji)");

            HttpResponse<String> bobCorrect = httpGet(port, "bob:bobpass");
            System.out.println("GET /api/whoami jako 'bob' Z poprawnym haslem -> status: " + bobCorrect.statusCode() + " (oczekiwane: 200)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> httpGet(int port, String basicAuthCredentials) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String encoded = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes());
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/whoami"))
                .header("Authorization", "Basic " + encoded)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
