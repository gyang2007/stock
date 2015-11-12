package org.stock.db.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 补充数据库中每日交易信息
 */
public class PullStockExchangeInfoData {
	private static final Logger LOGGER = LoggerFactory.getLogger(PullStockExchangeInfoData.class);

	private final IStockBaseInfoService baseInfoService = (IStockBaseInfoService) SpringService
			.getInstance().getService("stockBaseInfoService");
	private final IStockExchangeInfoService exchangeInfoService = (IStockExchangeInfoService) SpringService
			.getInstance().getService("stockExchangeInfoService");

	// 1990-12-19 00:00:00 ----> 661536000000
	
	private static final Date INIT_DATE = new Date(661536000000L);
	private static final int TIME_GAP = 1 * 24 * 60 * 60 * 1000;

	public PullStockExchangeInfoData() {
		
	}
	
	public void pull() {
		String nowTxDate = formatterTxDate(new Date());
		List<StockExchangeInfo> stockExchangeInfos = exchangeInfoService.selectMaxDate();
		int count = 0;
		StockExchangeInfo stockExchangeInfo;
		for(Iterator<StockExchangeInfo> iter = stockExchangeInfos.iterator(); iter.hasNext(); ) {
			stockExchangeInfo = iter.next();
			System.out.println(count++);
			LOGGER.info("Pull code={}, type={}, txDate={} exchange info.", new Object[]{stockExchangeInfo.getCode(), stockExchangeInfo.getType(), DateFormatUtils.format(stockExchangeInfo.getTxDate(), "yyyy-MM-dd")});
			if(StringUtils.endsWith(nowTxDate, formatterTxDate(stockExchangeInfo.getTxDate()))) {
				LOGGER.info("The newest tx_date, code = {}, type = {}, txDate = {}", new Object[]{stockExchangeInfo.getCode(), stockExchangeInfo.getType(), DateFormatUtils.format(stockExchangeInfo.getTxDate(), "yyyy-MM-dd")});
				continue;
			}
			else {
				StockBaseInfo baseInfo = new StockBaseInfo();
				baseInfo.setCode(stockExchangeInfo.getCode());
				baseInfo.setType(stockExchangeInfo.getType());
				pull(baseInfo, DateUtils.addDays(stockExchangeInfo.getTxDate(), 1));
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					LOGGER.error("", e);
				}
			}
		}
		
		// stock_base_info与stock_exchange_info数据表差集
		Set<String> codeTypeSet = new HashSet<String>();
		for(Iterator<StockExchangeInfo> iter = stockExchangeInfos.iterator(); iter.hasNext(); ) {
			stockExchangeInfo = iter.next();
			codeTypeSet.add(stockExchangeInfo.getCode() + ":" + stockExchangeInfo.getType());
		}
		List<StockBaseInfo> baseInfos = baseInfoService.selectAllStockBaseInfoList();
		StockBaseInfo stockBaseInfo;
		for(Iterator<StockBaseInfo> iter = baseInfos.iterator(); iter.hasNext(); ) {
			stockBaseInfo = iter.next();
			if(!codeTypeSet.contains(stockBaseInfo.getCode() + ":" + stockBaseInfo.getType())) {
				pull(stockBaseInfo, INIT_DATE);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					LOGGER.error("", e);
				}
			}
		}
		
