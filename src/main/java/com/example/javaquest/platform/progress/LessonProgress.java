package com.example.javaquest.platform.progress;

import java.time.LocalDateTime;

import com.example.javaquest.platform.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * "Ta lekcja jest zrobiona" - zaznaczona checkboxem przez konkretnego uzytkownika. Istnienie wiersza
 * = lekcja ukonczona; odznaczenie kasuje wiersz.
 *
 * <p>Lekcja jest identyfikowana slugami (rozdzial + lekcja), a NIE kluczem obcym do tabeli "lessons":
 * {@link com.example.javaquest.platform.content.ContentReset} odtwarza tabele tresci przy kazdym starcie
 * (nowe id), wiec klucz obcy do nich by sie rozsypal, a postep uzytkownika ma przetrwac restart.
 */
@Entity
@Table(name = "lesson_progress", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "chapter_slug", "lesson_slug"}))
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "chapter_slug", nullable = false)
    private String chapterSlug;

    @Column(name = "lesson_slug", nullable = false)
    private String lessonSlug;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    protected LessonProgress() {
        // wymagane przez JPA
    }

    public LessonProgress(User user, String chapterSlug, String lessonSlug) {
        this.user = user;
        this.chapterSlug = chapterSlug;
        this.lessonSlug = lessonSlug;
        this.completedAt = LocalDateTime.now();
    }

    public String getChapterSlug() {
        return chapterSlug;
    }

    public String getLessonSlug() {
        return lessonSlug;
    }
}
