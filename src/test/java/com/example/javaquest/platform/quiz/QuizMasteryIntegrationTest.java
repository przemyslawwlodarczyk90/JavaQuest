package com.example.javaquest.platform.quiz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.javaquest.platform.chapter.LessonRepository;
import com.example.javaquest.platform.content.QuizQuestion;
import com.example.javaquest.platform.content.QuizQuestionRepository;
import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import com.example.javaquest.web.JavaQuestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * Pelny przeplyw quiz -> mastery na prawdziwym kontekscie platformy (H2 w pamieci, prawdziwa tresc
 * lekcji z JSON) z przestawialnym zegarem - zaden test nie zalezy od realnej daty.
 * Kazdy test pracuje na wlasnych, swiezych uzytkownikach, bo kontekst (i baza) jest wspolny.
 */
@SpringBootTest(classes = {JavaQuestApplication.class, QuizMasteryIntegrationTest.ClockConfig.class},
        properties = {
                "spring.flyway.enabled=false",
                "spring.autoconfigure.exclude="
                        + "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,"
                        + "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,"
                        + "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration"
        })
@ActiveProfiles("h2")
class QuizMasteryIntegrationTest {

    private static final String CHAPTER = "_03_collections";
    private static final String LESSON = "08_HashMap";

    /** Zegar, ktory test przestawia recznie. */
    static final class MutableClock extends Clock {
        private Instant now = Instant.parse("2026-01-05T20:00:00Z");

        void advance(Duration duration) {
            now = now.plus(duration);
        }

