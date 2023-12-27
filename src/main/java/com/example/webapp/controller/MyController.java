package com.example.webapp.controller;

import com.example.webapp.service.ExchangeRateService;
import com.example.webapp.vo.ExchangeRateResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/exchangeRate/")
@Slf4j
public class MyController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping("/")
    public ExchangeRateResultVo getExchangeRate(String source, String target, String amount) {
        try {
            return exchangeRateService.getExchangeRate(source, target, amount);
        } catch (Exception e) {
            log.warn("[Error] getExchangeRate occur a error.", e);
            return new ExchangeRateResultVo(e.getMessage(), null);
        }
    }
}
