package com.xuechenhe.ssm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuechenhe.ssm.pojo.ad.Ad;

public class JsonTest {

	@Test
	public void testObjectToJsonStr() throws Exception {
		//模拟需要转换的数据
		List<Ad> adList = new ArrayList<Ad>();
		
		Ad ad1 = new Ad();
		ad1.setId(001L);
		ad1.setTitle("xxxxx");
		ad1.setUrl("www.xxx.com");
		
		Ad ad2 = new Ad();
		ad2.setId(002L);
		ad2.setTitle("aaaaa");
		ad2.setUrl("www.aaaaaa.com");
		
		adList.add(ad1);
		adList.add(ad2);
		
		//创建json转换类
		ObjectMapper om = new ObjectMapper();
		//设置转换对象中的null值属性不参与转换
		om.setSerializationInclusion(Include.NON_NULL);
		//将Object类型转换成json格式字符串
		String str = om.writeValueAsString(adList);
		System.out.println("========" + str);
		
				
	}
}

