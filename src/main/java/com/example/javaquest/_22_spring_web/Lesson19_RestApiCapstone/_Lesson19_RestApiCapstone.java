package com.example.javaquest._22_spring_web.Lesson19_RestApiCapstone;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class _Lesson19_RestApiCapstone {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 19 (KAPSZTON): JavaQuest Tasks API - Spring MVC ===");

        /*
         * ============================================================
         * 📦 KAPSZTON - WSZYSTKO Z ROZDZIALU RAZEM
         * ============================================================
         * Ten sam mini-projekt "JavaQuest Tasks API" co
         * `_18_rest_api/Lesson20` (tam: surowy `HttpServer`), TERAZ
         * ZAIMPLEMENTOWANY W Spring MVC - DOSKONALA okazja, zeby
         * PORÓWNAC ilosc kodu "recznie" vs "frameworkiem" DLA
         * IDENTYCZNEGO scenariusza. Laczy:
         * - Lesson01-04: `@RestController`/`@GetMapping`/`@PathVariable`/
         *   `@RequestParam`.
         * - Lesson05-07: `@RequestBody`, `ResponseEntity`, DTO (Request/
         *   Response).
         * - Lesson08-10: `@Valid`, `@RestControllerAdvice`,
         *   `ProblemDetail` (RFC 7807).
         * - Lesson12-13: stronicowanie, sortowanie, filtrowanie.
         * - Lesson15-16: CORS, `HandlerInterceptor` (logowanie).
         */
        System.out.println("Kapszton laczy WSZYSTKIE 18 poprzednich lekcji w 1 dzialajacym mini-API - Spring MVC ZAMIAST surowego HttpServer z _18_rest_api/Lesson20.");

        runEndToEndScenarios();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE ROZDZIALU
         * ============================================================
         * - Spring MVC AUTOMATYZUJE: routing (`@GetMapping` itd.),
         *   parsowanie JSON (`@RequestBody`), walidacje (`@Valid`),
         *   obsluge bledow (`@RestControllerAdvice`), negocjacje tresci
         *   (`produces`), CORS - WSZYSTKO, co W `_18_rest_api/Lesson20`
         *   pisalismy RECZNIE NA surowym `HttpServer`.
         * - Rozdziel logike NA warstwy (kontroler/serwis/"repozytorium")
         *   NAWET W malym projekcie - LATWIEJ o testowalnosc I zmiane.
         * - Nastepny rozdzial (`_23_spring_data_jpa`) zastapi
         *   `ConcurrentHashMap` (nasza "baza W pamieci") PRAWDZIWA
         *   baza danych PRZEZ Spring Data - reszta (kontrolery/DTO/
         *   walidacja/obsluga bledow) POZOSTANIE PRAWIE BEZ ZMIAN.
         */
        System.out.println("\n=== KONIEC LEKCJI 19 I ROZDZIALU _22_spring_web ===");
    }

    // ============================================================
    // Model domenowy ("encja") - w pamieci, ConcurrentHashMap jako "baza".
    // ============================================================
    static class TaskEntity {
        final String id;
        String title;
        boolean done;
        final Instant createdAt;

        TaskEntity(String id, String title) {
            this.id = id;
            this.title = title;
            this.done = false;
            this.createdAt = Instant.now();
        }
    }

    // ============================================================
    // DTO - Request/Response (Lesson07)
    // ============================================================
    record CreateTaskRequest(@NotBlank(message = "Tytul nie moze byc pusty") @Size(max = 200, message = "Tytul maksymalnie 200 znakow") String title) {
    }

    record TaskResponse(String id, String title, boolean done, Instant createdAt) {
        static TaskResponse of(TaskEntity entity) {
            return new TaskResponse(entity.id, entity.title, entity.done, entity.createdAt);
        }
    }

    record PageResponse<T>(List<T> content, int page, int size, long totalElements, int totalPages) {
        static <T> PageResponse<T> of(List<T> allItems, int page, int size) {
            int totalElements = allItems.size();
            int totalPages = Math.max(1, (int) Math.ceil((double) totalElements / size));
            int fromIndex = Math.min(page * size, totalElements);
            int toIndex = Math.min(fromIndex + size, totalElements);
            return new PageResponse<>(allItems.subList(fromIndex, toIndex), page, size, totalElements, totalPages);
        }
    }

    // ============================================================
    // "Repozytorium" w pamieci - zapowiedz _23_spring_data_jpa.
    // ============================================================
    static class TaskRepository {
        private final Map<String, TaskEntity> storage = new ConcurrentHashMap<>();
        private final AtomicInteger nextId = new AtomicInteger(1);

        TaskEntity save(String title) {
            String id = "T" + nextId.getAndIncrement();
            TaskEntity entity = new TaskEntity(id, title);
            storage.put(id, entity);
            return entity;
        }

        Optional<TaskEntity> findById(String id) {
            return Optional.ofNullable(storage.get(id));
        }

        List<TaskEntity> findAll() {
            return new ArrayList<>(storage.values());
        }

        boolean deleteById(String id) {
            return storage.remove(id) != null;
        }
    }

    static class TaskNotFoundException extends RuntimeException {
        TaskNotFoundException(String id) {
            super("Zadanie o ID '" + id + "' nie zostalo znalezione");
        }
    }

    // ============================================================
    // Kontroler - Lesson01-06, Lesson12-13
    // ============================================================
    @RestController
    @RequestMapping("/api/tasks")
    static class TaskController {
        private final TaskRepository repository = new TaskRepository();

        TaskController() {
            repository.save("Napisac lekcje 19");
            repository.save("Sprawdzic kompilacje");
            repository.save("Uruchomic testy");
        }

        @GetMapping
        PageResponse<TaskResponse> listTasks(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam Optional<Boolean> done) {
            List<TaskEntity> filtered = repository.findAll().stream()
                    .filter(t -> done.map(d -> d.equals(t.done)).orElse(true))
                    .sorted(Comparator.comparing(t -> t.id))
                    .collect(Collectors.toList());
            return PageResponse.of(filtered.stream().map(TaskResponse::of).collect(Collectors.toList()), page, size);
        }

        @GetMapping("/{id}")
        TaskResponse getTask(@PathVariable String id) {
            TaskEntity entity = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
            return TaskResponse.of(entity);
        }

        @PostMapping
        ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
            TaskEntity entity = repository.save(request.title());
            return ResponseEntity.created(URI.create("/api/tasks/" + entity.id)).body(TaskResponse.of(entity));
        }

        @PutMapping("/{id}")
        TaskResponse updateTask(@PathVariable String id, @Valid @RequestBody CreateTaskRequest request) {
            TaskEntity entity = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
            entity.title = request.title();
            return TaskResponse.of(entity);
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        void deleteTask(@PathVariable String id) {
            if (!repository.deleteById(id)) {
                throw new TaskNotFoundException(id);
            }
        }
    }

    // ============================================================
    // Globalna obsluga bledow - Lesson09-10 (ProblemDetail, RFC 7807)
    // ============================================================
    @RestControllerAdvice
    static class ApiExceptionHandler {

        @ExceptionHandler(TaskNotFoundException.class)
        ProblemDetail handleNotFound(TaskNotFoundException ex) {
            ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
            problem.setTitle("Zadanie nie znalezione");
            return problem;
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
            ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Nieprawidlowe dane wejsciowe");
            problem.setTitle("Blad walidacji");
            Map<String, String> fieldErrors = new LinkedHashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));
            problem.setProperty("errors", fieldErrors);
            return problem;
        }
    }

    // ============================================================
    // CORS - Lesson15
    // ============================================================
    static class TasksApiCorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**").allowedOrigins("https://javaquest-frontend.example.com").allowedMethods("*");
        }
    }

    // ============================================================
    // Interceptor - logowanie kazdego zadania (Lesson16)
    // ============================================================
    static final List<String> ACCESS_LOG = Collections.synchronizedList(new ArrayList<>());

    static class AccessLogInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            ACCESS_LOG.add(request.getMethod() + " " + request.getRequestURI());
            return true;
        }
    }

    static class TasksApiInterceptorConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new AccessLogInterceptor()).addPathPatterns("/api/**");
        }
    }

    @Configuration
    static class TasksApiWebConfig {
        @Bean
        WebMvcConfigurer tasksApiWebMvcConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    new TasksApiCorsConfig().addCorsMappings(registry);
                }

                @Override
                public void addInterceptors(InterceptorRegistry registry) {
                    new TasksApiInterceptorConfig().addInterceptors(registry);
                }
            };
        }
    }

    @SpringBootApplication
    static class TasksApiApp {
    }

    private static void runEndToEndScenarios() throws Exception {
        Properties props = new Properties();
        props.setProperty("server.port", "0");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(TasksApiApp.class)
                .properties(props)
                .run();
        try {
            int port = context.getEnvironment().getProperty("local.server.port", Integer.class);

            System.out.println("\n--- Scenariusz 1: lista zadan (stronicowanie) ---");
            System.out.println("GET /api/tasks?page=0&size=2 -> " + httpGet(port, "/api/tasks?page=0&size=2").body());

            System.out.println("\n--- Scenariusz 2: utworzenie nowego zadania (201 + Location) ---");
            HttpResponse<String> created = httpPost(port, "/api/tasks", "{\"title\":\"Nowe zadanie\"}");
            System.out.println("POST /api/tasks -> status: " + created.statusCode() + " (oczekiwane: 201), Location: " + created.headers().firstValue("Location").orElse("BRAK") + ", body: " + created.body());

            System.out.println("\n--- Scenariusz 3: pobranie NIEISTNIEJACEGO zadania (404, RFC 7807) ---");
            HttpResponse<String> notFound = httpGet(port, "/api/tasks/T999");
            System.out.println("GET /api/tasks/T999 -> status: " + notFound.statusCode() + " (oczekiwane: 404), body: " + notFound.body());

            System.out.println("\n--- Scenariusz 4: utworzenie Z PUSTYM tytulem (400, blad walidacji) ---");
            HttpResponse<String> invalid = httpPost(port, "/api/tasks", "{\"title\":\"\"}");
            System.out.println("POST /api/tasks (pusty tytul) -> status: " + invalid.statusCode() + " (oczekiwane: 400), body: " + invalid.body());

            System.out.println("\n--- Scenariusz 5: aktualizacja istniejacego zadania ---");
            HttpResponse<String> updated = httpPut(port, "/api/tasks/T1", "{\"title\":\"Zaktualizowany tytul\"}");
            System.out.println("PUT /api/tasks/T1 -> status: " + updated.statusCode() + " (oczekiwane: 200), body: " + updated.body());

            System.out.println("\n--- Scenariusz 6: usuniecie zadania (204 No Content) ---");
            HttpResponse<String> deleted = httpDelete(port, "/api/tasks/T2");
            System.out.println("DELETE /api/tasks/T2 -> status: " + deleted.statusCode() + " (oczekiwane: 204)");

            System.out.println("\n--- Interceptor: dziennik dostepu zebrany PRZEZ HandlerInterceptor ---");
            ACCESS_LOG.forEach(entry -> System.out.println("  " + entry));
        } finally {
            context.close();
        }
    }

    private static HttpResponse<String> httpGet(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> httpPost(int port, String path, String jsonBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> httpPut(int port, String path, String jsonBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> httpDelete(int port, String path) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).DELETE().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
