package com.example.webapp.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;

class ExchangeRateServiceImplTest {

    @Test
    void testGetExchangeRate() {
        String amount = "$170496.53";
        Assert.isTrue(amount.replaceAll("\\$", "").equals("170496.53"), "testGetExchangeRate  occur error!!!");

        BigDecimal decimal = new BigDecimal("170496.53");
        decimal = decimal.multiply(new BigDecimal("3.669")).setScale(2, RoundingMode.HALF_UP);
        // 170496.53 * 3.669 = 625551.76857
        Assert.isTrue(decimal.equals(BigDecimal.valueOf(625551.77)), "testGetExchangeRate  occur error!!!");

    }

    @Test
    void testGetCommaFormat() {
        BigDecimal number = new BigDecimal("12345678.9");
        Assert.isTrue(getCommaFormat(number).equals("$12,345,678.9"), "testGetCommaFormat occur error!!!");
        number = new BigDecimal("123.45");
        Assert.isTrue(getCommaFormat(number).equals("$123.45"), "testGetCommaFormat occur error!!!");
        number = new BigDecimal("123");
        Assert.isTrue(getCommaFormat(number).equals("$123"), "testGetCommaFormat occur error!!!");
    }

    private String getCommaFormat(BigDecimal number) {
        String s = number.toString();
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
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

    @Test
    void testValidateParams() {
        String amount = "12345";
        Assert.isTrue(amount.matches("^[$\\d.]*$"), "testValidateParams occur error!!!");
        amount = "$12345";
        Assert.isTrue(amount.matches("^[$\\d.]*$"), "testValidateParams occur error!!!");
        amount = "$123.45";
        Assert.isTrue(amount.matches("^[$\\d.]*$"), "testValidateParams occur error!!!");
        amount = "abc123";
        Assert.isTrue(!amount.matches("^[$\\d.]*$"), "testValidateParams occur error!!!");
    }
}