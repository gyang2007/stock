package org.stock.db.dao;

import org.springframework.stereotype.Repository;
import org.stock.db.bean.StockMAInfo;

import java.util.List;

@Repository(value = "stockMAInfoDao")
public interface IStockMAInfoDao {

	public int insert(StockMAInfo info);

	public int insertBatch(List<StockMAInfo> infos);

	public List<StockMAInfo> selectAll();

	public List<StockMAInfo> selectByCodeType(StockMAInfo stockMAInfo);
}
