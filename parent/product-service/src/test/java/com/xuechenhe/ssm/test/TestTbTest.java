package com.xuechenhe.ssm.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xuechenhe.ssm.pojo.TbTest;
import com.xuechenhe.ssm.service.TbTestService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:application-context.xml"})
public class TestTbTest {
	
	@Autowired
	
	private TbTestService tbTestService;
	@Test
	public void testInsertTbTest() throws Exception {
		TbTest tb = new TbTest();
		tb.setBirthday(new Date());
		tb.setName("王五");
		
		TbTest tb2 = new TbTest();
		tb2.setBirthday(new Date());
		tb2.setName("苍蝇");
		
		
		tbTestService.insertTest(tb);
		tbTestService.insertTest(tb2);
	}
}
