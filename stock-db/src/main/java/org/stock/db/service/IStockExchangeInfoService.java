package org.stock.db.service;

import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;

import java.util.List;


public interface IStockExchangeInfoService {
	void saveStockExchangeInfo(StockExchangeInfo info);
	void saveStockExchangeInfoList(List<StockExchangeInfo> infos);
	StockExchangeInfo selectByCodeFomMaxDate(StockBaseInfo info);
	StockExchangeInfo selectByCodeTypeFomMaxDate(StockBaseInfo info);
	StockExchangeInfo selectByCodeType(StockBaseInfo info);

	/**
	 * 获取每一个CODE值的最大交易日期
	 * @return
	 */
	List<StockExchangeInfo> selectMaxDate();
	int updateTypeByCode(StockExchangeInfo info);
	List<StockExchangeInfo> selectByCodeTypeTxDate(StockExchangeInfo info);
}
