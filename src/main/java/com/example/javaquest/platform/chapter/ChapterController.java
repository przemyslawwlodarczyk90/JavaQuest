package com.example.javaquest.platform.chapter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChapterController {

    record ChapterSummary(String slug, String title, int lessonCount) {
    }

    // Etap 2 (2026-09): usunieto pole "hasContent" - z analizy (WORK_PROGRESS.md) wynikalo,
    // ze jest true dla 100% z 664 lekcji (cala tresc JSON juz istnieje), wiec przestalo
    // cokolwiek roznicowac, a nic wiecej sie na nim nie opieralo (ani routing, ani testy).
    record LessonSummary(String slug, String title) {
    }

    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;

    ChapterController(ChapterRepository chapterRepository, LessonRepository lessonRepository) {
        this.chapterRepository = chapterRepository;
        this.lessonRepository = lessonRepository;
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
                .map(lesson -> new LessonSummary(lesson.getSlug(), lesson.getTitle()))
                .toList();
        return ResponseEntity.ok(lessons);
    }
}
