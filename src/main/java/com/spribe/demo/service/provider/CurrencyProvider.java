package com.spribe.demo.service.provider;

import com.spribe.demo.entity.RatesTake;

import java.util.Set;

public interface CurrencyProvider {

    Set<String> availableSymbols();

    RatesTake rates(String symbol);
}