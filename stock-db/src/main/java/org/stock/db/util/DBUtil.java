package org.stock.db.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public enum DBUtil {
	INSTANCE;
	
	private static final BasicDataSource ds = new BasicDataSource();
	
	static {
		ds.setInitialSize(2);
		ds.setMaxIdle(10);
		ds.setMinIdle(2);
		ds.setMaxActive(100);
		ds.setMaxWait(5000);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("select unix_timestamp()");
		ds.setDriverClassName(PropertiesUtil.getProperties("mysql.jdbc.driver", ""));
		ds.setUrl(PropertiesUtil.getProperties("mysql.jdbc.url", ""));
		ds.setUsername(PropertiesUtil.getProperties("mysql.jdbc.username", ""));
		ds.setPassword(PropertiesUtil.getProperties("mysql.jdbc.password", ""));
	}
	
	private DBUtil() {
		
	}
	
	public static final DBUtil getInstance() {
		return INSTANCE;
	}
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
