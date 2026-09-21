package com.example.javaquest.platform.quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** Jedno wylosowane pytanie w podejsciu: pozycja w tym podejsciu + udzielona odpowiedz (jesli juz jest). */
@Entity
@Table(name = "quiz_attempt_questions")
public class QuizAttemptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt attempt;

    /** Miejsce w losowej kolejnosci tego podejscia (0..n-1) - po nim frontend odpowiada. */
    @Column(nullable = false)
    private int position;

    /** Numer pytania w lekcji ({@code QuizQuestion.sortOrder}) - klucz naturalny, przezywa reseed tresci. */
    @Column(name = "question_no", nullable = false)
    private int questionNo;

    @Column(length = 1)
    private String selectedOption;

    private Boolean correct;

    protected QuizAttemptQuestion() {
        // wymagane przez JPA
    }

    QuizAttemptQuestion(QuizAttempt attempt, int position, int questionNo) {
        this.attempt = attempt;
        this.position = position;
        this.questionNo = questionNo;
    }

    void answer(String option, boolean correct) {
        this.selectedOption = option;
        this.correct = correct;
    }

    public int getPosition() {
        return position;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public boolean isAnswered() {
        return selectedOption != null;
    }
}
