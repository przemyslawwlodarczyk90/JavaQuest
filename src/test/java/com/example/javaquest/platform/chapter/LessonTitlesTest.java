package com.example.javaquest.platform.chapter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class LessonTitlesTest {

    @Test
    void everySeededLessonHasPolishTitle() {
        LessonTitles titles = LessonTitles.load();
        var missing = new ArrayList<String>();
        for (ChapterSeedData.ChapterSeed chapter : ChapterSeedData.CHAPTERS) {
            for (String lessonSlug : chapter.lessonSlugs()) {
                if (!titles.hasTitle(chapter.slug(), lessonSlug)) {
                    missing.add(chapter.slug() + "/" + lessonSlug);
                }
            }
        }
        assertThat(missing).as("lekcje bez wpisu w " + LessonTitles.RESOURCE).isEmpty();
    }

    @Test
    void fallsBackToHumanizedSlugForUnknownLesson() {
        assertThat(LessonTitles.load().titleOf("_nieznany", "01_SomeNewLesson")).isEqualTo("Some New Lesson");
    }
}
