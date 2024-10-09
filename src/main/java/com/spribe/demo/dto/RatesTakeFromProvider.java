package com.spribe.demo.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RatesTakeFromProvider {
    private String base;
    private long timestamp;
    private Map<String, String> rates = new HashMap<>();
}
