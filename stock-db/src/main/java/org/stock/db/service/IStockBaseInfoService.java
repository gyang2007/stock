package org.stock.db.service;

import java.util.List;

import org.stock.db.bean.StockBaseInfo;

public interface IStockBaseInfoService {
	void saveStockBaseInfo(StockBaseInfo info);
	void saveStockBaseInfoList(List<StockBaseInfo> infos);
}
