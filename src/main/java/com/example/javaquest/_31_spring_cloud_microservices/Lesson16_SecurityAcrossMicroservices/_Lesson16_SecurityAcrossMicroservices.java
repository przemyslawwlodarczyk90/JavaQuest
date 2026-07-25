package com.example.javaquest._31_spring_cloud_microservices.Lesson16_SecurityAcrossMicroservices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

public class _Lesson16_SecurityAcrossMicroservices {

    // WSPOLNY klucz podpisu - W PRAWDZIWYM systemie serwisy WERYFIKUJACE token NIE MUSZA znac
    // klucza PODPISUJACEGO (moga uzyc klucza PUBLICZNEGO, jesli token podpisany asymetrycznie
    // RS256, LUB odpytac WSPOLNY Authorization Server, `_19_security_basics/Lesson06`) - TU,
    // DLA prostoty demo (jak `_24_spring_security`), UZYWAMY WSPOLNEGO klucza symetrycznego.
    private static final SecretKey KLUCZ = Jwts.SIG.HS256.key().build();

    @RestController
    static class PaymentsController {
        @GetMapping("/payments/{id}")
        ResponseEntity<String> getPayment(@PathVariable String id, @RequestHeader(value = "Authorization", required = false) String authHeader) {
            String uzytkownik = walidujToken(authHeader);
            if (uzytkownik == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Brak lub nieprawidlowy token");
            }
            return ResponseEntity.ok("Platnosc #" + id + " ZATWIERDZONA DLA " + uzytkownik);
        }
    }

    @RestController
    static class OrdersController {
        private final int paymentsPort;

        OrdersController(int paymentsPort) {
            this.paymentsPort = paymentsPort;
        }

        @GetMapping("/orders/{id}")
        ResponseEntity<String> getOrder(@PathVariable String id, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
            String uzytkownik = walidujToken(authHeader);
            if (uzytkownik == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Brak lub nieprawidlowy token");
            }

            // PROPAGACJA tokenu DALEJ, DO payments-service - orders-service NIE "ufa NA SLEPO"
            // wewnetrznej sieci, PRZEKAZUJE ORYGINALNY token, zeby payments-service SAM ZROBIL
            // WLASNA walidacje (defense in depth, NIE TYLKO "brama sprawdzila, wiec ufamy").
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + paymentsPort + "/payments/" + id))
                    .header("Authorization", authHeader)
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return ResponseEntity.status(response.statusCode())
                    .body("Zamowienie #" + id + " DLA " + uzytkownik + " | payments-service -> " + response.body());
        }
    }

    private static String walidujToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        try {
            Claims claims = Jwts.parser().verifyWith(KLUCZ).build()
                    .parseSignedClaims(authHeader.substring(7)).getPayload();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class PaymentsApp {
        @Bean
        PaymentsController paymentsController() {
            return new PaymentsController();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class OrdersApp {
        private final int paymentsPort;

        OrdersApp(int paymentsPort) {
            this.paymentsPort = paymentsPort;
        }

        @Bean
        OrdersController ordersController() {
            return new OrdersController(paymentsPort);
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 16: Bezpieczenstwo MIEDZY mikroserwisami - propagacja JWT ===");

        /*
         * ============================================================
         * 📦 PROBLEM: uwierzytelnienie NIE KONCZY SIE NA bramie
         * ============================================================
         * `_24_spring_security` uczyl uwierzytelniania NA GRANICY
         * (Gateway/kontroler). W mikroserwisach zadanie CZESTO
         * PRZECHODZI DALEJ (orders-service -> payments-service) -
         * PYTANIE: czy payments-service MA "NA SLEPO ufac" orders-
         * -service (bo "jest W wewnetrznej sieci"), CZY SAM ZROBIC
         * WLASNA walidacje?
         *
         * Odpowiedz "defense in depth": PROPAGUJ ORYGINALNY token
         * DALEJ (NIE twórz nowego, NIE "odswiezaj" zaufania) - KAZDY
         * serwis PO DRODZE SAM WERYFIKUJE token, NIEZALEZNIE.
         */
        System.out.println("Propagacja JWT: orders-service PRZEKAZUJE ORYGINALNY token DO payments-service - KAZDY serwis SAM go WERYFIKUJE.");

        ConfigurableApplicationContext paymentsContext = null;
        ConfigurableApplicationContext ordersContext = null;
        try {
            paymentsContext = startPayments();
            int paymentsPort = Integer.parseInt(paymentsContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("payments-service NA porcie " + paymentsPort + ".");

            ordersContext = startOrders(paymentsPort);
            int ordersPort = Integer.parseInt(ordersContext.getEnvironment().getProperty("local.server.port"));
            System.out.println("orders-service NA porcie " + ordersPort + ".");

            String validToken = generateToken("kasia", 60_000);
            String expiredToken = generateToken("kasia", -1_000);

            demonstrateScenario("BEZ tokenu", ordersPort, null);
            demonstrateScenario("Z WAZNYM tokenem", ordersPort, validToken);
            demonstrateScenario("Z WYGASLYM tokenem", ordersPort, expiredToken);
        } finally {
            if (ordersContext != null) {
                ordersContext.close();
            }
            if (paymentsContext != null) {
                paymentsContext.close();
            }
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Token JWT PROPAGOWANY DALEJ (naglowek `Authorization`) -
         *   NIE "nowy token zaufania", TEN SAM token AZ DO KONCA
         *   lancucha wywolan.
         * - KAZDY serwis SAM WALIDUJE - awaria/pominiecie walidacji
         *   W JEDNYM serwisie NIE "otwiera" reszty systemu.
         * - Powiazanie Z `_19_security_basics/Lesson06` (OAuth2/OIDC)
         *   - W REALNYM systemie serwisy CZESTO WERYFIKUJA token
         *     PRZEZ klucz PUBLICZNY (JWK) OD WSPOLNEGO Authorization
         *     Servera, NIE przez WSPOLNY klucz symetryczny jak TU.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    private static String generateToken(String subject, long ttlMillis) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(new Date(now + ttlMillis))
                .signWith(KLUCZ)
                .compact();
    }

    private static ConfigurableApplicationContext startPayments() {
        return new SpringApplicationBuilder(PaymentsApp.class)
                .run("--spring.application.name=payments-service", "--server.port=0", "--logging.level.root=WARN");
    }

    private static ConfigurableApplicationContext startOrders(int paymentsPort) {
        return new SpringApplicationBuilder(OrdersApp.class)
                .initializers(context -> context.getBeanFactory().registerSingleton("paymentsPort", paymentsPort))
                .run("--spring.application.name=orders-service", "--server.port=0", "--logging.level.root=WARN");
    }

    private static void demonstrateScenario(String opis, int ordersPort, String token) throws Exception {
        System.out.println("\n--- Scenariusz: " + opis + " ---");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + ordersPort + "/orders/1")).GET();
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }
        HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode() + " | Body: " + response.body());
    }
}
