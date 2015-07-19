package org.stock.db.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.stock.db.bean.StockExchangeInfo;
import org.stock.db.common.StockType;
import org.stock.db.util.SpringService;

public class TestStockExchangeInfoService {
	
	
	@Test
	public void testSave() throws IOException, ParseException {
/*		int count = 0;
		
		String fileDirPath = "/home/gyang/.stock/files/hs";
		int type = StockType.SHH_CI.getValue();
		
		File fileDir = new File(fileDirPath);
		File[] files = fileDir.listFiles();
		for(File f : files) {
			save(f, f.getName().substring(0, f.getName().indexOf(".")), type);
			System.out.println(StockType.SHH_CI.getLabel() + ":" + count++);
		}
		
		fileDirPath = "/home/gyang/.stock/files/sh";
		type = StockType.SHZH_CI.getValue();
		fileDir = new File(fileDirPath);
		files = fileDir.listFiles();
		for(File f : files) {
			save(f, f.getName().substring(0, f.getName().indexOf(".")), type);
			System.out.println(StockType.SHZH_CI + ":" + count++);
		}*/
	}
	
	@Test
	public void testSaveMulti() {
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int count = 1;
				
				String fileDirPath = "/home/gyang/.stock/files/hs";
				int type = StockType.SH.getValue();
				
				File fileDir = new File(fileDirPath);
				File[] files = fileDir.listFiles();
				for(File f : files) {
					try {
						save(f, f.getName().substring(0, f.getName().indexOf(".")), type);
						System.out.println(StockType.SH.getLabel2() + ":" + count++);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {		
			int count = 1;
			String fileDirPath = "/home/gyang/.stock/files/sh";
			int type = StockType.SZ.getValue();
			File fileDir = new File(fileDirPath);
			File[] files = fileDir.listFiles();
			for(File f : files) {
				try {
					save(f, f.getName().substring(0, f.getName().indexOf(".")), type);
					System.out.println(StockType.SZ + ":" + count++);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void save(File file, String code, int type) throws NumberFormatException, IOException, ParseException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		String[] vals;
		List<StockExchangeInfo> infos = new ArrayList<StockExchangeInfo>();
		StockExchangeInfo info;
		reader.readLine();
		while((line = reader.readLine()) != null) {
			if(!"".equals(line.trim())) {
				vals = line.split(",");
				
				info = new StockExchangeInfo();
				info.setRecordTime(DateUtils.parseDate(vals[0], new String[]{"yyyy-MM-dd"}));
				info.setOpen(Double.valueOf(vals[1]));
				info.setHight(Double.valueOf(vals[2]));
				info.setLow(Double.valueOf(vals[3]));
				info.setClose(Double.valueOf(vals[4]));
				info.setAdjClose(Double.valueOf(vals[6]));
				info.setVolume(Long.valueOf(vals[5]));
				info.setCode(Integer.valueOf(code));
				info.setType(type);
				
				infos.add(info);
			}
		}
		reader.close();
		
		long d1 = System.currentTimeMillis();
		IStockExchangeInfoService service = (IStockExchangeInfoService) SpringService.getInstance().getService("stockExchangeInfoService");
		service.saveStockExchangeInfoList(infos);
		System.out.println("save time: " + (System.currentTimeMillis() - d1));
	}
	
	@Test
	public void testSave2() throws IOException, ParseException {
/*		long d1 = System.currentTimeMillis();
		String code = "600262";
		File f = new File("/home/gyang/.stock/files/hs/600262.csv");
		save(f, code, 10);
		System.out.println(System.currentTimeMillis() - d1);*/
	}
}
