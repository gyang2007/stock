package org.stock.common.util;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.common.bean.StockExchangeInfo;

import java.util.List;

/**
 * 股票数据排序
 *
 * Created by gyang on 15-11-30.
 */
public final class SortStockUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SortStockUtil.class);

    // 按照交易时间升叙排列
    private static final Ordering<StockExchangeInfo> TX_DATE_ORDERING_DESC = Ordering.natural().onResultOf(new Function<StockExchangeInfo, Comparable>() {
        @Override
        public Comparable apply(StockExchangeInfo stockExchangeInfo) {
            if(stockExchangeInfo.getTxDate() == null) {
                LOGGER.error("Invalid txDate! code = {}, type = {}", new Object[]{stockExchangeInfo.getCode(), stockExchangeInfo.getType()});
            }

            return stockExchangeInfo.getTxDate();
        }
    });

    /**
     * 按照交易时间升序排列
     *
     * @param lists
     * @return
     */
    public static List<StockExchangeInfo> sortAscending (List<StockExchangeInfo> lists) {
        return TX_DATE_ORDERING_DESC.sortedCopy(lists);
    }

    /**
     * 按照交易时间降序排列
     *
     * @param lists
     * @return
     */
    public static List<StockExchangeInfo> sortDescending (List<StockExchangeInfo> lists) {
        return TX_DATE_ORDERING_DESC.reverse().sortedCopy(lists);
    }
}
