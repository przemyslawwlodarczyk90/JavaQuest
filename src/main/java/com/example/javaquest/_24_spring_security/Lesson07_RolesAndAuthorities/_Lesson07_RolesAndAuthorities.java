package com.example.javaquest._24_spring_security.Lesson07_RolesAndAuthorities;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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

public class _Lesson07_RolesAndAuthorities {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: Role I uprawnienia (GrantedAuthority) ===");

        /*
         * ============================================================
         * 📦 OD "CZY JESTES ZALOGOWANY" DO "CO WOLNO CI ZROBIC"
         * ============================================================
         * Lesson01-06 sprawdzaly TYLKO uwierzytelnienie (AuthN) - "czy
         * ktos JEST kim twierdzi, ze jest". Ta lekcja wprowadza
         * AUTORYZACJE (AuthZ, powiazanie Z `_19_security_basics/Lesson01`
         * I `Lesson07_AuthorizationPatternsAndRbac`) - "CO WOLNO danemu
         * uzytkownikowi zrobic".
         *
         * `GrantedAuthority` - interfejs reprezentujacy POJEDYNCZE
         * uprawnienie (String, np. "ROLE_ADMIN" LUB "ORDERS_DELETE").
         * KONWENCJA `ROLE_` - Spring Security TRAKTUJE role JAKO
         * SPECJALNY PRZYPADEK uprawnien Z prefiksem "ROLE_" - metody
         * `.roles("ADMIN")` (budowanie) I `.hasRole("ADMIN")` (sprawdzanie)
         * AUTOMATYCZNIE DODAJA/OCZEKUJA tego prefiksu, WIEC NIE piszesz
         * go recznie.
         */
        System.out.println("GrantedAuthority = pojedyncze uprawnienie (String). Role = uprawnienia Z prefiksem 'ROLE_' - dodawanym/oczekiwanym AUTOMATYCZNIE.");

        demonstrateRoleBasedAccessControl();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.roles("ADMIN")` PRZY budowaniu `UserDetails` -> WEWNETRZNIE
         *   ZAPISUJE uprawnienie "ROLE_ADMIN".
         * - `.hasRole("ADMIN")` PRZY autoryzacji -> WEWNETRZNIE
         *   SPRAWDZA uprawnienie "ROLE_ADMIN" (prefiks DODANY AUTOMATYCZNIE).
         * - `.hasAuthority("ROLE_ADMIN")` - TA SAMA sprawdzana wartosc,
         *   ale BEZ automatycznego prefiksu - musisz go podac SAM.
         * - `.hasAuthority("ORDERS_DELETE")` - uprawnienie BEZ prefiksu
         *   `ROLE_` (dokladnie DOSLOWNA nazwa) - uzyj `hasAuthority(...)`
         *   DLA uprawnien, KTORE NIE SA rolami.
         * - Brak wymaganej roli/uprawnienia (ale uwierzytelniony) -> 403
         *   Forbidden (NIE 401 - Security JUZ WIE, KIM jestes, ale
         *   ODMAWIA dostepu).
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    @RestController
    static class DemoController {
        @GetMapping("/user/profile")
        String userProfile() {
            return "Profil - dostepny DLA USER I ADMIN";
        }

        @GetMapping("/admin/panel")
        String adminPanel() {
            return "Panel administracyjny - TYLKO DLA ADMIN";
        }
    }

    @Configuration
    static class SecurityConfig {
        @Bean
        PasswordEncoder passwordEncoder() {
            // {noop} - CELOWO uproszczone (jak Lesson05), zeby SKUPIC sie NA rolach, NIE hashowaniu.
            return NoOpPasswordEncoder.getInstance();
        }

        @Bean
        UserDetailsService userDetailsService(PasswordEncoder encoder) {
            return new InMemoryUserDetailsManager(
                    User.withUsername("kasia").password(encoder.encode("haslo1")).roles("USER").build(),
                    User.withUsername("szef").password(encoder.encode("haslo2")).roles("USER", "ADMIN").build()
            );
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/user/**").hasRole("USER")
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }

    @SpringBootApplication
    static class RolesApp {
    }

    private static void demonstrateRoleBasedAccessControl() throws Exception {
        System.out.println("\n=== hasRole(\"ADMIN\") vs hasRole(\"USER\") - 2 uzytkownikow, rozne uprawnienia ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RolesApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            HttpResponse<String> kasiaOnUser = httpGet(port, "/user/profile", "kasia:haslo1");
            System.out.println("'kasia' (rola USER) -> GET /user/profile: status=" + kasiaOnUser.statusCode() + " (oczekiwane: 200)");

            HttpResponse<String> kasiaOnAdmin = httpGet(port, "/admin/panel", "kasia:haslo1");
            System.out.println("'kasia' (rola USER) -> GET /admin/panel: status=" + kasiaOnAdmin.statusCode() + " (oczekiwane: 403 - uwierzytelniona, ale BEZ roli ADMIN)");

            HttpResponse<String> szefOnUser = httpGet(port, "/user/profile", "szef:haslo2");
            System.out.println("'szef' (role USER+ADMIN) -> GET /user/profile: status=" + szefOnUser.statusCode() + " (oczekiwane: 200)");

            HttpResponse<String> szefOnAdmin = httpGet(port, "/admin/panel", "szef:haslo2");
            System.out.println("'szef' (role USER+ADMIN) -> GET /admin/panel: status=" + szefOnAdmin.statusCode() + " (oczekiwane: 200)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static HttpResponse<String> httpGet(int port, String path, String basicAuthCredentials) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String encoded = Base64.getEncoder().encodeToString(basicAuthCredentials.getBytes());
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Authorization", "Basic " + encoded)
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
