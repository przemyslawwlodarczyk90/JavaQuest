package com.example.javaquest.platform.mastery;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.javaquest.platform.chapter.Chapter;
import com.example.javaquest.platform.chapter.ChapterRepository;
import com.example.javaquest.platform.chapter.CourseTrack;
import com.example.javaquest.platform.mastery.MasteryPolicy.LessonMastery;
import com.example.javaquest.platform.quiz.PassedAttempt;
import com.example.javaquest.platform.quiz.QuizAttemptRepository;
import com.example.javaquest.platform.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mastery lekcji wyliczane przy odczycie z historii zaliczonych podejsc ({@code quiz_attempts}) dla
 * chwili {@code now} z wstrzykiwanego {@link Clock}. Nic tu nie zapisuje do bazy - patrz
 * {@link MasteryPolicy} i MASTERY_V1_PLAN.md.
 */
@Service
public class MasteryService {

    /** Mastery lekcji tak, jak widzi je frontend - celowo BEZ zadnych dat. */
    public record LessonMasteryView(String chapterSlug, String lessonSlug, int stars, boolean reviewSuggested) {
    }

    private final QuizAttemptRepository attemptRepository;
    private final ChapterRepository chapterRepository;
    private final MasteryPolicy policy;
    private final MasteryProperties properties;
    private final Clock clock;

    MasteryService(QuizAttemptRepository attemptRepository, ChapterRepository chapterRepository,
                   MasteryPolicy policy, MasteryProperties properties, Clock clock) {
        this.attemptRepository = attemptRepository;
        this.chapterRepository = chapterRepository;
        this.policy = policy;
        this.properties = properties;
        this.clock = clock;
    }

    /** Ile pytan musi miec podejscie, zeby liczylo sie do mastery. */
    public int minQuestions() {
        return properties.minQuestions();
    }

    @Transactional(readOnly = true)
    public LessonMastery forLesson(User user, String chapterSlug, String lessonSlug) {
        List<LocalDateTime> passedAt = attemptRepository
                .findPassedAttempts(user, chapterSlug, lessonSlug, properties.minQuestions()).stream()
                .map(PassedAttempt::finishedAt)
                .toList();
        return policy.evaluate(passedAt, LocalDateTime.now(clock));
    }

    /** Lekcje toru ({@code null} = wszystkie tory) z co najmniej jedna gwiazdka (pozostale = 0), w kolejnosci kursu. */
    @Transactional(readOnly = true)
    public List<LessonMasteryView> forTrack(User user, CourseTrack track) {
        return evaluateTrack(user, track).stream().map(Entry::view).toList();
    }

    /**
     * Lekcje toru, przy ktorych powtorka teraz cos da: najpierw te, ktore stracily poziom przez uplyw
     * czasu, potem o najmniejszej liczbie gwiazdek, potem w kolejnosci kursu.
     */
    @Transactional(readOnly = true)
    public List<LessonMasteryView> reviews(User user, CourseTrack track) {
        return evaluateTrack(user, track).stream()
                .filter(e -> e.mastery().reviewSuggested())
                .sorted(Comparator.comparing((Entry e) -> !e.mastery().decayed())
                        .thenComparingInt(e -> e.mastery().stars()))
                .map(Entry::view)
                .toList();
    }

    private record Entry(LessonMasteryView view, LessonMastery mastery) {
    }

    private List<Entry> evaluateTrack(User user, CourseTrack track) {
        Map<String, Integer> chapterOrder = new HashMap<>();
        List<Chapter> trackChapters = track == null
                ? chapterRepository.findAllByOrderBySortOrderAsc()
                : chapterRepository.findAllByTrackOrderBySortOrderAsc(track);
        for (Chapter chapter : trackChapters) {
            chapterOrder.put(chapter.getSlug(), chapter.getSortOrder());
        }

        Map<List<String>, List<LocalDateTime>> byLesson = new LinkedHashMap<>();
        for (PassedAttempt attempt : attemptRepository.findPassedAttempts(user, properties.minQuestions())) {
            if (chapterOrder.containsKey(attempt.chapterSlug())) {
                byLesson.computeIfAbsent(List.of(attempt.chapterSlug(), attempt.lessonSlug()), k -> new ArrayList<>())
                        .add(attempt.finishedAt());
            }
        }

        LocalDateTime now = LocalDateTime.now(clock);
        return byLesson.entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<List<String>, List<LocalDateTime>> e) ->
                                chapterOrder.get(e.getKey().get(0)))
                        .thenComparing(e -> e.getKey().get(1)))
                .map(e -> {
                    LessonMastery mastery = policy.evaluate(e.getValue(), now);
                    return new Entry(new LessonMasteryView(e.getKey().get(0), e.getKey().get(1),
                            mastery.stars(), mastery.reviewSuggested()), mastery);
                })
                .toList();
    }
}
