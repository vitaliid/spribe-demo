package com.spribe.demo.service;

import com.spribe.demo.entity.RatesTake;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryServiceTest {

    private final InMemoryService inMemoryService = new InMemoryService();

    @Test
    @DisplayName("Register symbol adds it to in-memory")
    void registerSymbol_addsItToInMemory() {
        // given
        inMemoryService.registerSymbol("USD");

        // when
        Set<String> symbols = inMemoryService.getSymbols();

        // then
        assertEquals(Set.of("USD"), symbols);
    }

    @Test
    @DisplayName("Update rates adds it to in-memory")
    void updateRates_addsItToInMemory() {
        // given
        RatesTake ratesTake = new RatesTake();
        ratesTake.setRates(Map.of("EUR", "1.0"));
        inMemoryService.updateRates("USD", ratesTake);

        // when
        RatesTake rates = inMemoryService.getRates("USD");

        // then
        assertEquals(Map.of("EUR", "1.0"), rates.getRates());
    }
}
