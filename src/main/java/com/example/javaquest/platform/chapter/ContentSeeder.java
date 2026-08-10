package com.example.javaquest.platform.chapter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Zasila baze danych platformy lista rozdzialow/lekcji z {@link ChapterSeedData} przy
 * pierwszym starcie aplikacji (jesli tabela "chapters" jest pusta). Faza 1 - tylko
 * metadane nawigacyjne, bez tresci lekcji (patrz EDU_PLATFORM_PLAN.md).
 *
 * <p>{@code @Order(1)} - MUSI wystartowac PRZED
 * {@link com.example.javaquest.platform.content.LessonContentLoader} ({@code @Order(2)}),
 * ktory zaklada, ze rozdzialy/lekcje juz istnieja w bazie.
 */
@Component
@Order(1)
class ContentSeeder implements ApplicationRunner {

    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;

    ContentSeeder(ChapterRepository chapterRepository, LessonRepository lessonRepository) {
        this.chapterRepository = chapterRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (chapterRepository.count() > 0) {
            return;
        }

        int chapterOrder = 0;
        for (ChapterSeedData.ChapterSeed seed : ChapterSeedData.CHAPTERS) {
            Chapter chapter = chapterRepository.save(new Chapter(seed.slug(), seed.title(), chapterOrder++));

            int lessonOrder = 0;
            for (String lessonSlug : seed.lessonSlugs()) {
                String title = LessonSlugTitles.humanize(lessonSlug);
                lessonRepository.save(new Lesson(chapter, lessonSlug, title, lessonOrder++));
            }
        }
    }
}
