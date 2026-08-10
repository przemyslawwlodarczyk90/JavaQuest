package com.example.javaquest.platform.chapter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByChapterSlugOrderBySortOrderAsc(String chapterSlug);
}
