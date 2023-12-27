package com.example.webapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
@Data
@AllArgsConstructor
public class ExchangeRateVo {
    private String name;
    private Map<String, String> rates;
}
