package org.stock.business.analysis;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;
import org.stock.common.util.DateUtil;
import org.stock.common.util.SortStockUtil;
import org.stock.db.common.StockType;
import org.stock.db.service.IStockBaseInfoService;
import org.stock.db.service.IStockExchangeInfoService;
import org.stock.db.util.SpringService;

import java.util.Date;
import java.util.List;

/**
 * Created by gyang on 15-12-1.
 */
public class StockPriceChangeAnalysisTest {

    private final IStockBaseInfoService stockBaseInfoService = (IStockBaseInfoService) SpringService.getInstance().getService("stockBaseInfoService");
    private IStockExchangeInfoService stockExchangeInfoService;

    @Before
    public void before() {
        stockExchangeInfoService = (IStockExchangeInfoService) SpringService.getInstance().getService("stockExchangeInfoService");
    }

    @Test
    public void test01() {
        List<StockBaseInfo> stockBaseInfos = stockBaseInfoService.selectStockBaseInfoListByType(StockType.STOCK);
        List<List<StockExchangeInfo>> lists = Lists.newArrayList();
        Date latestExchDate = DateUtil.getLastestExchDate();
        for(StockBaseInfo stockBaseInfo : stockBaseInfos) {
            List<StockExchangeInfo> listsTmp = stockExchangeInfoService.selectByCodeType(stockBaseInfo);
            listsTmp = SortStockUtil.sortDescendingByTxDate(listsTmp);
            if(StringUtils.equals(DateFormatUtils.format(listsTmp.get(0).getTxDate(), "yyyyMMdd"), DateFormatUtils.format(latestExchDate, "yyyyMMdd"))) {
                lists.add(listsTmp);
            }
        }

        StockPriceChangeAnalysis analysis = new StockPriceChangeAnalysis();
        List<StockBaseInfo> results = analysis.continuousRising(lists, 3, 9.5, 1.2, 20);
        if(CollectionUtils.isNotEmpty(results)) {
            for(StockBaseInfo stockBaseInfo : results) {
                System.out.println(String.format("code = %s", new Object[]{stockBaseInfo.getCode()}));
            }
        }
    }
}
