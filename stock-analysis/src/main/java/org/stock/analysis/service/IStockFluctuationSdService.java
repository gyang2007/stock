package org.stock.analysis.service;

import org.stock.common.bean.StockExchangeInfo;

import java.util.List;

/**
 * 股票价格波动的标准差计算
 *
 * Created by gyang on 15-11-25.
 */
public interface IStockFluctuationSdService {
    /**
     * 根据股票每日交易想详情计算股票震荡的标准差
     *
     * @param stockExchangeInfos
     * @return
     */
    double standardDeviation(List<StockExchangeInfo> stockExchangeInfos);
}
