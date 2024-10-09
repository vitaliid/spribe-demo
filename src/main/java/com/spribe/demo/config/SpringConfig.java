package com.spribe.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@EnableScheduling
@Configuration
public class SpringConfig {

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}
