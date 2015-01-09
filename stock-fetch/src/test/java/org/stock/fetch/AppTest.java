package org.stock.fetch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.stock.fetch.util.HttpResponse;
import org.stock.fetch.util.HttpUtil;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	@Test
	public void testHtmlGet() {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = "http://xueqiu.com/stock/cata/stocklist.json?page=2&size=90&order=desc&orderby=percent&type=11%2C12&_=1420722828441";
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
//			connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Cookie", "Hm_lvt_1db88642e346389874251b5a1eded6e3=1420458400,1420556076,1420724386; __utma=1.875296525.1420458400.1420595415.1420724386.4; __utmz=1.1420458400.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); xq_a_token=4QPaIynfb9DsbSvFMrYIOG; xq_r_token=OJV7lW7O3UnAE2w3geHcPP; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1420726342; __utmc=1");
//			connection.setRequestProperty("User-Agent",	"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	@Test
	public void test01() throws IOException {
	}
}
