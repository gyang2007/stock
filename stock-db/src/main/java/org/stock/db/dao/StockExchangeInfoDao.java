package org.stock.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.stock.db.bean.StockBaseInfo;
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
	
	public StockExchangeInfo selectByCodeFomMaxDate(StockBaseInfo info) {
		return (StockExchangeInfo) this.sqlSession.selectOne("org.stock.db.bean.StockExchangeInfo.selectByCodeFomMaxDate", info);
	}

	public StockExchangeInfo selectByCodeTypeFomMaxDate(StockBaseInfo info) {
		return (StockExchangeInfo) this.sqlSession.selectOne("org.stock.db.bean.StockExchangeInfo.selectByCodeTypeFomMaxDate", info);
	}
	
	public StockExchangeInfo selectByCodeType(StockBaseInfo info) {
		return (StockExchangeInfo) this.sqlSession.selectOne("org.stock.db.bean.StockExchangeInfo.selectByCodeType", info);
	}
	
	public List<StockExchangeInfo> selectMaxDate() {
		return this.sqlSession.selectList("org.stock.db.bean.StockExchangeInfo.selectMaxDate");
	}

    public int updateTypeByCode(StockExchangeInfo info) {
        return this.sqlSession.update("org.stock.db.bean.StockExchangeInfo.updateTypeByCode", info);
    }
}
