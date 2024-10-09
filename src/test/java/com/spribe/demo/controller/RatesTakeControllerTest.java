package com.spribe.demo.controller;

import com.spribe.demo.PostgresTestContainersInitializer;
import com.spribe.demo.SpribeDemoApplication;
import com.spribe.demo.dto.RatesTakeResponse;
import com.spribe.demo.entity.RatesTake;
import com.spribe.demo.helper.client.MockMvcClient;
import com.spribe.demo.service.InMemoryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest(classes = SpribeDemoApplication.class)
@ContextConfiguration(initializers = PostgresTestContainersInitializer.class)
class RatesTakeControllerTest {
    private static final String RATES_ENDPOINT_URL = "/api/v1/rates";

    @Autowired
    private MockMvcClient client;

    @Autowired
    private InMemoryService inMemoryService;

    @Test
    void getRates() throws Exception {
        // given
        RatesTake ratesTake = new RatesTake();
        ratesTake.setTimestamp(123);
        ratesTake.setRates(Map.of("EUR", "1.0"));
        inMemoryService.updateRates("USD", ratesTake);

        // when
        MvcResult result = client.perform(get(RATES_ENDPOINT_URL + "/USD"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        client.deserialise(result, RatesTakeResponse.class);
    }
}
