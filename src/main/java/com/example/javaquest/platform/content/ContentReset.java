package com.example.javaquest.platform.content;

import com.example.javaquest.platform.chapter.ChapterRepository;
import com.example.javaquest.platform.chapter.LessonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Czysci tabele TRESCI kursu (rozdzialy, lekcje, teoria, cwiczenia, quizy) tuz przed ich ponownym
 * zasianiem przy kazdym starcie.
 *
 * <p>Dlaczego: baza jest teraz trwala (PostgreSQL, ddl-auto=update), a
 * {@link com.example.javaquest.platform.chapter.ContentSeeder} i {@link LessonContentLoader} pomijaja
 * to, co juz jest w bazie - bez czyszczenia edycja pliku JSON lekcji albo nowy rozdzial w
 * ChapterSeedData nigdy nie trafilyby do dzialajacej aplikacji (poprzednio H2 create-drop odtwarzal
 * wszystko przy kazdym starcie, wiec problem nie istnial). Tresc jest w 100% pochodna od plikow w
 * jarze, wiec czyszczenie jej niczego nie gubi. Tabele uzytkownikow ({@code app_user}, tokeny) NIE sa
 * tu ruszane. Jesli kiedys pojawia sie dane powiazane z lekcjami (np. postep nauki), ten mechanizm
 * trzeba zamienic na synchronizacje po slugach.
 *
 * <p>Kolejnosc kasowania idzie od tabel-dzieci (klucze obce). {@code ContentSeeder} ma
 * {@code @DependsOn("contentReset")}, wiec czyszczenie zawsze poprzedza zasiew.
 */
@Component
class ContentReset {

    private final QuizQuestionRepository quizQuestionRepository;
    private final ExerciseRepository exerciseRepository;
    private final ContentBlockRepository contentBlockRepository;
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;

    ContentReset(QuizQuestionRepository quizQuestionRepository, ExerciseRepository exerciseRepository,
                 ContentBlockRepository contentBlockRepository, LessonRepository lessonRepository,
                 ChapterRepository chapterRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.exerciseRepository = exerciseRepository;
        this.contentBlockRepository = contentBlockRepository;
        this.lessonRepository = lessonRepository;
        this.chapterRepository = chapterRepository;
    }

    @PostConstruct
    void reset() {
        quizQuestionRepository.deleteAllInBatch();
        exerciseRepository.deleteAllInBatch();
        contentBlockRepository.deleteAllInBatch();
        lessonRepository.deleteAllInBatch();
        chapterRepository.deleteAllInBatch();
    }
}
