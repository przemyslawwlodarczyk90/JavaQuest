package com.example.javaquest.platform.mastery;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MasteryProperties.class)
class MasteryConfig {

    @Bean
    MasteryPolicy masteryPolicy(MasteryProperties properties) {
        return new MasteryPolicy(properties);
    }
}
