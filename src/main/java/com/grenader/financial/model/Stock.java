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
    private double forwardP_E;
    private double priceToBook;
    private double priceToCashFlow;

    private double[] dividendsPerStock;
    private double[] dividendsGrowth;

    private double[] returnPerYears;
    private double[] dividendsPerYears;
    private double[] totalReturnPerYear;

    private double[] operationalIncome;
    private double[] netIncome;
    private double payoutRatioLastYear;
    private double payoutRatioTTM;
    private double[] operCashFlow;
    private double[] interestExpense;
    private double[] returnOnEquity;
    private String sectorName;
    private double[] revenue;

    public Stock(String stockName, String stockTicker) {
        this.stockName = stockName;
        this.stockTicker = stockTicker;
    }

    public Stock(String stockName, String stockTicker, double lastPrice, double openPrice, double yield, String isReinvestment, String marketCap, double forwardP_E, double priceToBook, double priceToCashFlow) {

        this.stockName = stockName;
        this.stockTicker = stockTicker;
        this.lastPrice = lastPrice;
        this.openPrice = openPrice;
        this.yield = yield;
        this.isReinvestment = isReinvestment;
        this.marketCap = marketCap;
        this.forwardP_E = forwardP_E;
        this.priceToBook = priceToBook;
        this.priceToCashFlow = priceToCashFlow;
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

    public double getForwardP_E() {
        return forwardP_E;
    }

    public double getPriceToBook() {
        return priceToBook;
    }

    public double[] getReturnPerYears() {
        return returnPerYears;
    }

    public double[] getDividendsPerYears() {
        return dividendsPerYears;
    }

    public double[] getDividendsPerStock() {
        return dividendsPerStock;
    }

    public void setReturnPerYears(double[] returnPerYears) {
        updateTotalReturn(returnPerYears, dividendsPerYears);
        this.returnPerYears = returnPerYears;
    }

    public void setDividendsPerYears(double[] dividendsPerYears) {
        updateTotalReturn(returnPerYears, dividendsPerYears);
        this.dividendsPerYears = dividendsPerYears;
    }

    private void updateTotalReturn(double[] returnPerYears, double[] dividendsPerYears) {
        if (returnPerYears == null || dividendsPerYears == null)
            return;
        totalReturnPerYear = new double[dividendsPerYears.length];
        for (int ii = 0; ii < dividendsPerYears.length; ii++) {
            // ignore -1 return
            totalReturnPerYear[ii] = returnPerYears[ii] + (dividendsPerYears[ii] != -1 ? dividendsPerYears[ii] : 0);
        }
    }

    public void setDividendsPerStock(double[] dividendsPerStock) {
        this.dividendsPerStock = dividendsPerStock;

        dividendsGrowth = calculateAverageGrowthRate(this.dividendsPerStock, false);
        System.out.println("dividendsGrowth = " + Arrays.toString(dividendsGrowth));
    }

     double[] calculateAverageGrowthRate(double[] sequence, boolean skipTTM) {
        if (sequence == null)
            return null;

        int shortCut = 1;
        if (skipTTM)
            shortCut = 2;

        double[] growthSequence = new double[sequence.length-shortCut];

        // calculate growth per year
        for (int ii = 0; ii < sequence.length-shortCut; ii++) {
            if (sequence[ii + 1] == -1 || sequence[ii] == -1)
                growthSequence[ii] = -1;
            else
                growthSequence[ii] = (sequence[ii + 1] - sequence[ii]) / Math.abs(sequence[ii]);
        }
        return growthSequence;
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

    public double getAverageNetIncomeGrowth() {
        double[] averageGrowthRate = calculateAverageGrowthRate(netIncome, true);
        return getFiveYearsAverage(averageGrowthRate);
    }

    public double getAverageRevenueGrowth() {
        double[] averageGrowthRate = calculateAverageGrowthRate(revenue, true);
        return getFiveYearsAverage(averageGrowthRate);
    }

    public double getAverageOperationalIncomeGrowth() {
        double[] averageGrowthRate = calculateAverageGrowthRate(operationalIncome, true);
        return getFiveYearsAverage(averageGrowthRate);
    }

    public double getAverageReturnOnEquityGrowth() {
        double[] averageGrowthRate = calculateAverageGrowthRate(returnOnEquity, true);
        return getFiveYearsAverage(averageGrowthRate);
    }

    public double getAverageOperationalCashFlowGrowth() {
        double[] averageGrowthRate = calculateAverageGrowthRate(operCashFlow, true);
        return getFiveYearsAverage(averageGrowthRate);
    }

    double getFiveYearsAverage(double[] averageGrowthRate) {
        if (averageGrowthRate == null)
            return -1;
        return Arrays.stream(averageGrowthRate).skip(averageGrowthRate.length - 4).filter(i -> i != -1).average().getAsDouble();
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

    public void setInterestExpense(double[] interestExpense) {
        this.interestExpense = interestExpense;
    }

    public double[] getInterestExpense() {
        return interestExpense;
    }

    public void setReturnOnEquity(double[] returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }

    public double[] getReturnOnEquity() {
        return returnOnEquity;
    }

    public double getAverageROE3Years() {
        if (returnOnEquity == null)
            return -1d;
        return Arrays.stream(returnOnEquity).skip(returnOnEquity.length - 3).average().getAsDouble();
    }

    public double getDebtCoverageRatio() {
        if (netIncome == null || interestExpense == null)
            return -1d;
        if (netIncome[netIncome.length-1] == -1 || interestExpense[interestExpense.length-1]  == -1 )
            return -1d;

        return netIncome[netIncome.length-1] / interestExpense[interestExpense.length-1];
    }

    public double[] getDividendsGrowth() {
        return dividendsGrowth;
    }

    public double[] getTotalReturnPerYear() {
        return totalReturnPerYear;
    }

    public double getAverageDividendsGrowth() {
        if (Arrays.stream(dividendsGrowth).allMatch(v -> v == -1))
            return 0; // do not calculate average when all the values are -1.

        return Arrays.stream(dividendsGrowth).average().getAsDouble();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockName='" + stockName + '\'' +
                ", stockTicker='" + stockTicker + '\'' +
                ", lastPrice=" + lastPrice +
                ", openPrice=" + openPrice +
                ", yield=" + yield +
                ", getAverageDividendsGrowth=" + getAverageDividendsGrowth() +
                ", getDebtCoverageRatio=" + getDebtCoverageRatio() +
                ", getAverageROE3Years=" + getAverageROE3Years() +
                ", getAverageNetIncomeGrowth=" + getAverageNetIncomeGrowth() +
                ", getAverageOperationalIncomeGrowth=" + getAverageOperationalIncomeGrowth() +
                ", getAverageRevenueGrowth=" + getAverageRevenueGrowth() +
                ", getAverageOperationalCashFlowGrowth=" + getAverageOperationalCashFlowGrowth() +
                ", isReinvestment='" + isReinvestment + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", forwardP_E=" + forwardP_E +
                ", priceToBook=" + priceToBook +
                ", priceToCashFlow=" + priceToCashFlow +
                ", dividendsPerStock=" + Arrays.toString(dividendsPerStock) +
                ", dividendsGrowth=" + Arrays.toString(dividendsGrowth) +
                ", returnPerYears=" + Arrays.toString(returnPerYears) +
                ", dividendsPerYears=" + Arrays.toString(dividendsPerYears) +
                ", totalReturnPerYear=" + Arrays.toString(totalReturnPerYear) +
                ", operationalIncome=" + Arrays.toString(operationalIncome) +
                ", netIncome=" + Arrays.toString(netIncome) +
                ", payoutRatioLastYear=" + payoutRatioLastYear +
                ", payoutRatioTTM=" + payoutRatioTTM +
                ", operCashFlow=" + Arrays.toString(operCashFlow) +
                ", interestExpense=" + Arrays.toString(interestExpense) +
                ", returnOnEquity=" + Arrays.toString(returnOnEquity) +
                ", sectorName='" + sectorName + '\'' +
                ", revenue=" + Arrays.toString(revenue) +
                '}';
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setRevenue(double[] revenue) {
        this.revenue = revenue;
    }

    public double[] getRevenue() {
        return revenue;
    }

    public double getPriceToCashFlow() {
        return priceToCashFlow;
    }
}