        @Override
        public ZoneId getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return now;
        }
    }

    @TestConfiguration
    static class ClockConfig {
        @Bean
        @Primary
        MutableClock testClock() {
            return new MutableClock();
        }
    }

    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizAttemptRepository attemptRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private QuizQuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MutableClock clock;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private String newUser() {
        String email = UUID.randomUUID() + "@example.com";
        User user = new User("Test", "User", email, "x");
        user.setEnabled(true);
        userRepository.save(user);
        return email;
    }

    private Map<Integer, String> correctOptions() {
        Long lessonId = lessonRepository.findByChapterSlugAndSlug(CHAPTER, LESSON).orElseThrow().getId();
        return questionRepository.findByLessonIdOrderBySortOrderAsc(lessonId).stream()
                .collect(Collectors.toMap(QuizQuestion::getSortOrder, QuizQuestion::getCorrectOption));
    }

    /** position -> numer pytania w lekcji dla podejscia. */
    private Map<Integer, Integer> questionNos(Long attemptId) {
        return transactionTemplate.execute(status -> attemptRepository.findById(attemptId).orElseThrow()
                .getQuestions().stream()
                .collect(Collectors.toMap(QuizAttemptQuestion::getPosition, QuizAttemptQuestion::getQuestionNo)));
    }

    private static String wrongOption(String correct) {
        return correct.equals("A") ? "B" : "A";
    }

    /** Rozwiazuje cale podejscie: {@code correctAnswers} pierwszych pozycji poprawnie, reszta zle. */
    private QuizService.AnswerResponse solve(String email, int correctAnswers) {
        QuizService.AttemptView attempt = quizService.startAttempt(email, CHAPTER, LESSON);
        Map<Integer, String> correct = correctOptions();
        Map<Integer, Integer> nos = questionNos(attempt.id());
        QuizService.AnswerResponse last = null;
        for (QuizService.QuestionView q : attempt.questions()) {
            String right = correct.get(nos.get(q.position()));
            String option = q.position() < correctAnswers ? right : wrongOption(right);
            last = quizService.answer(email, CHAPTER, LESSON, attempt.id(), q.position(), option);
        }
        return last;
    }

    @Test
    void firstPassGivesStarButRepeatingImmediatelyDoesNot() {
        String email = newUser();
        assertThat(quizService.getStatus(email, CHAPTER, LESSON).mastery().stars()).isZero();
        assertThat(quizService.getStatus(email, CHAPTER, LESSON).masteryAvailable()).isTrue();

        QuizService.AnswerResponse first = solve(email, 20);
        assertThat(first.result().passed()).isTrue();
        assertThat(first.masteryBefore()).isZero();
        assertThat(first.masteryAfter()).isEqualTo(1);

        clock.advance(Duration.ofHours(1));
        QuizService.AnswerResponse again = solve(email, 20);
        assertThat(again.result().passed()).as("quiz zaliczony i zapisany").isTrue();
        assertThat(again.masteryAfter()).as("ale gwiazdka bez zmian - za wczesnie").isEqualTo(1);

        clock.advance(Duration.ofHours(21));
        QuizService.AnswerResponse spaced = solve(email, 20);
        assertThat(spaced.masteryBefore()).isEqualTo(1);
        assertThat(spaced.masteryAfter()).isEqualTo(2);
        assertThat(quizService.getStatus(email, CHAPTER, LESSON).mastery().stars()).isEqualTo(2);
    }

    @Test
    void failedQuizChangesNothingAndRetryBringsBackWrongQuestions() {
        String email = newUser();
        QuizService.AnswerResponse failed = solve(email, 10);
        assertThat(failed.result().passed()).isFalse();
        assertThat(failed.masteryBefore()).isZero();
        assertThat(failed.masteryAfter()).isZero();

        Long firstId = attemptRepository
                .findFirstByUserAndChapterSlugAndLessonSlugAndFinishedAtIsNotNullOrderByFinishedAtDesc(
                        userRepository.findByEmail(email).orElseThrow(), CHAPTER, LESSON)
                .orElseThrow().getId();
        Set<Integer> wrongBefore = questionNos(firstId).entrySet().stream()
                .filter(e -> e.getKey() >= 10).map(Map.Entry::getValue).collect(Collectors.toSet());

        QuizService.AttemptView retry = quizService.startAttempt(email, CHAPTER, LESSON);
        assertThat(retry.id()).isNotEqualTo(firstId);
        Set<Integer> retryNos = new HashSet<>(questionNos(retry.id()).values());
        retryNos.retainAll(wrongBefore);
        assertThat(retryNos).as("do 5 pytan wczesniej blednych wraca w powtorce").hasSize(5);

        // Ponowna proba moze zaliczyc - i wtedy dopiero pierwsza gwiazdka
        Map<Integer, String> correct = correctOptions();
        Map<Integer, Integer> nos = questionNos(retry.id());
        QuizService.AnswerResponse last = null;
        for (QuizService.QuestionView q : retry.questions()) {
            last = quizService.answer(email, CHAPTER, LESSON, retry.id(), q.position(),
                    correct.get(nos.get(q.position())));
        }
        assertThat(last.masteryAfter()).isEqualTo(1);
    }

    @Test
    void answeringTheSameQuestionTwiceIsRejected() {
        String email = newUser();
        QuizService.AttemptView attempt = quizService.startAttempt(email, CHAPTER, LESSON);
        quizService.answer(email, CHAPTER, LESSON, attempt.id(), 0, "A");
        assertThatThrownBy(() -> quizService.answer(email, CHAPTER, LESSON, attempt.id(), 0, "B"))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        e -> assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT));
        // ponowny start nie losuje nowego podejscia, tylko wznawia to samo
        assertThat(quizService.startAttempt(email, CHAPTER, LESSON).id()).isEqualTo(attempt.id());
    }

    @Test
    void finishedAttemptCannotBeSubmittedAgain() {
        String email = newUser();
        solve(email, 20);
        Long id = attemptRepository
                .findFirstByUserAndChapterSlugAndLessonSlugAndFinishedAtIsNotNullOrderByFinishedAtDesc(
                        userRepository.findByEmail(email).orElseThrow(), CHAPTER, LESSON)
                .orElseThrow().getId();
        assertThatThrownBy(() -> quizService.answer(email, CHAPTER, LESSON, id, 19, "A"))
                .isInstanceOf(ResponseStatusException.class);
        assertThat(quizService.getStatus(email, CHAPTER, LESSON).mastery().stars()).isEqualTo(1);
    }

    @Test
    void usersDoNotShareMastery() {
        String alice = newUser();
        String bob = newUser();
        solve(alice, 20);
        assertThat(quizService.getStatus(alice, CHAPTER, LESSON).mastery().stars()).isEqualTo(1);
        assertThat(quizService.getStatus(bob, CHAPTER, LESSON).mastery().stars()).isZero();
    }

    @Test
    void attemptsOfOneUserNeverTouchAnotherUsersAttempt() {
        String alice = newUser();
        String bob = newUser();
        QuizService.AttemptView attempt = quizService.startAttempt(alice, CHAPTER, LESSON);
        assertThatThrownBy(() -> quizService.answer(bob, CHAPTER, LESSON, attempt.id(), 0, "A"))
                .isInstanceOfSatisfying(ResponseStatusException.class,
                        e -> assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        assertThat(quizService.getStatus(bob, CHAPTER, LESSON).activeAttempt()).isNull();
    }
}
