package org.stock.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.stock.db.bean.StockBaseInfo;
import org.stock.db.common.StockType;
import org.stock.db.dao.StockBaseInfoDao;
import org.stock.db.service.IStockBaseInfoService;

@Service(value = "stockBaseInfoService")
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

	@Override
	public List<StockBaseInfo> selectAllStockBaseInfoList() {
		return stockBaseInfoDao.selectAllStockBaseInfoList();
	}

	@Override
	public List<StockBaseInfo> selectStockBaseInfoListByType(StockType type) {
		return stockBaseInfoDao.selectStockBaseInfoListByType(type.getValue());
	}

	@Override
	public StockBaseInfo selectStockBaseInfoById(int id) {
		return stockBaseInfoDao.selectStockBaseInfoById(id);
	}
}
