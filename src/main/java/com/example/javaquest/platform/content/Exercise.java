package com.example.javaquest.platform.content;

import com.example.javaquest.platform.chapter.Lesson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Zadanie lekcji - tresc + podpowiedz + rozwiazanie, ujawniane frontendowo osobnymi
 * przyciskami ("Podpowiedz"/"Rozwiazanie"). Odpowiedz uzytkownika NIE jest tu
 * przechowywana w Fazie 2 (brak jeszcze modelu postepu - patrz EDU_PLATFORM_PLAN.md,
 * Faza 4+).
 */
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Lob
    @Column(nullable = false)
    private String prompt;

    @Lob
    @Column(nullable = false)
    private String hint;

    @Lob
    @Column(nullable = false)
    private String solution;

    protected Exercise() {
        // wymagane przez JPA
    }

    public Exercise(Lesson lesson, int sortOrder, String prompt, String hint, String solution) {
        this.lesson = lesson;
        this.sortOrder = sortOrder;
        this.prompt = prompt;
        this.hint = hint;
        this.solution = solution;
    }

    public Long getId() {
        return id;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getHint() {
        return hint;
    }

    public String getSolution() {
        return solution;
    }
}
