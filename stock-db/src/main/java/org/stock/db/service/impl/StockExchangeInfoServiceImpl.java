package org.stock.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.stock.db.bean.StockBaseInfo;
import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.dao.StockExchangeInfoDao;
import org.stock.db.service.IStockExchangeInfoService;

@Service(value = "stockExchangeInfoService")
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

	@Override
	public StockExchangeInfo selectByCodeFomMaxDate(StockBaseInfo info) {
		return stockExchangeInfoDao.selectByCodeFomMaxDate(info);
	}

	@Override
	public StockExchangeInfo selectByCodeTypeFomMaxDate(StockBaseInfo info) {
		return stockExchangeInfoDao.selectByCodeTypeFomMaxDate(info);
	}

	@Override
	public StockExchangeInfo selectByCodeType(StockBaseInfo info) {
		return stockExchangeInfoDao.selectByCodeType(info);
	}

	@Override
	public List<StockExchangeInfo> selectMaxDate() {
		return stockExchangeInfoDao.selectMaxDate();
	}

    @Override
    public int updateTypeByCode(StockExchangeInfo info) {
        return stockExchangeInfoDao.updateTypeByCode(info);
    }
}
