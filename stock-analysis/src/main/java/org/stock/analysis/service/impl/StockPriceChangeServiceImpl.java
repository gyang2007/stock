package org.stock.analysis.service.impl;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.analysis.service.IStockPriceChangeService;
import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;
import org.stock.common.util.SortStockUtil;

import java.util.List;

/**
 * Created by gyang on 15-12-2.
 */
public class StockPriceChangeServiceImpl implements IStockPriceChangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceChangeServiceImpl.class);

    @Override
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
            if(CollectionUtils.isNotEmpty(infos)) {
                List<StockExchangeInfo> sortInfos = SortStockUtil.sortDescendingByTxDate(infos);
                i = 0;
                flag = true;
                while(i < days) {
                    stockExchangeInfo = sortInfos.get(i);
                    // 如果涨幅、成交量均满足条件，则筛选OK
                    if(stockExchangeInfo.getPriceChange() >= priceChange) {
                        if(volumeRate > 0.0) {
                            // 计算当日量比
                            int startPointIndex = i + 1;
                            int endPointIndex = startPointIndex + volumeAvgDays;
                            // 获取平均交易量样本数据不满足
                            if(sortInfos.size() < endPointIndex) {
                                flag = false;
                                break;
                            }

                            List<StockExchangeInfo> subList = sortInfos.subList(startPointIndex, endPointIndex);
                            long[] samples = new long[subList.size()];
                            for(int j = 0; j < subList.size(); j++) {
                                samples[j] = subList.get(j).getVolume();
                            }
                            double avgVolume = DoubleMath.mean(samples);
                            // 量比
                            if(avgVolume != 0.0) {
                                double currVolumeRate = stockExchangeInfo.getVolume() / avgVolume;
                                if(currVolumeRate < volumeRate) {
                                    // 量比不满足条件
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        flag = false;
                        break;
                    }

                    i++;
                }


                if(flag) {
                    StockBaseInfo stockBaseInfo = new StockBaseInfo();
                    stockBaseInfos.add(stockBaseInfo);
                    stockBaseInfo.setCode(sortInfos.get(0).getCode());
                    stockBaseInfo.setType(sortInfos.get(0).getType());
                }
            }
        }

        return stockBaseInfos;
    }

    @Override
    public StockBaseInfo continuousRisingOne(List<StockExchangeInfo> stockExchInfos, int days, double priceChange, double volumeRate, int volumeAvgDays) {
        List<List<StockExchangeInfo>> stockExchInfosList = Lists.newArrayList();
        stockExchInfosList.add(stockExchInfos);
        List<StockBaseInfo> list = continuousRising(stockExchInfosList, days, priceChange, volumeRate, volumeAvgDays);

        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }


    public static void main(String[] args) {

    }
}
