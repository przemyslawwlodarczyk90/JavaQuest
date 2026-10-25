package com.example.javaquest.platform.mastery;

import com.example.javaquest.platform.Utill.MotivationalQuotes;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MotivationalQuotesTest {
    @Test void collectionHasFiftyDistinctSourcedExcerpts() {
        assertThat(MotivationalQuotes.ALL).hasSize(50);
        assertThat(MotivationalQuotes.ALL).extracting(MotivationalQuotes.Quote::id).doesNotHaveDuplicates();
        assertThat(MotivationalQuotes.ALL).extracting(MotivationalQuotes.Quote::text).doesNotHaveDuplicates();
        for (var quote : MotivationalQuotes.ALL) {
            assertThat(quote.text()).isNotBlank();
            assertThat(quote.author()).isNotBlank();
            assertThat(quote.sourceText()).isEqualTo(quote.text());
            assertThat(quote.sourceUrl()).matches("https://(wolnelektury\\.pl/katalog/lektura/|pl\\.wikisource\\.org/wiki/).+");
            assertThat(quote.work()).isNotBlank();
            assertThat(quote.location()).isNotBlank();
        }
    }

    @Test void everyOpeningCanExcludeTheLastQuoteIncludingAcrossReloads() {
        for (var previous : MotivationalQuotes.ALL) {
            for (int attempt = 0; attempt < 10; attempt++) {
                var quote = MotivationalQuotes.randomExcluding(previous.id());
                assertThat(quote.id()).isNotEqualTo(previous.id());
                assertThat(MotivationalQuotes.ALL).contains(quote);
            }
        }
        assertThat(MotivationalQuotes.ALL).contains(MotivationalQuotes.randomExcluding(null));
        assertThat(MotivationalQuotes.ALL).contains(MotivationalQuotes.randomExcluding(-1));
    }
}
