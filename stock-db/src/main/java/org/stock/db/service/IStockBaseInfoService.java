package org.stock.db.service;

import java.util.List;

import org.stock.db.bean.StockBaseInfo;
import org.stock.db.common.StockType;

public interface IStockBaseInfoService {
	List<StockBaseInfo> selectAllStockBaseInfoList();
	List<StockBaseInfo> selectStockBaseInfoListByType(StockType type);
	void saveStockBaseInfo(StockBaseInfo info);
	void saveStockBaseInfoList(List<StockBaseInfo> infos);
}
