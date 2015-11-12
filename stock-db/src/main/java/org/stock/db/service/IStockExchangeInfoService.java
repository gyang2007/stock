package org.stock.db.service;

import java.util.List;

import org.stock.db.bean.StockBaseInfo;
import org.stock.db.bean.StockExchangeInfo;

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
}
