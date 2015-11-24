package org.stock.analysis;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.math.DoubleMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.common.bean.StockExchangeInfo;

import java.util.List;

/**
 * 分析股票某一时间段的振奋波动
 *
 * Created by gyang on 15-11-23.
 */
public class Analysis1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Analysis1.class);

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

    public void process(List<StockExchangeInfo> stockExchangeInfos) {
        List<StockExchangeInfo> infos = TX_DATE_ORDERING_DESC.sortedCopy(stockExchangeInfos);
        double[] doubles = new double[infos.size()];
        StockExchangeInfo info;
        for(int i = 0; i < infos.size(); i++) {
            doubles[i] = infos.get(i).getClose();
        }

        // 平均收盘价格
        double meanVal = DoubleMath.mean(doubles);

        // 每一天收盘价于平均收盘价的比值

        // 比值的标准差
    }
}
