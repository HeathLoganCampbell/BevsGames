package games.bevs.library.modules.redis;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;

@RequiredArgsConstructor
public class JedisPublisher<K>
{
	private @NonNull JedisSettings jedisSettings;
	private @NonNull String channel;

	public void write(K message) 
	{
		Jedis jedis = null;

		try 
		{
			jedis = this.jedisSettings.getJedisPool().getResource();

			if (this.jedisSettings.hasPassword())
				jedis.auth(this.jedisSettings.getPassword());
			System.out.println("Writing to redis");
			jedis.publish(this.channel, message.toString());
		}
		finally {
			if (jedis != null)
				jedis.close();
		}
	}

	public void writeDirectly(String message) {
		Jedis jedis = null;

		try 
		{
			jedis = this.jedisSettings.getJedisPool().getResource();

			if (this.jedisSettings.hasPassword()) 
			{
				jedis.auth(this.jedisSettings.getPassword());
			}

			jedis.publish(this.channel, message);
		}
		finally 
		{
			if (jedis != null)
				jedis.close();
		}
	}

}
