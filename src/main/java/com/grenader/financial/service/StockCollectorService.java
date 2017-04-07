package com.grenader.financial.service;

import com.grenader.financial.model.Stock;
import com.grenader.financial.model.TickerPair;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ikanshyn on 2017-03-26.
 */
public class StockCollectorService {

    private WebDriver driver;
    private StockDataReader reader;

    private StockWriter writer;

    public StockCollectorService() {
        reader = new StockDataReader();

        writer = new StockWriter();
    }

    public void collect(String resourceFileName, String excelResultFilePath) throws IOException {

        List<TickerPair> tickerToLoad = prepareTickersList(resourceFileName);

        List<Stock> list;
        try {
            driver = new FirefoxDriver();
            list = readAllStocksInfo(tickerToLoad);
        } finally {
            driver.close();
        }

        writer.writeToExcel(list, excelResultFilePath);
    }

    private List<Stock> readAllStocksInfo(List<TickerPair> tickerToLoad) {
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
        return list;
    }

    private List<TickerPair> prepareTickersList(String resourceFileName) {
        List<String> lines = readLines(resourceFileName);
        List<TickerPair> tickerToLoad = new ArrayList<>();

        for (String line : lines) {
            if (line.isEmpty())
                continue;
            StringTokenizer tokenizer = new StringTokenizer(line.trim(),",");
            if (tokenizer.countTokens() != 2)
                throw new IllegalArgumentException("Incorrect stock line: '"+line+"'! Reading was halted!");
            tickerToLoad.add(new TickerPair(tokenizer.nextToken(), tokenizer.nextToken()));
        }
        System.out.println("tickerToLoad = " + tickerToLoad);
        return tickerToLoad;
    }


    List<String> readLines(String resourceFileName) {
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


}
