package test;

import org.junit.Test;
import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.service.IStockExchangeInfoService;
import org.stock.db.util.SpringService;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by gyang on 15-9-4.
 */
public class Test01 {
    private final IStockExchangeInfoService exchangeInfoService = (IStockExchangeInfoService) SpringService
            .getInstance().getService("stockExchangeInfoService");

    @Test
    public void t1() {
        StockExchangeInfo updateInfo = new StockExchangeInfo();
        updateInfo.setCode(900957);
        updateInfo.setType(10);
        int count = exchangeInfoService.updateTypeByCode(updateInfo);
        System.out.println("count: " + count);
    }
}
