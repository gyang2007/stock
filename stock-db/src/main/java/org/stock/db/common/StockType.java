package org.stock.db.common;

public enum StockType {
	SHH_CI(10, "上证指数"),
	SHZH_CI(20, "深证指数");
	
	private int value;
	private String label;
	private StockType(int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getLabel() {
		return this.label;
	}
}
