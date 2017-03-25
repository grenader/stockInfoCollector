package com.grenader.financial.service;

import com.grenader.financial.model.Stock;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by ikanshyn on 2017-03-20.
 */
public class StockWriter {

    public static final int NEXT_YEAR = 2017;
    private final HSSFWorkbook workbook;
    private final Sheet sheet;
    private final HSSFCellStyle percentStyle;


    public StockWriter() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet();

        percentStyle = workbook.createCellStyle();
        percentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));


    }

    public void writeToExcel(List<Stock> listBook, String excelFilePath) throws IOException {
        int rowCount = 0;
        Row headerRow = sheet.createRow(rowCount);
        for (Stock aBook : listBook) {
            Row row = sheet.createRow(++rowCount);
            writeAStock(aBook, headerRow, row);
        }

        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        outputStream.close();
    }

    void writeAStock(Stock stock, Row headerRow, Row row) {
        int columnIndex = 0;

        writeHeaderCell(headerRow, columnIndex, "Securities Name");
        row.createCell(columnIndex++).setCellValue(stock.getStockName());

        writeHeaderCell(headerRow, columnIndex, "Stock Ticker");
        row.createCell(columnIndex++).setCellValue(stock.getStockTicker());
//        CellAddress address = cell.getAddress();
//        System.out.println("address = " + address);

        writeHeaderCell(headerRow, columnIndex, "Last Price");
        columnIndex = writeNumberCell(row, columnIndex, stock.getLastPrice());

        writeHeaderCell(headerRow, columnIndex, "Yield, %");
        columnIndex = writePercentCell(row, columnIndex, stock.getYield()/100);
//                Cell cell1 = row.createCell(columnIndex++);
//        cell1.setCellValue(stock.getYield());
//        cell1.setCellStyle(percentStyle);

        // Dividend Growth
        writeHeaderCell(headerRow, columnIndex, "Average Div Growth, 4 years, %");
        columnIndex = writePercentCell(row, columnIndex, stock.getAverageDividendsGrowth());

        writeHeaderCell(headerRow, columnIndex, "Payout Ratio, TTM");
        columnIndex = writeNumberCell(row, columnIndex, stock.getPayoutRatioTTM());

        writeHeaderCell(headerRow, columnIndex, "Payout Ratio, Last Year");
        columnIndex = writeNumberCell(row, columnIndex, stock.getPayoutRatioLastYear());

        writeHeaderCell(headerRow, columnIndex, "Is Reinvestment?");
        row.createCell(columnIndex++).setCellValue(stock.getIsReinvestment());

        // Average ROE, 3 years, > 13%
        writeHeaderCell(headerRow, columnIndex, "Average ROE, 3 years, > 13%");
        columnIndex = writeNumberCell(row, columnIndex, stock.getAverageROE3Years());

        // Debt coverage ratio,  should be > 3
        writeHeaderCell(headerRow, columnIndex, "Debt coverage ratio,  should be > 3");
        columnIndex = writeNumberCell(row, columnIndex, stock.getDebtCoverageRatio());

        writeHeaderCell(headerRow, columnIndex, "Sector / Industry");
        row.createCell(columnIndex++).setCellValue(stock.getSectorName());

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getDividendsPerStock() != null) {
            int ii = NEXT_YEAR - 5;
            for (double div : stock.getDividendsPerStock()) {
                writeHeaderCell(headerRow, columnIndex, "Dividend per Stock, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getDividendsGrowth() != null) {
            int ii = NEXT_YEAR - 6;
            for (double div : stock.getDividendsGrowth()) {
                writeHeaderCell(headerRow, columnIndex, "Dividend Growth, " + ii++);
                columnIndex = writePercentCell(row, columnIndex, div);
            }
        }

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getReturnPerYears() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getReturnPerYears()) {
                writeHeaderCell(headerRow, columnIndex, "Return, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getDividendsPerYears() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getDividendsPerYears()) {
                writeHeaderCell(headerRow, columnIndex, "Dividend per Year, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getTotalReturnPerYear() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getTotalReturnPerYear()) {
                writeHeaderCell(headerRow, columnIndex, "Total Return, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getNetIncome() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getNetIncome()) {
                writeHeaderCell(headerRow, columnIndex, "Net Income, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }
        writeHeaderCell(headerRow, columnIndex, "Average Net Income Growth, 5 year");
        columnIndex = writePercentCell(row, columnIndex, stock.getAverageNetIncomeGrowth());

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getRevenue() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getRevenue()) {
                writeHeaderCell(headerRow, columnIndex, "Revenue, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }
        writeHeaderCell(headerRow, columnIndex, "Average Revenue Growth, 5 year");
        columnIndex = writePercentCell(row, columnIndex, stock.getAverageRevenueGrowth());

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getOperationalIncome() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getOperationalIncome()) {
                writeHeaderCell(headerRow, columnIndex, "Operational Income, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }
        writeHeaderCell(headerRow, columnIndex, "Average Operational Income Growth, 5 year");
        columnIndex = writePercentCell(row, columnIndex, stock.getAverageOperationalIncomeGrowth());

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getOperCashFlow() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getOperCashFlow()) {
                writeHeaderCell(headerRow, columnIndex, "Operation Cash Flow, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }
        writeHeaderCell(headerRow, columnIndex, "Average Op Cash Flow Growth, 5 year");
        columnIndex = writePercentCell(row, columnIndex, stock.getAverageOperationalCashFlowGrowth());

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getReturnOnEquity() != null) {
            int ii = NEXT_YEAR - 10;
            for (double div : stock.getReturnOnEquity()) {
                writeHeaderCell(headerRow, columnIndex, "Return on Equity, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }
        writeHeaderCell(headerRow, columnIndex, "Average ROE Growth, 5 year");
        columnIndex = writePercentCell(row, columnIndex, stock.getAverageReturnOnEquityGrowth());

        // just an empty cell
        writeHeaderCell(headerRow, columnIndex, "");
        row.createCell(columnIndex++).setCellValue("");
        if (stock.getInterestExpense() != null) {
            int ii = NEXT_YEAR - 5;
            for (double div : stock.getInterestExpense()) {
                writeHeaderCell(headerRow, columnIndex, "Interest Expenses, " + ii++);
                columnIndex = writeNumberCell(row, columnIndex, div);
            }
        }

    }

    private int writeNumberCell(Row row, int columnIndex, double numberValue) {
        row.createCell(columnIndex++).setCellValue(round2(numberValue));
        return columnIndex;
    }

    private int writePercentCell(Row row, int columnIndex, double percentValue) {
        Cell cell = row.createCell(columnIndex++);
        cell.setCellValue(round4(percentValue));
        cell.setCellStyle(percentStyle);

        return columnIndex;
    }

    private void writeHeaderCell(Row row, int columnIndex, String s) {
        getHeaderCell(row, columnIndex).setCellValue(s);
    }

    private Cell getHeaderCell(Row row, int columnIndex) {
        return row.createCell(columnIndex);
    }

    public static double round2(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double round4(double value) {
        long factor = (long) Math.pow(10, 4);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

/*
    String round2(double value){
        DecimalFormat df = new DecimalFormat("####0.00");
        return df.format(value);
    }
*/

}
