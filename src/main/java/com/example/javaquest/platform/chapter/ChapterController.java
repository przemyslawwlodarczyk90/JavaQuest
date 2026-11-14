package com.example.javaquest.platform.chapter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChapterController {

    record ChapterSummary(String slug, String title, int lessonCount) {
    }

    // Etap 2 (2026-09): usunieto pole "hasContent" - z analizy wynikalo,
    // ze jest true dla 100% z 664 lekcji (cala tresc JSON juz istnieje), wiec przestalo
    // cokolwiek roznicowac, a nic wiecej sie na nim nie opieralo (ani routing, ani testy).
    record LessonSummary(String slug, String title) {
    }

    // Spis tresci toru: wszystkie rozdzialy z lekcjami w jednym zapytaniu (zamiast N wywolan /lessons).
    record ChapterToc(String slug, String title, List<LessonSummary> lessons) {
    }

    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;

    ChapterController(ChapterRepository chapterRepository, LessonRepository lessonRepository) {
        this.chapterRepository = chapterRepository;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping("/api/chapters")
    List<ChapterSummary> getChapters(@RequestParam(defaultValue = "JAVA") CourseTrack track) {
        return chapterRepository.findAllByTrackOrderBySortOrderAsc(track).stream()
                .map(chapter -> new ChapterSummary(
                        chapter.getSlug(),
                        chapter.getTitle(),
                        lessonRepository.findByChapterSlugOrderBySortOrderAsc(chapter.getSlug()).size()))
                .toList();
    }

    @GetMapping(value = "/api/chapters", params = "withLessons=true")
    List<ChapterToc> getTableOfContents(@RequestParam(defaultValue = "JAVA") CourseTrack track) {
        var lessonsByChapter = new java.util.LinkedHashMap<String, List<LessonSummary>>();
        for (Lesson lesson : lessonRepository.findAllByTrackWithChapter(track)) {
            lessonsByChapter.computeIfAbsent(lesson.getChapter().getSlug(), slug -> new java.util.ArrayList<>())
                    .add(new LessonSummary(lesson.getSlug(), lesson.getTitle()));
        }
        return chapterRepository.findAllByTrackOrderBySortOrderAsc(track).stream()
                .map(chapter -> new ChapterToc(chapter.getSlug(), chapter.getTitle(),
                        lessonsByChapter.getOrDefault(chapter.getSlug(), List.of())))
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
