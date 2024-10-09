package com.spribe.demo.controller;

import com.spribe.demo.dto.AddCurrencyRequest;
import com.spribe.demo.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public Set<String> getCurrencies() {
        return currencyService.getSymbols();
    }

    @PostMapping
    public void addCurrency(@RequestBody AddCurrencyRequest request) {
        currencyService.addCurrency(request.getSymbol());
    }
}
