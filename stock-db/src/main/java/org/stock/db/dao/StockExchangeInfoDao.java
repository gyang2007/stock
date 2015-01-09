package org.stock.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.stock.db.bean.StockExchangeInfo;

public class StockExchangeInfoDao {
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void saveStockExchangeInfo(StockExchangeInfo info) {
		this.sqlSession.insert("org.stock.db.bean.StockExchangeInfo.insert", info);
	}
	
	public void saveStockExchangeInfoList(List<StockExchangeInfo> infos) {
		this.sqlSession.insert("org.stock.db.bean.StockExchangeInfo.insertBatch", infos);
	}
}
