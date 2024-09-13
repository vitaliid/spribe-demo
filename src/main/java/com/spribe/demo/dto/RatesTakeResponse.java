package com.spribe.demo.dto;

import lombok.Value;

import java.util.Map;

@Value
public class RatesTakeResponse {

    long timestamp;

    Map<String, String> rates;
}
