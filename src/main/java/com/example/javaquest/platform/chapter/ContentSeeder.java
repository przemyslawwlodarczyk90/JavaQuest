package com.example.javaquest.platform.chapter;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Zasila baze danych platformy lista rozdzialow/lekcji z {@link ChapterSeedData} przy
 * kazdym starcie aplikacji (tabele tresci sa przed tym czyszczone przez ContentReset). Faza 1 - tylko
 * metadane nawigacyjne, bez tresci lekcji (patrz EDU_PLATFORM_PLAN.md).
 *
 * <p>Celowo {@code @PostConstruct}, NIE {@code ApplicationRunner} - ApplicationRunner uruchamia
 * sie PO starcie wbudowanego serwera (Tomcat zaczyna przyjmowac polaczenia w
 * {@code finishRefresh()}, ApplicationRunnery dopiero PO nim), co dawalo realne, widoczne dla
 * uzytkownika opoznienie ("lekcja w przygotowaniu") tuz po starcie backendu. @PostConstruct
 * wykonuje sie podczas inicjalizacji beanow (PRZED uruchomieniem serwera), wiec baza jest w pelni
 * zasilona, zanim ktokolwiek moze wyslac pierwsze zapytanie.
 *
 * <p>MUSI wystartowac PRZED {@link com.example.javaquest.platform.content.LessonContentLoader},
 * ktory zaklada, ze rozdzialy/lekcje juz istnieja w bazie - wymuszone przez
 * {@code @DependsOn("contentSeeder")} na tamtej klasie.
 */
@Component
@DependsOn("contentReset")
class ContentSeeder {

    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;

    ContentSeeder(ChapterRepository chapterRepository, LessonRepository lessonRepository) {
        this.chapterRepository = chapterRepository;
        this.lessonRepository = lessonRepository;
    }

    @PostConstruct
    void seed() {
        if (chapterRepository.count() > 0) {
            return;
        }

        int chapterOrder = 0;
        for (ChapterSeedData.ChapterSeed seed : ChapterSeedData.CHAPTERS) {
            Chapter chapter = chapterRepository.save(
                    new Chapter(seed.slug(), seed.title(), chapterOrder++, seed.track()));

            int lessonOrder = 0;
            for (String lessonSlug : seed.lessonSlugs()) {
                String title = LessonSlugTitles.humanize(lessonSlug);
                lessonRepository.save(new Lesson(chapter, lessonSlug, title, lessonOrder++));
            }
        }
    }
}
