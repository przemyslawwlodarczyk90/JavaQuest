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
}
