package com.spribe.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {

    private final InMemoryService inMemoryService;

    public Set<String> getCurrencies() {
        log.info("Getting currencies");

        return inMemoryService.getSymbols();
    }

    public void addCurrency(String symbol) {
        log.info("Add new currency: {}", symbol);

        inMemoryService.registerSymbol(symbol);
    }

}
