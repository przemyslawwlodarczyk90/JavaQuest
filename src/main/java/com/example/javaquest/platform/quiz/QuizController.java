package com.example.javaquest.platform.quiz;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Quiz lekcji: {@code GET} = stan (pula, prog, wynik ostatniego podejscia, otwarte podejscie),
 * {@code POST /attempts} = start (albo wznowienie) podejscia z losowaniem, {@code POST .../answers} =
 * odpowiedz na jedno pytanie (zwraca natychmiast poprawna odpowiedz i wyjasnienie; po ostatnim - wynik).
 */
@RestController
@RequestMapping("/api/chapters/{chapterSlug}/lessons/{lessonSlug}/quiz")
class QuizController {

    record AnswerRequest(int position, String option) {
    }

    private final QuizService quizService;

    QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    QuizService.QuizStatus getStatus(@PathVariable String chapterSlug, @PathVariable String lessonSlug,
                                     Authentication authentication) {
        return quizService.getStatus(authentication.getName(), chapterSlug, lessonSlug);
    }

    @PostMapping("/attempts")
    QuizService.AttemptView startAttempt(@PathVariable String chapterSlug, @PathVariable String lessonSlug,
                                         Authentication authentication) {
        return quizService.startAttempt(authentication.getName(), chapterSlug, lessonSlug);
    }

    @PostMapping("/attempts/{attemptId}/answers")
    QuizService.AnswerResponse answer(@PathVariable String chapterSlug, @PathVariable String lessonSlug,
                                      @PathVariable Long attemptId, @RequestBody AnswerRequest request,
                                      Authentication authentication) {
        return quizService.answer(authentication.getName(), chapterSlug, lessonSlug, attemptId,
                request.position(), request.option());
    }
}
