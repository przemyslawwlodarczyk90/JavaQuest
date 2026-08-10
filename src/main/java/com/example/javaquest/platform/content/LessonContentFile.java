package com.example.javaquest.platform.content;

import java.util.List;
import java.util.Map;

/**
 * Ksztalt pliku JSON z tresc lekcji (patrz
 * {@code src/main/resources/content/<rozdzial>/<lekcja>.json}). Parsowany przez Jacksona
 * (rekordy Javy sa wspierane natywnie od Jacksona 2.12+).
 *
 * <p>Przyklad:
 * <pre>{@code
 * {
 *   "theory": [
 *     { "type": "ANALOGY", "heading": "...", "body": "..." },
 *     { "type": "CODE_EXAMPLE", "heading": "...", "body": "...", "code": "..." }
 *   ],
 *   "exercises": [
 *     { "prompt": "...", "hint": "...", "solution": "..." }
 *   ],
 *   "quiz": [
 *     { "question": "...", "options": {"A": "...", "B": "...", "C": "...", "D": "..."},
 *       "correct": "B", "explanation": "..." }
 *   ]
 * }
 * }</pre>
 */
public record LessonContentFile(
        List<TheoryBlockJson> theory,
        List<ExerciseJson> exercises,
        List<QuizQuestionJson> quiz) {

    /** {@code code} jest opcjonalny (null) dla blokow innych niz CODE_EXAMPLE. */
    public record TheoryBlockJson(String type, String heading, String body, String code) {
    }

    public record ExerciseJson(String prompt, String hint, String solution) {
    }

    public record QuizQuestionJson(String question, Map<String, String> options, String correct,
                                    String explanation) {
    }
}
