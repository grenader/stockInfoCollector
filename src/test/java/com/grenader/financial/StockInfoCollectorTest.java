package com.grenader.financial;

import com.grenader.financial.model.Stock;
import com.grenader.financial.model.TickerPair;
import com.grenader.financial.service.StockDataReader;
import com.grenader.financial.service.StockWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
        writter.writeToExcel(list, "writeRealStocks_2.xls");
//        System.out.println("MSFT stock = " + stock);
    }


    @After
    public void tearDown() throws Exception {
        driver.close();
    }
}
