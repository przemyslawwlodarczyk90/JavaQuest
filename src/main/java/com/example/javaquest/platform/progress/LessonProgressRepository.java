package com.example.javaquest.platform.progress;

import java.util.List;

import com.example.javaquest.platform.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    interface ChapterCount {
        String getChapterSlug();
        long getTotal();
    }

    @org.springframework.data.jpa.repository.Query("select p.chapterSlug as chapterSlug, count(p) as total "
            + "from LessonProgress p where p.user = :user and exists "
            + "(select l.id from Lesson l where l.chapter.slug = p.chapterSlug and l.slug = p.lessonSlug) "
            + "group by p.chapterSlug")
    List<ChapterCount> countByChapter(@org.springframework.data.repository.query.Param("user") User user);

    List<LessonProgress> findByUser(User user);

    boolean existsByUserAndChapterSlugAndLessonSlug(User user, String chapterSlug, String lessonSlug);

    void deleteByUserAndChapterSlugAndLessonSlug(User user, String chapterSlug, String lessonSlug);
}
