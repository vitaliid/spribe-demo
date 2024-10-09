package com.spribe.demo.service;

import com.spribe.demo.entity.RatesTake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class InMemoryService {

    private static final Map<String, RatesTake> CURRENCY_SYMBOL_WITH_RATES = new ConcurrentHashMap<>();

    public Set<String> getSymbols() {
        return CURRENCY_SYMBOL_WITH_RATES.keySet();
    }

    public RatesTake getRates(String currencySymbol) {
        return CURRENCY_SYMBOL_WITH_RATES.get(currencySymbol);
    }

    public void registerSymbol(String currencySymbol) {
        CURRENCY_SYMBOL_WITH_RATES.putIfAbsent(currencySymbol, new RatesTake());
    }

    public void updateRates(String currencySymbol, RatesTake ratesTake) {
        CURRENCY_SYMBOL_WITH_RATES.put(currencySymbol, ratesTake);
    }
}
