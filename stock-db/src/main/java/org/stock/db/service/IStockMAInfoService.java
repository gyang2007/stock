package org.stock.db.service;

import org.stock.db.bean.StockMAInfo;
import org.stock.db.common.StockType;

import java.util.List;

public interface IStockMAInfoService {
	List<StockMAInfo> selectAllStockMAInfoList();
	List<StockMAInfo> selectStockMAInfoListByCodeType(StockMAInfo stockMAInfo);
	int saveStockMAInfo(StockMAInfo info);
	int saveStockMAInfoList(List<StockMAInfo> infos);
}
