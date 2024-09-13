package com.spribe.demo.service;

import com.spribe.demo.dto.RatesTakeResponse;
import com.spribe.demo.entity.RatesTake;
import com.spribe.demo.mapper.RatesTakeMapper;
import com.spribe.demo.repository.RatesTakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatesTakeService {

    private final InMemoryService inMemoryService;
    private final RatesTakeMapper ratesTakeMapper;
    private final RatesTakeRepository ratesTakeRepository;

    public RatesTakeResponse getRates(String currencySymbol) {
        log.info("Getting currency {} latest rates", currencySymbol);

        RatesTake rates = inMemoryService.getRates(currencySymbol);
        return ratesTakeMapper.fromEntity(rates);
    }

    public void saveRates(RatesTake ratesTake) {
        ratesTakeRepository.save(ratesTake);
    }
}
