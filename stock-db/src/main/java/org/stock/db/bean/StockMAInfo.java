package org.stock.db.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gyang on 15-9-8.
 */
public class StockMAInfo implements Serializable {

    private static final long serialVersionUID = -303866177162093424L;

    private long id;
    private int code;
    private int type;
    /**
     * 收盘价
     */
    private double close;
    /**
     * 5日均线
     */
    private double ma5;
    /**
     * 10日均线
     */
    private double ma10;
    /**
     * 20日均线
     */
    private double ma20;
    /**
     * 30日均线
     */
    private double ma30;
    /**
     * 60日均线
     */
    private double ma60;
    /**
     * 120日均线
     */
    private double ma120;
    /**
     * 250日均线
     */
    private double ma250;
    /**
     * 收盘日期
     */
    private Date txDate;
    /**
     * 记录日期
     */
    private Date recordTime = new Date();

    public StockMAInfo() {

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

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public double getMa30() {
        return ma30;
    }

    public void setMa30(double ma30) {
        this.ma30 = ma30;
    }

    public double getMa60() {
        return ma60;
    }

    public void setMa60(double ma60) {
        this.ma60 = ma60;
    }

    public double getMa120() {
        return ma120;
    }

    public void setMa120(double ma120) {
        this.ma120 = ma120;
    }

    public double getMa250() {
        return ma250;
    }

    public void setMa250(double ma250) {
        this.ma250 = ma250;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return "StockMAInfo{" +
                "id=" + id +
                ", code=" + code +
                ", type=" + type +
                ", close=" + close +
                ", ma5=" + ma5 +
                ", ma10=" + ma10 +
                ", ma20=" + ma20 +
                ", ma30=" + ma30 +
                ", ma60=" + ma60 +
                ", ma120=" + ma120 +
                ", ma250=" + ma250 +
                ", txDate=" + txDate +
                ", recordTime=" + recordTime +
                '}';
    }
}
