package org.stock.db.service.impl;

import org.springframework.stereotype.Service;
import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;
import org.stock.db.dao.StockExchangeInfoDao;
import org.stock.db.service.IStockExchangeInfoService;

import java.util.List;

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

	@Override
	public List<StockExchangeInfo> selectByCodeTypeTxDate(StockExchangeInfo info) {
		return stockExchangeInfoDao.selectByCodeTypeTxDate(info);
	}
}
