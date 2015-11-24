package org.stock.db.dao;

import org.apache.ibatis.session.SqlSession;
import org.stock.common.bean.StockBaseInfo;

import java.util.List;

public class StockBaseInfoDao {
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void saveStockBaseInfo(StockBaseInfo info) {
		this.sqlSession.insert("org.stock.db.bean.StockBaseInfo.insert", info);
	}
	
	public void saveStockBaseInfo(List<StockBaseInfo> infos) {
		this.sqlSession.insert("org.stock.db.bean.StockBaseInfo.insertBatch", infos);
	}
	
	public List<StockBaseInfo> selectAllStockBaseInfoList() {
		return this.sqlSession.selectList("org.stock.db.bean.StockBaseInfo.selectAll");
	}
	
	public List<StockBaseInfo> selectStockBaseInfoListByType(int type) {
		return this.sqlSession.selectList("org.stock.db.bean.StockBaseInfo.selectByType", type);
	}

	public StockBaseInfo selectStockBaseInfoById(int id) {
		return this.sqlSession.selectOne("org.stock.db.bean.StockBaseInfo.selectById", id);
	}
}
