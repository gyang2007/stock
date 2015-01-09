package org.stock.fetch.client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.stock.fetch.FetchStockBaseInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FetchStockBaseInfoClient {

	private static final String FILE_STOCK_BASE_INFO = "/home/gyang/stockBaseInfo.txt";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		FetchStockBaseInfo fetchStockBaseInfo = new FetchStockBaseInfo();
		Map<String, String> param = new HashMap<String, String>();
		
		int pageSize = 11;
		while(true) {
			param.put("page", String.valueOf(pageSize));
			param.put("size", "90");
			param.put("order", "desc");
			param.put("orderby", "code");
			param.put("type", "11,12");
			String result = fetchStockBaseInfo.fetchFromXueqiu(param);
			System.out.println(result);
			
			if(StringUtils.isBlank(result)) {
				System.out.println("break!!!");
				break;
			}
			pageSize++;
			
			parse(result);
			
			TimeUnit.MILLISECONDS.sleep(1 * 500);
		}
	}

	private static void parse(String result) {
		JSONObject jo = JSON.parseObject(result);
		if("true".equals(jo.getString("success"))) {
			parseStocks(jo.getJSONArray("stocks"));
		}
	}
	
	private static void parseStocks(JSONArray stockArr) {
		Iterator<Object> iter = stockArr.iterator();
		JSONObject stock;
		String symbol;
		String code;
		String type;
		StringBuilder outResult = new StringBuilder();
		while(iter.hasNext()) {
			stock = (JSONObject) iter.next();
			symbol = stock.getString("symbol");
			if(symbol.startsWith("SH") || symbol.startsWith("SZ")) {
				code = stock.getString("code");
				
				outResult.append(code).append(",").append(stock.getString("name")).append(",");
				
				// 上证
				if(symbol.startsWith("SH")) {
					outResult.append(1);
				}
				// 深证
				else if(symbol.startsWith("SZ")) {
					outResult.append(2);
				}
				outResult.append("\r\n");
			}
		}
		
		if(outResult.length() != 0) {
			write(outResult.toString());
		}
	}
	
	private static void write(String content) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(FILE_STOCK_BASE_INFO, true);
			fw.write(content);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
