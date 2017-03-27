package com.grenader.financial.service;

import com.grenader.financial.model.Stock;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by ikanshyn on 2017-03-20.
 */
public class StockWriterTest {

    private StockWriter service;

    @Before
    public void setUp() throws Exception {
        service = new StockWriter();
    }

    @Test
    public void testWriteToExcel_simple() throws Exception {
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        stocks.add(new Stock("Test Stock Name 1", "ST1"));
        stocks.add(new Stock("Test Stock Name 2", "ST1"));
        service.writeToExcel(stocks, "writeResSimple.xls");
    }

    @Test
    public void testWriteToExcel_full() throws Exception {
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        Stock stock1 = new Stock("Test Stock Name 1", "ST1", 101, 102, 4.4, "Yes", "123 ml", 14.1, 11.1, 10.3);
        stock1.setDividendsPerStock(new double[]{3.142, 3.373, 3.58, 3.749, 3.8501});
        stock1.setReturnPerYears(new double[]{90, 95.1, 100.3, 105.6, 106.7, 110.8, 90, 95.1, 100.3, 105.6, 106.7});
        stock1.setDividendsPerYears(new double[]{5.78, 6.32, 7.41, 8.34, 9.7, 5.23, 6.87, 7.37, 8.70, 9.32});
        stock1.setNetIncome(new double[]{5.78, 6.32, 7.41, 8.34, 9.7, 5.23, 6.87, 7.37, 8.70, 9.32, 11.34});
        stock1.setOperationalIncome(new double[]{5.78, 6.32, 7.41, 8.34, 9.7, 5.23, 6.87, 7.37, 8.70, 9.32, 11.34});
        stocks.add(stock1);
        Stock stock2 = new Stock("Test Stock Name 2", "ST2", 201, 202, 3.4, "No", "321 ml", 11.1, 7.1, 6.3);
        stock2.setDividendsPerStock(new double[]{4.1, 4.3, 4.5, 4.7, 4.8});
        stock2.setReturnPerYears(new double[]{190, 195.1, 200.3, 205.6, 206.7, 210.8, 190, 195.1, 200.3, 205.6, 206.7});
        stock2.setDividendsPerYears(new double[]{15, 16, 17, 18, 19, 15, 16, 17, 18, 19});
        stock2.setNetIncome(new double[]{2.78, 4.32, 7.51, 8.34, 9.7, 5.23, 6.87, 4.37, 8.35, 9.12, 10.34});
        stock2.setOperationalIncome(new double[]{2.78, 4.32, 7.51, 8.34, 9.7, 5.23, 6.87, 4.37, 8.35, 9.12, 10.34});
        stocks.add(stock2);
        service.writeToExcel(stocks, "writeResTwoRecords.xls");
    }

    @Test
    public void testRound2() throws Exception {
        double round = service.round2(12.3456);

        assertEquals(12.35, round, 0.0001);
    }

    @Test
    public void testRound4() throws Exception {
        double round = service.round4(12.34568);

        assertEquals(12.3457, round, 0.0001);
    }

    @Test
    public void testWriteAStock() throws Exception {
        assertEquals("10.00", service.round2(10));
        assertEquals("10.12", service.round2(10.12));
        assertEquals("10.00", service.round2(10.0012));

        assertEquals("10.05", service.round2(10.05));
        assertEquals("10.06", service.round2(10.059));
    }


    @Test
    public void testFormat() throws IOException, InvalidFormatException {

        try{
            FileOutputStream out = new FileOutputStream("dateFormat.xls");
            HSSFWorkbook hssfworkbook = new HSSFWorkbook();
            HSSFSheet sheet = hssfworkbook.createSheet("new sheet");

            HSSFCellStyle cs = hssfworkbook.createCellStyle();
            HSSFDataFormat df = hssfworkbook.createDataFormat();
            cs.setDataFormat(df.getFormat("#,##0.0"));

            HSSFCellStyle percentStyle = hssfworkbook.createCellStyle();
            percentStyle.setDataFormat(hssfworkbook.createDataFormat().getFormat("0.00%"));

            HSSFRow row = sheet.createRow((short)0);

            HSSFCell cell = row.createCell((short)0);
            cell.setCellValue(11111.1);
            cell.setCellStyle(cs);

            HSSFCell cell2 = row.createCell((short)1);
            cell2.setCellStyle(percentStyle);
            cell2.setCellValue(0.1534);


            hssfworkbook.write(out);

            out.close();
        }catch(Exception e){}
    }




}