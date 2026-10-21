package com.example.javaquest.platform.chapter;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Zasila baze danych platformy lista rozdzialow/lekcji z {@link ChapterSeedData} przy
 * kazdym starcie aplikacji. Synchronizacja po slugach zachowuje identyfikatory, aktualizuje
 * metadane i dodaje brakujace wpisy. Nie usuwa lekcji powiazanych z materialami uzytkownikow.
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
@DependsOn({"contentReset", "courseTrackConstraintSync"})
class ContentSeeder {

    private final ChapterRepository chapterRepository;
    private final LessonRepository lessonRepository;

    ContentSeeder(ChapterRepository chapterRepository, LessonRepository lessonRepository) {
        this.chapterRepository = chapterRepository;
        this.lessonRepository = lessonRepository;
    }

    @PostConstruct
    void seed() {
        var chapters = new java.util.HashMap<String, Chapter>();
        for (Chapter chapter : chapterRepository.findAllByOrderBySortOrderAsc()) {
            chapters.put(chapter.getSlug(), chapter);
        }
        var lessons = new java.util.HashMap<java.util.List<String>, Lesson>();
        for (Lesson lesson : lessonRepository.findAllWithChapter()) {
            lessons.put(java.util.List.of(lesson.getChapter().getSlug(), lesson.getSlug()), lesson);
        }

        int chapterOrder = 0;
        for (ChapterSeedData.ChapterSeed seed : ChapterSeedData.CHAPTERS) {
            Chapter chapter = chapters.get(seed.slug());
            if (chapter == null) chapter = new Chapter(seed.slug(), seed.title(), chapterOrder, seed.track());
            chapter.synchronize(seed.title(), chapterOrder++, seed.track());
            chapter = chapterRepository.save(chapter);

            int lessonOrder = 0;
            for (String lessonSlug : seed.lessonSlugs()) {
                String title = LessonSlugTitles.humanize(lessonSlug);
                Lesson lesson = lessons.get(java.util.List.of(seed.slug(), lessonSlug));
                if (lesson == null) lesson = new Lesson(chapter, lessonSlug, title, lessonOrder);
                lesson.synchronize(title, lessonOrder++);
                lessonRepository.save(lesson);
            }
        }
        removeLessonsDroppedFromReactPlan(chapters, lessons);
    }

    // Tor REACT jest w budowie i jego plan sie zmienia: lekcje i rozdzialy usuniete z ChapterSeedData
    // nie zostaja w bazie jako puste pozycje (o ile uzytkownik nie dodal do nich materialow).
    private void removeLessonsDroppedFromReactPlan(java.util.Map<String, Chapter> chapters,
                                                   java.util.Map<java.util.List<String>, Lesson> lessons) {
        var seeded = new java.util.HashSet<java.util.List<String>>();
        var seededChapters = new java.util.HashSet<String>();
        for (ChapterSeedData.ChapterSeed seed : ChapterSeedData.CHAPTERS) {
            seededChapters.add(seed.slug());
            for (String lessonSlug : seed.lessonSlugs()) seeded.add(java.util.List.of(seed.slug(), lessonSlug));
        }
        var keptChapters = new java.util.HashSet<String>();
        lessons.forEach((key, lesson) -> {
            if (lesson.getChapter().getTrack() != CourseTrack.REACT || seeded.contains(key)) return;
            if (lessonRepository.countMaterials(lesson) > 0) keptChapters.add(key.get(0));
            else lessonRepository.delete(lesson);
        });
        chapters.values().stream()
                .filter(c -> c.getTrack() == CourseTrack.REACT && !seededChapters.contains(c.getSlug())
                        && !keptChapters.contains(c.getSlug()))
                .forEach(chapterRepository::delete);
    }
}
