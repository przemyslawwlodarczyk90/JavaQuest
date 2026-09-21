package com.example.javaquest.platform.progress;

import java.util.List;

import com.example.javaquest.platform.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    List<LessonProgress> findByUser(User user);

    boolean existsByUserAndChapterSlugAndLessonSlug(User user, String chapterSlug, String lessonSlug);

    void deleteByUserAndChapterSlugAndLessonSlug(User user, String chapterSlug, String lessonSlug);
}
