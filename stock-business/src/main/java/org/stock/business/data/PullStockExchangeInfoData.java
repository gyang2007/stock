package org.stock.business.data;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.db.bean.StockBaseInfo;
import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.common.StockType;
import org.stock.db.service.IStockBaseInfoService;
import org.stock.db.service.IStockExchangeInfoService;
import org.stock.db.util.SpringService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.stock.fetch.bean.StockExchangeData;
import org.stock.fetch.service.IFetchStockExchangeService;
import org.stock.fetch.service.impl.FetchStockExchangeServiceImpl;

/**
 * 补充数据库中每日交易信息
 */
public class PullStockExchangeInfoData {
	private static final Logger LOGGER = LoggerFactory.getLogger(PullStockExchangeInfoData.class);

	private final IStockBaseInfoService baseInfoService = (IStockBaseInfoService) SpringService
			.getInstance().getService("stockBaseInfoService");
	private final IStockExchangeInfoService exchangeInfoService = (IStockExchangeInfoService) SpringService
			.getInstance().getService("stockExchangeInfoService");

    private final IFetchStockExchangeService fetchStockExchangeService = new FetchStockExchangeServiceImpl();

    // 按照交易时间升叙排列
    private static final Ordering<StockExchangeData> TX_DATE_ORDERING_DESC = Ordering.natural().onResultOf(new Function<StockExchangeData, Comparable>() {
        @Override
        public Comparable apply(StockExchangeData stockExchangeData) {
            if(stockExchangeData.getTxDate() == null) {
                LOGGER.error("Invalid txDate! code = {}, type = {}", new Object[]{stockExchangeData.getCode(), stockExchangeData.getType()});
            }

            return stockExchangeData.getTxDate();
        }
    });

	// 1990-12-19 00:00:00 ----> 661536000000
//	private static final Date INIT_DATE = new Date(661536000000L);

    // 1995-1-1 ----> 788889600000
	private static final Date INIT_DATE = new Date(788889600000L);
	private static final int TIME_GAP = 1 * 24 * 60 * 60 * 1000;

	public PullStockExchangeInfoData() {
		
	}
	
	public void pull() {
		String nowTxDate = formatterTxDate(new Date());
		List<StockExchangeInfo> stockExchangeInfos = exchangeInfoService.selectMaxDate();
		int count = 0;
		StockExchangeInfo stockExchangeInfo;
	}

    public void pull(StockBaseInfo stockBaseInfo) {
        pull(Lists.newArrayList(stockBaseInfo));
    }
	
	public void pull(List<StockBaseInfo> stockBaseInfos) {
        StockExchangeInfo stockExchangeInfoInDb;
        List<StockExchangeData> stockExchangeDatas;
		for(StockBaseInfo stockBaseInfo : stockBaseInfos) {
            stockExchangeInfoInDb = exchangeInfoService.selectByCodeTypeFomMaxDate(stockBaseInfo);
            stockExchangeDatas = pull(stockBaseInfo, stockExchangeInfoInDb);
            stockExchangeDatas = TX_DATE_ORDERING_DESC.sortedCopy(stockExchangeDatas);
        }
	}

    private List<StockExchangeData> pull(StockBaseInfo stockBaseInfo, StockExchangeInfo stockExchangeInfo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int maxYear = calendar.get(Calendar.YEAR);

        calendar.setTime(INIT_DATE);
        int startYear = calendar.get(Calendar.YEAR);
        if(stockExchangeInfo != null && stockExchangeInfo.getTxDate() != null) {
            calendar.setTime(stockExchangeInfo.getTxDate());
            startYear = calendar.get(Calendar.YEAR);
        }
        int yearTmp = startYear;
        List<StockExchangeData> stockExchangeDatas = Lists.newArrayList();
        while(yearTmp <= maxYear) {
            stockExchangeDatas.addAll(fetchStockExchangeService.pullStockExchangeDatas(stockBaseInfo.getCode(), stockBaseInfo.getType(), startYear));
            yearTmp++;
        }

        return stockExchangeDatas;
    }

	private Date getNextDate(Date sd) {
		Date nd = DateUtils.addYears(sd, 1);
		Date cd = new Date(System.currentTimeMillis());
		if(nd.getTime() > cd.getTime()) {
			// 当前时间在15点之后
			if(cd.after(DateUtils.setHours(cd, 15))) {
				Date d = DateUtils.addDays(cd, 1);
				return DateUtils.setHours(d, 0);
			}
			else {
				return DateUtils.setHours(cd, 0);
			}
		}

		return DateUtils.addYears(sd, 1);
	}

    private String formatterTxDate(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

	public static void main(String[] args) throws ParseException {
        StockBaseInfo stockBaseInfo = new StockBaseInfo();
        stockBaseInfo.setCode(572);
        stockBaseInfo.setType(20);
		PullStockExchangeInfoData p = new PullStockExchangeInfoData();
        p.pull(Lists.newArrayList(stockBaseInfo));
    }
	

}
