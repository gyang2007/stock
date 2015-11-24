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
		this.sqlSession.insert("StockBaseInfo.insert", info);
	}
	
	public void saveStockBaseInfo(List<StockBaseInfo> infos) {
		this.sqlSession.insert("StockBaseInfo.insertBatch", infos);
	}
	
	public List<StockBaseInfo> selectAllStockBaseInfoList() {
		return this.sqlSession.selectList("StockBaseInfo.selectAll");
	}
	
	public List<StockBaseInfo> selectStockBaseInfoListByType(int type) {
		return this.sqlSession.selectList("StockBaseInfo.selectByType", type);
	}

	public StockBaseInfo selectStockBaseInfoById(int id) {
		return this.sqlSession.selectOne("StockBaseInfo.selectById", id);
	}
}
