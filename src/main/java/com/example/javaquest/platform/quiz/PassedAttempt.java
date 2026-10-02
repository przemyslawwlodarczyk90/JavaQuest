package com.example.javaquest.platform.quiz;

import java.time.LocalDateTime;

/**
 * Projekcja zaliczonego podejscia do quizu - "zdarzenie" dla mastery (patrz
 * {@link com.example.javaquest.platform.mastery.MasteryPolicy}). Tylko slugi lekcji i czas zakonczenia.
 */
public record PassedAttempt(String chapterSlug, String lessonSlug, LocalDateTime finishedAt) {
}
