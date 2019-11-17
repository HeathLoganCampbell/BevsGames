package games.bevs.library.modules.ticker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Ticker
{
	public Ticker(JavaPlugin plugin)
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
				{
					@Override
					public void run()
					{
						TimeType[] types = TimeType.values();
						
						for(int i = 0; i < types.length; i++)
							if(types[i].elapsed()) Bukkit.getPluginManager().callEvent(new TickEvent(types[i]));
					}
				}
		, 0l, 1l);
	}
}
