package games.bevs.library.modules.redis.subscribe.impli;
import java.io.StringReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import games.bevs.library.modules.redis.subscribe.JedisSubscriptionGenerator;

public class JsonJedisSubscriptionGenerator implements JedisSubscriptionGenerator<JsonObject> {

	@Override
	public JsonObject generateSubscription(String message) {
		try {
			JsonReader jsonReader = new JsonReader(new StringReader(message));
			jsonReader.setLenient(true);
			return new JsonParser().parse(jsonReader).getAsJsonObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new JsonObject();
	}

}
