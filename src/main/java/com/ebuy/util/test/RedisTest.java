package com.ebuy.util.test;

import com.ebuy.util.util.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * $
 *
 * @author Lian
 * @date 2016/10/20
 * @since 1.0
 */
public class RedisTest {

	private Jedis jedis;

	@Before
	public void setUp() {
		jedis = RedisUtil.getJedis();
	}

	/**
	 * redis存储字符串
	 */
	@Test
	public void testString() {
		// add data
		jedis.set("name", "tingting");
		System.out.println(jedis.get("name"));

		// append
		jedis.append("name", " is my lover");
		System.out.println(jedis.get("name"));

		// delete some key
		jedis.del("name");
		System.out.println(jedis.get("name"));

		// set multi key value
		jedis.mset("name", "lian", "age", "23", "qq", "1234");
		// +1
		jedis.incr("age");
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

	}

	/**
	 * redis操作Map
	 */
	@Test
	public void testMap() {
		// add data
		Map<String, String> map = new HashMap<>();
		map.put("name", "tingting");
		map.put("age", "22");
		map.put("qq", "1234");
		jedis.hmset("user", map);

		/**
		 * 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		 * 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		 */
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);

		// delete some key of the map
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age"));
		System.out.println(jedis.hlen("user"));
		System.out.println(jedis.exists("user"));
		System.out.println(jedis.hkeys("user"));
		System.out.println(jedis.hvals("user"));

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/**
	 * redis操作List
	 */
	@Test
	public void testList() {
		// before start, remove all data
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));

		// 先向key 'java framework'中存放三条数据
		jedis.lpush("java_framework", "spring");
		jedis.lpush("java_framework", "struts");
		jedis.lpush("java_framework", "hibernate");

		/**
		 * 再取出所有数据jedis.lrange是按范围取出
		 * 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		 */
		System.out.println(jedis.lrange("java_framework", 0, -1));

		jedis.del("java_framework");
		jedis.rpush("java_framework", "spring");
		jedis.rpush("java_framework", "struts");
		jedis.rpush("java_framework", "hibernate");
		System.out.println(jedis.lrange("java_framework",0,-1));
	}

	/**
	 * redis操作Set
	 */
	@Test
	public void testSet() {
		// remove the key "user"
		jedis.del("user");

		// add data
		jedis.sadd("user", "lian");
		jedis.sadd("user", "lian");
		jedis.sadd("user", "xin");
		jedis.sadd("user", "zhong");
		jedis.sadd("user", "who");

		// remove 'who'
		jedis.srem("user", "who");

		// get all value
		System.out.println(jedis.smembers("user"));
		// check the key 'who' exists in the map
		System.out.println(jedis.sismember("user", "who"));
		// random member
		System.out.println(jedis.srandmember("user"));
		// get size of the set
		System.out.println(jedis.scard("user"));
	}

	/**
	 * jedis 排序
	 * 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
	 */
	@Test
	public void testSort() {
		// before start, remove key
		jedis.del("a");

		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));

		System.out.println(jedis.sort("a"));

		System.out.println(jedis.lrange("a", 0, -1));
	}

	@Test
	public void testRedisPool() {
//		RedisUtil.getJedis().set("newname", "中文测试");
//		System.out.println(RedisUtil.getJedis().get("newname"));

		Map<String, Person> map = new HashMap<>();
		map.put("person1", new Person());
		map.put("person2", new Person());

		jedis.hget("", "");
	}
}

class Person {

	public Person() {

	}
}
