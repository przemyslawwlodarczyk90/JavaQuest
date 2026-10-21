package com.example.javaquest.platform.chapter;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.javaquest.platform.content.ContentBlockType;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Uzgadnia ograniczenie CHECK kolumny {@code chapters.track} z aktualnymi wartosciami
 * {@link CourseTrack} przy kazdym starcie, PRZED zasianiem rozdzialow.
 *
 * <p>Dlaczego: Hibernate 6 dla kolumny {@code @Enumerated(STRING)} tworzy ograniczenie
 * {@code CHECK (track IN (...))} z wartosciami enuma z chwili utworzenia tabeli, a
 * {@code ddl-auto=update} NIE aktualizuje go przy dodaniu nowej wartosci. Po dodaniu toru
 * {@link CourseTrack#LINUX} istniejaca baza odrzucilaby zapis rozdzialow Linux. Odtworzenie
 * ograniczenia z {@code CourseTrack.values()} dziala tez dla kolejnych, przyszlych torow.
 */
@Component
class CourseTrackConstraintSync {

    private final JdbcTemplate jdbcTemplate;

    CourseTrackConstraintSync(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void sync() {
        recreate("chapters", "track", Arrays.stream(CourseTrack.values()).map(Enum::name));
        // Ten sam problem dotyczy content_blocks.type - nowe typy blokow (np. CODE_JSX/CODE_TSX toru React)
        // bylyby odrzucane. Bloki i tak sa kasowane przy kazdym starcie (ContentReset), wiec to bezpieczne.
        recreate("content_blocks", "type", Arrays.stream(ContentBlockType.values()).map(Enum::name));
    }

    private void recreate(String table, String column, Stream<String> values) {
        String allowed = values.map(value -> "'" + value + "'").collect(Collectors.joining(", "));
        String name = table + "_" + column + "_check";
        jdbcTemplate.execute("ALTER TABLE " + table + " DROP CONSTRAINT IF EXISTS " + name);
        jdbcTemplate.execute("ALTER TABLE " + table + " ADD CONSTRAINT " + name + " CHECK (" + column + " IN (" + allowed + "))");
    }
}
