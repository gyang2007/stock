package org.stock.fetch.test;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

public class Test01 {
	@Test
	public void t01() {
		
		System.out.println(DateFormatUtils.format(new Date(1389531733247L), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateFormatUtils.format(new Date(1421067733248L), "yyyy-MM-dd HH:mm:ss"));
		
		System.out.println(DateFormatUtils.format(new Date(1421068729942L), "yyyy-MM-dd HH:mm:ss"));
	}
}
