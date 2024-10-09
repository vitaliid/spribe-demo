package com.spribe.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ContextConfiguration(initializers = PostgresTestContainersInitializer.class)
class SpribeDemoApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> {
        });
    }

}
