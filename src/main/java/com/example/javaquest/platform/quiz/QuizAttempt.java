package com.example.javaquest.platform.quiz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.javaquest.platform.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

/**
 * Jedno podejscie uzytkownika do quizu lekcji: zapisuje KTORE pytania wylosowano (i w jakiej kolejnosci),
 * jak odpowiedzial i wynik. Podejscie jest "otwarte" ({@code finishedAt == null}), dopoki nie odpowie na
 * wszystkie pytania - odswiezenie strony wznawia to samo podejscie zamiast losowac nowe.
 *
 * <p>Lekcja i pytania sa identyfikowane naturalnymi kluczami (slugi, numer pytania w lekcji), nie kluczami
 * obcymi - patrz {@link com.example.javaquest.platform.progress.LessonProgress}.
 */
@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {

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
    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    private Integer correctCount;

    private Boolean passed;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<QuizAttemptQuestion> questions = new ArrayList<>();

    protected QuizAttempt() {
        // wymagane przez JPA
    }

    public QuizAttempt(User user, String chapterSlug, String lessonSlug) {
        this.user = user;
        this.chapterSlug = chapterSlug;
        this.lessonSlug = lessonSlug;
        this.createdAt = LocalDateTime.now();
    }

    void addQuestion(int questionNo) {
        questions.add(new QuizAttemptQuestion(this, questions.size(), questionNo));
    }

    void finish(int correctCount, boolean passed) {
        this.finishedAt = LocalDateTime.now();
        this.correctCount = correctCount;
        this.passed = passed;
    }

    public Long getId() {
        return id;
    }

    public String getChapterSlug() {
        return chapterSlug;
    }

    public String getLessonSlug() {
        return lessonSlug;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public Boolean getPassed() {
        return passed;
    }

    public List<QuizAttemptQuestion> getQuestions() {
        return questions;
    }

    public boolean isFinished() {
        return finishedAt != null;
    }
}
