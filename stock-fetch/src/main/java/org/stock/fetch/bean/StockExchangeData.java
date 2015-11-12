package org.stock.fetch.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gyang on 15-11-13.
 */
public class StockExchangeData implements Serializable{
    private int code;
    private int type;
    private double open;
    private double hight;
    private double low;
    private double close;
    private double volume;
    private double exchRate;
    private Date txDate;

    public StockExchangeData() {
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getExchRate() {
        return exchRate;
    }

    public void setExchRate(double exchRate) {
        this.exchRate = exchRate;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    @Override
    public String toString() {
        return "StockExchangeData{" +
                "code=" + code +
                ", type=" + type +
                ", open=" + open +
                ", hight=" + hight +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", exchRate=" + exchRate +
                ", txDate=" + txDate +
                '}';
    }
}
