package com.grenader.financial;

/**
 * Created by Igor on 2017-03-19.
 */
public class Stock {
    private String stockName;
    private String stockTicker;
    private double lastPrice;
    private double openPrice;
    private double yield;
    private String isReinvestment;
    private String marketCap;
    private double forward_p_e;
    private double priceToBook;
    private double[] pricePerYears;
    private double[] dividendsPerYears;
    private double[] dividendsPerStock;

    public Stock(String stockName, String stockTicker, double lastPrice, double openPrice, double yield, String isReinvestment, String marketCap, double forward_p_e, double priceToBook, double[] pricePerYears, double[] dividendsPerYears, double[] dividendsPerStock) {

        this.stockName = stockName;
        this.stockTicker = stockTicker;
        this.lastPrice = lastPrice;
        this.openPrice = openPrice;
        this.yield = yield;
        this.isReinvestment = isReinvestment;
        this.marketCap = marketCap;
        this.forward_p_e = forward_p_e;
        this.priceToBook = priceToBook;
        this.pricePerYears = pricePerYears;
        this.dividendsPerYears = dividendsPerYears;
        this.dividendsPerStock = dividendsPerStock;
    }


    public String getStockName() {
        return stockName;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getYield() {
        return yield;
    }

    public String getIsReinvestment() {
        return isReinvestment;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public double getForward_p_e() {
        return forward_p_e;
    }

    public double getPriceToBook() {
        return priceToBook;
    }

    public double[] getPricePerYears() {
        return pricePerYears;
    }

    public double[] getDividendsPerYears() {
        return dividendsPerYears;
    }

    public double[] getDividendsPerStock() {
        return dividendsPerStock;
    }
}
