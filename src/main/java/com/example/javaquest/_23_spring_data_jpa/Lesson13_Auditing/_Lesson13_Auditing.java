package com.example.javaquest._23_spring_data_jpa.Lesson13_Auditing;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;
import java.util.Optional;
import java.util.Properties;

public class _Lesson13_Auditing {

    public static void main(String[] args) throws Exception {

        System.out.println("=== LEKCJA 13: Auditing - KTO i KIEDY zmienil rekord ===");

        /*
         * ============================================================
         * 📦 CZYM JEST AUDITING W SPRING DATA JPA
         * ============================================================
         * "Auditing" tutaj NIE oznacza pelnej historii zmian (to robi
         * Hibernate Envers, `_12_hibernate/Lesson29_HibernateEnvers`) -
         * oznacza AUTOMATYCZNE wypelnianie 4 "metadanych" NA SAMEJ
         * encji, BEZ RECZNEGO ustawiania ich w kodzie:
         *   - @CreatedDate      - KIEDY rekord POWSTAL,
         *   - @LastModifiedDate - KIEDY rekord OSTATNIO zmieniono,
         *   - @CreatedBy        - KTO rekord UTWORZYL,
         *   - @LastModifiedBy   - KTO OSTATNIO go zmienil.
         *
         * Spring Data ROBI to PRZEZ `AuditingEntityListener` (haczyk
         * JPA `@PrePersist`/`@PreUpdate`) - encja SAMA NIE MUSI wiedziec
         * NIC o czasie ani o "biezacym uzytkowniku".
         *
         * PORÓWNANIE z Envers (`_12_hibernate/Lesson29`): Envers
         * trzyma KAZDA HISTORYCZNA WERSJE encji (osobna tabela `_AUD`,
         * mozesz odtworzyc stan sprzed 5 zmian) - JPA Auditing trzyma
         * TYLKO OSTATNI stan (4 pola NA SAMEJ encji, ZERO historii).
         * Czesto uzywa sie OBU naraz: Auditing DO szybkiego "kto/kiedy
         * ostatnio", Envers DO PELNEJ historii/audytu zgodnosci.
         */
        System.out.println("JPA Auditing = 4 pola metadanych NA encji (created/modified date+by). Envers (_12_hibernate/Lesson29) = PELNA historia w osobnej tabeli.");

        demonstrateCreatedAndModifiedDatesAutomatically();
        demonstrateCreatedByAndModifiedByWithAuditorAware();

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - `@EntityListeners(AuditingEntityListener.class)` NA encji +
         *   `@EnableJpaAuditing` NA konfiguracji WLACZAJA mechanizm.
         * - `@CreatedDate`/`@LastModifiedDate` (typ `Instant`/`LocalDateTime`)
         *   dzialaja OD RAZU, BEZ zadnej dodatkowej konfiguracji.
         * - `@CreatedBy`/`@LastModifiedBy` WYMAGAJA beana `AuditorAware<T>`
         *   (wskazanego przez `auditorAwareRef`) - TO ON odpowiada NA
         *   pytanie "KTO jest biezacym uzytkownikiem?" (w prawdziwej
         *   aplikacji: z `SecurityContextHolder` Spring Security -
         *   zapowiedz `_24_spring_security`).
         * - BEZ zarejestrowanego `AuditorAware`, `@CreatedBy`/`@LastModifiedBy`
         *   PO CICHU zostaja `null` (BRAK bledu) - `@CreatedDate`/
         *   `@LastModifiedDate` dzialaja NIEZALEZNIE od tego.
         * - `@CreatedDate` NIGDY sie NIE zmienia PO pierwszym zapisie,
         *   `@LastModifiedDate` AKTUALIZUJE SIE PRZY KAZDYM `@PreUpdate`.
         */
        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    @Entity(name = "BlogPost")
    @EntityListeners(AuditingEntityListener.class)
    static class BlogPost {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String title;
        String content;

        @CreatedDate
        Instant createdDate;

        @LastModifiedDate
        Instant lastModifiedDate;

        @CreatedBy
        String createdBy;

        @LastModifiedBy
        String lastModifiedBy;

        BlogPost() {
        }

        BlogPost(String title, String content) {
            this.title = title;
            this.content = content;
        }

        @Override
        public String toString() {
            return "BlogPost{title='" + title + "', createdDate=" + createdDate
                    + ", lastModifiedDate=" + lastModifiedDate
                    + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy + "}";
        }
    }

    interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    @EnableJpaAuditing
    static class TimestampAuditingApp {
        // CELOWO BEZ AuditorAware - pokazujemy, ze @CreatedDate/@LastModifiedDate
        // dzialaja SAME, BEZ zadnej dodatkowej konfiguracji "biezacego uzytkownika".
    }

    private static void demonstrateCreatedAndModifiedDatesAutomatically() throws Exception {
        System.out.println("\n=== @CreatedDate / @LastModifiedDate - BEZ AuditorAware ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson13timestamps;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(TimestampAuditingApp.class)
                .properties(props)
                .run();
        try {
            BlogPostRepository repository = context.getBean(BlogPostRepository.class);

            BlogPost saved = repository.save(new BlogPost("Pierwszy wpis", "Tresc..."));
            System.out.println("Po zapisie (BEZ recznego ustawienia daty): " + saved);
            System.out.println("-> createdDate == lastModifiedDate (pierwszy zapis): " + saved.createdDate.equals(saved.lastModifiedDate));
            System.out.println("-> createdBy/lastModifiedBy pozostaja null (brak AuditorAware): createdBy=" + saved.createdBy);

            Instant originalCreatedDate = saved.createdDate;
            Thread.sleep(50);

            saved.content = "Zaktualizowana tresc...";
            BlogPost updated = repository.save(saved);
            System.out.println("Po aktualizacji: " + updated);
            System.out.println("-> createdDate NIE ZMIENIL SIE: " + originalCreatedDate.equals(updated.createdDate));
            System.out.println("-> lastModifiedDate JEST PÓZNIEJSZY niz przy tworzeniu: " + updated.lastModifiedDate.isAfter(originalCreatedDate));
        } finally {
            context.close();
        }
    }

    static class CurrentUserAuditorAware implements AuditorAware<String> {
        // W PRAWDZIWEJ aplikacji: Optional.ofNullable(SecurityContextHolder.getContext()
        // .getAuthentication()).map(Authentication::getName) - zapowiedz _24_spring_security.
        // Tu SYMULUJEMY "biezacego uzytkownika" przez ThreadLocal, zeby demo bylo deterministyczne.
        private static final ThreadLocal<String> CURRENT_USER = ThreadLocal.withInitial(() -> "system");

        static void setCurrentUser(String user) {
            CURRENT_USER.set(user);
        }

        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.ofNullable(CURRENT_USER.get());
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(considerNestedRepositories = true)
    @EnableJpaAuditing(auditorAwareRef = "currentUserAuditorAware")
    static class AuditorAwareApp {
        @Bean
        AuditorAware<String> currentUserAuditorAware() {
            return new CurrentUserAuditorAware();
        }
    }

    private static void demonstrateCreatedByAndModifiedByWithAuditorAware() throws Exception {
        System.out.println("\n=== @CreatedBy / @LastModifiedBy - Z AuditorAware<String> ===");

        Properties props = new Properties();
        props.setProperty("spring.datasource.url", "jdbc:h2:mem:lesson13auditor;DB_CLOSE_DELAY=-1");
        props.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
        props.setProperty("spring.main.web-application-type", "none");

        ConfigurableApplicationContext context = new SpringApplicationBuilder(AuditorAwareApp.class)
                .properties(props)
                .run();
        try {
            BlogPostRepository repository = context.getBean(BlogPostRepository.class);

            CurrentUserAuditorAware.setCurrentUser("kasia");
            BlogPost saved = repository.save(new BlogPost("Drugi wpis", "Tresc..."));
            System.out.println("Utworzony przez 'kasia': " + saved);
            System.out.println("-> createdBy=" + saved.createdBy + ", lastModifiedBy=" + saved.lastModifiedBy + " (OBA rowne PRZY TWORZENIU)");

            CurrentUserAuditorAware.setCurrentUser("marek");
            saved.content = "Poprawka od 'marek'";
            BlogPost updated = repository.save(saved);
            System.out.println("Zaktualizowany przez 'marek': " + updated);
            System.out.println("-> createdBy NADAL 'kasia' (" + updated.createdBy + "), lastModifiedBy TERAZ 'marek' (" + updated.lastModifiedBy + ")");
        } finally {
            context.close();
        }
    }
}
