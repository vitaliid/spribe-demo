package com.spribe.demo.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.spribe.demo.PostgresTestContainersInitializer;
import com.spribe.demo.SpribeDemoApplication;
import com.spribe.demo.dto.AddCurrencyRequest;
import com.spribe.demo.helper.client.MockMvcClient;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest(classes = SpribeDemoApplication.class)
@ContextConfiguration(initializers = PostgresTestContainersInitializer.class)
class CurrencyControllerTest {
    private static final String CURRENCIES_ENDPOINT_URL = "/api/v1/currencies";

    @Autowired
    private MockMvcClient client;

    @RegisterExtension
    public static final WireMockExtension MOCK_EXCHANGE_SERVICE = WireMockExtension.newInstance().options(wireMockConfig().port(1234)).build();

    @Test
    void getCurrencies() throws Exception {
        // given

        // when
        MvcResult result = client.perform(get(CURRENCIES_ENDPOINT_URL))
                .andExpect(status().isOk())
                .andReturn();

        // then
        client.deserialise(result, String[].class);
    }

    @Test
    void addCurrency() throws Exception {
        // given
        AddCurrencyRequest addCurrencyRequest = new AddCurrencyRequest();
        addCurrencyRequest.setSymbol("USD");
        String requestBody = client.serialise(addCurrencyRequest);

        MOCK_EXCHANGE_SERVICE.stubFor(
                WireMock.get(urlEqualTo(
                                "/currencies.json?app_id=test"
                        ))
                        .willReturn(aResponse().withBodyFile("currencies/currencies-response.json")
                                .withHeader(CONTENT_TYPE, new MediaType(APPLICATION_JSON, UTF_8).toString()))
        );

        // when
        ResultActions result = client.perform(post(CURRENCIES_ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        result.andExpect(status().isOk());
    }
}
