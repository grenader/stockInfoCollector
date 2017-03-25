package com.grenader.financial;

import com.grenader.financial.model.Stock;
import com.grenader.financial.model.TickerPair;
import com.grenader.financial.service.StockDataReader;
import com.grenader.financial.service.StockWriter;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockInfoCollectorTest {
    private WebDriver driver;

    private StockDataReader reader = new StockDataReader();
    private StockWriter writter;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        writter = new StockWriter();
    }

    @Test
    public void testReadAStock_BMO() throws Exception {
        Stock stock = reader.readAStock(driver, "BMO", "CAN");
        writter.writeToExcel(Arrays.asList(stock), "writeReal_BMO.xls");
    }

    @Test
    public void testReadAStock_CardinalHealth() throws Exception {
        Stock stock = reader.readAStock(driver, " CAH", "USA");
        writter.writeToExcel(Arrays.asList(stock), "writeReal_CAH.xls");
    }

    @Test
    public void testReadAStock_MSFT() throws Exception {
        Stock stock = reader.readAStock(driver, "MSFT", "USA");
        writter.writeToExcel(Arrays.asList(stock), "writeReal_MSFT.xls");
    }

    @Test
    public void testReadSeveralStocks() throws Exception {

        List<TickerPair> tickerToLoad = new ArrayList<TickerPair>();

        tickerToLoad.add(new TickerPair("NVDA", "USA"));
        tickerToLoad.add(new TickerPair("TXN", "USA"));
        tickerToLoad.add(new TickerPair("ASUUY", "USA"));
        tickerToLoad.add(new TickerPair("HPQ", "USA"));
        tickerToLoad.add(new TickerPair("AAPL", "USA"));
        tickerToLoad.add(new TickerPair("INTC", "USA"));
        tickerToLoad.add(new TickerPair("BMO", "CAN"));
        tickerToLoad.add(new TickerPair("RY", "CAN"));
        tickerToLoad.add(new TickerPair("TD", "CAN"));
        tickerToLoad.add(new TickerPair("MSFT", "USA"));

        // Sony Corp - SNEJF
        // Apple
        // Dell Technologies Inc V - DVMT
        // Advanced Micro Devices Inc - AMD
        // STMicroelectronics NV - STMEF
        // Semiconductors ETFs

        List<Stock> list = new ArrayList<Stock>();
        for (TickerPair tickerPair : tickerToLoad) {
            Stock stock1 = null;
            try {
                stock1 = reader.readAStock(driver, tickerPair.getTicker(), tickerPair.getCountryCode());
            } catch (Exception e) {
                System.out.println("Error while reading a stock: "+tickerPair);
                e.printStackTrace();
            }
            if (stock1 != null)
            list.add(stock1);
        }
        writter.writeToExcel(list, "writeRealStocks_3.xls");
//        System.out.println("MSFT stock = " + stock);
    }
    @Test
    public void testExpertsCADStocks() throws Exception {

        List<TickerPair> tickerToLoad = new ArrayList<TickerPair>();

        tickerToLoad.add(new TickerPair("REI.UN", "CAN"));
        tickerToLoad.add(new TickerPair("ENB", "CAN"));
        tickerToLoad.add(new TickerPair("ECI", "CAN"));
        tickerToLoad.add(new TickerPair("BCE", "CAN"));

        tickerToLoad.add(new TickerPair("BNS", "CAN"));
        tickerToLoad.add(new TickerPair("TRP", "CAN"));
        tickerToLoad.add(new TickerPair("SLF", "CAN"));

        tickerToLoad.add(new TickerPair("IGM", "CAN"));
        tickerToLoad.add(new TickerPair("ENF", "CAN"));

        List<Stock> list = new ArrayList<Stock>();
        for (TickerPair tickerPair : tickerToLoad) {
            Stock stock1 = null;
            try {
                stock1 = reader.readAStock(driver, tickerPair.getTicker(), tickerPair.getCountryCode());
            } catch (Exception e) {
                System.out.println("Error while reading a stock: "+tickerPair);
                e.printStackTrace();
            }
            if (stock1 != null)
            list.add(stock1);
        }
        writter.writeToExcel(list, "res_expertsCADStocks.xls");
    }

    @Test
    public void testReadUSAArictocrats() throws Exception {
        List<String> tickers = readTickers("/usaAristocrats2017.txt");

        List<TickerPair> tickerToLoad = new ArrayList<TickerPair>();

        for (String ticker : tickers) {
            tickerToLoad.add(new TickerPair(ticker, "USA"));
        }
        System.out.println("tickerToLoad = " + tickerToLoad);

        List<Stock> list = new ArrayList<Stock>();
        for (TickerPair tickerPair : tickerToLoad) {
            Stock stock1 = null;
            try {
                stock1 = reader.readAStock(driver, tickerPair.getTicker(), tickerPair.getCountryCode());
            } catch (Exception e) {
                System.out.println("Error while reading a stock: "+tickerPair);
                e.printStackTrace();
            }
            if (stock1 != null)
            list.add(stock1);
        }
        writter.writeToExcel(list, "res_usaAristocrats2017.xls");

    }

    List<String> readTickers(String resourceFileName) {
        try {

            URL resource = this.getClass().getResource(resourceFileName);
            if (resource == null)
                throw new IllegalArgumentException("Resource file ["+resourceFileName+"] not found!");

            File file = new File(resource.getFile());
            List<String> lines = FileUtils.readLines(file, "UTF-8");

            System.out.println("Going to read the followint stocks:");
            for (String line : lines) {
                System.out.println(line);
            }
            return  lines;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }
}
