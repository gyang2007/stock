package org.stock.fetch.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.LineReader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stock.fetch.bean.StockExchangeData;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 从同花顺获取股票每日的交易信息
 *
 * Created by gyang on 15-9-3.
 */
public class FetchStockExchangeServiceImpl extends AbstractFetchStockExchange {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchStockExchangeServiceImpl.class);

    // eg: http://d.10jqka.com.cn/v2/line/hs_600868/00/2015.js
    private static final String URL_FORMATTER = "http://d.10jqka.com.cn/v2/line/hs_%s/00/%s.js";

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00#");

    private static final Set<String> DUPLICATE_SET = Sets.newHashSet();

    private static final int START_YEAR = 1991;
    private static final int END_YEAR = 2015;

    private int index = 1;

    @Override
    public void process() {
        String line = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("/home/gyang/stock_data/stockBaseInfo.txt");
            LineReader lineReader = new LineReader(fileReader);
            while((line = lineReader.readLine()) != null) {
                LOGGER.info(index++ + ": Fetch line info: {}", line);
                String[] vals = line.split(",");
                if(vals.length < 3) {
                    LOGGER.warn("Lenght < 3, line = {}", line);
                    continue;
                }

                // 股票代码
                String code = vals[0];
                String name = vals[1];
                int type = Integer.valueOf(vals[2]);

                if(DUPLICATE_SET.contains(code)) {
                    LOGGER.warn("Duplicate code, code = {}", code);
                }
                DUPLICATE_SET.add(code);

                StringBuilder sb = new StringBuilder();
                for(int year = START_YEAR; year <= END_YEAR; year++) {
                    String httpUrl = String.format(URL_FORMATTER, code, year);
                    String httpResult = httpGet(httpUrl);
                    sb.append(httpResult + "\n");
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                writeFile(code, sb.toString());
            }
        } catch (IOException e) {
            LOGGER.error("Fetch error, line = " + line, e);
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

    @Override
    public List<StockExchangeData> pullStockExchangeDatas(int code, int type, int year) {
        List<StockExchangeData> infos = Lists.newArrayList();
        String codeStr = StringUtils.leftPad(String.valueOf(code), 6, '0');
        String httpUrl = String.format(URL_FORMATTER, codeStr, year);
        String httpResult = httpGet(httpUrl);
        if(StringUtils.isNotEmpty(httpResult)) {
            String content = StringUtils.substringBetween(httpResult, ":\"", "\"}");
            String[] vals = content.split(";");
            String[] stockVals;
            StockExchangeData info;
            for(String val : vals) {
                if(StringUtils.isNotEmpty(val)) {
                    stockVals = val.split(",");
                    if(stockVals.length < 5) {
                        LOGGER.warn("stockVals.length < 5, val = {}", val);
                    }

                    try {
                        info = new StockExchangeData();
                        infos.add(info);

                        info.setCode(code);  // code
                        info.setType(type);  // type
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
                            LOGGER.error("volume is invalid, code = {}, type = {}, val = {}", new Object[]{code, type, val});
                        }

                        try {
                            if(stockVals.length > 7) {
                                info.setExchRate(Double.valueOf(stockVals[7])); // 换手率
                            }
                        } catch (Exception e) {
                            info.setExchRate(0.0);
                            LOGGER.error("exch_rate is invalid, code = {}, type = {}, val = {}", new Object[]{code, type, val});
                        }

                        info.setTxDate(DateUtils.parseDate(stockVals[0], new String[]{"yyyyMMdd"}));    // tx_date
                    } catch (Exception e) {
                        LOGGER.error("Error!!! code = " + code + ", type = " + type + ", val = " + val, e);
                    }
                }
            }
        }

        return infos;
    }

    private String httpGet(String httpUrl) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(httpUrl);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            LOGGER.error("Http get error, httpUrl = " + httpUrl, e);
            e.printStackTrace();
        } finally {
            try {
                if(httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return StringUtils.EMPTY;
    }

    private void writeFile(String code, String data) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("/home/gyang/stock/" + code + ".txt", true);
            fileWriter.write(data);
        } catch (IOException e) {
            LOGGER.error("Write file error, code = " + code, e);
            e.printStackTrace();
        } finally {
            if(fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        new FetchStockExchangeServiceImpl().process();
        List<StockExchangeData> lists = new FetchStockExchangeServiceImpl().pullStockExchangeDatas(651, 20, 2015);
        System.out.println(lists);
    }
}