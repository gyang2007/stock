package test;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import java.util.Set;

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

    private List<Integer> getUniqueCodes() throws IOException {
        List<Integer> codes = Lists.newArrayList();
        LineReader lineReader = new LineReader(new FileReader("/home/gyang/stock_data/unique_code.txt"));
        String line = null;
        while ((line = lineReader.readLine()) != null) {
            if(StringUtils.isNotEmpty(line)) {
                codes.add(Integer.valueOf(line));
            }
        }

        return codes;
    }

    private void process() throws IOException {
        List<Integer> codes = getUniqueCodes();
        List<StockBaseInfo> stockBaseInfos = baseInfoService.selectAllStockBaseInfoList();
        final Set<String> codeStrSet = Sets.newHashSet();
        for(Integer code : codes) {
            codeStrSet.add(String.valueOf(code));
        }
        stockBaseInfos = Lists.newArrayList(Collections2.filter(stockBaseInfos, new Predicate<StockBaseInfo>() {
            @Override
            public boolean apply(StockBaseInfo stockBaseInfo) {
                return codeStrSet.contains(String.valueOf(stockBaseInfo.getCode()));
            }
        }));

        for(StockBaseInfo stockBaseInfo : stockBaseInfos) {
            System.out.println("Update stock_exchange_info, code = " + stockBaseInfo.getCode() + ", name = " + stockBaseInfo.getName());
            StockExchangeInfo updateInfo = new StockExchangeInfo();
            updateInfo.setCode(stockBaseInfo.getCode());
            updateInfo.setType(stockBaseInfo.getType());
            int count = exchangeInfoService.updateTypeByCode(updateInfo);
            System.out.println("Update stock_exchange_info, code = " + stockBaseInfo.getCode() + ", name = " + stockBaseInfo.getName() + ", success = " + (count > 0 ? "true" : "false"));
        }
    }

    public static void main(String[] args) throws IOException {
        ModifyData modifyData = new ModifyData();
        modifyData.process();
        System.out.println("Complete!!!");
    }
}