package org.stock.fetch.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * 从上交所获取股票信息
 *
 * Created by gyang on 15-9-1.
 */
public class Test02 {

    @Test
    public void t01() throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(1000)
                .build();

        int randomInt = (int) (Math.floor(Math.random() * (100000 + 1)));
        long time = new Date().getTime();
        String url = "http://query.sse.com.cn/security/fund/queryMonthQuat.do?jsonCallBack=jsonpCallback" + randomInt + "&productId=600004&inYear=2014&prodType=1&_=" + time;
        URI uri = new URI(url);
        HttpGet httpget = new HttpGet(uri);
        httpget.setConfig(requestConfig);
        httpget.setHeader("Accept", "*/*");
        httpget.setHeader("Accept-Encoding", "gzip,deflate,sdch");
        httpget.setHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4");
        httpget.setHeader("Referer", "http://www.sse.com.cn/assortment/stock/list/stockdetails/turnover/turnovermonth_index.shtml?COMPANY_CODE=600004&inYear=2014");
        httpget.setHeader("Host", "query.sse.com.cn");

        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity entity1 = response.getEntity();
        System.out.println(EntityUtils.toString(entity1));
        response.close();
    }

    @Test
    public void t2() {
        System.out.println((int) Math.floor(Math.random() * (100000 + 1)));
        System.out.println(new Date().getTime());
    }
}