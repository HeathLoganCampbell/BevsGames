package games.bevs.library.commons.utils;

import games.bevs.library.commons.CC;
import org.bukkit.Bukkit;


public class ServerUtils
{
	public static final String SERVER_NAME = "BevsGames";
	
	/**
	 * Says if the server is on the network
	 */
	public static boolean isOnBungee()
	{
		//return Bukkit.spigot().getSpigotConfig().getBoolean("settings.bungeecord");
		return !Bukkit.getOnlineMode();
	}
	
	public static void broadcast(String message)
	{
		Bukkit.broadcastMessage(CC.yellow + SERVER_NAME + CC.bGold + " !! " + CC.white + message);
	}
}
