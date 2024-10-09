package com.spribe.demo.service.provider;

import com.spribe.demo.config.AppConfig;
import com.spribe.demo.constants.ExchangeServerConstants;
import com.spribe.demo.dto.RatesTakeFromProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OpenexchangeratesorgProvider implements CurrencyProvider {

    private final AppConfig appConfig;
    private final RestClient restClient;

    @Override
    public Set<String> availableSymbols() {
        Map<String, String> symbols = restClient.get()
                .uri(uriBuilder ->
                        UriComponentsBuilder.fromUriString(appConfig.getExchangeServerApiUrl()
                                        .concat("/currencies.json"))
                                .queryParam(ExchangeServerConstants.PARAM_APP_ID, appConfig.getAppId())
                                .build()
                                .toUri()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return Optional.ofNullable(symbols)
                .map(Map::keySet)
                .orElse(Collections.emptySet());
    }

    @Override
    public RatesTakeFromProvider rates(String symbol) {
        return restClient.get()
                .uri(uriBuilder ->
                        UriComponentsBuilder.fromUriString(appConfig.getExchangeServerApiUrl()
                                        .concat("/latest.json"))
                                .queryParam(ExchangeServerConstants.PARAM_APP_ID, appConfig.getAppId())
                                .queryParam(ExchangeServerConstants.PARAM_BASE, symbol)
                                .build()
                                .toUri()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(RatesTakeFromProvider.class);
    }
}
