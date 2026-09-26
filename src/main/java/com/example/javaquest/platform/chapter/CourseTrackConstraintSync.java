package com.example.javaquest.platform.chapter;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        String allowed = Arrays.stream(CourseTrack.values())
                .map(track -> "'" + track.name() + "'")
                .collect(Collectors.joining(", "));
        jdbcTemplate.execute("ALTER TABLE chapters DROP CONSTRAINT IF EXISTS chapters_track_check");
        jdbcTemplate.execute("ALTER TABLE chapters ADD CONSTRAINT chapters_track_check CHECK (track IN (" + allowed + "))");
    }
}
