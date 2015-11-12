package org.stock.db.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.LineReader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.stock.db.bean.StockBaseInfo;
import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.common.StockType;
import org.stock.db.util.SpringService;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 保存股票每日交易信息
 */
public class TestStockExchangeInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestStockExchangeInfoService.class);

    private IStockExchangeInfoService service = (IStockExchangeInfoService) SpringService.getInstance().getService("stockExchangeInfoService");
    private IStockBaseInfoService service2 = (IStockBaseInfoService) SpringService.getInstance().getService("stockBaseInfoService");

    private static final ExecutorService ES = Executors.newFixedThreadPool(10);

    private static final AtomicInteger ato = new AtomicInteger();

    private static final Map<Integer, StockBaseInfo> STOCK_BASE_INFO_MAP = Maps.newHashMap();

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00#");

    private static final String START_FORMATTER = "quotebridge_v2_line_hs_%s_00_2015";

    public static void main(String[] args) throws IOException, ParseException {
        new TestStockExchangeInfoService().testSave();
    }

	public void testSave() throws IOException, ParseException {
        List<StockBaseInfo> stockBaseInfos = service2.selectAllStockBaseInfoList();
        for(StockBaseInfo stockBaseInfo : stockBaseInfos) {
            STOCK_BASE_INFO_MAP.put(stockBaseInfo.getCode(), stockBaseInfo);
        }

        File[] files = getFiles();
        LOGGER.info("Total file count: {}", files.length);
        for(final File f : files) {
            ES.submit(new Runnable() {
                @Override
                public void run() {
                    int index = ato.incrementAndGet();
                    LOGGER.info("{}: process {}", index, f.getAbsolutePath());
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    process(f);
                    stopWatch.stop();
                    LOGGER.info("{}: process {} finished, time = {}", new Object[]{index, f.getAbsolutePath(), stopWatch.getTime()});
                }
            });
        }

        ES.shutdown();
	}

    private void process(File file) {
        List<StockExchangeInfo> infos = Lists.newArrayList();
        String[] vals;
        String[] stockVals;
        StockExchangeInfo info;

        int code = Integer.valueOf(StringUtils.left(file.getName(), 6)).intValue();
        StockBaseInfo stockBaseInfo = STOCK_BASE_INFO_MAP.get(code);
        if(stockBaseInfo == null) {
            LOGGER.error("Cannot find stock base info record!!!, file = " + file.getName(), new Exception("Cannot find stock base info record!!!"));
            return;
        }
        stockBaseInfo.setType(StockType.SZ.getValue());
        String endStrPrefix = String.format(START_FORMATTER, StringUtils.left(file.getName(), 6));
        boolean isEnd = false;

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            LineReader lineReader = new LineReader(fileReader);
            String line = null;
            double lastClose = 0.0; // 上一个交易日收盘价格
            while ((line = lineReader.readLine()) != null) {
                if(StringUtils.isNotEmpty(line)) {
                    if(StringUtils.startsWith(line, endStrPrefix)) {
                        isEnd = true;
                    }

                    String content = StringUtils.substringBetween(line, ":\"", "\"}");
                    vals = content.split(";");

                    for(String val : vals) {
                        if(StringUtils.isNotEmpty(val)) {
                            stockVals = val.split(",");
                            if(stockVals.length < 5) {
                                LOGGER.warn("stockVals.length < 5, val = {}", val);
                            }

                            try {
                                info = new StockExchangeInfo();
                                info.setCode(stockBaseInfo.getCode());  // code
                                info.setType(stockBaseInfo.getType());  // type
                                if(StringUtils.isEmpty(stockVals[1])) {
                                    info.setOpen(Double.valueOf(stockVals[2])); // open
                                } else {
                                    info.setOpen(Double.valueOf(stockVals[1])); // open
                                }
                                info.setHight(Double.valueOf(stockVals[2]));    // high
                                info.setLow(Double.valueOf(stockVals[3]));  // low
                                info.setClose(Double.valueOf(stockVals[4])); // close

                                try {
                                    if(stockVals.length >5) {
                                        info.setVolume(Long.valueOf(stockVals[5])); // 量
                                    }
                                } catch (Exception e) {
                                    info.setVolume(0L);
                                    LOGGER.error("volume is invalid, name = {}, val = {}", file.getName(), val);
                                }

                                try {
                                    if(stockVals.length > 7) {
                                        info.setExchRate(Double.valueOf(stockVals[7])); // 换手率
                                    }
                                } catch (Exception e) {
                                    info.setExchRate(0.0);
                                    LOGGER.error("exch_rate is invalid, name = {}, val = {}", file.getName(), val);
                                }

                                info.setTxDate(DateUtils.parseDate(stockVals[0], new String[]{"yyyyMMdd"}));    // tx_date

                                // 涨跌幅
                                double priceChange = 0.0;
                                // 振幅
                                double totalPriceChange = 0.0;
                                if(lastClose != 0.0) {
                                    priceChange = Double.valueOf(DECIMAL_FORMAT.format((info.getClose() - lastClose) / lastClose * 100.0));
                                    totalPriceChange = Double.valueOf(DECIMAL_FORMAT.format((info.getHight() - info.getLow()) / lastClose * 100.0));
                                }
                                info.setPriceChange(priceChange);
                                info.setTotalPriceChange(totalPriceChange);

                                infos.add(info);

                                lastClose = info.getClose();
                            } catch (Exception e) {
                                LOGGER.error("Error!!! file name = " + file.getName() + ", val = " + val, e);
                            }
                        }
                    }
                }

                if(infos.size() != 0 && infos.size() % 10000 == 0) {
                    save(infos);
                    infos = Lists.newArrayList();
                }

                if(isEnd) {
                    LOGGER.info("Break, file name = {}", file.getName());
                    break;
                }
            }

            if(!CollectionUtils.isEmpty(infos)) {
                save(infos);
            }
        } catch (Exception e) {
            LOGGER.error("Process file error, file = " + file.getName(), e);
            e.printStackTrace();
        } finally {
            if(fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<String> duplicateCodes() {
        List<String> codes = Lists.newArrayList();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("/home/gyang/stock_data/duplicate.txt");
            LineReader lineReader = new LineReader(fileReader);
            String line = null;
            while((line = lineReader.readLine()) != null) {
                String[] vals = line.split(",");
                for(String val : vals) {
                    if(StringUtils.isNotEmpty(val)) {
                        codes.add(StringUtils.trim(val));
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return codes;
    }

    private File[] getFiles() {
        final List<String> duplicateCodes = duplicateCodes();

        File dir = new File("/home/gyang/stock_data/exchange_data");
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(StringUtils.equals(StringUtils.right(name, 4), ".txt")) {
                    return true;
                }

                return false;
            }
        });

        return files;
    }
	
	private void save(List<StockExchangeInfo> infos) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            service.saveStockExchangeInfoList(infos);
        } catch (Exception e) {
            LOGGER.error("Save db error.", e);
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println("Save consume time: " + stopWatch.getTime());
    }
}
