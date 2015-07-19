package org.stock.db.service;

import java.util.List;

import org.stock.db.bean.StockBaseInfo;
import org.stock.db.bean.StockExchangeInfo;

public interface IStockExchangeInfoService {
	void saveStockExchangeInfo(StockExchangeInfo info);
	void saveStockExchangeInfoList(List<StockExchangeInfo> infos);
	StockExchangeInfo selectByCodeFomMaxDate(StockBaseInfo info);
	StockExchangeInfo selectByCode(StockBaseInfo info);
	List<StockExchangeInfo> selectMaxDate();
}
