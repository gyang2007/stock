package org.stock.fetch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestFetchStockExchangeInfo {
	@Test
	public void testFetch() throws IOException {
		FetchStockExchangeInfo fetch = new FetchStockExchangeInfo();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("symbol", "SZ002440");
		params.put("period", "1day");
		params.put("type", "normal");
		params.put("begin", "1389533667448");
		params.put("end", "1421069667448");
		
		String r = fetch.fetchFromXueqiu(params);
		System.out.println(r);
	}
}
