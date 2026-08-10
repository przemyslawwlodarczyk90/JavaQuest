package com.example.javaquest.platform.content;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    record QuizQuestionDto(String question, Map<String, String> options, String correct, String explanation) {
    }

    private final LessonRepository lessonRepository;
    private final ContentBlockRepository contentBlockRepository;
    private final ExerciseRepository exerciseRepository;
    private final QuizQuestionRepository quizQuestionRepository;

    LessonContentController(LessonRepository lessonRepository, ContentBlockRepository contentBlockRepository,
                             ExerciseRepository exerciseRepository,
                             QuizQuestionRepository quizQuestionRepository) {
        this.lessonRepository = lessonRepository;
        this.contentBlockRepository = contentBlockRepository;
        this.exerciseRepository = exerciseRepository;
        this.quizQuestionRepository = quizQuestionRepository;
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

    @GetMapping("/api/chapters/{chapterSlug}/lessons/{lessonSlug}/quiz")
    ResponseEntity<List<QuizQuestionDto>> getQuiz(@PathVariable String chapterSlug,
                                                    @PathVariable String lessonSlug) {
        return withLesson(chapterSlug, lessonSlug, lesson -> quizQuestionRepository
                .findByLessonIdOrderBySortOrderAsc(lesson.getId()).stream()
                .map(q -> {
                    Map<String, String> options = new LinkedHashMap<>();
                    options.put("A", q.getOptionA());
                    options.put("B", q.getOptionB());
                    options.put("C", q.getOptionC());
                    options.put("D", q.getOptionD());
                    return new QuizQuestionDto(q.getQuestion(), options, q.getCorrectOption(), q.getExplanation());
                })
                .toList());
    }

    private <T> ResponseEntity<List<T>> withLesson(String chapterSlug, String lessonSlug,
                                                     java.util.function.Function<Lesson, List<T>> mapper) {
        Optional<Lesson> lesson = lessonRepository.findByChapterSlugAndSlug(chapterSlug, lessonSlug);
        if (lesson.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.apply(lesson.get()));
    }
}
