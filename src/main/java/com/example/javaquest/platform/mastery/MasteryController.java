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
 * wylacznie gwiazdki i flage "warto powtorzyc" - zadnych dat ani terminow (patrz MASTERY_V1_PLAN.md).
 * Bez {@code ?track=} - wszystkie tory naraz (frontend laduje to raz po zalogowaniu).
 */
@RestController
@RequestMapping("/api/mastery")
class MasteryController {

    private final MasteryService masteryService;
    private final UserRepository userRepository;

    MasteryController(MasteryService masteryService, UserRepository userRepository) {
        this.masteryService = masteryService;
        this.userRepository = userRepository;
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
