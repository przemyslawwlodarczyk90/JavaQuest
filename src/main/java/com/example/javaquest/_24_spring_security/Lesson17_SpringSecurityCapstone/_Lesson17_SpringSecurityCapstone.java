package com.example.javaquest._24_spring_security.Lesson17_SpringSecurityCapstone;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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

public class _Lesson17_SpringSecurityCapstone {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 17 (KAPSZTON): JavaQuest Secure API - WSZYSTKIE 16 lekcji Spring Security razem ===");

        /*
         * ============================================================
         * 📦 KAPSZTON - PORÓWNANIE Z `_19_security_basics/Lesson21`
         * ============================================================
         * `_19_security_basics/Lesson21_OwaspTop10OverviewAndCapstone`
         * zbudowal PODOBNE API (login -> BCrypt -> JWT -> RBAC) CALKOWICIE
         * RECZNIE, NA surowym `com.sun.net.httpserver.HttpServer` - kazdy
         * filtr, kazde sprawdzenie roli, kazda serializacja bledu byla
         * NAPISANA OD ZERA. TEN kapszton buduje DOKLADNIE TA SAMA
         * FUNKCJONALNOSC, ale Spring Security ROBI ZA CIEBIE: routing
         * bezpieczenstwa (Lesson02), role (Lesson07), hashowanie
         * (Lesson06), autoryzacje metodowa (Lesson10), CORS (Lesson14),
         * spojne bledy (Lesson16) - PORÓWNAJ OBJETOSC kodu OBU podejsc.
         */
        System.out.println("Ten sam scenariusz co _19_security_basics/Lesson21 (recznie), ale TERAZ Spring Security robi ciezka prace za Ciebie.");

