package com.example.javaquest.platform.quiz;

import java.util.List;
import java.util.Optional;

import com.example.javaquest.platform.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    Optional<QuizAttempt> findByIdAndUser(Long id, User user);

    /** Otwarte (nieskonczone) podejscie do quizu tej lekcji - jest co najwyzej jedno. */
    Optional<QuizAttempt> findFirstByUserAndChapterSlugAndLessonSlugAndFinishedAtIsNull(
            User user, String chapterSlug, String lessonSlug);

    Optional<QuizAttempt> findFirstByUserAndChapterSlugAndLessonSlugAndFinishedAtIsNotNullOrderByFinishedAtDesc(
            User user, String chapterSlug, String lessonSlug);

    boolean existsByUserAndChapterSlugAndLessonSlugAndPassedTrue(User user, String chapterSlug, String lessonSlug);

    /** Numery pytan tej lekcji, ktore uzytkownik juz kiedys mial wylosowane. */
    @Query("""
            select distinct q.questionNo from QuizAttemptQuestion q
            where q.attempt.user = :user and q.attempt.chapterSlug = :chapterSlug
              and q.attempt.lessonSlug = :lessonSlug
            """)
    List<Integer> findSeenQuestionNos(@Param("user") User user, @Param("chapterSlug") String chapterSlug,
                                      @Param("lessonSlug") String lessonSlug);

    /**
     * Wszystkie zaliczone podejscia uzytkownika z co najmniej {@code minQuestions} pytaniami - zdarzenia
     * mastery. Projekcja (bez encji i pytan), wiec odczyt calej historii jest tani.
     */
    @Query("""
            select new com.example.javaquest.platform.quiz.PassedAttempt(a.chapterSlug, a.lessonSlug, a.finishedAt)
            from QuizAttempt a
            where a.user = :user and a.passed = true and size(a.questions) >= :minQuestions
            """)
    List<PassedAttempt> findPassedAttempts(@Param("user") User user, @Param("minQuestions") int minQuestions);

    /** To samo, zawezone do jednej lekcji. */
    @Query("""
            select new com.example.javaquest.platform.quiz.PassedAttempt(a.chapterSlug, a.lessonSlug, a.finishedAt)
            from QuizAttempt a
            where a.user = :user and a.chapterSlug = :chapterSlug and a.lessonSlug = :lessonSlug
              and a.passed = true and size(a.questions) >= :minQuestions
            """)
    List<PassedAttempt> findPassedAttempts(@Param("user") User user, @Param("chapterSlug") String chapterSlug,
                                           @Param("lessonSlug") String lessonSlug,
                                           @Param("minQuestions") int minQuestions);

    /**
     * Wszystkie udzielone odpowiedzi uzytkownika w quizie lekcji, od najstarszych: [numer pytania, czy
     * poprawnie]. Ostatnia odpowiedz na dane pytanie decyduje, czy jest ono "do poprawy".
     */
    @Query("""
            select q.questionNo, q.correct from QuizAttemptQuestion q
            where q.attempt.user = :user and q.attempt.chapterSlug = :chapterSlug
              and q.attempt.lessonSlug = :lessonSlug and q.correct is not null
            order by q.attempt.createdAt asc, q.id asc
            """)
    List<Object[]> findAnswerHistory(@Param("user") User user, @Param("chapterSlug") String chapterSlug,
                                     @Param("lessonSlug") String lessonSlug);
}
