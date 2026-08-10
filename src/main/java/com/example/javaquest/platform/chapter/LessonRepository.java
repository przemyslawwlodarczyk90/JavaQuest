package com.example.javaquest.platform.chapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByChapterSlugOrderBySortOrderAsc(String chapterSlug);

    Optional<Lesson> findByChapterSlugAndSlug(String chapterSlug, String slug);
}
