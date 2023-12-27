package com.example.webapp.service.impl;

import com.example.webapp.service.ExchangeRateService;
import com.example.webapp.vo.ExchangeRateResultVo;
import com.example.webapp.vo.ExchangeRateVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private Map<String, ExchangeRateVo> configMap;

    public ExchangeRateServiceImpl() {
        initConfig();
    }
    public void initConfig() {
        configMap = new HashMap<>();
        ExchangeRateVo twdVo = new ExchangeRateVo("TWD", new HashMap<>());
        twdVo.getRates().put("TWD", "1");
        twdVo.getRates().put("JPY", "3.669");
        twdVo.getRates().put("USD", "0.03281");
        configMap.put("TWD", twdVo);

        ExchangeRateVo jpyVo = new ExchangeRateVo("TWD", new HashMap<>());
        jpyVo.getRates().put("TWD", "0.26956");
        jpyVo.getRates().put("JPY", "1");
        jpyVo.getRates().put("USD", "0.00885");
        configMap.put("JPY", jpyVo);

        ExchangeRateVo usdVo = new ExchangeRateVo("TWD", new HashMap<>());
        usdVo.getRates().put("TWD", "30.444");
        usdVo.getRates().put("JPY", "111.801");
        usdVo.getRates().put("USD", "1");
        configMap.put("USD", usdVo);
    }

    @Override
    public ExchangeRateResultVo getExchangeRate(String source, String target, String amount) {
        validateParams(source, target, amount);

        ExchangeRateVo exchangeRateVo = configMap.get(source);
        if (Objects.isNull(exchangeRateVo) || Objects.isNull(exchangeRateVo.getRates()) || !exchangeRateVo.getRates().containsKey(target)) {
            throw new RuntimeException("Source or target not exist!!!");
        }
        ExchangeRateResultVo resultVo = new ExchangeRateResultVo();
        String targetExchangeRate = exchangeRateVo.getRates().get(target);
        String formatAmount = amount.replaceAll("\\$", "");

        BigDecimal decimal = new BigDecimal(formatAmount);
        decimal = decimal.multiply(new BigDecimal(targetExchangeRate)).setScale(2, RoundingMode.HALF_UP);

        resultVo.setMsg("success");
        resultVo.setAmount(getCommaFormat(decimal));
        return resultVo;
    }


    private void validateParams(String source, String target, String amount) {
        if (!StringUtils.hasText(source)) {
            throw new InvalidParameterException("Source can not be null!!!");
        }
        if (!StringUtils.hasText(target)) {
            throw new InvalidParameterException("Target can not be null!!!");
        }
        if (!StringUtils.hasText(amount)) {
            throw new InvalidParameterException("Amount can not be null!!!");
        }
        if (!amount.matches("^[$\\d.]*$")) {
            throw new InvalidParameterException("This amount is invalid!!!");
        }
    }

    private String getCommaFormat(BigDecimal number) {
        String s = number.toString();
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
        // 小數點處理
        while (i >= 0) {
            sb.append(s.charAt(i));
            if (s.charAt(i--) == '.') {
                break;
            }
        }
        int cnt = 0;
        while (i >= 0) {
            sb.append(s.charAt(i--));
            if (++cnt % 3 == 0 && i > 0) {
                sb.append(",");
            }
        }
        return "$" + sb.reverse();
    }

}
