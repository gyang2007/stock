package org.stock.db.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.stock.common.bean.StockBaseInfo;
import org.stock.db.common.StockType;
import org.stock.db.util.SpringService;

/**
 * 存储股票基本信息
 */
public class TestStockBaseInfoService {
	@Test
	public void testSave() throws IOException {
		List<StockBaseInfo> infos = new ArrayList<StockBaseInfo>();
		
		BufferedReader reader = new BufferedReader(new FileReader("/home/gyang/stockBaseInfo.txt"));
		String line;
		String[] vals;
		StockBaseInfo info;
		while((line = reader.readLine()) != null) {
			info = new StockBaseInfo();
			vals = line.split(",");
			
			info.setCode(Integer.valueOf(vals[0]));
			info.setName(vals[1]);
			info.setType(getType(vals[2]));
			
			infos.add(info);
		}
		
		reader.close();
		
		IStockBaseInfoService service = (IStockBaseInfoService) SpringService.getInstance().getService("stockBaseInfoService");
		service.saveStockBaseInfoList(infos);
/*		StockBaseInfo info = new StockBaseInfo();
		info.setCode(1);
		info.setName("T");
		info.setType(1);
		service.saveStockBaseInfo(info);*/
	}
	
	private int getType(String typeStr) {
		if(typeStr.endsWith("1")) {
			return StockType.STOCK.getValue();
		}
		else if(typeStr.endsWith("2")) {
			return StockType.STOCK_INDEX.getValue();
		}
		
		return -1;
	}

	@Test
	public void testSelect() {
		IStockBaseInfoService service = (IStockBaseInfoService) SpringService.getInstance().getService("stockBaseInfoService");
		System.out.println(service.selectStockBaseInfoById(20));
//		System.out.println(service.selectAllStockBaseInfoList());
	}
}
