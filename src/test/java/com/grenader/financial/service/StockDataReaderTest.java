package com.grenader.financial.service;

import com.grenader.financial.model.Stock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by ikanshyn on 2017-03-20.
 */
public class StockDataReaderTest {


    private WebDriver driver;

    private StockDataReader reader = new StockDataReader();
    private StockWriter writter = new StockWriter();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
    }

    @Test
    public void testReadAStock_BMO() throws Exception {
        Stock stock = reader.readAStock(driver, "BMO", "CAN");
        System.out.println("BMO stock = " + stock);
    }

    @Test
    public void testReadAStock_MSFT() throws Exception {
        Stock stock = reader.readAStock(driver, "MSFT", "USA");
        System.out.println("MSFT stock = " + stock);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }
}