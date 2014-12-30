package org.stock.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class TestMovingAverage {
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws IOException, NumberFormatException, ParseException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("D:/develop/workspaces/app/StockAnalyze/resource", "300133.csv")));
		String line;
		String[] lines;
		reader.readLine();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<Date, Double> map = new HashMap<Date, Double>();
		while((line = reader.readLine()) != null) {
			lines = line.split(",");
			map.put(dateFormat.parse(lines[0]), Double.valueOf(lines[6]));
		}
		
		reader.close();
		
		MovingAverage mv = new MovingAverage();
		Map<Date, Double> result = mv.ma(map, 10);
		
		for(Iterator<Entry<Date, Double>> iterator = result.entrySet().iterator(); iterator.hasNext(); ) {
			Entry<Date, Double> entry = iterator.next();
			Date date = entry.getKey();
			Double dValue = entry.getValue();
			
			String dFormat = dateFormat.format(date);
			System.out.println(dFormat + "\t" + dValue);
		}
	}

}
