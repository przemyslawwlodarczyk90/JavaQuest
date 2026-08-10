package com.example.javaquest.platform.chapter;

/**
 * Zamienia surowy slug lekcji z {@link ChapterSeedData} (np. "00_JavaPlatformBasics")
 * na czytelny, prowizoryczny tytul ("Java Platform Basics") do wyswietlenia w Fazie 1,
 * zanim kazda lekcja dostanie prawdziwy, redakcyjnie napisany tytul wraz z tresci
 * (Faza 2+, patrz EDU_PLATFORM_PLAN.md).
 */
final class LessonSlugTitles {

    private LessonSlugTitles() {
    }

    static String humanize(String slug) {
        String withoutNumericPrefix = slug.replaceFirst("^\\d+_", "");
        String spaced = withoutNumericPrefix
                .replaceAll("([a-z0-9])([A-Z])", "$1 $2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2");
        return spaced.isBlank() ? slug : spaced;
    }
}
