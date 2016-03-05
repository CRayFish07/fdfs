package com.example.utils;

import redis.clients.jedis.Jedis;

public class RedisUtils {

	private static Jedis jedis = null;
	
	static {
		jedis = new Jedis(Const.REDIS_HOST, Const.REDIS_PORT);
	}
	
	public static Jedis getInstance() {
		return jedis;
	}
	
}
