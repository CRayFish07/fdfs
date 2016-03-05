package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Const {

	/**
	 * redis相关参数
	 */
	public static String REDIS_HOST = null;
	public static int REDIS_PORT = 0;
	public static String REDIS_AUTH = null;
	
	
	public static String UPLOAD_TEMP_DIR = null;
	public static String UPLOAD_DIR = null;
	
	public static String KEY_PREFIX = null;
	
	static {
		InputStream inStream = Const.class.getResourceAsStream("/const.properties");
		Properties properties = new Properties();
		try {
			properties.load(inStream);
			
			REDIS_HOST = properties.getProperty("REDIS_HOST");
			REDIS_PORT = Integer.parseInt(properties.getProperty("REDIS_PORT"));
			REDIS_AUTH = properties.getProperty("REDIS_AUTH");
			
			UPLOAD_TEMP_DIR = properties.getProperty("UPLOAD_TEMP_DIR");
			UPLOAD_DIR = properties.getProperty("UPLOAD_DIR");
			KEY_PREFIX = properties.getProperty("KEY_PREFIX");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		RedisUtils.getInstance().set("lg", "thinker");
	}
	
}
