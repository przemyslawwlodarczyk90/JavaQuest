package com.example.javaquest._27_spring_test.Lesson20_SpringTestCapstone;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

public class _Lesson20_SpringTestCapstone {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20 (KAPSZTON): 'JavaQuest Tasks API' - pelny pakiet testow Spring ===");

        /*
         * ============================================================
         * 📦 KAPSZTON - LACZYMY WSZYSTKIE techniki Z Lesson01-19 W JEDNYM API
         * ============================================================
         * `TaskController` -> `TaskService` -> `TaskRepository` (JPA)
         * + `NotificationService` (zewnetrzna zaleznosc). Test
         * WYKORZYSTUJE: `@SpringBootTest(RANDOM_PORT)` +
         * `TestRestTemplate` (Lesson02-03/12), PRAWDZIWA baze H2
         * PRZEZ `TaskRepository` (Lesson06), `@MockitoBean` DLA
         * `NotificationService` (Lesson08), `@TestConfiguration` Z
         * DETERMINISTYCZNYM `Clock` (Lesson09), `@ActiveProfiles`
         * (Lesson10) I NA KONCU regule ArchUnit (Lesson18)
         * PILNUJACA, ze `TaskController` NIE OMIJA `TaskService`.
         */
        System.out.println("Kapszton LACZY: RANDOM_PORT+TestRestTemplate + PRAWDZIWA baza H2 + @MockitoBean + @TestConfiguration (Clock) + @ActiveProfiles + ArchUnit.");

        runAndReport(TasksApiCapstoneTest.class);
        checkArchitectureRule();

        System.out.println("\n=== KONIEC LEKCJI 20, KONIEC ROZDZIALU _27_spring_test ===");
    }

    // ============================================================
    // DOMENA
    // ============================================================
    @Entity(name = "CapstoneTask")
    static class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String title;
        String createdAt;

        Task() {
        }

        Task(String title, String createdAt) {
            this.title = title;
            this.createdAt = createdAt;
        }
    }

    interface TaskRepository extends JpaRepository<Task, Long> {
    }

    interface NotificationService {
        void notifyTaskCreated(String title);
    }

    static class TaskService {
        private final TaskRepository repository;
        private final NotificationService notificationService;
        private final Clock clock;

        TaskService(TaskRepository repository, NotificationService notificationService, Clock clock) {
            this.repository = repository;
            this.notificationService = notificationService;
            this.clock = clock;
        }

        Task createTask(String title) {
            Task task = new Task(title, clock.instant().toString());
            Task saved = repository.save(task);
            notificationService.notifyTaskCreated(title);
            return saved;
        }

        List<Task> findAll() {
            return repository.findAll();
        }
    }

    @RestController
    static class TaskController {
        private final TaskService taskService;

        TaskController(TaskService taskService) {
            this.taskService = taskService;
        }

        @PostMapping("/tasks")
        Map<String, Object> create(@RequestBody Map<String, String> body) {
            Task task = taskService.createTask(body.get("title"));
            return Map.of("id", task.id, "title", task.title, "createdAt", task.createdAt);
        }

        @GetMapping("/tasks")
        List<Map<String, Object>> list() {
            return taskService.findAll().stream()
                    .map(t -> Map.<String, Object>of("id", t.id, "title", t.title, "createdAt", t.createdAt))
                    .toList();
        }
    }

    @SpringBootApplication
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class TestApp {
        @Bean
        NotificationService notificationService() {
            return title -> {
                throw new UnsupportedOperationException("PRAWDZIWE powiadomienia NIE MOGA dzialac w tescie - dlatego @MockitoBean.");
            };
        }

        @Bean
        TaskService taskService(TaskRepository repository, NotificationService notificationService, Clock clock) {
            return new TaskService(repository, notificationService, clock);
        }

        @Bean
        Clock clock() {
            return Clock.systemUTC();
        }
    }

    @TestConfiguration
    static class FixedClockConfig {
        @Bean
        @Primary
        Clock fixedClock() {
            return Clock.fixed(Instant.parse("2026-03-01T09:00:00Z"), ZoneOffset.UTC);
        }
    }

    @SpringBootTest(classes = TestApp.class, webEnvironment = RANDOM_PORT, properties = {
            "spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"})
    @Import(FixedClockConfig.class)
    @ActiveProfiles("test")
    static class TasksApiCapstoneTest {
        @LocalServerPort
        int port;

        @Autowired
        TestRestTemplate restTemplate;

        @MockitoBean
        NotificationService notificationService;

        @Test
        void createTaskPersistsToRealDatabaseUsesFixedClockAndNotifiesMockedService() {
            String url = "http://localhost:" + port + "/tasks";
            Map<String, Object> response = restTemplate.postForObject(url, Map.of("title", "Napisac kapszton"), Map.class);

            assertThat(response.get("title")).isEqualTo("Napisac kapszton");
            assertThat(response.get("createdAt")).isEqualTo("2026-03-01T09:00:00Z");
            verify(notificationService).notifyTaskCreated("Napisac kapszton");
            System.out.println("POST /tasks -> " + response + " (Clock DETERMINISTYCZNY z @TestConfiguration, notify() zweryfikowany na @MockitoBean).");
        }

        @Test
        void listTasksReturnsRealDataFromDatabase() {
            restTemplate.postForObject("http://localhost:" + port + "/tasks", Map.of("title", "Zadanie A"), Map.class);
            restTemplate.postForObject("http://localhost:" + port + "/tasks", Map.of("title", "Zadanie B"), Map.class);

            List<?> tasks = restTemplate.getForObject("http://localhost:" + port + "/tasks", List.class);

            assertThat(tasks).hasSizeGreaterThanOrEqualTo(2);
            System.out.println("GET /tasks -> " + tasks.size() + " zadan (PRAWDZIWA baza H2, PRZEZ prawdziwy port RANDOM_PORT).");
        }
    }

    private static void checkArchitectureRule() {
        System.out.println("\n--- ArchUnit: TaskController NIE MOZE zalezec bezposrednio od TaskRepository ---");
        JavaClasses classes = new ClassFileImporter()
                .importPackages("com.example.javaquest._27_spring_test.Lesson20_SpringTestCapstone");

        ArchRule rule = noClasses()
                .that().haveSimpleName("TaskController")
                .should().dependOnClassesThat().haveSimpleNameEndingWith("Repository")
                .because("kontroler MUSI korzystac z repozytorium WYLACZNIE przez warstwe serwisowa");

        rule.check(classes);
        System.out.println("ArchUnit: REGULA PRZESZLA - TaskController POPRAWNIE korzysta TYLKO z TaskService.");
    }

    private static void runAndReport(Class<?> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println(testClass.getSimpleName() + " -> udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount());
    }
}
