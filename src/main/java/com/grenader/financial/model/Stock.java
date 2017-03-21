package com.grenader.financial.model;

import java.util.Arrays;

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
    private double[] operationalIncome;
    private double[] netIncome;
    private double payoutRatioLastYear;
    private double payoutRatioTTM;
    private double[] operCashFlow;
    private double[] interestExpense;

    public Stock(String stockName, String stockTicker) {
        this.stockName = stockName;
        this.stockTicker = stockTicker;
    }

    public Stock(String stockName, String stockTicker, double lastPrice, double openPrice, double yield, String isReinvestment, String marketCap, double forward_p_e, double priceToBook) {

        this.stockName = stockName;
        this.stockTicker = stockTicker;
        this.lastPrice = lastPrice;
        this.openPrice = openPrice;
        this.yield = yield;
        this.isReinvestment = isReinvestment;
        this.marketCap = marketCap;
        this.forward_p_e = forward_p_e;
        this.priceToBook = priceToBook;
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

    public void setPricePerYears(double[] pricePerYears) {
        this.pricePerYears = pricePerYears;
    }

    public void setDividendsPerYears(double[] dividendsPerYears) {
        this.dividendsPerYears = dividendsPerYears;
    }

    public void setDividendsPerStock(double[] dividendsPerStock) {
        this.dividendsPerStock = dividendsPerStock;
    }


    public void setOperationalIncome(double[] operationalIncome) {
        this.operationalIncome = operationalIncome;
    }

    public double[] getOperationalIncome() {
        return operationalIncome;
    }

    public void setNetIncome(double[] netIncome) {
        this.netIncome = netIncome;
    }

    public double[] getNetIncome() {
        return netIncome;
    }

    public void setPayoutRatioLastYear(double payoutRatioLastYear) {
        this.payoutRatioLastYear = payoutRatioLastYear;
    }

    public double getPayoutRatioLastYear() {
        return payoutRatioLastYear;
    }

    public void setPayoutRatioTTM(double payoutRatioTTM) {
        this.payoutRatioTTM = payoutRatioTTM;
    }

    public double getPayoutRatioTTM() {
        return payoutRatioTTM;
    }

    public void setOperCashFlow(double[] operCashFlow) {
        this.operCashFlow = operCashFlow;
    }

    public double[] getOperCashFlow() {
        return operCashFlow;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockName='" + stockName + '\'' +
                ", stockTicker='" + stockTicker + '\'' +
                ", lastPrice=" + lastPrice +
                ", openPrice=" + openPrice +
                ", yield=" + yield +
                ", isReinvestment='" + isReinvestment + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", forward_p_e=" + forward_p_e +
                ", priceToBook=" + priceToBook +
                ", pricePerYears=" + Arrays.toString(pricePerYears) +
                ", dividendsPerYears=" + Arrays.toString(dividendsPerYears) +
                ", dividendsPerStock=" + Arrays.toString(dividendsPerStock) +
                ", operationalIncome=" + Arrays.toString(operationalIncome) +
                ", netIncome=" + Arrays.toString(netIncome) +
                ", payoutRatioLastYear=" + payoutRatioLastYear +
                ", payoutRatioTTM=" + payoutRatioTTM +
                ", operCashFlow=" + Arrays.toString(operCashFlow) +
                ", interestExpense=" + Arrays.toString(interestExpense) +
                '}';
    }

    public void setInterestExpense(double[] interestExpense) {
        this.interestExpense = interestExpense;
    }

    public double[] getInterestExpense() {
        return interestExpense;
    }
}
