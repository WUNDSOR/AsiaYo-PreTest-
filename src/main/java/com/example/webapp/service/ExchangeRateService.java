package com.example.webapp.service;

import com.example.webapp.vo.ExchangeRateResultVo;

public interface ExchangeRateService {
    ExchangeRateResultVo getExchangeRate(String source, String target, String amount);
}
