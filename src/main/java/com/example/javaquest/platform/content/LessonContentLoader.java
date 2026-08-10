package com.example.javaquest.platform.content;

import java.io.IOException;

import com.example.javaquest.platform.chapter.Lesson;
import com.example.javaquest.platform.chapter.LessonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Wczytuje tresc lekcji (teoria/zadania/quiz) z plikow JSON pod
 * {@code src/main/resources/content/<rozdzial>/<lekcja>.json} - patrz {@link LessonContentFile}
 * dla ksztaltu pliku. Dziala dla KAZDEJ lekcji juz zasilonej przez
 * {@link com.example.javaquest.platform.chapter.ContentSeeder} (kolejnosc wymuszona przez
 * {@code @Order} - ten runner MUSI wystartowac PO seederze rozdzialow/lekcji).
 *
 * <p>Lekcje BEZ odpowiadajacego pliku JSON zostaja z Fazy 1 - tylko nawigacyjne metadane, bez
 * tresci (frontend pokazuje wtedy "tresc w przygotowaniu"). To CELOWE - pelna tresc jest
 * pisana rozdzial po rozdziale (patrz EDU_PLATFORM_PLAN.md, Faza 2/3).
 */
@Component
@Order(2)
class LessonContentLoader implements ApplicationRunner {

    private final LessonRepository lessonRepository;
    private final ContentBlockRepository contentBlockRepository;
    private final ExerciseRepository exerciseRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final ObjectMapper objectMapper;

    LessonContentLoader(LessonRepository lessonRepository, ContentBlockRepository contentBlockRepository,
                         ExerciseRepository exerciseRepository, QuizQuestionRepository quizQuestionRepository,
                         ObjectMapper objectMapper) {
        this.lessonRepository = lessonRepository;
        this.contentBlockRepository = contentBlockRepository;
        this.exerciseRepository = exerciseRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.objectMapper = objectMapper;
    }

    // @Transactional trzyma sesje Hibernate otwarta przez CALA metode - bez tego
    // lesson.getChapter().getSlug() (LAZY) rzucalby LazyInitializationException, bo
    // sesja z lessonRepository.findAll() zamyka sie zaraz po zwroceniu wyniku (dokladnie
    // ta sama pulapka co udokumentowana w CLAUDE.md dla _23_spring_data_jpa/Lesson09).
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws IOException {
        for (Lesson lesson : lessonRepository.findAll()) {
            if (contentBlockRepository.existsByLessonId(lesson.getId())) {
                continue;
            }
            String resourcePath = "content/%s/%s.json".formatted(lesson.getChapter().getSlug(), lesson.getSlug());
            ClassPathResource resource = new ClassPathResource(resourcePath);
            if (!resource.exists()) {
                continue;
            }
            LessonContentFile file = objectMapper.readValue(resource.getInputStream(), LessonContentFile.class);
            persist(lesson, file);
        }
    }

    private void persist(Lesson lesson, LessonContentFile file) {
        int order = 0;
        for (LessonContentFile.TheoryBlockJson block : file.theory()) {
            contentBlockRepository.save(new ContentBlock(
                    lesson, order++, ContentBlockType.valueOf(block.type()), block.heading(), block.body(),
                    block.code()));
        }

        order = 0;
        for (LessonContentFile.ExerciseJson exercise : file.exercises()) {
            exerciseRepository.save(new Exercise(lesson, order++, exercise.prompt(), exercise.hint(),
                    exercise.solution()));
        }

        order = 0;
        for (LessonContentFile.QuizQuestionJson question : file.quiz()) {
            quizQuestionRepository.save(new QuizQuestion(
                    lesson, order++, question.question(),
                    question.options().get("A"), question.options().get("B"),
                    question.options().get("C"), question.options().get("D"),
                    question.correct(), question.explanation()));
        }
    }
}
