package com.grenader.financial.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by ikanshyn on 2017-03-20.
 */
public class StockTest {

    @Test
    public void testGetAverageROE3Years_bigger() throws Exception {
        double[] array = new double[]{10, 20, 30, 1, 2, 3};

        Stock stock = new Stock("Name", "ticker");
        stock.setReturnOnEquity(array);
        assertEquals(2, stock.getAverageROE3Years(), 0.0001);
    }

    @Test
    public void testGetAverageROE3Years_small() throws Exception {
        double[] array = new double[]{100, 10, 20, 30};

        Stock stock = new Stock("Name", "ticker");
        stock.setReturnOnEquity(array);
        assertEquals(20, stock.getAverageROE3Years(), 0.0001);
    }

    @Test
    public void testGetAverageROE3Years_negative() throws Exception {
        double[] array = new double[]{-20, 10, 300, 30};

        Stock stock = new Stock("Name", "ticker");
        stock.setReturnOnEquity(array);
        assertEquals(30, stock.getAverageROE3Years(), 0.0001);
    }

    @Test
    public void testDividendsGrowth() throws Exception {
        Stock stock1 = new Stock("Test Stock Name 1", "ST1", 101, 102, 4.4, "Yes", "123 ml", 14.1, 11.1);
        stock1.setDividendsPerStock(new double[]{3.1, 3.3, 3.5, 3.7, 3.8});

        System.out.println("stock1 = " + Arrays.toString(stock1.getDividendsGrowth()));

    }

    @Test
    public void testCalculateAverageGrowthRate() {
        Stock stock = new Stock("Name", "ticker");

        double[] rate = new double[] {-20, -40, -20, 0, 20, 40, 80};
        double[] growth = stock.calculateAverageGrowthRate(rate, false);

        double[] expected = new double[] { -1.0, 0.5, 1.0, Double.POSITIVE_INFINITY, 1.0, 1.0};
        assertArrayEquals(expected, growth, 0.0001);
    }
}