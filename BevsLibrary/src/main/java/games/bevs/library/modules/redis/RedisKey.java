package games.bevs.library.modules.redis;

import games.bevs.library.commons.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RedisKey
{
	private @Getter String message;

	public RedisKey fromParams(String... strings) 
	{
		RedisKey newRedis = new RedisKey(message);

		if (strings.length != StringUtils.count(message, "%s"))
			throw new RuntimeException("Invalid number of arguements");

		for (String s : strings)
			newRedis.message = newRedis.message.replaceFirst("%s", s);
		
		return newRedis;
	}

	public String getKey() {
		if (message.contains("%s"))
			throw new RuntimeException("Invalid redisnotify " + message);
		return message;
	}
}
