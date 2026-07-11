package com.example.javaquest._22_spring_web.Lesson07_DtoInRestApi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class _Lesson07_DtoInRestApi {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 7: DTO w REST API ===");

        /*
         * ============================================================
         * 📦 DLACZEGO NIE ZWRACAC ENCJI WPROST - POGLEBIENIE _17_architecture/Lesson07
         * ============================================================
         * `_17_architecture/Lesson07_DtoEntityMapper` uczyl KONCEPCJI
         * (dlaczego kontrakt API nie powinien byc sprzezony ze schematem
         * bazy). Tu pokazujemy to NA ZYWO, na poziomie Spring MVC:
         * kontroler zwracajacy ENCJE WPROST wycieka pola, ktore NIGDY
         * nie powinny opuscic serwera (hash hasla, wewnetrzne notatki).
         *
         * Jackson serializuje KAZDE publiczne pole/gettera obiektu - NIE
         * WIE, ze `passwordHash` jest "prywatny z natury biznesowej".
         * DTO (Data Transfer Object) to OSOBNA klasa/rekord, ktora
         * ZAWIERA TYLKO to, co API POWINNO ujawnic.
         */
        System.out.println("Zwrocenie ENCJI wprost z kontrolera = Jackson serializuje WSZYSTKIE jej pola, WLACZNIE z wrazliwymi.");

        demonstrateExposingEntityLeaksSensitiveFields();
        demonstrateResponseDtoHidesInternalFields();
        demonstrateRequestDtoSeparatesInputFromEntity();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Zwrocenie ENCJI bezposrednio z kontrolera = wyciek
         *   WEWNETRZNYCH pol (hasla, audyt, flagi) do klienta API.
         * - Response DTO = OSOBNA klasa zawierajaca TYLKO pola
         *   PRZEZNACZONE dla klienta - mapowanie RECZNE (`toDto(entity)`)
         *   lub przez narzedzie (MapStruct, `_13_libraries/Lesson21`).
         * - Request DTO = OSOBNA klasa dla WEJSCIA - pozwala na
         *   WALIDACJE (Lesson08) SPECYFICZNA dla operacji (np. `password`
         *   wymagane przy tworzeniu, ale NIEOBECNE przy edycji profilu).
         * - Korzysc DODATKOWA: zmiana STRUKTURY encji (baza danych) NIE
         *   LAMIE kontraktu API, dopoki mapper jest zaktualizowany -
         *   ENTITY i DTO EWOLUUJA NIEZALEZNIE.
         */
        System.out.println("\n=== KONIEC LEKCJI 7 ===");
    }

    // "Encja" - w prawdziwej aplikacji odpowiadalaby tabeli w bazie danych (patrz _12_hibernate).
    static class UserEntity {
        int id;
        String username;
        String passwordHash;
        String internalAuditNotes;

        UserEntity(int id, String username, String passwordHash, String internalAuditNotes) {
            this.id = id;
            this.username = username;
            this.passwordHash = passwordHash;
            this.internalAuditNotes = internalAuditNotes;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getPasswordHash() {
            return passwordHash;
        }

        public String getInternalAuditNotes() {
            return internalAuditNotes;
        }
    }

    @RestController
    static class LeakyController {
        @GetMapping("/users-raw/{id}")
        UserEntity getUser(@PathVariable int id) {
            // ZLE: zwracamy ENCJE WPROST - Jackson zserializuje WSZYSTKIE gettery, wlacznie z passwordHash.
            return new UserEntity(id, "jkowalski", "$2a$10$SUPERTAJNYHASHHASLA", "Konto oznaczone do przegladu przez zespol bezpieczenstwa");
        }
    }

    @SpringBootApplication
    static class LeakyApp {
    }

    private static void demonstrateExposingEntityLeaksSensitiveFields() throws Exception {
        System.out.println("\n=== ZLY PRZYKLAD: KONTROLER ZWRACA ENCJE WPROST ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(LeakyApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String body = httpGet(port, "/users-raw/1").body();
            System.out.println("GET /users-raw/1 -> " + body);
            System.out.println("-> UWAGA: odpowiedz zawiera 'passwordHash' i 'internalAuditNotes' - dane, ktore NIGDY nie powinny trafic do klienta API!");
        } finally {
            context.close();
        }
    }

    record UserResponseDto(int id, String username) {
    }

    static UserResponseDto toResponseDto(UserEntity entity) {
        // RECZNE mapowanie - w wiekszej aplikacji zastapione np. MapStruct (_13_libraries/Lesson21).
        return new UserResponseDto(entity.getId(), entity.getUsername());
    }

    @RestController
    static class SafeController {
        @GetMapping("/users/{id}")
        UserResponseDto getUser(@PathVariable int id) {
            UserEntity entity = new UserEntity(id, "jkowalski", "$2a$10$SUPERTAJNYHASHHASLA", "Konto oznaczone do przegladu przez zespol bezpieczenstwa");
            // DOBRZE: mapujemy encje na DTO PRZED zwroceniem - klient widzi TYLKO to, co POWINIEN.
            return toResponseDto(entity);
        }
    }

    @SpringBootApplication
    static class SafeApp {
    }

    private static void demonstrateResponseDtoHidesInternalFields() throws Exception {
        System.out.println("\n=== DOBRY PRZYKLAD: KONTROLER MAPUJE ENCJE NA Response DTO ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SafeApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            String body = httpGet(port, "/users/1").body();
            System.out.println("GET /users/1 -> " + body);
            System.out.println("-> Odpowiedz zawiera TYLKO 'id' i 'username' - passwordHash i internalAuditNotes NIGDY nie opuszcza serwera.");
        } finally {
            context.close();
        }
    }

    // Request DTO - CELOWO ma INNY ksztalt niz Response DTO (przyjmuje 'password' w postaci JAWNEJ, do zahashowania).
    record CreateUserRequest(String username, String password) {
    }

    static final AtomicInteger NEXT_USER_ID = new AtomicInteger(1);

    static UserEntity toEntity(CreateUserRequest request) {
        // W prawdziwej aplikacji: hashowanie hasla (_19_security_basics/Lesson02-03), NIE zapis jawnego tekstu.
        String simulatedHash = "$2a$10$" + Integer.toHexString(request.password().hashCode());
        return new UserEntity(NEXT_USER_ID.getAndIncrement(), request.username(), simulatedHash, "Nowo utworzone konto");
    }

    @RestController
    static class RequestDtoController {
        @PostMapping("/users")
        UserResponseDto createUser(@RequestBody CreateUserRequest request) {
            // Request DTO (wejscie) -> Entity (logika/przechowywanie) -> Response DTO (wyjscie) - TRZY ROZNE ksztalty danych.
            UserEntity entity = toEntity(request);
            return toResponseDto(entity);
        }
    }

    @SpringBootApplication
    static class RequestDtoApp {
    }

    private static void demonstrateRequestDtoSeparatesInputFromEntity() throws Exception {
        System.out.println("\n=== Request DTO (wejscie) != Entity (logika) != Response DTO (wyjscie) ===");

        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(RequestDtoApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/users"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\"nowak\",\"password\":\"TajneHaslo123\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("POST /users {username, password} -> " + response.body());
            System.out.println("-> Klient WYSLAL 'password', ale odpowiedz NIE zawiera go ani jego hasha - Request DTO i Response DTO maja CELOWO INNY ksztalt.");
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
