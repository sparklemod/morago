package com.example.morago.util;

import java.math.BigDecimal;

public class CoinConverter {
    //TODO спросить про конвертацию
    public static BigDecimal convertWonToCoins(BigDecimal won) {
        return won.multiply(BigDecimal.valueOf(0.95));
    }
}
