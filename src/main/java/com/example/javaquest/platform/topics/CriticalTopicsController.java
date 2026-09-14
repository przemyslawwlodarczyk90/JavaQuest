package com.example.javaquest.platform.topics;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Zakladka "Krytyczne" na platformie: lista tematow krytycznych/waznych dla rekrutacji
 * na Java Developera, z opcjonalnym odnosnikiem do konkretnego rozdzialu/lekcji kursu,
 * jesli temat jest juz w nim opracowany (patrz {@link CriticalTopic}). Dane statyczne,
 * bez bazy danych - wczytywane raz przy starcie z
 * {@code src/main/resources/platform/critical-topics.json}, tym samym wzorcem
 * ClassPathResource+ObjectMapper co {@code LessonContentLoader}.
 */
@RestController
class CriticalTopicsController {

    private final List<CriticalTopic> topics;

    CriticalTopicsController(ObjectMapper objectMapper) throws IOException {
        ClassPathResource resource = new ClassPathResource("platform/critical-topics.json");
        this.topics = objectMapper.readValue(resource.getInputStream(), CriticalTopicsFile.class).topics();
    }

    @GetMapping("/api/critical-topics")
    List<CriticalTopic> getCriticalTopics() {
        return topics;
    }
}
