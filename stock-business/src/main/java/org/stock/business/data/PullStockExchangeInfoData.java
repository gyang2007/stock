package org.stock.business.data;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.common.bean.StockBaseInfo;
import org.stock.common.bean.StockExchangeInfo;
import org.stock.common.constant.Constant;
import org.stock.common.util.DateUtil;
import org.stock.db.common.StockType;
import org.stock.db.service.IStockBaseInfoService;
import org.stock.db.service.IStockExchangeInfoService;
import org.stock.db.util.SpringService;
import org.stock.fetch.bean.StockExchangeData;
import org.stock.fetch.service.IFetchStockExchangeService;
import org.stock.fetch.service.impl.FetchStockExchangeServiceImpl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 补充数据库中每日交易信息
 */
public class PullStockExchangeInfoData {
	private static final Logger LOGGER = LoggerFactory.getLogger(PullStockExchangeInfoData.class);

    // 格式化小数
    private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00#");

	private final IStockBaseInfoService stockBaseInfoService = (IStockBaseInfoService) SpringService
			.getInstance().getService("stockBaseInfoService");
	private final IStockExchangeInfoService exchangeInfoService = (IStockExchangeInfoService) SpringService
			.getInstance().getService("stockExchangeInfoService");

    private final IFetchStockExchangeService fetchStockExchangeService = new FetchStockExchangeServiceImpl();

    // 按照交易时间升叙排列
    private final Ordering<StockExchangeData> TX_DATE_ORDERING_DESC = Ordering.natural().onResultOf(new Function<StockExchangeData, Comparable>() {
        @Override
        public Comparable apply(StockExchangeData stockExchangeData) {
            if(stockExchangeData.getTxDate() == null) {
                LOGGER.error("Invalid txDate! code = {}, type = {}", new Object[]{stockExchangeData.getCode(), stockExchangeData.getType()});
            }

            return stockExchangeData.getTxDate();
        }
    });

	public PullStockExchangeInfoData() {
		
	}

    public void pull(StockBaseInfo stockBaseInfo) {
        pull(Lists.newArrayList(stockBaseInfo));
    }
	
