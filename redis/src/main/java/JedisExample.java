import java.util.Set;
import java.util.logging.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


public class JedisExample {
	Logger log = Logger.getLogger("JedisExample.class");

	/**
	 * @param args
	 */
	public void String(Jedis jedis){
		jedis.set("mykey", "myValue");
		log.info("String example :"+jedis.get("mykey"));
	}
	public void Set(Jedis jedis){
		jedis.sadd("myset", "member1","member2");
		log.info("Set example :"+jedis.spop("myset"));
		log.info("Set example :"+jedis.spop("myset"));
		log.info("Set example :"+jedis.spop("myset"));
		
	}
	public void SortedSet(Jedis jedis){
		jedis.zadd("sortedset", 10,"v10");
		jedis.zadd("sortedset", 20,"v20");
		jedis.zadd("sortedset", 40,"v40");
		jedis.zadd("sortedset", 30,"v30");
		
		log.info("SortedSet :"+jedis.zrange("sortedset", 0,2)); // return with String
		
		Set set = jedis.zrevrange("sortedset", 0,3); // return with Set
		for(String v : (Set<String>)set){ 
			log.info("SortedSet :"+v);
		}
	}
	public void List(Jedis jedis){
		
	}
	public void Hashes(Jedis jedis){
		jedis.hset("hmap", "name", "Terry");
		jedis.hset("hmap", "address", "Seoul");
		
		log.info("Hash :"+jedis.hget("hmap", "name"));
		log.info("Hash :"+jedis.hget("hmap", "address"));
	}
	
	public void pipeLineInsert(Jedis jedis){
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		JedisPool pool = new JedisPool(config,"localhost");
		JedisExample example = new JedisExample();
		Jedis jedis = pool.getResource();
		
		try{
			example.String(jedis);
			example.Set(jedis);
			example.SortedSet(jedis);
			example.List(jedis);
			example.Hashes(jedis);
		}catch(JedisConnectionException e){
			pool.returnBrokenResource(jedis);
		}finally{
			pool.returnResource(jedis);
		}
		pool.destroy();

	}

}
