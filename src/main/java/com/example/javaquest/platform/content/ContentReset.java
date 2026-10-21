package com.example.javaquest.platform.content;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Odswieza wylacznie odtwarzalna tresc JSON: teorie, cwiczenia i pytania quizow.
 * Rozdzialy i lekcje zachowuja identyfikatory, poniewaz lesson_materials odwoluje sie
 * do lekcji kluczem obcym. ContentSeeder synchronizuje ich metadane po slugach.
 * Dane uzytkownikow, materialy, postep i historia quizow nie sa kasowane.
 *
 * <p>Kolejnosc kasowania idzie od tabel-dzieci (klucze obce). {@code ContentSeeder} ma
 * {@code @DependsOn("contentReset")}, wiec czyszczenie zawsze poprzedza zasiew.
 */
@Component
class ContentReset {

    private final QuizQuestionRepository quizQuestionRepository;
    private final ExerciseRepository exerciseRepository;
    private final ContentBlockRepository contentBlockRepository;

    ContentReset(QuizQuestionRepository quizQuestionRepository, ExerciseRepository exerciseRepository,
                 ContentBlockRepository contentBlockRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.exerciseRepository = exerciseRepository;
        this.contentBlockRepository = contentBlockRepository;
    }

    @PostConstruct
    void reset() {
        quizQuestionRepository.deleteAllInBatch();
        exerciseRepository.deleteAllInBatch();
        contentBlockRepository.deleteAllInBatch();
    }
}
