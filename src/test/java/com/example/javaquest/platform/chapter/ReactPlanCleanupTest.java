package com.example.javaquest.platform.chapter;

import com.example.javaquest.web.JavaQuestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/** Lekcje i rozdzialy toru REACT usuniete z planu znikaja z bazy przy kolejnym zasiewie. */
@SpringBootTest(classes = JavaQuestApplication.class, properties = {
        "spring.datasource.url=jdbc:h2:mem:react_plan_cleanup;DB_CLOSE_DELAY=-1",
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude="
                + "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,"
                + "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration"
})
@ActiveProfiles("h2")
class ReactPlanCleanupTest {
    @Autowired ContentSeeder seeder;
    @Autowired ChapterRepository chapters;
    @Autowired LessonRepository lessons;

    @Test
    void dropsReactEntriesMissingFromSeedButKeepsOtherTracks() {
        Chapter stale = chapters.save(new Chapter("_react_99_stary", "Stary", 9999, CourseTrack.REACT));
        lessons.save(new Lesson(stale, "01_Old", "Old", 0));
        Chapter kept = chapters.findBySlug("_react_02_komponenty").orElseThrow();
        lessons.save(new Lesson(kept, "99_Removed", "Removed", 99));
        Chapter otherTrack = chapters.save(new Chapter("_lx_99_inny", "Inny", 9998, CourseTrack.LINUX));
        lessons.save(new Lesson(otherTrack, "01_Other", "Other", 0));

        seeder.seed();

        assertThat(chapters.findBySlug("_react_99_stary")).isEmpty();
        assertThat(lessons.findByChapterSlugAndSlug("_react_02_komponenty", "99_Removed")).isEmpty();
        assertThat(lessons.findByChapterSlugAndSlug("_react_02_komponenty", "01_AppShellAndFirstComponent")).isPresent();
        assertThat(lessons.findByChapterSlugAndSlug("_lx_99_inny", "01_Other")).isPresent();
    }
}
