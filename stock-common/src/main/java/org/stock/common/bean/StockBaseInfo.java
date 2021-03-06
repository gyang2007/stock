package org.stock.common.bean;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class StockBaseInfo implements Serializable {

    private static final long serialVersionUID = 697731234386234266L;

    private int id;
    private int code;
    private String name;
    private int type;
    private String descp = StringUtils.EMPTY;
    private Date recordTime = new Date();

    public StockBaseInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return "StockBaseInfo [code=" + code + ", name=" + name + ", type="
                + type + "]";
    }
}
