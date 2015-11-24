package org.stock.business.analysis;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.analysis.service.IStockFluctuationSdService;
import org.stock.analysis.service.impl.StockFluctuationSdServiceImpl;
import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;
import org.stock.db.common.StockType;
import org.stock.db.service.IStockBaseInfoService;
import org.stock.db.service.IStockExchangeInfoService;
import org.stock.db.util.SpringService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 计算股票波动的标准差
 *
 * Created by gyang on 15-11-25.
 */
public class StockPriceStandardDeviation {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceStandardDeviation.class);

    private final IStockBaseInfoService stockBaseInfoService = (IStockBaseInfoService) SpringService.getInstance().getService("stockBaseInfoService");
    private final IStockExchangeInfoService stockExchangeInfoService = (IStockExchangeInfoService) SpringService.getInstance().getService("stockExchangeInfoService");

    private final IStockFluctuationSdService sdService = new StockFluctuationSdServiceImpl();

    private void process() {
        List<Map<Integer, Double>> lists = Lists.newArrayList();
        List<StockBaseInfo> stockBaseInfos = stockBaseInfoService.selectStockBaseInfoListByType(StockType.STOCK);
        Date txDate = getTxDate();
        StockExchangeInfo queryModel = new StockExchangeInfo();
        queryModel.setType(StockType.STOCK.getValue());
        queryModel.setTxDate(txDate);
        List<StockExchangeInfo> stockExchangeInfos;
        double sdValue;
        for(StockBaseInfo stockBaseInfo : stockBaseInfos) {
            queryModel.setCode(stockBaseInfo.getCode());
            stockExchangeInfos = stockExchangeInfoService.selectByCodeTypeTxDate(queryModel);
            // 交易记录大于20条
            if(CollectionUtils.isEmpty(stockExchangeInfos) || stockExchangeInfos.size() < 20) {
                LOGGER.info("Query stockExchangeInfos record count is not satisfying, code = {}, type = {}, count = {}", new Object[]{queryModel.getCode(), queryModel.getType(), stockExchangeInfos.size()});
                continue;
            }

            sdValue = sdService.standardDeviation(stockExchangeInfos);
            if(sdValue != 0.0) {
                lists.add(ImmutableMap.of(Integer.valueOf(stockBaseInfo.getCode()), Double.valueOf(sdValue)));
            }
        }

        Ordering<Map<Integer, Double>> ordering = Ordering.natural().onResultOf(new Function<Map<Integer, Double>, Comparable>() {
            @Override
            public Comparable apply(Map<Integer, Double> input) {
                return input.entrySet().iterator().next().getValue().doubleValue();
            }
        });

        lists = ordering.sortedCopy(lists);

        for(int i = 0; i < 50 && i < lists.size(); i++) {
            LOGGER.info("StockPriceStandardDeviation result: {}", new Object[]{lists.get(i)});
            System.out.println(lists.get(i));
        }
    }

    private Date getTxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 8, 1, 0, 0,  0);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        new StockPriceStandardDeviation().process();
    }
}
