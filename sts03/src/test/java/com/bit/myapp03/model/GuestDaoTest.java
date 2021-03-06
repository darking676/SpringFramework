package com.bit.myapp03.model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.bit.myapp03.model.entity.GuestVo;

public class GuestDaoTest {

	private static GuestDao guestDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Resource resource = new ClassPathResource("applicationContext.xml");
		BeanFactory factory = new XmlBeanFactory(resource );
		guestDao = (GuestDao) factory.getBean("guestDao");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Before
	public void setUp() throws Exception {
		assertNotNull("dao ���� ����",guestDao);
	}

	@Test
	public void testConnection() {
		assertNotNull(((GuestDaoImpl02)guestDao).getJdbcTemplate().getDataSource());
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testselectAll() throws SQLException {
		assertNotNull(guestDao.selectAll());
		assertNotNull(guestDao.selectAll().size()>0);
	}
	
	@Test
	public void testselectOne() throws SQLException {
		assertNotNull(guestDao.selectOne(1111));
	}
	
	@Test
	public void testCrud() throws SQLException {
		int sabun = 9999;
		GuestVo target = new GuestVo(sabun, "test9", null, 9000);
		GuestVo target2 = new GuestVo(sabun, "test", null, 10000);
		assertEquals(1,guestDao.insertOne(target));
		assertEquals(1, guestDao.updateOne(target2));
		assertEquals(target2, guestDao.selectOne(sabun));
		assertEquals(1, guestDao.deleteOne(sabun));
		
	}

}
