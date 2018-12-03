package com.xyj.oa.redis;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

public class Redis {

	private static final Log log = LogFactory.getLog(Redis.class);

	private static ClassPathResource cr = new ClassPathResource("redis.properties");

	private static Properties pros = new Properties();

	private static String host = "";

	private static String port = "";

	public boolean add(String keys, String value) {
//		Jedis jedis = null;
//		try {
//			if (CollectionUtils.isEmpty(pros.entrySet()) || StringHelper.isEmpty(host) || StringHelper.isEmpty(port)) {
//				pros.load(cr.getInputStream());
//				host = pros.getProperty("redis.host");
//				port = pros.getProperty("redis.port");
//			}
//			if (StringHelper.isEmpty(keys) || StringHelper.isEmpty(value))
//				return false;
//			JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
//			jedis = new Jedis(jedisShardInfo);
//			jedis.connect();
//			String[] split = keys.split(",");
//			for (String key : split) {
//				String result = jedis.set(key.getBytes(), value.getBytes());
//				if (!"OK".equalsIgnoreCase(result))
//					return false;
//			}
//			return true;
//		} catch (Exception e) {
//			log.error(LogUtil.stackTraceToString(e));
//			return false;
//		} finally {
//			if (jedis != null)
//				jedis.disconnect();
//		}
		return true;
	}
//
//	public void deleteAll() {
//		Jedis jedis = null;
//		try {
//			if (CollectionUtils.isEmpty(pros.entrySet()) || StringHelper.isEmpty(host) || StringHelper.isEmpty(port)) {
//				pros.load(cr.getInputStream());
//				host = pros.getProperty("redis.host");
//				port = pros.getProperty("redis.port");
//			}
//			JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
//			jedis = new Jedis(jedisShardInfo);
//			jedis.connect();
//			jedis.flushDB();
//		} catch (Exception e) {
//			log.error(LogUtil.stackTraceToString(e));
//		} finally {
//			if (jedis != null)
//				jedis.disconnect();
//		}
//	}
//
//	public Set<String> getAllKey() {
//		Jedis jedis = null;
//		try {
//			if (CollectionUtils.isEmpty(pros.entrySet()) || StringHelper.isEmpty(host) || StringHelper.isEmpty(port)) {
//				pros.load(cr.getInputStream());
//				host = pros.getProperty("redis.host");
//				port = pros.getProperty("redis.port");
//			}
//			JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
//			jedis = new Jedis(jedisShardInfo);
//			jedis.connect();
//			Set<String> keys = jedis.keys("*");
//			jedis.disconnect();
//			return keys;
//		} catch (Exception e) {
//			log.error(LogUtil.stackTraceToString(e));
//			return null;
//		} finally {
//			if (jedis != null)
//				jedis.disconnect();
//		}
//	}
}
