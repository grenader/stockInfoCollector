package com.grenader.financial.service;

import com.grenader.financial.model.Stock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by ikanshyn on 2017-03-20.
 */
public class StockWriterTest {

    private StockWriter service = new StockWriter();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void writeToExcel_simple() throws Exception {
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        stocks.add(new Stock("Test Stock Name 1", "ST1"));
        stocks.add(new Stock("Test Stock Name 2", "ST1"));
        service.writeToExcel(stocks, "writeResSimple.xls");
    }

    @Test
    public void writeToExcel_full() throws Exception {
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        Stock stock1 = new Stock("Test Stock Name 1", "ST1", 101, 102, 4.4, "Yes", "123 ml", 14.1, 11.1);
        stock1.setDividendsPerStock(new double[]{3.1, 3.3, 3.5, 3.7, 3.8});
        stock1.setPricePerYears(new double[]{90, 95.1, 100.3, 105.6, 106.7, 110.8, 90, 95.1, 100.3, 105.6, 106.7});
        stock1.setDividendsPerYears(new double[]{5, 6, 7, 8, 9, 5, 6, 7, 8, 9});
        stocks.add(stock1);
        Stock stock2 = new Stock("Test Stock Name 2", "ST2", 201, 202, 3.4, "No", "321 ml", 11.1, 7.1);
        stock2.setDividendsPerStock(new double[]{4.1, 4.3, 4.5, 4.7, 4.8});
        stock2.setPricePerYears(new double[]{190, 195.1, 200.3, 205.6, 206.7, 210.8, 190, 195.1, 200.3, 205.6, 206.7});
        stock2.setDividendsPerYears(new double[]{15, 16, 17, 18, 19, 15, 16, 17, 18, 19});
        stocks.add(stock2);
        service.writeToExcel(stocks, "writeResTwoRecords.xls");
    }

    @Test
    public void writeAStock() throws Exception {

    }

}