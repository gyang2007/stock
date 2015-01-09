package org.stock.db.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum SpringService {
	INSTANCE;
	
	private final ApplicationContext context;
	
	private SpringService() {
		context = new ClassPathXmlApplicationContext(
				new String[] {"spring/applicationContext.xml"});
	}
	
	public static SpringService getInstance() {
		return INSTANCE;
	}
	
	public Object getService(String id) {
		return context.getBean(id);
	}
}
