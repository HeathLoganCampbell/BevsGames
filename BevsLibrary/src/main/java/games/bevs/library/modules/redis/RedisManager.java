package games.bevs.library.modules.redis;

import java.io.IOException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;

/**
 * Thank you TehNeon :3
 */
public class RedisManager {
	private static JedisPool redis;

	public RedisManager(int minIdle, String url, String password) 
	{
		if (url.isEmpty())
			throw new UnsupportedOperationException("No redis database has been setup");

		JedisPoolConfig config = new JedisPoolConfig();

		config.setMinIdle(minIdle);
		config.setMaxIdle(99);
		config.setNumTestsPerEvictionRun(4);
		config.setTestWhileIdle(true);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setMinEvictableIdleTimeMillis(15000);
		config.setMaxTotal(100);
		config.setMaxWaitMillis(120000);
		config.setBlockWhenExhausted(false);

		redis = new JedisPool(config, url, Protocol.DEFAULT_PORT, 0, password, Protocol.DEFAULT_DATABASE, null);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public boolean equals(Object obj) {
				return obj != null && hashCode() == obj.hashCode();
			}

			public int hashCode() {
				return "redis".hashCode();
			}

			public void run() {
				redis.close();
			}
		});
	}
	
	public Jedis getRedis() 
	{
		assert redis != null;

		synchronized (redis) 
		{
			return redis.getResource();
		}
	}
	
	public void addListener(JedisPubSub listener, RedisKey notify) {
        if (notify.getKey().contains("*")) {
            System.out.println("Registered patterned listener for " + notify.getKey());

            try 
            {
                getRedis().psubscribe(listener, notify.getKey());
            }
            catch (Exception throwable) 
            {
            	throwable.printStackTrace();
                if (throwable instanceof IOException)
                    addListener(listener, notify);
            }
        } else {
            System.out.println("Registered listener for " + notify.getKey());

            try 
            {
                getRedis().subscribe(listener, notify.getKey());
            }
            catch (Exception throwable) 
            {
            	throwable.printStackTrace();

                if (throwable instanceof IOException)
                    addListener(listener, notify);
            }
        }
    }

}
