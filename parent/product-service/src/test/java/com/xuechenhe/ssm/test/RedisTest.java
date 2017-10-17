package com.xuechenhe.ssm.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:application-context.xml"})
public class RedisTest {
	@Autowired
	private Jedis jedis;
	@Test
	public void testRedis() throws Exception {
		jedis.set("key3", "2");
	    System.err.println(jedis.get("key3"));
	}
}
