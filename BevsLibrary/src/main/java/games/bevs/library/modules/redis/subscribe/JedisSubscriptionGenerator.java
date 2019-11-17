package games.bevs.library.modules.redis.subscribe;

public interface JedisSubscriptionGenerator<K> {

	K generateSubscription(String message);

}
