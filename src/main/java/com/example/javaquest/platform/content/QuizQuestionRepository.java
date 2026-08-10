package com.example.javaquest.platform.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByLessonIdOrderBySortOrderAsc(Long lessonId);
}
