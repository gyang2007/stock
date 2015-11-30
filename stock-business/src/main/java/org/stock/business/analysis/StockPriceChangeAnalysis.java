package org.stock.business.analysis;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;
import org.stock.common.util.SortStockUtil;

import java.util.List;

/**
 * 股票涨幅分析
 *
 * Created by gyang on 15-11-30.
 */
public class StockPriceChangeAnalysis {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceChangeAnalysis.class);

    /**
     * 根据涨幅、量比筛选股票信息
     *
     * @param stockExchInfosList    股票交易数据
     * @param days  交易天数
     * @param priceChange   涨幅
     * @param volumeRate    量比
     * @param volumeAvgDays    平均交易量依据天数
     * @return
     */
    public List<StockBaseInfo> continuousRising(List<List<StockExchangeInfo>> stockExchInfosList, int days, double priceChange, double volumeRate, int volumeAvgDays) {
        if(CollectionUtils.isEmpty(stockExchInfosList)) {
            LOGGER.warn("股票交易数据为空!");
            return Lists.newArrayList();
        }

        List<StockBaseInfo> stockBaseInfos = Lists.newArrayList();
        StockExchangeInfo stockExchangeInfo;
        int i = 0;
        boolean flag;
        for(List<StockExchangeInfo> infos : stockExchInfosList) {
            List<StockExchangeInfo> sortInfos = SortStockUtil.sortDescending(infos);
            i = 0;
            flag = true;
            while(i++ < days) {
                stockExchangeInfo = sortInfos.get(i);
                // 如果涨幅、成交量均满足条件，则筛选OK
                if(stockExchangeInfo.getPriceChange() >= priceChange) {
                    if(volumeRate > 0.0) {

                    }
                }
                else {
                    flag = false;
                    break;
                }
            }
        }

        return null;
    }

    /**
     * 根据涨幅、量比筛选股票信息
     *
     * @param stockExchInfos    股票交易数据
     * @param days  交易天数
     * @param priceChange   涨幅
     * @param volumeRate    量比
     * @param volumeAvgDays    平均交易量依据天数
     * @return
     */
    public StockBaseInfo continuousRisingOne(List<StockExchangeInfo> stockExchInfos, int days, double priceChange, double volumeRate, int volumeAvgDays) {
        List<List<StockExchangeInfo>> stockExchInfosList = Lists.newArrayList();
        stockExchInfosList.add(stockExchInfos);
        List<StockBaseInfo> list = continuousRising(stockExchInfosList, days, priceChange, volumeRate, volumeAvgDays);

        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }



    private double avgVolumeRate(long[] volumes) {
        return DoubleMath.mean(volumes);
    }
}
