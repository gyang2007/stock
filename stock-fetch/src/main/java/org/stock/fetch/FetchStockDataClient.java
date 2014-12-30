package org.stock.fetch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FetchStockDataClient {
	private static final String getUrl = "http://table.finance.yahoo.com/table.csv";
//	private static final String getUrl = "http://ichart.yahoo.com/table.csv";
	
/*	// 沪市股票代码
	private static final String file = "D:\\develop\\workspaces\\data\\stock\\files\\hs.csv";
	// 沪市股票文件夹
	private static final String newFileDir = "D:\\develop\\workspaces\\data\\stock\\files\\hs\\";
	*//**
	 * 沪市股票代码后缀
	 *//*
	private static final String STOCK_POSTFIX = ".SS";*/
	
	// 深市股票代码
	private static final String file = "D:\\develop\\workspaces\\data\\stock\\files\\sh.csv";
	// 深市股票文件夹
	private static final String newFileDir = "D:\\develop\\workspaces\\data\\stock\\files\\sh\\";
	/**
	 * 深市股票代码后缀
	 */
	private static final String STOCK_POSTFIX = ".SZ";
	

	/**
	 * 所有要请求资源的股票代码
	 */
	public static List<String> lists = new ArrayList<String>();
	/**
	 * 本地已存在的股票代码
	 */
	public static List<String> oldLists = new ArrayList<String>();

	public static void getList() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			String[] fields;
			while ((line = reader.readLine()) != null) {
				if (!"".equals(line)) {
					fields = line.split(" ");
					// 最后一列为股票代码
					lists.add(fields[fields.length - 1]);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Read file error!");
			e.printStackTrace();
		}

	}

	public static void getOldList() {
		File dir = new File(newFileDir);
		String[] fileNames = dir.list();

		oldLists.addAll(Arrays.asList(fileNames));
	}

	public static void getData(String code) {
		String newFileName = code + ".csv";
		if (oldLists.contains(newFileName)) {
			System.out.println(newFileName + " have already exist!");
			return;
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fetch " + code + " stock!");
		String result = HttpRequest.sendGet(getUrl, "s=" + code + STOCK_POSTFIX);
		if("".equals(result)) {
			System.out.println("Code = " + code + ", request error!!!");
			return;
		}
		
		File newFile = new File(newFileDir, code + ".csv");
		try {
			FileWriter fw = new FileWriter(newFile);
			fw.write(result);
			fw.close();
		} catch (IOException e) {
			System.out.println("getData Error!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getList();
		getOldList();
		System.out.println("Stock code size = " + lists.size());
		System.out.println("Stock file size = " + oldLists.size());

		for (int i = 0; i < lists.size(); i++) {
			getData(lists.get(i));
		}
	}
}
