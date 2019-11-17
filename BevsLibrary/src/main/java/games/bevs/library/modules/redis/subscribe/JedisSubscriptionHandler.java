package games.bevs.library.modules.redis.subscribe;

public interface JedisSubscriptionHandler<K> {

	void handleMessage(K object);

}
