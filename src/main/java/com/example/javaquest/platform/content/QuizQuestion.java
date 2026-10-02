package com.example.javaquest.platform.content;

import com.example.javaquest.platform.chapter.Lesson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** Pytanie quizowe ABCD lekcji, z wyjasnieniem pokazywanym po udzieleniu odpowiedzi. */
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    // columnDefinition = "TEXT", NIE @Lob - patrz komentarz w ContentBlock.body (Hibernate +
    // Postgres @Lob String -> kolumna "oid", zapis wisi w nieskoczonosc na Large Object API).
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    /** Opcjonalny fragment kodu, ktorego dotyczy pytanie (null = pytanie czysto tekstowe). */
    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String optionA;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String optionB;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String optionC;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String optionD;

    /** "A", "B", "C" albo "D". */
    @Column(name = "correct_option", nullable = false, length = 1)
    private String correctOption;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String explanation;

    protected QuizQuestion() {
        // wymagane przez JPA
    }

    public QuizQuestion(Lesson lesson, int sortOrder, String question, String code, String optionA,
                         String optionB, String optionC, String optionD, String correctOption,
                         String explanation) {
        this.lesson = lesson;
        this.sortOrder = sortOrder;
        this.question = question;
        this.code = code;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.explanation = explanation;
    }

    public Long getId() {
        return id;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getQuestion() {
        return question;
    }

    public String getCode() {
        return code;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getExplanation() {
        return explanation;
    }
}
