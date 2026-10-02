package com.example.javaquest.platform.content;

import java.io.IOException;

import com.example.javaquest.platform.chapter.Lesson;
import com.example.javaquest.platform.chapter.LessonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Wczytuje tresc lekcji (teoria/zadania/quiz) z plikow JSON pod
 * {@code src/main/resources/content/<rozdzial>/<lekcja>.json} - patrz {@link LessonContentFile}
 * dla ksztaltu pliku. Dziala dla KAZDEJ lekcji juz zasilonej przez
 * {@link com.example.javaquest.platform.chapter.ContentSeeder}.
 *
 * <p>Wywolywana przez {@link ContentBootstrap} ({@code @PostConstruct}), NIE uruchamiana tutaj
 * bezposrednio jako {@code @PostConstruct}/{@code ApplicationRunner} - {@code @Transactional}
 * ponizej dziala TYLKO, gdy ta metoda jest wywolywana Z ZEWNATRZ, przez proxy AOP wygenerowany
 * dla tego beana. Samo-wywolanie {@code @PostConstruct} na tym samym obiekcie omija proxy (Spring
 * tworzy proxy transakcyjne DOPIERO po zakonczeniu inicjalizacji beana, wiec {@code @PostConstruct}
 * wykonywalby sie na "surowym" obiekcie, bez transakcji) - stad osobny, malutki
 * {@code ContentBootstrap}, ktory wstrzykuje TEN bean (dostaje juz gotowy proxy) i wywoluje
 * {@link #load()} z zewnatrz.
 */
@Component
class LessonContentLoader {

    // Co ile PRZETWORZONYCH (nie: pominietych przez "continue") lekcji odswiezyc sesje
    // Hibernate - patrz komentarz przy load() ponizej.
    private static final int SESSION_CLEAR_BATCH_SIZE = 20;

    private final LessonRepository lessonRepository;
    private final ContentBlockRepository contentBlockRepository;
    private final ExerciseRepository exerciseRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    LessonContentLoader(LessonRepository lessonRepository, ContentBlockRepository contentBlockRepository,
                         ExerciseRepository exerciseRepository, QuizQuestionRepository quizQuestionRepository,
                         ObjectMapper objectMapper, EntityManager entityManager) {
        this.lessonRepository = lessonRepository;
        this.contentBlockRepository = contentBlockRepository;
        this.exerciseRepository = exerciseRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.objectMapper = objectMapper;
        this.entityManager = entityManager;
    }

    // @Transactional trzyma sesje Hibernate otwarta przez CALA metode - bez tego
    // lesson.getChapter().getSlug() (LAZY) rzucalby LazyInitializationException, bo
    // sesja z lessonRepository.findAll() zamyka sie zaraz po zwroceniu wyniku (dokladnie
    // ta sama pulapka co udokumentowana w CLAUDE.md dla _23_spring_data_jpa/Lesson09).
    //
    // Zweryfikowane empirycznie (przy 850 lekcjach x ~15-20 wierszy tresci/lekcje, pierwszy
    // realny przebieg tego loadera): BEZ okresowego entityManager.clear() sesja Hibernate
    // rosnie przez CALA petle (nigdy nie jest czyszczona), a auto-flush WYKONYWANY PRZED
    // KAZDYM zapytaniem existsByLessonId() (bo sesja ma "dirty" encje z poprzednich lekcji)
    // skanuje WSZYSTKIE dotychczas zaladowane encje (StatefulPersistenceContext/Cascade) -
    // koszt tego skanowania rosnie z kazda lekcja, dajac kwadratowe spowolnienie calej
    // petli (jstack: main utrzymywal RUNNABLE/wysokie CPU w AbstractFlushingEventListener.
    // prepareEntityFlushes/Cascade.cascadeToOne, NIE w I/O - realna, narastajaca praca, nie
    // deadlock). "findAllWithChapter()" (fetch-join) + okresowe flush()+clear() utrzymuje
    // rozmiar sesji OGRANICZONY (staly narzut per partia), zamiast rosnacym z kazda lekcja -
    // "clear()" jest tu KLUCZOWY (nie tylko "flush()"): to on faktycznie USUWA encje z
    // pamieci sesji, "flush()" sam w sobie tylko wysyla SQL, nie zwalnia PersistenceContext.
    @Transactional
    void load() throws IOException {
        int processedSinceClear = 0;
        for (Lesson lesson : lessonRepository.findAllWithChapter()) {
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

            processedSinceClear++;
            if (processedSinceClear >= SESSION_CLEAR_BATCH_SIZE) {
                entityManager.flush();
                entityManager.clear();
                processedSinceClear = 0;
            }
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
                    lesson, order++, question.question(), question.code(),
                    question.options().get("A"), question.options().get("B"),
                    question.options().get("C"), question.options().get("D"),
                    question.correct(), question.explanation()));
        }
    }
}
