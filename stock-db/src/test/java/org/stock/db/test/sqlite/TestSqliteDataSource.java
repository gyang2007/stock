package org.stock.db.test.sqlite;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSqliteDataSource {

	@Test
	public void testDataSource() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring/applicationContext.xml" });

		SqlSessionFactory sessionFactory = (SqlSessionFactory) context
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = sessionFactory.openSession();

		User u = sqlSession.selectOne("com.gyang.test.selectUser", 3);
		System.out.println(u.getAge());
		sqlSession.close();

		context.close();
	}

	@Test
	public void testDatasource2() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring/applicationContext.xml" });

		SqlSession sqlSession = (SqlSession) context.getBean("sqlSession");

		User u = sqlSession.selectOne("com.gyang.test.selectUser", 3);
		System.out.println(u.getAge());

		context.close();
	}
	
	@Test
	public void testDatasource3() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring/applicationContext.xml" });

		SqlSession sqlSession = (SqlSession) context.getBean("sqlSession");

		 List<User> l = sqlSession.selectList("com.gyang.test.selectUserToList");
		 System.out.println(l);

		context.close();
	}
}