/*		List<StockBaseInfo> baseInfos = baseInfoService.selectAllStockBaseInfoList();
		int count = 1;
		for (StockBaseInfo baseInfo : baseInfos) {
			System.out.println(count++);
			LOGGER.info("Pull {} exchange info.", new Object[]{baseInfo});
			StockExchangeInfo exchangeInfo = exchangeInfoService.selectByCodeFomMaxDate(baseInfo);
			if (exchangeInfo == null) {
				pull(baseInfo, INIT_DATE);
			} else if(exchangeInfo.getRecordTime().getTime() >= 1421164800000L) {
				continue;
			} else {
				pull(baseInfo, exchangeInfo.getRecordTime());
			}
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				LOGGER.error("", e);
			}
		}*/

	}
	
	public void pull(List<StockBaseInfo> baseInfos) {
		
	}

	private void pull(StockBaseInfo baseInfo, Date startDate) {
		List<StockExchangeInfo> exchangeInfos = new ArrayList<StockExchangeInfo>();
		
		Map<String, String> params = new HashMap<String, String>();
//		params.put("symbol", "SZ002440");
		params.put("symbol", StockType.getByValue(baseInfo.getType()).getLabel1() + formatCode(baseInfo.getCode()));
		params.put("period", "1day");
		params.put("type", "normal");
//		params.put("begin", "1389533667448");
//		params.put("end", "1421069667448");
		
/*		FetchStockExchangeInfo fetch = new FetchStockExchangeInfo();
		
		Date sd = startDate;
		Date ed = getNextDate(sd);
		while(ed.after(sd)) {
			params.put("begin", String.valueOf(sd.getTime()));
			params.put("end", String.valueOf(ed.getTime()));
			
			String content = null;
			try {
				content = fetch.fetchFromXueqiu(params);
				if(!StringUtils.isBlank(content)) {
					exchangeInfos.addAll(parse(baseInfo, content));
				}
			} catch (IOException e) {
				LOGGER.error("", e);
				
				break;
			}
			
			sd = getStartDate(ed);
			ed = getNextDate(sd);
			
			try {
				TimeUnit.MILLISECONDS.sleep(1 * 10);
			} catch (InterruptedException e) {
				LOGGER.error("", e);
			}
		}*/
		
		if(exchangeInfos.size() > 0) {
			LOGGER.info("Save stock exchange info, code={}, type={}, {} records in total.", new Object[]{baseInfo.getCode(), baseInfo.getType(), exchangeInfos.size()});
			exchangeInfoService.saveStockExchangeInfoList(exchangeInfos);
		}
	}
	
	// {"chartlist":null,"success":"true","stock":{"symbol":"SH900957"}}
	
/*    {
        "volume": 40140684,
        "open": 23,
        "high": 23.29,
        "close": 22.73,
        "low": 22.05,
        "chg": -0.09,
        "percent": -0.39,
        "turnrate": 9.46,
        "ma5": 21.83,
        "ma10": 20.09,
        "ma20": 19.09,
        "ma30": 18.61,
        "dif": 1.14,
        "dea": 0.65,
        "macd": 0.99,
        "time": "Wed Jan 14 00:00:00 +0800 2015"
    }*/
	private List<StockExchangeInfo> parse(StockBaseInfo baseInfo, String value) {
		List<StockExchangeInfo> infos = new ArrayList<StockExchangeInfo>();
		StockExchangeInfo info;
		
		JSONObject jsonObject = JSON.parseObject(value);
		JSONArray chartList;
		Iterator<Object> iter;
		JSONObject exchangeJsonValue;
		if(jsonObject.containsKey("success") && "true".equals(jsonObject.getString("success")) && jsonObject.containsKey("chartlist")) {
			chartList = jsonObject.getJSONArray("chartlist");
			if(chartList == null){
				return infos;
			}
			
			iter = chartList.iterator();
			while(iter.hasNext()) {
				exchangeJsonValue = (JSONObject) iter.next();
				
				info = new StockExchangeInfo();
				info.setCode(baseInfo.getCode());
				info.setType(baseInfo.getType());
				info.setOpen(exchangeJsonValue.getDoubleValue("open"));
				info.setHight(exchangeJsonValue.getDoubleValue("high"));
				info.setLow(exchangeJsonValue.getDoubleValue("low"));
				info.setClose(exchangeJsonValue.getDoubleValue("close"));
				info.setVolume(exchangeJsonValue.getLongValue("volume"));
				try {
					info.setRecordTime(parseDate(exchangeJsonValue.getString("time")));
				} catch (ParseException e) {
					info.setRecordTime(INIT_DATE);
				}
				
				infos.add(info);
			}
		}
		
		return infos;
	}
	
	private Date getStartDate(Date sd) {
		return DateUtils.addDays(sd, 1);
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
	
	private String formatCode(int value) {
		String v = String.valueOf(1000000 + value);
		return v.substring(1);
	}
	
	private Date parseDate(String dateTime) throws ParseException {
		try {
			return DateUtils.parseDate(dateTime, new String[]{"E MMM dd hh:mm:ss z yyyy"});
		} catch (ParseException e) {
			LOGGER.error("", e);
			
			throw e;
		}
	}

	public static void main(String[] args) throws ParseException {
		PullStockExchangeInfoData p = new PullStockExchangeInfoData();
		p.pull();
		
//		System.out.println(DateUtils.parseDate("2015-01-14 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}).getTime());
//		System.out.println(DateFormatUtils.format(new Date(1421164800000L), "yyyy-MM-dd HH:mm:ss"));
		
//		new PullStockExchangeInfoData().test1();
	}
	
	private String formatterTxDate(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}
}