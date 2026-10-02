package com.example.javaquest.platform.quiz;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
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
import com.example.javaquest.platform.mastery.MasteryPolicy.LessonMastery;
import com.example.javaquest.platform.mastery.MasteryService;
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
 *
 * <p>Quiz mozna rozwiazywac ZAWSZE. Zaliczenie to co innego niz wzrost mastery: gwiazdki liczy
 * {@link MasteryService} z historii zaliczen (spacing) - odpowiedz konczaca podejscie zwraca tylko
 * liczbe gwiazdek przed i po, zeby ekran wyniku mogl to pokazac (bez zadnych dat).
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

    record MasteryView(int stars, boolean reviewSuggested) {
        static MasteryView of(LessonMastery mastery) {
            return new MasteryView(mastery.stars(), mastery.reviewSuggested());
        }
    }

    /** {@code masteryAvailable} - czy ta lekcja ma dosc pytan, zeby zaliczenia dawaly gwiazdki. */
    record QuizStatus(int questionCount, int drawSize, int passPercent, int requiredCorrect, boolean passed,
                      ResultView lastResult, AttemptView activeAttempt, boolean masteryAvailable,
                      MasteryView mastery) {
    }

    /** {@code masteryBefore/After} - gwiazdki przed i po; tylko w odpowiedzi konczacej podejscie. */
    record AnswerResponse(boolean correct, String correctOption, String explanation, ResultView result,
                          Integer masteryBefore, Integer masteryAfter) {
    }

    private final LessonRepository lessonRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final MasteryService masteryService;
    private final Clock clock;
    private final int drawSize;
    private final int passPercent;
    private final int retryWrongMax;
    private final Random random = new Random();

    QuizService(LessonRepository lessonRepository, QuizQuestionRepository quizQuestionRepository,
                QuizAttemptRepository attemptRepository, UserRepository userRepository,
                MasteryService masteryService, Clock clock,
                @Value("${platform.quiz.draw-size:20}") int drawSize,
                @Value("${platform.quiz.pass-percent:80}") int passPercent,
                @Value("${platform.quiz.retry-wrong-max:5}") int retryWrongMax) {
        this.lessonRepository = lessonRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.masteryService = masteryService;
        this.clock = clock;
        this.drawSize = drawSize;
        this.passPercent = passPercent;
        this.retryWrongMax = retryWrongMax;
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
                QuizRules.requiredCorrect(effectiveDraw, passPercent), passed, last, active,
                effectiveDraw >= masteryService.minQuestions(),
                MasteryView.of(masteryService.forLesson(user, chapterSlug, lessonSlug)));
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
        Set<Integer> wrong = lastAnswerWrong(user, chapterSlug, lessonSlug);
        List<Integer> drawn = QuizRules.draw(List.copyOf(pool.keySet()), seen, wrong, drawSize, retryWrongMax, random);
        QuizAttempt attempt = new QuizAttempt(user, chapterSlug, lessonSlug, LocalDateTime.now(clock));
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
        Integer masteryBefore = null;
        Integer masteryAfter = null;
        if (attempt.getQuestions().stream().allMatch(QuizAttemptQuestion::isAnswered)) {
            masteryBefore = masteryService.forLesson(user, chapterSlug, lessonSlug).stars();
            int correctCount = (int) attempt.getQuestions().stream().filter(QuizAttemptQuestion::getCorrect).count();
            int required = QuizRules.requiredCorrect(attempt.getQuestions().size(), passPercent);
            attempt.finish(correctCount, correctCount >= required, LocalDateTime.now(clock));
            result = result(attempt);
            // Zapytanie o historie widzi juz to podejscie (Hibernate oproznia zmiany przed zapytaniem JPQL).
            masteryAfter = masteryService.forLesson(user, chapterSlug, lessonSlug).stars();
        }
        return new AnswerResponse(correct, question.getCorrectOption(), question.getExplanation(), result,
                masteryBefore, masteryAfter);
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

    /** Pytania "do poprawy": takie, na ktore OSTATNIA odpowiedz uzytkownika byla bledna. */
    private Set<Integer> lastAnswerWrong(User user, String chapterSlug, String lessonSlug) {
        Map<Integer, Boolean> lastAnswer = new HashMap<>();
        for (Object[] row : attemptRepository.findAnswerHistory(user, chapterSlug, lessonSlug)) {
            lastAnswer.put((Integer) row[0], (Boolean) row[1]);
        }
        Set<Integer> wrong = new HashSet<>();
        lastAnswer.forEach((no, correct) -> {
            if (!correct) {
                wrong.add(no);
            }
        });
        return wrong;
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
