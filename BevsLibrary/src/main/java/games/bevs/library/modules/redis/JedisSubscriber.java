package games.bevs.library.modules.redis;

import java.util.logging.Logger;

import com.google.gson.JsonObject;

import games.bevs.library.modules.redis.subscribe.JedisSubscriptionGenerator;
import games.bevs.library.modules.redis.subscribe.JedisSubscriptionHandler;
import games.bevs.library.modules.redis.subscribe.impli.JsonJedisSubscriptionGenerator;
import games.bevs.library.modules.redis.subscribe.impli.StringJedisSubscriptionGenerator;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisSubscriber<K> {

	public static final JedisSubscriptionGenerator<String> STRING_GENERATOR = new StringJedisSubscriptionGenerator();
	public static final JedisSubscriptionGenerator<JsonObject> JSON_GENERATOR = new JsonJedisSubscriptionGenerator();

	protected final String channel;
	@Getter private final JedisSettings jedisSettings;
	@Getter private final Jedis jedis;
	@Getter private JedisPubSub pubSub;

	private JedisSubscriptionHandler<K> jedisSubscriptionHandler;

	public JedisSubscriber(JedisSettings jedisSettings, String channel, JedisSubscriptionGenerator<K> jedisSubscriptionGenerator, JedisSubscriptionHandler<K> jedisSubscriptionHandler) {
		this.jedisSettings = jedisSettings;
		this.channel = channel;
		this.jedisSubscriptionHandler = jedisSubscriptionHandler;

		this.pubSub = new JedisPubSub() {
			@Override
			public void onMessage(String channel, String message) 
			{
				System.out.println("message in coming redis");
				if (jedisSubscriptionGenerator != null) 
				{
					K object = jedisSubscriptionGenerator.generateSubscription(message);
					JedisSubscriber.this.jedisSubscriptionHandler.handleMessage(object);
				}
				else {
					System.out.println("Generator type is null");
				}
			}
		};

		this.jedis = new Jedis(this.jedisSettings.getAddress(), this.jedisSettings.getPort());
		this.authenticate();
		this.connect();
	}

	private void authenticate() {
		if (this.jedisSettings.hasPassword()) {
			this.jedis.auth(this.jedisSettings.getPassword());
		}
	}

	private void connect() {
		Logger.getGlobal().info("Jedis is now reading on " + this.channel);

		new Thread(() -> {
			try {
				this.jedis.subscribe(this.pubSub, this.channel);
			}
			catch (Exception e) {
				e.printStackTrace();
				Logger.getGlobal().info("For some odd reason our JedisSubscriber(" + this.channel + ") threw an exception");
				close();
				connect();
			}
		}).start();
	}

	public void close() {
		Logger.getGlobal().info("Jedis is no longer reading on " + this.channel);

		if (this.pubSub != null) {
			this.pubSub.unsubscribe();
		}

		if (this.jedis != null) {
			this.jedis.close();
		}
	}

}
