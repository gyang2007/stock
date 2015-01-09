package org.stock.db.service.impl;

import java.util.List;

import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.dao.StockExchangeInfoDao;
import org.stock.db.service.IStockExchangeInfoService;

public class StockExchangeInfoServiceImpl implements IStockExchangeInfoService {

	private StockExchangeInfoDao stockExchangeInfoDao;
	
	public void setStockExchangeInfoDao(StockExchangeInfoDao stockExchangeInfoDao) {
		this.stockExchangeInfoDao = stockExchangeInfoDao;
	}
	
	@Override
	public void saveStockExchangeInfo(StockExchangeInfo info) {
		stockExchangeInfoDao.saveStockExchangeInfo(info);
	}

	@Override
	public void saveStockExchangeInfoList(List<StockExchangeInfo> infos) {
		stockExchangeInfoDao.saveStockExchangeInfoList(infos);
	}

}
