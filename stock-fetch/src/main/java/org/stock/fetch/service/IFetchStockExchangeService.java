package org.stock.fetch.service;

import org.stock.fetch.bean.StockExchangeData;

import java.util.List;

/**
 * Created by gyang on 15-9-3.
 */
public interface IFetchStockExchangeService {
    void process();


    /**
     * 获取指定年份的股票交易信息
     *
     * @param code 代码
     * @param type 类型
     * @param year 年份
     * @return
     */
    List<StockExchangeData> pullStockExchangeDatas(int code, int type, int year);
}
