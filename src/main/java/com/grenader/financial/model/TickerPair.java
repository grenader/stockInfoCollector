package com.grenader.financial.model;

/**
 * Created by ikanshyn on 2017-03-22.
 */
public class TickerPair {
    private String ticker;
    private String countryCode;

    private TickerPair() {
    }

    public TickerPair(String ticker, String countryCode) {
        this.ticker = ticker;
        this.countryCode = countryCode;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return "TickerPair{" +
                "ticker='" + ticker + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
