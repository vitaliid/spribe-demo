package com.spribe.demo.controller;

import com.spribe.demo.dto.RatesTakeResponse;
import com.spribe.demo.service.RatesTakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rates")
@RequiredArgsConstructor
public class RatesTakeController {

    private final RatesTakeService ratesTakeService;

    @GetMapping("/{currency}")
    public RatesTakeResponse getRates(@PathVariable(name = "currency") String currencySymbol) {
        return ratesTakeService.getRates(currencySymbol);
    }
}
