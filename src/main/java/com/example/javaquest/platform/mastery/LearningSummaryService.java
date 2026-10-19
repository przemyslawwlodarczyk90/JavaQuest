package com.example.javaquest.platform.mastery;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

import com.example.javaquest.platform.chapter.*;
import com.example.javaquest.platform.progress.LessonProgressRepository;
import com.example.javaquest.platform.quiz.QuizAttemptRepository;
import com.example.javaquest.platform.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Read-only aggregation of existing Mastery; one star equals one valid point. */
@Service
public class LearningSummaryService {
    public record CourseSummary(CourseTrack track, long points, long completedLessons, long totalLessons) {}
    public record Summary(long points, long expiringSoon, long completedLessons, long totalLessons,
                          List<CourseSummary> courses) {}

    private final ChapterRepository chapters;
    private final LessonRepository lessons;
    private final LessonProgressRepository progress;
    private final QuizAttemptRepository attempts;
    private final MasteryPolicy policy;
    private final MasteryProperties properties;
    private final Clock clock;

    public LearningSummaryService(ChapterRepository chapters, LessonRepository lessons,
            LessonProgressRepository progress, QuizAttemptRepository attempts,
            MasteryPolicy policy, MasteryProperties properties, Clock clock) {
        this.chapters = chapters;
        this.lessons = lessons;
        this.progress = progress;
        this.attempts = attempts;
        this.policy = policy;
        this.properties = properties;
        this.clock = clock;
    }

    @Transactional(readOnly = true)
    public Summary forUser(User user) {
        var tracks = new HashMap<String, CourseTrack>();
        var totals = new EnumMap<CourseTrack, long[]>(CourseTrack.class);
        for (var chapter : chapters.findAllByOrderBySortOrderAsc()) {
            tracks.put(chapter.getSlug(), chapter.getTrack());
            totals.computeIfAbsent(chapter.getTrack(), key -> new long[3]);
        }
        // Database counts, without loading lesson bodies or one query per chapter/course.
        for (var count : lessons.countByChapter()) {
            var track = tracks.get(count.getChapterSlug());
            if (track != null) totals.get(track)[2] += count.getTotal();
        }
        for (var count : progress.countByChapter(user)) {
            var track = tracks.get(count.getChapterSlug());
            if (track != null) totals.get(track)[1] += count.getTotal();
        }
        var history = new HashMap<List<String>, List<LocalDateTime>>();
        for (var attempt : attempts.findPassedAttempts(user, properties.minQuestions())) {
            if (tracks.containsKey(attempt.chapterSlug())) {
                history.computeIfAbsent(List.of(attempt.chapterSlug(), attempt.lessonSlug()), key -> new ArrayList<>())
                        .add(attempt.finishedAt());
            }
        }
        var now = LocalDateTime.now(clock);
        long expiring = 0;
        for (var entry : history.entrySet()) {
            var state = policy.replay(entry.getValue());
            int points = policy.effectiveLevel(state, now);
            totals.get(tracks.get(entry.getKey().getFirst()))[0] += points;
            expiring += points - policy.effectiveLevel(state, now.plusDays(7));
        }
        var courses = totals.entrySet().stream().map(e -> new CourseSummary(e.getKey(),
                e.getValue()[0], e.getValue()[1], e.getValue()[2])).toList();
        return new Summary(courses.stream().mapToLong(CourseSummary::points).sum(), expiring,
                courses.stream().mapToLong(CourseSummary::completedLessons).sum(),
                courses.stream().mapToLong(CourseSummary::totalLessons).sum(), courses);
    }
}
