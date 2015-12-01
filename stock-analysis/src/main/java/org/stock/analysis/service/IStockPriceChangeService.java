package org.stock.analysis.service;

import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;

import java.util.List;

/**
 * 根据股票交易数据筛选股票
 *
 * Created by gyang on 15-12-2.
 */
public interface IStockPriceChangeService {

    /**
     * 筛选连续若干交易日股票涨幅、量比涨幅满足条件的股票
     *
     * @param stockExchInfosList    股票交易数据
     * @param days  交易天数
     * @param priceChange   涨幅
     * @param volumeRate    量比
     * @param volumeAvgDays    平均交易量依据天数
     * @return
     */
    public List<StockBaseInfo> continuousRising(List<List<StockExchangeInfo>> stockExchInfosList, int days, double priceChange, double volumeRate, int volumeAvgDays);

    /**
     * 筛选连续若干交易日股票涨幅、量比涨幅满足条件的股票
     *
     * @param stockExchInfos    股票交易数据
     * @param days  交易天数
     * @param priceChange   涨幅
     * @param volumeRate    量比
     * @param volumeAvgDays    平均交易量依据天数
     * @return
     */
    public StockBaseInfo continuousRisingOne(List<StockExchangeInfo> stockExchInfos, int days, double priceChange, double volumeRate, int volumeAvgDays);
}
