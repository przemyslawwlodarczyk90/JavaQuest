package com.example.javaquest.platform.mastery;

import java.util.List;

import com.example.javaquest.platform.chapter.CourseTrack;
import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Mastery zalogowanego uzytkownika (prywatne - tylko konto z tokenu JWT). Odpowiedzi zawieraja
 * wylacznie gwiazdki i flage "warto powtorzyc" - zadnych dat ani terminow.
 * Bez {@code ?track=} - wszystkie tory naraz (frontend laduje to raz po zalogowaniu).
 */
@RestController
@RequestMapping("/api/mastery")
class MasteryController {

    private final MasteryService masteryService;
    private final UserRepository userRepository;
    private final LearningSummaryService summaryService;
    private final MasteryProperties properties;
    private final int passPercent;

    MasteryController(MasteryService masteryService, UserRepository userRepository, LearningSummaryService summaryService,
                      MasteryProperties properties, @org.springframework.beans.factory.annotation.Value("${platform.quiz.pass-percent:80}") int passPercent) {
        this.masteryService = masteryService;
        this.userRepository = userRepository;
        this.summaryService = summaryService;
        this.properties = properties;
        this.passPercent = passPercent;
    }

    record PointsRules(int minQuestions, int passPercent, long firstReviewHours, long secondReviewDays,
                       long renewalDays, long twoStarDays, long threeStarDays, int warningDays) {}

    @GetMapping("/rules")
    PointsRules rules() {
        return new PointsRules(properties.minQuestions(), passPercent, properties.spacingLevel1().toHours(),
                properties.spacingLevel2().toDays(), properties.spacingLevel3().toDays(),
                properties.retentionLevel2().toDays(), properties.retentionLevel3().toDays(), LearningSummaryService.WARNING_DAYS);
    }

    @GetMapping("/expiring")
    List<LearningSummaryService.ExpiringLesson> expiring(Authentication authentication) {
        return summaryService.expiringLessons(currentUser(authentication));
    }

    @GetMapping("/quote")
    com.example.javaquest.platform.Utill.MotivationalQuotes.Quote quote(@RequestParam(required = false) Integer exclude) {
        return com.example.javaquest.platform.Utill.MotivationalQuotes.randomExcluding(exclude);
    }

    @GetMapping("/summary")
    LearningSummaryService.Summary getSummary(Authentication authentication) {
        return summaryService.forUser(currentUser(authentication));
    }

    @GetMapping
    List<MasteryService.LessonMasteryView> getMastery(@RequestParam(required = false) CourseTrack track,
                                                      Authentication authentication) {
        return masteryService.forTrack(currentUser(authentication), track);
    }

    @GetMapping("/reviews")
    List<MasteryService.LessonMasteryView> getReviews(@RequestParam(required = false) CourseTrack track,
                                                      Authentication authentication) {
        return masteryService.reviews(currentUser(authentication), track);
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
