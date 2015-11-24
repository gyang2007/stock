package org.stock.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;

public class StockExchangeInfoDao {
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void saveStockExchangeInfo(StockExchangeInfo info) {
		this.sqlSession.insert("StockExchangeInfo.insert", info);
	}
	
	public void saveStockExchangeInfoList(List<StockExchangeInfo> infos) {
		this.sqlSession.insert("StockExchangeInfo.insertBatch", infos);
	}
	
	public StockExchangeInfo selectByCodeFomMaxDate(StockBaseInfo info) {
		return (StockExchangeInfo) this.sqlSession.selectOne("StockExchangeInfo.selectByCodeFomMaxDate", info);
	}

	public StockExchangeInfo selectByCodeTypeFomMaxDate(StockBaseInfo info) {
		return (StockExchangeInfo) this.sqlSession.selectOne("StockExchangeInfo.selectByCodeTypeFomMaxDate", info);
	}
	
	public StockExchangeInfo selectByCodeType(StockBaseInfo info) {
		return (StockExchangeInfo) this.sqlSession.selectOne("StockExchangeInfo.selectByCodeType", info);
	}
	
	public List<StockExchangeInfo> selectMaxDate() {
		return this.sqlSession.selectList("StockExchangeInfo.selectMaxDate");
	}

    public int updateTypeByCode(StockExchangeInfo info) {
        return this.sqlSession.update("StockExchangeInfo.updateTypeByCode", info);
    }

	public List<StockExchangeInfo> selectByCodeTypeTxDate(StockExchangeInfo info) {
		return this.sqlSession.selectList("StockExchangeInfo.selectByCodeTypeTxDate", info);
	}
}
