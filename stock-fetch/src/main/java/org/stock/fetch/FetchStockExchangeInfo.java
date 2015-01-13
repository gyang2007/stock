package org.stock.fetch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class FetchStockExchangeInfo {
	private static final String PREFIX_XUEQIU = "http://xueqiu.com/stock/forchartk/stocklist.json";
	
	private static final String COOKI_DEFAULT = "xq_a_token=JxDkzB0RJmf8aSDaHul92x; xq_r_token=69oXi8d7F1GWLWqFPFyBKP; Hm_lvt_1db88642e346389874251b5a1eded6e3=1419923552,1421067152; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1421070590";
	
	/**
	 * page=1&size=90&
	 * 
	 * @param param
	 * @return
	 * @throws IOException 
	 */
	public String fetchFromXueqiu(Map<String, String> param) throws IOException {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		propertiesMap.put("Cookie", COOKI_DEFAULT);
//		propertiesMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		propertiesMap.put("Accept-Encoding", "gzip, deflate, sdch");
//		propertiesMap.put("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4");
//		propertiesMap.put("Cache-Control", "max-age=0");
//		propertiesMap.put("Connection", "keep-alive");
//		propertiesMap.put("Host", "xueqiu.com");
//		propertiesMap.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		
		return fetchFromXueqiu(param, propertiesMap);
	}
	
	public String fetchFromXueqiu(Map<String, String> param, Map<String, String> propertiesMap) throws IOException {
		return fetch(param, propertiesMap);
	}
	
	private String fetch(Map<String, String> parameters, Map<String, String> properties) {
		String urlString = PREFIX_XUEQIU;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : parameters.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(parameters.get(key));
			i++;
		}
		urlString += param;

		URL url;
		BufferedReader in = null;
		try {
			url = new URL(urlString);
			// 打开和URL之间的连接
			URLConnection connection = url.openConnection();
			
			for (String key : properties.keySet()) {
				connection.addRequestProperty(key, properties.get(key));
			}
			
			connection.connect();
			
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line).append("\r\n");
			}
			
			return result.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}
}
