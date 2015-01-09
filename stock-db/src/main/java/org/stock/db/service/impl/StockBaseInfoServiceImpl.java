package org.stock.db.service.impl;

import java.util.List;

import org.stock.db.bean.StockBaseInfo;
import org.stock.db.dao.StockBaseInfoDao;
import org.stock.db.service.IStockBaseInfoService;

public class StockBaseInfoServiceImpl implements IStockBaseInfoService {
	
	private StockBaseInfoDao stockBaseInfoDao;
	
	public void setStockBaseInfoDao(StockBaseInfoDao stockBaseInfoDao) {
		this.stockBaseInfoDao = stockBaseInfoDao;
	}

	@Override
	public void saveStockBaseInfo(StockBaseInfo info) {
		stockBaseInfoDao.saveStockBaseInfo(info);
	}

	@Override
	public void saveStockBaseInfoList(List<StockBaseInfo> infos) {
		stockBaseInfoDao.saveStockBaseInfo(infos);
	}

}
