package com.gbrag.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Value("${app.system-prompt}")
    private String systemPrompt;

    @Bean
    public String systemPrompt() {
        return systemPrompt;
    }
}
