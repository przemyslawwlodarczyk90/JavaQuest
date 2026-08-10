package com.example.javaquest.platform.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentBlockRepository extends JpaRepository<ContentBlock, Long> {

    List<ContentBlock> findByLessonIdOrderBySortOrderAsc(Long lessonId);

    boolean existsByLessonId(Long lessonId);
}