	public void pull(List<StockBaseInfo> stockBaseInfos) {
        StockExchangeInfo stockExchangeInfoInDb;
        List<StockExchangeData> stockExchangeDatas;
        // 获取股票交易数据的开始日期
        Date startDate;
        // 获取最近的股票交易日期
        Date lastestExchDate = DateUtil.getLastestExchDate();
        int lastestExchDateIntValue = Integer.valueOf(DateFormatUtils.format(lastestExchDate, "yyyyMMdd")).intValue();
        LOGGER.info("The lastest exchange date = {}, int value: {}", new Object[]{DateFormatUtils.format(lastestExchDate, "yyyy-MM-dd"), lastestExchDateIntValue});
        double lastClose = 0.0; // 上一个交易日收盘价格
        List<StockExchangeInfo> stockExchangeInfos;
        StockExchangeInfo stockExchangeInfo;
        StopWatch stopWatch;
		for(StockBaseInfo stockBaseInfo : stockBaseInfos) {
            stopWatch = new StopWatch();
            stopWatch.start();
            LOGGER.info("Start pull exchange data, code = {}, type = {}", new Object[]{stockBaseInfo.getCode(), stockBaseInfo.getType()});
            stockExchangeDatas = Lists.newArrayList();
            stockExchangeInfos = Lists.newArrayList();
            lastClose = 0.0;
            startDate = Constant.VALID_DATE_START;

            stockExchangeInfoInDb = exchangeInfoService.selectByCodeTypeFomMaxDate(stockBaseInfo);
            // 如果数据库中有交易记录，则计算上一交易日收盘价和将要获取交易数据的最小交易日期
            if(stockExchangeInfoInDb != null) {
                // 上一交易日收盘价
                lastClose = stockExchangeInfoInDb.getClose();
                // 获取股票交易数据的开始日期 startDate
                startDate = DateUtil.getNextExchDate(stockExchangeInfoInDb.getTxDate());
            }


            int tmpDateValue = Integer.valueOf(DateFormatUtils.format(startDate, "yyyyMMdd")).intValue();
            // 获取股票数据的开始日期大于最近一个股票交易日，则不获取最新数据
            if(tmpDateValue > lastestExchDateIntValue) {
                LOGGER.info("The stock has the newest exchange data, code = {}, type = {}", new Object[]{stockBaseInfo.getCode(), stockBaseInfo.getType()});
                continue;
            }
            // 获取股票数据的开始日期等于最近一个股票交易日，则获取最新一天的交易数据
            else if(tmpDateValue == lastestExchDateIntValue) {
                StockExchangeData tmpData = pullToday(stockBaseInfo);
                if(tmpData != null) {
                    stockExchangeDatas.add(tmpData);
                }
            } else {
                stockExchangeDatas = pull(stockBaseInfo, stockExchangeInfoInDb);
                stockExchangeDatas = TX_DATE_ORDERING_DESC.sortedCopy(stockExchangeDatas);
            }

            stockExchangeDatas = filter(stockExchangeDatas, startDate);
            LOGGER.info("Pull stockExchangeDatas count: {}, code = {}, type = {}, startDate = {}", new Object[]{stockExchangeDatas.size(), stockBaseInfo.getCode(), stockBaseInfo.getType(), DateFormatUtils.format(startDate, "yyyy-MM-dd")});
            if(CollectionUtils.isNotEmpty(stockExchangeDatas)) {
                for(StockExchangeData stockExchangeData : stockExchangeDatas) {
                    stockExchangeInfo = new StockExchangeInfo();
                    stockExchangeInfos.add(stockExchangeInfo);
                    stockExchangeInfo.setCode(stockBaseInfo.getCode());
                    stockExchangeInfo.setType(stockBaseInfo.getType());
                    stockExchangeInfo.setOpen(stockExchangeData.getOpen()); // open
                    stockExchangeInfo.setHight(stockExchangeData.getHight());    // high
                    stockExchangeInfo.setLow(stockExchangeData.getLow());  // low
                    stockExchangeInfo.setClose(stockExchangeData.getClose()); // close
                    stockExchangeInfo.setVolume(stockExchangeData.getVolume());
                    stockExchangeInfo.setExchRate(stockExchangeData.getExchRate());
                    stockExchangeInfo.setTxDate(stockExchangeData.getTxDate());
                    // 涨跌幅
                    double priceChange = 0.0;
                    // 振幅
                    double totalPriceChange = 0.0;
                    if(lastClose != 0.0) {
                        priceChange = Double.valueOf(DECIMAL_FORMAT.format((stockExchangeData.getClose() - lastClose) / lastClose * 100.0));
                        totalPriceChange = Double.valueOf(DECIMAL_FORMAT.format((stockExchangeData.getHight() - stockExchangeData.getLow()) / lastClose * 100.0));
                    }
                    stockExchangeInfo.setPriceChange(priceChange);
                    stockExchangeInfo.setTotalPriceChange(totalPriceChange);

                    lastClose = stockExchangeData.getClose();

                    if(stockExchangeInfos.size() != 0 && stockExchangeInfos.size() % 10000 == 0) {
                        save(stockExchangeInfos);
                        LOGGER.info("Save stockExchangeInfos count: {}, code = {}, type = {}", new Object[]{stockExchangeInfos.size(), stockBaseInfo.getCode(), stockBaseInfo.getType()});
                        stockExchangeInfos = Lists.newArrayList();
                    }
                }

                if(!CollectionUtils.isEmpty(stockExchangeInfos)) {
                    LOGGER.info("Save stockExchangeInfos count: {}, code = {}, type = {}", new Object[]{stockExchangeInfos.size(), stockBaseInfo.getCode(), stockBaseInfo.getType()});
                    save(stockExchangeInfos);
                }

                stopWatch.stop();
                LOGGER.info("Pull the stock exchange data success, code = {}, type = {}, consume time = {}", new Object[]{stockBaseInfo.getCode(), stockBaseInfo.getType(), stopWatch.getTime()});
            }
        }
	}

