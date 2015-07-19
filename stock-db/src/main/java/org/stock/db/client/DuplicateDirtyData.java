package org.stock.db.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.util.DBUtil;

public class DuplicateDirtyData {

	private static final String SQL_COUNT = "select code, type, record_time from stock_exchange_info group by code, type, record_time having count(*) >= 1;";
	private static final String SQL_DELETE = "delete from stock_exchange_info where code=? and type=? and record_time=?;";
	
	public static void main(String[] args) {
		DuplicateDirtyData ddd = new DuplicateDirtyData();
		ddd.delete(ddd.getStockBaseInfoList());
	}

	private List<StockExchangeInfo> getStockBaseInfoList() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<StockExchangeInfo> stockBaseInfoList = new ArrayList<StockExchangeInfo>();
		try {
			conn = DBUtil.getInstance().getConnection();
			ps = conn.prepareStatement(SQL_COUNT);
			rs = ps.executeQuery();
			
			StockExchangeInfo info;
			while(rs.next()) {
				info = new StockExchangeInfo();
				
				info.setCode(rs.getInt("code"));
				info.setType(rs.getInt("type"));
				info.setRecordTime(rs.getDate("record_time"));
				
				stockBaseInfoList.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return stockBaseInfoList;
	}
	
	private void delete(List<StockExchangeInfo> infos) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = DBUtil.getInstance().getConnection();
			ps = conn.prepareStatement(SQL_DELETE);
			
			int i = 0;
			for(StockExchangeInfo info : infos) {
				ps.setInt(1, info.getCode());
				ps.setInt(2, info.getType());
				ps.setDate(3, (java.sql.Date)info.getRecordTime());
				
				ps.addBatch();
				
				System.out.println("Delete: code=" + info.getCode() + ", type=" + info.getType() + ", record_time=" + DateFormatUtils.format(info.getRecordTime(), "yyyy-MM-dd HH:mm:ss"));
				if(++i % 1000 == 0) {
					ps.executeBatch();
				}
			}
			
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
