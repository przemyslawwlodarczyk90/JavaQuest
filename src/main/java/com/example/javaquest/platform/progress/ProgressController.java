package com.example.javaquest.platform.progress;

import java.util.List;

import com.example.javaquest.platform.chapter.LessonRepository;
import com.example.javaquest.platform.user.User;
import com.example.javaquest.platform.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Postep uzytkownika: ktore lekcje odhaczyl jako zrobione. Dane sa PRYWATNE - kazdy endpoint
 * dziala wylacznie na koncie z tokenu JWT, nie da sie odczytac ani zmienic cudzego postepu.
 */
@RestController
class ProgressController {

    record CompletedLesson(String chapterSlug, String lessonSlug) {
    }

    record CompletionRequest(boolean completed) {
    }

    private final LessonProgressRepository progressRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    ProgressController(LessonProgressRepository progressRepository, LessonRepository lessonRepository,
                       UserRepository userRepository) {
        this.progressRepository = progressRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    /** Wszystkie lekcje odhaczone przez zalogowanego uzytkownika (frontend liczy z tego postep rozdzialow). */
    @GetMapping("/api/progress")
    @Transactional(readOnly = true)
    List<CompletedLesson> getProgress(Authentication authentication) {
        return progressRepository.findByUser(currentUser(authentication)).stream()
                .map(p -> new CompletedLesson(p.getChapterSlug(), p.getLessonSlug()))
                .toList();
    }

    /** Idempotentne: ponowne zaznaczenie zaznaczonej / odznaczenie odznaczonej lekcji nic nie psuje. */
    @PutMapping("/api/chapters/{chapterSlug}/lessons/{lessonSlug}/progress")
    @Transactional
    CompletionRequest setCompleted(@PathVariable String chapterSlug, @PathVariable String lessonSlug,
                                   @RequestBody CompletionRequest request, Authentication authentication) {
        if (lessonRepository.findByChapterSlugAndSlug(chapterSlug, lessonSlug).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie ma takiej lekcji.");
        }
        User user = currentUser(authentication);
        boolean exists = progressRepository.existsByUserAndChapterSlugAndLessonSlug(user, chapterSlug, lessonSlug);
        if (request.completed() && !exists) {
            progressRepository.save(new LessonProgress(user, chapterSlug, lessonSlug));
        } else if (!request.completed() && exists) {
            progressRepository.deleteByUserAndChapterSlugAndLessonSlug(user, chapterSlug, lessonSlug);
        }
        return request;
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
