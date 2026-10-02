package com.example.javaquest.platform.mastery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.example.javaquest.platform.chapter.Chapter;
import com.example.javaquest.platform.chapter.ChapterRepository;
import com.example.javaquest.platform.chapter.CourseTrack;
import com.example.javaquest.platform.mastery.MasteryService.LessonMasteryView;
import com.example.javaquest.platform.quiz.PassedAttempt;
import com.example.javaquest.platform.quiz.QuizAttemptRepository;
import com.example.javaquest.platform.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Mastery per uzytkownik / lekcja / tor - repozytoria zamockowane, zegar staly. */
class MasteryServiceTest {

    private static final LocalDateTime NOW = LocalDateTime.of(2026, 3, 1, 12, 0);

    private final QuizAttemptRepository attempts = mock(QuizAttemptRepository.class);
    private final ChapterRepository chapters = mock(ChapterRepository.class);
    private final MasteryProperties properties = new MasteryProperties(10, Duration.ofHours(20),
            Duration.ofDays(6), Duration.ofDays(30), Duration.ofDays(30), Duration.ofDays(60));
    private final MasteryService service = new MasteryService(attempts, chapters, new MasteryPolicy(properties),
            properties, Clock.fixed(NOW.toInstant(ZoneOffset.UTC), ZoneOffset.UTC));

    private final User alice = new User("Alice", "A", "alice@example.com", "x");
    private final User bob = new User("Bob", "B", "bob@example.com", "x");

    @BeforeEach
    void chapters() {
        when(chapters.findAllByTrackOrderBySortOrderAsc(CourseTrack.JAVA)).thenReturn(List.of(
                new Chapter("_01_fundamentals", "Podstawy", 0, CourseTrack.JAVA),
                new Chapter("_03_collections", "Kolekcje", 2, CourseTrack.JAVA)));
        when(chapters.findAllByTrackOrderBySortOrderAsc(CourseTrack.JAVASCRIPT)).thenReturn(List.of(
                new Chapter("_js_01_basics", "Podstawy JS", 100, CourseTrack.JAVASCRIPT)));
        when(chapters.findAllByTrackOrderBySortOrderAsc(CourseTrack.LINUX)).thenReturn(List.of(
                new Chapter("_lx_01_intro", "Wprowadzenie", 200, CourseTrack.LINUX)));
    }

    private static PassedAttempt pass(String chapter, String lesson, LocalDateTime at) {
        return new PassedAttempt(chapter, lesson, at);
    }

    @Test
    void twoUsersHaveIndependentMastery() {
        when(attempts.findPassedAttempts(eq(alice), eq(10))).thenReturn(List.of(
                pass("_03_collections", "08_HashMap", NOW.minusDays(10)),
                pass("_03_collections", "08_HashMap", NOW.minusDays(9)),
                pass("_03_collections", "08_HashMap", NOW.minusDays(2))));
        when(attempts.findPassedAttempts(eq(bob), eq(10))).thenReturn(List.of(
                pass("_03_collections", "08_HashMap", NOW.minusHours(1))));

        assertThat(service.forTrack(alice, CourseTrack.JAVA))
                .containsExactly(new LessonMasteryView("_03_collections", "08_HashMap", 3, false));
        assertThat(service.forTrack(bob, CourseTrack.JAVA))
                .containsExactly(new LessonMasteryView("_03_collections", "08_HashMap", 1, false));
    }

    @Test
    void twoLessonsAreIndependentAndOrderedByCourse() {
        when(attempts.findPassedAttempts(eq(alice), eq(10))).thenReturn(List.of(
                pass("_03_collections", "08_HashMap", NOW.minusDays(3)),
                pass("_03_collections", "08_HashMap", NOW.minusDays(1)),
                pass("_01_fundamentals", "01_Variables", NOW.minusHours(2))));

        assertThat(service.forTrack(alice, CourseTrack.JAVA)).containsExactly(
                new LessonMasteryView("_01_fundamentals", "01_Variables", 1, false),
                new LessonMasteryView("_03_collections", "08_HashMap", 2, false));
    }

    @Test
    void tracksAreFilteredByChapter() {
        when(attempts.findPassedAttempts(eq(alice), eq(10))).thenReturn(List.of(
                pass("_03_collections", "08_HashMap", NOW.minusDays(1)),
                pass("_js_01_basics", "01_Variables", NOW.minusDays(1)),
                pass("_lx_01_intro", "01_WhatIsLinux", NOW.minusDays(1))));

        assertThat(service.forTrack(alice, CourseTrack.JAVA)).extracting(LessonMasteryView::chapterSlug)
                .containsExactly("_03_collections");
        assertThat(service.forTrack(alice, CourseTrack.JAVASCRIPT)).extracting(LessonMasteryView::chapterSlug)
                .containsExactly("_js_01_basics");
        assertThat(service.forTrack(alice, CourseTrack.LINUX)).extracting(LessonMasteryView::chapterSlug)
                .containsExactly("_lx_01_intro");
    }

    @Test
    void reviewsListDecayedLessonsFirstAndSkipFreshOnes() {
        when(attempts.findPassedAttempts(eq(alice), eq(10))).thenReturn(List.of(
                // swieze ★ - jeszcze nic do powtorki
                pass("_01_fundamentals", "01_Variables", NOW.minusHours(2)),
                // ★ sprzed 2 dni - powtorka da ★★
                pass("_01_fundamentals", "02_Operators", NOW.minusDays(2)),
                // ★★★ sprzed 80 dni - spadlo do ★★
                pass("_03_collections", "08_HashMap", NOW.minusDays(90)),
                pass("_03_collections", "08_HashMap", NOW.minusDays(89)),
                pass("_03_collections", "08_HashMap", NOW.minusDays(80))));

        assertThat(service.reviews(alice, CourseTrack.JAVA)).containsExactly(
                new LessonMasteryView("_03_collections", "08_HashMap", 2, true),
                new LessonMasteryView("_01_fundamentals", "02_Operators", 1, true));
    }

    @Test
    void singleLessonUsesItsOwnHistory() {
        when(attempts.findPassedAttempts(eq(alice), eq("_03_collections"), eq("08_HashMap"), anyInt()))
                .thenReturn(List.of(pass("_03_collections", "08_HashMap", NOW.minusDays(1))));
        when(attempts.findPassedAttempts(eq(alice), eq("_03_collections"), eq("09_PriorityQueue"), anyInt()))
                .thenReturn(List.of());

        assertThat(service.forLesson(alice, "_03_collections", "08_HashMap").stars()).isEqualTo(1);
        assertThat(service.forLesson(alice, "_03_collections", "08_HashMap").reviewSuggested()).isTrue();
        assertThat(service.forLesson(alice, "_03_collections", "09_PriorityQueue").stars()).isZero();
    }
}
