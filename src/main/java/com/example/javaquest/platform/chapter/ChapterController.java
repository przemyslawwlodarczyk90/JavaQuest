package com.example.javaquest.platform.chapter;

import java.util.List;

import com.example.javaquest.platform.content.ContentBlockRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChapterController {

    record ChapterSummary(String slug, String title, int lessonCount) {
    }

    record LessonSummary(String slug, String title, boolean hasContent) {
    }

    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;
    private final ContentBlockRepository contentBlockRepository;

    ChapterController(ChapterRepository chapterRepository, LessonRepository lessonRepository,
                       ContentBlockRepository contentBlockRepository) {
        this.chapterRepository = chapterRepository;
        this.lessonRepository = lessonRepository;
        this.contentBlockRepository = contentBlockRepository;
    }

    @GetMapping("/api/chapters")
    List<ChapterSummary> getChapters() {
        return chapterRepository.findAllByOrderBySortOrderAsc().stream()
                .map(chapter -> new ChapterSummary(
                        chapter.getSlug(),
                        chapter.getTitle(),
                        lessonRepository.findByChapterSlugOrderBySortOrderAsc(chapter.getSlug()).size()))
                .toList();
    }

    @GetMapping("/api/chapters/{chapterSlug}/lessons")
    ResponseEntity<List<LessonSummary>> getLessons(@PathVariable String chapterSlug) {
        if (chapterRepository.findBySlug(chapterSlug).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<LessonSummary> lessons = lessonRepository.findByChapterSlugOrderBySortOrderAsc(chapterSlug).stream()
                .map(lesson -> new LessonSummary(
                        lesson.getSlug(),
                        lesson.getTitle(),
                        contentBlockRepository.existsByLessonId(lesson.getId())))
                .toList();
        return ResponseEntity.ok(lessons);
    }
}
