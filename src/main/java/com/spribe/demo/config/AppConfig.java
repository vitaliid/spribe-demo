package com.spribe.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {

    @Value("${app.exchange-server.url}")
    private String exchangeServerApiUrl;

    @Value("${app.exchange-server.app-id}")
    private String appId;
}