        Properties props = new Properties();
        props.setProperty("server.port", "0");
        System.setProperty("spring.autoconfigure.exclude", "");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SecureApiApp.class)
                .properties(props)
                .run();
        try {
            SecretKey key = context.getBean(SecretKey.class);
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();

            System.out.println("\n--- SCENARIUSZ 1: Logowanie 'kasia' (poprawne haslo) - UserDetailsService+BCrypt (Lesson05-06) ---");
            String kasiaToken = login(client, port, "kasia", "haslo123");
            System.out.println("Token wydany: " + (kasiaToken != null));

            System.out.println("\n--- SCENARIUSZ 2: Logowanie 'kasia' (BLEDNE haslo) ---");
            HttpResponse<String> badLogin = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/login"))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString("username=kasia&password=zlehaslo"))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("status: " + badLogin.statusCode() + " (oczekiwane: 401)");

            System.out.println("\n--- SCENARIUSZ 3: GET /api/public/info BEZ tokenu - permitAll (Lesson02/09) ---");
            System.out.println("status: " + get(client, port, "/api/public/info", null).statusCode() + " (oczekiwane: 200)");

            System.out.println("\n--- SCENARIUSZ 4: GET /api/profile Z tokenem 'kasia' - authenticated() (Lesson09/12/13) ---");
            System.out.println("status: " + get(client, port, "/api/profile", kasiaToken).statusCode() + " (oczekiwane: 200)");

            System.out.println("\n--- SCENARIUSZ 5: GET /api/profile BEZ tokenu - WLASNY JSON 401 (Lesson16) ---");
            HttpResponse<String> noAuth = get(client, port, "/api/profile", null);
            System.out.println("status: " + noAuth.statusCode() + ", body: " + noAuth.body() + " (oczekiwane: 401)");

            System.out.println("\n--- SCENARIUSZ 6: GET /api/admin/report Z tokenem 'kasia' (USER) - @PreAuthorize blokuje (Lesson10) ---");
            HttpResponse<String> deniedReport = get(client, port, "/api/admin/report", kasiaToken);
            System.out.println("status: " + deniedReport.statusCode() + ", body: " + deniedReport.body() + " (oczekiwane: 403)");

            System.out.println("\n--- SCENARIUSZ 7: Logowanie 'dyrektor' (ADMIN) I GET /api/admin/report ---");
            String dyrektorToken = login(client, port, "dyrektor", "haslo456");
            HttpResponse<String> allowedReport = get(client, port, "/api/admin/report", dyrektorToken);
            System.out.println("status: " + allowedReport.statusCode() + ", body: " + allowedReport.body() + " (oczekiwane: 200)");

            System.out.println("\n--- SCENARIUSZ 8: GET /api/profile Z ZMANIPULOWANYM tokenem ---");
            String tampered = kasiaToken.substring(0, kasiaToken.length() - 5) + "AAAAA";
            System.out.println("status: " + get(client, port, "/api/profile", tampered).statusCode() + " (oczekiwane: 401)");

            System.out.println("\n--- SCENARIUSZ 9: CORS - Origin SPOZA dozwolonej domeny (Lesson14) ---");
            HttpResponse<String> corsBlocked = client.send(
                    HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/public/info"))
                            .header("Origin", "https://zlosliwa-domena.example.com")
                            .GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("status: " + corsBlocked.statusCode() + " (oczekiwane: 403 - CORS ODRZUCONY NA SERWERZE)");
        } finally {
            context.close();
            System.clearProperty("spring.autoconfigure.exclude");
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE ROZDZIALU
         * ============================================================
         * - `UserDetailsService`+`PasswordEncoder` (Lesson05-06) - ZRODLO
         *   uzytkownikow I bezpieczne hashowanie.
         * - `SecurityFilterChain`+`authorizeHttpRequests` (Lesson02/09) -
         *   warstwowe regoly URL.
         * - Rola/`hasRole` (Lesson07) + `@PreAuthorize` (Lesson10) -
         *   autoryzacja NA 2 POZIOMACH (URL I metoda).
         * - WLASNY filtr JWT (Lesson12) + `STATELESS` (Lesson13) -
         *   bezstanowe API.
         * - `.cors(...)` (Lesson14) - kontrola pochodzenia zadan.
         * - `AuthenticationEntryPoint`/`AccessDeniedHandler` (Lesson16) -
         *   SPOJNE, JSON-owe bledy.
         * - Lesson01/03/04/08/11 (podstawy/historia/formLogin/WLASNA
         *   strona logowania) I Lesson15 (OAuth2) SA CZESCIA TEJ SAMEJ
         *   "skrzynki narzedziowej" - W TYM kapsztonie wybrano
         *   PODEJSCIE API+JWT (jak `_19_security_basics/Lesson21`),
         *   ale W aplikacji Z panelem webowym rownie dobrze
         *   pasowalyby formLogin+WLASNA strona logowania.
         * Spring Security = TA SAMA teoria bezpieczenstwa (`_19_security_
         * basics`), ZAIMPLEMENTOWANA DEKLARATYWNIE - mniej kodu, wiecej
         * konfiguracji, ta sama gwarancja bezpieczenstwa.
         */
        System.out.println("\n=== KONIEC LEKCJI 17 I KONIEC ROZDZIALU _24_spring_security ===");
    }

    static class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"type\":\"about:blank\",\"title\":\"Brak uwierzytelnienia\",\"status\":401}");
        }
    }

    static class JsonAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"type\":\"about:blank\",\"title\":\"Brak uprawnien\",\"status\":403}");
        }
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
                    String rolesClaim = claims.get("roles", String.class);
                    List<SimpleGrantedAuthority> authorities = List.of(rolesClaim.split(",")).stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .toList();
                    var authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException ignored) {
                    // Token nieprawidlowy - BRAK Authentication -> AuthenticationEntryPoint (401).
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    @Service
    static class ReportService {
        @PreAuthorize("hasRole('ADMIN')")
        String generateConfidentialReport() {
            return "Raport poufny: przychody Q3 = 1 250 000 PLN";
        }
    }

    @RestController
    static class AuthController {
        private final SecretKey key;
        private final UserDetailsService userDetailsService;
        private final PasswordEncoder encoder;

        @Autowired
        AuthController(SecretKey key, UserDetailsService userDetailsService, PasswordEncoder encoder) {
            this.key = key;
            this.userDetailsService = userDetailsService;
            this.encoder = encoder;
        }

        @PostMapping("/api/login")
        ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails == null || !encoder.matches(password, userDetails.getPassword())) {
                return ResponseEntity.status(401).body("Nieprawidlowe dane logowania");
            }
            String roles = userDetails.getAuthorities().stream()
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");
            String token = Jwts.builder()
                    .subject(username)
                    .claim("roles", roles)
                    .expiration(new Date(System.currentTimeMillis() + 5 * 60_000))
                    .signWith(key)
                    .compact();
            return ResponseEntity.ok(token);
        }
    }

    @RestController
    static class ApiController {
        private final ReportService reportService;

        @Autowired
        ApiController(ReportService reportService) {
            this.reportService = reportService;
        }

        @GetMapping("/api/public/info")
        String publicInfo() {
            return "Informacja publiczna - JavaQuest Secure API";
        }

        @GetMapping("/api/profile")
        String profile() {
            return "Profil zalogowanego uzytkownika";
        }

        @GetMapping("/api/admin/report")
        String adminReport() {
            // URL rule (nizej) wymaga TYLKO authenticated() - TO @PreAuthorize NA
            // ReportService.generateConfidentialReport() FAKTYCZNIE broni tego zasobu (Lesson10).
            return reportService.generateConfidentialReport();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableMethodSecurity
    static class SecureApiApp {
        @Bean
        SecretKey jwtKey() {
            return Jwts.SIG.HS256.key().build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        UserDetailsService userDetailsService(PasswordEncoder encoder) {
            return new InMemoryUserDetailsManager(
                    User.withUsername("kasia").password(encoder.encode("haslo123")).roles("USER").build(),
                    User.withUsername("dyrektor").password(encoder.encode("haslo456")).roles("USER", "ADMIN").build()
            );
        }

        @Bean
        ReportService reportService() {
            return new ReportService();
        }

        @Bean
        AuthController authController(SecretKey key, UserDetailsService userDetailsService, PasswordEncoder encoder) {
            return new AuthController(key, userDetailsService, encoder);
        }

        @Bean
        ApiController apiController(ReportService reportService) {
            return new ApiController(reportService);
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("https://javaquest.example.com"));
            config.setAllowedMethods(List.of("GET", "POST"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, SecretKey key,
                                         @Qualifier("corsConfigurationSource") CorsConfigurationSource corsSource) throws Exception {
            http.cors(cors -> cors.configurationSource(corsSource))
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling(handling -> handling
                            .authenticationEntryPoint(new JsonAuthenticationEntryPoint())
                            .accessDeniedHandler(new JsonAccessDeniedHandler()))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/login").permitAll()
                            .requestMatchers("/api/public/**").permitAll()
                            .anyRequest().authenticated())
                    .addFilterBefore(new JwtAuthenticationFilter(key), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    }

    private static String login(HttpClient client, int port, String username, String password) throws Exception {
        HttpResponse<String> response = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/login"))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString("username=" + username + "&password=" + password))
                        .build(),
                HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200 ? response.body() : null;
    }

    private static HttpResponse<String> get(HttpClient client, int port, String path, String bearerToken) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET();
        if (bearerToken != null) {
            builder.header("Authorization", "Bearer " + bearerToken);
        }
        return client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
