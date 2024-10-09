package com.spribe.demo;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class PostgresTestContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        var postgresSqlContainer = new PostgreSQLContainer<>("postgres:16.4");

        postgresSqlContainer.start();

        // should shut down container on context close
        applicationContext.getBeanFactory().registerSingleton("postgresSqlContainer", postgresSqlContainer);

        TestPropertyValues.of(
                Map.of(
                        "spring.datasource.url", postgresSqlContainer.getJdbcUrl(),
                        "spring.datasource.username", postgresSqlContainer.getUsername(),
                        "spring.datasource.password", postgresSqlContainer.getPassword()
                )
        ).applyTo(applicationContext);
    }
}
