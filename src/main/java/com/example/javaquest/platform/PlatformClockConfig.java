package com.example.javaquest.platform;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jedno zrodlo "teraz" dla platformy. Podejscia do quizu i mastery biora czas z tego beana zamiast
 * z {@code LocalDateTime.now()}, dzieki czemu testy starzenia wiedzy podmieniaja zegar i sa
 * deterministyczne (nie zaleza od realnej daty ani od tego, jak dlugo trwa test).
 */
@Configuration
class PlatformClockConfig {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
