package test;

import com.google.common.collect.Lists;
import com.google.common.io.LineReader;
import org.apache.commons.lang.StringUtils;
import org.stock.db.bean.StockBaseInfo;
import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.service.IStockBaseInfoService;
import org.stock.db.service.IStockExchangeInfoService;
import org.stock.db.util.SpringService;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 修正数据
 *
 * Created by gyang on 15-11-5.
 */
public class ModifyData {

    private final IStockBaseInfoService baseInfoService = (IStockBaseInfoService) SpringService
            .getInstance().getService("stockBaseInfoService");
    private final IStockExchangeInfoService exchangeInfoService = (IStockExchangeInfoService) SpringService
            .getInstance().getService("stockExchangeInfoService");

    private List<Integer> getIds() throws IOException {
        List<Integer> ids = Lists.newArrayList();
        LineReader lineReader = new LineReader(new FileReader("/home/gyang/stock/stock_id.txt"));
        String line = null;
        while ((line = lineReader.readLine()) != null) {
            if(StringUtils.isNotEmpty(line)) {
                ids.add(Integer.valueOf(line));
            }
        }

        return ids;
    }

    private void process() throws IOException {
        List<Integer> ids = getIds();
        StockBaseInfo stockBaseInfo = null;
        StockExchangeInfo stockExchangeInfo = null;
        for(Integer id : ids) {
            stockBaseInfo = baseInfoService.selectStockBaseInfoById(id.intValue());
            stockExchangeInfo = exchangeInfoService.selectByCodeFomMaxDate(stockBaseInfo);
            if(stockBaseInfo != null && stockExchangeInfo != null && stockBaseInfo.getType() != stockExchangeInfo.getType()) {
                // 更新type
                System.out.println("Update stock_exchange_info, code = " + stockBaseInfo.getCode() + ", name = " + stockBaseInfo.getName());
                StockExchangeInfo updateInfo = new StockExchangeInfo();
                updateInfo.setCode(stockBaseInfo.getCode());
                updateInfo.setType(stockBaseInfo.getType());
                int count = exchangeInfoService.updateTypeByCode(updateInfo);
                System.out.println("Update stock_exchange_info, code = " + stockBaseInfo.getCode() + ", name = " + stockBaseInfo.getName() + ", success = " + (count > 0 ? "true" : "false"));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ModifyData modifyData = new ModifyData();
        modifyData.process();
        System.out.println("Complete!!!");
    }
}