package com.example.javaquest.platform.content;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * Sprawdza WSZYSTKIE pliki tresci lekcji tym samym rekordem, ktorym czyta je LessonContentLoader.
 * Czysty ObjectMapper (w odroznieniu od springowego) odrzuca nieznane pola, wiec literowka w nazwie
 * pola (np. "cod" zamiast "code") wysypie test zamiast po cichu zniknac z platformy.
 */
class LessonContentFilesTest {

    private static final Path CONTENT = Path.of("src/main/resources/content");
    private static final Set<String> OPTION_KEYS = Set.of("A", "B", "C", "D");

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void everyLessonFileParsesAndHasValidQuiz() throws IOException {
        List<Path> files = lessonFiles();
        assertThat(files).isNotEmpty();

        for (Path file : files) {
            LessonContentFile lesson = mapper.readValue(file.toFile(), LessonContentFile.class);
            for (int i = 0; i < lesson.quiz().size(); i++) {
                LessonContentFile.QuizQuestionJson q = lesson.quiz().get(i);
                String where = file + " quiz[" + i + "]";
                assertThat(q.question()).as(where).isNotBlank();
                assertThat(q.options().keySet()).as(where).isEqualTo(OPTION_KEYS);
                assertThat(q.options().values()).as(where).allSatisfy(o -> assertThat(o).isNotBlank());
                assertThat(q.correct()).as(where).isIn(OPTION_KEYS);
                assertThat(q.explanation()).as(where).isNotBlank();
                if (q.code() != null) {
                    assertThat(q.code()).as(where + " code").isNotBlank();
                }
            }
        }
    }

    @Test
    void everyTheoryImagePointsToExistingAssetWithAltText() throws IOException {
        Path assets = Path.of("frontend/public/lesson-assets");
        for (Path file : lessonFiles()) {
            LessonContentFile lesson = mapper.readValue(file.toFile(), LessonContentFile.class);
            for (int i = 0; i < lesson.theory().size(); i++) {
                LessonContentFile.TheoryBlockJson block = lesson.theory().get(i);
                if (block.image() == null) {
                    continue;
                }
                String where = CONTENT.relativize(file) + " theory[" + i + "]";
                assertThat(block.image()).as(where).matches("[a-z0-9_/-]+\\.(svg|png|webp)");
                assertThat(Files.isRegularFile(assets.resolve(block.image()))).as(where + " plik").isTrue();
                assertThat(block.imageAlt()).as(where + " imageAlt").isNotBlank();
            }
        }
    }

    @Test
    void quizCodeFieldIsReadFromJson() throws IOException {
        LessonContentFile lesson = mapper.readValue(
                CONTENT.resolve("_32_algorithms_and_data_structures/01_BigONotation.json").toFile(),
                LessonContentFile.class);

        assertThat(lesson.quiz()).anySatisfy(q -> assertThat(q.code()).contains("for ("));
        assertThat(lesson.quiz()).anySatisfy(q -> assertThat(q.code()).isNull());
    }

    private static List<Path> lessonFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(CONTENT)) {
            return paths.filter(p -> p.toString().endsWith(".json")).sorted().toList();
        }
    }
}
