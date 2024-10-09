package com.spribe.demo.scheduled;

import com.spribe.demo.dto.RatesTakeFromProvider;
import com.spribe.demo.entity.Currency;
import com.spribe.demo.entity.RatesTake;
import com.spribe.demo.service.CurrencyService;
import com.spribe.demo.service.InMemoryService;
import com.spribe.demo.service.RatesTakeService;
import com.spribe.demo.service.provider.CurrencyProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RatesScheduler {

    public static final int PERIOD_1_HOUR = 360000;

    private final InMemoryService inMemoryService;
    private final CurrencyProvider currencyProvider;
    private final CurrencyService currencyService;
    private final RatesTakeService ratesTakeService;


    @Scheduled(fixedRate = PERIOD_1_HOUR)
    public void getRatesFromServer() {
        log.info("[JOB] Getting rates from provider started");

        Set<String> symbols = inMemoryService.getSymbols();
        log.info("[JOB] Getting rates from provider for symbols: {}", symbols);

        final Map<String, Currency> currencyMap = currencyService.getCurrencies().stream()
                .collect(Collectors.toMap(Currency::getSymbol, currency -> currency));

        symbols.forEach(symbol -> {
            RatesTakeFromProvider ratesFromProvider = currencyProvider.rates(symbol);

            RatesTake rates = Optional.ofNullable(inMemoryService.getRates(symbol)).orElse(new RatesTake());
            rates.setBase(currencyMap.get(ratesFromProvider.getBase()));
            rates.setTimestamp(ratesFromProvider.getTimestamp());
            rates.setRates(ratesFromProvider.getRates());
            rates = ratesTakeService.saveRates(rates);
            log.info("[JOB] Updated rates in DB for {}", symbol);

            inMemoryService.updateRates(symbol, rates);
            log.info("[JOB] Updated rates in memory for {}", symbol);
        });

        log.info("[JOB] Getting rates from provider finished");
    }
}
