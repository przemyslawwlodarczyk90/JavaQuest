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

    @Lob
    @Column(nullable = false)
    private String question;

    @Lob
    @Column(nullable = false)
    private String optionA;

    @Lob
    @Column(nullable = false)
    private String optionB;

    @Lob
    @Column(nullable = false)
    private String optionC;

    @Lob
    @Column(nullable = false)
    private String optionD;

    /** "A", "B", "C" albo "D". */
    @Column(name = "correct_option", nullable = false, length = 1)
    private String correctOption;

    @Lob
    @Column(nullable = false)
    private String explanation;

    protected QuizQuestion() {
        // wymagane przez JPA
    }

    public QuizQuestion(Lesson lesson, int sortOrder, String question, String optionA, String optionB,
                         String optionC, String optionD, String correctOption, String explanation) {
        this.lesson = lesson;
        this.sortOrder = sortOrder;
        this.question = question;
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
