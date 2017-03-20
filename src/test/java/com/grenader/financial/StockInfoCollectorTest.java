package com.grenader.financial;

import com.thoughtworks.selenium.Selenium;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

public class StockInfoCollectorTest {
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        WebDriver driver = new FirefoxDriver();
        String baseUrl = "https://www.google.ca/";
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Test
    public void testStockInfoCollectorTest() throws Exception {

        WebDriver driver;
//		System.setProperty("webdriver.firefox.marionette","C:\\geckodriver.exe");
        driver = new FirefoxDriver();
        String baseUrl = "http://quote.morningstar.ca/quicktakes/Stock/s_ca.aspx?t=BMO&region=CAN&culture=en-CA";
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

//		findElement(By.id

        double lastPrice = Double.parseDouble(driver.findElement(By.id("last-price-value")).getText());
        System.out.println("lastPrice = " + lastPrice);
        double openPrice = Double.parseDouble(driver.findElement(By.id("open-price")).getText());
        System.out.println("openPrice = " + openPrice);

        String yieldStr = driver.findElement(By.cssSelector("td.gr_table_colm2b > span.gr_text1")).getText();
        double yield = Double.parseDouble(yieldStr.replaceAll("%", "").trim());
        System.out.println("yield = " + yield);

        String isReinvestment = driver.findElement(By.cssSelector("div.gr_colm_d1b")).getText();
        System.out.println("isReinvestment = " + isReinvestment);

        String marketCap = driver.findElement(By.id("MarketCap")).getText();
        System.out.println("marketCap = " + marketCap);

        double forward_P_E = Double.parseDouble(driver.findElement(By.cssSelector("span[vkey=PE]")).getText());
        System.out.println("forward_P_E = " + forward_P_E);
        double priceToBook = Double.parseDouble(driver.findElement(By.cssSelector("h3[gkey=PB] ~ span")).getText());
        System.out.println("priceToBook = " + priceToBook);

        // Loading performance information
        driver.findElement(By.linkText("Performance")).click();
        Thread.sleep(4000);

//		String divident_amount_1 =  driver.findElement(By.cssSelector("//div[@id='div_annual_dividends']/table/tbody/tr[2]/td")).getText();
        double[] pricePerYears = new double[11];
        for (int i = 0; i < 11; i++) {
            String priceValue = driver.findElement(By.xpath("//div[@id='chart_container']/table/tbody/tr[1]/td[" + (i + 1) + "]")).getText();
            System.out.println("priceValue = " + priceValue);
            pricePerYears[i] = Double.parseDouble(priceValue);
        }
        System.out.println("pricePerYears = " + Arrays.toString(pricePerYears));

        double[] dividendsPerYears = new double[10];
        for (int i = 0; i < 10; i++) {
            String dividendValue = driver.findElement(By.xpath("//div[@id='chart_container']/table/tbody/tr[4]/td[" + (i + 1) + "]")).getText();
            System.out.println("dividendValue = " + dividendValue);
            dividendsPerYears[i] = Double.parseDouble(dividendValue);
        }
        System.out.println("dividendsPerYears = " + Arrays.toString(dividendsPerYears));

        // Click on Dividend tab
        driver.findElement(By.linkText("Dividend & Splits")).click();
        Thread.sleep(2000);


        double[] dividendsPerStock = new double[5];
        for (int i = 0; i < 5; i++) {
            String dividendPerStock = driver.findElement(By.xpath("//div[@id='div_annual_dividends']/table/tbody/tr[2]/td[" + (i + 1) + "]")).getText();
            System.out.println("dividendPerStock = " + dividendPerStock);
            dividendsPerStock[i] = Double.parseDouble(dividendPerStock);
        }
        System.out.println("dividendsPerStock = " + Arrays.toString(dividendsPerStock));

        Stock stock = new Stock(stockName, stockTicker, lastPrice, openPrice, yield, isReinvestment, marketCap, forward_P_E, priceToBook, pricePerYears, dividendsPerYears, dividendsPerStock);

        // Write results:
        ArrayList<Stock> listBook = new ArrayList<Stock>();
        listBook.add(stock);
        writeExcel(listBook, "c:/!/excelResult.xls");


//		selenium.click("link=Financials");
//		selenium.waitForPageToLoad("3000");
    }

    public void writeExcel(List<Stock> listBook, String excelFilePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;

        for (Stock aBook : listBook) {
            Row row = sheet.createRow(++rowCount);
            writeStock(aBook, row);
        }

        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        outputStream.close();
    }

    private void writeStock(Stock stock, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(stock.getStockName());

        cell = row.createCell(2);
        cell.setCellValue(stock.getStockTicker());

        cell = row.createCell(3);
        cell.setCellValue(stock.getLastPrice());
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}
