package com.grenader.financial.service;

import com.grenader.financial.model.Stock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by ikanshyn on 2017-03-20.
 */
public class StockDataReader {

    public Stock readAStock(WebDriver driver, String ticker, String region) throws InterruptedException
    {
        String baseUrl = "http://quote.morningstar.ca/quicktakes/Stock/s_ca.aspx?t=" +
                ticker + "&region=" + region + "&culture=en-CA";

        String actualTitle = "";

        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);
        Thread.sleep(2000);

        // get the actual value of the title
        actualTitle = driver.getTitle();
        System.out.println("actualTitle = " + actualTitle);

        String stockName = driver.findElement(By.cssSelector(".reports_nav .r_title h1")).getText();
        System.out.println("stockName = " + stockName);

        String stockTicker = driver.findElement(By.cssSelector(".reports_nav .r_title .gry")).getText();
        System.out.println("stockTicker = " + stockTicker);

        double lastPrice = getDouble(driver.findElement(By.id("last-price-value")).getText());
        System.out.println("lastPrice = " + lastPrice);
        double openPrice = getDouble(driver.findElement(By.id("open-price")).getText());
        System.out.println("openPrice = " + openPrice);

        String yieldStr = driver.findElement(By.cssSelector("td.gr_table_colm2b > span.gr_text1")).getText();
        double yield = getDouble(yieldStr.replaceAll("%", "").trim());
        System.out.println("yield = " + yield);

        String isReinvestment = driver.findElement(By.cssSelector("div.gr_colm_d1b")).getText();
        System.out.println("isReinvestment = " + isReinvestment);

        String marketCap = driver.findElement(By.id("MarketCap")).getText();
        System.out.println("marketCap = " + marketCap);

        double forward_P_E = getDouble(driver.findElement(By.cssSelector("span[vkey=PE]")).getText());
        System.out.println("forward_P_E = " + forward_P_E);
        double priceToBook = getDouble(driver.findElement(By.cssSelector("h3[gkey=PB] ~ span")).getText());
        System.out.println("priceToBook = " + priceToBook);

        Stock stock = new Stock(stockName, stockTicker, lastPrice, openPrice, yield, isReinvestment, marketCap, forward_P_E, priceToBook);

        // Loading Key Stats information
        loadFinancialsInformation(driver, stock);

        // Loading Key Stats information
        loadKeyStatsInformation(driver, stock);

        // Loading performance information
        loadPerformanceInformation(driver, stock);

        // Write results:
        ArrayList<Stock> listBook = new ArrayList<Stock>();
        listBook.add(stock);

