package com.example.javaquest.platform.chapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByChapterSlugOrderBySortOrderAsc(String chapterSlug);

    Optional<Lesson> findByChapterSlugAndSlug(String chapterSlug, String slug);

    // "join fetch" laduje Chapter RAZEM z Lesson (jedno zapytanie) - uzywane przez
    // LessonContentLoader, ktory okresowo czysci sesje Hibernate (entityManager.clear())
    // w trakcie dlugiej petli po WSZYSTKICH lekcjach; bez fetch-join "chapter" zostalby
    // LAZY proxy, a po clear() dostep do lesson.getChapter().getSlug() rzucalby
    // LazyInitializationException (sesja, ktora go inicjalizowala, juz nie istnieje).
    @Query("select l from Lesson l join fetch l.chapter")
    List<Lesson> findAllWithChapter();
}
