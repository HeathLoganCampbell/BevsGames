package games.bevs.library.modules.redis.subscribe.impli;


import games.bevs.library.modules.redis.subscribe.JedisSubscriptionGenerator;

public class StringJedisSubscriptionGenerator implements JedisSubscriptionGenerator<String> {

	@Override
	public String generateSubscription(String message) {
		return message;
	}

}