        return stock;
    }

    void loadPerformanceInformation(WebDriver driver, Stock stock) throws InterruptedException
    {
        driver.findElement(By.linkText("Performance")).click();
        Thread.sleep(5000);

        double[] pricePerYears = new double[11];
        for (int i = 0; i < 11; i++) {
            String priceValue = driver.findElement(By.xpath("//div[@id='chart_container']/table/tbody/tr[1]/td[" + (i + 1) + "]")).getText();
            System.out.println("priceValue = " + priceValue);
            pricePerYears[i] = getDouble(priceValue);
        }
        System.out.println("pricePerYears = " + Arrays.toString(pricePerYears));
        stock.setReturnPerYears(pricePerYears);

        double[] dividendsPerYears = new double[10];
        for (int i = 0; i < 10; i++) {
            String dividendValue = driver.findElement(By.xpath("//div[@id='chart_container']/table/tbody/tr[4]/td[" + (i + 1) + "]")).getText();
            System.out.println("dividendValue = " + dividendValue);
            dividendsPerYears[i] = getDouble(dividendValue);
        }
        System.out.println("dividendsPerYears = " + Arrays.toString(dividendsPerYears));
        stock.setDividendsPerYears(dividendsPerYears);

        String sector = driver.findElement(By.xpath("//div[@id='chart_container']/table/tbody/tr[2]/th")).getText();
        System.out.println("sector = " + sector);
        stock.setSectorName(sector);


        // Click on Dividend tab
        driver.findElement(By.linkText("Dividend & Splits")).click();
        Thread.sleep(2000);


        double[] dividendsPerStock = new double[5];
        for (int i = 0; i < 5; i++) {
            String dividendPerStock = driver.findElement(By.xpath("//div[@id='div_annual_dividends']/table/tbody/tr[2]/td[" + (i + 1) + "]")).getText();
            System.out.println("dividendPerStock = " + dividendPerStock);
            dividendsPerStock[i] = getDouble(dividendPerStock);
        }
        System.out.println("dividendsPerStock = " + Arrays.toString(dividendsPerStock));
        stock.setDividendsPerStock(dividendsPerStock);

    }

    void loadFinancialsInformation(WebDriver driver, Stock stock) throws InterruptedException
    {

        driver.findElement(By.linkText("Financials")).click();
        Thread.sleep(4000);


        WebElement groupNameToTest = null;
        try {
            groupNameToTest = driver.findElement(By.xpath("//*[contains(text(), 'Interest expense')]"));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        if (groupNameToTest != null) // link is there
        {
            WebElement section = groupNameToTest.findElement(By.xpath(".."));
            String section_label_id = section.getAttribute("id");
            System.out.println("section_label_id = " + section_label_id);

            String sectionId = getSectionId(section_label_id);
            System.out.println("sectionId = " + sectionId);

            double[] interestExpense = readStatLine(driver, "operationalIncome",
                    "//div[@id='"+sectionId+"']/div[", 6);
            stock.setInterestExpense(interestExpense);
        }

    }

    private String getSectionId(String label_id) {
        return label_id.replace("label_", "data_");
    }

    void loadKeyStatsInformation(WebDriver driver, Stock stock) throws InterruptedException
    {
        driver.findElement(By.linkText("Key Stats")).click();
        Thread.sleep(4000);

        double[] operationalIncomes = readStatLine(driver, "operationalIncome",
                "//div[@id='financials']/table/tbody/tr[6]/td[", 11);
        stock.setOperationalIncome(operationalIncomes);

        double[] netIncomes = readStatLine(driver, "netIncomes",
                "//div[@id='financials']/table/tbody/tr[10]/td[", 11);
        stock.setNetIncome(netIncomes);

        double payoutRatioLastYear = getDouble(driver.findElement(By.xpath("//div[@id='financials']/table/tbody/tr[16]/td[10]")).getText());
        System.out.println("payoutRatioLastYear = " + payoutRatioLastYear);
        stock.setPayoutRatioLastYear(payoutRatioLastYear);

        double payoutRatioTTM = getDouble(driver.findElement(By.xpath("//div[@id='financials']/table/tbody/tr[16]/td[11]")).getText());
        System.out.println("payoutRatioTTM = " + payoutRatioTTM);
        stock.setPayoutRatioTTM(payoutRatioTTM);

        double[] operCashFlow = readStatLine(driver, "operCashFlow",
                "//div[@id='financials']/table/tbody/tr[22]/td[", 11);
        stock.setOperCashFlow(operCashFlow);

        double[] returnOnEquity = readStatLine(driver, "returnOnEquity",
                "//div[@id='tab-profitability']/table[2]/tbody/tr[12]/td[", 11);
        stock.setReturnOnEquity(returnOnEquity);

    }

    private double[] readStatLine(WebDriver driver, String sequenceName, String reqExp, int length) {
        double[] operationalIncome = new double[length];
        for (int i = 0; i < length; i++) {
            String operIncome = driver.findElement(By.xpath(reqExp + (i + 1) + "]")).getText();
            System.out.println("operIncome = " + operIncome);
            operationalIncome[i] = getDouble(operIncome);
        }
        System.out.println(sequenceName + " = " + Arrays.toString(operationalIncome));
        return operationalIncome;

    }

    private double getDouble(String str) {
        str = str.replaceAll("%", "").replaceAll(",", "").replaceAll("—", "0");
        if (str.isEmpty())
            return -1;
        return Double.parseDouble(str);
    }

}
