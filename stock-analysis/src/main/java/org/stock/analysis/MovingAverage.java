package org.stock.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovingAverage {

	private int MA_10 = 10;
	
	public MovingAverage() {
	
	}
	
	/**
	 * 10日均线
	 * 
	 * @param src
	 * @return
	 */
	public Map<Date, Double> ma10(Map<Date, Double> src) {
		return ma(src, MA_10);
	}
	
	public Map<Date, Double> ma(Map<Date, Double> src, int ma) {
		if(src.size() < ma) {
			return new HashMap<Date, Double>();
		}
		
		List<Date> dates = new ArrayList<Date>(src.keySet());
		Collections.sort(dates, new Comparator<Date>() {

			@Override
			public int compare(Date o1, Date o2) {
				return o1.getTime() - o2.getTime() <= 0 ? -1 : 1;
			}
		});
		
		Map<Date, Double> m = new HashMap<Date, Double>();
		for(int i = 0; (i + ma) < dates.size(); i++) {
			double total = 0.0;
			int j = 0;
			for(; j < ma; j++) {
				total += src.get(dates.get(i + j));
			}
			m.put(dates.get(i + j - 1), total / ma);
		}
		
		return m;
	}

}
