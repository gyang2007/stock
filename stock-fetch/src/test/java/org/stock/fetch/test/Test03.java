package org.stock.fetch.test;

import com.google.common.collect.Sets;
import com.google.common.io.LineReader;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Set;

/**
 * Created by gyang on 15-9-1.
 */
public class Test03 {

    private static final String DIR = "/home/gyang/stock_data/exchange_data";

    @Test
    public void t01() {
        File dir = new File(DIR);
        File[] fileArr = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return StringUtils.endsWithIgnoreCase(pathname.getName(), ".txt");
            }
        });

        Set<String> emptyLineSet = Sets.newHashSet();
        Set<String> duplicateCodeSet = Sets.newHashSet();

        for(File f : fileArr) {
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(f);
                LineReader lineReader = new LineReader(fileReader);
                String line = lineReader.readLine();
                if(StringUtils.isEmpty(line)) {
                    emptyLineSet.add(StringUtils.left(f.getName(), 6));

                    continue;
                }

                String startStr = StringUtils.substring(line, 0, StringUtils.indexOf(line, "("));
                String startStrTmp = null;
                while((line = lineReader.readLine()) != null) {
                    startStrTmp = StringUtils.substring(line, 0, StringUtils.indexOf(line, "("));
                    if(StringUtils.equals(startStr, startStrTmp)) {
                        duplicateCodeSet.add(StringUtils.left(f.getName(), 6));
                    }
                }
            } catch (Exception e) {
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

        if(!emptyLineSet.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(String code : emptyLineSet) {
                sb.append(code).append(",");
            }

            try {
                FileWriter fileWriter = new FileWriter("/home/gyang/stock_data/empty.txt");
                fileWriter.write(sb.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!duplicateCodeSet.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(String code : duplicateCodeSet) {
                sb.append(code).append(",");
        }

            try {
                FileWriter fileWriter = new FileWriter("/home/gyang/stock_data/duplicate.txt");
                fileWriter.write(sb.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("!!!");
    }
}