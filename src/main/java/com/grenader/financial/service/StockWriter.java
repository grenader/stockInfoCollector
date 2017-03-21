package com.grenader.financial.service;

import com.grenader.financial.model.Stock;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by ikanshyn on 2017-03-20.
 */
public class StockWriter {

    public static final int NEXT_YEAR = 2017;

    public void writeToExcel(List<Stock> listBook, String excelFilePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;
        Row row = sheet.createRow(++rowCount);
        writeHeader(row);
        for (Stock aBook : listBook) {
            row = sheet.createRow(++rowCount);
            writeAStock(aBook, row);
        }

        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        outputStream.close();
    }

    void writeAStock(Stock stock, Row row) {
        int columnIndex = 0;
        Cell cell = row.createCell(columnIndex++);
        cell.setCellValue(stock.getStockName());

        cell = row.createCell(columnIndex++);
        cell.setCellValue(stock.getStockTicker());
        CellAddress address = cell.getAddress();
        System.out.println("address = " + address);


        cell = row.createCell(columnIndex++);
        cell.setCellValue(stock.getLastPrice());

        cell = row.createCell(columnIndex++);
        cell.setCellValue(stock.getYield());

        cell = row.createCell(columnIndex++);
        cell.setCellValue(stock.getPayoutRatioTTM());

        cell = row.createCell(columnIndex++);
        cell.setCellValue(stock.getPayoutRatioLastYear());

        // Dividend Growth
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");

        cell = row.createCell(columnIndex++);
        cell.setCellValue(stock.getIsReinvestment());

        // Average ROE, 3 years, > 13%
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");

        // Debt coveraga ratio,  should be > 3
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");

        // just an empty cell
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");
        if (stock.getDividendsPerStock() != null)
        for (double div : stock.getDividendsPerStock()) {
            cell = row.createCell(columnIndex++);
            CellAddress address2 = cell.getAddress();
            System.out.println("address = " + address2);
            cell.setCellValue(div);
        }

        // just an empty cell
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");
        if (stock.getPricePerYears() != null)
        for (double div : stock.getPricePerYears()) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue(div);
        }

        // just an empty cell
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");
        if (stock.getDividendsPerYears() != null)
        for (double div : stock.getDividendsPerYears()) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue(div);
        }
    }

    void writeHeader(Row row) {
        int columnIndex = 0;
        Cell cell = row.createCell(columnIndex++);
        cell.setCellValue("Securities Name");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Stock Ticker");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Last Price");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Yield, %");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Payout Ratio, TTM");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Payout Ratio, Last Year");

        // Dividend Growth
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Dividend Growth, x years, %");

        cell = row.createCell(columnIndex++);
        cell.setCellValue("Is Reinvestment?");

        // Average ROE, 3 years, > 13%
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Average ROE, 3 years, > 13%");

        // Debt coverage ratio,  should be > 3
        cell = row.createCell(columnIndex++);
        cell.setCellValue("Debt coverage ratio,  should be > 3");

        // just an empty cell
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");
        for (int ii = NEXT_YEAR-5; ii < NEXT_YEAR; ii++) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue("DpS, "+ii);
        }

        // just an empty cell
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");
        for (int ii = NEXT_YEAR-10; ii < NEXT_YEAR; ii++) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue("PpY, "+ii);
        }
        cell = row.createCell(columnIndex++);
        cell.setCellValue("PpY, YTD");

        // just an empty cell
        cell = row.createCell(columnIndex++);
        cell.setCellValue("");
        for (int ii = NEXT_YEAR-10; ii < NEXT_YEAR; ii++) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue("DpY, "+ii);
        }
    }

}
