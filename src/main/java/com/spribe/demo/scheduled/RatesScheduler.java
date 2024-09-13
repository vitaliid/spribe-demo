package com.spribe.demo.scheduled;

import com.spribe.demo.entity.Currency;
import com.spribe.demo.entity.RatesTake;
import com.spribe.demo.service.CurrencyService;
import com.spribe.demo.service.InMemoryService;
import com.spribe.demo.service.RatesTakeService;
import com.spribe.demo.service.provider.CurrencyProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RatesScheduler {

    public static final int DEFAULT_RATE_FIXED_RATE = 60000; // 1 minute

    private final InMemoryService inMemoryService;
    private final CurrencyProvider currencyProvider;
    private final CurrencyService currencyService;
    private final RatesTakeService ratesTakeService;


    @Scheduled(fixedRate = DEFAULT_RATE_FIXED_RATE)
    public void getRatesFromServer() {
        Set<String> symbols = inMemoryService.getSymbols();

        final Map<String, Currency> currencyMap = currencyService.getCurrencies().stream().collect(Collectors.toMap(Currency::getSymbol, currency -> currency));

        symbols.forEach(symbol -> {
            RatesTake rates = currencyProvider.rates(symbol);

            rates.setBase(currencyMap.get(symbol));
            ratesTakeService.saveRates(rates);

            inMemoryService.updateRates(symbol, rates);
        });
    }
}
