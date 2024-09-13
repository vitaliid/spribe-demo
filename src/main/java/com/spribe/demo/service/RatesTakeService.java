package com.spribe.demo.service;

import com.spribe.demo.entity.RatesTake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatesTakeService {

    private final InMemoryService inMemoryService;

    public RatesTake getRates(String currencySymbol) {
        log.info("Getting currency {} latest rates", currencySymbol);

        return inMemoryService.getRates(currencySymbol);
    }
}
