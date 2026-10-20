package com.example.javaquest.platform.mastery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.*;
import com.example.javaquest.platform.chapter.*;
import com.example.javaquest.platform.progress.LessonProgressRepository;
import com.example.javaquest.platform.quiz.*;
import com.example.javaquest.platform.user.User;
import org.junit.jupiter.api.Test;

class LearningSummaryServiceTest {
    private final ChapterRepository chapters = mock(ChapterRepository.class);
    private final LessonRepository lessons = mock(LessonRepository.class);
    private final LessonProgressRepository progress = mock(LessonProgressRepository.class);
    private final QuizAttemptRepository attempts = mock(QuizAttemptRepository.class);
    private final User alice = new User("Alice", "Test", "alice@test.local", "x");
    private final User bob = new User("Bob", "Test", "bob@test.local", "x");
    private final LocalDateTime now = LocalDateTime.of(2026, 10, 19, 12, 0);
    private final MasteryProperties properties = new MasteryProperties(10, Duration.ofHours(20),
            Duration.ofDays(6), Duration.ofDays(30), Duration.ofDays(30), Duration.ofDays(60));

    private LearningSummaryService service(LocalDateTime at) {
        when(chapters.findAllByOrderBySortOrderAsc()).thenReturn(Arrays.stream(CourseTrack.values())
                .map(t -> new Chapter(t.name(), t.name(), t.ordinal(), t)).toList());
        return new LearningSummaryService(chapters, lessons, progress, attempts, new MasteryPolicy(properties),
                properties, Clock.fixed(at.toInstant(ZoneOffset.UTC), ZoneOffset.UTC));
    }

    private List<PassedAttempt> history() {
        return new ArrayList<>(List.of(
                new PassedAttempt("JAVA", "a", now.minusDays(29)),
                new PassedAttempt("JAVA", "a", now.minusDays(28)),
                new PassedAttempt("JAVASCRIPT", "a", now.minusDays(200)),
                new PassedAttempt("LINUX", "a", now.minusDays(67)),
                new PassedAttempt("LINUX", "a", now.minusDays(66)),
                new PassedAttempt("LINUX", "a", now.minusDays(59))));
    }

    @Test void aggregatesAllCoursesAndCountsOnlyPointsThatActuallyExpire() {
        when(attempts.findPassedAttempts(alice, 10)).thenReturn(history());
        var result = service(now).forUser(alice);
        assertThat(result.points()).isEqualTo(6);
        assertThat(result.expiringSoon()).isEqualTo(2);
        assertThat(result.courses()).hasSize(CourseTrack.values().length);
        assertThat(result.courses()).extracting(LearningSummaryService.CourseSummary::points)
                .containsExactly(2L, 1L, 3L, 0L);
        verify(attempts, times(1)).findPassedAttempts(alice, 10);
        verify(lessons, never()).findAll();
        assertThat(service(now).forUser(bob).points()).isZero();
    }

    @Test void strictExpiryBoundaryAndPermanentFirstStarArePreserved() {
        when(attempts.findPassedAttempts(alice, 10)).thenReturn(history());
        assertThat(service(now.plusDays(2)).forUser(alice).points()).isEqualTo(5);
        assertThat(service(now.plusDays(2).plusNanos(1)).forUser(alice).points()).isEqualTo(4);
        assertThat(service(now.plusYears(10)).forUser(alice).points()).isEqualTo(3);
        assertThat(service(now.plusYears(10)).forUser(alice).expiringSoon()).isZero();
    }

    @Test void spacedRepetitionsRenewBothCoursesAndEarlyRepeatsDoNot() {
        var history = history();
        history.add(new PassedAttempt("LINUX", "a", now));
        history.add(new PassedAttempt("JAVA", "a", now));
        when(attempts.findPassedAttempts(alice, 10)).thenReturn(history);
        var result = service(now).forUser(alice);
        assertThat(result.points()).isEqualTo(7);
        assertThat(result.expiringSoon()).isZero();
        history.add(new PassedAttempt("JAVA", "a", now.plusHours(1)));
        assertThat(service(now.plusDays(60)).forUser(alice).points()).isEqualTo(7);
        assertThat(service(now.plusDays(60).plusNanos(1)).forUser(alice).points()).isEqualTo(5);
    }
}
