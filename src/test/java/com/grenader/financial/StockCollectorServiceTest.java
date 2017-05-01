package com.grenader.financial;

import com.grenader.financial.service.StockCollectorService;
import org.junit.Before;
import org.junit.Test;

public class StockCollectorServiceTest {

    private StockCollectorService service;

    @Before
    public void setUp() throws Exception {
        service = new StockCollectorService();
    }

    @Test
    public void testReadUSAArictocrats() throws Exception {
        service.collect("/usaAristocrats2017.txt", "res_usaAristocrats2017.xls");
    }

    @Test
    public void testReadCADExpertsStocks() throws Exception {
        service.collect("/cadExpertStocks2017.txt", "res_cadExpertStocks2017.xls");
    }

    @Test
    public void testReadTop10CanadianDividendStocksMarch2017() throws Exception {
        service.collect("/top10CanadianDividendStocksMarch2017.txt", "res_top10CanadianDividendStocksMarch2017-2.xls");
    }

    @Test
    public void testReadDividendEarner2017() throws Exception {
        service.collect("/dividendEarner2017.txt", "res_dividendEarner2017.xls");
    }

    @Test
    public void testReadDividendEarnerComplete() throws Exception {
        service.collect("/dividendEarnerComplete.txt", "res_dividendEarnerComplete.xls");
    }

    @Test
    public void testReadMoneysenseBestDividendAllStars2016() throws Exception {
        service.collect("/moneysenseBestDividendAllStars2016.txt", "res_moneysenseBestDividendAllStars2016.xls");
    }

    @Test
    public void testReadDividentSnapshotCanada2017() throws Exception {
        service.collect("/dividentSnapshotCanada2017.txt", "res_dividentSnapshotCanada2017.xls");
    }

    @Test
    public void testReadSemiconductorStocksFromEFTs() throws Exception {
        service.collect("/semiconductorStocks.txt", "res_semiconductorStocksFromEFT.xls");
    }

    @Test
    public void testReadSemiconductorEFTs() throws Exception {
        service.collect("/semiconductorETFs.txt", "res_semiconductorEFTs.xls");
    }

    @Test
    public void testReadCanadianREIT() throws Exception {
        service.collect("/canadianREIT.txt", "res_canadianREIT.xls");
    }

}
