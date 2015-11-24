package org.stock.analysis.main;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.math.DoubleMath;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.analysis.service.IStockFluctuationSdService;
import org.stock.analysis.service.impl.StockFluctuationSdServiceImpl;
import org.stock.common.bean.StockExchangeInfo;

import java.util.List;

/**
 * 分析股票某一时间段的振奋波动
 *
 * Created by gyang on 15-11-23.
 */
public class Analysis1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Analysis1.class);

    private IStockFluctuationSdService sdService = new StockFluctuationSdServiceImpl();

    // 按照交易时间升叙排列
    private final Ordering<StockExchangeInfo> TX_DATE_ORDERING_DESC = Ordering.natural().onResultOf(new Function<StockExchangeInfo, Comparable>() {
        @Override
        public Comparable apply(StockExchangeInfo stockExchangeData) {
            if(stockExchangeData.getTxDate() == null) {
                LOGGER.error("Invalid txDate! code = {}, type = {}", new Object[]{stockExchangeData.getCode(), stockExchangeData.getType()});
            }

            return stockExchangeData.getTxDate();
        }
    });

    public void process() {

    }

    public void process(List<StockExchangeInfo> stockExchangeInfos) {
        List<StockExchangeInfo> infos = TX_DATE_ORDERING_DESC.sortedCopy(stockExchangeInfos);
        double standardValue = sdService.standardDeviation(infos);
    }

    public static void main(String[] args) {
        new Analysis1().process();
    }
}
