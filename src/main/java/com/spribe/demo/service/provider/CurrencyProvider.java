package com.spribe.demo.service.provider;

import com.spribe.demo.dto.RatesTakeFromProvider;

import java.util.Set;

public interface CurrencyProvider {

    Set<String> availableSymbols();

    RatesTakeFromProvider rates(String symbol);
}