package com.spribe.demo.service;

import com.spribe.demo.entity.Currency;
import com.spribe.demo.repository.CurrencyRepository;
import com.spribe.demo.service.provider.CurrencyProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {

    private final InMemoryService inMemoryService;
    private final CurrencyProvider currencyProvider;
    private final CurrencyRepository currencyRepository;

    public Set<String> getSymbols() {
        log.info("Getting symbols");

        return inMemoryService.getSymbols();
    }

    public List<Currency> getCurrencies() {
        log.info("Getting currencies");

        return currencyRepository.findAll();
    }

    public void addCurrency(String symbol) {
        log.info("Add new currency: {}", symbol);

        Set<String> availableSymbols = currencyProvider.availableSymbols();
        if (!availableSymbols.contains(symbol)) {
            throw new IllegalArgumentException("Symbol " + symbol + " is not available");
        }
        inMemoryService.registerSymbol(symbol);
    }
}