    /**
     * 获取交易数据。如果stockExchangeInfo == null，则从默认初始日期获取数据。
     * @param stockBaseInfo
     * @param stockExchangeInfo
     * @return
     */
    private List<StockExchangeData> pull(StockBaseInfo stockBaseInfo, StockExchangeInfo stockExchangeInfo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int maxYear = calendar.get(Calendar.YEAR);

        calendar.setTime(Constant.VALID_DATE_START);
        int startYear = calendar.get(Calendar.YEAR);
        if(stockExchangeInfo != null && stockExchangeInfo.getTxDate() != null) {
            calendar.setTime(stockExchangeInfo.getTxDate());
            startYear = calendar.get(Calendar.YEAR);
        }
        int yearTmp = startYear;
        List<StockExchangeData> stockExchangeDatas = Lists.newArrayList();
        while(yearTmp <= maxYear) {
            stockExchangeDatas.addAll(fetchStockExchangeService.pullStockExchangeDatas(stockBaseInfo.getCode(), stockBaseInfo.getType(), yearTmp));
            yearTmp++;
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return stockExchangeDatas;
    }

    private StockExchangeData pullToday(StockBaseInfo stockBaseInfo) {
        return fetchStockExchangeService.pullStockExchangeDataToday(stockBaseInfo.getCode(), stockBaseInfo.getType());
    }

    private void save(List<StockExchangeInfo> infos) {
        try {
            exchangeInfoService.saveStockExchangeInfoList(infos);
        } catch (Exception e) {
            LOGGER.error("Save db error.", e);
            e.printStackTrace();
        }
    }

    /**
     * 根据获取股票交易数据的开始日期过滤交易数据
     *
     * @param stockExchangeDatas
     * @param startDate
     * @return
     */
    private List<StockExchangeData> filter(List<StockExchangeData> stockExchangeDatas, final Date startDate) {
        return Lists.newArrayList(Collections2.filter(stockExchangeDatas, new Predicate<StockExchangeData>() {
            @Override
            public boolean apply(StockExchangeData stockExchangeData) {
                // 交易日期等于或大于开始日期
                return stockExchangeData.getTxDate().equals(startDate) || stockExchangeData.getTxDate().after(startDate);
            }
        }));
    }

	public static void main(String[] args) throws ParseException {
        processSingleThread();
//        processMultiThread();
    }

    private static void processSingleThread() {
        PullStockExchangeInfoData pull = new PullStockExchangeInfoData();
        List<StockBaseInfo> stockBaseInfos = pull.stockBaseInfoService.selectStockBaseInfoListByType(StockType.STOCK);
        Ordering<StockBaseInfo> ordering = Ordering.natural().onResultOf(new Function<StockBaseInfo, Comparable>() {
            @Override
            public Comparable apply(StockBaseInfo stockBaseInfo) {
                return stockBaseInfo.getCode();
            }
        });
        stockBaseInfos = ordering.sortedCopy(stockBaseInfos);

/*        for(StockBaseInfo stockBaseInfo : stockBaseInfos) {
            if(stockBaseInfo.getCode() > 2322) {
                pull.pull(stockBaseInfo);
            }
        }*/

        pull.pull(stockBaseInfos);
    }

    // 开启过多线程导致HTTP请求获取异常结果
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);
    private static void processMultiThread() {
        PullStockExchangeInfoData pull = new PullStockExchangeInfoData();
        List<StockBaseInfo> stockBaseInfos = pull.stockBaseInfoService.selectAllStockBaseInfoList();
        final Set<String> codeStrSet = Sets.newHashSet();
        for(int code : Constant.STOCK_CODE_UNIQUE_ARR) {
            codeStrSet.add(String.valueOf(code));
        }
        stockBaseInfos = Lists.newArrayList(Collections2.filter(stockBaseInfos, new Predicate<StockBaseInfo>() {
            @Override
            public boolean apply(StockBaseInfo stockBaseInfo) {
                return codeStrSet.contains(String.valueOf(stockBaseInfo.getCode()));
            }
        }));

        for(final StockBaseInfo stockBaseInfo : stockBaseInfos) {
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    final PullStockExchangeInfoData pullStockExchangeInfoData = new PullStockExchangeInfoData();
                    pullStockExchangeInfoData.pull(stockBaseInfo);
                }
            });
        }
        EXECUTOR_SERVICE.shutdown();
    }
}
