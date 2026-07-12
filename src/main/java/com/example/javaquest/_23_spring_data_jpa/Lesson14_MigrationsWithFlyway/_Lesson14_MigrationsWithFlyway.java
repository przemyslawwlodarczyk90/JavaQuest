package com.example.javaquest._23_spring_data_jpa.Lesson14_MigrationsWithFlyway;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class _Lesson14_MigrationsWithFlyway {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 14: Integracja Flyway Z Spring Data JPA ===");

        /*
         * ============================================================
         * 📦 FLYWAY W SPRING BOOT - AUTOMATYCZNA INTEGRACJA
         * ============================================================
         * `_10_dao/Lesson25_DatabaseMigrations` uczyl RECZNEGO
         * odpalania Flyway przez `Flyway.configure()...load().migrate()`.
         * W Spring Boot NIC z tego NIE trzeba pisac - wystarczy, ze
         * `flyway-core` I `DataSource` (np. przez `spring-boot-starter-
         * data-jpa`) SA na classpath, a Spring Boot AUTOMATYCZNIE:
         *   1. tworzy `Flyway`,
         *   2. uruchamia `migrate()` PRZED zainicjalizowaniem
         *      `EntityManagerFactory` (WAZNA kolejnosc - schemat MUSI
         *      byc gotowy, ZANIM Hibernate sprobuje go zwalidowac),
         *   3. domyslnie szuka plikow W `classpath:db/migration`.
         *
         * `spring.flyway.enabled` DOMYSLNIE `true` - NIE trzeba go
         * jawnie wlaczac. W TYM PROJEKCIE oznacza to, ze KAZDA lekcja
         * W TYM rozdziale, ktora otwiera kontekst Spring Boot Z
         * DataSource, JUZ TERAZ CICHO uruchamia migracje `V1`/`V2` Z
         * dzielonego `src/main/resources/db/migration/` (tabela
         * 'users') - dokladnie ten log widziany W konsoli Lesson08/
         * Lesson11 ("Migrating schema ... create users table"), ktory
         * NIC nie mial wspolnego Z tematem tamtych lekcji.
         */
        System.out.println("spring.flyway.enabled=true DOMYSLNIE, gdy flyway-core + DataSource sa na classpath - migracje URUCHAMIAJA SIE SAME, PRZED Hibernate.");

        demonstrateDefaultFlywayLocationAndSharedMigrations();
        demonstrateIsolatedMigrationLocationWithIncrementalVersions();
        demonstrateDdlAutoValidateWithFlywayManagedSchema();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Flyway W Spring Boot = ZERO kodu, TYLKO pliki `.sql` NA
         *   classpath + (opcjonalnie) `spring.flyway.locations`.
         * - `spring.jpa.hibernate.ddl-auto` MUSI byc `validate` (LUB
         *   `none`) W KAZDEJ aplikacji, gdzie Flyway zarzadza schematem -
         *   `create-drop`/`update` W TAKIEJ SYTUACJI to BLAD projektowy
         *   (DWA niezalezne mechanizmy walczace O TEN SAM schemat).
         * - `flyway_schema_history` - tabela, KTORA Flyway SAM tworzy,
         *   zeby pamietac, KTORE wersje juz ZASTOSOWANO - kolejne
         *   uruchomienie aplikacji NIE POWTARZA juz zastosowanych
         *   migracji.
         * - `spring.flyway.locations` pozwala IZOLOWAC zestawy migracji
         *   (np. per-modul, per-lekcja) zamiast trzymac WSZYSTKO W
         *   jednym, dzielonym `db/migration`.
         * - Powiazanie Z `_10_dao/Lesson25`: TA SAMA konwencja plikow
         *   (`V<wersja>__<opis>.sql`, checksumy, rosnaca numeracja) -
         *   Spring Boot TYLKO automatyzuje URUCHOMIENIE, mechanika
         *   Flyway pozostaje IDENTYCZNA.
         */
        System.out.println("\n=== KONIEC LEKCJI 14 ===");
    }

    private static void printFlywaySchemaHistory(DataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT \"installed_rank\", \"version\", \"description\", \"success\" " +
                             "FROM \"flyway_schema_history\" ORDER BY \"installed_rank\"")) {
            System.out.println("flyway_schema_history:");
            while (rs.next()) {
                System.out.println("  #" + rs.getInt("installed_rank") + " version=" + rs.getString("version")
                        + " description='" + rs.getString("description") + "' success=" + rs.getBoolean("success"));
            }
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class SharedLocationApp {
    }

    private static void demonstrateDefaultFlywayLocationAndSharedMigrations() throws Exception {
        System.out.println("\n=== DOMYSLNA LOKALIZACJA (classpath:db/migration) - DZIELONE migracje CALEGO rozdzialu ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson14shared;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "none");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(SharedLocationApp.class)
                .properties(props)
                .run();
        try {
            DataSource dataSource = context.getBean(DataSource.class);

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT first_name, email FROM users ORDER BY id")) {
                System.out.println("Tabela 'users' (utworzona PRZEZ V1/V2 Z dzielonego db/migration, NIE PRZEZ TA lekcje):");
                while (rs.next()) {
                    System.out.println("  " + rs.getString("first_name") + " <" + rs.getString("email") + ">");
                }
            }

            printFlywaySchemaHistory(dataSource);
            System.out.println("-> DOKLADNIE TE SAME 2 migracje, KTORE CICHO dzialaly W TLE od Lesson01 tego rozdzialu.");
        } finally {
            context.close();
        }
    }

    @Configuration
    @EnableAutoConfiguration
    static class IsolatedLocationApp {
    }

    private static void demonstrateIsolatedMigrationLocationWithIncrementalVersions() throws Exception {
        System.out.println("\n=== IZOLOWANA LOKALIZACJA (spring.flyway.locations) - WLASNY, niezalezny zestaw migracji ===");

        /*
         * PUŁAPKA NAPOTKANA PRZY PISANIU TEJ LEKCJI (warto zapamiętać): domyślna
         * lokalizacja `classpath:db/migration` skanuje REKURENCYJNIE - podfolder
         * `db/migration/lesson14/V1__...sql` NIE JEST izolowany, tylko DOKŁADA SIĘ
         * do współdzielonego zestawu i koliduje numerem wersji z `db/migration/
         * V1__create_users_table.sql` (`FlywayException: Found more than one
         * migration with version 1`) - zepsuło to WSZYSTKIE lekcje w tym rozdziale
         * korzystające z domyślnej lokalizacji, nie tylko tę. Naprawa: prawdziwie
         * izolowane migracje muszą leżeć POZA `db/migration` (tu: sąsiedni folder
         * `db/migration-lesson14`), nie w jego podfolderze.
         */
        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson14isolated;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.flyway.locations", "classpath:db/migration-lesson14");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "none");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(IsolatedLocationApp.class)
                .properties(props)
                .run();
        try {
            DataSource dataSource = context.getBean(DataSource.class);

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT title, done, priority FROM tasks ORDER BY id")) {
                System.out.println("Tabela 'tasks' (V1 create + V2 add priority, TYLKO Z db/migration-lesson14):");
                while (rs.next()) {
                    System.out.println("  '" + rs.getString("title") + "' done=" + rs.getBoolean("done") + " priority=" + rs.getInt("priority"));
                }
            }

            printFlywaySchemaHistory(dataSource);
            System.out.println("-> ZERO wpisow dotyczacych 'users' - TA baza NIGDY NIE widziala dzielonego db/migration.");
        } finally {
            context.close();
        }
    }

    // @Table(name = "tasks") JEST TU KONIECZNE - @Entity(name=...) ustawia TYLKO nazwe
    // encji JPA (uzywana W HQL), NIE nazwe tabeli SQL. BEZ @Table Hibernate wyprowadzilby
    // nazwe tabeli Z nazwy klasy Javy ("TaskEntity" -> "task_entity"), KTORA NIE ISTNIEJE -
    // Flyway (V1/V2) stworzyl tabele 'tasks', wiec musimy WPROST wskazac te nazwe.
    @Entity(name = "TaskEntity")
    @Table(name = "tasks")
    static class Task {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String title;
        boolean done;
        int priority;

        @Override
        public String toString() {
            return "Task{title='" + title + "', done=" + done + ", priority=" + priority + "}";
        }
    }

    interface TaskRepository extends JpaRepository<Task, Long> {
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    static class ValidateApp {
    }

    private static void demonstrateDdlAutoValidateWithFlywayManagedSchema() throws Exception {
        System.out.println("\n=== ddl-auto=validate - Flyway TWORZY schemat, Hibernate GO TYLKO WERYFIKUJE ===");

        /*
         * KLUCZOWA ZASADA PRODUKCYJNA: gdy Flyway zarzadza schematem, Hibernate NIGDY
         * nie powinien go tworzyc/zmieniac sam (`create-drop`/`update`) - powinien
         * TYLKO zweryfikowac, ze mapowanie encji ODPOWIADA rzeczywistej strukturze
         * tabeli ('validate'). Jesli NIE odpowiada (np. brakuje kolumny) - aplikacja
         * NIE WYSTARTUJE (SchemaManagementException), ZAMIAST po cichu "naprawic" schemat.
         */
        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson14validate;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.flyway.locations", "classpath:db/migration-lesson14");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "validate");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ValidateApp.class)
                .properties(props)
                .run();
        try {
            TaskRepository repository = context.getBean(TaskRepository.class);
            List<Task> tasks = repository.findAll();
            System.out.println("Hibernate wystartowal Z ddl-auto=validate BEZ bledu - mapowanie Task ODPOWIADA schematowi Z Flyway.");
            tasks.forEach(t -> System.out.println("  " + t));
        } finally {
            context.close();
        }
    }
}
