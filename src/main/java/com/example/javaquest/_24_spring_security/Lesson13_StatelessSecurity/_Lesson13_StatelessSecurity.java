package com.example.javaquest._24_spring_security.Lesson13_StatelessSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class _Lesson13_StatelessSecurity {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: SessionCreationPolicy.STATELESS ===");

        /*
         * ============================================================
         * 📦 DLACZEGO JWT + SESJA SIE WYKLUCZAJA (koncepcyjnie)
         * ============================================================
         * Powiazanie Z `_19_security_basics/Lesson04` (sesje) vs
         * `Lesson05` (JWT) - sesje I JWT to 2 ROZNE FILOZOFIE:
         * sesja = serwer PAMIETA stan (ID sesji -> dane W pamieci/Redis),
         * JWT = TOKEN SAM niesie stan (serwer NIC nie pamieta). LACZENIE
         * OBU (JWT + WCIAZ tworzona sesja) jest MARNOTRAWSTWEM - token
         * JUZ NIESIE wszystko, WIEC sesja jest NADMIAROWA.
         * `SessionCreationPolicy.STATELESS` MOWI Security: "NIGDY NIE
         * TWORZ ani NIE UZYWAJ `HttpSession`" - lekcja Lesson12 (JWT)
         * dzialala PRAWIDLOWO, ale NIE MIALA TEJ jawnej deklaracji -
         * TERAZ sprawdzimy, CZY sesja BYLA mimo wszystko tworzona.
         */
        System.out.println("SessionCreationPolicy.STATELESS = Security NIGDY nie tworzy/uzywa HttpSession - token (Lesson12) JUZ niesie CALY stan.");

        demonstrateStatelessCreatesNoSession();
        demonstrateStatefulDefaultCreatesSessionCookie();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `.sessionManagement(session -> session.sessionCreationPolicy(
         *   SessionCreationPolicy.STATELESS))` - JAWNE wylaczenie sesji.
         * - DOMYSLNA polityka to `IF_REQUIRED` - Security TWORZY sesje
         *   TYLKO wtedy, GDY mechanizm logowania TEGO WYMAGA. ZWERYFIKOWANE
         *   EMPIRYCZNIE (PONIZEJ): `httpBasic()` SAM Z SIEBIE NIE tworzy
         *   sesji (bezstanowy z natury - poswiadczenia lecacego W KAZDYM
         *   zadaniu), ale `formLogin()` TAK (MUSI PRZETRWAC przekierowanie
         *   302 PO zalogowaniu) - "IF_REQUIRED" oznacza WIEC "jesli
         *   mechanizm uwierzytelniania FAKTYCZNIE tego potrzebuje", NIE
         *   "zawsze PRZY pierwszym uwierzytelnieniu".
         * - STATELESS = KAZDE zadanie MUSI SAMO niesc DOWOD tozsamosci
         *   (token JWT) - serwer NIC NIE PAMIETA miedzy zadaniami.
         * - Zaleta STATELESS: SKALOWALNOSC POZIOMA BEZ dzielonego
         *   magazynu sesji (kazda instancja serwera moze zweryfikowac
         *   token SAMODZIELNIE).
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    static class JwtAuthenticationFilter extends OncePerRequestFilter {
        private final SecretKey key;

        JwtAuthenticationFilter(SecretKey key) {
            this.key = key;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                try {
                    Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(header.substring(7)).getPayload();
                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                    var authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException ignored) {
                    // Token nieprawidlowy - request idzie DALEJ jako NIEUWIERZYTELNIONY.
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    @RestController
    static class StatelessController {
        @GetMapping("/api/data")
        String data() {
            return "Dane Z bezstanowego API";
        }
    }

    // @Configuration + @EnableAutoConfiguration (NIE @SpringBootApplication) - TEN plik ma
    // WIECEJ NIZ 1 klase zrodlowa (StatelessApp/StatefulApp), a implicit component-scan
    // @SpringBootApplication ZNALAZLBY SASIADA jako kolejna klase @Configuration I
    // ZAREJESTROWAL JEGO WLASNY bean 'filterChain' PONOWNIE - dajac
    // BeanDefinitionOverrideException (zweryfikowane empirycznie, TA SAMA pulapka
    // udokumentowana W CLAUDE.md DLA `_20_spring_core`/`_21_spring_boot`/`_22_spring_web`/
    // `_23_spring_data_jpa`, TU NAPOTKANA PIERWSZY RAZ W `_24_spring_security`, bo TO
    // PIERWSZA lekcja Z 2 klasami `@SpringBootApplication` W JEDNYM pliku).
    @Configuration
    @EnableAutoConfiguration
    static class StatelessApp {
        @Bean
        StatelessController statelessController() {
            return new StatelessController();
        }

        @Bean
        SecretKey jwtKey() {
            return Jwts.SIG.HS256.key().build();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, SecretKey jwtKey) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .addFilterBefore(new JwtAuthenticationFilter(jwtKey), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

    private static void demonstrateStatelessCreatesNoSession() throws Exception {
        System.out.println("\n=== STATELESS: zadanie Z tokenem JWT NIE tworzy sesji (BRAK Set-Cookie JSESSIONID) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(StatelessApp.class)
                .properties(props)
                .run();
        try {
            SecretKey key = context.getBean(SecretKey.class);
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String token = Jwts.builder().subject("ewa").expiration(new Date(System.currentTimeMillis() + 60_000)).signWith(key).compact();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/data"))
                            .header("Authorization", "Bearer " + token)
                            .GET().build(),
                    HttpResponse.BodyHandlers.ofString());

            boolean hasSessionCookie = response.headers().allValues("Set-Cookie").stream().anyMatch(v -> v.contains("JSESSIONID"));
            System.out.println("GET /api/data Z tokenem JWT -> status: " + response.statusCode()
                    + ", naglowek Set-Cookie zawiera JSESSIONID: " + hasSessionCookie + " (oczekiwane: false - STATELESS)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    @RestController
    static class StatefulController {
        @GetMapping("/api/data")
        String data() {
            return "Dane Z domyslnie stanowego API";
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class StatefulApp {
        @Bean
        StatefulController statefulController() {
            return new StatefulController();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // BRAK jawnej sessionManagement(...) - DOMYSLNA polityka IF_REQUIRED. formLogin()
            // (NIE httpBasic()) - ZWERYFIKOWANE EMPIRYCZNIE PONIZEJ, ze SAM httpBasic() Z
            // domyslna polityka IF_REQUIRED NIE tworzy sesji (Spring NIE persystuje kontekstu
            // DLA bezstanowego z natury Basic Auth) - formLogin JEST z definicji zbudowany
            // WOKOL sesji (musi PRZETRWAC przekierowanie 302 PO zalogowaniu), WIEC TU
            // faktycznie TWORZY JSESSIONID.
            http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .formLogin(Customizer.withDefaults());
            return http.build();
        }
    }

    private static void demonstrateStatefulDefaultCreatesSessionCookie() throws Exception {
        System.out.println("\n=== formLogin() Z DOMYSLNA polityka IF_REQUIRED: sesja TWORZONA PO zalogowaniu ===");

        /*
         * PUŁAPKA odkryta empirycznie PRZY pisaniu tej lekcji: pierwotny plan zakladal, ze
         * SAM `httpBasic()` (BEZ formLogin) Z domyslna polityka `IF_REQUIRED` TEZ TWORZY sesje
         * PO udanym uwierzytelnieniu - ZWERYFIKOWANE, ze TO NIEPRAWDA W tej wersji Spring
         * Security (6.x/Boot 3.4.4): Basic Auth jest z natury bezstanowy (poswiadczenia
         * wysylane PRZY KAZDYM zadaniu), WIEC Security NIE WIDZI POWODU, zeby cokolwiek
         * zapisywac DO sesji - `Set-Cookie: JSESSIONID` NIE POJAWIL SIE. Zamieniono demo NA
         * `formLogin()`, KTORY z definicji WYMAGA sesji (MUSI PRZETRWAC przekierowanie 302).
         */
        Properties props = new Properties();
        props.setProperty("server.port", "0");
        props.setProperty("spring.security.user.name", "user");
        props.setProperty("spring.security.user.password", "sekret123");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(StatefulApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> loginPage = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login")).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            String csrfToken = extractCsrfToken(loginPage.body());

            String formBody = "username=user&password=sekret123&_csrf=" + java.net.URLEncoder.encode(csrfToken, java.nio.charset.StandardCharsets.UTF_8);
            HttpResponse<Void> loginResponse = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/login"))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString(formBody))
                            .build(),
                    HttpResponse.BodyHandlers.discarding());

            boolean hasSessionCookie = loginResponse.headers().allValues("Set-Cookie").stream().anyMatch(v -> v.contains("JSESSIONID"));
            System.out.println("POST /login (formLogin) -> status: " + loginResponse.statusCode()
                    + ", naglowek Set-Cookie zawiera JSESSIONID: " + hasSessionCookie
                    + " (oczekiwane: true - formLogin WYMAGA sesji, zeby PRZETRWAC przekierowanie)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }
    }

    private static final java.util.regex.Pattern CSRF_TOKEN_PATTERN = java.util.regex.Pattern.compile("name=\"_csrf\"[^>]*value=\"([^\"]+)\"");

    private static String extractCsrfToken(String html) {
        java.util.regex.Matcher matcher = CSRF_TOKEN_PATTERN.matcher(html);
        return matcher.find() ? matcher.group(1) : null;
    }
}
