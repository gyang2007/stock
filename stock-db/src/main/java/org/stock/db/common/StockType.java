package org.stock.db.common;

import java.util.HashMap;
import java.util.Map;

public enum StockType {
	// 可交易的股票类型
	STOCK(1),
	// 指数
	STOCK_INDEX(2);
	
	private int value;

	private static Map<Integer, StockType> map = new HashMap<Integer, StockType>();

	static {
		for(StockType type : StockType.values()) {
			map.put(type.getValue(), type);
		}
	}
	
	private StockType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
