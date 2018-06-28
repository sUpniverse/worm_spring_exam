package com.supniverse.myapp;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supniverse.domain.MemberVO;
import org.supniverse.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class MemberDAOTest {
	
	@Inject
	private MemberDAO dao;
	
	@Test
	public void testTime() throws Exception {
		System.out.println(dao.getTime());
	}
	
	@Test
	public void testInserMember() throws Exception {
		MemberVO vo = new MemberVO();
		vo.setUserid("user10");
		vo.setUserpw("user10");
		vo.setUsername("user10");
		vo.setEmail("user10@aaa.com");
		
		dao.insertMember(vo);		
	}
	
	@Test
	public void testReadMember() throws Exception {
		MemberVO vo = dao.readMember("user10");
		System.out.println(vo.toString());
	}
	
	@Test
	public void testReadWithPW() throws Exception {
		MemberVO vo = dao.readWithPW("user10", "user10");
		System.out.println(vo.toString());
	}

}
