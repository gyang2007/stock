package org.stock.db.common;

import java.util.HashMap;
import java.util.Map;

public enum StockType {
	SH(10, "SH", "上证指数"),
	SZ(20, "SZ", "深证指数");
	
	private int value;
	private String label1;
	private String label2;
	
	private static Map<Integer, StockType> m1 = new HashMap<Integer, StockType>();
	private static Map<String, StockType> m2 = new HashMap<String, StockType>();
	
	static {
		for(StockType type : StockType.values()) {
			m1.put(type.getValue(), type);
			m2.put(type.getLabel1(), type);
		}
	}
	
	private StockType(int value, String label1, String label2) {
		this.value = value;
		this.label1 = label1;
		this.label2 = label2;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getLabel1() {
		return this.label1;
	}
	
	public String getLabel2() {
		return this.label2;
	}
	
	public static StockType getByValue(int value) {
		return m1.get(value);
	}
	
	public static StockType getByLabel1(String label) {
		return m2.get(label);
	}
}
