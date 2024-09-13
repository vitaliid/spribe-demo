package com.spribe.demo.service;

import com.spribe.demo.entity.RatesTake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InMemoryService {

    private static final Map<String, RatesTake> CURRENCY_SYMBOL_WITH_RATES = new HashMap<>();

    public Set<String> getSymbols() {
        return CURRENCY_SYMBOL_WITH_RATES.keySet();
    }

    public RatesTake getRates(String currencySymbol) {
        return CURRENCY_SYMBOL_WITH_RATES.get(currencySymbol);
    }

    public void registerSymbol(String currencySymbol) {
        CURRENCY_SYMBOL_WITH_RATES.putIfAbsent(currencySymbol, null);
    }

    public void updateRates(String currencySymbol, RatesTake ratesTake) {
        CURRENCY_SYMBOL_WITH_RATES.putIfAbsent(currencySymbol, ratesTake);
    }
}
