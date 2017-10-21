package com.xuechenhe.ssm.test;


import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	
	@Test
	public void testJedis() throws Exception {
		Jedis jedis = new Jedis("192.168.200.128", 6379);
		jedis.set("key1", "1");
		System.out.println(jedis.get("key1"));
		jedis.close();
	}
}
