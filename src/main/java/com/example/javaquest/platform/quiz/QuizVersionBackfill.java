package com.example.javaquest.platform.quiz;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Uzupelnia {@code version = 0} w podejsciach zapisanych, zanim encje dostaly {@code @Version}.
 *
 * <p>Dlaczego: {@code ddl-auto=update} dodaje kolumne {@code version} jako NULL dla istniejacych
 * wierszy, a Hibernate aktualizuje encje warunkiem {@code where version = ?} - z NULL-em taki update
 * nigdy nie trafilby w wiersz (odpowiedz w starym, niedokonczonym podejsciu konczylaby sie bledem).
 * Operacja jest idempotentna i niczego nie kasuje. Zaleznosc od {@link EntityManagerFactory} gwarantuje,
 * ze Hibernate juz zaktualizowal schemat (kolumna istnieje).
 */
@Component
class QuizVersionBackfill {

    private final JdbcTemplate jdbcTemplate;

    QuizVersionBackfill(JdbcTemplate jdbcTemplate, EntityManagerFactory entityManagerFactory) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void backfill() {
        jdbcTemplate.update("UPDATE quiz_attempts SET version = 0 WHERE version IS NULL");
        jdbcTemplate.update("UPDATE quiz_attempt_questions SET version = 0 WHERE version IS NULL");
    }
}
