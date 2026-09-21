package com.example.javaquest.platform.content;

import java.util.List;
import java.util.Optional;

import com.example.javaquest.platform.chapter.Lesson;
import com.example.javaquest.platform.chapter.LessonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LessonContentController {

    record ContentBlockDto(String type, String heading, String body, String code) {
    }

    record ExerciseDto(String prompt, String hint, String solution) {
    }

    private final LessonRepository lessonRepository;
    private final ContentBlockRepository contentBlockRepository;
    private final ExerciseRepository exerciseRepository;

    LessonContentController(LessonRepository lessonRepository, ContentBlockRepository contentBlockRepository,
                             ExerciseRepository exerciseRepository) {
        this.lessonRepository = lessonRepository;
        this.contentBlockRepository = contentBlockRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("/api/chapters/{chapterSlug}/lessons/{lessonSlug}/theory")
    ResponseEntity<List<ContentBlockDto>> getTheory(@PathVariable String chapterSlug,
                                                      @PathVariable String lessonSlug) {
        return withLesson(chapterSlug, lessonSlug, lesson -> contentBlockRepository
                .findByLessonIdOrderBySortOrderAsc(lesson.getId()).stream()
                .map(b -> new ContentBlockDto(b.getType().name(), b.getHeading(), b.getBody(), b.getCode()))
                .toList());
    }

    @GetMapping("/api/chapters/{chapterSlug}/lessons/{lessonSlug}/exercises")
    ResponseEntity<List<ExerciseDto>> getExercises(@PathVariable String chapterSlug,
                                                     @PathVariable String lessonSlug) {
        return withLesson(chapterSlug, lessonSlug, lesson -> exerciseRepository
                .findByLessonIdOrderBySortOrderAsc(lesson.getId()).stream()
                .map(e -> new ExerciseDto(e.getPrompt(), e.getHint(), e.getSolution()))
                .toList());
    }

    // Quiz jest obslugiwany przez com.example.javaquest.platform.quiz (losowanie, zaliczenie, ocena na
    // serwerze) - ten kontroler celowo NIE wystawia juz pytan wraz z poprawnymi odpowiedziami.

    private <T> ResponseEntity<List<T>> withLesson(String chapterSlug, String lessonSlug,
                                                     java.util.function.Function<Lesson, List<T>> mapper) {
        Optional<Lesson> lesson = lessonRepository.findByChapterSlugAndSlug(chapterSlug, lessonSlug);
        if (lesson.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.apply(lesson.get()));
    }
}
