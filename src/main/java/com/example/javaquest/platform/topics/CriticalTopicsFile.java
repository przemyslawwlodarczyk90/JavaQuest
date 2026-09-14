package com.example.javaquest.platform.topics;

import java.util.List;

/**
 * Ksztalt pliku {@code src/main/resources/platform/critical-topics.json}. Parsowany przez
 * Jacksona (rekordy Javy sa wspierane natywnie od Jacksona 2.12+) - patrz {@link CriticalTopic}
 * dla ksztaltu pojedynczego tematu.
 */
public record CriticalTopicsFile(List<CriticalTopic> topics) {
}
