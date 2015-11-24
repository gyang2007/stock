package org.stock.db.service;

import java.util.List;

import com.google.common.io.LineReader;
import org.stock.common.bean.StockBaseInfo;
import org.stock.db.common.StockType;

public interface IStockBaseInfoService {
	List<StockBaseInfo> selectAllStockBaseInfoList();
	List<StockBaseInfo> selectStockBaseInfoListByType(StockType type);
	StockBaseInfo selectStockBaseInfoById(int id);
	void saveStockBaseInfo(StockBaseInfo info);
	void saveStockBaseInfoList(List<StockBaseInfo> infos);
}
