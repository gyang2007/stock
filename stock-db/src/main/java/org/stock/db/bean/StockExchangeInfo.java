package org.stock.db.bean;

import java.io.Serializable;
import java.util.Date;

public class StockExchangeInfo implements Serializable {

    private static final long serialVersionUID = 2276674675270621232L;

    private long id;
    private int code;
    private int type;
    /**
     * 开盘价
     */
    private double open;
    /**
     * 最高价
     */
    private double hight;
    /**
     * 最低价
     */
    private double low;
    /**
     * 收盘价
     */
    private double close;
    /**
     * 成交量
     */
    private long volume;
    /**
     * 涨跌幅
     */
    private double priceChange;
    /**
     * 振幅
     */
    private double totalPriceChange;
    /**
     * 换手率
     */
    private double exchRate;
    /**
     * 收盘日期
     */
    private Date txDate;
    /**
     * 记录日期
     */
    private Date recordTime;

    public StockExchangeInfo() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHight() {
        return hight;
    }

    public void setHight(double hight) {
        this.hight = hight;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

    public double getTotalPriceChange() {
        return totalPriceChange;
    }

    public void setTotalPriceChange(double totalPriceChange) {
        this.totalPriceChange = totalPriceChange;
    }

    public double getExchRate() {
        return exchRate;
    }

    public void setExchRate(double exchRate) {
        this.exchRate = exchRate;
    }

    @Override
    public String toString() {
        return "StockExchangeInfo{" +
                "id=" + id +
                ", code=" + code +
                ", type=" + type +
                ", open=" + open +
                ", hight=" + hight +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", priceChange=" + priceChange +
                ", totalPriceChange=" + totalPriceChange +
                ", exchRate=" + exchRate +
                ", txDate=" + txDate +
                ", recordTime=" + recordTime +
                '}';
    }
}
