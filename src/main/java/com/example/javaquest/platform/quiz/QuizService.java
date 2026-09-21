package com.example.javaquest.platform.quiz;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import com.example.javaquest.platform.chapter.Lesson;
import com.example.javaquest.platform.chapter.LessonRepository;
import com.example.javaquest.platform.content.QuizQuestion;
import com.example.javaquest.platform.content.QuizQuestionRepository;
import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Quiz z losowaniem i zaliczeniem na serwerze. Przy starcie podejscia losuje (max) {@code draw-size}
 * pytan z puli lekcji, zapisuje je i podaje w losowej kolejnosci; poprawne odpowiedzi ujawnia dopiero
 * po udzieleniu odpowiedzi na dane pytanie. Podejscie jest zaliczone od {@code pass-percent}% poprawnych
 * (zaokraglone W GORE - patrz {@link QuizRules#requiredCorrect}); niezdane = uzytkownik startuje kolejne.
 */
@Service
class QuizService {

    record AnswerView(String selected, boolean correct, String correctOption, String explanation) {
    }

    record QuestionView(int position, String question, Map<String, String> options, AnswerView answer) {
    }

    record AttemptView(Long id, List<QuestionView> questions) {
    }

    record ResultView(int correctCount, int total, int requiredCorrect, int percent, boolean passed) {
    }

    record QuizStatus(int questionCount, int drawSize, int passPercent, int requiredCorrect, boolean passed,
                      ResultView lastResult, AttemptView activeAttempt) {
    }

    record AnswerResponse(boolean correct, String correctOption, String explanation, ResultView result) {
    }

    private final LessonRepository lessonRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final int drawSize;
    private final int passPercent;
    private final Random random = new Random();

    QuizService(LessonRepository lessonRepository, QuizQuestionRepository quizQuestionRepository,
                QuizAttemptRepository attemptRepository, UserRepository userRepository,
                @Value("${platform.quiz.draw-size:20}") int drawSize,
                @Value("${platform.quiz.pass-percent:80}") int passPercent) {
        this.lessonRepository = lessonRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.drawSize = drawSize;
        this.passPercent = passPercent;
    }

    // Nie readOnly: przy okazji sprzata "nieaktualne" otwarte podejscie (patrz openAttempt).
    @Transactional
    QuizStatus getStatus(String email, String chapterSlug, String lessonSlug) {
        User user = user(email);
        Map<Integer, QuizQuestion> pool = pool(chapterSlug, lessonSlug);
        int effectiveDraw = Math.min(drawSize, pool.size());

        AttemptView active = openAttempt(user, chapterSlug, lessonSlug, pool).map(a -> view(a, pool)).orElse(null);
        ResultView last = attemptRepository
                .findFirstByUserAndChapterSlugAndLessonSlugAndFinishedAtIsNotNullOrderByFinishedAtDesc(
                        user, chapterSlug, lessonSlug)
                .map(this::result).orElse(null);
        boolean passed = attemptRepository.existsByUserAndChapterSlugAndLessonSlugAndPassedTrue(
                user, chapterSlug, lessonSlug);
        return new QuizStatus(pool.size(), effectiveDraw, passPercent,
                QuizRules.requiredCorrect(effectiveDraw, passPercent), passed, last, active);
    }

    /** Wznawia otwarte podejscie, a jesli go nie ma - losuje nowe. Dzieki temu podwojny klik nie losuje dwa razy. */
    @Transactional
    AttemptView startAttempt(String email, String chapterSlug, String lessonSlug) {
        User user = user(email);
        Map<Integer, QuizQuestion> pool = pool(chapterSlug, lessonSlug);
        if (pool.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Quiz tej lekcji jest w przygotowaniu.");
        }
        Optional<QuizAttempt> open = openAttempt(user, chapterSlug, lessonSlug, pool);
        if (open.isPresent()) {
            return view(open.get(), pool);
        }

        Set<Integer> seen = new HashSet<>(attemptRepository.findSeenQuestionNos(user, chapterSlug, lessonSlug));
        List<Integer> drawn = QuizRules.draw(List.copyOf(pool.keySet()), seen, drawSize, random);
        QuizAttempt attempt = new QuizAttempt(user, chapterSlug, lessonSlug);
        drawn.forEach(attempt::addQuestion);
        return view(attemptRepository.save(attempt), pool);
    }

    @Transactional
    AnswerResponse answer(String email, String chapterSlug, String lessonSlug, Long attemptId, int position,
                          String option) {
        if (option == null || !option.matches("[A-D]")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Odpowiedz musi byc A, B, C albo D.");
        }
        User user = user(email);
        QuizAttempt attempt = attemptRepository.findByIdAndUser(attemptId, user)
                .filter(a -> a.getChapterSlug().equals(chapterSlug) && a.getLessonSlug().equals(lessonSlug))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie ma takiego podejscia."));
        if (attempt.isFinished()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "To podejscie jest juz zakonczone.");
        }
        QuizAttemptQuestion aq = attempt.getQuestions().stream()
                .filter(q -> q.getPosition() == position).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie ma takiego pytania."));
        if (aq.isAnswered()) {
            // Odpowiedzi nie da sie zmienic - inaczej dalo by sie "przeklikac" A-B-C-D az do trafienia.
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Na to pytanie juz odpowiedziano.");
        }
        QuizQuestion question = pool(chapterSlug, lessonSlug).get(aq.getQuestionNo());
        if (question == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pytanie nie istnieje juz w tresci lekcji.");
        }

        boolean correct = option.equals(question.getCorrectOption());
        aq.answer(option, correct);

        ResultView result = null;
        if (attempt.getQuestions().stream().allMatch(QuizAttemptQuestion::isAnswered)) {
            int correctCount = (int) attempt.getQuestions().stream().filter(QuizAttemptQuestion::getCorrect).count();
            int required = QuizRules.requiredCorrect(attempt.getQuestions().size(), passPercent);
            attempt.finish(correctCount, correctCount >= required);
            result = result(attempt);
        }
        return new AnswerResponse(correct, question.getCorrectOption(), question.getExplanation(), result);
    }

    /**
     * Otwarte podejscie, o ile nadal pasuje do tresci lekcji. Tresc jest odtwarzana z plikow JSON przy
     * kazdym starcie - jesli pytanie wylosowane w niedokonczonym podejsciu zniknelo z pliku, to podejscie
     * jest bezuzyteczne, wiec je kasujemy (uzytkownik dostanie nowe losowanie).
     */
    private Optional<QuizAttempt> openAttempt(User user, String chapterSlug, String lessonSlug,
                                              Map<Integer, QuizQuestion> pool) {
        Optional<QuizAttempt> open = attemptRepository
                .findFirstByUserAndChapterSlugAndLessonSlugAndFinishedAtIsNull(user, chapterSlug, lessonSlug);
        if (open.isPresent() && !open.get().getQuestions().stream().allMatch(q -> pool.containsKey(q.getQuestionNo()))) {
            attemptRepository.delete(open.get());
            return Optional.empty();
        }
        return open;
    }

    private AttemptView view(QuizAttempt attempt, Map<Integer, QuizQuestion> pool) {
        List<QuestionView> questions = attempt.getQuestions().stream().map(aq -> {
            QuizQuestion q = pool.get(aq.getQuestionNo());
            Map<String, String> options = new LinkedHashMap<>();
            options.put("A", q.getOptionA());
            options.put("B", q.getOptionB());
            options.put("C", q.getOptionC());
            options.put("D", q.getOptionD());
            // Poprawna odpowiedz i wyjasnienie tylko dla pytan, na ktore juz odpowiedziano.
            AnswerView answer = aq.isAnswered()
                    ? new AnswerView(aq.getSelectedOption(), aq.getCorrect(), q.getCorrectOption(), q.getExplanation())
                    : null;
            return new QuestionView(aq.getPosition(), q.getQuestion(), options, answer);
        }).toList();
        return new AttemptView(attempt.getId(), questions);
    }

    private ResultView result(QuizAttempt attempt) {
        int total = attempt.getQuestions().size();
        int correct = attempt.getCorrectCount();
        return new ResultView(correct, total, QuizRules.requiredCorrect(total, passPercent),
                Math.round(correct * 100f / total), Boolean.TRUE.equals(attempt.getPassed()));
    }

    /** Pula pytan lekcji: numer pytania (sortOrder) -> pytanie, w kolejnosci z pliku. */
    private Map<Integer, QuizQuestion> pool(String chapterSlug, String lessonSlug) {
        Lesson lesson = lessonRepository.findByChapterSlugAndSlug(chapterSlug, lessonSlug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie ma takiej lekcji."));
        Map<Integer, QuizQuestion> pool = new LinkedHashMap<>();
        quizQuestionRepository.findByLessonIdOrderBySortOrderAsc(lesson.getId())
                .forEach(q -> pool.put(q.getSortOrder(), q));
        return pool;
    }

    private User user(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
