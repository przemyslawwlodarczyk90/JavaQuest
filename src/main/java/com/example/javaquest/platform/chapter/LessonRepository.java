package com.example.javaquest.platform.chapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    interface PracticedTitle {
        String getChapterSlug();
        String getLessonSlug();
        String getTitle();
        String getChapterTitle();
        CourseTrack getTrack();
    }

    @Query("select l.chapter.slug as chapterSlug, l.slug as lessonSlug, l.title as title, "
            + "l.chapter.title as chapterTitle, l.chapter.track as track from Lesson l where exists "
            + "(select a.id from QuizAttempt a where a.user = :user and a.passed = true "
            + "and a.chapterSlug = l.chapter.slug and a.lessonSlug = l.slug)")
    List<PracticedTitle> findPracticedTitles(@org.springframework.data.repository.query.Param("user")
            com.example.javaquest.platform.user.User user);
    interface ChapterCount {
        String getChapterSlug();
        long getTotal();
    }

    @Query("select l.chapter.slug as chapterSlug, count(l) as total from Lesson l group by l.chapter.slug")
    List<ChapterCount> countByChapter();

    List<Lesson> findByChapterSlugOrderBySortOrderAsc(String chapterSlug);

    Optional<Lesson> findByChapterSlugAndSlug(String chapterSlug, String slug);

    // "join fetch" laduje Chapter RAZEM z Lesson (jedno zapytanie) - uzywane przez
    // LessonContentLoader, ktory okresowo czysci sesje Hibernate (entityManager.clear())
    // w trakcie dlugiej petli po WSZYSTKICH lekcjach; bez fetch-join "chapter" zostalby
    // LAZY proxy, a po clear() dostep do lesson.getChapter().getSlug() rzucalby
    // LazyInitializationException (sesja, ktora go inicjalizowala, juz nie istnieje).
    @Query("select l from Lesson l join fetch l.chapter")
    List<Lesson> findAllWithChapter();

    @Query("select count(m) from LessonMaterial m where m.lesson = :lesson")
    long countMaterials(Lesson lesson);

    @Query("select count(b) from ContentBlock b where b.lesson = :lesson")
    long countContentBlocks(Lesson lesson);
}
