package org.stock.common.constant;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by gyang on 15-9-7.
 */
public final class Constant {

    /**
     * 股票有效数据开始日期
     */
    public static final String VALID_DATE_START_STR = "1994-01-03";

    /**
     * 股票有效数据开始日期
     */
    public final Date VALID_DATE_START = new Date(1994, 0, 3);

    private Constant() {

    }

    static {

    }

    public static void main(String[] args) {
        System.out.println();
    }
}
