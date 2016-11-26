package com.dev.smtm;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dev.smtm.persistence.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*.xml" })
public class MemberDAOTest {
	@Inject
	private UserDAO dao;
	private static Logger logger = LoggerFactory.getLogger(MemberDAOTest.class);

	@Test
	public void testListAll() throws Exception {

		logger.info(dao.listAll().toString());

	}
}
