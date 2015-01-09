package org.stock.fetch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestFetchStockBaseInfo {

	@Test
	public void testFetch() throws IOException {
		FetchStockBaseInfo fetchStockBaseInfo = new FetchStockBaseInfo();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("page", "1");
		param.put("size", "90");
		param.put("order", "desc");
		param.put("orderby", "code");
		param.put("type", "11,12");
		String result = fetchStockBaseInfo.fetchFromXueqiu(param);
		System.out.println(result);
	}
}
