package games.bevs.library.commons;

import org.bukkit.Bukkit;

public class OnlinePlayers
{
	
	public OnlinePlayers()
	{
		
	}
	
	public int playerCount()
	{
		return Bukkit.getOnlinePlayers().size();
	}
}